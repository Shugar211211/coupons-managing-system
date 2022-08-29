package me.coupons.repository;

public interface CouponRepositoryExtension {
	
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
