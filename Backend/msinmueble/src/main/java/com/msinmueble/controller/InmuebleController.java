package com.msinmueble.controller;

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

import com.msinmueble.model.Inmueble;
import com.msinmueble.service.InmuebleService;


@RestController
@RequestMapping("/api/inmueble")
public class InmuebleController {
	
	@Autowired
	private InmuebleService inmuebleService;

	@PostMapping
	public ResponseEntity<?> saveInmueble(@RequestBody Inmueble inmueble ){
		
		return new ResponseEntity<>(inmuebleService
									.saveInmueble(inmueble),
									HttpStatus.CREATED);
		
	}

	@DeleteMapping("{inmuebleId}")
	public ResponseEntity<?> deleteInmueble(@PathVariable Long inmuebleId){
		inmuebleService.deleteInmueble(inmuebleId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	
	@GetMapping
	public ResponseEntity<?> getAllInmuebles(){
		return  ResponseEntity.ok(inmuebleService.findAllInmuebles());
	}













}
