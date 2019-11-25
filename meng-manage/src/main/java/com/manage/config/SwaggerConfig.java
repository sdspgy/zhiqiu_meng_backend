package com.manage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author zhiqiu
 * @since 2019-11-06
 */
@Configuration
public class SwaggerConfig {

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
						.apiInfo(apiInfo())
						.select()
						.apis(RequestHandlerSelectors.basePackage("com.manage"))
						.paths(PathSelectors.any())
						.build();
	}

	private ApiInfo apiInfo() {
		Contact contact = new Contact("zhiqiu", "https://mvptyz.cn", "573691723@qq.com");
		return new ApiInfoBuilder()
						.title("ApiBackend")
						.description("restful风格测试")
						.termsOfServiceUrl("")
						.contact(contact)
						.version("1.0.0")
						.build();
	}

}
