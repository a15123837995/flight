package xyz.xfcloud.flight.util;

import java.text.MessageFormat;

/**
 * created by Jizhi on 2019/10/12
 */
public class Test {


    public static void main(String[] args) {
        String message = MessageFormat.format("您好{0}，晚上好！您目前余额：{1}元，积分：{2}", "张三", 10.155, 10,11);
        System.out.println(message);
    }

}
