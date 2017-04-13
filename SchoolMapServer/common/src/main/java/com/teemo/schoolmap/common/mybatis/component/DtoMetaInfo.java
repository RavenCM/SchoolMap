package com.teemo.schoolmap.common.mybatis.component;

import com.teemo.schoolmap.common.component.Pair;
import com.teemo.schoolmap.common.mybatis.annotation.Condition;
import com.teemo.schoolmap.common.mybatis.enums.SqlKeyWord;
import com.teemo.schoolmap.common.mybatis.exception.MybatisException;
import org.springframework.util.ReflectionUtils;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/10 19:39
 * @version 1.0
 * @name schoolmap-server
 * @description DTO 类元数据
 */
public class DtoMetaInfo {

    /**
     * DTO 对应元数据信息缓存
     */
    private static Map<Class<?>, DtoMetaInfo> dtoMetaInfoCache = new HashMap<>();

    /**
     * 表名
     */
    private String tableName;
    /**
     * 列集合
     */
    private Map<String, Field> columns;
    /**
     * 主键
     */
    private Pair<String, Field> primaryKey;
    /**
     * 列以及判断条件
     */
    private Map<Pair<String, String>, Field> conditions;

    /**
     * 返回字段-值的键值对
     *
     * @param object object
     * @return 字段-值的键值对
     */
    public Map<String, Object> getColumnWithOutPrimaryKey(Object object) {
        Map<String, Object> columnDateList = new HashMap<>();
        columns.forEach((key, value) -> {
            ReflectionUtils.makeAccessible(value);
            value.setAccessible(true);
            columnDateList.put(key, ReflectionUtils.getField(value, object));
        });
        columnDateList.remove(primaryKey.getKey());
        return columnDateList;
    }

    /**
     * 获取 where 查询条件
     *
     * @param object object
     * @return where 查询条件
     */
    public Map<WhereCondition, Object> getWhereCondition(Object object) {
        Map<WhereCondition, Object> whereConditionObjectMap = new HashMap<>();
        Map<Pair<String, String>, Field> conditions = getConditions();
        if (conditions != null) {
            conditions.forEach((key, value) -> {
                if (value.isAnnotationPresent(Id.class)) return;
                ReflectionUtils.makeAccessible(value);
                WhereCondition whereCondition = new WhereCondition();
                whereCondition.setName(key.getKey());
                whereCondition.setOp(key.getValue());
                Object o;
                value.setAccessible(true);
                if ((o = ReflectionUtils.getField(value, object)) != null) {
                    whereConditionObjectMap.put(whereCondition, o);
                }
            });
        }
        return whereConditionObjectMap;
    }

    /**
     * 获取主键
     *
     * @param object object
     * @return 主键
     */
    public Map<WhereCondition, Object> getPrimaryKeyCondition(Object object, boolean isSelect) {
        if (primaryKey == null) throw new MybatisException("在更新的时候没有找到主键");
        Map<WhereCondition, Object> conditionMap = new HashMap<>();
        // 添加主键
        WhereCondition whereCondition = new WhereCondition();
        whereCondition.setName(primaryKey.getKey());
        whereCondition.setOp(Condition.EQ);
        Field primaryKeyField = primaryKey.getValue();
        primaryKeyField.setAccessible(true);
        conditionMap.put(whereCondition, ReflectionUtils.getField(primaryKeyField, object));

        // 添加 ObjectVersionNumber
        if (!isSelect && columns.containsKey(SqlKeyWord.OBJECT_VERSION_NUMBER.getContent())) {
            whereCondition = new WhereCondition();
            whereCondition.setName(SqlKeyWord.OBJECT_VERSION_NUMBER.getContent());
            whereCondition.setOp(Condition.EQ);
            Field objectVersionNumberField = columns.get(SqlKeyWord.OBJECT_VERSION_NUMBER.getContent());
            objectVersionNumberField.setAccessible(true);
            conditionMap.put(whereCondition, ReflectionUtils.getField(objectVersionNumberField, object));
        }
        return conditionMap;
    }

    public DtoMetaInfo() {
        this.columns = new HashMap<>();
        this.conditions = new HashMap<>();
    }

    public static Map<Class<?>, DtoMetaInfo> getDtoMetaInfoCache() {
        return dtoMetaInfoCache;
    }

    public static void setDtoMetaInfoCache(Map<Class<?>, DtoMetaInfo> dtoMetaInfoCache) {
        DtoMetaInfo.dtoMetaInfoCache = dtoMetaInfoCache;
    }


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Map<String, Field> getColumns() {
        return columns;
    }

    public void setColumns(Map<String, Field> columns) {
        this.columns = columns;
    }

    public Pair<String, Field> getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Pair<String, Field> primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Map<Pair<String, String>, Field> getConditions() {
        return conditions;
    }

    public void setConditions(Map<Pair<String, String>, Field> conditions) {
        this.conditions = conditions;
    }


}
