package me.coupons.service.auth_service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import me.coupons.auth.models.Principal;
import me.coupons.auth.models.UserDetails;
import me.coupons.entity.ClientType;
import me.coupons.entity.Company;
import me.coupons.entity.Customer;
import me.coupons.repository.CompanyRepository;
import me.coupons.repository.CustomerRepository;
import me.coupons.rest.custom_exceptions.WrongCredentialsException;

@Service
public class AuthServiceImpl implements AuthService {

	private String admin_email;
	private String admin_password;
	private CompanyRepository companyRepository;
	private CustomerRepository customerRepository;
	
	@Autowired
	public AuthServiceImpl(@Value("${admin.email}")String admin_email, 
						   @Value("${admin.password}")String admin_password, 
						   CompanyRepository companyRepository,
						   CustomerRepository customerRepository) {
		this.admin_email = admin_email;
		this.admin_password = admin_password;
		this.companyRepository = companyRepository;
		this.customerRepository = customerRepository;
	}

	@Override
	public Principal authenticate(UserDetails userDetails) {
		
		ClientType role = userDetails.getClientType();
		
		switch (role) {
		case ADMINISTRATOR: 
			return this.authAdmin(userDetails);
		case COMPANY: 
			return this.authCompany(userDetails);
		case CUSTOMER:
			return this.authCustomer(userDetails);
		default: throw new IllegalArgumentException("Unexpected role: " + role);
		}
	}

	private Principal authAdmin(UserDetails userDetails) {
		if(userDetails.getEmail().equals(this.admin_email) && userDetails.getPassword().equals(this.admin_password)) {
			return new Principal(0, "Administrator", ClientType.ADMINISTRATOR);
		}
		else { throw new WrongCredentialsException("Wrong email or password"); }
	}
	
	private Principal authCompany(UserDetails userDetails) {
		Optional<Company> optionalCompany = 
				companyRepository.findByEmailAndPassword(userDetails.getEmail(), userDetails.getPassword());
		if(optionalCompany.isPresent())
		{
			Company thisCompany = optionalCompany.get();
			return new Principal(thisCompany.getId(), thisCompany.getName(), ClientType.COMPANY);
		}
		else { throw new WrongCredentialsException("Wrong email or password"); }
	}
	
	private Principal authCustomer(UserDetails userDetails) {
		Optional<Customer> optionalCustomer = 
				customerRepository.findByEmailAndPassword(userDetails.getEmail(), userDetails.getPassword());
		if(optionalCustomer.isPresent())
		{
			Customer thisCustomer = optionalCustomer.get();
			return new Principal(thisCustomer.getId(), 
								 thisCustomer.getFirstName().concat(" ").concat(thisCustomer.getLastName()), 
								 ClientType.CUSTOMER);
		}
		else { throw new WrongCredentialsException("Wrong email or password"); }
	}
}
