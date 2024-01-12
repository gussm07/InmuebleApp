package com.msinmueble.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msinmueble.model.Compra;
import com.msinmueble.repository.CompraRepository;

@Service
public class CompraServiceImpl implements CompraService{
	
	@Autowired
	private CompraRepository compraRepository;
	
	@Override
	public Compra saveCompra(Compra compra) {
		compra.setFechaCompra(LocalDateTime.now());
		
		return compraRepository.save(compra);
	}
	
	@Override
	public List<Compra> allComprasByUser(Long userId){
		return compraRepository.findAllByUserId(userId);
	}
	
	

}
