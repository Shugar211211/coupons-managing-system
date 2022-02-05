package com.jb.coupons_project.repository;

public interface CouponRepositoryCustom {
	
	/**
	 * Add coupon purchase
	 * @param customerID
	 * @param couponID
	 */
	void purchaseCoupon(int customerID, int couponID);
	
	/**
	 * Delete coupon purchase
	 * @param customerID
	 * @param couponID
	 */
	void deleteCouponPurchase(int customerID, int couponID);
}
