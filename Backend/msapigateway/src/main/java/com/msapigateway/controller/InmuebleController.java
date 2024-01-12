package com.msapigateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msapigateway.request.InmuebleServiceRequest;

@RestController
@RequestMapping("/gateway/inmueble")
public class InmuebleController {

	@Autowired
	private InmuebleServiceRequest inmuebleServiceRequest;
	
	
	@PostMapping
	public ResponseEntity<?> saveInmueble(@RequestBody Object inmueble){
		return new ResponseEntity<>(inmuebleServiceRequest.saveInmueble(inmueble), HttpStatus.CREATED);
	}
	
	@DeleteMapping("{inmuebleId}")
	public ResponseEntity<?> deleteInmueble(@PathVariable("inmuebleId") Long inmuebleId){
		inmuebleServiceRequest.deleteInmueble(inmuebleId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<?> getAllInmuebles(){
		return ResponseEntity.ok(inmuebleServiceRequest.getAllInmuebles());
	}
	
	
}
