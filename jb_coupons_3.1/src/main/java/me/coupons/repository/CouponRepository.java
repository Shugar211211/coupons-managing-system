package me.coupons.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import me.coupons.entity.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Integer>, CouponRepositoryExtension
{
	/**
	 * Delete all coupons whose end date is less than given date
	 * @param date 
	 * @return number of coupons deleted
	 */
//	@Query(value = "DELETE FROM coupons WHERE end_date < :endDate", 
//			nativeQuery = true)
	@Query("DELETE FROM Coupon WHERE endDate < :endDate")
	@Modifying
	public int deleteCouponsOlderThan(@Param("endDate") Date date);

//	/** 
//	 * Retrieve all coupons with given company id
//	 * @param companyID as search criteria
//	 * @return ArrayList of coupons
//	 */
//	@Query("from Coupon where company_id = :companyID")
//	public ArrayList<Coupon> findCouponsByCompany(@Param("companyID") int companyID);
	
	/**
	 * Retrieve all coupons with given company id that are under the given price
	 * @param companyID as search criteria
	 * @param maxPrice as search criteria
	 * @return ArrayList of coupons
	 */
	@Query("from Coupon where company_id = :companyID and price < :maxPrice")
	public List<Coupon> findAllCouponsByCompanyUnderMaxPrice(@Param("companyID") int companyID, @Param("maxPrice") double maxPrice);
	
	/**
	 * Retrieve all coupons with given company id that are in the given category
	 * @param companyID as search criteria
	 * @param category as search criteria
	 * @return ArrayList of coupons
	 */
	@Query("from Coupon where company_id = :companyID and category_id = :category")
	public List<Coupon> findAllCouponsByCompanyAndByCategory(@Param("companyID") int companyID, @Param("category") String categoryName);
	
	/**
	 * Retrieve all coupons with given title and company id
	 * @param title as search criteria
	 * @param companyID as search criteria
	 * @return ArrayList of coupons
	 */
	@Query("from Coupon where title = :title and company_id = :companyID")
	public List<Coupon> findAllCouponsByTitleAndCompany(@Param("title") String title, @Param("companyID") int companyID);
	
	/**
	 * Retrieve all coupons in given category purchased by customer whose id is provided
	 * @param customer id
	 * @param category
	 * @return ArrayList of coupons
	 */
	@Query(value = "SELECT T1.ID, "
						+ "T1.COMPANY_ID, "
						+ "T1.TITLE, "
						+ "T1.DESCRIPTION, "
						+ "T1.CATEGORY_ID, "
						+ "T1.START_DATE, "
						+ "T1.END_DATE, "
						+ "T1.AMOUNT, "
						+ "T1.PRICE, "
						+ "T1.IMAGE "
					+ "FROM COUPONS T1 JOIN CUSTOMERS_VS_COUPONS T2 ON T1.ID = T2.COUPON_ID "
					+ "WHERE T2.CUSTOMER_ID = :customerID AND T1.CATEGORY_ID = :category", 
		   nativeQuery = true)
	public List<Coupon> findCouponsByCustomerAndCategory(@Param("customerID")int customerID, 
															  @Param("category")String category);
	
	/**
	 * Retrieve all coupons below given price purchased by customer whose id is provided
	 * @param customer id
	 * @param maximum price
	 * @return
	 */
	@Query(value = "SELECT T1.ID, "
						+ "T1.COMPANY_ID, "
						+ "T1.TITLE, "
						+ "T1.DESCRIPTION, "
						+ "T1.CATEGORY_ID, "
						+ "T1.START_DATE, "
						+ "T1.END_DATE, "
						+ "T1.AMOUNT, "
						+ "T1.PRICE, "
						+ "T1.IMAGE "
					+ "FROM COUPONS T1 JOIN CUSTOMERS_VS_COUPONS T2 ON T1.ID = T2.COUPON_ID "
					+ "WHERE T2.CUSTOMER_ID = :customerID AND T1.PRICE < :maxPrice", 
		   nativeQuery = true)
	public List<Coupon> findCouponsByCustomerAndMaxPrice(@Param("customerID")int cutomerID, 
															  @Param("maxPrice")double maxPrice);
	/**
	 * Checks if coupon is purchased by customer
	 * @param customerID
	 * @param couponID
	 * @return true / false
	 */
	@Query(value = "SELECT COUNT(*) FROM customers_vs_coupons "
					+ "WHERE (customer_id = :customerID AND coupon_id = :couponID)",
			nativeQuery = true)
	public int checkIfCouponIsPurchasedByCustomer(@Param("customerID") int customerID, 
												  @Param("couponID") int couponID);
}
