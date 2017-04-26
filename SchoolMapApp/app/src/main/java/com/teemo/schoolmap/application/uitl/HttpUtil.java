package com.teemo.schoolmap.application.uitl;

import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017/4/19 16:59
 * @description Http 请求相关的操作
 */
public class HttpUtil {
    /**
     * OkHttpClient
     */
    private static OkHttpClient okHttpClient = new OkHttpClient();

    /**
     * 返回 GET 请求的 CALL
     *
     * @param url         url
     * @param argumentMap argumentMap
     * @return CALL
     */
    public static Call getCall(String url, Map<String, Object> argumentMap) {
        StringBuilder parameterLink = null;
        for (String key : argumentMap.keySet()) {
            if (parameterLink == null) {
                parameterLink = new StringBuilder("?");
            } else {
                parameterLink.append("&");
            }
            Object value = argumentMap.get(key);
            if (value != null && !"".equals(value)) {
                if (value instanceof List) {
                    for (Object v : (List) value) {
                        parameterLink.append(key).append("=").append(v);
                    }
                } else {
                    parameterLink.append(key).append("=").append(argumentMap.get(key));
                }
            }
        }
        Request request = new Request.Builder().get().url(parameterLink != null ? url + parameterLink.toString() : url).build();
        return okHttpClient.newCall(request);
    }
}
