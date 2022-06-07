package me.coupons.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.coupons.entity.Company;
import me.coupons.entity.Customer;
import me.coupons.rest.custom_exceptions.EntityRetrievalException;
import me.coupons.service.WebAwareAdminService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/admin")
public class AdminRestController {
	
	@Autowired
	private WebAwareAdminService adminService;
	
	// endpoint for POST "/companies" - add new company
	@PostMapping("/companies")
	public Company addCompany(@RequestBody Company company) {
		company.setId(0);
		this.adminService.addCompany(company);
		if(!this.adminService.getClientMsg().equals("Company added")) {
			throw new RuntimeException(adminService.getClientMsg());
		}
		return company;
	}
	
	// endpoint for POST "/customers" - add new customer
	@PostMapping("/customers")
	public Customer addCustomer(@RequestBody Customer customer) {
		customer.setId(0);
//		customerRepository.save(customer);
		this.adminService.addCustomer(customer);
		if(!this.adminService.getClientMsg().equals("Customer added")) {
			throw new RuntimeException(adminService.getClientMsg());
		}
		return customer;
	}
	
	// endpoint for DELETE /companies - delete company
	@DeleteMapping("/companies/{companyId}")
	public String deleteCompany(@PathVariable int companyId) {
		this.adminService.deleteCompany(companyId);
		if(!this.adminService.getClientMsg().equals("Company deleted")) {
			throw new RuntimeException(adminService.getClientMsg());
		}
		return "{\"Message\": \"Company deleted\"}";
	}
	
	// endpoint for DELETE /customers - delete customer
	@DeleteMapping("/customers/{customerId}")
	public String deleteCustomer(@PathVariable int customerId) {
		this.adminService.deleteCustomer(customerId);
		if(!this.adminService.getClientMsg().equals("Customer deleted")) {
			throw new RuntimeException(adminService.getClientMsg());
		}
		return "{\"Message\": \"Customer deleted\"}";
	}
	
	// endpoint for GET "/companies" - return list of companies
	@GetMapping("/companies")
	public List<Company> getCompanies() {
		List<Company> companies = this.adminService.getAllCompanies();
		return companies;
	}
	
	// endpoint for GET "/companies/{companyId}" - return company at index
	@GetMapping("/companies/{companyId}")
	public Company getCompany(@PathVariable int companyId) {
		Company company = this.adminService.getOneCompany(companyId);
		if(this.adminService.getClientMsg().equals("Company not found")) {
			throw new EntityRetrievalException(adminService.getClientMsg());
		}
		return company;
	}
	
	// endpoint for "/customers" - return list of customers
	@GetMapping("/customers")
	public List<Customer> getCustomers() {
		List<Customer> customers = this.adminService.getAllCustomers();
		return customers;
	}
	
	// endpoint for "/customers/{customerId}" - return customer at index
	@GetMapping("/customers/{customerId}")
	public Customer getCustomer(@PathVariable int customerId) {
		Customer customer = this.adminService.getOneCustomer(customerId);
		if(this.adminService.getClientMsg().equals("Custmoer not found")) {
			throw new EntityRetrievalException(adminService.getClientMsg());
		}
		return customer;
	}
	
	// endpoint for PUT /companies - update company
	@PutMapping("/companies")
	public Company updateCompany(@RequestBody Company company) {
		this.adminService.updateCompany(company);
		if(!this.adminService.getClientMsg().equals("Company updated")) {
			throw new RuntimeException(adminService.getClientMsg());
		}
		return company;
	}
	
	// endpoint for PUT /customers - update customer
	@PutMapping("/customers")
	public Customer updateCustomer(@RequestBody Customer customer) {
		this.adminService.updateCustomer(customer);
		if(!this.adminService.getClientMsg().equals("Customer updated")) {
			throw new RuntimeException(adminService.getClientMsg());
		}
		return customer;
	}
}
