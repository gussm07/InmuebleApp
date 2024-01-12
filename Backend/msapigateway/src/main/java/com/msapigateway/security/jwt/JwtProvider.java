package com.msapigateway.security.jwt;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.msapigateway.model.User;
import com.msapigateway.security.UserPrincipal;

public interface JwtProvider {

	String generateToken(UserPrincipal auth);

	Authentication getAuthentication(HttpServletRequest request);

	boolean isTokenValid(HttpServletRequest request);

	String generateToken(User user);

	boolean isTokenValid(String token);

	Authentication getAuthentication(String token);

	Set<GrantedAuthority> getAuthoritiesFromToken(String token);

	String getUsernameFromToken(String token);

	String generateToken(String username, Set<GrantedAuthority> authorities);


}
