package com.search;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author zhiqiu
 * @since 2019-11-25
 */

@ComponentScan(basePackages = { "com.manage", "com.auth", "com.core" })
@MapperScan(basePackages = { "com.core.mapper"})
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@EnableSwagger2
public class MengApplication {

	public static void main(String[] args) {
		SpringApplication.run(MengApplication.class, args);
	}
}
