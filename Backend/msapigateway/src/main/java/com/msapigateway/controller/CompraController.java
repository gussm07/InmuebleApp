package com.msapigateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msapigateway.request.CompraServiceRequest;
import com.msapigateway.security.UserPrincipal;

@RestController
@RequestMapping("gateway/compra")
public class CompraController {

	@Autowired
	private CompraServiceRequest compraServiceRequest;
	
	@PostMapping
	public ResponseEntity<?> saveCompra(@RequestBody Object compra){
		return new ResponseEntity<>(compraServiceRequest.saveCompra(compra), HttpStatus.CREATED);
	}
	
	
	@GetMapping
	public ResponseEntity<?> getAllComprasOfUser(@AuthenticationPrincipal UserPrincipal userPrincipal){
		return ResponseEntity.ok(compraServiceRequest.getAllComprasOfUser(userPrincipal.getId()));
	}
	
	
}
