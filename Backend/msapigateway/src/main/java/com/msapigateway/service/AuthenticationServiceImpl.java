package com.msapigateway.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.msapigateway.model.User;
import com.msapigateway.repository.UserRepository;
import com.msapigateway.security.UserPrincipal;
import com.msapigateway.security.jwt.JwtProvider;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private UserRepository userRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
	@Override
	public User signInAndReturnJWT(User signInRequest) {
		
		User user = userRepository.findByUsername(signInRequest.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("El usuario no fue encontrado" + signInRequest.getUsername()));
		
		Authentication authentication = authenticationManager.authenticate(
		new UsernamePasswordAuthenticationToken(user.getUsername(), signInRequest.getPassword()));

	UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();
	String jwt = jwtProvider.generateToken(userPrincipal);
	
	System.out.print("Token de inicio de sesión: " + jwt);
	logger.debug(jwt);
	
	User signInUser = userPrincipal.getUser();
	signInUser.setToken(jwt);
	
	return signInUser;
	
	
	}
	
	
}
