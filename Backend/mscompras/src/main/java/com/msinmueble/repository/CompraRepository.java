package com.msinmueble.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msinmueble.model.Compra;

public interface CompraRepository extends JpaRepository<Compra,Long>{

	List<Compra> findAllByUserId(Long userId);
	List<Compra> findByPrecio(Double precio);
}
