package xyz.xfcloud.flight.dubbo.pojo.result;

import lombok.Data;

import java.io.Serializable;

/**
 * 航班结果
 * created by Jizhi on 2019/10/10
 */
@Data
public class FlightResult implements Serializable {

    private static final long serialVersionUID = -2108605086562698466L;

    private FlightDetailed flightDetailed;

    private Fare fare;
}
