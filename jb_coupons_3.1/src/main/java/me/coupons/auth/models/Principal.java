package me.coupons.auth.models;

import org.springframework.stereotype.Component;

import me.coupons.entity.ClientType;

@Component
public class Principal {
	
	private int id;
	private String name;
	private ClientType role;
	
	public Principal() {
	}
	public Principal(int id, String name, ClientType role) {
		this.id = id;
		this.name = name;
		this.role = role;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ClientType getRole() {
		return role;
	}
	public void setRole(ClientType role) {
		this.role = role;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
