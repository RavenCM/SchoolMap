package com.teemo.schoolmap.common.mybatis.util;

import com.teemo.schoolmap.common.component.Pair;
import com.teemo.schoolmap.common.mybatis.annotation.Condition;
import com.teemo.schoolmap.common.mybatis.component.DtoMetaInfo;
import com.teemo.schoolmap.common.mybatis.exception.MybatisException;
import com.teemo.schoolmap.common.util.CommonUtil;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/10 19:56
 * @version 1.0
 * @name schoolmap-server
 * @description
 */
public class ReflectUtil {

    /**
     * 获取 @Table 注解中的 name 属性
     *
     * @param clazz 获取的注解的类
     * @return TableName
     */
    private static String getTableName(Class clazz) {
        if (!clazz.isAnnotationPresent(Table.class)) {
            throw new MybatisException(clazz.getName() + " 类上没有@Table注解");
        }
        return ((Table) clazz.getAnnotation(Table.class)).name();
    }

    /**
     * 根据 object 填充 dtoMetaInfo
     *
     * @param object      object
     * @param dtoMetaInfo dtoMetaInfo
     * @return dtoMetaInfo
     */
    static DtoMetaInfo getMetaInfo(Object object, DtoMetaInfo dtoMetaInfo) {
        dtoMetaInfo.setTableName(ReflectUtil.getTableName(object.getClass()));
        Map<String, Field> columns = new HashMap<>();
        List<Field> fieldList = new ArrayList<>();
        for (Class clazz = object.getClass(); !Object.class.equals(clazz); clazz = clazz.getSuperclass()) {
            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
        }
        fieldList.forEach(field -> {
            Column column = AnnotationUtils.getAnnotation(field, Column.class);
            if (column != null) {
                String columnName = StringUtils.isEmpty(column.name()) ? field.getName() : column.name();
                columns.put(ContentUtil.camelToUnderline(columnName), field);
                if (field.isAnnotationPresent(Id.class)) {
                    if (dtoMetaInfo.getPrimaryKey() != null) {
                        throw new MybatisException(object.getClass().getName() + " 主键必须唯一");
                    }
                    dtoMetaInfo.setPrimaryKey(new Pair<>(columnName, field));
                }
                if (field.isAnnotationPresent(Condition.class)) {
                    dtoMetaInfo.getConditions().put(new Pair<>(columnName, AnnotationUtils.getAnnotation(field, Condition.class).operator()), field);
                }
            }
        });
        dtoMetaInfo.setColumns(columns);
        return dtoMetaInfo;
    }

    public static Object fillingDto(Object dto, Map<String, Object> properties) {
        Assert.notNull(dto, CommonUtil.buildAssertNotNullMessage(dto, "select"));
        Assert.notNull(dto, CommonUtil.buildAssertNotNullMessage(properties, "select"));
        DtoMetaInfo metaInfo = DtoMetaInfoBuilder.build(dto);
        metaInfo.getColumns().forEach((key, value) -> {
            Object fieldValue = properties.containsKey(key) ? TypeTranslator.transformJdbcToJava(properties.get(key).getClass(), value.getType(), properties.get(key)) : null;
            ReflectionUtils.makeAccessible(value);
            ReflectionUtils.setField(value, dto, fieldValue);
        });
        return dto;
    }
}
