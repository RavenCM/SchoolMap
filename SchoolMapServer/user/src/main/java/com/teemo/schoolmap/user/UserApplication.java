package com.teemo.schoolmap.user;

import com.teemo.schoolmap.common.annotation.EnableRequestFilter;
import com.teemo.schoolmap.common.mybatis.config.CommonMapperAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ImportAutoConfiguration(CommonMapperAutoConfiguration.class)
@EnableRequestFilter
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}
}
