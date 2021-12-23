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
import com.pageaction.framework.FunctionalComponents;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

//***Chilis Gift Card Test Cases for Android App***//
public class TestChilisAndroidApp_QA3 extends Base {
	

	public AndroidDriver<AndroidElement> driver = null;
	SeeTestClient seetest;
	@BeforeSuite
	public void setPlatform()
	{
		Properties prop2 = returnProperty(PROJECT_PATH+"/src/test/java/com/properties/framework/platform.properties");
		editProperties(prop2, "Android_Browser", "false");
		editProperties(prop2, "IOS_Browser", "false");
		editProperties(prop2, "Android_App", "true");
		editProperties(prop2, "IOS_App", "false");
		editProperties(prop2,"WEB","false");
		
	}
	@BeforeMethod
	public void initialize() throws Exception {
		Properties pro = returnProperty(PROJECT_PATH+"/src/test/java/com/properties/framework/data.properties");
		DesiredCapabilities dc = sendAndroidChilisAppCapabilities();
		driver = new AndroidDriver<AndroidElement>(new URL(pro.getProperty("CloudDeviceURL")), dc);
		seetest = new SeeTestClient(driver);
		seetest.install("cloud:com.brinker.chilis/.features.LaunchActivity", true, false);
		driver.context("NATIVE_APP_INSTRUMENTED");
		seetest.launch("cloud:com.brinker.chilis/.features.LaunchActivity", true, false);
		driver.rotate(ScreenOrientation.PORTRAIT);
		seetest.setProperty("Android.native.nonInstrumented","true");
		getDriver(driver);
		}
	@Test(priority = 1)
	public void TC22_ANDROID_APP_QA2_loginLogout_TestCase() throws Exception {
		Logger log = LogManager.getLogger("loginLogout_TestCase");
		log.info("******TC22_ANDROID_APP_QA2 : Starting to login logout functionality in Chilis App******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.androidAppClosePopUp();
		components.androidAppValidateLogin();
		components.androidAppValidateLogout();
	}
	@Test(priority = 2)
	public void TC23_ANDROID_APP_QA2_validateLocationSearch_TestCase() throws Exception {
		Logger log = LogManager.getLogger("validateLocationSearch_TestCase");
		log.info("******TC23_ANDROID_APP_QA2 : Starting to test Location Search Functionality of Chilis App******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.androidAppClosePopUp();
		components.androidAppValidateRestaurantSelection(3);
	}
	@Test(priority = 3)
	public void TC24_ANDROID_APP_QA2_validateRewards_SignedInUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("validateRewards_SignedInUser_TestCase");
		log.info("******TC24_ANDROID_APP_QA2 : Starting to validate Rewards for sign in users******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.androidAppClosePopUp();
		components.androidAppValidateLogin();
		components.androidAppValidateRewards();
	}
	@Test(priority = 4)
	public void TC25_ANDROID_APP_QA2_validateChilisFavouriteItems_TestCase() throws Exception {
		Logger log = LogManager.getLogger("validateChilisFavouriteItems_TestCase");
		log.info("******TC25_ANDROID_APP_QA2 : Starting to validate Chilis Favourite Items for sign in users******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.androidAppClosePopUp();
		components.androidAppValidateLogin();
		components.androidAppgetChilisFavouriteItems();
	}
	@Test(priority = 5)
	public void TC26_ANDROID_APP_QA2_loggedInUserOrder_TestCase() throws Exception {
		Logger log = LogManager.getLogger("loggedInUserOrder_TestCase");
		log.info("******TC26_ANDROID_APP_QA2 : Starting to validate user is able to do order placement with existing logged in user******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.androidAppClosePopUp();
		components.androidAppValidateLogin();
		components.androidAppValidateRestaurantSelectionforLoggedInUser(4);
		components.androidAppValidateMenuSelection(3);
		components.androidAppValidateCheckOut();
		components.androidAppContinueToPayment();
		components.androidAppEnterPaymentDetailswithSuggestedTipAndGiftCard(4);
		components.androidAppPlaceOrder();
		components.androidAppvalidateOrderPlace();
	}
	@Test(priority = 6)
	public void TC27_ANDROID_APP_QA2_AddMyVisit_TestCase() throws Exception {
		Logger log = LogManager.getLogger("AddMyVisit_TestCase");
		log.info("******TC27_ANDROID_APP_QA2 : Starting to verify user is able to submit Add my visit form for login member******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.androidAppClosePopUp();
		components.androidAppValidateLogin();
		components.androidAppAddVisitDetailsqa2();
		components.appretrieveAddVisitSuccessMessage();
	}
	@Test(priority = 7)
	public void TC28_ANDROID_APP_QA2_CurbsideOrder_GuestUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("CurbsideOrder_GuestUser_TestCase");
		log.info("******TC28_ANDROID_APP_QA2 : Starting to validate user is able to place Curbside order for guest user******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.androidAppClosePopUp();
		components.androidAppValidateRestaurantSelection(3);
		components.androidAppValidateMenuSelection(4);
		components.androidAppValidateCheckOut();  
		components.androidAppSelectCurbside();
		components.androidAppEnterGuestDetails();
		components.androidAppEnterVehicleDetails();
		components.androidAppContinueToPayment();
		components.androidAppEnterPaymentDetailswithCustomTipAndGiftCard(3);
		components.androidAppPlaceOrder();
		components.androidAppvalidateOrderPlace();
	}
	@Test(priority = 8)
	public void TC29_ANDROID_APP_QA2_CarryOutASAP_MCAUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("CarryOutASAP_MCAUser_TestCase");
		log.info("******TC29_ANDROID_APP_QA2 : Starting to validate user is able to do place Carry out ASAP order with MCA User******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.androidAppClosePopUp();
		components.androidAppValidateLogin();
		components.androidAppValidateRestaurantSelectionforLoggedInUser(3);
		components.androidAppValidateMenuSelection(6);
		components.androidAppValidateCheckOut();
		components.androidAppSelectCarryout();
		components.androidAppSelectPickupAsap();
		components.androidAppContinueToPayment();
		components.androidAppEnterPaymentDetailswithSuggestedTipAndGiftCard(3);
		components.androidAppPlaceOrder();
		components.androidAppvalidateOrderPlace();
	}
	@Test(priority = 9)
	public void TC30_ANDROID_APP_QA2_CurbsideASAP_MCAUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("CurbsideASAP_MCAUser_TestCase");
		log.info("******TC30_ANDROID_APP_QA2 : Starting to validate user is able to do place Curbside ASAP order with MCA User******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.androidAppClosePopUp();
		components.androidAppValidateLogin();
		components.androidAppValidateRestaurantSelectionforLoggedInUser(3);
		components.androidAppValidateMenuSelection(8);
		components.androidAppValidateCheckOut();
		components.androidAppSelectCurbside();
		components.androidAppSelectPickupAsap();
		components.androidAppContinueToPayment();
		components.androidAppEnterPaymentDetailswithCustomTipAndGiftCard(3);
		components.androidAppPlaceOrder();
	    components.androidAppvalidateOrderPlace();
	}
	@Test(priority = 10)
	public void TC31_ANDROID_APP_QA2_CarryOutLaterToday_MCAUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("CarryOutLaterToday_MCAUser_TestCase");
		log.info("******TC31_ANDROID_APP_QA2 : Starting to validate user is able to do place Carry out Later Today order with MCA User******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.androidAppClosePopUp();
		components.androidAppValidateLogin();
		components.androidAppValidateRestaurantSelectionforLoggedInUser(3);
		components.androidAppValidateMenuSelection(2);
		components.androidAppValidateCheckOut();
		components.androidAppSelectCarryout();
		components.androidAppSelectPickupLaterToday();
		components.androidAppContinueToPayment();
		components.androidAppEnterPaymentDetailswithSuggestedTipAndGiftCard(3);
		components.androidAppPlaceOrder();
		components.androidAppvalidateOrderPlace();
	}
	@Test(priority = 11)
	public void TC32_ANDROID_APP_QA2_CurbsideLaterToday_MCAUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("CurbsideLaterToday_MCAUser_TestCase");
		log.info("******TC32_ANDROID_APP_QA2 : Starting to validate user is able to do place Curbside Later Today order with MCA User******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.androidAppClosePopUp();
		components.androidAppValidateLogin();
		components.androidAppValidateRestaurantSelectionforLoggedInUser(3);
		components.androidAppValidateMenuSelection(8);
		components.androidAppValidateCheckOut();
		components.androidAppSelectCurbside();
		components.androidAppSelectPickupLaterToday();
		components.androidAppContinueToPayment();
		components.androidAppEnterPaymentDetailswithCustomTipAndGiftCard(3);
		components.androidAppPlaceOrder();
	    components.androidAppvalidateOrderPlace();
	}
	@Test(priority = 12)
	public void TC33_ANDROID_APP_QA2_DeliveryLaterToday_MCAUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("DeliveryLaterToday_MCAUser_TestCase");
		log.info("******TC33_ANDROID_APP_QA2 : Starting to validate user is able to do place Delivery Later Today order with MCA User******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.androidAppClosePopUp();
		components.androidAppValidateLogin();
		components.androidAppValidateRestaurantSelectionforLoggedInUser(3);
		components.androidAppValidateMenuSelection(3);
		components.androidAppValidateCheckOut();
		components.androidAppEnterDeliveryDetailsQA2(4);
		components.androidAppSelectDeliveryDateLaterToday();
		components.androidAppContinueToPayment();
		components.androidAppEnterPaymentDetailswithSuggestedTipAndGiftCard(3);
		components.androidAppPlaceOrder();
	    components.androidAppvalidateOrderPlace();
	}
	@Test(priority = 13)
	public void TC34_ANDROID_APP_QA2_DeliveryASAP_MCAUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("DeliveryASAP_MCAUser_TestCase");
		log.info("******TC34_ANDROID_APP_QA2 : Starting to validate user is able to do place Delivery ASAP order with MCA User******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.androidAppClosePopUp();
		components.androidAppValidateLogin();
		components.androidAppValidateRestaurantSelectionforLoggedInUser(3);    
		components.androidAppValidateMenuSelection(2);
		components.androidAppValidateCheckOut();
		components.androidAppEnterDeliveryDetailsQA2(3);
		components.androidAppSelectDeliveryDateASAP();
		components.androidAppContinueToPayment();
		components.androidAppEnterPaymentDetailswithCustomTipAndGiftCard(3);
		components.androidAppPlaceOrder();
	    components.androidAppvalidateOrderPlace();
	}
	@Test(priority = 14)
	public void TC35_ANDROID_APP_QA2_DeliveryLaterTodayOrder_GuestUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("DeliveryLaterTodayOrder_GuestUser_TestCase");
		log.info("******TC35_ANDROID_APP_QA2 : Starting to validate user is able to place Delivery Later Today Order for guest user******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.androidAppClosePopUp();
		components.androidAppValidateRestaurantSelection(3);
		components.androidAppSelectChilisFavouriteItems(2);
		components.androidAppChangeQuantity();
		components.androidAppValidateCheckOut();
		components.androidAppEnterDeliveryDetailsQA2(3);
		components.androidAppSelectDeliveryDateLaterToday();
		components.androidAppEnterGuestDetails();
		components.androidAppContinueToPayment();
		components.androidAppEnterPaymentDetailswithSuggestedTipAndGiftCard(3);
		components.androidAppPlaceOrder();
		components.androidAppvalidateOrderPlace();
		components.androidAppValidateOrderTrackable();
	}
	@Test(priority = 15)
	public void TC36_ANDROID_APP_QA2_DeliveryASAPOrder_GuestUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("DeliveryASAPOrder_GuestUser_TestCase");
		log.info("******TC36_ANDROID_APP_QA2 : Starting to validate user is able to place Delivery ASAP Order for guest user******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.androidAppClosePopUp();
		components.androidAppValidateRestaurantSelection(3);
		components.androidAppSelectChilisFavouriteItems(3);
		components.androidAppValidateCheckOut();
		components.androidAppEnterDeliveryDetailsQA2(3);
		components.androidAppSelectDeliveryDateASAP();
		components.androidAppEnterGuestDetails();
		components.androidAppContinueToPayment();
		components.androidAppEnterPaymentDetailswithCustomTipAndGiftCard(3);
		components.androidAppPlaceOrder();
		components.androidAppvalidateOrderPlace();
		components.androidAppValidateOrderTrackable();
	}
	@Test(priority = 16)
	public void TC37_ANDROID_APP_QA2_CurbsideItemCustomization_MCAUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("CurbsideItemCustomization_MCAUser_TestCase");
		log.info("******TC37_ANDROID_APP_QA2 : Starting to validate user is able to do place Curbside order Item Customization for MCA User******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.androidAppClosePopUp();
		components.androidAppValidateLogin();
		components.androidAppValidateRestaurantSelectionforLoggedInUser(3);
		components.androidAppSelectMenuCategory();
		components.androidAppSelectMenuItem();
		components.androidAppCustomizeMenuItem();
		components.androidAppValidateCheckOut();
		components.androidAppSelectCurbside();
		components.androidAppSelectPickupAsap();
		components.androidAppContinueToPayment();
		components.androidAppEnterPaymentDetailswithSuggestedTipAndGiftCard(3);
		components.androidAppPlaceOrder();
		components.androidAppvalidateOrderPlace();
	}
	@Test(priority = 17)
	public void TC38_ANDROID_APP_QA2_CarryOutFutureDateOrder_MCAUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("CarryOutFutureDateOrder_MCAUser_TestCase");
		log.info("******TC38_ANDROID_APP_QA2 : Starting to validate user is able to do place Carry out Future Date favourite Items order with MCA User******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.androidAppClosePopUp();
		components.androidAppValidateLogin();
		components.androidAppValidateRestaurantSelectionforLoggedInUser(3);
		components.androidAppValidateMenuSelection(3);
		components.androidAppValidateCheckOut();
		components.androidAppSelectCarryout();
		components.androidAppSelectPickupFutureDate();
		components.androidAppCheckRoundOff();
		components.androidAppPlaceOrder();
	}
	@Test(priority = 18)
	public void TC39_ANDROID_APP_QA2_CarryOutFutureOrderItemCustomization_GuestUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("CarryOutFutureOrderItemCustomization_GuestUser_TestCase");
		log.info("******TC39_ANDROID_APP_QA2_Starting to validate user is able to place Carry Out Future Order item customization for Guest User******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.androidAppClosePopUp();
		components.androidAppValidateRestaurantSelection(3);
		components.androidAppSelectMenuCategory();
		components.androidAppSelectMenuItem();
		components.androidAppCustomizeMenuItem();
		components.androidAppValidateCheckOut();
		components.androidAppSelectCarryout();
		components.androidAppSelectPickupFutureDate();
		components.androidAppEnterGuestDetails();
		components.androidAppCheckRoundOff();
		components.androidAppPlaceOrder();
	}
	@Test(priority = 19)
	public void TC40_ANDROID_APP_QA2_Reorder_TestCase() throws Exception {
		Logger log = LogManager.getLogger("Reorder_TestCase");
		log.info("******TC40_ANDROID_APP_QA2 : Starting to verify user is able to place reorder******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.androidAppClosePopUp();
		components.androidAppValidateLogin();
		components.androidAppValidateRestaurantSelectionforLoggedInUser(3);
		components.androidAppReorderItem();
		components.androidAppValidateCheckOut();
		components.androidAppSelectCurbside();
		components.androidAppSelectPickupAsap();
		components.androidAppContinueToPayment();
		components.androidAppEnterPaymentDetailswithCustomTipAndGiftCard(3);
		components.androidAppPlaceOrder();
		components.androidAppvalidateOrderPlace();
	}
	@Test(priority = 20)
	public void TC41_ANDROID_APP_QA2_UpdateMyAccount_TestCase() throws Exception {
		Logger log = LogManager.getLogger("UpdateMyAccount_TestCase");
		log.info("******TC41_ANDROID_APP_QA2 : Starting to validate user is able to update my Account******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.androidAppClosePopUp();
		components.androidAppValidateLogin();
		components.androidAppValidateMuAccountUpdate();
	}
	@Test(priority = 21)
	public void TC42_ANDROID_APP_QA2_PlaceOrderWithRewards_TestCase() throws Exception {
		Logger log = LogManager.getLogger("PlaceOrderWithRewards_TestCase");
		log.info("******TC42_ANDROID_APP_QA2 : Starting to validate user is able to place order with Rewards******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.androidAppClosePopUp();
		components.androidAppValidateLogin();
		components.androidAppValidateRestaurantSelectionforLoggedInUser(3);
		components.androidAppValidateMenuSelection(2);
		components.androidAppAddRewardItem();
		components.androidAppSelectCarryout();
		components.androidAppSelectPickupAsap();
		components.androidAppVerifyReward();
		components.androidAppContinueToPayment();
		components.androidAppEnterPaymentDetailswithSuggestedTipAndGiftCard(3);
		components.androidAppPlaceOrder();
		components.androidAppvalidateOrderPlace();
	}
	
	@AfterMethod
	public void tearDown() {
		System.out.println("Report URL: " + driver.getCapabilities().getCapability("reportUrl"));
		seetest.applicationClearData("com.brinker.chilis/.features.LaunchActivity");
		driver.quit();
		
	}

}
