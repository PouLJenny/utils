package top.poul.utils;

/**
 * unicode编码与字符的转换
 * @author Poul.Y
 * @version 1.0
 * @since 1.0
 */
public class Unicode {

    /**
     * 将字符串转换成unicode编码
     * @author Poul.Y
     * @date 2016年11月24日 下午10:49:51
     * @since 1.0
     * @param str
     * @return
     */
    public static String toUnicode(String str){
        
        char[] charArray = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        String s = null;
        for (int i = 0, l = charArray.length; i < l; i++) {
            s = Integer.toHexString(Integer.valueOf(charArray[i]));
            for (int j = 0, k = s.length(); j < (4 - k); j++) {
                s = "0" + s;
            }
            sb.append("\\u").append(s);
        }
        return sb.toString();
    }
    
    /**
     * 将unicode编码转换成字符串
     * @author Poul.Y
     * @date 2016年11月24日 下午10:52:35
     * @since 1.0
     * @param str
     * @return
     */
    public static String toString(String str){
        
        String[] split = str.split("\\\\u");
        StringBuilder sb = new StringBuilder();
        for (int i = 0, l = split.length; i < l; i++ ){
            if (split[i].length() > 0) {
                sb.append((char)(int)(Integer.valueOf(split[i], 16)));
            }
        }
        return sb.toString();
    } 
    
    
}
