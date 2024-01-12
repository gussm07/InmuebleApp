package com.msapigateway.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msapigateway.model.User;
import com.msapigateway.service.AuthenticationService;
import com.msapigateway.service.UserService;

@RestController
@RequestMapping("api/authentication")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("sign-up")
	public ResponseEntity<?> signUp(@RequestBody User user){
		
		//SI YA EXISTE UN USUARIO EN LA BASE DE DATOS, ENVÍA UN ERROR DE YA CREADO
		if(userService.findByUsername(user.getUsername()).isPresent()) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);

		}
		//SI NO, GUARDA EL USUARIO CREADO
		return new ResponseEntity<>(userService.saveUser(user),HttpStatus.CREATED);
		
	}

	
	@PostMapping("sign-in")
	public ResponseEntity<?> signIn(@RequestBody User user){
		return new ResponseEntity<>(authenticationService.signInAndReturnJWT(user), 
				HttpStatus.OK);
	}

	
}
