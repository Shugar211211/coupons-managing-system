package com.jb.coupons_project.login_manager;

import com.jb.coupons_project.ClientType;
import com.jb.coupons_project.service.ClientFacade;

public interface LoginManager {
	public String getClientMsg();
	public ClientFacade login(String email, String password, ClientType clientType);
	public void logout(ClientFacade clientFacade);
}