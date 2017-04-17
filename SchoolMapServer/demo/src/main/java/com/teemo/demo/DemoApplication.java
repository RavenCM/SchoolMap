package com.teemo.demo;

import com.teemo.schoolmap.common.annotation.EnableRequestFilter;
import com.teemo.schoolmap.common.mybatis.annotation.EnableCommonMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCommonMapper
@EnableRequestFilter
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
