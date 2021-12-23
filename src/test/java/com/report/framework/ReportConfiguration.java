package com.report.framework;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.configuration.framework.Base;

public class ReportConfiguration extends Base {
	
	private static ExtentReports extent;
	static Properties reportDetails, platformFlag;
	
	
	
	@SuppressWarnings("deprecation")
	public static ExtentReports getReportObjectAndroidBrowser() 
	{
	 reportDetails = returnProperty(PROJECT_PATH+"/src/test/java/com/properties/framework/data.properties");
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");  
	LocalDateTime now = LocalDateTime.now();  
	String path = System.getProperty("user.dir")+"/reports/AndroidBrowser/index-"+dtf.format(now)+".html";
	ExtentHtmlReporter reporter = new ExtentHtmlReporter(path);
	reporter.config().setReportName("Mobile SeeTest Android Browser Automation Results");
	reporter.config().setDocumentTitle("Test Results");
	reporter.config().setTheme(Theme.STANDARD);
	extent = new ExtentReports();
	extent.attachReporter(reporter);
	extent.setSystemInfo("Tester","Somnath Baul");
	extent.setSystemInfo("Environment","QA");
	extent.setSystemInfo("Platform","Android Browser");
	extent.setSystemInfo("Application_URL",reportDetails.getProperty("url"));
	return extent;
	}
	
	public static ExtentReports getReportObjectIOSBrowser() 
	{
    reportDetails = returnProperty(PROJECT_PATH+"/src/test/java/com/properties/framework/data.properties");
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");  
	LocalDateTime now = LocalDateTime.now();  
	String path = System.getProperty("user.dir")+"/reports/IOSBrowser/index-"+dtf.format(now)+".html";
	ExtentHtmlReporter reporter = new ExtentHtmlReporter(path);
	reporter.config().setReportName("Mobile SeeTest IOS Browser Automation Results");
	reporter.config().setDocumentTitle("Test Results");
	reporter.config().setTheme(Theme.STANDARD);
	extent = new ExtentReports();
	extent.attachReporter(reporter);
	extent.setSystemInfo("Tester","Somnath Baul");
	extent.setSystemInfo("Environment","QA");
	extent.setSystemInfo("Platform","IOS Browser");
	extent.setSystemInfo("Application_URL",reportDetails.getProperty("url"));
	return extent;
	}
	
	@SuppressWarnings("deprecation")
	public static ExtentReports getReportObjectAndroidApp() 
	{
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");  
	LocalDateTime now = LocalDateTime.now();  
	String path = System.getProperty("user.dir")+"/reports/AndroidApp/index-"+dtf.format(now)+".html";
	ExtentHtmlReporter reporter = new ExtentHtmlReporter(path);
	reporter.config().setReportName("Mobile SeeTest Android App Automation Results");
	reporter.config().setDocumentTitle("Test Results");
	reporter.config().setTheme(Theme.STANDARD);
	extent = new ExtentReports();
	extent.attachReporter(reporter);
	extent.setSystemInfo("Tester","Somnath Baul");
	extent.setSystemInfo("Environment","QA");
	extent.setSystemInfo("Platform","Android App");
	return extent;
	}
	@SuppressWarnings("deprecation")
	public static ExtentReports getReportObjectIOSApp() 
	{
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");  
	LocalDateTime now = LocalDateTime.now();  
	String path = System.getProperty("user.dir")+"/reports/IOSApp/index-"+dtf.format(now)+".html";
	ExtentHtmlReporter reporter = new ExtentHtmlReporter(path);
	reporter.config().setReportName("Mobile SeeTest IOS App Automation Results");
	reporter.config().setDocumentTitle("Test Results");
	reporter.config().setTheme(Theme.STANDARD);
	extent = new ExtentReports();
	extent.attachReporter(reporter);
	extent.setSystemInfo("Tester","Somnath Baul");
	extent.setSystemInfo("Environment","QA");
	extent.setSystemInfo("Platform","IOS App");
	return extent;
	}
	public static ExtentReports getReportObjectWebApp() 
	{
	reportDetails = returnProperty(PROJECT_PATH+"/src/test/java/com/properties/framework/data.properties");
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");  
	LocalDateTime now = LocalDateTime.now();  
	String path = System.getProperty("user.dir")+"/reports/WebApp/index-"+dtf.format(now)+".html";
	ExtentHtmlReporter reporter = new ExtentHtmlReporter(path);
	reporter.config().setReportName("Web Automation Results");
	reporter.config().setDocumentTitle("Test Results");
	reporter.config().setTheme(Theme.STANDARD);
	extent = new ExtentReports();
	extent.attachReporter(reporter);
	extent.setSystemInfo("Tester","Somnath Baul");
	extent.setSystemInfo("Environment","QA");
	extent.setSystemInfo("Platform","Web App");
	return extent;
	}
	
	
	public static ExtentReports reportObjectSelector()
	{
     platformFlag = returnProperty(PROJECT_PATH+"/src/test/java/com/properties/framework/platform.properties"); 
	if(platformFlag.getProperty("Android_Browser").equals("true"))
	{
		 return getReportObjectAndroidBrowser();
	}
	else if(platformFlag.getProperty("IOS_Browser").equals("true")) 
	{
		return getReportObjectIOSBrowser();
	}
	else if(platformFlag.getProperty("Android_App").equals("true"))
	{
		return getReportObjectAndroidApp();
	}
	else if(platformFlag.getProperty("IOS_App").equals("true"))
	{
		 return getReportObjectIOSApp();
	}
	else if(platformFlag.getProperty("WEB").equals("true"))
	{
		return getReportObjectWebApp();
	}
	else
		return null;
	
	}

}
