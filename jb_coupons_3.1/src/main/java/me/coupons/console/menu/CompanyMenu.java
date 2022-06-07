package me.coupons.console.menu;

import me.coupons.console.service.ClientFacade;

public interface CompanyMenu {

	/**
	 * Company operations menu
	 * @param company email
	 * @param compnay password
	 * @throws InvalidInputException in case of database operation error. 
	 */
	void companyMenuOptions(ClientFacade clientFacade);

}