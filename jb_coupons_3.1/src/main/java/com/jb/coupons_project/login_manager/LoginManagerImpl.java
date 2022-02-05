package com.jb.coupons_project.login_manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jb.coupons_project.ClientType;
import com.jb.coupons_project.service.AdminService;
import com.jb.coupons_project.service.ClientFacade;
import com.jb.coupons_project.service.CompanyService;
import com.jb.coupons_project.service.CustomerService;
import com.jb.coupons_project.utils.InputValidator;

@Component
@Scope("singleton")
public class LoginManagerImpl implements LoginManager 
{	
	// Operation status message - used for client feedback
	private String clientMsg = "";
		
	// Create validation object for user input data
	private InputValidator inputValidator;
	
	private ClientFacade clientFacade = null;;
	
	private AdminService adminService;
	private CompanyService companyService;
	private CustomerService customerService;
	
	/**
	 * Constructor
	 */
	@Autowired
	public LoginManagerImpl(InputValidator inputValidator, 
							AdminService adminService,
							CompanyService companyService,
							CustomerService customerService)
	{
		this.inputValidator = inputValidator;
		this.adminService = adminService;
		this.companyService = companyService;
		this.customerService = customerService;
	}
	
	/**
	 * This method returns client message.
	 * @return client message
	 */
	public String getClientMsg()
	{
		return this.clientMsg;
	}
	
	/**
	 * This method is used to login client into system. It checks client type
	 * and creates appropriate client facade. Then it calls login method of client facade.
	 * @param email - client email
	 * @param password - client password
	 * @param clientType - client type
	 * @return client facade object corresponding to client type if login data is valid, 
	 * or null if login data is invalid.
	 * @throws DBOperationException in case of database operation error
	 */
	public ClientFacade login(String email, String password, ClientType clientType)
	{
		if(inputValidator.validateEmail(email) == null || inputValidator.validatePassword(password) == null)
		{
			this.clientMsg = inputValidator.getClientMsg();
			return null;
		}
		switch (clientType) {
		case ADMINISTRATOR:
			clientFacade = (ClientFacade) adminService; break;
		case COMPANY:
			clientFacade = (ClientFacade) companyService; break;
		case CUSTOMER:
			clientFacade = (ClientFacade) customerService; break;
		default:
			System.out.println("Wrong client type"); return null;
		}
		
		if(clientFacade.login(email, password))
		{
			this.clientMsg = email + ": logged in as " + clientType.getClientType();
			return clientFacade;
		}
		else
		{
			this.clientMsg = "Wrong email or password";
			return null;
		}
	}
	
	/**
	 * This method is used to logout client and destroy client facade.
	 * @param clientFacade - client facade object to log out.
	 */
	public void logout(ClientFacade clientFacade)
	{
		clientFacade = null;
		this.clientMsg = "Logged out.";
	}
}
