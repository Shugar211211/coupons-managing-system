package com.jb.coupons_project.test.console_menu;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jb.coupons_project.ClientType;
import com.jb.coupons_project.login_manager.LoginManager;
import com.jb.coupons_project.service.ClientFacade;

@Component
public class MainMenuImpl implements MainMenu
{
	private LoginManager loginManager;
	private ClientType clientType;
	private ClientFacade clientFacade;
	private Scanner scanner = new Scanner(System.in);
	private AdminMenu adminMenu;
	private CompanyMenu companyMenu;
	private CustomerMenu customerMenu;
	
	/**
	 * Constructor
	 */
	@Autowired
	public MainMenuImpl(LoginManager loginManager, 
						AdminMenu adminMenu, 
						CompanyMenu companyMenu, 
						CustomerMenu customerMenu)
	{
		this.loginManager = loginManager;
		clientType = null;
		this.adminMenu = adminMenu;
		this.companyMenu = companyMenu;
		this.customerMenu = customerMenu;
//		clientFacade = null;
	}
	
	/**
	 * This method scans user login details and calls client menu according to client type.
	 * @throws InvalidInputException in case of invalid user input.
	 */
	public void loginMenu()
	{
//		scanner = new Scanner(System.in);
		String loginType = "";
		System.out.println("\nWellcome to JB_Coupons test menu");
		while( ! loginType.equals("4") )
		{
			System.out.println("\n------------------------------------------------------------");
			System.out.println("Login menu:");
			System.out.println("\n1: login as administrator (email: admin@admin.com, pass: admin)");
			System.out.println("2: login as company");
			System.out.println("3: login as customer");
			System.out.println("4: quit application");
			System.out.println("------------------------------------------------------------");
			loginType = scanner.nextLine();
			if(loginType.equals("4"))
			{
				System.out.println("Bye");
				scanner.close();
				return;
			}
			else if(loginType.equals("1"))
				clientType = ClientType.ADMINISTRATOR;
			else if(loginType.equals("2"))
				clientType = ClientType.COMPANY;
			else if(loginType.equals("3"))
				clientType = ClientType.CUSTOMER;
			else
			{
				System.out.println("Invalid input");
				continue;
			}
			
			// obtain login credentials from user
			System.out.println("Enter email: ");
			String email = scanner.nextLine();
			System.out.println("Enter password: ");
			String password = scanner.nextLine();
			
			// check login credentials
			try 
			{
				clientFacade = loginManager.login(email, password, clientType);
			} 
			catch (Exception e) 
			{
				System.out.println(e.getMessage());
			}
			
			if(clientFacade == null)
			{
				System.out.println(loginManager.getClientMsg());
			}
			else if(clientType == ClientType.ADMINISTRATOR)
			{
				System.out.println(loginManager.getClientMsg());
				adminMenu.adminMenuOptions(clientFacade);
			}
			else if(clientType == ClientType.COMPANY)
			{
				System.out.println(loginManager.getClientMsg());
				companyMenu.companyMenuOptions(clientFacade);
			}
			else// if(clientType == ClientType.CUSTOMER)
			{
				System.out.println(loginManager.getClientMsg());
				customerMenu.customerMenuOptions(clientFacade);
			}
		}
	}
}
