package com.testscripts.framework;


import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.configuration.framework.Base;
import com.experitest.appium.SeeTestClient;
import com.pageaction.framework.FunctionalComponents;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class TestChilisIOSBrowser extends Base {
	
	public IOSDriver<IOSElement> IOSdriver = null;
	SeeTestClient seetest;
	String projectPath = System.getProperty("user.dir");

	@BeforeSuite
	public void setPlatform() {
		Properties prop2 = returnProperty(projectPath + "/src/test/java/com/properties/framework/platform.properties");
		editProperties(prop2, "Android_Browser", "false");
		editProperties(prop2, "IOS_Browser", "true");
		editProperties(prop2, "Android_App", "false");
		editProperties(prop2, "IOS_App", "false");
		editProperties(prop2, "WEB", "false");

	}

	@BeforeMethod
	public void initialize() throws Exception {

		Properties pro = returnProperty(projectPath + "/src/test/java/com/properties/framework/data.properties");
		DesiredCapabilities dc = sendIOSBrowserCapabilities();
		IOSdriver = new IOSDriver<IOSElement>(new URL(pro.getProperty("CloudDeviceURL")), dc);
		seetest = new SeeTestClient(IOSdriver);
		getDriver(IOSdriver);
		IOSdriver.rotate(ScreenOrientation.PORTRAIT);
		IOSdriver.get(pro.getProperty("url"));
		IOSdriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		IOSdriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	}

	@Test(priority = 1)
	public void TC01_IOS_BROWSER_LoginLogoutTestCase() throws Exception {
		Logger log = LogManager.getLogger("LoginLogoutTestCase");
		log.info("******TC01_IOS_BROWSER : Starting to Validate Login and Logout functionality of Chilis WebApp******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateLogin();
		components.validateLogout();
	}
	
	@Test(priority = 2)
	public void TC02_IOS_BROWSER_LoggedInUserOrder_TestCase() throws Exception {
		Logger log = LogManager.getLogger("LoggedInUserOrder_TestCase");
		log.info(
				"******TC02_IOS_BROWSER : Starting to validate user is able order placement with existing log in user******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateLogin();
		components.validateRestaurantSelection(3);
		components.validateMenuItemSelection(4);
		components.validateCheckOutPageIOS();
		components.continueToPayment();
		components.enterPaymentDetailsIOS();
		String priceBeforePlacingOrder = components.OrderTotal();
		components.placeOrder();
		components.getSuccessMessageforLoggedInOrder();
		String priceAfterPlacingOrder = components.returnOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}
	
	@Test(priority = 3)
	public void TC03_IOS_BROWSER_CurbsideASAPorder_MCAUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("CurbsideASAPorder_MCAUser_TestCase");
		log.info(
				"******TC03_IOS_BROWSER : Starting to validate user is able to place Curb side ASAP Order for MCA user*******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateLogin();
		components.validateRestaurantSelection(3);
		components.validateChilisFavoriteItemsSelection(4);
		components.validateCheckOutPageIOS();
		components.selectCurbside();
		components.selectPickupAsapIOS();
		components.continueToPayment();
		components.enterPaymentDetailsIOS();
		String priceBeforePlacingOrder = components.OrderTotal();
		components.placeOrder();
		components.getSuccessMessageforLoggedInOrder();
		String priceAfterPlacingOrder = components.returnOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}
	
	@Test(priority = 4)
	public void TC04_IOS_BROWSER_CarryOutLaterTodayOrder_MCAUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("CarryOutLaterTodayOrder_MCAUser_TestCase");
		log.info(
				"******TC04_IOS_BROWSER : Starting to validate user is able to place Carry-out Later Today order for MCA user******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateLogin();
		components.validateRestaurantSelection(3);
		components.validateChilisFavoriteItemsSelection(4);
		components.validateCheckOutPageIOS();
		components.clickOnCarryOutIOS();
		components.selectPickupLaterTodayIOS();
		components.continueToPayment();
		components.enterPaymentDetailsIOS();
		String priceBeforePlacingOrder = components.OrderTotal();
		components.placeOrder();
		components.getSuccessMessageforLoggedInOrder();
		String priceAfterPlacingOrder = components.returnOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}
	
	@Test(priority = 5)
	public void TC05_IOS_BROWSER_CurbSideLaterTodayOrder_MCAUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("CurbSideLaterTodayOrder_MCAUser_TestCase");
		log.info(
				"******TC05_IOS_BROWSER : Starting to validate user is able to place CurbSide Later Today order for MCA user******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateLogin();
		components.validateRestaurantSelection(3);
		components.validateChilisFavoriteItemsSelection(4);
		components.validateCheckOutPageIOS();
		components.selectCurbside();
		components.selectPickupLaterTodayIOS();
		components.continueToPayment();
		components.enterPaymentDetailsIOS();
		String priceBeforePlacingOrder = components.OrderTotal();
		components.placeOrder();
		components.getSuccessMessageforLoggedInOrder();
		String priceAfterPlacingOrder = components.returnOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}
	
	@Test(priority = 6)
	public void TC06_IOS_BROWSER_CarryOutASAPOrder_MCAUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("CarryOutASAPOrder_MCAUser_TestCase");
		log.info(
				"******TC06_IOS_BROWSER : Starting to validate user is able to place Carry Out ASAP order for MCA user******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateLogin();
		components.validateRestaurantSelection(3);
		components.validateChilisFavoriteItemsSelection(5);
		components.validateCheckOutPageIOS();
		components.clickOnCarryOutIOS();
		components.selectPickupAsapIOS();
		components.continueToPayment();
		components.enterPaymentDetailsIOS();
		String priceBeforePlacingOrder = components.OrderTotal();
		components.placeOrder();
		components.getSuccessMessageforLoggedInOrder();
		String priceAfterPlacingOrder = components.returnOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}
	
	@Test(priority = 7)
	public void TC07_IOS_BROWSER_DeliveryASAPOrder_MCAUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("DeliveryASAPOrder_MCAUser_TestCase");
		log.info(
				"******TC07_IOS_BROWSER : Starting to validate user is able to place Delivery ASAP order for MCA User******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateLogin();
		components.validateRestaurantSelection(3);
		components.validateChilisFavoriteItemsSelection(2);
		components.validateCheckOutPageIOS();
		components.enterDeliveryDetails(3);
		components.selectDeliveryDateASAPIOS();
		components.continueToPayment();
		components.enterPaymentDetailsIOS();
		String priceBeforePlacingOrder = components.DeliveryOrderTotal();
		components.placeOrder();
		components.getSuccessMessageforLoggedInOrder();
		String priceAfterPlacingOrder = components.returnDeliveryOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}
	
	@Test(priority = 8)
	public void TC08_IOS_BROWSER_DeliveryLaterTodayOrder_MCAUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("DeliveryLaterTodayOrder_MCAUser_TestCase");
		log.info(
				"******TC08_IOS_BROWSER : Starting to validate user is able to place Delivery-Later Today for MCA user******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateLogin();
		components.validateRestaurantSelection(3);
		components.validateChilisFavoriteItemsSelection(2);
		components.validateCheckOutPageIOS();
		components.enterDeliveryDetails(3);
		components.selectDeliveryLaterTodayIOS();
		components.continueToPayment();
		components.enterPaymentDetailsIOS();
		String priceBeforePlacingOrder = components.DeliveryOrderTotal();
		components.placeOrder();
		components.getSuccessMessageforLoggedInOrder();
		String priceAfterPlacingOrder = components.returnDeliveryOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}
	
	@Test(priority = 9)
	public void TC09_IOS_BROWSER_CurbsideOrder_GuestUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("CurbsideOrder_GuestUser_TestCase");
		log.info(
				"******TC09_IOS_BROWSER : Starting to validate user is able to place curbside order for Guest User******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateRestaurantSelectionforGuestUser(3);
		components.validateMenuItemSelection(3);
		components.validateCheckOutPageIOS();
		components.selectCurbside();
		components.selectPickupAsapIOS();
		components.enterGuestDetailsForCurbsideOrder();
		components.continueToPayment();
		components.enterPaymentDetailsIOS();
		String priceBeforePlacingOrder = components.OrderTotal();
		components.placeOrder();
		System.out.println(components.getSuccessMessageforLoggedInOrder());
		String priceAfterPlacingOrder = components.returnOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}
	
	@Test(priority = 10)
	public void TC10_IOS_BROWSER_DeliveryASAP_GuestUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("DeliveryASAP_GuestUser_TestCase");
		log.info(
				"******TC10_IOS_BROWSER : Starting to validate user is able to place Delivery-ASAP order,for Guest User******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateRestaurantSelectionforGuestUser(3);
		components.validateMenuItemSelection(4);
		components.validateCheckOutPageIOS();
		components.enterDeliveryDetails(3);
		components.selectDeliveryDateASAPIOS();
		components.enterGuestDetails();
		components.continueToPayment();
		components.enterPaymentDetailsIOS();
		String priceBeforePlacingOrder = components.DeliveryOrderTotal();
		components.placeOrder();
		System.out.println(components.getSuccessMessageforLoggedInOrder());
		String priceAfterPlacingOrder = components.returnDeliveryOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}
	
	@Test(priority = 11)
	public void TC11_IOS_BROWSER_deliveryLaterTodayOrder_GuestUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("deliveryLaterTodayOrder_GuestUser_TestCase");
		log.info(
				"******TC11_IOS_BROWSER_Starting to validate user is able to place Delivery-Later Today order, for Guest user******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateRestaurantSelectionforGuestUser(3);
		components.validateMenuItemSelection(5);
		components.validateCheckOutPageIOS();
		components.enterDeliveryDetails(3);
		components.selectDeliveryLaterTodayIOS();
		components.enterGuestDetails();
		components.continueToPayment();
		components.enterPaymentDetailsIOS();
		String priceBeforePlacingOrder = components.DeliveryOrderTotal();
		components.placeOrder();
		System.out.println(components.getSuccessMessageforLoggedInOrder());
		String priceAfterPlacingOrder = components.returnDeliveryOrderPrice();
	    components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}
	
	@Test(priority = 12)
	public void TC12_IOS_BROWSER_PlaceOrderWithRewards_TestCase() throws Exception {
		Logger log = LogManager.getLogger("PlaceOrderWithRewards_TestCase");
		log.info(
				"******TC12_IOS_BROWSER : Starting to validate user is able to  place order with Rewards. For sign in user********");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateLogin();
		components.validateRestaurantSelection(3);
		components.validateMenuItemSelection(6);
		components.validateRewardsSelectionIOS();
		components.clickOnCarryOutIOS();
		components.selectPickupLaterTodayIOS();
		components.isRewardApplied();
		components.continueToPayment();
		components.enterPaymentDetailsIOS();
		String priceBeforePlacingOrder = components.OrderTotal();
		components.placeOrder();
		System.out.println(components.getSuccessMessageforLoggedInOrder());
		String priceAfterPlacingOrder = components.returnOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}
	
	@Test(priority = 13)
	public void TC13_IOS_BROWSER_Reorder_TestCase() throws Exception {
		Logger log = LogManager.getLogger("Reorder_TestCase");
		log.info("******TC13_IOS_BROWSER : Starting to validate user is able to place reorder******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateLogin();
		components.validateRestaurantSelection(3);
		components.validateReorderItemSelection();
		components.validateCheckOutPageIOS();
		components.continueToPayment();
		components.enterPaymentDetailsIOS();
		String priceBeforePlacingOrder = components.OrderTotal();
		components.placeOrder();
		System.out.println(components.getSuccessMessageforLoggedInOrder());
		String priceAfterPlacingOrder = components.returnOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}
	
	@Test(priority = 14)
	public void TC14_IOS_BROWSER_UpdateMyAccount_TestCase() throws Exception {
		Logger log = LogManager.getLogger("UpdateMyAccount_TestCase");
		log.info("******TC14_IOS_BROWSER : Starting to validate user is able to update my account******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateLogin();
		components.validateMyAccountUpdate();
	}
	
	@Test(priority = 15)
	public void TC15_IOS_BROWSER_CurbsideCustomizationOrder_MCAUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("CurbsideCustomizationOrder_MCAUser_TestCase");
		log.info(
				"******TC15_IOS_BROWSER : Starting to validate user is able to place Curbside order item customization for MCA User*******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateLogin();
		components.validateRestaurantSelection(3);
		components.selectCustomizedMenuItemIOS();
		components.validateCheckOutPageIOS();
		components.selectCurbside();
		components.selectPickupAsapIOS();
		components.continueToPayment();
		components.enterPaymentDetailsIOS();
		String priceBeforePlacingOrder = components.OrderTotal();
		components.placeOrder();
		System.out.println(components.getSuccessMessageforLoggedInOrder());
		String priceAfterPlacingOrder = components.returnOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}
	
	@Test(priority = 16)
	public void TC16_IOS_BROWSER_CarryOutFutureCustomizedOrder_GuestUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("CarryOutFutureCustomizedOrder_GuestUser_TestCase");
		log.info(
				"******TC16_IOS_BROWSER : Starting to validate user is able to place Carry Out Future Customized order for guest user******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateRestaurantSelectionforGuestUser(3);
		components.selectCustomizedMenuItemIOS();
		components.validateCheckOutPageIOS();
		components.clickOnCarryOut();
		components.selectPickupForFutureIOS();
		components.enterGuestDetails();
		components.checkRoundOff();
		components.OrderTotal();
		components.placeOrderFuture();
	}
	
	@Test(priority = 17)
	public void TC17_IOS_BROWSER_CarryOutFutureOrder_TestCase() throws Exception {
		Logger log = LogManager.getLogger("CarryOutFutureOrder_TestCase");
		log.info(
				"******TC17_IOS_BROWSER : Starting to validate user is able to place Carryout-future-date-favourite Items order for sign in user********");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateLogin();
		components.validateRestaurantSelection(3);
		components.validateMenuItemSelection(7);
		components.validateCheckOutPageIOS();
		components.clickOnCarryOut();
		components.selectPickupForFutureIOS();
		components.checkRoundOff();
		components.OrderTotal();
		components.placeOrderFuture();
	}
	
	@Test(priority = 18)
	public void TC18_IOS_BROWSER_ValidateRewards_TestCase() throws Exception {
		Logger log = LogManager.getLogger("ValidateRewards_TestCase");
		log.info("******TC18_IOS_BROWSER : Starting to validate Rewards details page for logged in user******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateLogin();
		int noOfRewardsDisplayed = components.noOfRewards();
		int ActualrewardCount = components.ActualRewardsCount();
		Assert.assertEquals(noOfRewardsDisplayed, ActualrewardCount, "Discrepancy observed in Rewards Count");
		components.getRewardTitle();
	}
	
	@Test(priority = 19)
	public void TC19_IOS_BROWSER_ValidateFavouriteItems_TestCase() throws Exception {
		Logger log = LogManager.getLogger("ValidateFavouriteItems_TestCase");
		log.info("******TC19_IOS_BROWSER : Starting to verify Favorites items is displaying on menu page******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateLogin();
		components.getAllChilisFavouriteItems();
	}
	
	@Test(priority = 20)
	public void TC20_IOS_BROWSER_ValidateLocationSearch_GuestUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("ValidateLocationSearch_GuestUser_TestCase");
		log.info("******TC20_IOS_BROWSER : Starting to validate location search fields and links for guest user******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateRestaurantSelectionforGuestUser(3);
	}
	
	@Test(priority = 21)
	public void TC21_IOS_BROWSER_AddMyVisit_TestCase() throws Exception {
		Logger log = LogManager.getLogger("AddMyVisit_TestCase");
		log.info(
				"******TC21_IOS_BROWSER : Starting to verify user is able to submit Add my visit form for login member******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateLogin();
		components.enterVisitDetailsIOS();
		components.retrieveSuccessMessage();
	}
	
	@Test(priority = 22)
	public void TC22_IOS_BROWSER_QA2_LoginLogoutTestCase() throws Exception {
		Logger log = LogManager.getLogger("LoginLogoutTestCase");
		log.info("******TC22_IOS_BROWSER_QA2 : Starting to Validate Login and Logout functionality of Chilis WebApp******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateLogin();
		components.validateLogout();
	}
	
	@Test(priority = 23)
	public void TC23_IOS_BROWSER_QA2_LoggedInUserOrder_TestCase() throws Exception {
		Logger log = LogManager.getLogger("LoggedInUserOrder_TestCase");
		log.info(
				"******TC23_IOS_BROWSER_QA2 : Starting to validate user is able order placement with existing log in user******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateLogin();
		components.validateRestaurantSelection(8);
		components.validateMenuItemSelection(4);
		components.validateCheckOutPageIOS();
		components.continueToPayment();
		components.enterPaymentDetailswithSuggestedTipIOS(8);
		String priceBeforePlacingOrder = components.OrderTotal();
		components.placeOrder();
		components.getSuccessMessageforLoggedInOrder();
		String priceAfterPlacingOrder = components.returnOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}
	
	@Test(priority = 24)
	public void TC24_IOS_BROWSER_QA2_CurbsideASAPorder_MCAUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("CurbsideASAPorder_MCAUser_TestCase");
		log.info(
				"******TC24_IOS_BROWSER_QA2 : Starting to validate user is able to place Curb side ASAP Order for MCA user*******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateLogin();
		components.validateRestaurantSelection(6);
		components.validateChilisFavoriteItemsSelection(4);
		components.validateCheckOutPageIOS();
		components.selectCurbside();
		components.selectPickupAsapIOS();
		components.continueToPayment();
		components.enterPaymentDetailswithCustomTipIOS(6);
		String priceBeforePlacingOrder = components.OrderTotal();
		components.placeOrder();
		components.getSuccessMessageforLoggedInOrder();
		String priceAfterPlacingOrder = components.returnOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}
	
	@Test(priority = 25)
	public void TC25_IOS_BROWSER_QA2_CarryOutLaterTodayOrder_MCAUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("CarryOutLaterTodayOrder_MCAUser_TestCase");
		log.info(
				"******TC25_IOS_BROWSER_QA2 : Starting to validate user is able to place Carry-out Later Today order for MCA user******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateLogin();
		components.validateRestaurantSelection(6);
		components.validateChilisFavoriteItemsSelection(4);
		components.validateCheckOutPageIOS();
		components.clickOnCarryOutIOS();
		components.selectPickupLaterTodayIOS();
		components.continueToPayment();
		components.enterPaymentDetailswithSuggestedTipIOS(6);
		String priceBeforePlacingOrder = components.OrderTotal();
		components.placeOrder();
		components.getSuccessMessageforLoggedInOrder();
		String priceAfterPlacingOrder = components.returnOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}
	
	@Test(priority = 26)
	public void TC26_IOS_BROWSER_QA2_CurbSideLaterTodayOrder_MCAUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("CurbSideLaterTodayOrder_MCAUser_TestCase");
		log.info(
				"******TC26_IOS_BROWSER_QA2 : Starting to validate user is able to place CurbSide Later Today order for MCA user******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateLogin();
		components.validateRestaurantSelection(8);
		components.validateChilisFavoriteItemsSelection(4);
		components.validateCheckOutPageIOS();
		components.selectCurbside();
		components.selectPickupLaterTodayIOS();
		components.continueToPayment();
		components.enterPaymentDetailswithCustomTipIOS(8);
		String priceBeforePlacingOrder = components.OrderTotal();
		components.placeOrder();
		components.getSuccessMessageforLoggedInOrder();
		String priceAfterPlacingOrder = components.returnOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}
	
	@Test(priority = 27)
	public void TC27_IOS_BROWSER_QA2_CarryOutASAPOrder_MCAUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("CarryOutASAPOrder_MCAUser_TestCase");
		log.info(
				"******TC27_IOS_BROWSER_QA2 : Starting to validate user is able to place Carry Out ASAP order for MCA user******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateLogin();
		components.validateRestaurantSelection(6);
		components.validateChilisFavoriteItemsSelection(5);
		components.validateCheckOutPageIOS();
		components.clickOnCarryOutIOS();
		components.selectPickupAsapIOS();
		components.continueToPayment();
		components.enterPaymentDetailswithSuggestedTipIOS(6);
		String priceBeforePlacingOrder = components.OrderTotal();
		components.placeOrder();
		components.getSuccessMessageforLoggedInOrder();
		String priceAfterPlacingOrder = components.returnOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}
	
	@Test(priority = 28)
	public void TC28_IOS_BROWSER_QA2_DeliveryASAPOrder_MCAUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("DeliveryASAPOrder_MCAUser_TestCase");
		log.info(
				"******TC28_IOS_BROWSER_QA2 : Starting to validate user is able to place Delivery ASAP order for MCA User******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateLogin();
		components.validateRestaurantSelection(6);
		components.validateChilisFavoriteItemsSelection(2);
		components.validateCheckOutPageIOS();
		components.enterDeliveryDetailsQA2(6);
		components.selectDeliveryDateASAPIOS();
		components.continueToPayment();
		components.enterPaymentDetailswithCustomTipIOS(6);
		String priceBeforePlacingOrder = components.DeliveryOrderTotal();
		components.placeOrder();
		components.getSuccessMessageforLoggedInOrder();
		String priceAfterPlacingOrder = components.returnDeliveryOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}
	
	@Test(priority = 29)
	public void TC29_IOS_BROWSER_QA2_DeliveryLaterTodayOrder_MCAUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("DeliveryLaterTodayOrder_MCAUser_TestCase");
		log.info(
				"******TC29_IOS_BROWSER_QA2 : Starting to validate user is able to place Delivery-Later Today for MCA user******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateLogin();
		components.validateRestaurantSelection(8);
		components.validateChilisFavoriteItemsSelection(2);
		components.validateCheckOutPageIOS();
		components.enterDeliveryDetailsQA2(8);
		components.selectDeliveryLaterTodayIOS();
		components.continueToPayment();
		components.enterPaymentDetailswithSuggestedTipIOS(8);
		String priceBeforePlacingOrder = components.DeliveryOrderTotal();
		components.placeOrder();
		components.getSuccessMessageforLoggedInOrder();
		String priceAfterPlacingOrder = components.returnDeliveryOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}
	
	@Test(priority = 30)
	public void TC30_IOS_BROWSER_QA2_CurbsideOrder_GuestUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("CurbsideOrder_GuestUser_TestCase");
		log.info(
				"******TC30_IOS_BROWSER_QA2 : Starting to validate user is able to place curbside order for Guest User******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateRestaurantSelectionforGuestUser(6);
		components.validateMenuItemSelection(3);
		components.validateCheckOutPageIOS();
		components.selectCurbside();
		components.selectPickupAsapIOS();
		components.enterGuestDetailsForCurbsideOrder();
		components.continueToPayment();
		components.enterPaymentDetailswithCustomTipIOS(6);
		String priceBeforePlacingOrder = components.OrderTotal();
		components.placeOrder();
		System.out.println(components.getSuccessMessageforLoggedInOrder());
		String priceAfterPlacingOrder = components.returnOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}
	
	@Test(priority = 31)
	public void TC31_IOS_BROWSER_QA2_DeliveryASAP_GuestUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("DeliveryASAP_GuestUser_TestCase");
		log.info(
				"******TC31_IOS_BROWSER_QA2 : Starting to validate user is able to place Delivery-ASAP order,for Guest User******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateRestaurantSelectionforGuestUser(8);
		components.validateMenuItemSelection(4);
		components.validateCheckOutPageIOS();
		components.enterDeliveryDetailsQA2(8);
		components.selectDeliveryDateASAPIOS();
		components.enterGuestDetails();
		components.continueToPayment();
		components.enterPaymentDetailswithSuggestedTipIOS(8);
		String priceBeforePlacingOrder = components.DeliveryOrderTotal();
		components.placeOrder();
		System.out.println(components.getSuccessMessageforLoggedInOrder());
		String priceAfterPlacingOrder = components.returnDeliveryOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}
	
	@Test(priority = 32)
	public void TC32_IOS_BROWSER_QA2_deliveryLaterTodayOrder_GuestUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("deliveryLaterTodayOrder_GuestUser_TestCase");
		log.info(
				"******TC32_IOS_BROWSER_QA2_Starting to validate user is able to place Delivery-Later Today order, for Guest user******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateRestaurantSelectionforGuestUser(6);
		components.validateMenuItemSelection(5);
		components.validateCheckOutPageIOS();
		components.enterDeliveryDetailsQA2(6);
		components.selectDeliveryLaterTodayIOS();
		components.enterGuestDetails();
		components.continueToPayment();
		components.enterPaymentDetailswithCustomTipIOS(6);
		String priceBeforePlacingOrder = components.DeliveryOrderTotal();
		components.placeOrder();
		System.out.println(components.getSuccessMessageforLoggedInOrder());
		String priceAfterPlacingOrder = components.returnDeliveryOrderPrice();
	    components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}
	
	@Test(priority = 33)
	public void TC33_IOS_BROWSER_QA2_PlaceOrderWithRewards_TestCase() throws Exception {
		Logger log = LogManager.getLogger("PlaceOrderWithRewards_TestCase");
		log.info(
				"******TC33_IOS_BROWSER_QA2 : Starting to validate user is able to  place order with Rewards. For sign in user********");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateLogin();
		components.validateRestaurantSelection(8);
		components.validateMenuItemSelection(6);
		components.validateRewardsSelectionIOS();
		components.clickOnCarryOutIOS();
		components.selectPickupLaterTodayIOS();
		components.isRewardApplied();
		components.continueToPayment();
		components.enterPaymentDetailswithSuggestedTipIOS(8);
		String priceBeforePlacingOrder = components.OrderTotal();
		components.placeOrder();
		System.out.println(components.getSuccessMessageforLoggedInOrder());
		String priceAfterPlacingOrder = components.returnOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}
	
	@Test(priority = 34)
	public void TC34_IOS_BROWSER_QA2_Reorder_TestCase() throws Exception {
		Logger log = LogManager.getLogger("Reorder_TestCase");
		log.info("******TC34_IOS_BROWSER_QA2 : Starting to validate user is able to place reorder******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateLogin();
		components.validateRestaurantSelection(6);
		components.validateReorderItemSelection();
		components.validateCheckOutPageIOS();
		components.continueToPayment();
		components.enterPaymentDetailswithCustomTipIOS(6);
		String priceBeforePlacingOrder = components.OrderTotal();
		components.placeOrder();
		System.out.println(components.getSuccessMessageforLoggedInOrder());
		String priceAfterPlacingOrder = components.returnOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}
	
	@Test(priority = 35)
	public void TC35_IOS_BROWSER_QA2_UpdateMyAccount_TestCase() throws Exception {
		Logger log = LogManager.getLogger("UpdateMyAccount_TestCase");
		log.info("******TC35_IOS_BROWSER_QA2 : Starting to validate user is able to update my account******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateLogin();
		components.validateMyAccountUpdate();
	}
	
	@Test(priority = 36)
	public void TC36_IOS_BROWSER_QA2_CurbsideCustomizationOrder_MCAUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("CurbsideCustomizationOrder_MCAUser_TestCase");
		log.info(
				"******TC36_IOS_BROWSER_QA2 : Starting to validate user is able to place Curbside order item customization for MCA User*******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateLogin();
		components.validateRestaurantSelection(8);
		components.selectCustomizedMenuItemIOS();
		components.validateCheckOutPageIOS();
		components.selectCurbside();
		components.selectPickupAsapIOS();
		components.continueToPayment();
		components.enterPaymentDetailswithSuggestedTipIOS(8);
		String priceBeforePlacingOrder = components.OrderTotal();
		components.placeOrder();
		System.out.println(components.getSuccessMessageforLoggedInOrder());
		String priceAfterPlacingOrder = components.returnOrderPrice();
		components.comparePrice(priceBeforePlacingOrder, priceAfterPlacingOrder);
	}
	
	@Test(priority = 37)
	public void TC37_IOS_BROWSER_QA2_CarryOutFutureCustomizedOrder_GuestUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("CarryOutFutureCustomizedOrder_GuestUser_TestCase");
		log.info(
				"******TC37_IOS_BROWSER_QA2 : Starting to validate user is able to place Carry Out Future Customized order for guest user******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateRestaurantSelectionforGuestUser(6);
		components.selectCustomizedMenuItemIOS();
		components.validateCheckOutPageIOS();
		components.clickOnCarryOut();
		components.selectPickupForFutureIOS();
		components.enterGuestDetails();
		components.checkRoundOff();
		components.OrderTotal();
		components.placeOrderFuture();
	}
	
	@Test(priority = 38)
	public void TC38_IOS_BROWSER_QA2_CarryOutFutureOrder_TestCase() throws Exception {
		Logger log = LogManager.getLogger("CarryOutFutureOrder_TestCase");
		log.info(
				"******TC38_IOS_BROWSER_QA2 : Starting to validate user is able to place Carryout-future-date-favourite Items order for sign in user********");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateLogin();
		components.validateRestaurantSelection(8);
		components.validateMenuItemSelection(7);
		components.validateCheckOutPageIOS();
		components.clickOnCarryOut();
		components.selectPickupForFutureIOS();
		components.checkRoundOff();
		components.OrderTotal();
		components.placeOrderFuture();
	}
	
	@Test(priority = 39)
	public void TC39_IOS_BROWSER_QA2_ValidateRewards_TestCase() throws Exception {
		Logger log = LogManager.getLogger("ValidateRewards_TestCase");
		log.info("******TC39_IOS_BROWSER_QA2 : Starting to validate Rewards details page for logged in user******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateLogin();
		int noOfRewardsDisplayed = components.noOfRewards();
		int ActualrewardCount = components.ActualRewardsCount();
		Assert.assertEquals(noOfRewardsDisplayed, ActualrewardCount, "Discrepancy observed in Rewards Count");
		components.getRewardTitle();
	}
	
	@Test(priority = 40)
	public void TC40_IOS_BROWSER_QA2_ValidateFavouriteItems_TestCase() throws Exception {
		Logger log = LogManager.getLogger("ValidateFavouriteItems_TestCase");
		log.info("******TC40_IOS_BROWSER_QA2 : Starting to verify Favorites items is displaying on menu page******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateLogin();
		components.getAllChilisFavouriteItems();
	}
	
	@Test(priority = 41)
	public void TC41_IOS_BROWSER_QA2_ValidateLocationSearch_GuestUser_TestCase() throws Exception {
		Logger log = LogManager.getLogger("ValidateLocationSearch_GuestUser_TestCase");
		log.info("******TC41_IOS_BROWSER_QA2 : Starting to validate location search fields and links for guest user******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateRestaurantSelectionforGuestUser(3);
	}
	
	@Test(priority = 42)
	public void TC42_IOS_BROWSER_QA2_AddMyVisit_TestCase() throws Exception {
		Logger log = LogManager.getLogger("AddMyVisit_TestCase");
		log.info(
				"******TC42_IOS_BROWSER_QA2 : Starting to verify user is able to submit Add my visit form for login member******");
		FunctionalComponents components = new FunctionalComponents(IOSdriver, log);
		components.validateQASiteIOS();
		components.validateLogin();
		components.enterVisitDetailsIOS();
		components.retrieveSuccessMessage();
	}
	
	@AfterMethod
	public void tearDown() {
		System.out.println("Report URL: " + IOSdriver.getCapabilities().getCapability("reportUrl"));
		IOSdriver.quit();
	}

	


	
}
