package com.teemo.schoolmap.common.mybatis.mapper.impl;

import com.teemo.schoolmap.common.mybatis.exception.MybatisException;
import com.teemo.schoolmap.common.mybatis.mapper.CommonMapper;
import com.teemo.schoolmap.common.mybatis.util.ReflectUtil;
import com.teemo.schoolmap.common.mybatis.util.SqlArgumentUtil;
import com.teemo.schoolmap.common.util.CommonUtil;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/10 17:17
 * @version 1.0
 * @name schoolmap-server
 * @description 公共的 Mapper ，有基本的单表增删改查
 */
public class CommonMapperImpl<T> extends SqlSessionDaoSupport implements CommonMapper<T> {

    private static final String INSERT = CommonMapper.class.getName() + ".insert";
    private static final String DELETE = CommonMapper.class.getName() + ".delete";
    private static final String UPDATE = CommonMapper.class.getName() + ".update";
    private static final String SELECT = CommonMapper.class.getName() + ".select";

    private static final String KEY_PROPERTY = "generatedKey";

    /**
     * 根据dto的主键插入数据，dto必须要有Id注解，此方法会插入null值，插入成功后自增主键会被设置到传入的dto中
     *
     * @param dto 插入的数据
     * @return 受影响行数
     */
    @Override
    public int insert(T dto) {
        Assert.notNull(dto, CommonUtil.buildAssertNotNullMessage(dto, "insert"));
        Map<String, Object> argumentMap = SqlArgumentUtil.prepareInsertArgument(dto, false);
        int cnt = getSqlSession().insert(INSERT, argumentMap);
        if (argumentMap.containsKey(KEY_PROPERTY)) {
            SqlArgumentUtil.setPrimaryKeyAfterInsert(dto, argumentMap.get(KEY_PROPERTY));
        }
        return cnt;
    }

    /**
     * 与insert相同，区别在于不会插入null数据
     *
     * @param dto 插入的数据
     * @return 受影响行数
     */
    @Override
    public int insertSelective(T dto) {
        Assert.notNull(dto, CommonUtil.buildAssertNotNullMessage(dto, "insertSelective"));
        Map<String, Object> argumentMap = SqlArgumentUtil.prepareInsertArgument(dto, true);
        int cnt = getSqlSession().insert(INSERT, argumentMap);
        if (argumentMap.containsKey(KEY_PROPERTY)) {
            SqlArgumentUtil.setPrimaryKeyAfterInsert(dto, argumentMap.get(KEY_PROPERTY));
        }
        return cnt;
    }

    /**
     * 根据主键查询数据
     *
     * @param dto 查询参数
     * @return 查询结果
     */
    @Override
    @SuppressWarnings("unchecked")
    public T selectByPrimaryKey(T dto) {
        Assert.notNull(dto, CommonUtil.buildAssertNotNullMessage(dto, "selectByPrimaryKey"));
        Map<String, Object> argumentMap = SqlArgumentUtil.prepareSelectArgument(dto, false);
        List<Map<String, Object>> result = getSqlSession().selectList(SELECT, argumentMap);
        if (result.isEmpty()) return null;
        if (result.size() > 1) throw new MybatisException("查询结果数量必须为1，查询结果数量：" + result.size());
        return (T) ReflectUtil.fillingDto(dto, result.get(0));
    }

    /**
     * 根据condition属性查询数据
     *
     * @param dto 查询条件
     * @return 查询结果
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<T> select(T dto) {
        Assert.notNull(dto, CommonUtil.buildAssertNotNullMessage(dto, "select"));
        Map<String, Object> argumentMap = SqlArgumentUtil.prepareSelectArgument(dto, true);
        List<Map<String, Object>> result = getSqlSession().selectList(SELECT, argumentMap);
        if (result.isEmpty()) return null;
        List<T> list = new ArrayList<>();
        result.forEach(item ->
                list.add((T) ReflectUtil.fillingDto(dto, item))
        );
        return list;
    }

    /**
     * 根据主键更新数据，更新DTO里的所有数据
     *
     * @param dto 需要更新的数据
     * @return 受影响行数
     */
    @Override
    public int update(T dto) {
        Assert.notNull(dto, CommonUtil.buildAssertNotNullMessage(dto, "update"));
        return getSqlSession().update(UPDATE, SqlArgumentUtil.prepareUpdateArgument(dto, false));
    }

    /**
     * 与update相同，不会更新null属性
     *
     * @param dto 需要更新的数据
     * @return 受影响行数
     */
    @Override
    public int updateSelective(T dto) {
        Assert.notNull(dto, CommonUtil.buildAssertNotNullMessage(dto, "updateSelective"));
        return getSqlSession().update(UPDATE, SqlArgumentUtil.prepareUpdateArgument(dto, true));
    }

    /**
     * 根据主键删除数据
     *
     * @param dto 需要删除的数据
     * @return 受影响行数
     */
    @Override
    public int delete(T dto) {
        Assert.notNull(dto, CommonUtil.buildAssertNotNullMessage(dto, "delete"));
        return getSqlSession().delete(DELETE, SqlArgumentUtil.prepareDeleteArgument(dto));
    }
}
