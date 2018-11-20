package com.application.scripts;

import org.testng.annotations.Test;

import com.objectRepository.CommonPage;
import com.objectRepository.Homepage;
import com.objectRepository.SearchResPage;
import com.objectRepository.ShoppingCart;
import com.utilities.Xls_Reader;

public class TC11 extends CommonPage{
	
	//Create object for excel
	Xls_Reader reader = new Xls_Reader("TestData/Data.xlsx");
	
	ShoppingCart sc = new ShoppingCart();
	Homepage hp = new Homepage();
	SearchResPage sp = new SearchResPage();
	
	
	@Test
	public void testTC11()
	{
		String strProduct = reader.getCellData("Search", "PRODUCT_NAME", 2);
		try {
			type(txtSearch, strProduct, "Search textbox");
			click(btnSearch, "Search button"); //Click on Search button
			
			//Explicit wait statement
			waitForElementPresent(sp.strHeading, "Search Results Heading");

			String strResultsPageTitle = driver.getTitle(); // Retrieve page title
			// Verify page title
			if (strResultsPageTitle.contains(strProduct)) {
				SuccessReport("Verify Search Results Page Title", "Appropriate page title - " + strResultsPageTitle + " is displayed for the searched product '" + strProduct + "'");
			} else {
				failureReport("Verify Search Results Page Title", "Appropriate page title - " + strResultsPageTitle + " is not displayed for the product searched '" + strProduct + "'");
			}
			
			click(sp.imgProduct, "Product Image"); //Click on product image
			switchToNewWindow();
			click(sc.btnAddToCart, "ADD TO CART"); //Click on ADDTOCART button
			} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
