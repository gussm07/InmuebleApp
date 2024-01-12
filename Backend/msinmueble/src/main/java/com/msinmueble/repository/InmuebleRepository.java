package com.msinmueble.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msinmueble.model.Inmueble;

public interface InmuebleRepository extends JpaRepository<Inmueble, Long>{

}
