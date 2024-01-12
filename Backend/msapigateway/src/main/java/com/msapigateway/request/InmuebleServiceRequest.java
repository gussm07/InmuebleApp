package com.msapigateway.request;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//PERMITE EL REDIRECCIONAMIENTO A LOS MICROSERVICIOS
@FeignClient(
		value="inmueble-service",
		path="/api/inmueble",
		//url="${inmueble.service.url}",
		configuration= FeignConfiguration.class)
public interface InmuebleServiceRequest {

	@PostMapping
	Object saveInmueble(@RequestBody Object requestBody);
	
	@DeleteMapping("{inmuebleId}")
	void deleteInmueble(@PathVariable("inmuebleId")Long inmuebleId);
	
	@GetMapping()
	List<Object> getAllInmuebles();



}
