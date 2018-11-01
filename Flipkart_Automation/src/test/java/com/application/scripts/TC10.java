package com.application.scripts;

import org.testng.annotations.Test;

import com.objectRepository.CommonPage;
import com.objectRepository.SearchResPage;
import com.utilities.Xls_Reader;


public class TC10 extends CommonPage{

	//Create object for excel
	Xls_Reader reader = new Xls_Reader("TestData/Data.xlsx");
	SearchResPage sp = new SearchResPage();

	//Description: Verifies the search functionality based on search criteria	
	@Test
	public void verifySearchFunctionality() throws Throwable
	{
		boolean bflag = false;
		String txtSearchTerm = reader.getCellData("Sheet2", "SearchTerm", 2); //Search term value
		try {
			type(txtSearch, txtSearchTerm, "Search textbox");
			click(btnSearch, "Search button");
			String strHeading = driver.findElement(sp.ResultsHeading).getText(); //Search results page heading
			if (strHeading.contains(txtSearchTerm)) {
				SuccessReport("Search Product", "Appropriate heading is displayed for the search criteria : " + txtSearchTerm);
			} else {
				failureReport("Search Product", "Appropriate heading is not displayed for the search criteria : " + txtSearchTerm);
			}
			bflag = true;
		} catch (Throwable e) {
			System.out.println(e.getMessage());
		}

		finally {
			if(bflag)
			{
				SuccessReport("Search Product", "Successfully searched the product with the search criteria : " + txtSearchTerm);
			}
			else
			{
				failureReport("Search Product" , "Failed searched the product with the search criteria : " + txtSearchTerm);
			}
		}

	}

}
