package top.poul.utils;
/**
 * JSON工具类
 * @author 杨霄鹏  
 * @since 2017年5月7日 下午3:24:29
 */
public class JsonUtil {

	// 导致JSON解析异常的字符集
	private static final String[] ABNORMAL_CHARACTOR;
	
	static {
		ABNORMAL_CHARACTOR = new String[34];
		for (int i = 0; i <= 31; i++) {
			ABNORMAL_CHARACTOR[i] = String.valueOf((char) i);
		}

		ABNORMAL_CHARACTOR[32] = String.valueOf((char) 34);
		ABNORMAL_CHARACTOR[33] = "\\\\";
	}
	
	/**
	 * 私有化构造器，无法实例化
	 */
	private JsonUtil() {

	}
	
	/**
	 * 删除所有可以影响JSON解析的特殊字符
	 * @param str
	 * @return
	 * @since 2017年5月7日 下午3:26:53
	 */
	public static String replaceAbnormalCharactor(final String str) {
		String result = str;
		for(String c : ABNORMAL_CHARACTOR) {
			 result = result.replaceAll(c, "");
		}
		return result;
	}
	
	
	
	
}
