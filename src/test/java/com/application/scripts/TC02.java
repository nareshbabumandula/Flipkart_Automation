package com.application.scripts;

import org.testng.annotations.Test;

import com.accelerators.ActionEngine;
import com.objectRepository.CommonPage;

public class TC02 extends CommonPage{

	@Test
	public void testTC02() throws Throwable {
		System.out.println("Executing TC02...");
		SuccessReport("Logout", "Successfully logged into the application");
		failureReport("Logout", "Failed to logout from the application");
		isVisible(btnSearch, "Search button");
	}

}
