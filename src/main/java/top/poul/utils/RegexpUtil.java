package top.poul.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    public static String[] getMatches(String content, Pattern pattern) {
        List<String> result = getMatchesList(content, pattern);
        if (result == null) {
            return null;
        }
        return result.toArray(new String[result.size()]);
    }

    public static List<String> getMatchesList(String content, Pattern pattern) {
        Matcher matcher = pattern.matcher(content);
        ArrayList<String> result = new ArrayList<>();
        while (matcher.find()) {
            result.add(matcher.group());
        }
        return result;
    }



    public static void main(String[] args) {
       String str = "nihao[天,地]ddd";
        Pattern compile = Pattern.compile("\\[.+]");
        List<String> matchesList = getMatchesList(str, compile);
        System.out.println(matchesList);
    }

}
