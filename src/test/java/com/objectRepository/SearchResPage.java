package com.objectRepository;

import org.openqa.selenium.By;

import com.accelerators.ActionEngine;
import com.utilities.Xls_Reader;

public class SearchResPage extends ActionEngine{

	public static By ResultsHeading = By.xpath("//span[@class='_10Ermr']/span");
	public static By imgProduct = By.xpath("(//img[contains(@src,'.jpeg?q=')])[4]");
	public static By imgSearchedProduct = By.xpath("//img[@class='_2r_T1I']");
	public static By strHeading = By.className("_2yAnYN");
	public static By filters = By.xpath("//div[@class='_3ywJNQ']/child::*");

	
	

}

