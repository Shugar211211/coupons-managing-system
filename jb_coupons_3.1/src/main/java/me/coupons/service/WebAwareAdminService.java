package me.coupons.service;

import java.util.List;

import me.coupons.entity.Company;
import me.coupons.entity.Customer;

public interface WebAwareAdminService {
	
	/**
	 * Method returns client message.
	 * @return client message
	 */
	String getClientMsg();

	/**
	 * Method adds new company to database.
	 * @param company - company object to add.
	 */
	void addCompany(Company company);

	/**
	 * Updates company in database.
	 * @param company - company object that represents updated company
	 */
	void updateCompany(Company company);

	/**
	 * Deletes company from database.
	 * @param companyID - id of company to delete.
	 */
	void deleteCompany(int companyID);

	/**
	 * Retrieves all companies from database.
	 * @return ArrayList of Company objects that represent companies.
	 */
	List<Company> getAllCompanies();

	/**
	 * Retrieves company by company id.
	 * @param companyID - company id to search for
	 * @return company object if found or null if company not found.
	 */
	Company getOneCompany(int companyID);

	/**
	 * Adds new customer to database.
	 * @param customer - customer object to add that represents customer.
	 */
	void addCustomer(Customer customer);

	/**
	 * Updates customer in database.
	 * @param customer - Customer object that represents updated customer.
	 */
	void updateCustomer(Customer customer);

	/**
	 * Reset company password
	 * @param id: company id
	 * @param password: company new password
	 */
	void resetCompanyPassword(int id, String password);
	
	/**
	 * Reset customer password
	 * @param id: customer id
	 * @param password: customer new password
	 */
	void resetCustomerPassword(int id, String password);
	
	/**
	 * Deletes customer from database.
	 * @param customerID to find and delete customer with this id.
	 */
	void deleteCustomer(int customerID);

	/**
	 * Retrieves all customers from database.
	 * @return list of all customers.
	 */
	List<Customer> getAllCustomers();

	/**
	 * Finds one customer by customer id.
	 * @param customerID to search for.
	 * @return customer object if found or null if not found.
	 */
	Customer getOneCustomer(int customerID);

}
