package top.poul.utils;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 *  中华人民共和国第二代居民身份证校验工具
 *
 *  第二代居民身份证一共18位 根据GB 11643-1999中有关公民身份号码的规定
 *  从左向右前17位本体码master number
 *     前6位数字地址码 GB/T 2260
 *     7~14位为8位数字出生日期码
 *     15~17是3位顺序码 奇数分配给男性，偶数分配给女性
 *     最后一位是数字校验码
 *  最后一位校验码check number
 *
 */
public class IDCardUtils {

    private IDCardUtils() {
        throw new AssertionError("No com.peng.utils.IDCardUtils instances for you!");
    }

    private static final int LENTH = 18;
    /** 身份证正则表达式 */
    private static final Pattern ID_CARD_RULE = Pattern.compile("^\\d{6}\\d{8}\\d{3}[0-9|X]$");
    /** 每位加权因子 */
    private static final int[] POWER ;// = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};

    static {
        POWER = new int[LENTH];
        for (int i = 0,l = LENTH;i < LENTH;i++,l--) {
            POWER[i] = (1 << (l-1)) % 11;
        }
    }

    /**
     * 校验身份号码是否正确
     * @param idCardNo
     * @return
     */
    public static boolean isRightNo(String idCardNo) {
        Objects.requireNonNull(idCardNo);
        if (!ID_CARD_RULE.matcher(idCardNo).matches()) {
            return false;
        }
        String checkNum = getCheckNum(idCardNo);
        String calculateCheckNumber = calculateCheckNumber(getMasterNo(idCardNo).toCharArray());
        return Objects.equals(checkNum,calculateCheckNumber);
    }

    public static boolean notRightNo(String idCardNo) {
        return !isRightNo(idCardNo);
    }

    /**
     * 通过本体码计算校验码
     * @param masterNumber
     * @return
     */
    private static String calculateCheckNumber(char[] masterNumber) {
        int sum = 0;
        for (int i = 0; i < 17;i++) {
            sum += Integer.valueOf(String.valueOf(masterNumber[i])) * POWER[i];
        }

        int i = sum % 11;

        switch (i) {
            case 0:
                return "1";
            case 1:
                return "0";
            case 2:
                return "X";
            case 3:
                return "9";
            case 4:
                return "8";
            case 5:
                return "7";
            case 6:
                return "6";
            case 7:
                return "5";
            case 8:
                return "4";
            case 9:
                return "3";
            case 10:
                return "2";
            default:
                throw new RuntimeException("校验码计算错误");
        }
    }

    /**
     * 获取17位本体数字码
     * @param idCardNo
     * @return
     */
    public static String getMasterNo(String idCardNo) {
        return idCardNo.substring(0,17);
    }

    /**
     * 获取1位检验码
     * @param idCardNo
     * @return
     */
    public static String getCheckNum(String idCardNo) {
        return String.valueOf(idCardNo.charAt(17));
    }

    /**
     * 获取6位地址数字码
     * @param idCardNo
     * @return
     */
    public static String getAddrNo(String idCardNo) {
        return idCardNo.substring(0,6);
    }

    /**
     * 获取8位的出生日期数字码
     * @param idCardNo
     * @return
     */
    public static String getBirthNo(String idCardNo) {
        return idCardNo.substring(6,14);
    }

    /**
     * 获取3位顺序数字码
     * @param idCardNo
     * @return
     */
    public static String getOrderNo(String idCardNo) {
        return idCardNo.substring(14,17);
    }

    /**
     * 是否是女性
     * @return
     */
    public static boolean isFemale(String idCardNo) {
        String orderNo = getOrderNo(idCardNo);
        // 偶数是女性
        return (Integer.valueOf(orderNo) & 1) == 0;
    }
    /**
     * 是否是男性
     * @return
     */
    public static boolean isMale(String idCardNo) {
        return !isFemale(idCardNo);
    }

    public static void main(String[] args) {

        String idcardNo = "372324199308271012";
        boolean b = isRightNo(idcardNo);
        System.out.println(idcardNo.charAt(17));


    }


}
