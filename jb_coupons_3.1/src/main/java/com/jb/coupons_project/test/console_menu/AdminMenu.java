package com.jb.coupons_project.test.console_menu;

import com.jb.coupons_project.service.ClientFacade;

public interface AdminMenu {

	/**
	 * This method gets from user choice and call corresponding method.  
	 * @param admin email
	 * @param admin password
	 */
	void adminMenuOptions(ClientFacade clientFacade);

}