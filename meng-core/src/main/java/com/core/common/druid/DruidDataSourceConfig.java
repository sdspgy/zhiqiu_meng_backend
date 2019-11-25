package com.core.common.druid;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhiqiu
 * @since 2019-10-26
 */
//@Configuration
@MapperScan(basePackages = "com.core.mapper.**", sqlSessionFactoryRef = "sqlSessionFactory")
public class DruidDataSourceConfig {

	/*配置mapper的扫描，找到所有的mapper.xml映射文件*/
	@Value("${mybatis-plus.mapper-locations}")
	private String mapperLocations;

	/*数据源1*/
	@Bean(name = "manageDruidSource")
	@ConfigurationProperties(prefix = "spring.datasource.manage")
	public DataSource manageDruidSource() {
		return DruidDataSourceBuilder.create().build();
	}

	/*数据源2*/
	@Bean(name = "logDruidSource")
	@ConfigurationProperties(prefix = "spring.datasource.log")
	public DataSource logDruidSource() {
		return DruidDataSourceBuilder.create().build();
	}

	/*数据源管理*/
	@Bean
	public DataSource dynamicDataSource() throws SQLException {
		DynamicDataSource dynmicDataSource = new DynamicDataSource();
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put("manageDruidSource", manageDruidSource());
		targetDataSources.put("logDruidSource", logDruidSource());
		dynmicDataSource.setTargetDataSources(targetDataSources);
		/*设置默认数据源*/
		dynmicDataSource.setDefaultTargetDataSource(manageDruidSource());
		return dynmicDataSource;
	}

	/*SqlSessionFactory*/
	@Bean
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dataSource) throws Exception {
		/*兼容Mybatis-plus的接口*/
		MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
		return bean.getObject();
	}

	/*事物*/
	@Bean
	public PlatformTransactionManager transactionManager(@Qualifier("dynamicDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

}