package com.msapigateway.security.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.msapigateway.Utils.SecurityUtils;
import com.msapigateway.model.User;
import com.msapigateway.security.UserPrincipal;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtProviderImpl  implements JwtProvider{
	private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
	@Value("${app.jwt.secret}")
	private String JWT_SECRET;

	@Value("${app.jwt.expiration-in-ms}")
	private Long JWT_EXPIRATION_IN_MS;
	
	@Value("${app.jwt.refreshExpirationDateInMs}")
	private int refreshExpirationDateInMs;
	
	
	public void setRefreshExpirationDateInMs(int refreshExpirationDateInMs) {
		this.refreshExpirationDateInMs = refreshExpirationDateInMs;
	}
	
	@Override
	public String generateToken(UserPrincipal auth) {
		String authorities = auth.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		
		//KEY PARA DESENCRIPTAR EL TOKEN
		Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

		Date now = new Date();
		return Jwts.builder()
				.setSubject(auth.getUsername())
				.claim("roles", authorities)
				.claim("userId", auth.getId())
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + JWT_EXPIRATION_IN_MS))
				.signWith(key, SignatureAlgorithm.HS512)
				.compact();
	}
	
	@Override
	public String generateToken(User user) {
		
		Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
		
		return Jwts.builder()
				.setSubject(user.getUsername())
				.claim("roles", user.getRole())
				.claim("userId", user.getId())
				.setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_IN_MS))
				.signWith(key, SignatureAlgorithm.HS512)
				.compact();	
		}

	@Override
	public Authentication getAuthentication(String token) {
		Claims claims = extractClaims(token);
		if(claims == null) {
			return null;
		}
		
		String username = claims.getSubject();
		Long userId = claims.get("userId", Long.class);
		
		Set<GrantedAuthority> authorities = Arrays.stream(claims.get("roles").toString().split(","))
				.map(SecurityUtils::convertToAuthority)
				.collect(Collectors.toSet());
		
		//CREA EL OBJETO USER CON LAS CREDENCIALES EXTRAIDAS DEL TOKEN DE ARRIBA
		UserDetails userDetails = UserPrincipal.builder()
				.username(username)
				.authorities(authorities)
				.id(userId)
				.build();
		
		if(username == null) {
			return null;
		}
		
		return new UsernamePasswordAuthenticationToken(userDetails, null,authorities);
		
	}
	
	@Override
	public boolean isTokenValid(String token) {
		  try {
		    Claims claims = Jwts.parserBuilder()
		        .setSigningKey(Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8)))
		        .build()
		        .parseClaimsJws(token)
		        .getBody();

		    return !claims.getExpiration().before(new Date());
		  } catch (JwtException e) {
		    logger.error("Token inválido o expirado: {}", e.getMessage());
		    return false;
		  }
		}
	
	
	
	private Claims extractClaims(String token) {
		//IDENTIFICAR SI EXISTE UN VALOR EN EL HEADER, EXTRAERLO Y PASARLO COMO UN TOKEN
//		String token = SecurityUtils.extractAuthTokenFromRequest(request);
//		logger.debug(token);
//		if(token == null) {
//			return null;
//		}
//		
//		
//		Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
//		
//		return Jwts.parserBuilder()
//				.setSigningKey(key)
//				.build()
//				.parseClaimsJws(token)
//				.getBody();
		
		 try {
		        return Jwts.parserBuilder()
		                .setSigningKey(Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8)))
		                .build()
		                .parseClaimsJws(token)
		                .getBody();
		    } catch (JwtException e) {
		        return null;
		    }
	}

	@Override
	public boolean isTokenValid(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Authentication getAuthentication(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsernameFromToken(String token) {
	    try {
	        Claims claims = Jwts.parserBuilder()
	                .setSigningKey(JWT_SECRET.getBytes(StandardCharsets.UTF_8))
	                .build()
	                .parseClaimsJws(token)
	                .getBody();

	        return claims.getSubject();
	    } catch (JwtException e) {
	        logger.error("Error al extraer el username del token: {}", e.getMessage());
	        return null;
	    }
	}
	
	@Override
	public Set<GrantedAuthority> getAuthoritiesFromToken(String token) {
	    try {
	        Claims claims = Jwts.parserBuilder()
	                .setSigningKey(JWT_SECRET.getBytes(StandardCharsets.UTF_8))
	                .build()
	                .parseClaimsJws(token)
	                .getBody();

	        String authorities = (String) claims.get("roles");
	        return Arrays.stream(authorities.split(","))
	                .map(SecurityUtils::convertToAuthority)
	                .collect(Collectors.toSet());
	    } catch (JwtException e) {
	        logger.error("Error al extraer las authorities del token: {}", e.getMessage());
	        return null;
	    }
	}

	@Override
	public String generateToken(String username, Set<GrantedAuthority> authorities) {
		String authoritiesString = authorities.stream()
	            .map(GrantedAuthority::getAuthority)
	            .collect(Collectors.joining(","));

	    // KEY PARA DESENCRIPTAR EL TOKEN
	    Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

	    long now = (new Date()).getTime();
	    Date validity = new Date(now + JWT_EXPIRATION_IN_MS);

	    return Jwts.builder()
	            .setSubject(username)
	            .claim("roles", authoritiesString)
	            .setIssuedAt(new Date(now))
	            .setExpiration(validity)
	            .signWith(key, SignatureAlgorithm.HS512)
	            .compact();
	}
	
	
	

	
	
	
	
	
	
}
