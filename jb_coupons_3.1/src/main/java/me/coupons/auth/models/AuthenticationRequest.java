package me.coupons.auth.models;

import org.springframework.stereotype.Component;

import me.coupons.entity.ClientType;

@Component
public class AuthenticationRequest {
	private String email;
	private String password;
	private ClientType clientType;
	
	public AuthenticationRequest() {
	}
	public AuthenticationRequest(String email, String password, ClientType clientType) {
		super();
		this.email = email;
		this.password = password;
		this.clientType = clientType;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public ClientType getClientType() {
		return clientType;
	}
	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}
}
