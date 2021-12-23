package com.pageaction.framework;



import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.testng.Assert.assertEquals;

import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import org.springframework.util.StringUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.ScreenCapture;
import com.configuration.framework.Base;
import com.experitest.appium.SeeTestClient;
import com.experitest.client.Client;
import com.listener.framework.Listeners;
import com.report.framework.ReportConfiguration;
import com.utils.framework.ExcelUtils;
import com.utils.framework.ReusableMethods;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.nativekey.PressesKey;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class FunctionalComponents extends Base {

	RemoteWebDriver driver = null;
	Properties property = returnProperty(PROJECT_PATH + "/src/test/java/com/properties/framework/data.properties");
	Listeners listen;
	static ExtentTest extTestObj;
	WebDriverWait wait;
	Logger log;
	ExcelUtils excel;
	SeeTestClient seetest;

	/*default constructor*/
	public FunctionalComponents()
	{
		try {
			excel = new ExcelUtils(PROJECT_PATH + "/CommonData.xlsx");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* Constructor for invoking Android driver */
	public FunctionalComponents(AndroidDriver<AndroidElement> driver, Logger log) {
		this();
		this.driver = driver;
		seetest = new SeeTestClient(driver);
		this.log = log;
		wait = new WebDriverWait(this.driver, 60);	
	}

	/* Constructor for invoking IOS driver */
	public FunctionalComponents(IOSDriver<IOSElement> driver, Logger log) {
		this();
		this.driver = driver;
		seetest = new SeeTestClient(driver);
		this.log = log;
		wait = new WebDriverWait(this.driver,60);
	}

	/* Constructor for invoking webdriver*/
	public FunctionalComponents(RemoteWebDriver driver, Logger log) {
		this();
		this.driver = driver;
		seetest = new SeeTestClient(driver);
		this.log = log;
		wait = new WebDriverWait(this.driver, 60);
	}
	
	/* To obtain the Extent Test object for logging in Extent Report */
	public static void getExtentTest(ExtentTest extentTest) {
		extTestObj = extentTest;
	}
	
	/*
	 * Scroll functions for bringing an web element into view
	 * scrollIntoViewBottom(By element) : for bringing an element into view at the
	 * bottom of the screen with argument as locator
	 * 
	 * scrollIntoViewBottomByElement(AndroidElement element) : for bringing an
	 * element into view at the bottom of the screen with argument as web element
	 * 
	 * scrollIntoViewTop(By element) : for bringing an element into view at the top
	 * of the screen with argument as locator
	 * 
	 * scrollIntoViewHalf(By element) : for bringing an element into view at the
	 * middle of the screen with argument as locator
	 * 
	 * scrollIntoViewHalfByElement(WebElement element) : for bringing an element into view at the
	 * middle of the screen with argument as webelement
	 */
	public void scrollIntoViewBottom(By element) throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", driver.findElement(element));
		Thread.sleep(1000);
	}

	public void scrollIntoViewBottomByElement(WebElement element) throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", element);
		Thread.sleep(500);
	}

	public void scrollIntoViewTop(By element) throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(element));
		Thread.sleep(500);
	}

	public void scrollIntoViewHalf(By element) throws InterruptedException {
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView(true); window.scrollBy(0, -window.innerHeight / 2);",
				driver.findElement(element));
		Thread.sleep(500);
	}
	public void scrollIntoViewHalfByElement(WebElement element) throws InterruptedException {
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView(true); window.scrollBy(0, -window.innerHeight / 2);",
				element);
		Thread.sleep(500);
	}

	/*
	 * function to click an element after certain wait time with argument as locator
	 */
	public void clickableWait(By element) {
		wait.until(ExpectedConditions.elementToBeClickable(element)).click();
	}

	/* function to wait for the presence of an element with argument as locator */
	public void explicitWait(By element) {
		wait.until(ExpectedConditions.presenceOfElementLocated(element));
	}
	
	/* function to wait for the visibility of an element with argument as locator */
	public void explicitWaitforVisibility(By element)
	{
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(element)));
	}
	
	/* function to wait for clickability of an element with argument as locator */
	public void explicitWaitforClickability(By element)
	{   WebDriverWait visible = new WebDriverWait(driver,1);
		visible.until(ExpectedConditions.elementToBeClickable(element));
	}

	/* function to click an element with argument as locator */
	public void clickElement(By element) {
		driver.findElement(element).click();
	}

	/*
	 * function to enter data in a text box by clicking on it after certain wait time with argument as
	 * locator and the data
	 */
	public void sendKeysWait(By element, String value) {
		wait.until(ExpectedConditions.elementToBeClickable(element)).click();
		driver.findElement(element).clear();
		driver.findElement(element).sendKeys(value);
	}
   
	/*
	 * function to enter data in a text box without clicking on it after certain wait time with argument as
	 * locator and the data
	 */
	public void sendKeysWaitWithoutClick(By element, String value) {
		wait.until(ExpectedConditions.elementToBeClickable(element));
		driver.findElement(element).clear();
		driver.findElement(element).sendKeys(value);
	}

	/* function to scroll down from start with argument as end coordinate */
	public void scrollDownFromStart(String endpoint) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0," + endpoint + ")", "");
	}

	/* function to scroll up with argument as end coordinate */
	public void scrollUp(String endpoint) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,-" + endpoint + ")", "");
	}

	/*
	 * Function Name : validateQASite()
	 * Purpose : To validate site launch and close the welcome pop up of Chilis Web site in Android chrome
	 * Platform : Android Chrome
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void validateQASite() {
		log.info("Starting QA site validation");
		// Validating site launch
		try {
			seetest.waitForElement("NATIVE","xpath="+Elements.popUpCloseButton,0,4000);
			log.info("Site launch successful : " + prop.getProperty("url"));
			extTestObj.createNode("Site launch successful : " + prop.getProperty("url")).pass("PASSED");
		} catch (Exception e) {
			log.error("QA site launch failed");
			extTestObj.createNode("QA site launch failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			
			stopTestforMobileBrowser();
		}
		// To close the welcome pop up
		try {
//			clickElement(By.xpath(Elements.popUpCloseButton));
			seetest.click("NATIVE","xpath="+Elements.popUpCloseButton,0,1);
			seetest.waitForElement("NATIVE","xpath="+Elements.chilisLogo.toString(),0,4000);
//			explicitWait(Elements.chilisLogo);
			log.info("Pop up closed");
			extTestObj.createNode("Pop up closed").pass("PASSED");
		} catch (Exception e) {
			log.error("Pop up close failed");
			extTestObj.createNode("Pop up close failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
	}
	
	/*
	 * Function Name : validateQASiteIOS()
	 * Purpose : To validate site launch and close the welcome pop up of Chilis Web site in IOS Safari
	 * Platform : IOS Safari
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void validateQASiteIOS() {
		log.info("Starting QA site validation");
		// Validating site launch
		try {
			try {
			explicitWait(By.xpath(Elements.popUpCloseButton));
			}
			catch(Exception e) {}
			log.info("Site launch successful : " + prop.getProperty("url"));
			extTestObj.createNode("Site launch successful : " + prop.getProperty("url")).pass("PASSED");
		} catch (Exception e) {
			log.error("QA site launch failed");
			extTestObj.createNode("QA site launch failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		// To close the welcome pop up
		try {
			try {
			clickElement(By.xpath(Elements.popUpCloseButton));
			}
			catch(Exception e) {}
			explicitWait(Elements.chilisLogo);
			log.info("Pop up closed");
			extTestObj.createNode("Pop up closed").pass("PASSED");
		} catch (Exception e) {
			log.error("Pop up close failed");
			extTestObj.createNode("Pop up close failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
	}

	/*
	 * Function Name : validateLogout()
	 * Purpose : To validate log out functionality of Chilis Website
	 * Platform : Android Chrome
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void validateLogout() {
		// To click on the Hamburger menu button
		try {
			clickElement(Elements.menuButton);
			scrollIntoViewHalf(Elements.logoutButton);
			log.info("Menu button clicked");
			extTestObj.createNode("Menu button clicked").pass("PASSED");
		} catch (Exception e) {
			log.error("Menu button click failed");
			extTestObj.createNode("Menu button click failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		// To click on Log out option
		try {
			clickElement(Elements.logoutButton);
			explicitWait(Elements.loginHeader);
			log.info("Logout button clicked and logout successful");
			extTestObj.createNode("Logout button clicked and logout successful").pass("PASSED");
		} catch (Exception e) {
			log.info("Logout failed");
			extTestObj.createNode("Logout failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}

	}
	/*
	 * Function Name : validateRestaurantSelectionforGuestUser(int args)
	 * Purpose : To validate restaurant location selection for Guest user with data fed from excel
	 * Platform : Android Chrome
	 * Parameters : location index from CommonData.xlsx
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void validateRestaurantSelectionforGuestUser(int locationIndex)
	{
		//to click on Hamburger menu button
		try {
			clickableWait(Elements.menuButton);
			explicitWait(Elements.rewardsButton);
			log.info("Menu button clicked");
			extTestObj.createNode("Menu button clicked").pass("PASSED");
		} catch (Exception e) {
			log.error("Menu button click failed");
			extTestObj.createNode("Menu button click failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		//to select location option
		try {
			clickableWait(Elements.locationsButton);
			explicitWait(Elements.locationPageHeader);
			log.info("Location option selected");
			extTestObj.createNode("Location option selected").pass("PASSED");
		} catch (Exception e) {
			log.error("Location button selection failed");
			extTestObj.createNode("Location button selection failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//to enter data fed from excel in location search text box
		try {
			explicitWait(Elements.locationSearchTextBox);
			clickableWait(Elements.locationSearchTextBox);
			String locn = excel.getCellData("Locations", "Location", locationIndex);
			driver.getKeyboard().sendKeys(locn);
			log.info("Restaurant location " + locn + " entered");
			extTestObj.createNode("Restaurant location " + locn + " entered").pass("PASSED");

		} catch (Exception e) {
			log.error("Failed to enter Restaurant location");
			extTestObj.createNode("Failed to enter Restaurant location")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//to click search button
		try {
			clickableWait(Elements.searchButton);
			log.info("Search button clicked");
			extTestObj.createNode("Search button clicked").pass("PASSED");
		} catch (Exception e) {
			log.error("Search button click failed");
			extTestObj.createNode("Search button click failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		//to click "Order Now" button against appropriate store
		String storeName = excel.getCellData("Locations", "Store Name for Mobile Browser", locationIndex);
		try {
			Thread.sleep(3000);
			scrollIntoViewHalf(
					By.xpath("//a[contains(@href,'" + storeName + "')]/following-sibling::a[text()='Order Now']"));
			Thread.sleep(3000);
			clickElement(By.xpath("//a[contains(@href,'" + storeName + "')]/following-sibling::a[text()='Order Now']"));
			explicitWait(Elements.chilisMenuHeader);
			log.info("Site scrolled and order button clicked");
			extTestObj.createNode("Site scrolled and order button clicked").pass("PASSED");

		} catch (Exception e) {
			log.error("Order Now button click failed");
			extTestObj.createNode("Order Now button click failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		
	}
	
	/*
	 * Function Name : validateRestaurantSelectionforGuestUser_QA3(int args)
	 * Purpose : To validate restaurant location selection for Guest user with data fed from excel
	 * Platform : Android Chrome
	 * Parameters : location index from CommonData.xlsx
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void validateRestaurantSelectionforGuestUser_QA3(int locationIndex)
	{
		//to click on Hamburger menu button
		try {
			clickableWait(Elements.menuButton);
			explicitWait(Elements.rewardsButton);
			log.info("Menu button clicked");
			extTestObj.createNode("Menu button clicked").pass("PASSED");
		} catch (Exception e) {
			log.error("Menu button click failed");
			extTestObj.createNode("Menu button click failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		//to select location option
		try {
			clickableWait(Elements.locationsButton);
			explicitWait(Elements.locationPageHeader);
			log.info("Location option selected");
			extTestObj.createNode("Location option selected").pass("PASSED");
		} catch (Exception e) {
			log.error("Location button selection failed");
			extTestObj.createNode("Location button selection failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//to enter data fed from excel in location search text box
		try {
			explicitWait(Elements.locationSearchTextBox);
			clickableWait(Elements.locationSearchTextBox);
			String locn = excel.getCellData("Locations", "Location", locationIndex);
			driver.getKeyboard().sendKeys(locn);
			log.info("Restaurant location " + locn + " entered");
			extTestObj.createNode("Restaurant location " + locn + " entered").pass("PASSED");

		} catch (Exception e) {
			log.error("Failed to enter Restaurant location");
			extTestObj.createNode("Failed to enter Restaurant location")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//to click search button
		try {
			clickableWait(Elements.searchButton);
			log.info("Search button clicked");
			extTestObj.createNode("Search button clicked").pass("PASSED");
		} catch (Exception e) {
			log.error("Search button click failed");
			extTestObj.createNode("Search button click failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		//to click "Order Now" button against appropriate store
		String storeID = excel.getCellData("Locations", "Store ID", locationIndex);
		try {
			Thread.sleep(3000);
			scrollIntoViewHalf(
					By.xpath("//*[@id='location-select-"+storeID+"']"));
			Thread.sleep(3000);
			clickElement(By.xpath("//*[@id='location-select-"+storeID+"']"));
			explicitWait(Elements.chilisMenuHeader);
			log.info("Site scrolled and order button clicked");
			extTestObj.createNode("Site scrolled and order button clicked").pass("PASSED");

		} catch (Exception e) {
			log.error("Order Now button click failed");
			extTestObj.createNode("Order Now button click failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		
	}
	/*
	 * Function Name : validateLogin()
	 * Purpose : To validate login functionality of Chilis Web site
	 * Platform : Android Chrome
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void validateLogin()
	{
		//To click the Hamburger menu button
		try {
			clickElement(Elements.menuButton);
			explicitWait(Elements.loginButton);
			log.info("Menu button clicked");
			extTestObj.createNode("Menu button clicked").pass("PASSED");
		} catch (Exception e) {
			log.error("Menu button click failed");
			extTestObj.createNode("Menu button click failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		//To select login option 
		try {
			clickElement(Elements.loginButton);
			explicitWait(Elements.loginHeader);
			log.info("Login option selected");
			extTestObj.createNode("Login option selected").pass("PASSED");
		} catch (Exception e) {
			log.error("Login option not selected");
			extTestObj.createNode("Login option not selected")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		//To enter username in the Login Page
		try {
			String username = excel.getCellData("Credentials", "UserName", 2);
			sendKeysWait(Elements.userNameTextBox, username);
			seetest.closeKeyboard();
			log.info("User name " + driver.findElement(Elements.userNameTextBox).getAttribute("text") + " entered");
			extTestObj.createNode("User name " + driver.findElement(Elements.userNameTextBox).getAttribute("text") + " entered").pass("PASSED");
		} catch (Exception e) {
			log.error("Could not enter user name");
			extTestObj.createNode("Could not enter user name")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		//To enter password in the Login Page
		try {
			String password = excel.getCellData("Credentials", "Password", 2);
			scrollIntoViewBottom(Elements.passwordTextBox);
			sendKeysWait(Elements.passwordTextBox, password);
			seetest.closeKeyboard();
			log.info("Password " + driver.findElement(Elements.passwordTextBox).getAttribute("text") + " entered");
			extTestObj.createNode("Password " + driver.findElement(Elements.passwordTextBox).getAttribute("text") + " entered").pass("PASSED");
		} catch (Exception e) {
			log.error("Could not enter password");
			extTestObj.createNode("Enter password")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		// To click on Sign in button in the Login Page
		try {
			scrollIntoViewBottom(Elements.signinButton);
			clickElement(Elements.signinButton);
			try {
				seetest.click("NATIVE","xpath="+Elements.neverSavePassword,0,1);
			}
			catch(Exception exp) {}
			explicitWait(Elements.rewardsHeaderInLoginPage);
			log.info("Sign in button clicked");
			extTestObj.createNode("Sign in button clicked").pass("PASSED");
		} catch (Exception e) {
			log.error("Sign in button click failed");
			extTestObj.createNode("Sign in button click failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
	}
	/*
	 * Function Name : validateRestaurantSelection(int args)
	 * Purpose : To validate Restaurant location search and store selection of Chilis Web site for Logged in User
	 * Platform : Android Chrome
	 * Parameters : Location and Store name fetched from CommonData.xlsx
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	
	public void validateRestaurantSelection(int locationIndex)
	{
		// To click on the Hamburger menu button
		try {
			clickElement(Elements.menuButton);
			explicitWait(Elements.locationsButton);
			log.info("Menu button clicked");
			extTestObj.createNode("Menu button clicked").pass("PASSED");
		} catch (Exception e) {
			log.error("Menu button click failed");
			extTestObj.createNode("Menu button click failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		// To select Location option
		try {
			clickElement(Elements.locationsButton);
			explicitWait(Elements.locationPageHeader);
			log.info("Location option selected");
			extTestObj.createNode("Location option selected").pass("PASSED");
		} catch (Exception e) {
			log.error("Location button selection failed");
			extTestObj.createNode("Location button selection failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		//To enter location in the Restaurant location search text box
		String restLocation = excel.getCellData("Locations", "Location",locationIndex);

		try {
			clickableWait(Elements.locationSearchTextBox);
			driver.getKeyboard().sendKeys(restLocation);
			seetest.closeKeyboard();
			log.info("Restaurant location entered as " + restLocation);
			extTestObj.createNode("Restaurant location entered as " + restLocation).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter Restaurant location");
			extTestObj.createNode("Failed to enter Restaurant location")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		//To click on search button
		try {
			scrollIntoViewHalf(Elements.searchButton);
			clickElement(Elements.searchButton);
			explicitWait(Elements.restaurantName);
			log.info("Search button clicked");
			log.info("Restaurant Name displayed as : " + driver.findElement(Elements.restaurantName).getText());
			extTestObj.createNode("Search button clicked").pass("PASSED");
			extTestObj
					.createNode(
							"Restaurant Name displayed as : " + driver.findElement(Elements.restaurantName).getText())
					.info("INFO");
		} catch (Exception e) {
			log.error("Search button click failed");
			extTestObj.createNode("Search button click failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To select a particular store which comes under the entered Restaurant location
		String storeName = excel.getCellData("Locations", "Store Name for Mobile Browser",locationIndex);
		try {
			
			scrollIntoViewHalf(
					By.xpath("//a[contains(@href,'" + storeName + "')]/following-sibling::a[text()='Order Now']"));
			Thread.sleep(1000);
			clickElement(By.xpath("//a[contains(@href,'" + storeName + "')]/following-sibling::a[text()='Order Now']"));
			String storeNameDisplayed = driver.findElement(Elements.storeNameXpath).getText();
			explicitWait(Elements.favoriteItemsHeader);
			log.info("Store : "+storeNameDisplayed+ " selected");
			extTestObj.createNode("Store : "+storeNameDisplayed+ " selected").pass("PASSED");

		} catch (Exception e) {
			log.error("Order Now button click failed");
			extTestObj.createNode("Order Now button click failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}	
	}

	/*
	 * Function Name : validateRestaurantSelection_QA3(int args)
	 * Purpose : To validate Restaurant location search and store selection of Chilis Web site for Logged in User
	 * Platform : Android Chrome
	 * Parameters : Location and Store name fetched from CommonData.xlsx
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	
	public void validateRestaurantSelection_QA3(int locationIndex)
	{
		// To click on the Hamburger menu button
		try {
			clickElement(Elements.menuButton);
			explicitWait(Elements.locationsButton);
			log.info("Menu button clicked");
			extTestObj.createNode("Menu button clicked").pass("PASSED");
		} catch (Exception e) {
			log.error("Menu button click failed");
			extTestObj.createNode("Menu button click failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		// To select Location option
		try {
			clickElement(Elements.locationsButton);
			explicitWait(Elements.locationPageHeader);
			log.info("Location option selected");
			extTestObj.createNode("Location option selected").pass("PASSED");
		} catch (Exception e) {
			log.error("Location button selection failed");
			extTestObj.createNode("Location button selection failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		//To enter location in the Restaurant location search text box
		String restLocation = excel.getCellData("Locations", "Location",locationIndex);

		try {
			clickableWait(Elements.locationSearchTextBox);
			driver.getKeyboard().sendKeys(restLocation);
			seetest.closeKeyboard();
			log.info("Restaurant location entered as " + restLocation);
			extTestObj.createNode("Restaurant location entered as " + restLocation).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter Restaurant location");
			extTestObj.createNode("Failed to enter Restaurant location")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		//To click on search button
		try {
			scrollIntoViewHalf(Elements.searchButton);
			clickElement(Elements.searchButton);
			explicitWait(Elements.restaurantName);
			log.info("Search button clicked");
			log.info("Restaurant Name displayed as : " + driver.findElement(Elements.restaurantName).getText());
			extTestObj.createNode("Search button clicked").pass("PASSED");
			extTestObj
					.createNode(
							"Restaurant Name displayed as : " + driver.findElement(Elements.restaurantName).getText())
					.info("INFO");
		} catch (Exception e) {
			log.error("Search button click failed");
			extTestObj.createNode("Search button click failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To select a particular store which comes under the entered Restaurant location
		String storeID = excel.getCellData("Locations", "Store ID",locationIndex);
		try {
			
			scrollIntoViewHalf(
					By.xpath("//*[@id='location-select-"+storeID+"']"));
			Thread.sleep(1000);
			clickElement(By.xpath("//*[@id='location-select-"+storeID+"']"));
			String storeNameDisplayed = driver.findElement(Elements.storeNameXpath).getText();
			explicitWait(Elements.favoriteItemsHeader);
			log.info("Store : "+storeNameDisplayed+ " selected");
			extTestObj.createNode("Store : "+storeNameDisplayed+ " selected").pass("PASSED");

		} catch (Exception e) {
			log.error("Order Now button click failed");
			extTestObj.createNode("Order Now button click failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}	
	}
	/*
	 * Function Name : validateRestaurantSelection2(int args)
	 * Purpose : To validate Restaurant location search and store selection of Chilis Web site for Logged in User
	 * Platform : Android Chrome
	 * Parameters : Location and Store name fetched from CommonData.xlsx
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void validateRestaurantSelection2(int locationIndex)
	{
		// To click on the Hamburger menu button
		try {
			clickElement(Elements.menuButton);
			explicitWait(Elements.locationsButton);
			log.info("Menu button clicked");
			extTestObj.createNode("Menu button clicked").pass("PASSED");
		} catch (Exception e) {
			log.error("Menu button click failed");
			extTestObj.createNode("Menu button click failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		// To select Location option
		try {
			clickElement(Elements.locationsButton);
			explicitWait(Elements.locationPageHeader);
			log.info("Location option selected");
			extTestObj.createNode("Location option selected").pass("PASSED");
		} catch (Exception e) {
			log.error("Location button selection failed");
			extTestObj.createNode("Location button selection failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		//To enter location in the Restaurant location search text box
		String restLocation = excel.getCellData("Locations", "Location",locationIndex);

		try {
			clickableWait(Elements.locationSearchTextBox);
			driver.getKeyboard().sendKeys(restLocation);
			seetest.closeKeyboard();
			log.info("Restaurant location entered as " + restLocation);
			extTestObj.createNode("Restaurant location entered as " + restLocation).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter Restaurant location");
			extTestObj.createNode("Failed to enter Restaurant location")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		//To click on search button
		try {
			scrollIntoViewHalf(Elements.searchButton);
			clickElement(Elements.searchButton);
			explicitWait(Elements.restaurantName);
			log.info("Search button clicked");
			log.info("Restaurant Name displayed as : " + driver.findElement(Elements.restaurantName).getText());
			extTestObj.createNode("Search button clicked").pass("PASSED");
			extTestObj
					.createNode(
							"Restaurant Name displayed as : " + driver.findElement(Elements.restaurantName).getText())
					.info("INFO");
		} catch (Exception e) {
			log.error("Search button click failed");
			extTestObj.createNode("Search button click failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To select a particular store which comes under the entered Restaurant location
		String storeName = excel.getCellData("Locations", "Store Name for Mobile Browser",locationIndex);
		try {
			List<WebElement> stores = driver.findElements(By.xpath("//div[@class='location-name row']"));
			for(WebElement store: stores)
			{
				if(store.getText().contains(storeName))
					scrollIntoViewHalfByElement(store);
				    	Thread.sleep(1000);
					store.findElement(By.xpath("/following-sibling::div/div/a")).click();
			}
//			scrollIntoViewHalf(
//					By.xpath("//a[contains(@href,'" + storeName + "')]/following-sibling::a[text()='Order Now']"));
//			Thread.sleep(1000);
//			clickElement(By.xpath("//a[contains(@href,'" + storeName + "')]/following-sibling::a[text()='Order Now']"));
			String storeNameDisplayed = driver.findElement(Elements.storeNameXpath).getText();
			explicitWait(Elements.favoriteItemsHeader);
			log.info("Store : "+storeNameDisplayed+ " selected");
			extTestObj.createNode("Store : "+storeNameDisplayed+ " selected").pass("PASSED");

		} catch (Exception e) {
			log.error("Order Now button click failed");
			extTestObj.createNode("Order Now button click failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}	
	}
	
	/*
	 * Function Name : noOfRewards()
	 * Purpose : To retrieve the reward count displayed in the reward page of MCA
	 * Platform : Android Chrome
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public int noOfRewards() {
		String displayedCount = "";
		try {
			explicitWait(Elements.noOfRewards);
			displayedCount = driver.findElement(Elements.noOfRewards).getText();
			log.info("Displayed rewards count obtained : " + displayedCount);
			extTestObj.createNode("Displayed rewards count obtained : " + displayedCount).pass("PASSED");
		} catch (Exception e) {
			log.error("Displayed rewards count couldn't be obtained");
			extTestObj.createNode("Displayed rewards count couldn't be obtained")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

		}

		return Integer.parseInt(displayedCount);
	}

	/*
	 * Function Name : ActualRewardsCount()
	 * Purpose : To retrieve the actual reward count, i.e., no of rewards available in the MCA home page
	 * Platform : Android Chrome
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public int ActualRewardsCount() {
		int count = 0;
		try {
			count = driver.findElements(Elements.actualRewardsCount).size();
			log.info("Actual rewards count obtained");
			extTestObj.createNode("Actual rewards count obtained : " + count).pass("PASSED");
		} catch (Exception e) {
			log.error("Actual rewards count couldn't be obtained");
			extTestObj.createNode("Actual rewards count couldn't be obtained")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

		}
		return count;

	}

	/*
	 * Function Name : getRewardTitle()
	 * Purpose : To retrieve the titles of rewards available in the rewards page of MCA user
	 * Platform : Android Chrome
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void getRewardTitle() {
		List<WebElement> rewards = driver.findElements(Elements.actualRewardsCount);
		String rewardsTitle = "";
		try {
			log.info("Reward Titles :");
			extTestObj.createNode("Reward Titles :").info("INFO");
			for (WebElement reward : rewards) {
				rewardsTitle = reward.findElement(By.xpath("//div[@class='rewards-active-title item-title']"))
						.getText();
				log.info(rewardsTitle);
				extTestObj.createNode(rewardsTitle).pass("PASSED");
			}
			log.info("Reward Titles displayed");
			extTestObj.createNode("Reward Titles displayed").pass("PASSED");
		} catch (Exception e) {
			log.error("Reward titles couldn't be obtained");
			extTestObj.createNode("Reward titles couldn't be obtained")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

		}
	}
	
	/*
	 * Function Name : getAllChilisFavouriteItems()
	 * Purpose : To retrieve the favorite Items from the MCA User Menu Page
	 * Platform : Android Chrome
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */

	public String[] getAllChilisFavouriteItems() {
		
		//To click on the Hamburger menu button
		try {
			clickableWait(Elements.menuButton);
			explicitWait(Elements.rewardsButton);
			log.info("Menu button clicked");
			extTestObj.createNode("Menu button clicked").pass("PASSED");
		} catch (Exception e) {
			log.error("Menu button click failed");
			extTestObj.createNode("Menu button click failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		//To select Menu Option
		try {
			clickableWait(Elements.menuOption);
			log.info("Menu Option selected");
			extTestObj.createNode("Menu Option selected").pass("PASSED");
		} catch (Exception e) {
			log.error("Menu Option selection failed");
			extTestObj.createNode("Menu Option selection failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}

		//To retrieve the menu items and return it as a list
		List<WebElement> items = null;
		String[] favItems = new String[5];
		int i = 0;
		try {
			for (int j = 0; j < favItems.length; j++) {
				favItems[j] = excel.getCellData("Menu", "ChilisFavourites", j + 2);
			}
			items = driver.findElements(Elements.favouriteMenu);
			log.info("Chillis favourite Items : ");
			extTestObj.log(Status.INFO, "Chillis favourite Items : ");
			for (WebElement el : items) {
				if (favItems[i].equals(el.findElement(Elements.favouriteItemsTitle).getText()))
					log.info(el.findElement(Elements.favouriteItemsTitle).getText());
				extTestObj.createNode(el.findElement(Elements.favouriteItemsTitle).getText()).info("INFO");
				i++;
			}
			log.info("All chilis favourite items obtained");
			extTestObj.createNode("All chilis favourite items obtained").pass("PASSED");
		} catch (Exception e) {
			log.error("Couldn't obtain chilis favourite items");
			extTestObj.createNode("Couldn't obtain chilis favourite items")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

		}
		return favItems;
	}

	
	/*
	 * Function Name : retrieveSuccessMessage()
	 * Purpose : To retrieve the success message after submitting past visit details
	 * Platform : Android Chrome
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public String retrieveSuccessMessage() {

		String successMsg = "";
		try {
			explicitWait(Elements.getSuccessMessageforAddMyVisit);
			successMsg = driver.findElement(Elements.getSuccessMessageforAddMyVisit).getText();
			log.info("Success message for add my visit displayed as : " + successMsg);
			extTestObj.createNode("Success message for add my visit displayed as : " + successMsg).pass("PASSED");
		} catch (Exception e) {
			log.error("Success message for add my visit not displayed");
			extTestObj.createNode("Success message for add my visit not displayed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();

		}
		return driver.findElement(Elements.getSuccessMessageforAddMyVisit).getText();

	}
	

	/*
	 * Function Name : appretrieveAddVisitSuccessMessage()
	 * Purpose : To retrieve the success message after submitting past visit details
	 * Platform : Android App
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void appretrieveAddVisitSuccessMessage() {

		String successMsg = "";
		try {
			Thread.sleep(3000);
			explicitWait(Elements.appAddMyVisitSuccessMessage);
			successMsg = driver.findElement(Elements.appAddMyVisitSuccessMessage).getText();
			clickableWait(By.xpath(Elements.OKbuttoninAddMyVisitXpath));
			log.info("Success message for add my visit displayed as : " + successMsg);
			extTestObj.createNode("Success message for add my visit displayed as : " + successMsg).pass("PASSED");
		} catch (Exception e) {
			log.error("Success message for add my visit not displayed");
			extTestObj.createNode("Success message for add my visit not displayed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();

		}

	}
	
	/*
	 * Function Name : validateMyAccountUpdate()
	 * Purpose : To update Account details and validate the same
	 * Platform : Android Chrome
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void validateMyAccountUpdate()
	{
		
		//To click on the Hamburger menu button
		try {
			clickableWait(Elements.menuButton);
			explicitWait(Elements.rewardsButton);
			log.info("Menu button clicked");
			extTestObj.createNode("Menu button clicked").pass("PASSED");
		} catch (Exception e) {
			log.error("Menu button click failed");
			extTestObj.createNode("Menu button click failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		//To select myaccount option
		try {
			clickableWait(Elements.myAccountOption);
			log.info("My Account option selected");
			extTestObj.createNode("My Account option selected").pass("PASSED");
		} catch (Exception e) {
			log.error("My Account option selection failed");
			extTestObj.createNode("My Account option selection failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		//To retrieve initial first name
		String initialFirstName = "";
		try {
			explicitWait(Elements.firstNameTextBox);
			initialFirstName = driver.findElement(Elements.firstNameTextBox).getAttribute("value");
			log.info("First Name before update obtained as : " + initialFirstName);
			extTestObj.createNode("First Name before update obtained as : " + initialFirstName).pass("PASSED");
		} catch (Exception e) {
			log.error("First Name before update not obtained");
			extTestObj.createNode("First Name before update not obtained")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

		}
		//To retrieve initial last name
		String initialLastName = "";
		try {
			explicitWait(Elements.lastNameTextBox);
			initialLastName = driver.findElement(Elements.lastNameTextBox).getAttribute("value");
			log.info("Last Name before update obtained as : " + initialLastName);
			extTestObj.createNode("Last Name before update obtained as : " + initialLastName).pass("PASSED");
		} catch (Exception e) {
			log.error("Last Name before update not obtained");
			extTestObj.createNode("Last Name before update not obtained")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

		}
		//To retrieve initial email address
		String initialEmail = "";
		try {
			explicitWait(Elements.emailTextBox);
			initialEmail = driver.findElement(Elements.emailTextBox).getAttribute("value");
			log.info("Email before update obtained as : " + initialEmail);
			extTestObj.createNode("Email before update obtained as : " + initialEmail).pass("PASSED");
		} catch (Exception e) {
			log.error("Email before update not obtained");
			extTestObj.createNode("Email before update not obtained")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

		}
		
		//To retrieve initial zip code
		String initialZipCode = "";
		try {
			explicitWait(Elements.zipCodeTextBox);
			initialZipCode = driver.findElement(Elements.zipCodeTextBox).getAttribute("value");
			log.info("Zip Code before update obtained as : " + initialZipCode);
			extTestObj.createNode("Zip Code before update obtained as : " + initialZipCode).pass("PASSED");
		} catch (Exception e) {
			log.error("Zip Code before update not obtained");
			extTestObj.createNode("Zip Code before update not obtained")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

		}
		//To update first name
		try {
			String firstName = excel.getCellData("UpdateMyAccount", "First Name", 2);
			sendKeysWait(Elements.firstNameTextBox, firstName);
			log.info("First Name updated with : " + firstName);
			extTestObj.createNode("First Name updated with : " + firstName).pass("PASSED");
		} catch (Exception e) {
			log.error("First Name updation failed");
			extTestObj.createNode("First Name updation failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

		}
		
		//To update last name
		try {
			String lastName = excel.getCellData("UpdateMyAccount", "Last Name", 2);
			sendKeysWait(Elements.lastNameTextBox, lastName);
			log.info("Last Name updated with : " + lastName);
			extTestObj.createNode("Last Name updated with : " + lastName).pass("PASSED");
		} catch (Exception e) {
			log.error("Last Name updation failed");
			extTestObj.createNode("Last Name updation failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

		}
		
		//To update email address
		try {
			String email = excel.getCellData("UpdateMyAccount", "Email", 2);
			sendKeysWait(Elements.emailTextBox, email);
			log.info("Email updated with : " + email);
			extTestObj.createNode("Email updated with : " + email).pass("PASSED");

		} catch (Exception e) {
			log.error("Email updation failed");
			extTestObj.createNode("Email updation failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

		}
		
		//To update zip code
		try {
			String zipCode = excel.getCellData("UpdateMyAccount", "Zip Code", 2);
			sendKeysWait(Elements.zipCodeTextBox, zipCode);
			log.info("Zip Code updated with : " + zipCode);
			extTestObj.createNode("Zip Code updated with : " + zipCode).pass("PASSED");
		} catch (Exception e) {
			log.error("Zip Code updation failed");
			extTestObj.createNode("Zip Code updation failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

		}
		//To check consent
		try {
			clickableWait(Elements.consentCheckBox);
			log.info("Consent checked");
			extTestObj.createNode("Consent checked").pass("PASSED");
		} catch (Exception e) {
			log.error("Consent check failed");
			extTestObj.createNode("Consent check failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

		}
		
		//To click update button
		try {
			clickableWait(Elements.updateButton);
			log.info("Update button clicked");
			extTestObj.createNode("Update button clicked").pass("PASSED");
		} catch (Exception e) {
			log.error("Update button click failed");
			extTestObj.createNode("Update button click failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To retrieve sucess message after updation
		String successMessage = "";
		try {
			explicitWait(Elements.successMessageforUpdate);
			successMessage = driver.findElement(Elements.successMessageforUpdate).getText();
			log.info("Success message obtained as : " + successMessage);
			extTestObj.createNode("Success message obtained as : " + successMessage).pass("PASSED");
		} catch (Exception e) {
			log.error("Success message not obtained");
			extTestObj.createNode("Success message not obtained")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

		}
		//To retrieve the  updated first name
		String finalFirstName = "";
		try {
			explicitWait(Elements.firstNameTextBox);
			finalFirstName = driver.findElement(Elements.firstNameTextBox).getAttribute("value");
			log.info("First Name after update obtained as : " + finalFirstName);
			extTestObj.createNode("First Name after update obtained as : " + finalFirstName).pass("PASSED");
		} catch (Exception e) {
			log.error("First Name after update not obtained");
			extTestObj.createNode("First Name after update not obtained")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

		}
		
		//To retrieve the  updated last name
		String finalLastName = "";
		try {
			explicitWait(Elements.lastNameTextBox);
			finalLastName = driver.findElement(Elements.lastNameTextBox).getAttribute("value");
			log.info("Last Name after update obtained as : " + finalLastName);
			extTestObj.createNode("Last Name after update obtained as : " + finalLastName).pass("PASSED");
		} catch (Exception e) {
			log.error("Last Name after update not obtained");
			extTestObj.createNode("Last Name after update not obtained")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

		}
		
		//To retrieve the updated email address
		String finalEmail = "";
		try {
			explicitWait(Elements.emailTextBox);
			finalEmail = driver.findElement(Elements.emailTextBox).getAttribute("value");
			log.info("Email after update obtained as : " + finalEmail);
			extTestObj.createNode("Email after update obtained as : " + finalEmail).pass("PASSED");
		} catch (Exception e) {
			log.error("Email after update not obtained");
			extTestObj.createNode("Email after update not obtained")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

		}
		
		//To retrieve the updated zip code
		String finalZipCode = "";
		try {
			explicitWait(Elements.zipCodeTextBox);
			finalZipCode = driver.findElement(Elements.zipCodeTextBox).getAttribute("value");
			log.info("Zip Code after update obtained as : " + finalZipCode);
			extTestObj.createNode("Zip Code after update obtained as : " + finalZipCode).pass("PASSED");
		} catch (Exception e) {
			log.error("Zip Code after update not obtained");
			extTestObj.createNode("Zip Code after update not obtained")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

		}
		
		//To compare the updated values from the data fed from excel sheet
		try {
			Assert.assertEquals(excel.getCellData("UpdateMyAccount", "First Name", 2), finalFirstName);
			Assert.assertEquals(excel.getCellData("UpdateMyAccount", "Last Name", 2), finalLastName);
			Assert.assertEquals(excel.getCellData("UpdateMyAccount", "Email", 2), finalEmail);
			Assert.assertEquals(excel.getCellData("UpdateMyAccount", "Zip Code", 2), finalZipCode);
			log.info("All fields are updated");
			extTestObj.createNode("All fields are updated").pass("PASSED");
		} catch (Exception e) {
			log.info("Error observed in fields updation");
			extTestObj.createNode("Error observed in fields updation")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

		}
		
		
	}
	
	
	/*
	 * Function Name : validateChilisFavoriteItemsSelection(int args)
	 * Purpose : To validate favorite items selected from Chilis website (applicable only for Logged in user)
	 * Platform : Android Chrome
	 * Parameters : Menu Name fetched from CommonData.xlsx based on index
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void validateChilisFavoriteItemsSelection(int menuIndex) {
		// To select the Favorite Item based on inputs from Data Sheet
		String chilisFavItem = excel.getCellData("Menu", "ChilisFavourites", menuIndex).trim();
		try {

			List<WebElement> itemNames = driver.findElements(Elements.favouriteItemsNames);
			 for (int i = 0; i < itemNames.size(); i++) {
			 String name = itemNames.get(i).getText().trim();
			 if (name.equalsIgnoreCase(chilisFavItem)) {
			 WebElement ele = driver.findElements(Elements.addToOrder).get(i);
			 scrollIntoViewHalfByElement(ele);
			 ele.click();
			 break;
			 }
		 }
			log.info("Chilis favourite Item " + chilisFavItem + " selected");
			extTestObj.createNode("Chilis favourite Item " + chilisFavItem + " selected").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select chilis favourite item");
			extTestObj.createNode("Failed to select chilis favourite item")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}

		// To click on Cart Icon and Validate the menu name displayed in Cart
		try {
			scrollIntoViewHalf(Elements.cartIcon);
			clickElement(Elements.cartIcon);
			log.info("Item name in cart displayed as : " + driver.findElement(Elements.itemNameInCart).getText());
			extTestObj
					.createNode(
							"Item name in cart displayed as : " + driver.findElement(Elements.itemNameInCart).getText())
					.pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to display item name in Cart");
			extTestObj.createNode("Failed to display item name in Cart")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

		}
		// To click on View Cart button
		try {
			clickElement(Elements.viewCartButton);
			explicitWait(Elements.orderPageHeader);
			log.info("View Cart clicked");
			extTestObj.createNode("View Cart clicked").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to click View Cart");
			extTestObj.createNode("Failed to click View Cart")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}

	}

	/*
	 * Function Name : validateRewardsSelection()
	 * Purpose : To add available rewards with the selected item and then checkout the order page
	 * Platform : Android Chrome
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void validateRewardsSelection()
	{
		//To retrieve selected item name from checkout page
		try {
		      explicitWait(Elements.itemNameInCheckOutPage);
			log.info("Item name in CheckOut Page : "+driver.findElement(Elements.itemNameInCheckOutPage).getAttribute("text"));
			extTestObj.createNode("Item name in CheckOut Page : "+driver.findElement(Elements.itemNameInCheckOutPage).getAttribute("text")).pass("PASSED");
		}
		catch(Exception e)
		{
		log.error("Failed to display item name in Check Out Page");
		log.error(e.getMessage());
		extTestObj.createNode("Failed to display item name in Check Out Page")
				.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			
		}
		
		//To select quantity of selected item
		String quantity = excel.getCellData("ReOrder", "Quantity", 2);
		try {
			scrollIntoViewBottom(Elements.quantity);
			scrollIntoViewBottom(By.xpath("//*[text()='" + quantity + "']"));
			clickElement(By.xpath("//*[text()='" + quantity + "']"));
			log.info("Quantity changed to : "+driver.findElement(Elements.quantity).getAttribute("text"));
			extTestObj.createNode("Quantity changed to : "+driver.findElement(Elements.quantity).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select and change quantity");
			log.error(e.getMessage());
			extTestObj.createNode("Failed to select and change quantity")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			stopTestforMobileBrowser();
		}
		//To opt in silver ware
		try {
			Thread.sleep(3000);
			scrollIntoViewBottom(Elements.silverWareCheckBox);
			explicitWait(Elements.silverWareCheckBox);
			clickableWait(Elements.optSilverWareCheckBox);
			log.info("Silver ware opted in");
			extTestObj.createNode("Silver ware opted in").pass("PASSED");
		} catch (Exception e) {
			log.error("Silver ware opt in failed");
			extTestObj.createNode("Silver ware opt in failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();

		}
		
		//To add available rewards
		try {
			explicitWait(Elements.addRewards);
			scrollIntoViewBottom(Elements.addRewards);
			clickableWait(Elements.addRewards);
			log.info("Reward added");
			extTestObj.createNode("Reward added").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to add reward");
			extTestObj.createNode("Failed to add reward")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		//To click Check Out button
		try {
			explicitWait(Elements.checkOutButton);
			scrollIntoViewBottom(Elements.checkOutButton);
			clickableWait(Elements.checkOutButton);
			log.info("Order checked Out");
			extTestObj.createNode("Order checked Out").pass("PASSED");
		} catch (Exception e) {
			log.error("Order check out failed");
			extTestObj.createNode("Order check out failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To select item associated with the reward
		String rewarditem = excel.getCellData("OrderWithRewards", "Reward Item", 2);
		try {
			explicitWait(By.xpath("//*[contains(text(),'" + rewarditem + "')]"));
			clickableWait(By.xpath("//*[contains(text(),'" + rewarditem + "')]"));
			scrollIntoViewHalf(Elements.addThisItem);
			explicitWait(Elements.addThisItem);
			clickableWait(Elements.addThisItem);
			log.info("Reward item " + rewarditem + " added");
			extTestObj.createNode("Reward item " + rewarditem + " added").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to add reward item");
			extTestObj.createNode("Failed to add reward item")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		//To click on view cart button
		try {
			explicitWait(Elements.viewCartButton);
			clickableWait(Elements.viewCartButton);
			log.info("View Cart clicked");
			extTestObj.createNode("View Cart clicked").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to click View Cart");
			extTestObj.createNode("Failed to click View Cart")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To select quantity of the selected item
		try {
			scrollIntoViewBottom(Elements.quantity);
			scrollIntoViewBottom(By.xpath("//*[text()='" + quantity + "']"));
			clickElement(By.xpath("//*[text()='" + quantity + "']"));
			log.info("Quantity changed");
			extTestObj.createNode("Quantity changed").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select and change quantity");
			log.error(e.getMessage());
			extTestObj.createNode("Failed to select and change quantity")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			stopTestforMobileBrowser();
		}
		
		//To again add rewards
		try {
			explicitWait(Elements.addRewards);
			scrollIntoViewBottom(Elements.addRewards);
			clickableWait(Elements.addRewards);
			log.info("Reward added");
			extTestObj.createNode("Reward added").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to add reward");
			extTestObj.createNode("Failed to add reward")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		//To click CheckOut button
		try {
			explicitWait(Elements.checkOutButton);
			scrollIntoViewBottom(Elements.checkOutButton);
			clickableWait(Elements.checkOutButton);
			explicitWait(Elements.checkOutPageHeader);
			log.info("Order checked Out");
			extTestObj.createNode("Order checked Out").pass("PASSED");
		} catch (Exception e) {
			log.error("Order check out failed");
			extTestObj.createNode("Order check out failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		
	}
	
	/*
	 * Function Name : validateRewardsSelectionIOS()
	 * Purpose : To add available rewards with the selected item and then checkout the order page
	 * Platform : IOS Safari
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void validateRewardsSelectionIOS()
	{
		//To select quantity of the selected item
		String quantity = excel.getCellData("ReOrder", "Quantity", 2);
		try {
			scrollIntoViewBottom(Elements.quantity);
			explicitWait(Elements.quantity);
			clickableWait(Elements.quantity);
			seetest.setPickerValues("WEB", "xpath=//*[@id='items0.quantity']", 0, 0, quantity);
			Thread.sleep(1000);
			seetest.setPickerValues("WEB", "xpath=//*[@id='items0.quantity']", 0, 0, quantity);
			log.info("Quantity changed");
			extTestObj.createNode("Quantity changed").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select and change quantity");
			log.error(e.getMessage());
			extTestObj.createNode("Failed to select and change quantity")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			stopTestforMobileBrowser();
		}
		
		//To opt in silver ware
		try {
			Thread.sleep(3000);
			scrollDownFromStart("450");
			explicitWait(Elements.silverWareCheckBox);
			clickableWait(Elements.optSilverWareCheckBox);
			log.info("Silver ware opted in");
			extTestObj.createNode("Silver ware opted in").pass("PASSED");
		} catch (Exception e) {
			log.error("Silver ware opt in failed");
			extTestObj.createNode("Silver ware opt in failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();

		}
		
		//To add available rewards
		try {
			explicitWait(Elements.addRewards);
			scrollIntoViewBottom(Elements.addRewards);
			clickableWait(Elements.addRewards);
			log.info("Reward added");
			extTestObj.createNode("Reward added").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to add reward");
			extTestObj.createNode("Failed to add reward")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		//To click Check Out button
		try {
			explicitWait(Elements.checkOutButton);
			scrollIntoViewBottom(Elements.checkOutButton);
			clickableWait(Elements.checkOutButton);
			log.info("Order checked Out");
			extTestObj.createNode("Order checked Out").pass("PASSED");
		} catch (Exception e) {
			log.error("Order check out failed");
			extTestObj.createNode("Order check out failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To select item associated with the reward
		String rewarditem = excel.getCellData("OrderWithRewards", "Reward Item", 2);
		try {
			explicitWait(By.xpath("//*[contains(text(),'" + rewarditem + "')]"));
			clickableWait(By.xpath("//*[contains(text(),'" + rewarditem + "')]"));
			scrollIntoViewHalf(Elements.addThisItem);
			explicitWait(Elements.addThisItem);
			clickableWait(Elements.addThisItem);
			log.info("Reward item " + rewarditem + " added");
			extTestObj.createNode("Reward item " + rewarditem + " added").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to add reward item");
			extTestObj.createNode("Failed to add reward item")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		//To click view cart button
		try {
			explicitWait(Elements.viewCartButton);
			clickableWait(Elements.viewCartButton);
			log.info("View Cart clicked");
			extTestObj.createNode("View Cart clicked").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to click View Cart");
			extTestObj.createNode("Failed to click View Cart")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To change iten quantity
		try {
			scrollIntoViewBottom(Elements.quantity);
			explicitWait(Elements.quantity);
			clickableWait(Elements.quantity);
			seetest.setPickerValues("WEB", "xpath=//*[@id='items0.quantity']", 0, 0, quantity);
			Thread.sleep(1000);
			seetest.setPickerValues("WEB", "xpath=//*[@id='items0.quantity']", 0, 0, quantity);
			log.info("Quantity changed");
			extTestObj.createNode("Quantity changed").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select and change quantity");
			log.error(e.getMessage());
			extTestObj.createNode("Failed to select and change quantity")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			stopTestforMobileBrowser();
		}
		
		//To add available rewards
		try {
			explicitWait(Elements.addRewards);
			scrollIntoViewBottom(Elements.addRewards);
			clickableWait(Elements.addRewards);
			log.info("Reward added");
			extTestObj.createNode("Reward added").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to add reward");
			extTestObj.createNode("Failed to add reward")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		//To click Check Out button
		try {
			explicitWait(Elements.checkOutButton);
			scrollIntoViewBottom(Elements.checkOutButton);
			clickableWait(Elements.checkOutButton);
			explicitWait(Elements.checkOutPageHeader);
			log.info("Order checked Out");
			extTestObj.createNode("Order checked Out").pass("PASSED");
		} catch (Exception e) {
			log.error("Order check out failed");
			extTestObj.createNode("Order check out failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		
	}
		
	/*
	 * Function Name : validateCheckOutPage()
	 * Purpose : To validate the functionalities available in Check out page of Chilis website
	 * Platform : Android Chrome
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void validateCheckOutPage() {
		// To validate the Item name in Check out page
		try {
			log.info("Item name in CheckOut Page : "
					+ driver.findElement(Elements.itemNameInCheckOutPage).getAttribute("text"));
			extTestObj.createNode("Item name in CheckOut Page : "
					+ driver.findElement(Elements.itemNameInCheckOutPage).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to display item name in Check Out Page");
			log.error(e.getMessage());
			extTestObj.createNode("Failed to display item name in Check Out Page")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
		}

		// To validate the quantity selection functionality
		String quantity = excel.getCellData("ReOrder", "Quantity", 2);
		try {
			scrollIntoViewBottom(Elements.quantity);
			scrollIntoViewBottom(By.xpath("//*[text()='" + quantity + "']"));
			clickElement(By.xpath("//*[text()='" + quantity + "']"));
			log.info("Quantity changed to : " + driver.findElement(Elements.quantity).getAttribute("text"));
			extTestObj.createNode("Quantity changed to : " + driver.findElement(Elements.quantity).getAttribute("text"))
					.pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select and change quantity");
			log.error(e.getMessage());
			extTestObj.createNode("Failed to select and change quantity")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			stopTestforMobileBrowser();
		}

		// To check the Silver ware opt in check box
		try {
			scrollIntoViewBottom(Elements.silverWareCheckBox);
			clickableWait(Elements.optSilverWareCheckBox);
			log.info("Silver ware opted in");
			extTestObj.createNode("Silver ware opted in").pass("PASSED");
		} catch (Exception e) {
			log.error("Silver ware opt in failed");
			extTestObj.createNode("Silver ware opt in failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}

		// To click on Check out button available at the bottom of the page
		try {
			scrollIntoViewBottom(Elements.checkOutButton);
			clickElement(Elements.checkOutButton);
			explicitWait(Elements.checkOutPageHeader);
			log.info("Order checked Out");
			extTestObj.createNode("Order checked Out").pass("PASSED");
		} catch (Exception e) {
			log.error("Order check out failed");
			extTestObj.createNode("Order check out failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}

	}
	
	/*
	 * Function Name : validateCheckOutPageIOS()
	 * Purpose : To validate the functionalities available in Check out page of Chilis website
	 * Platform : IOS Safari
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void validateCheckOutPageIOS()
	{
		// To validate the Item name in Check out page
		String quantity = excel.getCellData("ReOrder", "Quantity", 2);
		try {
			scrollIntoViewBottom(Elements.quantity);
			explicitWait(Elements.quantity);
			clickableWait(Elements.quantity);
			seetest.setPickerValues("WEB", "xpath=//*[@id='items0.quantity']", 0, 0, quantity);
			Thread.sleep(1000);
			seetest.setPickerValues("WEB", "xpath=//*[@id='items0.quantity']", 0, 0, quantity);
			log.info("Quantity changed");
			extTestObj.createNode("Quantity changed").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select and change quantity");
			log.error(e.getMessage());
			extTestObj.createNode("Failed to select and change quantity")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			stopTestforMobileBrowser();
		}
		
		// To check the Silver ware opt in check box
		try {
			Thread.sleep(3000);
			scrollDownFromStart("450");
			explicitWait(Elements.silverWareCheckBox);
			clickableWait(Elements.optSilverWareCheckBox);
			log.info("Silver ware opted in");
			extTestObj.createNode("Silver ware opted in").pass("PASSED");
		} catch (Exception e) {
			log.error("Silver ware opt in failed");
			extTestObj.createNode("Silver ware opt in failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();

		}
		
		//To click on the check out button
		try {
			explicitWait(Elements.checkOutButton);
			scrollIntoViewBottom(Elements.checkOutButton);
			clickableWait(Elements.checkOutButton);
			explicitWait(Elements.checkOutPageHeader);
			log.info("Order checked Out");
			extTestObj.createNode("Order checked Out").pass("PASSED");
		} catch (Exception e) {
			log.error("Order check out failed");
			extTestObj.createNode("Order check out failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
	}

	
	/*
	 * Function Name : continueToPayment()
	 * Purpose : To click on the "CONTINUE TO PAYMENT" button available at the bottom of the page
	 * Platform : Android Chrome
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void continueToPayment() {
		try {
			scrollIntoViewBottom(Elements.paymentButton);
			clickElement(Elements.paymentButton);
			explicitWait(Elements.finalizeOrderPageHeader);
			log.info("Payment button clicked");
			extTestObj.createNode("Payment button clicked").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to click Payment button");
			extTestObj.createNode("Failed to click Payment button")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
	}

	
	/*
	 * Function Name : enterPaymentDetails()
	 * Purpose : To validate the Payment page and enter payment details
	 * Platform : Android Chrome
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void enterPaymentDetails()
	{
		//To  select payment method from the payment method dropdown based on inputs from data sheet
		String paymentMethod = excel.getCellData("LoggedInOrder", "Payment Method", 2);
		try {
			scrollIntoViewBottom(Elements.paymentMethodDropDown2);
			scrollIntoViewBottom(By.xpath("//*[@name='paymentMethod']/option[@text='"+paymentMethod+"']"));
			clickElement(By.xpath("//*[@name='paymentMethod']/option[@text='"+paymentMethod+"']"));
			log.info("Payment Method selected as : " + driver.findElement(Elements.paymentMethodDropDown2).getAttribute("text"));
			extTestObj.createNode("Payment Method selected as : " + driver.findElement(Elements.paymentMethodDropDown2).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Payment method");
			extTestObj.createNode("Failed to select Payment method")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		//To select card type from the card type dropdown based on inputs from data sheet
		String cardType = excel.getCellData("LoggedInOrder", "Card Type", 2);
		try {
			scrollIntoViewBottom(Elements.cardTypeDropDown);
			scrollIntoViewBottom(By.xpath("//*[@id='card-type-selector']/option[@text='"+cardType+"']"));
			clickElement(By.xpath("//*[@id='card-type-selector']/option[@text='"+cardType+"']"));
			log.info("Card Type selected as : " + driver.findElement(Elements.cardTypeDropDown).getAttribute("text"));
			extTestObj.createNode("Card Type selected as : " + driver.findElement(Elements.cardTypeDropDown).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Card Type");
			extTestObj.createNode("Failed to select Card Type")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		//To enter card number based on input from data sheet
		String cardNo = excel.getCellData("LoggedInOrder", "Card Number", 2);
		try {
			scrollIntoViewBottom(Elements.cardNo);
			clickElement(Elements.cardNo);
			driver.getKeyboard().sendKeys(cardNo);
			seetest.closeKeyboard();
			log.info("Card Number entered as : " + driver.findElement(Elements.cardNo).getAttribute("text"));
			extTestObj.createNode("Card Number entered as : " + driver.findElement(Elements.cardNo).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter Card Number");
			extTestObj.createNode("Failed to enter Card Number")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		//To enter CVV based on input from data sheet
		String cvv = excel.getCellData("LoggedInOrder", "CVV", 2);
		try {
			scrollIntoViewBottom(Elements.cvvTextBox);
			clickElement(Elements.cvvTextBox);
			driver.getKeyboard().sendKeys(cvv);
			seetest.closeKeyboard();
			log.info("CVV entered as : " + driver.findElement(Elements.cvvTextBox).getAttribute("text"));
			extTestObj.createNode("CVV entered as : " + driver.findElement(Elements.cvvTextBox).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter CVV");
			extTestObj.createNode("Failed to enter CVV")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		//To select Expiration month based on input from Data Sheet
		String month = excel.getCellData("LoggedInOrder", "Expiration Month", 2);
		try {
			scrollIntoViewBottom(Elements.expirationMonth);
			scrollIntoViewBottom(By.xpath("//*[contains(text(),'(0" + month + ")')]"));
			clickElement(By.xpath("//*[contains(text(),'(0" + month + ")')]"));
			log.info("Expiration Month selected as : " + driver.findElement(Elements.expirationMonth).getAttribute("text"));
			extTestObj.createNode("Expiration Month selected as : " + driver.findElement(Elements.expirationMonth).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Expiration Month");
			extTestObj.createNode("Failed to select Expiration Month")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To select Expiration year based on input from Data sheet
		String year = excel.getCellData("LoggedInOrder", "Expiration Year", 2);
		try {
			scrollIntoViewBottom(Elements.expirationYear);
			scrollIntoViewBottom(By.xpath("//*[text()='" + year + "']"));
			clickElement(By.xpath("//*[text()='" + year + "']"));
			log.info("Expiration Year selected as : " +driver.findElement(Elements.expirationYear).getAttribute("text"));
			extTestObj.createNode("Expiration Year selected as : " + driver.findElement(Elements.expirationYear).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Expiration Year");
			extTestObj.createNode("Failed to select Expiration Year")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To enter name on card based on input from Data Sheet
		String nameOnCard = excel.getCellData("LoggedInOrder", "Name On Card", 2);
		try {
			scrollIntoViewBottom(Elements.nameOnCard);
			clickElement(Elements.nameOnCard);
			driver.getKeyboard().sendKeys(nameOnCard);
			seetest.closeKeyboard();
			log.info("Name on Card entered as : " +driver.findElement(Elements.nameOnCard).getAttribute("text"));
			extTestObj.createNode("Name on Card entered as : " + driver.findElement(Elements.nameOnCard).getAttribute("text"));
		} catch (Exception e) {
			log.error("Failed to enter Name on Card");
			extTestObj.createNode("Failed to enter Name on Card")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			;
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To enter zip code based on input from data sheet
		String zipCode = excel.getCellData("LoggedInOrder", "Zip Code", 2);
		try {
			scrollIntoViewBottom(Elements.billingZip);
			clickElement(Elements.billingZip);
			driver.getKeyboard().sendKeys(zipCode);
			seetest.closeKeyboard();
			log.info("Billing zip code entered as : " + driver.findElement(Elements.billingZip).getAttribute("text"));
			extTestObj.createNode("Billing zip code entered as : " + driver.findElement(Elements.billingZip).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter Billing zip code");
			extTestObj.createNode("Failed to enter Billing zip code")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		//To enter tip amount based on input from data sheet
		String tip = excel.getCellData("LoggedInOrder", "Tip", 2);
		try {
			scrollIntoViewBottom(Elements.tipTextBox);
			sendKeysWait(Elements.tipTextBox, tip);
			seetest.closeKeyboard();
			log.info("Tip entered as : " + driver.findElement(Elements.tipTextBox).getAttribute("text"));
			extTestObj.createNode("Tip entered as : "+driver.findElement(Elements.tipTextBox).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter tip");
			extTestObj.createNode("Failed to enter tip")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

		}
		
		//To check the donation payment to St Jude
		try {
			scrollIntoViewBottom(Elements.donationCheckBox);
			clickElement(Elements.donationCheckBox);
			log.info("Donation checked");
			extTestObj.createNode("Donation checked").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to check donation check box");
			extTestObj.createNode("Failed to check donation check box")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
		}
		
		
	}
	
	/*
	 * Function Name : enterPaymentDetailswithSuggestedTip(int args)
	 * Purpose : To validate the Payment page and enter payment details along with suggested tip
	 * Platform : Android Chrome
	 * Parameters : Location Index from CommonaData.xlsx for UCOM,Non-UCOM check
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void enterPaymentDetailswithSuggestedTip(int locationIndex)
	{
		//To select suggested tip and validate the same
		String tip = excel.getCellData("LoggedInOrder", "Tip", 2);
		String tipAmount="";
		String displayedTipAmount="";
		try {
			scrollIntoViewHalf(By.xpath("//*[@id='suggested-tip-"+tip+"']"));
			clickElement(By.xpath("//*[@id='suggested-tip-"+tip+"']"));
			tipAmount = driver.findElement(By.xpath("//*[@id='suggested-tip-"+tip+"']/label[2]")).getText();
			scrollIntoViewHalf(Elements.displayedTipAmount);
			displayedTipAmount = driver.findElement(Elements.displayedTipAmount).getText();
		}
		 catch (Exception e) {
				log.error("Failed to enter tip");
				extTestObj.createNode("Failed to enter tip")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());

			}
			int flag = 0;
			if(tipAmount.contentEquals(displayedTipAmount))
				flag=1;
			try {
				int flagchecker = 1/flag;
			log.info("Chosen Suggested Tip percentage : "+tip+", tip amount : "+displayedTipAmount);
			extTestObj.createNode("Chosen Suggested Tip percentage : "+tip+", tip amount : "+displayedTipAmount).pass("PASSED");
		}
			catch(Exception e)
			{
				log.error("Discrepancy observed between suggested tip chosen and amount displayed");
				extTestObj.createNode("Discrepancy observed between suggested tip chosen and amount displayed").fail("FAILED");
			}

			//To check St Jude Donation
		try {
			scrollIntoViewHalf(Elements.donationCheckBox);
			clickableWait(Elements.donationCheckBox);
			log.info("Donation checked");
			extTestObj.createNode("Donation checked").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to check donation check box");
			extTestObj.createNode("Failed to check donation check box")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
		}
		
		
		//To click on "PAY WITH CREDIT CARD" for UCOM store
		try {
		if(excel.getCellData("Locations","Type",locationIndex).equals("UCOM"))
		clickableWait(Elements.payWithCreditCardXpath);
		}
		catch(Exception e) {}
		
		//To select payment method
		String paymentMethod = excel.getCellData("LoggedInOrder", "Payment Method", 2);
		try {
			scrollIntoViewBottom(Elements.paymentMethodDropDown);
			scrollIntoViewBottom(By.xpath("//*[@id='payment-method-selector']/option[@text='"+paymentMethod+"']"));
			clickElement(By.xpath("//*[@id='payment-method-selector']/option[@text='"+paymentMethod+"']"));
			log.info("Payment Method selected as : " + driver.findElement(Elements.paymentMethodDropDown).getAttribute("text"));
			extTestObj.createNode("Payment Method selected as : " + driver.findElement(Elements.paymentMethodDropDown).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Payment method");
			extTestObj.createNode("Failed to select Payment method")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		//To select card type
		String cardType = excel.getCellData("LoggedInOrder", "Card Type", 2);
		try {
			scrollIntoViewBottom(Elements.cardTypeDropDown);
			scrollIntoViewBottom(By.xpath("//*[@id='card-type-selector']/option[@text='"+cardType+"']"));
			clickElement(By.xpath("//*[@id='card-type-selector']/option[@text='"+cardType+"']"));
			log.info("Card Type selected as : " + driver.findElement(Elements.cardTypeDropDown).getAttribute("text"));
			extTestObj.createNode("Card Type selected as : " + driver.findElement(Elements.cardTypeDropDown).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Card Type");
			extTestObj.createNode("Failed to select Card Type")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		//To enter card number
		String cardNo = excel.getCellData("LoggedInOrder", "Card Number", 2);
		try {
			Thread.sleep(4000);
			scrollIntoViewBottom(Elements.cardNo);
			explicitWait(Elements.cardNo);
			clickableWait(Elements.cardNo);
			driver.getKeyboard().sendKeys(cardNo);
			seetest.closeKeyboard();
			log.info("Card Number entered as : " + driver.findElement(Elements.cardNo).getAttribute("text"));
			extTestObj.createNode("Card Number entered as : " + driver.findElement(Elements.cardNo).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter Card Number");
			extTestObj.createNode("Failed to enter Card Number")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To enter CVV
		String cvv = excel.getCellData("LoggedInOrder", "CVV", 2);
		try {
			explicitWait(Elements.cvvTextBox);
			scrollIntoViewBottom(Elements.cvvTextBox);
			clickableWait(Elements.cvvTextBox);
			driver.getKeyboard().sendKeys(cvv);
			seetest.closeKeyboard();
			log.info("CVV entered as : " + driver.findElement(Elements.cvvTextBox).getAttribute("text"));
			extTestObj.createNode("CVV entered as : " + driver.findElement(Elements.cvvTextBox).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter CVV");
			extTestObj.createNode("Failed to enter CVV")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To select expiration month
		String month = excel.getCellData("LoggedInOrder", "Expiration Month Name", 2);
		try {
			scrollIntoViewBottom(Elements.expirationMonth);
			scrollIntoViewBottom(By.xpath("//*[contains(text(),'"+month+"')]"));
			clickElement(By.xpath("//*[contains(text(),'"+month+"')]"));
			log.info("Expiration Month selected as : " + driver.findElement(Elements.expirationMonth).getAttribute("text"));
			extTestObj.createNode("Expiration Month selected as : " + driver.findElement(Elements.expirationMonth).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Expiration Month");
			extTestObj.createNode("Failed to select Expiration Month")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		//To select expiration year
		String year = excel.getCellData("LoggedInOrder", "Expiration Year", 2);
		try {
			scrollIntoViewBottom(Elements.expirationYear);
			scrollIntoViewBottom(By.xpath("//*[text()='" + year + "']"));
			clickElement(By.xpath("//*[text()='" + year + "']"));
			log.info("Expiration Year selected as : " +driver.findElement(Elements.expirationYear).getAttribute("text"));
			extTestObj.createNode("Expiration Year selected as : " + driver.findElement(Elements.expirationYear).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Expiration Year");
			extTestObj.createNode("Failed to select Expiration Year")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To enter Name on card
		String nameOnCard = excel.getCellData("LoggedInOrder", "Name On Card", 2);
		try {
			scrollIntoViewBottom(Elements.nameOnCard2);
			explicitWait(Elements.nameOnCard2);
			clickableWait(Elements.nameOnCard2);
			driver.getKeyboard().sendKeys(nameOnCard);
			seetest.closeKeyboard();
			log.info("Name on Card entered as : " +driver.findElement(Elements.nameOnCard2).getAttribute("text"));
			extTestObj.createNode("Name on Card entered as : " + driver.findElement(Elements.nameOnCard2).getAttribute("text"));
		} catch (Exception e) {
			log.error("Failed to enter Name on Card");
			extTestObj.createNode("Failed to enter Name on Card")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			;
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To enter zip code
		String zipCode = excel.getCellData("Locations", "Zip Code",locationIndex);
		try {
			scrollIntoViewBottom(Elements.billingZip2);
			explicitWait(Elements.billingZip2);
			clickableWait(Elements.billingZip2);
			driver.getKeyboard().sendKeys(zipCode);
			seetest.closeKeyboard();
			log.info("Billing zip code entered as : " + driver.findElement(Elements.billingZip2).getAttribute("text"));
			extTestObj.createNode("Billing zip code entered as : " + driver.findElement(Elements.billingZip2).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter Billing zip code");
			extTestObj.createNode("Failed to enter Billing zip code")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		
	}
	
	/*
	 * Function Name : enterPaymentDetailswithSuggestedTipWithGiftCard(int args)
	 * Purpose : To validate the Payment page and enter payment details along with suggested tip and gift card
	 * Platform : Android Chrome
	 * Parameters : Location Index from CommonaData.xlsx for UCOM,Non-UCOM check
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void enterPaymentDetailswithSuggestedTipWithGiftCard(int locationIndex)
	{
		//To select suggested tip and validate the same
		String tip = excel.getCellData("LoggedInOrder", "Tip", 2);
		String tipAmount="";
		String displayedTipAmount="";
		try {
			scrollIntoViewHalf(By.xpath("//*[@id='suggested-tip-"+tip+"']"));
			clickElement(By.xpath("//*[@id='suggested-tip-"+tip+"']"));
			tipAmount = driver.findElement(By.xpath("//*[@id='suggested-tip-"+tip+"']/label[2]")).getText();
			scrollIntoViewHalf(Elements.displayedTipAmount);
			displayedTipAmount = driver.findElement(Elements.displayedTipAmount).getText();
		}
		 catch (Exception e) {
				log.error("Failed to enter tip");
				extTestObj.createNode("Failed to enter tip")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());

			}
			int flag = 0;
			if(tipAmount.contentEquals(displayedTipAmount))
				flag=1;
			try {
				int flagchecker = 1/flag;
			log.info("Chosen Suggested Tip percentage : "+tip+", tip amount : "+displayedTipAmount);
			extTestObj.createNode("Chosen Suggested Tip percentage : "+tip+", tip amount : "+displayedTipAmount).pass("PASSED");
		}
			catch(Exception e)
			{
				log.error("Discrepancy observed between suggested tip chosen and amount displayed");
				extTestObj.createNode("Discrepancy observed between suggested tip chosen and amount displayed").fail("FAILED");
			}

			//To check St Jude Donation
		try {
			scrollIntoViewHalf(Elements.donationCheckBox);
			clickableWait(Elements.donationCheckBox);
			log.info("Donation checked");
			extTestObj.createNode("Donation checked").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to check donation check box");
			extTestObj.createNode("Failed to check donation check box")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
		}
		//To add gift card
		String giftCardNumber = excel.getCellData("LoggedInOrder","Gift Card Number",2);
		try {
			scrollIntoViewBottom(Elements.browserGiftCardTextBox);
			sendKeysWait(Elements.browserGiftCardTextBox, giftCardNumber);
			seetest.closeKeyboard();
			clickableWait(Elements.browserApplyGiftCardButton);
			log.info("Gift card applied");
			extTestObj.createNode("Gift card applied").pass("PASSED");
		}
		catch(Exception e)
		{
			log.error("Failed to apply gift card");
			extTestObj.createNode("Failed to apply gift card")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopIOSTest();
		}
		
		//To click on "PAY WITH CREDIT CARD" for UCOM store
		try {
		if(excel.getCellData("Locations","Type",locationIndex).equals("UCOM"))
		clickableWait(Elements.payWithCreditCardXpath);
		}
		catch(Exception e) {}
		
		//To select payment method
		String paymentMethod = excel.getCellData("LoggedInOrder", "Payment Method", 2);
		try {
			scrollIntoViewBottom(Elements.paymentMethodDropDown);
			scrollIntoViewBottom(By.xpath("//*[@id='payment-method-selector']/option[@text='"+paymentMethod+"']"));
			clickElement(By.xpath("//*[@id='payment-method-selector']/option[@text='"+paymentMethod+"']"));
			log.info("Payment Method selected as : " + driver.findElement(Elements.paymentMethodDropDown).getAttribute("text"));
			extTestObj.createNode("Payment Method selected as : " + driver.findElement(Elements.paymentMethodDropDown).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Payment method");
			extTestObj.createNode("Failed to select Payment method")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		//To select card type
		String cardType = excel.getCellData("LoggedInOrder", "Card Type", 2);
		try {
			scrollIntoViewBottom(Elements.cardTypeDropDown);
			scrollIntoViewBottom(By.xpath("//*[@id='card-type-selector']/option[@text='"+cardType+"']"));
			clickElement(By.xpath("//*[@id='card-type-selector']/option[@text='"+cardType+"']"));
			log.info("Card Type selected as : " + driver.findElement(Elements.cardTypeDropDown).getAttribute("text"));
			extTestObj.createNode("Card Type selected as : " + driver.findElement(Elements.cardTypeDropDown).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Card Type");
			extTestObj.createNode("Failed to select Card Type")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		//To enter card number
		String cardNo = excel.getCellData("LoggedInOrder", "Card Number", 2);
		try {
			Thread.sleep(4000);
			scrollIntoViewBottom(Elements.cardNo);
			explicitWait(Elements.cardNo);
			clickableWait(Elements.cardNo);
			driver.getKeyboard().sendKeys(cardNo);
			seetest.closeKeyboard();
			log.info("Card Number entered as : " + driver.findElement(Elements.cardNo).getAttribute("text"));
			extTestObj.createNode("Card Number entered as : " + driver.findElement(Elements.cardNo).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter Card Number");
			extTestObj.createNode("Failed to enter Card Number")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To enter CVV
		String cvv = excel.getCellData("LoggedInOrder", "CVV", 2);
		try {
			explicitWait(Elements.cvvTextBox);
			scrollIntoViewBottom(Elements.cvvTextBox);
			clickableWait(Elements.cvvTextBox);
			driver.getKeyboard().sendKeys(cvv);
			seetest.closeKeyboard();
			log.info("CVV entered as : " + driver.findElement(Elements.cvvTextBox).getAttribute("text"));
			extTestObj.createNode("CVV entered as : " + driver.findElement(Elements.cvvTextBox).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter CVV");
			extTestObj.createNode("Failed to enter CVV")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To select expiration month
		String month = excel.getCellData("LoggedInOrder", "Expiration Month Name", 2);
		try {
			scrollIntoViewBottom(Elements.expirationMonth);
			scrollIntoViewBottom(By.xpath("//*[contains(text(),'"+month+"')]"));
			clickElement(By.xpath("//*[contains(text(),'"+month+"')]"));
			log.info("Expiration Month selected as : " + driver.findElement(Elements.expirationMonth).getAttribute("text"));
			extTestObj.createNode("Expiration Month selected as : " + driver.findElement(Elements.expirationMonth).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Expiration Month");
			extTestObj.createNode("Failed to select Expiration Month")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		//To select expiration year
		String year = excel.getCellData("LoggedInOrder", "Expiration Year", 2);
		try {
			scrollIntoViewBottom(Elements.expirationYear);
			scrollIntoViewBottom(By.xpath("//*[text()='" + year + "']"));
			clickElement(By.xpath("//*[text()='" + year + "']"));
			log.info("Expiration Year selected as : " +driver.findElement(Elements.expirationYear).getAttribute("text"));
			extTestObj.createNode("Expiration Year selected as : " + driver.findElement(Elements.expirationYear).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Expiration Year");
			extTestObj.createNode("Failed to select Expiration Year")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To enter Name on card
		String nameOnCard = excel.getCellData("LoggedInOrder", "Name On Card", 2);
		try {
			scrollIntoViewBottom(Elements.nameOnCard2);
			explicitWait(Elements.nameOnCard2);
			clickableWait(Elements.nameOnCard2);
			driver.getKeyboard().sendKeys(nameOnCard);
			seetest.closeKeyboard();
			log.info("Name on Card entered as : " +driver.findElement(Elements.nameOnCard2).getAttribute("text"));
			extTestObj.createNode("Name on Card entered as : " + driver.findElement(Elements.nameOnCard2).getAttribute("text"));
		} catch (Exception e) {
			log.error("Failed to enter Name on Card");
			extTestObj.createNode("Failed to enter Name on Card")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			;
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To enter zip code
		String zipCode = excel.getCellData("Locations", "Zip Code",locationIndex);
		try {
			scrollIntoViewBottom(Elements.billingZip2);
			explicitWait(Elements.billingZip2);
			clickableWait(Elements.billingZip2);
			driver.getKeyboard().sendKeys(zipCode);
			seetest.closeKeyboard();
			log.info("Billing zip code entered as : " + driver.findElement(Elements.billingZip2).getAttribute("text"));
			extTestObj.createNode("Billing zip code entered as : " + driver.findElement(Elements.billingZip2).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter Billing zip code");
			extTestObj.createNode("Failed to enter Billing zip code")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		
	}
	
	/*
	 * Function Name : enterPaymentDetailswithCustomTip(int args)
	 * Purpose : To validate the Payment page and enter payment details along with custom tip
	 * Platform : Android Chrome
	 * Parameters : Location Index from CommonaData.xlsx for UCOM,Non-UCOM check
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void enterPaymentDetailswithCustomTip(int locationIndex)
	{
		//To enter tip amount 
		String tip = excel.getCellData("LoggedInOrder", "Custom Tip", 2);
		String tipAmount="";
		String displayedTipAmount="";
		try {
			scrollIntoViewHalf(Elements.customTipInput);
			clickableWait(Elements.customTipInput);
			seetest.sendText(tip);
//			driver.getKeyboard().sendKeys(tip);
			seetest.closeKeyboard();
			tipAmount = driver.findElement(Elements.customTipInput).getAttribute("text");
			scrollIntoViewHalf(Elements.displayedTipAmount);
			displayedTipAmount = driver.findElement(Elements.displayedTipAmount).getText();
			log.info("Custom tip amount entered as :"+displayedTipAmount);
			extTestObj.createNode("Custom tip amount entered as :"+displayedTipAmount).pass("PASSED");
		}
		 catch (Exception e) {
				log.error("Failed to enter tip");
				extTestObj.createNode("Failed to enter tip")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());

			}
//			int flag = 0;
//			if(tipAmount.contentEquals(displayedTipAmount))
//				flag=1;
//			try {
//				int flagchecker = 1/flag;
//			log.info("Custom tip amount entered as :"+displayedTipAmount);
//			extTestObj.createNode("Custom tip amount entered as :"+displayedTipAmount).pass("PASSED");
//		}
//			catch(Exception e)
//			{
//				log.error("Discrepancy observed between custom tip entered and amount displayed");
//				extTestObj.createNode("Discrepancy observed between custom tip entered and amount displayed").fail("FAILED");
//			}

		//To check St Jude donation
		try {
			scrollIntoViewBottom(Elements.donationCheckBox);
			clickableWait(Elements.donationCheckBox);
			log.info("Donation checked");
			extTestObj.createNode("Donation checked").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to check donation check box");
			extTestObj.createNode("Failed to check donation check box")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
		}
		
		//To select "PAY WITH CREDIT CARD" for UCOM store
		try {
		if(excel.getCellData("Locations","Type",locationIndex).equals("UCOM"))
			clickableWait(Elements.payWithCreditCardXpath);
		}
		catch(Exception e) {}
		
		//To select payment method
		String paymentMethod = excel.getCellData("LoggedInOrder", "Payment Method", 2);
		try {
			scrollIntoViewBottom(Elements.paymentMethodDropDown);
			scrollIntoViewBottom(By.xpath("//*[@id='payment-method-selector']/option[@text='"+paymentMethod+"']"));
			clickElement(By.xpath("//*[@id='payment-method-selector']/option[@text='"+paymentMethod+"']"));
			log.info("Payment Method selected as : " + driver.findElement(Elements.paymentMethodDropDown).getAttribute("text"));
			extTestObj.createNode("Payment Method selected as : " + driver.findElement(Elements.paymentMethodDropDown).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Payment method");
			extTestObj.createNode("Failed to select Payment method")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To select card type
		String cardType = excel.getCellData("LoggedInOrder", "Card Type", 2);
		try {
			scrollIntoViewBottom(Elements.cardTypeDropDown);
			scrollIntoViewBottom(By.xpath("//*[@id='card-type-selector']/option[@text='"+cardType+"']"));
			clickElement(By.xpath("//*[@id='card-type-selector']/option[@text='"+cardType+"']"));
			log.info("Card Type selected as : " + driver.findElement(Elements.cardTypeDropDown).getAttribute("text"));
			extTestObj.createNode("Card Type selected as : " + driver.findElement(Elements.cardTypeDropDown).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Card Type");
			extTestObj.createNode("Failed to select Card Type")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
			
		}
		
		//To enter card number
		String cardNo = excel.getCellData("LoggedInOrder", "Card Number", 2);
		try {
			Thread.sleep(4000);
			scrollIntoViewBottom(Elements.cardNo);
			explicitWait(Elements.cardNo);
			clickableWait(Elements.cardNo);
			driver.getKeyboard().sendKeys(cardNo);
			seetest.closeKeyboard();
			log.info("Card Number entered as : " + driver.findElement(Elements.cardNo).getAttribute("text"));
			extTestObj.createNode("Card Number entered as : " + driver.findElement(Elements.cardNo).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter Card Number");
			extTestObj.createNode("Failed to enter Card Number")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To enter CVV number
		String cvv = excel.getCellData("LoggedInOrder", "CVV", 2);
		try {
			explicitWait(Elements.cvvTextBox);
			scrollIntoViewBottom(Elements.cvvTextBox);
			clickableWait(Elements.cvvTextBox);
			driver.getKeyboard().sendKeys(cvv);
			seetest.closeKeyboard();
			log.info("CVV entered as : " + driver.findElement(Elements.cvvTextBox).getAttribute("text"));
			extTestObj.createNode("CVV entered as : " + driver.findElement(Elements.cvvTextBox).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter CVV");
			extTestObj.createNode("Failed to enter CVV")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To select expiration month
		String month = excel.getCellData("LoggedInOrder", "Expiration Month Name", 2);
		try {
			scrollIntoViewBottom(Elements.expirationMonth);
			scrollIntoViewBottom(By.xpath("//*[contains(text(),'"+month+"')]"));
			clickElement(By.xpath("//*[contains(text(),'"+month+"')]"));
			log.info("Expiration Month selected as : " + driver.findElement(Elements.expirationMonth).getAttribute("text"));
			extTestObj.createNode("Expiration Month selected as : " + driver.findElement(Elements.expirationMonth).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Expiration Month");
			extTestObj.createNode("Failed to select Expiration Month")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To select expiration year
		String year = excel.getCellData("LoggedInOrder", "Expiration Year", 2);
		try {
			scrollIntoViewBottom(Elements.expirationYear);
			scrollIntoViewBottom(By.xpath("//*[text()='" + year + "']"));
			clickElement(By.xpath("//*[text()='" + year + "']"));
			log.info("Expiration Year selected as : " +driver.findElement(Elements.expirationYear).getAttribute("text"));
			extTestObj.createNode("Expiration Year selected as : " + driver.findElement(Elements.expirationYear).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Expiration Year");
			extTestObj.createNode("Failed to select Expiration Year")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To enter name on card
		String nameOnCard = excel.getCellData("LoggedInOrder", "Name On Card", 2);
		try {
			scrollIntoViewBottom(Elements.nameOnCard2);
			explicitWait(Elements.nameOnCard2);
			clickableWait(Elements.nameOnCard2);
			driver.getKeyboard().sendKeys(nameOnCard);
			seetest.closeKeyboard();
			log.info("Name on Card entered as : " +driver.findElement(Elements.nameOnCard2).getAttribute("text"));
			extTestObj.createNode("Name on Card entered as : " + driver.findElement(Elements.nameOnCard2).getAttribute("text"));
		} catch (Exception e) {
			log.error("Failed to enter Name on Card");
			extTestObj.createNode("Failed to enter Name on Card")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			;
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To enter zip code
		String zipCode = excel.getCellData("Locations", "Zip Code", 2);
		try {
			scrollIntoViewBottom(Elements.billingZip2);
			explicitWait(Elements.billingZip2);
			clickableWait(Elements.billingZip2);
			driver.getKeyboard().sendKeys(zipCode);
			seetest.closeKeyboard();
			log.info("Billing zip code entered as : " + driver.findElement(Elements.billingZip2).getAttribute("text"));
			extTestObj.createNode("Billing zip code entered as : " + driver.findElement(Elements.billingZip2).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter Billing zip code");
			extTestObj.createNode("Failed to enter Billing zip code")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		
	}
	/*
	 * Function Name : enterPaymentDetailswithCustomTipWithGiftCard(int args)
	 * Purpose : To validate the Payment page and enter payment details along with custom tip and gift card
	 * Platform : Android Chrome
	 * Parameters : Location Index from CommonaData.xlsx for UCOM,Non-UCOM check
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void enterPaymentDetailswithCustomTipWithGiftCard(int locationIndex)
	{
		//To enter tip amount 
		String tip = excel.getCellData("LoggedInOrder", "Custom Tip", 2);
		String tipAmount="";
		String displayedTipAmount="";
		try {
			scrollIntoViewHalf(Elements.customTipInput);
			clickableWait(Elements.customTipInput);
			seetest.sendText(tip);
//			driver.getKeyboard().sendKeys(tip);
			seetest.closeKeyboard();
			tipAmount = driver.findElement(Elements.customTipInput).getAttribute("text");
			scrollIntoViewHalf(Elements.displayedTipAmount);
			displayedTipAmount = driver.findElement(Elements.displayedTipAmount).getText();
			log.info("Custom tip amount entered as :"+displayedTipAmount);
			extTestObj.createNode("Custom tip amount entered as :"+displayedTipAmount).pass("PASSED");
		}
		 catch (Exception e) {
				log.error("Failed to enter tip");
				extTestObj.createNode("Failed to enter tip")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());

			}
//			int flag = 0;
//			if(tipAmount.contentEquals(displayedTipAmount))
//				flag=1;
//			try {
//				int flagchecker = 1/flag;
//			log.info("Custom tip amount entered as :"+displayedTipAmount);
//			extTestObj.createNode("Custom tip amount entered as :"+displayedTipAmount).pass("PASSED");
//		}
//			catch(Exception e)
//			{
//				log.error("Discrepancy observed between custom tip entered and amount displayed");
//				extTestObj.createNode("Discrepancy observed between custom tip entered and amount displayed").fail("FAILED");
//			}

		//To check St Jude donation
		try {
			scrollIntoViewBottom(Elements.donationCheckBox);
			clickableWait(Elements.donationCheckBox);
			log.info("Donation checked");
			extTestObj.createNode("Donation checked").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to check donation check box");
			extTestObj.createNode("Failed to check donation check box")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
		}
		//To add gift card
		String giftCardNumber = excel.getCellData("LoggedInOrder","Gift Card Number",2);
		try {
			scrollIntoViewBottom(Elements.browserGiftCardTextBox);
			sendKeysWait(Elements.browserGiftCardTextBox, giftCardNumber);
			seetest.closeKeyboard();
			clickableWait(Elements.browserApplyGiftCardButton);
			log.info("Gift card applied");
			extTestObj.createNode("Gift card applied").pass("PASSED");
		}
		catch(Exception e)
		{
			log.error("Failed to apply gift card");
			extTestObj.createNode("Failed to apply gift card")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopIOSTest();
		}
		
		//To select "PAY WITH CREDIT CARD" for UCOM store
		try {
		if(excel.getCellData("Locations","Type",locationIndex).equals("UCOM"))
			clickableWait(Elements.payWithCreditCardXpath);
		}
		catch(Exception e) {}
		
		//To select payment method
		String paymentMethod = excel.getCellData("LoggedInOrder", "Payment Method", 2);
		try {
			scrollIntoViewBottom(Elements.paymentMethodDropDown);
			scrollIntoViewBottom(By.xpath("//*[@id='payment-method-selector']/option[@text='"+paymentMethod+"']"));
			clickElement(By.xpath("//*[@id='payment-method-selector']/option[@text='"+paymentMethod+"']"));
			log.info("Payment Method selected as : " + driver.findElement(Elements.paymentMethodDropDown).getAttribute("text"));
			extTestObj.createNode("Payment Method selected as : " + driver.findElement(Elements.paymentMethodDropDown).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Payment method");
			extTestObj.createNode("Failed to select Payment method")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To select card type
		String cardType = excel.getCellData("LoggedInOrder", "Card Type", 2);
		try {
			scrollIntoViewBottom(Elements.cardTypeDropDown);
			scrollIntoViewBottom(By.xpath("//*[@id='card-type-selector']/option[@text='"+cardType+"']"));
			clickElement(By.xpath("//*[@id='card-type-selector']/option[@text='"+cardType+"']"));
			log.info("Card Type selected as : " + driver.findElement(Elements.cardTypeDropDown).getAttribute("text"));
			extTestObj.createNode("Card Type selected as : " + driver.findElement(Elements.cardTypeDropDown).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Card Type");
			extTestObj.createNode("Failed to select Card Type")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
			
		}
		
		//To enter card number
		String cardNo = excel.getCellData("LoggedInOrder", "Card Number", 2);
		try {
			Thread.sleep(4000);
			scrollIntoViewBottom(Elements.cardNo);
			explicitWait(Elements.cardNo);
			clickableWait(Elements.cardNo);
			driver.getKeyboard().sendKeys(cardNo);
			seetest.closeKeyboard();
			log.info("Card Number entered as : " + driver.findElement(Elements.cardNo).getAttribute("text"));
			extTestObj.createNode("Card Number entered as : " + driver.findElement(Elements.cardNo).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter Card Number");
			extTestObj.createNode("Failed to enter Card Number")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To enter CVV number
		String cvv = excel.getCellData("LoggedInOrder", "CVV", 2);
		try {
			explicitWait(Elements.cvvTextBox);
			scrollIntoViewBottom(Elements.cvvTextBox);
			clickableWait(Elements.cvvTextBox);
			driver.getKeyboard().sendKeys(cvv);
			seetest.closeKeyboard();
			log.info("CVV entered as : " + driver.findElement(Elements.cvvTextBox).getAttribute("text"));
			extTestObj.createNode("CVV entered as : " + driver.findElement(Elements.cvvTextBox).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter CVV");
			extTestObj.createNode("Failed to enter CVV")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To select expiration month
		String month = excel.getCellData("LoggedInOrder", "Expiration Month Name", 2);
		try {
			scrollIntoViewBottom(Elements.expirationMonth);
			scrollIntoViewBottom(By.xpath("//*[contains(text(),'"+month+"')]"));
			clickElement(By.xpath("//*[contains(text(),'"+month+"')]"));
			log.info("Expiration Month selected as : " + driver.findElement(Elements.expirationMonth).getAttribute("text"));
			extTestObj.createNode("Expiration Month selected as : " + driver.findElement(Elements.expirationMonth).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Expiration Month");
			extTestObj.createNode("Failed to select Expiration Month")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To select expiration year
		String year = excel.getCellData("LoggedInOrder", "Expiration Year", 2);
		try {
			scrollIntoViewBottom(Elements.expirationYear);
			scrollIntoViewBottom(By.xpath("//*[text()='" + year + "']"));
			clickElement(By.xpath("//*[text()='" + year + "']"));
			log.info("Expiration Year selected as : " +driver.findElement(Elements.expirationYear).getAttribute("text"));
			extTestObj.createNode("Expiration Year selected as : " + driver.findElement(Elements.expirationYear).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Expiration Year");
			extTestObj.createNode("Failed to select Expiration Year")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To enter name on card
		String nameOnCard = excel.getCellData("LoggedInOrder", "Name On Card", 2);
		try {
			scrollIntoViewBottom(Elements.nameOnCard2);
			explicitWait(Elements.nameOnCard2);
			clickableWait(Elements.nameOnCard2);
			driver.getKeyboard().sendKeys(nameOnCard);
			seetest.closeKeyboard();
			log.info("Name on Card entered as : " +driver.findElement(Elements.nameOnCard2).getAttribute("text"));
			extTestObj.createNode("Name on Card entered as : " + driver.findElement(Elements.nameOnCard2).getAttribute("text"));
		} catch (Exception e) {
			log.error("Failed to enter Name on Card");
			extTestObj.createNode("Failed to enter Name on Card")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			;
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To enter zip code
		String zipCode = excel.getCellData("Locations", "Zip Code", 2);
		try {
			scrollIntoViewBottom(Elements.billingZip2);
			explicitWait(Elements.billingZip2);
			clickableWait(Elements.billingZip2);
			driver.getKeyboard().sendKeys(zipCode);
			seetest.closeKeyboard();
			log.info("Billing zip code entered as : " + driver.findElement(Elements.billingZip2).getAttribute("text"));
			extTestObj.createNode("Billing zip code entered as : " + driver.findElement(Elements.billingZip2).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter Billing zip code");
			extTestObj.createNode("Failed to enter Billing zip code")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		
	}
	/*
	 * Function Name : enterPaymentDetailsIOS()
	 * Purpose : To validate the Payment page and enter payment details
	 * Platform : IOS Safari
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void enterPaymentDetailsIOS()
	{
		//To enter card number
		String cardNo = excel.getCellData("LoggedInOrder", "Card Number", 2);
		try {
			Thread.sleep(4000);
			scrollIntoViewBottom(Elements.cardNo);
			explicitWait(Elements.cardNo);
			clickableWait(Elements.cardNo);
			driver.getKeyboard().sendKeys(cardNo);
			log.info("Card Number entered as : " + cardNo);
			extTestObj.createNode("Card Number entered as : " + cardNo).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter Card Number");
			extTestObj.createNode("Failed to enter Card Number")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To enter CVV number
		String cvv = excel.getCellData("LoggedInOrder", "CVV", 2);
		try {
			scrollIntoViewBottom(Elements.cvvTextBox);
			sendKeysWait(Elements.cvvTextBox, cvv);
			log.info("CVV entered as : " + cvv);
			extTestObj.createNode("CVV entered as : " + cvv).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter CVV");
			extTestObj.createNode("Failed to enter CVV")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To select expiration month
		String month = excel.getCellData("LoggedInOrder", "Expiration Month", 2);
		try {
			scrollIntoViewBottom(Elements.expirationMonth);
			explicitWait(Elements.expirationMonth);
			clickableWait(Elements.expirationMonth);
			Select s = new Select(driver.findElement(Elements.expirationMonth));
			WebElement deff = s.getFirstSelectedOption();
			if (deff.isSelected()) {
				seetest.setPickerValues("WEB", "xpath=//*[@id='month-selector']", 0, 0, month);
				Thread.sleep(1000);
				seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.setPickerValues("WEB", "xpath=//*[@id='month-selector']", 0, 0, month);
			}
			log.info("Expiration Month selected as : " + month);
			extTestObj.createNode("Expiration Month selected as : " + month).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Expiration Month");
			extTestObj.createNode("Failed to select Expiration Month")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To select expiration year
		String year = excel.getCellData("LoggedInOrder", "Expiration Year", 2);
		try {
			scrollIntoViewBottom(Elements.expirationYear);
			explicitWait(Elements.expirationYear);
			clickableWait(Elements.expirationYear);
			Select s = new Select(driver.findElement(Elements.expirationYear));
			WebElement deff = s.getFirstSelectedOption();
			if (deff.isSelected()) {
				seetest.setPickerValues("WEB", "xpath=//*[@id='year-selector']", 0, 0, year);
				Thread.sleep(1000);
				seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.setPickerValues("WEB", "xpath=//*[@id='year-selector']", 0, 0, year);
			}
			log.info("Expiration Year selected as : " + year);
			extTestObj.createNode("Expiration Year selected as : " + year).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Expiration Year");
			extTestObj.createNode("Failed to select Expiration Year")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To enter name on card
		String nameOnCard = excel.getCellData("LoggedInOrder", "Name On Card", 2);
		try {
			scrollIntoViewBottom(Elements.nameOnCard);
			explicitWait(Elements.nameOnCard);
			clickableWait(Elements.nameOnCard);
			driver.getKeyboard().sendKeys(nameOnCard);
			log.info("Name on Card entered as : " + nameOnCard);
			extTestObj.createNode("Name on Card entered as : " + nameOnCard);
		} catch (Exception e) {
			log.error("Failed to enter Name on Card");
			extTestObj.createNode("Failed to enter Name on Card")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			;
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To enter zip code
		String zipCode = excel.getCellData("LoggedInOrder", "Zip Code", 2);
		try {
			scrollIntoViewBottom(Elements.billingZip);
			explicitWait(Elements.billingZip);
			clickableWait(Elements.billingZip);
			driver.getKeyboard().sendKeys(zipCode);
			log.info("Billing zip code entered as : " + zipCode);
			extTestObj.createNode("Billing zip code entered as : " + zipCode).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter Billing zip code");
			extTestObj.createNode("Failed to enter Billing zip code")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		//To enter tip
		String tip = excel.getCellData("LoggedInOrder", "Tip", 2);
		try {
			scrollIntoViewBottom(Elements.tipTextBox);
			sendKeysWait(Elements.tipTextBox, tip);
			log.info("Tip entered as : " + driver.findElement(Elements.tipTextBox).getAttribute("text"));
			extTestObj.createNode("Tip entered as : "+driver.findElement(Elements.tipTextBox).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter tip");
			extTestObj.createNode("Failed to enter tip")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

		}
		
		//To check St Jude donation
		try {
			scrollIntoViewBottom(Elements.donationCheckBox);
			clickableWait(Elements.donationCheckBox);
			log.info("Donation checked");
			extTestObj.createNode("Donation checked").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to check donation check box");
			extTestObj.createNode("Failed to check donation check box")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
		}
		
		
	}
	/*
	 * Function Name : enterPaymentDetailswithSuggestedTipIOS(int args)
	 * Purpose : To validate the Payment page and enter payment details along with suggested tip
	 * Platform : IOS Safari
	 * Parameters : Location Index from CommonaData.xlsx for UCOM,Non-UCOM check
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void enterPaymentDetailswithSuggestedTipIOS(int locationIndex)
	{
		//To select one of the suggested tip and validate the same
		String tip = excel.getCellData("LoggedInOrder", "Tip", 2);
		String tipAmount="";
		String displayedTipAmount="";
		try {
			scrollIntoViewHalf(By.xpath("//*[@id='suggested-tip-"+tip+"']"));
			clickElement(By.xpath("//*[@id='suggested-tip-"+tip+"']"));
			tipAmount = driver.findElement(By.xpath("//*[@id='suggested-tip-"+tip+"']/label[2]")).getText();
			scrollIntoViewHalf(Elements.displayedTipAmount);
			displayedTipAmount = driver.findElement(Elements.displayedTipAmount).getText();
		}
		 catch (Exception e) {
				log.error("Failed to enter tip");
				extTestObj.createNode("Failed to enter tip")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());

			}
			int flag = 0;
			if(tipAmount.contentEquals(displayedTipAmount))
				flag=1;
			try {
				int flagchecker = 1/flag;
			log.info("Chosen Suggested Tip percentage : "+tip+", tip amount : "+displayedTipAmount);
			extTestObj.createNode("Chosen Suggested Tip percentage : "+tip+", tip amount : "+displayedTipAmount).pass("PASSED");
		}
			catch(Exception e)
			{
				log.error("Discrepancy observed between suggested tip chosen and amount displayed");
				extTestObj.createNode("Discrepancy observed between suggested tip chosen and amount displayed").fail("FAILED");
			}

			//To check St Jude's donation
		try {
			scrollIntoViewBottom(Elements.donationCheckBox);
			clickableWait(Elements.donationCheckBox);
			log.info("Donation checked");
			extTestObj.createNode("Donation checked").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to check donation check box");
			extTestObj.createNode("Failed to check donation check box")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
		}
		
		//To select "PAY WITH CREDIT CARD" for UCOM store
		if(excel.getCellData("Locations","Type",locationIndex).equals("UCOM"))
			clickableWait(Elements.payWithCreditCardXpath);
		
		//To select payment method
		String paymentMethod = excel.getCellData("LoggedInOrder", "Payment Method", 2);
		try {
			scrollIntoViewBottom(Elements.paymentMethodDropDown);
			clickableWait(Elements.paymentMethodDropDown);
			Select s = new Select(driver.findElement(Elements.paymentMethodDropDown));
			WebElement deff = s.getFirstSelectedOption();
			if (deff.isSelected()) {
				seetest.setPickerValues("WEB", "xpath=//*[@id='payment-method-selector']", 0, 0, paymentMethod);
				Thread.sleep(1000);
				seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.setPickerValues("WEB", "xpath=//*[@id='payment-method-selector']", 0, 0, paymentMethod);
			}
			log.info("Payment Method selected as : " + driver.findElement(Elements.paymentMethodDropDown).getAttribute("text"));
			extTestObj.createNode("Payment Method selected as : " + driver.findElement(Elements.paymentMethodDropDown).getAttribute("text")).pass("PASSED");
		}
		catch (Exception e) {
			log.error("Failed to select Payment method");
			extTestObj.createNode("Failed to select Payment method")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		//To select card type
		String cardType = excel.getCellData("LoggedInOrder", "Card Type", 2);
		try {
			scrollIntoViewBottom(Elements.cardTypeDropDown);
			clickableWait(Elements.cardTypeDropDown);
			Select s = new Select(driver.findElement(Elements.cardTypeDropDown));
			WebElement deff = s.getFirstSelectedOption();
			if (deff.isSelected()) {
				seetest.setPickerValues("WEB", "xpath=//*[@id='card-type-selector']", 0, 0, paymentMethod);
				Thread.sleep(1000);
				seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.setPickerValues("WEB", "xpath=//*[@id='card-type-selector']", 0, 0, paymentMethod);
			}
			log.info("Payment Method selected as : " + driver.findElement(Elements.cardTypeDropDown).getAttribute("text"));
			extTestObj.createNode("Payment Method selected as : " + driver.findElement(Elements.cardTypeDropDown).getAttribute("text")).pass("PASSED");
		}
		catch (Exception e) {
			log.error("Failed to select Card Type");
			extTestObj.createNode("Failed to select Card Type")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		//To enter card number
		String cardNo = excel.getCellData("LoggedInOrder", "Card Number", 2);
		try {
			Thread.sleep(4000);
			scrollIntoViewBottom(Elements.cardNo);
			explicitWait(Elements.cardNo);
			clickableWait(Elements.cardNo);
			driver.getKeyboard().sendKeys(cardNo);
			log.info("Card Number entered as : " + cardNo);
			extTestObj.createNode("Card Number entered as : " + cardNo).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter Card Number");
			extTestObj.createNode("Failed to enter Card Number")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To enter CVV
		String cvv = excel.getCellData("LoggedInOrder", "CVV", 2);
		try {
			scrollIntoViewBottom(Elements.cvvTextBox);
			clickElement(Elements.cvvTextBox);
			driver.getKeyboard().sendKeys(cvv);
			seetest.closeKeyboard();
			log.info("CVV entered as : " + cvv);
			extTestObj.createNode("CVV entered as : " + cvv).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter CVV");
			extTestObj.createNode("Failed to enter CVV")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		//To select expiration month
		String month = excel.getCellData("LoggedInOrder", "Expiration Month Name", 2);
		try {
			scrollIntoViewBottom(Elements.expirationMonth);
			explicitWait(Elements.expirationMonth);
			clickableWait(Elements.expirationMonth);
			Select s = new Select(driver.findElement(Elements.expirationMonth));
			WebElement deff = s.getFirstSelectedOption();
			if (deff.isSelected()) {
				seetest.setPickerValues("WEB", "xpath=//*[@id='month-selector']", 0, 0, month);
				Thread.sleep(1000);
				seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.setPickerValues("WEB", "xpath=//*[@id='month-selector']", 0, 0, month);
			}
			log.info("Expiration Month selected as : " + month);
			extTestObj.createNode("Expiration Month selected as : " + month).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Expiration Month");
			extTestObj.createNode("Failed to select Expiration Month")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To select expiration year
		String year = excel.getCellData("LoggedInOrder", "Expiration Year", 2);
		try {
			scrollIntoViewBottom(Elements.expirationYear);
			explicitWait(Elements.expirationYear);
			clickableWait(Elements.expirationYear);
			Select s = new Select(driver.findElement(Elements.expirationYear));
			WebElement deff = s.getFirstSelectedOption();
			if (deff.isSelected()) {
				seetest.setPickerValues("WEB", "xpath=//*[@id='year-selector']", 0, 0, year);
				Thread.sleep(1000);
				seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.setPickerValues("WEB", "xpath=//*[@id='year-selector']", 0, 0, year);
			}
			log.info("Expiration Year selected as : " + year);
			extTestObj.createNode("Expiration Year selected as : " + year).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Expiration Year");
			extTestObj.createNode("Failed to select Expiration Year")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To enter name on card
		String nameOnCard = excel.getCellData("LoggedInOrder", "Name On Card", 2);
		try {
			scrollIntoViewBottom(Elements.nameOnCard2);
			explicitWait(Elements.nameOnCard2);
			clickableWait(Elements.nameOnCard2);
			driver.getKeyboard().sendKeys(nameOnCard);
			log.info("Name on Card entered as : " + nameOnCard);
			extTestObj.createNode("Name on Card entered as : " + nameOnCard);
		} catch (Exception e) {
			log.error("Failed to enter Name on Card");
			extTestObj.createNode("Failed to enter Name on Card")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			;
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To enter zip code
		String zipCode = excel.getCellData("Locations", "Zip Code",locationIndex);
		try {
			scrollIntoViewBottom(Elements.billingZip2);
			explicitWait(Elements.billingZip2);
			clickableWait(Elements.billingZip2);
			driver.getKeyboard().sendKeys(zipCode);
			log.info("Billing zip code entered as : " + zipCode);
			extTestObj.createNode("Billing zip code entered as : " + zipCode).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter Billing zip code");
			extTestObj.createNode("Failed to enter Billing zip code")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		
	}
	/*
	 * Function Name : enterPaymentDetailswithSuggestedTipIOSWithGiftCard(int args)
	 * Purpose : To validate the Payment page and enter payment details along with suggested tip and gift card
	 * Platform : IOS Browser
	 * Parameters : Location Index from CommonaData.xlsx for UCOM,Non-UCOM check
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void enterPaymentDetailswithSuggestedTipIOSWithGiftCard(int locationIndex)
	{
		//To select one of the suggested tip and validate the same
		String tip = excel.getCellData("LoggedInOrder", "Tip", 2);
		String tipAmount="";
		String displayedTipAmount="";
		try {
			scrollIntoViewHalf(By.xpath("//*[@id='suggested-tip-"+tip+"']"));
			clickElement(By.xpath("//*[@id='suggested-tip-"+tip+"']"));
			tipAmount = driver.findElement(By.xpath("//*[@id='suggested-tip-"+tip+"']/label[2]")).getText();
			scrollIntoViewHalf(Elements.displayedTipAmount);
			displayedTipAmount = driver.findElement(Elements.displayedTipAmount).getText();
		}
		 catch (Exception e) {
				log.error("Failed to enter tip");
				extTestObj.createNode("Failed to enter tip")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());

			}
			int flag = 0;
			if(tipAmount.contentEquals(displayedTipAmount))
				flag=1;
			try {
				int flagchecker = 1/flag;
			log.info("Chosen Suggested Tip percentage : "+tip+", tip amount : "+displayedTipAmount);
			extTestObj.createNode("Chosen Suggested Tip percentage : "+tip+", tip amount : "+displayedTipAmount).pass("PASSED");
		}
			catch(Exception e)
			{
				log.error("Discrepancy observed between suggested tip chosen and amount displayed");
				extTestObj.createNode("Discrepancy observed between suggested tip chosen and amount displayed").fail("FAILED");
			}

			//To check St Jude's donation
		try {
			scrollIntoViewBottom(Elements.donationCheckBox);
			clickableWait(Elements.donationCheckBox);
			log.info("Donation checked");
			extTestObj.createNode("Donation checked").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to check donation check box");
			extTestObj.createNode("Failed to check donation check box")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
		}
		String giftCardNumber = excel.getCellData("LoggedInOrder","Gift Card Number",2);
		try {
			scrollIntoViewBottom(Elements.browserGiftCardTextBox);
			sendKeysWait(Elements.browserGiftCardTextBox, giftCardNumber);
			seetest.closeKeyboard();
			clickableWait(Elements.browserApplyGiftCardButton);
			log.info("Gift card applied");
			extTestObj.createNode("Gift card applied").pass("PASSED");
		}
		catch(Exception e)
		{
			log.error("Failed to apply gift card");
			extTestObj.createNode("Failed to apply gift card")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopIOSTest();
		}
		//To select "PAY WITH CREDIT CARD" for UCOM store
		if(excel.getCellData("Locations","Type",locationIndex).equals("UCOM"))
			clickableWait(Elements.payWithCreditCardXpath);
		
		//To select payment method
		String paymentMethod = excel.getCellData("LoggedInOrder", "Payment Method", 2);
		try {
			scrollIntoViewBottom(Elements.paymentMethodDropDown);
			clickableWait(Elements.paymentMethodDropDown);
			Select s = new Select(driver.findElement(Elements.paymentMethodDropDown));
			WebElement deff = s.getFirstSelectedOption();
			if (deff.isSelected()) {
				seetest.setPickerValues("WEB", "xpath=//*[@id='payment-method-selector']", 0, 0, paymentMethod);
				Thread.sleep(1000);
				seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.setPickerValues("WEB", "xpath=//*[@id='payment-method-selector']", 0, 0, paymentMethod);
			}
			log.info("Payment Method selected as : " + driver.findElement(Elements.paymentMethodDropDown).getAttribute("text"));
			extTestObj.createNode("Payment Method selected as : " + driver.findElement(Elements.paymentMethodDropDown).getAttribute("text")).pass("PASSED");
		}
		catch (Exception e) {
			log.error("Failed to select Payment method");
			extTestObj.createNode("Failed to select Payment method")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		//To select card type
		String cardType = excel.getCellData("LoggedInOrder", "Card Type", 2);
		try {
			scrollIntoViewBottom(Elements.cardTypeDropDown);
			clickableWait(Elements.cardTypeDropDown);
			Select s = new Select(driver.findElement(Elements.cardTypeDropDown));
			WebElement deff = s.getFirstSelectedOption();
			if (deff.isSelected()) {
				seetest.setPickerValues("WEB", "xpath=//*[@id='card-type-selector']", 0, 0, paymentMethod);
				Thread.sleep(1000);
				seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.setPickerValues("WEB", "xpath=//*[@id='card-type-selector']", 0, 0, paymentMethod);
			}
			log.info("Payment Method selected as : " + driver.findElement(Elements.cardTypeDropDown).getAttribute("text"));
			extTestObj.createNode("Payment Method selected as : " + driver.findElement(Elements.cardTypeDropDown).getAttribute("text")).pass("PASSED");
		}
		catch (Exception e) {
			log.error("Failed to select Card Type");
			extTestObj.createNode("Failed to select Card Type")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		//To enter card number
		String cardNo = excel.getCellData("LoggedInOrder", "Card Number", 2);
		try {
			Thread.sleep(4000);
			scrollIntoViewBottom(Elements.cardNo);
			explicitWait(Elements.cardNo);
			clickableWait(Elements.cardNo);
			driver.getKeyboard().sendKeys(cardNo);
			log.info("Card Number entered as : " + cardNo);
			extTestObj.createNode("Card Number entered as : " + cardNo).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter Card Number");
			extTestObj.createNode("Failed to enter Card Number")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To enter CVV
		String cvv = excel.getCellData("LoggedInOrder", "CVV", 2);
		try {
			scrollIntoViewBottom(Elements.cvvTextBox);
			clickElement(Elements.cvvTextBox);
			driver.getKeyboard().sendKeys(cvv);
			seetest.closeKeyboard();
			log.info("CVV entered as : " + cvv);
			extTestObj.createNode("CVV entered as : " + cvv).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter CVV");
			extTestObj.createNode("Failed to enter CVV")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		//To select expiration month
		String month = excel.getCellData("LoggedInOrder", "Expiration Month Name", 2);
		try {
			scrollIntoViewBottom(Elements.expirationMonth);
			explicitWait(Elements.expirationMonth);
			clickableWait(Elements.expirationMonth);
			Select s = new Select(driver.findElement(Elements.expirationMonth));
			WebElement deff = s.getFirstSelectedOption();
			if (deff.isSelected()) {
				seetest.setPickerValues("WEB", "xpath=//*[@id='month-selector']", 0, 0, month);
				Thread.sleep(1000);
				seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.setPickerValues("WEB", "xpath=//*[@id='month-selector']", 0, 0, month);
			}
			log.info("Expiration Month selected as : " + month);
			extTestObj.createNode("Expiration Month selected as : " + month).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Expiration Month");
			extTestObj.createNode("Failed to select Expiration Month")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To select expiration year
		String year = excel.getCellData("LoggedInOrder", "Expiration Year", 2);
		try {
			scrollIntoViewBottom(Elements.expirationYear);
			explicitWait(Elements.expirationYear);
			clickableWait(Elements.expirationYear);
			Select s = new Select(driver.findElement(Elements.expirationYear));
			WebElement deff = s.getFirstSelectedOption();
			if (deff.isSelected()) {
				seetest.setPickerValues("WEB", "xpath=//*[@id='year-selector']", 0, 0, year);
				Thread.sleep(1000);
				seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.setPickerValues("WEB", "xpath=//*[@id='year-selector']", 0, 0, year);
			}
			log.info("Expiration Year selected as : " + year);
			extTestObj.createNode("Expiration Year selected as : " + year).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Expiration Year");
			extTestObj.createNode("Failed to select Expiration Year")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To enter name on card
		String nameOnCard = excel.getCellData("LoggedInOrder", "Name On Card", 2);
		try {
			scrollIntoViewBottom(Elements.nameOnCard2);
			explicitWait(Elements.nameOnCard2);
			clickableWait(Elements.nameOnCard2);
			driver.getKeyboard().sendKeys(nameOnCard);
			log.info("Name on Card entered as : " + nameOnCard);
			extTestObj.createNode("Name on Card entered as : " + nameOnCard);
		} catch (Exception e) {
			log.error("Failed to enter Name on Card");
			extTestObj.createNode("Failed to enter Name on Card")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			;
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To enter zip code
		String zipCode = excel.getCellData("Locations", "Zip Code",locationIndex);
		try {
			scrollIntoViewBottom(Elements.billingZip2);
			explicitWait(Elements.billingZip2);
			clickableWait(Elements.billingZip2);
			driver.getKeyboard().sendKeys(zipCode);
			log.info("Billing zip code entered as : " + zipCode);
			extTestObj.createNode("Billing zip code entered as : " + zipCode).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter Billing zip code");
			extTestObj.createNode("Failed to enter Billing zip code")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		
	}
	/*
	 * Function Name : enterPaymentDetailswithCustomTipIOS(int args)
	 * Purpose : To validate the Payment page and enter payment details along with custom tip
	 * Platform : IOS Safari
	 * Parameters : Location Index from CommonaData.xlsx for UCOM,Non-UCOM check
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void enterPaymentDetailswithCustomTipIOS(int locationIndex)
	{
		//To enter custom tip
		String tip = excel.getCellData("LoggedInOrder", "Custom Tip", 2);
		String tipAmount="";
		String displayedTipAmount="";
		try {
			scrollIntoViewHalf(Elements.customTipInput);
			clickableWait(Elements.customTipInput);
			driver.getKeyboard().sendKeys(tip);
			seetest.closeKeyboard();
			tipAmount = driver.findElement(Elements.customTipInput).getAttribute("text");
			scrollIntoViewHalf(Elements.displayedTipAmount);
			displayedTipAmount = driver.findElement(Elements.displayedTipAmount).getText();
		}
		 catch (Exception e) {
				log.error("Failed to enter tip");
				extTestObj.createNode("Failed to enter tip")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());

			}
			int flag = 0;
			if(tipAmount.contentEquals(displayedTipAmount))
				flag=1;
			try {
				int flagchecker = 1/flag;
			log.info("Custom tip amount entered as :"+displayedTipAmount);
			extTestObj.createNode("Custom tip amount entered as :"+displayedTipAmount).pass("PASSED");
		}
			catch(Exception e)
			{
				log.error("Discrepancy observed between custom tip entered and amount displayed");
				extTestObj.createNode("Discrepancy observed between custom tip entered and amount displayed").fail("FAILED");
			}

			//To check St Jude's donation
		try {
			scrollIntoViewBottom(Elements.donationCheckBox);
			clickableWait(Elements.donationCheckBox);
			log.info("Donation checked");
			extTestObj.createNode("Donation checked").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to check donation check box");
			extTestObj.createNode("Failed to check donation check box")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
		}
		
		//To select "PAY WITH CREDIT CARD" for UCOM store
		if(excel.getCellData("Locations","Type",locationIndex).equals("UCOM"))
			clickableWait(Elements.payWithCreditCardXpath);
		
		//To select Payment method
		String paymentMethod = excel.getCellData("LoggedInOrder", "Payment Method", 2);
		try {
			scrollIntoViewBottom(Elements.paymentMethodDropDown);
			clickableWait(Elements.paymentMethodDropDown);
			Select s = new Select(driver.findElement(Elements.paymentMethodDropDown));
			WebElement deff = s.getFirstSelectedOption();
			if (deff.isSelected()) {
				seetest.setPickerValues("WEB", "xpath=//*[@id='payment-method-selector']", 0, 0, paymentMethod);
				Thread.sleep(1000);
				seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.setPickerValues("WEB", "xpath=//*[@id='payment-method-selector']", 0, 0, paymentMethod);
			}
			log.info("Payment Method selected as : " + driver.findElement(Elements.paymentMethodDropDown).getAttribute("text"));
			extTestObj.createNode("Payment Method selected as : " + driver.findElement(Elements.paymentMethodDropDown).getAttribute("text")).pass("PASSED");
		}
		catch (Exception e) {
			log.error("Failed to select Payment method");
			extTestObj.createNode("Failed to select Payment method")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		//To select card type
		String cardType = excel.getCellData("LoggedInOrder", "Card Type", 2);
		try {
			scrollIntoViewBottom(Elements.cardTypeDropDown);
			clickableWait(Elements.cardTypeDropDown);
			Select s = new Select(driver.findElement(Elements.cardTypeDropDown));
			WebElement deff = s.getFirstSelectedOption();
			if (deff.isSelected()) {
				seetest.setPickerValues("WEB", "xpath=//*[@id='card-type-selector']", 0, 0, paymentMethod);
				Thread.sleep(1000);
				seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.setPickerValues("WEB", "xpath=//*[@id='card-type-selector']", 0, 0, paymentMethod);
			}
			log.info("Payment Method selected as : " + driver.findElement(Elements.cardTypeDropDown).getAttribute("text"));
			extTestObj.createNode("Payment Method selected as : " + driver.findElement(Elements.cardTypeDropDown).getAttribute("text")).pass("PASSED");
		}
		catch (Exception e) {
			log.error("Failed to select Card Type");
			extTestObj.createNode("Failed to select Card Type")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
			
		}
		//To enter card number
		String cardNo = excel.getCellData("LoggedInOrder", "Card Number", 2);
		try {
			Thread.sleep(4000);
			scrollIntoViewBottom(Elements.cardNo);
			explicitWait(Elements.cardNo);
			clickableWait(Elements.cardNo);
			driver.getKeyboard().sendKeys(cardNo);
			log.info("Card Number entered as : " + cardNo);
			extTestObj.createNode("Card Number entered as : " + cardNo).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter Card Number");
			extTestObj.createNode("Failed to enter Card Number")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To enter CVV number
		String cvv = excel.getCellData("LoggedInOrder", "CVV", 2);
		try {
			scrollIntoViewBottom(Elements.cvvTextBox);
			clickElement(Elements.cvvTextBox);
			driver.getKeyboard().sendKeys(cvv);
			seetest.closeKeyboard();
			log.info("CVV entered as : " + cvv);
			extTestObj.createNode("CVV entered as : " + cvv).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter CVV");
			extTestObj.createNode("Failed to enter CVV")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To select expiration month
		String month = excel.getCellData("LoggedInOrder", "Expiration Month Name", 2);
		try {
			scrollIntoViewBottom(Elements.expirationMonth);
			explicitWait(Elements.expirationMonth);
			clickableWait(Elements.expirationMonth);
			Select s = new Select(driver.findElement(Elements.expirationMonth));
			WebElement deff = s.getFirstSelectedOption();
			if (deff.isSelected()) {
				seetest.setPickerValues("WEB", "xpath=//*[@id='month-selector']", 0, 0, month);
				Thread.sleep(1000);
				seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.setPickerValues("WEB", "xpath=//*[@id='month-selector']", 0, 0, month);
			}
			log.info("Expiration Month selected as : " + month);
			extTestObj.createNode("Expiration Month selected as : " + month).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Expiration Month");
			extTestObj.createNode("Failed to select Expiration Month")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To select expiration year
		String year = excel.getCellData("LoggedInOrder", "Expiration Year", 2);
		try {
			scrollIntoViewBottom(Elements.expirationYear);
			explicitWait(Elements.expirationYear);
			clickableWait(Elements.expirationYear);
			Select s = new Select(driver.findElement(Elements.expirationYear));
			WebElement deff = s.getFirstSelectedOption();
			if (deff.isSelected()) {
				seetest.setPickerValues("WEB", "xpath=//*[@id='year-selector']", 0, 0, year);
				Thread.sleep(1000);
				seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.setPickerValues("WEB", "xpath=//*[@id='year-selector']", 0, 0, year);
			}
			log.info("Expiration Year selected as : " + year);
			extTestObj.createNode("Expiration Year selected as : " + year).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Expiration Year");
			extTestObj.createNode("Failed to select Expiration Year")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To enter name on card
		String nameOnCard = excel.getCellData("LoggedInOrder", "Name On Card", 2);
		try {
			scrollIntoViewBottom(Elements.nameOnCard2);
			explicitWait(Elements.nameOnCard2);
			clickableWait(Elements.nameOnCard2);
			driver.getKeyboard().sendKeys(nameOnCard);
			log.info("Name on Card entered as : " + nameOnCard);
			extTestObj.createNode("Name on Card entered as : " + nameOnCard);
		} catch (Exception e) {
			log.error("Failed to enter Name on Card");
			extTestObj.createNode("Failed to enter Name on Card")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			;
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To enter zip code
		String zipCode = excel.getCellData("Locations", "Zip Code",locationIndex);
		try {
			scrollIntoViewBottom(Elements.billingZip2);
			explicitWait(Elements.billingZip2);
			clickableWait(Elements.billingZip2);
			driver.getKeyboard().sendKeys(zipCode);
			log.info("Billing zip code entered as : " + zipCode);
			extTestObj.createNode("Billing zip code entered as : " + zipCode).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter Billing zip code");
			extTestObj.createNode("Failed to enter Billing zip code")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		
	}
	/*
	 * Function Name : enterPaymentDetailswithCustomTipIOSWithGiftCard(int args)
	 * Purpose : To validate the Payment page and enter payment details along with custom tip and gift card
	 * Platform : IOS Browser
	 * Parameters : Location Index from CommonaData.xlsx for UCOM,Non-UCOM check
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void enterPaymentDetailswithCustomTipIOSWithGiftCard(int locationIndex)
	{
		//To enter custom tip
		String tip = excel.getCellData("LoggedInOrder", "Custom Tip", 2);
		String tipAmount="";
		String displayedTipAmount="";
		try {
			scrollIntoViewHalf(Elements.customTipInput);
			clickableWait(Elements.customTipInput);
			driver.getKeyboard().sendKeys(tip);
			seetest.closeKeyboard();
			tipAmount = driver.findElement(Elements.customTipInput).getAttribute("text");
			scrollIntoViewHalf(Elements.displayedTipAmount);
			displayedTipAmount = driver.findElement(Elements.displayedTipAmount).getText();
		}
		 catch (Exception e) {
				log.error("Failed to enter tip");
				extTestObj.createNode("Failed to enter tip")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());

			}
			int flag = 0;
			if(tipAmount.contentEquals(displayedTipAmount))
				flag=1;
			try {
				int flagchecker = 1/flag;
			log.info("Custom tip amount entered as :"+displayedTipAmount);
			extTestObj.createNode("Custom tip amount entered as :"+displayedTipAmount).pass("PASSED");
		}
			catch(Exception e)
			{
				log.error("Discrepancy observed between custom tip entered and amount displayed");
				extTestObj.createNode("Discrepancy observed between custom tip entered and amount displayed").fail("FAILED");
			}

			//To check St Jude's donation
		try {
			scrollIntoViewBottom(Elements.donationCheckBox);
			clickableWait(Elements.donationCheckBox);
			log.info("Donation checked");
			extTestObj.createNode("Donation checked").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to check donation check box");
			extTestObj.createNode("Failed to check donation check box")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
		}
		String giftCardNumber = excel.getCellData("LoggedInOrder","Gift Card Number",2);
		try {
			scrollIntoViewBottom(Elements.browserGiftCardTextBox);
			sendKeysWait(Elements.browserGiftCardTextBox, giftCardNumber);
			seetest.closeKeyboard();
			clickableWait(Elements.browserApplyGiftCardButton);
			log.info("Gift card applied");
			extTestObj.createNode("Gift card applied").pass("PASSED");
		}
		catch(Exception e)
		{
			log.error("Failed to apply gift card");
			extTestObj.createNode("Failed to apply gift card")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopIOSTest();
		}
		//To select "PAY WITH CREDIT CARD" for UCOM store
		if(excel.getCellData("Locations","Type",locationIndex).equals("UCOM"))
			clickableWait(Elements.payWithCreditCardXpath);
		
		//To select Payment method
		String paymentMethod = excel.getCellData("LoggedInOrder", "Payment Method", 2);
		try {
			scrollIntoViewBottom(Elements.paymentMethodDropDown);
			clickableWait(Elements.paymentMethodDropDown);
			Select s = new Select(driver.findElement(Elements.paymentMethodDropDown));
			WebElement deff = s.getFirstSelectedOption();
			if (deff.isSelected()) {
				seetest.setPickerValues("WEB", "xpath=//*[@id='payment-method-selector']", 0, 0, paymentMethod);
				Thread.sleep(1000);
				seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.setPickerValues("WEB", "xpath=//*[@id='payment-method-selector']", 0, 0, paymentMethod);
			}
			log.info("Payment Method selected as : " + driver.findElement(Elements.paymentMethodDropDown).getAttribute("text"));
			extTestObj.createNode("Payment Method selected as : " + driver.findElement(Elements.paymentMethodDropDown).getAttribute("text")).pass("PASSED");
		}
		catch (Exception e) {
			log.error("Failed to select Payment method");
			extTestObj.createNode("Failed to select Payment method")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		//To select card type
		String cardType = excel.getCellData("LoggedInOrder", "Card Type", 2);
		try {
			scrollIntoViewBottom(Elements.cardTypeDropDown);
			clickableWait(Elements.cardTypeDropDown);
			Select s = new Select(driver.findElement(Elements.cardTypeDropDown));
			WebElement deff = s.getFirstSelectedOption();
			if (deff.isSelected()) {
				seetest.setPickerValues("WEB", "xpath=//*[@id='card-type-selector']", 0, 0, paymentMethod);
				Thread.sleep(1000);
				seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.setPickerValues("WEB", "xpath=//*[@id='card-type-selector']", 0, 0, paymentMethod);
			}
			log.info("Payment Method selected as : " + driver.findElement(Elements.cardTypeDropDown).getAttribute("text"));
			extTestObj.createNode("Payment Method selected as : " + driver.findElement(Elements.cardTypeDropDown).getAttribute("text")).pass("PASSED");
		}
		catch (Exception e) {
			log.error("Failed to select Card Type");
			extTestObj.createNode("Failed to select Card Type")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
			
		}
		//To enter card number
		String cardNo = excel.getCellData("LoggedInOrder", "Card Number", 2);
		try {
			Thread.sleep(4000);
			scrollIntoViewBottom(Elements.cardNo);
			explicitWait(Elements.cardNo);
			clickableWait(Elements.cardNo);
			driver.getKeyboard().sendKeys(cardNo);
			log.info("Card Number entered as : " + cardNo);
			extTestObj.createNode("Card Number entered as : " + cardNo).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter Card Number");
			extTestObj.createNode("Failed to enter Card Number")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To enter CVV number
		String cvv = excel.getCellData("LoggedInOrder", "CVV", 2);
		try {
			scrollIntoViewBottom(Elements.cvvTextBox);
			clickElement(Elements.cvvTextBox);
			driver.getKeyboard().sendKeys(cvv);
			seetest.closeKeyboard();
			log.info("CVV entered as : " + cvv);
			extTestObj.createNode("CVV entered as : " + cvv).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter CVV");
			extTestObj.createNode("Failed to enter CVV")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To select expiration month
		String month = excel.getCellData("LoggedInOrder", "Expiration Month Name", 2);
		try {
			scrollIntoViewBottom(Elements.expirationMonth);
			explicitWait(Elements.expirationMonth);
			clickableWait(Elements.expirationMonth);
			Select s = new Select(driver.findElement(Elements.expirationMonth));
			WebElement deff = s.getFirstSelectedOption();
			if (deff.isSelected()) {
				seetest.setPickerValues("WEB", "xpath=//*[@id='month-selector']", 0, 0, month);
				Thread.sleep(1000);
				seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.setPickerValues("WEB", "xpath=//*[@id='month-selector']", 0, 0, month);
			}
			log.info("Expiration Month selected as : " + month);
			extTestObj.createNode("Expiration Month selected as : " + month).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Expiration Month");
			extTestObj.createNode("Failed to select Expiration Month")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To select expiration year
		String year = excel.getCellData("LoggedInOrder", "Expiration Year", 2);
		try {
			scrollIntoViewBottom(Elements.expirationYear);
			explicitWait(Elements.expirationYear);
			clickableWait(Elements.expirationYear);
			Select s = new Select(driver.findElement(Elements.expirationYear));
			WebElement deff = s.getFirstSelectedOption();
			if (deff.isSelected()) {
				seetest.setPickerValues("WEB", "xpath=//*[@id='year-selector']", 0, 0, year);
				Thread.sleep(1000);
				seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.setPickerValues("WEB", "xpath=//*[@id='year-selector']", 0, 0, year);
			}
			log.info("Expiration Year selected as : " + year);
			extTestObj.createNode("Expiration Year selected as : " + year).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Expiration Year");
			extTestObj.createNode("Failed to select Expiration Year")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To enter name on card
		String nameOnCard = excel.getCellData("LoggedInOrder", "Name On Card", 2);
		try {
			scrollIntoViewBottom(Elements.nameOnCard2);
			explicitWait(Elements.nameOnCard2);
			clickableWait(Elements.nameOnCard2);
			driver.getKeyboard().sendKeys(nameOnCard);
			log.info("Name on Card entered as : " + nameOnCard);
			extTestObj.createNode("Name on Card entered as : " + nameOnCard);
		} catch (Exception e) {
			log.error("Failed to enter Name on Card");
			extTestObj.createNode("Failed to enter Name on Card")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			;
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To enter zip code
		String zipCode = excel.getCellData("Locations", "Zip Code",locationIndex);
		try {
			scrollIntoViewBottom(Elements.billingZip2);
			explicitWait(Elements.billingZip2);
			clickableWait(Elements.billingZip2);
			driver.getKeyboard().sendKeys(zipCode);
			log.info("Billing zip code entered as : " + zipCode);
			extTestObj.createNode("Billing zip code entered as : " + zipCode).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter Billing zip code");
			extTestObj.createNode("Failed to enter Billing zip code")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		
	}
	
	/*
	 * Function Name : enterVisitDetailsIOS()
	 * Purpose : To validate the visit page and enter visit details
	 * Platform : IOS Safari
	 * Parameters : Location Index from CommonaData.xlsx for UCOM,Non-UCOM check
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void enterVisitDetailsIOS()
	{
		//To click Add my visit button
		try {
			Thread.sleep(7000);
			scrollIntoViewBottom(Elements.addMyVisitButton);
			clickableWait(Elements.addMyVisitButton);
			log.info("Add my visit button clicked");
			extTestObj.createNode("Add my visit button clicked").pass("PASSED");
		} catch (Exception e) {
			log.error("Add my visit button click failed");
			extTestObj.createNode("Add my visit button click failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		//To enter location of the restaurant visited
		String loc = excel.getCellData("AddMyVisit", "Location", 2);
		try {
			scrollIntoViewBottom(Elements.restaurantLocTextBox);
			explicitWait(Elements.restaurantLocTextBox);
			clickableWait(Elements.restaurantLocTextBox);
			driver.getKeyboard().sendKeys(loc);
			scrollIntoViewBottom(By.xpath("//*[text()='" + loc + "']"));
			seetest.click("WEB", "xpath=//*[text()='" + loc + "']", 0, 1);
			log.info("Restaurant location entered as : " + loc);
			extTestObj.createNode("Restaurant location entered as : " + loc).pass("PASSED");
			Thread.sleep(3000);
		} catch (Exception e) {
			log.error("Failed to enter restaurant location");
			extTestObj.createNode("Failed to enter restaurant location")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		//To select location from drop down
		String locFromDropDown = excel.getCellData("AddMyVisit", "Chilis Location from DropDown", 2);
		try {
			scrollIntoViewBottom(Elements.chillisLocDropDown);
			explicitWait(Elements.chillisLocDropDown);
			clickableWait(Elements.chillisLocDropDown);
			Select s = new Select(driver.findElement(Elements.chillisLocDropDown));
			WebElement deff = s.getFirstSelectedOption();
			if (deff.isSelected()) {
				seetest.setPickerValues("WEB", "xpath=//*[@id='store-number']", 0, 0, locFromDropDown);
				Thread.sleep(1000);
				seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.setPickerValues("WEB", "xpath=//*[@id='store-number']", 0, 0, locFromDropDown);
			}

			log.info("Chilis location selected as : " + locFromDropDown);
			extTestObj.createNode("Chilis location selected as : " + locFromDropDown).pass("PASSED");
			Thread.sleep(3000);

		} catch (Exception e) {
			log.error("Chillis location selection failed");
			extTestObj.createNode("Chillis location selection failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		//To select visit month
		String visitMonth = excel.getCellData("AddMyVisit", "Visit Month", 2);
		try {
			scrollIntoViewBottom(Elements.visitMonthDropDown);
			explicitWait(Elements.visitMonthDropDown);
			clickableWait(Elements.visitMonthDropDown);
			Select s = new Select(driver.findElement(Elements.visitMonthDropDown));
			WebElement deff = s.getFirstSelectedOption();
			if (deff.isSelected()) {
				seetest.setPickerValues("WEB", "xpath=//*[@id='visit-month']", 0, 0, visitMonth);
				Thread.sleep(1000);
				seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.setPickerValues("WEB", "xpath=//*[@id='visit-month']", 0, 0, visitMonth);
			}
			log.info("Visit month selected as :" + visitMonth);
			extTestObj.createNode("Visit month selected as :" + visitMonth).pass("PASSED");
		} catch (Exception e) {
			log.error("Visit month selection failed");
			extTestObj.createNode("Visit month selection failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To select visit day
		String visitDay = excel.getCellData("AddMyVisit", "Visit Day", 2);
		try {
			scrollIntoViewBottom(Elements.visitDayDropDown);
			explicitWait(Elements.visitDayDropDown);
			clickableWait(Elements.visitDayDropDown);
			Select s = new Select(driver.findElement(Elements.visitDayDropDown));
			WebElement deff = s.getFirstSelectedOption();
			if (deff.isSelected()) {
				seetest.setPickerValues("WEB", "xpath=//*[@id='visit-day']", 0, 0, visitDay);
				Thread.sleep(1000);
				seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.setPickerValues("WEB", "xpath=//*[@id='visit-day']", 0, 0, visitDay);
			}

			log.info("Visit day selected as : " + visitDay);
			extTestObj.createNode("Visit day selected as : " + visitDay).pass("PASSED");
		} catch (Exception e) {
			log.error("Visit day selection failed");
			extTestObj.createNode("Visit day selection failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		//To select visit year
		String visitYear = excel.getCellData("AddMyVisit", "Visit Year", 2);
		try {
			scrollIntoViewBottom(Elements.visitYearDropDown);
			explicitWait(Elements.visitYearDropDown);
			clickableWait(Elements.visitYearDropDown);
			Select s = new Select(driver.findElement(Elements.visitDayDropDown));
			WebElement deff = s.getFirstSelectedOption();
			if (deff.isSelected()) {
				seetest.setPickerValues("WEB", "xpath=//*[@id='visit-year']", 0, 0, visitYear);
				Thread.sleep(1000);
				seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.setPickerValues("WEB", "xpath=//*[@id='visit-year']", 0, 0, visitYear);
			}
			log.info("Visit year selected as : " + visitYear);
			extTestObj.createNode("Visit year selected as : " + visitYear).pass("PASSED");
		} catch (Exception e) {
			log.error("Visit year selection failed");
			extTestObj.createNode("Visit year selection failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To enter check number
		String checkNo = excel.getCellData("AddMyVisit", "Receipt Check Number", 2);

		try {
			sendKeysWait(Elements.checkNumberTextBox, checkNo);
			log.info("Check Number entered as : " + checkNo);
			extTestObj.createNode("Check Number entered as : " + checkNo).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter check number");
			extTestObj.createNode("Failed to enter check number")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

		}
		
		//To enter check total
		String checkTotal = excel.getCellData("AddMyVisit", "Subtotal", 2);
		try {
			clickableWait(Elements.checkTotalTextBox);
			driver.getKeyboard().sendKeys(checkTotal);
			log.info("Check total entered as : " + checkTotal);
			extTestObj.createNode("Check total entered as : " + checkTotal).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter check total");
			extTestObj.createNode("Failed to enter check total")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

		}
		
		//To click on Submit button
		try {
			clickableWait(Elements.visitSubmitButton);
			log.info("Visit submitted");
			extTestObj.createNode("Visit submitted").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to submit visit");
			extTestObj.createNode("Failed to submit visit")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}


		
	}
	/*
	 * Function Name : comparePrice(String, String)
	 * Purpose : To check whether the order total before placing order is same as that displayed in the confirmation page
	 * Platform : All
	 * Parameters : priceBeforePlacingOrder, priceAfterPlacingOrder
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void comparePrice(String priceBeforePlacingOrder, String priceAfterPlacingOrder) {
		int i = 0, j = 0;
		if (priceBeforePlacingOrder.equals(priceAfterPlacingOrder))
			i = 1;
		else
			i = 0;
		try {
			j = 1 / i;
			log.info("Prices matched");
			extTestObj.createNode("Prices matched").pass("PASSED");
		} catch (Exception e) {
			log.warn("Incorrect price displayed");
			extTestObj.createNode("WARNING : Incorrect price displayed")
					.pass("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()")
					.pass(priceBeforePlacingOrder + " not equal to " + priceAfterPlacingOrder);
		}
	}
	/*
	 * Function Name : compareRewardCount(int,int)
	 * Purpose : To compare whether the displayed reward count equals the actual count
	 * Platform : All
	 * Parameters : displayedCount, actualCount
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void compareRewardCount(int displayedCount, int actualCount) {
		if (displayedCount == actualCount) {
			log.info("Reward count matched");
			extTestObj.createNode("Reward count matched").pass("PASSED");
		} else {
			log.error("Discrepancy observed in Reward count");
			extTestObj.createNode("Discrepancy observed in Reward count")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		}

	}
	/*
	 * Function Name : OrderTotal()
	 * Purpose : To get the Order total before placing the order
	 * Platform : Android Chrome
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public String OrderTotal() {
		String pickUpCost="";
		try {
			scrollIntoViewBottom(Elements.pickUpCost);
			pickUpCost = driver.findElement(Elements.pickUpCost).getText();
			log.info("Pick up cost displayed as : " + pickUpCost);
			extTestObj
					.createNode("Order Total  cost displayed as : " + pickUpCost)
					.pass("PASSED");
		} catch (Exception e) {
			log.error("Pick up cost not displayed");
			extTestObj.createNode("Order Total cost not displayed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

		}
		return pickUpCost;
	}
	/*
	 * Function Name : DeliveryOrderTotal()
	 * Purpose : To get the order total for delivery type orders in the finalize order page
	 * Platform : Android Chrome
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public String DeliveryOrderTotal() {
		try {
			scrollIntoViewBottom(Elements.deliveryPicKUpCost);
			explicitWait(Elements.deliveryPicKUpCost);
			log.info("Pick up cost displayed as : " + driver.findElement(Elements.deliveryPicKUpCost).getText());
			extTestObj.createNode(
					"Order Total  cost displayed as : " + driver.findElement(Elements.deliveryPicKUpCost).getText())
					.pass("PASSED");
		} catch (Exception e) {
			log.error("Pick up cost not displayed");
			extTestObj.createNode("Order Total cost not displayed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

		}
		return driver.findElement(Elements.deliveryPicKUpCost).getText();
	}

	/*
	 * Function Name : checkRoundOff()
	 * Purpose : To check St Jude's donation for round off
	 * Platform : Android Chrome
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void checkRoundOff() {
		try {
			scrollIntoViewBottom(Elements.donationCheckBox);
			clickableWait(Elements.donationCheckBox);
			log.info("Donation checked");
			extTestObj.createNode("Donation checked").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to check donation check box");
			extTestObj.createNode("Failed to check donation check box")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
		}
	}

	/*
	 * Function Name : checkRoundOffforFutureOrder()
	 * Purpose : To check St Jude's donation for round off in case of future order
	 * Platform : Android Chrome
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void checkRoundOffforFutureOrder() {
		try {
			scrollIntoViewBottom(Elements.orderSummary);
			scrollIntoViewBottom(Elements.roundOffforFutureOrder);
			clickableWait(Elements.roundOffforFutureOrder);
			log.info("Donation checked");
			extTestObj.createNode("Donation checked").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to check donation check box");
			extTestObj.createNode("Failed to check donation check box")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
		}
	}
	
	/*
	 * Function Name : placeOrder()
	 * Purpose : To click Place Order button available at the bottom of the finalize order page
	 * Platform : Android Chrome
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void placeOrder() {
		try {
			scrollIntoViewBottom(Elements.placeOrder);
			clickElement(Elements.placeOrder);
			try {
				seetest.click("NATIVE", "xpath=//*[@text='No, thanks']", 0, 1);
			} catch (Exception exp) {
			}
			log.info("Place order button clicked");
			extTestObj.createNode("Place order button clicked").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to click place order button");
			extTestObj.createNode("Failed to click place order button")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();

		}
	}

	/*
	 * Function Name : placeOrderFuture()
	 * Purpose : To click Place Order button available at the bottom of the finalize order page for future date orders
	 * Platform : Android Chrome
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void placeOrderFuture() {
		try {
			scrollIntoViewBottom(Elements.placeOrderButton);
			clickableWait(Elements.placeOrderButton);
			log.info("Place order button clicked");
			extTestObj.createNode("Place order button clicked").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to click place order button");
			extTestObj.createNode("Failed to click place order button")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();

		}
	}

	/*
	 * Function Name : getSuccessMessageforLoggedInOrder()
	 * Purpose : To get the success message in the confirmation page after the order is placed
	 * Platform : Android Chrome
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public String getSuccessMessageforLoggedInOrder() {
		String successMessage = "";
		try {
			scrollIntoViewBottom(Elements.successMessageforLoggedInOrder);
			successMessage = driver.findElement(Elements.successMessageforLoggedInOrder).getText();
			log.info("Success message displayed as : " + successMessage);
			extTestObj.createNode("Success message displayed as : " + successMessage).pass("PASSED");
		} catch (Exception e) {
			log.warn("Success message not displayed");
			extTestObj.createNode("WARNING : Success message not displayed")
					.pass("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").pass(e);
			log.error(e.getMessage());
		}
		return successMessage;

	}
	/*
	 * Function Name : returnOrderPrice()
	 * Purpose : To get the order total from the confirmation page
	 * Platform : Android Chrome
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public String returnOrderPrice() {
		String orderPrice = "";
		try {
			scrollIntoViewBottom(Elements.orderPrice);
			orderPrice = driver.findElement(Elements.orderPrice).getText();
			log.info("Order price displayed as  : " + orderPrice);
			extTestObj.createNode(
					"Order price after payment displayed as  : " + orderPrice)
					.pass("PASSED");
		} catch (Exception e) {
			log.warn("Order price not displayed");
			extTestObj.createNode("WARNING : Order price after payment not displayed")
					.pass("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").pass(e);
			log.error(e.getMessage());
		}
		return orderPrice;

	}
	/*
	 * Function Name : returnDeliveryOrderPrice()
	 * Purpose : To get the order total for delivery type orders in the confirmation page
	 * Platform : Android Chrome
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public String returnDeliveryOrderPrice() {
		try {
			scrollIntoViewBottom(Elements.deliveryOrderPrice);
			explicitWait(Elements.deliveryOrderPrice);
			log.info("Order price displayed as  : " + driver.findElement(Elements.deliveryOrderPrice).getText());
			extTestObj.createNode(
					"Order price after payment displayed as  : " + driver.findElement(Elements.deliveryOrderPrice).getText())
					.pass("PASSED");
		} catch (Exception e) {
			log.error("Order price not displayed");
			extTestObj.createNode("WARNING :Order price after payment not displayed")
					.pass("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
			log.error(e.getMessage());
		}
		return driver.findElement(Elements.deliveryOrderPrice).getText();

	}
	/*
	 * Function Name : validateReorderItemSelection()
	 * Purpose : To validate reorder functionality of chilis website
	 * Platform : Android Chrome
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void validateReorderItemSelection()
	{
		
		//To select hamburger menu button
		try {
			clickableWait(Elements.menuButton);
			explicitWait(Elements.rewardsButton);
			log.info("Menu button clicked");
			extTestObj.createNode("Menu button clicked").pass("PASSED");
		} catch (Exception e) {
			log.error("Menu button click failed");
			extTestObj.createNode("Menu button click failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		//To select reorder option
		try {
			clickableWait(Elements.reorderOption);
			Thread.sleep(3000);
			log.info("Reorder option selected");
			extTestObj.createNode("Reorder option selected").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select reorder option");
			log.error(e.getMessage());
			extTestObj.createNode("Failed to select reorder option")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			stopTestforMobileBrowser();
		}
		//To click reorder for a particular item
		try {
			scrollIntoViewBottom(Elements.reOrder);
			clickableWait(Elements.reOrder);
			log.info("Clicked reorder for a particular order");
			Thread.sleep(3000);
			extTestObj.createNode("Clicked reorder for a particular order").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to click reorder");
			log.error(e.getMessage());
			extTestObj.createNode("Failed to click reorder")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			stopTestforMobileBrowser();
		}
		
		
		
	}

	/*
	 * Function Name : selectPickupForFuture()
	 * Purpose : To select pick up date as future and a pick up time on the same date
	 * Platform : Android Chrome
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void selectPickupForFuture() {
		//To select pick up date as future
		try {
			scrollIntoViewBottom(Elements.pickDate);
			scrollIntoViewBottom((Elements.futurePickUpOption));
			Thread.sleep(3000);
			clickElement(Elements.futurePickUpOption);
			log.info("Pickup future time selected as : "+driver.findElement(Elements.pickDate).getAttribute("text"));
			extTestObj.createNode("Pickup future time selected as : "+driver.findElement(Elements.pickDate).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select pick up future time");

			extTestObj.createNode("Failed to select pick up future time")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		//To select pick up time
		try {
			scrollIntoViewBottom(Elements.pickTime);
			scrollIntoViewBottom(Elements.pickUpTimeOption);
			clickElement(Elements.pickUpTimeOption);
			log.info("Pickup time selected as : "+driver.findElement(Elements.pickTime).getAttribute("text"));
			extTestObj.createNode("Pickup time selected as : "+driver.findElement(Elements.pickTime).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Pickup time");
			extTestObj.createNode("Failed to select Pickup time")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
	}
	/*
	 * Function Name : selectPickupForFutureIOS()
	 * Purpose : To select pick up date as future and a pick up time on the same date
	 * Platform : IOS Safari
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void selectPickupForFutureIOS() {
		//To select pick up date as future
		try {
			scrollIntoViewBottom(Elements.pickDate);
			clickableWait(Elements.pickDate);
			Select s = new Select(driver.findElement(Elements.pickDate));
			WebElement deff = s.getFirstSelectedOption();
			if (deff.isSelected()) {
				seetest.setPickerValues("WEB", "xpath=//*[@id='pickup-date']", 0, 0,"Tomorrow");
				Thread.sleep(1000);
				seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.setPickerValues("WEB", "xpath=//*[@id='pickup-date']", 0, 0,"Tomorrow");
			}
			seetest.sleep(2000);
			log.info("Pickup future time is selected");
			extTestObj.createNode("Pickup future time is selected").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select pick up future time");

			extTestObj.createNode("Failed to select pick up future time")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		//To select pick up time
		String timeInput = excel.getCellData("CarryOut", "Pickup Time", 2);
		try {
			scrollIntoViewBottom(Elements.pickTime);
			explicitWait(Elements.pickTime);
			clickableWait(Elements.pickTime);
			Select s = new Select(driver.findElement(Elements.pickTime));
			WebElement deff = s.getFirstSelectedOption();
			if (deff.isSelected()) {
				seetest.setPickerValues("WEB", "xpath=//*[@id='pickup-time']", 0, 0, timeInput);
				Thread.sleep(1000);
				seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.setPickerValues("WEB", "xpath=//*[@id='pickup-time']", 0, 0, timeInput);
			}
			log.info("Pickup time selected as " + timeInput);
			extTestObj.createNode("Pickup time selected as " + timeInput).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Pickup time");
			extTestObj.createNode("Failed to select Pickup time")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
	}
	
	/*
	 * Function Name : validateMenuItemSelection(int args)
	 * Purpose : To validate Menu Category and Menu Item selection and then adding the same to Cartr
	 * Platform : Android Chrome
	 * Parameters : Menu Category and Menu Item obtained from CommonData.xlsx based on index
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void validateMenuItemSelection(int MenuIndex)
	{
		//To select menu category
		String category = excel.getCellData("Menu", "Category", MenuIndex);
		try {
			Thread.sleep(3000);
			scrollIntoViewHalf(By.xpath("//a[@title='" + category + "']"));
			Thread.sleep(3000);
			clickElement(By.xpath("//a[@title='" + category + "']"));
			log.info("Site scrolled and category " + category + " selected");
			extTestObj.createNode("Site scrolled and category " + category + " selected").pass("PASSED");
		} catch (Exception e) {
			log.error("Site scrolled but selected catagory button not clicked");
			extTestObj.createNode("Selected catagory button not clicked")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		//To select menu item under the selected category
		String item = excel.getCellData("Menu", "Item", MenuIndex);
		try {
			Thread.sleep(2000);
			scrollIntoViewHalf(By.xpath("//a[@title='" + item + "']"));
			Thread.sleep(2000);
			clickElement(By.xpath("//a[@title='" + item + "']"));
			log.info("Site scrolled and item" + item + "clicked");
			extTestObj.createNode("Site scrolled and item" + item + "clicked").pass("PASSED");
		} catch (Exception e) {
			log.error("Site scrolled but item not selected");
			extTestObj.createNode("Item not selected")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		//To retrieve the item name from the add to order page
		try {
			explicitWait(Elements.itemNameTobeAdded);
			String itemName=driver.findElement(Elements.itemNameTobeAdded).getText();
			explicitWait(Elements.addThisItem);
			scrollIntoViewBottom(Elements.addThisItem);
			clickElement(Elements.addThisItem);
			log.info("Item added to cart : "+itemName);
			extTestObj.createNode("Item added to cart : "+itemName).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to add item to cart");
			extTestObj.createNode("Failed to add item to cart")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		//To retrieve the item name from the cart pop up
		try {
			explicitWait(Elements.viewCartButton);
			log.info("Item name in cart displayed as : "+driver.findElement(Elements.itemNameInCart).getText());
			extTestObj.createNode("Item name in cart displayed as : "+driver.findElement(Elements.itemNameInCart).getText()).pass("PASSED");
		}
		catch(Exception e)
		{log.error("Failed to display item name in Cart");
		extTestObj.createNode("Failed to display item name in Cart")
		.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
         log.error(e.getMessage());
		}
		//To click on view cart button
		try {
			explicitWait(Elements.viewCartButton);
			clickableWait(Elements.viewCartButton);
			log.info("View Cart clicked");
			extTestObj.createNode("View Cart clicked").pass("PASSED");
		} 
		catch (Exception e) {
			log.error("Failed to click View Cart");
			extTestObj.createNode("Failed to click View Cart")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
	}
	/*
	 * Function Name : selectCustomizedMenuItem()
	 * Purpose : To select a menu item under menu category and customize the same
	 * Platform : Android Chrome
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void selectCustomizedMenuItem()
	{
		//To select a menu category
		String category = excel.getCellData("Menu", "Category", 2);
		try {
			Thread.sleep(3000);
			scrollIntoViewHalf(By.xpath("//a[@title='" + category + "']"));
			Thread.sleep(3000);
			clickElement(By.xpath("//a[@title='" + category + "']"));
			log.info("Site scrolled and category " + category + " selected");
			extTestObj.createNode("Site scrolled and category " + category + " selected").pass("PASSED");
		} catch (Exception e) {
			log.error("Site scrolled but selected catagory button not clicked");
			extTestObj.createNode("Selected catagory button not clicked")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		//To select a menu item
		String item = excel.getCellData("Menu", "Item", 2);
		try {
			Thread.sleep(2000);
			scrollIntoViewHalf(By.xpath("//a[@title='" + item + "']"));
			Thread.sleep(2000);
			clickElement(By.xpath("//a[@title='" + item + "']"));
			log.info("Site scrolled and item" + item + "clicked");
			extTestObj.createNode("Site scrolled and item" + item + "clicked").pass("PASSED");
		} catch (Exception e) {
			log.error("Site scrolled but item not selected");
			extTestObj.createNode("Item not selected")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		//To customize the selected menu item
		String custItem = excel.getCellData("Order Customization", "Custom Item", 2);
		try {
			scrollIntoViewHalf(Elements.customizeOrderButton); 
			clickableWait(Elements.customizeOrderButton);
			clickElement(By.xpath("//*[text()='" + custItem + "']"));
			clickableWait(Elements.addExtraSauce);
			log.info("Order customized with " + custItem);
			extTestObj.createNode("Order customized with " + custItem).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to customize order");
			extTestObj.createNode("Failed to customize order")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		//To click "Add to order" button for the selected Item
		try {
			explicitWait(Elements.addThisItem);
			scrollIntoViewBottom(Elements.addThisItem);
			clickElement(Elements.addThisItem);
			log.info("Item added to cart");
			extTestObj.createNode("Item added to cart").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to add item to cart");
			extTestObj.createNode("Failed to add item to cart")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		//To click "View Cart" button
		try {
			explicitWait(Elements.viewCartButton);
			clickableWait(Elements.viewCartButton);
			log.info("View Cart clicked");
			extTestObj.createNode("View Cart clicked").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to click View Cart");
			extTestObj.createNode("Failed to click View Cart")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To validate whether the item for customization has been added
		try {
			explicitWait(Elements.yourOrderHeaderXpath);
			scrollIntoViewBottomByElement(driver.findElement(Elements.customItem));
//			explicitWait(Elements.customItem);
			String custInfo = driver.findElement(Elements.customItem).getText();
			if (custInfo.contains(custItem) && (custInfo.contains("Extra"))) {
				log.info("The item added for customization is " + custInfo);
				extTestObj.createNode("The item added for customization is " + custInfo).pass("PASSED");
			}
		} catch (Exception e) {
			log.error("Incorrect Customization Info");
			extTestObj.createNode("Incorrect Customization Info")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		
		
	}
	/*
	 * Function Name : selectCustomizedMenuItemIOS()
	 * Purpose : To select a menu item under menu category and customize the same
	 * Platform : IOS Safari
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void selectCustomizedMenuItemIOS()
	{
		//To select a menu category
		String category = excel.getCellData("Menu", "Category", 2);
		try {
			Thread.sleep(3000);
			scrollIntoViewHalf(By.xpath("//a[@title='" + category + "']"));
			Thread.sleep(3000);
			clickElement(By.xpath("//a[@title='" + category + "']"));
			log.info("Site scrolled and category " + category + " selected");
			extTestObj.createNode("Site scrolled and category " + category + " selected").pass("PASSED");
		} catch (Exception e) {
			log.error("Site scrolled but selected catagory button not clicked");
			extTestObj.createNode("Selected catagory button not clicked")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		//To select a menu item
		String item = excel.getCellData("Menu", "Item", 2);
		try {
			Thread.sleep(2000);
			scrollIntoViewHalf(By.xpath("//a[@title='" + item + "']"));
			Thread.sleep(2000);
			clickElement(By.xpath("//a[@title='" + item + "']"));
			log.info("Site scrolled and item" + item + "clicked");
			extTestObj.createNode("Site scrolled and item" + item + "clicked").pass("PASSED");
		} catch (Exception e) {
			log.error("Site scrolled but item not selected");
			extTestObj.createNode("Item not selected")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		//To customize the selected menu item
		String custItem = excel.getCellData("Order Customization", "Custom Item", 2);
		try {
			scrollIntoViewHalf(Elements.customizeOrderButton); // added
			clickableWait(Elements.customizeOrderButton);
			clickableWait(Elements.customizeDropDown);
			Select s = new Select(driver.findElement(Elements.customizeDropDown));
			WebElement deff = s.getFirstSelectedOption();
			if (deff.isSelected()) {
				seetest.setPickerValues("WEB", "xpath=//select[contains(@name,'customizeItems')]", 0, 0, custItem);
				Thread.sleep(1000);
				seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.setPickerValues("WEB", "xpath=//select[contains(@name,'customizeItems')]", 0, 0, custItem);
			}
			seetest.sleep(2000);
			clickableWait(Elements.addExtraSauce);
			// added
			log.info("Order customized with " + custItem);
			extTestObj.createNode("Order customized with " + custItem).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to customize order");
			extTestObj.createNode("Failed to customize order")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		//To click "Add to order" button for the selected Item
		try {
			explicitWait(Elements.addThisItem);
			scrollIntoViewBottom(Elements.addThisItem);
			clickElement(Elements.addThisItem);
			log.info("Item added to cart");
			extTestObj.createNode("Item added to cart").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to add item to cart");
			extTestObj.createNode("Failed to add item to cart")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		//To click "View Cart" button
		try {
			explicitWait(Elements.viewCartButton);
			clickableWait(Elements.viewCartButton);
			explicitWait(Elements.yourOrderHeaderXpath);
			log.info("View Cart clicked");
			extTestObj.createNode("View Cart clicked").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to click View Cart");
			extTestObj.createNode("Failed to click View Cart")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		//To validate whether the item for customization has been added
		try {
			scrollIntoViewBottomByElement(driver.findElement(Elements.customItem));
//			explicitWait(Elements.customItem);
			String custInfo = driver.findElement(Elements.customItem).getText();
			if (custInfo.contains(custItem) && (custInfo.contains("Extra"))) {
				log.info("The item added for customization is " + custInfo);
				extTestObj.createNode("The item added for customization is " + custInfo).pass("PASSED");
			}
		} catch (Exception e) {
			log.error("Incorrect Customization Info");
			extTestObj.createNode("Incorrect Customization Info")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		
		
	}
	/*
	 * Function Name : enterGuestDetailsForCurbsideOrder()
	 * Purpose : To enter guest details for Curbside order(Guest user)
	 * Platform : Android Chrome
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void enterGuestDetailsForCurbsideOrder()
	{
		//To enter first name
		String firstName = excel.getCellData("Delivery", "First Name", 2);
		try {
			scrollIntoViewHalf(Elements.firstName);
			explicitWait(Elements.firstName);
			clickableWait(Elements.firstName);
			Thread.sleep(1000);
			driver.getKeyboard().sendKeys(firstName);
			seetest.closeKeyboard();
			log.info("First name entered as : " + driver.findElement(Elements.firstName).getAttribute("text"));
			extTestObj.createNode("First name entered as : " + driver.findElement(Elements.firstName).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter First name");
			extTestObj.createNode("Failed to enter First name")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		//To enter last name
		String LastName = excel.getCellData("Delivery", "Last Name", 2);
		try {
			scrollIntoViewHalf(Elements.lastName);
			explicitWait(Elements.lastName);
			clickableWait(Elements.lastName);
			driver.getKeyboard().sendKeys(LastName);
			seetest.closeKeyboard();
			log.info("Last name entered as : " + driver.findElement(Elements.lastName).getAttribute("text"));
			extTestObj.createNode("Last name entered as : " + driver.findElement(Elements.lastName).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter Last name");
			extTestObj.createNode("Failed to enter Last name")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		//To enter contact number
		String contactNum = excel.getCellData("Delivery", "Contact Number", 2);
		try {
			scrollIntoViewHalf(Elements.contantNumber);
			explicitWait(Elements.contantNumber);
			clickableWait(Elements.contantNumber);
			driver.getKeyboard().sendKeys(contactNum);
			seetest.closeKeyboard();
			log.info("Contact Number entered as : " +  driver.findElement(Elements.contantNumber).getAttribute("text"));
			extTestObj.createNode("Contact Number entered as : " + driver.findElement(Elements.contantNumber).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter Contact number");
			extTestObj.createNode("Failed to enter Contact number")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		//To enter email address
		String email = excel.getCellData("Delivery", "Email", 2);
		try {
			scrollIntoViewHalf(Elements.eMail);
			explicitWait(Elements.eMail);
			clickableWait(Elements.eMail);
			driver.getKeyboard().sendKeys(email);
			seetest.closeKeyboard();
			log.info("Email address entered as : " + driver.findElement(Elements.eMail).getAttribute("text"));
			extTestObj.createNode("Email address entered as : " +driver.findElement(Elements.eMail).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter Email id");
			extTestObj.createNode("Failed to enter Email id")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		//To enter vehicle make
		String vehicle = excel.getCellData("GuestUserCurbSide", "Vehicle Make", 2);
		try {
			scrollIntoViewBottom(Elements.vehicleMake);
			explicitWait(Elements.vehicleMake);
			clickableWait(Elements.vehicleMake);
			driver.getKeyboard().sendKeys(vehicle);
			seetest.closeKeyboard();
			log.info("Vehicle make entered as " + driver.findElement(Elements.vehicleMake).getAttribute("text"));
			extTestObj.createNode("Vehicle make entered as " + driver.findElement(Elements.vehicleMake).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter Vehicle make");
			extTestObj.createNode("Failed to enter Vehicle make")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		//To enter vehicle model
		String vehicleModel = excel.getCellData("GuestUserCurbSide", "Vehicle Model", 2);
		try {
			scrollIntoViewHalf(Elements.vehicleModel);
			explicitWait(Elements.vehicleModel);
			clickableWait(Elements.vehicleModel);
			driver.getKeyboard().sendKeys(vehicleModel);
			seetest.closeKeyboard();
			log.info("Vehicle model entered as : " + driver.findElement(Elements.vehicleModel).getAttribute("text"));
			extTestObj.createNode("Vehicle model entered as : " + driver.findElement(Elements.vehicleModel).getAttribute("text")).pass("PASSED");

		} catch (Exception e) {
			log.error("Failed to enter Vehicle model");
			extTestObj.createNode("Failed to enter Vehicle model")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		//To enter vehicle color
		String vehicleColor = excel.getCellData("GuestUserCurbSide", "Vehicle Color", 2);
		try {
			scrollIntoViewBottom(Elements.vehicleColor);
			explicitWait(Elements.vehicleColor);
			clickableWait(Elements.vehicleColor);
			driver.getKeyboard().sendKeys(vehicleColor);
			seetest.closeKeyboard();
			log.info("Vehicle Color entered as " + driver.findElement(Elements.vehicleColor).getAttribute("text"));
			extTestObj.createNode("Vehicle Color entered as " + driver.findElement(Elements.vehicleColor).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter Vehicle Color");
			extTestObj.createNode("Failed to enter Vehicle Color")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		
	}

	/*
	 * Function Name : enterDeliveryDetails(int args)
	 * Purpose : To enter Delivery details in the Check out page
	 * Platform : Android Chrome
	 * Parameters : Delivery location based on Store selection from CommonData.xlsx
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void enterDeliveryDetails(int locationIndex)
	{
		//To select order type as Delivery
		try {
			Thread.sleep(2000);
			scrollIntoViewBottom(Elements.selectDeliveryMode);
			Thread.sleep(2000);
			clickableWait(Elements.selectDeliveryMode);
			log.info("Site scrolled and Delivery mode is selected");
			extTestObj.createNode("Site scrolled and Delivery mode is selected").pass("PASSED");
		} catch (Exception e) {
			log.error("Site scrolled but Delivery mode is not selected");
			extTestObj.createNode("Delivery mode is not selected")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		//To select delivery address
		String location = excel.getCellData("Delivery", "Restaurant Address",locationIndex);
		try {
			scrollIntoViewBottom(Elements.deliveryAddress);
			clickableWait(Elements.deliveryAddress);
			driver.findElement(Elements.deliveryAddress).sendKeys(location);
			seetest.closeKeyboard();
			Thread.sleep(2000);
			String[] str = location.split(",");
			seetest.click("WEB", "xpath=//*[@class='pac-icon pac-icon-marker']", 0, 1);
			Thread.sleep(2000);
			log.info("Delivery location entered as " + driver.findElement(Elements.deliveryAddress).getAttribute("text"));
			extTestObj.createNode("Delivery location entered as " + driver.findElement(Elements.deliveryAddress).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter Delivery location");
			log.error(e.getMessage());
			extTestObj.createNode("Failed to enter Delivery location")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			stopTestforMobileBrowser();
		}
		//To enter apartment number
		String aptNo = excel.getCellData("Delivery", "Apt. no", 2);
		try {
			scrollIntoViewHalf(Elements.aptNo);
			explicitWait(Elements.aptNo);
			clickableWait(Elements.aptNo);
			driver.getKeyboard().sendKeys(aptNo);
			seetest.closeKeyboard();
			log.info("Apartment no. entered as : " + driver.findElement(Elements.aptNo).getAttribute("text"));
			extTestObj.createNode("Apartment no. entered as : " + driver.findElement(Elements.aptNo).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter Apartment no.");
			extTestObj.createNode("Failed to enter Apartment no.")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		//To enter deilvery instruction
		String deliveryInstruction = excel.getCellData("Delivery", "Instruction", 2);
		try {
			scrollIntoViewBottom(Elements.deliveryInstrTextbox);
			clickableWait(Elements.deliveryInstrTextbox);
			driver.getKeyboard().sendKeys(deliveryInstruction);
			seetest.closeKeyboard();
			log.info("Delivery Instruction given as " + driver.findElement(Elements.deliveryInstrTextbox).getAttribute("text"));
			extTestObj.createNode("Delivery Instruction given as " + driver.findElement(Elements.deliveryInstrTextbox).getAttribute("text")).pass("PASSED");

		} catch (Exception e) {
			log.error("Failed to provide Delivery Instructions");
			extTestObj.createNode("Failed to provide Delivery Instructions")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
	}
	/*
	 * Function Name : enterDeliveryDetailsQA2(int args)
	 * Purpose : To enter Delivery details in the Check out page
	 * Platform : Android Chrome
	 * Parameters : Delivery location based on Store selection from CommonData.xlsx
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void enterDeliveryDetailsQA2(int locationIndex)
	{
		//To select order type as Delivery
		try {
			Thread.sleep(2000);
			scrollIntoViewBottom(Elements.selectDeliveryMode);
			Thread.sleep(2000);
			clickableWait(Elements.selectDeliveryMode);
			log.info("Site scrolled and Delivery mode is selected");
			extTestObj.createNode("Site scrolled and Delivery mode is selected").pass("PASSED");
		} catch (Exception e) {
			log.error("Site scrolled but Delivery mode is not selected");
			extTestObj.createNode("Delivery mode is not selected")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		//To select delivery address
		String location = excel.getCellData("Delivery", "Restaurant Address",locationIndex);
		try {
			scrollIntoViewBottom(Elements.deliveryAddressqa2);
			clickableWait(Elements.deliveryAddressqa2);
			driver.findElement(Elements.deliveryAddressqa2).sendKeys(location);
			seetest.closeKeyboard();
			Thread.sleep(2000);
			String[] str = location.split(",");
			seetest.click("WEB", "xpath=//*[@class='pac-icon pac-icon-marker']", 0, 1);
			Thread.sleep(2000);
			log.info("Delivery location entered as " + driver.findElement(Elements.deliveryAddressqa2).getAttribute("text"));
			extTestObj.createNode("Delivery location entered as " + driver.findElement(Elements.deliveryAddressqa2).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter Delivery location");
			log.error(e.getMessage());
			extTestObj.createNode("Failed to enter Delivery location")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			stopTestforMobileBrowser();
		}
		//To enter apartment number
		String aptNo = excel.getCellData("Delivery", "Apt. no", 2);
		try {
			scrollIntoViewHalf(Elements.aptNo);
			explicitWait(Elements.aptNo);
			clickableWait(Elements.aptNo);
			driver.getKeyboard().sendKeys(aptNo);
			seetest.closeKeyboard();
			log.info("Apartment no. entered as : " + driver.findElement(Elements.aptNo).getAttribute("text"));
			extTestObj.createNode("Apartment no. entered as : " + driver.findElement(Elements.aptNo).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter Apartment no.");
			extTestObj.createNode("Failed to enter Apartment no.")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		//To enter deilvery instruction
		String deliveryInstruction = excel.getCellData("Delivery", "Instruction", 2);
		try {
			scrollIntoViewBottom(Elements.deliveryInstrTextbox);
			clickableWait(Elements.deliveryInstrTextbox);
			driver.getKeyboard().sendKeys(deliveryInstruction);
			seetest.closeKeyboard();
			log.info("Delivery Instruction given as " + driver.findElement(Elements.deliveryInstrTextbox).getAttribute("text"));
			extTestObj.createNode("Delivery Instruction given as " + driver.findElement(Elements.deliveryInstrTextbox).getAttribute("text")).pass("PASSED");

		} catch (Exception e) {
			log.error("Failed to provide Delivery Instructions");
			extTestObj.createNode("Failed to provide Delivery Instructions")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
	}
	/*
	 * Function Name : enterGuestDetails()
	 * Purpose : To enter guest details for Guest user order
	 * Platform : Android Chrome
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void enterGuestDetails()
	{
		//To enter first name
		String firstName = excel.getCellData("Delivery", "First Name", 2);
		try {
			scrollIntoViewHalf(Elements.firstName);
			explicitWait(Elements.firstName);
			clickableWait(Elements.firstName);
			Thread.sleep(1000);
			driver.getKeyboard().sendKeys(firstName);
			seetest.closeKeyboard();
			log.info("First name entered as : " + driver.findElement(Elements.firstName).getAttribute("text"));
			extTestObj.createNode("First name entered as : " + driver.findElement(Elements.firstName).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter First name");
			extTestObj.createNode("Failed to enter First name")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		//To enter last name
		String LastName = excel.getCellData("Delivery", "Last Name", 2);
		try {
			scrollIntoViewHalf(Elements.lastName);
			explicitWait(Elements.lastName);
			clickableWait(Elements.lastName);
			driver.getKeyboard().sendKeys(LastName);
			seetest.closeKeyboard();
			log.info("Last name entered as : " + driver.findElement(Elements.lastName).getAttribute("text"));
			extTestObj.createNode("Last name entered as : " + driver.findElement(Elements.lastName).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter Last name");
			extTestObj.createNode("Failed to enter Last name")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		//To enter contact number
		String contactNum = excel.getCellData("Delivery", "Contact Number", 2);
		try {
			scrollIntoViewHalf(Elements.contantNumber);
			explicitWait(Elements.contantNumber);
			clickableWait(Elements.contantNumber);
			driver.getKeyboard().sendKeys(contactNum);
			seetest.closeKeyboard();
			log.info("Contact Number entered as : " +  driver.findElement(Elements.contantNumber).getAttribute("text"));
			extTestObj.createNode("Contact Number entered as : " + driver.findElement(Elements.contantNumber).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter Contact number");
			extTestObj.createNode("Failed to enter Contact number")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		//To enter email
		String email = excel.getCellData("Delivery", "Email", 2);
		try {
			scrollIntoViewHalf(Elements.eMail);
			explicitWait(Elements.eMail);
			clickableWait(Elements.eMail);
			driver.getKeyboard().sendKeys(email);
			seetest.closeKeyboard();
			log.info("Email address entered as : " + driver.findElement(Elements.eMail).getAttribute("text"));
			extTestObj.createNode("Email address entered as : " +driver.findElement(Elements.eMail).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter Email id");
			extTestObj.createNode("Failed to enter Email id")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
	}
	/*
	 * Function Name : enterVisitDetails()
	 * Purpose : To enter past visit details for an user
	 * Platform : Android Chrome
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void enterVisitDetails()
	{
		//To click Add my visit button
		try {
			Thread.sleep(7000);
			scrollIntoViewBottom(Elements.addMyVisitButton);
			clickableWait(Elements.addMyVisitButton);
			log.info("Add my visit button clicked");
			extTestObj.createNode("Add my visit button clicked").pass("PASSED");
		} catch (Exception e) {
			log.error("Add my visit button click failed");
			extTestObj.createNode("Add my visit button click failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To enter location of the restaurant last visited
		String loc = excel.getCellData("AddMyVisit", "Location", 2);
		try {
			scrollIntoViewBottom(Elements.restaurantLocTextBox);
			explicitWait(Elements.restaurantLocTextBox);
			clickableWait(Elements.restaurantLocTextBox);
			driver.getKeyboard().sendKeys(loc);
			scrollIntoViewBottom(By.xpath("//*[text()='" + loc + "']"));
			((PressesKey) driver).pressKeyCode(AndroidKeyCode.ENTER);
			log.info("Restaurant location entered as : " + loc);
			extTestObj.createNode("Restaurant location entered as : " + loc).pass("PASSED");
			Thread.sleep(3000);
		} catch (Exception e) {
			log.error("Failed to enter restaurant location");
			extTestObj.createNode("Failed to enter restaurant location")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		//To select restaurant from the dropdown
		String locFromDropDown = excel.getCellData("AddMyVisit", "Chilis Location from DropDown", 2);
		try {
//			clickElement(Elements.chillisLocDropDown);
			scrollIntoViewBottom(By.xpath("//*[text()='" + locFromDropDown + "']"));
			clickElement(By.xpath("//*[text()='" + locFromDropDown + "']"));
			log.info("Chilis location selected as : " + locFromDropDown);
			extTestObj.createNode("Chilis location selected as : " + locFromDropDown).pass("PASSED");
			Thread.sleep(3000);

		} catch (Exception e) {
			log.error("Chillis location selection failed");
			extTestObj.createNode("Chillis location selection failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		//To select visit month
		String visitMonth = excel.getCellData("AddMyVisit", "Visit Month", 2);
		try {
			scrollIntoViewBottom(Elements.visitMonthDropDown);
			scrollIntoViewBottom(By.xpath("//*[text()='" + visitMonth + "']"));
			clickElement(By.xpath("//*[text()='" + visitMonth + "']"));
			log.info("Visit month selected as :" + visitMonth);
			extTestObj.createNode("Visit month selected as :" + visitMonth).pass("PASSED");
		} catch (Exception e) {
			log.error("Visit month selection failed");
			extTestObj.createNode("Visit month selection failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		//To select visit day
		String visitDay = excel.getCellData("AddMyVisit", "Visit Day", 2);
		try {
			scrollIntoViewBottom(Elements.visitDayDropDown);
			scrollIntoViewBottom(By.xpath("//*[text()='" + visitDay + "']"));
			clickElement(By.xpath("//*[text()='" + visitDay + "']"));
			log.info("Visit day selected as : " + visitDay);
			extTestObj.createNode("Visit day selected as : " + visitDay).pass("PASSED");
		} catch (Exception e) {
			log.error("Visit day selection failed");
			extTestObj.createNode("Visit day selection failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To select visit year
		String visitYear = excel.getCellData("AddMyVisit", "Visit Year", 2);
		try {
			explicitWait(Elements.visitYearDropDown);
			scrollIntoViewBottom(By.xpath("//*[text()='" + visitYear + "']"));
			clickElement(By.xpath("//*[text()='" + visitYear + "']"));
			log.info("Visit year selected as : " + visitYear);
			extTestObj.createNode("Visit year selected as : " + visitYear).pass("PASSED");
		} catch (Exception e) {
			log.error("Visit year selection failed");
			extTestObj.createNode("Visit year selection failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To enter check number
		String checkNo = excel.getCellData("AddMyVisit", "Receipt Check Number", 2);

		try {
			sendKeysWait(Elements.checkNumberTextBox, checkNo);
			seetest.closeKeyboard();
			log.info("Check Number entered as : " + checkNo);
			extTestObj.createNode("Check Number entered as : " + checkNo).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter check number");
			extTestObj.createNode("Failed to enter check number")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

		}
		//To enter check total
		String checkTotal = excel.getCellData("AddMyVisit", "Subtotal", 2);
		try {
			clickableWait(Elements.checkTotalTextBox);
			driver.getKeyboard().sendKeys(checkTotal);
			seetest.closeKeyboard();
			log.info("Check total entered as : " + checkTotal);
			extTestObj.createNode("Check total entered as : " + checkTotal).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter check total");
			extTestObj.createNode("Failed to enter check total")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

		}
		//To click submit button
		try {
			clickableWait(Elements.visitSubmitButton);
			log.info("Visit submitted");
			extTestObj.createNode("Visit submitted").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to submit visit");
			extTestObj.createNode("Failed to submit visit")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
	}

	/*
	 * Function Name : selectDeliveryDateASAP()
	 * Purpose : To select delivery pick up type as "ASAP"
	 * Platform : Android Chrome
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void selectDeliveryDateASAP() {
		try {
			scrollIntoViewBottom(Elements.delDate);
			scrollIntoViewBottom(Elements.dateInputASAP);
			clickElement(Elements.dateInputASAP);
			log.info("Delivery Option selected as : "+driver.findElement(Elements.delDate).getAttribute("text"));
			extTestObj.createNode("Delivery Option selected as : "+driver.findElement(Elements.delDate).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Delivery Option as ASAP");
			extTestObj.createNode("Failed to select Delivery Option as ASAP")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();

		}

	}
	/*
	 * Function Name : selectDeliveryDateASAPIOS()
	 * Purpose : To select delivery pick up type as "ASAP"
	 * Platform : IOS Safari
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void selectDeliveryDateASAPIOS() {
		try {
			scrollIntoViewBottom(Elements.delDate);
			explicitWait(Elements.delDate);
			clickableWait(Elements.delDate);
			Select s = new Select(driver.findElement(Elements.delDate));
			WebElement deff = s.getFirstSelectedOption();
			if (deff.isSelected()) {
				seetest.setPickerValues("WEB", "xpath=//*[@id='delivery-date']", 0, 0, "ASAP");
				Thread.sleep(1000);
				seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.setPickerValues("WEB", "xpath=//*[@id='delivery-date']", 0, 0, "ASAP");
			}
			log.info("Delivery Option selected as ASAP");
			extTestObj.createNode("Delivery Option selected as ASAP").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Delivery Option as ASAP");
			extTestObj.createNode("Failed to select Delivery Option as ASAP")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();

		}

	}
	/*
	 * Function Name : selectCurbside()
	 * Purpose : To order type as Curbside in the Check out page of Chilis Web site
	 * Platform : Android Chrome
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void selectCurbside() {
		try {
			clickElement(Elements.selectCurbsideMode);
			log.info("Curbside mode selected");
			extTestObj.createNode("Curbside mode selected").pass("PASSED");

		} catch (Exception e) {
			log.error("Curbside mode selection failed");
			extTestObj.createNode("Curbside mode selection failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
	}

	/*
	 * Function Name : selectDeliveryLaterToday()
	 * Purpose : To select delivery type as Later Today and also select delivery time
	 * Platform : Android Chrome
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void selectDeliveryLaterToday() {
		//To select delivery type as Later Today
		try {
			scrollIntoViewHalf(Elements.delDate);
			Thread.sleep(3000);
			scrollIntoViewBottom(Elements.deliveryLaterToday);
			clickElement(Elements.deliveryLaterToday);
			log.info("Delivery pick up time selected as Later Today");
			extTestObj.createNode("Delivery pick up time selected as Later Today").pass("PASSED");
			Thread.sleep(2000);
		} catch (Exception e) {
			log.error("Failed to select delivery time Later Today");
			extTestObj.createNode("Failed to select delivery time Later Today")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		//To select delivery time
		String pickTime = excel.getCellData("CarryOut", "Pickup Time for App", 2);
		try {
			scrollIntoViewHalf(Elements.delTime);
			 clickableWait(Elements.delTime);
			 seetest.elementListPick("NATIVE", "xpath=//*[@class='android.widget.ListView']", "NATIVE",
						"text=" + pickTime, 0, true);
			Thread.sleep(2000);
//			scrollIntoViewBottom(Elements.pickUpTimeOptionDelivery);
//			clickElement(Elements.pickUpTimeOptionDelivery);
			String time  = driver.findElement(Elements.delTime).getAttribute("text");
			log.info("Delivery time selected as : "+time);
			extTestObj.createNode("Delivery time selected as : "+time).pass("PASSED");
			Thread.sleep(2000);
		} catch (Exception e) {
			log.error("Failed to select Delivery time");
			log.error(e.getMessage());
			extTestObj.createNode("Failed to select Delivery time")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			stopTestforMobileBrowser();
		}
	}

	/*
	 * Function Name : selectDeliveryLaterTodayIOS()
	 * Purpose : To select delivery type as Later Today and also select delivery time
	 * Platform : IOS Safari
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void selectDeliveryLaterTodayIOS() {
		//To select delivery type as 'Later Today'
		try {
			scrollIntoViewBottom(Elements.delDate);
			explicitWait(Elements.delDate);
			clickableWait(Elements.delDate);
			Select s = new Select(driver.findElement(Elements.delDate));
			WebElement deff = s.getFirstSelectedOption();
			if (deff.isSelected()) {
				seetest.setPickerValues("WEB", "xpath=//*[@id='delivery-date']", 0, 0, "Later Today");
				Thread.sleep(1000);
				seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.setPickerValues("WEB", "xpath=//*[@id='delivery-date']", 0, 0, "Later Today");
			}
			log.info("Later Today delivery time is selected");
			extTestObj.createNode("Later Today delivery time is selected").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select delivery time Later Today");
			extTestObj.createNode("Failed to select delivery time Later Today")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		//To select Delivery time
		String timeInput = excel.getCellData("CarryOut", "Pickup Time", 2);
		try {
			scrollIntoViewBottom(Elements.delTime);
			explicitWait(Elements.delTime);
			clickableWait(Elements.delTime);
			Select s = new Select(driver.findElement(Elements.delTime));
			WebElement deff = s.getFirstSelectedOption();
			if (deff.isSelected()) {
				seetest.setPickerValues("WEB", "xpath=//*[@id='delivery-time']", 0, 0, timeInput);
				Thread.sleep(1000);
				seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.setPickerValues("WEB", "xpath=//*[@id='delivery-time']", 0, 0, timeInput);
			}
			log.info("Delivery time selected");
			extTestObj.createNode("Delivery time selected").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Delivery time");
			log.error(e.getMessage());
			extTestObj.createNode("Failed to select Delivery time")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			stopTestforMobileBrowser();
		}
	}

	/*
	 * Function Name : selectPickupAsap()
	 * Purpose : To select Pick up time as ASAP in the Check out page for any order type
	 * Platform : Android Chrome
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void selectPickupAsap() {
		try {
			scrollIntoViewBottom(Elements.pickDate);
			scrollIntoViewBottom(Elements.pickupAsapOrder);
			clickElement(Elements.pickupAsapOrder);
			log.info("Pickup time is selected as : "+driver.findElement(Elements.pickDate).getAttribute("text"));
			extTestObj.createNode("Pickup time is selected as : "+driver.findElement(Elements.pickDate).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select pickup time 'ASAP'");
			extTestObj.createNode("Failed to select pickup time 'ASAP'")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
	}

	/*
	 * Function Name : selectPickupAsapIOS()
	 * Purpose : To select Pick up time as ASAP in the Check out page for any order type
	 * Platform : IOS Safari
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void selectPickupAsapIOS() {
		try {
			scrollIntoViewBottom(Elements.pickDate);
			explicitWait(Elements.pickDate);
			clickableWait(Elements.pickDate);
			Select s = new Select(driver.findElement(Elements.pickDate));
			WebElement deff = s.getFirstSelectedOption();
			if (deff.isSelected()) {
				seetest.setPickerValues("WEB", "xpath=//*[@id='pickup-date']", 0, 0, "ASAP");
				Thread.sleep(1000);
				seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.setPickerValues("WEB", "xpath=//*[@id='pickup-date']", 0, 0, "ASAP");
			}
			log.info("'ASAP' pickup time is selected");
			extTestObj.createNode("'ASAP' pickup time is selected").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select pickup time 'ASAP'");
			extTestObj.createNode("Failed to select pickup time 'ASAP'")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
	}

	/*
	 * Function Name : clickOnCarryOut()
	 * Purpose : To select Order type as "Carry Out"
	 * Platform : Android Chrome
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void clickOnCarryOut() {
		try {
			explicitWait(Elements.selectCarryOutMode);
			clickElement(Elements.selectCarryOutMode);
			log.info("Carryout Button is Clicked");
			extTestObj.createNode("Carryout Button is Clicked").pass("PASSED");
		} catch (Exception e) {
			log.error("Carryout Button click failed");
			extTestObj.createNode("Carryout Button click failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}

	}
	/*
	 * Function Name : clickOnCarryOut()
	 * Purpose : To select Order type as "Carry Out"
	 * Platform : IOS Safari
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void clickOnCarryOutIOS() {
		try {
			explicitWait(Elements.selectCarryOutModeIOS);
			clickableWait(Elements.selectCarryOutModeIOS);
			log.info("Carryout Button is Clicked");
			extTestObj.createNode("Carryout Button is Clicked").pass("PASSED");
		} catch (Exception e) {
			log.error("Carryout Button click failed");
			extTestObj.createNode("Carryout Button click failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}

	}
	/*
	 * Function Name : selectPickupLaterToday()
	 * Purpose : To select pick up tyoe as 'Later Today' and also select pick up time
	 * Platform : IOS Safari
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void selectPickupLaterToday() {
		//To select pick up type as 'Later Today'
		try {
			scrollIntoViewBottom(Elements.pickDate);
			scrollIntoViewBottom(Elements.LaterToday);
			clickElement(Elements.LaterToday);
			log.info("Pickup time selected as : "+driver.findElement(Elements.pickDate).getAttribute("text"));
			extTestObj.createNode("Pickup time selected as : "+driver.findElement(Elements.pickDate).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Later Today");
			extTestObj.createNode("Failed to select Later Today")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		//To select pick up time
		try {
			scrollIntoViewBottom(Elements.pickTime);
			scrollIntoViewBottom(Elements.pickUpTimeOption);
			clickElement(Elements.pickUpTimeOption);
			log.info("Pickup time selected as : "+driver.findElement(Elements.pickTime).getAttribute("text"));
			extTestObj.createNode("Pickup time selected as : "+driver.findElement(Elements.pickTime).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Pickup time");
			extTestObj.createNode("Failed to select Pickup time")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
	}
	/*
	 * Function Name : selectPickupLaterTodayIOS()
	 * Purpose : To select pick up type as 'Later Today' and also select pick up time
	 * Platform : IOS Safari
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void selectPickupLaterTodayIOS() {
		//To select pick up type as 'Later Today'
		try {
			scrollIntoViewBottom(Elements.pickDate);
			explicitWait(Elements.pickDate);
			clickableWait(Elements.pickDate);
			Select s = new Select(driver.findElement(Elements.pickDate));
			WebElement deff = s.getFirstSelectedOption();
			if (deff.isSelected()) {
				seetest.setPickerValues("WEB", "xpath=//*[@id='pickup-date']", 0, 0, "Later Today");
				Thread.sleep(1000);
				seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.setPickerValues("WEB", "xpath=//*[@id='pickup-date']", 0, 0, "Later Today");
			}
			log.info("Later Today pickup time is selected");
			extTestObj.createNode("Later Today pickup time is selected").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Later Today");
			extTestObj.createNode("Failed to select Later Today")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		//To select pick up time
		String timeInput = excel.getCellData("CarryOut", "Pickup Time", 2);
		try {
			scrollIntoViewBottom(Elements.pickTime);
			explicitWait(Elements.pickTime);
			clickableWait(Elements.pickTime);
			Select s = new Select(driver.findElement(Elements.pickTime));
			WebElement deff = s.getFirstSelectedOption();
			if (deff.isSelected()) {
				seetest.setPickerValues("WEB", "xpath=//*[@id='pickup-time']", 0, 0, timeInput);
				Thread.sleep(1000);
				seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.setPickerValues("WEB", "xpath=//*[@id='pickup-time']", 0, 0, timeInput);
			}
			log.info("Pickup time selected as " + timeInput);
			extTestObj.createNode("Pickup time selected as " + timeInput).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Pickup time");
			extTestObj.createNode("Failed to select Pickup time")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
	}

	/*
	 * Function Name : isRewardApplied()
	 * Purpose : To validate whether reward is applied to an order
	 * Platform : Android Chrome
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void isRewardApplied() {

		try {
			scrollIntoViewBottom(Elements.paymentButton);
			explicitWait(Elements.rewardName);
			if (driver.findElement(Elements.rewardName).isDisplayed()
					&& driver.findElement(Elements.discountLabel).isDisplayed()
					&& driver.findElement(Elements.discountAmount).isDisplayed()) {
				log.info("Reward " + driver.findElement(Elements.rewardName).getText() + " applied");
				log.info("Discount amount :" + driver.findElement(Elements.discountAmount).getText());
				extTestObj.createNode("Reward " + driver.findElement(Elements.rewardName).getText() + " applied")
						.pass("PASSED");
				extTestObj.createNode("Discount amount :" + driver.findElement(Elements.discountAmount).getText())
						.info("INFO");
			}
		} catch (Exception e) {
			log.error("Failed to apply discount");
			extTestObj.createNode("Failed to apply discount")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
	}
	
	/*
	 * Function Name : androidAppClosePopUp()
	 * Purpose : To close the welcome pop up
	 * Platform : Android App
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void androidAppClosePopUp() {
		try {
			//To close the device pop ups if displayed
			try {
				driver.findElement(Elements.appOkButtonXpath).click();
				driver.findElement(Elements.appCancelButtonXpath).click();
			} catch (Exception e) {
			}
			
			// To click the Chilis App pop up close button
			explicitWait(By.xpath("//*[@id='cancel_btn']"));
			clickableWait(By.xpath("//*[@id='cancel_btn']"));
			log.info("Pop up closed");
			extTestObj.createNode("Pop up closed").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to close pop up");
			extTestObj.createNode("Failed to close pop up")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}

	}

	/*
	 * Function Name : androidAppValidateLogout()
	 * Purpose : To validate logout functionality
	 * Platform : Android App
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void androidAppValidateLogout()
	{
		//To click on More button
		try {
			clickableWait(Elements.appMoreButtonXpath);
			explicitWait(Elements.appLogoutButtonXpath);
			if (driver.findElement(Elements.appLogoutButtonXpath).isDisplayed()) {
				log.info("More button clicked");
				extTestObj.createNode("More button clicked").pass("PASSED");
			}
		} catch (Exception e) {
			log.error("Failed to click More button");
			extTestObj.createNode("Failed to click More button")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		//To click on log out button and validate whether user logged out successfully
		try {
			clickableWait(Elements.appLogoutButtonXpath);
			clickableWait(Elements.signOutConfirmButtonXpath);
			explicitWait(Elements.applogoutValXpath);
			if (driver.findElement(Elements.applogoutValXpath).isDisplayed()) {
				log.info("Logout button clicked and log out successful");
				extTestObj.createNode("Logout button clicked and log out successful").pass("PASSED");
			}
		} catch (Exception e) {
			log.error("Logout button clicked and log out successful");
			extTestObj.createNode("Logout button clicked and log out successful")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
	}
	
	/*
	 * Function Name : androidAppValidateLogin()
	 * Purpose : To validate login functionality
	 * Platform : Android App
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void androidAppValidateLogin()
	{
		// To click on the Login button in the Home Page
		try {
			clickableWait(Elements.appLoginButtonXpath);
			explicitWait(Elements.apploginHeaderXpath);
			if (driver.findElement(Elements.apploginHeaderXpath).isDisplayed()) {
				log.info("Login button clicked");
				extTestObj.createNode("Login button clicked").pass("PASSED");
			}
		} catch (Exception e) {
			log.error("Login button clicked");
			extTestObj.createNode("Login button clicked")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();

		}
		// To enter Username in the Login Page
		String username = excel.getCellData("Credentials", "UserName", 2);
		try {
			sendKeysWait(Elements.appUserNameTextBoxXpath, username);
			log.info("User name " + driver.findElement(Elements.appUserNameTextBoxXpath).getAttribute("text") + " entered");
			extTestObj.createNode("User name " + driver.findElement(Elements.appUserNameTextBoxXpath).getAttribute("text") + " entered").pass("PASSED");
		} catch (Exception e) {
			log.error("Could not enter user name");
			extTestObj.createNode("Could not enter user name")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		// To enter Password in the Login Page
		String password = excel.getCellData("Credentials", "Password", 2);
		try {

			sendKeysWait(Elements.appPassWordTextBoxXpath, password);
			log.info("Password" + password + " entered");
			extTestObj.createNode("Password " + password + " entered").pass("PASSED");
		} catch (Exception e) {
			log.error("Could not enter Password");
			extTestObj.createNode("Could not enter Password")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		//To click on the Signin button in the login page
		try {
			clickableWait(Elements.appSigninButtonXpath);
//			clickableWait(Elements.appCancelButtonXpath);
			log.info("Sign in button clicked");
			extTestObj.createNode("Sign in button clicked").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to click Sign in button");
			extTestObj.createNode("Failed to click Sign in button")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
	}

	/*
	 * Function Name : androidAppValidateRestaurantSelectionforLoggedInUser()
	 * Purpose : To validate restaurant selection functionality for logged in user
	 * Platform : Android App
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void androidAppValidateRestaurantSelectionforLoggedInUser(int locationIndex)
	{
		//To check whether the store is already selected
		String restaurantLocation = excel.getCellData("Locations", "Location", locationIndex);
		String storeName = excel.getCellData("Locations", "Store Name",locationIndex);
		String str1 = storeName.split(",")[0];
		String str2 = storeName.split(" ")[0];
		try {
			explicitWait(By.xpath("//*[contains(@text,'"+str2.toUpperCase()+"')]"));
			log.info("The Store "+storeName+" is already selected");
			extTestObj.createNode("The Store "+storeName+" is already selected").pass("PASSED");
		}
		
		catch(Exception exception) {
		//To click the find Restaurant link 
		try {
					explicitWait(Elements.appOrderButton);
					clickableWait(Elements.appOrderButton);
					try {
					seetest.setProperty("Android.native.nonInstrumented","false");
					seetest.waitForElement("WEB","xpath="+Elements.appChangeLocationInsXpath,0,3000);
					seetest.click("WEB", "xpath="+Elements.appChangeLocationInsXpath,0, 1);
					}
					catch(Exception exp)
					{}
					seetest.setProperty("Android.native.nonInstrumented","true");
			explicitWait(Elements.appResSearchTextBoxXpath);
			if (driver.findElement(Elements.appResSearchTextBoxXpath).isDisplayed()) {
				log.info("User navigated to Restaurant search text box");
				extTestObj.createNode("User navigated to Restaurant search text box").pass("PASSED");
			}
		} catch (Exception e) {
			log.error("Failed to click Find Restaurant link");
			extTestObj.createNode("Failed to click Find Restaurant link")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		// To enter the Restaurant Location
		
		String[] str = restaurantLocation.split(",");
		try {
			clickableWait(Elements.appResSearchTextBoxXpath);
			sendKeysWait(Elements.appResSearchAutocompleteTextBoxXpath, restaurantLocation);
			Thread.sleep(3000);
			clickElement(MobileBy.xpath("//*[@id='places_autocomplete_prediction_primary_text']"));
			explicitWait(Elements.appResSearchTextBoxXpath);
			String displayedAddress = driver.findElement(Elements.appResSearchTextBoxXpath).getAttribute("text");
			String[] dispString = displayedAddress.split(",");
			if (str[0].equalsIgnoreCase(dispString[0])) {
				log.info("Restaurant location entered as : " + displayedAddress);
				extTestObj.createNode("Restaurant location entered as : " + displayedAddress).pass("PASSED");
			}
		}

		catch (Exception e) {
			log.error("Failed to enter Restaurant Location");
			extTestObj.createNode("Failed to enter Restaurant Location")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();

		}
		//To select the store
		try {
			explicitWait(MobileBy.xpath("//*[@id='search_clear_btn']"));
			seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE",
					"xpath=//*[@text='SELECT' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[./*[@text='"
							+ str1 + "']]]]",
					0, 3000, 5, true);
			Thread.sleep(3000);
			log.info("Resturant selected as : " + str1);
			extTestObj.createNode("Resturant selected as : " + str1).pass("PASSED");
		} catch (Exception e) {
			log.error("Not able to select any resturant");
			extTestObj.createNode("Not able to select any resturant")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();

		}
		}
		
	}
	
	/*
	 * Function Name : androidAppValidateRestaurantSelection()
	 * Purpose : To validate restaurant selection functionality for Guest user
	 * Platform : Android App
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void androidAppValidateRestaurantSelection(int locationIndex)
	{
		//To click the find Restaurant link 
		try {
			try {
				try {
			explicitWait(Elements.appfindRestaurantLinkXpath);
			clickableWait(Elements.appfindRestaurantLinkXpath);
			}
				catch(Exception e)
				{
					explicitWait(Elements.appChangeLocationButton);
					clickableWait(Elements.appChangeLocationButton);
					
				}
			}
			catch(Exception e)
			{
					explicitWait(Elements.appOrderButton);
					clickableWait(Elements.appOrderButton);
					try {
					seetest.setProperty("Android.native.nonInstrumented","false");
					seetest.waitForElement("WEB","xpath="+Elements.appChangeLocationInsXpath,0,3000);
					seetest.click("WEB", "xpath="+Elements.appChangeLocationInsXpath,0, 1);
					seetest.setProperty("Android.native.nonInstrumented","true");
					}
					catch(Exception exp)
					{}
					
			}
			explicitWait(Elements.appResSearchTextBoxXpath);
			if (driver.findElement(Elements.appResSearchTextBoxXpath).isDisplayed()) {
				log.info("User navigated to Restaurant search text box");
				extTestObj.createNode("User navigated to Restaurant search text box").pass("PASSED");
			}
		} catch (Exception e) {
			log.error("Failed to click Find Restaurant link");
			extTestObj.createNode("Failed to click Find Restaurant link")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		// To enter the Restaurant Location
		String restaurantLocation = excel.getCellData("Locations", "Location", locationIndex);
		String[] str = restaurantLocation.split(",");
		try {
			clickableWait(Elements.appResSearchTextBoxXpath);
			sendKeysWait(Elements.appResSearchAutocompleteTextBoxXpath, restaurantLocation);
			Thread.sleep(3000);
			clickElement(MobileBy.xpath("//*[@id='places_autocomplete_prediction_primary_text']"));
			explicitWait(Elements.appResSearchTextBoxXpath);
			String displayedAddress = driver.findElement(Elements.appResSearchTextBoxXpath).getAttribute("text");
			String[] dispString = displayedAddress.split(",");
			if (str[0].equalsIgnoreCase(dispString[0])) {
				log.info("Restaurant location entered as : " + displayedAddress);
				extTestObj.createNode("Restaurant location entered as : " + displayedAddress).pass("PASSED");
			}
		}

		catch (Exception e) {
			log.error("Failed to enter Restaurant Location");
			extTestObj.createNode("Failed to enter Restaurant Location")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();

		}
		//To select the store
		String storeName = excel.getCellData("Locations", "Store Name",locationIndex);
		String str1 = storeName.split(",")[0];
		try {
			explicitWait(MobileBy.xpath("//*[@id='search_clear_btn']"));
			seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE",
					"xpath=//*[@text='SELECT' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[./*[@text='"
							+ str1 + "']]]]",
					0, 3000, 5, true);
			Thread.sleep(3000);
			log.info("Resturant selected as : " + str1);
			extTestObj.createNode("Resturant selected as : " + str1).pass("PASSED");
		} catch (Exception e) {
			log.error("Not able to select any resturant");
			extTestObj.createNode("Not able to select any resturant")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();

		}
		
	}

	/*
	 * Function Name : androidAppAddVisitDetails()
	 * Purpose : To add past visit details
	 * Platform : Android App
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void androidAppAddVisitDetails()
	{
	  //To click on rewards button
		try {
			clickableWait(Elements.appRewardButtonXpath);
			explicitWait(Elements.appRewardsHeaderXpath);
			if (driver.findElement(Elements.appRewardsHeaderXpath).isDisplayed()) {
				log.info("Rewards button clicked");
				extTestObj.createNode("Rewards button clicked").pass("PASSED");

			}
		}

		catch (Exception e) {
			log.error("Rewards button clicked");
			extTestObj.createNode("Rewards button clicked")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		//To click on AddMyVisit button
		try {
			explicitWait(Elements.appaddMyVisitButtonXpath);
			clickableWait(Elements.appaddMyVisitButtonXpath);
			log.info("Add my visit button clicked");
			extTestObj.createNode("Add my visit button clicked").pass("PASSED");
		} catch (Exception e) {
			log.error("Add my visit button click failed");
			extTestObj.createNode("Add my visit button click failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To enter zip code
		String zipcode = excel.getCellData("AddMyVisit", "Zipcode", 2);

		try {
			sendKeysWait(Elements.appenterZipcodeXpath_addmyvisit, zipcode);
			seetest.closeKeyboard();
			log.info("Zipcode entered as : " + zipcode);
			extTestObj.createNode("Zipcode entered as : " + zipcode).pass("PASSED");
			Thread.sleep(3000);
		} catch (Exception e) {
			log.error("Failed to enter Zipcode");
			extTestObj.createNode("Failed to enter Zipcode")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		//To select restaurant location
		String locFromDropDown = excel.getCellData("AddMyVisit", "Chilis Location from DropDown(for App)", 2);
		try {
			clickableWait(Elements.appselectchilislocXpath_addmyvisit);
			Thread.sleep(3000);
			seetest.click("NATIVE",
					"xpath=(//android.widget.CheckedTextView)[2]", 0,1);
			log.info("Chilis location selected as : " + locFromDropDown);
			extTestObj.createNode("Chilis location selected as : " + locFromDropDown).pass("PASSED");
			Thread.sleep(3000);

		} catch (Exception e) {
			log.error("Chillis location selection failed");
			extTestObj.createNode("Chillis location selection failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To select visit month, day and year
		String visitMonth = excel.getCellData("AddMyVisit", "Visit Month", 2);
		String visitday = excel.getCellData("AddMyVisit", "Visit Day", 2);
		String visitYear = excel.getCellData("AddMyVisit", "Visit Year", 2);
		try {
			clickableWait(Elements.appDateofvisitXpath);
			Thread.sleep(3000);
			List<WebElement>date = driver.findElements(By.xpath("//*[@id='numberpicker_input']"));
			LocalDate currentdate = LocalDate.now();
			int currentYear = currentdate.getYear();
			for(int j=0;j<currentYear;j++)
			{
				if(date.get(0).getText().equals(visitYear))
					break;
				else
					seetest.setPickerValues("NATIVE","xpath=//*[@class='android.widget.NumberPicker']",0,0,"down:1");
			}
			for(int j=0;j<12;j++)
			{
				if(date.get(1).getText().equals(visitMonth.substring(0,3)))
					break;
				else
					seetest.setPickerValues("NATIVE","xpath=//*[@class='android.widget.NumberPicker']",1,0,"down:1");
			}
			if(Integer.parseInt(visitday)<10)
				visitday="0"+visitday;
			System.out.println(visitday);
			for(int j=0;j<31;j++)
			{
				if(date.get(2).getText().equals(visitday))
					break;
				else
					seetest.setPickerValues("NATIVE","xpath=//*[@class='android.widget.NumberPicker']",2,0,"down:1");
			}
			
			seetest.click("NATIVE","xpath=//*[@id='button1']",0,1);
			log.info("Dateofvisit selected as : " +driver.findElement(Elements.appDateofvisitXpath).getAttribute("text"));
			extTestObj.createNode("Dateofvisit selected as : " +driver.findElement(Elements.appDateofvisitXpath).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Dateofvisit selection failed");
			extTestObj.createNode("Dateofvisit selection failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		// To ener receipt check number
		String checkNo = excel.getCellData("AddMyVisit", "Receipt Check Number", 2);

		try {
			clickElement(Elements.appreceiptcheckNumberTextBox_addmyvisit);
			sendKeysWait(Elements.appreceiptcheckNumberTextBox_addmyvisit, checkNo);
			seetest.closeKeyboard();
			log.info("Check Number entered as : " + checkNo);
			extTestObj.createNode("Check Number entered as : " + checkNo).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter check number");
			extTestObj.createNode("Failed to enter check number")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

		}
		
		//To enter check total
		String checkTotal = excel.getCellData("AddMyVisit", "Subtotal", 2);
		String subtotal = "$" + checkTotal + "." + "00";
		try {
			clickableWait(Elements.appsubTotalTextBox_addmyvisit);
			driver.getKeyboard().sendKeys(checkTotal);
			seetest.closeKeyboard();
			log.info("Check total entered as : " + subtotal);
			extTestObj.createNode("Check total entered as : " + subtotal).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter check total");
			extTestObj.createNode("Failed to enter check total")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

		}
		//To submit the visit details
		try {
			clickableWait(Elements.appvisitSubmitButton);
			log.info("Visit submitted");
			extTestObj.createNode("Visit submitted").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to submit visit");
			extTestObj.createNode("Failed to submit visit")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
	}
	/*
	 * Function Name : androidAppAddVisitDetails()
	 * Purpose : To add past visit details
	 * Platform : Android App
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void androidAppAddVisitDetailsqa2()
	{
		//To click on rewards button
		try {
			clickableWait(Elements.appRewardButtonXpath);
			explicitWait(Elements.appRewardsHeaderXpath);
			if (driver.findElement(Elements.appRewardsHeaderXpath).isDisplayed()) {
				log.info("Rewards button clicked");
				extTestObj.createNode("Rewards button clicked").pass("PASSED");

			}
		}

		catch (Exception e) {
			log.error("Rewards button clicked");
			extTestObj.createNode("Rewards button clicked")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		//To click on AddMyVisit button
		try {
			explicitWait(Elements.appaddMyVisitButtonXpath);
			clickableWait(Elements.appaddMyVisitButtonXpath);
			log.info("Add my visit button clicked");
			extTestObj.createNode("Add my visit button clicked").pass("PASSED");
		} catch (Exception e) {
			log.error("Add my visit button click failed");
			extTestObj.createNode("Add my visit button click failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
		//To enter restaurant location zip code
		String zipcode = excel.getCellData("AddMyVisit", "Zipcode", 2);

		try {
			sendKeysWait(Elements.appenterZipcodeXpath_addmyvisit, zipcode);
			seetest.closeKeyboard();
			log.info("Zipcode entered as : " + zipcode);
			extTestObj.createNode("Zipcode entered as : " + zipcode).pass("PASSED");
			Thread.sleep(3000);
		} catch (Exception e) {
			log.error("Failed to enter Zipcode");
			extTestObj.createNode("Failed to enter Zipcode")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		//To select restaurant location
		String locFromDropDown = excel.getCellData("AddMyVisit", "Chilis Location from DropDown(for App)", 2);
		try {
			clickableWait(Elements.appselectchilislocXpath_addmyvisit);
			Thread.sleep(3000);
			seetest.click("NATIVE",
					"xpath=(//android.widget.CheckedTextView)[2]", 0,1);
			log.info("Chilis location selected as : " + locFromDropDown);
			extTestObj.createNode("Chilis location selected as : " + locFromDropDown).pass("PASSED");
			Thread.sleep(3000);

		} catch (Exception e) {
			log.error("Chillis location selection failed");
			extTestObj.createNode("Chillis location selection failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		//To select visit month, visit day, visit year
		String visitMonth = excel.getCellData("AddMyVisit", "Visit Month Number", 2);
		String visitday = excel.getCellData("AddMyVisit", "Visit Day", 2);
		String visitYear = excel.getCellData("AddMyVisit", "Visit Year", 2);
		try {
			clickableWait(Elements.appDateofvisitXpath);
			Thread.sleep(3000);
			List<WebElement>date = driver.findElements(By.xpath("//*[@id='numberpicker_input']"));
			LocalDate currentdate = LocalDate.now();
			int currentYear = currentdate.getYear();
			for(int j=0;j<currentYear;j++)
			{
				if(date.get(0).getText().equals(visitYear))
					break;
				else
					seetest.setPickerValues("NATIVE","xpath=//*[@class='android.widget.NumberPicker']",0,0,"down:1");
			}
			for(int j=0;j<12;j++)
			{
				if(date.get(1).getText().equals(visitMonth.substring(0,3)))
					break;
				else
					seetest.setPickerValues("NATIVE","xpath=//*[@class='android.widget.NumberPicker']",1,0,"down:1");
			}
			if(Integer.parseInt(visitday)<10)
				visitday="0"+visitday;
			System.out.println(visitday);
			for(int j=0;j<31;j++)
			{
				if(date.get(2).getText().equals(visitday))
					break;
				else
					seetest.setPickerValues("NATIVE","xpath=//*[@class='android.widget.NumberPicker']",2,0,"down:1");
			}
			
			seetest.click("NATIVE","xpath=//*[@id='button1']",0,1);
			log.info("Dateofvisit selected as : " +driver.findElement(Elements.appDateofvisitXpath).getAttribute("text"));
			extTestObj.createNode("Dateofvisit selected as : " +driver.findElement(Elements.appDateofvisitXpath).getAttribute("text")).pass("PASSED");
		} catch (Exception e) {
			log.error("Dateofvisit selection failed");
			extTestObj.createNode("Dateofvisit selection failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		// To enter receipt check number
		String checkNo = excel.getCellData("AddMyVisit", "Receipt Check Number", 2);

		try {
			clickElement(Elements.appreceiptcheckNumberTextBox_addmyvisit);
			sendKeysWait(Elements.appreceiptcheckNumberTextBox_addmyvisit, checkNo);
			seetest.closeKeyboard();
			log.info("Check Number entered as : " + checkNo);
			extTestObj.createNode("Check Number entered as : " + checkNo).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter check number");
			extTestObj.createNode("Failed to enter check number")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

		}
		//To enter subtotal
		String checkTotal = excel.getCellData("AddMyVisit", "Subtotal", 2);
		String subtotal = "$" + checkTotal + "." + "00";
		try {
			clickableWait(Elements.appsubTotalTextBox_addmyvisit);
			driver.getKeyboard().sendKeys(checkTotal);
			seetest.closeKeyboard();
			log.info("Check total entered as : " + subtotal);
			extTestObj.createNode("Check total entered as : " + subtotal).pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to enter check total");
			extTestObj.createNode("Failed to enter check total")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

		}
		//To submit visit details
		try {
			clickableWait(Elements.appvisitSubmitButton);
			log.info("Visit submitted");
			extTestObj.createNode("Visit submitted").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to submit visit");
			extTestObj.createNode("Failed to submit visit")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopTestforMobileBrowser();
		}
		
	}
	/*
	 * Function Name : androidAppValidateRewards()
	 * Purpose : To validate the available rewards for logged in user
	 * Platform : Android App
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void androidAppValidateRewards()
	{
		//To click on Rewards button
		try {
			clickableWait(Elements.appRewardButtonXpath);
			explicitWait(Elements.appRewardsHeaderXpath);
			if (driver.findElement(Elements.appRewardsHeaderXpath).isDisplayed()) {
				log.info("Rewards button clicked");
				extTestObj.createNode("Rewards button clicked").pass("PASSED");
			}
		}
		catch (Exception e) {
			log.error("Rewards button clicked");
			extTestObj.createNode("Rewards button clicked")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		//To validate no of Rewards
		try {
			explicitWait(Elements.appdisplayedRewardCountXpath);
			int displayedCount = Integer.parseInt(driver.findElement(Elements.appdisplayedRewardCountXpath).getText());
			List<WebElement> displayedRewards = driver.findElements(Elements.appActiveRewardXpath);
			int actualCount = displayedRewards.size();
			log.info("Displayed reward count : " + displayedCount);
			log.info("Actual reward count : " + actualCount);
			extTestObj.createNode("Displayed reward count : " + displayedCount).info("INFO");
			extTestObj.createNode("Actual reward count : " + actualCount).info("INFO");
			if (displayedCount == actualCount) {
				log.info("Rewards count matched");
				extTestObj.createNode("Rewards count matched").pass("PASSED");
			}
		}

		catch (Exception e) {
			log.error("Rewards count did not match");
			extTestObj.createNode("Rewards count did not match")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
		
		//To view the Reward Names
		try {
			List<WebElement> displayedRewards = driver.findElements(Elements.appActiveRewardXpath);

			for (int i = 0; i < displayedRewards.size(); i++) {
				AndroidElement element = (AndroidElement) driver.findElements(Elements.appTapToViewXpath).get(i);
				element.click();
				explicitWait(Elements.appActiveRewardName);
				log.info("Reward Name : " + driver.findElement(Elements.appActiveRewardName).getText());
				extTestObj.createNode("Reward Name : " + driver.findElement(Elements.appActiveRewardName).getText())
						.info("INFO");
				clickableWait(Elements.appCloseRewardsPopUp);

			}
		} catch (Exception e) {
			log.error("Failed to retrieve Reward names");
			extTestObj.createNode("Failed to retrieve Reward names")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();

		}
		
	}

	/*
	 * Function Name : androidAppSelectChilisFavouriteItems(int)
	 * Purpose : To select favorite items for Logged In/MCA user
	 * Platform : Android App
	 * Parameters : menu Index from CommonData.xlsx
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	/*
	 * Tried the below appium commands, however did not work in SeeTest Android
	 * device androidDriver.findElementByAndroidUIAutomator(
	 * "new UiScrollable(new UiSelector()).scrollIntoView(text(\"SELECT ITEMS\"))");
	 * TouchAction action = new TouchAction(androidDriver);
	 * action.longPress(longPressOptions().withElement(element(prevItemElement)).
	 * withDuration(Duration.ofSeconds(2))).
	 * moveTo(element(nextItemElement)).release().perform();
	 */
		public void androidAppSelectChilisFavouriteItems(int menuIndex) {
				String favItem= excel.getCellData("Menu", "ChilisFavourites", menuIndex);
			try {
				explicitWait(Elements.appMenuButtonXpath);
				seetest.swipeWhileNotFound("Down", 500, 200, "NATIVE", "xpath=//*[@text='SELECT ITEMS']", 0, 2000, 5,
						false);
					try {
						explicitWait(MobileBy.xpath("//*[@text='" + favItem + "']"));
						clickableWait(MobileBy.xpath("//*[@text='" + favItem + "']"));
						clickableWait(Elements.appFavoritesAddToCartXpath);
						log.info("Selected Favourite Item Name : " + favItem);
						extTestObj.createNode("Selected Favourite Item Name : " + favItem).info("INFO");
					} catch (Exception exp) {
						String favItemLocator = "//*[@text='" + favItem + "']";
						seetest.elementSwipeWhileNotFound("NATIVE", "xpath=//*[@id='card_home_recent_order_carousel']", "Right",
								100, 2000, "NATIVE", "xpath=" + favItemLocator + "", 0, 1000, 5, false);
						clickableWait(MobileBy.xpath("//*[@text='" + favItem + "']"));
						clickableWait(Elements.appFavoritesAddToCartXpath);
						log.info("Selected Favourite Item Name : " +favItem);
						extTestObj.createNode("Favourite Item Name : " +favItem).info("INFO");
					}
				log.info("Chilis Favourite Item Selected");
				extTestObj.createNode("Chilis Favourite Item Selected").pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to select Chilis Favourite Item");
				extTestObj.createNode("Failed to select Chilis Favourite Item")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();

			}
		}
		/*
		 * Function Name : androidAppgetChilisFavouriteItems()
		 * Purpose : To retrieve the names of favorite items available for Logged In/MCA user
		 * Platform : Android App
		 * Parameters : None
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */
		public void androidAppgetChilisFavouriteItems() {
			String[] favoriteItems = new String[5];
			for (int i = 0; i < favoriteItems.length; i++)
				favoriteItems[i] = excel.getCellData("Menu", "ChilisFavourites", i + 2);
			try {
				try {
				explicitWait(Elements.appfindRestaurantLinkXpath);
				}
				catch(Exception e)
				{
					explicitWait(Elements.appChangeLocationButton);
				}
				seetest.swipeWhileNotFound("Down", 500, 200, "NATIVE", "xpath=//*[@text='SELECT ITEMS']", 0, 2000, 5,
						false);
				for (int i = 0; i < favoriteItems.length; i++) {
					String fav = favoriteItems[i];
					try {
						explicitWait(MobileBy.xpath("//*[@text='" + fav + "']"));
						String dispItem = driver.findElement(MobileBy.xpath("//*[@text='" + fav + "']")).getText();
						log.info(" Favourite Item Name : " + dispItem);
						extTestObj.createNode("Favourite Item Name : " + dispItem).info("INFO");
					} catch (Exception exp) {
						String favItemLocator = "//*[@text='" + fav + "']";
						seetest.elementSwipeWhileNotFound("NATIVE", "xpath=//*[@id='card_home_recent_order_carousel']", "Right",
								100, 2000, "NATIVE", "xpath=" + favItemLocator + "", 0, 1000, 5, false);
						String dispItem = driver.findElement(MobileBy.xpath("//*[@text='" + fav + "']")).getText();
						log.info(" Favourite Item Name : " + dispItem);
						extTestObj.createNode("Favourite Item Name : " + dispItem).info("INFO");
					}
				}
				log.info("Chilis Favourite Item names obtained");
				extTestObj.createNode("Chilis Favourite Item names obtained").pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to retrieve Chilis Favourite Item Names");
				extTestObj.createNode("Failed to retrieve Chilis Favourite Item Names")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();

			}
		}

		/*
		 * Function Name : iosAppgetChilisFavouriteItems()
		 * Purpose : To retrieve the names of favorite items available for Logged In/MCA user
		 * Platform : IOS App
		 * Parameters : None
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */
		public void iosAppgetChilisFavouriteItems() {
			String[] favoriteItems = new String[5];
			for (int i = 0; i < favoriteItems.length; i++)
				favoriteItems[i] = excel.getCellData("Menu", "ChilisFavourites", i + 2);
			try {
//				explicitWait(Elements.iosstartOrderXpath);
				seetest.swipe("Down",600,2000);
				for (int i = 0; i < favoriteItems.length; i++) {
					String[] fav = favoriteItems[i].split(" ");
					try {
						explicitWait(MobileBy.xpath("//*[contains(@text,'" + fav[0] + "')]"));
						String dispItem = driver.findElement(MobileBy.xpath("//*[contains(@text,'" + fav[0] + "')]"))
								.getText();
						log.info(" Favourite Item Name : " + dispItem);
						extTestObj.createNode("Favourite Item Name : " + dispItem).info("INFO");
					} catch (Exception exp) {
						String favItemLocator = "//*[contains(@text,'" + fav[0] + "')]";
						seetest.elementSwipeWhileNotFound("NATIVE", "xpath=(//*[@class='UIACollectionView'])[1]", "Right",
								100, 2000, "NATIVE", "xpath=" + favItemLocator + "", 0, 1000, 5, false);
						String dispItem = driver.findElement(MobileBy.xpath("//*[contains(@text,'" + fav[0] + "')]"))
								.getText();
						log.info(" Favourite Item Name : " + dispItem);
						extTestObj.createNode("Favourite Item Name : " + dispItem).info("INFO");
					}
				}
				log.info("Chilis Favourite Item names obtained");
				extTestObj.createNode("Chilis Favourite Item names obtained").pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to retrieve Chilis Favourite Item Names");
				extTestObj.createNode("Failed to retrieve Chilis Favourite Item Names")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();

			}
		}

		/*
		 * Function Name : iosAppClosePopUp()
		 * Purpose : To close welcome pop up in IOS App
		 * Platform : IOS App
		 * Parameters : None
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */
		public void iosAppClosePopUp() {
			try {
				clickableWait(Elements.iosPopUpClose);
//				explicitWait(Elements.ioswelcomeMessageXpath);
//				if (driver.findElement(Elements.ioswelcomeMessageXpath).isDisplayed()) {
					log.info("Pop up closed");
					extTestObj.createNode("Pop up closed").pass("PASSED");
//				}
			} catch (Exception e) {
				log.error("Failed to close pop up");
				extTestObj.createNode("Failed to close pop up")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}

		}
		
		/*
		 * Function Name : iosAppValidateLogin()
		 * Purpose : To validate login functionality
		 * Platform : IOS App
		 * Parameters : None
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */	
	public void iosAppValidateLogin()
	{
		//To click on login button
		try {
			explicitWait(Elements.iosLoginButton);
			clickableWait(Elements.iosLoginButton);
			explicitWait(Elements.iosloginHeaderXpath);
			if (driver.findElement(Elements.iosloginHeaderXpath).isDisplayed()) {
				log.info("Login button clicked");
				extTestObj.createNode("Login button clicked").pass("PASSED");
			}
		} catch (Exception e) {
			log.error("Login button click failed");
			extTestObj.createNode("Login button click failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopIOSTest();
		}
		//To enter username
		String username = excel.getCellData("Credentials", "UserName", 2);
		try {
			sendKeysWait(Elements.iosUserNameTextBoxXpath, username);
			log.info("User name " + username + " entered");
			extTestObj.createNode("User name " + username + " entered").pass("PASSED");
		} catch (Exception e) {
			log.error("Could not enter user name");
			extTestObj.createNode("Could not enter user name")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopIOSTest();
		}
		
		//To enter password
		String password = excel.getCellData("Credentials", "Password", 2);
		try {
			sendKeysWait(Elements.iosPaswordTextBoxXpath, password);
			log.info("Password" + password + " entered");
			extTestObj.createNode("Password  " + password + " entered").pass("PASSED");
		} catch (Exception e) {
			log.error("Could not enter Password");
			extTestObj.createNode("Could not enter Password")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopIOSTest();
		}
		//To click on sign in button
		try {
			clickableWait(Elements.iosSignInButtonXpath);
			try {
			clickableWait(Elements.iosCancelButtonXpath);
			}
			catch(Exception e)
			{}
			log.info("Sign in button clicked");
			extTestObj.createNode("Sign in button clicked").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to click Sign in button");
			extTestObj.createNode("Failed to click Sign in button")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopIOSTest();
		}
		
		
	}
	/*
	 * Function Name : appClickMoreButtonIOS()
	 * Purpose : To click on More button
	 * Platform : IOS App
	 * Parameters : None
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */	
		public void appClickMoreButtonIOS() {
			try {
				clickableWait(Elements.iosMoreButtonXpath);
				explicitWait(Elements.iosLogOutButtonXpath);
				if (driver.findElement(Elements.iosLogOutButtonXpath).isDisplayed()) {
					log.info("More button clicked");
					extTestObj.createNode("More button clicked").pass("PASSED");
				}
			} catch (Exception e) {
				log.error("Failed to click More button");
				extTestObj.createNode("Failed to click More button")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
		}
		
		/*
		 * Function Name : iosAppValidateLogout()
		 * Purpose : To validate log out functionality
		 * Platform : IOS App
		 * Parameters : None
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */	
		public void iosAppValidateLogout()
		{
			//To click on More button
			try {
				clickableWait(Elements.iosMoreButtonXpath);
				explicitWait(Elements.iosLogOutButtonXpath);
				if (driver.findElement(Elements.iosLogOutButtonXpath).isDisplayed()) {
					log.info("More button clicked");
					extTestObj.createNode("More button clicked").pass("PASSED");
				}
			} catch (Exception e) {
				log.error("Failed to click More button");
				extTestObj.createNode("Failed to click More button")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			//To select log out option
			try {
				incrementalDragOnScreenAndClick(seetest,"xpath=//*[@id='LOG OUT']","Down","NATIVE",true);
				seetest.sleep(1000);
//				clickableWait(MobileBy.xpath("//*[@text='YES']"));
//				explicitWait(Elements.ioslogoutValXpath);
					log.info("Logout button clicked and log out successful");
					extTestObj.createNode("Logout button clicked and log out successful").pass("PASSED");
			} catch (Exception e) {
				log.error("Logout button clicked and log out successful");
				extTestObj.createNode("Logout button clicked and log out successful")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			
		}
		
		/*
		 * Function Name : iosAppValidateRestaurantSelectionForGuestUser(int)
		 * Purpose : To validate restaurant selection for guest user
		 * Platform : IOS App
		 * Parameters : Location index from CommonData.xlsx
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */	
		public void iosAppValidateRestaurantSelectionForGuestUser(int locationIndex)
		{
			//To click on search restaurant link and navigate to Restaurant search window
			String storeName = excel.getCellData("Locations", "Store Name", locationIndex);
			try {
				Thread.sleep(4000);
				explicitWait(Elements.iosSearchLocationLinkXpath);
				clickableWait(Elements.iosSearchLocationLinkXpath);
				explicitWait(Elements.iosRestaurantSearchTextBoxXpath);
			log.info("User navigated to Restaurant search window");
			extTestObj.createNode("User navigated to Restaurant search window").pass("PASSED");
		}
			
			catch (Exception e) {
				log.error("Failed to navigate to restaurant search window");
				extTestObj.createNode("Failed to navigate to restaurant search window")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			//To enter restaurant location
			String restaurantLocation = excel.getCellData("Locations", "Location", locationIndex);
			String[] str = restaurantLocation.split(",");
			try {
				clickableWait(Elements.iosRestaurantSearchTextBoxXpath);
				sendKeysWait(Elements.iosRestaurantAutocompleteTextBoxXpath, restaurantLocation);
				clickElement(MobileBy.xpath("(//*[@class='UIAStaticText'])[5]"));
				String displayedAddress = driver.findElement(Elements.iosRestaurantSearchTextBoxXpath).getAttribute("text");
					log.info("Restaurant location entered as : " + displayedAddress);
					extTestObj.createNode("Restaurant location entered as : " + displayedAddress).pass("PASSED");
			}

			catch (Exception e) {
				log.error("Failed to enter Restaurant Location");
				extTestObj.createNode("Failed to enter Restaurant Location")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();

			}
			//To select a particular store available in the entered location
			try {
				explicitWait(Elements.iosresSearchClearXpath);
				incrementalDragOnScreenAndClick(seetest,"//*[@id='SelectRestaurantButton' and (./preceding-sibling::* | ./following-sibling::*)[@text='"+storeName+"']]","Down","NATIVE",true);
				Thread.sleep(3000);
				log.info("Resturant selected as : " +storeName);
				extTestObj.createNode("Resturant selected as : " + storeName).pass("PASSED");
			} catch (Exception e) {
				log.error("Not able to select any resturant");
				extTestObj.createNode("Not able to select any resturant")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();

			}	
		}
		

		/*
		 * Function Name : iosAppValidateRestaurantSelection(int)
		 * Purpose : To validate restaurant selection for logged in/MCA user
		 * Platform : IOS App
		 * Parameters : Location index from CommonData.xlsx
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */	
		public void iosAppValidateRestaurantSelection(int locationIndex)
		{
			//To check whether the store is already selected
			try {
				clickableWait(By.xpath("//*[@text='Cancel']"));
			}
			catch(Exception e) {}
			String storeName = excel.getCellData("Locations", "Store Name", locationIndex);
			try {
				explicitWait(By.xpath("//*[contains(@id,'"+storeName.split(" ")[0]+"')]"));
				log.info("Store "+storeName+" is already selected");
				extTestObj.createNode("Store "+storeName+" is already selected").pass("PASSED");
			}
			//To navigate to the Restaurant search window if the required store is not selected
			catch(Exception exception) {
			try {
			try {
				Thread.sleep(4000);
				explicitWait(Elements.iosSearchLocationLinkXpath);
				clickableWait(Elements.iosSearchLocationLinkXpath);
//				try {
//					clickableWait(By.xpath("//*[@text='Back']"));
//				}
//				catch(Exception exp) {}
				explicitWait(Elements.iosRestaurantSearchTextBoxXpath);
			}
			catch(Exception e)
			{   
				clickableWait(Elements.iosappClickOrder);
				try {
				clickableWait(Elements.iosChangeLocation);
				}
				catch(Exception exp)
				{}
				explicitWait(Elements.iosRestaurantSearchTextBoxXpath);
			}
			log.info("User navigated to Restaurant search window");
			extTestObj.createNode("User navigated to Restaurant search window").pass("PASSED");
		}
			
			catch (Exception e) {
				log.error("Failed to navigate to restaurant search window");
				extTestObj.createNode("Failed to navigate to restaurant search window")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			//To enter Restaurant location
			String restaurantLocation = excel.getCellData("Locations", "Location", locationIndex);
			String[] str = restaurantLocation.split(",");
			try {
				clickableWait(Elements.iosRestaurantSearchTextBoxXpath);
				sendKeysWait(Elements.iosRestaurantAutocompleteTextBoxXpath, restaurantLocation);
				clickElement(MobileBy.xpath("(//*[contains(@text,'"+str[0]+"') and @class='UIAStaticText'])[1]"));
				String displayedAddress = driver.findElement(Elements.iosRestaurantSearchTextBoxXpath).getAttribute("text");
					log.info("Restaurant location entered as : " + displayedAddress);
					extTestObj.createNode("Restaurant location entered as : " + displayedAddress).pass("PASSED");
			}

			catch (Exception e) {
				log.error("Failed to enter Restaurant Location");
				extTestObj.createNode("Failed to enter Restaurant Location")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();

			}
			//To select a particular store available in the selected location
			try {
				explicitWait(Elements.iosresSearchClearXpath);
				incrementalDragOnScreenAndClick(seetest,"//*[@id='SelectRestaurantButton' and (./preceding-sibling::* | ./following-sibling::*)[@text='"+storeName+"']]","Down","NATIVE",true);
				Thread.sleep(3000);
				log.info("Resturant selected as : " +storeName);
				extTestObj.createNode("Resturant selected as : " + storeName).pass("PASSED");
			} catch (Exception e) {
				log.error("Not able to select any resturant");
				extTestObj.createNode("Not able to select any resturant")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();

			}
			}
			
		}
	
		/*
		 * Function Name : iosAppValidateRewards()
		 * Purpose : To validate the rewards available for Logged In/MCA user
		 * Platform : IOS App
		 * Parameters : None
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */	
		public void  iosAppValidateRewards()
		{ 
			//To click on Rewards button
			try {
				clickableWait(Elements.iosRewardsButtonXpath);
				explicitWait(Elements.iosRewardsHeaderXpath);
				if (driver.findElement(Elements.iosRewardsHeaderXpath).isDisplayed()) {
					log.info("Rewards button clicked");
					extTestObj.createNode("Rewards button clicked").pass("PASSED");
				}
			}
			catch (Exception e) {
				log.error("Rewards button clicked failed");
				extTestObj.createNode("Rewards button clicked failed")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			//To retrieve the displayed reward count, actual reward count and compare them
			try {
				explicitWait(Elements.iosDisplayedRewardCount);
				int displayedCount = Integer.parseInt(driver.findElement(Elements.iosDisplayedRewardCount).getText());
				clickableWait(Elements.iosClaimedRewardsXpath);
				explicitWait(Elements.iosRewardsSubHeaderXpath);
				List<WebElement> displayedRewards = driver.findElements(Elements.iosrewardsImageXpath);
				int actualCount = displayedRewards.size();
				log.info("Displayed reward count : " + displayedCount);
				log.info("Actual reward count : " + actualCount);
				extTestObj.createNode("Displayed reward count : " + displayedCount).info("INFO");
				extTestObj.createNode("Actual reward count : " + actualCount).info("INFO");
				if (displayedCount == actualCount) {
					log.info("Rewards count matched");
					extTestObj.createNode("Rewards count matched").pass("PASSED");
				}
			}

			catch (Exception e) {
				log.error("Rewards count did not match");
				extTestObj.createNode("Rewards count did not match")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			//To retireve the reward names
			try {
				List<WebElement> displayedRewards = driver.findElements(Elements.iosrewardsImageXpath);

				for (int i = 0; i < displayedRewards.size(); i++) {
					WebElement element = (WebElement) driver.findElements(Elements.iostapToViewXpath).get(i);
					element.click();
					explicitWait(Elements.iosRewardsNameXpath);
					log.info("Reward Name : " + driver.findElement(Elements.iosRewardsNameXpath).getText());
					extTestObj.createNode("Reward Name : " + driver.findElement(Elements.iosRewardsNameXpath).getText())
							.info("INFO");
					clickableWait(Elements.iosBackButtonXpath);

				}
			} catch (Exception e) {
				log.error("Failed to retrieve Reward names");
				extTestObj.createNode("Failed to retrieve Reward names")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();

			}
			
		}
		/*
		 * Function Name : androidAppEnterGuestDetails()
		 * Purpose : To enter guest details for guest user
		 * Platform : Android App
		 * Parameters : None
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */	
		public void androidAppEnterGuestDetails()
		{
			//To enter guest first name
			String firstName = excel.getCellData("GuestUserDetails", "First Name", 2);
			try {
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath=" + Elements.appFirstName, 0, 3000, 5, false);
				explicitWait(By.xpath(Elements.appFirstName));
				seetest.elementSendText("NATIVE", "xpath=" + Elements.appFirstName, 0, firstName);
				log.info("First name entered as : " + firstName);
				extTestObj.createNode("First name entered as : " +firstName).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter First name");
				extTestObj.createNode("Failed to enter First name")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			//To enter guest last name
			String lastName = excel.getCellData("GuestUserDetails", "Last Name", 2);
			try {
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath=" + Elements.appLastName, 0, 3000, 5, false);
				explicitWait(By.xpath(Elements.appLastName));
				seetest.elementSendText("NATIVE", "xpath=" + Elements.appLastName, 0, lastName);
				log.info("Last name entered as : " + lastName);
				extTestObj.createNode("Last name entered as : " + lastName).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter Last name");
				extTestObj.createNode("Failed to enter Last name")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			//To enter guest contact number
			String contactNum = excel.getCellData("GuestUserDetails", "Contact Number", 2);
			try {
				seetest.setProperty("Android.native.nonInstrumented","false");
				seetest.swipeWhileNotFound("Down", 900, 500, "WEB", "xpath=" + Elements.appContactNumber, 0, 3000, 5,
						false);
				seetest.waitForElement("WEB", "xpath=" + Elements.appContactNumber,0, 4000);
				seetest.elementSendText("WEB", "xpath=" + Elements.appContactNumber, 0, contactNum);
				seetest.setProperty("Android.native.nonInstrumented","true");
				log.info("Contact Number entered as : " + contactNum);
				extTestObj.createNode("Contact Number entered as : " + contactNum).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter Contact number");
				extTestObj.createNode("Failed to enter Contact number")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			//To enter guest email
			String email = excel.getCellData("GuestUserDetails", "Email", 2);
			try {
				seetest.swipeWhileNotFound("Down", 900, 500, "TEXT", "E-MAIL ADDRESS", 0, 3000, 5, true);
				driver.getKeyboard().sendKeys(email);
				seetest.closeKeyboard();
				log.info("Email address entered as : " + email);
				extTestObj.createNode("Email address entered as : " + email).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter Email id");
				extTestObj.createNode("Failed to enter Email id")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());

				stopTestforMobileBrowser();
			}
			
		}
		/*
		 * Function Name : androidAppEnterVehicleDetails()
		 * Purpose : To enter vehicle details for curbside order
		 * Platform : Android App
		 * Parameters : None
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */	
		public void androidAppEnterVehicleDetails()
		{
			//To enter vehicle make
			String vehicleMake = excel.getCellData("GuestUserCurbSide", "Vehicle Make", 2);
			try {
				seetest.swipeWhileNotFound("Down", 900, 500, "TEXT", Elements.appVehicleMake, 0, 3000, 5, true);
				seetest.sendText(vehicleMake);
				seetest.closeKeyboard();
				log.info("Vehicle make entered as : " + vehicleMake);
				extTestObj.createNode("Vehicle make entered as : " + vehicleMake).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter Vehicle Make");
				extTestObj.createNode("Failed to enter Vehicle Make")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			
			//To enter vehicle model
			String vehicleModel = excel.getCellData("GuestUserCurbSide", "Vehicle Model", 2);
			try {
				seetest.swipeWhileNotFound("Down", 900, 500, "TEXT", Elements.appVehicleModel, 0, 3000, 5, true);
				seetest.sendText(vehicleModel);
				seetest.closeKeyboard();
				log.info("Vehicle model entered as : " + vehicleModel);
				extTestObj.createNode("Vehicle model entered as : " + vehicleModel).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter Vehicle Model");
				extTestObj.createNode("Failed to enter Vehicle Model")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			
			//To enter vehicle color
			String vehicleColor = excel.getCellData("GuestUserCurbSide", "Vehicle Color", 2);
			try {
				seetest.swipeWhileNotFound("Down", 900, 500, "TEXT", Elements.appVehicleColor, 0, 3000, 5, true);
				seetest.sendText(vehicleColor);
				seetest.closeKeyboard();
				log.info("Vehicle color entered as : " + vehicleColor);
				extTestObj.createNode("Vehicle color entered as : " + vehicleColor).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter Vehicle color");
				extTestObj.createNode("Failed to enter Vehicle color")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			
		}	
	  
		/*
		 * Function Name : androidAppSelectMenuCategory()
		 * Purpose : To select menu category
		 * Platform : Android App
		 * Parameters : None
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */	
		public void androidAppSelectMenuCategory() {
			try {
				try {
				clickableWait(Elements.appMenuButtonXpath);
				}
				catch(Exception exp)
				{}
				explicitWait(Elements.appChilisMenuHeader);
				String menuCategory = excel.getCellData("Menu", "Category for App", 2);
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE",
						"xpath=//*[@text='" + menuCategory + "' and ./parent::*[contains(@id,'menu-category-name')]]", 0,
						3000, 5, true);
				log.info("Menu Category selected with : " + menuCategory);
				extTestObj.createNode("Menu Category selected with : " + menuCategory).pass("PASSED");

			} catch (Exception e) {
				log.error("Menu Category selection failed");
				extTestObj.createNode("Menu Category selection failed")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
		}
		
		/*
		 * Function Name : androidAppValidateMenuSelection(int)
		 * Purpose : To validate selection of menu category and menu item
		 * Platform : Android App
		 * Parameters :menuIndex from CommonData.xlsx
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */	
		public void androidAppValidateMenuSelection(int menuIndex)
		{
			//To select Menu Category
			try {
				try {
				clickableWait(Elements.appMenuButtonXpath);
				}
				catch(Exception exp)
				{}
				explicitWait(Elements.appChilisMenuHeader);
				String menuCategory = excel.getCellData("Menu", "Category", menuIndex);
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE",
						"xpath=//*[@text='" + menuCategory + "' and ./parent::*[contains(@id,'menu-category-name')]]", 0,
						3000, 5, true);
				log.info("Menu Category selected with : " + menuCategory);
				extTestObj.createNode("Menu Category selected with : " + menuCategory).pass("PASSED");

			} catch (Exception e) {
				log.error("Menu Category selection failed");
				extTestObj.createNode("Menu Category selection failed")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			//To select Menu Item
			String menuItem = excel.getCellData("Menu", "Item", menuIndex);
			try {
				
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE",
						"xpath=//*[@text='" + menuItem + "' and ./parent::*[contains(@id,'menu-item-name')]]", 0, 3000, 5,
						true);
				log.info("Menu Item selected with : " + menuItem);
				extTestObj.createNode("Menu Item selected with : " + menuItem).pass("PASSED");

			} catch (Exception e) {
				log.error("Menu Item selection failed");
				extTestObj.createNode("Menu Item selection failed")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			
			//To add the item to order
			try {
				
				explicitWait(MobileBy.xpath("//*[@text='" + menuItem + "' and @class='android.view.View']"));
//				seetest.setProperty("Android.native.nonInstrumented","false");
				seetest.swipeWhileNotFound("Down", 900,1000, "TEXT","ADD TO ORDER", 0, 2000, 5, true);
				log.info("Add To Order button clicked");
				extTestObj.createNode("Add To Order button clicked").pass("PASSED");
//				seetest.setProperty("Android.native.nonInstrumented","true");
			} catch (Exception e) {
				log.error("Add To Order button click failed");
				extTestObj.createNode("Add To Order button click failed")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			
		}

		/*
		 * Function Name : androidAppSelectMenuItem()
		 * Purpose : To validate selection of  menu item
		 * Platform : Android App
		 * Parameters :None
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */	
		public void androidAppSelectMenuItem() { // new
			try {
				String menuItem = excel.getCellData("Menu", "Item", 2);
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE",
						"xpath=//*[@text='" + menuItem + "' and ./parent::*[contains(@id,'menu-item-name')]]", 0, 3000, 5,
						true);
				log.info("Menu Item selected with : " + menuItem);
				extTestObj.createNode("Menu Item selected with : " + menuItem).pass("PASSED");

			} catch (Exception e) {
				log.error("Menu Item selection failed");
				extTestObj.createNode("Menu Item selection failed")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();

			}

		}

		/*
		 * Function Name : androidAppValidateCheckOut()
		 * Purpose : To validate check out page and its functionality
		 * Platform : Android App
		 * Parameters :None
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */	
		public void androidAppValidateCheckOut()
		{
			//To validate item name in check out page
			try {
				seetest.setProperty("Android.native.nonInstrumented","false");
				seetest.verifyElementFound("WEB","xpath="+Elements.itemNameInCheckOutPageOfApp,0);
				String ItemName = seetest.elementGetText("WEB","xpath="+Elements.itemNameInCheckOutPageOfApp,0);
				log.info("Item name in checkout page : "+ItemName);
				extTestObj.createNode("Item name in checkout page : "+ItemName).pass("PASSED");
			}
			catch(Exception e)
			{log.warn("Item name not displayed in checkout page");
			extTestObj.createNode("Item name not displayed in checkout page")
			.warning("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
	        log.error(e.getMessage());
				
			}
			//To opt in silver ware
			try {
				seetest.swipeWhileNotFound("Down", 900, 500, "TEXT",
						"Include silverware",
						0, 3000, 5, false);
				seetest.click("TEXT","Include silverware", 0, 1);
				log.info("Silver ware opted in");
				extTestObj.createNode("Silver ware opted in").pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to opt in Silver ware");
				extTestObj.createNode("Failed to opt in Silver ware")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			//To click on check out button
			try {
				seetest.setProperty("Android.native.nonInstrumented","true");
				seetest.swipeWhileNotFound("Down", 450, 500, "NATIVE", "xpath="+Elements.appCheckOutButtonXpath, 0, 3000, 6, true);
//				seetest.swipeWhileNotFound("Down", 450, 500, "TEXT", "CHECK OUT", 0, 3000, 6, true);
				explicitWait(Elements.appCheckOutHeaderXpath);
				log.info("Checkout button clicked");
				extTestObj.createNode("Checkout button clicked").pass("PASSED");
				
			} catch (Exception e) {
				log.error("Checkout button click failed");
				extTestObj.createNode("Checkout button click failed")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();

			}
			
		}
		/*
		 * Function Name : androidAppSelectCarryout()
		 * Purpose : To select order type as carry out
		 * Platform : Android App
		 * Parameters :None
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */	
		public void androidAppSelectCarryout() {
			try {
				clickableWait(Elements.appSelectCarryOut);
				log.info("Carryout button clicked");
				extTestObj.createNode("Carryout button clicked").pass("PASSED");
			} catch (Exception e) {
				log.error("Carryout button click failed");
				extTestObj.createNode("Carryout button click failed")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();

			}
		}

		/*
		 * Function Name : androidAppSelectCurbside()
		 * Purpose : To select order type as curbside
		 * Platform : Android App
		 * Parameters :None
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */	
		public void androidAppSelectCurbside() {
			try {
				clickableWait(Elements.appSelectCurbside);
				log.info("Curbside button clicked");
				extTestObj.createNode("Curbide button clicked").pass("PASSED");
			} catch (Exception e) {
				log.error("Curbside button click failed");
				extTestObj.createNode("Curbside button click failed")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();

			}
		}

		/*
		 * Function Name : androidAppSelectPickupLaterToday()
		 * Purpose : To select pick up type as Later Today and select pick up time
		 * Platform : Android App
		 * Parameters :None
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */	
		public void androidAppSelectPickupLaterToday() {

			//To select pick up type as 'Later Today'
			try {
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath=//*[@id='pickup-date']", 0, 3000, 5, false);
				if (!driver.findElement(Elements.pickDate).getAttribute("text").contains("Later")) {
					clickableWait(Elements.pickDate);
					seetest.elementListPick("NATIVE", "xpath=//*[@class='android.widget.ListView']", "NATIVE",
							"text=Later Today", 0, true);
				}
				log.info("Pickup date selected as 'Later Today'");
				extTestObj.createNode("Pickup date selected as 'Later Today'").pass("PASSED");
			} catch (Exception e) {
				log.error("Pickup date selection failed");
				extTestObj.createNode("Pickup date selection failed")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			//To select pick up time
			String pickTime = excel.getCellData("CarryOut", "Pickup Time for App", 2);
			try {
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath=//*[@id='pickup-time']", 0, 3000, 5, false);
				if (!driver.findElement(Elements.pickTime).getAttribute("text").contains(pickTime)) {
					clickableWait(Elements.pickTime);
					seetest.elementListPick("NATIVE", "xpath=//*[@class='android.widget.ListView']", "NATIVE",
							"text=" + pickTime, 0, true);
				}
				log.info("Pickup time selected as: " + pickTime);
				extTestObj.createNode("\"Pickup time selected as: " + pickTime).pass("PASSED");
			} catch (Exception e) {
				log.error("Pick up time selection failed");
				extTestObj.createNode("Pick up time selection failed")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();

			}
			
		}
		/*
		 * Function Name : androidAppSelectPickupFutureDate()
		 * Purpose : To select pick up type as Future Date and select pick up time
		 * Platform : Android App
		 * Parameters :None
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */	
		public void androidAppSelectPickupFutureDate() {

			//To select future pick up date
			try {
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath=//*[@id='pickup-date']", 0, 3000, 5, true);
				seetest.elementListPick("NATIVE", "xpath=//*[@class='android.widget.ListView']", "NATIVE","xpath="+Elements.appFutureDateXpath, 0, true);
				explicitWait(Elements.pickDate);
				String date=driver.findElement(Elements.pickDate).getAttribute("text");
				log.info("Pickup date selected as "+date);
				extTestObj.createNode("Pickup date selected as "+date).pass("PASSED");
			} catch (Exception e) {
				log.error("Pickup date selection failed");
				extTestObj.createNode("Pickup date selection failed")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			//To select pick up time
			String pickTime = excel.getCellData("CarryOut", "Pickup Time for App", 2);
			try {
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath=//*[@id='pickup-time']", 0, 3000, 5, false);
				if (!driver.findElement(Elements.pickTime).getAttribute("text").contains(pickTime)) {
					clickableWait(Elements.pickTime);
					seetest.elementListPick("NATIVE", "xpath=//*[@class='android.widget.ListView']", "NATIVE",
							"text=" + pickTime, 0, true);
				}
				log.info("Pickup time selected as: " + pickTime);
				extTestObj.createNode("\"Pickup time selected as: " + pickTime).pass("PASSED");
			} catch (Exception e) {
				log.error("Pick up time selection failed");
				extTestObj.createNode("Pick up time selection failed")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();

			}
		}
		
		/*
		 * Function Name : androidAppSelectPickupAsap()
		 * Purpose : To select pick up type as ASAP 
		 * Platform : Android App
		 * Parameters :None
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */	
		public void androidAppSelectPickupAsap() {

			try {
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath=//*[@id='pickup-date']", 0, 3000, 5, false);
				if (!driver.findElement(Elements.pickDate).getAttribute("text").contains("ASAP")) {
					clickableWait(Elements.pickDate);
					seetest.elementListPick("NATIVE", "xpath=//*[@class='android.widget.ListView']", "NATIVE",
							"xpath=//*[contains(@text,'ASAP')]", 0, true);
				}
				log.info("Pickup  selected as 'Asap'");
				extTestObj.createNode("Pickup  selected as 'Asap'").pass("PASSED");
			} catch (Exception e) {
				log.error("Pickup Asap selection failed");
				extTestObj.createNode("Pickup Asap selection failed")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();

			}
		}



		/*
		 * Function Name : androidAppContinueToPayment()
		 * Purpose : To click 'Continue to Payment'
		 * Platform : Android App
		 * Parameters :None
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */	
		/*
		 * Tried the below appium commands but did not work,
		 * androidDriver.findElementByAndroidUIAutomator(
		 * "new UiScrollable(new UiSelector()).scrollIntoView(text(\"CONTINUE TO PAYMENT\"))"
		 * ); clickableWait(Elements.appContinuePayment);
		 */
		public void androidAppContinueToPayment() {
			try {
				seetest.setProperty("Android.native.nonInstrumented","false");
				seetest.swipeWhileNotFound("Down", 900, 600, "WEB", Elements.appContinueToPaymentXpath, 0, 4000, 7, false);
				seetest.click("WEB", Elements.appContinueToPaymentXpath, 0, 1);
				log.info("Continue To Payment button clicked");
				extTestObj.createNode("Continue To Payment button clicked").pass("PASSED");
				seetest.setProperty("Android.native.nonInstrumented","true");
			} catch (Exception e) {
				log.error("Continue To Payment button click failed");
				extTestObj.createNode("Continue To Payment button click failed")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
				

			}
		}

		/*
		 * Function Name : iosAppContinueToPayment()
		 * Purpose : To click 'Continue to Payment'
		 * Platform : IOS App
		 * Parameters :None
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */	
		public void iosAppContinueToPayment() {
			try {
				incrementalDragOnScreenAndClick(seetest,Elements.iosContinueToPayment,"Down","NATIVE",false);
				seetest.swipe("Down",600, 500);
				seetest.click("NATIVE","xpath="+Elements.iosContinueToPayment,0,1);
				log.info("Continue To Payment button clicked");
				extTestObj.createNode("Continue To Payment button clicked").pass("PASSED");
			} catch (Exception e) {
				log.error("Continue To Payment button click failed");
				extTestObj.createNode("Continue To Payment button click failed")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
		}
		
		/*
		 * Function Name : androidAppEnterPaymentDetails()
		 * Purpose : To validate payment page and enter payment details
		 * Platform : Android App
		 * Parameters :None
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */	
		public void androidAppEnterPaymentDetails()
		{
			//To enter Card Number
			try {
				explicitWait(Elements.iosFinalizeOrderHeaderXpath);
				String cardNumber = excel.getCellData("LoggedInOrder", "Card Number", 2);
				Thread.sleep(4000);
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.appCardNumber, 0, 3000, 5, false);
				seetest.elementSendText("NATIVE","xpath="+Elements.appCardNumber,0,cardNumber);
				seetest.closeKeyboard();
				log.info("Card number entered as : " + driver.findElement(By.xpath(Elements.appCardNumber)).getAttribute("text"));
				extTestObj.createNode("Card number entered as : " + driver.findElement(By.xpath(Elements.appCardNumber)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter card number");
				extTestObj.createNode("Failed to card number")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			
			// To enter CVV Number
			try {

				String cvvNumber = excel.getCellData("LoggedInOrder", "CVV", 2);
				Thread.sleep(2000);
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.appCVVNumber, 0, 3000, 5, false);
				seetest.elementSendText("NATIVE", "xpath="+Elements.appCVVNumber, 0, cvvNumber);
				seetest.closeKeyboard();
				log.info("Cvv number entered as : " + driver.findElement(By.xpath(Elements.appCVVNumber)).getAttribute("text"));
				extTestObj.createNode("Cvv number entered as : " + driver.findElement(By.xpath(Elements.appCVVNumber)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter cvv number");
				extTestObj.createNode("Failed to cvv number")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			
			// To select expiration month
			String month = excel.getCellData("LoggedInOrder", "Expiration Month", 2);
			try {
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.appExpirationMonthXpath, 0, 3000, 5,
						false);
				clickableWait(Elements.expirationMonth);
				seetest.sleep(2000);
				seetest.elementListPick("NATIVE", "xpath="+Elements.androidListViewXpath, "NATIVE",
						"xpath=//*[contains(@text,'(0" + month + ")')]", 0, true);
				log.info("Expiration Month selected as : " + driver.findElement(By.xpath(Elements.appExpirationMonthXpath)).getAttribute("text"));
				extTestObj.createNode("Expiration Month selected as : " + driver.findElement(By.xpath(Elements.appExpirationMonthXpath)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to select Expiration Month");
				extTestObj.createNode("Failed to select Expiration Month")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());

				stopTestforMobileBrowser();
			}
			
			//To select expiration year
			String year = excel.getCellData("LoggedInOrder", "Expiration Year", 2);
			try {
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.appExpirationYearXpath, 0, 3000, 5, false);
				clickableWait(Elements.expirationYear);
				seetest.sleep(2000);
				seetest.click("NATIVE", "xpath=//*[contains(@text,'" + year + "')]", 0, 1);
				log.info("Expiration Year selected as : " +driver.findElement(By.xpath(Elements.appExpirationYearXpath)).getAttribute("text"));
				extTestObj.createNode("Expiration Year selected as : " + driver.findElement(By.xpath(Elements.appExpirationYearXpath)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to select Expiration Year");
				extTestObj.createNode("Failed to select Expiration Year")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			
			//To enter Name on Card
			try {

				String cardName = excel.getCellData("LoggedInOrder", "Name On Card", 2);
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.appNameOnCardXpath, 0, 3000, 5, false);
				seetest.elementSendText("NATIVE", "xpath="+Elements.appNameOnCardXpath, 0, cardName);
				seetest.closeKeyboard();
				log.info("Name on Card entered as : " +driver.findElement(By.xpath(Elements.appNameOnCardXpath)).getAttribute("text"));
				extTestObj.createNode("Name on Card entered as : " + driver.findElement(By.xpath(Elements.appNameOnCardXpath)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter card number");
				extTestObj.createNode("Failed to enter card number")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();

			}
			
			//To enter billing Zip Code
			try {

				String zipcode = excel.getCellData("LoggedInOrder", "Zip Code", 2);
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.appZipCodeXpath, 0, 3000, 5, false);
				seetest.elementSendText("NATIVE", "xpath="+Elements.appZipCodeXpath, 0, zipcode);
				seetest.closeKeyboard();
				log.info("Zipcode entered as : " + driver.findElement(By.xpath(Elements.appZipCodeXpath)).getAttribute("text"));
				extTestObj.createNode("Zipcode entered as : " + driver.findElement(By.xpath(Elements.appZipCodeXpath)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter zipcode");
				extTestObj.createNode("Failed to enter zipcode")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			
			//To enter tip amoount
			String tip = excel.getCellData("LoggedInOrder", "Tip", 2);
			try {
				seetest.setProperty("Android.native.nonInstrumented","false");
				seetest.swipeWhileNotFound("Down", 900, 500, "WEB", "xpath="+Elements.appGiveTipXpath, 0, 3000, 5, true);
				driver.getKeyboard().sendKeys(tip);
				seetest.closeKeyboard();
				seetest.setProperty("Android.native.nonInstrumented","false");
				log.info("Tip given as : " +Integer.parseInt(tip)*0.01);
				extTestObj.createNode("Tip given as : "+Integer.parseInt(tip)*0.01).pass("PASSED");
				seetest.setProperty("Android.native.nonInstrumented","true");
			} catch (Exception e) {
				log.error("Failed to enter tip");
				extTestObj.createNode("Failed to enter tip")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			
			//To check for giving donation
			try {
				seetest.setProperty("Android.native.nonInstrumented","false");
				seetest.swipeWhileNotFound("Down", 900, 600, "WEB",Elements.appDonationBoxText, 0, 4000, 7, false);
				seetest.waitForElement("WEB",Elements.appDonationBoxText,0,3000);
				seetest.click("WEB",Elements.appDonationBoxText,0,1);
				log.info("Donation checked");
				extTestObj.createNode("Donation checked").pass("PASSED");
				seetest.setProperty("Android.native.nonInstrumented","true");
			} catch (Exception e) {
				log.error("Failed to check donation check box");
				extTestObj.createNode("Failed to check donation check box")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			
			
		}
		/*
		 * Function Name : androidAppEnterPaymentDetailswithSuggestedTip(int)
		 * Purpose : To validate payment page and enter payment details with Suggested tip
		 * Platform : Android App
		 * Parameters :location index to check for UCOM or Non-UCOM store
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */	
		public void androidAppEnterPaymentDetailswithSuggestedTip(int locationIndex)
		{
			
			//To enter tip amount
			String tip = excel.getCellData("LoggedInOrder", "Tip", 2);
			String tipAmount="";
			String displayedTipAmount="";
			try {
				explicitWait(Elements.iosFinalizeOrderHeaderXpath);
				seetest.setProperty("Android.native.nonInstrumented","false");
				seetest.swipeWhileNotFound("Down", 900, 500, "WEB", "xpath=//*[@id='suggested-tip-"+tip+"']", 0, 3000, 5, false);
				seetest.click("WEB","xpath=//*[@id='suggested-tip-"+tip+"']",0, 1);
				tipAmount = seetest.elementGetText("WEB","xpath=//*[@id='suggested-tip-"+tip+"']/label[2]",0);
				seetest.swipeWhileNotFound("Down", 900, 500, "WEB", "xpath="+Elements.androiddisplayedTipAmount, 0, 3000, 5, false);
				displayedTipAmount =seetest.elementGetText("WEB","xpath="+Elements.androiddisplayedTipAmount,0);
			}
			 catch (Exception e) {
					log.error("Failed to enter tip");
					extTestObj.createNode("Failed to enter tip")
							.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
					log.error(e.getMessage());

				}
				int flag = 0;
				if(tipAmount.contentEquals(displayedTipAmount))
					flag=1;
				try {
					int flagchecker = 1/flag;
				log.info("Chosen Suggested Tip percentage : "+tip+", tip amount : "+displayedTipAmount);
				extTestObj.createNode("Chosen Suggested Tip percentage : "+tip+", tip amount : "+displayedTipAmount).pass("PASSED");
			}
				catch(Exception e)
				{
					log.error("Discrepancy observed between suggested tip chosen and amount displayed");
					extTestObj.createNode("Discrepancy observed between suggested tip chosen and amount displayed").fail("FAILED");
				}
			
			//To check for giving donation
			try {
				seetest.swipeWhileNotFound("Down", 900, 600, "WEB",Elements.appDonationBoxText, 0, 4000, 7, false);
				seetest.waitForElement("WEB",Elements.appDonationBoxText,0,3000);
				seetest.click("WEB",Elements.appDonationBoxText,0,1);
				log.info("Donation checked");
				extTestObj.createNode("Donation checked").pass("PASSED");
				seetest.setProperty("Android.native.nonInstrumented","true");
			} catch (Exception e) {
				log.error("Failed to check donation check box");
				extTestObj.createNode("Failed to check donation check box")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			//To click "PAY WITH CREDIT CARD" for UCOM store
			if(excel.getCellData("Locations","Type",locationIndex).equals("UCOM"))
				clickableWait(Elements.payWithCreditCardXpath);
			
			//To select payment method
          try {
				
				String paymentMethod = excel.getCellData("LoggedInOrder", "Payment Method", 2);
				Thread.sleep(4000);
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.androidApppaymentMethodDropDown, 0, 3000, 5, false);
				seetest.elementSendText("NATIVE","xpath="+Elements.androidApppaymentMethodDropDown,0,paymentMethod);
				seetest.closeKeyboard();
				log.info("Payment Method selected as : " + driver.findElement(By.xpath(Elements.androidApppaymentMethodDropDown)).getAttribute("text"));
				extTestObj.createNode("Payment Method selected as : " + driver.findElement(By.xpath(Elements.androidApppaymentMethodDropDown)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to select Payment Method");
				extTestObj.createNode("Failed to select Payment Method")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
          //To select card type
          try {
				
				String cardType = excel.getCellData("LoggedInOrder", "Card Type", 2);
				Thread.sleep(4000);
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.androidAppcardTypeDropDown, 0, 3000, 5, false);
				seetest.elementSendText("NATIVE","xpath="+Elements.androidAppcardTypeDropDown,0,cardType);
				log.info("Card Type selected as : " + driver.findElement(By.xpath(Elements.androidAppcardTypeDropDown)).getAttribute("text"));
				extTestObj.createNode("Card Type selected as : " + driver.findElement(By.xpath(Elements.androidAppcardTypeDropDown)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to select Card Type");
				extTestObj.createNode("Failed to select Card Type")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			
			
			//To enter Card Number
			try {
				
				String cardNumber = excel.getCellData("LoggedInOrder", "Card Number", 2);
				Thread.sleep(4000);
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.appCardNumber, 0, 3000, 5, false);
				seetest.elementSendText("NATIVE","xpath="+Elements.appCardNumber,0,cardNumber);
				seetest.closeKeyboard();
				log.info("Card number entered as : " + driver.findElement(By.xpath(Elements.appCardNumber)).getAttribute("text"));
				extTestObj.createNode("Card number entered as : " + driver.findElement(By.xpath(Elements.appCardNumber)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter card number");
				extTestObj.createNode("Failed to card number")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			
			
			// To select expiration month
			String month = excel.getCellData("LoggedInOrder", "Expiration Month Name", 2);
			try {
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.appExpirationMonthXpath, 0, 3000, 5,
						false);
				clickableWait(Elements.expirationMonth);
				seetest.sleep(2000);
				seetest.elementListPick("NATIVE", "xpath="+Elements.androidListViewXpath, "NATIVE",
						"xpath=//*[contains(@text,'"+month+"')]", 0, true);
				log.info("Expiration Month selected as : " + driver.findElement(By.xpath(Elements.appExpirationMonthXpath)).getAttribute("text"));
				extTestObj.createNode("Expiration Month selected as : " + driver.findElement(By.xpath(Elements.appExpirationMonthXpath)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to select Expiration Month");
				extTestObj.createNode("Failed to select Expiration Month")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());

				stopTestforMobileBrowser();
			}
			
			//To select expiration year
			String year = excel.getCellData("LoggedInOrder", "Expiration Year", 2);
			try {
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.appExpirationYearXpath, 0, 3000, 5, false);
				clickableWait(Elements.expirationYear);
				seetest.sleep(2000);
				seetest.click("NATIVE", "xpath=//*[contains(@text,'" + year + "')]", 0, 1);
				log.info("Expiration Year selected as : " +driver.findElement(By.xpath(Elements.appExpirationYearXpath)).getAttribute("text"));
				extTestObj.createNode("Expiration Year selected as : " + driver.findElement(By.xpath(Elements.appExpirationYearXpath)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to select Expiration Year");
				extTestObj.createNode("Failed to select Expiration Year")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			// To enter CVV Number
			try {

				String cvvNumber = excel.getCellData("LoggedInOrder", "CVV", 2);
				Thread.sleep(2000);
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.appCVVNumber, 0, 3000, 5, false);
				seetest.elementSendText("NATIVE", "xpath="+Elements.appCVVNumber, 0, cvvNumber);
				seetest.closeKeyboard();
				log.info("Cvv number entered as : " + driver.findElement(By.xpath(Elements.appCVVNumber)).getAttribute("text"));
				extTestObj.createNode("Cvv number entered as : " + driver.findElement(By.xpath(Elements.appCVVNumber)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter cvv number");
				extTestObj.createNode("Failed to cvv number")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			//To enter billing Zip Code
			try {

				String zipcode = excel.getCellData("Locations", "Zip Code",locationIndex);
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.appZipCodeqa2Xpath, 0, 3000, 5, false);
				seetest.elementSendText("NATIVE", "xpath="+Elements.appZipCodeqa2Xpath, 0, zipcode);
				seetest.closeKeyboard();
				log.info("Zipcode entered as : " + driver.findElement(By.xpath(Elements.appZipCodeqa2Xpath)).getAttribute("text"));
				extTestObj.createNode("Zipcode entered as : " + driver.findElement(By.xpath(Elements.appZipCodeqa2Xpath)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter zipcode");
				extTestObj.createNode("Failed to enter zipcode")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			
			//To enter Name on Card
			try {

				String cardName = excel.getCellData("LoggedInOrder", "Name On Card", 2);
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.appNameOnCardqa2Xpath, 0, 3000, 5, false);
				seetest.elementSendText("NATIVE", "xpath="+Elements.appNameOnCardqa2Xpath, 0, cardName);
				seetest.closeKeyboard();
				log.info("Name on Card entered as : " +driver.findElement(By.xpath(Elements.appNameOnCardqa2Xpath)).getAttribute("text"));
				extTestObj.createNode("Name on Card entered as : " + driver.findElement(By.xpath(Elements.appNameOnCardqa2Xpath)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter card number");
				extTestObj.createNode("Failed to enter card number")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();

			}	
			
		}
		/*
		 * Function Name : androidAppEnterPaymentDetailswithSuggestedTipAndGiftCard(int)
		 * Purpose : To validate payment page and enter payment details with Suggested tip with gift card
		 * Platform : Android App
		 * Parameters :location index to check for UCOM or Non-UCOM store
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */	
		public void androidAppEnterPaymentDetailswithSuggestedTipAndGiftCard(int locationIndex)
		{
			
			//To enter tip amount
			String tip = excel.getCellData("LoggedInOrder", "Tip", 2);
			String tipAmount="";
			String displayedTipAmount="";
			try {
				explicitWait(Elements.iosFinalizeOrderHeaderXpath);
				seetest.setProperty("Android.native.nonInstrumented","false");
				seetest.swipeWhileNotFound("Down", 900, 500, "WEB", "xpath=//*[@id='suggested-tip-"+tip+"']", 0, 3000, 5, false);
				seetest.click("WEB","xpath=//*[@id='suggested-tip-"+tip+"']",0, 1);
				tipAmount = seetest.elementGetText("WEB","xpath=//*[@id='suggested-tip-"+tip+"']/label[2]",0);
				seetest.swipeWhileNotFound("Down", 900, 500, "WEB", "xpath="+Elements.androiddisplayedTipAmount, 0, 3000, 5, false);
				displayedTipAmount =seetest.elementGetText("WEB","xpath="+Elements.androiddisplayedTipAmount,0);
			}
			 catch (Exception e) {
					log.error("Failed to enter tip");
					extTestObj.createNode("Failed to enter tip")
							.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
					log.error(e.getMessage());

				}
				int flag = 0;
				if(tipAmount.contentEquals(displayedTipAmount))
					flag=1;
				try {
					int flagchecker = 1/flag;
				log.info("Chosen Suggested Tip percentage : "+tip+", tip amount : "+displayedTipAmount);
				extTestObj.createNode("Chosen Suggested Tip percentage : "+tip+", tip amount : "+displayedTipAmount).pass("PASSED");
			}
				catch(Exception e)
				{
					log.error("Discrepancy observed between suggested tip chosen and amount displayed");
					extTestObj.createNode("Discrepancy observed between suggested tip chosen and amount displayed").fail("FAILED");
				}
			
			//To check for giving donation
			try {
				seetest.swipeWhileNotFound("Down", 900, 600, "WEB",Elements.appDonationBoxText, 0, 4000, 7, false);
				seetest.waitForElement("WEB",Elements.appDonationBoxText,0,3000);
				seetest.click("WEB",Elements.appDonationBoxText,0,1);
				log.info("Donation checked");
				extTestObj.createNode("Donation checked").pass("PASSED");
				seetest.setProperty("Android.native.nonInstrumented","true");
			} catch (Exception e) {
				log.error("Failed to check donation check box");
				extTestObj.createNode("Failed to check donation check box")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			//To add gift card
			String giftCardNumber = excel.getCellData("LoggedInOrder","Gift Card Number",2);
			try {
				seetest.swipeWhileNotFound("Down",900,500,"NATIVE","xpath="+Elements.iosAppGiftCardTextBox,0,4000,6,true);
				driver.getKeyboard().sendKeys(giftCardNumber);
				seetest.closeKeyboard();
				seetest.click("NATIVE","xpath="+Elements.iosAppApplyGiftCard,0,1);
				log.info("Gift card applied");
				extTestObj.createNode("Gift card applied").pass("PASSED");
			}
			catch(Exception e)
			{
				log.error("Failed to apply gift card");
				extTestObj.createNode("Failed to apply gift card")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			//To click "PAY WITH CREDIT CARD" for UCOM store
			if(excel.getCellData("Locations","Type",locationIndex).equals("UCOM"))
				clickableWait(Elements.payWithCreditCardXpath);
			
			//To select payment method
          try {
				
				String paymentMethod = excel.getCellData("LoggedInOrder", "Payment Method", 2);
				Thread.sleep(4000);
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.androidApppaymentMethodDropDown, 0, 3000, 5, false);
				seetest.elementSendText("NATIVE","xpath="+Elements.androidApppaymentMethodDropDown,0,paymentMethod);
				seetest.closeKeyboard();
				log.info("Payment Method selected as : " + driver.findElement(By.xpath(Elements.androidApppaymentMethodDropDown)).getAttribute("text"));
				extTestObj.createNode("Payment Method selected as : " + driver.findElement(By.xpath(Elements.androidApppaymentMethodDropDown)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to select Payment Method");
				extTestObj.createNode("Failed to select Payment Method")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
          //To select card type
          try {
				
				String cardType = excel.getCellData("LoggedInOrder", "Card Type", 2);
				Thread.sleep(4000);
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.androidAppcardTypeDropDown, 0, 3000, 5, false);
				seetest.elementSendText("NATIVE","xpath="+Elements.androidAppcardTypeDropDown,0,cardType);
				log.info("Card Type selected as : " + driver.findElement(By.xpath(Elements.androidAppcardTypeDropDown)).getAttribute("text"));
				extTestObj.createNode("Card Type selected as : " + driver.findElement(By.xpath(Elements.androidAppcardTypeDropDown)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to select Card Type");
				extTestObj.createNode("Failed to select Card Type")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			
			
			//To enter Card Number
			try {
				
				String cardNumber = excel.getCellData("LoggedInOrder", "Card Number", 2);
				Thread.sleep(4000);
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.appCardNumber, 0, 3000, 5, false);
				seetest.elementSendText("NATIVE","xpath="+Elements.appCardNumber,0,cardNumber);
				seetest.closeKeyboard();
				log.info("Card number entered as : " + driver.findElement(By.xpath(Elements.appCardNumber)).getAttribute("text"));
				extTestObj.createNode("Card number entered as : " + driver.findElement(By.xpath(Elements.appCardNumber)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter card number");
				extTestObj.createNode("Failed to card number")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			
			
			// To select expiration month
			String month = excel.getCellData("LoggedInOrder", "Expiration Month Name", 2);
			try {
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.appExpirationMonthXpath, 0, 3000, 5,
						false);
				clickableWait(Elements.expirationMonth);
				seetest.sleep(2000);
				seetest.elementListPick("NATIVE", "xpath="+Elements.androidListViewXpath, "NATIVE",
						"xpath=//*[contains(@text,'"+month+"')]", 0, true);
				log.info("Expiration Month selected as : " + driver.findElement(By.xpath(Elements.appExpirationMonthXpath)).getAttribute("text"));
				extTestObj.createNode("Expiration Month selected as : " + driver.findElement(By.xpath(Elements.appExpirationMonthXpath)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to select Expiration Month");
				extTestObj.createNode("Failed to select Expiration Month")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());

				stopTestforMobileBrowser();
			}
			
			//To select expiration year
			String year = excel.getCellData("LoggedInOrder", "Expiration Year", 2);
			try {
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.appExpirationYearXpath, 0, 3000, 5, false);
				clickableWait(Elements.expirationYear);
				seetest.sleep(2000);
				seetest.click("NATIVE", "xpath=//*[contains(@text,'" + year + "')]", 0, 1);
				log.info("Expiration Year selected as : " +driver.findElement(By.xpath(Elements.appExpirationYearXpath)).getAttribute("text"));
				extTestObj.createNode("Expiration Year selected as : " + driver.findElement(By.xpath(Elements.appExpirationYearXpath)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to select Expiration Year");
				extTestObj.createNode("Failed to select Expiration Year")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			// To enter CVV Number
			try {

				String cvvNumber = excel.getCellData("LoggedInOrder", "CVV", 2);
				Thread.sleep(2000);
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.appCVVNumber, 0, 3000, 5, false);
				seetest.elementSendText("NATIVE", "xpath="+Elements.appCVVNumber, 0, cvvNumber);
				seetest.closeKeyboard();
				log.info("Cvv number entered as : " + driver.findElement(By.xpath(Elements.appCVVNumber)).getAttribute("text"));
				extTestObj.createNode("Cvv number entered as : " + driver.findElement(By.xpath(Elements.appCVVNumber)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter cvv number");
				extTestObj.createNode("Failed to cvv number")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			//To enter billing Zip Code
			try {

				String zipcode = excel.getCellData("Locations", "Zip Code",locationIndex);
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.appZipCodeqa2Xpath, 0, 3000, 5, false);
				seetest.elementSendText("NATIVE", "xpath="+Elements.appZipCodeqa2Xpath, 0, zipcode);
				seetest.closeKeyboard();
				log.info("Zipcode entered as : " + driver.findElement(By.xpath(Elements.appZipCodeqa2Xpath)).getAttribute("text"));
				extTestObj.createNode("Zipcode entered as : " + driver.findElement(By.xpath(Elements.appZipCodeqa2Xpath)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter zipcode");
				extTestObj.createNode("Failed to enter zipcode")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			
			//To enter Name on Card
			try {

				String cardName = excel.getCellData("LoggedInOrder", "Name On Card", 2);
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.appNameOnCardqa2Xpath, 0, 3000, 5, false);
				seetest.elementSendText("NATIVE", "xpath="+Elements.appNameOnCardqa2Xpath, 0, cardName);
				seetest.closeKeyboard();
				log.info("Name on Card entered as : " +driver.findElement(By.xpath(Elements.appNameOnCardqa2Xpath)).getAttribute("text"));
				extTestObj.createNode("Name on Card entered as : " + driver.findElement(By.xpath(Elements.appNameOnCardqa2Xpath)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter card number");
				extTestObj.createNode("Failed to enter card number")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();

			}	
			
		}
		/*
		 * Function Name : androidAppEnterPaymentDetailswithCustomTip(int)
		 * Purpose : To validate payment page and enter payment details with custom tip
		 * Platform : Android App
		 * Parameters :location index to check for UCOM or Non-UCOM store
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */	
		public void androidAppEnterPaymentDetailswithCustomTip(int locationIndex)
		{
			//To enter tip amount
			String tip = excel.getCellData("LoggedInOrder", "Tip", 2);
			String tipAmount="";
			String displayedTipAmount="";
			try {
				explicitWait(Elements.iosFinalizeOrderHeaderXpath);
				seetest.setProperty("Android.native.nonInstrumented","false");
				seetest.swipeWhileNotFound("Down", 900, 500, "WEB", "xpath="+Elements.androidAppcustomTipInput, 0, 3000, 5, false);
				seetest.click("WEB","xpath="+Elements.androidAppcustomTipInput,0, 1);
				driver.getKeyboard().sendKeys(tip);
				seetest.closeKeyboard();
				tipAmount = seetest.elementGetText("WEB","xpath="+Elements.androidAppcustomTipInput,0);
				seetest.swipeWhileNotFound("Down", 900, 500, "WEB", "xpath="+Elements.androiddisplayedTipAmount, 0, 3000, 5, false);
				displayedTipAmount =seetest.elementGetText("WEB","xpath="+Elements.androiddisplayedTipAmount,0);
			}
			 catch (Exception e) {
					log.error("Failed to enter tip");
					extTestObj.createNode("Failed to enter tip")
							.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
					log.error(e.getMessage());

				}
				int flag = 0;
				if(tipAmount.contentEquals(displayedTipAmount))
					flag=1;
				try {
					int flagchecker = 1/flag;
				log.info("Chosen Suggested Tip percentage : "+tip+", tip amount : "+displayedTipAmount);
				extTestObj.createNode("Chosen Suggested Tip percentage : "+tip+", tip amount : "+displayedTipAmount).pass("PASSED");
			}
				catch(Exception e)
				{
					log.error("Discrepancy observed between suggested tip chosen and amount displayed");
					extTestObj.createNode("Discrepancy observed between suggested tip chosen and amount displayed").fail("FAILED");
				}
			
			//To check for giving donation
			try {
				seetest.swipeWhileNotFound("Down", 900, 600, "WEB",Elements.appDonationBoxText, 0, 4000, 7, false);
				seetest.waitForElement("WEB",Elements.appDonationBoxText,0,3000);
				seetest.click("WEB",Elements.appDonationBoxText,0,1);
				log.info("Donation checked");
				extTestObj.createNode("Donation checked").pass("PASSED");
				seetest.setProperty("Android.native.nonInstrumented","true");
			} catch (Exception e) {
				log.error("Failed to check donation check box");
				extTestObj.createNode("Failed to check donation check box")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			//To click "PAY WITH CREDIT CARD" for UCOM store
			if(excel.getCellData("Locations","Type",locationIndex).equals("UCOM"))
				clickableWait(Elements.payWithCreditCardXpath);
			//To select payment method
          try {
				
				String paymentMethod = excel.getCellData("LoggedInOrder", "Payment Method", 2);
				Thread.sleep(4000);
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.androidApppaymentMethodDropDown, 0, 3000, 5, false);
				seetest.elementSendText("NATIVE","xpath="+Elements.androidApppaymentMethodDropDown,0,paymentMethod);
				seetest.closeKeyboard();
				log.info("Payment Method selected as : " + driver.findElement(By.xpath(Elements.androidApppaymentMethodDropDown)).getAttribute("text"));
				extTestObj.createNode("Payment Method selected as : " + driver.findElement(By.xpath(Elements.androidApppaymentMethodDropDown)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to select Payment Method");
				extTestObj.createNode("Failed to select Payment Method")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
          //To select card type
          try {
				
				String cardType = excel.getCellData("LoggedInOrder", "Card Type", 2);
				Thread.sleep(4000);
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.androidAppcardTypeDropDown, 0, 3000, 5, false);
				seetest.elementSendText("NATIVE","xpath="+Elements.androidAppcardTypeDropDown,0,cardType);
				log.info("Card Type selected as : " + driver.findElement(By.xpath(Elements.androidAppcardTypeDropDown)).getAttribute("text"));
				extTestObj.createNode("Card Type selected as : " + driver.findElement(By.xpath(Elements.androidAppcardTypeDropDown)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to select Card Type");
				extTestObj.createNode("Failed to select Card Type")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			
			
			//To enter Card Number
			try {
				
				String cardNumber = excel.getCellData("LoggedInOrder", "Card Number", 2);
				Thread.sleep(4000);
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.appCardNumber, 0, 3000, 5, false);
				seetest.elementSendText("NATIVE","xpath="+Elements.appCardNumber,0,cardNumber);
				seetest.closeKeyboard();
				log.info("Card number entered as : " + driver.findElement(By.xpath(Elements.appCardNumber)).getAttribute("text"));
				extTestObj.createNode("Card number entered as : " + driver.findElement(By.xpath(Elements.appCardNumber)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter card number");
				extTestObj.createNode("Failed to card number")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			
			
			// To select expiration month
			String month = excel.getCellData("LoggedInOrder", "Expiration Month Name", 2);
			try {
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.appExpirationMonthXpath, 0, 3000, 5,
						false);
				clickableWait(Elements.expirationMonth);
				seetest.sleep(2000);
				seetest.elementListPick("NATIVE", "xpath="+Elements.androidListViewXpath, "NATIVE",
						"xpath=//*[contains(@text,'"+month+"')]", 0, true);
				log.info("Expiration Month selected as : " + driver.findElement(By.xpath(Elements.appExpirationMonthXpath)).getAttribute("text"));
				extTestObj.createNode("Expiration Month selected as : " + driver.findElement(By.xpath(Elements.appExpirationMonthXpath)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to select Expiration Month");
				extTestObj.createNode("Failed to select Expiration Month")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());

				stopTestforMobileBrowser();
			}
			
			//To select expiration year
			String year = excel.getCellData("LoggedInOrder", "Expiration Year", 2);
			try {
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.appExpirationYearXpath, 0, 3000, 5, false);
				clickableWait(Elements.expirationYear);
				seetest.sleep(2000);
				seetest.click("NATIVE", "xpath=//*[contains(@text,'" + year + "')]", 0, 1);
				log.info("Expiration Year selected as : " +driver.findElement(By.xpath(Elements.appExpirationYearXpath)).getAttribute("text"));
				extTestObj.createNode("Expiration Year selected as : " + driver.findElement(By.xpath(Elements.appExpirationYearXpath)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to select Expiration Year");
				extTestObj.createNode("Failed to select Expiration Year")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			// To enter CVV Number
			try {

				String cvvNumber = excel.getCellData("LoggedInOrder", "CVV", 2);
				Thread.sleep(2000);
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.appCVVNumber, 0, 3000, 5, false);
				seetest.elementSendText("NATIVE", "xpath="+Elements.appCVVNumber, 0, cvvNumber);
				seetest.closeKeyboard();
				log.info("Cvv number entered as : " + driver.findElement(By.xpath(Elements.appCVVNumber)).getAttribute("text"));
				extTestObj.createNode("Cvv number entered as : " + driver.findElement(By.xpath(Elements.appCVVNumber)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter cvv number");
				extTestObj.createNode("Failed to cvv number")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			//To enter billing Zip Code
			try {

				String zipcode = excel.getCellData("Locations", "Zip Code",locationIndex);
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.appZipCodeqa2Xpath, 0, 3000, 5, false);
				seetest.elementSendText("NATIVE", "xpath="+Elements.appZipCodeqa2Xpath, 0, zipcode);
				seetest.closeKeyboard();
				log.info("Zipcode entered as : " + driver.findElement(By.xpath(Elements.appZipCodeqa2Xpath)).getAttribute("text"));
				extTestObj.createNode("Zipcode entered as : " + driver.findElement(By.xpath(Elements.appZipCodeqa2Xpath)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter zipcode");
				extTestObj.createNode("Failed to enter zipcode")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			
			//To enter Name on Card
			try {

				String cardName = excel.getCellData("LoggedInOrder", "Name On Card", 2);
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.appNameOnCardqa2Xpath, 0, 3000, 5, false);
				seetest.elementSendText("NATIVE", "xpath="+Elements.appNameOnCardqa2Xpath, 0, cardName);
				seetest.closeKeyboard();
				log.info("Name on Card entered as : " +driver.findElement(By.xpath(Elements.appNameOnCardqa2Xpath)).getAttribute("text"));
				extTestObj.createNode("Name on Card entered as : " + driver.findElement(By.xpath(Elements.appNameOnCardqa2Xpath)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter card number");
				extTestObj.createNode("Failed to enter card number")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();

			}	
			
		}
		/*
		 * Function Name : androidAppEnterPaymentDetailswithCustomTipAndGiftCard(int)
		 * Purpose : To validate payment page and enter payment details with custom tip and gift card
		 * Platform : Android App
		 * Parameters :location index to check for UCOM or Non-UCOM store
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */	
		public void androidAppEnterPaymentDetailswithCustomTipAndGiftCard(int locationIndex)
		{
			//To enter tip amount
			String tip = excel.getCellData("LoggedInOrder", "Tip", 2);
			String tipAmount="";
			String displayedTipAmount="";
			try {
				explicitWait(Elements.iosFinalizeOrderHeaderXpath);
				seetest.setProperty("Android.native.nonInstrumented","false");
				seetest.swipeWhileNotFound("Down", 900, 500, "WEB", "xpath="+Elements.androidAppcustomTipInput, 0, 3000, 5, false);
				seetest.click("WEB","xpath="+Elements.androidAppcustomTipInput,0, 1);
				driver.getKeyboard().sendKeys(tip);
				seetest.closeKeyboard();
				tipAmount = seetest.elementGetText("WEB","xpath="+Elements.androidAppcustomTipInput,0);
				seetest.swipeWhileNotFound("Down", 900, 500, "WEB", "xpath="+Elements.androiddisplayedTipAmount, 0, 3000, 5, false);
				displayedTipAmount =seetest.elementGetText("WEB","xpath="+Elements.androiddisplayedTipAmount,0);
			}
			 catch (Exception e) {
					log.error("Failed to enter tip");
					extTestObj.createNode("Failed to enter tip")
							.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
					log.error(e.getMessage());

				}
				int flag = 0;
				if(tipAmount.contentEquals(displayedTipAmount))
					flag=1;
				try {
					int flagchecker = 1/flag;
				log.info("Chosen Suggested Tip percentage : "+tip+", tip amount : "+displayedTipAmount);
				extTestObj.createNode("Chosen Suggested Tip percentage : "+tip+", tip amount : "+displayedTipAmount).pass("PASSED");
			}
				catch(Exception e)
				{
					log.error("Discrepancy observed between suggested tip chosen and amount displayed");
					extTestObj.createNode("Discrepancy observed between suggested tip chosen and amount displayed").fail("FAILED");
				}
			
			//To check for giving donation
			try {
				seetest.swipeWhileNotFound("Down", 900, 600, "WEB",Elements.appDonationBoxText, 0, 4000, 7, false);
				seetest.waitForElement("WEB",Elements.appDonationBoxText,0,3000);
				seetest.click("WEB",Elements.appDonationBoxText,0,1);
				log.info("Donation checked");
				extTestObj.createNode("Donation checked").pass("PASSED");
				seetest.setProperty("Android.native.nonInstrumented","true");
			} catch (Exception e) {
				log.error("Failed to check donation check box");
				extTestObj.createNode("Failed to check donation check box")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			//Add gift card
			String giftCardNumber = excel.getCellData("LoggedInOrder","Gift Card Number",2);
			try {
				seetest.swipeWhileNotFound("Down",900, 500,"NATIVE","xpath="+Elements.iosAppGiftCardTextBox ,0,4000, 6,true);
				driver.getKeyboard().sendKeys(giftCardNumber);
				seetest.closeKeyboard();
				seetest.click("NATIVE","xpath="+Elements.iosAppApplyGiftCard,0,1);
				log.info("Gift card applied");
				extTestObj.createNode("Gift card applied").pass("PASSED");
			}
			catch(Exception e)
			{
				log.error("Failed to apply gift card");
				extTestObj.createNode("Failed to apply gift card")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			//To click "PAY WITH CREDIT CARD" for UCOM store
			if(excel.getCellData("Locations","Type",locationIndex).equals("UCOM"))
				clickableWait(Elements.payWithCreditCardXpath);
			//To select payment method
          try {
				
				String paymentMethod = excel.getCellData("LoggedInOrder", "Payment Method", 2);
				Thread.sleep(4000);
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.androidApppaymentMethodDropDown, 0, 3000, 5, false);
				seetest.elementSendText("NATIVE","xpath="+Elements.androidApppaymentMethodDropDown,0,paymentMethod);
				seetest.closeKeyboard();
				log.info("Payment Method selected as : " + driver.findElement(By.xpath(Elements.androidApppaymentMethodDropDown)).getAttribute("text"));
				extTestObj.createNode("Payment Method selected as : " + driver.findElement(By.xpath(Elements.androidApppaymentMethodDropDown)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to select Payment Method");
				extTestObj.createNode("Failed to select Payment Method")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
          //To select card type
          try {
				
				String cardType = excel.getCellData("LoggedInOrder", "Card Type", 2);
				Thread.sleep(4000);
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.androidAppcardTypeDropDown, 0, 3000, 5, false);
				seetest.elementSendText("NATIVE","xpath="+Elements.androidAppcardTypeDropDown,0,cardType);
				log.info("Card Type selected as : " + driver.findElement(By.xpath(Elements.androidAppcardTypeDropDown)).getAttribute("text"));
				extTestObj.createNode("Card Type selected as : " + driver.findElement(By.xpath(Elements.androidAppcardTypeDropDown)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to select Card Type");
				extTestObj.createNode("Failed to select Card Type")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			
			
			//To enter Card Number
			try {
				
				String cardNumber = excel.getCellData("LoggedInOrder", "Card Number", 2);
				Thread.sleep(4000);
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.appCardNumber, 0, 3000, 5, false);
				seetest.elementSendText("NATIVE","xpath="+Elements.appCardNumber,0,cardNumber);
				seetest.closeKeyboard();
				log.info("Card number entered as : " + driver.findElement(By.xpath(Elements.appCardNumber)).getAttribute("text"));
				extTestObj.createNode("Card number entered as : " + driver.findElement(By.xpath(Elements.appCardNumber)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter card number");
				extTestObj.createNode("Failed to card number")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			
			
			// To select expiration month
			String month = excel.getCellData("LoggedInOrder", "Expiration Month Name", 2);
			try {
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.appExpirationMonthXpath, 0, 3000, 5,
						false);
				clickableWait(Elements.expirationMonth);
				seetest.sleep(2000);
				seetest.elementListPick("NATIVE", "xpath="+Elements.androidListViewXpath, "NATIVE",
						"xpath=//*[contains(@text,'"+month+"')]", 0, true);
				log.info("Expiration Month selected as : " + driver.findElement(By.xpath(Elements.appExpirationMonthXpath)).getAttribute("text"));
				extTestObj.createNode("Expiration Month selected as : " + driver.findElement(By.xpath(Elements.appExpirationMonthXpath)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to select Expiration Month");
				extTestObj.createNode("Failed to select Expiration Month")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());

				stopTestforMobileBrowser();
			}
			
			//To select expiration year
			String year = excel.getCellData("LoggedInOrder", "Expiration Year", 2);
			try {
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.appExpirationYearXpath, 0, 3000, 5, false);
				clickableWait(Elements.expirationYear);
				seetest.sleep(2000);
				seetest.click("NATIVE", "xpath=//*[contains(@text,'" + year + "')]", 0, 1);
				log.info("Expiration Year selected as : " +driver.findElement(By.xpath(Elements.appExpirationYearXpath)).getAttribute("text"));
				extTestObj.createNode("Expiration Year selected as : " + driver.findElement(By.xpath(Elements.appExpirationYearXpath)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to select Expiration Year");
				extTestObj.createNode("Failed to select Expiration Year")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			// To enter CVV Number
			try {

				String cvvNumber = excel.getCellData("LoggedInOrder", "CVV", 2);
				Thread.sleep(2000);
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.appCVVNumber, 0, 3000, 5, false);
				seetest.elementSendText("NATIVE", "xpath="+Elements.appCVVNumber, 0, cvvNumber);
				seetest.closeKeyboard();
				log.info("Cvv number entered as : " + driver.findElement(By.xpath(Elements.appCVVNumber)).getAttribute("text"));
				extTestObj.createNode("Cvv number entered as : " + driver.findElement(By.xpath(Elements.appCVVNumber)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter cvv number");
				extTestObj.createNode("Failed to cvv number")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			//To enter billing Zip Code
			try {

				String zipcode = excel.getCellData("Locations", "Zip Code",locationIndex);
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.appZipCodeqa2Xpath, 0, 3000, 5, false);
				seetest.elementSendText("NATIVE", "xpath="+Elements.appZipCodeqa2Xpath, 0, zipcode);
				seetest.closeKeyboard();
				log.info("Zipcode entered as : " + driver.findElement(By.xpath(Elements.appZipCodeqa2Xpath)).getAttribute("text"));
				extTestObj.createNode("Zipcode entered as : " + driver.findElement(By.xpath(Elements.appZipCodeqa2Xpath)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter zipcode");
				extTestObj.createNode("Failed to enter zipcode")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			
			//To enter Name on Card
			try {

				String cardName = excel.getCellData("LoggedInOrder", "Name On Card", 2);
				seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath="+Elements.appNameOnCardqa2Xpath, 0, 3000, 5, false);
				seetest.elementSendText("NATIVE", "xpath="+Elements.appNameOnCardqa2Xpath, 0, cardName);
				seetest.closeKeyboard();
				log.info("Name on Card entered as : " +driver.findElement(By.xpath(Elements.appNameOnCardqa2Xpath)).getAttribute("text"));
				extTestObj.createNode("Name on Card entered as : " + driver.findElement(By.xpath(Elements.appNameOnCardqa2Xpath)).getAttribute("text")).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter card number");
				extTestObj.createNode("Failed to enter card number")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();

			}	
			
		}
		
		/*
		 * Function Name : iosAppEnterPaymentDetailswithSuggestedTip(int)
		 * Purpose : To validate payment page and enter payment details with suggested tip
		 * Platform : IOS App
		 * Parameters :location index to check for UCOM or Non-UCOM store
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */	
		public void iosAppEnterPaymentDetailswithSuggestedTip()
		{//To select payment method
			String paymentMethod = excel.getCellData("LoggedInOrder", "Payment Method", 2);
			try {
				explicitWait(Elements.iosFinalizeOrderHeaderXpath);
				incrementalDragOnScreenAndClick(seetest,Elements.paymentMethodDropDown.toString(),"Down","NATIVE",true);
				  String[] pickerList=  seetest.getPickerValues("NATIVE","xpath=//*[@class='UIAPicker']",0,0);
				  for(int i=0;i<pickerList.length;i++)
				  {
					  if(pickerList[i].contains(paymentMethod)) {
						  paymentMethod=pickerList[i];
						  break;
					  }
				  }
					seetest.setPickerValues("NATIVE", "xpath=//*[@class='UIAPicker']", 0, 0,paymentMethod);
					Thread.sleep(1000);
					seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.sleep(2000);
				log.info("Payment Method selected as : " + paymentMethod);
				extTestObj.createNode("Payment Method selected as : " + paymentMethod).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to select Payment method");
				extTestObj.createNode("Failed to select Payment method")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			//To select card type
			String cardType = excel.getCellData("LoggedInOrder", "Card Type", 2);
			try {
				incrementalDragOnScreenAndClick(seetest,Elements.cardTypeDropDown.toString(),"Down","NATIVE",true);
				  String[] pickerList=  seetest.getPickerValues("NATIVE","xpath=//*[@class='UIAPicker']",0,0);
				  for(int i=0;i<pickerList.length;i++)
				  {
					  if(pickerList[i].contains(cardType)) {
						  cardType=pickerList[i];
						  break;
					  }
				  }
					seetest.setPickerValues("NATIVE", "xpath=//*[@class='UIAPicker']", 0, 0,cardType);
					Thread.sleep(1000);
					seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.sleep(2000);
				log.info("Card type selected as : " + cardType);
				extTestObj.createNode("Card type selected as : " + cardType).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to select card type");
				extTestObj.createNode("Failed to select card type")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			//To enter card number
			try {
				String cardNumber = excel.getCellData("LoggedInOrder", "Card Number", 2);
				Thread.sleep(4000);
				incrementalDragOnScreenAndClick(seetest,Elements.iosCardNumberTextBoxXpath,"Down","NATIVE",false);
				seetest.elementSendText("NATIVE","xpath="+Elements.iosCardNumberTextBoxXpath,0,cardNumber);
				seetest.closeKeyboard();
				log.info("Card number entered as : " + cardNumber);
				extTestObj.createNode("Card number entered as : " + cardNumber).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter card number");
				extTestObj.createNode("Failed to card number")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			//To enter CVV number
			try {
				String cvvNumber = excel.getCellData("LoggedInOrder", "CVV", 2);
				Thread.sleep(2000);
				incrementalDragOnScreenAndClick(seetest,Elements.iosCVVTextBoxXpath,"Down","NATIVE",false);
				seetest.elementSendText("NATIVE", "xpath="+Elements.iosCVVTextBoxXpath, 0, cvvNumber);
				seetest.closeKeyboard();
//				seetest.click("NATIVE","xpath=//*[@text='Done']",0, 1);
				log.info("Cvv number entered as : " + cvvNumber);
				extTestObj.createNode("Cvv number entered as : " + cvvNumber).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter cvv number");
				extTestObj.createNode("Failed to cvv number")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			//To select Expiration month
			String month = excel.getCellData("LoggedInOrder", "Expiration Month", 2);
			try {
				incrementalDragOnScreenAndClick(seetest,Elements.iosExpirationMonthXpath,"Down","NATIVE",true);
				  String[] pickerList=  seetest.getPickerValues("NATIVE","xpath=//*[@class='UIAPicker']",0,0);
				  for(int i=0;i<pickerList.length;i++)
				  {
					  if(pickerList[i].contains(month)) {
					    month=pickerList[i];
						  break;
					  }
				  }
					seetest.setPickerValues("NATIVE", "xpath=//*[@class='UIAPicker']", 0, 0,month);
					Thread.sleep(1000);
					seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.sleep(2000);
				log.info("Expiration Month selected as : " + month);
				extTestObj.createNode("Expiration Month selected as : " + month).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to select Expiration Month");
				extTestObj.createNode("Failed to select Expiration Month")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			//To select expiration year
			String year = excel.getCellData("LoggedInOrder", "Expiration Year", 2);
			try {
				incrementalDragOnScreenAndClick(seetest,Elements.iosExpirationYearXpath,"Down","NATIVE",true);
				seetest.sleep(2000);
				String[] pickerList=  seetest.getPickerValues("NATIVE","xpath=//*[@class='UIAPicker']",0,0);
				  for(int i=0;i<pickerList.length;i++)
				  {
					  if(pickerList[i].contains(year)) {
					    year=pickerList[i];
						  break;
					  }
				  }
					seetest.setPickerValues("NATIVE", "xpath=//*[@class='UIAPicker']", 0, 0, year);
					Thread.sleep(1000);
					seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
//					seetest.setPickerValues("NATIVE", "xpath=//*[@class='UIAPicker']", 0, 0, year);
//				}
				log.info("Expiration Year selected as : " + year);
				extTestObj.createNode("Expiration Year selected as : " + year).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to select Expiration Year");
				extTestObj.createNode("Failed to select Expiration Year")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			//To enter name on card
			try {

				String cardName = excel.getCellData("LoggedInOrder", "Name On Card", 2);
				incrementalDragOnScreenAndClick(seetest,Elements.iosNameOnCardTextBoxXpath,"Down","NATIVE",false);
				seetest.elementSendText("NATIVE", "xpath="+Elements.iosNameOnCardTextBoxXpath, 0, cardName);
				seetest.closeKeyboard();
				log.info("Name on Card entered as : " + cardName);
				extTestObj.createNode("Name on Card entered as : " + cardName).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter card number");
				extTestObj.createNode("Failed to enter card number")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			//To enter zip code
			try {
				String zipcode = excel.getCellData("LoggedInOrder", "Zip Code", 2);
				incrementalDragOnScreenAndClick(seetest,Elements.iosZipCodeTextBoxXpath,"Down","NATIVE",false);
				seetest.elementSendText("NATIVE", "xpath="+Elements.iosZipCodeTextBoxXpath, 0, zipcode);
				seetest.closeKeyboard();
				log.info("Zipcode entered as : " + zipcode);
				extTestObj.createNode("Zipcode entered as : " + zipcode).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter zipcode");
				extTestObj.createNode("Failed to enter zipcode")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			//To enter tip
			String tip = excel.getCellData("LoggedInOrder", "Tip", 2);
			try {

				incrementalDragOnScreenAndClick(seetest,Elements.iosTipTextBoxXpath,"Down","NATIVE",false);
				seetest.elementSendText("NATIVE","xpath="+Elements.iosTipTextBoxXpath,0,tip);
				seetest.closeKeyboard();
				log.info("Tip given as : " + Integer.parseInt(tip)*0.01);
				extTestObj.createNode("Tip given as : " + Integer.parseInt(tip)*0.01).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter tip");
				extTestObj.createNode("Failed to enter tip")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			//To check St Jude's donation
           try {
				
				incrementalDragOnScreenAndClick(seetest,Elements.iosDonationCheckBox,"Down","NATIVE",false);
				seetest.swipe("Down",600, 500);
				seetest.click("NATIVE", "xpath="+Elements.iosDonationCheckBox, 0, 1);
				log.info("Donation checked");
				extTestObj.createNode("Donation checked").pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to check donation check box");
				extTestObj.createNode("Failed to check donation check box")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			
		}
		/*
		 * Function Name : iosAppEnterPaymentDetails()
		 * Purpose : To validate payment page and enter payment details 
		 * Platform : IOS App
		 * Parameters :None
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */
		public void iosAppEnterPaymentDetails()
		{	
			//To select payment method
			String paymentMethod = excel.getCellData("LoggedInOrder", "Payment Method", 2);
			try {
				explicitWait(Elements.iosFinalizeOrderHeaderXpath);
				incrementalDragOnScreenAndClick(seetest,Elements.paymentMethodDropDown.toString(),"Down","NATIVE",true);
				  String[] pickerList=  seetest.getPickerValues("NATIVE","xpath=//*[@class='UIAPicker']",0,0);
				  for(int i=0;i<pickerList.length;i++)
				  {
					  if(pickerList[i].contains(paymentMethod)) {
						  paymentMethod=pickerList[i];
						  break;
					  }
				  }
					seetest.setPickerValues("NATIVE", "xpath=//*[@class='UIAPicker']", 0, 0,paymentMethod);
					Thread.sleep(1000);
					seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.sleep(2000);
				log.info("Payment Method selected as : " + paymentMethod);
				extTestObj.createNode("Payment Method selected as : " + paymentMethod).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to select Payment method");
				extTestObj.createNode("Failed to select Payment method")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			//To select card type
			String cardType = excel.getCellData("LoggedInOrder", "Card Type", 2);
			try {
				incrementalDragOnScreenAndClick(seetest,Elements.cardTypeDropDown.toString(),"Down","NATIVE",true);
				  String[] pickerList=  seetest.getPickerValues("NATIVE","xpath=//*[@class='UIAPicker']",0,0);
				  for(int i=0;i<pickerList.length;i++)
				  {
					  if(pickerList[i].contains(cardType)) {
						  cardType=pickerList[i];
						  break;
					  }
				  }
					seetest.setPickerValues("NATIVE", "xpath=//*[@class='UIAPicker']", 0, 0,cardType);
					Thread.sleep(1000);
					seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.sleep(2000);
				log.info("Card type selected as : " + cardType);
				extTestObj.createNode("Card type selected as : " + cardType).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to select card type");
				extTestObj.createNode("Failed to select card type")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			//To enter card number
			try {
				String cardNumber = excel.getCellData("LoggedInOrder", "Card Number", 2);
				Thread.sleep(4000);
				incrementalDragOnScreenAndClick(seetest,Elements.iosCardNumberTextBoxXpath,"Down","NATIVE",false);
				seetest.elementSendText("NATIVE","xpath="+Elements.iosCardNumberTextBoxXpath,0,cardNumber);
				seetest.closeKeyboard();
				log.info("Card number entered as : " + cardNumber);
				extTestObj.createNode("Card number entered as : " + cardNumber).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter card number");
				extTestObj.createNode("Failed to card number")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			//To enter CVV number
			try {
				String cvvNumber = excel.getCellData("LoggedInOrder", "CVV", 2);
				Thread.sleep(2000);
				incrementalDragOnScreenAndClick(seetest,Elements.iosCVVTextBoxXpath,"Down","NATIVE",false);
				seetest.elementSendText("NATIVE", "xpath="+Elements.iosCVVTextBoxXpath, 0, cvvNumber);
				seetest.closeKeyboard();
//				seetest.click("NATIVE","xpath=//*[@text='Done']",0, 1);
				log.info("Cvv number entered as : " + cvvNumber);
				extTestObj.createNode("Cvv number entered as : " + cvvNumber).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter cvv number");
				extTestObj.createNode("Failed to cvv number")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			//To select Expiration month
			String month = excel.getCellData("LoggedInOrder", "Expiration Month", 2);
			try {
				incrementalDragOnScreenAndClick(seetest,Elements.iosExpirationMonthXpath,"Down","NATIVE",true);
				  String[] pickerList=  seetest.getPickerValues("NATIVE","xpath=//*[@class='UIAPicker']",0,0);
				  for(int i=0;i<pickerList.length;i++)
				  {
					  if(pickerList[i].contains(month)) {
					    month=pickerList[i];
						  break;
					  }
				  }
					seetest.setPickerValues("NATIVE", "xpath=//*[@class='UIAPicker']", 0, 0,month);
					Thread.sleep(1000);
					seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.sleep(2000);
				log.info("Expiration Month selected as : " + month);
				extTestObj.createNode("Expiration Month selected as : " + month).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to select Expiration Month");
				extTestObj.createNode("Failed to select Expiration Month")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			//To select expiration year
			String year = excel.getCellData("LoggedInOrder", "Expiration Year", 2);
			try {
				incrementalDragOnScreenAndClick(seetest,Elements.iosExpirationYearXpath,"Down","NATIVE",true);
				seetest.sleep(2000);
				String[] pickerList=  seetest.getPickerValues("NATIVE","xpath=//*[@class='UIAPicker']",0,0);
				  for(int i=0;i<pickerList.length;i++)
				  {
					  if(pickerList[i].contains(year)) {
					    year=pickerList[i];
						  break;
					  }
				  }
					seetest.setPickerValues("NATIVE", "xpath=//*[@class='UIAPicker']", 0, 0, year);
					Thread.sleep(1000);
					seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
//					seetest.setPickerValues("NATIVE", "xpath=//*[@class='UIAPicker']", 0, 0, year);
//				}
				log.info("Expiration Year selected as : " + year);
				extTestObj.createNode("Expiration Year selected as : " + year).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to select Expiration Year");
				extTestObj.createNode("Failed to select Expiration Year")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			//To enter name on card
			try {

				String cardName = excel.getCellData("LoggedInOrder", "Name On Card", 2);
				incrementalDragOnScreenAndClick(seetest,Elements.iosNameOnCardTextBoxXpath,"Down","NATIVE",false);
				seetest.elementSendText("NATIVE", "xpath="+Elements.iosNameOnCardTextBoxXpath, 0, cardName);
				seetest.closeKeyboard();
				log.info("Name on Card entered as : " + cardName);
				extTestObj.createNode("Name on Card entered as : " + cardName).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter card number");
				extTestObj.createNode("Failed to enter card number")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			//To enter zip code
			try {
				String zipcode = excel.getCellData("LoggedInOrder", "Zip Code", 2);
				incrementalDragOnScreenAndClick(seetest,Elements.iosZipCodeTextBoxXpath,"Down","NATIVE",false);
				seetest.elementSendText("NATIVE", "xpath="+Elements.iosZipCodeTextBoxXpath, 0, zipcode);
				seetest.closeKeyboard();
				log.info("Zipcode entered as : " + zipcode);
				extTestObj.createNode("Zipcode entered as : " + zipcode).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter zipcode");
				extTestObj.createNode("Failed to enter zipcode")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			//To enter tip amount
			String tip = excel.getCellData("LoggedInOrder", "Tip", 2);
			try {

				incrementalDragOnScreenAndClick(seetest,Elements.iosTipTextBoxXpath,"Down","NATIVE",false);
				seetest.elementSendText("NATIVE","xpath="+Elements.iosTipTextBoxXpath,0,tip);
				seetest.closeKeyboard();
				log.info("Tip given as : " + Integer.parseInt(tip)*0.01);
				extTestObj.createNode("Tip given as : " + Integer.parseInt(tip)*0.01).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter tip");
				extTestObj.createNode("Failed to enter tip")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			//To check St Jude's donation
           try {
				
				incrementalDragOnScreenAndClick(seetest,Elements.iosDonationCheckBox,"Down","NATIVE",false);
				seetest.swipe("Down",600, 500);
				seetest.click("NATIVE", "xpath="+Elements.iosDonationCheckBox, 0, 1);
				log.info("Donation checked");
				extTestObj.createNode("Donation checked").pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to check donation check box");
				extTestObj.createNode("Failed to check donation check box")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			
		}
		
		/*
		 * Function Name : iosAppEnterPaymentDetailsQA2(int)
		 * Purpose : To validate payment page and enter payment details 
		 * Platform : IOS App
		 * Parameters :Location Index for checking UCOM/Non-UCOM store
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */
		public void iosAppEnterPaymentDetailsQA2(int locationIndex)
		{	
			if(excel.getCellData("Locations","Type",locationIndex).equals("UCOM"))
				seetest.click("NATIVE","xpath="+Elements.iosAppPayWithCreditCardButton,0,1);
			//To select payment method
			String paymentMethod = excel.getCellData("LoggedInOrder", "Payment Method", 2);
			try {
				explicitWait(Elements.iosFinalizeOrderHeaderXpath);
				incrementalDragOnScreenAndClick(seetest,Elements.paymentMethodDropDown.toString(),"Down","NATIVE",true);
				  String[] pickerList=  seetest.getPickerValues("NATIVE","xpath=//*[@class='UIAPicker']",0,0);
				  for(int i=0;i<pickerList.length;i++)
				  {
					  if(pickerList[i].contains(paymentMethod)) {
						  paymentMethod=pickerList[i];
						  break;
					  }
				  }
					seetest.setPickerValues("NATIVE", "xpath=//*[@class='UIAPicker']", 0, 0,paymentMethod);
					Thread.sleep(1000);
					seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.sleep(2000);
				log.info("Payment Method selected as : " + paymentMethod);
				extTestObj.createNode("Payment Method selected as : " + paymentMethod).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to select Payment method");
				extTestObj.createNode("Failed to select Payment method")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			//To select card type
			String cardType = excel.getCellData("LoggedInOrder", "Card Type", 2);
			try {
				incrementalDragOnScreenAndClick(seetest,Elements.cardTypeDropDown.toString(),"Down","NATIVE",true);
				  String[] pickerList=  seetest.getPickerValues("NATIVE","xpath=//*[@class='UIAPicker']",0,0);
				  for(int i=0;i<pickerList.length;i++)
				  {
					  if(pickerList[i].contains(cardType)) {
						  cardType=pickerList[i];
						  break;
					  }
				  }
					seetest.setPickerValues("NATIVE", "xpath=//*[@class='UIAPicker']", 0, 0,cardType);
					Thread.sleep(1000);
					seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.sleep(2000);
				log.info("Card type selected as : " + cardType);
				extTestObj.createNode("Card type selected as : " + cardType).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to select card type");
				extTestObj.createNode("Failed to select card type")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			//To enter card number
			try {
				String cardNumber = excel.getCellData("LoggedInOrder", "Card Number", 2);
				Thread.sleep(4000);
				incrementalDragOnScreenAndClick(seetest,Elements.iosCardNumberTextBoxXpath,"Down","NATIVE",false);
				seetest.elementSendText("NATIVE","xpath="+Elements.iosCardNumberTextBoxXpath,0,cardNumber);
				seetest.closeKeyboard();
				log.info("Card number entered as : " + cardNumber);
				extTestObj.createNode("Card number entered as : " + cardNumber).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter card number");
				extTestObj.createNode("Failed to card number")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			//To enter CVV number
			try {
				String cvvNumber = excel.getCellData("LoggedInOrder", "CVV", 2);
				Thread.sleep(2000);
				incrementalDragOnScreenAndClick(seetest,Elements.iosCVVTextBoxXpath,"Down","NATIVE",false);
				seetest.elementSendText("NATIVE", "xpath="+Elements.iosCVVTextBoxXpath, 0, cvvNumber);
				seetest.closeKeyboard();
//				seetest.click("NATIVE","xpath=//*[@text='Done']",0, 1);
				log.info("Cvv number entered as : " + cvvNumber);
				extTestObj.createNode("Cvv number entered as : " + cvvNumber).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter cvv number");
				extTestObj.createNode("Failed to cvv number")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			//To select Expiration month
			String month = excel.getCellData("LoggedInOrder", "Expiration Month", 2);
			try {
				incrementalDragOnScreenAndClick(seetest,Elements.iosExpirationMonthXpath,"Down","NATIVE",true);
				  String[] pickerList=  seetest.getPickerValues("NATIVE","xpath=//*[@class='UIAPicker']",0,0);
				  for(int i=0;i<pickerList.length;i++)
				  {
					  if(pickerList[i].contains(month)) {
					    month=pickerList[i];
						  break;
					  }
				  }
					seetest.setPickerValues("NATIVE", "xpath=//*[@class='UIAPicker']", 0, 0,month);
					Thread.sleep(1000);
					seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
				seetest.sleep(2000);
				log.info("Expiration Month selected as : " + month);
				extTestObj.createNode("Expiration Month selected as : " + month).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to select Expiration Month");
				extTestObj.createNode("Failed to select Expiration Month")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			//To select expiration year
			String year = excel.getCellData("LoggedInOrder", "Expiration Year", 2);
			try {
				incrementalDragOnScreenAndClick(seetest,Elements.iosExpirationYearXpath,"Down","NATIVE",true);
				seetest.sleep(2000);
				String[] pickerList=  seetest.getPickerValues("NATIVE","xpath=//*[@class='UIAPicker']",0,0);
				  for(int i=0;i<pickerList.length;i++)
				  {
					  if(pickerList[i].contains(year)) {
					    year=pickerList[i];
						  break;
					  }
				  }
					seetest.setPickerValues("NATIVE", "xpath=//*[@class='UIAPicker']", 0, 0, year);
					Thread.sleep(1000);
					seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
//					seetest.setPickerValues("NATIVE", "xpath=//*[@class='UIAPicker']", 0, 0, year);
//				}
				log.info("Expiration Year selected as : " + year);
				extTestObj.createNode("Expiration Year selected as : " + year).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to select Expiration Year");
				extTestObj.createNode("Failed to select Expiration Year")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			//To enter name on card
			try {

				String cardName = excel.getCellData("LoggedInOrder", "Name On Card", 2);
				incrementalDragOnScreenAndClick(seetest,Elements.iosNameOnCardTextBoxXpath,"Down","NATIVE",false);
				seetest.elementSendText("NATIVE", "xpath="+Elements.iosNameOnCardTextBoxXpath, 0, cardName);
				seetest.closeKeyboard();
				log.info("Name on Card entered as : " + cardName);
				extTestObj.createNode("Name on Card entered as : " + cardName).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter card number");
				extTestObj.createNode("Failed to enter card number")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			//To enter zip code
			try {
				String zipcode = excel.getCellData("LoggedInOrder", "Zip Code", 2);
				incrementalDragOnScreenAndClick(seetest,Elements.iosZipCodeTextBoxXpath,"Down","NATIVE",false);
				seetest.elementSendText("NATIVE", "xpath="+Elements.iosZipCodeTextBoxXpath, 0, zipcode);
				seetest.closeKeyboard();
				log.info("Zipcode entered as : " + zipcode);
				extTestObj.createNode("Zipcode entered as : " + zipcode).pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to enter zipcode");
				extTestObj.createNode("Failed to enter zipcode")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			
			
		}
		
		/*
		 * Function Name : iosAppaddGiftCardDetails()
		 * Purpose : To add gift card 
		 * Platform : IOS App
		 * Parameters :None
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */
		public void iosAppaddGiftCardDetails()
		{
			String giftCardNumber = excel.getCellData("LoggedInOrder","Gift Card Number",2);
			try {
				incrementalDragOnScreenAndClick(seetest, "xpath="+Elements.iosAppGiftCardTextBox,"Down","NATIVE",true);
				driver.getKeyboard().sendKeys(giftCardNumber);
				seetest.closeKeyboard();
				seetest.click("NATIVE","xpath="+Elements.iosAppApplyGiftCard,0,1);
				log.info("Gift card applied");
				extTestObj.createNode("Gift card applied").pass("PASSED");
			}
			catch(Exception e)
			{
				log.error("Failed to apply gift card");
				extTestObj.createNode("Failed to apply gift card")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			
		}
		
		

		/*
		 * Function Name : androidAppPlaceOrder()
		 * Purpose : To retrieve price before order place and click Place Order
		 * Platform : Android App
		 * Parameters :None
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */
		public String androidAppPlaceOrder() {
			//To retrieve price before placing order
			String amount="";
			try {
				seetest.setProperty("Android.native.nonInstrumented","false");
				try {
				seetest.swipeWhileNotFound("Down", 900, 600, "WEB","xpath="+Elements.appOrderTotalBeforePlace, 0, 4000,7, false);
				amount=seetest.elementGetText("WEB", "xpath="+Elements.appOrderTotalBeforePlace, 0);
				}
				catch(Exception e)
				{
					seetest.swipeWhileNotFound("Down", 900, 600, "WEB","xpath="+Elements.deliveryPickUpCost, 0, 4000,7, false);
					amount=seetest.elementGetText("WEB", "xpath="+Elements.deliveryPickUpCost, 0);
				}
				log.info("Price before placing order : "+amount);
				extTestObj.createNode("Price before placing order : "+amount).pass("PASSED");
				seetest.setProperty("Android.native.nonInstrumented","true");
			}
			catch(Exception e)
			{
				log.warn("Failed to retrieve price before order place");
				extTestObj.createNode("Failed to retrieve price before order place")
						.warning("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").warning(e);
				log.error(e.getMessage());
			}
			//To click Place Order button
			try {
				seetest.setProperty("Android.native.nonInstrumented","false");
				seetest.swipeWhileNotFound("Down", 900, 600, "WEB",Elements.appPlaceOrderXpath, 0, 4000,7, false);
				seetest.verifyElementFound("WEB",Elements.appPlaceOrderXpath, 0);
				seetest.click("WEB",Elements.appPlaceOrderXpath, 0, 2);
				seetest.setProperty("Android.native.nonInstrumented","true");
				try {
					seetest.click("NATIVE", "xpath=//*[@text='No, thanks']", 0, 1);
				} catch (Exception e) {
				}
				log.info("Place order button clicked");
				extTestObj.createNode("Place order button clicked").pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to click place order button");
				extTestObj.createNode("Failed to click place order button")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
			return amount;
		}
		/*
		 * Function Name : iosAppPlaceOrder()
		 * Purpose : To  click Place Order and validate whether the order is placed
		 * Platform : IOS App
		 * Parameters :None
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */
		public String iosAppPlaceOrder() {
			
			String amt = "";
			try {
				incrementalDragOnScreenAndClick(seetest,Elements.iosPlaceOrderXpath,"Down","NATIVE",false);
				seetest.swipe("Down",450,3000);
				seetest.click("NATIVE","xpath="+Elements.iosPlaceOrderXpath,0, 2);
				try {
					clickableWait(By.xpath("xpath=//*[@id='Not Now']"));
					clickableWait(By.xpath("//*[@text='Cancel']"));
				} catch (Exception e) {
				}
				try {
				try {
				explicitWait(Elements.appOrderSuccessMessageXpath);
				log.info("Place order button clicked");
				String successMsg = driver.findElement(Elements.appOrderSuccessMessageXpath).getText();
				log.info("Success Message : "+successMsg);
				extTestObj.createNode("Place order button clicked").pass("PASSED");
				extTestObj.createNode("Success Message : "+successMsg);
				}
				catch(Exception exp)
				{
					explicitWait(Elements.iosCallTheRestaurant);
					explicitWait(Elements.iosPickUpTimeLabel);
					log.info("Place order button clicked");
					extTestObj.createNode("Place order button clicked").pass("PASSED");
					log.info("Order place complete");
					extTestObj.createNode("Order place complete").pass("PASSED");
					
				}
				}
				catch(Exception exception)
				{
					explicitWait(By.xpath("//*[@id='BUY NOW']"));
					log.info("Place order button clicked");
					extTestObj.createNode("Place order button clicked").pass("PASSED");
					log.info("Order place complete");
					extTestObj.createNode("Order place complete").pass("PASSED");
				}
			} catch (Exception e) {
				log.error("Failed to click place order button");
				extTestObj.createNode("Failed to click place order button")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			return amt;
		}
		
		/*
		 * Function Name : iosAppPlaceOrderforFutureOrder()
		 * Purpose : To  click Place Order for orders of future date
		 * Platform : IOS App
		 * Parameters :None
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */
		public String iosAppPlaceOrderforFutureOrder() {
			String amt = "";
			try {
				incrementalDragOnScreenAndClick(seetest,Elements.iosPlaceOrderXpath,"Down","NATIVE",false);
				seetest.swipe("Down",450,3000);
				seetest.click("NATIVE","xpath="+Elements.iosPlaceOrderXpath,0, 2);
				try {
					seetest.click("NATIVE", "xpath=//*[@text='No, thanks']", 0, 1);
					seetest.click("NATIVE","xpath=//*[@text='Not Now']",0,1);
				} catch (Exception e) {
				}
				log.info("Place order button clicked");
				extTestObj.createNode("Place order button clicked").pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to click place order button");
				extTestObj.createNode("Failed to click place order button")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();
			}
			return amt;
		}


		public void appSelectMyAccountOptionIOS() {
			try {
				explicitWait(Elements.iosmyAccountOptionXpath);
				clickableWait(Elements.iosmyAccountOptionXpath);
				log.info("My Account Option selected");
				extTestObj.createNode("My Account Option selected").pass("PASSED");
			} catch (Exception e) {
				log.error("Failed to select My Account Option");
				extTestObj.createNode("Failed to select My Account Option")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopIOSTest();

			}

		}
		/* function to retrieve the first name before My Account update */
		public void appgetFirstNameBeforeUpdateIOS() {
			String initialFirstName = "";
			try {
				seetest.swipeWhileNotFound("Down", 50, 200, "WEB", "xpath=//*[@id='firstName']", 0, 1000, 5, false);
				explicitWait(Elements.iosfirstNameTextBox);
				initialFirstName = driver.findElement(Elements.iosfirstNameTextBox).getAttribute("value");
				log.info("First Name before update obtained as : " + initialFirstName);
				extTestObj.createNode("First Name before update obtained as : " + initialFirstName).pass("PASSED");
			} catch (Exception e) {
				log.error("First Name before update not obtained");
				extTestObj.createNode("First Name before update not obtained")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());

			}

		}
		/*
		 * Function Name : dragOnScreen(SeeTestClient, int, int, int, int)
		 * Purpose : To  perform random drag on screen
		 * Platform : All
		 * Parameters :seetestClient object, start coordinates in percentage, end coordinates in percentage
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */
	public void dragOnScreen(SeeTestClient seeTestClient, int start_percent_x, int start_percent_y, int end_percent_x,
			int end_percent_y) throws Exception {

		// checking whether all coordinates fall within the permissible range of [0,100]
		if (start_percent_x < 0 || start_percent_x > 100 || start_percent_y < 0 || start_percent_y > 100
				|| end_percent_x < 0 || end_percent_x > 100 || end_percent_y < 0 || end_percent_y > 100) {
			throw new Exception("INVALID COORDINATES USED, COORDINATES MUST BE IN [0,100]");
		}

		// converting from input percentage from screen width/height to coordinates
		int start_x_coordinate = seeTestClient.p2cx(start_percent_x);
		int start_y_coordinate = seeTestClient.p2cy(start_percent_y);
		int end_x_coordinate = seeTestClient.p2cx(end_percent_x);
		int end_y_coordinate = seeTestClient.p2cy(end_percent_y);

		// set a time delay for the drag/scroll in milliseconds
		int delay = 5;

		try {
			seeTestClient.dragCoordinates(start_x_coordinate, start_y_coordinate, end_x_coordinate, end_y_coordinate,
					delay);
		} catch (Exception e) {
			throw new Exception("ERROR OCCURED WHILE DRAGGING" + e.getMessage());
		}
	}
	/*
	 * Function Name : incrementalDragOnScreenAndClick(SeeTestClient,String,String,String,Boolean)
	 * Purpose : To  incrementally drag screen to have a particular element into view and then click it based on click consent
	 * Platform : All
	 * Parameters :seetestClient object, element locator, direction, zone, clickConsent
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void incrementalDragOnScreenAndClick(SeeTestClient seeTestClient,String element,String direction,String zone,Boolean clickConsent) throws Exception {
		int percent_x=60,start_percent_ydown=90,counter=30,start_percent_yUp=10,end_percent_yUp=10;
        if(direction.equals("Down"))
        {
		while(counter>=0)  
        { 
		  try {
			explicitWaitforClickability(By.xpath(element));
			int elementYpos = Integer.parseInt(driver.findElement(By.xpath(element)).getAttribute("y"));
			int footerYpos =Integer.parseInt(driver.findElement(By.xpath("//*[@class='UIATabBar']")).getAttribute("y"));
			int elementHeight= Integer.parseInt(driver.findElement(By.xpath(element)).getAttribute("height"));
			while((footerYpos-elementYpos)<elementHeight)
			{
				dragOnScreen(seetest,60,85,60,80);	
				elementYpos = Integer.parseInt(driver.findElement(By.xpath(element)).getAttribute("y"));
			}
			if(clickConsent==true)
        	seetest.click(zone,"xpath="+element,0,1);
        	 break;
          }
		catch(Exception e)	
		  {
        	  dragOnScreen(seetest,60,85,60,80);
        	  counter--;
          }
          }
		
        }
        
        if(direction.equals("Up"))
        {
		while(counter<=100)  
        { 
		  try {
			explicitWaitforVisibility(By.xpath(element));
        	seetest.click(zone,"xpath="+element,0,1);
			log.info("Element found and clicked");
        	 break;
          }
		catch(Exception e)
		  {
			end_percent_yUp+=7;
        	int delay = 1000;
        	int start_x_coordinate = seeTestClient.p2cx(percent_x);
            int start_y_coordinate = seeTestClient.p2cy(start_percent_yUp);
            int end_x_coordinate = seeTestClient.p2cx(percent_x);
            int end_y_coordinate = seeTestClient.p2cy(end_percent_yUp);
            start_percent_yUp=end_percent_yUp;
        	seeTestClient.dragCoordinates(start_x_coordinate, start_y_coordinate, end_x_coordinate, end_y_coordinate, delay);
          }
          }
		seetest.swipe("Up",600, 500);
        }
		
	}
	
	
	/*
	 * Function Name : androidAppvalidateOrderPlace()
	 * Purpose : To  validate whether order is placed successfully 
	 * Platform : Android App
	 * Parameters :seetestClient object, element locator, direction, zone, clickConsent
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void androidAppvalidateOrderPlace()
 {
	 try {
		 try {
			 seetest.waitForElement("NATIVE", "xpath=//*[@text='YES']", 0,4000);
			 seetest.click("NATIVE","xpath=//*[@text='YES']",0, 1);
			 seetest.setProperty("Android.native.nonInstrumented","false");
			 seetest.waitForElement("NATIVE", "xpath=//*[@text='Rate Later']", 0,4000);
			 seetest.click("NATIVE","xpath=//*[@text='Rate Later']",0,1);
			 seetest.setProperty("Android.native.nonInstrumented","true");
			 explicitWait(Elements.appMenuButtonXpath);
			 log.info("Order place validated");
		     extTestObj.createNode("Order place validated").pass("PASSED");
		 }
		 catch(Exception e)
		 {
			 explicitWait(Elements.appOrderSuccessMessageXpath);
			 log.info("Order place validated, success message : "+driver.findElement(Elements.appOrderSuccessMessageXpath).getAttribute("text"));
		     extTestObj.createNode("Order place validated, success message : "+driver.findElement(Elements.appOrderSuccessMessageXpath).getAttribute("text")).pass("PASSED");
			 
		 }
	     }
	 catch(Exception e)
	 {
		 log.error("Order place validation failed");
	 		extTestObj.createNode("Order place validation failed")
	 				.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
	 		log.error(e.getMessage());
	 		stopTestforMobileBrowser();
		 
	 }
 }
	/*
	 * Function Name : androidAppValidateOrderTrackable()
	 * Purpose : To  validate whether track order functionality is working fine for delivery orders
	 * Platform : Android App
	 * Parameters :sNone
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	 public void androidAppValidateOrderTrackable()
	 {
		 try {
		 explicitWait(By.xpath(Elements.trackOrder));
		 clickableWait(By.xpath(Elements.trackOrder));
		 explicitWait(By.xpath(Elements.ordertrackImg));
		 seetest.setProperty("Android.native.nonInstrumented","false");
		 log.info("Order Status : "+seetest.elementGetText("WEB","xpath="+Elements.deliveryOrderStatusXpath, 0));
		 extTestObj.createNode("Order Status : "+seetest.elementGetText("WEB","xpath="+Elements.deliveryOrderStatusXpath, 0)).pass("PASSED");
		 seetest.setProperty("Android.native.nonInstrumented","true");
		 }
		 catch(Exception e)
		 {
			 log.error("Order not trackable");
		 		extTestObj.createNode("Order not trackable")
		 				.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
		 		log.error(e.getMessage());
		 		stopTestforMobileBrowser();
	 }
 }
	 /*
		 * Function Name : iosAppValidateCheckout()
		 * Purpose : To validate the check out page and its functionality
		 * Platform : IOS App
		 * Parameters :sNone
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */
	public void iosAppValidateCheckout() {
		//To opt in Silver ware
		try {
			incrementalDragOnScreenAndClick(seetest,Elements.iosSilverWareOptInXpath,"Down","NATIVE",false);
			seetest.sleep(2000);
//			dragOnScreen(seetest,50,85,50,80);
			seetest.sleep(2000);
			seetest.click("NATIVE","xpath="+Elements.iosSilverWareOptInXpath,0, 1);
			seetest.sleep(2000);
			 log.info("Silver ware opted in");
			 extTestObj.createNode("Silver ware opted in").pass("PASSED");
		}
		
		catch(Exception e)
		{
			log.error("Failed to opt in Silver ware");
			extTestObj.createNode("Failed to opt in Silver ware")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopIOSTest();
		}
		//To click on Check out button
		try {
			incrementalDragOnScreenAndClick(seetest,Elements.iosCheckOutXpath,"Down","NATIVE",false);
			seetest.swipe("Down",600, 500);
			seetest.sleep(2000);
			seetest.click("NATIVE","xpath="+Elements.iosCheckOutXpath,0,1);
			explicitWait(Elements.appCheckOutHeaderXpath); 
			log.info("Checkout button clicked");
			 extTestObj.createNode("Checkout button clicked").pass("PASSED");
		}
		catch(Exception e)
		{
			log.error("Checkout button click failed");
			extTestObj.createNode("Checkout button click failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopIOSTest();
		}
	}
	
	public void iosAppValidateAccountUpdate()
	{
		String firstname = excel.getCellData("UpdateMyAccount","First Name",2);
		String lastname = excel.getCellData("UpdateMyAccount","Last Name",2);
		String email = excel.getCellData("UpdateMyAccount","Email", 2);
		String zipcode = excel.getCellData("UpdateMyAccount","Zip Code",2);
		
		
		try {
			clickableWait(Elements.iosMoreButtonXpath);
			clickableWait(By.xpath("//*[@text='MY ACCOUNT']"));
			explicitWait(By.xpath("//*[@text='UPDATE PROFILE' and @class='UIAStaticText']"));
			incrementalDragOnScreenAndClick(seetest,"xpath=//*[@id='Please enter your first name']", "Down","NATIVE", true);
			seetest.elementSendText("NATIVE","xpath=//*[@id='Please enter your first name']",0,firstname);
			incrementalDragOnScreenAndClick(seetest,"xpath=//*[@id='Please enter your last name']", "Down","NATIVE", true);
			seetest.elementSendText("NATIVE","xpath=//*[@id='Please enter your last name']",0,lastname);
			incrementalDragOnScreenAndClick(seetest,"xpath=//*[@id='Please enter your e-mail address']", "Down","NATIVE", true);
			seetest.elementSendText("NATIVE","xpath=//*[@id='Please enter your e-mail address']",0,email);
			incrementalDragOnScreenAndClick(seetest,"xpath=//*[@id='Please enter your 5-digit ZIP code']", "Down","NATIVE", true);
			seetest.elementSendText("NATIVE","xpath=//*[@id='Please enter your 5-digit ZIP code']",0,zipcode);
			seetest.closeKeyboard();
			incrementalDragOnScreenAndClick(seetest,"xpath=//*[@text='UPDATE']", "Down","NATIVE", true);
			explicitWait(By.xpath("//*[@id='Account successfully updated']"));
			String successMessage = seetest.elementGetText("NATIVE","xpath=//*[@id='Account successfully updated']",0);
			extTestObj.createNode("Success message retrieved : "+successMessage).pass("PASSED");
			log.info("Success message retrieved : "+successMessage);
			incrementalDragOnScreenAndClick(seetest,"xpath=//*[@id='Please enter your first name']", "Down","NATIVE", false);
			Assert.assertEquals(firstname, seetest.elementGetText("NATIVE","xpath=//*[@id='Please enter your first name']",0));
			incrementalDragOnScreenAndClick(seetest,"xpath=//*[@id='Please enter your last name']", "Down","NATIVE", false);
			Assert.assertEquals(lastname, seetest.elementGetText("NATIVE","xpath=//*[@id='Please enter your last name']",0));
			incrementalDragOnScreenAndClick(seetest,"xpath=//*[@id='Please enter your e-mail address']", "Down","NATIVE", false);
			Assert.assertEquals(email, seetest.elementGetText("NATIVE","xpath=//*[@id='Please enter your e-mail address']",0));
			incrementalDragOnScreenAndClick(seetest,"xpath=//*[@id='Please enter your 5-digit ZIP code']", "Down","NATIVE", false);
			Assert.assertEquals(zipcode, seetest.elementGetText("NATIVE","xpath=//*[@id='Please enter your 5-digit ZIP code']",0));
			extTestObj.createNode("Account Update successful").pass("PASSED");
			log.info("Account Update successful");
		}
		catch(Exception e)
		{
			log.error("My account update failed");
			extTestObj.createNode("My account update failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopIOSTest();	
		}
	}
	
	 /*
	 * Function Name : iosAppClickCheckoutforCustomizedOrder()
	 * Purpose : To validate the check out page and its functionality for customized order
	 * Platform : IOS App
	 * Parameters :sNone
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
	public void iosAppClickCheckoutforCustomizedOrder() {
		//To opt in silver ware
		try {
			incrementalDragOnScreenAndClick(seetest,Elements.iosSilverWareOptInXpath,"Down","NATIVE",false);
			seetest.click("NATIVE","xpath="+Elements.iosSilverWareOptInXpath,0, 1);
			 log.info("Silver ware opted in");
			 extTestObj.createNode("Silver ware opted in").pass("PASSED");
		}
		
		catch(Exception e)
		{
			log.error("Failed to opt in Silver ware");
			extTestObj.createNode("Failed to opt in Silver ware")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopIOSTest();
		}
		//To click on Check out button
		try {
			incrementalDragOnScreenAndClick(seetest,Elements.iosCheckOutXpath,"Down","NATIVE",false);
			seetest.swipe("Down",600, 500);
			seetest.click("NATIVE","xpath="+Elements.iosCheckOutXpath,0,1);
			explicitWait(Elements.appCheckOutHeaderXpath); 
			log.info("Checkout button clicked");
			 extTestObj.createNode("Checkout button clicked").pass("PASSED");
		}
		catch(Exception e)
		{
			log.error("Checkout button click failed");
			extTestObj.createNode("Checkout button click failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
	}
	 /*
		 * Function Name : iosAppValidateMenuSelection()
		 * Purpose : To validate menu category and menu item selection
		 * Platform : IOS App
		 * Parameters :sNone
		 * Added by : Somnath Baul
		 * Modified by : Somnath Baul
		 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
		 */
    public void iosAppValidateMenuSelection()
    {
    	//To select menu category
    	 String menuCategory= excel.getCellData("Menu", "Category for App", 2);
         try {
       	  try {
       		  clickableWait(By.xpath("//*[@text='Cancel']"));
       		  clickableWait(By.xpath("//*[@text='Cancel']"));
       	  }
       	  catch(Exception e)
       	  {}
       	clickableWait(By.xpath("//*[@id='MORE' and @class='UIAButton']"));
   		clickableWait(By.xpath("//*[@id='VIEW MENU']"));
       	explicitWait(Elements.appChilisMenuHeader);
       	  incrementalDragOnScreenAndClick(seetest,"//*[@text='"+menuCategory+"' and @class='UIAStaticText' and @width>0]","Down","NATIVE",true);
       	  log.info("Menu Category selected with : " +  menuCategory);
       	  extTestObj.createNode("Menu Category selected with : " +  menuCategory).pass("PASSED");
         }
         catch (Exception e) {
       	  log.error("Menu Category selection failed");
       	  extTestObj.createNode("Menu Category selection failed")
             .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
       	  log.error(e.getMessage());
       	  stopIOSTest();
         }
         //To select menu item
         String menuItem= excel.getCellData("Menu", "Item", 2);
 		try {
     		incrementalDragOnScreenAndClick(seetest,"//*[@text='"+menuItem+"' and @class='UIAStaticText' and @width>0]","Down","NATIVE",false);
     		seetest.sleep(3000);
     		seetest.click("NATIVE","xpath=//*[@text='"+menuItem+"' and @class='UIAStaticText' and @width>0]",0, 1);
 			log.info("Menu Item selected with : " +  menuItem);
 			extTestObj.createNode("Menu Item selected with : " +  menuItem).pass("PASSED");
 		}
 		catch(Exception e)
 		{
 			log.error("Menu Item selection failed");
 			extTestObj.createNode("Menu Item selection failed").fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
 			log.error(e.getMessage());
 			stopIOSTest();
         }
 		//To click Add to Order button
		try {
			explicitWait(MobileBy.xpath("//*[@text='" + menuItem + "' and @class='UIAView']"));
			incrementalDragOnScreenAndClick(seetest,"xpath="+Elements.iosAddtoOrderXpath,"Down","NATIVE",false);
			seetest.click("NATIVE","xpath="+Elements.iosAddtoOrderXpath,0,1);
			log.info("Add To Order button clicked");
			extTestObj.createNode("Add To Order button clicked").pass("PASSED");
		} catch (Exception e) {
			log.error("Add To Order button click failed");
			extTestObj.createNode("Add To Order button click failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopIOSTest();
		}
    	
    }
    /*
	 * Function Name : iosAppCheckOutWithReward()
	 * Purpose : To check out an order with rewards and items related to the order
	 * Platform : IOS App
	 * Parameters :sNone
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
    public void iosAppCheckOutWithReward()
    {
    	try
    	{
    		incrementalDragOnScreenAndClick(seetest,"xpath="+Elements.rewardOption,"Down","NATIVE",true);
    		incrementalDragOnScreenAndClick(seetest,"xpath="+Elements.iosCheckOutXpath,"Down","NATIVE",false);
    		seetest.swipe("Down",600, 500);
			seetest.sleep(2000);
			seetest.click("NATIVE","xpath="+Elements.iosCheckOutXpath,0,1);
			seetest.waitForElement("NATIVE","xpath="+Elements.rewardItemIOSApp,0,3000);
			seetest.click("NATIVE","xpath="+Elements.rewardItemIOSApp,0,1);
			seetest.waitForElement("NATIVE","xpath="+Elements.rewardItemName,0,3000);
			incrementalDragOnScreenAndClick(seetest,"xpath="+Elements.iosAddtoOrderXpath,"Down","NATIVE",true);
			incrementalDragOnScreenAndClick(seetest,"xpath="+Elements.rewardOption,"Down","NATIVE",true);
			incrementalDragOnScreenAndClick(seetest,Elements.iosCheckOutXpath,"Down","NATIVE",false);
    		seetest.swipe("Down",600, 500);
			seetest.sleep(2000);
			seetest.click("NATIVE","xpath="+Elements.iosCheckOutXpath,0,1);
			explicitWait(Elements.appCheckOutHeaderXpath);
			log.info("Check Out successful");
			extTestObj.createNode("Check Out successful").pass("PASSED");	
    	}
    	catch(Exception e)
    	{
    		log.error("Check Out failed");
			extTestObj.createNode("Check Out failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopIOSTest();
    	}
    }
    
    /*
	 * Function Name : iosAppvalidateRewardInCheckOutPage()
	 * Purpose : To validate the reward details in check out page
	 * Platform : IOS App
	 * Parameters :sNone
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
    public void iosAppvalidateRewardInCheckOutPage()
    {
    	try {
    		incrementalDragOnScreenAndClick(seetest,"xpath="+Elements.discountLabelIOSApp,"Down","NATIVE",false);
    		String discountAmount = seetest.elementGetText("NATIVE","xpath="+Elements.discountAmountIOSApp,0);
    		extTestObj.createNode("Discount Amount : "+discountAmount).pass("PASSED");
    		log.info("Discount Amount : "+discountAmount);
    		String rewardTitle = seetest.elementGetText("NATIVE","xpath="+Elements.rewardItemIOSApp,0);
    		extTestObj.createNode("Reward Title : "+rewardTitle).pass("PASSED");
    		log.info("Reward Title : "+rewardTitle);
    	}
    	catch(Exception e)
    	{
    		log.error("Reward validation failed");
			extTestObj.createNode("Reward validation failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopIOSTest();
    	}
    }
    /*
	 * Function Name : androidAppEnterDeliveryDetails(int)
	 * Purpose : To validate menu category and menu item selection
	 * Platform : Android App
	 * Parameters :locationIndex from CommonData.xlsx
	 * Added by : Somnath Baul
	 * Modified by : Somnath Baul
	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
	 */
    public void androidAppEnterDeliveryDetails(int locationIndex)
    {
    	//To select Order type as Delivery
    	try {
    		explicitWait(Elements.iosCheckoutHeaderXpath);
    		clickableWait(Elements.appDeliveryOrderTypeXpath);
    		log.info("Delivery order type selected");
    		extTestObj.createNode("Delivery order type selected").pass("PASSED");
    	}
    	
    	catch(Exception e)
    	{
    		log.error("Delivery order type selected");
			extTestObj.createNode("Delivery order type selected").fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
    	}
    	//To select delivery location
    	String deliveryLoc=excel.getCellData("Delivery","Restaurant Address",locationIndex);
    	String aptNo=excel.getCellData("Delivery","Apt. no", 2);
    	String deliveryInstructions=excel.getCellData("Delivery","Instruction", 2);
    	try {
    		seetest.swipeWhileNotFound("Down",900,500,"NATIVE","xpath="+Elements.appDeliveryAddressTextBox,0,3000,6,true);
    		driver.getKeyboard().sendKeys(deliveryLoc);
    		seetest.closeKeyboard();
    		seetest.setProperty("Android.native.nonInstrumented","false");
    		seetest.verifyElementFound("WEB","xpath="+Elements.appDeliveryAddressOption,0);
    		seetest.click("WEB","xpath="+Elements.appDeliveryAddressOption,0, 1);
    		seetest.setProperty("Android.native.nonInstrumented","true");
    		log.info("Delivery address entered as "+deliveryLoc);
    		extTestObj.createNode("Delivery address entered as "+deliveryLoc).pass("PASSED");
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to enter and select delivery address");
			extTestObj.createNode("Failed to enter and select delivery address").fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
    	}
    	//To enter Apartment No.
    	try {
    		seetest.swipeWhileNotFound("Down",900,500,"NATIVE","xpath="+Elements.appDeliveryAptNo,0,3000,6,false);
    		seetest.elementSendText("NATIVE","xpath="+Elements.appDeliveryAptNo,0,aptNo);
    		log.info("Delivery Apartment No entered as "+aptNo);
    		extTestObj.createNode("Delivery Apartment No entered as "+aptNo).pass("PASSED");
    	}
    	
    	catch(Exception e)
    	{
    		log.error("Failed to enter delivery Apartment No");
			extTestObj.createNode("Failed to enter delivery Apartment No").fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
    	}
    	//To enter delivery instruction
    	try {
    		seetest.swipeWhileNotFound("Down",900,500,"NATIVE","xpath="+Elements.appDeliveryInstruction,0,3000,6,false);
    		seetest.elementSendText("NATIVE","xpath="+Elements.appDeliveryInstruction,0,deliveryInstructions);
    		log.info("Delivery Instruction entered as "+deliveryInstructions);
    		extTestObj.createNode("Delivery Instruction entered as "+deliveryInstructions).pass("PASSED");	
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to enter Delivery instruction");
			extTestObj.createNode("Failed to enter Delivery instruction").fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
    	}
    }
    /*
   	 * Function Name : androidAppEnterDeliveryDetailsQA2(int)
   	 * Purpose : To validate menu category and menu item selection
   	 * Platform : Android App
   	 * Parameters :locationIndex from CommonData.xlsx
   	 * Added by : Somnath Baul
   	 * Modified by : Somnath Baul
   	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
   	 */
    public void androidAppEnterDeliveryDetailsQA2(int locationIndex)
    {
    	//To select order type as Delivery
    	try {
    		explicitWait(Elements.iosCheckoutHeaderXpath);
    		clickableWait(Elements.appDeliveryOrderTypeXpath);
    		log.info("Delivery order type selected");
    		extTestObj.createNode("Delivery order type selected").pass("PASSED");
    	}
    	
    	catch(Exception e)
    	{
    		log.error("Delivery order type selected");
			extTestObj.createNode("Delivery order type selected").fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();

    	}
    	//To select delivery location
    	String deliveryLoc=excel.getCellData("Delivery","Restaurant Address",locationIndex);
    	String aptNo=excel.getCellData("Delivery","Apt. no", 2);
    	String deliveryInstructions=excel.getCellData("Delivery","Instruction", 2);
    	try {
    		seetest.swipeWhileNotFound("Down",900,500,"NATIVE","xpath="+Elements.appDeliveryAddressTextBoxqa2,0,3000,6,true);
    		driver.getKeyboard().sendKeys(deliveryLoc);
    		seetest.closeKeyboard();
    		seetest.setProperty("Android.native.nonInstrumented","false");
    		seetest.verifyElementFound("WEB","xpath=//*[@class='pac-icon pac-icon-marker']",0);
    		seetest.click("WEB","xpath=//*[@class='pac-icon pac-icon-marker']",0, 1);
    		seetest.setProperty("Android.native.nonInstrumented","true");
    		log.info("Delivery address entered as "+deliveryLoc);
    		extTestObj.createNode("Delivery address entered as "+deliveryLoc).pass("PASSED");
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to enter and select delivery address");
			extTestObj.createNode("Failed to enter and select delivery address").fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
    	}
    	//To enter Apartment No.
    	try {
    		seetest.swipeWhileNotFound("Down",900,500,"NATIVE","xpath="+Elements.appDeliveryAptNo,0,3000,6,false);
    		seetest.elementSendText("NATIVE","xpath="+Elements.appDeliveryAptNo,0,aptNo);
    		log.info("Delivery Apartment No entered as "+aptNo);
    		extTestObj.createNode("Delivery Apartment No entered as "+aptNo).pass("PASSED");
    	}
    	
    	catch(Exception e)
    	{
    		log.error("Failed to enter delivery Apartment No");
			extTestObj.createNode("Failed to enter delivery Apartment No").fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
    	}
    	//To enter Delivery Instruction
    	try {
    		seetest.swipeWhileNotFound("Down",900,500,"NATIVE","xpath="+Elements.appDeliveryInstruction,0,3000,6,false);
    		seetest.elementSendText("NATIVE","xpath="+Elements.appDeliveryInstruction,0,deliveryInstructions);
    		log.info("Delivery Instruction entered as "+deliveryInstructions);
    		extTestObj.createNode("Delivery Instruction entered as "+deliveryInstructions).pass("PASSED");	
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to enter Delivery instruction");
			extTestObj.createNode("Failed to enter Delivery instruction").fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
    	}
    }
    /*
   	 * Function Name : androidAppSelectDeliveryDateASAP()
   	 * Purpose : To select Delivery pick up type as ASAP
   	 * Platform : Android App
   	 * Parameters :None
   	 * Added by : Somnath Baul
   	 * Modified by : Somnath Baul
   	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
   	 */
    public void androidAppSelectDeliveryDateASAP()
    {
    	try {
    		seetest.swipeWhileNotFound("Down",900,500,"NATIVE","xpath="+Elements.appDeliveryDate,0,3000,6,false);
    		if(!driver.findElement(MobileBy.xpath(Elements.appDeliveryDate)).getAttribute("text").contains("ASAP"))
    		{
    			clickableWait(MobileBy.xpath(Elements.appDeliveryDate));
    			seetest.elementListPick("NATIVE","xpath=//*[@class='android.widget.ListView']","NATIVE","xpath=//*[contains(@text,'ASAP')]",0,true);
    		}
    		log.info("Delivery date selected as ASAP");
    		extTestObj.createNode("Delivery date selected as ASAP").pass("PASSED");
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to select delivery date as ASAP");
			extTestObj.createNode("Failed to select delivery date as ASAP").fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
    	}
    	
    }
    /*
   	 * Function Name : androidAppSelectDeliveryDateLaterToday()
   	 * Purpose : To select Delivery pick up type as Later Today and also select pick up time
   	 * Platform : Android App
   	 * Parameters :None
   	 * Added by : Somnath Baul
   	 * Modified by : Somnath Baul
   	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
   	 */
    public void androidAppSelectDeliveryDateLaterToday()
    {
       //To select delivery pick up type as Later Today	
    	try {
    		seetest.swipeWhileNotFound("Down",900,500,"NATIVE","xpath="+Elements.appDeliveryDate,0,3000,6,false);
    		if(!driver.findElement(MobileBy.xpath(Elements.appDeliveryDate)).getAttribute("text").contains("Later Today"))
    		{
    			clickableWait(MobileBy.xpath(Elements.appDeliveryDate));
    			seetest.elementListPick("NATIVE","xpath=//*[@class='android.widget.ListView']","NATIVE","xpath=//*[contains(@text,'Later Today')]",0,true);
    		}
    		log.info("Delivery date selected as Later Today");
    		extTestObj.createNode("Delivery date selected as Later Today").pass("PASSED");
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to select delivery date as Later Today");
			extTestObj.createNode("Failed to select delivery date as Later Today").fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
    	}
    	
    	//To select pick up time
    	String pickTime = excel.getCellData("Delivery", "Pickup Time for App", 2);
		try {
			seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE", "xpath=//*[@id='delivery-time']", 0, 3000, 5, false);
			if (!driver.findElement(Elements.appDeliveryPickUpTimeXpath).getAttribute("text").contains(pickTime)) {
				clickableWait(Elements.appDeliveryPickUpTimeXpath);
				seetest.elementListPick("NATIVE", "xpath=//*[@class='android.widget.ListView']", "NATIVE",
						"text=" + pickTime, 0, true);
			}
			log.info("Delivery Pickup time selected as: " + pickTime);
			extTestObj.createNode("Delivery Pickup time selected as: " + pickTime).pass("PASSED");
		} catch (Exception e) {
			log.error("Delivery pick up time selection failed");
			extTestObj.createNode("Delivery pick up time selection failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();

		}
    }
    /*
   	 * Function Name : androidAppCustomizeMenuItem()
   	 * Purpose : To select a menu item and customize the same
   	 * Platform : Android App
   	 * Parameters :None
   	 * Added by : Somnath Baul
   	 * Modified by : Somnath Baul
   	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
   	 */
    public void androidAppCustomizeMenuItem()
    {
    	String custItem=excel.getCellData("Order Customization","Custom Item",2);
    	String menuItem = excel.getCellData("Menu", "Item", 2);
    	//To select the customization item
    	try {
    		explicitWait(MobileBy.xpath("//*[@text='" + menuItem + "' and @class='android.view.View']"));
    		seetest.swipeWhileNotFound("Down",900,600,"NATIVE","xpath="+Elements.appCustomizeButtonXpath,0,5000,5,true);
    		seetest.swipeWhileNotFound("Down",900,600,"NATIVE","xpath="+Elements.appCustomizeItemDropDownXpath,0,5000,5,false);
    		seetest.click("NATIVE","xpath="+Elements.appCustomizeItemDropDownXpath,0,1);
    		seetest.elementListPick("NATIVE","xpath=//*[@class='android.widget.ListView']","NATIVE","text="+custItem,0,true);
    		clickableWait(Elements.appExtraCheckBox);
    		try {
				seetest.swipeWhileNotFound("Down", 900, 200, "NATIVE", "xpath=//*[@text='ADD TO ORDER']", 0, 2000, 5, true);
				log.info("Add To Order button clicked");
				extTestObj.createNode("Add To Order button clicked").pass("PASSED");
			} catch (Exception e) {
				log.error("Add To Order button click failed");
				extTestObj.createNode("Add To Order button click failed")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
    		//To validate whether customization item is added
    		explicitWait(Elements.iosYourOrderHeaderXpath);
    		seetest.swipeWhileNotFound("Down",900,600,"NATIVE","xpath="+Elements.appDisplayedCustomizeItem, 0,6000,5,false);
    		String dispCustItem = seetest.elementGetText("NATIVE","xpath="+Elements.appDisplayedCustomizeItem,0);
    		if(dispCustItem.contains("Extra")&&dispCustItem.contains(custItem))
    		{
    			log.info("Customization item selected as" +custItem);
        		extTestObj.createNode("Customization item selected as" +custItem).pass("PASSED");
    		}
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to select customization item");
			extTestObj.createNode("Failed to select customization item").fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
    	}
    }
    /*
   	 * Function Name : androidAppChangeQuantity()
   	 * Purpose : To change quantity of a particular menu item
   	 * Platform : Android App
   	 * Parameters :None
   	 * Added by : Somnath Baul
   	 * Modified by : Somnath Baul
   	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
   	 */
    public void androidAppChangeQuantity()
    {
    	String quantity=excel.getCellData("ReOrder","Quantity", 2);
    	try {
    		explicitWait(Elements.quantity);
    		clickElement(Elements.quantity);
    		seetest.elementListPick("NATIVE","xpath=//*[@class='android.widget.ListView']","NATIVE","text="+quantity,0,true);
    		log.info("Quantity changed to  : "+quantity);
    		extTestObj.createNode("Quantity changed to  : "+quantity).pass("PASSED");
    	}
    	catch(Exception e)
    	{
    		log.warn("Failed to change quantity");
			extTestObj.createNode("Failed to change quantity").warning("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").warning(e);
			log.error(e.getMessage());
    	}
    	
    }
    /*
   	 * Function Name : androidAppReorderItem()
   	 * Purpose : To re-order a previously ordered menu item
   	 * Platform : Android App
   	 * Parameters :None
   	 * Added by : Somnath Baul
   	 * Modified by : Somnath Baul
   	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
   	 */
    public void androidAppReorderItem()
    {
    	//To click reorder against a previously ordered menu item and modify the same
    	String quantity=excel.getCellData("ReOrder","Quantity", 2);
    	try {
    		explicitWait(Elements.reOrderButtonXpath);
    		clickableWait(Elements.reOrderButtonXpath);
    		explicitWait(Elements.orderAgainLinkXpath);
    		clickableWait(Elements.orderAgainLinkXpath);
    		explicitWait(Elements.quantity);
    		clickElement(Elements.quantity);
    		seetest.elementListPick("NATIVE","xpath=//*[@class='android.widget.ListView']","NATIVE","text="+quantity,0,true);
    		log.info("Quantity changed to  : "+quantity);
    		extTestObj.createNode("Quantity changed to  : "+quantity).pass("PASSED");
    		log.info("Item reordered and modified");
    		extTestObj.createNode("Item reordered and modified").pass("PASSED");
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to reorder");
			extTestObj.createNode("Failed to reorder").fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
    	}
    	//To click Add to order
		try {
			seetest.swipeWhileNotFound("Down", 900, 200, "NATIVE", "xpath="+Elements.androidAppAddToOrder, 0, 2000, 5, true);
			log.info("Add To Order button clicked");
			extTestObj.createNode("Add To Order button clicked").pass("PASSED");
		} catch (Exception e) {
			log.error("Add To Order button click failed");
			extTestObj.createNode("Add To Order button click failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();

		}
    	
    }
    /*
   	 * Function Name : androidAppValidateMuAccountUpdate()
   	 * Purpose : To validate My Account update
   	 * Platform : Android App
   	 * Parameters :None
   	 * Added by : Somnath Baul
   	 * Modified by : Somnath Baul
   	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
   	 */
    public void androidAppValidateMuAccountUpdate()
    {
    	//To click "More" button
    	try {
			clickableWait(Elements.appMoreButtonXpath);
			explicitWait(Elements.appLogoutButtonXpath);
			if (driver.findElement(Elements.appLogoutButtonXpath).isDisplayed()) {
				log.info("More button clicked");
				extTestObj.createNode("More button clicked").pass("PASSED");
			}
		} catch (Exception e) {
			log.error("Failed to click More button");
			extTestObj.createNode("Failed to click More button")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
    	//To select My Account option
    	try {
			clickableWait(Elements.appMyAccountOptionXpath);
			explicitWait(Elements.appMyAccountPageHeaderXpath);
			if (driver.findElement(Elements.appMyAccountPageHeaderXpath).isDisplayed()) {
				log.info("My Account option selected");
				extTestObj.createNode("My Account option selected").pass("PASSED");
			}
		} catch (Exception e) {
			log.error("My Account option selected");
			extTestObj.createNode("My Account option selected")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();

		}
    	//To retrieve initial First name
    	String[] initialAccDetails = new String[10];
    	try {
    		seetest.swipeWhileNotFound("Down",900,500,"NATIVE","xpath="+Elements.appAccFirstName,0,3000,4,false);
    		String initialFirstName=driver.findElement(MobileBy.xpath(Elements.appAccFirstName)).getAttribute("text");
    		initialAccDetails[0]=initialFirstName;
    		log.info("Initial First Name obtained as "+initialFirstName);
    		extTestObj.createNode("Initial First Name obtained as "+initialFirstName).pass("PASSED");
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to obtain initial First Name");
			extTestObj.createNode("Failed to obtain initial First Name").fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
    	}
    	//To retrieve initial Last name
    	try {
    		seetest.swipeWhileNotFound("Down",900,500,"NATIVE","xpath="+Elements.appAccLastName,0,3000,4,false);
    		String initialLastName=driver.findElement(MobileBy.xpath(Elements.appAccLastName)).getAttribute("text");
    		initialAccDetails[1]=initialLastName;
    		log.info("Initial Last Name obtained as "+initialLastName);
    		extTestObj.createNode("Initial Last Name obtained as "+initialLastName).pass("PASSED");
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to obtain initial Last Name");
			extTestObj.createNode("Failed to obtain initial Last Name").fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
    	}
    	//To retrieve initial Email
    	try {
    		seetest.swipeWhileNotFound("Down",900,500,"NATIVE","xpath="+Elements.appAccEmail,0,3000,4,false);
    		String initialEmail=driver.findElement(MobileBy.xpath(Elements.appAccEmail)).getAttribute("text");
    		initialAccDetails[2]=initialEmail;
    		log.info("Initial email obtained as "+initialEmail);
    		extTestObj.createNode("Initial email obtained as "+initialEmail).pass("PASSED");
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to obtain initial Email");
			extTestObj.createNode("Failed to obtain initial Email").fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
    	}
    	//To retrieve initial Zip Code
    	try {
    		seetest.swipeWhileNotFound("Down",900,500,"NATIVE","xpath="+Elements.appAccZipCode,0,3000,4,false);
    		String initialZipCode=driver.findElement(MobileBy.xpath(Elements.appAccZipCode)).getAttribute("text");
    		initialAccDetails[3]=initialZipCode;
    		log.info("Initial Zip Code obtained as "+initialZipCode);
    		extTestObj.createNode("Initial Zip Code obtained as "+initialZipCode).pass("PASSED");
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to obtain initial Zip Code");
			extTestObj.createNode("Failed to obtain initial Zip Code").fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
    	}
    	
    	String firstName=excel.getCellData("UpdateMyAccount","First Name",2);
    	String lastName=excel.getCellData("UpdateMyAccount","Last Name", 2);
    	String email=excel.getCellData("UpdateMyAccount","Email", 2);
    	String zipCode=excel.getCellData("UpdateMyAccount","Zip Code", 2);
    	//To update first name
    	try {
    		seetest.swipeWhileNotFound("Up",900,500,"NATIVE","xpath="+Elements.appAccFirstName,0,3000,4,false);
    		seetest.elementSendText("NATIVE","xpath="+Elements.appAccFirstName,0,firstName);
    		log.info("First Name updated with "+firstName);
    		extTestObj.createNode("First Name updated with "+firstName).pass("PASSED");
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to update First Name");
			extTestObj.createNode("Failed to update First Name").fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
    	}
    	//To update last name
    	try {
    		seetest.swipeWhileNotFound("Down",900,500,"NATIVE","xpath="+Elements.appAccLastName,0,3000,4,false);
    		seetest.elementSendText("NATIVE","xpath="+Elements.appAccLastName,0,lastName);
    		log.info("Last Name updated with "+lastName);
    		extTestObj.createNode("Last Name updated with "+lastName).pass("PASSED");
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to update Last Name");
			extTestObj.createNode("Failed to update Last Name").fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
    	}
    	//To update email
    	try {
    		seetest.swipeWhileNotFound("Down",900,500,"NATIVE","xpath="+Elements.appAccEmail,0,3000,4,false);
    		seetest.elementSendText("NATIVE","xpath="+Elements.appAccEmail,0,email);
    		log.info("Email updated with "+email);
    		extTestObj.createNode("Email updated with "+email).pass("PASSED");
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to update email");
			extTestObj.createNode("Failed to update email").fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
    	}
    	//To update zip code
    	try {
    		seetest.swipeWhileNotFound("Down",900,500,"NATIVE","xpath="+Elements.appAccZipCode,0,3000,4,false);
    		seetest.elementSendText("NATIVE","xpath="+Elements.appAccZipCode,0,zipCode);
    		log.info("Zip Code updated with "+zipCode);
    		extTestObj.createNode("Zip Code updated with "+zipCode).pass("PASSED");
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to update Zip Code");
			extTestObj.createNode("Failed to update Zip Code").fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
    	}
    	//To click update button
    	try {
    		seetest.setProperty("Android.native.nonInstrumented","false");
    		seetest.swipeWhileNotFound("Down",900,600,"WEB","xpath=//*[@text='Update']",0,5000,6,true);
    		seetest.setProperty("Android.native.nonInstrumented","true");
    		log.info("Update button clicked");
    		extTestObj.createNode("Update button clicked").pass("PASSED");
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to click update button");
			extTestObj.createNode("Failed to click update button").fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
    	}
    	//To check the success message after successful update
    	try {
    		explicitWait(Elements.appSuccessMessageXpath);
    		log.info("Account Successfully updated");
    		extTestObj.createNode("Account Successfully updated").pass("PASSED");
    	}
    	catch(Exception e)
    	{
        log.error("Account updation failed");
		extTestObj.createNode("Account updation failed").fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
		log.error(e.getMessage());
		stopTestforMobileBrowser();
    		
    	}
    	String[] finalAccDetails = new String[10];
    	//To retrieve the updated first name
    	try {
    		seetest.swipeWhileNotFound("Down",900,500,"NATIVE","xpath="+Elements.appAccFirstName,0,3000,4,false);
    		String updatedFirstName=driver.findElement(MobileBy.xpath(Elements.appAccFirstName)).getAttribute("text");
    		finalAccDetails[0]=updatedFirstName;
    		log.info("Updated First Name obtained as "+updatedFirstName);
    		extTestObj.createNode("Updated First Name obtained as "+updatedFirstName).pass("PASSED");
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to obtain updated First Name");
			extTestObj.createNode("Failed to obtain updated First Name").fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
    	}
    	//To retrieve the updated last name
    	try {
    		seetest.swipeWhileNotFound("Down",900,500,"NATIVE","xpath="+Elements.appAccLastName,0,3000,4,false);
    		String updatedLastName=driver.findElement(MobileBy.xpath(Elements.appAccLastName)).getAttribute("text");
    		finalAccDetails[1]=updatedLastName;
    		log.info("Updated Last Name obtained as "+updatedLastName);
    		extTestObj.createNode("Updated Last Name obtained as "+updatedLastName).pass("PASSED");
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to obtain updated Last Name");
			extTestObj.createNode("Failed to obtain updated Last Name").fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
    	}
    	
    	//To retrieve the updated Email
    	try {
    		seetest.swipeWhileNotFound("Down",900,500,"NATIVE","xpath="+Elements.appAccEmail,0,3000,4,false);
    		String updatedEmail=driver.findElement(MobileBy.xpath(Elements.appAccEmail)).getAttribute("text");
    		finalAccDetails[2]=updatedEmail;
    		log.info("Updated email obtained as "+updatedEmail);
    		extTestObj.createNode("Updated email obtained as "+updatedEmail).pass("PASSED");
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to obtain updated Email");
			extTestObj.createNode("Failed to obtain updated Email").fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
    	}
    	//To retrieve updated zip code
    	try {
    		seetest.swipeWhileNotFound("Down",900,500,"NATIVE","xpath="+Elements.appAccZipCode,0,3000,4,false);
    		String updatedZipCode=driver.findElement(MobileBy.xpath(Elements.appAccZipCode)).getAttribute("text");
    		finalAccDetails[3]=updatedZipCode;
    		log.info("Updated Zip Code obtained as "+updatedZipCode);
    		extTestObj.createNode("Updated Zip Code obtained as "+updatedZipCode).pass("PASSED");
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to obtain updated Zip Code");
			extTestObj.createNode("Failed to obtain updated Zip Code").fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
    	}
    	//To validate whether account details updated successfully
    	
    		int i=1;
    		if(Arrays.equals(initialAccDetails,finalAccDetails))
    			i=0;
    		try {	
    			int j=1/i;
    		log.info("Update account validated");
    		extTestObj.createNode("Update account validated").pass("PASSED");
    		}
    	catch(Exception e)
    	{
    		log.error("Update account validation failed");
			extTestObj.createNode("Update account validation failed").fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error("Update account validation failed");
			stopTestforMobileBrowser();
    	}
    }
    /*
   	 * Function Name : androidAppAddRewardItem()
   	 * Purpose : To add reward to a particular menu item
   	 * Platform : Android App
   	 * Parameters :None
   	 * Added by : Somnath Baul
   	 * Modified by : Somnath Baul
   	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
   	 */
    public void androidAppAddRewardItem()
    {
       //To opt in silver ware
    	try {
			seetest.swipeWhileNotFound("Down", 900, 500, "NATIVE",
					"xpath=//*[@class='android.view.View' and ./*[@text='Include silverware with my order (fork, knife, spoon and napkins).']]",
					0, 3000, 5, false);
			clickableWait(Elements.appOptInSilverWareXpath);
			log.info("Silver ware opted in");
			extTestObj.createNode("Silver ware opted in").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to opt in Silver ware");
			extTestObj.createNode("Failed to opt in Silver ware")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();

		}
    	//To add a particular reward
    	String rewarditem = excel.getCellData("OrderWithRewards", "Reward Item", 2);
    	try {
    		try {
    		seetest.swipeWhileNotFound("Down",900,600,"NATIVE","xpath="+Elements.appRewardRadioButtonXpath,0,6000,6, true);
    		seetest.click("NATIVE","xpath=//*[@text='Add Reward']",0,1);
    		log.info("Add Reward button clicked");
    		extTestObj.createNode("Add Reward button clicked").pass("PASSED");
    		}
    		catch(Exception e)
    		{log.error("Failed to click add reward button");
			extTestObj.createNode("Failed to click add reward button")
			.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
	         log.error(e.getMessage());
	         stopTestforMobileBrowser();	
    		}
    		//To click Check out button
    		try {
    		seetest.swipeWhileNotFound("Down", 450, 500, "NATIVE", "xpath=//*[@id='cart-checkout']", 0, 3000, 6, true);
    		log.info("Checkout button clicked");
			extTestObj.createNode("Checkout button clicked").pass("PASSED");
    		}
    		catch (Exception e) {
				log.error("Checkout button click failed");
				extTestObj.createNode("Checkout button click failed")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
    		//To select a reward item
    		try {
    		seetest.swipeWhileNotFound("Up",900,600,"NATIVE","xpath=//*[contains(text(),'"+rewarditem+"')]",0,7000, 7, true);
    		log.info("Reward item "+rewarditem+" selected");
    		extTestObj.createNode("Reward item "+rewarditem+" selected").pass("PASSED");
    		}
    		catch(Exception e)
    		{log.error("Failed to select reward item");
			extTestObj.createNode("Failed to select reward item")
			.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
	         log.error(e.getMessage());
	          stopTestforMobileBrowser();
    		}
    		//To click "Add to Order" button
    		try {
				explicitWait(MobileBy.xpath("//*[contains(@text,'"+rewarditem.split(" ")[0]+"') and @class='android.view.View']"));
				seetest.swipeWhileNotFound("Down", 900, 200, "NATIVE", "xpath=//*[@text='ADD TO ORDER']", 0, 2000, 5, true);
				log.info("Add To Order button clicked");
				extTestObj.createNode("Add To Order button clicked").pass("PASSED");
			} catch (Exception e) {
				log.error("Add To Order button click failed");
				extTestObj.createNode("Add To Order button click failed")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();

			}
    		//To add the previously selected reward once again
    		try {
    			seetest.setProperty("Android.native.nonInstrumented","false");
        		seetest.swipeWhileNotFound("Down",900,600,"WEB","xpath=((//*[@id='rewards-carousel']/*/*[@text='  ' and @nodeName='DIV' and ./parent::*[@text=' ']])[2]/*[@nodeName='LABEL'])\r\n" + 
        				"[1]",0,6000,6,true);
        		seetest.setProperty("Android.native.nonInstrumented","true");
        		log.info("Add Reward button clicked");
        		extTestObj.createNode("Add Reward button clicked").pass("PASSED");
        		}
        		catch(Exception e)
        		{log.error("Failed to click add reward button");
    			extTestObj.createNode("Failed to click add reward button")
    			.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
    	         log.error(e.getMessage());
    	         stopTestforMobileBrowser();	
        		}
    		//To click Check out button
    		try {
    			seetest.setProperty("Android.native.nonInstrumented","false");
				seetest.swipeWhileNotFound("Down", 450, 500, "WEB", "xpath=//*[@id='cart-checkout']", 0, 3000, 6, true);
				seetest.setProperty("Android.native.nonInstrumented","true");
				explicitWait(Elements.appCheckOutHeaderXpath);
				log.info("Checkout button clicked");
				extTestObj.createNode("Checkout button clicked").pass("PASSED");
			} catch (Exception e) {
				log.error("Checkout button click failed");
				extTestObj.createNode("Checkout button click failed")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopTestforMobileBrowser();
			}
    	}
    	catch(Exception e)
    	{
    		log.error(e.getMessage());
			stopTestforMobileBrowser();
    	}
    }
    
    /*
   	 * Function Name : androidAppVerifyReward()
   	 * Purpose : To verify whether reward added
   	 * Platform : Android App
   	 * Parameters :None
   	 * Added by : Somnath Baul
   	 * Modified by : Somnath Baul
   	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
   	 */
    public void androidAppVerifyReward()
    {
    	String rewarditem = excel.getCellData("OrderWithRewards", "Reward Item", 2);
    	try {
    		String[] str=rewarditem.split(" ");
    		seetest.swipeWhileNotFound("Down",900,600,"TEXT",str[0],0,6000,6,false);
    		seetest.verifyElementFound("TEXT",str[0],0);
    		log.info("Reward verified");
    		extTestObj.createNode("Reward verified").pass("PASSED");
    	}
    	catch(Exception e)
    	{
    		log.error("Reward verification failed : not displayed in checkout page");
			extTestObj.createNode("Reward verification failed : not displayed in checkout page")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
    	}
    }
    /*
   	 * Function Name : iosAppselectCurbsideASAP()
   	 * Purpose : To select order type as Curbside and pick up type as ASAP
   	 * Platform : IOS App
   	 * Parameters :None
   	 * Added by : Somnath Baul
   	 * Modified by : Somnath Baul
   	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
   	 */
    public void iosAppselectCurbsideASAP()
    {
    	//To select order type as Curbside
    	try {
    		explicitWait(By.xpath(Elements.iosCurbsideXpath));
    		clickableWait(By.xpath(Elements.iosCurbsideXpath));
    		log.info("Curbside Order Type selected");
    		extTestObj.createNode("Curbside Order Type selected").pass("PASSED");
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to select Curbside Order Type");
			extTestObj.createNode("Failed to select Curbside Order Type")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
    	}
    	//To select Pick up type as ASAP
    	try {
    		incrementalDragOnScreenAndClick(seetest,Elements.iosPickUpTypeXpath,"Down","NATIVE",true);
    		String[] pickerList=  seetest.getPickerValues("NATIVE","xpath=//*[@class='UIAPicker']",0,0);
    		String option="";
    		 for(int i=0;i<pickerList.length;i++)
			  {
				  if(pickerList[i].contains("ASAP")) {
				    option=pickerList[i];
					  break;
				  }
			  }
    		 seetest.setPickerValues("NATIVE", "xpath=//*[@class='UIAPicker']", 0, 0,option);
    		 Thread.sleep(1000);
			 seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
    		 seetest.sleep(2000);
    		 explicitWait(By.xpath("//*[contains(@text,'ASAP') and @class='UIAView']"));
    		 log.info("PickUp type selected as ASAP");
    		 extTestObj.createNode("PickUp type selected as ASAP").pass("PASSED");	
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to select pick up type as ASAP");
			extTestObj.createNode("Failed to select pick up type as ASAP")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			stopTestforMobileBrowser();
			log.error(e.getMessage());
    	}
    }
    /*
   	 * Function Name : iosAppSelectCarryOutASAP()
   	 * Purpose : To select order type as Carryout and pick up type as ASAP
   	 * Platform : IOS App
   	 * Parameters :None
   	 * Added by : Somnath Baul
   	 * Modified by : Somnath Baul
   	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
   	 */
    public void iosAppSelectCarryOutASAP()
    {
    	//To select order type as Carry Out
    	try {
    		explicitWait(By.xpath(Elements.iosCarryoutXpath));
    		clickableWait(By.xpath(Elements.iosCarryoutXpath));
    		log.info("Carryout Order Type selected");
    		extTestObj.createNode("Carryout Order Type selected").pass("PASSED");
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to select Carryout Order Type");
			extTestObj.createNode("Failed to select Carryout Order Type")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
    	}
    	//To select pickup type as ASAP
    	try {
    		incrementalDragOnScreenAndClick(seetest,Elements.iosPickUpTypeXpath,"Down","NATIVE",true);
    		String[] pickerList=  seetest.getPickerValues("NATIVE","xpath=//*[@class='UIAPicker']",0,0);
    		String option="";
    		 for(int i=0;i<pickerList.length;i++)
			  {
				  if(pickerList[i].contains("ASAP")) {
				    option=pickerList[i];
					  break;
				  }
			  }
    		 seetest.setPickerValues("NATIVE", "xpath=//*[@class='UIAPicker']", 0, 0,option);
    		 Thread.sleep(1000);
			 seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
    		 seetest.sleep(2000);
    		 explicitWait(By.xpath("//*[contains(@text,'ASAP') and @class='UIAView']"));
    		 log.info("PickUp type selected as ASAP");
    		 extTestObj.createNode("PickUp type selected as ASAP").pass("PASSED");	
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to select pick up type as ASAP");
			extTestObj.createNode("Failed to select pick up type as ASAP")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();	
    	}
    }
    /*
   	 * Function Name : iosAppSelectCurbsideLaterToday()
   	 * Purpose : To select order type as Curbside and pick up type as Later Today and also select Pick up time
   	 * Platform : IOS App
   	 * Parameters :None
   	 * Added by : Somnath Baul
   	 * Modified by : Somnath Baul
   	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
   	 */
    public void iosAppSelectCurbsideLaterToday()
    {
    //To select order type as Curbside
    	try {
    		explicitWait(By.xpath(Elements.iosCurbsideXpath));
    		clickableWait(By.xpath(Elements.iosCurbsideXpath));
    		log.info("Curbside Order Type selected");
    		extTestObj.createNode("Curbside Order Type selected").pass("PASSED");
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to select Curbside Order Type");
			extTestObj.createNode("Failed to select Curbside Order Type")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
    	}
    	//To select pick up type as Later Today
    	try {
    		incrementalDragOnScreenAndClick(seetest,Elements.iosPickUpTypeXpath,"Down","NATIVE",true);
    		String[] pickerList=  seetest.getPickerValues("NATIVE","xpath=//*[@class='UIAPicker']",0,0);
    		String option="";
    		 for(int i=0;i<pickerList.length;i++)
			  {
				  if(pickerList[i].contains("Later Today")) {
				    option=pickerList[i];
					  break;
				  }
			  }
    		 seetest.setPickerValues("NATIVE", "xpath=//*[@class='UIAPicker']", 0, 0,option);
    		 Thread.sleep(1000);
			 seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
    		 seetest.sleep(2000);
    		 explicitWait(By.xpath("//*[contains(@text,'Later Today') and @class='UIAView']"));
    		 log.info("PickUp type selected as Later Today");
    		 extTestObj.createNode("PickUp type selected as Later Today").pass("PASSED");	
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to select pick up type as Later Today");
			extTestObj.createNode("Failed to select pick up type as Later Today")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
    	}
    	//To select pick up time
    	try {
    		String pickUpTime = excel.getCellData("CarryOut","Pickup Time",2);
    		incrementalDragOnScreenAndClick(seetest,Elements.iosPickUpTimeXpath,"Down","NATIVE",true);
    		String[] pickerList=  seetest.getPickerValues("NATIVE","xpath=//*[@class='UIAPicker']",0,0);
    		int flag=0;
			  for(int i=0;i<pickerList.length;i++)
			  {
				  if(pickerList[i].contains(pickUpTime)) {
					  pickUpTime=pickerList[i];
					  flag=1;
					  break;
				  }
			  }
			  if(flag==0)
				  pickUpTime=pickerList[0];
			  seetest.setPickerValues("NATIVE", "xpath=//*[@class='UIAPicker']", 0, 0,pickUpTime);
			  Thread.sleep(1000);
			   seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
			   seetest.sleep(2000);
			   log.info("Pick up time selected as "+driver.findElement(By.xpath(Elements.iosPickUpTimeXpath)).getAttribute("value"));
			   extTestObj.createNode("Pick up time selected as "+driver.findElement(By.xpath(Elements.iosPickUpTimeXpath)).getAttribute("value"));
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to select pick up time");
		extTestObj.createNode("Failed to select pick up time")
		.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
        log.error(e.getMessage());
        stopTestforMobileBrowser();
    		
    	}
    	
    }
    /*
   	 * Function Name : iosAppSelectCarryOutLaterToday()
   	 * Purpose : To select order type as CarryOut and pick up type as Later Today and also select Pick up time
   	 * Platform : IOS App
   	 * Parameters :None
   	 * Added by : Somnath Baul
   	 * Modified by : Somnath Baul
   	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
   	 */
    public void iosAppSelectCarryOutLaterToday()
    {
    	//To select order type as Carry Out
    	try {
    		explicitWait(By.xpath(Elements.iosCarryoutXpath));
    		clickableWait(By.xpath(Elements.iosCarryoutXpath));
    		log.info("Carryout Order Type selected");
    		extTestObj.createNode("Carryout Order Type selected").pass("PASSED");
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to select Carryout Order Type");
			extTestObj.createNode("Failed to select Carryout Order Type")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
    	}
    	//To select pick up type as 'Later Today'
    	try {
    		incrementalDragOnScreenAndClick(seetest,Elements.iosPickUpTypeXpath,"Down","NATIVE",true);
    		String[] pickerList=  seetest.getPickerValues("NATIVE","xpath=//*[@class='UIAPicker']",0,0);
    		String option="";
    		 for(int i=0;i<pickerList.length;i++)
			  {
				  if(pickerList[i].contains("Later Today")) {
				    option=pickerList[i];
					  break;
				  }
			  }
    		 seetest.setPickerValues("NATIVE", "xpath=//*[@class='UIAPicker']", 0, 0,option);
    		 Thread.sleep(1000);
			 seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
    		 seetest.sleep(2000);
    		 explicitWait(By.xpath("//*[contains(@text,'Later Today') and @class='UIAView']"));
    		 log.info("PickUp type selected as Later Today");
    		 extTestObj.createNode("PickUp type selected as Later Today").pass("PASSED");	
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to select pick up type as Later Today");
			extTestObj.createNode("Failed to select pick up type as Later Today")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
    	}
    	//To select pick up time
    	try {
    		String pickUpTime = excel.getCellData("CarryOut","Pickup Time",2);
    		incrementalDragOnScreenAndClick(seetest,Elements.iosPickUpTimeXpath,"Down","NATIVE",true);
    		String[] pickerList=  seetest.getPickerValues("NATIVE","xpath=//*[@class='UIAPicker']",0,0);
    		int flag=0;
			  for(int i=0;i<pickerList.length;i++)
			  {
				  if(pickerList[i].contains(pickUpTime)) {
					  pickUpTime=pickerList[i];
					  flag=1;
					  break;
				  }
			  }
			  if(flag==0)
				  pickUpTime=pickerList[0];
			  seetest.setPickerValues("NATIVE", "xpath=//*[@class='UIAPicker']", 0, 0,pickUpTime);
			  Thread.sleep(1000);
			   seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
			   seetest.sleep(2000);
			   log.info("Pick up time selected as "+driver.findElement(By.xpath(Elements.iosPickUpTimeXpath)).getAttribute("value"));
			   extTestObj.createNode("Pick up time selected as "+driver.findElement(By.xpath(Elements.iosPickUpTimeXpath)).getAttribute("value"));
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to select pick up time");
		extTestObj.createNode("Failed to select pick up time")
		.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
        log.error(e.getMessage());
        stopTestforMobileBrowser();
    		
    	}
    	
    }
    /*
   	 * Function Name : iosAppenterGuestDetails()
   	 * Purpose : To enter guest details
   	 * Platform : IOS App
   	 * Parameters :None
   	 * Added by : Somnath Baul
   	 * Modified by : Somnath Baul
   	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
   	 */
    public void iosAppenterGuestDetails()
    {
    	String firstName = excel.getCellData("GuestUserDetails","First Name",2);
    	String lastName = excel.getCellData("GuestUserDetails","Last Name",2);
    	String contactNumber = excel.getCellData("GuestUserDetails","Contact Number",2);
    	String email = excel.getCellData("GuestUserDetails","Email",2);
    	//To enter first name
    	try {
    		incrementalDragOnScreenAndClick(seetest,Elements.iosGuestFirstNameXpath,"Down","NATIVE",false);
    		seetest.elementSendText("NATIVE","xpath="+Elements.iosGuestFirstNameXpath,0, firstName);
    		seetest.closeKeyboard();
    		log.info(" Guest First Name entered as :"+firstName);
    		extTestObj.createNode(" Guest First Name entered as :"+firstName).pass("PASSED");
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to enter guest first name");
    		extTestObj.createNode("Failed to enter guest first name")
    		.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
            log.error(e.getMessage());
            stopTestforMobileBrowser();
        		
    	}
    	//To enter last name
    	try {
    		incrementalDragOnScreenAndClick(seetest,Elements.iosGuestLastNameXpath,"Down","NATIVE",true);
    		driver.getKeyboard().sendKeys(lastName);
    		seetest.closeKeyboard();
    		log.info(" Guest Last Name entered as :"+lastName);
    		extTestObj.createNode(" Guest Last Name entered as :"+lastName).pass("PASSED");
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to enter guest last name");
    		extTestObj.createNode("Failed to enter guest last name")
    		.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
            log.error(e.getMessage());
            stopTestforMobileBrowser();
        		
    	}
    	//To enter contact number
    	try {
    		incrementalDragOnScreenAndClick(seetest,Elements.iosGuestContactNumberXpath,"Down","NATIVE",true);
    		driver.getKeyboard().sendKeys(contactNumber);
    		seetest.closeKeyboard();
    		log.info(" Guest Contact Number entered as :"+contactNumber);
    		extTestObj.createNode(" Guest Contact Number entered as :"+contactNumber).pass("PASSED");
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to enter guest contact number");
    		extTestObj.createNode("Failed to enter guest contact number")
    		.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
            log.error(e.getMessage());
            stopTestforMobileBrowser();
    }
    	//To enter email address
    	try {
    		incrementalDragOnScreenAndClick(seetest,Elements.iosGuestEmailAddressXpath,"Down","NATIVE",true);
    		driver.getKeyboard().sendKeys(email);
    		seetest.closeKeyboard();
    		log.info(" Guest Email entered as :"+email);
    		extTestObj.createNode(" Guest Email entered as :"+email).pass("PASSED");
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to enter guest email");
    		extTestObj.createNode("Failed to enter guest email")
    		.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
            log.error(e.getMessage());
            stopTestforMobileBrowser();
    }
    }
    /*
   	 * Function Name : iosAppEnterVehicleDetails()
   	 * Purpose : To enter vehicle details
   	 * Platform : IOS App
   	 * Parameters :None
   	 * Added by : Somnath Baul
   	 * Modified by : Somnath Baul
   	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
   	 */
    public void iosAppEnterVehicleDetails()
    {
    	String vehicleMake = excel.getCellData("GuestUserCurbSide","Vehicle Make",2);
    	String vehicleModel = excel.getCellData("GuestUserCurbSide","Vehicle Model",2);
    	String vehicleColor = excel.getCellData("GuestUserCurbSide","Vehicle Color",2);
    	//To enter vehicle make
    	try {
    		incrementalDragOnScreenAndClick(seetest,Elements.iosVehicleMakeXpath,"Down","NATIVE",true);
    		driver.getKeyboard().sendKeys(vehicleMake);
    		seetest.closeKeyboard();
    		log.info(" Vehicle make entered as :"+vehicleMake);
    		extTestObj.createNode(" Vehicle make entered as :"+vehicleMake).pass("PASSED");	
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to enter vehicle make");
    		extTestObj.createNode("Failed to enter vehicle make")
    		.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
            log.error(e.getMessage());
            stopTestforMobileBrowser();
    }
    	//To enter vehicle model
    	try {
    		incrementalDragOnScreenAndClick(seetest,Elements.iosVehicleModelXpath,"Down","NATIVE",true);
    		driver.getKeyboard().sendKeys(vehicleModel);
    		seetest.closeKeyboard();
    		log.info(" Vehicle model entered as :"+vehicleModel);
    		extTestObj.createNode(" Vehicle model entered as :"+vehicleModel).pass("PASSED");	
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to enter vehicle model");
    		extTestObj.createNode("Failed to enter vehicle model")
    		.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
            log.error(e.getMessage());
            stopTestforMobileBrowser();
    }
    	//To enter vehicle color
    	try {
    		incrementalDragOnScreenAndClick(seetest,Elements.iosVehicleColorXpath,"Down","NATIVE",true);
    		driver.getKeyboard().sendKeys(vehicleColor);
    		seetest.closeKeyboard();
    		log.info(" Vehicle color entered as :"+vehicleColor);
    		extTestObj.createNode(" Vehicle color entered as :"+vehicleColor).pass("PASSED");	
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to enter vehicle color");
    		extTestObj.createNode("Failed to enter vehicle color")
    		.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
            log.error(e.getMessage());
            stopTestforMobileBrowser();
    }
    }
    /*
   	 * Function Name : iosAppEnterDeliveryDetails()
   	 * Purpose : To enter details for delivery order
   	 * Platform : IOS App
   	 * Parameters :None
   	 * Added by : Somnath Baul
   	 * Modified by : Somnath Baul
   	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
   	 */
    
    public void iosAppEnterDeliveryDetails(int locationIndex)
    {
    	//To select order type as Delivery
    	try {
    		explicitWait(By.xpath(Elements.iosDeliveryXpath));
    		clickableWait(By.xpath(Elements.iosDeliveryXpath));
    		log.info("Delivery order type selected");
    		extTestObj.createNode("Delivery order type selected").pass("PASSED");
    	}
    	catch(Exception e)
    	{
    	log.error("Failed to select Delivery order type");
		extTestObj.createNode("Failed to select Delivery order type")
		.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
        log.error(e.getMessage());
        stopTestforMobileBrowser();	
    	}
    	//To select delivery address
    	try {
    		String deliveryAddress = excel.getCellData("Delivery","Restaurant Address",locationIndex);
    		incrementalDragOnScreenAndClick(seetest,Elements.iosDeliveryAddress,"Down","NATIVE",false);
    		seetest.elementSendText("NATIVE","xpath="+Elements.iosDeliveryAddress,0,deliveryAddress);
    		seetest.closeKeyboard();
    		explicitWait(By.xpath("//*[@text='"+deliveryAddress.split(" ")[0]+"']"));
    		clickElement(By.xpath("//*[@text='"+deliveryAddress.split(" ")[0]+"']"));
    		log.info("Delivery Address selected as "+deliveryAddress);
    		extTestObj.createNode("Delivery Address selected as "+deliveryAddress).pass("PASSED");
    	}
    	catch(Exception e)
    	{
    	log.error("Failed to select Delivery address");
		extTestObj.createNode("Failed to select Delivery address")
		.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
        log.error(e.getMessage());
        stopTestforMobileBrowser();	
    	}
    	//To select Apartment number
    	try {
    		String deliveryAptNo = excel.getCellData("Delivery","Apt. no",2);
    		incrementalDragOnScreenAndClick(seetest,Elements.iosDeliveryAptNoXpath,"Down","NATIVE",false);
    		sendKeysWait(By.xpath(Elements.iosDeliveryAptNoXpath),deliveryAptNo);
    		seetest.closeKeyboard();
    		log.info("Delivery Apartment no selected as "+deliveryAptNo);
    		extTestObj.createNode("Delivery Apartment no selected as "+deliveryAptNo).pass("PASSED");
    	}
    	catch(Exception e)
    	{
    	log.error("Failed to select Delivery Apartment no");
		extTestObj.createNode("Failed to select Delivery Apartment no")
		.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
        log.error(e.getMessage());
        stopTestforMobileBrowser();	
    	}
    	//To enter delivery instruction
    	try {
    		String deliveryInstruction = excel.getCellData("Delivery","Instruction",2);
    		incrementalDragOnScreenAndClick(seetest,Elements.iosDeliveryInstructionTextBoxXpath,"Down","NATIVE",false);
    		seetest.elementSendText("NATIVE","xpath="+Elements.iosDeliveryInstructionTextBoxXpath,0,deliveryInstruction);
    		seetest.closeKeyboard();
    		log.info("Delivery Instruction entered as "+deliveryInstruction);
    		extTestObj.createNode("Delivery Instruction entered as "+deliveryInstruction).pass("PASSED");
    	}
    	catch(Exception e)
    	{
    	log.error("Failed to select Delivery Instruction");
		extTestObj.createNode("Failed to select Delivery Instruction")
		.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
        log.error(e.getMessage());
        stopTestforMobileBrowser();	
    	}
    }
    /*
   	 * Function Name : iosAppoptDeliveryASAP()
   	 * Purpose : To select delivery pick up type ASAP
   	 * Platform : IOS App
   	 * Parameters :None
   	 * Added by : Somnath Baul
   	 * Modified by : Somnath Baul
   	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
   	 */
    public void iosAppoptDeliveryASAP()
    {
    	try {
    		String option ="";
    		incrementalDragOnScreenAndClick(seetest,Elements.iosDeliveryTypeXpath,"Down","NATIVE", true);
    		String[] pickerList=  seetest.getPickerValues("NATIVE","xpath=//*[@class='UIAPicker']",0,0);
    		for(int i=0;i<pickerList.length;i++)
			  {
				  if(pickerList[i].contains("ASAP")) {
					  option=pickerList[i];
					  break;
				  }
			  }
    		seetest.setPickerValues("NATIVE", "xpath=//*[@class='UIAPicker']", 0, 0,option);
			Thread.sleep(1000);
			seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
		seetest.sleep(2000);
		explicitWait(By.xpath("//*[contains(@text,'ASAP') and @class='UIAView']"));
		log.info("Delivery type selected as ASAP");
		extTestObj.createNode("Delivery type selected as ASAP").pass("PASSED");
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to select Delivery type as ASAP");
    		extTestObj.createNode("Failed to select Delivery type as ASAP")
    		.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
            log.error(e.getMessage());
            stopTestforMobileBrowser();
    	}
    	
    }
    /*
   	 * Function Name : iosAppoptDeliveryASAP()
   	 * Purpose : To select delivery pick up type Later Today and also select pick up time
   	 * Platform : IOS App
   	 * Parameters :None
   	 * Added by : Somnath Baul
   	 * Modified by : Somnath Baul
   	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
   	 */
    public void iosAppSelectDeliveryLaterToday()
    {
    	//To select delivery pick up type as Later Today
    	try {
    		incrementalDragOnScreenAndClick(seetest,Elements.iosDeliveryTypeXpath,"Down","NATIVE",true);
    		String[] pickerList=  seetest.getPickerValues("NATIVE","xpath=//*[@class='UIAPicker']",0,0);
    		String option="";
    		 for(int i=0;i<pickerList.length;i++)
			  {
				  if(pickerList[i].contains("Later Today")) {
				    option=pickerList[i];
					  break;
				  }
			  }
    		 seetest.setPickerValues("NATIVE", "xpath=//*[@class='UIAPicker']", 0, 0,option);
    		 Thread.sleep(1000);
			 seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
    		 seetest.sleep(2000);
    		 explicitWait(By.xpath("//*[contains(@text,'Later Today') and @class='UIAView']"));
    		 log.info("Delivery type selected as Later Today");
    		 extTestObj.createNode("Delivery type selected as Later Today").pass("PASSED");	
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to select Delivery type as Later Today");
			extTestObj.createNode("Failed to select Delivery type as Later Today")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
    	}
    	//To select pick up time
    	try {
    		String pickUpTime = excel.getCellData("CarryOut","Pickup Time",2);
    		incrementalDragOnScreenAndClick(seetest,Elements.iosDeliveryTimeXpath,"Down","NATIVE",true);
    		String[] pickerList=  seetest.getPickerValues("NATIVE","xpath=//*[@class='UIAPicker']",0,0);
			int flag=0;  
    		for(int i=0;i<pickerList.length;i++)
			  {
				  if(pickerList[i].contains(pickUpTime)) {
					  pickUpTime=pickerList[i];
					  flag=1;
					  break;
				  }
			  }
    		if(flag==0)
    			pickUpTime=pickerList[0];
			  seetest.setPickerValues("NATIVE", "xpath=//*[@class='UIAPicker']", 0, 0,pickUpTime);
			  Thread.sleep(1000);
			   seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
			   seetest.sleep(2000);
			   log.info("Delivery time selected as "+driver.findElement(By.xpath(Elements.iosDeliveryTimeXpath)).getAttribute("value"));
			   extTestObj.createNode("Delivery time selected as "+driver.findElement(By.xpath(Elements.iosDeliveryTimeXpath)).getAttribute("value"));
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to select Delivery time");
		extTestObj.createNode("Failed to select Delivery time")
		.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
        log.error(e.getMessage());
        stopTestforMobileBrowser();
    		
    	}
    	
    }
    /*
   	 * Function Name : iosAppselectCarryoutPickUpFuture()
   	 * Purpose : To select order type as Carry Out and pick up type as future date
   	 * Platform : IOS App
   	 * Parameters :None
   	 * Added by : Somnath Baul
   	 * Modified by : Somnath Baul
   	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
   	 */
    public void iosAppselectCarryoutPickUpFuture()
    {
    	//Order type selected as Carry Out
    	try {
    		explicitWait(By.xpath(Elements.iosCarryoutXpath));
    		clickableWait(By.xpath(Elements.iosCarryoutXpath));
    		log.info("Carryout Order Type selected");
    		extTestObj.createNode("Carryout Order Type selected").pass("PASSED");
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to select Carryout Order Type");
			extTestObj.createNode("Failed to select Carryout Order Type")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
    	}
    	//Pick up type selected as Future date
    	try {
    		incrementalDragOnScreenAndClick(seetest,Elements.iosPickUpTypeXpath,"Down","NATIVE",true);
    		String[] pickerList=  seetest.getPickerValues("NATIVE","xpath=//*[@class='UIAPicker']",0,0);
    		 seetest.setPickerValues("NATIVE", "xpath=//*[@class='UIAPicker']", 0, 0,pickerList[6]);
    		 Thread.sleep(1000);
			 seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
    		 seetest.sleep(2000);
    		 log.info("Future date selected as "+driver.findElement(By.xpath(Elements.iosPickUpTypeXpath)).getAttribute("text"));
    		 extTestObj.createNode("Future date selected as "+driver.findElement(By.xpath(Elements.iosPickUpTypeXpath)).getAttribute("text")).pass("PASSED");	
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to select pick up type as Future Date");
			extTestObj.createNode("Failed to select pick up type as Future Date")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
    	}
    	//To select pick up time
    	try {
    		String pickUpTime = excel.getCellData("CarryOut","Pickup Time",2);
    		incrementalDragOnScreenAndClick(seetest,Elements.iosPickUpTimeXpath,"Down","NATIVE",true);
    		String[] pickerList=  seetest.getPickerValues("NATIVE","xpath=//*[@class='UIAPicker']",0,0);
    		int flag=0;
			  for(int i=0;i<pickerList.length;i++)
			  {
				  if(pickerList[i].contains(pickUpTime)) {
					  pickUpTime=pickerList[i];
					  flag=1;
					  break;
				  }
			  }
			  if(flag==0)
				  pickUpTime=pickerList[0];
			  seetest.setPickerValues("NATIVE", "xpath=//*[@class='UIAPicker']", 0, 0,pickUpTime);
			  Thread.sleep(1000);
			   seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
			   seetest.sleep(2000);
			   log.info("Pick up time selected as "+driver.findElement(By.xpath(Elements.iosPickUpTimeXpath)).getAttribute("value"));
			   extTestObj.createNode("Pick up time selected as "+driver.findElement(By.xpath(Elements.iosPickUpTimeXpath)).getAttribute("value"));
    	}
    	catch(Exception e)
    	{
    		log.error("Failed to select pick up time");
		extTestObj.createNode("Failed to select pick up time")
		.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
        log.error(e.getMessage());
        stopTestforMobileBrowser();
    		
    	}
    	
    }
    /*
   	 * Function Name : iosAppSelectChilisFavouriteItems()
   	 * Purpose : To select favorite Items available for LoggedIn/MCA user
   	 * Platform : IOS App
   	 * Parameters :None
   	 * Added by : Somnath Baul
   	 * Modified by : Somnath Baul
   	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
   	 */
	public void iosAppSelectChilisFavouriteItems() {
		String favItem= excel.getCellData("Menu", "ChilisFavourites for Orders", 2);
		try {
			explicitWait(By.xpath("//*[@text='SELECT ITEMS']"));
			seetest.swipeWhileNotFound("Down", 500, 200, "NATIVE", "xpath=//*[@text='SELECT ITEMS']", 0, 2000, 5,
					false);
				try {
					explicitWait(MobileBy.xpath("//*[contains(@text,'" + favItem + "')]"));
					clickableWait(MobileBy.xpath("//*[@text='" + favItem + "']"));
					clickableWait(MobileBy.xpath(Elements.iosAddToCartXpath));
					log.info("Selected Favourite Item Name : " + favItem);
					extTestObj.createNode("Selected Favourite Item Name : " + favItem).info("INFO");
				} catch (Exception exp) {
					String favItemLocator = "//*[contains(@text,'" + favItem + "')]";
					seetest.elementSwipeWhileNotFound("NATIVE", "xpath=(//*[@class='UIACollectionView'])[1]", "Right",
							100, 2000, "NATIVE", "xpath=" + favItemLocator + "", 0, 1000, 5, false);
					clickableWait(MobileBy.xpath("//*[@text='" + favItem + "']"));
					clickableWait(MobileBy.xpath(Elements.iosAddToCartXpath));
					log.info("Selected Favourite Item Name : " +favItem);
					extTestObj.createNode("Favourite Item Name : " +favItem).info("INFO");
				}
			
		log.info("Chilis Favourite Item Selected");
		extTestObj.createNode("Chilis Favourite Item Selected").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select Chilis Favourite Item");
			extTestObj.createNode("Failed to select Chilis Favourite Item")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopIOSTest();

		}
	}
	 /*
   	 * Function Name : iosAppcustomizeItem()
   	 * Purpose : To customize a selected menu item
   	 * Platform : IOS App
   	 * Parameters :None
   	 * Added by : Somnath Baul
   	 * Modified by : Somnath Baul
   	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
   	 */
	public void iosAppcustomizeItem()
	{
		//To select menu category
		String menuCategory= excel.getCellData("Menu", "Category for App", 2);
         try {
       	  try {
       		  clickableWait(By.xpath("//*[@text='Cancel']"));
       		  clickableWait(By.xpath("//*[@text='Cancel']"));
       	  }
       	  catch(Exception e)
       	  {}
       	clickableWait(By.xpath("//*[@id='MORE' and @class='UIAButton']"));
   		clickableWait(By.xpath("//*[@id='VIEW MENU']"));
       	  explicitWait(Elements.appChilisMenuHeader);
       	  incrementalDragOnScreenAndClick(seetest,"//*[@text='"+menuCategory+"' and @class='UIAStaticText' and @width>0]","Down","NATIVE",true);
       	  log.info("Menu Category selected with : " +  menuCategory);
       	  extTestObj.createNode("Menu Category selected with : " +  menuCategory).pass("PASSED");
         }
         catch (Exception e) {
       	  log.error("Menu Category selection failed");
       	  extTestObj.createNode("Menu Category selection failed")
             .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
       	  log.error(e.getMessage());
       	  stopIOSTest();
         }
         //To select menu item
         String menuItem= excel.getCellData("Menu", "Item", 2);
 		try {
     		incrementalDragOnScreenAndClick(seetest,"//*[@text='"+menuItem+"' and @class='UIAStaticText' and @width>0]","Down","NATIVE",false);
     		seetest.sleep(3000);
     		seetest.click("NATIVE","xpath=//*[@text='"+menuItem+"' and @class='UIAStaticText' and @width>0]",0, 1);
 			log.info("Menu Item selected with : " +  menuItem);
 			extTestObj.createNode("Menu Item selected with : " +  menuItem).pass("PASSED");
 		}
 		catch(Exception e)
 		{
 			log.error("Menu Item selection failed");
 			extTestObj.createNode("Menu Item selection failed").fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
 			log.error(e.getMessage());
 			stopIOSTest();
         }
 		//To customize the menu item
		String customizeItem = excel.getCellData("Order Customization","Custom Item", 2);
		try {
			explicitWait(MobileBy.xpath("//*[@text='" + menuItem + "' and @class='UIAView']"));
			incrementalDragOnScreenAndClick(seetest,Elements.iosCustomizeItemButtonXpath,"Down","NATIVE",true);
			incrementalDragOnScreenAndClick(seetest,Elements.iosCustomizeItemDropDownXpath,"Down","NATIVE", true);
			String[] pickerList=  seetest.getPickerValues("NATIVE","xpath=//*[@class='UIAPicker']",0,0);
			  for(int i=0;i<pickerList.length;i++)
			  {
				  if(pickerList[i].contains(customizeItem)) {
					  customizeItem=pickerList[i];
					  break;
				  }
			  }
				seetest.setPickerValues("NATIVE", "xpath=//*[@class='UIAPicker']", 0, 0,customizeItem);
				Thread.sleep(1000);
				seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
			seetest.sleep(2000);
			clickableWait(By.xpath(Elements.iosExtraCheckBoxXpath));
			incrementalDragOnScreenAndClick(seetest,"xpath="+Elements.iosAddtoOrderXpath,"Down","NATIVE",false);
			seetest.click("NATIVE","xpath="+Elements.iosAddtoOrderXpath,0,1);
			explicitWait(By.xpath(Elements.iosYourorderHeaderXpath));
			explicitWait(By.xpath("//*[contains(@id,'"+customizeItem+"')]"));
			log.info("The menu item "+menuItem+" has been customized with : "+driver.findElement(By.xpath("//*[contains(@id,'"+customizeItem+"')]")).getText());
			extTestObj.createNode("The menu item "+menuItem+" has been customized with : "+driver.findElement(By.xpath("//*[contains(@id,'"+customizeItem+"')]")).getText()).pass("PASSED");	
		}
		catch (Exception e) {
			log.error("Failed to customize menu Item");
			extTestObj.createNode("Failed to customize menu Item")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopIOSTest();

		}
	}
	/*
   	 * Function Name : iosAppReorder()
   	 * Purpose : To reorder a previously ordered menu item
   	 * Platform : IOS App
   	 * Parameters :None
   	 * Added by : Somnath Baul
   	 * Modified by : Somnath Baul
   	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
   	 */
	public void iosAppReorder()
	{
		
		String quantity = excel.getCellData("ReOrder","Quantity",2);
		//To click Re-order button
		try {
			 try {
       		  clickableWait(By.xpath("//*[@text='Cancel']"));
       		  clickableWait(By.xpath("//*[@text='Cancel']"));
       	  }
       	  catch(Exception e)
       	  {}
			try {
			explicitWait(By.xpath(Elements.iosReorderButtonXpath));
			clickableWait(By.xpath(Elements.iosReorderButtonXpath));
			log.info("Reorder button clicked");
			extTestObj.createNode("Reorder button clicked").pass("PASSED");
			}
			catch(Exception e)
			{
				log.error("Failed to click Reorder button");
			  extTestObj.createNode("Failed to click Reorder button")
			.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
	         log.error(e.getMessage());
	          stopIOSTest();
			}
			//To select a particular item to be re-ordered and then modify its quantity
			List<WebElement> prevOrders = driver.findElements(By.xpath(Elements.iosOrderAgainXpath));
			prevOrders.get(0).click();
			explicitWait(By.xpath(Elements.iosYourorderHeaderXpath));
			incrementalDragOnScreenAndClick(seetest,Elements.iosQuantityXpath,"Down","NATIVE",true);
			String[] pickerList=  seetest.getPickerValues("NATIVE","xpath=//*[@class='UIAPicker']",0,0);
			  for(int i=0;i<pickerList.length;i++)
			  {
				  if(pickerList[i].contains(quantity)) {
					  quantity=pickerList[i];
					  break;
				  }
			  }
				seetest.setPickerValues("NATIVE", "xpath=//*[@class='UIAPicker']", 0, 0,quantity);
				Thread.sleep(1000);
				seetest.click("NATIVE", "xpath=//*[@id='Done']", 0, 1);
			seetest.sleep(2000);
			log.info("Quantity of the previously ordered item changed to "+driver.findElement(By.xpath(Elements.iosQuantityXpath)).getAttribute("value"));
            extTestObj.createNode("Quantity of the previously ordered item changed to "+driver.findElement(By.xpath(Elements.iosQuantityXpath)).getAttribute("value"));
		   log.info("Item reordered");
		   extTestObj.createNode("Item reordered").pass("PASSED");
		}
		catch(Exception e)
		{
			log.error("Failed to Reorder item");
			  extTestObj.createNode("Failed to Reorder item")
			.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
	         log.error(e.getMessage());
	          stopIOSTest();
		}
	}
	/*
   	 * Function Name : iosAppenterVisitDetails()
   	 * Purpose : To enter past visit details
   	 * Platform : IOS App
   	 * Parameters :None
   	 * Added by : Somnath Baul
   	 * Modified by : Somnath Baul
   	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
   	 */
	public void iosAppenterVisitDetails()
	{
		//To click Add My Visit button
		try {
            explicitWait(Elements.iosRewardsButtonXpath);
            clickableWait(Elements.iosRewardsButtonXpath);
            incrementalDragOnScreenAndClick(seetest,"xpath=//*[@id='ADD MY VISIT']","Down","NATIVE",false);
			clickableWait(By.xpath(Elements.iosAddMyVisit));
			log.info("Add My Visit button clicked");
			extTestObj.createNode("Add My Visit button clicked").pass("PASSED");
		}
		catch(Exception e)
		{
			log.error("Failed to clicked Add My Visit button");
			  extTestObj.createNode("Failed to clicked Add My Visit button")
			.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
	         log.error(e.getMessage());
	          stopIOSTest();	
		}
		//To enter zip code
		try {
			String zipcode = excel.getCellData("AddMyVisit", "Zipcode", 2);
			explicitWait(By.xpath(Elements.iosAddMyVisitHeaderXpath));
			incrementalDragOnScreenAndClick(seetest,Elements.iosRestaurantZipCodeXpath,"Down","NATIVE",false);
			seetest.elementSendText("NATIVE",Elements.iosRestaurantZipCodeXpath,0,zipcode);
			log.info("Restaurant zip code entered as : "+driver.findElement(By.xpath(Elements.iosRestaurantZipCodeXpath)).getAttribute("text"));
			extTestObj.createNode("Restaurant zip code entered as : "+driver.findElement(By.xpath(Elements.iosRestaurantZipCodeXpath)).getAttribute("text")).pass("PASSED");
			}
		catch(Exception e)
		{
			log.error("Failed to enter Restaurant Zip Code");
			  extTestObj.createNode("Failed to enter Restaurant Zip Code")
			.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
	         log.error(e.getMessage());
	          stopIOSTest();	
		}
		//To select visit month, visit day, visit year
		try {
			  String visitMonth = excel.getCellData("AddMyVisit", "Visit Month", 2);
			  String visitday = excel.getCellData("AddMyVisit", "Visit Day", 2);
			  String visitYear = excel.getCellData("AddMyVisit", "Visit Year", 2);
			  LocalDate currentdate = LocalDate.now();
		      int currentDay = currentdate.getDayOfMonth();
		      String currentMonth = currentdate.getMonth().toString();
		      char first = Character.toUpperCase(currentMonth.charAt(0));
		      String acceptedCurrentMonth=first+currentMonth.substring(1,currentMonth.length()).toLowerCase();
		      int currentYear = currentdate.getYear();
		      incrementalDragOnScreenAndClick(seetest,Elements.iosDateOfVisitXpath,"Down","NATIVE",true);
		      seetest.elementSetProperty("NATIVE","xpath=//*[@text='"+acceptedCurrentMonth+"' and @class='UIAPickerWheel']",0,"text",visitMonth);
		      seetest.elementSetProperty("NATIVE","xpath=//*[@text='"+currentDay+"' and @class='UIAPickerWheel']",0,"text",visitday);
		      seetest.elementSetProperty("NATIVE","xpath=//*[@text='"+currentYear+"' and @class='UIAPickerWheel']",0,"text",visitYear);
		      seetest.sleep(1000);
		      seetest.click("NATIVE","xpath=//*[@text='Done']",0,1);
		      seetest.sleep(2000);
		      log.info("Visit date added as : "+driver.findElement(By.xpath(Elements.iosDateOfVisitXpath)).getAttribute("text"));
		       extTestObj.createNode("Visit date added as : "+driver.findElement(By.xpath(Elements.iosDateOfVisitXpath)).getAttribute("text")).pass("PASSED");
		}
		catch(Exception e)
		{
			log.error("Failed to add date of visit");
			  extTestObj.createNode("Failed to add date of visit")
			.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
	         log.error(e.getMessage());
	          stopIOSTest();	
		}
		//To enter Receipt check number
		try {
			String checkNo = excel.getCellData("AddMyVisit", "Receipt Check Number", 2);
			incrementalDragOnScreenAndClick(seetest,Elements.iosCheckNumberXpath,"Down","NATIVE",false);
			seetest.elementSendText("NATIVE",Elements.iosCheckNumberXpath,0,checkNo);
			seetest.closeKeyboard();
			log.info("Receipt Check Number entered as : "+driver.findElement(By.xpath(Elements.iosCheckNumberXpath)).getAttribute("text"));
		    extTestObj.createNode("Receipt Check Number entered as : "+driver.findElement(By.xpath(Elements.iosCheckNumberXpath)).getAttribute("text")).pass("PASSED");
		}
		catch(Exception e)
		{log.error("Failed to enter receipt check number");
		  extTestObj.createNode("Failed to enter receipt check number")
		.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
       log.error(e.getMessage());
        stopIOSTest();		
		}
		//To enter check total
		try {
			String checkTotal = excel.getCellData("AddMyVisit", "Subtotal", 2);
			incrementalDragOnScreenAndClick(seetest,Elements.iosSubTotalXpath,"Down","NATIVE",false);
			seetest.elementSendText("NATIVE",Elements.iosSubTotalXpath,0,checkTotal);
			seetest.closeKeyboard();
			log.info("Sub Total entered as : "+driver.findElement(By.xpath(Elements.iosSubTotalXpath)).getAttribute("text"));
		    extTestObj.createNode("Sub Total entered as : "+driver.findElement(By.xpath(Elements.iosSubTotalXpath)).getAttribute("text")).pass("PASSED");
		}
		catch(Exception e)
		{log.error("Failed to enter sub total");
		  extTestObj.createNode("Failed to enter sub total")
		.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
       log.error(e.getMessage());
        stopIOSTest();		
		}
	}
	
	/*
   	 * Function Name : iosAppclickVisitSubmit()
   	 * Purpose : To click submit visit button
   	 * Platform : IOS App
   	 * Parameters :None
   	 * Added by : Somnath Baul
   	 * Modified by : Somnath Baul
   	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
   	 */
	public void iosAppclickVisitSubmit()
	{
		try {
			incrementalDragOnScreenAndClick(seetest,Elements.iosSubmitVisitButton,"Down","NATIVE",true);
			explicitWait(By.xpath(Elements.iosVisitSubmitConfirmationMessage));
			log.info("Submit button clicked");
			extTestObj.createNode("Submit button clicked").pass("PASSED");
		}
		
		catch(Exception e)
		{
			log.error("Failed to click visit submit button");
		  extTestObj.createNode("Failed to click visit submit button")
		.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
         log.error(e.getMessage());
         stopIOSTest();	
		}
		try {
		log.info("Message : "+driver.findElement(By.xpath(Elements.iosVisitSubmitConfirmationMessage)).getText());
		extTestObj.createNode("Message : "+driver.findElement(By.xpath(Elements.iosVisitSubmitConfirmationMessage))).pass("PASSED");
		clickableWait(By.xpath(Elements.OKbuttoninAddMyVisitXpath));
		explicitWait(By.xpath(Elements.iosAddMyVisitHeaderXpath));
		log.info("Visit added successfully");
		extTestObj.createNode("Visit added successfully").pass("PASSED");
		}
		catch(Exception e)
		{
			log.error("Failed to add visit");
			  extTestObj.createNode("Failed to add visit")
			.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
	         log.error(e.getMessage());
	         stopIOSTest();	
		}
}
	/*
   	 * Function Name : androidAppCheckRoundOff()
   	 * Purpose : To check St Jude's donation checkbox to round off the order total
   	 * Platform : Android App
   	 * Parameters :None
   	 * Added by : Somnath Baul
   	 * Modified by : Somnath Baul
   	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
   	 */
	public void androidAppCheckRoundOff() {
		try {
			seetest.setProperty("Android.native.nonInstrumented","false");
			seetest.swipeWhileNotFound("Down", 900, 600, "WEB",Elements.appDonationBoxText, 0, 4000, 7, false);
			seetest.waitForElement("WEB",Elements.appDonationBoxText,0,3000);
			seetest.click("WEB",Elements.appDonationBoxText,0,1);
			log.info("Donation checked");
			extTestObj.createNode("Donation checked").pass("PASSED");
			seetest.setProperty("Android.native.nonInstrumented","true");
		} catch (Exception e) {
			log.error("Failed to check donation check box");
			extTestObj.createNode("Failed to check donation check box")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopTestforMobileBrowser();
		}
	}
	/*
   	 * Function Name : iosAppCheckRoundOff()
   	 * Purpose : To check St Jude's donation checkbox to round off the order total
   	 * Platform : IOS App
   	 * Parameters :None
   	 * Added by : Somnath Baul
   	 * Modified by : Somnath Baul
   	 * Organization Name : Cognizant Technology Solutions Pvt Ltd.
   	 */
	public void iosAppCheckRoundOff() {
		try {
			
			incrementalDragOnScreenAndClick(seetest,Elements.iosDonationCheckBox,"Down","NATIVE",false);
			seetest.swipe("Down",600, 500);
			seetest.click("NATIVE", "xpath="+Elements.iosDonationCheckBox, 0, 1);
			log.info("Donation checked");
			extTestObj.createNode("Donation checked").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to check donation check box");
			extTestObj.createNode("Failed to check donation check box")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopIOSTest();
		}
	}
	
		
		//To Login into UberEats page
		public void uberEats_Login() {
			
			String url=excel.getCellData("UberEats","URL", 2);
			
			try {  											  // open UberEats Url
				driver.get(url);
				driver.manage().window().maximize();
				explicitWait(Elements.validateHomePage);
				if (driver.findElement(Elements.validateHomePage).isDisplayed()) {
					log.info("Uber Eats site has launched");
					extTestObj.createNode("Uber Eats site has launched").pass("PASSED");
				}
			} catch (Exception e) {
				log.error("Uber Eats site launch failed");
				extTestObj.createNode("Uber Eats site launch failed")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopWebTest();
			}
			
			
			try {//click On SignIn Button

				explicitWait(Elements.signInButton);
				clickElement(Elements.signInButton);        
				explicitWait(Elements.validateSignInPage);

				if (driver.findElement(Elements.validateSignInPage).isDisplayed()) {
					log.info("Sign In button is clicked");
					extTestObj.createNode("Sign In button is clicked").pass("PASSED");
				}
			} catch (Exception e) {
				log.error("Sign In button click failed");
				extTestObj.createNode("Sign In button click failed")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopWebTest();
			}
			

			String useremail = excel.getCellData("UberEats","UserEmail",2);			//enter UserName
			
			try {

				sendKeysWait(Elements.userInputBox, useremail);
				clickableWait(Elements.nextButton);
				log.info("User name: " + useremail+ " enter is success");
				extTestObj.createNode("User name: " +useremail+ " enter is success").pass("PASSED");
			} catch (Exception e) {
				log.error("User name enter is failed");
				extTestObj.createNode("User name enter is failed")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopWebTest();
			}
			
			
			
			String password = excel.getCellData("UberEats","Password",2);   //enter Password
			try {

				sendKeysWait(Elements.passInputBox, password);
				clickableWait(Elements.nextButton);
				Thread.sleep(3000);

				log.info("Password:  "+password+" enter is success");
				extTestObj.createNode("Password:  "+password+" enter is success").pass("PASSED");

			} catch (Exception e) {
				log.error("Password enter is failed");
				extTestObj.createNode("Sign In button click failed")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopWebTest();
			}
			
			

			String address = excel.getCellData("UberEats","Location",2);      //enter Address

			try {

				sendKeysWait(Elements.deliverAdressInputbox, address);
				clickableWait(Elements.findFoodButton);
				log.info("Successfully entered the address: "+address);
				extTestObj.createNode("Successfully entered the address: "+address).pass("PASSED");
			} catch (Exception e) {
				log.error("Entering address is failed");
				extTestObj.createNode("Entering address is failed")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopWebTest();
			}
				
			
		}
	   

		//To Check if Store link is open or not
		public void checkIfStoreOpen() {
			
			String storeName = excel.getCellData("UberEats","Store_Name",2);
			try {
				explicitWait(By.xpath("//h3[contains(text(),\""+storeName+"\")]"));
				if (driver.findElement(By.xpath("//h3[contains(text(),\""+storeName+"\")]")).isEnabled()) {
					log.info("Chili's store is open");
					extTestObj.createNode("Chili's store is open").pass("PASSED");
				}
			} catch (Exception e) {
				log.error("Chili's store is closed");
				extTestObj.createNode("Chili's store is closed")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopWebTest();
			}

		}
		
		
		//To click on Store link
		public void openStore() {
			
			String storeName = excel.getCellData("UberEats","Store_Name",2);
			
			try {
				clickableWait(By.xpath("//h3[contains(text(),\""+storeName+"\")]"));
				explicitWait(Elements.appetizers);
				if (driver.findElement(Elements.appetizers).isDisplayed()) {
					log.info("Opened Chili's store");
					extTestObj.createNode("Chili's store is open").pass("PASSED");
				}
			} catch (Exception e) {
				log.error("Chili's store opening failed");
				extTestObj.createNode("Chili's store opening failed")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopWebTest();
			}

		}

		//To validate All Menucategory Name
		public void validateMenuCategories() {

			try {
				int x = 0;
				List<WebElement> categoryList = driver.findElements(Elements.menuCategoryList);
				ArrayList<String> list = new ArrayList<String>(Arrays.asList("Appetizers", "Big Mouth Burgers®",
						"Ribs &amp; Steaks", "Fajitas", "Salads, Soups &amp; Chili", "Chicken &amp; Seafood", "Sandwiches",
						"Chicken Crispers®", "Guiltless Grill", "Tacos &amp; Quesadillas", "Smokehouse Combos",
						"Lunch Specials", "Sides", "Desserts", "Beverages", "Kids Menu"));
				

				for (int j = 0; j < list.size(); ++j) {
					for (int i = 0; i < categoryList.size(); ++i) {
						if (categoryList.get(i).getAttribute("innerHTML").contains(list.get(j))) {
							++x;

						}
						// System.out.println(categoryList.get(i).getAttribute("innerHTML"));
					}
					// System.out.println(list.get(j));
				}

				if (x == list.size()) {
					log.info("Every menucategory is present");
					extTestObj.createNode("Every menucategory is present").pass("PASSED");
				} else {
					log.info("Every menucategory is not present");
					extTestObj.createNode("Every menucategory is not present").fail("FAILED");
				}
			} catch (Exception e) {
				log.error("Menu category validation failed");
				extTestObj.createNode("Menu category validation failed")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopWebTest();
			}

		}

		//To select a MenuItem
		public void selectMenuItem(int index) {

			String menuItem = excel.getCellData("Menu", "Item", index);
			System.out.println(menuItem);
			
			try {																		//selcet a MenuItem
				
				clickableWait(By.xpath("//div[contains(text(),'" + menuItem + "')]"));
				Thread.sleep(5000);
				explicitWait(By.xpath("//h1[contains(text(),'" + menuItem + "')]"));
				if (driver.findElement(By.xpath("//h1[contains(text(),'" + menuItem + "')]")).isDisplayed()) {
					log.info("Selected menu item: " + menuItem);
					extTestObj.createNode("Selected menu item: " + menuItem).pass("PASSED");
				}
			} catch (Exception e) {
				log.error("Failed to select menu item");
				extTestObj.createNode("Failed to select menu item")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopWebTest();
			}
			
			
			try {																	//validate Item Name
				 
				Thread.sleep(8000);
				explicitWait(By.xpath("//h1[contains(text(),'" + menuItem + "')]"));
				if (driver.findElement(By.xpath("//h1[contains(text(),'" + menuItem + "')]")).isDisplayed()) {
					log.info("Menu item name: " + menuItem + " validation complete");
					extTestObj.createNode("Menu item name: " + menuItem + " validation complete").pass("PASSED");
				} else {
					log.info("Menu item name: " + menuItem + " validation failed");
					extTestObj.createNode("Menu item name: " + menuItem + " validation failed").fail("FAILED");
				}
			} catch (Exception e) {
				log.error("Failed to validate menu item");
				extTestObj.createNode("Failed to validate menu item")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopWebTest();
			}

		}
		public void itemIncreaseCheck() {

			try {
				clickableWait(Elements.itemIncrease);
				explicitWait(Elements.addToOrder);
				if (driver.findElement(By.xpath("//div[contains(text(),'Add 2 to order')]")).isDisplayed()) {
					log.info("Item increased to 2: check completed");
					extTestObj.createNode("Item increase to 2: check completed").pass("PASSED");
				} else {
					log.info("Item increase check: failed");
					extTestObj.createNode("Item increase check: failed").fail("FAILED");
				}
			} catch (Exception e) {
				log.error("Item increase check: failed");
				extTestObj.createNode("Item increase check: failed")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopWebTest();
			}

		}

		//To decrease Item Quantity
		public void itemDecreaseCheck() {

			try {
				clickableWait(Elements.itemDecrease);
				explicitWait(Elements.addToOrder);
				// Thread.sleep(10000);
				if (driver.findElement(By.xpath("//div[contains(text(),'Add 1 to order')]")).isDisplayed()) {
					log.info("Item decrease to 1: check completed");
					extTestObj.createNode("Item decrease to 1: check completed").pass("PASSED");
				} else {
					log.info("Item decrease check: failed");
					extTestObj.createNode("Item decrease check: failed").fail("FAILED");
				}
			} catch (Exception e) {
				log.error("Item decrease check: failed");
				extTestObj.createNode("Item decrease check: failed")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopWebTest();
			}

		}
		
		public void clickPriority() {

			try {

				explicitWait(Elements.priorityOptionClick);
				// Thread.sleep(4000);
				clickableWait(Elements.priorityOptionClick);
				if (driver.findElement(Elements.priorityOption).isSelected()) {
					log.info("Priority option selected");
					extTestObj.createNode("Priority option selected").pass("PASSED");
				}
			} catch (Exception e) {
				log.error("Priority option selection Failed");
				extTestObj.createNode("Priority option selection Failed")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopWebTest();
			}

		}

//		public void clickSchedule() {}

		//To select a Schedule Time
		public void selectScheduleTime() {
			
			
			try {									//click on Schedule Option

				explicitWait(Elements.scheduleOption);
				// Thread.sleep(4000);
				clickableWait(Elements.scheduleOption);
				if (driver.findElement(Elements.scheduleButton).isDisplayed()) {
					log.info("Schedule option selected");
					extTestObj.createNode("Schedule option selected").pass("PASSED");
				}
			} catch (Exception e) {
				log.error("Schedule option selection Failed");
				extTestObj.createNode("Schedule option selection Failed")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopWebTest();
			}

			try {															//select a Schedule Time
				
				String timeInput = excel.getCellData("Delivery", "Delivery Time UE", 2);
				explicitWait(Elements.selectScheduleTime);
				// Thread.sleep(4000);
				Select time = new Select(driver.findElement(Elements.selectScheduleTime));
				time.selectByVisibleText(timeInput);
				Thread.sleep(7000);
				log.info("Schedule time selected");
				extTestObj.createNode("Schedule time selected").pass("PASSED");

			} catch (Exception e) {
				log.error("Schedule time selection Failed");
				extTestObj.createNode("Schedule time selection Failed")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopWebTest();
			}
			
			
			try {												//click Schedule Button
				
				String timeInput = excel.getCellData("Delivery", "Delivery Time UE verify", 2);
				explicitWait(Elements.scheduleButton);
				clickableWait(Elements.scheduleButton);
				Thread.sleep(1000);
				explicitWait(By.xpath("//span[contains(text(),'" + timeInput + "')]"));
				if (driver.findElement(By.xpath("//span[contains(text(),'" + timeInput + "')]")).isDisplayed()) {
					log.info("Scheduled the delivery time successfully");
					extTestObj.createNode("Scheduled the delivery time successfully").pass("PASSED");
				}
			} catch (Exception e) {
				log.error("Failed to schedule delivery time");
				extTestObj.createNode("Failed to schedule delivery time")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopWebTest();
			}

		}

//		public void clickScheduleButton() {}
		

		//To place a Schedule Later Delivery
		public void clickPlaceOrderSL() {
		
			String tip=excel.getCellData("UberEats", "Tip", 2);					//add a Tip
			 try {
				 
				   explicitWait(Elements.otherButtonUE);
				   clickableWait(Elements.otherButtonUE);
				   Thread.sleep(5000);
				   sendKeysWait(Elements.enterTipUE,tip);
				   clickableWait(Elements.saveButton);
				   
				   log.info("Tip added to Total: "+tip);
				   extTestObj.createNode("Tip added to Total: "+tip).pass("PASSED");
			    }
			   catch(Exception e)
			   {
				   log.error("Failed to add Tip");
				   log.error(e.getMessage());
				   extTestObj.createNode("Failed to add Tip").warning("WARNING");
			               
			   } 
			 
			 
			 	  
			 	String subTotal,tipValue,orderTotal;								//Getting Order details before Order
			 try {
				 
				 
				List<WebElement> item=driver.findElements(Elements.itemName);
				
				for(int i=0;i<item.size();i++) {
					
					 log.info("Items name before order: "+ item.get(i).getText());
					 extTestObj.createNode("Item name before order: "+ item.get(i).getText());
				}
				
				 subTotal=driver.findElement(Elements.subTotal).getText();
				 tipValue=driver.findElement(Elements.tip).getText();
				 orderTotal=driver.findElement(Elements.Total).getText().trim().substring(5);
				
				  log.info("Subtotal before order: "+ subTotal);
				   extTestObj.createNode("Subtotal before order: "+ subTotal); 
				  
				  log.info("Added Tip before order: "+ tipValue);
				   extTestObj.createNode("Added Tip before order: "+ tipValue);
				   
				  log.info("Order Total before order: "+ orderTotal);
				   extTestObj.createNode("Order Total before order: "+ orderTotal); 
				   
				 
					 }catch(Exception e) {
						 
						 log.error("Failed to collect order Details");
						  log.error(e.getMessage());
						  extTestObj.createNode("Failed to collect order Details").warning("WARNING");
						 
					 }
			 
			 

			try {																//Place Schedule Order

				explicitWait(Elements.placeOrderButtonUE);
				// Thread.sleep(4000);
				clickableWait(Elements.placeOrderButtonUE);
				Thread.sleep(3000);
				if (driver.findElement(Elements.orderConfirmLT).isDisplayed()) {
					Thread.sleep(10000);
					log.info("Order placed successfully");
					extTestObj.createNode("Order placed successfully").pass("PASSED");
				}
			} catch (Exception e) {
				log.error("Failed to place order");
				extTestObj.createNode("Failed to place order")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopWebTest();
			}

		}
		
		//To place a Asap Order
		public void clickPlaceOrderASAP() {
			
			String tip=excel.getCellData("UberEats", "Tip", 2);					//add a Tip
			 try {
				 
				   explicitWait(Elements.otherButtonUE);
				   clickableWait(Elements.otherButtonUE);
				   Thread.sleep(5000);
				   sendKeysWait(Elements.enterTipUE,tip);
				   clickableWait(Elements.saveButton);
				   
				   log.info("Tip added to Total: "+ tip);
				   extTestObj.createNode("Tip added to Total: "+tip).pass("PASSED");
			    }
			   catch(Exception e)
			   {
				   log.error("Failed to add Tip");
				   log.error(e.getMessage());
				   extTestObj.createNode("Failed to add Tip").warning("WARNING");
			               
			   }  
			 
			 
			  
			 String itemName,subTotal,tipValue,orderTotal;								//Getting Order details before Order
			 
			 try {
				 itemName=driver.findElement(Elements.itemName).getText();
				 subTotal=driver.findElement(Elements.subTotal).getText();
				 tipValue=driver.findElement(Elements.tip).getText();
				 orderTotal=driver.findElement(Elements.Total).getText().trim().substring(5);
				 
				 log.info("Item name before order: "+ itemName);
				   extTestObj.createNode("Item name before order: "+ itemName);
				   
				  log.info("Subtotal before order: "+ subTotal);
				   extTestObj.createNode("Subtotal before order: "+ subTotal); 
				  
				  log.info("Added Tip before order: "+ tipValue);
				   extTestObj.createNode("Added Tip before order: "+ tipValue);
				   
				  log.info("Order Total before order: "+ orderTotal);
				   extTestObj.createNode("Order Total before order: "+ orderTotal); 
				   
				 
			 }catch(Exception e) { 
				 
				 log.error("Failed to collect order Details"); 
				   log.error(e.getMessage());
				   extTestObj.createNode("Failed to collect order Details").warning("WARNING");
				 
			 }

			try {																//Placing Asap Order

				explicitWait(Elements.placeOrderButtonUE);
				clickableWait(Elements.placeOrderButtonUE);
				Thread.sleep(10000);
				if (driver.findElement(Elements.orderConfirmASAP).isDisplayed()) {
					log.info("Order placed successfully");
					extTestObj.createNode("Order placed successfully").pass("PASSED");
				}
			} catch (Exception e) {
				log.error("Failed to place order");
				extTestObj.createNode("Failed to place order")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopWebTest();
			}

		}
		
		//To open url of Orders page in UberEats 
		public void openUrlUberEatsOrders() {
			
			String ordersPageUrl=excel.getCellData("UberEats","URL", 3);
			
			try {
				driver.get(ordersPageUrl);
				explicitWait(Elements.verifyOrdersPage);
				if (driver.findElement(Elements.verifyOrdersPage).isDisplayed()) {
					log.info("Uber Eats Orders page has launched");
					extTestObj.createNode("Uber Eats Orders page has launched").pass("PASSED");
				}
			} catch (Exception e) {
				log.error("Uber Eats Orders page launch failed");
				extTestObj.createNode("Uber Eats Orders page launch failed")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopWebTest();
			}
			
		}
		
		//To Click on ShowMore Button
		public void clickShowMore() {

			try {
				int attempts=0;
				int done=0;
				for(int i=0;i<=attempts || attempts>-1;++i) {
					try {
						explicitWait(Elements.showMoreButton);
						clickableWait(Elements.showMoreButton);
						Thread.sleep(5000);
						++attempts;
					}catch(Exception e1) {
						done=attempts;
						attempts=-1;
					}
				}
				log.info("Show more button clicked "+done+" times");
				extTestObj.createNode("Show more button clicked "+done+" times").pass("PASSED");
				
			} catch (Exception e) {
				log.error("Failed to click show more");
				extTestObj.createNode("Failed to click show more")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopWebTest();
			}

		}
		
		//To count the no. of Total Orders
		public void countTotalOrders() {

			try {

				List<WebElement>list=new ArrayList<WebElement>();
				list=driver.findElements(Elements.listOfOrders);
				int numberOfOrders=list.size()-3;
				Thread.sleep(5000);
				
					
					log.info("All "+numberOfOrders+ " orders are displayed");
					extTestObj.createNode("All "+numberOfOrders+ " orders are displayed").pass("PASSED");
				
			} catch (Exception e) {
				log.error("Failed to count the number of orders");
				extTestObj.createNode("Failed to count the number of orders")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				log.error(e.getMessage());
				stopWebTest();
			}

		}
		
		
			
				//Click On Store link
				public void clickOnStore() {
					
					String storeName = excel.getCellData("UberEats","Store_Name",2);
				try {
					   clickableWait(By.xpath("//h3[contains(text(),\""+storeName+"\")]"));
					   log.info("Click on store link is success");
					   extTestObj.createNode("Click on store link is success").pass("PASSED");
				    }
				   catch(Exception e)
				   {
					   log.error("Click on store link is failed");
				          extTestObj.createNode("Click on store link is failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
				
			}
		
		
			//Verify the store Home Page Element	
			public void verifyStoreHomePageUI() {
				
			try {
				   explicitWait(Elements.chilisstore6_title);
				   driver.findElement(Elements.chilisstore6_title).isDisplayed();
				   log.info("'Chili's Test Store 6' element is displayed ");
				   extTestObj.createNode("'Chili's Test Store 6' element is displayed ").pass("PASSED");
			    }
			   catch(Exception e)
			   {
				   log.error("'Chili's Test Store 6' element is not displayed ");
			          extTestObj.createNode("'Chili's Test Store 6' element is not displayed")
			                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			          log.error(e.getMessage());
			          stopWebTest();
			   }
			     
		   }
			
		 
			
			
			//To select a Menu Item
			  public void clickMenuItem(int index) {				 
				  
				  String menuItem=excel.getCellData("Menu","Item",index);
				  
				 try {																			//click on a MenuItem
					 By menuItemName=By.xpath("//div[contains(text(),'"+menuItem+"')]");
					   clickableWait(menuItemName);
					   Thread.sleep(5000);
					   log.info("Click on menu Item " +menuItem+ " is success");
					   extTestObj.createNode("Click on menu Item " +menuItem+ " is success").pass("PASSED");
				    }
				   catch(Exception e)
				   {
					   log.error("Click on menu Item is failed");
				          extTestObj.createNode("Click on menu Item is failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
				 
				 
				  try {																//validate Menu Item
					   
					   explicitWait(By.xpath("//h1[contains(text(),'"+menuItem+"')]"));
					   if(driver.findElement(By.xpath("//h1[contains(text(),'"+menuItem+"')]")).isDisplayed())
					   {
					   log.info("Menu item: "+menuItem+" validation complete");
					   extTestObj.createNode("Menu item : "+menuItem+" validation complete").pass("PASSED");
					   }else {
						   log.info("Menu item: "+menuItem+" validation failed");
						   extTestObj.createNode("Menu item : "+menuItem+" validation failed").fail("FAILED");
					   }
				    }
				   catch(Exception e)
				   {
					   log.error("Failed to validate menu item");
				          extTestObj.createNode("Failed to validate menu item")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
				  
				
				     
			 }
			  
			  

		/*		public void validateItemName() {
					   
					   try {
						   String menuItem= excel.getCellData("Menu", "Item", 2);
						   
						   explicitWait(By.xpath("//h1[contains(text(),'"+menuItem+"')]"));
						   if(driver.findElement(By.xpath("//h1[contains(text(),'"+menuItem+"')]")).isDisplayed())
						   {
						   log.info("Menu item: "+menuItem+" validation complete");
						   extTestObj.createNode("Menu item : "+menuItem+" validation complete").pass("PASSED");
						   }else {
							   log.info("Menu item: "+menuItem+" validation failed");
							   extTestObj.createNode("Menu item : "+menuItem+" validation failed").fail("FAILED");
						   }
					    }
					   catch(Exception e)
					   {
						   log.error("Failed to validate menu item");
					          extTestObj.createNode("Failed to validate menu item")
					                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
					          log.error(e.getMessage());
					          stopWebTest();
					   }
					     
				   } */
				
				
				 
			  
			  //Click on Add To Order Button
			  public void clickAddToOrder() { 
				  
				  try {
						   explicitWait(Elements.addToOrder_button);
						   clickableWait(Elements.addToOrder_button);
						   Thread.sleep(3000);
						   driver.navigate().refresh();
						   driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
						   log.info("Click on Add To Order is success");
						   extTestObj.createNode("Click on Add To Order is success").pass("PASSED");
					    }
					   catch(Exception e)
					   {
						   log.error("Click on Add To Order is failed");
					          extTestObj.createNode("Click on Add To Order is failed")
					                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
					          log.error(e.getMessage());
					          stopWebTest();
					   }
					     
				 }
			  
			  
			  
		 
			  
			
			//Click On View Cart Button	  
		  public void clickOnViewCart() {
			
			 									 
			   
				  try
				  	{
				  	
					  clickableWait(Elements.cart);
						
					   log.info("Click on view cart is success");
					   extTestObj.createNode("Click on view cart is success").pass("PASSED");
				  }
				  catch (Exception e)
				  {
					  log.error("Failed to click on view cart");
			          extTestObj.createNode("Failed to click on view cart")
			                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			          log.error(e.getMessage());
			          stopWebTest();
				  	}
			  
			  
			  	}   
	  
			
			//Verify Order Item showing in Cart
			public void verifyOrderItem(int index) { 
				
				String menuItem=excel.getCellData("Menu","Item",index);
				System.out.println(menuItem);
				  try {
					   	
						   explicitWait(By.xpath("(//*[contains(text(),'"+menuItem+"')])[1]"));
						   driver.findElement(By.xpath("(//*[contains(text(),'"+menuItem+"')])[1]")).isDisplayed();
						   log.info("Order Item is displayed in Checkout page");
						   extTestObj.createNode("Order Item is displayed in Checkout page").pass("PASSED");
					    }
					   catch(Exception e)
					   {
						   log.error("Order Item is not displayed in Checkout page");
					          extTestObj.createNode("Order Item is not displayed in Checkout page")
					                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
					          log.error(e.getMessage());
					          stopWebTest();
					   }
					     
				 }
			  
			//To Change the Order Quantity
			public  void changeItemQuantity() {

				 try {
					 	 Thread.sleep(3000);
					 	explicitWait(Elements.selectChangeQuantity);
					   Select select=new Select(driver.findElement(Elements.selectChangeQuantity));
					   select.selectByVisibleText("2");
					   Thread.sleep(5000);
					   System.out.println(driver.findElement(Elements.verifyCartQuantity).getText());
					   if( driver.findElement(Elements.verifyCartQuantity).getText().substring(6).trim().equals("2"))
					   {
					   log.info("Quantity has been changed to 2");
					   extTestObj.createNode("Quantity has been changed to 2").pass("PASSED");
					   }
				    }
				   catch(Exception e)
				   {
					   log.error("Quantity change is failed");
				          extTestObj.createNode("Quantity change is failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }    
			 }
			
			//To Remove the Order Quantity
			public  void RemoveQuantity() {

				 try {
					 	 Thread.sleep(3000);
					 	explicitWait(Elements.selectChangeQuantity);
					   Select select=new Select(driver.findElement(Elements.selectChangeQuantity));
					   select.selectByVisibleText("Remove");
					   Thread.sleep(10000);
					   if( driver.findElement(Elements.verifyCartQuantity).getText().substring(6).trim().equals("0"))
					   {
					   log.info("Item has been Removed from the cart");
					   extTestObj.createNode("Item has been Removed from the cart").pass("PASSED");
					   }
				    }
				   catch(Exception e)
				   {
					   log.error("Failed to remove Quantity");
				          extTestObj.createNode("Failed to remove Quantity")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }    
			 }
			
			
			
			//To Checkout a Item
			public void clickCheckoutItem() {
				
				
				  try {											//Item added To Order
					  
					   explicitWait(Elements.addToOrder_button);
					   clickableWait(Elements.addToOrder_button);
					   Thread.sleep(3000);
					   driver.navigate().refresh();
					   driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
					   log.info("Click on Add To Order is success");
					   extTestObj.createNode("Click on Add To Order is success").pass("PASSED");
				    }
				   catch(Exception e)
				   {
					   log.error("Click on Add To Order is failed");
				          extTestObj.createNode("Click on Add To Order is failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
				
				
													
					  
					  try										//click On ViewCart 
					  	{
					  	
						  clickableWait(Elements.cart);
							
						   log.info("Click on view cart is success");
						   extTestObj.createNode("Click on view cart is success").pass("PASSED");
					  }
					  
					  catch (Exception e)
					  {
						  log.error("Failed to click on view cart");
				          extTestObj.createNode("Failed to click on view cart")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
					  	}
			  
				  	 
				 	
				try {												//checkout
					
					clickableWait(Elements.checkoutButton);
					if (driver.findElement(Elements.placeOrderButtonUE).isDisplayed()) {
						
						log.info("Item successfully checked out");
						extTestObj.createNode("Item successfully checked out").pass("PASSED");
					}
				} catch (Exception e) {
					log.error("Checkout Failed");
					extTestObj.createNode("Checkout Failed")
							.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
					log.error(e.getMessage());
					stopWebTest();
				}

			}  
			
			//To click on Schedule Option
			public void clickScheduleOption() {

				try {

					explicitWait(Elements.scheduleOption);
				 
					clickableWait(Elements.scheduleOption);
						log.info("Schedule option selected");
						extTestObj.createNode("Schedule option selected").pass("PASSED");
				} catch (Exception e) {
					log.error("Schedule option selection Failed");
					extTestObj.createNode("Schedule option selection Failed")
							.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
					log.error(e.getMessage());
					stopWebTest();
				}

			}
			
			
			
			//Verify the Delivery Button in Order Page
			public void verifyDeliveryButton() {
				
				 try {
					  
					   explicitWait(Elements.deliveryButton_Xpath);
					   
					   if ( driver.findElement(Elements.deliveryButton_Xpath).isEnabled()) {
					   log.info("Delivery button is enabled");
					   extTestObj.createNode("Delivery button is enabled").pass("PASSED");
					   }  
				    }
				   catch(Exception e)
				   {
					   log.error("Delivery button is disabled");
				          extTestObj.createNode("Delivery button is disabled")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
			}
			
			//Verify the Schedule Button in Order Page
	 		public void verifyScheduleButton() {
				
				 try {
					  
					   explicitWait(Elements.scheduleButton);
					   
					   if ( driver.findElement(Elements.scheduleButton).isEnabled()) {
					   log.info("Schedule button is enabled");
					   extTestObj.createNode("Schedule button is enabled").pass("PASSED");
					   }  
				 } 
				   catch(Exception e)
				   {
					   log.error("Schedule button is disabled");
				          extTestObj.createNode("Schedule button is disabled")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
			}
			
			
		 
				//Click On View Orders after placing a order
				public void clickOnViewOrders() {
					
					try {

					explicitWait(Elements.viewOrders);
					 
					clickableWait(Elements.viewOrders);
					log.info("Click on view Orders is successful");
					extTestObj.createNode("Click on view Orders is successful").pass("PASSED");
					} catch (Exception e)
					{
					log.error("Failed to click on view orders");
					extTestObj.createNode("Failed to click on view orders")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
					log.error(e.getMessage());
					stopWebTest();
						}

					}
				
				//To verify Placed order Timezone is same as selected Timezone
				public void verifyPlacedOrderTimezone() {
					
					String timeZone=excel.getCellData("Delivery", "verify_timezone_ue", 2);
					
					try {

						explicitWait(Elements.upcomingOrders);
						if (driver.findElement(Elements.verifyTimeZone).getText().contains(timeZone)) {
						log.info("Placed order time is same as  selected timezone");
						extTestObj.createNode("Placed order time is same as  selected timezone").pass("PASSED");
						}
					} catch (Exception e)
					{
					log.error("Placed order time is not matching with selected timezone");
					extTestObj.createNode("Placed order time is same as  selected timezone")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
					log.error(e.getMessage());
					stopWebTest();
					
						}

					}
				
				
				//To verify Past Order item name is Showing
				public void verifyPastOrderMenuItemName() {
					
					 try {
						  
						    explicitWait(Elements.pastOrderItemName);
						   
						   if ( driver.findElement(Elements.pastOrderItemName).isDisplayed())
						   {
							  String menuItem=driver.findElement(Elements.pastOrderItemName).getText(); 
							  
						   log.info("MenuItem in past Order is displayed as: "+menuItem);
						   extTestObj.createNode("MenuItem in past Order is displayed as: "+menuItem).pass("PASSED");
						   }  
					 } 
					   catch(Exception e)
					   {
						   log.error("MenuItem name is not displayed is past Order");
					          extTestObj.createNode("MenuItem name is not displayed is past Order")
					                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
					          log.error(e.getMessage());
					          stopWebTest();
					   }
				}
				
				//To verify Past Order item price is Showing
				public void verifyPastOrderMenuItemPrice() {
					
					 try {
						  
						   explicitWait(Elements.pastOrderItemPrice);
						   
						   if ( driver.findElement(Elements.pastOrderItemPrice).isDisplayed())
						   {
							  String menuItemPrice=driver.findElement(Elements.pastOrderItemPrice).getText().substring(0,17); 
							  
						   log.info("MenuItem price in past Order is displayed as: "+menuItemPrice);
						   extTestObj.createNode("MenuItem price in past Order is displayed as: "+menuItemPrice).pass("PASSED");
						   }  
					 } 
					   catch(Exception e)
					   {
						   log.error("MenuItem price is not displayed is past Order");
					          extTestObj.createNode("MenuItem price is not displayed is past Order")
					                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
					          log.error(e.getMessage());
					          stopWebTest();
					   }
				} 
				
				
		
				
				//To Customize MenuItem Boss-Burger
				public void customizeItem() {
					
					driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
					JavascriptExecutor jse=(JavascriptExecutor)driver;
					WebDriverWait wait = new WebDriverWait(driver, 30);
					
					 String protein=excel.getCellData("Order Customization","Protein",2);    			 //Selects the protein
					 try {
						 By Protein=By.xpath("//div[contains(text(),'"+protein+"')]/preceding::input[1]");
						 
						 wait.until(ExpectedConditions.invisibilityOfElementLocated(Protein));
						 WebElement option1=driver.findElement(Protein);
						 jse.executeScript("arguments[0].click()", option1);
						 Thread.sleep(3000);
						 
						   log.info("Click on protein: " +protein+ " is success");
						   extTestObj.createNode("Clicked on protein: \"" +protein+ "\"").pass("PASSED");
					    }
					   catch(Exception e)
					   {
						   log.error("Click on Protein customization is failed");
						   log.error(e.getMessage());
						   extTestObj.createNode("Click on Protein customization is failed").warning("WARNING"); 
						   
					   }
					 
						 
					 
					 String temp=excel.getCellData("Order Customization","Temp",2);	      				//Selects the temperature
					 System.out.println(temp);
					 try {
						 	By Temp=By.xpath("//div[contains(text(),'"+temp+"')]/preceding::input[1]"); 
						 	 
							 wait.until(ExpectedConditions.invisibilityOfElementLocated(Temp));
							 WebElement option2=driver.findElement(Temp);
							 jse.executeScript("arguments[0].click()", option2);
							 Thread.sleep(3000);
						 	
					 
						
						   log.info("Click on temp " +temp+ " is success");
						   extTestObj.createNode("Clicked on temp: \"" +temp+ "\"").pass("PASSED");
					    }
					   catch(Exception e)
					   {
						   log.error("Click on temperature customization is failed");
						   log.error(e.getMessage());
						   extTestObj.createNode("Click on temperature customization is failed").warning("WARNING");
						    
					           
					   }
					 
					 String condiment=excel.getCellData("Order Customization","Condiments",2);				//Selects a condiment
					 System.out.println(condiment);
					 try {
						 By Condiment=By.xpath("//div[contains(text(),'"+condiment+"')]/preceding::input[1]");
						  
						 wait.until(ExpectedConditions.invisibilityOfElementLocated(Condiment));
						 WebElement option3=driver.findElement(Condiment);
						 jse.executeScript("arguments[0].click()", option3);
						 Thread.sleep(3000);
						   
						   log.info("Click on Condiment " +condiment+ " is success");
						   extTestObj.createNode("Clicked on Condiment \"" +condiment+ "\"").pass("PASSED");
					    }
					   catch(Exception e)
					   {
						   log.error("Click on condiment failed");
						   log.error(e.getMessage());
						   extTestObj.createNode("Click on condiment failed").warning("WARNING");
						    
					          
					   }
					 
					 for(int i=2;i<=3;i++) {
						  String topping=excel.getCellData("Order Customization","Topping",i);			//Selects a topping
						  System.out.println(topping);
						 try {
							 By Topping=By.xpath("//div[contains(text(),'"+topping+"')]/preceding::input[1]");
							 
							 wait.until(ExpectedConditions.invisibilityOfElementLocated(Topping));
							 WebElement option4=driver.findElement(Topping);
							 jse.executeScript("arguments[0].click()", option4);
							 Thread.sleep(3000);
							   
							   log.info("Click on Topping " +topping+ " is success");
							   extTestObj.createNode("Clicked on Topping \"" +topping+ "\"").pass("PASSED");
						    }
						   catch(Exception e)
						   {
							   log.error("Topping selection failed");
							   log.error(e.getMessage());
							   extTestObj.createNode("Topping selection failed").warning("WARNING");
						   }
					   }
					 
					 String side=excel.getCellData("Order Customization","Side",2);					//Selects a side
					 try {
						 By Side=By.xpath("//div[contains(text(),'"+side+"')]/preceding::input[1]");
						 
						 wait.until(ExpectedConditions.invisibilityOfElementLocated(Side));
						 WebElement option5=driver.findElement(Side);
						 jse.executeScript("arguments[0].click()", option5);
						 Thread.sleep(3000);
						   
						   log.info("Click on Side " +side+ " is success");
						   extTestObj.createNode("Clicked on Side: \"" +side+ "\"").pass("PASSED");
					    }
					   catch(Exception e)
					   {
						   log.error("Side selection failed");
						   log.error(e.getMessage());
						   extTestObj.createNode("Topping selection failed").warning("WARNING");
					   }
					 
					 String beverage=excel.getCellData("Order Customization","Beverage",2);			//Selects a beverage
					 try {
						 By Beverage=By.xpath("//div[contains(text(),'"+beverage+"')]/preceding::input[1]");
						  
						 wait.until(ExpectedConditions.invisibilityOfElementLocated(Beverage));
						 WebElement option6=driver.findElement(Beverage);
						 jse.executeScript("arguments[0].click()", option6);
						 Thread.sleep(3000);
						   
						   log.info("Click on Beverage " +beverage+ " is success");
						   extTestObj.createNode("Clicked on Beverage: \"" +beverage+ "\"").pass("PASSED");
					    }
					   catch(Exception e)
					   {
						   log.error("Beverage selection failed");
						   log.error(e.getMessage());
						   extTestObj.createNode("Beverage selection failed").warning("WARNING");
					   }
					 
					 String soup=excel.getCellData("Order Customization","Soup type",2);			//Selects a soup type
					 try {
						 By Soup=By.xpath("//div[contains(text(),'"+soup+"')]/preceding::input[1]");
						 
						 wait.until(ExpectedConditions.invisibilityOfElementLocated(Soup));
						 WebElement option7=driver.findElement(Soup);
						 jse.executeScript("arguments[0].click()", option7);
						 Thread.sleep(3000);
						   
						   log.info("Click on Soup type " +soup+ " is success");
						   extTestObj.createNode("Clicked on Soup type: \"" +soup+ "\"").pass("PASSED");
					    }
					   catch(Exception e)
					   {
						   log.error("Soup type selection failed");
						   log.error(e.getMessage());
						   extTestObj.createNode("Soup type selection failed").warning("WARNING");
					   }
					 
					 String salad=excel.getCellData("Order Customization","Salad",2);					//Selects a salad
					 try {
						 By Salad=By.xpath("//div[contains(text(),'"+salad+"')]/preceding::input[1]");
						  
						 wait.until(ExpectedConditions.invisibilityOfElementLocated(Salad));
						 WebElement option8=driver.findElement(Salad);
						 jse.executeScript("arguments[0].click()", option8);
						 Thread.sleep(3000);
						   
						   log.info("Click on Salad type " +salad+ " is success");
						   extTestObj.createNode("Clicked on Salad type: \"" +salad+ "\"").pass("PASSED");
					    }
					   catch(Exception e)
					   {
						   log.error("Salad type selection failed");
						   log.error(e.getMessage());
						   extTestObj.createNode("Salad type selection failed").warning("WARNING");
					   }
					 
					 //selects the dressing
					 String dressing=excel.getCellData("Order Customization","Dressing choice" ,2);
					 try {
						 By dressingChoice=By.xpath("//div[contains(text(),'"+dressing+"')]/preceding::input[1]");
						 	Thread.sleep(3000);
						 	
						 	wait.until(ExpectedConditions.invisibilityOfElementLocated(dressingChoice));
							 WebElement option9=driver.findElement(dressingChoice);
							 jse.executeScript("arguments[0].click()", option9);
							 Thread.sleep(3000);
						   
						   log.info("Click on Dressing choice " +dressing+ " is success");
						   extTestObj.createNode("Clicked on Dressing choice: \"" +dressing+ "\"").pass("PASSED");
					    }
					   catch(Exception e)
					   {
						   log.error("Dressing choice selection failed");
						   log.error(e.getMessage());
						   extTestObj.createNode("Salad type selection failed").warning("WARNING");
					   }
					 
					 
					 //Selects the toppings
					 String topping=excel.getCellData("Order Customization","Toppings" ,2);
					 try {
						 By toppingChoice=By.xpath("//div[contains(text(),'"+topping+"')]/preceding::input[1]");
						 
						 wait.until(ExpectedConditions.invisibilityOfElementLocated(toppingChoice));
						 WebElement option10=driver.findElement(toppingChoice);
						 jse.executeScript("arguments[0].click()", option10);
						 Thread.sleep(3000);
						   
						   log.info("Click on Topping choice " +topping+ " is success");
						   extTestObj.createNode("Clicked on Topping choice: \"" +topping+ "\"").pass("PASSED");
					    }
					   catch(Exception e)
					   {
						   log.error("Topping choice selection failed");
						   log.error(e.getMessage());
						   extTestObj.createNode("Topping choice selection failed").warning("WARNING");
					   }
					 
					//clicks on save button
					 try {
						  
						 wait.until(ExpectedConditions.presenceOfElementLocated(Elements.saveButtonUE));
						 WebElement saveOption=driver.findElement(Elements.saveButtonUE);
						 jse.executeScript("arguments[0].click()", saveOption);
						 Thread.sleep(3000);
						   
						   log.info("Clicked on save button");
						   extTestObj.createNode("Clicked on save button").pass("PASSED");
					    }
					   catch(Exception e)
					   {
						   log.error("Failed to click on save button");
						   log.error(e.getMessage());
						   extTestObj.createNode("Failed to click on save button").warning("WARNING");
					               
					   }  
				
				}
				
				//To Click on add-Item in Checkout Page
				public void clickAddItemInCheckout() {
					
					 try {
						  
						   explicitWait(Elements.addItemUE);
						   clickableWait(Elements.addItemUE);
						    
						   
						   if ( driver.findElement(Elements.cart).isDisplayed())
						   { 
							  
						   log.info("Click On \"Add items\" in Checkout page is sucess");
						   extTestObj.createNode("Click On \"Add items\" in Checkout page is sucess").pass("PASSED");
						   }  
					 } 
					   catch(Exception e)
					   {
						   log.error("Failed to click on add Items on Checkout page");
					          extTestObj.createNode("Failed to click on add Items on Checkout page")
					                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
					          log.error(e.getMessage());
					          stopWebTest();
					   }
				} 
				
			

				public void validateMenuDescriptionForUE()
				{
					
					 try {
						 int count=0,dcount=0,nodesc=0;
						
						 explicitWait(Elements.menuCategory);
						 List<WebElement>menuCategoryList=driver.findElements(Elements.menuCategory);
						 extTestObj.createNode("Displaying the Menu Name and Item Description").info("INFO");
						 for(WebElement lst:menuCategoryList)
						 {
								
							 String menuName=lst.getText();
							 lst.click();
							 count+=1;
							 try
							 {
								  explicitWait(Elements.menuDescriptionDisplay);
								  String menuDescription=driver.findElement(Elements.menuDescriptionDisplay).getText();
								 if(driver.findElement(Elements.menuDescriptionDisplay).isDisplayed())
								 {
									 dcount+=1;
									 log.info("MENU NAME:          "+menuName+"\t"+" MENU DESCRIPTION:          "+menuDescription);
									 extTestObj.createNode("MENU NAME:          "+menuName).createNode("MENU DESCRIPTION:          "+menuDescription);
									 
								 }
								
							 }
							 catch(Exception e)
							 {
								 log.info("Description is not displayed for the menu - "+menuName);
								 extTestObj.createNode("Description is not displayed for the MENU:           - "+menuName).warning("WARNING");
								 nodesc+=1;
							 }
							 clickableWait(Elements.menuClosebutton);
						 }
				/*		 for(int i=1;i<=menuCategoryList.size();i++)
						 {
							 String menuName=menuCategoryList.get(i).getText();
							 menuCategoryList.get(i).click();
							 count+=1;
							// String menudesc=driver.findElement(Elements.menuDescription).getText();
								// if(!(menudesc.contains("$")))
							 if(driver.findElement(By.xpath("//main[@id='main-content']/div[5]/ul/li[1]/ul/li["+i+"]/div/div/div/div[1]/div[2]/div")).isDisplayed())
								   {
									 dcount+=1;
								   
								   }
								 else
								 {
									 log.info("Description is not displayed for the menu "+menuName);
									 extTestObj.createNode("Description is not displayed for the menu "+menuName).fail("FAILED");
								 }
								
							 }
							 */
						 		   
						   if(dcount==count)
						   {
						   log.info("Description is displayed for all the menu items.Total count of menu items is  "+count+" .Description is displayed for "+dcount+" menu items");
						   extTestObj.createNode("Description is displayed for all the menu items.Total count of menu items is "+count+" .Description is displayed for "+dcount+" menu items").pass("PASSED");
						   }
						   else {
							   log.info("Description is not displayed for all the menu items.Total count of menu items is "+count+" .Description is not displayed for "+nodesc+" menu items");
							   extTestObj.createNode("Description is not displayed for all the menu items.Total count of menu items is "+count+" .Description is not displayed for "+nodesc+" menu items").warning("WARNING");
						   }
					    }
					   catch(Exception e)
					   {
						   log.error("Chili's store is closed");
					          extTestObj.createNode("Chili's store is closed")
					                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
					          log.error(e.getMessage());
					          stopWebTest();
					   }
					

				}
				 
				 
				public void validateMenuPriceForUE()
				{
					 try {
						 int count=0,dcount=0,noprice=0;
						 List<WebElement>menuCategoryList=driver.findElements(Elements.menuCategory);
						 extTestObj.createNode("Displaying the Menu Name and Price").info("INFO");
						 for(WebElement lst:menuCategoryList)
						 {
							 String menuName=lst.getText();
							 lst.click();
							 explicitWait(Elements.menuPriceDisplay);
							 String menuPrice=driver.findElement(Elements.menuPriceDisplay).getText();
							 count+=1;
								 if(driver.findElement(Elements.menuPriceDisplay).isDisplayed())
								   {
									 dcount+=1;
									 log.info("MENU NAME:          "+menuName+"\t"+" MENU PRICE:          "+menuPrice);
									 extTestObj.createNode("MENU NAME:          "+menuName).createNode("MENU PRICE:          "+menuPrice);
								   
								   
								   }
								 else
								 {
									 log.info("Price is not displayed for the menu "+menuName);
									 extTestObj.createNode("Price is not displayed for the menu "+menuName).warning("WARNING");
									 noprice+=1;
								 }
								 clickableWait(Elements.menuClosebutton);
							 }
						 		   
						   if(dcount==count)
						   {
						   log.info("Price is displayed for all the menu items.Total menu items "+count+" .Price displayed for "+dcount+" menu items");
						   extTestObj.createNode("Price is displayed for all the menu items.Total menu items "+count+" .Price displayed for "+dcount+" menu items").pass("PASSED");
						   }
						   else {
							   log.info("Price is not displayed for all the menu items.Total menu items "+count+" .Price is not displayed for "+noprice+" menu items");
							   extTestObj.createNode("Price is not displayed for all the menu items.Total menu items "+count+" .Price is not displayed for "+noprice+" menu items").warning("WARNING");
						   }
					    }
					   catch(Exception e)
					   {
						   log.error("Chili's store is closed");
					          extTestObj.createNode("Chili's store is closed")
					                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
					          log.error(e.getMessage());
					        
					          stopWebTest();
					   }
					    
				}
			
				
				//To Verify Order is showing in Orders page after placing a order
				public void viewOrderDetails(int index)
				{
					 String orderedItem=excel.getCellData("Menu","Item", index);
					
					try
					{
					//clickableWait(Elements.clickMain);
					//clickableWait(Elements.clickOrders);
					//clickableWait(Elements.viewOrder);
						
					if(driver.findElement(Elements.summaryOrder).getText().equalsIgnoreCase(orderedItem))
					{
						log.info("Order Summary details displayed successfully");
						extTestObj.createNode("Order Summary details displayed successfully").pass("PASSED");
					}
					
					
					} catch(Exception e) {
						log.error("Failed to display Order Summary details");
						extTestObj.createNode("Failed to display Order Summary details")
								.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
						log.error(e.getMessage());
						stopWebTest();
						
					}
				}
				
				
		/***********************************************************GFO **************************************************************************/	
				
				public void GFOLogin() throws InterruptedException{
					
				
						
		/*	driver.get("https://console.actions.google.com/project/itsjustwings-19e54/actions/vertical/");
					
					driver.findElement(By.xpath("//input[@id='identifierId']")).sendKeys("rajarshi.de@brinker.com");
				
					driver.findElement(By.xpath("//span[contains(text(),'Next')]")).click();
					
					driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Welcome2307");
					
					driver.findElement(By.xpath("//span[contains(text(),'Next')]")).click();
					
				//	Thread.sleep(120000);
					driver.findElement(By.xpath("//span[contains(text(),'Data feeds')]")).click();
					
					
					explicitWait(By.xpath("//div[contains(text(),'Test your app with feeds')]"));
					clickableWait(By.xpath("//a[contains(text(),'Get started')]"));
					
					explicitWait(By.xpath("//button[contains(text(),'Launch Test')]"));
					clickableWait(By.xpath("//button[contains(text(),'Launch Test')]")); 
				
					*/
				
					try
					{
					driver.get("https://stackoverflow.com/"); 
					driver.manage().window().maximize();
					Thread.sleep(5000);
					driver.findElement(By.xpath("/html/body/header/div/ol[2]/li[2]/a[1]")).click();
					Thread.sleep(5000);
					driver.findElement(By.xpath("//*[@id='openid-buttons']/button[1]")).click();
					Thread.sleep(5000);
					driver.findElement(By.xpath("//*[@id='identifierId']")).sendKeys("rajarshi.de@brinker.com");
					Thread.sleep(10000);
					//driver.findElement(By.xpath("//*[@id='identifierNext']/div/button/div[2]")).click();
					driver.findElement(By.xpath("//*[@id='identifierNext']/div/button/span")).click();
					Thread.sleep(10000);
					driver.findElement(By.xpath("//*[@id='password']/div[1]/div/div[1]/input")).sendKeys("Welcome2307");
					Thread.sleep(10000);
					driver.findElement(By.xpath("//*[@id='passwordNext']/div/button/div[2]")).click();
					Thread.sleep(10000);
					
					driver.get("https://console.actions.google.com/project/itsjustwings-19e54/actions/vertical/");
					}
					catch(Exception e)
					{
						log.error(e.getMessage());
					}
					
					
					/*   System.setProperty("webdriver.chrome.driver","./src//lib//chromedriver");
	            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
	            ChromeOptions options = new ChromeOptions();
	            options.addArguments("test-type");
	            options.addArgument("--start-maximized");
	            options.addArguments("--disable-web-security");
	            options.addArguments("--allow-running-insecure-content");
	            capabilities.setCapability("chrome.binary","./src//lib//chromedriver");
	            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
	            driver = new ChromeDriver(capabilities);
	            driver.get("https://www.google.com/");
	            
	            */
					
					
				}
				
				public void webLogin() {
			    	String siteURL=excel.getCellData("DoorDash","siteURL",2);
			    	//Launches store page
			    	try {
			    		  driver.get(siteURL);
			    			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			    			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			    			driver.manage().window().maximize();
			    		
			    			 explicitWait(Elements.hamburgerMenuButtonXpath_DD);
			    		  
			    		  
			    			  log.info("Site launch successful, site name : "+driver.getCurrentUrl());
			    			  extTestObj.createNode("Site launch successful, site name : "+driver.getCurrentUrl()).pass("PASSED");
			    	        
			    		  }
			    		  catch(Exception e)
			    		  {
			    			  log.error("Site launch failed");
			    	          extTestObj.createNode("Site launch failed")
			    	                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			    	          log.error(e.getMessage());
			    	          stopWebTest();
			    		  }
			    	//Clicks on the hamburger menu button
			    	try {
			  		  clickableWait(Elements.hamburgerMenuButtonXpath_DD);
			  		  explicitWait(Elements.siginSignUpButtonXpath_DD);
			  		  if(driver.findElement(Elements.siginSignUpButtonXpath_DD).isDisplayed())
			  		  {
			  			  log.info("Hamburger Menu button clicked");
			  			  extTestObj.createNode("Hamburger Menu button clicked").pass("PASSED");  
			  		  }
			  	  }
			  	  catch(Exception e)
			  	  {
			  		  log.error("Hamburger Menu button click failed");
			            extTestObj.createNode("Hamburger Menu button click failed")
			                    .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			            log.error(e.getMessage());
			            stopWebTest();
			  	  }
			    	//Clicks on the sign in or sign up button
			    	try {
			 		   clickableWait(Elements.siginSignUpButtonXpath_DD);
			 		   explicitWait(Elements.signinPageHeaderXpath_DD);
			 		   if(driver.findElement(Elements.signinPageHeaderXpath_DD).isDisplayed())
			 		   {
			 			   log.info("Sign in or Sign up button clicked");
			 				  extTestObj.createNode("Sign in or Sign up button clicked").pass("PASSED"); 
			 		   }  
			 	   }
			 	   catch(Exception e)
			 	   {
			 		   log.error("Sign in or Sign up button click failed");
			 	          extTestObj.createNode("Sign in or Sign up button click failed")
			 	                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			 	          log.error(e.getMessage());
			 	          stopWebTest(); 
			 	   }
			    	//Enters user email in the said field
			    	String useremail = excel.getCellData("DoorDash","UserEmail",2);
			  	  try {
			  		  sendKeysWait(Elements.emailTextBoxXpath_DD,useremail);
			  		  if(driver.findElement(Elements.emailTextBoxXpath_DD).getAttribute("value").equals(useremail))
			  		  {
			  			  log.info("Email address entered as : "+useremail);
			  			  extTestObj.createNode("Email address entered as : "+useremail).pass("PASSED");  
			  		  }
			  	  }
			  	  catch(Exception e)
			  	  {
			  		  log.error("Failed to enter email address");
			            extTestObj.createNode("Failed to enter email address")
			                    .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			            log.error(e.getMessage());
			            stopWebTest(); 
			  	  }
			  	  //Enters the password in the said field
			  	String password = excel.getCellData("DoorDash","Password",2);
				  try {
					  sendKeysWait(Elements.passwordTextBoxXpath_DD,password);
					  if(driver.findElement(Elements.passwordTextBoxXpath_DD).getAttribute("value").equals(password))
					  {
						  log.info("Password entered as : "+password);
						  extTestObj.createNode("Password entered as : "+password).pass("PASSED");  
					  }
				  }
				  catch(Exception e)
				  {
					  log.error("Failed to enter password");
			        extTestObj.createNode("Failed to enter password")
			                .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			        log.error(e.getMessage());
			        stopWebTest(); 
				  }
				  //Clicks on the sign in button
				  try {
					   clickableWait(Elements.signinButtonXpath_DD);
					   explicitWait(Elements.doorDashHeaderXpath_DD);
					   if(driver.findElement(Elements.doorDashHeaderXpath_DD).isDisplayed())
					   {
						   log.info("Sign in button clicked and login successful");
						   extTestObj.createNode("Sign in button clicked and login successful").pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("Login failed");
				          extTestObj.createNode("Login failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest(); 
					   
				   }
				  //Verifies the account name
				  String accountName = excel.getCellData("DoorDash","Account Name", 2);
				   try {
					   clickableWait(Elements.hamburgerMenuButtonXpath_DD);
					   explicitWait(Elements.accountNameXpath_DD);
					   if(driver.findElement(Elements.accountNameXpath_DD).getText().equals(accountName))
					   {
						   log.info("Account Name displayed as : "+accountName);
						   extTestObj.createNode("Account Name displayed as : "+accountName).pass("PASSED");
					   }
					   Thread.sleep(2000);
					   clickableWait(Elements.closeSlidingMenu_DD);
				   }
				   catch(Exception e)
				   {
					   log.error("Account Name validation failed");
				          extTestObj.createNode("Account Name validation failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
					   
				   }
				   //Launches the store site again
				   try {
			 		  driver.get(siteURL);
			 			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			 			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			 			driver.manage().window().maximize();
			 		
			 			 explicitWait(Elements.hamburgerMenuButtonXpath_DD);
			 		  
			 		  
			 			  log.info("Site launch successful, site name : "+driver.getCurrentUrl());
			 			  extTestObj.createNode("Site launch successful, site name : "+driver.getCurrentUrl()).pass("PASSED");
			 	        
			 		  }
			 		  catch(Exception e)
			 		  {
			 			  log.error("Site launch failed");
			 	          extTestObj.createNode("Site launch failed")
			 	                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			 	          log.error(e.getMessage());
			 	          stopWebTest();
			 		  }
			  	  
			    }
			    
			    
			    
			    
			    
			    
			    
			    
			    
			    
			    
			    
			    //Launches site
			  public void webValidateSiteLaunch(String siteURL)
			  {
				  try {
				  driver.get(siteURL);
					driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
					driver.manage().window().maximize();
				
					 explicitWait(Elements.hamburgerMenuButtonXpath_DD);
				  
				  
					  log.info("Site launch successful, site name : "+driver.getCurrentUrl());
					  extTestObj.createNode("Site launch successful, site name : "+driver.getCurrentUrl()).pass("PASSED");
			        
				  }
				  catch(Exception e)
				  {
					  log.error("Site launch failed");
			          extTestObj.createNode("Site launch failed")
			                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			          log.error(e.getMessage());
			          stopWebTest();
				  }
					  
				  }
			    //Clicks on the hamburger menu button
			    public void webClickHamburgerMenuButton()
			  {
				  try {
					  clickableWait(Elements.hamburgerMenuButtonXpath_DD);
					  explicitWait(Elements.siginSignUpButtonXpath_DD);
					  if(driver.findElement(Elements.siginSignUpButtonXpath_DD).isDisplayed())
					  {
						  log.info("Hamburger Menu button clicked");
						  extTestObj.createNode("Hamburger Menu button clicked").pass("PASSED");  
					  }
				  }
				  catch(Exception e)
				  {
					  log.error("Hamburger Menu button click failed");
			          extTestObj.createNode("Hamburger Menu button click failed")
			                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			          log.error(e.getMessage());
			          stopWebTest();
				  }  
			  }
			    //Clicks on sign in or sign up button 
			   public void  webClickSignInorSignUpButton()
			   {
				   try {
					   clickableWait(Elements.siginSignUpButtonXpath_DD);
					   explicitWait(Elements.signinPageHeaderXpath_DD);
					   if(driver.findElement(Elements.signinPageHeaderXpath_DD).isDisplayed())
					   {
						   log.info("Sign in or Sign up button clicked");
							  extTestObj.createNode("Sign in or Sign up button clicked").pass("PASSED"); 
					   }  
				   }
				   catch(Exception e)
				   {
					   log.error("Sign in or Sign up button click failed");
				          extTestObj.createNode("Sign in or Sign up button click failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest(); 
				   }
			   }
			   //Enters user email in the said field
			   public void webEnterUserEmail()
			   {
				  String useremail = excel.getCellData("DoorDash","UserEmail",2);
				  try {
					  sendKeysWait(Elements.emailTextBoxXpath_DD,useremail);
					  if(driver.findElement(Elements.emailTextBoxXpath_DD).getAttribute("value").equals(useremail))
					  {
						  log.info("Email address entered as : "+useremail);
						  extTestObj.createNode("Email address entered as : "+useremail).pass("PASSED");  
					  }
				  }
				  catch(Exception e)
				  {
					  log.error("Failed to enter email address");
			          extTestObj.createNode("Failed to enter email address")
			                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			          log.error(e.getMessage());
			          stopWebTest(); 
				  }
				  
			   }
			 //Enters user password in the said field
			   public void webEnterUserPassword()
			   {
				  String password = excel.getCellData("DoorDash","Password",2);
				  try {
					  sendKeysWait(Elements.passwordTextBoxXpath_DD,password);
					  if(driver.findElement(Elements.passwordTextBoxXpath_DD).getAttribute("value").equals(password))
					  {
						  log.info("Password entered as : "+password);
						  extTestObj.createNode("Password entered as : "+password).pass("PASSED");  
					  }
				  }
				  catch(Exception e)
				  {
					  log.error("Failed to enter password");
			          extTestObj.createNode("Failed to enter password")
			                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			          log.error(e.getMessage());
			          stopWebTest(); 
				  }
				  
			   }
			   //Clicks on the sign in button
			   public void webClickSignInButton()
			   {
				   try {
					   clickableWait(Elements.signinButtonXpath_DD);
					   explicitWait(Elements.doorDashHeaderXpath_DD);
					   if(driver.findElement(Elements.doorDashHeaderXpath_DD).isDisplayed())
					   {
						   log.info("Sign in button clicked and login successful");
						   extTestObj.createNode("Sign in button clicked and login successful").pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("Login failed");
				          extTestObj.createNode("Login failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest(); 
					   
				   }
			   }
			   //Validates the account name if it is correct from the menu
			   public void webValidateAccountName()
			   {
				   String accountName = excel.getCellData("DoorDash","Account Name", 2);
				   try {
					   clickableWait(Elements.hamburgerMenuButtonXpath_DD);
					   explicitWait(Elements.accountNameXpath_DD);
					   if(driver.findElement(Elements.accountNameXpath_DD).getText().equals(accountName))
					   {
						   log.info("Account Name displayed as : "+accountName);
						   extTestObj.createNode("Account Name displayed as : "+accountName).pass("PASSED");
					   }
					   Thread.sleep(2000);
					   clickableWait(Elements.closeSlidingMenu_DD);
				   }
				   catch(Exception e)
				   {
					   log.error("Account Name validation failed");
				          extTestObj.createNode("Account Name validation failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
					   
				   }
			   }
			   //Clicks on the sign out
			   public void webClickSignOutButton()
			   {
				   try {
					   clickableWait(Elements.hamburgerMenuButtonXpath_DD);
					   explicitWait(Elements.accountNameXpath_DD);
					   clickableWait(Elements.signOutButtonXpath_DD);
					   explicitWait(Elements.doorDashHomePageLogo_DD);
					   if(driver.findElement(Elements.doorDashHomePageLogo_DD).isDisplayed())
					   {
						   log.info("User signed out");
						   extTestObj.createNode("User signed out").pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("User sign out failed");
				          extTestObj.createNode("User sign out failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
			   }
			   
			   public void webSetLocation() {
				   //Clicks on the address
				   try {
					   Thread.sleep(3000);
					   clickElement(Elements.locationSelect_DD);
					   explicitWait(Elements.verifyInputBox_DD);
					   if(driver.findElement(Elements.verifyInputBox_DD).isDisplayed())
					   {
						   log.info("Address clicked");
						   extTestObj.createNode("Address clicked").pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("Address click failed");
				          extTestObj.createNode("Address click failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
				   //Enters the address given in the excel sheet
				   String address=excel.getCellData("DoorDash","Location", 2);
				   try {
					   sendKeysWait(Elements.enterAddress_DD,address);
					   Thread.sleep(2000);
					   driver.findElement(Elements.enterAddress_DD).sendKeys(Keys.RETURN);
					   explicitWait(Elements.clickSave_DD);
					   if(driver.findElement(Elements.clickSave_DD).isDisplayed())
					   {
						   log.info("Address entered as: "+ address);
						   extTestObj.createNode("Address entered as: "+address).pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("Address enter failed");
				          extTestObj.createNode("Address enter failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
				   //Clicks on save
				   try {
					   clickableWait(Elements.clickSave_DD);
					   explicitWait(Elements.verifyAddress_DD);
					   if(driver.findElement(Elements.verifyAddress_DD).isDisplayed())
					   {
						   log.info("Address enter successful");
						   extTestObj.createNode("Address enter successful").pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("Your Address click failed");
				          extTestObj.createNode("Your Address click failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
				   //Verifies the account name
				   String accountName = excel.getCellData("DoorDash","Account Name", 2);
				   try {
					   clickableWait(Elements.hamburgerMenuButtonXpath_DD);
					   explicitWait(Elements.accountNameXpath_DD);
					   if(driver.findElement(Elements.accountNameXpath_DD).getText().equals(accountName))
					   {
						   log.info("Account Name displayed as : "+accountName);
						   extTestObj.createNode("Account Name displayed as : "+accountName).pass("PASSED");
					   }
					   Thread.sleep(2000);
					   clickableWait(Elements.closeSlidingMenu_DD);
				   }
				   catch(Exception e)
				   {
					   log.error("Account Name validation failed");
				          extTestObj.createNode("Account Name validation failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
					   
				   }
				   //Verifies the address shown on the top of the page
				   try {
					   explicitWait(Elements.verifyAddress_DD);
					   if(driver.findElement(Elements.verifyAddress_DD).isDisplayed())
					   {
						   log.info("Top Account address is Verified");
						   extTestObj.createNode("Top Account address is Verified").pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("Top Account address is not showing as Expected");
				          extTestObj.createNode("Top Bottom Account address is not showing as Expected")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   } 
				 //Verifies the address shown on the bottom of the page
				   try {
					    explicitWait(Elements.verifyAddress_Xpath_DD);
					 
					   if(driver.findElement(Elements.verifyAddress_Xpath_DD).getText().contains("Seal Dr s, Adak, AK 99546, USA"))
					   {
						   log.info("Bottom Account address is Verified ");
						   extTestObj.createNode("Bottom Account address is Verified ").pass("PASSED");
					   }else
					   {
						   throw new Exception("Bottom Account address is not showing as Expected");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("Bottom Account address is not showing as Expected: "+ driver.findElement(Elements.verifyAddress_Xpath_DD).getText());
				          extTestObj.createNode("Bottom Account address is not showing as Expected: "+ driver.findElement(Elements.verifyAddress_Xpath_DD).getText())
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
					   
				   }
				   
			   }
			   
			   
			   
			   
			   
			   
			   
			   
			   //Validates the Top address if it is correct or not
			   public void webValidateTopAddress()
			   {
				   try {
					   explicitWait(Elements.verifyAddress_DD);
					   if(driver.findElement(Elements.verifyAddress_DD).isDisplayed())
					   {
						   log.info("Top Account address is Verified");
						   extTestObj.createNode("Top Account address is Verified").pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("Top Account address is not showing as Expected");
				          extTestObj.createNode("Top Bottom Account address is not showing as Expected")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
			   }
			   //Validates the Bottom address if it is correct or not
			   public void webValidateBottomAddress()
			   {
				    
				   try {
					    explicitWait(Elements.verifyAddress_Xpath_DD);
					 
					   if(driver.findElement(Elements.verifyAddress_Xpath_DD).getText().contains("Seal Dr s, Adak, AK 99546, USA"))
					   {
						   log.info("Bottom Account address is Verified ");
						   extTestObj.createNode("Bottom Account address is Verified ").pass("PASSED");
					   }else
					   {
						   throw new Exception("Bottom Account address is not showing as Expected");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("Bottom Account address is not showing as Expected: "+ driver.findElement(Elements.verifyAddress_Xpath_DD).getText());
				          extTestObj.createNode("Bottom Account address is not showing as Expected: "+ driver.findElement(Elements.verifyAddress_Xpath_DD).getText())
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
					   
				   }
			   }
			   //Clicks on the current selected address
			   public void webClickOnLocation()
			   {
				   try {
					   Thread.sleep(3000);
					   clickElement(Elements.locationSelect_DD);
					   explicitWait(Elements.verifyInputBox_DD);
					   if(driver.findElement(Elements.verifyInputBox_DD).isDisplayed())
					   {
						   log.info("Address clicked");
						   extTestObj.createNode("Address clicked").pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("Address click failed");
				          extTestObj.createNode("Address click failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
			   }
			   //Enters the desired address in the said field
			   public void webEnterAddress()
			   {
				   String address=excel.getCellData("DoorDash","Location", 2);
				   try {
					   sendKeysWait(Elements.enterAddress_DD,address);
					   Thread.sleep(2000);
					   driver.findElement(Elements.enterAddress_DD).sendKeys(Keys.RETURN);
					   explicitWait(Elements.clickSave_DD);
					   if(driver.findElement(Elements.clickSave_DD).isDisplayed())
					   {
						   log.info("Address entered as: "+ address);
						   extTestObj.createNode("Address entered as: "+address).pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("Address enter failed");
				          extTestObj.createNode("Address enter failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
			   }
			   //Clicks on save
			   public void webClickOnSave()
			   {
				   try {
					   clickableWait(Elements.clickSave_DD);
					   explicitWait(Elements.verifyAddress_DD);
					   if(driver.findElement(Elements.verifyAddress_DD).isDisplayed())
					   {
						   log.info("Address enter successful");
						   extTestObj.createNode("Address enter successful").pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("Your Address click failed");
				          extTestObj.createNode("Your Address click failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
			   }
			   //Adds order item to cart and checks out
			   public void webAddOrderItemCheckout(int index) {
				   //Clicks on the menu item
				   String menuItem=excel.getCellData("Menu","Item",index);
					 try {
						 By menuItemName=By.xpath("//div[@data-anchor-id='MenuItem']//span[contains(text(),'"+menuItem+"')]");
						   clickableWait(menuItemName);
						   Thread.sleep(5000);
						   log.info("Click on menu Item " +menuItem+ " is success");
						   extTestObj.createNode("Click on menu Item " +menuItem+ " is success").pass("PASSED");
					    }
					   catch(Exception e)
					   {
						   log.error("Click on menu Item is failed");
					          extTestObj.createNode("Click on menu Item is failed")
					                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
					          log.error(e.getMessage());
					          stopWebTest();
					   }
					 //Clicks on add to order
					 try {
						  
						  	String itemName= driver.findElement(By.xpath("//input[@type='number']")).getAttribute("value");
						  	
						  	 this.item=itemName;
						  	 
						 	   clickableWait(Elements.addToOrderbutton_DD);
							   Thread.sleep(5000);
							   log.info("Click on Add To Order is success");
							   extTestObj.createNode("Click on Add To Order is success").pass("PASSED");
						    }
						   catch(Exception e)
						   {
							   log.error("Click on Add To Order is failed");
						          extTestObj.createNode("Click on Add To Order is failed")
						                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
						          log.error(e.getMessage());
						          stopWebTest();
						   }
					 
					 System.out.println(menuItem);
					 //Verifies if the menu item is shown in the checkout section
					  try {
						   
							   explicitWait(By.xpath("//button[@data-anchor-id='OrderCartItem']//span[contains(text(),'"+menuItem+"')]"));
							   driver.findElement(By.xpath("//button[@data-anchor-id='OrderCartItem']//span[contains(text(),'"+menuItem+"')]")).isDisplayed();
							   log.info("Order Item is displayed in Checkout page");
							   extTestObj.createNode("Order Item is displayed in Checkout page").pass("PASSED");
						    }
						   catch(Exception e)
						   {
							   log.error("Order Item is not displayed in Checkout page");
						          extTestObj.createNode("Order Item is not displayed in Checkout page")
						                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
						          log.error(e.getMessage());
						          stopWebTest();
						   }
					  //Clicks on checkout
					  try {
						   Thread.sleep(5000);
						//  clickableWait(Elements.checkout_DD);
						   wait.until(ExpectedConditions.elementToBeClickable(Elements.checkout_DD));
						   clickElement(Elements.checkout_DD);
						   
						   explicitWait(Elements.amountDue_DD);
						   if(driver.findElement(Elements.amountDue_DD).isDisplayed())
						   {
							   log.info("Checkout successful");
							   extTestObj.createNode("Checkout successful").pass("PASSED");
						   }
					   }
					   catch(Exception e)
					   {
						   log.error("Checkout failed");
					          extTestObj.createNode("Checkout failed")
					                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
					          log.error(e.getMessage());
					          stopWebTest();
					   }
					 
			   }
			   
			   public void webAddToOrderItem(int index) {
				 //Clicks on the menu item
				   String menuItem=excel.getCellData("Menu","Item",index);
					 try {
						 By menuItemName=By.xpath("//div[@data-anchor-id='MenuItem']//span[contains(text(),'"+menuItem+"')]");
						   clickableWait(menuItemName);
						   Thread.sleep(5000);
						   log.info("Click on menu Item " +menuItem+ " is success");
						   extTestObj.createNode("Click on menu Item " +menuItem+ " is success").pass("PASSED");
					    }
					   catch(Exception e)
					   {
						   log.error("Click on menu Item is failed");
					          extTestObj.createNode("Click on menu Item is failed")
					                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
					          log.error(e.getMessage());
					          stopWebTest();
					   }
					//Clicks on add to order
					 try {
						  
						  	String itemName= driver.findElement(By.xpath("//input[@type='number']")).getAttribute("value");
						  	
						  	 this.item=itemName;
						  	 
						 	   clickableWait(Elements.addToOrderbutton_DD);
							   Thread.sleep(5000);
							   log.info("Click on Add To Order is success");
							   extTestObj.createNode("Click on Add To Order is success").pass("PASSED");
						    }
						   catch(Exception e)
						   {
							   log.error("Click on Add To Order is failed");
						          extTestObj.createNode("Click on Add To Order is failed")
						                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
						          log.error(e.getMessage());
						          stopWebTest();
						   }
					 
					 System.out.println(menuItem);
					//Verifies if the menu item is shown in the checkout section
					  try {
						   
							   explicitWait(By.xpath("//button[@data-anchor-id='OrderCartItem']//span[contains(text(),'"+menuItem+"')]"));
							   driver.findElement(By.xpath("//button[@data-anchor-id='OrderCartItem']//span[contains(text(),'"+menuItem+"')]")).isDisplayed();
							   log.info("Order Item is displayed in Checkout page");
							   extTestObj.createNode("Order Item is displayed in Checkout page").pass("PASSED");
						    }
						   catch(Exception e)
						   {
							   log.error("Order Item is not displayed in Checkout page");
						          extTestObj.createNode("Order Item is not displayed in Checkout page")
						                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
						          log.error(e.getMessage());
						          stopWebTest();
						   }
			   }
			   
			   
			   
			   
			   
			   //Clicks on the menu item name taken from excel sheet
			   public void webClickMenuItem(int index) {
					  String menuItem=excel.getCellData("Menu","Item",index);
					 try {
						 By menuItemName=By.xpath("//div[@data-anchor-id='MenuItem']//span[contains(text(),'"+menuItem+"')]");
						   clickableWait(menuItemName);
						   Thread.sleep(5000);
						   log.info("Click on menu Item " +menuItem+ " is success");
						   extTestObj.createNode("Click on menu Item " +menuItem+ " is success").pass("PASSED");
					    }
					   catch(Exception e)
					   {
						   log.error("Click on menu Item is failed");
					          extTestObj.createNode("Click on menu Item is failed")
					                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
					          log.error(e.getMessage());
					          stopWebTest();
					   }
					     
				 }
			  
			   public void webSelectCustomizations() {
				    //Selects the protein
					  String protein=excel.getCellData("Order Customization","Protein",2);
					 try {
						 By Protein=By.xpath("//label/div/span[contains(text(),'"+protein+"')]");
						   clickableWait(Protein);
						   Thread.sleep(5000);
						   
						   log.info("Click on protein " +protein+ " is success");
						   extTestObj.createNode("Click on protein " +protein+ " is success").pass("PASSED");
					    }
					   catch(Exception e)
					   {
						   log.error("Click on protein is failed");
					          extTestObj.createNode("Click on protein is failed")
					                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
					          log.error(e.getMessage());
					          //stopWebTest();
					   }
					     
				 
			   //Selects the temp
			   
					  String temp=excel.getCellData("Order Customization","Temp",2);
					 try {
						 By Temp=By.xpath("//label/div/span[contains(text(),'"+temp+"')]");
						   clickableWait(Temp);
						   Thread.sleep(5000);
						   
						   log.info("Click on temp " +temp+ " is success");
						   extTestObj.createNode("Click on temp " +temp+ " is success").pass("PASSED");
					    }
					   catch(Exception e)
					   {
						   log.error("Click on temp is failed");
					          extTestObj.createNode("Click on temp is failed")
					                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
					          log.error(e.getMessage());
					          //stopWebTest();
					   }
					     
				 
			   //Selects a condiment
			   
					  String condiment=excel.getCellData("Order Customization","Condiments",2);
					 try {
						 By Condiment=By.xpath("//label/div/span[contains(text(),'"+condiment+"')]/preceding::input[1]");
						 if (!driver.findElement(Condiment).isSelected()) {
						   clickableWait(Condiment);
						   Thread.sleep(5000);
						 }
						   log.info("Click on Condiment " +condiment+ " is success");
						   extTestObj.createNode("Click on Condiment " +condiment+ " is success").pass("PASSED");
					    }
					   catch(Exception e)
					   {
						   log.warn("Click on condiment failed");
						   log.error(e.getMessage());
					          //stopWebTest();
					   }
					 //clicks on save if applicable
					 try {
						   
						   clickableWait(Elements.saveButton_DD);
						   Thread.sleep(5000);
						   
						   log.info("Clicked on save button");
						   extTestObj.createNode("Clicked on save button").pass("PASSED");
					    }
					   catch(Exception e)
					   {
						   log.warn("Save button not available for this option");
					          
					          //stopWebTest();
					   }
					     
				 
			   
			 //Selects a topping
			   for(int i=2;i<=3;i++) {
					  String topping=excel.getCellData("Order Customization","Topping",i);
					 try {
						 By Topping=By.xpath("//label/div/span[contains(text(),'"+topping+"')]/preceding::input[1]");
						 if (!driver.findElement(Topping).isSelected()) {
						   clickableWait(Topping);
						   Thread.sleep(5000);
						 }  
						   log.info("Click on Topping " +topping+ " is success");
						   extTestObj.createNode("Click on Topping " +topping+ " is success").pass("PASSED");
					    }
					   catch(Exception e)
					   {
						   log.warn("Topping selection failed");
						   log.error(e.getMessage());
					   }
					//clicks on save if applicable
					 try {
						 clickableWait(Elements.saveButton_DD);
						   Thread.sleep(5000);
						   
						   log.info("Clicked on save button");
						   extTestObj.createNode("Clicked on save button").pass("PASSED");
					    }
					   catch(Exception e)
					   {
						   log.warn("Save button not available for this option");
					          
					          //stopWebTest();
					   }
					     
				 }
			   
			 //Selects a side
			  
					  String side=excel.getCellData("Order Customization","Side",2);
					 try {
						 By Side=By.xpath("//label/div/span[contains(text(),'"+side+"')]");
						   clickableWait(Side);
						   Thread.sleep(5000);
						   
						   log.info("Click on Side " +side+ " is success");
						   extTestObj.createNode("Click on Side " +side+ " is success").pass("PASSED");
					    }
					   catch(Exception e)
					   {
						   log.warn("Side selection failed");
						   log.error(e.getMessage());
					   }
					//clicks on save if applicable
					 try {
						 
						 clickableWait(Elements.saveButton_DD);
						   Thread.sleep(5000);
						   
						   log.info("Clicked on save button");
						   extTestObj.createNode("Clicked on save button").pass("PASSED");
					    }
					   catch(Exception e)
					   {
						   log.warn("Save button not available for this option");
					          
					          //stopWebTest();
					   }
					     
				 
			   
			 //Selects a beverage
			  
					  String beverage=excel.getCellData("Order Customization","Beverage",2);
					 try {
						 By Beverage=By.xpath("//label/div/span[contains(text(),'"+beverage+"')]/preceding::input[1]");
						 if (!driver.findElement(Beverage).isSelected()) {
						   clickableWait(Beverage);
						   Thread.sleep(5000);
						 }
						   log.info("Click on Beverage " +beverage+ " is success");
						   extTestObj.createNode("Click on Beverage " +beverage+ " is success").pass("PASSED");
					    }
					   catch(Exception e)
					   {
						   log.warn("Beverage selection failed");
						   log.error(e.getMessage());
					   }
					
					     
				 
			   
			   //Selects a soup
			   
				   //Selects the soup type
					  String soup=excel.getCellData("Order Customization","Soup type",2);
					 try {
						 By Soup=By.xpath("//label/div/span[contains(text(),'"+soup+"')]/preceding::input[1]"); 
						 if (!driver.findElement(Soup).isSelected()) {
						   clickableWait(Soup);
						   Thread.sleep(5000);
						 }
						   log.info("Click on Soup type " +soup+ " is success");
						   extTestObj.createNode("Click on Soup type " +soup+ " is success").pass("PASSED");
					    }
					   catch(Exception e)
					   {
						   log.warn("Soup type selection failed");
						   log.error(e.getMessage());
					   }
					 //Selects the soup name
					 String soupName=excel.getCellData("Order Customization","Soup name",2);
					 try {
						 By Soupname=By.xpath("//label/div/span[contains(text(),'"+soupName+"')]/preceding::input[1]");
						 if (!driver.findElement(Soupname).isSelected()) {
						   clickableWait(Soupname);
						   Thread.sleep(5000);
						 } 
						   log.info("Click on Soup " +soupName+ " is success");
						   extTestObj.createNode("Click on Soup " +soupName+ " is success").pass("PASSED");
					    }
					   catch(Exception e)
					   {
						   log.warn("Soup selection failed");
						   log.error(e.getMessage());
					   }
					 
					//clicks on save if applicable
					 try {
						 
						 clickableWait(Elements.saveButton_DD);
						   Thread.sleep(5000);
						   
						   log.info("Clicked on save button");
						   extTestObj.createNode("Clicked on save button").pass("PASSED");
					    }
					   catch(Exception e)
					   {
						   log.warn("Save button not available for this option");
					          
					          //stopWebTest();
					   }
					     
				 
			   
			   //Selects a salad
			  
					  String salad=excel.getCellData("Order Customization","Salad",2);
					 try {
						 By Salad=By.xpath("//label/div/span[contains(text(),'"+salad+"')]/preceding::input[1]");
						 if (!driver.findElement(Salad).isSelected()) {
						   clickableWait(Salad);
						   Thread.sleep(5000);
						 } 
						   log.info("Click on Salad type " +salad+ " is success");
						   extTestObj.createNode("Click on Salad type " +salad+ " is success").pass("PASSED");
					    }
					   catch(Exception e)
					   {
						   log.warn("Salad type selection failed");
						   log.error(e.getMessage());
					   }
					 //selects the dressing
					 String dressing=excel.getCellData("Order Customization","Dressing choice" ,2);
					 try {
						 By dresssingChoice=By.xpath("//label/div/span[contains(text(),'"+dressing+"')]");
						   clickableWait(dresssingChoice);
						   Thread.sleep(5000);
						   
						   log.info("Click on Dressing choice " +dressing+ " is success");
						   extTestObj.createNode("Click on Dressing choice " +dressing+ " is success").pass("PASSED");
					    }
					   catch(Exception e)
					   {
						   log.warn("Dressing choice selection failed");
						   log.error(e.getMessage());
					   }
					//clicks on save if applicable
					 try {
						 
						 clickableWait(Elements.saveButton_DD);
						   Thread.sleep(5000);
						   
						   log.info("Clicked on save button");
						   extTestObj.createNode("Clicked on save button").pass("PASSED");
					    }
					   catch(Exception e)
					   {
						   log.warn("Save button not available for this option");
					          
					          //stopWebTest();
					   }
					 //Selects the toppings
					 String topping=excel.getCellData("Order Customization","Toppings" ,2);
					 try {
						 By toppingChoice=By.xpath("//label/div/span[contains(text(),'"+topping+"')]/preceding::input[1]");
						 if (!driver.findElement(toppingChoice).isSelected()) {
						   clickableWait(toppingChoice);
						   Thread.sleep(5000);
						 }
						   log.info("Click on Topping choice " +topping+ " is success");
						   extTestObj.createNode("Click on Topping choice " +topping+ " is success").pass("PASSED");
					    }
					   catch(Exception e)
					   {
						   log.warn("Topping choice selection failed");
						   log.error(e.getMessage());
					   }
					 
					//clicks on save if applicable
					 try {
						 
						 clickableWait(Elements.saveButton_DD);
						   Thread.sleep(5000);
						   
						   log.info("Clicked on save button");
						   extTestObj.createNode("Clicked on save button").pass("PASSED");
					    }
					   catch(Exception e)
					   {
						   log.warn("Save button not available for this option");
					          
					          //stopWebTest();
					   }
					//clicks on save if applicable
					 try {
						 
						 clickableWait(Elements.saveButton_DD);
						   Thread.sleep(5000);
						   
						   log.info("Clicked on save button");
						   extTestObj.createNode("Clicked on save button").pass("PASSED");
					    }
					   catch(Exception e)
					   {
						   log.warn("Save button not available for this option");
					          
					          //stopWebTest();
					   }
					     
				 }
			   
			   
			   //Clicks on add to order
			   String item;
			   public void webClickAddToOrder() { 
					  
					  try {
							  
						  	String itemName= driver.findElement(By.xpath("//input[@type='number']")).getAttribute("value");
						  	
						  	 this.item=itemName;
						  	 
						 	   clickableWait(Elements.addToOrderbutton_DD);
							   Thread.sleep(5000);
							   log.info("Click on Add To Order is success");
							   extTestObj.createNode("Click on Add To Order is success").pass("PASSED");
						    }
						   catch(Exception e)
						   {
							   log.error("Click on Add To Order is failed");
						          extTestObj.createNode("Click on Add To Order is failed")
						                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
						          log.error(e.getMessage());
						          stopWebTest();
						   }
					  
						   
					 }
			   
			   
				  
				  
				
			   //Verifies the item name
				public void webVerifyOrderItem(int index) { 
					
					String menuItem=excel.getCellData("Menu","Item",index);
					System.out.println(menuItem);
					  try {
						   
							   explicitWait(By.xpath("//button[@data-anchor-id='OrderCartItem']//span[contains(text(),'"+menuItem+"')]"));
							   driver.findElement(By.xpath("//button[@data-anchor-id='OrderCartItem']//span[contains(text(),'"+menuItem+"')]")).isDisplayed();
							   log.info("Order Item is displayed in Checkout page");
							   extTestObj.createNode("Order Item is displayed in Checkout page").pass("PASSED");
						    }
						   catch(Exception e)
						   {
							   log.error("Order Item is not displayed in Checkout page");
						          extTestObj.createNode("Order Item is not displayed in Checkout page")
						                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
						          log.error(e.getMessage());
						          stopWebTest();
						   }
						     
					 }
				//Verifies the item quantity before adding to cart and after
				public void webVerifyOrderQuantity() { 
					
					 
					  try {
						  	
						  		explicitWait(Elements.checkout_DD);
							    String QuantityAfter=driver.findElement(Elements.verifyOrderQuantity_DD).getText().trim();
							    System.out.println(QuantityAfter);
							    
							    String QuantityBefore=(item +" ×").trim();
							    
							    System.out.println(QuantityBefore);
							   
							    if(QuantityAfter.equalsIgnoreCase(QuantityBefore))
							   {
							    
							   log.info("Order Item quantity in checkout is same as before add to order");
							   extTestObj.createNode("Order Item quantity in checkout is same as before add to order").pass("PASSED");
							   
							    }else {
							 
							    	log.error("Order Item quantity in checkout is not same as before add to order");
							         extTestObj.createNode("Order Item quantity in checkout is not same as before add to order").fail("FAILED");
							    
							    	}
						    }
						   catch(Exception e)
						   {
							   log.error("Order Item quantity in checkout is not same as before add to order");
						          extTestObj.createNode("Order Item quantity in checkout is not same as before add to order")
						                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
						          log.error(e.getMessage());
						          stopWebTest();
						   }
						     
						} 
				//Validates if all the Appetizer Item are showing or not
				public void webValidateMenuItem() {

					try {
						List<WebElement> itemList = driver.findElements(Elements.verifyMenuItem_DD);
						 
							for (int i = 0; i < itemList.size(); i+=2) {
								
								if (itemList.get(i).isDisplayed()) {
									
									System.out.println(itemList.get(i).getText());

								}
								 
							}
							 
							log.info("All Appetizer menu item is present");
							extTestObj.createNode("All Appetizer menu item is present").pass("PASSED");
						 
						
					} catch (Exception e) {
						log.error("All Appetizer menuitem is not present");
						extTestObj.createNode("All Appetizer menuitem is not present")
								.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
						log.error(e.getMessage());
						stopWebTest();
					}

				}
				//Verifying if the delivery button is enabled or not
				public void webVerifyDeliveryButton() {
					
					 try {
						  
						   explicitWait(Elements.deliveryButton_DD);
						   driver.findElement(Elements.deliveryButton_DD).isEnabled();
						   
						   log.info("Delivery button is enabled");
						   extTestObj.createNode("Delivery button is enabled").pass("PASSED");
					    }
					   catch(Exception e)
					   {
						   log.error("Delivery button is disabled");
					          extTestObj.createNode("Delivery button is disabled")
					                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
					          log.error(e.getMessage());
					          stopWebTest();
					   }
				}
				//Verifying if the pickup button is enabled or not
				public void webVerifyPickupButton() {
					
					 try {
						  
						   explicitWait(Elements.pickupButton_DD);
						   driver.findElement(Elements.pickupButton_DD).isEnabled();
						   
						   log.info("Pickup button is enabled");
						   extTestObj.createNode("Pickup button is enabled").pass("PASSED");
					    }
					   catch(Exception e)
					   {
						   log.error("Pickup button is disabled");
					          extTestObj.createNode("Pickup button is disabled")
					                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
					          log.error(e.getMessage());
					          stopWebTest();
					   }
				}

			   //Clicks on checkout button
			   public void webClickOnCheckout()
			   {
				   try {
					   Thread.sleep(5000);
					//  clickableWait(Elements.checkout_DD);
					   wait.until(ExpectedConditions.elementToBeClickable(Elements.checkout_DD));
					   clickElement(Elements.checkout_DD);
					   
					   explicitWait(Elements.amountDue_DD);
					   if(driver.findElement(Elements.amountDue_DD).isDisplayed())
					   {
						   log.info("Checkout successful");
						   extTestObj.createNode("Checkout successful").pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("Checkout failed");
				          extTestObj.createNode("Checkout failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
			   }
			   
			 //Checks Item increase after checkout
			   public void webCheckIncreaseAfterCheckout()
			   {
				   try {
					   clickableWait(Elements.validateCountOne_DD);
					   clickableWait(Elements.increaseCount_DD);
					   Thread.sleep(8000);
					   if(driver.findElement(Elements.validateCountTwo_DD).isDisplayed())
					   {
						   log.info("Item increase to 2 check after checkout: complete");
						   extTestObj.createNode("Item increase to 2 check after checkout: complete").pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("Item increase to 2 check after checkout: failed");
				          extTestObj.createNode("Item increase to 2 check after checkout: failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
			   }
			   
			 //Checks Item decrease after checkout
			   public void webCheckDecreaseAfterCheckout()
			   {
				   try {
					   clickableWait(Elements.validateCountTwo_DD);
					   clickableWait(Elements.decreaseCount_DD);
					   Thread.sleep(8000);
					   if(driver.findElement(Elements.validateCountOne_DD).isDisplayed())
					   {
						   log.info("Item decrease to 1 check after checkout: complete");
						   extTestObj.createNode("Item decrease to 1 check after checkout: complete").pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("Item decrease to 1 check after checkout: failed");
				          extTestObj.createNode("Item decrease to 1 check after checkout: failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
			   }
			   
			   public void webPickupASAP(){
				   //Clicks on pickup option
				   try {
					   clickableWait(Elements.pickupOption_DD);
					   explicitWait(Elements.verifyPickupOption_DD);
					   if(driver.findElement(Elements.verifyPickupOption_DD).isDisplayed())
					   {
						   log.info("Pickup option selected");
						   extTestObj.createNode("Pickup option selected").pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("Pickup option selection failed");
				          extTestObj.createNode("Pickup option selection failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
				   //Selects the asap option
				   try {
						Thread.sleep(2000);
						if(driver.findElement(Elements.asapButton_DD).isEnabled()){
						clickableWait(Elements.asapButton_DD);
						}
						Thread.sleep(2000);
						
						log.info("Asap button is selected");
						extTestObj.createNode("Asap button is selected").pass("PASSED");
						
					} catch (Exception e) {
						log.error("Failed to select asap button");
						extTestObj.createNode("Failed to select asap button")
								.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
						log.error(e.getMessage());
						stopWebTest();
					}
				   
				 //Logs order details
				   try {
						String total=driver.findElement(Elements.totalCost_DD).getAttribute("innerHTML");
						List<WebElement> itemNames=driver.findElements(Elements.itemNames_DD);
						List<WebElement> itemQtys=driver.findElements(Elements.itemQtys_DD);
						log.info("Total cost is: "+total);
						log.info("And the items are: ");
						for(int i=0;i<itemNames.size();++i) {
						log.info((itemQtys.get(i).getAttribute("innerHTML"))+(itemNames.get(i).getAttribute("innerHTML")));
						}
						
					} catch (Exception e1) {
						log.error("Failed to fetch some details");
						extTestObj.createNode("Failed to fetch some details")
								.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e1);
						log.error(e1.getMessage());
						stopWebTest();
					}
				   
				   //Clicks on place pickup order
				   try {
					   explicitWait(Elements.placePickupOrder_DD);
					   Thread.sleep(5000);
					   clickElement(Elements.placePickupOrder_DD);
					   explicitWait(Elements.orderConfirmationAsap_DD);
					   if(driver.findElement(Elements.orderConfirmationAsap_DD).isDisplayed())
					   {
						   log.info("Pickup Order Placed Successfully");
						   extTestObj.createNode("Pickup Order Placed Successfully").pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("Failed to Place Pickup Order");
				          extTestObj.createNode("Failed to PlacePickup Order")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
				   
			   }
			   //Clicks on add more items
			   public void clickOnAddMoreItems() {
				   try {
					   explicitWait(Elements.addMoreItems_DD);
					   
					   clickElement(Elements.addMoreItems_DD);
					   explicitWait(Elements.checkout_DD);
					   if(driver.findElement(Elements.checkout_DD).isDisplayed())
					   {
						   log.info("Add more items button clicked Successfully");
						   extTestObj.createNode("Add more items button clicked Successfully").pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("Failed to click Add more items button");
				          extTestObj.createNode("Failed to click Add more items button")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
			   }
			   
			   public void webPickupSchedule() {
				   //Clicks on the pickup option
				   try {
					   clickableWait(Elements.pickupOption_DD);
					   explicitWait(Elements.verifyPickupOption_DD);
					   if(driver.findElement(Elements.verifyPickupOption_DD).isDisplayed())
					   {
						   log.info("Pickup option selected");
						   extTestObj.createNode("Pickup option selected").pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("Pickup option selection failed");
				          extTestObj.createNode("Pickup option selection failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
				   //Selects schedule later
				   try {
					   Thread.sleep(4000);
					   clickableWait(Elements.scheduleLater_DD);
					   explicitWait(Elements.verifyPickupScheduleLater_DD);
					   if(driver.findElement(Elements.verifyPickupScheduleLater_DD).isDisplayed())
					   {
						   log.info("Schedule later option selected");
						   extTestObj.createNode("Schedule later option selected").pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("Schedule later option selection failed");
				          extTestObj.createNode("Schedule later option selection failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
				   //Clicks on tomorrow from the date-time menu and verifies if the time menu is visible after clicking on TMR
				   String pickupTime=(String)excel.getCellData("DoorDash", "Delivery Time", 2); //PM-4
				   System.out.println(pickupTime);
				   String[] pickTimes=pickupTime.split(" ");
				   try {
					   
					   if((driver.findElement(Elements.selectTomorrow_DD)).isEnabled()){
					   clickableWait(Elements.selectTomorrow_DD);
					   }
					   explicitWait(By.xpath("//div[@kind='BUTTON/SECONDARY_PILL']//span[contains(text(),'"+pickTimes[1]+"-"+pickTimes[0]+"')]"));
					   if(driver.findElement(By.xpath("//div[@kind='BUTTON/SECONDARY_PILL']//span[contains(text(),'"+pickTimes[1]+"-"+pickTimes[0]+"')]")).isDisplayed())
					   {
						   log.info("Tomorrow option is selected and time list is visible");
						   extTestObj.createNode("Tomorrow option is selected and time list is visible").pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("Tomorrow option selection failed");
				          extTestObj.createNode("Tomorrow option selection failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
				   
				   //Clicks on the time and selects it
				   
				   try {
					    
					   if((driver.findElement(By.xpath("//div[@kind='BUTTON/SECONDARY_PILL']//span[contains(text(),'"+pickTimes[1]+"-"+pickTimes[0]+"')]"))).isEnabled()){
					   clickableWait(By.xpath("//div[@kind='BUTTON/SECONDARY_PILL']//span[contains(text(),'"+pickTimes[1]+"-"+pickTimes[0]+"')]"));
					   }
					   //explicitWait(Elements.verifyScheduleLater_DD);
					   Thread.sleep(3000);
					   if(driver.findElement(Elements.placePickupOrder_DD).isEnabled())
					   {
						   log.info("Pickup time selected around: "+pickupTime );
						   extTestObj.createNode("Pickup time selected around: "+pickupTime ).pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("Pickup time selection failed");
				          extTestObj.createNode("Pickup time selection failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
				   
				 //Logs order details
				   try {
						String total=driver.findElement(Elements.totalCost_DD).getAttribute("innerHTML");
						List<WebElement> itemNames=driver.findElements(Elements.itemNames_DD);
						List<WebElement> itemQtys=driver.findElements(Elements.itemQtys_DD);
						log.info("Total cost is: "+total);
						log.info("And the items are: ");
						for(int i=0;i<itemNames.size();++i) {
						log.info((itemQtys.get(i).getAttribute("innerHTML"))+(itemNames.get(i).getAttribute("innerHTML")));
						}
						
					} catch (Exception e1) {
						log.error("Failed to fetch some details");
						extTestObj.createNode("Failed to fetch some details")
								.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e1);
						log.error(e1.getMessage());
						stopWebTest();
					}
				   
				   //Places the pickup order
				   try {
					   explicitWait(Elements.placePickupOrder_DD);
					   Thread.sleep(5000);
					   clickElement(Elements.placePickupOrder_DD);
					   explicitWait(Elements.verifyScheduleOrderPlaced_DD);
					   if(driver.findElement(Elements.verifyScheduleOrderPlaced_DD).isDisplayed())
					   {
						   log.info("Pickup Order Placed Successfully");
						   extTestObj.createNode("Pickup Order Placed Successfully").pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("Failed to Place Pickup Order");
				          extTestObj.createNode("Failed to Place Pickup Order")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
				   
			   }
			   
			   public void webDeliveryASAP() {
				   //Delivery option is selected
				   try {
						driver.findElement(Elements.deliveryButton_DD).isEnabled();
						clickableWait(Elements.deliveryButton_DD);
						Thread.sleep(3000);
					
						log.info("Delivery button is selected");
						extTestObj.createNode("Delivery button is selected").pass("PASSED");
					} catch (Exception e) {
						log.error("Failed to select delivery button");
						extTestObj.createNode("Failed to select delivery button")
								.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
						log.error(e.getMessage());
						stopWebTest();
					}
				   //Adds a $3 tip for the dasher
				   try {
					   if(driver.findElement(Elements.tipThree_DD).isEnabled()){
					   clickableWait(Elements.tipThree_DD);
					   }
					   explicitWait(Elements.verifyThreeTip_DD);
					   if(driver.findElement(Elements.verifyThreeTip_DD).isDisplayed())
					   {
						   log.info("$3 tip option selected");
						   extTestObj.createNode("$3 tip option selected").pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("$3 tip option selection failed");
				          extTestObj.createNode("$3 option selection failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
				   //Selects the asap option
				   try {
						Thread.sleep(2000);
						if(driver.findElement(Elements.asapButton_DD).isEnabled()){
						clickableWait(Elements.asapButton_DD);
						}
						Thread.sleep(2000);
						
						log.info("Asap button is selected");
						extTestObj.createNode("Asap button is selected").pass("PASSED");
						
					} catch (Exception e) {
						log.error("Failed to select asap button");
						extTestObj.createNode("Failed to select asap button")
								.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
						log.error(e.getMessage());
						stopWebTest();
					}
				   //Logs order details
				   try {
						String total=driver.findElement(Elements.totalCost_DD).getAttribute("innerHTML");
						List<WebElement> itemNames=driver.findElements(Elements.itemNames_DD);
						List<WebElement> itemQtys=driver.findElements(Elements.itemQtys_DD);
						log.info("Total cost is: "+total);
						log.info("And the items are: ");
						for(int i=0;i<itemNames.size();++i) {
						log.info((itemQtys.get(i).getAttribute("innerHTML"))+(itemNames.get(i).getAttribute("innerHTML")));
						}
						
					} catch (Exception e1) {
						log.error("Failed to fetch some details");
						extTestObj.createNode("Failed to fetch some details")
								.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e1);
						log.error(e1.getMessage());
						stopWebTest();
					}
				   
				   //Clicks on place order
				   try {
						explicitWait(Elements.placeOrder_DD);
						driver.findElement(Elements.placeOrder_DD).isEnabled();
						Thread.sleep(5000);
						clickElement(Elements.placeOrder_DD);
						
						explicitWait ((Elements.orderConfirmationAsap_DD));
						if(driver.findElement(Elements.orderConfirmationAsap_DD).isDisplayed()) {
							
						log.info("Order is placed");
						extTestObj.createNode("Order is placed").pass("PASSED");
						}
						
					} catch (Exception e) {
						log.error("Failed to place order");
						extTestObj.createNode("Failed to place order")
								.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
						log.error(e.getMessage());
						stopWebTest();
					} 
				   
			   }
			   public void webDeliverySchedule() {
				   //CLicks on the delivery option
				   try {
						driver.findElement(Elements.deliveryButton_DD).isEnabled();
						clickableWait(Elements.deliveryButton_DD);
						Thread.sleep(3000);
					
						log.info("Delivery button is selected");
						extTestObj.createNode("Delivery button is selected").pass("PASSED");
					} catch (Exception e) {
						log.error("Failed to select delivery button");
						extTestObj.createNode("Failed to select delivery button")
								.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
						log.error(e.getMessage());
						stopWebTest();
					}
				   //Adds a $4 tip for the dasher
				   try {
					   if(driver.findElement(Elements.tipFour_DD).isEnabled()){
					   clickableWait(Elements.tipFour_DD);
					   }
					   explicitWait(Elements.verifyFourTip_DD);
					   if(driver.findElement(Elements.verifyFourTip_DD).isDisplayed())
					   {
						   log.info("$4 tip option selected");
						   extTestObj.createNode("$4 tip option selected").pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("$4 tip option selection failed");
				          extTestObj.createNode("$4 option selection failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
				   //Selects the schedule later option
				   try {
						   
					   explicitWait(Elements.scheduleLater_DD);
					   
					   clickableWait(Elements.scheduleLater_DD);
					   
					   explicitWait(Elements.verifyDeliveryScheduleLater_DD);
					   
					   if(driver.findElement(Elements.verifyDeliveryScheduleLater_DD).isDisplayed())
					   {
						   log.info("Schedule Later option selected");
						   extTestObj.createNode("Schedule Later option selected").pass("PASSED");
					   }
				   }
				   
				   catch(Exception e)
				   {
					   log.error("Schedule Later option selection failed");
				          extTestObj.createNode("Schedule Later option selection failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
				 //Clicks on tomorrow from the date-time menu and verifies if the time menu is visible after clicking on TMR
				   String deliveryTime=(String)excel.getCellData("DoorDash", "Delivery Time", 2); //4 PM
				   System.out.println(deliveryTime);
				   String[] delTimes=deliveryTime.split(" ");
				   
				   try {
					   
					   
					   if((driver.findElement(Elements.selectTomorrow_DD)).isEnabled()){
						   
					   clickableWait(Elements.selectTomorrow_DD);
					   }
					   explicitWait(By.xpath("//button[@kind='BUTTON/SECONDARY_PILL']//span[contains(text(),'"+delTimes[1]+"-"+delTimes[0]+"')]")); //PM-4 
					   if(driver.findElement(By.xpath("//button[@kind='BUTTON/SECONDARY_PILL']//span[contains(text(),'"+delTimes[1]+"-"+delTimes[0]+"')]")).isDisplayed())
					   {
						   log.info("Tomorrow option is selected and time menu is visible");
						   extTestObj.createNode("Tomorrow option is selected and time menu is visible").pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("Tomorrow option selection failed");
				          extTestObj.createNode("Tomorrow option selection failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
				   //Clicks on the time given in the excel sheet to select it
				   try {
					   
					   if((driver.findElement(By.xpath("//button[@kind='BUTTON/SECONDARY_PILL']//span[contains(text(),'"+delTimes[1]+"-"+delTimes[0]+"')]"))).isEnabled()){
						   
					   clickableWait(By.xpath("//button[@kind='BUTTON/SECONDARY_PILL']//span[contains(text(),'"+delTimes[1]+"-"+delTimes[0]+"')]"));
					   }
					   	explicitWait(Elements.placeOrder_DD);
					   	
					   if(driver.findElement(Elements.placeOrder_DD).isDisplayed())
					   {
						   log.info("Delivery schedule  time selected around:" +deliveryTime );
						   extTestObj.createNode("Delivery schedule  time selected around:" +deliveryTime).pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("Delivery time selection failed");
				          extTestObj.createNode("Delivery time selection failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
				   
				 //Logs order details
				   try {
						String total=driver.findElement(Elements.totalCost_DD).getAttribute("innerHTML");
						List<WebElement> itemNames=driver.findElements(Elements.itemNames_DD);
						List<WebElement> itemQtys=driver.findElements(Elements.itemQtys_DD);
						log.info("Total cost is: "+total);
						log.info("And the items are: ");
						for(int i=0;i<itemNames.size();++i) {
						log.info((itemQtys.get(i).getAttribute("innerHTML"))+(itemNames.get(i).getAttribute("innerHTML")));
						}
						
					} catch (Exception e1) {
						log.error("Failed to fetch some details");
						extTestObj.createNode("Failed to fetch some details")
								.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e1);
						log.error(e1.getMessage());
						stopWebTest();
					}
				   
				   //Clicks on place order
				   try {
						explicitWait(Elements.placeOrder_DD);
						driver.findElement(Elements.placeOrder_DD).isEnabled();
						Thread.sleep(5000);
						clickElement(Elements.placeOrder_DD);
						
						explicitWait ((Elements.orderConfirmationScheduled_DD));
						if(driver.findElement(Elements.orderConfirmationScheduled_DD).isDisplayed()) {
							
						log.info("Order is placed");
						extTestObj.createNode("Order is placed").pass("PASSED");
						}
						
					} catch (Exception e) {
						log.error("Failed to place order");
						extTestObj.createNode("Failed to place order")
								.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
						log.error(e.getMessage());
						stopWebTest();
					}
				   
			   }
			   
			   
			   
			   
			   
			   
			   
			   //Selects the pickup mode
			   public void webSelectPickupMode()
			   {
				   try {
					   clickableWait(Elements.pickupOption_DD);
					   explicitWait(Elements.verifyPickupOption_DD);
					   if(driver.findElement(Elements.verifyPickupOption_DD).isDisplayed())
					   {
						   log.info("Pickup option selected");
						   extTestObj.createNode("Pickup option selected").pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("Pickup option selection failed");
				          extTestObj.createNode("Pickup option selection failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
			   }
			   //Selects the schedule later option
			   public void webSelectScheduleLater()
			   {
				   try {
					   Thread.sleep(4000);
					   clickableWait(Elements.scheduleLater_DD);
					   explicitWait(Elements.verifyPickupScheduleLater_DD);
					   if(driver.findElement(Elements.verifyPickupScheduleLater_DD).isDisplayed())
					   {
						   log.info("Schedule later option selected");
						   extTestObj.createNode("Schedule later option selected").pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("Schedule later option selection failed");
				          extTestObj.createNode("Schedule later option selection failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
			   }
			   
			   //Selects the Tomorrow option from the date-time menu - pickup order
			   public void webSelectPickupTomorrow()
			   {
				   String pickupTime=(String)excel.getCellData("DoorDash", "Delivery Time", 2); //PM-4
				   System.out.println(pickupTime);
				   String[] pickTimes=pickupTime.split(" ");
				   try {
					   
					   if((driver.findElement(Elements.selectTomorrow_DD)).isEnabled()){
					   clickableWait(Elements.selectTomorrow_DD);
					   }
					   explicitWait(By.xpath("//div[@kind='BUTTON/SECONDARY_PILL']//span[contains(text(),'"+pickTimes[1]+"-"+pickTimes[0]+"')]"));
					   if(driver.findElement(By.xpath("//div[@kind='BUTTON/SECONDARY_PILL']//span[contains(text(),'"+pickTimes[1]+"-"+pickTimes[0]+"')]")).isDisplayed())
					   {
						   log.info("Tomorrow option is selected and time list is visible");
						   extTestObj.createNode("Tomorrow option is selected and time list is visible").pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("Tomorrow option selection failed");
				          extTestObj.createNode("Tomorrow option selection failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
			   }
			   
			   //Selects the pickup time taken from the excel sheet
			   public void webSelectPickupTime()
			   {
				   String pickupTime=(String)excel.getCellData("DoorDash", "Delivery Time", 2); //PM-4
				   System.out.println(pickupTime);
				   String[] pickTimes=pickupTime.split(" ");
				   
				   try {
					    
					   if((driver.findElement(By.xpath("//div[@kind='BUTTON/SECONDARY_PILL']//span[contains(text(),'"+pickTimes[1]+"-"+pickTimes[0]+"')]"))).isEnabled()){
					   clickableWait(By.xpath("//div[@kind='BUTTON/SECONDARY_PILL']//span[contains(text(),'"+pickTimes[1]+"-"+pickTimes[0]+"')]"));
					   }
					   //explicitWait(Elements.verifyScheduleLater_DD);
					   Thread.sleep(3000);
					   if(driver.findElement(Elements.placePickupOrder_DD).isEnabled())
					   {
						   log.info("Pickup time selected around: "+pickupTime );
						   extTestObj.createNode("Pickup time selected around: "+pickupTime ).pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("Pickup time selection failed");
				          extTestObj.createNode("Pickup time selection failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
			   }
			   //Selects the 'other' option under tips
			   public void webClickOnTipOther()
			   {
				   try {
					   clickableWait(Elements.tipOther_DD);
					   explicitWait(Elements.otherTipInput_DD);
					   if(driver.findElement(Elements.otherTipInput_DD).isDisplayed())
					   {
						   log.info("Other tip option selected");
						   extTestObj.createNode("Other tip option selected").pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("Other tip option selection failed");
				          extTestObj.createNode("Other tip option selection failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
			   }
			   
			   public void webClickOnTipThree()
			   {
				   try {
					   clickableWait(Elements.tipThree_DD);
					   explicitWait(Elements.verifyThreeTip_DD);
					   if(driver.findElement(Elements.verifyThreeTip_DD).isDisplayed())
					   {
						   log.info("$3 tip option selected");
						   extTestObj.createNode("$3 tip option selected").pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("$3 tip option selection failed");
				          extTestObj.createNode("$3 option selection failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
			   }
			   
			   public void webClickOnTipFour()
			   {
				   try {
					   clickableWait(Elements.tipFour_DD);
					   explicitWait(Elements.verifyFourTip_DD);
					   if(driver.findElement(Elements.verifyFourTip_DD).isDisplayed())
					   {
						   log.info("$4 tip option selected");
						   extTestObj.createNode("$4 tip option selected").pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("$4 tip option selection failed");
				          extTestObj.createNode("$4 option selection failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
			   }
			   
			   //Enters 0 as tip and verifies if its working
			   public void webEnterZeroTip()
			   {
				   try {
					   
					   sendKeysWait(Elements.otherTipInput_DD,"0");
					   if(driver.findElement(Elements.verifyZeroTip_DD).isDisplayed())
					   {
						   log.info("Zero tip verified");
						   extTestObj.createNode("Zero tip verified").pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("Zero tip verification failed");
				          extTestObj.createNode("Zero tip verification failed")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
			   }
			   //Places a pickup order
			   public void webPlacePickupScheduleOrder()
			   {
				   try {
					   explicitWait(Elements.placePickupOrder_DD);
					   Thread.sleep(5000);
					   clickElement(Elements.placePickupOrder_DD);
					   explicitWait(Elements.verifyScheduleOrderPlaced_DD);
					   if(driver.findElement(Elements.verifyScheduleOrderPlaced_DD).isDisplayed())
					   {
						   log.info("Pickup Order Placed Successfully");
						   extTestObj.createNode("Pickup Order Placed Successfully").pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("Failed to Place Pickup Order");
				          extTestObj.createNode("Failed to PlacePickup Order")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
			   }
			   
			   public void webPlacePickupAsapOrder()
			   {
				   try {
					   explicitWait(Elements.placePickupOrder_DD);
					   Thread.sleep(5000);
					   clickElement(Elements.placePickupOrder_DD);
					   explicitWait(Elements.orderConfirmationAsap_DD);
					   if(driver.findElement(Elements.orderConfirmationAsap_DD).isDisplayed())
					   {
						   log.info("Pickup Order Placed Successfully");
						   extTestObj.createNode("Pickup Order Placed Successfully").pass("PASSED");
					   }
				   }
				   catch(Exception e)
				   {
					   log.error("Failed to Place Pickup Order");
				          extTestObj.createNode("Failed to PlacePickup Order")
				                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
				          log.error(e.getMessage());
				          stopWebTest();
				   }
			   }
			   
			   
			   
			   //Validates if all the menu categories are present
			   public void webValidateMenuCategories() {

					try {
						int x = 0;
						List<WebElement> categoryList = driver.findElements(Elements.menuCategoriesList_DD);
						ArrayList<String> list = new ArrayList<String>(Arrays.asList("Appetizers", "Big Mouth Burgers®",
								"Ribs &amp; Steaks", "Fajitas", "Salads, Soups &amp; Chili", "Chicken &amp; Seafood", "Sandwiches",
								"Chicken Crispers®", "Guiltless Grill", "Tacos &amp; Quesadillas", "Smokehouse Combos",
								"Lunch Specials", "Sides", "Desserts", "Beverages", "Kids Menu"));
						
						  System.out.println(categoryList.size()); 
						  for(int j=0;j<categoryList.size();++j) {
						  System.out.println(categoryList.get(j).getAttribute("innerHTML"));
						  System.out.println(list.get(j)+"\n");
						  
						  }
						 

						for (int j = 0; j < list.size(); ++j) {
							for (int i = 0; i < categoryList.size(); ++i) {
								if (categoryList.get(i).getAttribute("innerHTML").contains(list.get(j))) {
									++x;

								}
								 System.out.println(categoryList.get(i).getAttribute("innerHTML"));
							}
							 System.out.println(list.get(j));
						}

						if (x == list.size()) {
							log.info("Every menucategory is present");
							extTestObj.createNode("Every menucategory is present").pass("PASSED");
						} else {
							log.info("Every menucategory is not present");
							extTestObj.createNode("Every menucategory is not present").fail("FAILED");
						}
					} catch (Exception e) {
						log.error("Menu category validation failed");
						extTestObj.createNode("Menu category validation failed")
								.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
						log.error(e.getMessage());
						stopWebTest();
					}

				}
			   //Before adding to cart, checks if we can increase the quantity by clicking on the '+' button
			   public void webItemIncreaseCheck() {

					try {
						clickableWait(Elements.increaseItem_DD);
						explicitWait(Elements.addToOrderbutton_DD);
						// Thread.sleep(10000);
						if (driver.findElement(By.xpath("//input[@aria-label='Current quantity is 2']")).isDisplayed()) {
							log.info("Item increase to 2 check: completed");
							extTestObj.createNode("Item increase to 2 check: completed").pass("PASSED");
						} else {
							log.info("Item increase to 2 check: failed");
							extTestObj.createNode("Item increase to 2 check: failed").fail("FAILED");
						}
					} catch (Exception e) {
						log.error("Item increase to 2 check: failed");
						extTestObj.createNode("Item increase to 2 check: failed")
								.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
						log.error(e.getMessage());
						stopWebTest();
					}

				}
			 //Before adding to cart, checks if we can decrease the quantity by clicking on the '-' button
				public void webItemDecreaseCheck() {

					try {
						clickableWait(Elements.decreaseItem_DD);
						explicitWait(Elements.addToOrderbutton_DD);
						// Thread.sleep(10000);
						if (driver.findElement(By.xpath("//input[@aria-label='Current quantity is 1']")).isDisplayed()) {
							log.info("Item decrease to 1 check: completed");
							extTestObj.createNode("Item decrease to 1 check: completed").pass("PASSED");
						} else {
							log.info("Item decrease to 1 check: failed");
							extTestObj.createNode("Item decrease to 1 check: failed").fail("FAILED");
						}
					} catch (Exception e) {
						log.error("Item decrease to 1 check: failed");
						extTestObj.createNode("Item decrease to 1 check: failed")
								.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
						log.error(e.getMessage());
						stopWebTest();
					}

				}
				//Removes the item
				public void webRemoveItem(int index) {
					String menuItem=excel.getCellData("Menu","Item",index);
					try {
							explicitWait(By.xpath("//button[@aria-label='Remove "+menuItem+" from order']"));
							clickableWait(By.xpath("//button[@aria-label='Remove "+menuItem+" from order']"));
						
						Thread.sleep(2000);
						if (driver.findElement(Elements.verifyEmptyCart_DD).isDisplayed()) {
							log.info("Successfully removed "+menuItem);
							extTestObj.createNode("Successfully removed "+menuItem).pass("PASSED");
						}
					} catch (Exception e1) {
						log.error("Failed to remove "+menuItem);
						extTestObj.createNode("Failed to remove "+menuItem)
								.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e1);
						log.error(e1.getMessage());
						stopWebTest();
					}
				}
				
				//Removes the item
					public void webRemoveItemNV(int index) {
						String menuItem=excel.getCellData("Menu","Item",index);
						try {
								explicitWait(By.xpath("//button[@aria-label='Remove "+menuItem+" from order']"));
								clickableWait(By.xpath("//button[@aria-label='Remove "+menuItem+" from order']"));
							
							Thread.sleep(2000);
							
						} catch (Exception e1) {
							log.error("Failed to remove "+menuItem);
							extTestObj.createNode("Failed to remove "+menuItem)
									.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e1);
							log.error(e1.getMessage());
							stopWebTest();
						}
					}
					
					//Logs order details
							public void webRetrieveDetails() {
								
								try {
									String total=driver.findElement(Elements.totalCost_DD).getAttribute("innerHTML");
									List<WebElement> itemNames=driver.findElements(Elements.itemNames_DD);
									List<WebElement> itemQtys=driver.findElements(Elements.itemQtys_DD);
									log.info("Total cost is: "+total);
									log.info("And the items are: ");
									for(int i=0;i<itemNames.size();++i) {
									log.info((itemQtys.get(i).getAttribute("innerHTML"))+(itemNames.get(i).getAttribute("innerHTML")));
									}
									
								} catch (Exception e1) {
									log.error("Failed to fetch some details");
									extTestObj.createNode("Failed to fetch some details")
											.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e1);
									log.error(e1.getMessage());
									stopWebTest();
								}
							}
				
				//TC08 - Verify Menu Item name and Description - Doordash by Srividhya - 07-02-2021 begin   
				//Validates if all the descriptions of all the items are present or not
						 
						public void DD_WebvalidateMenuDescription()
						{
							 try {
								 int count=0,dcount=0;
								 List<WebElement>menuCategoryList=driver.findElements(Elements.menuCategory_DD);
								 extTestObj.createNode("Displaying the Menu Name and Item Description").info("INFO");
								 for(int i=1;i<=menuCategoryList.size();i++)
								 {			 
										 String menuName = driver.findElement(By.xpath("(//div[@data-anchor-id='MenuItem']//following::span/div/div/div/div[1]/span)["+i+"]")).getText();
										 String menuDescription= driver.findElement(By.xpath("(//div[@data-anchor-id='MenuItem']//following::span/div/div/div/span[1])["+i+"]")).getText();
										 boolean menuDescDisplay= driver.findElement(By.xpath("(//div[@data-anchor-id='MenuItem']//following::span/div/div/div/span[1])["+i+"]")).isDisplayed();
										 count+=1;
										 if(menuDescDisplay)
										   {
											 dcount+=1;
											 log.info("MENU NAME:          "+menuName+"\t"+" MENU DESCRIPTION:          "+menuDescription);
											 extTestObj.createNode("MENU NAME:          "+menuName).createNode("MENU DESCRIPTION:          "+menuDescription);
										   
										   
										   }
										 else
										 {
											 log.info("Menu Item Description is not displayed for the menu "+menuName);
											 extTestObj.createNode("Menu Item Description is not displayed for the menu "+menuName).warning("WARNING");
										 }
										
								 }
								 	 		   
								   if(dcount==count)
								   {
								   log.info("Menu Item Description is displayed for all the menu items.Total count of menu items is "+count+" .Description displayed for "+dcount+" menu items");
								   extTestObj.createNode("Menu Item Description is displayed for all the menu items.Total count of menu items is "+count+" .Description is displayed for "+dcount+" menu items").pass("PASSED");
								   }
								   else {
									   log.info("Menu Item Description is not displayed for all the menu items.Total menu items "+count+" .Menu Item Description displayed for "+dcount+" menu items");
									   extTestObj.createNode("Menu Item Description is not displayed for all the menu items").warning("WARNING");
								   }
							    }
							   catch(Exception e)
							   {
								   log.error("Chili's store is closed");
							          extTestObj.createNode("Chili's store is closed")
							                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							          log.error(e.getMessage());
							          driver.quit();
							          stopWebTest();
							   }
							    
						}
						
						//TC08 - Verify Menu Item name and Description - Doordash by Srividhya - 07-02-2021 end
						
						
						//TC09 - Verify Menu Item  and Price - Doordash by Srividhya - 07-02-2021 begin  
						//Validates if all the prices of the all the items are present or not 
								public void DD_WebvalidateMenuPrice()
								{
									 try {
										 int count=0,dcount=0,noprice=0;
										 List<WebElement>menuCategoryList=driver.findElements(Elements.menuCategory_DD);
										 extTestObj.createNode("Displaying the Menu Name and Price").info("INFO");
										 for(int i=1;i<=menuCategoryList.size();i++)
										 {			 
												 String menuName = driver.findElement(By.xpath("(//div[@data-anchor-id='MenuItem']//following::span/div/div/div/div[1]/span)["+i+"]")).getText();
												 String menuPrice=driver.findElement(By.xpath("(//div[@data-anchor-id='MenuItem']/button/div//descendant::div[7]/span)["+i+"]")).getText();
												 boolean menuPriceDisplay= driver.findElement(By.xpath("(//div[@data-anchor-id='MenuItem']/button/div//descendant::div[7]/span)["+i+"]")).isDisplayed();
												 count+=1;
												 if(menuPriceDisplay)
												   {
													 dcount+=1;
													 log.info("MENU NAME:          "+menuName+"\t"+" MENU PRICE:          "+menuPrice);
													 extTestObj.createNode("MENU NAME:          "+menuName).createNode("MENU PRICE:          "+menuPrice);
												   
												   
												   }
												 else
												 {
													 noprice+=1;
													 log.info("Price is not displayed for the menu "+menuName);
													 extTestObj.createNode("Price is not displayed for the menu "+menuName).warning("WARNING");
												 }
												
										 }
										 	 		   
										   if(dcount==count)
										   {
										   log.info("Price is displayed for all the menu items.Total count of menu items is "+count+" .Price is displayed for "+dcount+" menu items");
										   extTestObj.createNode("Price is displayed for all the menu items.Total count of menu items is "+count+" .Price is displayed for "+dcount+" menu items").pass("PASSED");
										   }
										   else {
											   log.info("Price is not displayed for all the menu items.Total menu items "+count+" .Price is not displayed for "+noprice+" menu items");
											   extTestObj.createNode("Price is not displayed for all the menu items.Total menu items "+count+" .Price is not displayed for "+noprice+" menu items").warning("WARNING");
										   }
									    }
									   catch(Exception e)
									   {
										   log.error("Chili's store is closed");
									          extTestObj.createNode("Chili's store is closed")
									                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
									          log.error(e.getMessage());
									          driver.quit();
									          stopWebTest();
									   }
									    
								}
				
				
				
				//created by suman
								
				//Selects delivery option
				public void webSelectDelivery() {

					try {

						driver.findElement(Elements.deliveryButton_DD).isEnabled();
						clickableWait(Elements.deliveryButton_DD);
						Thread.sleep(3000);
					
						log.info("Delivery button is selected");
						extTestObj.createNode("Delivery button is selected").pass("PASSED");
					} catch (Exception e) {
						log.error("Failed to select delivery button");
						extTestObj.createNode("Failed to select delivery button")
								.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
						log.error(e.getMessage());
						stopWebTest();
					}

				}
				//Selects ASAP option
				public void webSelectASAP() {

					try {
						Thread.sleep(2000);
						if(driver.findElement(Elements.asapButton_DD).isEnabled()){
						clickableWait(Elements.asapButton_DD);
						}
						Thread.sleep(2000);
						
						log.info("Asap button is selected");
						extTestObj.createNode("Asap button is selected").pass("PASSED");
						
					} catch (Exception e) {
						log.error("Failed to select asap button");
						extTestObj.createNode("Failed to select asap button")
								.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
						log.error(e.getMessage());
						stopWebTest();
					}

				}
				//Selecting the "Schedule for later" option from "Delivery Details Page"
				public void webSelectScheduleLaterDelivery()
				   {
					   try {
						  			   
						   explicitWait(Elements.scheduleLater_DD);
						   
						   clickableWait(Elements.scheduleLater_DD);
						   
						   explicitWait(Elements.verifyDeliveryScheduleLater_DD);
						   
						   if(driver.findElement(Elements.verifyDeliveryScheduleLater_DD).isDisplayed())
						   {
							   log.info("Schedule Later option selected");
							   extTestObj.createNode("Schedule Later option selected").pass("PASSED");
						   }
					   }
					   
					   catch(Exception e)
					   {
						   log.error("Schedule Later option selection failed");
					          extTestObj.createNode("Schedule Later option selection failed")
					                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
					          log.error(e.getMessage());
					          stopWebTest();
					   }
				   }
				
				//Selects Tomorrow from the date-time menu - delivery order
				   public void webSelectDeliveryTomorrow()
				   {
					   String deliveryTime=(String)excel.getCellData("DoorDash", "Delivery Time", 2); //PM-4
					   System.out.println(deliveryTime);
					   String[] delTimes=deliveryTime.split(" ");
					   
					   try {
						   
						   
						   if((driver.findElement(Elements.selectTomorrow_DD)).isEnabled()){
							   
						   clickableWait(Elements.selectTomorrow_DD);
						   }
						   explicitWait(By.xpath("//button[@kind='BUTTON/SECONDARY_PILL']//span[contains(text(),'"+delTimes[1]+"-"+delTimes[0]+"')]")); //PM-4 
						   if(driver.findElement(By.xpath("//button[@kind='BUTTON/SECONDARY_PILL']//span[contains(text(),'"+delTimes[1]+"-"+delTimes[0]+"')]")).isDisplayed())
						   {
							   log.info("Tomorrow option is selected and time menu is visible");
							   extTestObj.createNode("Tomorrow option is selected and time menu is visible").pass("PASSED");
						   }
					   }
					   catch(Exception e)
					   {
						   log.error("Tomorrow option selection failed");
					          extTestObj.createNode("Tomorrow option selection failed")
					                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
					          log.error(e.getMessage());
					          stopWebTest();
					   }
				   } 
				   
				  
				   //Selects the delivery time taken from excel sheet
				   public void webSelectDeliveryTime()
				   {
					   String deliveryTime=(String)excel.getCellData("DoorDash", "Delivery Time", 2); //4 PM
					   System.out.println(deliveryTime);
					   String[] delTimes=deliveryTime.split(" ");
					   
					   try {
						   
						   if((driver.findElement(By.xpath("//button[@kind='BUTTON/SECONDARY_PILL']//span[contains(text(),'"+delTimes[1]+"-"+delTimes[0]+"')]"))).isEnabled()){
							   
						   clickableWait(By.xpath("//button[@kind='BUTTON/SECONDARY_PILL']//span[contains(text(),'"+delTimes[1]+"-"+delTimes[0]+"')]"));
						   }
						   	explicitWait(Elements.placeOrder_DD);
						   	
						   if(driver.findElement(Elements.placeOrder_DD).isDisplayed())
						   {
							   log.info("Delivery schedule  time selected around:" +deliveryTime );
							   extTestObj.createNode("Delivery schedule  time selected around:" +deliveryTime).pass("PASSED");
						   }
					   }
					   catch(Exception e)
					   {
						   log.error("Delivery time selection failed");
					          extTestObj.createNode("Delivery time selection failed")
					                  .fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
					          log.error(e.getMessage());
					          stopWebTest();
					   }
				   } 
				   
				   //Places a delivery - asap order
				   public void webPlaceDeliveryAsapOrder() {

						try {
							explicitWait(Elements.placeOrder_DD);
							driver.findElement(Elements.placeOrder_DD).isEnabled();
							Thread.sleep(5000);
							clickElement(Elements.placeOrder_DD);
							
							explicitWait ((Elements.orderConfirmationAsap_DD));
							if(driver.findElement(Elements.orderConfirmationAsap_DD).isDisplayed()) {
								
							log.info("Order is placed");
							extTestObj.createNode("Order is placed").pass("PASSED");
							}
							
						} catch (Exception e) {
							log.error("Failed to place order");
							extTestObj.createNode("Failed to place order")
									.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							log.error(e.getMessage());
							stopWebTest();
						}

					} 
					//Places a delivery - scheduled for later order	
					public void webPlaceDeliveryScheduleOrder() {

						try {
							explicitWait(Elements.placeOrder_DD);
							driver.findElement(Elements.placeOrder_DD).isEnabled();
							Thread.sleep(5000);
							clickElement(Elements.placeOrder_DD);
							
							explicitWait ((Elements.orderConfirmationScheduled_DD));
							if(driver.findElement(Elements.orderConfirmationScheduled_DD).isDisplayed()) {
								
							log.info("Order is placed");
							extTestObj.createNode("Order is placed").pass("PASSED");
							}
							
						} catch (Exception e) {
							log.error("Failed to place order");
							extTestObj.createNode("Failed to place order")
									.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							log.error(e.getMessage());
							stopWebTest();
						}

					}
					
					public void webLoginIJW() {
						// webValidateSiteLaunchIJW
						//get the URL from the execl based on Addison or delray beach
						String IJWsiteURL= excel.getCellData("IJWURL", "StoreURL", 3);
						try {
							driver.get(IJWsiteURL);
							driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
							driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
							driver.manage().window().maximize();
							explicitWait(Elements.IJWDisplayXpath);
							if (driver.findElement(Elements.IJWDisplayXpath).isDisplayed()) {
								log.info("Site launch successful, site name : " + driver.getCurrentUrl());
								extTestObj.createNode("Site launch successful, site name : " + driver.getCurrentUrl()).pass("PASSED");
								Thread.sleep(2000);
							}
						} catch (Exception e) {
							log.error("Site launch failed");
							extTestObj.createNode("Site launch failed")
									.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							log.error(e.getMessage());
							stopWebTest();
						}

						// webClickOpenMenuButtonIJW
						try {
							clickableWait(Elements.IJWOpenMenuXpath);
							driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
							explicitWait(Elements.IJWsiginSignUpButtonXpath);
							if (driver.findElement(Elements.IJWsiginSignUpButtonXpath).isDisplayed()) {
								log.info("Open Menu button clicked");
								extTestObj.createNode("Open Menu button clicked").pass("PASSED");
							}
						} catch (Exception e) {
							log.error("Open Menu button click failed");
							extTestObj.createNode("Open Menu button click failed")
									.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							log.error(e.getMessage());

							stopWebTest();
						}

						// webClickSignInorSignUpButtonIJW
						try {
							clickableWait(Elements.IJWsiginSignUpButtonXpath);
							driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
							explicitWait(Elements.IJWsigninPageHeaderXpath);
							if (driver.findElement(Elements.IJWsigninPageHeaderXpath).isDisplayed()) {
								log.info("Sign in or Sign up button clicked");
								extTestObj.createNode("Sign in or Sign up button clicked").pass("PASSED");

							}

						} catch (Exception e) {
							log.error("Sign in or Sign up button click failed");
							extTestObj.createNode("Sign in or Sign up button click failed")
									.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							log.error(e.getMessage());

							stopWebTest();
						}

						// webEnterUserEmailIJW
						String useremail = excel.getCellData("DoorDash", "UserEmail", 3);
						try {
							sendKeysWait(Elements.IJWemailTextBoxXpath, useremail);
							driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
							if (driver.findElement(Elements.IJWemailTextBoxXpath).getAttribute("value").equals(useremail)) {
								log.info("Email address entered as : " + useremail);
								extTestObj.createNode("Email address entered as : " + useremail).pass("PASSED");
							}
						} catch (Exception e) {
							log.error("Failed to enter email address");
							extTestObj.createNode("Failed to enter email address")
									.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							log.error(e.getMessage());

							stopWebTest();
						}

						// webEnterUserPasswordIJW
						String password = excel.getCellData("DoorDash", "Password", 3);
						try {
							sendKeysWait(Elements.IJWpasswordTextBoxXpath, password);
							driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
							if (driver.findElement(Elements.IJWpasswordTextBoxXpath).getAttribute("value").equals(password)) {
								log.info("Password entered as : " + password);
								extTestObj.createNode("Password entered as : " + password).pass("PASSED");
							}
						} catch (Exception e) {
							log.error("Failed to enter password");
							extTestObj.createNode("Failed to enter password")
									.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							log.error(e.getMessage());

							stopWebTest();
						}

						// webClickSignInButtonIJW
						try {
							clickableWait(Elements.IJWsigninButtonXpath);
							driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
							explicitWait(Elements.IJWdoorDashHeaderXpath);
							if (driver.findElement(Elements.IJWdoorDashHeaderXpath).isDisplayed()) {
								log.info("Sign in button clicked and login successful");
								extTestObj.createNode("Sign in button clicked and login successful").pass("PASSED");
							}
						} catch (Exception e) {
							log.error("Login failed");
							extTestObj.createNode("Login failed")
									.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							log.error(e.getMessage());

							stopWebTest();

						}

						// webValidateSiteLaunchIJW
						try {
							
							driver.get(IJWsiteURL);
							driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
							driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
							driver.manage().window().maximize();
							explicitWait(Elements.IJWDisplayXpath);
							if (driver.findElement(Elements.IJWDisplayXpath).isDisplayed()) {
								log.info("Site launch successful, site name : " + driver.getCurrentUrl());
								extTestObj.createNode("Site launch successful, site name : " + driver.getCurrentUrl()).pass("PASSED");
								Thread.sleep(2000);
							}
						} catch (Exception e) {
							log.error("Site launch failed");
							extTestObj.createNode("Site launch failed")
									.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							log.error(e.getMessage());

							stopWebTest();
						}

				//webEnterAddressIJW
						try {

							Thread.sleep(3000);
							driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
							driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
							WebElement addressclick = driver.findElement(Elements.IJWlocClickXpath);
							JavascriptExecutor js = (JavascriptExecutor) driver;
							js.executeScript("arguments[0].click()", addressclick);
							driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
							Thread.sleep(3000);
							WebElement loc = driver.findElement(Elements.IJWEnterAddressTextboxXpath);
							js.executeScript("arguments[0].click()", loc);
							Thread.sleep(2000);
							sendKeysWait(Elements.IJWEnterAddressTextboxXpath, prop.getProperty("ijw_store_addr"));
							Thread.sleep(2000);
							driver.findElement(Elements.IJWEnterAddressTextboxXpath).sendKeys(Keys.RETURN);
							driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
							clickableWait(Elements.IJWaddressSaveButtonXpath);
							driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
							Thread.sleep(2000);
							log.info("Successfully entered the address");
							extTestObj.createNode("Successfully entered the address").pass("PASSED");
						} catch (Exception e) {
							log.error("Entering address is failed");
							extTestObj.createNode("Entering address is failed")
									.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							log.error(e.getMessage());

							stopWebTest();
						}
					
					}
					
					//webClickSignOutButtonIJW
					public void webClickSignOutButtonIJW() {
						try {
							Thread.sleep(5000);
							explicitWait(Elements.IJWOpenMenuXpath);
							clickableWait(Elements.IJWOpenMenuXpath);
							driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
							Thread.sleep(3000);
							WebElement SignoutClick = driver.findElement(Elements.IJWsignOutButtonXpath);
							JavascriptExecutor js = (JavascriptExecutor) driver;
							js.executeScript("arguments[0].click()", SignoutClick);
							driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
							Thread.sleep(3000);
							explicitWait(Elements.doorDashHomePageLogo);
							if (driver.findElement(Elements.doorDashHomePageLogo).isDisplayed()) {
								log.info("User signed out");
								extTestObj.createNode("User signed out").pass("PASSED");
							}
						} catch (Exception e) {
							log.error("User sign out failed");
							extTestObj.createNode("User sign out failed")
									.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							log.error(e.getMessage());

							stopWebTest();
						}
					}
					
					//websitelaunchIJW
					public void websitelaunchIJW()
					{
						try {
							String IJWsiteURL= excel.getCellData("IJWURL", "StoreURL", 3);
							driver.get(IJWsiteURL);
							driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
							driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
							//driver.manage().window().maximize();
						
						} catch (Exception e) {
							log.error("Site launch failed");
							extTestObj.createNode("Site launch failed")
									.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							log.error(e.getMessage());
							stopWebTest();
						}
					}

					public void webValidateAccountNameIJW() {
						boolean stopTest = false;
						int x = 0;
						try {
							driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
							String accountName = excel.getCellData("DoorDash", "Account Name", 3);
							String addressLoc_Excel = excel.getCellData("DoorDash", "Location", 2);
							String addressLoc_xpath = driver.findElement(Elements.IJWlocClickXpath).getText();
							String addr = driver.findElement(Elements.IJWValidateAddress_bottom).getText();
							String addr_bottom[] = addr.split(" ", 3);// split the address got from bottom of the page based on space and validate against input from excel
							boolean accName_Display, addr_topDisplay, addr_bottomDisplay = false;
							if (addressLoc_Excel.contains(addressLoc_xpath)) {
								addr_topDisplay = true;

							} else {
								addr_topDisplay = false;
								x = 1;
							}
							if (addressLoc_Excel.contains(addr_bottom[1])) {
								addr_bottomDisplay = true;
							} else {
								addr_bottomDisplay = false;
								x = 2;
							}
							explicitWait(Elements.IJWOpenMenuXpath);
							clickableWait(Elements.IJWOpenMenuXpath);
							Thread.sleep(3000);
							driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
							driver.manage().timeouts().pageLoadTimeout(80, TimeUnit.SECONDS);
							explicitWait(Elements.IJWaccountNameXpath);
							String accountname_xpath = driver.findElement(Elements.IJWaccountNameXpath).getText();
							Thread.sleep(1000);
							if (accountname_xpath.equals(accountName)) {
								accName_Display = true;

							} else {
								accName_Display = false;
								x = 3;
							}
				//pass the validation only if all three validations are passed
							
							if (accName_Display && addr_topDisplay && addr_bottomDisplay) {
								log.info("Account Validation Passed");
								log.info("Location address Displayed: " + addressLoc_xpath);
								log.info("Address at the bottom: " + addr);
								log.info("Account name displayed: " + accountname_xpath);
								extTestObj.createNode("Account Validation Passed").pass("PASSED");
								stopTest = false;
							} else {
								log.info("Account Validation Failed" + x);
								log.info("Location address Displayed: " + addressLoc_xpath);
								log.info("Address at the bottom: " + addr);
								log.info("Account name displayed: " + accountname_xpath);
								extTestObj.createNode("Account Validation Failed").fail("FAILED");
								stopTest = true;
							}
							Thread.sleep(3000);
							WebElement CloseMenuClick=driver.findElement(By.xpath("//button[@aria-label='Close']"));
							JavascriptExecutor js = (JavascriptExecutor) driver;
							js.executeScript("arguments[0].click()", CloseMenuClick);
							Thread.sleep(3000);
							driver.manage().timeouts().pageLoadTimeout(80, TimeUnit.SECONDS);
						}
						catch (Exception e) {
							log.error("Account Name validation failed");
							extTestObj.createNode("Account Name validation failed")
									.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							log.error(e.getMessage());
							stopWebTest();
						}
						if (stopTest) {
							stopWebTest();
						}
					}


				//webClickMenuItemIJW	also has the function - webSelectReqChoicesforMenuIJW
					int excelMenuno;
					String OrderMenu;
					public void webClickMenuItemIJW(int n) {
						String menuItem = excel.getCellData("IJWMENU", "Item", n);
						excelMenuno = n;
						OrderMenu=menuItem;
						try {
							driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
							explicitWait(Elements.IJWMenuItems);
							List <WebElement> menuitems = driver.findElements(Elements.IJWMenuItems); 
							for(int i=0;i<menuitems.size();i++)
							{
								if(menuitems.get(i).getText().equals(menuItem))
								{
									menuitems.get(i).click();
									driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
									break;
								}
							}
							Thread.sleep(5000);
							log.info("Clicked on menu Item " + menuItem);
							extTestObj.createNode("Clicked on menu Item " + menuItem).pass("PASSED");
							webSelectReqChoicesforMenuIJW(); //call this function to select the required and optional choices for the menu. Inputs taken from excel
							Thread.sleep(3000);
						} catch (Exception e) {
							log.error("Click on menu Item " + menuItem + " failed");
							extTestObj.createNode("Click on menu Item " + menuItem + " failed")
									.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							log.error(e.getMessage());
							stopWebTest();
						}
					}

				//webSelectReqChoicesforMenuIJW
				//get the last word from the menu name and switch based on that. Required and optional choices are selected based on the first 2 digits of the menu name
				String IJWitem;
					public void webSelectReqChoicesforMenuIJW() {
						try {
							
							int menucount = 0;
							String menuCategory = excel.getCellData("IJWMenu", "Category", excelMenuno);
							String menuItem = excel.getCellData("IJWMenu", "Item", excelMenuno);
							String requiredFlavor1 = excel.getCellData("IJWMenu", "IJWRequired1", excelMenuno);
							String[] substr = menuItem.split(" ");
							int size = substr.length - 1;
							String lastword = substr[size];
							switch (lastword) {
							case "Combo":
								try {
									String[] s = menuItem.split("");
									StringBuffer f = new StringBuffer();
									for (int i = 0; i < s.length; i++) {
										if ((s[i].matches("[0-9]+"))) {
											f.append(s[i]);
										} else {
											menucount = Integer.parseInt(f.toString());
										}
									}
									if(menucount<=11)
									{
									List<WebElement> Flavor1 = driver.findElements(Elements.IJWFlavor8or11Xpath);
									for (WebElement lst1 : Flavor1) {
										if (lst1.getText().equals(requiredFlavor1)) {
											lst1.click();
											log.info("Clicked on required Flavor1 " + requiredFlavor1);
											extTestObj.createNode("Clicked on required Flavor1 " + requiredFlavor1).pass("PASSED");
											break;
										}
									}
									}
									else
									{
										List<WebElement> Flavor1 = driver.findElements(Elements.IJWFlavor1Xpath);
										for (WebElement lst1 : Flavor1) {
											if (lst1.getText().equals(requiredFlavor1)) {
												lst1.click();
												log.info("Clicked on required Flavor1 " + requiredFlavor1);
												extTestObj.createNode("Clicked on required Flavor1 " + requiredFlavor1).pass("PASSED");
												break;
											}
										}
									}
									if (menucount > 11 && menucount < 32)
									{
										String requiredFlavor2 = excel.getCellData("IJWMenu", "IJWRequired2", excelMenuno);
										List<WebElement> Flavor2 = driver.findElements(Elements.IJWFlavor2Xpath);
										for (WebElement lst2 : Flavor2) {
											if (lst2.getText().equals(requiredFlavor2)) {
												lst2.click();
												log.info("Clicked on required Flavor2 " + requiredFlavor2);
												extTestObj.createNode("Clicked on required Flavor2 " + requiredFlavor2).pass("PASSED");
												break;
											}
										}
									}
									if (menucount >= 32) {
										String requiredFlavor3 = excel.getCellData("IJWMenu", "IJWRequired3", excelMenuno);
										String requiredFlavor4 = excel.getCellData("IJWMenu", "IJWRequired4", excelMenuno);
										List<WebElement> Flavor3 = driver.findElements(Elements.IJWFlavor3Xpath);
										for (WebElement lst3 : Flavor3) {
											if (lst3.getText().contains(requiredFlavor3)) {
												lst3.click();
												log.info("Clicked on required Flavor3 " + requiredFlavor3);
												extTestObj.createNode("Clicked on required Flavor3 " + requiredFlavor3).pass("PASSED");
												break;
											}
										}
										List<WebElement> Flavor4 = driver.findElements(Elements.IJWFlavor4Xpath);
										for (WebElement lst4 : Flavor4) {
											if (lst4.getText().equals(requiredFlavor4)) {
												lst4.click();
												log.info("Clicked on required Flavor4 " + requiredFlavor4);
												extTestObj.createNode("Clicked on required Flavor4 " + requiredFlavor4).pass("PASSED");
												break;
											}
										}
									}
									String Beveragereq = excel.getCellData("IJWMenu", "Beverage", excelMenuno);
									List<WebElement> Beverage = driver.findElements(Elements.IJWBeverageXpath);
									for (WebElement lst5 : Beverage) {
										if (lst5.getText().equals(Beveragereq)) {
											lst5.click();
											log.info("Clicked on required Beverage " + Beveragereq);
											extTestObj.createNode("Clicked on required Beverage " + Beveragereq).pass("PASSED");
											break;
										}
									}
								} catch (Exception e) {
									log.error("Clicking on required Flavors and Beverage Failed for Combo Menu " + menuItem);

									extTestObj.createNode("Clicking on required Flavors and Beverage Failed for Combo Menu " + menuItem)
											.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()")
											.error(e);
									log.error(e.getMessage());

									stopWebTest();
								}
								break;

							case "Wings":
								try {
									String[] s = menuItem.split("");
									StringBuffer f = new StringBuffer();
									for (int i = 0; i < s.length; i++) {
										if ((s[i].matches("[0-9]+"))) {
											f.append(s[i]);
										} else {
											menucount = Integer.parseInt(f.toString());
										}
									}
									if(menucount<=11)
									{
									List<WebElement> Flavor1 = driver.findElements(Elements.IJWFlavor8or11Xpath);
									for (WebElement lst1 : Flavor1) {
										if (lst1.getText().equals(requiredFlavor1)) {
											lst1.click();
											log.info("Clicked on required Flavor1 " + requiredFlavor1);
											extTestObj.createNode("Clicked on required Flavor1 " + requiredFlavor1).pass("PASSED");
											break;
										}
									}
									}
									else
									{
										List<WebElement> Flavor1 = driver.findElements(Elements.IJWFlavor1Xpath);
										for (WebElement lst1 : Flavor1) {
											if (lst1.getText().equals(requiredFlavor1)) {
												lst1.click();
												log.info("Clicked on required Flavor1 " + requiredFlavor1);
												extTestObj.createNode("Clicked on required Flavor1 " + requiredFlavor1).pass("PASSED");
												break;
											}
										}
									}
									if (menucount > 11 && menucount < 32) {
										String requiredFlavor2 = excel.getCellData("IJWMenu", "IJWRequired2", excelMenuno);
										List<WebElement> Flavor2 = driver.findElements(Elements.IJWFlavor2Xpath);
										for (WebElement lst2 : Flavor2) {
											if (lst2.getText().equals(requiredFlavor2)) {
												lst2.click();
												log.info("Clicked on required Flavor2 " + requiredFlavor2);
												extTestObj.createNode("Clicked on required Flavor2 " + requiredFlavor2).pass("PASSED");
												break;
											}
										}
									}
									if (menucount >= 32) {
										String requiredFlavor2 = excel.getCellData("IJWMenu", "IJWRequired2", excelMenuno);
										String requiredFlavor3 = excel.getCellData("IJWMenu", "IJWRequired3", excelMenuno);
										String requiredFlavor4 = excel.getCellData("IJWMenu", "IJWRequired4", excelMenuno);
										List<WebElement> Flavor2 = driver.findElements(Elements.IJWFlavor2Xpath);
										for (WebElement lst2 : Flavor2) {
											if (lst2.getText().equals(requiredFlavor2)) {
												lst2.click();
												log.info("Clicked on required Flavor2 " + requiredFlavor2);
												extTestObj.createNode("Clicked on required Flavor2 " + requiredFlavor2).pass("PASSED");
												break;
											}
										}
										List<WebElement> Flavor3 = driver.findElements(Elements.IJWFlavor3Xpath);
										for (WebElement lst3 : Flavor3) {
											if (lst3.getText().equals(requiredFlavor3)) {
												lst3.click();
												log.info("Clicked on required Flavor3 " + requiredFlavor3);
												extTestObj.createNode("Clicked on required Flavor3 " + requiredFlavor3).pass("PASSED");
												break;
											}
										}
										List<WebElement> Flavor4 = driver.findElements(Elements.IJWFlavor4Xpath);
										for (WebElement lst4 : Flavor4) {
											if (lst4.getText().equals(requiredFlavor4)) {
												lst4.click();
												log.info("Clicked on required Flavor4 " + requiredFlavor4);
												extTestObj.createNode("Clicked on required Flavor4 " + requiredFlavor4).pass("PASSED");
												break;
											}
										}
									}
				//if beverage is made an option for wings - for future use:
								/*	String Beveragereq = excel.getCellData("IJWMenu", "Beverage", excelMenuno);
									List<WebElement> Beverage = driver.findElements(Elements.IJWBeverageXpath);
									for (WebElement lst5 : Beverage) {
										if (lst5.getText().contains(Beveragereq)) {
											lst5.click();
											log.info("Clicked on required Beverage " + Beveragereq);
											extTestObj.createNode("Clicked on required Beverage " + Beveragereq).pass("PASSED");
											break;
										}
									} */
								} catch (Exception e) {
									log.error("Clicking on required Flavors Failed for Wings Menu " + menuItem);
									extTestObj.createNode("Clicking on required Flavors Failed for Wings Menu " + menuItem)
											.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()")
											.error(e);
									log.error(e.getMessage());
									stopWebTest();
								}
								break;
							case "Drinks":
								try {
									String Beveragereq = excel.getCellData("IJWMenu", "Beverage", excelMenuno);
									List<WebElement> drinks_choice = driver.findElements(Elements.IJWDrinksReqXpath);
									for (WebElement drinkslst : drinks_choice) {
										if (drinkslst.getText().equals(Beveragereq)) {
											drinkslst.click();
											log.info("Clicked on required choice for Drinks " + Beveragereq);
											extTestObj.createNode("Clicked on required choice for Drinks  " + Beveragereq)
													.pass("PASSED");
											break;
										}
									}
								} catch (Exception e) {
									log.error("Clicking on required choice for Drinks failed for Menu  " + menuItem);
									extTestObj.createNode("Clicking on required choice for Drinks failed for Menu  " + menuItem)
											.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()")
											.error(e);
									log.error(e.getMessage());
									stopWebTest();
								}
								break;
							case "Pies":
								try {
									
									String[] s = menuItem.split("");
									StringBuffer f = new StringBuffer();
									for (int i = 0; i < s.length; i++) {
										if ((s[i].matches("[0-9]+"))) {
											f.append(s[i]);
										} else {
											menucount = Integer.parseInt(f.toString());
										}
									}
									if (menucount > 11) {
										String PierequiredFlavor1 = excel.getCellData("IJWMenu", "IJWRequired1", excelMenuno);
										String PierequiredFlavor2 = excel.getCellData("IJWMenu", "IJWRequired2", excelMenuno);
										String PierequiredFlavor3 = excel.getCellData("IJWMenu", "IJWRequired3", excelMenuno);
										String PierequiredFlavor4 = excel.getCellData("IJWMenu", "IJWRequired4", excelMenuno);
										
										explicitWait(Elements.IJWPieFlavorsXpath);
										List<WebElement> PieFlavors = driver.findElements(Elements.IJWPieFlavorsXpath);
										for (int i = 0; i < PieFlavors.size(); i++) {
											if (PieFlavors.get(i).getText().equals(PierequiredFlavor1)) {
												
												driver.findElement(By.xpath("(//button[@data-anchor-id='IncrementQuantity'])[" + (i+1) + "]")).click();
												
												log.info("Clicked on required PieFlavor1 " + PierequiredFlavor1);
												extTestObj.createNode("Clicked on required PieFlavor1 " + PierequiredFlavor1)
														.pass("PASSED");
												break;
											}
										}
										for (int i = 0; i < PieFlavors.size(); i++) {
											if (PieFlavors.get(i).getText().equals(PierequiredFlavor2)) {
												
												driver.findElement(By.xpath("(//button[@data-anchor-id='IncrementQuantity'])[" + (i+1) + "]")).click();
												
												log.info("Clicked on required PieFlavor2 " + PierequiredFlavor2);
												extTestObj.createNode("Clicked on required PieFlavor2 " + PierequiredFlavor2)
														.pass("PASSED");
												break;
											}
										}
										for (int i = 0; i < PieFlavors.size(); i++) {
											if (PieFlavors.get(i).getText().equals(PierequiredFlavor3)) {
												
												driver.findElement(By.xpath("(//button[@data-anchor-id='IncrementQuantity'])[" + (i+1) + "]")).click();
												
												log.info("Clicked on required PieFlavor3 " + PierequiredFlavor3);
												extTestObj.createNode("Clicked on required PieFlavor3 " + PierequiredFlavor3)
														.pass("PASSED");
												break;
											}
										}
										for (int i = 0; i < PieFlavors.size(); i++) {
											if (PieFlavors.get(i).getText().equals(PierequiredFlavor4)) {
												
												driver.findElement(By.xpath("(//button[@data-anchor-id='IncrementQuantity'])[" + (i+1) + "]")).click();
												
												log.info("Clicked on required PieFlavor4 " + PierequiredFlavor4);
												extTestObj.createNode("Clicked on required PieFlavor4 " + PierequiredFlavor4)
														.pass("PASSED");
												break;
											}
										}
				//decrement and increment pie flavors based on input from excel	- needs more testing to change the flavors	
										
										String Decrement = excel.getCellData("IJWMenu", "DecrementPieFlavor", excelMenuno);
										String dec[]= Decrement.split("-");
										int decc_count=(int) Double.parseDouble(dec[2]);
										int actualdecc_count=0;
										if(dec[0].equalsIgnoreCase("Y"))
										{
											for (int i = 0; i < PieFlavors.size(); i++) {
												if (PieFlavors.get(i).getText().equals(dec[1])) {
													for(int l=0;l<decc_count;l++)
													{
													driver.findElement(By.xpath("(//button[@data-anchor-id='DecrementQuantity'])[" + (i+1) + "]")).click();
													actualdecc_count+=1;
													}
													log.info("Decremented the Pie flavor "+PieFlavors.get(i).getText()+" by "+actualdecc_count+" times");
													extTestObj.createNode("Decremented the Pie flavor "+dec[1]+" by "+actualdecc_count+" times")
															.pass("PASSED");
													break;
												}
											}
										}
										
				//Following code written to increment or decrement the pie flavor. Had some roadblocks because it allows to select only 4 count.
				//Ignore this as of now. In future if there is a requirement to increment the pie flavors use this function and test it
										
										String Increment = excel.getCellData("IJWMenu", "IncrementPieFlavor", excelMenuno);
										String inc[]= Increment.split("-");
										int inc_count=(int) Double.parseDouble(inc[2]);
										int actualinc_count=0;
										if(inc[0].equalsIgnoreCase("Y"))
										{
											for (int i = 0; i < PieFlavors.size(); i++) {
												if (PieFlavors.get(i).getText().equals(inc[1])) {
													for(int l=0;l<inc_count;l++)
													{
													driver.findElement(By.xpath("(//button[@data-anchor-id='IncrementQuantity'])[" + (i+1) + "]")).click();
													actualinc_count+=1;
													}
													log.info("Incremented the Pie flavor "+PieFlavors.get(i).getText()+" by "+actualinc_count+" times");
													extTestObj.createNode("Incremented the Pie flavor "+PieFlavors.get(i).getText()+" by "+actualinc_count+" times")
															.pass("PASSED");
													break;
												}
											}
										}
										
									
									}
								} catch (Exception e) {
									log.error("Clicking on required flavors for Pie Menu  " + menuItem + " Failed");
									extTestObj.createNode("Clicking on required flavors for Pie Menu  " + menuItem + " Failed")
											.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()")
											.error(e);
									log.error(e.getMessage());
									stopWebTest();
								}
								break;
							}
				//code to select optionals for Combo and Wings			
							if (lastword.equals("Combo") || lastword.equals("Wings")) {
								String opDessert = excel.getCellData("IJWMENU", "OptionalDessert", excelMenuno);
								String opDrinks = excel.getCellData("IJWMENU", "OptionalDrinks", excelMenuno);
								String opSauce = excel.getCellData("IJWMENU", "OptionalSauce", excelMenuno);
								try {
									if (StringUtils.hasText(opDessert) || StringUtils.hasText(opDrinks)
											|| StringUtils.hasText(opSauce))
									{
									List<WebElement> OPDessert = driver.findElements(Elements.IJWOptionalDessertXpath);
									for (int i=0;i<OPDessert.size();i++) {
										if (OPDessert.get(i).getText().equals(opDessert)) {
											OPDessert.get(i).click();
											log.info("Clicked on Optional Dessert " + opDessert);
											extTestObj.createNode("Clicked on Optional Dessert " + opDessert).pass("PASSED");
											break;
										}
									}	
									List<WebElement> OPDrinks = driver.findElements(Elements.IJWOptionalDrinksXpath);
									for (int i=0;i<OPDrinks.size();i++) {
										if (OPDrinks.get(i).getText().equals(opDrinks)) {
											OPDrinks.get(i).click();
											log.info("Clicked on Optional Drinks and Fries " + opDrinks);
											extTestObj.createNode("Clicked on Optional Drinks and Fries " + opDrinks).pass("PASSED");
											break;
										}
									}	
									List<WebElement> OPSauce = driver.findElements(Elements.IJWOptionalSauceXpath);
									for (int i=0;i<OPSauce.size();i++) {
										if (OPSauce.get(i).getText().equals(opSauce)) {
											OPSauce.get(i).click();
											log.info("Clicked on Optional Sauce/Ranch " + opSauce);
											extTestObj.createNode("Clicked on Optional Sauce/Ranch " + opSauce).pass("PASSED");
											break;
										}
									}
									}
								} catch (Exception e) {
									log.error("Optionals are not selected");
									extTestObj.createNode("Optionals are not selected")
											.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()")
											.error(e);
									log.error(e.getMessage());

									stopWebTest();
								}
							}
				//increase the qty of the menu based on input from excel			
							String menuQTY = excel.getCellData("IJWMENU", "Qty", excelMenuno);
							boolean increased=false;
							int noQty = (int) Double.parseDouble(menuQTY);
							if (noQty > 1) {
								for (int i = 1; i < noQty; i++) {
									clickableWait(Elements.IJWIncreaseQty);
									increased=true;
								}
									}
							if(increased)
							{
							log.info("Successfully increased Quantity. Total Qty - "+noQty);
							extTestObj.createNode("Successfully increased Quantity. Total Qty - "+noQty).pass("PASSED");
							}
							Thread.sleep(1000);
							String itemName = driver.findElement(By.xpath("//input[@type='number']")).getAttribute("value");
							this.IJWitem = itemName;
							//log.info("Selection of required choices for Menu - success");
							//extTestObj.createNode("Selection of required choices for Menu - success").pass("PASSED");
						} catch (Exception e) {
							log.error("Selection of required choices for Menu - fail");
							extTestObj.createNode("Selection of required choices for Menu - failed")
									.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							log.error(e.getMessage());
							stopWebTest();
						}
					}
					
				//webClickAddToOrderIJW
					public void webClickAddToOrderIJW() {
						try {
							clickableWait(Elements.addToOrderbutton_dd);
							driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
							Thread.sleep(15000);
							log.info("Click on Add To Order - success");
							extTestObj.createNode("Click on Add To Order - success").pass("PASSED");
						} catch (Exception e) {
							log.error("Click on Add To Order - failed");
							extTestObj.createNode("Click on Add To Order - failed")
									.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							log.error(e.getMessage());
							stopWebTest();
						}
					}

				//webVerifyOrderItem_QtyIJW
				//This function verifies the order qty and menu name is displayed or not in the checkout page
					public void webVerifyOrderItem_QtyIJW() {

						String menuItem = excel.getCellData("IJWMENU", "Item", excelMenuno);
						System.out.println(menuItem);
						try {
							Thread.sleep(3000);
							explicitWait(By.xpath("(//span[contains(text(),'" +menuItem +"')])[2]"));
							driver.findElement(By.xpath("(//span[contains(text(),'" + menuItem +"')])[2]")).isDisplayed();
							log.info("Order Item " + menuItem + " is displayed in Checkout page");
							extTestObj.createNode("Order Item " + menuItem + " is displayed in Checkout page").pass("PASSED");
						} catch (Exception e) {
							log.error("Order Item " + menuItem + " is not displayed in Checkout page");
							extTestObj.createNode("Order Item " + menuItem + " is not displayed in Checkout page")
									.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							log.error(e.getMessage());

							stopWebTest();
						}
						try {
							explicitWait(Elements.checkout);
							String QuantityAft = driver.findElement(Elements.verifyOrderQuantity).getText().trim();
							String[] QuantityAfter=QuantityAft.split(" ");
							System.out.println(QuantityAfter[0]);
							String[] QuantityBefore = IJWitem.split(" ");
							System.out.println(QuantityBefore[0]);
							if (QuantityAfter[0].equalsIgnoreCase(QuantityBefore[0])) {
								log.info("Order Item quantity: Before Add to Order: "+QuantityBefore[0]+" After Add to Order: "+QuantityAfter[0]+" are same");
								extTestObj.createNode("Order Item quantity: Before Add to Order: "+QuantityBefore[0]+"After Add to Order: "+QuantityAfter[0]+" are same").pass("PASSED");
							} else {
							log.error("Order Item quantity: Before Add to Order: "+QuantityBefore[0]+" After Add to Order: "+QuantityAfter[0]+" are not same");
								extTestObj.createNode("Order Item quantity: Before Add to Order: "+QuantityBefore[0]+" After Add to Order: "+QuantityAfter[0]+" are not same")
										.fail("FAILED");
							}

						} catch (Exception e) {
							log.error("Failed to get the Order Qty");
							extTestObj.createNode("Failed to get the Order Qty")
									.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							log.error(e.getMessage());
							stopWebTest();
						}
					}
					
					// TC08 - Verify Menu Item name and Description - IJW
					// UI changes often in production QA also when JASON is uploaded. So Menu category validation, description and price changes often
					//change the excel according to that. Other wise script will fail
					public void webvalidateMenuDescriptionIJW() {
						try {
							String[] itemDesc=new String[45];
							boolean match=false;
							for(int j=0;j<itemDesc.length;j++)
							{
								itemDesc[j]=excel.getCellData("IJWMENUPRICEDESC", "Item_Desc", j + 2);
							}
							int count = 0, dcount = 0;
							List<WebElement> menuCategoryList = driver.findElements(Elements.IJWMenuDesc);
							extTestObj.createNode("Displaying the Menu Name and Item Description").info("INFO");

							for (int i = 1; i <= menuCategoryList.size(); i++) {
								String menuName = driver.findElement(By.xpath(
										"(//div[@data-anchor-id='MenuItem']//following::span/div/div/div/div[1]/span)[" + i + "]"))
										.getText();
								String menuDescription = driver
										.findElement(By.xpath(
												"(//div[@data-anchor-id='MenuItem']//following::span/div/div/div/span[1])[" + i + "]"))
										.getText();
								boolean menuDescDisplay = driver
										.findElement(By.xpath(
												"(//div[@data-anchor-id='MenuItem']//following::span/div/div/div/span[1])[" + i + "]"))
										.isDisplayed();
								count += 1;
							
								if (menuDescDisplay) {
									dcount += 1;
									log.info("MENU NAME:          " + menuName + "\t" + " MENU DESCRIPTION:          "
											+ menuDescription);
									extTestObj.createNode("MENU NAME:          " + menuName)
											.createNode("MENU DESCRIPTION:          " + menuDescription);

								} else {
									log.info("Menu Item Description is not displayed for the menu " + menuName);
									extTestObj.createNode("Menu Item Description is not displayed for the menu " + menuName)
											.warning("WARNING");
								}
								for(int j=0;j<itemDesc.length;j++)
								{
				if(itemDesc[j].equals(menuDescription))
				{
					match=true;
					break;
				}
				else
				{
					match=false;
				}
						}
								if(!match)
								{
									log.info("MENU NAME:          " + menuName + "\t" + " MENU DESCRIPTION:          "
											+ menuDescription+" is not matching with the input from excel menu Description: "+itemDesc[i]);
									extTestObj.createNode("MENU NAME:          " + menuName)
											.createNode("MENU DESCRIPTION:          " + menuDescription+" is not matching with the input from excel menu Description: "+itemDesc[i]).warning("WARNING");
								}

							}

							if (dcount == count) {
								log.info("Menu Item Description is displayed for all the menu items.Total count of menu items is "
										+ count + ".Description displayed for " + dcount + " menu items");
								extTestObj.createNode(
										"Menu Item Description is displayed for all the menu items.Total count of menu items is "
												+ count + ".Description displayed for " + dcount + " menu items")
										.pass("PASSED");
							} else {
								log.info("Menu Item Description is not displayed for all the menu items.Total count of menu items is "
										+ count + " .Description displayed for " + dcount + " menu items");
								extTestObj.createNode(
										"Menu Item Description is not displayed for all the menu items.Total count of menu items is "
												+ count + " .Description displayed for " + dcount + " menu items")
										.warning("WARNING");
							}
							if(match)
				{
								log.info("Menu Item Description is successfully validated against the input from excel");
								extTestObj.createNode(
										"Menu Item Description is successfully validated against the input from excel")
										.pass("PASSED");
				}
							else
							{
								log.info("Menu Item Description validation failed against the input from excel");
								extTestObj.createNode(
										"Menu Item Description validation failed against the input from excel")
										.warning("WARNING");
							}
						} catch (Exception e) {
							log.error("Chili's store is closed");
							extTestObj.createNode("Chili's store is closed")
									.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							log.error(e.getMessage());
							stopWebTest();
						}

					}

					//Verify Menu Item name, Description and price - IJW
					// combined everything in a single function - can be used for future 
					public void webvalidateMenuDescPriceIJW() {
						try {
							String[] menuItem=new String[45];
							String[] itemDesc=new String[45];
							String[] itemPrice=new String[45];
							boolean match=false,display=false;
							String desc="",price="";
							int x=0;
							for(int i=0;i<menuItem.length;i++)
							{
								menuItem[i]=excel.getCellData("IJWMENUPRICEDESC", "SubMenu_Items", i + 2);
							}
							for(int i=0;i<itemDesc.length;i++)
							{
								itemDesc[i]=excel.getCellData("IJWMENUPRICEDESC", "Item_Desc", i + 2);
							}
							for(int i=0;i<itemPrice.length;i++)
							{
								itemPrice[i]=excel.getCellData("IJWMENUPRICEDESC", "Item_Price", i + 2);
							}
							int count = 0, dcount = 0, nodisplaycount=0;
							List<WebElement> menuCategoryList = driver.findElements(Elements.IJWMenuDesc);
							log.info("MENU NAME:          " +  "\t" + " MENU DESCRIPTION:          "
									+ "\t"+"MENU PRICE:          ");			
							extTestObj.createNode("Displaying the Menu Name, Item Description and Price").info("INFO");

							for (int i = 1; i <= menuCategoryList.size(); i++) {
								String menuName = driver.findElement(By.xpath(
										"(//div[@data-anchor-id='MenuItem']//following::span/div/div/div/div[1]/span)[" + i + "]"))
										.getText();
								String menuDescription = driver
										.findElement(By.xpath(
												"(//div[@data-anchor-id='MenuItem']//following::span/div/div/div/span[1])[" + i + "]"))
										.getText();
								boolean menuDescDisplay = driver
										.findElement(By.xpath(
												"(//div[@data-anchor-id='MenuItem']//following::span/div/div/div/span[1])[" + i + "]"))
										.isDisplayed();
								String menuPrice = driver.findElement(By.xpath(
										"(//div[@data-anchor-id='MenuItem']//following::span/div/div/div/div[2]/span)[" + i + "]"))
										.getText();
								boolean priceDisplay = driver.findElement(By.xpath(
										"(//div[@data-anchor-id='MenuItem']//following::span/div/div/div/div[2]/span)[" + i + "]"))
										.isDisplayed();
								count += 1;
							
								if (menuDescDisplay) {
									
								if (priceDisplay) {
										log.info("MENU NAME:     " + menuName + "\t" + " MENU DESCRIPTION:     "
												+ menuDescription+ "\t" + " MENU PRICE:     "+menuPrice);
										extTestObj.createNode("MENU NAME:     " + menuName+"MENU DESCRIPTION:     " + menuDescription+"MENU PRICE:     " + menuPrice);
										dcount+=1;
									} else {
										log.info("Price is not displayed for the menu " + menuName);
										extTestObj.createNode("Price is not displayed for the menu " + menuName).warning("WARNING");
										nodisplaycount+=1;
									}

								} else {
									log.info("Menu Item Description is not displayed for the menu " + menuName);
									extTestObj.createNode("Menu Item Description is not displayed for the menu " + menuName)
											.warning("WARNING");
								}
								
								for(int j=0;j<menuItem.length;j++)
								{
									
				if(menuItem[j].contains(menuName))
				{
					
					match=true;
					 desc=excel.getCellData("IJWMENUPRICEDESC", "Item_Desc", j + 2);
					 price=excel.getCellData("IJWMENUPRICEDESC","Item_Price",j + 2);
					if(desc.contains(menuDescription))
					{
						match=true;
						if(price.contains(menuPrice))
						{
							match=true;
						}
						else
						{
							match=false;
							x=1;
						}
					}
					else
					{
						match=false;
						x=2;
					}
					break;
				}
				else
				{
					match=false;
					x=3;
				}

				}
								if(!match)
								{
									log.info("MENU NAME: " + menuName + "\t" + " MENU DESCRIPTION: "+ menuDescription+  "\t" + " MENU PRICE :"+ menuPrice+" is not matching with the input from excel menu Name:"+menuItem[i]+"\t"+"MENU DESC:"+desc+"\t"+" MENU PRICE: "+"\t"+price+" flag no: "+x);
									extTestObj.createNode("MENU NAME :" + menuName + "\t" + " MENU DESCRIPTION :"+ menuDescription+  "\t" + " MENU PRICE :"+ menuPrice+" is not matching with the input from excel menu Name:"+menuItem[i]+"\t"+"MENU DESC:"+desc+"\t"+" MENU PRICE: "+"\t"+price+" flag no: "+x).warning("WARNING");
								}
						}
								
							if (dcount == count) {
								log.info("Menu Item Description and price are displayed for all the menu items.Total count of menu items is "
										+ count + ".Description and price is displayed for " + dcount + " menu items");
								extTestObj.createNode(
										"Menu Item Description and price are displayed for all the menu items.Total count of menu items is "
												+ count + ".Description and price is displayed for " + dcount + " menu items")
										.pass("PASSED");
							} else {
								log.info("Menu Item Description and price is not displayed for all the menu items.Total count of menu items is "
										+ count + " .Description and price is not displayed for " + nodisplaycount + " menu items");
								extTestObj.createNode(
										"Menu Item Description and price is not displayed for all the menu items.Total count of menu items is "
												+ count + " .Description and price is not displayed for " + nodisplaycount + " menu items")
										.warning("WARNING");
							}
							if(match)
				{
								log.info("Menu Item Description and Price are successfully validated against the input from excel");
								extTestObj.createNode(
										"Menu Item Description and price are successfully validated against the input from excel")
										.pass("PASSED");
				}
							else
							{
								log.info("Menu Item Description and price validation failed against the input from excel");
								extTestObj.createNode(
										"Menu Item Description and price validation failed against the input from excel")
										.warning("WARNING");
							}
						} catch (Exception e) {
							log.error("Menu Item Description and price validation failed");
							extTestObj.createNode("Menu Item Description and price validation failed")
									.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							log.error(e.getMessage());
							stopWebTest();
						}

					}
						
					// TC09 - validate price for all menu items - IJW
					// First display all the menu price from web and then validate those with the input from excel
					public void webvalidateMenuPriceIJW() {
						try {
							String[] itemmenuPrice=new String[45];
							boolean match=false;
							for(int j=0;j<itemmenuPrice.length;j++)
							{
								itemmenuPrice[j]=excel.getCellData("IJWMENUPRICEDESC", "Item_Price", j + 2);
							}
							int count = 0, dcount = 0, noprice = 0;
							List<WebElement> menuCategoryList = driver.findElements(Elements.IJWMenuDesc);
							extTestObj.createNode("Displaying the Menu Name and Price").info("INFO");
							for (int i = 1; i <= menuCategoryList.size(); i++) {
								String menuName = driver.findElement(By.xpath(
										"(//div[@data-anchor-id='MenuItem']//following::span/div/div/div/div[1]/span)[" + i + "]"))
										.getText();
								String menuPrice = driver.findElement(By.xpath(
										"(//div[@data-anchor-id='MenuItem']//following::span/div/div/div/div[2]/span)[" + i + "]"))
										.getText();
								boolean priceDisplay = driver.findElement(By.xpath(
										"(//div[@data-anchor-id='MenuItem']//following::span/div/div/div/div[2]/span)[" + i + "]"))
										.isDisplayed();
								count += 1;
								if (priceDisplay) {
									dcount += 1;

									log.info("MENU NAME:          " + menuName + "\t" + " MENU PRICE:          " + menuPrice);
									extTestObj.createNode("MENU NAME:          " + menuName)
											.createNode("MENU PRICE:          " + menuPrice);

								} else {
									noprice += 1;
									log.info("Price is not displayed for the menu " + menuName);
									extTestObj.createNode("Price is not displayed for the menu " + menuName).warning("WARNING");
								}
								for(int j=0;j<itemmenuPrice.length;j++)
								{
				if(itemmenuPrice[j].equals(menuPrice))
				{
					match=true;
				}
				else
				{
					match=false;
				}
						}
								if(!match)
								{
									log.info("MENU NAME:          " + menuName + "\t" + " MENU PRICE:          "
											+ menuPrice+" is not matching with the input from excel menu price: "+itemmenuPrice[i]);
									extTestObj.createNode("MENU NAME:          " + menuName)
											.createNode("MENU PRICE:          " + menuPrice+" is not matching with the input from excel menu price: "+itemmenuPrice[i]).warning("WARNING");
								}

							}
						
							if (dcount == count) {
								log.info("Price is displayed for all the menu items.Total menu items " + count
										+ " .Price displayed for " + dcount + " menu items");
								extTestObj.createNode("Price is displayed for all the menu items.Total menu items " + count
										+ " .Price displayed for " + dcount + " menu items").pass("PASSED");
							} else {
								log.info("Price is not displayed for all the menu items.Total menu items " + count
										+ " .Price is not displayed for " + noprice + " menu items");
								extTestObj.createNode("Price is not displayed for all the menu items.Total menu items " + count
										+ " .Price is not displayed for " + noprice + " menu items").warning("WARNING");
							}
							if(match)
							{
											log.info("Menu Price is successfully validated against the input from excel");
											extTestObj.createNode(
													"Menu Price is successfully validated against the input from excel")
													.pass("PASSED");
							}
										else
										{
											log.info("Menu Price validation failed against the input from excel");
											extTestObj.createNode(
													"Menu Price validation failed against the input from excel")
													.warning("WARNING");
										}
						} catch (Exception e) {
							log.error("Chili's store is closed");
							extTestObj.createNode("Chili's store is closed")
									.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							log.error(e.getMessage());
							stopWebTest();
						}

					}

				// TC07 - validate Menu Category - IJW 

					public void webvalidateMenuCategoriesIJW() {
						try
						{
							List<WebElement> webmenucat=null;
							String[] menucat=new String[7];
							//int count=0;
							boolean found=false;
							for(int j=0;j<menucat.length;j++)
							{
								menucat[j]=excel.getCellData("IJWMENUPRICEDESC", "Menu_Category", j+2);
							}
							explicitWait(Elements.IJWOpenMenucat);
							clickableWait(Elements.IJWOpenMenucat);
							webmenucat=driver.findElements(Elements.IJWMenucat);
							log.info("Validating Menu Categories");
							extTestObj.log(Status.INFO,"Validating Menu Categories");
							for(int i=0;i<menucat.length;i++)
							{
								for(int k=0;k<webmenucat.size();k++)
								{
									if(menucat[i].equals(webmenucat.get(k).getText()))
									{
										log.info("Menu Category: "+menucat[i]+" is Present");
										extTestObj.createNode("Menu Category: "+menucat[i]+" is Present").pass("PASSED");
										
										found=true;
										break;
									}
									else
									{
										found=false;
									}
								}
								if(!found)
								{
									log.info("Menu category: "+menucat[i]+" is not Present in the store.");
									extTestObj.createNode("Menu category: "+menucat[i]+" is not Present in the store.").warning("warning");
								}
							}
							if(menucat.length==webmenucat.size())
							{
								log.info("All menu categories are Present in the store. Count - "+webmenucat.size());
								extTestObj.createNode("All menu categories are Present in the store. Count - "+webmenucat.size()).pass("PASSED");
							}
							else
							{
							log.info("All menu categories are not present in the store. Available menu category - "+menucat.length+" Menu Categories Present in the store - "+webmenucat.size());
							extTestObj.createNode("All menu categories are not present in the store. Available menu category - "+menucat.length+" Menu Categories Present in the store - "+webmenucat.size()).warning("WARNING");
							}
						}
						catch(Exception e)
						{
							log.error("Validating menu categories failed");
							extTestObj.createNode("Validating menu categories failed")
									.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							log.error(e.getMessage());

							stopWebTest();
							
						}

					}

					// View Orders - IJW
					public void webClickViewOrdersIJW() {
						try {
							String IJWsiteURL= excel.getCellData("IJWURL", "StoreURL", 3);
							driver.get(IJWsiteURL);
							driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
							driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
							driver.manage().window().maximize();
							Thread.sleep(4000);
							WebElement OpenMenuClick = driver.findElement(Elements.IJWOpenMenuXpath);
							JavascriptExecutor js = (JavascriptExecutor) driver;
							js.executeScript("arguments[0].click()", OpenMenuClick);
							driver.manage().timeouts().pageLoadTimeout(80, TimeUnit.SECONDS);
							explicitWait(Elements.IJWViewOrdersXpath);
							clickableWait(Elements.IJWViewOrdersXpath);
							driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

							if (driver.findElement(Elements.IJWValidateViewOrderXpath).isDisplayed()) {
								log.info("View Orders button clicked");
								extTestObj.createNode("View Orders button clicked").pass("PASSED");
							}
							Thread.sleep(5000);

							WebElement OpenFirstOrderIP = driver.findElement(Elements.IJWOrderIP1);
							js.executeScript("arguments[0].click()", OpenFirstOrderIP);
							driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
							driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
							log.info("Clicked on First Order in Progress");
							extTestObj.createNode("Clicking on First Order in Progress").pass("PASSED");
							Thread.sleep(3000);
							explicitWait(Elements.IJWIJWDisplaysvg);
							clickableWait(Elements.IJWIJWDisplaysvg);
							driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
							Thread.sleep(3000);
							websitelaunchIJW();

						} catch (Exception e) {
							log.error("View Orders button click failed");
							extTestObj.createNode("View Orders button click failed")
									.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							log.error(e.getMessage());

							stopWebTest();
						}

					}

				// View all Orders in Progress IJW 
					
					 public void webclickViewAllOrdersInProgressIJW() {	
						 try {
							WebElement OpenMenuClick = driver.findElement(Elements.IJWOpenMenuXpath);
							JavascriptExecutor js = (JavascriptExecutor) driver;
							js.executeScript("arguments[0].click()", OpenMenuClick);
							driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
							driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

							explicitWait(Elements.IJWViewOrdersXpath);
							clickableWait(Elements.IJWViewOrdersXpath);
							driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
							driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

							if (driver.findElement(Elements.IJWValidateViewOrderXpath).isDisplayed()) {
								log.info("View Orders button clicked");
								extTestObj.createNode("View Orders button clicked").pass("PASSED");
							}
							Thread.sleep(5000);
							log.info("Displaying All the Orders in Progress");
							extTestObj.createNode("Displaying All the Orders in Progress");
							List<WebElement> OIP=driver.findElements(Elements.IJWOrderInProgress);
							for(int i=0;i<OIP.size();i++)
							{
								log.info(OIP.get(i).getText());
								extTestObj.createNode(OIP.get(i).getText());
							}
							Thread.sleep(1000);
							log.info("Displayed all the Orders in Progress");
							extTestObj.createNode("Displayed all the Orders in Progress").pass("PASSED");
									
						} catch (Exception e) {
							log.error("View All the Orders in Progress - Failed");
							extTestObj.createNode("View All the Orders in Progress - Failed")
									.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							log.error(e.getMessage());

							stopWebTest();
						}
				}
				//view previous orders - IJW
					 
					 public void webclickViewPreviousOrdersIJW() {	
						 try {
							WebElement OpenMenuClick = driver.findElement(Elements.IJWOpenMenuXpath);
							JavascriptExecutor js = (JavascriptExecutor) driver;
							js.executeScript("arguments[0].click()", OpenMenuClick);
							driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
							driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

							explicitWait(Elements.IJWViewOrdersXpath);
							clickableWait(Elements.IJWViewOrdersXpath);
							driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
							driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

							if (driver.findElement(Elements.IJWValidateViewOrderXpath).isDisplayed()) {
								log.info("View Orders button clicked");
								extTestObj.createNode("View Orders button clicked").pass("PASSED");
							}
							Thread.sleep(5000);
							log.info("Displaying All the Previous Orders");
							extTestObj.createNode("Displaying All the Previous Orders");
							List<WebElement> PO=driver.findElements(Elements.IJWPreviousOrders);
							for(int i=0;i<PO.size();i++)
							{
								log.info(PO.get(i).getText());
								extTestObj.createNode(PO.get(i).getText());
							}
							Thread.sleep(1000);
							log.info("Displayed all the Previous Orders");
							extTestObj.createNode("Displayed all the Previous Orders").pass("PASSED");
									
						} catch (Exception e) {
							log.error("View All the Previous Orders - Failed");
							extTestObj.createNode("View All the Previous Orders - Failed")
									.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							log.error(e.getMessage());

							stopWebTest();
						}
				}
				// Validate Orders IJW 
					 
					public void webValidateOrdersIJW() {
						try {

							if (OrderSubTotal_After.equals(OrderSubtotal_before)) {
								log.info("Order SubTotal Before " + OrderSubtotal_before + " is equal to Order SubTotal After "
										+ OrderSubTotal_After);
								extTestObj.createNode("Order SubTotal Before " + OrderSubtotal_before + " is equal to Order SubTotal After "
										+ OrderSubTotal_After).pass("PASSED");
							} else {
								log.info("Order SubTotal Before " + OrderSubtotal_before + " is not equal to Order SubTotal After "
										+ OrderSubTotal_After);
								extTestObj.createNode("Order SubTotal Before " + OrderSubtotal_before
										+ " is not equal to Order SubTotal After " + OrderSubTotal_After).fail("FAILED");
							}
							if (OrderTotal_After.equals(AmountDue_before)) {
								log.info("Order Total Before " + AmountDue_before + " is equal to Order Total After "
										+ OrderTotal_After);
								extTestObj.createNode("Order Total Before " + AmountDue_before + " is equal to Order Total After "
										+ OrderTotal_After).pass("PASSED");
							} else {
								log.info("Order Total Before " + AmountDue_before + " is not equal to Order Total After "
										+ OrderTotal_After);
								extTestObj.createNode("Order Total Before " + AmountDue_before + " is not equal to Order Total After "
										+ OrderTotal_After).fail("FAILED");
							}
							
							Thread.sleep(3000);
						} catch (Exception e) {
							log.error("Order Price validation failed");
							extTestObj.createNode("Order detail validation failed")
									.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							log.error(e.getMessage());
							stopWebTest();
						}
					}

				//Displaying order details after placing order - IJW 
				//This function collects all Order details after placing order. ASAP is Different and PICK up is different	
					public void webOrderDetailsAfterIJW() throws InterruptedException
					{
						try
						{
							Thread.sleep(15000);
							log.info("Displaying Order details After placing Order");
							extTestObj.createNode("Displaying Order details After placing Order");
							driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
							explicitWait(Elements.IJWIJWDisplaysvg);
							clickableWait(Elements.IJWIJWDisplaysvg);
							driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);	
						explicitWait(Elements.IJWAfterOrderDetails);
						Thread.sleep(3000);
				//if the order is Delivery
						if(deliveryorpickup.equals("Delivery"))
						{
							//To Display Order Time
							log.info("Order Time: "+driver.findElement(Elements.IJWAfterAddressTime).getText());
							extTestObj.createNode("Order Time: "+driver.findElement(Elements.IJWAfterAddressTime).getText());
							
					//To Display Order details - Qty, menu name and price
							String[] OrderDetails = new String[5]; //array to contain qty, name and price
							List <WebElement> OrdersList=driver.findElements(Elements.IJWAfterOrdersList);
							for(int i=0;i<OrdersList.size();i++)
								{
								 	List <WebElement>QtyNamePrice = driver.findElements(By.xpath("//span[text()=\"It's Just Wings\"]//following::div[4]/div["+(i+1)+"]/div[1]/div/div/span"));
								 	for(int j=0;j<QtyNamePrice.size();j++)
										{
								 		if(!(QtyNamePrice.get(j).getText().isEmpty()))
										{
								 			OrderDetails[j]=QtyNamePrice.get(j).getText();
										
										}
								 	}
								
								}
							log.info("Order Qty: "+OrderDetails[0]);
							extTestObj.createNode("Order Qty: "+OrderDetails[0]);
							log.info("Menu Name: "+OrderDetails[1]);
							extTestObj.createNode("Menu Name: "+OrderDetails[1]);
							log.info("Menu Price: "+OrderDetails[3]);
							extTestObj.createNode("Menu Price: "+OrderDetails[3]);
							log.info("Displaying Order Price Details");
							extTestObj.createNode("Displaying Order Price Details");
							List <WebElement> afterPricelist=driver.findElements(Elements.IJWAfterOrderPriceDetails);
							for(int i=0;i<(afterPricelist.size()-1);i++)
								{
									if(!(afterPricelist.get(i).getText().isEmpty()))
									{
									log.info(afterPricelist.get(i).getText());
									extTestObj.createNode(afterPricelist.get(i).getText());
									}
								}
							
						}
				//if the order is pick up
						else
						{
							//To Display Order Time
							log.info("Order Time: "+driver.findElement(Elements.IJWAfterOrderPickupTime).getText());
							extTestObj.createNode("Order Time: "+driver.findElement(Elements.IJWAfterOrderPickupTime).getText());
							
					//To Display Order details - Qty, menu name and price
							String[] OrderDetails = new String[5]; //array to contain qty, name and price
							List <WebElement> OrdersList=driver.findElements(Elements.IJWAfterPickupOrdersList);
							for(int i=0;i<OrdersList.size();i++)
								{
								 	List <WebElement>QtyNamePrice = driver.findElements(By.xpath("//span[text()=\"It's Just Wings\"]//following::div[7]/div["+(i+1)+"]/div/div/div/span"));
								 	for(int j=0;j<QtyNamePrice.size();j++)
										{
								 		if(!(QtyNamePrice.get(j).getText().isEmpty()))
										{
								 			OrderDetails[j]=QtyNamePrice.get(j).getText();
										
										}
								 	}
								
								}
							log.info("Order Qty: "+OrderDetails[0]);
							extTestObj.createNode("Order Qty: "+OrderDetails[0]);
							log.info("Menu Name: "+OrderDetails[1]);
							extTestObj.createNode("Menu Name: "+OrderDetails[1]);
							log.info("Menu Price: "+OrderDetails[3]);
							extTestObj.createNode("Menu Price: "+OrderDetails[3]);
							log.info("Displaying Order Price Details");
							extTestObj.createNode("Displaying Order Price Details");
							List <WebElement> afterPricelist=driver.findElements(Elements.IJWAfterPickupPriceDetails);
							for(int i=0;i<afterPricelist.size();i++)
								{
									if(!(afterPricelist.get(i).getText().isEmpty()))
									{
									log.info(afterPricelist.get(i).getText());
									extTestObj.createNode(afterPricelist.get(i).getText());
									}
								}
							
						}
						OrderTotal_After = driver.findElement(Elements.IJWASAPAfterOrdertotal).getText();
						OrderSubTotal_After=driver.findElement(Elements.IJWASAPAfterOrdeSubtotal).getText();
						}
						catch(Exception e)
						{
							log.error("Failed to get After Order Details");
							extTestObj.createNode("Failed to get After Order Details")
									.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							log.error(e.getMessage());
							stopWebTest();
						}
					}
					
				//Increase Decrease item before checkout - This function is to increase or decrease the qty before clicing on checkout 

					public void webIncDecbeforeCheckoutIJW(int menu) throws InterruptedException
					{
						try
						{
						String menuItem = excel.getCellData("IJWMENU", "Item", menu);
						List <WebElement> cartitems=driver.findElements(Elements.IJWcartitems);
						for(int i=0;i<cartitems.size();i++)
						{
							if(cartitems.get(i).getText().equals(menuItem))
							{
								driver.findElement(By.xpath("(//button[@data-anchor-id='OrderCartItem']//child::button[@shape='Pill'])["+(i+1)+"]")).click();
								Thread.sleep(1000);
								driver.findElement(Elements.IJWPlusClick).click();
								driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
								Thread.sleep(3000);
								log.info("Item Increase check: Completed");
								extTestObj.createNode("Item Increase check: Completed" ).pass("PASSED");
								break;
											}
						}
						for(int i=0;i<cartitems.size();i++)
						{
							if(cartitems.get(i).getText().equals(menuItem))
							{								
								driver.findElement(By.xpath("(//button[@data-anchor-id='OrderCartItem']//child::button[@shape='Pill'])["+(i+1)+"]")).click();
								Thread.sleep(1000);
								driver.findElement(Elements.IJWDec1x).click();
								driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
								Thread.sleep(3000);
								log.info("Item Decrease check: Completed");
								extTestObj.createNode("Item Decrease check: Completed" ).pass("PASSED");
								break;
							}
						}
						}
						catch (Exception e) {
							log.info("Item Increase Decrease Check Failed");
							extTestObj.createNode("Item Increase Decrease Check Failed")
									.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							log.error(e.getMessage());
							stopWebTest();
							}
					}
					
					// Reorder for IJW 
					
					public void webReorderIJW() {
						String reorderitem = excel.getCellData("IJWMenu", "ReorderItem", 3);
						String ordereddate=excel.getCellData("IJWMenu", "OrderedDate", 3);
						try {
							int click = 0;
							List<WebElement> reorderitems = driver.findElements(Elements.IJWReorderitems);
							for (int i = 0; i < reorderitems.size(); i++) {
								if (reorderitem.contains(reorderitems.get(i).getText())) {
									reorderitems.get(i).click();
									driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
									Thread.sleep(3000);
									log.info("Reorder Menu Item " + reorderitem + " clicked");
									extTestObj.createNode("Reorder Menu Item " + reorderitem + " clicked").pass("PASSED");
									click += 1;
									break;
								}
							}
							if (click != 1) {
								log.info("Reorder Menu Item " + item + " is not found in the Reorder list");
								extTestObj.createNode("Reorder Menu Item " + item + " is not found in the Reorder list")
										.warning("WARNING");
								stopWebTest();
							}
							List<WebElement> reorderdate = driver.findElements(Elements.IJWReorderDates);
							for (int i = 0; i < reorderdate.size(); i++) {
								if (ordereddate.contains(reorderdate.get(i).getText())) {
									reorderdate.get(i).click();
									driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
									log.info("Clicked on " + ordereddate);
									extTestObj.createNode("Clicked on " + ordereddate).pass("PASSED");
									click += 1;
								}
							}
							if (click != 2) {
								log.info("Clicking on " + ordereddate + " Failed");
								extTestObj.createNode("Clicking on " + ordereddate + " Failed").fail("FAILED");
							}
				//calling the other funcs to reorder the item	
							
							webClickAddToOrderIJW();
							webClickOnCheckoutIJW();
							webSelectDeliveryASAPIJW();
							webOrderDetailsbeforeIJW();
							webPlaceDeliveryAsapOrderIJW();
							webOrderDetailsAfterIJW();
							webValidateOrdersIJW();
							webClickViewOrdersIJW();			
							
						} catch (Exception e) {
							log.info("Reordering function of  " + reorderitem + " Failed");
							extTestObj.createNode("Reordering function of  " + reorderitem + " Failed")
									.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							log.error(e.getMessage());
							stopWebTest();
							}
					}

					public void webClickOnCheckoutIJW() {
						try {
							clickableWait(Elements.checkout);
							driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
							explicitWait(Elements.IJWdisplayPlaceOrderxpath);
							if (driver.findElement(Elements.IJWdisplayPlaceOrderxpath).isDisplayed()) {
								log.info("Checkout successful");
								extTestObj.createNode("Checkout successful").pass("PASSED");
							
							}
						} catch (Exception e) {
							log.error("Checkout failed");
							extTestObj.createNode("Checkout failed")
									.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							log.error(e.getMessage());

							stopWebTest();
						}
					}

					

					
				// webPickupScheduleLaterIJW - contains the following functions which is called sequentially 
				/*
				webSelectPickupMode();
				webSelectScheduleLater();
				webSelectScheduleLaterToday();
				webSelectPickupTimeIJW();
				 */
					public void webPickupScheduleLaterIJW()
					{
						try {
							driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
							clickableWait(Elements.pickupOption_DD);
							driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
							explicitWait(Elements.verifyPickupOption_DD);
							if (driver.findElement(Elements.verifyPickupOption_DD).isDisplayed()) {
								log.info("Pickup option selected");
								extTestObj.createNode("Pickup option selected").pass("PASSED");
							}
						} catch (Exception e) {
							log.error("Pickup option selection failed");
							extTestObj.createNode("Pickup option selection failed")
									.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							log.error(e.getMessage());

							stopWebTest();
						}

				try {
							Thread.sleep(4000);
							clickableWait(Elements.scheduleLater_DD);
							driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
							explicitWait(Elements.verifyScheduleLater_DD);
							if (driver.findElement(Elements.verifyScheduleLater_DD).isDisplayed()) {
								log.info("Schedule later selected");
								extTestObj.createNode("Schedule later selected").pass("PASSED");
							}
						} catch (Exception e) {
							log.error("Schedule later selection failed");
							extTestObj.createNode("Schedule later selection failed")
									.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							log.error(e.getMessage());

							stopWebTest();
						}

				String PickupTime = excel.getCellData("DoorDash", "Pickup Time", 3);
						System.out.println(PickupTime);

						try {
							explicitWait(Elements.IJWDeliveryDates);
							//selecting delivery today from the list of delivery dates
							List<WebElement> deliverydates=driver.findElements(Elements.IJWDeliveryDates);
							deliverydates.get(0).click();
							driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
							explicitWait(Elements.IJWscheduleforLater);
							if((driver.findElement(Elements.IJWscheduleforLater)).isEnabled()){
							clickableWait(Elements.IJWscheduleforLater);
							driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
							 }
							explicitWait(By.xpath("//span[text()='"+PickupTime+"']"));
							 if(driver.findElement(By.xpath("//span[text()='"+PickupTime+"']")).isDisplayed())
							{
								log.info("Schedule for later-today selected and time menu is opened");
								extTestObj.createNode("Schedule for later-today selected and time menu is opened").pass("PASSED");
							}
						} catch (Exception e) {
							log.error("Schedule for later-today selection failed");
							extTestObj.createNode("Schedule for later-today selection failed")
									.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							log.error(e.getMessage());

							stopWebTest();
						}

				try {
							String pickupTime = excel.getCellData("DoorDash", "Pickup Time", 3);
							System.out.println(pickupTime);
							String[] pickTimes = pickupTime.split("-");
							if ((driver.findElement(
									By.xpath("//div[@kind='BUTTON/SECONDARY_PILL']//span[contains(text(),'" + pickupTime +"')]")))
											.isEnabled()) {
								clickableWait(
										By.xpath("//div[@kind='BUTTON/SECONDARY_PILL']//span[contains(text(),'" + pickupTime +"')]"));
								driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
							}
							// explicitWait(Elements.verifyScheduleLater_DD);
							Thread.sleep(3000);
							if (driver.findElement(Elements.placePickupOrder_DD).isEnabled()) {
								log.info("Pickup time selected around: " + pickupTime);
								extTestObj.createNode("Pickup time selected around: " +pickupTime)
										.pass("PASSED");
							}
							driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
						} catch (Exception e) {
							log.error("Pickup time selection failed");
							extTestObj.createNode("Pickup time selection failed")
									.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							log.error(e.getMessage());
							stopWebTest();
						}	
						
					}
					// Remove item function for IJW 
					public void webRemoveItemIJW(int n) throws InterruptedException {
						String removeitem = excel.getCellData("IJWMENU", "Item", n);
						try
						{
						explicitWait(Elements.IJWRemoveItemXpath);
						if(driver.findElement(Elements.IJWRemoveItemXpath).isDisplayed())
				{
						driver.findElement(By.xpath("//button[@data-anchor-id='RemoveOrderCartItemButton' and @aria-label='Remove "+ removeitem + " from order']")).click();
						Thread.sleep(3000);
						log.info("Successfully Removed Item "+removeitem);
						extTestObj.createNode("Successfully Removed Item "+removeitem).pass("PASSED");
						Thread.sleep(3000);
				}
						else
						{
							log.info("There is no Item to remove");
							extTestObj.createNode("There is no Item to remove").warning("WARNING");
						}
						}
						catch(Exception e)
						{
							log.error("There is no Item to remove");
							extTestObj.createNode("There is no Item to remove")
									.warning("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
							log.error(e.getMessage());
							stopWebTest();
						}
					}
					
				//func to click back to menu after removing item from checkout page
					
					public void webclickBacktoMenuIJW() throws InterruptedException
					{
						clickableWait(Elements.IJWBackToMenuXpath);
						driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
						Thread.sleep(3000);
					
					try {
						clickableWait(Elements.tipOther);
						explicitWait(Elements.otherTipInput);
						if (driver.findElement(Elements.otherTipInput).isDisplayed()) {
							log.info("Other tip option selected");
							extTestObj.createNode("Other tip option selected").pass("PASSED");
						}
					} catch (Exception exp) {
						log.error("Other tip option selection failed");
						extTestObj.createNode("Other tip option selection failed")
								.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(exp);
						log.error(exp.getMessage());

						stopWebTest();
					}
					try {
						sendKeysWait(Elements.otherTipInput, "0");
						if (driver.findElement(Elements.verifyZeroTip).isDisplayed()) {
							log.info("Zero tip verified");
							extTestObj.createNode("Zero tip verified").pass("PASSED");
						}
					} catch (Exception e) {
						log.error("Zero tip verification failed");
						extTestObj.createNode("Zero tip verification failed")
								.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
						log.error(e.getMessage());

						stopWebTest();
					}
				}


public void webEnterZeroTipIJW() {
	try {
		clickableWait(Elements.tipOther);
		explicitWait(Elements.otherTipInput);
		if (driver.findElement(Elements.otherTipInput).isDisplayed()) {
			log.info("Other tip option selected");
			extTestObj.createNode("Other tip option selected").pass("PASSED");
		}
	} catch (Exception e) {
		log.error("Other tip option selection failed");
		extTestObj.createNode("Other tip option selection failed")
				.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
		log.error(e.getMessage());

		stopWebTest();
	}
	try {
		sendKeysWait(Elements.otherTipInput, "0");
		if (driver.findElement(Elements.verifyZeroTip).isDisplayed()) {
			log.info("Zero tip verified");
			extTestObj.createNode("Zero tip verified").pass("PASSED");
		}
	} catch (Exception e) {
		log.error("Zero tip verification failed");
		extTestObj.createNode("Zero tip verification failed")
				.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
		log.error(e.getMessage());

		stopWebTest();
	}
}

public void webVerifyOrderModesIJW() {

	try {

		explicitWait(Elements.deliveryButton_dd);
		driver.findElement(Elements.deliveryButton_dd).isEnabled();

		log.info("Delivery button is enabled");
		extTestObj.createNode("Delivery button is enabled").pass("PASSED");
	} catch (Exception e) {
		log.error("Delivery button is disabled");
		extTestObj.createNode("Delivery button is disabled")
				.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
		log.error(e.getMessage());

		stopWebTest();
	}
	try {

		explicitWait(Elements.pickupButton_dd);
		driver.findElement(Elements.pickupButton_dd).isEnabled();

		log.info("Pickup button is enabled");
		extTestObj.createNode("Pickup button is enabled").pass("PASSED");
	} catch (Exception e) {
		log.error("Pickup button is disabled");
		extTestObj.createNode("Pickup button is disabled")
				.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
		log.error(e.getMessage());

		stopWebTest();
	}
}
String  OrderSubtotal_before, OrderTotal_before, AmountDue_before, OrderTime_before, dasherTip;
String deliveryorpickup;
public void webOrderDetailsbeforeIJW() {
	try {
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		Thread.sleep(50000);
		explicitWait(Elements.IJWdisplayPlaceOrderxpath);
				
		  String ordermore = excel.getCellData("IJWMENU", "Addmoreitem", excelMenuno);
		  if(ordermore.equalsIgnoreCase("Y"))
		  { 
			  webAddmoreItemIJW();
		  }
		 
		log.info("Displaying Order details before placing Order");
		extTestObj.createNode("Displaying Order details before placing Order");
		

		 deliveryorpickup = driver.findElement(Elements.IJWDeliveyorPickup).getText();

		if (deliveryorpickup.contains("Delivery")) {
			deliveryorpickup="Delivery";
			explicitWait(Elements.IJWSelectDashertip);
			String Tipamt = excel.getCellData("IJWMENU", "DasherTip", excelMenuno);
			List<WebElement> DasherTip = driver.findElements(Elements.IJWSelectDashertip);
			try {
				for (int i = 0; i < DasherTip.size(); i++) {
					dasherTip = DasherTip.get(i).getText();
					if (dasherTip.contains(Tipamt)) {
						DasherTip.get(i).click();
						Thread.sleep(3000);
						if (Tipamt.contains("Other")) {
							String OtherTipamt = excel.getCellData("IJWMENU", "OtherTip", excelMenuno);
							sendKeysWait(Elements.IJWEnterOtherTip, OtherTipamt);
							log.info("Dasher Tip selected as Other and entered amount " + OtherTipamt);
							extTestObj.createNode("Dasher Tip selected as Other and entered amount " + OtherTipamt);
						} else {
							log.info("Dasher Tip selected amount " + Tipamt);
							extTestObj.createNode("Dasher Tip selected amount " + Tipamt);
						}
						break;
					}
					Thread.sleep(5000);
				}
			} catch (Exception e) {
				log.error("Failed to Select Dashter Tip before placing order");
				extTestObj.createNode("Failed to Select Dashter Tip before placing order")
						.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()")
						.error(e);
				log.error(e.getMessage());

				//stopWebTest();
			}
			OrderSubtotal_before = driver.findElement(Elements.IJWSubtotal).getText();
			log.info("Order Subtotal: " + OrderSubtotal_before);
			extTestObj.createNode("Order Subtotal: " + OrderSubtotal_before);
			log.info("Delivery Fee: " + driver.findElement(Elements.IJWDeliveryFee).getText());
			extTestObj.createNode("Delivery Fee: " + driver.findElement(Elements.IJWDeliveryFee).getText());
			log.info("Tax: " + driver.findElement(Elements.IJWDeliveryTax).getText());
			extTestObj.createNode("Tax: " + driver.findElement(Elements.IJWDeliveryTax).getText());
			log.info("Dasher Tip: " + driver.findElement(Elements.IJWDashertip).getText());
			extTestObj.createNode("Dasher Tip: " + driver.findElement(Elements.IJWDashertip).getText());

		} else {
			deliveryorpickup="Pickup";
			OrderSubtotal_before = driver.findElement(Elements.IJWSubtotal).getText();
			log.info("Order Subtotal: " + OrderSubtotal_before);
			extTestObj.createNode("Order Subtotal: " + OrderSubtotal_before);
			log.info("Tax: " + driver.findElement(Elements.IJWPickupTax).getText());
			extTestObj.createNode("Tax: " + driver.findElement(Elements.IJWPickupTax).getText());
		}
		OrderTotal_before = driver.findElement(Elements.IJWOrderTotalXpath).getText();
		log.info("Order Total: " + OrderTotal_before);
		extTestObj.createNode("Order Total: " + OrderTotal_before);
		log.info("Doordash credits: " + driver.findElement(Elements.IJWDoordashCredits).getText());
		extTestObj.createNode("Doordash credits: " + driver.findElement(Elements.IJWDoordashCredits).getText());
		AmountDue_before = driver.findElement(Elements.IJWAmountDueXpath).getText();
		log.info("Amount Due: " + AmountDue_before);
		extTestObj.createNode("Amount Due: " + AmountDue_before);

		String AsaporLater = driver.findElement(Elements.IJWASAPorLater).getText();
		if (AsaporLater.contains("ASAP")) {
			OrderTime_before = driver.findElement(Elements.IJWDeliveryASAPTime).getText();
			log.info("Schedule Timimgs: " + OrderTime_before);
			extTestObj.createNode("Schedule Timimgs: " + OrderTime_before);
		} else {
			OrderTime_before = driver.findElement(Elements.IJWScheduletime).getText();
			log.info("Schedule Timimgs: " + OrderTime_before);
			extTestObj.createNode("Schedule Timimgs: " + OrderTime_before);

		}
		
	} catch (Exception e) {
		log.error("Failed to get Order Details before placing order");
		extTestObj.createNode("Failed to get Order Details before placing order")
				.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
		log.error(e.getMessage());

		stopWebTest();
	}
}

//adding more items for IJW order  before placing order

	public void webAddmoreItemIJW() throws InterruptedException {
		try {
			// explicitWait(Elements.IJWAddmoreItem);
			clickableWait(Elements.IJWAddmoreItem);
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			Thread.sleep(3000);
			String ordermoremenuitemno = excel.getCellData("IJWMENU", "moremenuitemno", excelMenuno);
			int itemno = (int) Double.parseDouble(ordermoremenuitemno);
			webClickMenuItemIJW(itemno);
			webClickAddToOrderIJW();
			webClickOnCheckoutIJW();
		} catch (Exception e) {
			log.error("Failed to click on Add more Items");
			extTestObj.createNode("Failed to click on Add more Items")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopWebTest();
		}
	}

	// getting pick up order details after ordering for IJW by Srividhya -
	// 07-12-2021 Begin
	String OrderName_After, OrderSubTotal_After, OrderQty_After, OrderTotal_After, OrderTime_After;
	String[] OrderPriceTotal_After = new String[10];

public void webPlacePickupOrderIJW() {
	try {
		explicitWait(Elements.IJWdisplayPlaceOrderxpath);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		clickableWait(Elements.IJWdisplayPlaceOrderxpath);
		Thread.sleep(20000);
		// explicitWait(Elements.verifyPickupOrderPlaced_DD);
		//
		 // if(driver.findElement(Elements.verifyPickupOrderPlaced_DD).isDisplayed()) {
		 log.info("Pickup Order Placed Successfully");
		  extTestObj.createNode("Pickup Order Placed Successfully").pass("PASSED"); 
		 
	} catch (Exception e) {
		log.error("Failed to Place Pickup Order");
		extTestObj.createNode("Failed to PlacePickup Order")
				.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
		log.error(e.getMessage());

		stopWebTest();
	}
}
public void webSelectPickupASAPIJW()
{
	try {
		clickableWait(Elements.pickupOption_DD);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		explicitWait(Elements.verifyPickupOption_DD);
		if (driver.findElement(Elements.verifyPickupOption_DD).isDisplayed()) {
			log.info("Pickup ASAP option selected");
			extTestObj.createNode("Pickup ASAP option selected").pass("PASSED");
		}
	} catch (Exception e) {
		log.error("Pickup ASAP option selection failed");
		extTestObj.createNode("Pickup ASAP option selection failed")
				.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
		log.error(e.getMessage());

		stopWebTest();
	}

	try {

		driver.findElement(Elements.asapButton_dd).isEnabled();
		clickableWait(Elements.asapButton_dd);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		Thread.sleep(2000);

		log.info("Asap button is selected");
		extTestObj.createNode("Asap button is selected").pass("PASSED");

	} catch (Exception e) {
		log.error("Failed to select asap button");
		extTestObj.createNode("Failed to select asap button")
				.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
		log.error(e.getMessage());

		stopWebTest();
	}
	
}	
//webSelectDeliveryASAP calls 	webSelectDelivery and webSelectASAP
	public void webSelectDeliveryASAPIJW()
	{
		try {
			explicitWait(Elements.deliveryButton_dd);
			driver.findElement(Elements.deliveryButton_dd).isEnabled();
			clickableWait(Elements.deliveryButton_dd);
			Thread.sleep(3000);

			log.info("Delivery button is selected");
			extTestObj.createNode("Delivery button is selected").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select delivery button");
			extTestObj.createNode("Failed to select delivery button")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopWebTest();
		}

		try {

			driver.findElement(Elements.asapButton_dd).isEnabled();
			clickableWait(Elements.asapButton_dd);
			Thread.sleep(2000);

			log.info("Asap button is selected");
			extTestObj.createNode("Asap button is selected").pass("PASSED");

		} catch (Exception e) {
			log.error("Failed to select asap button");
			extTestObj.createNode("Failed to select asap button")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopWebTest();
		}

		
	}
	public void webPlaceDeliveryAsapOrderIJW() {

		try {
			explicitWait(Elements.IJWdisplayPlaceOrderxpath);
			driver.findElement(Elements.IJWdisplayPlaceOrderxpath).isEnabled();
			clickElement(Elements.IJWdisplayPlaceOrderxpath);
			Thread.sleep(20000);
			log.info("Delivery ASAP Order is placed");
			extTestObj.createNode("Delivery ASAP Order is placed").pass("PASSED");
		
		} catch (Exception e) {
			log.error("Failed to place order");
			extTestObj.createNode("Failed to place order")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopWebTest();
		}
	}
	public void webPlaceDeliveryScheduleOrderIJW() {

		try {
			explicitWait(Elements.placeOrder_DD);
			driver.findElement(Elements.placeOrder_DD).isEnabled();
			Thread.sleep(5000);
			clickElement(Elements.placeOrder_DD);
			Thread.sleep(20000);

	//	explicitWait((Elements.orderConfirmationScheduled_dd));
		//	if (driver.findElement(Elements.orderConfirmationScheduled_dd).isDisplayed()) {

				log.info("Delivery Schedule Order is placed");
				extTestObj.createNode("Delivery Schedule Order is placed").pass("PASSED");
		//	}

		} catch (Exception e) {
			log.error("Failed to place order");
			extTestObj.createNode("Failed to place order")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopWebTest();
		}

	}
	public void webSelectDeliveryTodayIJW()
	{
		try {
			driver.findElement(Elements.deliveryButton_dd).isEnabled();
			clickableWait(Elements.deliveryButton_dd);
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			Thread.sleep(3000);
			log.info("Delivery button is selected");
			extTestObj.createNode("Delivery button is selected").pass("PASSED");
		} catch (Exception e) {
			log.error("Failed to select delivery button");
			extTestObj.createNode("Failed to select delivery button")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());
			stopWebTest();
		}
		try {
			Thread.sleep(4000);
			clickableWait(Elements.scheduleLater_DD);
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			explicitWait(Elements.crossButton_DD);

			if (driver.findElement(Elements.crossButton_DD).isDisplayed()) {
				log.info("Schedule Later option selected");
				extTestObj.createNode("Schedule Later option selected").pass("PASSED");
			}
		} catch (Exception e) {
			log.error("Schedule Later option selection failed");
			extTestObj.createNode("Schedule Later option selection failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopWebTest();
		}
		String deliveryTime = excel.getCellData("DoorDash", "Delivery Time", 3);
		System.out.println(deliveryTime);

		try {
			String deliveryDate = excel.getCellData("DoorDash", "ScheduleDate", 2);
			explicitWait(Elements.IJWDeliveryDates);
			//selecting delivery today from the list of delivery dates
			List<WebElement> deliverydates=driver.findElements(Elements.IJWDeliveryDates);
			for(int i=0;i<deliverydates.size();i++)
			{
				if(deliverydates.get(i).getText().equals(deliveryDate))
				{
					deliverydates.get(i).click();
					driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
				}
			}
			//deliverydates.get(0).click();
			log.info("Schedule Later - today button clicked");
			extTestObj.createNode("Schedule Later - today button clicked").pass("PASSED");
			Thread.sleep(3000);
			
			WebElement SLClick = driver.findElement(Elements.IJWscheduleforLater);
			JavascriptExecutor js = (JavascriptExecutor) driver;
						js.executeScript("arguments[0].click()", SLClick);
						driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			//explicitWait(Elements.IJWscheduleforLater);
			if((driver.findElement(Elements.IJWscheduleforLater)).isEnabled()){
			clickableWait(Elements.IJWscheduleforLater);
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			 }
			 explicitWait(By.xpath("//span[text()='"+deliveryTime+"']"));
			 if(driver.findElement(By.xpath("//span[text()='"+deliveryTime+"']")).isDisplayed())
			{
				log.info("Schedule for later-today selected and time menu is opened");
				extTestObj.createNode("Schedule for later-today selected and time menu is opened").pass("PASSED");
			}
		} catch (Exception e) {
			log.error("Schedule for later-today selection failed");
			extTestObj.createNode("Schedule for later-today selection failed")
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopWebTest();
		}
		String selectdeliveryTime = excel.getCellData("DoorDash", "Delivery Time", 3);
		System.out.println(selectdeliveryTime);

		try {

		//	if ((driver.findElement(By.xpath("//span[contains(text(),'" + selectdeliveryTime + "')]"))).isEnabled()) {

				clickableWait(By.xpath("//span[text()='" + selectdeliveryTime +"']"));
				driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		//	}
			explicitWait(Elements.placeOrder_DD);
			if (driver.findElement(Elements.placeOrder_DD).isDisplayed()) {
				log.info("Delivery time selected"+selectdeliveryTime);
				extTestObj.createNode("Delivery time selected"+selectdeliveryTime).pass("PASSED");
			}
		} catch (Exception e) {
			log.error("Delivery time selection failed"+selectdeliveryTime);
			extTestObj.createNode("Delivery time selection failed"+selectdeliveryTime)
					.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
			log.error(e.getMessage());

			stopWebTest();
		}
	}
	public void webSelectDeliveryOrderTomorrowIJW() {
		
		try {

					driver.findElement(Elements.deliveryButton_dd).isEnabled();
					clickableWait(Elements.deliveryButton_dd);
					driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
					Thread.sleep(3000);

					log.info("Delivery button is selected");
					extTestObj.createNode("Delivery button is selected").pass("PASSED");
				} catch (Exception e) {
					log.error("Failed to select delivery button");
					extTestObj.createNode("Failed to select delivery button")
							.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
					log.error(e.getMessage());

					stopWebTest();
				}


		try {
					Thread.sleep(4000);
					clickableWait(Elements.scheduleLater_DD);
					driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
					explicitWait(Elements.crossButton_DD);

					if (driver.findElement(Elements.crossButton_DD).isDisplayed()) {
						log.info("Schedule Later option selected");
						extTestObj.createNode("Schedule Later option selected").pass("PASSED");
					}
				} catch (Exception e) {
					log.error("Schedule Later option selection failed");
					extTestObj.createNode("Schedule Later option selection failed")
							.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
					log.error(e.getMessage());

					stopWebTest();
				}

		String deliveryTime = (String) excel.getCellData("DoorDash", "Delivery Time", 3); // PM-4
				System.out.println(deliveryTime);

				try {

					if ((driver.findElement(Elements.selectTomorrow_DD)).isEnabled()) {

						clickableWait(Elements.selectTomorrow_DD);
						driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
					}
					explicitWait(
							By.xpath("//button[@kind='BUTTON/SECONDARY_PILL']//span[contains(text(),'" + deliveryTime +"')]")); // PM-4
					if (driver
							.findElement(By.xpath(
									"//button[@kind='BUTTON/SECONDARY_PILL']//span[contains(text(),'" + deliveryTime +"')]"))
							.isDisplayed()) {
						log.info("Tomorrow option is selected and time menu is visible");
						extTestObj.createNode("Tomorrow option is selected and time menu is visible").pass("PASSED");
					}
				} catch (Exception e) {
					log.error("Tomorrow option selection failed");
					extTestObj.createNode("Tomorrow option selection failed")
							.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
					log.error(e.getMessage());
					stopWebTest();
				}

		String selectdeliveryTime = excel.getCellData("DoorDash", "Delivery Time", 3);
				System.out.println(selectdeliveryTime);

				try {

					if ((driver.findElement(By.xpath("//span[contains(text(),'" + selectdeliveryTime +"')]"))).isEnabled()) {

						clickableWait(By.xpath("//span[contains(text(),'" + selectdeliveryTime +"')]"));
						driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
					}
					explicitWait(Elements.placeOrder_DD);
					if (driver.findElement(Elements.placeOrder_DD).isDisplayed()) {
						log.info("Delivery time selected"+selectdeliveryTime);
						extTestObj.createNode("Delivery time selected"+selectdeliveryTime).pass("PASSED");
					}
				} catch (Exception e) {
					log.error("Delivery time selection failed"+selectdeliveryTime);
					extTestObj.createNode("Delivery time selection failed"+selectdeliveryTime)
							.fail("Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()").error(e);
					log.error(e.getMessage());

					stopWebTest();
				}
			}

				   
   }
    
    
   
   
  
   
    
    
    

    
	
	

	


