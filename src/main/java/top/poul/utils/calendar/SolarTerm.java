package top.poul.utils.calendar;

import java.math.BigDecimal;

/**
 *
 * 24节气  节气按照太阳历计算
 * https://baike.baidu.com/item/%E8%8A%82%E6%B0%94/453238?fr=aladdin
 * @author peng
 * @date 2018/5/21 20:43
 */
public enum SolarTerm {

    START_OF_SPRING("立春","每年2月3或4或5日,太阳到达黄经315°",2,4.475);
//    THE_RAINS("雨水","每年的2月18日或19或20日,太阳到达 黄经330°",2),
//    AWAKENING_OF_INSECTS("惊蛰","每年3月5日或3月6日.太阳到达黄经345°",3),
//    VERNAL_EQUINOX("春分","每年3月19日或20日或21日或22日,太阳到达黄经0°",3),
//    TOMB_SWEEPING_DAY("清明节","一般在公历4月5日前后，春分后第15日",4),
//    GRAIN_RAIN("谷雨","每年4月19日或20或21日,太阳到达黄经30°",4),
//    BEGINNING_OF_SUMMER("立夏","每年5月5或6或7日,太阳到达黄经45度",5),
//    GRAIN_BUDS("小满","每年5月18日或5月20或21或22日,太阳在黄经60°",5);
//    THE_RAINS("雨水",""),
//    THE_RAINS("雨水",""),
//    THE_RAINS("雨水",""),
//    THE_RAINS("雨水",""),
//    THE_RAINS("雨水",""),
//    THE_RAINS("雨水",""),
//    THE_RAINS("雨水",""),
//    THE_RAINS("雨水",""),
//    THE_RAINS("雨水",""),
//    THE_RAINS("雨水",""),
//    THE_RAINS("雨水",""),
//    THE_RAINS("雨水",""),
//    THE_RAINS("雨水",""),
//    THE_RAINS("雨水",""),
//    THE_RAINS("雨水",""),
//    THE_RAINS("雨水",""),
    private String name;
    private String description;
    private int month;
    private Integer day;
    /**21世纪系数*/
    private double C;
    private double D = 0.2422;
    SolarTerm(String name,String description,int month,double C){
        this.name = name;
        this.description = description;
        this.month = month;
        this.C = C;
    }


    /**
     * 计算是那一天 暂支持21世纪
     * https://zhidao.baidu.com/question/5871435.html
     * 计算公式：[Y*D+C]-L
     * @return
     */
    public int caculateDay(String year) {
        Integer Y = Integer.valueOf(year);
        BigDecimal first = new BigDecimal(Y).multiply(new BigDecimal(D)).add(new BigDecimal(C));
        BigDecimal second = new BigDecimal(Y).divide(new BigDecimal(4)).subtract(new BigDecimal(15));
        return first.subtract(second).intValue();
    }


    public static void main(String[] args) {
        int i = SolarTerm.START_OF_SPRING.caculateDay("2017");
        System.out.println(SolarTerm.START_OF_SPRING.month+ "月" + i);
    }
}
