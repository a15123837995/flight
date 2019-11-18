package xyz.xfcloud.flight.dubbo.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.xfcloud.flight.dubbo.consumer.dao.FlightMapper;
import xyz.xfcloud.flight.dubbo.consumer.service.FlightTaskService;
import xyz.xfcloud.flight.dubbo.pojo.data.Flight;

/**
 * created by Jizhi on 2019/10/17
 */
@RestController
public class TaskController {

    @Autowired
    private FlightMapper flightMapper;

    @Autowired
    private FlightTaskService flightTaskService;

    @RequestMapping("/task")
    public String submitTask(@RequestBody Flight flight){
        flightMapper.addFlight(flight);
        return "success";
    }

    @RequestMapping("/execTask")
    public String execTask(){
        flightTaskService.dealTask();
        return "exec";
    }
}
