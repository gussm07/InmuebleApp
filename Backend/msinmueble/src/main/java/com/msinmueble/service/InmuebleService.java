package com.msinmueble.service;

import java.util.List;

import com.msinmueble.model.Inmueble;

public interface InmuebleService{

	Inmueble saveInmueble(Inmueble inmueble);

	void deleteInmueble(Long inmuebleId);

	List<Inmueble> findAllInmuebles();

}
