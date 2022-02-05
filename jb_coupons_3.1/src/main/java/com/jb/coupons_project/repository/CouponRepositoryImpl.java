package com.jb.coupons_project.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CouponRepositoryImpl implements CouponRepositoryCustom {

	@PersistenceContext
    EntityManager entityManager;
	
	@Override
	public void purchaseCoupon(int customerID, int couponID) {
		// decrease coupon amount by 1
		Query query = entityManager.createNativeQuery("UPDATE coupons SET amount = amount - 1 WHERE id = ?1 ");
		query.setParameter(1, couponID);
		query.executeUpdate();
				
		// add purchase record
		query = entityManager.createNativeQuery("INSERT INTO customers_vs_coupons (customer_id, coupon_id) VALUES ( ?1 , ?2 )");
		query.setParameter(1, customerID);
		query.setParameter(2, couponID);
		query.executeUpdate();
	}

	@Override
	public void deleteCouponPurchase(int customerID, int couponID) {
		// increase coupon amount by 1
		Query query = entityManager.createNativeQuery("UPDATE coupons SET amount = amount + 1 WHERE id = ?1 ");
		query.setParameter(1, couponID);
		query.executeUpdate();
						
		// delete purchase record
		query = entityManager.createNativeQuery("DELETE FROM customers_vs_coupons WHERE customer_id = ?1 AND coupon_id = ?2 ");
		query.setParameter(1, customerID);
		query.setParameter(2, couponID);
		query.executeUpdate();
	}
}