package com.application.scripts;

import org.testng.annotations.Test;
import com.objectRepository.CommonPage;
import com.objectRepository.SearchResPage;
import com.utilities.Xls_Reader;

public class TC01 extends CommonPage
{

	//Create object for excel
	Xls_Reader reader = new Xls_Reader("TestData/Data.xlsx");
	SearchResPage sp = new SearchResPage();
	
	@Test
	public void testTC01() throws Throwable
	{
		
		String txtSearchTerm = reader.getCellData("Sheet1", "SearchTerm", 2);
		String txtCategory = reader.getCellData("Sheet1", "Category", 2);
	
		//Enter text in Search text box
		type(txtSearch, txtSearchTerm, "Product Search Textbox");
		//Click on Search button
		click(btnSearch, "Search button");
		//Get the page title
		String strText = getTitle();
		if (strText.toLowerCase().contains(txtCategory.toLowerCase())) {
			SuccessReport("Verify search results page title", "Appropriate page title '" +strText+"' is displayed for the search term - " + txtSearchTerm);
		} else 
		{
			failureReport("Verify search results page title", "Appropriate page title '" +strText+"' is not displayed for the search term - " + txtSearchTerm);
		}
		
		//Verify Search Results page heading
		String strSearchPageHeading = getText(sp.ResultsHeading, "Search Results Page Heading");
		
		if (strSearchPageHeading.toLowerCase().contains(txtCategory.toLowerCase())) {
			SuccessReport("Verify Page Heading", "Search term '" +txtSearchTerm+"' is found in search results heading");
		} else 
		{
			failureReport("Verify Page Heading", "Search term  '" +txtSearchTerm+ "' is not found in search results heading");
		}
		
		Thread.sleep(4000);
		//Click on Product
		click(sp.imgProduct, "Product");
	
	}

}
