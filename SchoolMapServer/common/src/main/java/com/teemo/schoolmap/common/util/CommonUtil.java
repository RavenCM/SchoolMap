package com.teemo.schoolmap.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/10 18:28
 * @version 1.0
 * @name schoolmap-server
 * @description
 */
public class CommonUtil {

    /**
     * Assert.NotNull 消息参数的构建
     *
     * @param object     判断对象
     * @param methodName 方法名称
     * @return message
     */
    public static String buildAssertNotNullMessage(Object object, String methodName) {
        return object.getClass().getName() + " - this argument is required; it must not be null in method " + methodName;
    }

    /**
     * 过滤 Map 中的 value 为 null 的键值对
     * @param map map
     * @param <K> key
     * @param <V> value
     * @return 不含 value 为空的 map
     */
    public static  <K, V> Map<K, V> filterMap(Map<K, V> map) {
        Map<K, V> filteredMap = new HashMap<>();
        map.forEach((key, value) -> {
            if (value != null) filteredMap.put(key, value);
        });
        return filteredMap;
    }
}
