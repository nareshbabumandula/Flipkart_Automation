/**
 * com.ctaf is a group of Selenium accelerators  
 */
package com.accelerators;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;

import com.support.ExcelReader;
import com.support.ReportStampSupport;

/**
 *  ActionEngine is a wrapper class of Selenium actions
 */
@SuppressWarnings("deprecation")
public class ActionEngine extends TestEngine {
	
	
	public  XSSFSheet inputSheet =null;
	
	public 	ExcelReader Excelobject=new ExcelReader();
	public static long ty=Long.valueOf(configProps.getProperty("Sleep_VLow").toString());
	public static long lSleep_Low=Long.valueOf(configProps.getProperty("Sleep_Low").toString());
	public static long lSleep_Medium=Long.valueOf(configProps.getProperty("Sleep_Medium").toString());
	public static long lSleep_High=Long.valueOf(configProps.getProperty("Sleep_High").toString());
	public WebDriverWait wait;
//	public static boolean blnEventReport = false;
	public  String gStrErrMsg=" ";
	public String strHQ;
	public String strCST;
	public  List<String> strC=new ArrayList<String>();
	
	
	/**
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:Login Button, SignIn Link
	 *            etc..)
	 * @return --boolean (true or false)
	 * @throws Throwable
	 */

	public boolean click(By locator, String locatorName)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {
			driver.findElement(locator).click();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (flag) {
				SuccessReport("Click on "+locatorName, "Successfully clicked on: \""+locatorName+"\"");
				//return true;
			} else if(!flag) {
				failureReport("Click on "+locatorName, "Unable to click on: \""+ locatorName+"\"");
			}
		}
		return flag;
	}
	
	public boolean cleartext(By locator, String locatorName)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {
			driver.findElement(locator).clear();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (flag) {
				SuccessReport("Click on "+locatorName, "Successfully cleared the value from : \""+locatorName+"\"");
				//return true;
			} else if(!flag) {
				failureReport("Click on "+locatorName, "Failed to clear the value from : \""+ locatorName+"\"");

			}
			
		}
		return flag;
	}
		
	public boolean clickWithOutReport(By locator, String locatorName)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {
			driver.findElement(locator).click();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return flag;
	}
/*****
 * moveToElement
 * @param locator		: Action to be performed on element (Get it from Object repository)
 * @param locatorName	:Meaningful name to the element (Ex:Login Button, SignIn Link etc..)
 * @return				:(true or false)
 */
	public boolean moveToElement(By locator, String locatorName){
		boolean flag = false;
		try{
			WebElement element = driver.findElement(locator);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].scrollIntoView(true);", element);
			Actions actions = new Actions(driver);		
			actions.moveToElement(driver.findElement(locator)).build().perform();
			flag=true;
			}catch(Exception e){
				e.printStackTrace();
			}
		return flag;
	}
	/**
	 * This method returns check existence of element
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:Textbox, checkbox etc)
	 * @return: Boolean value(True or False)
	 * @throws Throwable 
	 * @throws NoSuchElementException
	 */
	public boolean isElementPresent(By by, String locatorName) throws Throwable{
		cmdStartTime=System.currentTimeMillis();
		//boolean flag = false;
		try {
			driver.findElement(by);
			flag = true;
			SuccessReport("Verify Element "+locatorName, locatorName + " is Present");
			return true;
		} catch (Exception e) {
				System.out.println(e.getMessage());
			return false;
		} finally {
			/*if (flag) {
			} else {
				failureReport("Check IsElementPresent ","\""+locatorName+"\"Element is not present on the page");
			}*/
		}
	}

	public boolean isPopUpElementPresent(By by, String locatorName)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {
			if (driver.findElement(by).isDisplayed())
				flag = true;
			else
				flag = false;
			return flag;
		} catch (Exception e) {

			System.out.println(e.getMessage());
			return false;
		} finally {
			if (flag) {
				SuccessReport("IsElementPresent ","Able to locate the element \""+locatorName+"\"");
			} else if(!flag) {
				failureReport("check IsElementPresent","\""+locatorName+"\"Element is not present on the page");
			}
		}
	}

	/**
	 * This method used type value in to text box or text area
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param testdata
	 *            : Value wish to type in text box / text area
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:Textbox,Text Area etc..)
	 * 
	 * @throws NoSuchElementException
	 */
	public boolean type(By locator, String testdata, String locatorName)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {
			driver.findElement(locator).sendKeys(Keys.ESCAPE);
			driver.findElement(locator).clear();
			driver.findElement(locator).sendKeys(testdata);
			flag = true;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;

		} finally {
			if (flag) {
				SuccessReport("Type Data in "+locatorName,"Data typing action is performed on \""+locatorName+"\" with data \""+testdata+"\"");
			} else if(!flag){
				failureReport("Type Data in "+locatorName,"Data typing action is not perform on \""+locatorName+"\" with data \""+testdata+"\"");
			}
		}
		return flag;
	}
	
	
	/**
	 * This method used type value in to text box or text area
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param testdata
	 *            : Value wish to type in text box / text area
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:Textbox,Text Area etc..)
	 * 
	 * @throws NoSuchElementException
	 */
	public boolean typewithoutclear(By locator, String testdata, String locatorName)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {
			driver.findElement(locator).sendKeys(testdata);
			flag = true;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;

		} finally {
			if (flag) {
				SuccessReport("Type Data in "+locatorName,"Data typing action is performed on \""+locatorName+"\" with data \""+testdata+"\"");
			} else if(!flag){
				failureReport("Type Data in "+locatorName,"Data typing action is not perform on \""+locatorName+"\" with data \""+testdata+"\"");
			}
		}
		return flag;
	}
	
	
	public boolean jsType(By locator, String testdata, String locatorName)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {
			driver.findElement(locator).clear();
			WebElement element = driver.findElement(locator);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].setAttribute('value', "+testdata+")", element);
			flag = true;

		} catch (Exception e) {

			System.out.println(e.getMessage());
			return false;
		} finally {
			if (flag) {
				SuccessReport("Type Data in "+locatorName,"Data typing action is performed on \""+locatorName+"\" with data \""+testdata+"\"");
			} else if(!flag){
				failureReport("Type Data in "+locatorName,"Data typing action is not perform on \""+locatorName+"\" with data \""+testdata+"\"");
			}
		}
		return flag;
	}

	/**
	 * Moves the mouse to the middle of the element. The element is scrolled
	 * into view and its location is calculated using getBoundingClientRect.
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:link,menus etc..)
	 * 
	 */
	public boolean mouseover(By locator, String locatorName)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {
			WebElement mo = driver.findElement(locator);
			new Actions(driver).moveToElement(mo).build().perform();
			flag = true;
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			/*if (flag) {
				SuccessReport("MouseOver ","MouserOver Action is performed on \""+locatorName+"\"");
			} else {
				failureReport("MouseOver","MouseOver action is not performed on \""+locatorName+"\"");
			}*/
		}
	}

	/**
	 * A convenience method that performs click-and-hold at the location of the
	 * source element, moves by a given offset, then releases the mouse.
	 * 
	 * @param source
	 *            : Element to emulate button down at.
	 * 
	 * @param xOffset
	 *            : Horizontal move offset.
	 * 
	 * @param yOffset
	 *            : Vertical move offset.
	 * 
	 */
	public boolean draggable(By source, int x, int y, String locatorName)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {

			WebElement dragitem = driver.findElement(source);
			new Actions(driver).dragAndDropBy(dragitem, x, y).build().perform();
			Thread.sleep(5000);
			flag = true;
			return true;

		} catch (Exception e) {
		
			return false;
			
			
		} finally {
			if (flag) {
				SuccessReport("Draggable ","Draggable Action is performed on \""+locatorName+"\"");			
			} else if(!flag) {
				failureReport("Draggable ","Draggable action is not performed on \""+locatorName+"\"");
			}
		}
	}

	/**
	 * A convenience method that performs click-and-hold at the location of the
	 * source element, moves to the location of the target element, then
	 * releases the mouse.
	 * 
	 * @param source
	 *            : Element to emulate button down at.
	 * 
	 * @param target
	 *            : Element to move to and release the mouse at.
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:Button,image etc..)
	 * 
	 */
	public boolean draganddrop(By source, By target, String locatorName)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {
			WebElement from = driver.findElement(source);
			WebElement to = driver.findElement(target);
			new Actions(driver).dragAndDrop(from, to).perform();
			flag = true;
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			if (flag) {
				SuccessReport("DragAndDrop ","DragAndDrop Action is performed on \""+locatorName+"\"");
			} else if(!flag) {
				failureReport("DragAndDrop ","DragAndDrop action is not performed on \""+locatorName+"\"");
			}
		}
	}

	/**
	 * To slide an object to some distance
	 * 
	 * @param slider
	 *            : Action to be performed on element
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:Login Button, SignIn Link
	 *            etc..)
	 * 
	 */
	public boolean slider(By slider, int x, int y, String locatorName)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();

		boolean flag = false;
		try {
			WebElement dragitem = driver.findElement(slider);
			// new Actions(driver).dragAndDropBy(dragitem, 400, 1).build()
			// .perform();
			new Actions(driver).dragAndDropBy(dragitem, x, y).build().perform();// 150,0
			Thread.sleep(5000);
			flag = true;
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			if (flag) {
				SuccessReport("Slider ", "Slider Action is performed on \""+locatorName+"\"");
			} else {
				failureReport("Slider ","Slider action is not performed on \""+locatorName+"\"");
			}
		}
	}

	/**
	 * To right click on an element
	 * 
	 * @param by
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:Login Button, SignIn Link
	 *            etc..)
	 * 
	 * @throws Throwable
	 */

	public boolean rightclick(By by, String locatorName)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {
			WebElement elementToRightClick = driver.findElement(by);
			Actions clicker = new Actions(driver);
			clicker.contextClick(elementToRightClick).perform();
			flag = true;
			return true;
			// driver.findElement(by1).sendKeys(Keys.DOWN);
		} catch (Exception e) {

			return false;
		} finally {
			if (flag) {
				SuccessReport("RightClick ","RightClick Action is performed on \""+locatorName+"\"");
			} else {
				failureReport("RightClick ","RightClick action is not performed on \""+locatorName+"\"");
			}
		}
	}

	/**
	 * Wait for an element
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 */

	public boolean waitForTitlePresent(By locator) throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		boolean bValue = false;

		try {
			for (int i = 0; i < 200; i++) {
				if (driver.findElements(locator).size() > 0) {
					flag = true;
					bValue = true;
					break;
				} else {
					driver.wait(50);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (flag) {
				SuccessReport("WaitForTitlePresent ","Launched successfully expected Title");
			} else {
				failureReport("WaitForTitlePresent ", "Title is wrong");
			}
		}
		return bValue;
	}

	/**
	 * Wait for an ElementPresent
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @return Whether or not the element is displayed
	 */
	public boolean waitForElementPresent(By by, String locator)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;             
		for (int i = 0; i < 100; i++) {
			try{
				if (driver.findElement(by).isDisplayed()){
					Thread.sleep(250);
					flag= true;   
					return flag;
				}
			}catch(Exception e){                     
				System.out.println(""+locator+" Element not found");
				if(i==5)
					failureReport("WaitForElementPresent ","Falied to locate the element \""+locator+"\"");                       

				else
					continue;
			}

		}

		return flag;  

	}
	
	public boolean waitForElementPresentWithoutReport(By by, String locator)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;             
		for (int i = 0; i < 100; i++) {
			try{
				if (driver.findElement(by).isDisplayed()){
					flag= true;   
					return flag;
				}
			}catch(Exception e){                     
				//System.out.println(""+locator+" Element not found");
				if(i==5)
					
					failureReport("WaitForElementPresent ","Falied to locate the element \""+locator+"\"");                       

				else
					continue;
			}

		}

		return flag;  

	}

	/**
	 * This method Click on element and wait for an element
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param waitElement
	 *            : Element name wish to wait for that (Get it from Object
	 *            repository)
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:Login Button, SignIn Link
	 *            etc..)
	 */
	public boolean clickAndWaitForElementPresent(By locator,
			By waitElement, String locatorName) throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {
			click(locator, locatorName);
			waitForElementPresent(waitElement, locatorName);
			flag = true;
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (flag){
				SuccessReport("ClickAndWaitForElementPresent ","Successfully performed clickAndWaitForElementPresent action");
			} else {
				failureReport("ClickAndWaitForElementPresent ","Failed to perform clickAndWaitForElementPresent action");
			}
		}
	}

	/**
	 * Select a value from Dropdown using send keys
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param value
	 *            : Value wish type in dropdown list
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:Year Dropdown, items
	 *            Listbox etc..)
	 * 
	 */
	public boolean selectBySendkeys(By locator, String value,
			String locatorName) throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {
			driver.findElement(locator).sendKeys(value);
			flag = true;
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			if (flag) {
				SuccessReport("Select value from "+locatorName,"\""+value+"\"is Selected from the DropDown\""+locatorName+"\"");		
			} else {
				failureReport("Select value from "+locatorName,"\""+value+"\"is Not Selected from the DropDown\""+locatorName+"\"");
				// throw new ElementNotFoundException("", "", "")
			}
		}
	}

	/**
	 * select value from DropDown by using selectByIndex
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param index
	 *            : Index of value wish to select from dropdown list.
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:Year Dropdown, items
	 *            Listbox etc..)
	 * 
	 */
	public boolean selectByIndex(By locator, int index,
			String locatorName) throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {
			Select s = new Select(driver.findElement(locator));
			s.selectByIndex(index);
			flag = true;
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (flag) {
				SuccessReport("Select ", "Option at index \""+index+"\"is Selected from the DropDown \""+locatorName+"\"");
			} else {
				failureReport("Select ", "Option at index \""+index+"\"is Not Selected from the DropDown \""+locatorName+"\"");
			}
		}
	}

	/**
	 * select value from DD by using value
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param value
	 *            : Value wish to select from dropdown list.
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:Year Dropdown, items
	 *            Listbox etc..)
	 */

	public boolean selectByValue(By locator, String value,
			String locatorName) throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {
			Select s = new Select(driver.findElement(locator));
			s.selectByValue(value);
			flag = true;
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			if (flag) {
				SuccessReport("Select","Option with value attribute \""+value+"\"is Selected from the DropDown \""+locatorName+"\"");
			} else {
				failureReport("Select","Option with value attribute \""+value+"\"is Not Selected from the DropDown \""+locatorName+"\"");
			}
		}
	}

	/**
	 * select value from DropDown by using selectByVisibleText
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param visibletext
	 *            : VisibleText wish to select from dropdown list.
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:Year Dropdown, items
	 *            Listbox etc..)
	 */

	public boolean selectByVisibleText(By locator, String visibletext,
			String locatorName) throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {
			Select s = new Select(driver.findElement(locator));
			s.selectByVisibleText(visibletext);
			flag = true;
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (flag) {
				SuccessReport("Select","\""+visibletext+"\" is Selected from the DropDown \""+locatorName+"\"");		
			} else if(!flag) {
				failureReport("Select","\""+visibletext+"\" is Not Selected from the DropDown \""+locatorName+"\"");
			}
		}
	}

	/**
	 * SWITCH TO WINDOW BY USING TITLE
	 * 
	 * @param windowTitle
	 *            : Title of window wish to switch
	 * 
	 * @param count
	 *            : Selenium launched Window id (integer no)
	 * 
	 * @return: Boolean value(true or false)
	 * 
	 */
	//

	public Boolean switchWindowByTitle(String windowTitle, int count)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {
			Set<String> windowList = driver.getWindowHandles();

			//			Calendar calendar = new GregorianCalendar();
			//			int second = calendar.get(Calendar.SECOND); // /to get current time
			//			int timeout = second + 40;
			/*	while (windowCount != count && second < timeout) {
				Thread.sleep(500);
				windowList = driver.getWindowHandles();
				windowCount = windowList.size();

			}*/

			String[] array = windowList.toArray(new String[0]);

			driver.switchTo().window(array[count-1]);

			if (driver.getTitle().contains(windowTitle)){
				flag = true;
			}else{
				flag = false;
			}
			return flag;
		} catch (Exception e) {
			//flag = true;
			return false;
		} finally {
			if (flag) {
				SuccessReport("SelectWindow","Navigated to the window with title: \""+windowTitle+"\"");
			} else {
				failureReport("SelectWindow","The Window with title: \""+windowTitle+"\"is not Selected");
			}
		}
	}


	public Boolean switchToNewWindow()
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {

			Set<String> s=driver.getWindowHandles();
			Object popup[]=s.toArray();
			driver.switchTo().window(popup[1].toString());
			flag = true;
			return flag;
		} catch (Exception e) {
			flag = false;
			return flag;
		} finally {
			if (flag) {
				SuccessReport("SelectWindow","Window is Navigated with title:");				
			} else {
				failureReport("SelectWindow","The Window with title: is not Selected");
			}
		}
	}


	public Boolean switchToOldWindow()
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {

			Set<String> s=driver.getWindowHandles();
			Object popup[]=s.toArray();
			driver.switchTo().window(popup[0].toString());
			flag = true;
			return flag;
		} catch (Exception e) {
			flag = false;
			return flag;
		} finally {
			if (flag) {
				SuccessReport("SelectWindow","Focus navigated to the window with title:");			
			} else {
				failureReport("SelectWindow","The Window with title: is not Selected");
			}
		}
	}


	/**
	 * Function To get column count and print data in Columns
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @return: Returns no of columns.
	 * 
	 */
	public int getColumncount(By locator) throws Exception {
		cmdStartTime=System.currentTimeMillis();
		WebElement tr = driver.findElement(locator);
		List<WebElement> columns = tr.findElements(By.tagName("td"));
		int a = columns.size();
		System.out.println(columns.size());
		for (WebElement column : columns) {
			System.out.print(column.getText());
			System.out.print("|");
		}
		return a;

	}

	/**
	 * Function To get row count and print data in rows
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @return: returns no of rows.
	 */
	public int getRowCount(By locator) throws Exception {
		cmdStartTime=System.currentTimeMillis();
		WebElement table = driver.findElement(locator);
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		int a = rows.size() - 1;
		return a;
	}

	/**
	 * Verify alert present or not
	 * 
	 * @return: Boolean (True: If alert preset, False: If no alert)
	 * 
	 */
	public boolean Alert() throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean presentFlag = false;
		Alert alert = null;

		try {

			// Check the presence of alert
			alert = driver.switchTo().alert();
			// if present consume the alert
			alert.accept();
			presentFlag = true;
		} catch (NoAlertPresentException ex) {
			// Alert present; set the flag

			// Alert not present
			ex.printStackTrace();
		} finally {
			if (!presentFlag) {
				SuccessReport("Alert ","The Alert is handled successfully");		
			} else{
				failureReport("Alert ", "There was no alert to handle");
			}
		}

		return presentFlag;
	}

	/**
	 * To launch URL
	 * 
	 * @param url
	 *            : url value want to launch
	 * @throws Throwable
	 * 
	 */
	public boolean launchUrl(String url) throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {

			driver.navigate().to(url);

			/*if(isAlertPresent()){
			driver.switchTo().alert().accept();
			}*/

			ImplicitWait();
			flag = true;
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (flag) {
				SuccessReport("Launching URL","Successfully launched \""+url+"\"");				
			} else {
				failureReport("Launching URL", "Failed to launch \""+url+"\"");
			}
		}
	}


	public boolean isAlertPresent() 
	{ cmdStartTime=System.currentTimeMillis();
		try 
		{ 
			driver.switchTo().alert(); 
			return true; 
		}   // try 
		catch (NoAlertPresentException Ex) 
		{ 
			return false; 
		}   // catch 
	}


	/*
	 * public static int getResponseCode(String url) { try { return
	 * Request.Get(url).execute().returnResponse().getStatusLine()
	 * .getStatusCode(); } catch (Exception e) { throw new RuntimeException(e);
	 * } }
	 */
	/**
	 * This method verify check box is checked or not
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:sign in Checkbox etc..)
	 * 
	 * @return: boolean value(True: if it is checked, False: if not checked)
	 * 
	 */
	public boolean isChecked(By locator, String locatorName)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean bvalue = false;
		boolean flag = false;
		try {
			if (driver.findElement(locator).isSelected()) {
				flag = true;
				bvalue = true;
			}

		} catch (NoSuchElementException e) {

			bvalue = false;
		} finally {
			if (flag) {
				//SuccessReport("IsChecked", "\""+locatorName+"\"is Selected");
				// throw new ElementNotFoundException("", "", "");
			} else {
				//failureReport("IsChecked","\""+locatorName+"\"is not Selected");
			}
		}
		return bvalue;
	}

	/**
	 * Element is enable or not
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:Login Button, UserName
	 *            Textbox etc..)
	 * 
	 * @return: boolean value (True: if the element is enabled, false: if it not
	 *          enabled).
	 * 
	 */

	public boolean isEnabled(By locator, String locatorName)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		Boolean value = false;
		boolean flag = false;
		try {
			if (driver.findElement(locator).isEnabled()) {
				flag = true;
				value = true;
			}

		} catch (Exception e) {

			flag = false;

		} finally {
			/*if (flag) {
				SuccessReport("IsEnabled ", "\""+locatorName+"\" is Enabled");
			} else {
				failureReport("IsEnabled ", "\""+locatorName+"\" is not Enabled");
			}*/
		}
		return value;
	}

	/**
	 * Element visible or not
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:Login Button, SignIn Link
	 *            etc..)
	 * 
	 * @return: boolean value(True: if the element is visible, false: If element
	 *          not visible)
	 * 
	 */

	public boolean isVisible(By locator, String locatorName)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		Boolean value = false;
		boolean flag = false;
		try {

			value = driver.findElement(locator).isDisplayed();
			value = true;
			flag = true;
		} catch (Exception e) {
			flag = false;
			value = false;

		} finally {
			if (flag) {
				SuccessReport("IsVisible ","\""+locatorName+"\" Element is Visible");
			} else {
				failureReport("IsVisible","\""+locatorName+"\"Element is Not Visible");
			}
		}
		return value;
	}

	/**
	 * Get the CSS value of an element
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:Login Button, label color
	 *            etc..)
	 * 
	 * @param cssattribute
	 *            : CSS attribute name wish to verify the value (id, name,
	 *            etc..)
	 * 
	 * @return: String CSS value of the element
	 * 
	 */

	public String getCssValue(By locator, String cssattribute,
			String locatorName) throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		String value = "";
		boolean flag = false;
		try {
			if (isElementPresent(locator, "locatorName")) {
				value = driver.findElement(locator).getCssValue(cssattribute);
				flag = true;
			}
		} catch (Exception e) {

		} finally {
			if (flag) {
				SuccessReport("GetCssValue ","Was able to get Css value from \""+locatorName+"\"");
			} else {
				failureReport("GetCssValue ","Was not able to get Css value from \""+locatorName+"\"");
			}
		}
		return value;
	}

	/**
	 * Check the expected value is available or not
	 * 
	 * @param expvalue
	 *            : Expected value of attribute
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param attribute
	 *            : Attribute name of element wish to assert
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:link text, label text
	 *            etc..)
	 * 
	 */
	public boolean assertValue(String expvalue, By locator,
			String attribute, String locatorName) throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {

			Assert.assertEquals(expvalue, getAttribute(locator, attribute, locatorName));
			flag = true;
		} catch (Exception e) {

		} finally {
			if (flag) {
				SuccessReport("AssertValue ","\""+locatorName+"\" is present in the page");				
				return false;
			} else {
				failureReport("AssertValue ","\""+locatorName+"\" is not present in the page");
			}
		}
		return flag;
	}

	/**
	 * Check the text is presnt or not
	 * 
	 * @param text
	 *            : Text wish to assert on the page.
	 * 
	 */
	public boolean assertTextPresent(String text) throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {
			Assert.assertTrue(isTextPresent(text));
			flag = true;
		} catch (Exception e) {

		} finally {
			if (flag) {
				SuccessReport("AssertTextPresent ","\""+text+"\" is not present in the page ");
				return false;
			} else {
				failureReport("AssertTextPresent ", "\""+text+"\" is present in the page ");
			}
		}
		return flag;
	}

	/**
	 * Assert element present or not
	 * 
	 * @param by
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:Login Button, SignIn Link
	 *            etc..)
	 * 
	 */
	public boolean assertElementPresent(By by, String locatorName)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {
			Assert.assertTrue(isElementPresent(by, locatorName));
			flag = true;
		} catch (Exception e) {

		} finally {
			if (flag) {
				SuccessReport("AssertElementPresent ", "\""+locatorName+"\"is present in the page ");
				return false;
			} else {
				failureReport("AssertElementPresent ", "\""+locatorName+"\" is not present in the page ");
			}
		}
		return flag;

	}

	/**
	 * Assert element present is not
	 * 
	 * @param by
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:Login Button, SignIn Link
	 *            etc..)
	 * 
	 */
	public boolean assertElementNotPresent(By by, String locatorName)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {
			if(!isElementPresent(by, locatorName));
			flag = true;
		} catch (Exception e) {

		} finally {
			if (flag) {
				SuccessReport("AssertElementNotPresent ", "\""+locatorName+"\"is Not present in the page ");
				return false;
			} else {
				failureReport("AssertElementNotPresent ", "\""+locatorName+"\" is present in the page ");
			}
		}
		return flag;

	}
	/**
	 * Assert text on element
	 * 
	 * @param by
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param text
	 *            : expected text to assert on the element
	 * 
	 */

	public boolean assertText(By by, String text) throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {

			Assert.assertEquals(getText(by, text).trim(), text.trim());
			flag = true;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (flag) {
				SuccessReport("AssertText ","\""+text+"\" is  present in the element ");				
				return false;
			} else {
				failureReport("AssertText ", "\""+text+"\" is not present in the element ");
			}
		}

	}

	/**
	 * Assert text on element
	 * 
	 * @param by
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param text
	 *            : expected text to assert on the element
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:link text, label text
	 *            etc..)
	 * 
	 */
	public boolean verifyText(By by, String text, String locatorName)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		String vtxt="";

		try {

			vtxt = getText(by, locatorName).trim();
			System.out.println("Text:"+vtxt);
			if(vtxt.equals(text.trim()))
			{ flag = true;
			return true;
			}
			else
			{
				return false;
			}
		} catch (Exception e) {
			return false;
		} finally {
			if (flag) {
				SuccessReport("VerifyText ","\""+text+"\" is present in the location \""+ locatorName+"\"");						
			} else {
				failureReport("VerifyText ","Expected Text:"+text+"    "+"Actual Text:"+vtxt);
			}
		}
	}

	/**
	 * @return: return title of current page.
	 * 
	 * @throws Throwable
	 */

	public String getTitle() throws Throwable {
		cmdStartTime=System.currentTimeMillis();

		String text = driver.getTitle();
		if (!text.isEmpty()) {
			SuccessReport("Title ","Title of the page is: \""+text+"\"");
		}
		return text;
	}

	/**
	 * Assert Title of the page.
	 * 
	 * @param title
	 *            : Expected title of the page.
	 * 
	 */
	public boolean asserTitle(String title) throws Throwable {
		boolean flag = false;
		cmdStartTime=System.currentTimeMillis();
		try {
			By windowTitle = By.xpath("//title[contains(text(),'" + title
					+ "')]");
			if (waitForTitlePresent(windowTitle)) {
				Assert.assertEquals(getTitle(), title);
				flag = true;
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {

			if (flag) {
				SuccessReport("AsserTitle ","Page title is verified: \""+title+"\"");				
			} else {
				failureReport("AsserTitle ","Page title is not matched with: \""+title+"\"");
			}
		}

	}

	/**
	 * Verify Title of the page.
	 * 
	 * @param title
	 *            : Expected title of the page.
	 * 
	 */
	public boolean verifyTitle(String title) throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;

		try {
			getTitle().equals(title);
			flag = true;
			return true;
		} catch (Exception e) {
			return false;
		}

		finally {
			if (flag) {
				SuccessReport("VerifyTitle ","Page title is verified: \""+title+"\"");				
			} else {
				failureReport("VerifyTitle ","Page title is not matched with: \""+title+"\"");
			}
		}
	}

	/**
	 * Verify text present or not
	 * 
	 * @param text
	 *            : Text wish to verify on the current page.
	 * 
	 */
	public boolean verifyTextPresent(String text) throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		;
		if ((driver.getPageSource()).contains(text)) {
			SuccessReport("VerifyTextPresent ","\""+text+"\"is present in the page");
			flag = true;
			return flag;
		} else {
			failureReport("VerifyTextPresent ","\""+text+"\"is not present in the page");
			return flag;
		}
		
	}

	/**
	 * Get the value of a the given attribute of the element.
	 * 
	 * @param by
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param attribute
	 *            : Attribute name wish to assert the value.
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:label, SignIn Link etc..)
	 * 
	 * @return: String attribute value
	 * 
	 */

	public String getAttribute(By by, String attribute,
			String locatorName) throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		String value = "";
		if (isElementPresent(by, locatorName)) {
			value = driver.findElement(by).getAttribute(attribute);
		}
		return value;
	}

	/**
	 * Text present or not
	 * 
	 * @param text
	 *            : Text wish to verify on current page
	 * 
	 * @return: boolean value(true: if Text present, false: if text not present)
	 */

	public boolean isTextPresent(String text) throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean value = false;
		if(driver.getPageSource().contains(text)){
			value = true;
			flag = true;
		}else{
			System.out.println("is text "+text+" present  " + value);
			flag = false;
		}
		if (value) {
			SuccessReport("IsTextPresent ", "\""+text+"\"is present in the page ");		
		} else  {
			failureReport("IsTextPresent ", "\""+text+"\"is  not present in the page ");

		}
		return value;
	}

	/**
	 * The innerText of this element.
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:label text, SignIn Link
	 *            etc..)
	 * 
	 * @return: String return text on element
	 * 
	 */

	public String getText(By locator, String locatorName)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		String text = "";
		boolean flag = false;
		try {
			if (isElementPresent(locator, locatorName)) {
				text = driver.findElement(locator).getText();
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (flag) {
				SuccessReport("GetText ", " Able to get Text from \""+locatorName+"\"");			
			} else {
				warningReport("GetText ", " Unable to get Text from \""+locatorName+"\"");
			}
		}
		return text;
	}

	public String getValue(String locator, String locatorName)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		String text = "";
		boolean flag = false;
		try {
			if (driver.findElement(By.xpath(locator)).isDisplayed()) {
				text = driver.findElement(By.xpath(locator)).getAttribute(
						"value");
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (flag) {
				SuccessReport("GetValue from "+locatorName, " Able to get Text from \""+locatorName+"\"");
			} else {
				failureReport("GetValue from "+locatorName, " Unable to get Text from \""+locatorName+"\"");
			}
		}
		return text;
	}

	public int getElementsSize(By locator, String locatorName)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		int text = 0;
		try {
			if (driver.findElement(locator).isDisplayed()) {
				text = driver.findElements(locator).size();
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return text;
	}

	/**
	 * Capture Screenshot
	 * 
	 * @param fileName
	 *            : FileName screenshot save in local directory
	 * 
	 */
	public void screenShot(String fileName) {
		cmdStartTime=System.currentTimeMillis();
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		
		try {
			// Now you can do whatever you need to do with it, for example copy
			// somewhere
			FileUtils.copyFile(scrFile, new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Click on the Link
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:SignIn Link, menu's
	 *            etc..)
	 */

	public boolean mouseHoverByJavaScript(By locator, String locatorName)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {
			WebElement mo = driver.findElement(locator);
			String javaScript = "var evObj = document.createEvent('MouseEvents');"
					+ "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
					+ "arguments[0].dispatchEvent(evObj);";
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(javaScript, mo);
			flag = true;
			return true;
		}

		catch (Exception e) {

			return false;
		} finally {
			if (flag) {
				SuccessReport("MouseOver "," MouserOver Action is performed on \""+locatorName+"\"");				
			} else {
				failureReport("MouseOver "," MouseOver Action is not performed on \""+locatorName+"\"");
			}
		}
	}

	public boolean JSClick(By locator, String locatorName)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {
			WebElement element = driver.findElement(locator);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
			// driver.executeAsyncScript("arguments[0].click();", element);

			flag = true;

		}

		catch (Exception e) {
			throw e;

		} finally {
			if (flag) {
				SuccessReport("Click on "+locatorName," Click Action is performed on \""+locatorName+"\"");			
			} else if(!flag) {
				failureReport("Click on "+locatorName," Click Action is not performed on \""+locatorName+"\"");	
			}
		}
		return flag;
	}
	
	
	
	public  boolean waitForFrameToLoadAndSwitchToIt(By by, String LocatorName) throws Throwable{
		WebDriverWait wait = new WebDriverWait(driver, 12);

		cmdStartTime=System.currentTimeMillis();
		try {
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));
			/*if(blnEventReport==true)
			SuccessReport("waitForFrameToLoadAndSwitchToIt "+LocatorName, "Switched to frame successfully");*/
			 
			return true;
		} catch (Exception e) {
			failureReport("waitForFrameToLoadAndSwitchToIt",
					"Not able to switch to Frame : " + LocatorName);
					 
			return false;
		}

		
		
	}
	
	
	public  boolean waitForFrameToLoadAndSwitchToItWithOutReport(By by, String LocatorName) throws Throwable{
		WebDriverWait wait = new WebDriverWait(driver, 12);

		cmdStartTime=System.currentTimeMillis();
		try {
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));
			/*if(blnEventReport==true){
				//SuccessReport("waitForFrameToLoadAndSwitchToIt "+LocatorName, "Switched to frame successfully");	
			}*/
			
			 
			 
			return true;
		} catch (Exception e) {
//			failureReport("waitForFrameToLoadAndSwitchToIt",
//					"Not able to switch to Frame : " + LocatorName);
					 
			return false;
		}

		
		
	}
	
	

	/**
	 * This method switch the focus to selected frame using frame index
	 * 
	 * @param index
	 *            : Index of frame wish to switch
	 * 
	 */
	public boolean switchToFrameByIndex(int index) throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {
			new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe")));
			driver.switchTo().frame(index);
			flag = true;
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			if (flag) {
				SuccessReport("SelectFrame ", " Frame with index \""+index+"\" is selected");
			} else {
				failureReport("SelectFrame ", " Frame with index \""+index+"\" is not selected");
			}
		}
	}

	/**
	 * This method switch the to frame using frame ID.
	 * 
	 * @param idValue
	 *            : Frame ID wish to switch
	 * 
	 */
	public boolean switchToFrameById(String idValue) throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {
			driver.switchTo().frame(idValue);
			flag = true;
			return true;
		} catch (Exception e) {

			e.printStackTrace();
			return false;
		} finally {
			if (flag ) {
				//SuccessReport("SelectFrame", "Frame with Id \""+idValue+"\" is selected");			
			} else {
				//failureReport("SelectFrame", "Frame with Id \""+idValue+"\" is not selected");
			}
		}
	}

	/**
	 * This method switch the to frame using frame Name.
	 * 
	 * @param nameValue
	 *            : Frame Name wish to switch
	 * 
	 */
	public boolean switchToFrameByName(String nameValue)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {
			driver.switchTo().frame(nameValue);
			flag = true;
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			if (flag) {
				SuccessReport("SelectFrame ", "Frame with Name \""+nameValue+"\" is selected");			
			} else if(!flag){
				failureReport("SelectFrame ", "Frame with Name \""+nameValue+"\" is not selected");
			}
		}
	}

	/**
	 * This method switch the to Default Frame.
	 * 
	 * @throws Throwable
	 */
	public boolean switchToDefaultFrame() throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {
			driver.switchTo().defaultContent();
			flag = true;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (flag) {
				//SuccessReport("SelectFrame ","Frame with Name is selected");
			} else if(!flag) {
				//failureReport("SelectFrame ","The Frame is not selected");
			}
		}
	}

	/**
	 * This method switch the to frame using frame Name.
	 * 
	 * @param nameValue
	 *            : Frame Name wish to switch
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:SignIn Link, login button
	 *            etc..)
	 * 
	 * 
	 */
	public boolean switchToFrameByLocator(By lacator, String locatorName)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {
			driver.switchTo().frame(driver.findElement(lacator));
			flag = true;
			return true;
		} catch (Exception e) {

			e.printStackTrace();
			return false;
		} finally {
			if (flag) {
				SuccessReport("SelectFrame ", "Frame with Name \""+locatorName+"\" is selected");
			} else {
				failureReport("SelectFrame ", "Frame \""+locatorName+"\" is not selected");
			}
		}
	}

	/**
	 * This method wait selenium until element present on web page.
	 */
	public void ImplicitWait() {

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	public boolean waitUntilTextPresents(By by, String 
			expectedText, String locator) throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		wait = new WebDriverWait(driver, 120);
		boolean flag = false;

		try {
			wait.until(ExpectedConditions.textToBePresentInElementLocated(by,
					expectedText));

			flag = true;
			return  true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (flag) {
				SuccessReport(" WaitUntilTextPresent ","Successfully located element \""+locator+"\" with text \""+expectedText+"\"");
			} else {
				failureReport("WaitUntilTextPresent ","Falied to locate element \""+locator+"\" with text \""+expectedText+"\"");
			}

		}

	}

	/**
	 * Click on Element
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:SignIn Link, login button
	 *            etc..)
	 * 
	 * @throws StaleElementReferenceException
	 *             - If the element no longer exists as initially defined
	 */

	public boolean click1(WebElement locator, String locatorName)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {
			locator.click();
			flag = true;
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (flag) {
				SuccessReport("Click ", " Able to click on \""+locatorName+"\"");
			} else {
				failureReport("Click ", " Unable to click on \""+locatorName+"\"");
			}
		}

	}

	/**
	 * 
	 * This method wait driver until given driver time.
	 * 
	 */
	public WebDriverWait driverwait() {

		wait = new WebDriverWait(driver, 30);
		return wait;
	}

	/**
	 * This method wait selenium until visibility of Elements on WebPage.
	 * 
	 * @param by
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * @throws Throwable
	 * 
	 */

	public boolean waitForVisibilityOfElement(By by, String locator) throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		wait = new WebDriverWait(driver, 20);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			flag = true;
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			if (flag) {
				SuccessReport("WaitForVisibilityOfElement ", " Element\""+locator+"\"  is visible");			
			} else {
				failureReport("WaitForVisibilityOfElement ", " Element \""+locator+"\" is not visible");
			}
		}
	}
	
	
	
	
	/**
	 * This method wait selenium until visibility of Elements on WebPage.
	 * 
	 * @param by
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * @throws Throwable
	 * 
	 */
	
	public boolean waitForVisibilityOfElementWithoutReport(By by, String locator)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		wait = new WebDriverWait(driver, 30);

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			flag = true;
			return true;
		} catch (Exception e) {

			return false;
		} 
		
	}


	/**
	 * This method wait driver until Invisibility of Elements on WebPage.
	 * 
	 * @param by
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param by
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 */
	public boolean waitForInVisibilityOfElement(By by, String locator)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		wait = new WebDriverWait(driver, 60);
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
			flag = true;
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (flag) {
				SuccessReport("WaitForInVisibilityOfElement "," Element  \""+locator+"\"  is not visible");
			} else {
				failureReport("WaitForInVisibilityOfElement "," Element \""+locator+"\"  is  visible");
			}
		}

	}

	public List<WebElement> getElements(By locator) {

		List<WebElement> ele = driver.findElements(locator);

		return ele;
	}

	public boolean assertTextMatching(By by, String text,
			String locatorName) throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {
			String ActualText = getText(by, text).trim();
			if (ActualText.contains(text)) {
				flag = true;
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (flag) {
				SuccessReport("Verify \""+locatorName+"\"" , "\""+text+"\" is  present in the element ");				
			} else {
				failureReport("Verify \""+locatorName+"\"" , "\""+text+"\" is not present in the element");
			}
		}

	}

	// QuickFlix Functions added

	public boolean isElementDisplayed(WebElement element)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		for (int i = 0; i < 200; i++) {
			if (element.isDisplayed()) {
				flag = true;
				break;
			} else {
				Thread.sleep(50);
			}
		}
		return flag;
	}

	public void executeJavaScriptOnElement(String script) {
		((JavascriptExecutor) driver).executeScript(script);
	}

	public void closeBrowser() {
		driver.close();
		driver.quit();
	}

	public boolean hitKey(By locator, Keys keyStroke, String locatorName)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {
			driver.findElement(locator).sendKeys(keyStroke);
			flag = true;
			return true;
		} catch (NoSuchElementException e) {
			return false;
		} finally {
			if (flag) {
				SuccessReport("Type ","Data typing action is performed on \""+locatorName+"\" with data is \""+keyStroke+"\"");
			} else {
				failureReport("Type ","Data typing action is not perform on \""+locatorName+"\" with data is \""+keyStroke+"\"");

			}
		}
	}

	public Collection<WebElement> getWebElementsByTagInsideLocator(
			By locator, String tagName, String locatorName) throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		Collection<WebElement> elements;
		try {
			WebElement element = driver.findElement(locator);
			elements = element.findElements(By.tagName(tagName));
			flag = true;
		} catch (NoSuchElementException e) {
			throw e;
		} finally {
			if (flag) {
				SuccessReport("Type ","Data typing action is performed on \""+locatorName+"\" with data is \""+tagName+"\"");
			} else{
				failureReport("Type", "Data typing action is not performed on  \""+locatorName+"\" with data \""+tagName+"\"");
			}
		}
		return elements;
	}


	public void mouseOverElement(WebElement element, String locatorName)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;
		try {
			new Actions(driver).moveToElement(element).build().perform();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (flag) {
				SuccessReport("MouseOver "," MouserOver Action is performed on " + locatorName);		
			} else {
				failureReport("MouseOver "," MouseOver action is not performed on \""+locatorName+"\"");
			}
		}
	}

	//@Parameters({"browser"})
	public void SuccessReport(String strStepName, String strStepDes)
			throws Throwable {
		int intReporterType = Integer.parseInt(configProps
				.getProperty("reportsType"));
		switch (intReporterType) {
		case 1:

			break;
		case 2:
			if (configProps.getProperty("OnSuccessScreenshot")
					.equalsIgnoreCase("True")) {
				screenShot("Results/HTML"+suiteStartTime+"/Screenshots/"
						+ strStepDes.replaceAll("[^\\w]", "_") 
						+ ".jpeg");
			}
			onSuccess(strStepName, strStepDes);

			break;

		default:
			if (configProps.getProperty("OnSuccessScreenshot")
					.equalsIgnoreCase("True")) {
				screenShot("Results/HTML"+suiteStartTime+"/Screenshots/"
						+ strStepDes.replaceAll("[^\\w]", "_")
						+ ".jpeg");
			}
			onSuccess(strStepName, strStepDes);
			break;
		}
	}

	public void failureReport(String strStepName, String strStepDes)
			throws Throwable {
		int intReporterType = Integer.parseInt(configProps
				.getProperty("reportsType"));

		String stepExecTime =  ReportStampSupport.stepExecTime();
		switch (intReporterType) {
		case 1:
			flag = true;
			break;
		case 2:
			screenShot("Results/HTML"+suiteStartTime+"/Screenshots/"
					+ strStepDes.replaceAll("[^\\w]", "_")+stepExecTime+".jpeg");
			flag = true;
			onFailure(strStepName, strStepDes, stepExecTime);
			break;

		default:
			flag = true;
			screenShot("Results/HTML"+suiteStartTime+"/Screenshots/"
					+ strStepDes.replaceAll("[^\\w]", "_")+ReportStampSupport.stepExecTime()+".jpeg");
			onFailure(strStepName, strStepDes, stepExecTime);
			break;
		}

	}
	public void warningReport(String strStepName, String strStepDes)
			throws Throwable {
		int intReporterType = Integer.parseInt(configProps
				.getProperty("reportsType"));
		switch (intReporterType) {
		case 1:
			flag = true;
			break;
		case 2:
			screenShot("Results/HTML"+suiteStartTime+"/Screenshots/"
					+ strStepDes.replaceAll("[^\\w]", "_") 
					+ ".jpeg");
			flag = true;
			onWarning(strStepName, strStepDes);
			break;

		default:
			flag = true;
			screenShot("Results/HTML"+suiteStartTime+"/Screenshots/"
					+ strStepDes.replaceAll("[^\\w]", "_")
					+ ".jpeg");
			onWarning(strStepName, strStepDes);
			break;
		}

	}
	
	
	/***
	  * VerifyElementPresent
	  * @param by
	  * @param locatorName
	  * @return
	  * @throws Throwable
	  */
	public boolean VerifyElementPresent(By by, String locatorName)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		boolean flag = false;

		try {
			if (isElementPresent(by, locatorName)){       
				flag= true;   

			} 
		} catch(Exception e){                     
			System.out.println(e.getMessage());
			e.printStackTrace(); 
		}finally {
			if (flag) {
				SuccessReport("VerifyElementPresent ",locatorName+" is Displayed");    
			} else {
				failureReport("VerifyElementPresent ",locatorName+" is not Displayed");
			}
		}
		return flag;
	}
	
	public void waitProcessComplete(By by)
			throws Throwable {
		cmdStartTime=System.currentTimeMillis();
		int count = 1;
		while (count < 500) {
			
			try {
				if (driver.findElement(by).isDisplayed()) {
					Thread.sleep(100);
				} else {
					//SuccessReport("Wait Process Complete", "Wait Process is Completed");
					break;
				}
			} catch (Exception e) {
				break;
			}
			count++;
		}
		 
		 
	}
	
	
	public int getListCount(By by, String locatorName) throws Throwable {
		int count = 0;

		cmdStartTime=System.currentTimeMillis();
		try {
			if (isElementPresent(by, locatorName)) {
				count = driver.findElements(by)
						.size();
			}
			 
			 
			return count;

		} catch (Exception e) {
			failureReport("Frame Selection Exception Info :",
					"Unable to select the frame with locator :" + locatorName);
					 
			 
			return count;

		}
	}
	
	
	public List<WebElement> getWebElements(By by, String locatorName)
			throws Throwable {
		List<WebElement> webElements = null;

		cmdStartTime=System.currentTimeMillis();
		try {
			if (isElementPresent(by, locatorName)) {
				webElements = driver.findElements(by);
			}
			 
			 
			return webElements;

		} catch (Exception e) {
			failureReport("Exception Info :", "Unable to find with locator :"
					+ locatorName);
			return null;

		}
	}
	
	public  String getCurrentDate(String strFormat)
	{
		try{

			DateFormat dateFormat = new SimpleDateFormat(strFormat);
			Date dateObj = new Date();
			return dateFormat.format(dateObj);
		}
		catch(Exception e){
			return null;
		}
	}
	
	public  String getTomorrowDate(String strFormat)
	{
		try{
		    	DateFormat dateFormat = new SimpleDateFormat(strFormat);
		    	Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 1);
			return dateFormat.format(cal.getTime());
		}
		catch(Exception e){
			return null;
		}
	}
	
	public  boolean waitForElementPresent1(By by, String locatorName) throws Throwable 
	{
		boolean blnFlag = false;
		try
		{
			for (int i = 0; i < 50; i++) 
			{
				if (driver.findElement(by).isDisplayed()) 
					break;
				else
					Thread.sleep(lSleep_Low);
			}
			blnFlag = true;
			return blnFlag;
		} 
		catch (InterruptedException e) 
		{
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
//			if(!blnFlag)
//				failureReport("Wait for element Present", locatorName + " Element is not present. ");
//			else if(blnEventReport && blnFlag)
//				SuccessReport("Wait for element Present",locatorName +" Element is present");
		}
	}
	
}
