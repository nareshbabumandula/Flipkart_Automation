package com.sikuli.scripts;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.annotations.AfterClass;

public class SikuliDemo {
	
	public WebDriver driver;
	String strRootPath = System.getProperty("user.dir");
	
	@Test
	public void Sikuli_Methods() throws FindFailed {
		Screen screen = new Screen();
		Pattern imgCheckout = new Pattern(strRootPath + "/Images/btnCHECKOUT.png");
		Pattern txtSearch = new Pattern(strRootPath + "/Images/txtSearch.png");
		//screen.wait(imgCheckout);
		//Click on CHECKOUT button
		//screen.click(imgCheckout);
		screen.type(txtSearch,"pampers"); //Type the data in text box
	}
	
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", strRootPath + "/Drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://www.rightstart.com/");
		driver.manage().window().maximize();
	}

	@AfterClass
	public void afterClass() {
		//driver.quit();
	}

}
