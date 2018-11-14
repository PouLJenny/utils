package top.poul.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author peng
 * @date 2018/6/6 17:50
 */
public class RegexpUtil {

    private RegexpUtil(){

    }

    /**
     * 获取字符串中所有匹配的数据
     * @param content
     * @param pattern
     * @return
     */
    public static String[] getMathes(String content, Pattern pattern) {
        Matcher matcher = pattern.matcher(content);
        ArrayList<String> result = new ArrayList<>();
        while (matcher.find()) {
            result.add(matcher.group());
        }
        if (result.isEmpty()) {
            return null;
        }
        return result.toArray(new String[result.size()]);
    }



}
