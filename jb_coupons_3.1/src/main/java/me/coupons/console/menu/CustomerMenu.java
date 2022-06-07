package me.coupons.console.menu;

import me.coupons.console.service.ClientFacade;

public interface CustomerMenu {

	/**
	 * This method gets from user choice and call corresponding method. 
	 * @param customer email
	 * @param customer password
	 * @throws InvalidInputException in case of invalid input.
	 */
	void customerMenuOptions(ClientFacade clientFacade);

}