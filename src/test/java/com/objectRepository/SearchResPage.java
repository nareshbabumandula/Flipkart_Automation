package com.objectRepository;

import org.openqa.selenium.By;

import com.accelerators.ActionEngine;
import com.utilities.Xls_Reader;

public class SearchResPage extends ActionEngine{

	public By ResultsHeading = By.xpath("//span[@class='_10Ermr']/span");
	public By imgProduct = By.xpath("(//img[contains(@src,'.jpeg?q=')])[4]");
	public By imgSearchedProduct = By.xpath("//img[@class='_2r_T1I']");
	public By strHeading = By.className("_2yAnYN");
	public By filters = By.xpath("//div[@class='_3ywJNQ']/child::*");

	
	

}

