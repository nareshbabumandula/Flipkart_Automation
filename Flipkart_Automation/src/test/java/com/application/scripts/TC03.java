package com.application.scripts;

import org.testng.annotations.Test;

import com.objectRepository.CommonPage;
import com.objectRepository.Homepage;


public class TC03 extends Homepage {
	
	CommonPage cp = new CommonPage();
	
	@Test
	public void testAddToCart() throws Throwable
	{
		type(txtSearch, "mobiles", "Search Textbox");
		click(cp.btnSearch, "Search button");
	}

	public static void main(String[] args) {
		System.out.println("Executing AddToCart method test");
	}

}
