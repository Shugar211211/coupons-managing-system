package me.coupons.console.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import me.coupons.entity.Category;
import me.coupons.entity.Coupon;
import me.coupons.entity.Customer;
import me.coupons.repository.CompanyRepository;
import me.coupons.repository.CouponRepository;
import me.coupons.repository.CustomerRepository;
import me.coupons.utils.InputValidator;

@Service
@Scope("prototype")
public class CustomerServiceJpaImpl extends ClientFacade implements CustomerService {
	
	private InputValidator inputValidator;
	
	// Operation status message - used for client feedback
	private String clientMsg;
	
	// Data for the customer that is logged in
	private Integer customerID = null;
	private Customer customer;
	
	/**
	 * No args constructor
	 */
	public CustomerServiceJpaImpl() {
		this.clientMsg="";
	}
	
	/**
	 * Constructor
	 * @param companyRepository
	 * @param customerRepository
	 * @param couponRepository
	 * @throws DBOperationException
	 */
	@Autowired
	public CustomerServiceJpaImpl(CompanyRepository companyRepository, 
								 CustomerRepository customerRepository,
								 CouponRepository couponRepository,
								 InputValidator inputValidator) {
		super(companyRepository, customerRepository, couponRepository);
		this.clientMsg="";
		this.inputValidator = inputValidator;
	}
	
	@Override
	public String getClientMsg() {
		return this.clientMsg;
	}
	
	@Override
	public boolean login(String email, String password)
	{
		Optional<Customer> optionalCustomer = customerRepository.findByEmailAndPassword(email, password);
		
		if(optionalCustomer.isPresent())
		{
			this.customer = optionalCustomer.get();
			this.customerID = this.customer.getId();
			this.clientMsg = this.customer.getFirstName() + " " + this.customer.getLastName()+": logged in as customer.";
			return true;
		}
		else
		{
			this.clientMsg = "user " + email + " not found.";
			return false;
		}
	}
	
	@Override
	public void purchaseCoupon(Coupon coupon) {
		
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
		if(couponRepository.checkIfCouponIsPurchasedByCustomer(customerID, coupon.getId()) > 0)
//		if(this.customer.hasCoupon(coupon))
		{
			this.clientMsg="Can not make purchase: this coupon already purchased by this customer";
			return;
		}

		
		// make coupon purchase: add purchase record and decrease coupon amount by 1
		couponRepository.purchaseCoupon(customerID, coupon.getId());
		
		
//		// another way of making coupon purchase
//		
//		// update coupon amount for this coupon: decrease by 1
//		coupon.setAmount(coupon.getAmount()-1);
//		couponRepository.save(coupon);
//		couponRepository.saveAndFlush(coupon);
//		
//		// add coupon to customer
//		this.customer.addCoupon(coupon);
//		
//		// update customer
//		customerRepository.save(this.customer);
//		
		this.clientMsg="Coupon purchased";
	}

	@Override
	public void deletePurchase(Coupon coupon) {
		
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
		if(couponRepository.checkIfCouponIsPurchasedByCustomer(customerID, coupon.getId()) == 0)
//		if( ! this.customer.hasCoupon(coupon) )
		{
			this.clientMsg="Can not delete purchase: this coupon is not purchased by this customer";
			return;
		}
		
		// delete coupon purchase: delete purchase record and increase coupon amount by 1
		couponRepository.deleteCouponPurchase(customerID, coupon.getId());
		this.clientMsg="Coupon purchase deleted";
		
		
//		// another way of deleting coupon purchase:
//		
//		// update coupon amount for this coupon: increase by 1
//		coupon.setAmount(coupon.getAmount()+1);
//		couponRepository.save(coupon);
//		
//		// remove coupon from customer
//		if(this.customer.removeCoupon(coupon) != null)
//		{
//			// update customer
//			customerRepository.save(this.customer);	
//			this.clientMsg="Coupon purchase deleted";
//		}
//		else
//			this.clientMsg="Error: can not delete coupon purchase";
	}

	@Override
	public List<Coupon> getCustomerCoupons() {
		List<Coupon> coupons = this.customer.getCoupons();
		if(coupons == null)
			this.clientMsg="Error retrieving coupons";
		if(coupons.size() == 0)
			this.clientMsg="No coupons found purchased by the customer";
		this.clientMsg=coupons.size() + " coupons found purchased by for the customer";
		return coupons;
	}

	@Override
	public List<Coupon> getCustomerCoupons(Category category) {
		
		if(category == null)
		{
			this.clientMsg="Invalid category";
			return null;
		}
		
		List<Coupon> coupons = 
				couponRepository.findCouponsByCustomerAndCategory(this.customerID.intValue(), 
																  category.toString());
		if(coupons == null)
			this.clientMsg="Error retrieving coupons";
		if(coupons.size() == 0)
			this.clientMsg="No coupons found in this category purchased by the customer";
		this.clientMsg=coupons.size() + " coupons found in this category purchased by the customer";
		return coupons;
	}

	@Override
	public List<Coupon> getCustomerCoupons(double maxPrice) {
		List<Coupon> coupons = 
				couponRepository.findCouponsByCustomerAndMaxPrice(this.customerID.intValue(), maxPrice);
		if(coupons == null)
			this.clientMsg="Error retrieving coupons";
		if(coupons.size() == 0)
			this.clientMsg="No coupons found below this price purchased by the customer";
		this.clientMsg=coupons.size() + " coupons found below this price purchased by the customer";
		return coupons;
	}

	@Override
	public Customer getCustomerDetails() {
		if(this.customerID == null)
		{
			this.clientMsg="Error: client not logged in as customer";
			return null;
		}
		
		Optional<Customer> optionalCustomer = customerRepository.findById(customerID);
		Customer customer = optionalCustomer.get();
		if(customer == null)
			this.clientMsg="Customer was not found";
		return customer;
	}
}
