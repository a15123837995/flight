package xyz.xfcloud.flight.dubbo.pojo.result;

import lombok.Data;

import java.io.Serializable;

/**
 * 航班信息
 * created by Jizhi on 2019/10/10
 */
@Data
public class FlightDetailed implements Serializable {

    private static final long serialVersionUID = -4515834266940633259L;

    /**
     * 航班号
     */
    private String flightNo;

    /**
     * 出发机场三字码
     */
    private String departAirport;

    /**
     * 出发机场名
     */
    private String departAirportName;

    /**
     * 出发航站楼
     */
    private String departTerminal;

    /**
     * 到达机场三字码
     */
    private String arriveAirport;

    /**
     * 到达机场名
     */
    private String arriveAirportName;

    /**
     * 到达航站楼
     */
    private String arriveTerminal;

    /**
     * 出发时间
     */
    private String departTime;

    /**
     * 到达时间
     */
    private String arriveTime;

    /**
     * 航空公司代码
     */
    private String airline;

    /**
     * 航空公司名
     */
    private String airlineName;

    /**
     * 飞机名
     */
    private String planeName;

    /**
     * 是否有餐食，"0"-无，"1"-有
     */
    private String hasFood;

    /**
     * 是否共享，"0"-否，"1"-是
     */
    private String isCodeShare;

    /**
     * 共享航班号
     */
    private String shareFlightNo;

    /**
     * 实际承运航空公司代码
     */
    private String actualAirline;

    /**
     * 实际承运航空公司名
     */
    private String actualAirlineName;

    /**
     * 里程
     */
    private String mileage;

    /**
     * 总耗时
     */
    private String duration;

    /**
     * 是否经停，0-否，1-是
     */
    private Integer stopCount;

    /**
     * 经停城市
     */
    private String stopSites;
}
