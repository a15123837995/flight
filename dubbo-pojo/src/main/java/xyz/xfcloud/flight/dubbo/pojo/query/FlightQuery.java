package xyz.xfcloud.flight.dubbo.pojo.query;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询条件
 * created by Jizhi on 2019/10/10
 */
@Data
public class FlightQuery implements Serializable {

    private static final long serialVersionUID = -4520356037870098063L;

    /**
     * 出发城市三字码
     */
    private String departAirport;

    /**
     * 到达城市三字码
     */
    private String arriveAirport;

    /**
     * 出发时间，yyyy-mm-dd
     */
    private String departDate;

    /**
     * 是否携带儿童，0-不携带，1-携带
     */
    private Integer witchChild = 0;
}
