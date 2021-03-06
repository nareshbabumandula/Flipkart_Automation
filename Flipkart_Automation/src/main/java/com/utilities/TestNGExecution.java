package com.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;








import jxl.Sheet;
import jxl.Workbook;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.accelerators.ActionEngine;
import com.accelerators.TestEngine;
import com.support.ConfiguratorSupport;
import com.support.ReportStampSupport;



public class TestNGExecution {

	public static ConfiguratorSupport configProps=new ConfiguratorSupport("config.properties");

	public static void main(String[] args) throws Throwable 
	{
		ReportStampSupport.calculateSuiteStartTime();
		String FilePath = System.getProperty("user.dir")+"\\Macros\\"+configProps.getProperty("MacroFile");
		FileInputStream fs = new FileInputStream(FilePath);
		Workbook wb = Workbook.getWorkbook(fs);
		String sheet=configProps.getProperty("SheetName");
		Sheet sh = wb.getSheet(sheet);
		int rows = sh.getRows();
		int cols = sh.getColumns();

		FileInputStream updatefile = new FileInputStream(new File(FilePath));
		HSSFWorkbook  upworkbook = new HSSFWorkbook (updatefile);
		HSSFSheet upsheet = upworkbook.getSheet(configProps.getProperty("SheetName"));
		TestEngine te=new TestEngine();

		for(int row=1; row<sh.getRows(); row++)
		{
			boolean result=true;
			ArrayList<String> data=new ArrayList<String>();
			for(int col=0;col<cols;col++)
			{
				data.add(sh.getCell(col,row).getContents());
			}
			HSSFRow uprow=upsheet.getRow(row); 

			if(data.get(3).toUpperCase().equalsIgnoreCase("YES"))
			{
				//Student is null and Admin is not null
				if(data.get(2)!=null||!data.get(2).equals("NA"))
				{
					te.browsertype=data.get(2);
				}

				if(result)
				{
					try
					{	//Base.timeStampBeforeExecution = System.currentTimeMillis();

						
						XmlSuite suite = new XmlSuite();
						suite.setName("ClientDemo");
						XmlTest test = new XmlTest(suite);
						test.setName("TmpTest");
						List<XmlClass> classes = new ArrayList<XmlClass>();
						List<XmlSuite> suites = new ArrayList<XmlSuite>();
						
						classes.add(new XmlClass("com.application.scripts."+data.get(0)));
						test.setXmlClasses(classes) ;
						suites.add(suite);
						TestNG tng = new TestNG();
						tng.setXmlSuites(suites);
						
						System.out.println();
						System.out.println("com.application.scripts."+data.get(0));
						System.out.println();
						
						
						tng.run();
						
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
				updatefile.close();
				/*FileOutputStream outFile =new FileOutputStream(new File(FilePath));
				upworkbook.write(outFile);
				outFile.close();*/				
			}
		}
		
		
	}
}
