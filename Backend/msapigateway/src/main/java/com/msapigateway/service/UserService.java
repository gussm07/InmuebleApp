package com.msapigateway.service;

import java.util.Optional;

import com.msapigateway.model.Role;
import com.msapigateway.model.User;

public interface UserService {

	User saveUser(User user);

	Optional<User> findByUsername(String username);

	void changeRole(Role newRole, String username);

	User findByUsernameReturnToken(String username);

	
	
}
