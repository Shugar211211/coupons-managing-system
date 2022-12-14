package me.coupons.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import me.coupons.entity.Company;
import me.coupons.entity.Coupon;
import me.coupons.entity.Customer;
import me.coupons.repository.CompanyRepository;
import me.coupons.repository.CouponRepository;
import me.coupons.repository.CustomerRepository;
import me.coupons.utils.InputValidator;

@Service
@RequestScope
public class AdminServiceJpaImpl implements AdminService{
	
	// admin email
	@Value("${admin.email}")
	private String ADMIN_EMAIL;
	
	// admin password
	@Value("${admin.password}")
	private String ADMIN_PASSWORD;
	
	@Autowired
	private CouponRepository couponRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	private String clientMsg="";
	
	@Autowired
	private InputValidator inputValidator;
	
	@Autowired
	private PasswordEncoder argon2PasswordEncoderExtension;
	
	@Override
	public String getClientMsg() {
		return clientMsg;
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
		
		String encryptedPassword = argon2PasswordEncoderExtension.encode(company.getPassword());
		company.setPassword(encryptedPassword);
		
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
		
		String encryptedPassword = argon2PasswordEncoderExtension.encode(customer.getPassword());
		customer.setPassword(encryptedPassword);
		
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
		
		// if company object has password field and it is not empty, then update company
		String password = company.getPassword();
		if(password != null && !password.equals("")) {		
			// check if any company fields have invalid data
			Company validCompany = inputValidator.validateCompany(company);
			if(validCompany == null) {
				this.clientMsg = inputValidator.getClientMsg();
				return;
			}
			company.setPassword(argon2PasswordEncoderExtension.encode(password));
			companyRepository.save(company);
		}
		// if password field is null or is empty string, then update all fields except password
		else {
			// validate email field of company object
			if(inputValidator.validateEmail(company.getEmail()) == null) {
				this.clientMsg = inputValidator.getClientMsg();
				return;
			}	
			// validate name field of company object
			if(inputValidator.validateName(company.getName()) == null) {
				this.clientMsg = inputValidator.getClientMsg();
				return;
			}
			// update company info except password
			companyRepository.updateCompanySkipPassword(company);
		}
		this.clientMsg="Company updated";
	}

	@Override
	public void updateCustomer(Customer customer) {
		
		// if customer object has password field and it is not empty, then update customer
		String password = customer.getPassword();
		if(password != null && !password.equals("")) {
			// check if any customer fields have invalid data
			Customer validCustomer = inputValidator.validateCustomer(customer);
			if(validCustomer == null) {
				this.clientMsg = inputValidator.getClientMsg();
				return;
			}
			customer.setPassword(argon2PasswordEncoderExtension.encode(password));
			customerRepository.save(customer);
		}
		// if password field is null or is empty string, then update all fields except password
		else {
			// validate email field of customer object
			if(inputValidator.validateEmail(customer.getEmail()) == null) {
				this.clientMsg = inputValidator.getClientMsg();
				return;
			}	
			// validate first name field of customer object
			if(inputValidator.validateName(customer.getFirstName()) == null) {
				this.clientMsg = inputValidator.getClientMsg();
				return;
			}
			// validate last name field of customer object
			if(inputValidator.validateName(customer.getLastName()) == null) {
				this.clientMsg = inputValidator.getClientMsg();
				return;
			}
			// update customer info except password
			customerRepository.updateCustomerSkipPassword(customer);
		}
		this.clientMsg="Customer updated";
	}
}
