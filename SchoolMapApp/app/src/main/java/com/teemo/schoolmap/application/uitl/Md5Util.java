package com.teemo.schoolmap.application.uitl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017/4/20 13:50
 * @description Md5Util 加密
 */
public class Md5Util {
    /**
     * Md5Util 加密
     *
     * @param content content
     * @return 加密后的文本
     */
    public static String sign(String content) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(content.getBytes());
            int i;
            StringBuilder buf = new StringBuilder();
            byte[] b = md5.digest();// 加密
            for (byte aB : b) {
                i = aB;
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
