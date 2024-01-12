package com.msinmueble.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data //CADA PROPIEDAD GENERE EN TIEMPO DE EJECUCION GET Y SET 
@Table(name="Inmueble")
public class Inmueble {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name="nombre", length=150, nullable = false)
	private String nombre;
	@Column(name="direccion", length=500, nullable = false)
	private String direccion;
	@Column(name="foto", length=1300, nullable = true)
	private String foto;
	@Column(name="precio", nullable = false)
	private Double precio;
	@Column(name="fecha_creacion", nullable = false)
	private LocalDateTime fechaCreacion;




}
