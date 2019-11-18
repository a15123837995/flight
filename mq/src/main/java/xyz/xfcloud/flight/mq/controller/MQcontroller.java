package xyz.xfcloud.flight.mq.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.xfcloud.flight.dubbo.pojo.data.CityData;
import xyz.xfcloud.flight.dubbo.pojo.data.Flight;
import xyz.xfcloud.flight.mq.dto.FlightTaskDto;
import xyz.xfcloud.flight.mq.provider.ProviderMQ;

/**
 * created by Jizhi on 2019/10/23
 */

@Controller
public class MQcontroller {

    @Autowired
    private ProviderMQ mq;

    /**
     * 报文测试，Flight的json字符串
     * @param message
     * @return
     */
    @PostMapping("/send")
    @ResponseBody
    public String sendMQ(@RequestBody String message){
        // 发送至MQ
        mq.sendMessage(message);
        return "success";
    }

    /**
     *  跳转至表单填写
     * @return
     */
    @RequestMapping("/form")
    public String form(){
        return "form";
    }

    /**
     * 接收表单数据
     * @param taskDto
     * @return
     */
    @RequestMapping("/addTask")
    public String submit(FlightTaskDto taskDto){
        Flight flight = new Flight();
        flight.setDepartCity(taskDto.getDepartCity());
        flight.setDepartCityCode(CityData.cityMap.get(taskDto.getDepartCity()));
        flight.setArriveCity(taskDto.getArriveCity());
        flight.setArriveCityCode(CityData.cityMap.get(taskDto.getArriveCity()));
        flight.setDepartDate(taskDto.getDepartDate());
        // 最早、最晚时间如果有差，进行翻转
        if (taskDto.getBeginTime().compareTo(taskDto.getEndTime()) < 0){
            flight.setDepartTime(taskDto.getBeginTime()+","+taskDto.getEndTime());
        }
        else {
            flight.setDepartTime(taskDto.getEndTime()+","+taskDto.getBeginTime());
        }
        flight.setEmail(taskDto.getEmail());
        flight.setPhone(taskDto.getPhone());
        flight.setPrice(taskDto.getPrice());
        flight.setUsername(taskDto.getUsername());
        String flightJSON = JSONObject.toJSONString(flight);
        System.out.println(flightJSON);
        // 发送至MQ
        mq.sendMessage(flightJSON);
        return "redirect:/submit";
    }

    /**
     * 跳转到成功页
     * @return
     */
    @RequestMapping("/submit")
    public String submit(){
        return "success";
    }
}
