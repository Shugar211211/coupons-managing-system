package com.jb.coupons_project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.jb.coupons_project.entity.Company;
import com.jb.coupons_project.entity.Coupon;
import com.jb.coupons_project.entity.Customer;
import com.jb.coupons_project.repository.CompanyRepository;
import com.jb.coupons_project.repository.CouponRepository;
import com.jb.coupons_project.repository.CustomerRepository;
import com.jb.coupons_project.utils.InputValidator;

@Service
@Scope("prototype")
public class AdminServiceJpaImpl extends ClientFacade implements AdminService {
	
	// admin email
	@Value("${admin.email}")
	private String ADMIN_EMAIL;
	
	// admin password
	@Value("${admin.password}")
	private String ADMIN_PASSWORD;
	
	private InputValidator inputValidator;
		
	// Operation status message - used for client feedback
	private String clientMsg;
	
	public AdminServiceJpaImpl() {
		this.clientMsg="";
	}
	
	@Autowired
	public AdminServiceJpaImpl(CompanyRepository companyRepository, 
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
	public boolean login(String email, String password) {
		if(ADMIN_EMAIL.equals(email) && ADMIN_PASSWORD.equals(password))
		{
			this.clientMsg = this.ADMIN_EMAIL + ": logged in as admin";
			return true;
		}
		else
		{
			this.clientMsg = "user " + email + " not found";
			return false;
		}
	}
	
	@Override
	public void addCompany(Company company) {
		
		// check if any company fields have invalid data
		Company validCompany = inputValidator.validateCompany(company);
		if(validCompany == null)
		{
			this.clientMsg = inputValidator.getClientMsg();
			return;
		}
		
		// check for unique company email
		Optional<Company> optionalCompany = companyRepository.findByEmail(company.getEmail());
		if(optionalCompany.isPresent())
		{
			this.clientMsg="Cannot add company: company with this email already registered";
			return;
		}
		
		// check for unique company name
		optionalCompany = companyRepository.findByName(company.getName());
		if(optionalCompany.isPresent())
		{
			this.clientMsg="Cannot add company: company with this name already registered";
			return;
		}
		
		companyRepository.save(company);
		this.clientMsg="Company added";
	}
	
	@Override
	public void addCustomer(Customer customer) {
		
		// check if any customer fields have invalid data
		Customer validCustomer = inputValidator.validateCustomer(customer);
		if(validCustomer == null)
		{
			this.clientMsg = inputValidator.getClientMsg();
			return;
		}
		
		// check for unique customer email
		Optional<Customer> optionalCustomer = customerRepository.findByEmail(customer.getEmail());
		if(optionalCustomer.isPresent())
		{
			this.clientMsg="Cannot add customer: customer with this email already registered";
			return;
		}
		
		customerRepository.save(customer);
		this.clientMsg="Customer added";
	}
	
	@Override
	public void deleteCompany(int companyID) {
		Optional<Company> optionalCompany = companyRepository.findById(companyID);
		if(optionalCompany.isPresent())
		{
			companyRepository.deleteById(companyID);
			this.clientMsg="Company deleted";
		}
		else
			this.clientMsg="Company was not found";
	}
	
	@Override
	public void deleteCustomer(int customerID) {
		
		Optional<Customer> optionalCustomer = customerRepository.findById(customerID);
		if(optionalCustomer.isPresent())
		{
			// get list of all customer coupons and update amount of each coupon by 1
			List<Coupon> customerCoupons = optionalCustomer.get().getCoupons();
			for(Coupon currCoupon : customerCoupons)
			{
				currCoupon.setAmount(currCoupon.getAmount()+1);
				couponRepository.save(currCoupon);
			}
			
			// now delete customer
			customerRepository.deleteById(customerID);
			this.clientMsg="Customer deleted";
		}
		else
			this.clientMsg="Customer was not found";	
	}
	
	@Override
	public List<Company> getAllCompanies(){
		List<Company> companies = companyRepository.findAll();
		this.clientMsg=companies.size() + " companies registered";
		return companies;
	}
	
	@Override
	public List<Customer> getAllCustomers(){
		List<Customer> customers = customerRepository.findAll();
		this.clientMsg=customers.size() + " customers registered";
		return customers;
	}
	
	@Override
	public Company getOneCompany(int companyID){
		Optional<Company> optionalCompany = companyRepository.findById(companyID);
		if( ! optionalCompany.isPresent() )
		{
			this.clientMsg="Company not found";
			return null;
		}
		return optionalCompany.get();
	}
	
	@Override
	public Customer getOneCustomer(int customerID){
		Optional<Customer> optionalCustomer = customerRepository.findById(customerID);
		if( ! optionalCustomer.isPresent() )
		{
			this.clientMsg="Customer not found";
			return null;
		}
		return optionalCustomer.get();
	}
	
	@Override
	public void updateCompany(Company company) {
		
		// check if any company fields have invalid data
		Company validCompany = inputValidator.validateCompany(company);
		if(validCompany == null)
		{
			this.clientMsg = inputValidator.getClientMsg();
			return;
		}
		
		// protect from changing company name
		Optional<Company> optionalCompany = companyRepository.findById(company.getId());
		Company oldCompany = optionalCompany.get();
		if( ! oldCompany.getName().equals(company.getName()) )
		{
			this.clientMsg="Cannot update company: cannot update company name";
			return;
		}
		
		companyRepository.save(company);
		this.clientMsg="Company updated";
	}

	@Override
	public void updateCustomer(Customer customer) {
		
		// check if any customer fields have invalid data
		Customer validCustomer = inputValidator.validateCustomer(customer);
		if(validCustomer == null)
		{
			this.clientMsg = inputValidator.getClientMsg();
			return;
		}
		
		customerRepository.save(customer);
		this.clientMsg="Customer updated";
	}
}
