package xyz.xfcloud.flight.dubbo.server;

import xyz.xfcloud.flight.dubbo.pojo.query.FlightQuery;
import xyz.xfcloud.flight.dubbo.pojo.result.FlightResult;

import java.util.List;

/**
 * 航班查询接口
 * created by Jizhi on 2019/10/10
 */
public interface FlightServer {

    /**
     * 查询航班列表
     * @param query
     * @return
     */
    List<FlightResult> search(FlightQuery query);
}
