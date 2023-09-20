package com.application.scripts;

import org.testng.annotations.Test;
import com.objectRepository.CommonPage;
import com.objectRepository.Homepage;
import com.objectRepository.SearchResPage;
import com.utilities.Xls_Reader;

public class TC01 extends CommonPage
{
	//Create object for excel sheet
	Xls_Reader reader = new Xls_Reader("TestData/Data.xlsx");
	
	//Create object for excel sheet
	SearchResPage sp = new SearchResPage();
	Homepage hp = new Homepage();
	
	
	@Test
	public void testTC01() throws Throwable
	{
		String txtSearchTerm = reader.getCellData("Sheet1", "SearchTerm", 2);
		String txtCategory = reader.getCellData("Sheet1", "Category", 2);
		waitForVisibilityOfElement(txtSearch, "Search Textbox"); //Wait for Search textbox
		
		//Enter text in Search text box
		type(txtSearch, txtSearchTerm, "Product Search Textbox");
		SuccessReport("Enter Data in Search textbox", "Successfully entered data in the search textbox");
		//Click on Search button
		click(btnSearch, "Search button");
		Thread.sleep(3000);
		//Get the page title
		String strText = getTitle();
		Thread.sleep(3000);
		if (strText.toLowerCase().contains(txtSearchTerm.toLowerCase())) {
			SuccessReport("Verify search results page title", "Appropriate page title '" +strText+"' is displayed for the search term - " + txtSearchTerm);
		} else 
		{
			failureReport("Verify search results page title", "Appropriate page title '" +strText+"' is not displayed for the search term - " + txtSearchTerm);
		}
		Thread.sleep(3000);
		//Verify Search Results page heading
		String strSearchPageHeading = getText(sp.ResultsHeading, "Search Results Page Heading");
		
		if (strSearchPageHeading.toLowerCase().contains(txtCategory.toLowerCase())) {
			SuccessReport("Verify Page Heading", "Search term '" +txtCategory+"' is found in search results heading");
		} else 
		{
			failureReport("Verify Page Heading", "Search term  '" +txtCategory+ "' is not found in search results heading");
		}
		
		Thread.sleep(4000);
		//Click on Product
		click(sp.imgProduct, "Product");
	}
}
