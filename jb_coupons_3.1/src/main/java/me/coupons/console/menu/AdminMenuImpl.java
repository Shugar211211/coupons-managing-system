package me.coupons.console.menu;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import me.coupons.console.login_manager.LoginManager;
import me.coupons.console.service.AdminService;
import me.coupons.console.service.ClientFacade;
import me.coupons.entity.Company;
import me.coupons.entity.Coupon;
import me.coupons.entity.Customer;

@Component
public class AdminMenuImpl implements AdminMenu 
{
//	private ClientFacade clientFacade;
	private Scanner scanner;
	private LoginManager loginManager;
	
	@Autowired
	public AdminMenuImpl(LoginManager loginManager)
	{
		scanner = new Scanner(System.in);
		this.loginManager = loginManager;
	}
	
	/**
	 * This method gets from user choice and call corresponding method.  
	 * @param admin email
	 * @param admin password
	 */
	@Override
	public void adminMenuOptions(ClientFacade clientFacade)
	{
//		scanner = new Scanner(System.in);
		AdminService adminService = (AdminService) clientFacade;
		String userChoice = "";
		while( ! userChoice.equals("11") )
		{
			System.out.println("------------------------------------------------------------");
			System.out.println("\nAdmin menu:");
			System.out.println("\n1: Show all companies");
			System.out.println("2: See company\'s coupons");
			System.out.println("3: Add company");
			System.out.println("4: Update company");
			System.out.println("5: Delete company");
			System.out.println("\n6: Show all customers");
			System.out.println("7: See customer\'s coupons");
			System.out.println("8: Add customer");
			System.out.println("9: Update customer");
			System.out.println("10: Delete customer");
			System.out.println("\n11: Log out\n");
			System.out.println("------------------------------------------------------------");
			
			userChoice = scanner.nextLine();
			try
			{
				if(userChoice.equals("1")) {printAllCompanies(adminService);}
				if(userChoice.equals("2")) {printCompanyCoupons(adminService);}
				if(userChoice.equals("3")) {addCompany(adminService);}
				if(userChoice.equals("4")) {updateCompany(adminService);}
				if(userChoice.equals("5")) {deleteCompany(adminService);}
				if(userChoice.equals("6")) {printAllCustomers(adminService);}
				if(userChoice.equals("7")) {printCustomerCoupons(adminService);}
				if(userChoice.equals("8")) {addCustomer(adminService);}
				if(userChoice.equals("9")) {updateCustomer(adminService);}
				if(userChoice.equals("10")) {deleteCustomer(adminService);}
			}
			catch(Exception e)
			{
//				e.printStackTrace(); // used for debugging
				System.out.println(e.getMessage());
			}
		}

		loginManager.logout(clientFacade);
	}
	
	/**
	 * Add new company menu.
	 * @param adminFacade
	 * @throws DBOperationException in case of database operation error.
	 */
	private void addCompany(AdminService adminService)
	{
		
		System.out.println("Enter company name: ");
		String name = scanner.nextLine();
		System.out.println("Enter company email: ");
		String email = scanner.nextLine();
		System.out.println("Enter company password: ");
		String password = scanner.nextLine();
		Company company = new Company(name, email, password);
		
		adminService.addCompany(company);
		System.out.println(adminService.getClientMsg());
	}
	
	/**
	 * Add new customer menu.
	 * @param adminFacade
	 * @throws DBOperationException in case of database operation error.
	 */
	private void addCustomer(AdminService adminService)
	{
		
		System.out.println("Enter customer first name: ");
		String firstName = scanner.nextLine();
		System.out.println("Enter customer last name: ");
		String lastName = scanner.nextLine();
		System.out.println("Enter customer email: ");
		String email = scanner.nextLine();
		System.out.println("Enter customer password: ");
		String password = scanner.nextLine();
		Customer customer = new Customer(firstName, lastName, email, password);
		adminService.addCustomer(customer);
		System.out.println(adminService.getClientMsg());
	}
	
	/**
	 * Delete company menu.
	 * @param adminFacade
	 * @throws DBOperationException in case of database operation error.
	 */
	private void deleteCompany(AdminService adminService)
	{
		// read company id from user
		System.out.println("Enter ID of company to delete: ");
		String idStr = scanner.nextLine();
		int id;
		try
		{
			id = Integer.parseInt(idStr);
		}
		catch(NumberFormatException e)
		{
			System.out.println("Invalid input");
			return;
		}
		
		// check if company with given id exists in database
		Company company = adminService.getOneCompany(id);
		if(company == null)
		{
			System.out.println("Company with this ID was not found");
			return;
		}
		
		// confirmation dialogue
		System.out.println(company);
		System.out.println("DELETE THIS COMPANY? Y/N");
		String ans = scanner.nextLine();
		
		// delete company
		if(ans.equalsIgnoreCase("Y"))
		{
			adminService.deleteCompany(id);
			System.out.println(adminService.getClientMsg());
		}
		else
			return;
	}
	
	/**
	 * Delete customer menu.
	 * @param adminFacade
	 * @throws DBOperationException in case of database operation error.
	 */
	private void deleteCustomer(AdminService adminFacade)
	{
		System.out.println("Enter ID of customer to delete: ");
		String idStr = scanner.nextLine();
		int id;
		try
		{
			id = Integer.parseInt(idStr);
		}
		catch(NumberFormatException e)
		{
			System.out.println("Invalid input");
			return;
		}
		Customer customer= adminFacade.getOneCustomer(id);
		if(customer == null)
		{
			System.out.println("Customer with this ID was not found");
			return;
		}
		System.out.println(customer);
		System.out.println("DELETE THIS CUSTOMER? Y/N");
		String ans = scanner.nextLine();
		if(ans.equalsIgnoreCase("Y"))
		{
			adminFacade.deleteCustomer(id);
			System.out.println(adminFacade.getClientMsg());
		}
		else
			return;
	}
	
	/**
	 * Print all companies.
	 * @param adminService
	 * @throws DBOperationException
	 */
	private void printAllCompanies(AdminService adminService)
	{
		List<Company> companies = adminService.getAllCompanies();
		System.out.println(companies.size()+" companies registered");
		for(Company currCompany : companies)
			System.out.println("    id: "+currCompany.getId()
							+", name: "+currCompany.getName()
							+", email: "+currCompany.getEmail()
							+", password: "+currCompany.getPassword());
	}
	
	/**
	 * Print all customers menu.
	 * @param adminFacade
	 * @throws DBOperationException in case of database operation error.
	 */
	private void printAllCustomers(AdminService adminService)
	{
		List<Customer> customers = adminService.getAllCustomers();
		System.out.println(customers.size()+" customers registered");
		for(Customer currCustomer : customers)
			System.out.println("\t"+currCustomer);
	}
	
	/**
	 * Print company coupons.
	 * @param adminFacade
	 * @throws DBCompanyOperationException 
	 */
	private void printCompanyCoupons(AdminService adminService)
	{
		System.out.println("Enter company ID to see its coupons: ");
		String idStr = scanner.nextLine();
		int id = Integer.parseInt(idStr);
		
		Company company= adminService.getOneCompany(id);
		if(company == null)
		{
			System.out.println(adminService.getClientMsg());
			return;
		}
		
		List<Coupon> coupons = company.getCoupons();
		System.out.println(company);
		System.out.println(coupons.size() + " coupons found ");
		for(Coupon currCoupon : coupons)
		{
			System.out.println("  id: "+currCoupon.getId()
							+", category: "+currCoupon.getCategory().getCategoryDescription()
							+", title: "+currCoupon.getTitle()
							+", description: "+currCoupon.getDescription()
							+", start date: "+currCoupon.getStartDate()
							+", end date: "+currCoupon.getEndDate()
							+", amount: "+currCoupon.getAmount()
							+", price: "+currCoupon.getPrice());
		}
	}
	
	/**
	 * Print all coupons of a customer menu.
	 * @param adminFacade
	 * @throws DBCustomerOperationException in case of database operation error.
	 */
	private void printCustomerCoupons(AdminService adminService)
	{
		System.out.println("Enter customer ID to see his coupons: ");
		String idStr = scanner.nextLine();
		int id = Integer.parseInt(idStr);
		Customer customer= adminService.getOneCustomer(id);
		if(customer == null)
		{
			System.out.println(adminService.getClientMsg());
			return;
		}
		List<Coupon> coupons = customer.getCoupons();
		System.out.println(coupons.size() + " coupons found ");
		for(Coupon currCoupon : coupons)
		{
			System.out.println("  id: "+currCoupon.getId()
							+", category: "+currCoupon.getCategory().getCategoryDescription()
							+", title: "+currCoupon.getTitle()
							+", description: "+currCoupon.getDescription()
							+", start date: "+currCoupon.getStartDate()
							+", end date: "+currCoupon.getEndDate()
							+", amount: "+currCoupon.getAmount()
							+", price: "+currCoupon.getPrice());
		}
	}
	
	/**
	 * Update company menu.
	 * @param adminFacade
	 * @throws DBOperationException in case of database operation error.
	 */
	private void updateCompany(AdminService adminService)
	{
		System.out.println("Enter ID of company to update: ");
		String idStr = scanner.nextLine();
		int id = Integer.parseInt(idStr);
		Company company = adminService.getOneCompany(id);
		if(company == null)
		{
			System.out.println("Company with this ID was not found");
			return;
		}
		System.out.println(company);
		System.out.println("Enter new email: ");
		String email = scanner.nextLine();
		System.out.println("Enter new password: ");
		String password = scanner.nextLine();
		company.setEmail(email);
		company.setPassword(password);
		
		adminService.updateCompany(company);
		System.out.println(adminService.getClientMsg());
	}
	
	/**
	 * Update customer menu.
	 * @param adminFacade
	 * @throws DBOperationException in case of database operation error.
	 */
	private void updateCustomer(AdminService adminService)
	{
		// read from user id of customer to update
		System.out.println("Enter ID of customer to update: ");
		String idStr = scanner.nextLine();
		int id;
		try
		{
			id = Integer.parseInt(idStr);
		}
		catch(NumberFormatException e)
		{
			System.out.println("Invalid input");
			return;
		}
		
		// retrieve the customer by id
		Customer customer = adminService.getOneCustomer(id);
		if(customer == null)
		{
			System.out.println("Customer with this ID was not found");
			return;
		}
		
		// read new data from user
		System.out.println(customer);
		System.out.println("Enter new first name: ");
		String firstName = scanner.nextLine();
		System.out.println("Enter new last name: ");
		String lastName = scanner.nextLine();
		System.out.println("Enter new email: ");
		String email = scanner.nextLine();
		System.out.println("Enter new password: ");
		String password = scanner.nextLine();
		
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setEmail(email);
		customer.setPassword(password);
		
		// update the customer
		adminService.updateCustomer(customer);
		System.out.println(adminService.getClientMsg());
	}
}
