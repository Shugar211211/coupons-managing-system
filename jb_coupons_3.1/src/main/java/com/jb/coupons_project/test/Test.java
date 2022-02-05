package com.jb.coupons_project.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jb.coupons_project.test.console_menu.MainMenu;

@Component
public class Test 
{	
	MainMenu mainMenu;
	
	@Autowired
	public Test(MainMenu mainMenu) 
	{
		this.mainMenu = mainMenu;
	}

	/**
	 * Start interactive console menu for testing. 
	 */
	public void testAll()
	{
		try
		{	
			// all console menus
			System.out.println("\n\n*** Launching interactive menus:");
			mainMenu.loginMenu();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
//			e.printStackTrace(); // for debugging purposes
		}
	}
}
