package me.coupons.service;

import java.util.List;

import me.coupons.entity.Category;
import me.coupons.entity.Company;
import me.coupons.entity.Coupon;

public interface WebAwareCompanyService {
	/**
	 * This method returns client message.
	 * @return client message
	 */
	String getClientMsg();

	/**
	 * Method adds new coupon to database.
	 * @param coupon object that represents coupon to add.
	 */
	void addCoupon(int id, Coupon coupon);

	/**
	 * Method returns list of all categories
	 * @return
	 */
	String getCategoriesList();
	
	/**
	 * Get one coupon by coupon id
	 * @param id
	 * @return
	 */
	Coupon getCouponById(int id);
	
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
	List<Coupon> getCompanyCoupons(int id);
	
	/**
	 * Method retrieves all coupons of this company up to maximum price.
	 * @param maximum price to filter by.
	 * @return ArrayList of coupon objects or null if company not found.
	 */
	List<Coupon> getCompanyCoupons(int id, double maxPrice);

	/**
	 * Method retrieves all coupons of this company by category.
	 * @param category to filter by.
	 * @return ArrayList of coupon objects or null if company not found.
	 */
	List<Coupon> getCompanyCoupons(int id, Category category);

	/**
	 * Method retrieves company details by its id.
	 * @return Company object if found or null otherwise.
	 */
	Company getCompanyDetails(int id);
}
