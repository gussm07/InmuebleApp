package com.msinmueble.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="Compra")
public class Compra {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "user_id", nullable = false)
	private Long userId;
	@Column(name = "inmueble_id", nullable = false)
	private Long inmuebleId;
	@Column(name = "titulo", nullable = false)
	private String titulo;
	@Column(name = "precio", nullable = false)
	private Double precio;
	@Column(name = "fecha_compra", nullable = false)
	private LocalDateTime fechaCompra;
	
	
	
	
}
