package me.coupons.console.login_manager;

import me.coupons.console.service.ClientFacade;
import me.coupons.entity.ClientType;

public interface LoginManager {
	public String getClientMsg();
	public ClientFacade login(String email, String password, ClientType clientType);
	public void logout(ClientFacade clientFacade);
}