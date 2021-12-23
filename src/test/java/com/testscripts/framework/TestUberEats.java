package com.testscripts.framework;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.configuration.framework.Base;
import com.pageaction.framework.FunctionalComponents;

public class TestUberEats extends Base {
	
	public RemoteWebDriver driver = null;
	
	
	@BeforeSuite
	public void setPlatform()
	{
		Properties prop2 = returnProperty(PROJECT_PATH+"/src/test/java/com/properties/framework/platform.properties");
		editProperties(prop2, "Android_Browser", "false");
		editProperties(prop2, "IOS_Browser", "false");
		editProperties(prop2, "Android_App", "false");
		editProperties(prop2, "IOS_App", "false");
		editProperties(prop2,"WEB","true");
		
	}
	@BeforeMethod
	public void initialize() throws Exception {
		driver = initializeWebDriver();
		getDriver(driver);
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	}
	
	@Test (priority=1)									
	public void TC01_UberEats_ValidateIsStoreEnabled() {
		
		Logger log = LogManager.getLogger("TestValidateOpenStore");
		log.info("******TC01_UE_CHROME : Starting to Validate If  store-6 is opened in uber eats******");
		FunctionalComponents func = new FunctionalComponents(driver, log);
		func.uberEats_Login();
		func.checkIfStoreOpen();
		
	}  	
	
	
	@Test(priority=2)
		public void TC02_UberEats_ValidateStoreHomepage() {
			
			Logger log = LogManager.getLogger("TestValidateStoreHomepage");
			log.info("******TC02_UE_CHROME : Starting to Validate If  store-6 homepage can be opened in uber eats******");
			FunctionalComponents func = new FunctionalComponents(driver, log);
			func.uberEats_Login();
			func.openStore();
			
		}
	
	@Test(priority=3)					
	public void TC03_UberEats_HomePage_UI() {
		Logger log = LogManager.getLogger("TestValidateSoreHomePageUI");
		log.info("******TC03_UE_CHROME : Starting to Validate If  store-6 Home Page UI working in uber eats******");
		FunctionalComponents func = new FunctionalComponents(driver, log);
		func.uberEats_Login();
		func.clickOnStore();
		func.verifyStoreHomePageUI();
		 
		
	} 
		
		@Test(priority=4)
		public void TC04_UberEats_ValidateMenuCategory() {
			
			Logger log = LogManager.getLogger("TestValidateMenuCategory");
			log.info("******TC04_UE_CHROME : Starting to Validate If  all the menu categories are present in uber eats******");
			FunctionalComponents func = new FunctionalComponents(driver, log);
			func.uberEats_Login();
			func.openStore();
			func.validateMenuCategories();
		}
		
		
		@Test(priority=5)
		public void TC05_UberEats_ValidateMenuItemDescription() {
			
			Logger log = LogManager.getLogger("TestValidateMenuItemNameDescr");
			log.info("******TC05_UE_CHROME : Starting to Validate If  Description is displayed for all the menu items present in uber eats******");
			FunctionalComponents func = new FunctionalComponents(driver, log);
			func.uberEats_Login();
			func.openStore();
			func.validateMenuDescriptionForUE();
		}
		

			@Test(priority=6)
		public void TC06_UberEats_ValidateMenuPrice() {
			
			Logger log = LogManager.getLogger("TestValidateMenuPrice");
			log.info("******TC06_UE_CHROME : Starting to Validate If Price is displayed for all the menu categories in uber eats******");
			FunctionalComponents func = new FunctionalComponents(driver, log);
			func.uberEats_Login();
			func.openStore();
			func.validateMenuPriceForUE();
		
		} 
	
		
		@Test(priority=7)
		public void TC09_UberEats_ValidateMenuItemIncreaseDecrease() {
			
			Logger log = LogManager.getLogger("TestValidateMenuItemNameDescr");
			log.info("******TC09_UE_CHROME : Starting to Validate If the number of item can be increased or decreased before adding to orde in uber eats******");
			FunctionalComponents func = new FunctionalComponents(driver, log);
			func.uberEats_Login();
			func.openStore();
			func.selectMenuItem(2);
			func.itemIncreaseCheck();
			func.itemDecreaseCheck();
		} 
		
		
		@Test(priority=8)					
		public void TC08_UberEats_Verify_AddtoCart() {
			
			Logger log = LogManager.getLogger("TestValidateAddToOrder");
			log.info("******TC08_UE_CHROME : Starting to Validate Add to Order in uber eats******");
			FunctionalComponents func = new FunctionalComponents(driver, log);
			func.uberEats_Login();
			func.clickOnStore();
			func.clickMenuItem(5);
			func.clickAddToOrder();
			func.clickOnViewCart();
			func.verifyOrderItem(5);
			
		} 
		
		
		@Test(priority=9)					
		public void TC49_UberEats_changeQuantityAtCheckoutPage() {
				
			Logger log = LogManager.getLogger("TestValidateChangeQuantity");
			log.info("******TC49_UE_CHROME : Starting to Validate Changing Quantity in Checkout page in uber eats******");
			FunctionalComponents func = new FunctionalComponents(driver, log);
			func.uberEats_Login();
			func.clickOnStore();
			func.clickMenuItem(6);
			func.clickAddToOrder();
			func.clickOnViewCart();
			func.verifyOrderItem(6);
			func.changeItemQuantity();
				
			}  
		
		@Test(priority=10)
		public void TC50_UberEats_RemoveQuantityAtCheckoutPage() {
			
			Logger log = LogManager.getLogger("TestValidateChangeQuantity");
			log.info("******TC50_UE_CHROME : Starting to Validate Removing Item in Checkout page in uber eats******");
			FunctionalComponents func = new FunctionalComponents(driver, log);
			func.uberEats_Login();
			func.clickOnStore();
			func.clickMenuItem(6);
			func.clickAddToOrder();
			func.clickOnViewCart();
			func.verifyOrderItem(6);
			func.RemoveQuantity();
				
			} 
		
		
		
		@Test(priority=11)
		public void TC10_UberEats_Verify_OrderModes() {
			
			Logger log = LogManager.getLogger("TestOrderModesButton");
			log.info("******TC10_UE_CHROME : Starting to Validate delivery and schedule button in uber eats******");
			FunctionalComponents func = new FunctionalComponents(driver, log);
			func.uberEats_Login();
			func.clickOnStore();
			func.clickMenuItem(7);
			func.clickCheckoutItem();
			func.clickScheduleOption();
			func.verifyDeliveryButton();
			func.verifyScheduleButton();
			
		} 
		
		
		@Test(priority=12)
		public void TC31_UberEats_ValidateDifferentTimezoneOrder() {
		Logger log = LogManager.getLogger("TestValidatePlacedOrderTimezone");
		log.info("******TC31_UE_CHROME : Starting to validate If placed order timezone is same with selected timezone in uber eats******");
		FunctionalComponents func = new FunctionalComponents(driver, log);
		func.uberEats_Login();
		func.clickOnStore();
		func.clickMenuItem(8);
		func.clickCheckoutItem();
		func.selectScheduleTime();
		func.clickPlaceOrderSL();
		func.clickOnViewOrders();
		func.verifyPlacedOrderTimezone();
		 }  
		
		@Test(priority=13)
		public void TC23_UberEats_ValidateOrderSLDelivery() {
			
			Logger log = LogManager.getLogger("TestValidateOrderSLPickup");
			log.info("******TC23_UE_CHROME : Starting to Validate If user is able to place an order schedule-later Delivery in uber eats******");
			FunctionalComponents func = new FunctionalComponents(driver, log);
			func.uberEats_Login();
			func.openStore();
			func.selectMenuItem(3);
			func.clickCheckoutItem();
			func.selectScheduleTime();
			func.clickPlaceOrderSL();
			
		} 
		
		@Test(priority=14)
		public void TC22_UberEats_ValidateOrderASAPDelivery() {
			
			Logger log = LogManager.getLogger("TestValidateOrderASAPPickup");
			log.info("******TC22_UE_CHROME : Starting to Validate If user is able to place an order asap delivery in uber eats******");
			FunctionalComponents func = new FunctionalComponents(driver, log);
			func.uberEats_Login();
			func.openStore();
			func.selectMenuItem(4);
			func.clickCheckoutItem();
			func.clickPlaceOrderASAP();
			
		}
		
			@Test(priority=15)
			public void TC35_UberEats_ValidateOrders() {
				
				Logger log = LogManager.getLogger("TestValidateOrders");
				log.info("******TC35_UE_CHROME : Starting to Validate If user is able to see all orders in uber eats******");
				FunctionalComponents func = new FunctionalComponents(driver, log);
				func.uberEats_Login();
				func.openUrlUberEatsOrders();
				func.clickShowMore();
				func.countTotalOrders();
				
			} 
			

		
		@Test(priority=16)
		public void TC36_UberEats_Verify_PastOrderItemAndPrice() {
		Logger log = LogManager.getLogger("TestValidatePlacedOrderItemAndPrice");
		log.info("******TC36_UE_CHROME : Starting to validate  past order menu-item name & Price in uber eats******");
		FunctionalComponents func = new FunctionalComponents(driver, log);
		func.uberEats_Login();
		func.openUrlUberEatsOrders();
		func.verifyPastOrderMenuItemName();
		func.verifyPastOrderMenuItemPrice();
				 
		}  
		
		
		@Test(priority=17)
		public void TC51_UberEats_VERIFY_MultipleItemOrder()
		{
			Logger log = LogManager.getLogger("TestVerifyMultipleItemOrder");
			log.info("******TC51_UE_CHROME : Starting to Verify Multiple Item Can be added in UberEats WebApp******");
			FunctionalComponents func = new FunctionalComponents(driver, log);
			func.uberEats_Login();
			func.clickOnStore();
			func.clickMenuItem(2);
			func.clickAddToOrder();
			func.clickMenuItem(7);
			func.clickAddToOrder();
			func.clickMenuItem(17);
			func.clickCheckoutItem();
			func.clickPlaceOrderASAP();
			 
		} 
		
		
		
		@Test(priority=18)
		public void TC52_UberEats_ValidateItemCustomization() 
			{
			Logger log = LogManager.getLogger("TestValidateItemCustomization");
			log.info("******TC52_UE_CHROME : Starting to Validate If the item can be customized before adding to order in UberEats WebApp******");
			FunctionalComponents func = new FunctionalComponents(driver, log);
			func.uberEats_Login();
			func.clickOnStore();
			func.clickMenuItem(4);
			func.customizeItem();
			func.clickCheckoutItem();
			func.clickPlaceOrderASAP();
			
		}
			
			
			@Test(priority=19)
			public void TC53_UberEats_Verify_AddItemInCheckoutPage() {
				
				Logger log = LogManager.getLogger("TestValidateAddItemInCheckoutPage");
				log.info("******TC53_UE_CHROME : Starting to Validate If user is able to add-item in Checkout Page in uber eats******");
				FunctionalComponents func = new FunctionalComponents(driver, log);
				func.uberEats_Login();
				func.openStore();
				func.selectMenuItem(3);
				func.clickCheckoutItem();
				func.clickAddItemInCheckout();
				func.selectMenuItem(5);
				func.clickCheckoutItem();
				func.selectScheduleTime();
				func.clickPlaceOrderSL();
				
			}  
			
			
			@Test(priority=20)
			public void TC28_UberEats_VerifyOrderDetails() {
				
				Logger log = LogManager.getLogger("TestValidateViewOderDetails");
				log.info("******TC28_UE_CHROME : Starting to Validate If user is able to see placed order in view order details tab in uber eats******");
				FunctionalComponents func = new FunctionalComponents(driver, log);
				func.uberEats_Login();
				func.clickOnStore();
				func.clickMenuItem(9);
				func.clickCheckoutItem();
				func.selectScheduleTime();
				func.clickPlaceOrderSL();
				func.clickOnViewOrders();
				func.viewOrderDetails(9);
				
			}
		
			public void UberEats_DemoTest() {
				Logger log = LogManager.getLogger("TestValidateaFullScheduleLaterOrderFlow");
				log.info("******Demo_UE_CHROME : Starting to Validate If user is able to place a Schedule Later Order in uber eats******");
				FunctionalComponents func = new FunctionalComponents(driver, log);
				func.uberEats_Login();
				func.openStore();
				func.clickMenuItem(8);
				func.itemIncreaseCheck();
				func.itemDecreaseCheck();
				func.clickAddToOrder();
				func.clickOnViewCart();
				func.verifyOrderItem(8);
				func.changeItemQuantity();
				func.RemoveQuantity();
				func.clickMenuItem(7);
				func.clickCheckoutItem();
				func.clickAddItemInCheckout();
				func.clickMenuItem(4);
				func.customizeItem();
				func.clickCheckoutItem();
				func.selectScheduleTime();
				func.clickPlaceOrderSL();
				
			}
			
			@AfterMethod
			public void tearDown()
			{
				driver.close();
				driver.quit();
			}
		

}
