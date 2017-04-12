package com.teemo.schoolmap.common.mybatis.util;

import com.teemo.schoolmap.common.component.Pair;
import com.teemo.schoolmap.common.mybatis.exception.MybatisException;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/11 14:54
 * @version 1.0
 * @name schoolmap-server
 * @description jdbc 数据类型和 java 数据类型之间相互转换
 */
class TypeTranslator {
    private final static Map<Pair<Class, Class>, Method> JDBC_TO_JAVA = new HashMap<>();
//    private final static Map<Pair<Class, Class>, Method> JAVA_TO_JDBC = new HashMap<>();

    static {
        // BigInteger
        JDBC_TO_JAVA.put(new Pair<>(BigInteger.class, Long.class), ReflectionUtils.findMethod(BigInteger.class, "longValue"));
        JDBC_TO_JAVA.put(new Pair<>(BigInteger.class, long.class), ReflectionUtils.findMethod(BigInteger.class, "longValue"));
        JDBC_TO_JAVA.put(new Pair<>(BigInteger.class, Integer.class), ReflectionUtils.findMethod(BigInteger.class, "intValue"));
        JDBC_TO_JAVA.put(new Pair<>(BigInteger.class, int.class), ReflectionUtils.findMethod(BigInteger.class, "intValue"));
        JDBC_TO_JAVA.put(new Pair<>(BigInteger.class, Float.class), ReflectionUtils.findMethod(BigInteger.class, "floatValue"));
        JDBC_TO_JAVA.put(new Pair<>(BigInteger.class, float.class), ReflectionUtils.findMethod(BigInteger.class, "floatValue"));
        JDBC_TO_JAVA.put(new Pair<>(BigInteger.class, Double.class), ReflectionUtils.findMethod(BigInteger.class, "doubleValue"));
        JDBC_TO_JAVA.put(new Pair<>(BigInteger.class, double.class), ReflectionUtils.findMethod(BigInteger.class, "doubleValue"));
        JDBC_TO_JAVA.put(new Pair<>(BigInteger.class, String.class), ReflectionUtils.findMethod(BigInteger.class, "stringValue"));
        // BigDecimal
        JDBC_TO_JAVA.put(new Pair<>(BigDecimal.class, Long.class), ReflectionUtils.findMethod(BigDecimal.class, "longValue"));
        JDBC_TO_JAVA.put(new Pair<>(BigDecimal.class, long.class), ReflectionUtils.findMethod(BigDecimal.class, "longValue"));
        JDBC_TO_JAVA.put(new Pair<>(BigDecimal.class, Integer.class), ReflectionUtils.findMethod(BigDecimal.class, "intValue"));
        JDBC_TO_JAVA.put(new Pair<>(BigDecimal.class, int.class), ReflectionUtils.findMethod(BigDecimal.class, "intValue"));
        JDBC_TO_JAVA.put(new Pair<>(BigDecimal.class, Float.class), ReflectionUtils.findMethod(BigDecimal.class, "floatValue"));
        JDBC_TO_JAVA.put(new Pair<>(BigDecimal.class, float.class), ReflectionUtils.findMethod(BigDecimal.class, "floatValue"));
        JDBC_TO_JAVA.put(new Pair<>(BigDecimal.class, Double.class), ReflectionUtils.findMethod(BigDecimal.class, "doubleValue"));
        JDBC_TO_JAVA.put(new Pair<>(BigDecimal.class, double.class), ReflectionUtils.findMethod(BigDecimal.class, "doubleValue"));
        JDBC_TO_JAVA.put(new Pair<>(BigDecimal.class, String.class), ReflectionUtils.findMethod(BigDecimal.class, "stringValue"));
    }

    static Object transformJdbcToJava(Class sourceType, Class targetType, Object value) {
        Method transformMethod = JDBC_TO_JAVA.get(new Pair<>(sourceType, targetType));
        if (transformMethod != null) {
            return ReflectionUtils.invokeMethod(transformMethod, value);
        }
//        throw new MybatisException("不能将JDBC类型" + sourceType + "类型转化为Java类型" + targetType + "  [value=" + value + "]");
        return value;
    }
}
