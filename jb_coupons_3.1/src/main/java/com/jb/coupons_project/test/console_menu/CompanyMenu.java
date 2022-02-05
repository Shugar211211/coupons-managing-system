package com.jb.coupons_project.test.console_menu;

import com.jb.coupons_project.service.ClientFacade;

public interface CompanyMenu {

	/**
	 * Company operations menu
	 * @param company email
	 * @param compnay password
	 * @throws InvalidInputException in case of database operation error. 
	 */
	void companyMenuOptions(ClientFacade clientFacade);

}