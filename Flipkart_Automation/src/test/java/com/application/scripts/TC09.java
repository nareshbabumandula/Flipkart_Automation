package com.application.scripts;

import org.testng.annotations.Test;

import com.objectRepository.CommonPage;
import com.objectRepository.Homepage;
import com.utilities.Xls_Reader;

public class TC09 extends Homepage{
	
	//Create object for excel
	Xls_Reader reader = new Xls_Reader("TestData/Data.xlsx");
	
	CommonPage cp = new CommonPage();

	@Test
	public void testSearchProduct() throws Throwable
	{
		boolean bFlag = false;
		try {
			String txtSearchTerm = reader.getCellData("Product", "Product_Name", 2);
			type(txtSearch, txtSearchTerm, "Search textbox");
			click(cp.btnSearch, "Search button");
			bFlag =true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		finally {
			if (bFlag) {
				SuccessReport("Search Product", "Successfully searched the product");
			} else {
				failureReport("Search Product", "Failed to search the product");
			}
		}
	
		
		
	}
	
}
