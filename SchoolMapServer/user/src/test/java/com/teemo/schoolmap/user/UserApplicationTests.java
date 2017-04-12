package com.teemo.schoolmap.user;

import com.teemo.schoolmap.common.mybatis.mapper.CommonMapper;
import com.teemo.schoolmap.user.dto.DemoDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserApplicationTests {

    @Autowired
    private CommonMapper<DemoDto> commonMapper;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	public void contextLoads() {
        DemoDto insertDemo = new DemoDto();
        insertDemo.setValue("9999");
        insertDemo.setMeaning("01010");
        commonMapper.insertSelective(insertDemo);
        logger.info("insertDemo={}", insertDemo);

        DemoDto selectDemo = new DemoDto();
        selectDemo.setValue("9999");
        selectDemo.setMeaning("01010");
        selectDemo = commonMapper.select(selectDemo).get(0);
        logger.info("selectDemo={}", selectDemo);

        DemoDto updateDemo = selectDemo;
        updateDemo.setValue("8888");
        updateDemo.setMeaning("101010");
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
