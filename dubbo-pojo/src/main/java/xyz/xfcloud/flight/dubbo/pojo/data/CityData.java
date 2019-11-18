package xyz.xfcloud.flight.dubbo.pojo.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * created by Jizhi on 2019/10/10
 */
public class CityData implements Serializable {

    private static final long serialVersionUID = 2681964234400536752L;

    /**
     * 城市三字码
     */
    public static Map<String,String> cityMap = new HashMap<String, String>();

    static {
        cityMap.put("秦皇岛","SHP");cityMap.put("安庆","AQG");cityMap.put("包头","BAV");
        cityMap.put("北海福城","BHY");cityMap.put("广州","CAN");cityMap.put("常德","CGD");
        cityMap.put("郑州","CGO");cityMap.put("长春","CGQ");cityMap.put("赤峰","CIF");
        cityMap.put("长治","CIH");cityMap.put("清州","CJJ");cityMap.put("济州","CJU");
        cityMap.put("重庆","CKG");cityMap.put("长沙","CSX");cityMap.put("成都","CTU");
        cityMap.put("常州","CZX");cityMap.put("大连","DLC");cityMap.put("敦煌","DNH");
        cityMap.put("东营","DOY");cityMap.put("张家界","DYG");cityMap.put("延安","ENY");
        cityMap.put("阜阳","FIG");cityMap.put("福州","FOC");cityMap.put("广元","GYS");
        cityMap.put("海口","HAK");cityMap.put("呼和浩特","HET");cityMap.put("合肥","HFE");
        cityMap.put("杭州","HGH");cityMap.put("海拉尔","HLD");cityMap.put("哈尔滨","HRB");
        cityMap.put("舟山","HSN");cityMap.put("台州","HYN");cityMap.put("银川","INC");
        cityMap.put("景德镇","JDZ");cityMap.put("嘉峪关","JGN");cityMap.put("吉林","JIL");
        cityMap.put("泉州","JJN");cityMap.put("雅加达","JKT");cityMap.put("锦州","JNZ");
        cityMap.put("衢州","JUZ");cityMap.put("南昌","KHN");cityMap.put("昆明","KMG");
        cityMap.put("赣州","KOW");cityMap.put("贵阳","KWE");cityMap.put("桂林","KWL");
        cityMap.put("兰州","LHW");cityMap.put("丽江","LJG");cityMap.put("拉萨","LXA");
        cityMap.put("洛阳","LYA");cityMap.put("连云港","LYG");cityMap.put("临沂","LYI");
        cityMap.put("柳州","LZH");cityMap.put("泸州","LZO");cityMap.put("牡丹江","MDG");
        cityMap.put("绵阳","MIG");cityMap.put("齐齐哈尔","NDG");cityMap.put("宁波","NGB");
        cityMap.put("南京","NKG");cityMap.put("南宁","NNG");cityMap.put("南阳","NNY");
        cityMap.put("南通","NTG");cityMap.put("北京","PEK");cityMap.put("槟城","PEN");
        cityMap.put("上海浦东","PVG");cityMap.put("上海虹桥","SHA");cityMap.put("西安","SIA");
        cityMap.put("石家庄","SJW");cityMap.put("汕头","SWA");cityMap.put("三亚","SYX");
        cityMap.put("深圳","SZX");cityMap.put("青岛","TAO");cityMap.put("塔什干","TAS");
        cityMap.put("铜仁","TEN");cityMap.put("济南","TNA");cityMap.put("天津","TSN");
        cityMap.put("黄山","TXN");cityMap.put("太原","TYN");cityMap.put("乌鲁木齐","URC");
        cityMap.put("潍坊","WEF");cityMap.put("威海","WEH");cityMap.put("温州","WNZ");
        cityMap.put("武汉","WUH");cityMap.put("武夷山","WUS");cityMap.put("万县","WXN");
        cityMap.put("襄樊","XFN");cityMap.put("锡林","XIL");cityMap.put("厦门","XMN");
        cityMap.put("西宁","XNN");cityMap.put("徐州","XUZ");cityMap.put("宜宾","YBP");
        cityMap.put("宜昌","YIH");cityMap.put("义乌","YIW");cityMap.put("延吉","YNJ");
        cityMap.put("烟台","YNT");cityMap.put("湛江","ZHA");cityMap.put("珠海","ZUH");
    }
}
