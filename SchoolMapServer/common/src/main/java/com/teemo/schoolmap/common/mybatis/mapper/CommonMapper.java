package com.teemo.schoolmap.common.mybatis.mapper;

import java.util.List;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/10 17:14
 * @version 1.0
 * @name schoolmap-server
 * @description 公共的 Mapper ，有基本的单表增删改查
 */
public interface CommonMapper<T> {
    /**
     * 根据dto的主键插入数据，dto必须要有Id注解，此方法会插入null值，插入成功后自增主键会被设置到传入的dto中
     *
     * @param dto 插入的数据
     * @return 受影响行数
     */
    int insert(T dto);

    /**
     * 与insert相同，区别在于不会插入null数据
     *
     * @param dto 插入的数据
     * @return 受影响行数
     */
    int insertSelective(T dto);

    /**
     * 根据主键查询数据
     *
     * @param dto 查询参数
     * @return 查询结果
     */
    T selectByPrimaryKey(T dto);

    /**
     * 根据condition属性查询数据
     *
     * @param dto 查询条件
     * @return 查询结果
     */
    List<T> select(T dto);

    /**
     * 根据主键更新数据，更新DTO里的所有数据
     *
     * @param dto 需要更新的数据
     * @return 受影响行数
     */
    int update(T dto);

    /**
     * 与update相同，不会更新null属性
     *
     * @param dto 需要更新的数据
     * @return 受影响行数
     */
    int updateSelective(T dto);

    /**
     * 根据主键删除数据
     *
     * @param dto 需要删除的数据
     * @return 受影响行数
     */
    int delete(T dto);
}
