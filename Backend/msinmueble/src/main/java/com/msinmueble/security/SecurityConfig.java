package com.msinmueble.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
	
	//ACCEDER A LA VARIABLE DEL application.properties
	@Value("${spring.security.user.name}") 
	private String secureKeyUsername;
	
	//ACCEDER A LA VARIABLE DEL application.properties
	@Value("${spring.security.user.password}") 
	private String secureKeyPassword;
	
	
	//ACCEDER A LA VARIABLE DEL application.properties
	//@Value("${service.security.secure-key-username-2}") 
	//private String secureKeyUsername_2;
		
	//ACCEDER A LA VARIABLE DEL application.properties
	//@Value("${service.security.secure-key-password-2}") 
	//private String secureKeyPassword_2;
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		AuthenticationManagerBuilder authenticationManagerBuilder = 
				http.getSharedObject(AuthenticationManagerBuilder.class);
		
		//CARGA EN MEMORIA DOS USUARIOS CON SUS ROLES
		authenticationManagerBuilder.inMemoryAuthentication()
			.withUser(secureKeyUsername)
			.password(new BCryptPasswordEncoder().encode(secureKeyPassword))
			.authorities(AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"))
			//.and()
			//.withUser(secureKeyUsername_2)
			//.password(new BCryptPasswordEncoder().encode(secureKeyPassword_2))
			//.authorities(AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_DEV"))
			.and()
			.passwordEncoder(new BCryptPasswordEncoder());
		
		//TODOS LOS ENDPOINTS ESTÁN PROTEGIDOS POR USUARIOS NO LOGGEADOS
		return http.antMatcher("/**")
				.authorizeRequests()
				.anyRequest()
				.hasRole("ADMIN")
				.and()
				.csrf()
				.disable()
				.httpBasic()
				.and()
				.build();
		
				
		
	}

	
	@Bean
	public WebMvcConfigurer corsConfigurer()
	{
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*");
			}
		};
	}
	
	

}
