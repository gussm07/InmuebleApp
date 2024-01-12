package com.msapigateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.msapigateway.model.Role;
import com.msapigateway.security.jwt.JwtAuthorizationFilter;


@EnableWebSecurity
@Configuration
public class SecurityConfig {
	
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception
	{
		return authenticationConfiguration.getAuthenticationManager();
	}
	

	@Bean
	public JwtAuthorizationFilter jwtAuthorizationFilter() {
		return new JwtAuthorizationFilter();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
	{
		AuthenticationManagerBuilder auth = http.getSharedObject(
				AuthenticationManagerBuilder.class);
	
		
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
		
		AuthenticationManager authenticationManager = auth.build();
		
		//RUTAS PARA ACCESOS A CUALQUIER USUARIO SIN HABER LOGGEADO
		http.cors();
		http.csrf().disable();
		http.authenticationManager(authenticationManager);
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		
		http.authorizeHttpRequests()
		.antMatchers("/api/authentication/sign-in","/api/authentication/sign-up").permitAll()
		//SOLO SE PUEDE OBTENER INFORMACIÓN PÚBLICA PARA LA CONSULTA DE DATOS
		.antMatchers(HttpMethod.GET,"/gateway/inmueble").permitAll()
		//PERMITE REALIZAR MODIFICACIONES DE DATOS A LOS QUE TENGAN EL ROL DE ADMIN
		.antMatchers("/gateway/inmueble/**").hasRole(Role.ADMIN.name())
		//Y QUE ESTE AUTENTICADO EL ADMINISTRADOR
		.anyRequest().authenticated();
		
		http.addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	

	
	@Bean
	public WebMvcConfigurer corsConfigurer()
	{
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				//PERMITE ACCEDER A TODOS LOS ENDPOINTS Y DE CUAALQUIER ORIGEN
				registry.addMapping("/**").allowedOrigins("*");
			}
		};
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
