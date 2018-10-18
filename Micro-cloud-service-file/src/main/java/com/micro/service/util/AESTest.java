package com.micro.service.util;

import java.io.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

/**
 * This program tests the AES cipher. Usage:<br>
 * java aes.AESTest -genkey keyfile<br>
 * java aes.AESTest -encrypt plaintext encrypted keyfile<br>
 * java aes.AESTest -decrypt encrypted decrypted keyfile<br>
 *
 * @author Cay Horstmann
 * @version 1.01 2012-06-10
 */
public class AESTest {
    public static void main(String[] args)
            throws IOException, GeneralSecurityException, ClassNotFoundException {
//        if (args[0].equals("-genkey")) {
//            KeyGenerator keygen = KeyGenerator.getInstance("AES");
//            SecureRandom random = new SecureRandom();
//            keygen.init(random);
//            SecretKey key = keygen.generateKey();
//            File keyfile = new File("aaa");
//            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(args[1]))) {
//                out.writeObject(key);
//            }
//        } else {
            int mode;
//            if (args[0].equals("-encrypt"))
//                mode = Cipher.ENCRYPT_MODE;
//            else mode = Cipher.DECRYPT_MODE;
            mode = Cipher.ENCRYPT_MODE;
//
//            try (ObjectInputStream keyIn = new ObjectInputStream(new FileInputStream("E:\\secret\\secret.key"));
//                 InputStream in = new FileInputStream("E:\\secret\\aaaa.txt");
//                 OutputStream out = new FileOutputStream("E:\\secret\\bbbb.txt")) {
//                Key key = (Key) keyIn.readObject();
//                Cipher cipher = Cipher.getInstance("AES");
//                cipher.init(mode, key);
//                Util.crypt(in, out, cipher);
//            }




        try (ObjectInputStream keyIn = new ObjectInputStream(new FileInputStream("E:\\secret\\secret.key"));
             InputStream in = new FileInputStream("E:\\secret\\jceshi.txt");
            OutputStream out = new FileOutputStream("E:\\secret\\jceshi5.txt"))
        {
             Key key = (Key) keyIn.readObject();
             Cipher cipher = Cipher.getInstance("AES");
             cipher.init(mode, key);
            crypt(in, out, cipher);
        }
       // }
    }

    private static  void crypt(InputStream in, OutputStream out, Cipher cipher) throws IOException,
            GeneralSecurityException {
        int blockSize = cipher.getBlockSize();
        int outputSize = cipher.getOutputSize(blockSize);
        byte[] inBytes = new byte[blockSize];
        byte[] outBytes = new byte[outputSize];

        int inLength = 0;
        ;
        boolean more = true;
        while (more) {
            inLength = in.read(inBytes);
            if (inLength == blockSize) {
                int outLength = cipher.update(inBytes, 0, blockSize, outBytes);
                out.write(outBytes, 0, outLength);
            } else more = false;
        }
        if (inLength > 0) outBytes = cipher.doFinal(inBytes, 0, inLength);
        else outBytes = cipher.doFinal();
        out.write(outBytes);
    }


    private Key initKeyForAES(String key) throws NoSuchAlgorithmException {
        if (null == key || key.length() == 0) {
            throw new NullPointerException("key not is null");
        }
        SecretKeySpec key2 = null;
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(key.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            key2 = new SecretKeySpec(enCodeFormat, "AES");
        } catch (NoSuchAlgorithmException ex) {
            throw new NoSuchAlgorithmException();
        }
        return key2;
    }

    private Key initKeyForAES1(String key) throws NoSuchAlgorithmException {
        if (null == key || key.length() == 0) {
            throw new NullPointerException("key not is null");
        }
        SecretKeySpec key2 = null;
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(key.getBytes());
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            key2 = new SecretKeySpec(enCodeFormat, "AES");
        } catch (NoSuchAlgorithmException ex) {
            throw new NoSuchAlgorithmException();
        }
        return key2;

    }
}

