package com.teemo.schoolmap.common.mybatis.util;

import com.teemo.schoolmap.common.mybatis.component.DtoMetaInfo;
import com.teemo.schoolmap.common.mybatis.component.WhereCondition;
import com.teemo.schoolmap.common.mybatis.enums.SqlKeyWord;
import com.teemo.schoolmap.common.util.CommonUtil;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/11 11:14
 * @version 1.0
 * @name schoolmap-server
 * @description
 */
public class SqlArgumentUtil {

    /**
     * 准备插入的参数
     *
     * @param object      object
     * @param isSelective isSelective
     * @return 插入的参数
     */
    public static Map<String, Object> prepareInsertArgument(Object object, boolean isSelective) {
        DtoMetaInfo metaInfo = DtoMetaInfoBuilder.build(object);
        Map<String, Object> insertArgumentMap = new HashMap<>();
        insertArgumentMap.put(SqlKeyWord.TABLE.getContent(), metaInfo.getTableName());
        List<String> columnNameList = new ArrayList<>();
        List<Object> valueList = new ArrayList<>();
        metaInfo.getColumnWithOutPrimaryKey(object).forEach((key, value) -> {
            if (isSelective && value == null) return;
            columnNameList.add(key);
            valueList.add(value);
        });
        insertArgumentMap.put(SqlKeyWord.COLUMNS.getContent(), String.join(",", columnNameList));
        insertArgumentMap.put(SqlKeyWord.VALUES.getContent(), valueList);
        return insertArgumentMap;
    }

    /**
     * 更新 dto 的主键
     *
     * @param dto   dto
     * @param value value
     */
    public static void setPrimaryKeyAfterInsert(Object dto, Object value) {
        DtoMetaInfo metaInfo = DtoMetaInfoBuilder.build(dto);
        Field id = metaInfo.getPrimaryKey().getValue();
        ReflectionUtils.makeAccessible(id);
        ReflectionUtils.setField(id, dto, value == null ? null : TypeTranslator.transformJdbcToJava(value.getClass(), id.getType(), value));
    }

    /**
     * 准备查询的参数
     *
     * @param object object
     * @return 查询的参数
     */
    public static Map<String, Object> prepareSelectArgument(Object object, boolean isCondition) {
        DtoMetaInfo metaInfo = DtoMetaInfoBuilder.build(object);
        Map<String, Object> selectArgumentMap = new HashMap<>();
        selectArgumentMap.put(SqlKeyWord.TABLE.getContent(), metaInfo.getTableName());
        selectArgumentMap.put(SqlKeyWord.COLUMNS.getContent(), String.join(",", metaInfo.getColumns().keySet()));
        selectArgumentMap.put(SqlKeyWord.WHERE.getContent(), isCondition ? metaInfo.getWhereCondition(object) : metaInfo.getPrimaryKeyCondition(object));
        return selectArgumentMap;
    }

    /**
     * 准备更新的参数
     *
     * @param object      object
     * @param isSelective isSelective
     * @return 更新的参数
     */
    public static Map<String, Object> prepareUpdateArgument(Object object, boolean isSelective) {
        DtoMetaInfo metaInfo = DtoMetaInfoBuilder.build(object);
        Map<String, Object> updateArgumentMap = new HashMap<>();
        updateArgumentMap.put(SqlKeyWord.TABLE.getContent(), metaInfo.getTableName());
        Map<String, Object> columns = isSelective ? CommonUtil.filterMap(metaInfo.getColumnWithOutPrimaryKey(object)) : metaInfo.getColumnWithOutPrimaryKey(object);
        columns.remove(SqlKeyWord.OBJECT_VERSION_NUMBER.getContent());
        updateArgumentMap.put(SqlKeyWord.UPDATE.getContent(), columns);
        updateArgumentMap.put(SqlKeyWord.WHERE.getContent(), metaInfo.getPrimaryKeyCondition(object));
        return updateArgumentMap;
    }

    /**
     * 准备删除的参数
     *
     * @param object object
     * @return 删除的参数
     */
    public static Map<String, Object> prepareDeleteArgument(Object object) {
        DtoMetaInfo metaInfo = DtoMetaInfoBuilder.build(object);
        Map<String, Object> deleteArgumentMap = new HashMap<>();
        deleteArgumentMap.put(SqlKeyWord.TABLE.getContent(), metaInfo.getTableName());
        deleteArgumentMap.put(SqlKeyWord.WHERE.getContent(), metaInfo.getPrimaryKeyCondition(object));
        return deleteArgumentMap;
    }
}
