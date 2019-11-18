package xyz.xfcloud.flight.dubbo.consumer.service;

import xyz.xfcloud.flight.dubbo.pojo.query.FlightQuery;
import xyz.xfcloud.flight.dubbo.pojo.result.FlightResult;

import java.util.List;

/**
 * created by Jizhi on 2019/10/12
 */
public interface FlightQueryService {
    List<FlightResult> search(FlightQuery query);
}
