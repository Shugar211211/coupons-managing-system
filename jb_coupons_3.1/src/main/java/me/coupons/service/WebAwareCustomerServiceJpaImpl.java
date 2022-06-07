package me.coupons.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import me.coupons.entity.Category;
import me.coupons.entity.Coupon;
import me.coupons.entity.Customer;
import me.coupons.repository.CouponRepository;
import me.coupons.repository.CustomerRepository;
import me.coupons.utils.InputValidator;

@Service
@RequestScope
public class WebAwareCustomerServiceJpaImpl implements WebAwareCustomerService{
	
	@Autowired
	private CouponRepository couponRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	private String clientMsg="";
	
	@Autowired
	private InputValidator inputValidator;
	
	@Override
	public String getClientMsg() {
		return clientMsg;
	}
	
	@Override
	public void deletePurchase(int id, Coupon coupon) {
		
		// check if any coupon fields have invalid data
		Coupon validCoupon = inputValidator.validateCoupon(coupon);
		if(validCoupon == null)
		{
			this.clientMsg = inputValidator.getClientMsg();
			return;
		}
		
		// check if coupon with this id exists in db
		if(couponRepository.findById(coupon.getId()) == null)
		{
			this.clientMsg="Can not delete purchase: coupon with this id was not found";
			return;
		}
		
		// check if this coupon purchased by this customer
		if(couponRepository.checkIfCouponIsPurchasedByCustomer(id, coupon.getId()) == 0)
		{
			this.clientMsg="Can not delete purchase: this coupon is not purchased by this customer";
			return;
		}
		
		// delete coupon purchase: delete purchase record and increase coupon amount by 1
		couponRepository.deleteCouponPurchase(id, coupon.getId());
		this.clientMsg="Coupon purchase deleted";
	}	
	
	@Override
	public List<Coupon> getAwailableCoupons(int id) {
		List<Coupon> allCoupons = couponRepository.findAll();
		List<Coupon> customerCoupons = this.getCustomerCoupons(id);
		if(allCoupons == null || customerCoupons == null) {
			this.clientMsg = "Error retrieving coupons";
		}
		List<Coupon> awailableCoupons = new ArrayList<Coupon>(allCoupons);
		for(Coupon couponA : allCoupons) {
			for(Coupon couponC : customerCoupons) {
				if(couponA.getId() == couponC.getId() && couponA.getAmount()>0) {
					awailableCoupons.remove(couponA);
				}
			}
		}
		this.clientMsg = "Coupons retrieved";
		return awailableCoupons;
	}
	
	@Override
	public String getCategoriesList() {
		String categoriesStr = "{\"categories\": [\"";
		for(Category c : Category.values()) { 
//			categoriesStr = categoriesStr + c.getCategoryDescription().toString() + "\", \""; 
			categoriesStr = categoriesStr + c + "\", \"";
		}
		return categoriesStr.substring(0, categoriesStr.length()-3) + "]}";
	}
	
	@Override
	public Coupon getCouponById(int id) {
		Optional<Coupon> optionalCoupon = couponRepository.findById(id);
		if( ! optionalCoupon.isPresent() ) {
			this.clientMsg = "Coupon not found";
		}
		return optionalCoupon.get();
	}
	
	@Override
	public List<Coupon> getCustomerCoupons(int id) {
		Optional<Customer> optCustomer = customerRepository.findById(id);
		Customer customer = optCustomer.get();
		List<Coupon> coupons = customer.getCoupons();
		return coupons;
	}
	
	@Override
	public List<Coupon> getCustomerCoupons(int id, Category category) {
		
		if(category == null)
		{
			this.clientMsg="Invalid category";
			return null;
		}
		
		List<Coupon> coupons = couponRepository.findCouponsByCustomerAndCategory(id, category.toString());
		if(coupons == null)
			this.clientMsg="Error retrieving coupons";
		if(coupons.size() == 0)
			this.clientMsg="No coupons found in this category purchased by the customer";
		this.clientMsg=coupons.size() + " coupons found in this category purchased by the customer";	
		return coupons;
	}
	
	@Override
	public List<Coupon> getCustomerCoupons(int id, double maxPrice) {
		List<Coupon> coupons = couponRepository.findCouponsByCustomerAndMaxPrice(id, maxPrice);
		if(coupons == null)
			this.clientMsg="Error retrieving coupons";
		if(coupons.size() == 0)
			this.clientMsg="No coupons found below this price purchased by the customer";
		this.clientMsg=coupons.size() + " coupons found below this price purchased by the customer";
		return coupons;
	}
	
	@Override
	public Customer getCustomerDetails(int id) {
		Optional<Customer> optionalCustomer = customerRepository.findById(id);
		Customer customer = optionalCustomer.get();
		if(customer == null)
			this.clientMsg="Customer was not found";
		return customer;
	}
	
	@Override
	public void purchaseCoupon(int id, Coupon coupon) {
		
		// check if any coupon fields have invalid data
		Coupon validCoupon = inputValidator.validateCoupon(coupon);
		if(validCoupon == null)
		{
			this.clientMsg = inputValidator.getClientMsg();
			return;
		}
		
		// check if coupon with this id exists in db
		if(couponRepository.findById(coupon.getId()) == null)
		{
			this.clientMsg="Can not make purchase: coupon with this id was not found";
			return;
		}
		
		// check if amount of this coupon is at least 1
		if(couponRepository.findById(coupon.getId()).get().getAmount()<1)
		{
			this.clientMsg="Can not make purchase: coupon is not available for purchase";
			return;
		}
		
		// check if this coupon already purchased by this customer
		if(couponRepository.checkIfCouponIsPurchasedByCustomer(id, coupon.getId()) > 0)
		{
			this.clientMsg="Can not make purchase: this coupon already purchased by this customer";
			return;
		}
		
		// make coupon purchase: add purchase record and decrease coupon amount by 1
		couponRepository.purchaseCoupon(id, coupon.getId());
		this.clientMsg="Coupon purchased";
	}
}
