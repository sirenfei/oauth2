package com.faithbj.oauth.as;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RSACoderTest {
    private String publicKey;
    private String privateKey;

    /*
     * 非对称加密算法 RSA过程 ： 以甲乙双方为例 
     * 1、初始化密钥 构建密钥对,生成公钥、私钥保存到keymap中 KeyPairGenerator --> KeyPair --> RSAPublicKey、RSAPrivateKey 
     * 2、甲方使用私钥加密, 加密后在用私钥对加密数据进行数据签名，然后发送给乙方
     * RSACoder.encryptByPrivateKey(data, privateKey);
     * RSACoder.sign(encodedData, privateKey); 
     * 3、乙方则通过公钥验证签名的加密数据，如果验证正确则在通过公钥对加密数据进行解密 
     * RSACoder.verify(encodedData, publicKey, sign); RSACoder.decryptByPublicKey(encodedData, publicKey);
     *
     * 4、乙方在通过公钥加密发送给甲方 RSACoder.encryptByPublicKey(decodedData, publicKey);
     * 5、甲方通过私钥解密该数据
     * RSACoder.decryptPrivateKey(encodedData, privateKey);
     */
    @Before
    public void setUp() throws Exception {

        Map<String, Object> keyMap = RSACoder.initKey();

        publicKey = RSACoder.getPublicKey(keyMap);
        privateKey = RSACoder.getPrivateKey(keyMap);

        System.out.println("公钥：\n" + publicKey);
        System.out.println("私钥：\n" + privateKey);
    }

    @Test
    public void test() throws Exception {
        String inputStr = "abc";
        byte[] data = inputStr.getBytes();// 每次的得到的字节数组是不一样的。
        // 第二步 私钥加密
        byte[] encodedData = RSACoder.encryptByPrivateKey(data, privateKey);
        // 私钥进行数据签名
        String sign = RSACoder.sign(encodedData, privateKey);

        // 第三步 公钥验证数字签名
        boolean flag = RSACoder.verify(encodedData, publicKey, sign);
        System.out.println("flag:" + flag);
        // 用公钥对数据解密
        byte[] decodedData = RSACoder.decryptByPublicKey(encodedData, publicKey);

        System.out.println("data:" + data + "加密数据：" + encodedData + "    解密数据：" + decodedData);
        System.out.println("加密前数据-：" + new String(data) + "     解密后数据： " + new String(decodedData));

        // 第四步使用公钥加密数据
        encodedData = RSACoder.encryptByPublicKey(decodedData, publicKey);

        // 第五步 使用私钥解密数据
        decodedData = RSACoder.decryptPrivateKey(encodedData, privateKey);

        System.out.println("data:" + data + "加密数据：" + encodedData + "    解密数据：" + decodedData);
        System.out.println("加密前数据：" + inputStr + "     解密后数据： " + new String(decodedData));
    }

    @Test
    public void test1() throws Exception {
        System.out.println("私钥加密-----公钥解密");
        String inputStr = "abc";
        byte[] data = inputStr.getBytes();
        System.out.println("data:" + data);

        byte[] encodedData = RSACoder.encryptByPrivateKey(data, privateKey);
        byte[] decodedData = RSACoder.decryptByPublicKey(encodedData, publicKey);

        String outputStr = new String(decodedData);

        System.out.println("加密前：" + inputStr + "\n 解密后：" + outputStr);

        Assert.assertEquals(inputStr, outputStr);

        System.out.println("私钥签名---公钥验证签名");
        // 产生签名
        String sign = RSACoder.sign(encodedData, privateKey);
        System.out.println("签名：\r" + sign);

        // 验证签名
        boolean flag = RSACoder.verify(encodedData, publicKey, sign);

        System.out.println("状态：\r" + flag);

        Assert.assertTrue(flag);
    }
}
