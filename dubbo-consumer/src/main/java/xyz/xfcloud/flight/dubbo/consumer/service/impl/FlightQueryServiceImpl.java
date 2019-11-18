package xyz.xfcloud.flight.dubbo.consumer.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;
import xyz.xfcloud.flight.dubbo.consumer.service.FlightQueryService;
import xyz.xfcloud.flight.dubbo.pojo.query.FlightQuery;
import xyz.xfcloud.flight.dubbo.pojo.result.FlightResult;
import xyz.xfcloud.flight.dubbo.server.FlightServer;

import java.util.List;

/**
 * created by Jizhi on 2019/10/12
 */
@Service
public class FlightQueryServiceImpl implements FlightQueryService {

    @Reference(timeout = 10000)
    private FlightServer flightServer;

    @Override
    public List<FlightResult> search(FlightQuery query) {
        List<FlightResult> flightResults = flightServer.search(query);
        System.out.println(flightResults);
        return flightResults;
    }
}
