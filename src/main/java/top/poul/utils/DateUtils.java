package top.poul.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 * 日期工具类
 * @author 杨霄鹏
 * @since 2018-02-09 19:49:44
 */
public class DateUtils {


	private DateUtils(){}


	public static final ThreadLocal<DateFormat> DF_yyyy_MM_dd = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};

	public static final ThreadLocal<DateFormat> DF_yyyyMMdd = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyyMMdd");
		}
	};

	public static final ThreadLocal<DateFormat> DF_yyyy_MM_dd_HHmmss = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

}
