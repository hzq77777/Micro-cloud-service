package com.micro.service.util;

import com.micro.service.service.impl.FileServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

//import javax.crypto.Cipher;
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//import java.io.*;
//import java.security.GeneralSecurityException;
//import java.security.Key;
//import java.security.SecureRandom;
import java.io.*;
import java.security.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Administrator on 2018/2/5.
 */
public class Editpassword {

    private static String encry = ApplicationSupport.getParamVal("file.encry.algorithm");
    private static String keyfile = ApplicationSupport.getParamVal("file.encry.keyfile");
    private static String key = ApplicationSupport.getParamVal("file.encry.key");
    private static volatile Editpassword AESPassword = new Editpassword();
    public File file;

    public  static   void  main(String ars[]){
        Editpassword AESPassword = new Editpassword();
    }
    public  Editpassword() {

        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key.getBytes());
            keygen.init(secureRandom);
            SecretKey key = keygen.generateKey();

            String  aaa=new String(key.getEncoded());
            System.out.println(aaa);
//            file = new File(keyfile + File.separator + "secret.key");
//            if (!file.exists()) {
//                file.createNewFile();
//                try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
//                    out.writeObject(key);
//                    out.flush();
//                }
//            }
            System.out.println(key);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Editpassword GetPassword() {
        return AESPassword;
    }

    public String decryptpassword(InputStream in) {
        int mode = Cipher.DECRYPT_MODE;
        String message = null;
        try {
            file = new File(keyfile + File.separator + "secret.key");
            try (ObjectInputStream keyIn = new ObjectInputStream(new FileInputStream(file));
                 OutputStream out = new ByteArrayOutputStream()) {
                Key key = (Key) keyIn.readObject();
                Cipher cipher = Cipher.getInstance(encry);
                cipher.init(mode, key);
                //解密方法，入参数为：密文、明文及密钥
                crypt(in, out, cipher);
                message = out.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return message;
    }


    private void crypt(InputStream in, OutputStream out, Cipher cipher) throws IOException,
            GeneralSecurityException {
        int blockSize = cipher.getBlockSize();
        int outputSize = cipher.getOutputSize(blockSize);
        byte[] inBytes = new byte[blockSize];
        byte[] outBytes = new byte[outputSize];

        int inLength = 0;
        boolean more = true;
        while (more) {
            inLength = in.read(inBytes);
            if (inLength == blockSize) {
                int outLength = cipher.update(inBytes, 0, blockSize, outBytes);
                out.write(outBytes, 0, outLength);
            } else more = false;
        }
        //对于最后一段的读取，如果读取的数据小于一个块的大小，进入下面的if逻辑
        if (inLength > 0)
            outBytes = cipher.doFinal(inBytes, 0, inLength);
        else
            outBytes = cipher.doFinal();
        out.write(outBytes);
    }

//    private static byte[] encrypt(String content, String password)
//            throws Exception {
//
//        KeyGenerator kgen = KeyGenerator.getInstance("AES");
//        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
//        secureRandom.setSeed(password.getBytes());
//        kgen.init(length, secureRandom);
//        SecretKey secretKey = kgen.generateKey();
//        byte[] enCodeFormat = secretKey.getEncoded();
//        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
//        Cipher cipher = Cipher.getInstance("AES");// 创建密码器
//        byte[] byteContent = content.getBytes("utf-8");
//        cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
//        byte[] result = cipher.doFinal(byteContent);
//        return result; // 加密
//
//    }
}
