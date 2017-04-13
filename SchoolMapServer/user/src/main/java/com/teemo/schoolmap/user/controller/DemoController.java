package com.teemo.schoolmap.user.controller;

import com.teemo.schoolmap.common.mybatis.mapper.CommonMapper;
import com.teemo.schoolmap.user.dto.DemoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/13 10:23
 * @version 1.0
 * @name schoolmap-server
 * @description
 */
@RestController
@RequestMapping("api/demo")
public class DemoController {

    @Autowired
    private CommonMapper<DemoDto> commonMapper;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Transactional
    @RequestMapping("test")
    public void test(){
        DemoDto insertDemo = new DemoDto();
        insertDemo.setValue("aaa");
        insertDemo.setMeaning("bbb");
        commonMapper.insertSelective(insertDemo);
        logger.info("insertDemo={}", insertDemo);

        DemoDto selectDemo = new DemoDto();
        selectDemo.setValue("aaa");
        selectDemo.setMeaning("bbb");
        selectDemo = commonMapper.select(selectDemo).get(0);
        logger.info("selectDemo={}", selectDemo);

        DemoDto updateDemo = selectDemo;
        updateDemo.setValue("ccc");
        updateDemo.setMeaning("ddd");
        commonMapper.update(updateDemo);
        logger.info("updateDemo={}", updateDemo);

        DemoDto selectByPKDemo = new DemoDto();
        selectByPKDemo.setId(updateDemo.getId());
        selectByPKDemo = commonMapper.selectByPrimaryKey(selectByPKDemo);
        logger.info("selectByPKDemo={}", selectByPKDemo);

        DemoDto deleteDemo = selectByPKDemo;
        commonMapper.delete(selectByPKDemo);
        logger.info("deleteDemo={}", deleteDemo);
    }
}
