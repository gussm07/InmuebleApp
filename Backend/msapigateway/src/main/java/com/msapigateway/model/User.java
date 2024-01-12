package com.msapigateway.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;


@Data
@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="username", unique = true, nullable = false, length=100)
	private String username;
	
	@Column(name="password", nullable = false)
	private String password;
	
	@Column(name="nombre", nullable = false)
	private String nombre;
	
	@Column(name="fecha_creacion",nullable = false)
	private LocalDateTime fechaCreacion;
	
	@Column(name="apellido_paterno",nullable = false)
	private String apellido_paterno;
	
	@Column(name="apellido_materno",nullable = false)
	private String apellido_materno;
	
	@Column(name="email",nullable = false)
	private String email;
	
	@Column(name="telefono",nullable = false)
	private String telefono;
	
	//LINEAS PARA IDENTIFICAR EL ROL DE CADA USUARIO REGISTRADO
	@Enumerated(EnumType.STRING)
	@Column(name="role", nullable=false)
	private Role role;
	
	@Transient
	private String token;
	
	
	
	
	
	
}
