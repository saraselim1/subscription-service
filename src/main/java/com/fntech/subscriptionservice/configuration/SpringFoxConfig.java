package com.fntech.subscriptionservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {
	@Bean
	public Docket subscriptionServiceAPI() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("SubscriptionService").select()
				.apis(RequestHandlerSelectors.basePackage("com.fntech.subscriptionservice.controllers"))
				.apis(RequestHandlerSelectors.any()).build();
	}

}
