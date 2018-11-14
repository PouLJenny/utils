package top.poul.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 
 * 
 * 
 * @author Poul.Y
 * @since 2017年8月28日 下午12:05:47
 */
public final class PropertiesUtil {

    
    
    private PropertiesUtil() {}
    
    
    /**
     * 获取properties文件key值
     * @param path  文件的位置路径，建议用以"/"开头的绝对路径，如果不是得话那就是相对于 {@link PropertiesUtil} 类所在包路径的相对路径
     * @param key
     * @return
     * @throws IOException
     * 
     */
    public static String getValue(final String path,final String key) throws IOException {
        return getProperties(path).getProperty(key);
    }
    
    
    /**
     * 获取properties
     * @param path
     * @return
     * @throws IOException
     */
    public static Properties getProperties(final String path) throws IOException {
        InputStream is = null;
        Properties properties;
        try {
            is = PropertiesUtil.class.getResourceAsStream(path);
            properties = new Properties();
            // 防止中文乱码 设置UTF-8编码
            properties.load(new InputStreamReader(is, "UTF-8"));
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return properties;
    }
    
   
    
}
