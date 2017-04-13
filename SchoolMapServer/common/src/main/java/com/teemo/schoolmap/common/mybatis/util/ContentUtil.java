package com.teemo.schoolmap.common.mybatis.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/13 11:53
 * @version 1.0
 * @name schoolmap-server
 * @description 文本内容工具类
 */
public class ContentUtil {

    /**
     * 驼峰法转下划线
     *
     * @param line 源字符串
     * @return 转换后的字符串
     */
    public static String camelToUnderline(String line) {
        if (line == null || "".equals(line)) {
            return "";
        }
        line = String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
        StringBuilder sb = new StringBuilder();
        Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(word.toUpperCase());
            sb.append(matcher.end() == line.length() ? "" : "_");
        }
        return sb.toString();
    }

}
