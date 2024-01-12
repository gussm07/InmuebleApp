package com.msapigateway.service;

import com.msapigateway.model.User;

public interface AuthenticationService {

	User signInAndReturnJWT(User signInRequest);

	
	
	
	
	
}
