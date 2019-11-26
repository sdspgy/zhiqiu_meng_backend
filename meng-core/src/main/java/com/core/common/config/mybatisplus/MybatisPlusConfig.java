package com.core.common.config.mybatisplus;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {

	/**
	 * 分页插件，自动识别数据库类型
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}

	/**
	 * 自动填充功能
	 *
	 * @return
	 */
	@Bean
	public GlobalConfig globalConfig() {
		GlobalConfig globalConfig = new GlobalConfig();
		globalConfig.setMetaObjectHandler(new MetaHandler());
		return globalConfig;
	}
}
