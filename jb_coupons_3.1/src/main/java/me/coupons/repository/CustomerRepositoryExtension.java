package me.coupons.repository;

import me.coupons.entity.Customer;

public interface CustomerRepositoryExtension {

	/**
	 * Update customer, password field is ignored
	 * @param customer: Customer object
	 */
	void updateCustomerSkipPassword(Customer customer);
	
	/**
	 * Reset customer password
	 * @param id: customer id
	 * @param password: new password
	 */
	void resetCustomerPassword(int id, String password);
}
