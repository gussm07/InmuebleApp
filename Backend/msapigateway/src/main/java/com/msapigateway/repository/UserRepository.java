package com.msapigateway.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.msapigateway.model.Role;
import com.msapigateway.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	//findBy + nombreCampo
	//MÉTODO QUE PUEDE RETORNAR NULL SI EL USUARIO NO SE ENCUENTRA 
	//EN LA BASE DE DATOS
	Optional<User> findByUsername(String username);
	
	//METODO PARA SETEAR EL ROL DE UN USUARIO EN LA BASE DE DATOS
	@Modifying
	@Query("update User set role=:role where username=:username")
	void updateUserRole(@Param("username")String username, @Param("role") Role role);
}
