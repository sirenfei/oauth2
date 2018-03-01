/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.dubai.oauth.resource.utils;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

/**
 * Created by qiudeqiang qiudeqiang@baidu.com on 17/5/15.
 */
public class RSACoder {
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivatekey";
    private static final Map<String, Object> keyMap = new HashMap<String, Object>(2);
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;
    /**
     * 用私钥对信息生成数字签名
     *
     * @param data 加密数据
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        // 解密由base64编码的私钥
        byte[] keyBytes = decryptBASE64(privateKey);

        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 取私钥对象
        PrivateKey pKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        // 用私钥生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(pKey);
        signature.update(data);

        return encryptBASE64(signature.sign());
    }

    /**
     * 校验数字签名
     *
     * @param data 加密数据
     * @param publicKey 公钥
     * @param sign 数字签名
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {

        // 解密有base64编码的公钥
        byte[] keyBytes = decryptBASE64(publicKey);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 取公钥对象
        PublicKey pKey = keyFactory.generatePublic(keySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pKey);
        signature.update(data);
        // 验证签名是否正常
        return signature.verify(decryptBASE64(sign));
    }

    //    /**
    //     * 解密 用私钥解密
    //     *
    //     * @param data 加密数据
    //     * @param key
    //     * @return
    //     * @throws Exception
    //     */
    //    public static byte[] decryptPrivateKey(byte[] data, String key) throws Exception {
    //        byte[] keyBytes = decryptBASE64(key);
    //
    //        // 取得私钥
    //        PKCS8EncodedKeySpec encodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
    //        KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
    //        Key pKey = factory.generatePrivate(encodedKeySpec);
    //
    //        // 对数据解密
    //        Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
    //        cipher.init(Cipher.DECRYPT_MODE, pKey);
    //
    //        return cipher.doFinal(data);
    //    }
    //
    //    /**
    //     * 用公钥解密
    //     *
    //     * @param data
    //     * @param key
    //     * @return
    //     * @throws Exception
    //     */
    //    public static byte[] decryptByPublicKey(byte[] data, String key) throws Exception {
    //
    //        // 解密
    //        byte[] keyBytes = decryptBASE64(key);
    //
    //        // 取得公钥
    //        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
    //        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
    //        Key pKey = keyFactory.generatePublic(keySpec);
    //
    //        // 对数据解密
    //        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
    //        cipher.init(Cipher.DECRYPT_MODE, pKey);
    //
    //        return cipher.doFinal(data);
    //    }
    //
    //    /**
    //     * 用公钥加密
    //     *
    //     * @param data
    //     * @param key
    //     * @return
    //     * @throws Exception
    //     */
    //    public static byte[] encryptByPublicKey(byte[] data, String key) throws Exception {
    //
    //        byte[] keyBytes = decryptBASE64(key);
    //
    //        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
    //        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
    //        Key pKey = keyFactory.generatePublic(keySpec);
    //
    //        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
    //        cipher.init(Cipher.ENCRYPT_MODE, pKey);
    //
    //        return cipher.doFinal(data);
    //    }
    //
    //    /**
    //     * 用私钥加密
    //     *
    //     * @param data
    //     * @param key
    //     * @return
    //     * @throws Exception
    //     */
    //    public static byte[] encryptByPrivateKey(byte[] data, String key) throws Exception {
    //
    //        byte[] keyBytes = decryptBASE64(key);
    //
    //        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
    //        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
    //        Key privateKey = keyFactory.generatePrivate(keySpec);
    //
    //        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
    //        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
    //
    //        return cipher.doFinal(data);
    //    }
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey)
            throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * <p>
     * 公钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey)
            throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param data 源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey)
            throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * <p>
     * 私钥加密
     * </p>
     *
     * @param data 源数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey)
            throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }
    /**
     * 取得私钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return encryptBASE64(key.getEncoded());
        // encode with padding
        // encode without padding
        //     String encoded = Base64.getEncoder().withoutPadding().encodeToString(someByteArray);
    }

    /**
     * 取得公钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 初始化密钥
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> initKey() throws Exception {
        if (!keyMap.isEmpty()) return keyMap;
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGenerator.initialize(1024);

        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();


        keyMap.put(PRIVATE_KEY, privateKey);
        keyMap.put(PUBLIC_KEY, publicKey);
        return keyMap;
    }

    /**
     * BASE64解密
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64( String key ) throws Exception{
        //        byte [] barr = org.apache.commons.codec.binary.Base64.getDecoder().decode(encoded);
        byte [] barr = Base64.getDecoder().decode(key);
        return barr;
        //        return (new BASE64Decoder()).decodeBuffer(key);
    }

    public static String encryptBASE64( byte[] key) throws Exception{
        String encoded = Base64.getEncoder().encodeToString(key);
        return encoded;
    }

}
