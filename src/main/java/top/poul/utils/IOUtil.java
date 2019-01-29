package top.poul.utils;

import java.io.*;
import java.util.Arrays;

/**
 * IO流的工具类
 *
 * @author 杨霄鹏
 * @since 2017年6月4日 上午10:20:28
 */
public class IOUtil {

    private IOUtil() {

    }

    /**
     * 从输入流里面获取字符串信息
     *
     * @param is
     * @return
     * @throws IOException
     * @since 2017年6月4日 上午10:21:26
     */
    public static String getString(InputStream is) throws IOException {
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer();
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(is, "UTF-8");
            br = new BufferedReader(isr);
            String buffer;
            boolean remove = false;
            while ((buffer = br.readLine()) != null) {
                if (!remove) {
                    remove = true;
                } else {
                    sb.append("\n");
                }
                sb.append(buffer);
            }
        } finally {
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (is != null) {
                is.close();
            }
        }
        return sb.toString();
    }


    /**
     * 获取输入流的所有字节数组
     * 由于java的数组限制，最大只能存储2G - 1byte 大小的流
     * @param input
     * @return
     * @throws IOException
     */
    public static byte[] bytes(InputStream input) throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = input.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            return baos.toByteArray();
        } finally {
            if (input != null) {
                input.close();
            }
            if (baos != null) {
                baos.close();
            }
        }
    }

    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 将字节数组转换成16进制的小写字符数组
     *
     * @param data
     * @return
     */
    public static char[] encodeHex(byte[] data) {
        final int l = data.length;
        final char[] out = new char[l << 1];
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS_LOWER[0x0F & data[i]];
        }
        return out;
    }

    /**
     * 读取前面的字节
     */
    public static byte[] cutBegin(InputStream is, int length) throws IOException {
        try {
            byte[] bytes = new byte[length];
            is.read(bytes);
            return bytes;
        } finally {
            if (is != null)
                is.close();
        }
    }

    /**
     * 将字节数组转换成字符串
     * @param bytes
     * @return
     */
    public static String byteArrayString(byte[] bytes) {
        return Arrays.toString(bytes).replaceAll("[\\[|\\]|,]", "");
    }

    public static String byteArrayString(File file) throws IOException {
        byte[] bytes = bytes(new FileInputStream(file));
        return Arrays.toString(bytes).replaceAll("[\\[|\\]|,]", "");
    }

    public static InputStream getMarkSupport(InputStream stream) {
        if (stream.markSupported()) {
            return stream;
        }
        // we used to process the data via a PushbackInputStream, but user code could provide a too small one
        // so we use a BufferedInputStream instead now
        return new BufferedInputStream(stream);
    }

    public static void main(String[] args) throws IOException {
//        String path = "D:\\developer workspace\\nginx\\nginx-1.13.2\\data\\images\\1.jpg";
//        String path = "D:\\dev_workspace\\nginx\\nginx-1.13.2\\data\\images\\timg.jpg";
//        File file = new File(path);

//        byte[] bytes = cutBegin(new FileInputStream(file), 3);
//        char[] chars = encodeHex(bytes);
//        System.out.println(new String(chars).toUpperCase());
//        String s = byteArrayString(file);
//        System.out.println(s);


        String str = "吉";

        byte[] gbks = str.getBytes("GBK");
        String s = byteArrayString(gbks);
        char[] chars = encodeHex(gbks);
        System.out.println(s);
        System.out.println(new String(chars).toUpperCase());

    }

}
