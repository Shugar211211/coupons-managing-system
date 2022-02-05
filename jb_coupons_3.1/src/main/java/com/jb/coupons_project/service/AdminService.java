package com.jb.coupons_project.service;

import java.util.List;

import com.jb.coupons_project.entity.Company;
import com.jb.coupons_project.entity.Customer;

public interface AdminService {

	/**
	 * Method returns client message.
	 * @return client message
	 */
	String getClientMsg();

	/**
	 * Checks login credentials.
	 * @param email - administrator email
	 * @param password - administrator password
	 * @return true if credentials approved or false otherwise.
	 * @throws DBOperationException in case of database error while checking credentials.
	 */
	boolean login(String email, String password);

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