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

public class TestIJW extends Base {
	
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
	
	@Test(priority=1)
	public void TC01_DOORDASH_IJW_LoginLogout()
	{
		Logger log = LogManager.getLogger("TestLoginLogout");
		log.info("****** TC01 Starting to Validate Login and Logout functionality of Doordash - Addison IJW ******");
		FunctionalComponents func = new FunctionalComponents(driver, log);
		func.webLoginIJW();
		func.webClickSignOutButtonIJW();
	   		
	}


	@Test(priority=2)
	public void TC03_IJW_VERIFY_ADDTOCART() throws InterruptedException
	{
		Logger log = LogManager.getLogger("TestVerifyAddToCart");
		log.info("****** TC03 Starting to Verify Add To Cart functionality of IJW ******");
		FunctionalComponents func = new FunctionalComponents(driver, log);
		func.webLoginIJW();
		func.webValidateAccountNameIJW();
		func.webClickMenuItemIJW(7);
		func.webClickAddToOrderIJW();
		func.webVerifyOrderItem_QtyIJW();
		func.webRemoveItemIJW(7);
	//	func.webClickSignOutButtonIJW();
			
	}
	

	@Test(priority=3)									
	public void TC04_IJW_CHROME_VERIFY_Checkout() throws InterruptedException
	{
		Logger log = LogManager.getLogger("TestVerifyCheckout");
		log.info("****** TC04 Starting to Verify Checkout functionality of IJW ******");
		FunctionalComponents func = new FunctionalComponents(driver, log);
		func.webLoginIJW();
		func.webValidateAccountNameIJW();
		func.webClickMenuItemIJW(3);
		func.webClickAddToOrderIJW();
		func.webVerifyOrderItem_QtyIJW();
		func.webClickOnCheckoutIJW();
		func.webRemoveItemIJW(3);
	//	func.webClickSignOutButtonIJW();
			
	}
	

	@Test(priority=4)
	public void TC05_IJW_EnterTipAmountZero() throws InterruptedException
	{
		Logger log = LogManager.getLogger("TestEnterTipAmountZero");
		log.info("****** TC05 Starting to Validate if the User Can Enter Tip Amount Zero in IJW while placing order ******");
		FunctionalComponents func = new FunctionalComponents(driver, log);
		func.webLoginIJW();;
		func.webValidateAccountNameIJW();
		func.webClickMenuItemIJW(4);
		func.webClickAddToOrderIJW();
		func.webVerifyOrderItem_QtyIJW();
		func.webClickOnCheckoutIJW();
		func.webEnterZeroTipIJW();
		func.webRemoveItemIJW(4);
		//func.webClickSignOutButtonIJW();
		
	}

	
	@Test(priority=5)									
	public void TC06_IJW_VERIFY_Homepage_UI()
	{
		Logger log = LogManager.getLogger("TestVerifyHomepageUI");
		log.info("****** TC06 Starting to Verify Homepage UI of IJW ******");
		FunctionalComponents func = new FunctionalComponents(driver, log);
		func.webLoginIJW();
		func.webValidateAccountNameIJW();
		func.webvalidateMenuDescriptionIJW();
		//func.webClickSignOutButtonIJW();
	
	}


	@Test(priority=6)
	public void TC07_IJW_ValidateMenuCategory() {
		
		Logger log = LogManager.getLogger("TestValidateMenuCategory");
		log.info("******TC07 Starting to Validate If all the menu categories are present in IJW ******");
		FunctionalComponents func = new FunctionalComponents(driver, log);
		func.webLoginIJW();
		func.webvalidateMenuCategoriesIJW();
		//func.webClickSignOutButtonIJW();
	  
	}


	@Test(priority=7)
	public void TC08_IJW_ValidateMenuDescription() {
		
		Logger log = LogManager.getLogger("TestValidateMenuDescription");
		log.info("****** TC08 Starting to Validate Menu Description for all the menu Items in IJW ******");
		FunctionalComponents func = new FunctionalComponents(driver, log);
		func.webLoginIJW();
		func.webvalidateMenuDescriptionIJW();
		//func.webvalidateMenuDescPriceIJW();
		//func.webClickSignOutButtonIJW();
	  		
	}

	
//TC09 - validate price for all menu items - IJW by Srividhya - 06-28-2021 begin
	//@Test
	@Test(priority=8)
	public void TC09_IJW_ValidateMenuPrice() {
		
		Logger log = LogManager.getLogger("TestValidateMenuPrice");
		log.info("****** TC09 Starting to Validate Menu Price for all the menu items in IJW ******");
		FunctionalComponents func = new FunctionalComponents(driver, log);
		func.webLoginIJW();
		func.webvalidateMenuPriceIJW();
		//func.webClickSignOutButtonIJW();
			
	}

		
@Test(priority=9)
public void TC90_VERIFY_ITEM_ADDTOCART_CHECKOUT() throws InterruptedException
{
	Logger log = LogManager.getLogger("TestVerifyAddToCartCheckout");
	log.info("****** TC90 Starting to Verify AddtoCart and Checkout functionality of IJW ******");
	FunctionalComponents func = new FunctionalComponents(driver, log);
	func.webLoginIJW();
	func.webValidateAccountNameIJW();
	func.webClickMenuItemIJW(6);
	func.webClickAddToOrderIJW();
	func.webVerifyOrderItem_QtyIJW();
	func.webRemoveItemIJW(6);
}


@Test(priority=10)
public void TC91_ValidateItemIncreaseDecrease() throws InterruptedException 
{
Logger log = LogManager.getLogger("TestValidateItemIncreaseDecrease");
log.info("******TC91_IJW_CHROME : Starting to Validate If the number of items can be increased or decreased before adding to order in IJW Chilis Addison WebApp******");
FunctionalComponents func = new FunctionalComponents(driver, log);
func.webLoginIJW();
func.webValidateAccountNameIJW();
func.webClickMenuItemIJW(5);
func.webItemIncreaseCheck();
func.webItemDecreaseCheck();
func.webRemoveItemIJW(5);
//func.webClickSignOutButtonIJW();
}


@Test(priority=11)								
public void TC92_IJW_VERIFY_ORDER_MODES() throws InterruptedException
{
	Logger log = LogManager.getLogger("TestVerifyOrderModes");
	log.info("****** TC92 Starting to Verify Order Modes of IJW ******");
	FunctionalComponents func = new FunctionalComponents(driver, log);
	func.webLoginIJW();
	func.webValidateAccountNameIJW();
	func.webClickMenuItemIJW(6);
	func.webClickAddToOrderIJW();
	func.webVerifyOrderItem_QtyIJW();
	func.webClickOnCheckoutIJW();
	func.webVerifyOrderModesIJW();
	func.webRemoveItemIJW(6);
	//func.webClickSignOutButtonIJW();   
}


@Test(priority=12)
public void TC93_IJW_ValidatePickupScheduleLater() throws InterruptedException 
{
Logger log = LogManager.getLogger("TestValidatePickupScheduleLater");
log.info("****** TC93 Starting to Validate If the user is able to place a pickup Schedule later order in IJW ******");
FunctionalComponents func = new FunctionalComponents(driver, log);
func.webLoginIJW();
func.webValidateAccountNameIJW();
func.webClickMenuItemIJW(7);
func.webClickAddToOrderIJW();
func.webVerifyOrderItem_QtyIJW();
func.webClickOnCheckoutIJW();
func.webPickupScheduleLaterIJW();
func.webOrderDetailsbeforeIJW();
func.webPlacePickupOrderIJW();
func.webOrderDetailsAfterIJW();
func.webValidateOrdersIJW();
//func.webClickViewOrdersIJW();
//func.webClickSignOutButtonIJW();

}

@Test(priority=13)
public void TC_IJW_VERIFY_PICKUP_ORDER_ASAP() throws InterruptedException
{
	Logger log = LogManager.getLogger("TestVerifyAsapPickupOrder");
	log.info("****** TC Starting to Verify Pickup-Asap Order of IJW ******");
	FunctionalComponents func = new FunctionalComponents(driver, log);
	func.webLoginIJW();
	func.webValidateAccountNameIJW();
	func.webClickMenuItemIJW(8);
	func.webClickAddToOrderIJW();
	func.webVerifyOrderItem_QtyIJW();
	func.webClickOnCheckoutIJW();
	func.webSelectPickupASAPIJW();
	func.webOrderDetailsbeforeIJW();
	func.webPlacePickupOrderIJW();
	func.webOrderDetailsAfterIJW();
	func.webValidateOrdersIJW();
//	func.webClickViewOrdersIJW();	
	//func.webClickSignOutButtonIJW();
	
} 


@Test(priority=14)
public void TC94_IJW_VERIFY_DELIVERY_ORDER_ASAP() throws InterruptedException
{
	Logger log = LogManager.getLogger("TestVerifyAsapDeliveryOrder");
	log.info("****** TC94 Starting to Verify Delivery-Asap Order of IJW ******");
	FunctionalComponents func = new FunctionalComponents(driver, log);
	func.webLoginIJW();
	func.webValidateAccountNameIJW();
	func.webClickMenuItemIJW(9);
	func.webClickAddToOrderIJW();
	func.webVerifyOrderItem_QtyIJW();
	func.webClickOnCheckoutIJW();
	func.webSelectDeliveryASAPIJW();
	func.webOrderDetailsbeforeIJW();
	func.webPlaceDeliveryAsapOrderIJW();
	func.webOrderDetailsAfterIJW();
	func.webValidateOrdersIJW();
	//func.webClickViewOrdersIJW();	
	//func.webClickSignOutButtonIJW();
	
}  


@Test(priority=15)
public void IJW_VERIFY_DELIVERY_ORDER_ASAP_FOR_2ITEMS() throws InterruptedException
{
	Logger log = LogManager.getLogger("TestVerifyAsapDeliveryOrderfor2Items");
	log.info("******TC Starting to Verify Delivery-Asap Order (2 Items) of IJW ******");
	FunctionalComponents func = new FunctionalComponents(driver, log);
	func.webLoginIJW();
	func.webValidateAccountNameIJW();
	func.webClickMenuItemIJW(2);
	func.webClickAddToOrderIJW();
	func.webVerifyOrderItem_QtyIJW();
	func.webClickMenuItemIJW(13);
	func.webClickAddToOrderIJW();
	func.webClickOnCheckoutIJW();
	func.webSelectDeliveryASAPIJW();
	func.webOrderDetailsbeforeIJW();
	func.webPlaceDeliveryAsapOrderIJW();
	func.webOrderDetailsAfterIJW();
	func.webValidateOrdersIJW();
	//func.webClickViewOrdersIJW();
	//func.webClickSignOutButtonIJW();
	
}  


@Test(priority=16)
public void TC95_IJW_VERIFY_DELIVERY_SCHEDULE_ORDER_TODAY() throws InterruptedException
{
Logger log = LogManager.getLogger("TestVerifyDeliveryScheduleOrderToday");
log.info("****** TC95 Starting to Verify Delivery-Schedule Order_Today of IJW ******");
FunctionalComponents func = new FunctionalComponents(driver, log);
func.webLoginIJW();
func.webValidateAccountNameIJW();
func.webClickMenuItemIJW(10);
func.webClickAddToOrderIJW();
func.webVerifyOrderItem_QtyIJW();
func.webClickOnCheckoutIJW();
func.webSelectDeliveryTodayIJW();
func.webOrderDetailsbeforeIJW();
func.webPlaceDeliveryScheduleOrderIJW();
func.webOrderDetailsAfterIJW();
func.webValidateOrdersIJW();
//func.webClickViewOrdersIJW();
//func.webClickSignOutButtonIJW();

}


@Test(priority=17)
public void TC_DOORDASH_IJW_VERIFY_DELIVERY_TOMORROW() throws InterruptedException
{
	Logger log = LogManager.getLogger("TestVerifyDeliveryScheduleOrderTomorrow");
	log.info("****** TC Starting to Verify Delivery-Schedule Order - Tomorrow for IJW ******");
	FunctionalComponents func = new FunctionalComponents(driver, log);
	func.webLoginIJW();
	func.webValidateAccountNameIJW();
	func.webClickMenuItemIJW(11);
	func.webClickAddToOrderIJW();
	func.webVerifyOrderItem_QtyIJW();
	func.webClickOnCheckoutIJW();
	func.webSelectDeliveryOrderTomorrowIJW();
	func.webOrderDetailsbeforeIJW();
	func.webPlaceDeliveryScheduleOrderIJW();
	func.webOrderDetailsAfterIJW();
	func.webValidateOrdersIJW();
	//func.webClickViewOrdersIJW();	
	//func.webClickSignOutButtonIJW();
}


@Test(priority=18)
public void TC_DOORDASH_IJW_VERIFY_REMOVEITEM_BEFORE_CHECKOUT() throws InterruptedException 
{
	Logger log = LogManager.getLogger("TestRemoveItem");
	log.info("****** TC Starting to Validate Remove an Item before checkout functionality of IJW  ******");
	FunctionalComponents func = new FunctionalComponents(driver, log);
	func.webLoginIJW();
	func.webValidateAccountNameIJW();
	func.webClickMenuItemIJW(12);
	func.webClickAddToOrderIJW();
	func.webVerifyOrderItem_QtyIJW();
	func.webRemoveItemIJW(12);
	//func.webClickSignOutButtonIJW();
}


@Test(priority=19)
public void TC_DOORDASH_IJW_VERIFY_REMOVE_AN_ITEM_WHEN_MANY_PRESENT_BEFORE_CHECKOUT() throws InterruptedException 
{
	Logger log = LogManager.getLogger("TestRemoveItem");
	log.info("****** TC Starting to Validate Remove an Item (When many items are present) before checkout functionality of IJW  ******");
	FunctionalComponents func = new FunctionalComponents(driver, log);
	func.webLoginIJW();
	func.webValidateAccountNameIJW();
	func.webClickMenuItemIJW(13);
	func.webClickAddToOrderIJW();
	func.webVerifyOrderItem_QtyIJW();
	func.webClickMenuItemIJW(14);
	func.webClickAddToOrderIJW();
	func.webVerifyOrderItem_QtyIJW();
	func.webRemoveItemIJW(13);
	//func.webClickSignOutButtonIJW();
}


@Test(priority=20)
public void TC_DOORDASH_IJW_VERIFY_REMOVEITEMWHEN_NO_ITEM_BEFORE_CHECKOUT() throws InterruptedException 
{
	Logger log = LogManager.getLogger("TestRemoveItem");
	log.info("****** TC Starting to Validate Remove an Item (when no item is prsent) before checkout functionality of IJW  ******");
	FunctionalComponents func = new FunctionalComponents(driver, log);
	func.webLoginIJW();
	func.webValidateAccountNameIJW();
	func.webRemoveItemIJW(13);
	//func.webClickSignOutButtonIJW();
}


@Test(priority=21)
public void TC_DOORDASH_IJW_VERIFY_REMOVEITEM_AFTER_CHECKOUT() throws InterruptedException 
{
	Logger log = LogManager.getLogger("TestRemoveItem");
	log.info("****** TC Starting to Validate Remove an Item After checkout functionality of IJW  ******");
	FunctionalComponents func = new FunctionalComponents(driver, log);
	func.webLoginIJW();
	func.webValidateAccountNameIJW();
	func.webClickMenuItemIJW(15);
	func.webClickAddToOrderIJW();
	func.webVerifyOrderItem_QtyIJW();
	func.webClickOnCheckoutIJW();
	func.webRemoveItemIJW(15);
	//func.webClickSignOutButtonIJW();
}


@Test(priority=22)
public void TC_DOORDASH_IJW_VERIFY_REMOVEITEM_AFTER_CHECKOUT_WHEN_MANYITEM_PRESENT() throws InterruptedException 
{
	Logger log = LogManager.getLogger("TestRemoveItem");
	log.info("****** TC Starting to Validate Remove an Item After checkout when many items are present - functionality of IJW  ******");
	FunctionalComponents func = new FunctionalComponents(driver, log);
	func.webLoginIJW();
	func.webValidateAccountNameIJW();
	func.webClickMenuItemIJW(16);
	func.webClickAddToOrderIJW();
	func.webVerifyOrderItem_QtyIJW();
	func.webClickMenuItemIJW(17);
	func.webClickAddToOrderIJW();
	func.webVerifyOrderItem_QtyIJW();
	func.webClickOnCheckoutIJW();
	func.webRemoveItemIJW(17);
	func.webclickBacktoMenuIJW();
	//func.webClickSignOutButtonIJW();
}

@Test(priority=23)
public void TC_DOORDASH_IJW_VERIFY_REORDER()
{
	Logger log = LogManager.getLogger("TestReorder");
	log.info("****** TC Starting to Validate ReOrder functionality of IJW  ******");
	FunctionalComponents func = new FunctionalComponents(driver, log);
	func.webLoginIJW();
	func.webReorderIJW();
	//func.webClickSignOutButtonIJW();
}


@Test(priority=24)
public void TC_DOORDASH_IJW_VERIFY_VIEWORDERS()
{
	Logger log = LogManager.getLogger("TestViewOrders");
	log.info("****** TC Starting to Validate View Orders functionality of IJW  ******");
	FunctionalComponents func = new FunctionalComponents(driver, log);
	func.webLoginIJW();
	func.webclickViewAllOrdersInProgressIJW();
	
}


@Test(priority=25)
public void TC_DOORDASH_IJW_VERIFY_VIEWPREVIOUSORDERS()
{
	Logger log = LogManager.getLogger("TestViewPreviousOrders");
	log.info("****** TC Starting to Validate View Previous Orders functionality of IJW  ******");
	FunctionalComponents func = new FunctionalComponents(driver, log);
	func.webLoginIJW();
	func.webclickViewPreviousOrdersIJW();
	
}

	
	
	@AfterMethod
	public void tearDown()
	{
		driver.close();
		driver.quit();
	}
	

}
