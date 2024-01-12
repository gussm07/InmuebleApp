package com.msinmueble.service;

import java.util.List;

import com.msinmueble.model.Compra;

public interface CompraService {

	Compra saveCompra(Compra compra);

	List<Compra> allComprasByUser(Long userId);

	
}
