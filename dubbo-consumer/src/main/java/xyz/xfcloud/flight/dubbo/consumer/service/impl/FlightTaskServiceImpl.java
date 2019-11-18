package xyz.xfcloud.flight.dubbo.consumer.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import xyz.xfcloud.flight.dubbo.consumer.config.SmsConfig;
import xyz.xfcloud.flight.dubbo.consumer.dao.FlightMapper;
import xyz.xfcloud.flight.dubbo.consumer.service.FlightTaskService;
import xyz.xfcloud.flight.dubbo.pojo.data.Flight;
import xyz.xfcloud.flight.dubbo.pojo.query.FlightQuery;
import xyz.xfcloud.flight.dubbo.pojo.result.Fare;
import xyz.xfcloud.flight.dubbo.pojo.result.FlightDetailed;
import xyz.xfcloud.flight.dubbo.pojo.result.FlightResult;
import xyz.xfcloud.flight.dubbo.server.EmailServer;
import xyz.xfcloud.flight.dubbo.server.FlightServer;
import xyz.xfcloud.flight.dubbo.server.SmsServer;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * created by Jizhi on 2019/10/17
 */
@Service
@EnableScheduling
public class FlightTaskServiceImpl implements FlightTaskService {

    @Reference(timeout = 30000)
    private FlightServer flightServer;

    @Reference(timeout = 30000)
    private EmailServer emailServer;

    @Reference(timeout = 30000)
    private SmsServer smsServer;

    @Autowired
    private FlightMapper flightMapper;

    @Autowired
    private SmsConfig smsConfig;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void dealTask() {
        // 先查看Redis，如果没有值，就不需要再去查询数据库了
        String redisFlightKey = redisTemplate.opsForValue().get("flight");
        if ("0".equals(redisFlightKey)){
            return;
        }

        // 查询数据库，如果库中的数据也执行完了，更新Redis
        List<Flight> flights = flightMapper.getFlights();
        if (flights == null || flights.size() == 0){
            redisTemplate.opsForValue().set("flight","0");
            return;
        }
        // key-航班条件，value-为Set，针对多个人都是查询同一个航班信息的集合
        Map<String,Set<String>> map = new HashMap<>();
        for(Flight flight : flights){
            // 查询航班的条件
            String queryKey = flight.getDepartCityCode() + "_" +
                    flight.getArriveCityCode() + "_" + flight.getDepartDate();
            if (map.get(queryKey) == null){
                map.put(queryKey,new HashSet<String>());
            }

            String departTime =  flight.getDepartTime();
            String phone = flight.getPhone();
            String departCityName = flight.getDepartCity();
            String arriveCityName = flight.getArriveCity();

            // 存入筛选条件和通知信息
            map.get(queryKey).add(departTime + "," + flight.getPrice() +"," + phone + ","+
                    departCityName + "," + arriveCityName + "," + flight.getEmail() + "," +
                    flight.getId() + "," + flight.getUsername());
        }

        for (String key : map.keySet()){
            // 查询航班信息
            List<FlightResult> flightResults = queryFlight(key);
            // 根据航班信息进行处理
            dealFlightList(flightResults,map.get(key));
        }

    }

    /**
     * 处理航班信息
     * @param flightList
     * @param set
     */
    private void dealFlightList(List<FlightResult> flightList, Set<String> set){
        if (flightList == null || flightList.size() == 0){
            return;
        }
        // 按价格排序
        Collections.sort(flightList, new Comparator<FlightResult>() {
            @Override
            public int compare(FlightResult o1, FlightResult o2) {
                // 按价格进行排序
                double diff = o1.getFare().getAdultPrice().getTicketPrice() - o2.getFare().getAdultPrice().getTicketPrice();
                if (diff > 0){
                    return 1;
                }
                else if (diff < 0){
                    return -1;
                }
                return 0;
            }
        });
        // 遍历多个人
        for (String value : set){
            String[] values = value.split(",");
            String begin = values[0];
            String end = values[1];
            String price = values[2];
            String phone = values[3];
            String departCityName = values[4];
            String arriveCityName = values[5];
            String email = values[6];
            String id = values[7];
            String username = values[8];
            // 遍历航班信息
            for (FlightResult flightResult : flightList){
                FlightDetailed flightDetailed = flightResult.getFlightDetailed();
                Fare fare = flightResult.getFare();
                // 航班价格大于期望价格
                if (fare.getAdultPrice().getTicketPrice()>Double.valueOf(price)){
                    continue;
                }
                String departTime = flightDetailed.getDepartTime();
                String beginTime = departTime.substring(0,10) + " " + begin;
                String endTime = departTime.substring(0,10) + " " + end;
                // 航班时间不在期望时间之内
                if (!inTime(departTime,beginTime,endTime)){
                    continue;
                }
                // 短信内容
                String smsContext = smsContextTemplate(departTime,departCityName,arriveCityName,flightDetailed.getFlightNo(),fare.getAdultPrice().getTicketPrice()+"");
                // 邮件的正文（可以是html）
               //String emailContext = "尊敬的"+username+"您好，您期望的航班出现了。出发-到达时间为：" + departTime + " -- " + flightDetailed.getArriveTime() + "，从" + departCityName +"到" + arriveCityName + "" +
               //         "，航班号为：" + flightDetailed.getFlightNo() + ",价格为：" + fare.getAdultPrice().getTicketPrice() + "，请及时前去购买，避免价格波动造成差异";
                String emailContext = emailContextTemplate(username,price,departTime,flightDetailed.getArriveTime(),departCityName,arriveCityName,flightDetailed.getFlightNo(),fare.getAdultPrice().getTicketPrice()+"");
                // 邮件的主题
                String subject = "价格低于" + price + "的机票出现了";
                try {
                    // 调用dubbo，发送邮件通知
                    if ( emailServer.sendEmail(email,subject,emailContext)){
                        // 发送成功，更新数据库
                        flightMapper.updateFlightEmail(Integer.valueOf(id));
                    }
                    // 调用dubbo，发送短信通知
                    if (smsServer.sendSMS(phone,smsContext)){
                        // 发送成功，更新数据库
                        flightMapper.updateFlightPhone(Integer.valueOf(id));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
                break;
            }
        }
    }

    /**
     * 发送的短信内容
     * @param departTime 出发时间
     * @param departCityName 出发城市
     * @param arriveCityName 到达城市
     * @param flightNo 航班号
     * @param price 价格
     * @return 发送的短信内容
     */
    private String smsContextTemplate(String departTime,String departCityName,String arriveCityName,String flightNo,String price){
        // 从短信模板中获取内容进行组装
        String message = MessageFormat.format(smsConfig.getSmsContextTemplate(),departTime,departCityName,arriveCityName,flightNo,price);
        return message;
    }

    /**
     * 生成HTML字符串，用于发送邮件
     * @param username 姓名
     * @param exPrice 期望的价格
     * @param departTime 出发时间
     * @param arriveTime 到达时间
     * @param departCityName 出发城市
     * @param arriveCityName 到达城市
     * @param flightNo 航班号
     * @param price 航班价格
     * @return 发送的邮件内容（html）
     */
    private String emailContextTemplate(String username,String exPrice,String departTime,String arriveTime,String departCityName,String arriveCityName,String flightNo,String price){
        StringBuffer sb = new StringBuffer(2200);// 避免动态扩容造成的时间损耗
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
        sb.append("<title>通知</title>");
        sb.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>");
        sb.append("</head>");
        sb.append("<body style=\"margin-top:20px;margin-bottom:0px;margin-left:20px;margin-right:0px; padding-top:10px;padding-bottom:0px;padding-left:10px;padding-right:0px;\">");
        sb.append("<p style=\"font-size:20px\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;尊敬的"+username+"，您好：</p><br/>");
        sb.append("<p style=\"font-size:20px\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您期望的低于"+exPrice+"元的航班已经出现，请及时前去购买，避免价格波动造成差异，航班详情见下表</p><br/>");
        sb.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">");
        sb.append("<table align=\"center\" border=\"1\" cellpadding=\"0\" cellspacing=\"0\" width=\"400\" style=\"border-collapse: collapse;font-size:0px\">");
        sb.append("<tr height=\"40px\" align=\"center\"><td style=\"font-size:20px\">出发城市</td><td style=\"font-size:20px\">"+departCityName+"</td></tr>");
        sb.append("<tr height=\"40px\" align=\"center\"><td style=\"font-size:20px\">抵达城市</td><td style=\"font-size:20px\">"+arriveCityName+"</td></tr>");
        sb.append("<tr height=\"40px\" align=\"center\"><td style=\"font-size:20px\">出发时间</td><td style=\"font-size:20px\">"+departTime+"</td></tr>");
        sb.append("<tr height=\"40px\" align=\"center\"><td style=\"font-size:20px\">抵达时间</td><td style=\"font-size:20px\">"+arriveTime+"</td></tr>");
        sb.append("<tr height=\"40px\" align=\"center\"><td style=\"font-size:20px\">航班编号</td><td style=\"font-size:20px\">"+flightNo+"</td></tr>");
        sb.append("<tr height=\"40px\" align=\"center\"><td style=\"font-size:20px\">航班价格</td><td style=\"font-size:20px\">"+price+"元</td></tr>");
        sb.append("</table>");
        sb.append("</table>");
        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }

    /**
     * 查看时间是否在范围内
     * @param time
     * @param begin
     * @param end
     * @return
     */
    private boolean inTime(String time,String begin,String end){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date d = sdf.parse(time);
            Date d1 = sdf.parse(begin);
            Date d2 = sdf.parse(end);
            long ld = d.getTime();
            long ld1 = d1.getTime();
            long ld2 = d2.getTime();
            if (ld>=ld1&&ld<=ld2){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 查询航班
     * @param key
     * @return
     */
    private List<FlightResult> queryFlight(String key){
        String[] fields = key.split("_");
        // 组装查询条件
        FlightQuery query = new FlightQuery();
        query.setDepartAirport(fields[0]);
        query.setArriveAirport(fields[1]);
        query.setDepartDate(fields[2]);
        // 调用dubbo 查询航班列表
        List<FlightResult> flightResults = flightServer.search(query);
        return flightResults;
    }


    @Scheduled(cron = "0 0/30 * * * ? ")
    public void execTask(){
        //System.out.println("开始执行定时任务");
        dealTask();
        //System.out.println("定时任务执行完成");
    }
}
