package xyz.xfcloud.flight.dubbo.service.query.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.xfcloud.flight.dubbo.pojo.query.FlightQuery;
import xyz.xfcloud.flight.dubbo.pojo.result.*;
import xyz.xfcloud.flight.dubbo.server.FlightServer;
import xyz.xfcloud.flight.dubbo.service.config.TmcConfig;
import xyz.xfcloud.flight.util.HttpSendTool;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询航班列表
 * created by Jizhi on 2019/10/12
 */
@Service
public class FlightServiceImpl implements FlightServer {

    @Autowired
    private TmcConfig config;


    /**
     * 查询航班列表
     * @param flightQuery
     * @return
     */
    @Override
    public List<FlightResult> search(FlightQuery flightQuery) {
        String requestBody = JSONObject.toJSONString(flightQuery);
        String requestURL = config.getUrl();
        Map<String,String> header = new HashMap<>();
        header.put("Content-Type","application/json");
        header.put(config.getHeaderKey(),config.getHeaderValue());
        String responseBody = HttpSendTool.sendPostJson(requestURL,requestBody,header);
        ResponseData data = JSONObject.parseObject(responseBody,ResponseData.class);
        return merge(data);
    }

    /**
     * 整合航班信息
     * @param data
     * @return
     */
    private List<FlightResult> merge(ResponseData data){
        List<FlightInfo> infoList = data.getData();
        List<FlightResult> flightResults = new ArrayList<>();
        for (FlightInfo info : infoList){
            FlightDetailed flightDetailed = info.getFlightDetailed();
            Fare fare = info.getFares().get(0);
            FlightResult flightResult = new FlightResult();
            flightResult.setFare(fare);
            flightResult.setFlightDetailed(flightDetailed);
            flightResults.add(flightResult);
        }
        return flightResults;
    }
}
