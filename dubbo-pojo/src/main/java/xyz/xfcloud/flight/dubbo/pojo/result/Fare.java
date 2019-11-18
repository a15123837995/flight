package xyz.xfcloud.flight.dubbo.pojo.result;

import lombok.Data;

import java.io.Serializable;

/**
 * 舱位价格信息
 * created by Jizhi on 2019/10/10
 */
@Data
public class Fare implements Serializable {

    private static final long serialVersionUID = -6313339796989235020L;

    /**
     * 舱位名
     */
    private String cabinName;
    /**
     * 舱位代码
     */
    private String cabinCode;

    /**
     * 折扣
     */
    private String discount;

    /**
     * 燃油费
     */
    private Double fule;

    /**
     * 机建费
     */
    private Double departureTax;

    /**
     * 成人价格
     */
    private Price adultPrice;

    /**
     * 儿童价格
     */
    private Price childPrice;
}
