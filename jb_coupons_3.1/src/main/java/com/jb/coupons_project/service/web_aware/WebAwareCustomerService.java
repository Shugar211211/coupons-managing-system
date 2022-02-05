package com.jb.coupons_project.service.web_aware;

import java.util.List;

import com.jb.coupons_project.entity.Category;
import com.jb.coupons_project.entity.Coupon;
import com.jb.coupons_project.entity.Customer;

public interface WebAwareCustomerService {

	/**
	 * Method returns client message.
	 * @return client message.
	 */
	String getClientMsg();

	/**
	 * Method performs coupon purchase.
	 * @param coupon object that represents coupon to be purchased.
	 * while performing coupon purchase.
	 */
	void purchaseCoupon(int id, Coupon coupon);

	/**
	 * Method deletes coupon purchase from database. 
	 * Coupon itself is not deleted. 
	 * Coupon amount is increased by 1.
	 * @param coupon whose purchase to be deleted.
	 */
	void deletePurchase(int id, Coupon coupon);

	/**
	 * Returns coupons that are available for purchase
	 * @param id
	 * @return
	 */
	List<Coupon> getAwailableCoupons(int id);
	
	/**
	 * Method returns list of all categories
	 * @return
	 */
	String getCategoriesList();
	
	/**
	 * Method finds all coupons of this customer.
	 * @return ArrayList of coupon objects or that represent coupons, 
	 * or null if customer not found.
	 */
	List<Coupon> getCustomerCoupons(int id);

	/**
	 * Get one coupon by coupon id
	 * @param id
	 * @return
	 */
	Coupon getCouponById(int id);
	
	/**
	 * Method finds all coupons of this customer filtered by category.
	 * @param Category title
	 * @return ArrayList of coupon objects or that represent coupons, 
	 * or null if customer not found.
	 */
	List<Coupon> getCustomerCoupons(int id, Category category);

	/**
	 * Method finds all coupons of this customer filtered by max price.
	 * @param maxPrice - upper price bound
	 * @return ArrayList of coupon objects or that represent coupons, 
	 * or null if customer not found.
	 */
	List<Coupon> getCustomerCoupons(int id, double maxPrice);

	/**
	 * Method retrieves customer details by customer id.
	 * @return Customer object if found or null if not found.
	 */
	Customer getCustomerDetails(int id);
}
