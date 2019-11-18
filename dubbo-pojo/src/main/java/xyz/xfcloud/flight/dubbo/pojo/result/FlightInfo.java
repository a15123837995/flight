package xyz.xfcloud.flight.dubbo.pojo.result;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 航班、价格信息
 * created by Jizhi on 2019/10/10
 */
@Data
public class FlightInfo implements Serializable {

    private static final long serialVersionUID = -132436627222097072L;

    /**
     * 航班信息
     */
    private FlightDetailed flightDetailed;

    /**
     * 价格信息
     */
    private List<Fare> fares;
}
