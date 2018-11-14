package top.poul.utils;

import java.util.*;
import java.util.Map.Entry;

/**
 * 
 * 数学类的工具类
 * 
 * @author 杨霄鹏 2017年4月7日 上午9:46:14
 *
 */

public class MathUtil {

    private MathUtil() {

    }

    /**
     * 冥运算函数 b^n
     * 
     * @param b
     *            底数
     * @param n
     *            指数
     * @return 结果
     */
    public static long exponent(int b, int n) {
        if (n == 0) {
            return 1;
        }

        if (n == 1) {
            return b;
        }

        long result = b;

        for (int y = 2; y <= n; y++) {
            result *= b;
        }

        return result;
    }

    /**
     * 四舍五入运算，可以精确到小数点后几位进行四舍五入运算
     * 
     * @param a
     *            待计算的参数
     * @param b
     *            精确到小数点后几位，如果是0的话就是精确到小数点第0位，即不保留小数点后边的值
     * @return
     */
    public static double round(double a, int b) {
        double result;

        if (b >= 0) {
            double buffer1 = (double) exponent(10, b);
            double buffer2 = new Double(Math.round(a * buffer1));
            result = buffer2 / buffer1;
        } else {
            b = -b;
            double buffer1 = (double) exponent(10, b);
            double buffer2 = new Double(Math.round(a / buffer1));
            result = buffer2 * buffer1;
        }

        return result;
    }

    
    /**
     * 计算整形数的二进制原码表示
     * @param a
     * @return
     */
    public static String binary(long a) {

        StringBuffer sb = new StringBuffer();

        long base = a;
        long quotient; // 商
        long remainder;// 余数

        do {
            quotient = base / 2;
            remainder = base % 2;

            base = quotient;

            sb.append(remainder);
        } while (quotient != 0);

        return sb.reverse().toString();
    }
    
    
    /**
     * 概论中奖算法
     * @param prizePool 奖池 中奖概率
     * @return 中奖人
     */
    public static String probabilityGet(Map<String,Integer> prizePool) {
        
        Set<Entry<String,Integer>> entrySet = prizePool.entrySet();
        Iterator<Entry<String, Integer>> iterator = entrySet.iterator();
        
        List<String> pool = new ArrayList<String>();
        int s = 0;
        int e = 0;
        int max = 0;
        // 奖池赋值初始化
        while (iterator.hasNext()) {
            
            Entry<String, Integer> next = iterator.next();
            String key = next.getKey();
            Integer value = next.getValue();
            max += value;
            
            if (max > 100) {
                throw new IllegalArgumentException("总概率不能大于100！");
            }
            
            for (e = s + value; s < e; s++) {
                pool.add(key);
            }
            
        }
        
        if (max < 100) {
            
            for (;s < 100; s++) {
                pool.add("");
            }
            
        }
        
        // 打乱集合排序
        Collections.shuffle(pool);
        
        // 随机[0,100)的一个数
        int luckyMan = selectForm(0, 100);        
        
        return pool.get(luckyMan);
    }
    
    /**
     * 获取[lowerValue,upperValue) 之间的整数
     * @param lowerValue
     * @param upperValue
     * @return
     */
    public static  Integer selectForm(int lowerValue, int upperValue) {
        int choices = upperValue - lowerValue;
        return (int)Math.floor(Math.random() * choices + lowerValue);
    }


    /**
     * 判断是否是偶数
     * @param num
     * @return
     */
    public static boolean isEvenNumber(int num) {
        if (num >= 0) {
            return (num & 1) == 0;
        } else {
            return isEvenNumber(-num);
        }
    }


}
