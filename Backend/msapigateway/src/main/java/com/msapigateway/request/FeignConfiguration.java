package com.msapigateway.request;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.auth.BasicAuthRequestInterceptor;

@Configuration
public class FeignConfiguration {

	@Bean
	public BasicAuthRequestInterceptor basicAuthRequestInterceptor(
			@Value("${spring.security.user.name}") String username,
			@Value("${spring.security.user.password}") String password
			)
	{
	
		return new BasicAuthRequestInterceptor(username, password);
	}
	
	
}



