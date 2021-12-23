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

public class TestDoorDashChilis extends Base {
	
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
	
	
	public void TC01_DOORDASH_CHROME_LoginLogoutChilisAddison()
	{
		Logger log = LogManager.getLogger("TestLoginLogout");
		log.info("******TC01_DOORDASH_CHROME : Starting to Validate Login and Logout functionality of Doordash Chilis Addison WebApp******");
		FunctionalComponents func = new FunctionalComponents(driver, log);
		func.webLogin();
		func.webClickSignOutButton();
		
	}
	 
	public void TC01_DOORDASH_CHROME_LoginLogoutChilisDelray()
	{
		Logger log = LogManager.getLogger("TestLoginLogout");
		log.info("******TC01_DOORDASH_CHROME : Starting to Validate Login and Logout functionality of Doordash Chilis Delray WebApp******");
		FunctionalComponents func = new FunctionalComponents(driver, log);
		func.webValidateSiteLaunch(prop.getProperty("doordash_delray_chilis_URL"));
		func.webClickHamburgerMenuButton();
		func.webClickSignInorSignUpButton();
		func.webEnterUserEmail();
		func.webEnterUserPassword();
		func.webClickSignInButton();
		func.webValidateAccountName();
		func.webClickSignOutButton();
		
	}
	
		@Test(priority=1)
		public void TC02_DOORDASH_CHROME_ChangeLocation()
		{
			Logger log = LogManager.getLogger("TestChangeLocation");
			log.info("******TC02_DOORDASH_CHROME : Starting to Validate Changing Location of Doordash Chilis Addison WebApp******");
			FunctionalComponents func = new FunctionalComponents(driver, log);
			func.webLogin();
			func.webSetLocation();
			
		} 
		
		@Test(priority=2)
		public void TC03_DOORDASH_CHROME_VERIFY_ADDTOCART()
		{
			Logger log = LogManager.getLogger("TestVerifyAddToCart");
			log.info("******TC03_DOORDASH_CHROME : Starting to Verify Add To Cart of Doordash Chilis WebApp******");
			FunctionalComponents func = new FunctionalComponents(driver, log);
			func.webLogin();
			func.webSetLocation();
			func.webAddToOrderItem(2);
			func.webVerifyOrderQuantity();
			
			
		}
		
		
			@Test(priority=3)								
			public void TC04_DOORDASH_CHROME_VERIFY_Checkout()
			{
				Logger log = LogManager.getLogger("TestVerifyCheckout");
				log.info("******TC04_DOORDASH_CHROME : Starting to Verify Checkout functionality of Doordash Chilis WebApp******");
				FunctionalComponents func = new FunctionalComponents(driver, log);
				func.webLogin();
				func.webSetLocation();
				func.webValidateTopAddress();
				func.webAddOrderItemCheckout(3);
				
			}
	
		@Test(priority=4)
		public void TC05_DOORDASH_CHROME_EnterTipAmountZero()
		{
			Logger log = LogManager.getLogger("TestEnterTipAmountZero");
			log.info("******TC05_DOORDASH_CHROME : Starting to Validate if the User Can Enter Tip Amount Zero in Doordash Chilis Addison WebApp******");
			FunctionalComponents func = new FunctionalComponents(driver, log);
			func.webLogin();
			func.webSetLocation();
			func.webAddOrderItemCheckout(4);
			func.webSelectDelivery();
			func.webClickOnTipOther();
			func.webEnterZeroTip();
			
		}
		
		

		@Test(priority=5)									
	public void TC06_DOORDASH_CHROME_VERIFY_Homepage_UI()
	{
		Logger log = LogManager.getLogger("TestVerifyHomepageUI");
		log.info("******TC06_DOORDASH_CHROME : Starting to Verify Homepage UI of Doordash Chilis WebApp******");
		FunctionalComponents func = new FunctionalComponents(driver, log);
		func.webLogin();
		func.webSetLocation();
		func.webValidateMenuItem();
		
	}
		

		@Test(priority=6)
		public void TC07_DOORDASH_CHROME_ValidateMenuCategory() 
		{
		Logger log = LogManager.getLogger("TestValidateMenuCategory");
		log.info("******TC07_DOORDASH_CHROME : Starting to Validate If  all the menu categories are present in Doordash Chilis Addison WebApp******");
		FunctionalComponents func = new FunctionalComponents(driver, log);
		func.webLogin();
		func.webSetLocation();
		func.webValidateMenuCategories();
	}
		
		@Test(priority=7)
	public void TC90_DOORDASH_CHROME_VERIFY_ITEM_ADDTOCART_CHECKOUT()
	{
		Logger log = LogManager.getLogger("TestVerifyAddToCart");
		log.info("******TC90_DOORDASH_CHROME : Starting to Verify AddtoCart and Checkout of Doordash Chilis WebApp******");
		FunctionalComponents func = new FunctionalComponents(driver, log);
		func.webLogin();
		func.webSetLocation();
		func.webAddOrderItemCheckout(5);
		
	}	
		

		@Test(priority=8)
		public void TC91_DOORDASH_CHROME_ValidateItemIncreaseDecrease() 
		{
		Logger log = LogManager.getLogger("TestValidateItemIncreaseDecrease");
		log.info("******TC91_DOORDASH_CHROME : Starting to Validate If the number of item can be increased or decreased before adding to order in Doordash Chilis Addison WebApp******");
		FunctionalComponents func = new FunctionalComponents(driver, log);
		func.webLogin();
		func.webSetLocation();
		func.webClickMenuItem(6);
		func.webItemIncreaseCheck();
		func.webItemDecreaseCheck();
		
	}  
		@Test(priority=8)
		public void TC101_DOORDASH_CHROME_ValidateItemCustomization() 
		{
		Logger log = LogManager.getLogger("TestValidateItemCustomization");
		log.info("******TC91_DOORDASH_CHROME : Starting to Validate If the item can be customized before adding to order in Doordash Chilis Addison WebApp******");
		FunctionalComponents func = new FunctionalComponents(driver, log);
		func.webLogin();
		func.webSetLocation();
		func.webClickMenuItem(4);
		func.webSelectCustomizations();
		func.webClickAddToOrder();
		func.webClickOnCheckout();
		func.webPickupASAP();
		
	}
	
		@Test(priority=9)									
		public void TC92_DOORDASH_CHROME_VERIFY_ORDER_MODES()
		{
			Logger log = LogManager.getLogger("TestVerifyOrderModes");
			log.info("******TC92_DOORDASH_CHROME : Starting to Verify Order Modes of Doordash Chilis WebApp******");
			FunctionalComponents func = new FunctionalComponents(driver, log);
			func.webLogin();
			func.webSetLocation();
			func.webAddOrderItemCheckout(7);
			func.webVerifyDeliveryButton();
			func.webVerifyPickupButton();
		} 
		
		@Test(priority=10)
	public void TC94_DOORDASH_CHROME_VERIFY_DELIVERY_ORDER_ASAP_MargaritaGrilledChicken()
	{
		Logger log = LogManager.getLogger("TestVerifyAsapDeliveryOrder");
		log.info("******TC94_DOORDASH_CHROME : Starting to Verify Delivery-Asap Order of Margarita Grilled Chicken from Doordash Chilis WebApp******");
		FunctionalComponents func = new FunctionalComponents(driver, log);
		func.webLogin();
		func.webSetLocation();
		func.webAddOrderItemCheckout(9);
		func.webDeliveryASAP();
		
	} 
		
			
		@Test(priority=11)
		public void TC08_DOORDASH_CHROME_ValidateMenuItemDescription() 
		{
		Logger log = LogManager.getLogger("TestValidateMenuItemDescription");
		log.info("******TC08_DOORDASH_CHROME : Starting to Validate If  all the Menu Item Description are present in Doordash Chilis Addison WebApp******");
		FunctionalComponents func = new FunctionalComponents(driver, log);
		func.webValidateSiteLaunch(prop.getProperty("doordash_addison_chilis_URL"));
		func.webClickHamburgerMenuButton();
		func.webClickSignInorSignUpButton();
		func.webEnterUserEmail();
		func.webEnterUserPassword();
		func.webClickSignInButton();
		func.webValidateSiteLaunch(prop.getProperty("doordash_addison_chilis_URL"));
		func.DD_WebvalidateMenuDescription();
	}
			
		
 
		@Test(priority=12)
		public void TC09_DOORDASH_CHROME_ValidateMenuItemPrice() 
		{
		Logger log = LogManager.getLogger("TestValidateMenuItemPrice");
		log.info("******TC09_DOORDASH_CHROME : Starting to Validate If  Price is displayed for all the Menu Item in Doordash Chilis Addison WebApp******");
		FunctionalComponents func = new FunctionalComponents(driver, log);
		func.webLogin();
		func.DD_WebvalidateMenuPrice();
	}
			
	
			@Test(priority=13)
			public void TC93_DOORDASH_CHROME_ValidatePickupScheduleLater_QuesadillaExplosionSalad() 
			{
			Logger log = LogManager.getLogger("TestValidatePickupScheduleLater");
			log.info("******TC93_DOORDASH_CHROME : Starting to Validate If the user is able to place a pickup Schedule later order of Quesadilla Explosion Salad from Doordash Chilis Addison WebApp******");
			FunctionalComponents func = new FunctionalComponents(driver, log);
			func.webLogin();
			func.webSetLocation();
			func.webAddOrderItemCheckout(8);
			func.webPickupSchedule();
			
		} 
			
			@Test(priority=14)
			public void TC95_DOORDASH_CHROME_VERIFY_DELIVERY_SCHEDULE_ORDER_CrispyChickenCrispers()
			{
				Logger log = LogManager.getLogger("TestVerifyDeliveryScheduleOrder");
				log.info("******TC95_DOORDASH_CHROME : Starting to Verify Delivery-Schedule Order of Crispy Chicken Crispers from Doordash Chilis WebApp******");
				FunctionalComponents func = new FunctionalComponents(driver, log);
				func.webLogin();
				func.webSetLocation();
				func.webAddOrderItemCheckout(10);
				func.webDeliverySchedule();
				
				
			}
			
			@Test(priority=15)
			public void TC97_DOORDASH_CHROME_VERIFY_DELIVERY_ORDER_ASAP_LunchComboSpicyShrimpTacos()
			{
				Logger log = LogManager.getLogger("TestVerifyAsapDeliveryOrder");
				log.info("******TC97_DOORDASH_CHROME : Starting to Verify Delivery-Asap Order of Spicy Shrimp Tacos from Doordash Chilis WebApp******");
				FunctionalComponents func = new FunctionalComponents(driver, log);
				func.webLogin();
				func.webSetLocation();
				func.webAddOrderItemCheckout(14);
				func.webDeliveryASAP();
				
			}  
			
			
			@Test(priority=16)
			public void TC98_DOORDASH_CHROME_VERIFY_DELIVERY_SCHEDULE_ORDER_MoltenChocolateCake()
			{
				Logger log = LogManager.getLogger("TestVerifyDeliveryScheduleOrder");
				log.info("******TC98_DOORDASH_CHROME : Starting to Verify Delivery-Schedule Order of Molten Chocolate Cake from Doordash Chilis WebApp******");
				FunctionalComponents func = new FunctionalComponents(driver, log);
				func.webLogin();
				func.webSetLocation();
				func.webAddOrderItemCheckout(15);
				func.webDeliverySchedule();
				
			}
			
			@Test(priority=17)
			public void TC99_DOORDASH_CHROME_ValidatePickupScheduleLater_BaconRanchChickenQuesadillas() 
			{
			Logger log = LogManager.getLogger("TestValidatePickupScheduleLater");
			log.info("******TC99_DOORDASH_CHROME : Starting to Validate If the user is able to place a pickup Schedule later order of Bacon Ranch Chicken Quesadillas from Doordash Chilis Addison WebApp******");
			FunctionalComponents func = new FunctionalComponents(driver, log);
			func.webLogin();
			func.webSetLocation();
			func.webAddOrderItemCheckout(12);
			func.webPickupSchedule();
			
			
			
		} 
			
			@Test(priority=18)
			public void TC96_DOORDASH_CHROME_ValidatePickupAsap_GrilledChickenSalad() 
			{
			Logger log = LogManager.getLogger("TestValidatePickupAsap");
			log.info("******TC96_DOORDASH_CHROME : Starting to Validate If the user is able to place a pickup Asap order of Grilled Chicken Salad from Doordash Chilis Addison WebApp******");
			FunctionalComponents func = new FunctionalComponents(driver, log);
			func.webLogin();
			func.webSetLocation();
			func.webAddOrderItemCheckout(11);
			func.webPickupASAP();
			
		} 
			@Test(priority=19)
			public void TC100_DOORDASH_CHROME_ValidatePickupAsap_UltimateSmokehouseCombo() 
			{
			Logger log = LogManager.getLogger("TestValidatePickupAsap");
			log.info("******TC100_DOORDASH_CHROME : Starting to Validate If the user is able to place a pickup Asap order of Ultimate Smokehouse Combo from Doordash Chilis Addison WebApp******");
			FunctionalComponents func = new FunctionalComponents(driver, log);
			func.webLogin();
			func.webSetLocation();
			func.webAddOrderItemCheckout(13);
			func.webPickupASAP();
			
			
		} 
			
			@Test(priority=20)
			public void TC03_DOORDASH_CHROME_VERIFY_REMOVEITEM()
			{
				Logger log = LogManager.getLogger("TestVerifyRermoveItem");
				log.info("******TC03_DOORDASH_CHROME : Starting to Verify RemoveItem button of Doordash Chilis WebApp******");
				FunctionalComponents func = new FunctionalComponents(driver, log);
				func.webLogin();
				func.webSetLocation();
				func.webAddToOrderItem(2);
				func.webRemoveItem(2);
			}
			
			
			@Test(priority=21)
			public void TC102_DOORDASH_CHROME_VERIFY_MultipleItemOrder()
			{
				Logger log = LogManager.getLogger("TestVerifyRermoveItem");
				log.info("******TC03_DOORDASH_CHROME : Starting to Verify RemoveItem button of Doordash Chilis WebApp******");
				FunctionalComponents func = new FunctionalComponents(driver, log);
				func.webLogin();
				func.webSetLocation();
				func.webAddToOrderItem(2);
				func.webAddToOrderItem(7);
				func.webAddToOrderItem(17);
				func.webClickOnCheckout();
				func.webDeliveryASAP();
			}
			
			
			@Test(priority=22)
			public void TC103_DOORDASH_CHROME_VERIFY_AddMoreItems()
			{
				Logger log = LogManager.getLogger("TestVerifyAddMoreItems");
				log.info("******TC03_DOORDASH_CHROME : Starting to Verify Add more Items button of Doordash Chilis WebApp******");
				FunctionalComponents func = new FunctionalComponents(driver, log);
				func.webLogin();
				func.webSetLocation();
				func.webAddOrderItemCheckout(2);
				func.clickOnAddMoreItems();
				func.webAddOrderItemCheckout(7);
				func.webDeliveryASAP();
			}
			
			@Test(priority=23)
			public void TC104_DOORDASH_CHROME_VERIFY_ItemIncreaseDecreaseAfterCheckout()
			{
				Logger log = LogManager.getLogger("TestVerifyItemIncreaseDecreaseAfterCheckout");
				log.info("******TC03_DOORDASH_CHROME : Starting to Verify If the User is able to Increase or Decrease the quantity after checkout of Doordash Chilis WebApp******");
				FunctionalComponents func = new FunctionalComponents(driver, log);
				func.webLogin();
				func.webSetLocation();
				func.webAddOrderItemCheckout(2);
				func.webCheckIncreaseAfterCheckout();
				func.webCheckDecreaseAfterCheckout();
			}
			
			@AfterMethod
			public void tearDown()
			{
				driver.close();
				driver.quit();
			}
			

}
