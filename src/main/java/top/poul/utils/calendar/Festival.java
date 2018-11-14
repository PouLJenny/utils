package top.poul.utils.calendar;

import java.util.HashMap;
import java.util.Map;

/**
 * 节日
 * 阴历： 按月亮的月相周期来安排的历法
 * 阳历： 是以地球绕太阳公转的运动周期为基础而制定的历法。
 * 中国农历是一种阴阳合历
 * @author peng
 * @date 2018/5/21 20:02
 */
public class Festival {

    /**公历日期节日*/
    private static Map<String,String> gregorianCalendarFestival = new HashMap<String,String>();
    /**中国农历日期节日*/
    private static Map<String,String> chineseCalendarFestival = new HashMap<String,String>();

    static {
        initGregorianCalendarFestival();
        initChineseCalendarFestival();
    }

    /**
     * 初始化公历节日
     */
    private static void initGregorianCalendarFestival() {
        gregorianCalendarFestival.put("1-1","元旦");
        gregorianCalendarFestival.put("2-14","情人节");
        gregorianCalendarFestival.put("3-8","妇女节");
        gregorianCalendarFestival.put("3-12","植树节");
        gregorianCalendarFestival.put("4-1","愚人节");
        gregorianCalendarFestival.put("5-1","劳动节");
        gregorianCalendarFestival.put("5-4","青年节");
        gregorianCalendarFestival.put("6-1","儿童节");
        gregorianCalendarFestival.put("8-1","建军节");
        gregorianCalendarFestival.put("9-10","教师节");
        gregorianCalendarFestival.put("12-24","平安夜");
        gregorianCalendarFestival.put("12-25","圣诞节");
    }

    /**初始化中国农历节日*/
    private static void initChineseCalendarFestival() {
        chineseCalendarFestival.put("1-1","春节");
        chineseCalendarFestival.put("1-15","元宵节");
        chineseCalendarFestival.put("3-3","上巳节");
        chineseCalendarFestival.put("5-5","端午节");
        chineseCalendarFestival.put("7-7","七夕节");
        chineseCalendarFestival.put("7-15","中元节");
        chineseCalendarFestival.put("8-15","中秋节");
        chineseCalendarFestival.put("9-9","重阳节");
        chineseCalendarFestival.put("10-1","寒衣节");
        chineseCalendarFestival.put("10-15","下元节");
        chineseCalendarFestival.put("12-8","腊八节");
        chineseCalendarFestival.put("12-30","除夕");
    }



}
