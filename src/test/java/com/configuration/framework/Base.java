package com.configuration.framework;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Random;

import com.experitest.appium.SeeTestClient;

import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;

public class Base {
	
	private String accessKey = "eyJhbGciOiJIUzI1NiJ9.eyJ4cC51Ijo5NDY2NzQxLCJ4cC5wIjo5NDY2NzQwLCJ4cC5tIjoxNjAxNTI2MzAxODAxLCJleHAiOjE5MTY4OTI5NDQsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.H9SHgMkl1nXr3WVmM5BPwU4nX05Qt9IVSVItBur8WE0";
	private DesiredCapabilities dc = new DesiredCapabilities();
	private RemoteWebDriver driver = null;
	public static final String PROJECT_PATH = System.getProperty("user.dir");
	protected static Properties prop;
	SeeTestClient seetest;
	
	public Base()
	{
		prop = returnProperty(PROJECT_PATH+"/src/test/java/com/properties/framework/data.properties");
	}

	public DesiredCapabilities sendAndroidBrowserCapabilities() throws Exception {

		
		dc.setCapability("accessKey", accessKey);
		dc.setCapability("testName", "Quick Start Android Browser Demo");
//		dc.setCapability(MobileCapabilityType.UDID,"R58M7154VQD");
		dc.setCapability("deviceQuery",
			"@os='android' and @version='" + prop.getProperty("android_version") + "' and @category='PHONE'");
		dc.setBrowserName(MobileBrowserType.CHROMIUM);
		dc.setCapability("autoDismissAlerts", true);
		return dc;
		
	}
	
	public DesiredCapabilities sendAndroidChilisAppCapabilities() throws Exception {

		
		dc.setCapability("accessKey", accessKey);
		dc.setCapability("testName", "Quick Start Android App Demo");
		dc.setCapability("deviceQuery",
			"@os='android' and @version='" + prop.getProperty("android_version") + "' and @category='PHONE'");
//		dc.setCapability(MobileCapabilityType.UDID,"R58M7154VQD");
		dc.setCapability(MobileCapabilityType.APP,prop.getProperty("app_name"));
        dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,prop.getProperty("app_package"));
        dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,prop.getProperty("app_activity"));
        dc.setCapability("appBuildVersion",prop.getProperty("appBuildVersion"));
        dc.setCapability("appReleaseVersion",prop.getProperty("appReleaseVersion"));
        dc.setCapability("installOnlyForUpdate", false);
        dc.setCapability("autoDismissAlerts", true);
		return dc;
		
	}
	public DesiredCapabilities sendIOSChilisAppCapabilities() throws Exception
	{
		dc.setCapability("accessKey", accessKey);
		dc.setCapability("testName", "Quick Start IOS App Demo");
		dc.setCapability("deviceQuery",
				"@os='ios' and @version='"+prop.getProperty("IOS_version")+"' and @category='PHONE'");
//		dc.setCapability(MobileCapabilityType.UDID,prop.getProperty("udid_IOS"));
		dc.setCapability(MobileCapabilityType.APP,prop.getProperty("IOS_app_name"));
		dc.setCapability(IOSMobileCapabilityType.BUNDLE_ID,prop.getProperty("bundle_id"));
		dc.setCapability("appBuildVersion",prop.getProperty("IOS_build_version"));
        dc.setCapability("installOnlyForUpdate", false);
        dc.setCapability("autoDismissAlerts", true);
		return dc;
		
	}	
	public DesiredCapabilities sendIOSBrowserCapabilities() throws Exception {
		
		dc.setCapability("accessKey", accessKey);
		dc.setCapability("testName", "Quick Start iOS Browser Demo");
//		dc.setCapability(MobileCapabilityType.UDID,prop.getProperty("udid_IOS"));
		dc.setCapability("deviceQuery", "@os='ios' and @version='13.2.3' and @category='PHONE'");
		dc.setBrowserName(MobileBrowserType.SAFARI);
		return dc;
	}
	
public RemoteWebDriver initializeWebDriver() throws Exception {
	prop = returnProperty(PROJECT_PATH+"/src/test/java/com/properties/framework/data.properties");
	if(prop.getProperty("browser").equals("chrome")) {

	//System.setProperty("webdriver.chrome.driver",prop.getProperty("chromedriverpath"));
	WebDriverManager.chromedriver().setup();
	ChromeOptions options = new ChromeOptions();
	options.addArguments("--process-per-tab");
	options.addArguments("--process-per-site");
	options.addArguments("--disable-plugins");	
	options.addArguments("--disable-extensions");
	options.addArguments("--disable-gpu");
	options.addArguments("--media-cache-size=1 --disk-cache-size=1");
	driver = new ChromeDriver(options);
	}
	if(prop.getProperty("browser").equals("firefox"))
	{
		WebDriverManager.firefoxdriver().setup();
		driver = new FirefoxDriver();
	}
	if(prop.getProperty("browser").equals("IE"))
	{
		WebDriverManager.iedriver().setup();
		driver=new InternetExplorerDriver();
	}
    return driver;	
}
	public void getDriver(RemoteWebDriver driver)
	{
		this.driver = driver;
		seetest = new SeeTestClient(driver);
	}
	
	public void stopTestforMobileBrowser()
	{
		System.out.println("Report URL: " + driver.getCapabilities().getCapability("reportUrl"));
		try{
			
			seetest.applicationClearData("com.briker.chilis/.features.LaunchActivity");
		}
		catch(Exception e)
		{
			driver.quit();
		}
		
	}
	
	public void stopIOSTest()
	{
		seetest.uninstall("com.chilistogo.prod");
		System.out.println("Report URL: " + driver.getCapabilities().getCapability("reportUrl"));
		driver.quit();
		
	}
	
	
	
	public void stopWebTest()
	{
		driver.close();
	    driver.quit();
		
	}
	
	public boolean getEnableStatus(String testSummary)
	{
		return true;
		
	}
	public static Properties returnProperty(String filePath) {
		prop = new Properties();
		FileInputStream fis;
		try {
			fis = new FileInputStream(filePath);
			prop.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop;
	}
	
	public void editProperties(Properties prop,String key, String value)
	{
		FileOutputStream out;
		try {
			 out = new FileOutputStream(PROJECT_PATH + "/src/test/java/com/properties/framework/platform.properties");
			prop.setProperty(key, value);
			prop.store(out,null);
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
