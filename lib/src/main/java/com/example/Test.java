package com.example;

import org.apache.commons.codec.binary.Hex;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * Created by xiepan on 2016/10/8.
 */

public class Test {
    public static void main(String args[]) {
//        for (int i = 0; i < 10; ++i) {
//            test2(i);
//        }
//        byte[] b = new byte[12] ('0x97', '0x98');
//        System.out.println(Arrays.toString(b));

        String string = "abcdef你";
        sha(string);
        des(string);

    }

    private static void sha(String string) {
        BigInteger sha;
        System.out.println("加密前的数据:" + string);
        byte[] inputData = string.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(inputData);
            sha = new BigInteger(messageDigest.digest());
            System.out.println("SHA加密后的数据:" + sha.toString(32));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void des(String string) {
        byte[] byteArray = new byte[]{-1, -128, 1, 127};
        byte a = 3;
        System.out.println((int) a);
        System.out.println("byte[] " + Arrays.toString(byteArray));
        System.out.println("按位与运算符 0&0=0;   0&1=0;    1&0=0;     1&1=1");
        System.out.println("按位或运算符 0|0=0；  0|1=1；   1|0=1；    1|1=1");
        System.out.println(
                "\n\n如果是一个byte(8位)类型的数字，他的高24位里面都是随机数字，低8位才是实际的数据。\n" +
                "java.lang.Integer.toHexString() 方法的参数是int(32位)类型，如果输入一个byte(8位)类型的数字，\n" +
                "这个方法会把这个数字的高24为也看作有效位，这就必然导致错误，使用& 0XFF操作，可以把高24位置0以避免这样错误的发生。\n\n");
        System.out.println("Integer.toHexString(-28)= " + Integer.toHexString(-28));
        System.out.println("Integer.toHexString(-28 & 0xFF)= " + Integer.toHexString(-28 & 0xFF));
        System.out.println("Integer.toHexString(97)= " + Integer.toHexString(97));
        System.out.println("Integer.toHexString(97 & 0xFF)= " + Integer.toHexString(97 & 0xFF));

        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance("DES");

            byte[] bytesKey = keyGenerator.generateKey().getEncoded();
            DESKeySpec desKeySpec = new DESKeySpec(bytesKey);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = factory.generateSecret(desKeySpec);
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

            //加密
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] originBytes = string.getBytes("UTF-8");
            byte[] resultBytes = cipher.doFinal(originBytes);
            System.out.println("\n" +
                    "编码   hex dec (bytes) dec binary\n" +
                    "UTF-8 61   97          97   01100001\n");
            System.out.println("加密前 bytes[]        :" + Arrays.toString(originBytes));
            System.out.println("加密前 byte2hex       :" + byte2hex(originBytes));
            System.out.println("加密前 encodeHexString:" + Hex.encodeHexString(originBytes));
            System.out.println("加密前 new String     :" + new String(originBytes, "UTF-8"));
            System.out.println("加密后 bytes[]        :" + Arrays.toString(resultBytes));
            System.out.println("加密后 byte2hex       :" + byte2hex(resultBytes));
            System.out.println("加密后 encodeHexString:" + Hex.encodeHexString(resultBytes));
            System.out.println("加密后 new String     :" + new String(resultBytes, "UTF-8"));

            //解密
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] resultResultBytes = cipher.doFinal(resultBytes);
            System.out.println("解密后 bytes[]        :" + Arrays.toString(resultResultBytes));
            System.out.println("解密后 byte2hex       :" + byte2hex(resultResultBytes));
            System.out.println("解密后 encodeHexString:" + Hex.encodeHexString(resultResultBytes));
            System.out.println("解密后 new String:" + new String(resultResultBytes));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String byte2hex(byte[] buffer) {
        String h = "";

        for (int i = 0; i < buffer.length; i++) {
            String temp = Integer.toHexString(buffer[i] & 0xFF);
            if (temp.length() == 1) {
                temp = "0" + temp;
            }
            h = h + " " + temp;
        }
        return h;
    }

    static List<String> arrayList = new ArrayList<>();

    private static void test1(final int i) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    Thread.sleep((long) (Math.random() * 2000));
                    arrayList.add(i, i + "position");
                    System.out.print(arrayList.get(arrayList.size() - 1) + "\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
//        try {
//            thread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    private static void test2(final int i) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (arrayList) {
                    while (arrayList.size() != i) {
                        try {
                            arrayList.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep((long) (Math.random() * 2000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    arrayList.add(i, i + "position");
                    arrayList.notifyAll();
                    System.out.print(arrayList.get(arrayList.size() - 1) + "\n");

                }
            }
        }).start();
    }
}
