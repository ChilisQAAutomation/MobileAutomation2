package com.testscripts.framework;

import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

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
import com.listener.framework.Listeners;
import com.pageaction.framework.FunctionalComponents;
import com.retryconfig.framework.RetryAnalyzer;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;


//*****Chilis Gift Card Test Cases for Android Browser******//
public class TestChilisAndroidBrowser_QA3 extends Base {
	
	public AndroidDriver<AndroidElement> driver = null;
	SeeTestClient seetest;
	RetryAnalyzer retry = new RetryAnalyzer();

	@BeforeSuite
	public void setPlatform() {
		Properties prop2 = returnProperty(PROJECT_PATH + "/src/test/java/com/properties/framework/platform.properties");
		editProperties(prop2, "Android_Browser", "true");
		editProperties(prop2, "IOS_Browser", "false");
		editProperties(prop2, "Android_App", "false");
		editProperties(prop2, "IOS_App", "false");
		editProperties(prop2, "WEB", "false");

	}

	@BeforeMethod
	public void initialize() throws Exception {

		Properties pro = returnProperty(PROJECT_PATH + "/src/test/java/com/properties/framework/data.properties");
		DesiredCapabilities dc = sendAndroidBrowserCapabilities();
		driver = new AndroidDriver<>(new URL(pro.getProperty("CloudDeviceURL")), dc);
		seetest = new SeeTestClient(driver);
		Listeners listen = new Listeners();
		listen.setClient(driver);
		seetest.hybridClearCache();
		getDriver(driver);
		driver.rotate(ScreenOrientation.PORTRAIT);
		driver.get(pro.getProperty("url"));
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
	
	}
	
	
	@Test(priority = 1)
	public void TC22_ANDROID_BROWSER_QA2_LoginLogout_TestCase() throws Exception {
		Logger log = LogManager.getLogger("LoginLogout_TestCase");
		log.info(
				"******TC22_ANDROID_BROWSER_QA2 : Starting to Validate Login and Logout functionality of Chilis WebApp******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.validateQASite();
		components.validateLogin();
		components.validateLogout();
	}

	@Test(priority = 2)
	public void TC23_ANDROID_BROWSER_QA2_LoggedInUserOrder_TestCase() throws Exception {
		Logger log = LogManager.getLogger("LoggedInUserOrder_TestCase");
		log.info(
				"******TC23_ANDROID_BROWSER_QA2 : Starting to validate user is able order placement with existing log in user******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.validateQASite();
		components.validateLogin();
		components.validateRestaurantSelection_QA3(6);
		components.validateChilisFavoriteItemsSelection(2);
		components.validateCheckOutPage();
		components.continueToPayment();
		components.enterPaymentDetailswithSuggestedTipWithGiftCard(6);
		String priceBeforePlacingOrder = components.OrderTotal();
		components.placeOrder();
		components.getSuccessMessageforLoggedInOrder();
		String priceAfterPlacingOrder = components.returnOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}

	@Test(priority = 3)
	public void TC24_ANDROID_BROWSER_QA2_CurbsideASAPorder_MCAUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("CurbsideASAPorder_MCAUser_TestCase");
		log.info(
				"******TC24_ANDROID_BROWSER_QA2 : Starting to validate user is able to place Curb side ASAP Order for MCA user*******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.validateQASite();
		components.validateLogin();
		components.validateRestaurantSelection_QA3(7);
		components.validateChilisFavoriteItemsSelection(3);
		components.validateCheckOutPage();
		components.selectCurbside();
		components.selectPickupAsap();
		components.continueToPayment();
		components.enterPaymentDetailswithCustomTipWithGiftCard(7);
		String priceBeforePlacingOrder = components.OrderTotal();
		components.placeOrder();
		components.getSuccessMessageforLoggedInOrder();
		String priceAfterPlacingOrder = components.returnOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}

	@Test(priority = 4)
	public void TC25_ANDROID_BROWSER_QA2_CarryOutLaterTodayOrder_MCAUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("CarryOutLaterTodayOrder_MCAUser_TestCase");
		log.info(
				"******TC25_ANDROID_BROWSER_QA2 : Starting to validate user is able to place Carry-out Later Today order for MCA user******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.validateQASite();
		components.validateLogin();
		components.validateRestaurantSelection_QA3(6);
		components.validateChilisFavoriteItemsSelection(4);
		components.validateCheckOutPage();
		components.clickOnCarryOut();
		components.selectPickupLaterToday();
		components.continueToPayment();
		components.enterPaymentDetailswithSuggestedTipWithGiftCard(6);
		String priceBeforePlacingOrder = components.OrderTotal();
		components.placeOrder();
		components.getSuccessMessageforLoggedInOrder();
		String priceAfterPlacingOrder = components.returnOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
		}

	@Test(priority = 5)
	public void TC26_ANDROID_BROWSER_QA2_CurbSideLaterTodayOrder_MCAUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("CurbSideLaterTodayOrder_MCAUser_TestCase");
		log.info(
				"******TC26_ANDROID_BROWSER_QA2 : Starting to validate user is able to place CurbSide Later Today order for MCA user******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.validateQASite();
		components.validateLogin();
		components.validateRestaurantSelection_QA3(7);
		components.validateChilisFavoriteItemsSelection(5);
		components.validateCheckOutPage();
		components.selectCurbside();
		components.selectPickupLaterToday();
		components.continueToPayment();
		components.enterPaymentDetailswithCustomTipWithGiftCard(7);
		String priceBeforePlacingOrder = components.OrderTotal();
		components.placeOrder();
		components.getSuccessMessageforLoggedInOrder();
		String priceAfterPlacingOrder = components.returnOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);

	}

	@Test(priority = 6)
	public void TC27_ANDROID_BROWSER_QA2_CarryOutASAPOrder_MCAUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("CarryOutASAPOrder_MCAUser_TestCase");
		log.info(
				"******TC27_ANDROID_BROWSER_QA2 : Starting to validate user is able to place Carry Out ASAP order for MCA user******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.validateQASite();
		components.validateLogin();
		components.validateRestaurantSelection_QA3(5);
		components.validateChilisFavoriteItemsSelection(6);
		components.validateCheckOutPage();
		components.clickOnCarryOut();
		components.selectPickupAsap();
		components.continueToPayment();
		components.enterPaymentDetailswithSuggestedTipWithGiftCard(5);
		String priceBeforePlacingOrder = components.OrderTotal();
		components.placeOrder();
		components.getSuccessMessageforLoggedInOrder();
		String priceAfterPlacingOrder = components.returnOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}

	@Test(priority = 7)
	public void TC28_ANDROID_BROWSER_QA2_DeliveryASAPOrder_MCAUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("DeliveryASAPOrder_MCAUser_TestCase");
		log.info(
				"******TC28_ANDROID_BROWSER_QA2 : Starting to validate user is able to place Delivery ASAP order for MCA User******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.validateQASite();
		components.validateLogin();
		components.validateRestaurantSelection_QA3(6);
		components.validateMenuItemSelection(5);
		components.validateCheckOutPage();
		components.enterDeliveryDetailsQA2(6);
		components.selectDeliveryDateASAP();
		components.continueToPayment();
		components.enterPaymentDetailswithCustomTipWithGiftCard(6);
		String priceBeforePlacingOrder = components.DeliveryOrderTotal();
		components.placeOrder();
		components.getSuccessMessageforLoggedInOrder();
		String priceAfterPlacingOrder = components.returnDeliveryOrderPrice();
	    components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);

	}

	@Test(priority = 8)
	public void TC29_ANDROID_BROWSER_QA2_DeliveryLaterTodayOrder_MCAUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("DeliveryLaterTodayOrder_MCAUser_TestCase");
		log.info(
				"******TC29_ANDROID_BROWSER_QA2 : Starting to validate user is able to place Delivery-Later Today for MCA user******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.validateQASite();
		components.validateLogin();
		components.validateRestaurantSelection_QA3(4);
		components.validateMenuItemSelection(6);
		components.validateCheckOutPage();
		components.enterDeliveryDetailsQA2(4);
		components.selectDeliveryLaterToday();
		components.continueToPayment();
		components.enterPaymentDetailswithSuggestedTipWithGiftCard(4);
		String priceBeforePlacingOrder = components.DeliveryOrderTotal();
		components.placeOrder();
		components.getSuccessMessageforLoggedInOrder();
		String priceAfterPlacingOrder = components.returnDeliveryOrderPrice();
//		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}

	@Test(priority = 9)
	public void TC30_ANDROID_BROWSER_QA2_PlaceOrderWithRewards_TestCase() throws Exception {
		Logger log = LogManager.getLogger("PlaceOrderWithRewards_TestCase");
		log.info(
				"******TC30_ANDROID_BROWSER_QA2 : Starting to validate user is able to  place order with Rewards. For sign in user********");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.validateQASite();
		components.validateLogin();
		components.validateRestaurantSelection_QA3(3);
		components.validateMenuItemSelection(7);
		components.validateRewardsSelection();
		components.clickOnCarryOut();
		components.selectPickupLaterToday();
		components.isRewardApplied();
		components.continueToPayment();
		components.enterPaymentDetailswithCustomTipWithGiftCard(3);
		String priceBeforePlacingOrder = components.OrderTotal();
		components.placeOrder();
		System.out.println(components.getSuccessMessageforLoggedInOrder());
		String priceAfterPlacingOrder = components.returnOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}

	@Test(priority = 10)
	public void TC31_ANDROID_BROWSER_QA2_Reorder_TestCase() throws Exception {
		Logger log = LogManager.getLogger("Reorder_TestCase");
		log.info("******TC31_ANDROID_BROWSER_QA2 : Starting to validate user is able to place reorder******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.validateQASite();
		components.validateLogin();
		components.validateRestaurantSelection_QA3(6);
		components.validateReorderItemSelection();
		components.validateCheckOutPage();
		components.continueToPayment();
		components.enterPaymentDetailswithSuggestedTipWithGiftCard(6);
		String priceBeforePlacingOrder = components.OrderTotal();
		components.placeOrder();
		System.out.println(components.getSuccessMessageforLoggedInOrder());
		String priceAfterPlacingOrder = components.returnOrderPrice();
	}

	@Test(priority = 11)
	public void TC32_ANDROID_BROWSER_QA2_CarryOutFutureOrder_TestCase() throws Exception {
		Logger log = LogManager.getLogger("CarryOutFutureOrder_TestCase");
		log.info(
				"******TC32_ANDROID_BROWSER_QA2 : Starting to validate user is able to place Carryout-future-date-favourite Items order for sign in user********");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.validateQASite();
		components.validateLogin();
		components.validateRestaurantSelection_QA3(5);
		components.validateMenuItemSelection(8);
		components.validateCheckOutPage();
		components.clickOnCarryOut();
		components.selectPickupForFuture();
		components.checkRoundOffforFutureOrder();
		components.OrderTotal();
		components.placeOrderFuture();
	}

	@Test(priority = 12)
	public void TC33_ANDROID_BROWSER_QA2_CurbsideOrder_GuestUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("CurbsideOrder_GuestUser_TestCase");
		log.info(
				"******TC33_ANDROID_BROWSER_QA2 : Starting to validate user is able to place curbside order for Guest User******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.validateQASite();
		components.validateRestaurantSelectionforGuestUser_QA3(3);
		components.validateMenuItemSelection(4);
		components.validateCheckOutPage();
		components.selectCurbside();
		components.selectPickupLaterToday();
		components.enterGuestDetailsForCurbsideOrder();
		components.continueToPayment();
		components.enterPaymentDetailswithCustomTipWithGiftCard(3);
		String priceBeforePlacingOrder = components.OrderTotal();
		components.placeOrder();
		System.out.println(components.getSuccessMessageforLoggedInOrder());
		String priceAfterPlacingOrder = components.returnOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}

	@Test(priority = 13)
	public void TC34_ANDROID_BROWSER_QA2_DeliveryASAP_GuestUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("DeliveryASAP_GuestUser_TestCase");
		log.info(
				"******TC34_ANDROID_BROWSER_QA2 : Starting to validate user is able to place Delivery-ASAP order,for Guest User******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.validateQASite();
		components.validateRestaurantSelectionforGuestUser_QA3(3);
		components.validateMenuItemSelection(2);
		components.validateCheckOutPage();
		components.enterDeliveryDetailsQA2(3);
		components.selectDeliveryDateASAP();
		components.enterGuestDetails();
		components.continueToPayment();
		components.enterPaymentDetailswithSuggestedTipWithGiftCard(3);
		String priceBeforePlacingOrder = components.DeliveryOrderTotal();
		components.placeOrder();
		System.out.println(components.getSuccessMessageforLoggedInOrder());
		String priceAfterPlacingOrder = components.returnDeliveryOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}

	@Test(priority = 14)
	public void TC35_ANDROID_BROWSER_QA2_deliveryLaterTodayOrder_GuestUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("deliveryLaterTodayOrder_GuestUser_TestCase");
		log.info(
				"******TC35_ANDROID_BROWSER_QA2 : Starting to validate user is able to place Delivery-Later Today order, for Guest user******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.validateQASite();
		components.validateRestaurantSelectionforGuestUser_QA3(3);
		components.validateMenuItemSelection(3);		
		components.validateCheckOutPage();
		components.enterDeliveryDetailsQA2(3);
		components.selectDeliveryLaterToday();
		components.enterGuestDetails();
		components.continueToPayment();
		components.enterPaymentDetailswithCustomTipWithGiftCard(3);
		String priceBeforePlacingOrder = components.DeliveryOrderTotal();
		components.placeOrder();
		System.out.println(components.getSuccessMessageforLoggedInOrder());
		String priceAfterPlacingOrder = components.returnDeliveryOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}

	@Test(priority = 15)
	public void TC36_ANDROID_BROWSER_QA2_RewardsValidation_loggedInUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("RewardsValidation_loggedInUser_TestCase");
		log.info("******TC36_ANDROID_BROWSER_QA2 : Starting to validate Rewards details page for logged in user******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.validateQASite();
		components.validateLogin();
		int noOfRewardsDisplayed = components.noOfRewards();
		int ActualrewardCount = components.ActualRewardsCount();
		components.compareRewardCount(noOfRewardsDisplayed, ActualrewardCount);
		components.getRewardTitle();
	}

	@Test(priority = 16)
	public void TC37_ANDROID_BROWSER_QA2_ValidateLocationSearch_TestCase() throws Exception {
		Logger log = LogManager.getLogger("ValidateLocationSearch_TestCase");
		log.info(
				"******TC37_ANDROID_BROWSER_QA2 : Starting to validate location search fields and links for guest user******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.validateQASite();
		components.validateRestaurantSelectionforGuestUser_QA3(3);
	}

	@Test(priority = 17)
	public void TC38_ANDROID_BROWSER_QA2_ValidateFavouriteItems_TestCase() throws Exception {
		Logger log = LogManager.getLogger("ValidateFavouriteItems_TestCase");
		log.info("******TC38_ANDROID_BROWSER_QA2 : Starting to verify Favorites items is displaying on menu page******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.validateQASite();
		components.validateLogin();
		components.getAllChilisFavouriteItems();
	}

	@Test(priority = 18)
	public void TC39_ANDROID_BROWSER_QA2_UpdateMyAccount_TestCase() throws Exception {
		Logger log = LogManager.getLogger("UpdateMyAccount_TestCase");
		log.info("******TC39_ANDROID_BROWSER_QA2 : Starting to validate user is able to update my account******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.validateQASite();
		components.validateLogin();
		components.validateMyAccountUpdate();
	}

	@Test(priority = 19)
	public void TC40_ANDROID_BROWSER_QA2_CurbsideCustomizationOrder_MCAUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("CurbsideCustomizationOrder_MCAUser_TestCase");
		log.info(
				"******TC40_ANDROID_BROWSER_QA2 : Starting to validate user is able to place Curbside order item customization for MCA User*******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.validateQASite();
		components.validateLogin();
		components.validateRestaurantSelection_QA3(3);
		components.selectCustomizedMenuItem();
		components.validateCheckOutPage();
		components.selectCurbside();
		components.selectPickupAsap();
		components.continueToPayment();
		components.enterPaymentDetailswithSuggestedTipWithGiftCard(3);
		String priceBeforePlacingOrder = components.OrderTotal();
		components.placeOrder();
		System.out.println(components.getSuccessMessageforLoggedInOrder());
		String priceAfterPlacingOrder = components.returnOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}

	@Test(priority = 20)
	public void TC41_ANDROID_BROWSER_QA2_CarryOutFutureCustomizedOrder_GuestUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("CarryOutFutureCustomizedOrder_GuestUser_TestCase");
		log.info(
				"******TC41_ANDROID_BROWSER_QA2 : Starting to validate user is able to place Carry Out Future Customized order for guest user******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.validateQASite();
		components.validateRestaurantSelectionforGuestUser(3);
		components.selectCustomizedMenuItem();
		components.validateCheckOutPage();
		components.clickOnCarryOut();
		components.selectPickupForFuture();
		components.enterGuestDetails();
		components.checkRoundOff();
		components.OrderTotal();
		components.placeOrderFuture();
	}

	@Test(priority = 21)
	public void TC42_ANDROID_BROWSER_QA2_AddMyVisit_TestCase() throws Exception {
		Logger log = LogManager.getLogger("AddMyVisit_TestCase");
		log.info(
				"******TC42_ANDROID_BROWSER_QA2 : Starting to verify user is able to submit Add my visit form for login member******");
		FunctionalComponents components = new FunctionalComponents(driver, log);
		components.validateQASite();
		components.validateLogin();
		components.enterVisitDetails();
		components.retrieveSuccessMessage();
	}

	@AfterMethod
	public void tearDown() {
		System.out.println("Report URL: " + driver.getCapabilities().getCapability("reportUrl"));
		driver.quit();
	}
	
	
	
	
	

}
