package xyz.xfcloud.flight.dubbo.consumer.mq;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import xyz.xfcloud.flight.dubbo.consumer.dao.FlightMapper;
import xyz.xfcloud.flight.dubbo.consumer.service.FlightTaskService;
import xyz.xfcloud.flight.dubbo.pojo.data.Flight;

/**
 * created by Jizhi on 2019/10/23
 */
@Component
public class ConsumerMQ {

    @Autowired
    private FlightMapper flightMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private FlightTaskService flightTaskService;

    // 使用JmsListener配置消费者监听的队列，destination是queue，message是接收到的消息
    @JmsListener(destination = "addFlight")
    // SendTo 会将此方法返回的数据, 写入到 OutQueue 中去
    @SendTo("outFlight")
    public String handleMessage(String message) {

        //System.out.println("成功接受message:" + message);
        // 将MQ中的内容转成实体类
        Flight flight = JSONObject.parseObject(message,Flight.class);

        //System.out.println(flight);
        // 插入数据库
        flightMapper.addFlight(flight);
        // 更新Redis
        redisTemplate.opsForValue().set("flight","1");
        // 收到消息后，执行一次任务
        flightTaskService.dealTask();

        return "add flight success";
    }
}
