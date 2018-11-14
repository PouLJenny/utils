package top.poul.utils.calendar;

import java.util.HashMap;
import java.util.Map;

/**
 *  法定节假日
 * @author peng
 * @date 2018/5/22 16:29
 */
public class StatutoryHolidays {

    /**公历日期法定节假日*/
    private static Map<String,String> gregorianCalendarHolidays = new HashMap<String,String>();
    /**中国农历日期法定节假日*/
    private static Map<String,String> chineseCalendarHolidays = new HashMap<String,String>();

    static {

        gregorianCalendarHolidays.put("1-1","新年");
        gregorianCalendarHolidays.put("5-1","劳动节");
        gregorianCalendarHolidays.put("10-1","国庆节");
        gregorianCalendarHolidays.put("10-2","国庆节");
        gregorianCalendarHolidays.put("10-3","国庆节");

        chineseCalendarHolidays.put("1-1","春节");
        chineseCalendarHolidays.put("1-2","春节");
        chineseCalendarHolidays.put("1-3","春节");
        chineseCalendarHolidays.put("5-5","端午节");
        chineseCalendarHolidays.put("8-15","中秋节");

        // TODO 节气法定节假日  清明节

    }


}
