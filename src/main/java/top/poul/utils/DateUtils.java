package top.poul.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期工具类
 * @author 杨霄鹏
 * @since 2018-02-09 19:49:44
 */
public class DateUtils {


	private DateUtils(){

	}

	public static final ThreadLocal<DateFormat> DF_yyyy_MM_dd = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

	public static final ThreadLocal<DateFormat> DF_yyyyMMdd = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMdd"));

	public static final ThreadLocal<DateFormat> DF_yyyy_MM_dd_HHmmss = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

	private static final ThreadLocal<Map<String,DateFormat>> cache = ThreadLocal.withInitial(HashMap::new);


	public static DateFormat getDateFormat(String pattern) {
		return cache.get().computeIfAbsent(pattern, p -> new SimpleDateFormat(pattern));
	}

	/**
	 * 获取当前日期
	 * @return
	 */
	public static Date now() {
		return Date.from(Instant.now());
	}

}
