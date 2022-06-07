package me.coupons.console.menu;

import me.coupons.console.service.ClientFacade;

public interface AdminMenu {

	/**
	 * This method gets from user choice and call corresponding method.  
	 * @param admin email
	 * @param admin password
	 */
	void adminMenuOptions(ClientFacade clientFacade);

}