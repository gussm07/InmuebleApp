package com.msapigateway.security.jwt;

import java.io.IOException;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.msapigateway.Utils.SecurityUtils;


public class JwtAuthorizationFilter extends OncePerRequestFilter{

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);
	@Autowired
	private JwtProvider jwtProvider;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		 String token = SecurityUtils.extractAuthTokenFromRequest(request);

		    if (token == null) {
		        filterChain.doFilter(request, response);
		        return;
		    }

		    Authentication authentication = jwtProvider.getAuthentication(token);

		    if (authentication != null) {
		        // Si el token es válido, continua con la autenticación
		        if (jwtProvider.isTokenValid(token)) {
		            logger.debug("Autenticación exitosa en el filter");
		            SecurityContextHolder.getContext().setAuthentication(authentication);
		        } else {
		            // Si el token ha expirado, genera uno nuevo
		            if (jwtProvider.isTokenValid(token)) {
		            	String username = jwtProvider.getUsernameFromToken(token);
		            	Set<GrantedAuthority> authorities = jwtProvider.getAuthoritiesFromToken(token);
		                // Genera un nuevo token
		                String newToken = jwtProvider.generateToken(username, authorities);
		                logger.debug("generando nuevo token");
		                logger.debug(token);
		                logger.info("generando token");
		                // Actualiza la respuesta con el nuevo token
		                response.setHeader("Authorization", "Bearer " + newToken);
		            }
		        }
		    } else {
		        filterChain.doFilter(request, response);
		        return;
		    }

		    filterChain.doFilter(request, response);
		
	}
	
	
	
	
	

}
