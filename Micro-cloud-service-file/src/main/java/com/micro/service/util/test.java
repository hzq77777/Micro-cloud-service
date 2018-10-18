package com.micro.service.util;

import com.google.common.base.Utf8;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.util.Arrays;

/**
 * Created by Administrator on 2018/5/30.
 */
public class test {


    public static void main(String ars[]) {
        String end = "luXPdOQIJHwL1AFZabFP+rG7EHkSmKqRGHd0BH8/Fv6cS/mjOALH85mz5fNS52zcUnpq6xGjA4z2IPH06skctOhNvMM8gSx2RoQkRJfiRFo=";
        String  key="12345678" ;

        String  encode="hKrBL1SrZm7Pw==";
        String  content="hKrBL1SrZm7Pw==" ;

   //     func(key);
//        AESEncode("aaaa", encode);
//        AESDncode(content,encode) ;

        AESEncode1("aaa",encode);
        AESDncode1(content, encode);
//        byte[] bytes = null;
//        bytes = new byte[inputStream.available()];
//        inputStream.read(bytes);
//        String str = new String(bytes);
    }


    public  static  void func(String encyKey ){

        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(encyKey.getBytes());
            keygen.init(128,secureRandom);
            SecretKey key = keygen.generateKey();
            byte[] enCodeFormat = key.getEncoded();
            BASE64Encoder coder = new BASE64Encoder();
            BASE64Decoder dec=new BASE64Decoder();
            String  aaaa=  coder.encode(enCodeFormat) ;
            System.out.println(aaaa);
            byte[] ttt= dec.decodeBuffer(aaaa);
            Boolean  ise=  Arrays.equals(enCodeFormat, ttt);
            System.out.println(ise);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   //加密
    public static String AESEncode1(String content,String encodeRules){
        try {

            BASE64Decoder dec=new BASE64Decoder();
            byte [] raw=dec.decodeBuffer(encodeRules);
            SecretKey key=new SecretKeySpec(raw, "AES");
            Cipher cipher=Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte [] byte_encode=content.getBytes("utf-8");
            byte [] byte_AES=cipher.doFinal(byte_encode);
            String AES_encode=new String(new BASE64Encoder().encode(byte_AES));
            System.out.println(AES_encode);
            return AES_encode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
         } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    //解密
    public static String AESDncode1(String content,String encodeRules){
        try {
            BASE64Decoder dec=new BASE64Decoder();
            byte [] raw=dec.decodeBuffer(encodeRules) ;
            SecretKey key=new SecretKeySpec(raw, "AES");
            Cipher cipher=Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte [] byte_content= new BASE64Decoder().decodeBuffer(content);
            byte [] byte_decode=cipher.doFinal(byte_content);
            String AES_decode=new String(byte_decode,"utf-8");
            System.out.println(AES_decode);
            return AES_decode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static void func1(String content, String encyKey) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(encyKey.getBytes());
            kgen.init(128, secureRandom);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
            byte[] byteContent = content.getBytes("utf-8");
            byte[] result = cipher.doFinal(byteContent);
            BASE64Encoder encode = new BASE64Encoder();
            String strResult = encode.encode(result);
            System.out.println(strResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //加密
    public static String AESEncode(String content,String encodeRules){
        try {

            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen=KeyGenerator.getInstance("AES");
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            keygen.init(128, new SecureRandom(encodeRules.getBytes()));
            //3.产生原始对称密钥
            SecretKey original_key=keygen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte [] raw=original_key.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKey key=new SecretKeySpec(raw, "AES");
            //6.根据指定算法AES自成密码器
            Cipher cipher=Cipher.getInstance("AES");
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte [] byte_encode=content.getBytes("utf-8");
            //9.根据密码器的初始化方式--加密：将数据加密
            byte [] byte_AES=cipher.doFinal(byte_encode);
            //10.将加密后的数据转换为字符串
            String AES_encode=new String(new BASE64Encoder().encode(byte_AES));
            //11.将字符串返回
            System.out.println(AES_encode);
            return AES_encode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //如果有错就返加nulll
        return null;
    }

    //解密
    public static String AESDncode(String content,String encodeRules){
        try {
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen=KeyGenerator.getInstance("AES");
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            keygen.init(128, new SecureRandom(encodeRules.getBytes()));
            //3.产生原始对称密钥
            SecretKey original_key=keygen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte [] raw=original_key.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKey key=new SecretKeySpec(raw, "AES");
            //6.根据指定算法AES自成密码器
            Cipher cipher=Cipher.getInstance("AES");
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            //8.将加密并编码后的内容解码成字节数组
            byte [] byte_content= new BASE64Decoder().decodeBuffer(content);
            /*
             * 解密
             */
            byte [] byte_decode=cipher.doFinal(byte_content);
            String AES_decode=new String(byte_decode,"utf-8");
            System.out.println(AES_decode);
            return AES_decode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        //如果有错就返加nulll
        return null;
    }

}



