package top.poul.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常的工具类
 * @author 杨霄鹏  2017年5月18日 上午9:16:12
 *
 */
public class ExceptionUtil {


    /**
     * 构造器私有  无法实例化
     */
    private ExceptionUtil() {}




    /**
     * 获取异常的全部信息包括类名文件名代码行
     * @param e
     * @return
     */
    public static String getExceptionFullMessage(Exception e) {
        StringBuilder sb = new StringBuilder();
        sb.append(e.toString() + "\r\n");
        for(StackTraceElement s : e.getStackTrace()) {
            sb.append("\t" + s.toString() + "\r\n");
        }
        return sb.toString();
    }

    /**
     * 获取异常的全部信息包括类名文件名代码行
     * @param e
     * @return
     * @throws
     */
    public static String exceptionStackMessage(Exception e){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        String result = "";
        try{
            e.printStackTrace(pw);
            result = sw.toString();
        } finally {
            try {
                sw.close();
                pw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return result;
    }
}
