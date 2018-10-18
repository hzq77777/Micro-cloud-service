package com.micro.service.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;

/**
 * Created by Administrator on 2018/5/30.
 */
public class AesPassword {
  //  private static String key = ApplicationSupport.getParamVal("file.encry.key");
    private static String key = "hKrBL1SrZm7Pw==Q";


    // 解密
    public static String Decrypt(String sSrc) throws Exception {
        try {
            if (key == null) {
                System.out.print("Key为空null");
                return null;
            }
            if (key.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = key.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = new Base64().decode(sSrc);
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,"utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

public  static  void  main(String args[]){
    File file= new File("E:\\cetshi\\DeviceInfo.txt");
   InputStream inputStream=null;
    byte[] byt = null;
    try {
        inputStream=new FileInputStream(file);
        byt = new byte[inputStream.available()];
        inputStream.read(byt);
        String str = new String(byt);
       System.out.println( Decrypt(str));
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();

    }
}
    // 加密
    public static String Encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
         FileOutputStream out=null;
        out = new FileOutputStream("E:\\secret\\jceshi5.txt");
        String  end=new Base64().encodeToString(encrypted) ;
        out.write(new Base64().decode(end));
        out.flush();
        out.close();
        return end;//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }


}
