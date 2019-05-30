package top.poul.utils;

import java.util.Date;

/**
 * 计时器
 * @author Poul.Y
 * @since 19-4-3
 */
public class Timer {


	private static ThreadLocal<Date> threadLocal = new ThreadLocal<>();


	/**
	 * 开始计时
	 */
	public static void start() {
		threadLocal.set(DateUtils.now());
	}


	/**
	 * 结束计时并返回 耗时/毫秒值
	 * @return
	 */
	public static Long end() {
		long result = DateUtils.now().getTime() - threadLocal.get().getTime();
		threadLocal.remove();
		return result;
	}


}
