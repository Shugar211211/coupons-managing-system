package me.coupons.console.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import me.coupons.repository.CompanyRepository;
import me.coupons.repository.CouponRepository;
import me.coupons.repository.CustomerRepository;

@Component
public abstract class ClientFacade 
{	
	protected CompanyRepository companyRepository;
	protected CustomerRepository customerRepository;
	protected CouponRepository couponRepository;
	
	/**
	 * Checks login credentials.
	 * @param email - administrator email
	 * @param password - administrator password
	 * @return true if credentials approved or false otherwise.
	 * @throws DBOperationException in case of database error while checking credentials.
	 */
	public abstract boolean login(String email, String password);

	/**
	 * Constructor.
	 * @throws DBOperationException
	 */
	@Autowired
	public ClientFacade(CompanyRepository companyRepository, 
						CustomerRepository customerRepository, 
						CouponRepository couponRepository) 
	{
		this.companyRepository = companyRepository;
		this.customerRepository = customerRepository;
		this.couponRepository = couponRepository;
	}

	public ClientFacade() {
		super();
	}
}
