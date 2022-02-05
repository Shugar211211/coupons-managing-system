package com.jb.coupons_project.service;

import java.util.List;

import com.jb.coupons_project.entity.Category;
import com.jb.coupons_project.entity.Company;
import com.jb.coupons_project.entity.Coupon;

public interface CompanyService {

	/**
	 * This method returns client message.
	 * @return client message
	 */
	String getClientMsg();

	/**
	 * Method checks login credentials.
	 * @param email, password.
	 * @return true if credentials match company in database or false otherwise.
	 * @throws DBOperationException in case of database error.
	 */
	boolean login(String email, String password);

	/**
	 * Method adds new coupon to database.
	 * @param coupon object that represents coupon to add.
	 */
	void addCoupon(Coupon coupon);

	/**
	 * Method updates coupon entry in database.
	 * @param coupon object that represents new coupon to replace old one by id.
	 */
	void updateCoupon(Coupon coupon);

	/**
	 * Method deletes coupon entry from database.
	 * @param id of coupon to delete.
	 */
	void deleteCoupon(int couponID);

	/**
	 * Method retrieves all coupons of this company.
	 * @return ArrayList of coupon objects or null if company not found.
	 */
	List<Coupon> getCompanyCoupons();
	
	/**
	 * Method retrieves all coupons of this company up to maximum price.
	 * @param maximum price to filter by.
	 * @return ArrayList of coupon objects or null if company not found.
	 */
	List<Coupon> getCompanyCoupons(double maxPrice);

	/**
	 * Method retrieves all coupons of this company by category.
	 * @param category to filter by.
	 * @return ArrayList of coupon objects or null if company not found.
	 */
	List<Coupon> getCompanyCoupons(Category category);

	/**
	 * Method retrieves company details by its id.
	 * @return Company object if found or null otherwise.
	 */
	Company getCompanyDetails();
}