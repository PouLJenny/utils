package top.poul.utils;

import java.text.NumberFormat;

public class StringUtil {


    private StringUtil() {

    }


    /**
     * 将数字格式化成固定长度的字符串
     * @param stringSize
     * @param number
     * @return
     */
    public static String formatInteger(final int stringSize,final Integer number) {
        if (String.valueOf(number).length() > stringSize) {
            throw new IllegalArgumentException("数字长度过长，stringSize = " + stringSize);
        }
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumIntegerDigits(stringSize);
        nf.setMinimumIntegerDigits(stringSize);
        nf.setGroupingUsed(false);
        nf.setMaximumFractionDigits(0);
        return nf.format(number);
    }
	
	
	
	
	
}
