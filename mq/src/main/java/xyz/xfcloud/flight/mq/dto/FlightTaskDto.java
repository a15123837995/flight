package xyz.xfcloud.flight.mq.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * created by Jizhi on 2019/10/29
 */
@Data
public class FlightTaskDto implements Serializable {
    private static final long serialVersionUID = 4479851290270462912L;
    /**
     * 姓名
     */
    private String username;
    /**
     * 期望价格
     */
    private Double price;
    /**
     * 电话
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 出发城市
     */
    private String departCity;
    /**
     * 到达城市
     */
    private String arriveCity;
    /**
     * 出发日期
     */
    private String departDate;
    /**
     * 最早时间
     */
    private String beginTime;
    /**
     * 最晚时间
     */
    private String endTime;
}
