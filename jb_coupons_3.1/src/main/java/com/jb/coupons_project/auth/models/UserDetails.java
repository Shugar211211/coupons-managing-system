package com.jb.coupons_project.auth.models;

import org.springframework.stereotype.Component;

import com.jb.coupons_project.ClientType;

@Component
public class UserDetails {
	private String email;
	private String password;
	private ClientType clientType;
	
	public UserDetails() {
	}
	public UserDetails(String email, String password, ClientType clientType) {
		this.email = email;
		this.password = password;
		this.clientType = clientType;
	}
	public UserDetails(String email, String password, ClientType clientType, int id) {
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
