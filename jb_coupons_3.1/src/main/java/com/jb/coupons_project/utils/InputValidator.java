package com.jb.coupons_project.utils;

import com.jb.coupons_project.entity.Company;
import com.jb.coupons_project.entity.Coupon;
import com.jb.coupons_project.entity.Customer;

public interface InputValidator {

	/**
	 * This method returns client message.
	 */
	String getClientMsg();

	/**
	 * This method checks if password contains invalid characters and sets clientMsg accordingly.
	 * @param password to validate.
	 * @return password if password is validated, or null if password is not validated.
	 */
	String validatePassword(String password);

	/**
	 * This method checks if email contains invalid characters and sets clientMsg accordingly.
	 * @param email to validate.
	 * @return email if email is validated, or null if email is not validated.
	 */
	String validateEmail(String email);

	/**
	 * This method checks if name contains invalid characters and sets clientMsg accordingly.
	 * It is used to validate first and last names.
	 * @param name to validate.
	 * @return name if name is validated, or null if name is not validated.
	 */
	String validateName(String name);

	/**
	 * Checks if string contains invalid characters and set clientMsg accordingly.
	 * Used to validate coupons titles and descriptions.
	 * @param string to validate.
	 * @return string if string is validated, or null if string is not validated.
	 */
	String validateString(String str);
	
	/**
	 * This method checks if all fields of company object are valid 
	 * and sets clientMsg accordingly.
	 * @param company object to validate.
	 * @return company object if it was validated, or null if company object was not validated.
	 */
	Company validateCompany(Company company);

	/**
	 * This method checks if all fields of customer object are valid 
	 * and sets clientMsg accordingly.
	 * @param customer object to validate.
	 * @return customer object if it was validated, or null if customer object was not validated.
	 */
	Customer validateCustomer(Customer customer);

	/**
	 * This method checks if all fields of coupon object are valid 
	 * and sets clientMsg accordingly.
	 * @param coupon object to validate.
	 * @return coupon object if it was validated, or null if coupon object was not validated.
	 */
	Coupon validateCoupon(Coupon coupon);

}