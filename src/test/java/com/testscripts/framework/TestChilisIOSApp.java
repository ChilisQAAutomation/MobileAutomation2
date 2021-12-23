package com.testscripts.framework;


import java.net.URL;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.configuration.framework.Base;
import com.experitest.appium.SeeTestClient;
import com.pageaction.framework.Elements;
import com.pageaction.framework.FunctionalComponents;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class TestChilisIOSApp extends Base {
	public IOSDriver<IOSElement> driver = null;
	SeeTestClient seetest;
	
	@BeforeSuite
	public void setPlatform()
	{
		Properties prop2 = returnProperty(PROJECT_PATH+"/src/test/java/com/properties/framework/platform.properties");
		editProperties(prop2, "Android_Browser", "false");
		editProperties(prop2, "IOS_Browser", "false");
		editProperties(prop2, "Android_App", "false");
		editProperties(prop2, "IOS_App", "true");
		editProperties(prop2,"WEB","false");
		
	}
	@BeforeMethod
	public void initialize() throws Exception {
		Properties pro = returnProperty(PROJECT_PATH+"/src/test/java/com/properties/framework/data.properties");
		DesiredCapabilities dc = sendIOSChilisAppCapabilities();
		driver = new IOSDriver<IOSElement>(new URL(pro.getProperty("CloudDeviceURL")), dc);
		seetest = new SeeTestClient(driver);
//		seetest.install("cloud:com.chilistogo.prod", true, false);
//		driver.context("NATIVE_APP_INSTRUMENTED");
//		seetest.launch("com.chilistogo.prod", true, false);
		driver.rotate(ScreenOrientation.PORTRAIT);
//		seetest.setProperty("ios.native.nonInstrumented","true");
		try {
		driver.findElement(Elements.iosDontAllowButtonXpath).click();
		driver.findElement(Elements.appCancelButtonXpath).click();
		seetest.click("NATIVE","xpath="+Elements.OKbuttoninAddMyVisitXpath,0,1);
		}
		catch(Exception e){}
		getDriver(driver);	
	}
	
	@Test(priority=1)
	public void TC01_IOS_APP_LoginLogout_TestCase() throws Exception {
		Logger log = LogManager.getLogger("LoginLogout_TestCase");
		log.info("******TC01_IOS_APP : Starting to login logout functionality in IOS Chilis App******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.iosAppClosePopUp();
		components.iosAppValidateLogin();
		components.iosAppValidateLogout();
	}
	@Test(priority=2)
	public void TC02_IOS_APP_ValidateLocationSearch_TestCase() throws Exception {
		Logger log = LogManager.getLogger("ValidateLocationSearch_TestCase");
		log.info("******TC02_IOS_APP : Starting to test Location Search Functionality of IOS Chilis App******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.iosAppClosePopUp();
		components.iosAppValidateRestaurantSelectionForGuestUser(3);
	}
	@Test(priority=3)
	public void TC03_IOS_APP_RewardsValidation_loggedInUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("RewardsValidation_loggedInUser_TestCase");
		log.info("******TC03_IOS_APP : Starting to validate Rewards for sign in users******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.iosAppClosePopUp();
		components.iosAppValidateLogin();
		components.iosAppValidateRewards();		
	}
	@Test(priority=4)
	public void TC04_IOS_APP_UpdateMyAccount_TestCase() throws Exception {
		Logger log = LogManager.getLogger("UpdateMyAccount_TestCase");
        log.info("******TC04_IOS_APP : Starting to validate user is able to update my Account on IOS devices******");
        FunctionalComponents components = new FunctionalComponents(driver, log);
        components.iosAppClosePopUp();
        components.iosAppValidateLogin();
        components.iosAppValidateAccountUpdate();
	}
	@Test(priority=5)
	public void TC05_IOS_APP_validateChilisFavouriteItems_TestCase() throws Exception {
		Logger log = LogManager.getLogger("validateChilisFavouriteItems_TestCase");
		log.info("******TC05_IOS_APP : Starting to validate Chilis Favourite Items for sign in users******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.iosAppClosePopUp();
//		components.iosAppValidateLogin();
		components.iosAppgetChilisFavouriteItems();		
	}
	@Test(priority=6)
	public void TC06_IOS_APP_loggedInUserOrder_TestCase() throws Exception {
		Logger log = LogManager.getLogger("loggedInUserOrder_TestCase");
		 log.info("******TC06_IOS_APP : Starting to validate user is able to do order placement with existing logged in user******");
		 FunctionalComponents components = new FunctionalComponents(driver, log);
		 components.iosAppClosePopUp();
		 components.iosAppValidateLogin();
		 components.iosAppValidateRestaurantSelection(4);
		 components.iosAppValidateMenuSelection();
		 components.iosAppValidateCheckout();
		 components.iosAppContinueToPayment();
		 components.iosAppEnterPaymentDetails();
		 components.iosAppPlaceOrder();
	}
	@Test(priority=7)
	public void TC07_IOS_APP_CurbsideASAPOrder_MCAUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("TestCurbsideASAPOrderMCAUser");
		 log.info("******TC07_IOS_APP : Starting to validate user is able to place Curbside ASAP Order for MCA User******");
		 FunctionalComponents components = new FunctionalComponents(driver, log);
		 components.iosAppClosePopUp();
		 components.iosAppValidateLogin();
		 components.iosAppValidateRestaurantSelection(3);
		 components.iosAppValidateMenuSelection();
		 components.iosAppValidateCheckout();
		 components.iosAppselectCurbsideASAP();
		 components.iosAppContinueToPayment();
		 components.iosAppEnterPaymentDetails();
		 components.iosAppPlaceOrder();
	}
	@Test(priority=8)
	public void TC08_IOS_APP_CarryoutASAPOrder_MCAUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("CarryoutASAPOrder_MCAUser_TestCase");
		 log.info("******TC08_IOS_APP : Starting to validate user is able to place Carryout ASAP Order for MCA User******");
		 FunctionalComponents components = new FunctionalComponents(driver, log);
		 components.iosAppClosePopUp();
		 components.iosAppValidateLogin();
		 components.iosAppValidateRestaurantSelection(3);
		 components.iosAppValidateMenuSelection();
		 components.iosAppValidateCheckout();
		 components.iosAppSelectCarryOutASAP();
		 components.iosAppContinueToPayment();
		 components.iosAppEnterPaymentDetails();
		 components.iosAppPlaceOrder();	
	}
	@Test(priority=9)
	public void TC09_IOS_APP_CarryoutLaterTodayOrder_MCAUser_TestCase() throws Exception {
		 Logger log = LogManager.getLogger("CarryoutLaterTodayOrder_MCAUser_TestCase");
		 log.info("******TC09_IOS_APP : Starting to validate user is able to place Carryout Later Today Order for MCA User******");
		 FunctionalComponents components = new FunctionalComponents(driver, log);
		 components.iosAppClosePopUp();
		 components.iosAppValidateLogin();
		 components.iosAppValidateRestaurantSelection(3);
		 components.iosAppValidateMenuSelection();
		 components.iosAppValidateCheckout();
		 components.iosAppSelectCarryOutLaterToday();
		 components.iosAppContinueToPayment();
		 components.iosAppEnterPaymentDetails();
		 components.iosAppPlaceOrder();
	}
	@Test(priority=10)
	public void TC10_IOS_APP_CurbsideLaterTodayOrder_MCAUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("CurbsideLaterTodayOrder_MCAUser_TestCase");
		 log.info("******TC10_IOS_APP : Starting to validate user is able to place Curbside Later Today Order for MCA User******");
		 FunctionalComponents components = new FunctionalComponents(driver, log);
		 components.iosAppClosePopUp();
		 components.iosAppValidateLogin();
		 components.iosAppValidateRestaurantSelection(3);
		 components.iosAppValidateMenuSelection();
		 components.iosAppValidateCheckout();
		 components.iosAppSelectCurbsideLaterToday();
		 components.iosAppContinueToPayment();
		 components.iosAppEnterPaymentDetails();
		 components.iosAppPlaceOrder();
	}
	@Test(priority=11)
	public void TC11_IOS_APP_CurbsideGuestUserOrder_TestCase() throws Exception {
		 Logger log = LogManager.getLogger("CurbsideGuestUserOrder_TestCase");
		 log.info("******TC11_IOS_APP : Starting to validate user is able to place Curbside Order for Guest User******");
		 FunctionalComponents components = new FunctionalComponents(driver, log);
		 components.iosAppClosePopUp();
		 components.iosAppValidateRestaurantSelectionForGuestUser(3);
		 components.iosAppValidateMenuSelection();
		 components.iosAppValidateCheckout();
		 components.iosAppSelectCurbsideLaterToday();
		 components.iosAppenterGuestDetails();
		 components.iosAppEnterVehicleDetails();
		 components.iosAppContinueToPayment();
		 components.iosAppEnterPaymentDetails();
		 components.iosAppPlaceOrder();
	}
	@Test(priority=12)
	public void TC12_IOS_APP_DeliveryASAP_GuestUserOrder_TestCase() throws Exception {
		 Logger log = LogManager.getLogger("DeliveryASAP_GuestUserOrder_TestCase");
		 log.info("******TC12_IOS_APP : Starting to validate user is able to place Delivery ASAP Order for Guest User******");
		 FunctionalComponents components = new FunctionalComponents(driver, log);
		 components.iosAppClosePopUp();
		 components.iosAppValidateRestaurantSelectionForGuestUser(3);
		 components.iosAppValidateMenuSelection();
		 components.iosAppValidateCheckout();
		 components.iosAppEnterDeliveryDetails(3);
		 components.iosAppoptDeliveryASAP();
		 components.iosAppenterGuestDetails();
		 components.iosAppContinueToPayment();
		 components.iosAppEnterPaymentDetails();
		 components.iosAppPlaceOrder();
	}
	@Test(priority=13)
	public void TC13_IOS_APP_DeliveryLaterToday_GuestUserOrder_TestCase() throws Exception {
		Logger log = LogManager.getLogger("DeliveryLaterToday_GuestUserOrder_TestCase");
		 log.info("******TC13_IOS_APP : Starting to validate user is able to place Delivery Later Today Order for Guest User******");
		 FunctionalComponents components = new FunctionalComponents(driver, log);
		 components.iosAppClosePopUp();
		 components.iosAppValidateRestaurantSelectionForGuestUser(3);
		 components.iosAppValidateMenuSelection();
		 components.iosAppValidateCheckout();
		 components.iosAppEnterDeliveryDetails(3);
		 components.iosAppSelectDeliveryLaterToday();
		 components.iosAppenterGuestDetails();
		 components.iosAppContinueToPayment();
		 components.iosAppEnterPaymentDetails();
		 components.iosAppPlaceOrder();	
	}
	@Test(priority=14)
	public void TC14_IOS_APP_DeliveryASAPOrder_MCAUser_TestCase() throws Exception {
		 Logger log = LogManager.getLogger("DeliveryASAPOrder_MCAUser_TestCase");
		 log.info("******TC14_IOS_APP : Starting to validate user is able to place Delivery ASAP Order for MCA User******");
		 FunctionalComponents components = new FunctionalComponents(driver, log);
		 components.iosAppClosePopUp();
		 components.iosAppValidateLogin();
		 components.iosAppValidateRestaurantSelection(3);
		 components.iosAppValidateMenuSelection();
		 components.iosAppValidateCheckout();
		 components.iosAppEnterDeliveryDetails(3);
		 components.iosAppoptDeliveryASAP();
		 components.iosAppContinueToPayment();
		 components.iosAppEnterPaymentDetails();
		 components.iosAppPlaceOrder();
	}
	@Test(priority=15)
	public void TC15_IOS_APP_DeliveryLaterTodayOrder_MCAUser_TestCase() throws Exception {
		 Logger log = LogManager.getLogger("DeliveryLaterTodayOrder_MCAUser_TestCase");
		 log.info("******TC15_IOS_APP : Starting to validate user is able to place Delivery Later Today Order for MCA User******");
		 FunctionalComponents components = new FunctionalComponents(driver, log);
		 components.iosAppClosePopUp();
		 components.iosAppValidateLogin();
		 components.iosAppValidateRestaurantSelection(3);
		 components.iosAppValidateMenuSelection();
		 components.iosAppValidateCheckout();
		 components.iosAppEnterDeliveryDetails(3);
		 components.iosAppSelectDeliveryLaterToday();
		 components.iosAppContinueToPayment();
		 components.iosAppEnterPaymentDetails();
		 components.iosAppPlaceOrder();
	}
	@Test(priority=16)
	public void TC16_IOS_APP_CurbsideItemCustomizationOrder_MCAUser_TestCase() throws Exception {
		 Logger log = LogManager.getLogger("CurbsideItemCustomizationOrder_MCAUser_TestCase");
		 log.info("******TC16_IOS_APP : Starting to validate user is able to place Curbside Item Customized for MCA User******");
		 FunctionalComponents components = new FunctionalComponents(driver, log);
		 components.iosAppClosePopUp();
		 components.iosAppValidateLogin();
		 components.iosAppValidateRestaurantSelection(3);
		 components.iosAppcustomizeItem();
		 components.iosAppClickCheckoutforCustomizedOrder();
		 components.iosAppSelectCurbsideLaterToday();
		 components.iosAppContinueToPayment();
		 components.iosAppEnterPaymentDetails();
		 components.iosAppPlaceOrder();
	}
	@Test(priority=17)
	public void TC17_IOS_APP_CarryOutFutureOrderItemCustomizationOrder_GuestUser_TestCase() throws Exception {
		 Logger log = LogManager.getLogger("CarryOutFutureOrderItemCustomizationOrder_GuestUser_TestCase");
		 log.info("******TC17_IOS_APP : Starting to validate user is able to place Carry out future order Item Customization for Guest User******");
		 FunctionalComponents components = new FunctionalComponents(driver, log);
		 components.iosAppClosePopUp();
		 components.iosAppValidateRestaurantSelectionForGuestUser(3);
		 components.iosAppcustomizeItem();
		 components.iosAppClickCheckoutforCustomizedOrder();
		 components.iosAppselectCarryoutPickUpFuture();
		 components.iosAppenterGuestDetails();
		 components.iosAppCheckRoundOff();
		 components.iosAppPlaceOrderforFutureOrder();
	}
	@Test(priority=18)
	public void TC18_IOS_APP_CarryOutFutureFavouriteItemsOrder_SignedInUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("CarryOutFutureFavouriteItemsOrder_SignedInUser_TestCase");
		 log.info("******TC18_IOS_APP : Starting to validate user is able to place Carry out future favourite Items order for Signed in User******");
		 FunctionalComponents components = new FunctionalComponents(driver, log);
		 components.iosAppClosePopUp();
		 components.iosAppValidateLogin();
		 components.iosAppValidateRestaurantSelection(3);
		 components.iosAppSelectChilisFavouriteItems();
		 components.iosAppClickCheckoutforCustomizedOrder();
		 components.iosAppselectCarryoutPickUpFuture();
		 components.iosAppCheckRoundOff();
		 components.iosAppPlaceOrderforFutureOrder();
	}
	@Test(priority=19)
	public void TC19_IOS_APP_Reorder_TestCase() throws Exception {
		 Logger log = LogManager.getLogger("Reorder_TestCase");
		 log.info("******TC19_IOS_APP : Starting to validate user is able to place Reorder******");
		 FunctionalComponents components = new FunctionalComponents(driver, log);
		 components.iosAppClosePopUp();
		 components.iosAppValidateLogin();
		 components.iosAppReorder();
		 components.iosAppValidateCheckout();
		 components.iosAppSelectCurbsideLaterToday();
		 components.iosAppContinueToPayment();
		 components.iosAppEnterPaymentDetails();
		 components.iosAppPlaceOrder();
	}
	@Test(priority=20)
	public void TC20_IOS_APP_AddMyVisit_TestCase() throws Exception {
		Logger log = LogManager.getLogger("AddMyVisit_TestCase");
		 log.info("******TC20_IOS_APP : Starting to validate whether user is able to add my visit******");
		 FunctionalComponents obj = new FunctionalComponents(driver, log);
		 obj.iosAppClosePopUp();
	     obj.iosAppValidateLogin();
	     obj.iosAppenterVisitDetails();
	     obj.iosAppclickVisitSubmit();
		}
	@Test(priority=21)
	public void TC21_IOS_APP_PlaceOrderWithRewards_TestCase() throws Exception {
		Logger log = LogManager.getLogger("PlaceOrderWithRewards_TestCase");
		 log.info("******TC21_IOS_APP : Starting to validate user is able to place order with rewards******");
		 FunctionalComponents components = new FunctionalComponents(driver, log);
		 components.iosAppClosePopUp();
		 components.iosAppValidateLogin();
		 components.iosAppValidateRestaurantSelection(3);
		 components.iosAppValidateMenuSelection();
		 components.iosAppCheckOutWithReward();
		 components.iosAppSelectCarryOutASAP();
		 components.iosAppvalidateRewardInCheckOutPage();
		 components.iosAppContinueToPayment();
		 components.iosAppEnterPaymentDetails();
		 components.iosAppPlaceOrder();	
		
	}
	
	@AfterMethod
	public void tearDown() {
		System.out.println("Report URL: " + driver.getCapabilities().getCapability("reportUrl"));
		seetest.uninstall("com.chilistogo.prod");
		driver.quit();
		
		
	}
	

}
