package com.jb.coupons_project.auth.models;

import org.springframework.stereotype.Component;

@Component
public class AuthenticationResponse {
	private final String jwt;
	
	public AuthenticationResponse() {
		this.jwt = null;
	}
	public AuthenticationResponse(String jwt) {
		this.jwt = jwt;
	}
	public String getJwt() {
		return jwt;
	}
}
