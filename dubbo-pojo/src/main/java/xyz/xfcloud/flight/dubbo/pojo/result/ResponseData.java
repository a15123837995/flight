package xyz.xfcloud.flight.dubbo.pojo.result;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 第三方返回结果
 * created by Jizhi on 2019/10/10
 */
@Data
public class ResponseData implements Serializable {

    private static final long serialVersionUID = 2210766134641500693L;

    /**
     * 航班列表信息
     */
    private List<FlightInfo> data;

    /**
     * 返回的code，"200"-成功
     */
    private String code;

    /**
     * 返回的result，1-成功
     */
    private Integer result;

    /**
     * 说明
     */
    private String msg;
}
