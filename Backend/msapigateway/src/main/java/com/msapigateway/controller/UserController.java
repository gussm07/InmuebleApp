package com.msapigateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msapigateway.model.Role;
import com.msapigateway.security.UserPrincipal;
import com.msapigateway.service.UserService;

@RestController
@RequestMapping("api/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PutMapping("change/{role}")
	public ResponseEntity<?> changeRole(@AuthenticationPrincipal UserPrincipal userPrincipal,
			@PathVariable Role role){
		
		userService.changeRole(role, userPrincipal.getUsername());
		return ResponseEntity.ok(true);
		
	}
	
	
	@GetMapping
	public ResponseEntity<?> currentUser(@AuthenticationPrincipal UserPrincipal userPrincipal){
		return new ResponseEntity<>(userService.findByUsernameReturnToken(userPrincipal.getUsername()), HttpStatus.OK);
	}
	
	
	
	
	
}
