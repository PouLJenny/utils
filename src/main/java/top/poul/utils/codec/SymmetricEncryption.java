package top.poul.utils.codec;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * 对称加密算法
 * @author 杨霄鹏
 * @version 1.0, 2017-01-16
 * @since
 */
public class SymmetricEncryption {
    
    // 加/解密算法  / 工作模式 / 填充方式
    public static final String DES1 = "DES/CBC/NoPadding"; // 56
    public static final String DES2 = "DES/CBC/PKCS5Padding"; // 56
    // 此种模式需要加密的数据字节数组长度必须是8的倍数
    public static final String DES3 = "DES/ECB/NoPadding"; // 56
    public static final String DES4 = "DES/ECB/PKCS5Padding"; // 56
    
    public static final String AES1 = "AES/CBC/PKCS5Padding"; // 128
    /**
     * 对字符串进行对称加密
     * @param data
     * @param encrypName
     * @param key
     * @return 对称加密的字节数组的base64加密的字符串
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     * @throws InvalidKeySpecException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static String encode(String data, String encrypName, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = initCipher(Cipher.ENCRYPT_MODE, encrypName, key);
        byte[] doFinal = cipher.doFinal(data.getBytes(CharacterEncode.UTF_8));
        return Base64.getEncoder().encodeToString(doFinal);
    }
    
    /**
     * 对字符串进行解密
     * @param data
     * @param encrypName
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     * @throws InvalidKeySpecException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static String decode(String data, String encrypName, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = initCipher(Cipher.DECRYPT_MODE, encrypName, key);
        byte[] doFinal = cipher.doFinal(Base64.getDecoder().decode(data));
        return new String(doFinal,CharacterEncode.UTF_8);
    }
    
    /**
     * 初始化Cipher
     * @param cryptMode
     * @param encrypName
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     * @throws InvalidKeySpecException
     */
    private static Cipher initCipher(int cryptMode, String encrypName, String seed) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, InvalidKeySpecException {
        String[] split = encrypName.split("/");
        // 生成key
        // DES算法要求有一个可信任的随机数源
        SecureRandom random = new SecureRandom(seed.getBytes(CharacterEncode.UTF_8));
        KeyGenerator kg = KeyGenerator.getInstance(split[0]);
        kg.init(random);
        SecretKey generateKey = kg.generateKey();
        byte[] encoded = generateKey.getEncoded();
        
        
        DESKeySpec dks = new DESKeySpec(encoded);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(split[0]);
        SecretKey sk = skf.generateSecret(dks);
        
        Cipher cipher = Cipher.getInstance(encrypName);
        cipher.init(cryptMode, sk);
        return cipher;
    }
    
    
    public static void main(String[] args) {
        
        try {
                   
            String data = "Hello234";
            String key = "12345678";
//            byte[] bytes = key.getBytes("UTF-8");
//            System.out.println(bytes);
            test1(data,key,AES1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    private static void test1 (String data, String key, String enryptName) {
        try {
            String encode = encode(data,enryptName,key);
            System.out.println(encode);
            System.out.println(decode(encode,enryptName,key));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
