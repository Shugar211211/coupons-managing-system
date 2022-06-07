package me.coupons.console.service;

import java.util.List;

import me.coupons.entity.Category;
import me.coupons.entity.Coupon;
import me.coupons.entity.Customer;

public interface CustomerService {

	/**
	 * Method returns client message.
	 * @return client message.
	 */
	String getClientMsg();

	/**
	 * Method checks login credentials.
	 * @param email, password.
	 * @return true if customer with this email and password found in database, 
	 * or false otherwise.
	 * @throws DBOperationException in case of database operation error 
	 * while searching for customer.
	 */
	boolean login(String email, String password);

	/**
	 * Method performs coupon purchase.
	 * @param coupon object that represents coupon to be purchased.
	 * while performing coupon purchase.
	 */
	void purchaseCoupon(Coupon coupon);

	/**
	 * Method deletes coupon purchase from database. 
	 * Coupon itself is not deleted. 
	 * Coupon amount is increased by 1.
	 * @param coupon whose purchase to be deleted.
	 */
	void deletePurchase(Coupon coupon);

	/**
	 * Method finds all coupons of this customer.
	 * @return ArrayList of coupon objects or that represent coupons, 
	 * or null if customer not found.
	 */
	List<Coupon> getCustomerCoupons();

	/**
	 * Method finds all coupons of this customer filtered by category.
	 * @param Category title
	 * @return ArrayList of coupon objects or that represent coupons, 
	 * or null if customer not found.
	 */
	List<Coupon> getCustomerCoupons(Category category);

	/**
	 * Method finds all coupons of this customer filtered by max price.
	 * @param maxPrice - upper price bound
	 * @return ArrayList of coupon objects or that represent coupons, 
	 * or null if customer not found.
	 */
	List<Coupon> getCustomerCoupons(double maxPrice);

	/**
	 * Method retrieves customer details by customer id.
	 * @return Customer object if found or null if not found.
	 */
	Customer getCustomerDetails();

}