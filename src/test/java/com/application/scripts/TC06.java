package com.application.scripts;

import org.testng.annotations.Test;

import com.objectRepository.CommonPage;
import com.objectRepository.SearchResPage;
import com.utilities.Xls_Reader;

public class TC06 extends CommonPage{
		
	SearchResPage sp;
	//Create object for excel sheet
	Xls_Reader reader = new Xls_Reader("TestData/Data.xlsx");
	boolean bFlag=false;
	
	@Test
	public void testTC06() throws Throwable {
		String errMsg="";
		try {
			String product = reader.getCellData("Sheet1", "SearchTerm", 2);
			type(CommonPage.txtSearch, product, "Product name");
			click(CommonPage.btnSearch, "Search button");
			verifyText(sp.ResultsHeading, product, "Results heading");
			SuccessReport("Verify product search", "Successfully searched a product");
		} catch (Throwable e) {
			errMsg = e.getMessage();
			String[] asText = errMsg.split("}");
			errMsg = asText[0]  + "}";
			failureReport("Verify product search", "Failed to search a product since : " +errMsg);
			e.printStackTrace();
		}
	}

}
