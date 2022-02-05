package com.jb.coupons_project.test.console_menu;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jb.coupons_project.ClientType;
import com.jb.coupons_project.entity.Category;
import com.jb.coupons_project.entity.Company;
import com.jb.coupons_project.entity.Coupon;
import com.jb.coupons_project.entity.Customer;
import com.jb.coupons_project.login_manager.LoginManager;
import com.jb.coupons_project.service.AdminService;
import com.jb.coupons_project.service.ClientFacade;
import com.jb.coupons_project.service.CustomerService;

@Component
public class CustomerMenuImpl implements CustomerMenu 
{
//	private ClientFacade clientFacade;
	private Scanner scanner;
	private LoginManager loginManager;
	private AdminService adminService;
	
	@Autowired
	public CustomerMenuImpl(LoginManager loginManager, AdminService adminService)
	{
		scanner = new Scanner(System.in);
		this.loginManager = loginManager;
		this.adminService = adminService;
	}
	
	/**
	 * This method gets from user choice and call corresponding method. 
	 * @param customer email
	 * @param customer password
	 * @throws InvalidInputException in case of invalid input.
	 */
	@Override
	public void customerMenuOptions(ClientFacade clientService)
	{
		CustomerService customerService = (CustomerService) clientService;
		String userChoice = "";
		while( ! userChoice.equals("11") )
		{
			System.out.println("------------------------------------------------------------");
			System.out.println("\nCustomer menu:");
			System.out.println("\n1: Show customer details");
			System.out.println("2: Show all coupons");
			System.out.println("3: Filter coupons by category ");
			System.out.println("4: Filter coupons by maximum price ");
			System.out.println("5: Buy coupon");
			System.out.println("6: Delete coupon purchase");
			System.out.println("\n11: Log out\n");
			System.out.println("------------------------------------------------------------");
			
			userChoice = scanner.nextLine();
			
			try
			{
				if(userChoice.equals("1")) {printCustomerDetails(customerService);}
				if(userChoice.equals("2")) {printCustomerCoupons(customerService);}
				if(userChoice.equals("3")) {printCustomerCouponsByCategory(customerService);}
				if(userChoice.equals("4")) {printCustomerCouponsByMaxPrice(customerService);}
				if(userChoice.equals("5")) {buyCoupon(customerService);}
				if(userChoice.equals("6")) {deletePurchase(customerService);}
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			
		}
		loginManager.logout(clientService);
		System.out.println(loginManager.getClientMsg());
	}
	
	/**
	 * Delete coupon purchase menu.
	 * @param customerFacade object.
	 * @ in case of database operation.
	 */
	private void deletePurchase(CustomerService customerService) 
	{
		System.out.println("To delete coupon purchase enter coupon ID");
		String idStr = scanner.nextLine();
		int id;
		try
		{
			id=Integer.parseInt(idStr);
		}
		catch(NumberFormatException e)
		{
			System.out.println("Invalid id");
			return;
		}
		
		// retrieve all coupons of this customer
		List<Coupon> coupons = customerService.getCustomerCoupons();
		Coupon coupon = null;
		
		// find coupon whose purchase to be deleted
		for(Coupon tempCoupon : coupons)
			if(tempCoupon.getId() == id)
			{
				coupon = tempCoupon;
				break;
			}
		
		// delete purchase of the coupon
		if(coupon != null)
		{
			customerService.deletePurchase(coupon);
			System.out.println(customerService.getClientMsg());
		}
		else
			System.out.println("Customer dors not have coupon with this id");
	}
	
	/**
	 * Buy coupon menu.
	 * @param customerFacade object.
	 * @ in case of database operation.
	 */
	private void buyCoupon(CustomerService customerService) 
	{
		// log in as admin to retrieve list of all companies
		System.out.println("------------------------------------------------------------");
		System.out.println("Awailable coupons:");
		adminService = (AdminService)loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
		List<Company> companies;
		try 
		{
			companies = adminService.getAllCompanies();
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
			return;
		}
		
		// print all coupons for each company in the list
		for(Company currCompany : companies)
			for(Coupon currCoupon : currCompany.getCoupons())
				System.out.println(currCoupon);
		
		// read from user id of coupon to purchase
		System.out.println("------------------------------------------------------------");
		System.out.println("Enter coupon ID to purchase coupon");
		String idStr = scanner.nextLine();
		int id;
		try
		{
			id=Integer.parseInt(idStr);
		}
		catch(NumberFormatException e)
		{
			System.out.println("Invalid id");
			return;
		}
		
		// find coupon with given id
		Coupon coupon = null;
		for(Company currCompany : companies)
			for(Coupon currCoupon : currCompany.getCoupons())
				if(id == currCoupon.getId())
				{
					coupon = currCoupon;
					break;
				}
		
		// if coupon with given id found, make purchase
		if(coupon != null)
		{
			customerService.purchaseCoupon(coupon);
			System.out.println("Coupon purchased");
		}
		else
			System.out.println("Coupon not found");
	}
	
	/**
	 * This method read from customer maximum price 
	 * and prints all customer coupons under this price.
	 * @param customerFacade object.
	 * @ in case of database operation.
	 */
	private void printCustomerCouponsByMaxPrice(CustomerService customerService) 
						
	{
		List<Coupon> coupons;
		System.out.println("------------------------------------------------------------");
		System.out.println("Enter maximum price to filter coupons:");
		String maxPriceStr = scanner.nextLine();
		double maxPrice;
		try
		{
			maxPrice = Double.parseDouble(maxPriceStr);
		}
		catch(NumberFormatException e)
		{
			System.out.println("Invalid input");
			return;
		}
		coupons = customerService.getCustomerCoupons(maxPrice);
		System.out.println(customerService.getClientMsg());
		if(coupons == null)
		{
			System.out.println("Error retrieving coupons");
			return;
		}
		if(coupons.size() == 0)
		{
			System.out.println("This customer has no coupons below this price");
			return;
		}
//		System.out.println(coupons.size()+" coupons found for this customer below this price");
		for(Coupon currCoupon : coupons)
			System.out.println(currCoupon);
	}
	
	/**
	 * This method read from customer category 
	 * and prints all customer coupons in this category.
	 * @param customerFacade object.
	 * @ in case of database operation.
	 */
	private void printCustomerCouponsByCategory(CustomerService customerService) 
						
	{
		Category category = this.getCategory();
		List<Coupon> coupons = customerService.getCustomerCoupons(category);
//		System.out.println(customerService.getClientMsg());
		if(coupons == null)
		{
			System.out.println("Error retrieving coupons");
			return;
		}
		if(coupons.size() == 0)
		{
			System.out.println("This customer has no coupons in the chosen category");
			return;
		}
//		System.out.println(coupons.size()+" coupons found for this customer int chosen category");
		for(Coupon currCoupon : coupons)
			System.out.println(currCoupon);
	}
	
	/**
	 * This method prints all coupons that belong to this customer.
	 * @param customerFacade object.
	 * @ in case of database operation.
	 */
	private void printCustomerCoupons(CustomerService customerService) 
						
	{
		List<Coupon> coupons = customerService.getCustomerCoupons();
		System.out.println(customerService.getClientMsg());
		if(coupons == null)
		{
			System.out.println("Error retrieving coupons");
			return;
		}
		if(coupons.size() == 0)
		{
			System.out.println("This customer has no coupons");
			return;
		}
		System.out.println(coupons.size()+" coupons found for this customer");
		for(Coupon currCoupon : coupons)
			System.out.println(currCoupon);
	}
	
	/**
	 * This method prints customer details.
	 * @param customerFacade object.
	 * @ in case of database operation.
	 */
	private void printCustomerDetails(CustomerService customerService) 
						
	{
		Customer customer = customerService.getCustomerDetails();
		if(customer == null)
		{
			System.out.print(customerService.getClientMsg());
			return;
		}
		System.out.println(customer);
	}
	
	/**
	 * This method reads from user category choice.
	 * @return category.
	 */
	private Category getCategory()
	{
		System.out.println("------------------------------------------------------------");
		System.out.println("Choose category:");
		int i = 1;
		for(Category category : Category.values())
		{
			System.out.println(i + ": "+category.getCategoryDescription());
			i++;
		}
		System.out.println("------------------------------------------------------------");
		String userChoiceStr = scanner.nextLine();
		int userChoice;
		try
		{
			userChoice = Integer.parseInt(userChoiceStr);
		}
		catch(NumberFormatException e)
		{
			System.out.println("Invalid input");
			return null;
		}
		
		if(userChoice<1 || userChoice>25)
		{
//			System.out.println("Invalid category code");
			return null;
		}
		Category category;
		switch (userChoice) 
		{
			case 1: category = Category.ARTS; break;
			case 2: category = Category.AUTOMOTIVE; break;
			case 3: category = Category.BABY; break;
			case 4: category = Category.BEAUTY; break;
			case 5: category = Category.BOOKS; break;
			case 6: category = Category.COMPUTERS; break;
			case 7: category = Category.CLOTHING;break;
			case 8: category = Category.ELECTRONICS; break;
			case 9: category = Category.FASHION; break;
			case 10: category = Category.FINANCE; break;
			case 11: category = Category.FOOD; break;
			case 12: category = Category.HEALTH; break;
			case 13: category = Category.HOME; break;
			case 14: category = Category.LIFESTYLE; break;
			case 15: category = Category.MOVIES; break;
			case 16: category = Category.MUSIC; break;
			case 17: category = Category.OUTDOORS; break;
			case 18: category = Category.PETS; break;
			case 19: category = Category.RESTAURANTS; break;
			case 20: category = Category.SHOES; break;
			case 21: category = Category.SOFTWARE; break;
			case 22: category = Category.SPORTS; break;
			case 23: category = Category.TOOLS; break;
			case 24: category = Category.TRAVEL; break;
			case 25: category = Category.VIDEOGAMES; break;
			default: category = null;System.out.println("No such category"); break;
		}
		return category;
	}
}
