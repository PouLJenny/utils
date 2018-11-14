package top.poul.utils.codec;

import top.poul.utils.IOUtil;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;

/**
 * java 支持的摘要算法
 * 消息摘要算法包含
 * MD(仅支持MD2,MD5)
 * SHA(SHA/SHA-1,SHA-224,SHA-256,SHA-384,SHA-512)
 * MAC(HmacMD2,HmacMD5,HmacSHA1,HmacSHA224,HmacSHA256,HmacSHA384,HmacSHA512)
 * CRC32
 * @author 杨霄鹏
 * @version 1.0, 2017-01-16
 * @since
 */
public class DigestAlgorithm {
    
    

    private static final int STREAM_BUFFER_LENGTH = 1 << 10; // alias 1024
    /**
     * 摘要算法加密字节数组返回定长的字节数组
     * @param b
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static byte[] digest(byte[] b,String mdName) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(mdName);
        return md.digest(b);
    }
    
    /**
     * 对字节数组进行摘要算法
     * @param b
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String digestHexStr(byte[] b,String mdName) throws NoSuchAlgorithmException {
        char[] encodeHex = IOUtil.encodeHex(digest(b,mdName));
        return new String(encodeHex);
    }
    
    /**
     * 对字符串进行摘要算法
     * @param str
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String digestHexStr(String str,String mdName) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        return digestHexStr(str.getBytes(CharacterEncode.UTF_8),mdName);
    }
    
    /**
     * 将一个输入流进行摘要加密
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public static String digestHexStr(InputStream data,String mdName) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance(mdName);
        try {
            byte[] buffer = new byte[STREAM_BUFFER_LENGTH];
            int read;
            while ((read = data.read(buffer, 0, STREAM_BUFFER_LENGTH)) > -1) {
                md.update(buffer, 0, read);
            } 
        } finally {
            data.close();
        }
        return new String(IOUtil.encodeHex(md.digest()));
    }
    
    /**
     * 将字节数据进行hmac摘要算法计算
     * @param data
     * @param hmacName 例如：HmacMD5
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     */
    public static byte[] hmacMd(byte[] data, String hmacName,String key) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        Mac mac = Mac.getInstance(hmacName);
        SecretKeySpec sks = new SecretKeySpec(key.getBytes(CharacterEncode.UTF_8),hmacName);
        mac.init(sks);
        return mac.doFinal(data);
    }
    
    /**
     * 将字符串进行hmac摘要算法计算
     * @param data
     * @param hmacName
     * @param key
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String hmacMDHexStr(String data, String hmacName,String key) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        return new String(IOUtil.encodeHex(hmacMd(data.getBytes(CharacterEncode.UTF_8), hmacName, key)));
    }
    
    /**
     * 对输入流进行hmac摘要计算
     * @param data
     * @param hmacName
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws IllegalStateException
     * @throws IOException
     */
    public static String hmacMDHexStr(InputStream data, String hmacName, String key) throws NoSuchAlgorithmException, InvalidKeyException, IllegalStateException, IOException {
        Mac mac = Mac.getInstance(hmacName);
        try {
            SecretKeySpec sks = new SecretKeySpec(key.getBytes(CharacterEncode.UTF_8), hmacName);
            mac.init(sks);
            byte[] buffer = new byte[STREAM_BUFFER_LENGTH];
            int read;
            while ((read = data.read(buffer, 0, STREAM_BUFFER_LENGTH)) > -1) {
                mac.update(buffer, 0, read);
            } 
        } finally {
            data.close();
        }
        return new String(IOUtil.encodeHex(mac.doFinal()));
    }
    
    /**
     * CRC32摘要算法
     * @param data
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String CRC32Hex(String data) throws UnsupportedEncodingException {
        CRC32 crc32 = new CRC32();
        crc32.update(data.getBytes(CharacterEncode.UTF_8));
        return Long.toHexString(crc32.getValue());
    }
    
    /**
     * CRC32摘要算法
     * @param data
     * @return
     * @throws IOException
     */
    private static String CRC32Hex(InputStream data) throws IOException {
        CRC32 crc32 = new CRC32();
        byte[] buffer= new byte[STREAM_BUFFER_LENGTH];
        int read;
        while ((read = data.read(buffer, 0, STREAM_BUFFER_LENGTH)) > -1) {
            crc32.update(buffer,0,read);
        }
        return Long.toHexString(crc32.getValue());
    }
    

}
