package top.poul.utils;
/**
 * 普通常用的工具类
 * @author 杨霄鹏
 *
 */
public class CommonUtils {

    /**
     * 私有构造器无法实例化
     */
    private CommonUtils() {
        
    }
    
    /**
     * 校验字符串是否是空
     * @param 待检测的字符串
     * @return 如果是空字符串或者是null返回true
     */
    public static boolean isEmpty(String params) {
        return params == null || params.trim().length() < 1;
    }
    
    /**
     * 判断是否含有空字符串
     * @param params
     * @return
     */
    public static boolean hasEmpty(String... params) {
    	for (String s : params) {
    		if(!isEmpty(s)){
    			return false;
    		}
    	}
    	return true;
    }
    
}
