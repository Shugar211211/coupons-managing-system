package me.coupons.console.menu;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import me.coupons.console.login_manager.LoginManager;
import me.coupons.console.service.ClientFacade;
import me.coupons.console.service.CompanyService;
import me.coupons.entity.Category;
import me.coupons.entity.Company;
import me.coupons.entity.Coupon;

@Component
public class CompanyMenuImpl implements CompanyMenu 
{
//	private ClientFacade clientFacade;
	private Scanner scanner;
	private LoginManager loginManager;
	
	@Autowired
	public CompanyMenuImpl(LoginManager loginManager)
	{
		scanner = new Scanner(System.in);
		this.loginManager = loginManager;
	}
	
	/**
	 * Company operations menu
	 * @param company email
	 * @param compnay password
	 * @throws InvalidInputException in case of database operation error. 
	 */
	@Override
	public void companyMenuOptions(ClientFacade clientFacade)
	{
		CompanyService companyService = (CompanyService) clientFacade;
		String userChoice = "";
		while( ! userChoice.equals("11") )
		{
			System.out.println("------------------------------------------------------------");
			System.out.println("\nCompany menu:");
			System.out.println("\n1: Show company details");
			System.out.println("2: Show all coupons");
			System.out.println("3: Filter coupons by category");
			System.out.println("4: Filter coupons by maximum price ");
			System.out.println("5: Add new coupon");
			System.out.println("6: Edit coupon");
			System.out.println("7: Delete coupon");
			System.out.println("\n11: Log out\n");
			System.out.println("------------------------------------------------------------");
			
			userChoice = scanner.nextLine();
			
			try
			{
				if(userChoice.equals("1")) {printCompanyDetails(companyService);}
				if(userChoice.equals("2")) {printCompanyCoupons(companyService);}
				if(userChoice.equals("3")) {printCompanyCouponsByCategory(companyService);}
				if(userChoice.equals("4")) {printCompanyCouponsByMaxPrice(companyService);}
				if(userChoice.equals("5")) {addCoupon(companyService);}
				if(userChoice.equals("6")) {editCoupon(companyService);}
				if(userChoice.equals("7")) {deleteCoupon(companyService);}
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
		}
		
		loginManager.logout(clientFacade);
		System.out.println(loginManager.getClientMsg());
	}
	
	private Category getCategory()
	{
		System.out.println("------------------------------------------------------------");
		System.out.println("Choose category:\n");
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
	
	/**
	 * Helper method that reads coupon from user.
	 * @param companyFacade object.
	 * @return coupon object
	 * @ in case of database operation error.
	 */
	private Coupon readCouponFromUser(CompanyService companyService) 
					
	{
		Category category = getCategory();
		if(category == null)
			return null;
		System.out.println("Enter title");
		String title = scanner.nextLine();
		System.out.println("Enter description");
		String description = scanner.nextLine();
		System.out.println("Enter start date formatted as yyyy-MM-dd");
		String startDateStr = scanner.nextLine();
		Date startDate;
		try
		{
			startDate= Date.valueOf(startDateStr);
		}
		catch(IllegalArgumentException e)
		{
			System.out.println("Invalid start date entered");
			return null;
		}
		System.out.println("Enter end date formatted as yyyy-MM-dd");
		String endDateStr = scanner.nextLine();
		Date endDate;
		try
		{
			endDate= Date.valueOf(endDateStr);
		}
		catch(IllegalArgumentException e)
		{
			System.out.println("Invalid end date entered");
			return null;
		}
		System.out.println("Enter amount");
		String amountStr = scanner.nextLine();
		int amount;
		try
		{
			amount = Integer.parseInt(amountStr);
		}
		catch(NumberFormatException e)
		{
			System.out.println("Invalid input");
			return null;
		}
		System.out.println("Enter price");
		String priceStr = scanner.nextLine();
		double price;
		try
		{
			price = Double.parseDouble(priceStr);
		}
		catch(NumberFormatException e)
		{
			System.out.println("Invalid input");
			return null;
		}
		System.out.println("Enter link to coupon image (optional)");
		String imageLink = scanner.nextLine();
		
		Coupon coupon = new Coupon(companyService.getCompanyDetails(),
				   category,
				   title,
				   description,
				   startDate,
				   endDate,
				   amount,
				   price,
				   imageLink);
		
		return coupon;
	}
	
	/**
	 * Delete coupon menu.
	 * @param companyFacade object.
	 * @ in case of database operation error.
	 */
	private void deleteCoupon(CompanyService companyFacade) 
					
	{
		System.out.println("Enter id of coupon to delete");
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
		List<Coupon> coupons = companyFacade.getCompanyCoupons();
		for(Coupon currCoupon : coupons)
			if(id == currCoupon.getId())
			{
				System.out.println(currCoupon);
				System.out.println("Delete this coupon? Y/N");
				if(scanner.nextLine().equalsIgnoreCase("Y"))
				{
					companyFacade.deleteCoupon(id);
					System.out.println(companyFacade.getClientMsg());
				}
				else
				{
					System.out.println("Cancelled");
				}
				return;
			}
		System.out.println("Coupon with this id not found for the company");
	}
	
	/**
	 * Edit coupon menu.
	 * @param companyFacade object.
	 * @ in case of database operation error.
	 */
	private void editCoupon(CompanyService companyService) 
					
	{
		// read from user id of coupon to edit
		System.out.println("Enter id of coupon to edit");
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
		
		// retrieve all coupons for this company
		List<Coupon> coupons = companyService.getCompanyCoupons();
		for(Coupon currCoupon : coupons)
			// if found coupon with given id, read new data from user and update this coupon  
			if(id == currCoupon.getId())
			{
				System.out.println(currCoupon);
				Coupon coupon = this.readCouponFromUser(companyService);
				if(coupon != null)
				{
					// set the chosen id to the coupon in order to update and not to create a new one
					coupon.setId(id);
					companyService.updateCoupon(coupon);
					System.out.println(companyService.getClientMsg());
				}
				return;
			}
		System.out.println("Coupon with this id not found for the company");
	}
	
	/**
	 * Add new coupon menu.
	 * @param companyFacade object.
	 * @ in case of database operation error.
	 */
	private void addCoupon(CompanyService companyService) 
	{
		Coupon coupon = this.readCouponFromUser(companyService);
		if(coupon != null)
		{
			companyService.addCoupon(coupon);
			System.out.println(companyService.getClientMsg());
		}
	}
	
	/**
	 * This method reads from user max price and prints all company coupons below this price.
	 * @param companyFacade object.
	 * @throws InvalidInputException in case of invalid input.
	 * @ in case of database operation error.
	 */
	private void printCompanyCouponsByMaxPrice(CompanyService companyService) 
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
			System.out.println("Invalid input data.");
			return;
		}
		coupons = companyService.getCompanyCoupons(maxPrice);
		System.out.println("------------------------------------------------------------");
		System.out.println(companyService.getClientMsg());
		if(coupons == null)
			return;
		for(Coupon currCoupon : coupons)
			System.out.println(currCoupon);
	}
	
	/**
	 * This method reads from user category and prints all company coupons in this category. 
	 * @param companyFacade object.
	 * @throws InvalidInputException in case of invalid input.
	 * @ in case of database operation error.
	 */
	private void printCompanyCouponsByCategory(CompanyService companyService) 
	{
		Category category = this.getCategory();
		
		List<Coupon> coupons = companyService.getCompanyCoupons(category);
		if(coupons == null)
		{
			System.out.println(companyService.getClientMsg());
			return;
		}
		for(Coupon currCoupon : coupons)
			System.out.println(currCoupon);
		System.out.println("------------------------------------------------------------");
	}
	
	/**
	 * This method prints all coupons that belong to this company. 
	 * @param companyFacade object.
	 * @ in case of database operation error.
	 */
	private void printCompanyCoupons(CompanyService companyService) 
					
	{
		List<Coupon> coupons = companyService.getCompanyCoupons();
		if(coupons == null)
		{
			System.out.println("Error retrieving coupons");
			return;
		}
		if(coupons.size() == 0)
		{
			System.out.println("This company has no coupons");
			return;
		}
		System.out.println(coupons.size()+" coupons found for this company");
		for(Coupon currCoupon : coupons)
			System.out.println(currCoupon);
	}
	
	/**
	 * This method prints all company details.
	 * @param companyFacade object.
	 * @ in case of database operation error.
	 */
	private void printCompanyDetails(CompanyService companyService) 
					
	{
		Company company = companyService.getCompanyDetails();
		if(company == null)
		{
			System.out.print(companyService.getClientMsg());
			return;
		}
		System.out.println(company);
	}
}
