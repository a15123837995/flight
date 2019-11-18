package xyz.xfcloud.flight.dubbo.pojo.result;

import lombok.Data;

import java.io.Serializable;

/**
 * 价格信息
 * created by Jizhi on 2019/10/10
 */
@Data
public class Price implements Serializable {

    private static final long serialVersionUID = 7551961709838321389L;

    /**
     * 票价
     */
    private Double ticketPrice;

    /**
     * 总价
     */
    private Double totalSettlePrice;
}
