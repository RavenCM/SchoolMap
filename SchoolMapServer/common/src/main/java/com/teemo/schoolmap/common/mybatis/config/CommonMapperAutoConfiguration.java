package com.teemo.schoolmap.common.mybatis.config;

import com.teemo.schoolmap.common.mybatis.mapper.CommonMapper;
import com.teemo.schoolmap.common.mybatis.mapper.impl.CommonMapperImpl;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/11 17:19
 * @version 1.0
 * @name schoolmap-server
 * @description Common Mapper 的自动注入配置
 */
@Configuration
@AutoConfigureAfter(MybatisAutoConfiguration.class)
public class CommonMapperAutoConfiguration {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Bean
    public CommonMapperImpl commonMapper(){
        CommonMapperImpl commonMapper = new CommonMapperImpl();
        commonMapper.setSqlSessionTemplate(sqlSessionTemplate);
        return commonMapper;
    }
}
