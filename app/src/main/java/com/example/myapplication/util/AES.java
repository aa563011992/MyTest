package com.example.myapplication.util;

import android.text.TextUtils;


import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class AES {

    public static boolean initialized = false;

    public static final String ALGORITHM = "AES/ECB/PKCS7Padding";
    public static byte[] KEY;

    /**
     * @param String str  要被加密的字符串
     * @param byte[] key  加/解密要用的长度为32的字节数组（256位）密钥
     * @return byte[]  加密后的字节数组
     */
    public static byte[] Aes256Encode(byte[] bytes, byte[] key) {
        initialize();
        byte[] result = bytes;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES"); //生成加密解密需要的Key
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            result = cipher.doFinal(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param byte[] bytes  要被解密的字节数组
     * @param byte[] key    加/解密要用的长度为32的字节数组（256位）密钥
     * @return String  解密后的字符串
     */
    public static byte[] Aes256Decode(byte[] bytes, byte[] key) {
        initialize();
        byte result[] = bytes;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES"); //生成加密解密需要的Key
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            result = cipher.doFinal(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void initialize() {
        if (initialized) return;
        Security.addProvider(new BouncyCastleProvider());
        initialized = true;
    }


}