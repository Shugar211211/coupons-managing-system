package com.jb.coupons_project.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jb.coupons_project.auth.models.AuthenticationRequest;
import com.jb.coupons_project.auth.models.Principal;
import com.jb.coupons_project.auth.models.UserDetails;
import com.jb.coupons_project.auth.utils.JwtUtil;
import com.jb.coupons_project.service.web_aware.auth_service.AuthService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class AuthRestController {
	
	@Autowired
	private UserDetails userDetails;
	
	@Autowired
	private JwtUtil jwtUtil; 
	
	@Autowired 
	private AuthService authService;
	
	@Autowired 
	private Principal principal;
	
	// endpoint for POST "/authenticate" - client authentication
	@RequestMapping(value="/authenticate", method=RequestMethod.POST)
	public ResponseEntity<?> createAuthJwtToken(@RequestBody AuthenticationRequest authenticationRequest) throws JsonProcessingException {
		
		// create userDetails object
		userDetails.setEmail(authenticationRequest.getEmail());
		userDetails.setPassword(authenticationRequest.getPassword());
		userDetails.setClientType(authenticationRequest.getClientType());
		
		// perform authentication
		principal = authService.authenticate(userDetails);
		
		// if authorization is successful create token
		final String jwtToken = jwtUtil.generateToken(principal);
		Map<String, Object> params = new HashMap<>();
		params.put("token", jwtToken);
		String payload = new ObjectMapper().writeValueAsString(params);
//		final String responseJson = "{\n\t\"token\": \"" + jwtToken + "\"\n}";
//		final String responseJson = "{\"token\":\"" + jwtToken + "\"}";
//		return ResponseEntity.ok(responseJson);
//		return ResponseEntity.ok("Bearer "+jwtToken);
//		return ResponseEntity.ok(jwtToken);
		return ResponseEntity.ok(payload);
	}
}
