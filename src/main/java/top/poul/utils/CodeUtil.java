package top.poul.utils;


/**
 * 
 * @author 杨霄鹏
 * @since 2018-01-04 11:04:27
 */
public final  class CodeUtil {

    
    private CodeUtil(){}
    
    
    /**
     * 数字随机验证码池
     */
    private static final char [] DIGIT_CODE_POOL = {'0','1','2','3','4','5','6','7','8','9'};
    
    
    /**
     * 获取当前系统上jvm的编码
     * @return
     */
    public static String getJvmEncoding() {
//        return Charset.defaultCharset().displayName();
        return System.getProperty("file.encoding");
    }
    
    
    /**
     * 获取[lowerValue,upperValue) 之间的整数
     * @param lowerValue
     * @param upperValue
     * @return
     */
    public static int random(final int lowerValue, final int upperValue) {
    	
    	if (lowerValue < 0 || upperValue < 0 || lowerValue > upperValue) {
    		throw new IllegalArgumentException("参数不合法");
    	}
    	
    	int choices = upperValue - lowerValue;
    	
    	return  (int)Math.floor(Math.random() * choices + lowerValue);
    	
    }
    
    /***
     * 获取length长度的数字验证码
     * length > 0
     * @param length
     * @return
     */
    public static String randomCode(final int length) {
    	if (length < 1) {
    		throw new IllegalArgumentException("length不能小于1");
    	}
    	
    	StringBuilder sb = new StringBuilder();
    	
    	for (int i = 1; i <= length; i++) {
    		sb.append(DIGIT_CODE_POOL[random(0, DIGIT_CODE_POOL.length)]);
    	}
    	
    	return sb.toString();
    }
    
    
    
    
}
