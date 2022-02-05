package com.jb.coupons_project.test.console_menu;

import com.jb.coupons_project.service.ClientFacade;

public interface CustomerMenu {

	/**
	 * This method gets from user choice and call corresponding method. 
	 * @param customer email
	 * @param customer password
	 * @throws InvalidInputException in case of invalid input.
	 */
	void customerMenuOptions(ClientFacade clientFacade);

}