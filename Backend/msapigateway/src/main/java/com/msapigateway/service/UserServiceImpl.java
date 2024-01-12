package com.msapigateway.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.msapigateway.model.Role;
import com.msapigateway.model.User;
import com.msapigateway.repository.UserRepository;
import com.msapigateway.security.jwt.JwtProvider;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//CONTIENE EL TOKEN 
	@Autowired
	private JwtProvider jwtProvider;
	
	@Override
	public User saveUser(User user) {
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(Role.USER);
		user.setFechaCreacion(LocalDateTime.now());
		User userCreated = userRepository.save(user);
		
		//PASARLE EL TOKEN UNA VEZ QUE SE HA CREADO EL USUARIO EN LA BASE DE DATOS
		String jwt = jwtProvider.generateToken(userCreated);
		userCreated.setToken(jwt);
		
		return userCreated;
	}
	
	@Override
	public Optional<User> findByUsername(String username){
		return userRepository.findByUsername(username);
	}
	
	//SE OCUPA LA NOTACIÓN TRANSACTIONAL PARA INDICAR QUE SE ESTÁ EJECUTANDO UNA
	//SENTENCIA SQL DIRECTAMENTE A CÓDIGO
	@Transactional
	@Override
	public void changeRole(Role newRole, String username) {
		userRepository.updateUserRole(username, newRole);
	}
	
	
	//BUSCA EL USUARIO, SI TIENE UN TOKEN, SIGNIFICA QUE ESTA LOGGEADO
	//ENTONCES, ES EL USUARIO EN SESIÓN, PASA COMO PARAMETRO EL TOKEN
	@Override
	public User findByUsernameReturnToken(String username) {
		User user = userRepository.findByUsername(username).orElseThrow(() 
				-> new UsernameNotFoundException("El usuario no existe:"+ username));
		
		String jwt = jwtProvider.generateToken(user);
		user.setToken(jwt);
		return user;
	}
	
	
	
}
