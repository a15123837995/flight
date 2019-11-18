package xyz.xfcloud.flight.dubbo.pojo.data;

import lombok.Data;

import java.io.Serializable;

/**
 * created by Jizhi on 2019/10/17
 */
@Data
public class Flight implements Serializable {

    private static final long serialVersionUID = 7340217674495474605L;

    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 出发城市
     */
    private String departCity;

    /**
     * 出发城市三字码
     */
    private String departCityCode;

    /**
     * 到达城市
     */
    private String arriveCity;

    /**
     * 到达城市三字码
     */
    private String arriveCityCode;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 联系人
     */
    private String username;

    /**
     * 期望价格
     */
    private Double price;

    /**
     * 期望出发日期 yyyy-MM-dd
     */
    private String departDate;

    /**
     * 期望出发时间段，时间段在10点到18点
     * 10：00,18:00
     */
    private String departTime;

    /**
     * 是否已经发送了短信 0-否，1-是
     */
    private Integer sms;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 是否已经发送了邮件 0-否，1-是
     */
    private Integer sendEmail;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 创建时间
     */
    private String createTime;

}
