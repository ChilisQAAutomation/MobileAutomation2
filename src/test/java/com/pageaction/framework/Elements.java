package com.pageaction.framework;

import org.openqa.selenium.By;

import io.appium.java_client.MobileBy;

public class Elements {
	//Login Logout
	public static final String popUpCloseButton = "//*[@id='close-loyalty-popup']";
	public static final By chilisLogo = By.xpath("//*[@text=concat('Chili', \"'\", 's Grill & Bar - Local Restaurants Near Me | Chili', \"'\", 's')]");
	//*[@id='chili-logo']
	public static final By itemNameInCart = By.xpath("//*[@id='header-container']/div/div[4]/div[3]/div[@class='col-mini-cart-item']");
	public static final By itemNameInCheckOutPage = By.xpath("//div[@class='heading-tertiary']");
	public static final String itemNameInCheckOutPageOfApp="//div[@class='heading-tertiary']";
    public static final By itemNameTobeAdded= By.xpath("//*[@id='pattern-library-item-detail']/div[2]/h1");
 	//*[@id='location-address-click-wrapper']/div/div[@class='location-name']
	//*[contains(@text,'Mix') and @class='android.view.View']
	//*[contains(@text,'Oldtimer') and @class='android.view.View']
	
	public static final By menuButton = By.xpath("//*[@id='header-container']/a[3]");
	public static final By rewardsButton = By.xpath("//*[@id='header-rewards']");
	public static final By loginButton = By.xpath("//*[@id='header-login']");
	public static final By userNameTextBox = By.xpath("//input[@id='username']");
	public static final By passwordTextBox = By.xpath("//input[@id='password']");
	public static final By signinButton = By.xpath("//button[@id='login-submit']");
	public static final By rewardsHeaderInLoginPage = By.xpath("//*[text()='Rewards']");
	public static final By logoutButton = By.xpath("//*[@id='header-logout233']");
	public static final By loginHeader = By.xpath("//span[@class='summary title-summary-login']/preceding-sibling::h1");
	public static final By favoriteItemsHeader=By.xpath("//*[@id='page-container']/div[2]/div[1]/div/h2");
	public static final By favouriteItemsNames=By.xpath("//div[@class='heading-tertiary heading-favorite']");
	public static final By addToOrder=By.xpath("//div[@class='favorite-action']/button");
	public static final By orderPageHeader=By.xpath("//div[@class='heading-primary']");
	public static final By checkOutPageHeader=By.xpath("//div[@class='heading-primary']");
	public static final By finalizeOrderPageHeader=By.xpath("//div[@class='heading-primary']");
	public static final By pickUpTimeOptionDelivery = By.xpath("//select[@id='delivery-time']/option[3]");
	public static final By pickUpTimeOption = By.xpath("//select[@id='pickup-time']/option[3]");
	public static final By chilisMenuHeader=By.xpath("//*[@id='page-container']/div[3]/div[1]/div/div[1]");
	public static final By cvvLabel=By.xpath("//*[@id='lbl-cvv']");
	public static final By urlBar=By.xpath("//*[@id='url_bar']");
	//Location Search
	public static final By locationsButton = By.xpath("//*[@id='header-locations']");
	public static final By locationPageHeader=By.xpath("//*[@id='page-container']/div/div[1]/div/div/div/div[1]/div/h1");
	public static final By locationSearchTextBox = By.xpath("//*[@id='location-search']");
	public static final By searchButton = By.xpath("//*[@id='button-location-query']/span");
	public static final By restaurantName = By.xpath("//*[@class='location-summary']/preceding-sibling::h1");
	//Rewards
	public static final By noOfRewards = By.xpath("//*[@id='rewards-logged-in-summary-rewards']");
	public static final By actualRewardsCount = By.xpath("//*[@id='active-rewards-carousel']/div[1]/div/div");
	//My Account Update
	public static final By myAccountOption = By.xpath("//*[@id='header-account']");
	public static final By emailTextBox = By.xpath("//*[@id='email']");
	public static final By firstNameTextBox = By.xpath("//*[@id='firstName']");
	public static final By lastNameTextBox = By.xpath("//*[@id='lastName']");
	public static final By zipCodeTextBox = By.xpath("//*[@id='postalCode']");
	public static final By consentCheckBox = By.xpath("//*[@for='mobile-opt-in']");
	public static final By updateButton = By.xpath("//button[@type='submit' and text()='Update']");
	public static final By successMessageforUpdate = By.xpath("//span[@class='server-success-message']");
	    //Chilis Favourites
	public static final By menuOption = By.xpath("//*[@id='header-menu']");
	public static final By favouriteMenu = By.xpath("//*[@id='chilis-favorite-carousel']/div[1]/div/div");
	public static final By favouriteItemsTitle = By.xpath("//div[@class='heading-tertiary heading-favorite']");
	//Add My Visit
	public static final By addMyVisitButton = By.xpath("//button[text()='Add My Visit']");
	public static final By restaurantLocTextBox = By.xpath("//*[@id='location-search']");
	public static final By outsideClickLoc = By.xpath("//form[@id='manual-visit-request-form']/div/div/h2");
	public static final By chillisLocDropDown = By.xpath("//*[@id='store-number']");
	public static final By visitMonthDropDown = By.xpath("//*[@id='visit-month']");
	public static final By visitDayDropDown = By.xpath("//*[@id='visit-day']");
	public static final By visitYearDropDown = By.xpath("//*[@id='visit-year']");
	public static final By checkNumberTextBox = By.xpath("//*[@id='check-number']");
	public static final By checkTotalTextBox = By.xpath("//*[@id='check-total']");
	public static final By visitSubmitButton = By.xpath("//*[@id='mvr-confirm']");
	public static final By anyElement = By.xpath("//*[@id=\\\"active-rewards-carousel\\\"]/div[1]/div/div[1]/div/div[3]/span");
	public static final By getSuccessMessageforAddMyVisit = By.xpath("//span[@class='server-success-message']");
	public static final By toGetAddMyVisitButton = By.xpath("//*[@id='page-container']/div/div[4]/div/div/div[1]");
	public static final By chilisLocationOption = By.xpath("//*[text()='Addison - 4500 Beltline Rd., Dallas, TX 75001']");
	public static final By visitMonthOption = By.xpath("//*[text()='June']");
	public static final By visitDayOption = By.xpath("//*[text()='4']");
	public static final By visitYearOption = By.xpath("//*[text()='2018']");
	
	//Logged In Order
	//*[@text='Rate Later']
	public static final By orderNowButton = By.xpath("//a[contains(@href,'coit-road')]/following-sibling::a");
	public static final By addToCartButton = By.xpath("//button[@id='item-add-to-order']");
	public static final String appAddToCartButtonXpath="//button[@id='item-add-to-order']";
	public static final By viewCartButton = By.xpath("//a[@id='mini-cart-view-upsell']");
	public static final By optSilverWareCheckBox = By.xpath("//label[@for='silverware-opt-in-select']");
	public static final By checkOutButton = By.xpath("//*[@id='cart-checkout']");
	public static final String appCheckOutButtonXpath="//*[@text='CHECKOUT »']";
	public static final String silverWareXpath="//*[@for='silverware-opt-in-select']";
	public static final By orderTotal = By.xpath("//div[@class='heading-secondary order-total-label']");
	public static final By deliveryOption = By.xpath("//*[@id='location-001.005.0002']/div[3]/div/div/div/span[2]");
	public static final By addItem = By.xpath("//*[text()='Add to order']");
	public static final By cartIcon = By.xpath("//img[@alt='Cart']");
	public static final By addRewardsTitle = By.xpath("//h3[@class='accordion-title']");
	public static final By cartTotal = By.xpath("//div[@class='cart-totals']");
	public static final By paymentButton = By.xpath("//*[@id='continue-to-payment']");
	public static final By successMessageforLoggedInOrder =  By.xpath("//*[@id='page-container']/div/div[1]/div[1]/div/h1");
	public static final By orderPrice = By.xpath("//*[@id='pickup-total-confirm']/td[2]/div");
	public static final By deliveryOrderPrice = By.xpath("//*[@id='delivery-total-confirm']/td[2]/div");
	public static final By placeOrder = By.xpath("//*[contains(@id,'place-order')]");
	public static final By expirationMonth = By.xpath("//*[@id='month-selector']");
	public static final By expirationYear = By.xpath("//*[@id='year-selector']");
	public static final By cvvTextBox = By.xpath("//*[@id='cvv']");
	public static final By tipTextBox = By.xpath("//input[@name='tip']");
	public static final By pickUpCost = By.xpath("//*[@id='pickup-cost']");
	public static final By deliveryPicKUpCost = By.xpath("//*[@id='delivery-cost']");
	public static final String deliveryPickUpCost = "//*[@id='delivery-cost']";
	public static final By donationCheckBox = By.xpath("//*[contains(@id,'roundup-checkbox')]");
	public static final By locationHeader = By.xpath("//*[@id='page-container']/div/div[1]/div/div/div/div[1]/div/h1");
	public static final By silverWareCheckBox = By.xpath("//*[@id='silverware-opt-in-select']");
	public static final String trackOrder="//*[@text='TRACK MY ORDER']";
	public static final String ordertrackImg="//*[@id='order-recieved-img']";
	public static final String appDeliveryAddressOption="//*[@class='pac-icon pac-icon-marker']";
	public static final String deliveryAddressOption = "((//*[@nodeName='BODY']/*/*[@nodeName='DIV' and ./parent::*[@nodeName='DIV']])[3]/*[@nodeName='SPAN' and @width>0])[1]";
	public static final By displayedTipAmount = By.xpath("//*[@id='tip-payment']/td[2]/div");
	public static final String androiddisplayedTipAmount = "//*[@id='tip-payment']/td[2]/div";
	public static final By customTipInput = By.xpath("//*[@id='custom-tip-input']");
	public static final String androidAppcustomTipInput = "//*[@id='custom-tip-input']";
	public static final By paymentMethodDropDown = By.xpath("//*[@id='payment-method-selector']");
	public static final String androidApppaymentMethodDropDown ="//*[@id='payment-method-selector']";
	public static final By paymentMethodDropDown2 = By.xpath("//*[@name='paymentMethod']");
	public static final By cardTypeDropDown = By.xpath("//*[@id='card-type-selector']");
	public static final String androidAppcardTypeDropDown = "//*[@id='card-type-selector']";
	public static final By storeNameXpath = By.xpath("//*[@class='location-bar-text' and @text!='Change Location']");
	public static final By payWithCreditCardXpath=By.xpath("//*[@id='credit-card']");
	//Re Order	
	public static final By reorderOption = By.xpath("//*[@id='header-order-history']");
	public static final By quantity = By.xpath("//*[@id='items0.quantity']");
	public static final By reOrder = By.xpath("//div[contains(@class,'first-order')]/div[4]/div[2]/form/button");
	public static final String neverSavePassword="//*[@text='Never']";
	//Guest Delivery-ASAP //Ayushman
	public static final By orderNowButtonDallas = By.xpath("//a[@id='location-select-001.005.0002']");
	public static final By bigMouthBurgersCategory = By.xpath("//*[@id='menu-category-name-big-mouth-burgers']");
	public static final By baconRancher = By.xpath("//*[@id='menu-item-button-P110316']");
	public static final By addThisItem = By.xpath("//*[@id='item-add-to-order']");
	public static final By selectDeliveryMode = By.xpath("//*[text()='Delivery']");
	public static final By deliveryAddress = By.xpath("//*[@id='autocomplete']");
	public static final By deliveryAddressqa2 = By.xpath("//*[@name='deliveryAddress']");
	public static final By aptNo = By.xpath("//*[@id='suite-no']");
	public static final By delDate = By.xpath("//*[@id='delivery-date']");
	public static final By dateInputASAP = By.xpath("//select[@id='delivery-date']/option[contains(@value,'ASAP')]");
	public static final By dateInputLT = By.xpath("//*[text()='Later Today']");
	public static final By delTime = By.xpath("//*[@id='delivery-time']");
	public static final By timeInputLT = By.xpath("//*[text()='12:30 PM']");
	public static final By firstName = By.xpath("//*[@id='first-name']");
	public static final By lastName = By.xpath("//*[@id='last-name']");
	public static final By contantNumber = By.xpath("//*[@id='contact-phone']");
	public static final By eMail = By.xpath("//*[@id='email']");
	public static final By cardNo = By.xpath("//*[@id='card-number']");
	public static final By nameOnCard = By.xpath("//*[@id='nameOnCard']");
	public static final By nameOnCard2 = By.xpath("//*[@id='name-on-card']");
	public static final By billingZip = By.xpath("//*[@id='zipcode']");
	public static final By billingZip2 = By.xpath("//*[@id='zip-code']");
	public static final By yourOrderHeaderXpath = By.xpath("//*[text()='Your Order']");
	
	//Delivery ASAP MCA user
	public static final By deliveryInstrTextbox = By.xpath("//*[@id='special-instructions']");
	
	
	//Guest Curbside				//Ayushman
		public static final By selectCurbsideMode = By.xpath("//*[text()='Curbside']");
		public static final By vehicleMake = By.xpath("//*[@id='vehicle-make']");
		public static final By vehicleModel = By.xpath("//*[@id='vehicle-model']");
		public static final By vehicleColor = By.xpath("//*[@id='vehicle-color']");
	
		//Guest Carry-Out Future		//Ayushman
		public static final By tempCust = By.xpath("//*[@name='itemChoices[0]']");
		public static final By selectWellDone = By.xpath("//*[text()='Well Done']");
		public static final By sideCust = By.xpath("//*[@name='sideItems[0]']");
		public static final By selectHouseSalad = By.xpath("//*[text()='House Salad ($1.00)']");
		public static final By dressingCust = By.xpath("//*[@name='sideItems[10]']");
		public static final By selectRanch = By.xpath("//*[text()='Ranch Dressing']");
		public static final By beverageCust = By.xpath("//*[@name='additionalItems[0]']");
		public static final By selectBBIcedTea = By.xpath("//*[text()='Blackberry Iced Tea ($3.19)']");
		public static final By soupCust = By.xpath("//*[@name='additionalItems[1]']");
		public static final By selectBowlOfSoup = By.xpath("//*[text()='Bowl of Soup with Entrée ($3.99)']");
		public static final By saladCust = By.xpath("//*[@name='additionalItems[4]']");
		public static final By selectCaeserSalad = By.xpath("//*[text()='Caesar Salad with Entrée ($3.69)']");
		public static final By additionalDressingCust = By.xpath("//*[@name='itemChoices[0]']");
		public static final By selectCarryOutMode = By.xpath("//*[@id='order-type-carryout-btn']");
		public static final By selectCarryOutModeIOS=By.xpath("//*[text()='Carryout']");
		public static final By pickDate = By.xpath("//*[@id='pickup-date']");
		public static final By dateInputTom = By.xpath("//*[text()='Tomorrow']");
		public static final By pickTime = By.xpath("//*[@id='pickup-time']");
		public static final By placeOrderButton = By.xpath("//*[@id='place-order']");
		public static final By discountLabel= By.xpath("//div[@class='order-discounts-label']");
		public static final By discountAmount=By.xpath("//div[@class='cost discount-amount']");
		public static final By rewardName=By.xpath("//p[@id='member-reward']");
		public static final By futurePickUpOption=By.xpath("//*[@id='pickup-date']/option[7]");
		public static final By orderSummary=By.xpath("//*[@id='pickup-info-form']/div[1]/div[1]/div[2]");
		public static final By roundOffforFutureOrder=By.xpath("//*[@id='roundup-checkbox']");
		public static final By iosresSearchClearXpath=By.xpath("//*[@id='Clear text']");
		//Delivery Later Today MCA
		public static final By LaterToday = By.xpath("//select[@id='pickup-date']/option[text()='Later Today']");
		public static final By deliveryLaterToday = By.xpath("//select[@id='delivery-date']/option[text()='Later Today']");
		//Curbside ASAP MCA 
		public static final By pickupAsapOrder = By.xpath("//*[@id='pickup-date']/option[contains(@value,'ASAP')]");
		
		//Place order with Rewards
		public static final By addRewards = By.xpath("//*[@name='reward']");
		public static final By rewardItem = By.xpath("//*[contains(text(),'Chips and Salsa')]");
		
		//Carry Out Customized order with Guest user
		public static final By customizeOrderButton = By.xpath("//*[@id='customize-item-button']");
		public static final By addExtraSauce = By.xpath("//input[contains(@id,'extra')]");
		public static final By customizeDropDown = By.xpath("//select[contains(@name,'customizeItems')]");
		public static final By customItem = By.xpath("//span[@class='item-details-title']");
		
		
		
		
		//*[@id="place-dinein-order"]
		//*[@id="order-type-dine-in-btn"]

		//*[@id="delivery-confirmation"]/a
		//*[@id="order-delivery-status"]
		
		//*[@id='infobar_close_button']
		
//Android App Automation : Login Logout
	public static final By appOkButtonXpath = MobileBy.xpath("//android.widget.Button[@text='OK']");
	public static final By appCancelButtonXpath = MobileBy.xpath("//android.widget.Button[@text='CANCEL']");
	public static final By iosCancelButtonXpath=MobileBy.xpath("//*[@text='Cancel']");
	public static final By appPopUpCloseXpath = MobileBy.xpath("//*[@id='cancel_btn']");
	public static final By appwelcomeMessageXpath = MobileBy.xpath("//android.widget.TextView[@text='FEED YOUR WHOLE FAMILY WITH']");
	public static final By appLoginButtonXpath = MobileBy.xpath("//android.widget.Button[@id='card_home_logged_out_login']");
	public static final By appUserNameTextBoxXpath = By.xpath("//android.widget.EditText[@id='login_username_field']");
	public static final By appPassWordTextBoxXpath = MobileBy.xpath("//android.widget.EditText[@id='login_password_field']");
	public static final By appSigninButtonXpath =MobileBy.xpath("//android.widget.Button[@id='login_btn']");
	public static final By apploginHeaderXpath =MobileBy.xpath("//*[@text='LOG IN TO YOUR ACCOUNT']");
	public static final By appMoreButtonXpath = MobileBy.xpath("//*[@id='icon' and ./parent::*[@id='menu_bottom_nav_more']]");
	public static final By appLogoutButtonXpath = MobileBy.xpath("//android.widget.TextView[@text='Log Out']");
	public static final By signOutConfirmButtonXpath = MobileBy.xpath("//*[@text='YES']");
	public static final By applogoutValXpath = MobileBy.xpath("//*[@id='card_home_logged_out_login']");
	public static final By appMapLinkXpath=MobileBy.xpath("//android.widget.Button[@text='MAP']");
	
//Location Search
	public static final By appfindRestaurantLinkXpath = MobileBy.xpath("//android.widget.Button[@id='card_home_restaurant_not_selected_find']");
	public static final By appResSearchTextBoxXpath = MobileBy.xpath("//android.widget.EditText[@id='address_text_field']");
	public static final By appResSearchAutocompleteTextBoxXpath = MobileBy.xpath("//android.widget.EditText[@id='places_autocomplete_edit_text']");
	public static final By appRestaurantNameXpath = MobileBy.xpath("//android.widget.TextView[@id='restaurant_name']");
	public static final By appRestaurantSelectedAddressXpath = MobileBy.xpath("//android.widget.TextView[@id='card_home_restaurant_selected_address']");
	public static final By appRestaurantChangeButtonXpath = MobileBy.xpath("//android.widget.Button[@text='CHANGE']");
	

	
//Validate Rewards Details
	public static final By appRewardButtonXpath = MobileBy.xpath("//*[@id='icon' and ./parent::*[@id='menu_bottom_nav_rewards']]");
	public static final By appRewardsHeaderXpath = MobileBy.xpath("//android.widget.TextView[@text='My Rewards']");
	public static final By appdisplayedRewardCountXpath = MobileBy.xpath("//android.widget.TextView[@id='myrewards_dashboard_account_rewards_available']");
	public static final By appActiveRewardXpath = MobileBy.xpath("//*[@id='card_active_reward_image']");
	public static final By appTapToViewXpath = MobileBy.xpath("//*[@id='card_active_reward_scan']");
	public static final By appActiveRewardName = MobileBy.xpath("//android.widget.TextView[@id='active_reward_name']");
	public static final By appCloseRewardsPopUp = MobileBy.xpath("//android.widget.ImageButton[@contentDescription='Navigate up']");

//Favourite Items 
	public static final By appFavouriteItems = MobileBy.xpath("//*[@id='card_order_summary']");
	
//My Account Update
	public static final By appMyAccountOptionXpath = MobileBy.xpath("//android.widget.TextView[@text='My Account']");
	public static final By appUpdateButtonXpath = MobileBy.xpath("//android.widget.Button[@text='UPDATE']");
	public static final By appConsentCheckBoxXpath = MobileBy.xpath("//*[@id='mobile-opt-in']");
	public static final By appMyAccountPageHeaderXpath = MobileBy.xpath("//*[@text='UPDATE PROFILE']");
	public static final By appSuccessMessageXpath = MobileBy.xpath("//*[@class='android.view.View' and ./*[@text='  '] and ./*[@text='Account successfully updated']]");
	
	
	
//IOS 
//Login Logout
	public static final By iosDontAllowButtonXpath=MobileBy.xpath("//*[@text='Horizontal scroll bar, 1 page' and (./preceding-sibling::* | ./following-sibling::*)[./*[./*[./*[@text='Allow Once']]]]]");
	public static final By iosnotificationDontAllowButtonXpath = MobileBy.xpath("//*[@text='Don’t Allow']");
	public static final By iosPopUpClose=MobileBy.xpath("//*[@id='CancelButton']");
	public static final By iosLoginButton=MobileBy.xpath("((//*[@class='UIAScrollView']/*[@class='UIAView'])[2]/*/*/*[@text='Log In'])[1]");
	public static final By iosUserNameTextBoxXpath=MobileBy.xpath("//*[@id='Mobile Number']");
	public static final By iosPaswordTextBoxXpath=MobileBy.xpath("//*[@id='Password']");
	public static final By iosloginHeaderXpath=MobileBy.xpath("//*[@text='LOG IN TO YOUR ACCOUNT']");
	
	public static final By ioswelcomeMessageXpath=MobileBy.xpath("(((//*[@class='UIAScrollView']/*[@class='UIAView'])[2]/*/*[@class='UIAView' and ./parent::*[@class='UIAView']])[13]/*[@class='UIAStaticText' and @width>0])[1]");
	public static final By iosSignInButtonXpath=MobileBy.xpath("//*[@accessibilityLabel='LogInButton']");
	public static final By iosMoreButtonXpath=MobileBy.xpath("//*[@id='MORE']");
	public static final By iosLogOutButtonXpath=MobileBy.xpath("//*[@id='LOG OUT']");
	public static final By ioslogoutValXpath=MobileBy.xpath("((//*[@class='UIAScrollView']/*[@class='UIAView'])[1]/*/*/*[@text='Log in'])[1]");
	public static final By iosCallTheRestaurant=MobileBy.xpath("//*[@id='CALL THE RESTAURANT']");
	public static final By iosPickUpTimeLabel=MobileBy.xpath("//*[contains(@id,'Pick-up time')]");
	//Location Search
	public static final By iosSearchLocationLinkXpath=MobileBy.xpath("((//*[@class='UIAScrollView']/*[@class='UIAView'])[2]/*/*/*/*/*[@text=concat('FIND A NEARBY CHILI', \"'\", 'S')])[1]");
	public static final By iosRestaurantSearchTextBoxXpath=MobileBy.xpath("//*[@placeholder='City, State, ZIP OR Delivery Address']");
	public static final By iosRestaurantAutocompleteTextBoxXpath=MobileBy.xpath("//*[@accessibilityLabel='searchBar']");
	
	//*[@id='Search' and @class='UIATextField']
	//Rewards
	public static final By iosRewardsButtonXpath=MobileBy.xpath("//*[@id='REWARDS']");
	public static final By iosRewardsHeaderXpath=MobileBy.xpath("//*[@text='REWARDS' and @class='UIAStaticText' and ./parent::*[@text='Rewards']]");
	public static final By iosDisplayedRewardCount=MobileBy.xpath("(//*[@text='Rewards']/*[@class='UIAStaticText'])[3]");
	public static final By iosClaimedRewardsXpath=MobileBy.xpath("//*[@text='REWARDS' and @class='UIAStaticText' and ./parent::*[@text='Claimed Rewards']]");
	public static final By iosRewardsSubHeaderXpath=MobileBy.xpath("//*[@text='REWARDS' and @class='UIAStaticText']");
	public static final By iosrewardsImageXpath=MobileBy.xpath("//*[@class='UIAImage']");
	public static final By iostapToViewXpath=MobileBy.xpath("//*[@id='Tap to view']");
	public static final By iosRewardsNameXpath=MobileBy.xpath("(//*[@class='UIAStaticText' and @knownSuperClass='UILabel'])[1]");
	public static final By iosBackButtonXpath=MobileBy.xpath("//*[@id='Back']");
	//*[@text='1']
	
	
	//*[@text='Free Chips & Salsa OR Non-Alcoholic Beverage']
	
	
	
	  public static final By appAddToCart = MobileBy.xpath("//*[@text='ADD TO CART']");
	    public static final By appAddToOrder = MobileBy.xpath("//*[@text='ADD TO ORDER']");
	    public static final String androidAppAddToOrder="//*[@text='ADD TO ORDER']";
	    public static final By iosappClickOrder = MobileBy.xpath("//*[@text='ORDER']");
	    public static final By appClickCheckout = MobileBy.xpath("//*[@text='CHECKOUT »']");
	    public static final By appSelectCarryOut = MobileBy.xpath("//*[@text='Carryout']");
	    public static final By appSelectCurbside = MobileBy.xpath("//*[@text='Curbside']");
	    public static final By appSelectDelivery = MobileBy.xpath("//*[@text='Delivery']");
	    public static final By appContinuePayment = MobileBy.xpath("//*[@text='CONTINUE TO PAYMENT']");
	    public static final By appCardNuber = MobileBy.xpath("//*[@id='card-number']");
	    public static final By appCVV = MobileBy.xpath("//*[@id='cvv']");
	    public static final By appNameOnCard = MobileBy.xpath("//*[@id='nameOnCard']");
	    public static final By appZipcode = MobileBy.xpath("//*[@id='zipcode']");
	    public static final By appPlaceOrder = MobileBy.xpath("//*[@text='PLACE ORDER']");
	    public static final By appDeliveryAddress = MobileBy.xpath("//*[@id='autocomplete']");
		public static final By appAptNo = MobileBy.xpath("//*[@id='suite-no']");
		public static final By appDelDate = MobileBy.xpath("//*[@id='delivery-date']");
//		public static final By 
		public static final String appFirstName ="//*[@id='first-name']";
		public static final String appLastName = "//*[@id='last-name']";
		public static final String appContactNumber = "//*[@id='contact-phone']";
		public static final String appeMail = "//*[@id='email']";
		public static final String appVehicleMake="VEHICLE MAKE";
		public static final String appVehicleModel="VEHICLE MODEL";
		public static final String appVehicleColor="VEHICLE COLOR";
		public static final String appContinueToPayment="CONTINUE TO PAYMENT";
		public static final By appCardNo = MobileBy.xpath("//*[@id='card-number']");
		public static final String appDonationBoxText="//*[@class='roundup-checkbox-label']";
		public static final String appPlaceOrderXpath="//*[contains(@id,'place-order')]";
		public static final By appChangeLocationButton=MobileBy.xpath("//*[@text='CHANGE']");
		public static final String appOrderTotalBeforePlace="//*[@id='pickup-cost']";
		public static final String appContinueToPaymentXpath="//*[@id='continue-to-payment']";
		//*[@id='vehicle-make']
		//*[@id='vehicle-model']";
		//*[@id='vehicle-color']";

		
		//Delivery
	 
		
		
//DOORDASH AUTOMATION		
/**************************************************************************************************************/

		
/**************************************************************************************************************/
//21-06-2021


public static final By iosClearTextXpath=MobileBy.xpath("//*[@id='Clear text']");
    public static final By iosOrderButtonXpath=MobileBy.xpath("//*[@text='ORDER']");
    public static final By iosOrderHeaderXpath=MobileBy.xpath("//*[@text='ORDER' and @class='UIAStaticText']");
    public static final By iosYourOrderHeaderXpath=MobileBy.xpath("//*[@text='YOUR ORDER']");
    public static final By iosCheckoutHeaderXpath=MobileBy.xpath("//*[@text='CHECKOUT']");
    public static final By iosSelectCurbsideModeXpath = By.xpath("//*[text()='Curbside']");
    public static final By iosFinalizeOrderHeaderXpath=MobileBy.xpath("//*[@text='FINALIZE ORDER']");
    public static final By iosCardNoXpath=MobileBy.xpath("//*[@id='*CARD NUMBER' and @class='UIATextField']");
    public static final By iosCVVXpath=MobileBy.xpath("//*[@id='*CVV' and @class='UIATextField']");
    public static final By iosPickerWheelXpath=MobileBy.xpath("//*[@class='UIAPicker']");
    public static final By iosNameOnCardXpath=MobileBy.xpath("//*[@id='NAME ON CARD' and @class='UIATextField']");
    public static final By iosZipcodeXpath=MobileBy.xpath("//*[@id='*BILLING ZIP CODE' and @class='UIATextField']");
    public static final By iosPlaceOrderButtonXpath=MobileBy.xpath("//*[@text='PLACE ORDER']");
    public static final By iosSelectCarryoutModeXpath = By.xpath("//*[text()='Carryout']");
		
	
    
    
    
    public static final By appaddMyVisitButtonXpath = MobileBy.xpath("//*[@text='ADD MY VISIT']");	
	public static final By appenterZipcodeXpath_addmyvisit = MobileBy.xpath("//android.widget.EditText[@id='favorite_chilis_edittext']");	
	//public static final By appselectchilislocXpath = MobileBy.xpath("//android.widget.CheckedTextView[@index='1']");
	//public static final By appselectchilislocXpath = MobileBy.xpath("//android.widget.CheckedTextView[@id='text1' and @resource-id='android:id/text1']");
	public static final By appselectchilislocXpath_addmyvisit = MobileBy.xpath("//*[@id='favorite_chilis_spinner']");
	public static final By appDateofvisitXpath = MobileBy.xpath("//android.widget.EditText[@id='visitdate_edittext']");	
	public static final By appsendDateofvisitXpath_addmyvisit = MobileBy.xpath("//android.widget.NumberPicker[@index='1']");
	public static final By appclickokforvisitdateXpath = MobileBy.xpath("//android.widget.Button[@id='button1']");
	public static final By appreceiptcheckNumberTextBox_addmyvisit = MobileBy.xpath("//android.widget.EditText[@id='receipt_check_number_edittext']");
	public static final By appsubTotalTextBox_addmyvisit = MobileBy.xpath("//android.widget.EditText[@id='subtotal_edittext']");
	public static final String appsubTotalTextBox_AddMyVisit="//android.widget.EditText[@id='subtotal_edittext']";
	public static final By appvisitSubmitButton = MobileBy.xpath("//android.widget.Button[@id='submit_button']");
	public static final By appgetSuccessMessageforAddMyVisit = MobileBy.xpath("//android.widget.TextView[@id='message]");
	public static final By appMenuButtonXpath=MobileBy.xpath("//*[@id='card_home_restaurant_selected_menu']");
	public static final By appOptInSilverWareXpath=MobileBy.xpath("//*[@class='android.view.View' and ./*[@text='Include silverware with my order (fork, knife, spoon and napkins).']]");
	public static final By appChilisMenuHeader=MobileBy.xpath("//*[@text=concat('CHILI', \"'\", 'S MENU')]");
	public static final By appCheckOutHeaderXpath=MobileBy.xpath("//*[@text='CHECKOUT']");
	public static final By appOrderReceiptXpath = MobileBy.xpath("//*[@text='ORDER RECEIPT']");
	public static final By appPriceafterOrderPlace = MobileBy.xpath("//*[contains(@text,'$') and ./parent::*[./parent::*[@id='pickup-total-confirm']]]");
	public static final By appChilisLogoXpath= MobileBy.xpath("//*[@contentDescription='Chilis']");
	public static final By appOrderSuccessMessageXpath = MobileBy.xpath("//*[contains(@text,'THANK YOU FOR ORDERING')]");
	public static final By appDeliveryPickUpTimeXpath=MobileBy.xpath("//*[@id='delivery-time']");
	public static final By appAddMyVisitSuccessMessage=MobileBy.xpath("//*[@id='message']");
	//*[@text='Oldtimer with Cheese']
	//*[@id='card_favorite_addToCart']
	//*[@id='silverware-opt-in-select']
	
	//*[@text='Southwestern Eggrolls' and ./parent::*[contains(@id,'menu-item-name')]]
	
	
	//*[@text='SELECT' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[./*[@text='Coit Road']]]]
	
	//*[contains(@text,'Oldtimer')]
	//*[contains(@text,'Original')]
	//*[contains(@text,'Mix')]
	//*[contains(@text,'Crispy')]
	//*[contains(@text,'Smokehouse')]
	public static final By iosmyAccountOptionXpath=MobileBy.xpath("//*[@id='MY ACCOUNT']");
	public static final By iosaccountPageHeaderXpath=MobileBy.xpath("//*[@text='ACCOUNT' and @class='UIAStaticText']");
	public static final By iosfirstNameTextBox = MobileBy.xpath("//*[@id='firstName']");
	public static final By iosstartOrderXpath=MobileBy.xpath("((//*[@class='UIAScrollView']/*[@class='UIAView'])[2]/*/*/*/*[@text='START ORDER' and @class='UIAStaticText'])[1]");
    public static final By iosappOptInSilverWareXpath=MobileBy.xpath("//*[@text='Include silverware with my order (fork, knife, spoon and napkins).' and @class='UIAStaticText']");
    public static final By iosappCheckOutXpath=MobileBy.xpath("//*[contains(@id,'CHECKOUT')]");
    public static final By appFavoritesAddToCartXpath=MobileBy.xpath("//*[@id='card_favorite_addToCart']");
    public static final By appDeliveryOrderTypeXpath=MobileBy.xpath("//*[@id='order-type-delivery-btn']");
    public static final String appDeliveryAddressTextBox="//*[@id='autocomplete']";
    public static final String appDeliveryAddressTextBoxqa2="//*[@name='deliveryAddress']";
    public static final String appDeliveryAptNo="//*[@id='suite-no']";
    public static final String appDeliveryInstruction="//*[@id='special-instructions']";
    public static final String appDeliveryDate="//*[@id='delivery-date']";
    public static final By appSelectRewardRadioButton=MobileBy.xpath("//*[contains(@id,'member-reward')]");
    public static final String appCustomizeButtonXpath="//android.widget.TextView[@text='CUSTOMIZE ITEM']";
    public static final String appCustomizeItemDropDownXpath="//*[contains(@text,'Ranch')]";
    public static final String androidAppNotFindingCustomizationXpath="//*[@text='Not finding the customizations you need? Please call ']";
    public static final By appExtraCheckBox=MobileBy.xpath("//android.widget.CheckBox[@text='EXTRA']");
    public static final String appDisplayedCustomizeItem="//*[@class='android.widget.ListView']/android.view.View";
   public static final String appFutureDateXpath="//*[contains(@text,'Friday')][1]";
   public static final By reOrderButtonXpath=MobileBy.xpath("//*[@text='REORDER']");
   public static final By orderAgainLinkXpath=MobileBy.xpath("(//*[@id='card_order_reorder'])[1]");
   public static final By restaurantSelectionAlertTitle=MobileBy.xpath("//*[@id='alertTitle']");
   public static final String appAccFirstName="//*[@id='firstName']";
   public static final String appAccLastName="//*[@id='lastName']";
   public static final String appAccEmail="//*[@id='email']";
   public static final String appAccZipCode="//*[@id='postalCode']";
   public static final String appRewardRadioButtonXpath="//*[contains(@id,'member-reward')]";
   public static final String iosAddtoOrderXpath="//*[@id='ADD TO ORDER']";
   public static final String iosSilverWareOptInXpath="//*[@id='Include silverware with my order (fork, knife, spoon and napkins).' and @class='UIAStaticText']";
   public static final String iosCheckOutXpath="//*[@id='CHECKOUT »']";
   public static final String iosContinueToPayment="//*[contains(@id,'CONTINUE TO PAYMENT')]";
   public static final String iosCardNumberTextBoxXpath="//*[contains(@id,'CARD NUMBER')and @class='UIATextField']";
   public static final String iosCVVTextBoxXpath="//*[contains(@id,'CVV') and @class='UIATextField']";
   public static final String iosExpirationMonthXpath="//*[contains(@value,'January') and @id='*EXPIRATION DATE']";
   public static final String iosExpirationYearXpath="//*[contains(@id,'EXPIRATION DATE') and @XCElementType='XCUIElementTypeOther'][2]";
   public static final String iosNameOnCardTextBoxXpath="//*[@id='NAME ON CARD' and @class='UIATextField']";
   public static final String iosZipCodeTextBoxXpath="//*[contains(@id,'BILLING ZIP CODE') and @class='UIATextField']";
   public static final String iosTipTextBoxXpath="//*[@id='Gratuity']";
   public static final String iosDonationCheckBox="//*[contains(@id,'RESEARCH HOSPITAL')]";
   public static final String iosPlaceOrderXpath="//*[contains(@id,'PLACE ORDER')]";
   
   
   
   public static final String iosCurbsideXpath="//*[contains(@id,'Curbside')]";
   public static final String iosPickUpTypeXpath="//*[@text='*PICKUP TIME' and @class='UIAStaticText']";
   public static final String iosCarryoutXpath="//*[contains(@id,'Carryout') and contains(@text,'Carryout') and @class='UIAButton']";
   public static final String iosDeliveryXpath="//*[contains(@id,'Delivery')]";
   public static final String iosPickUpTimeXpath="//*[@text='*PICKUP TIME' and @class='UIAView' and (./preceding-sibling::* | ./following-sibling::*)[@class='UIAView']]";
   public static final String iosDeliveryAddress="//*[@placeholder='Enter a location' and @text]";
   public static final String iosDeliveryAptNoXpath="(//*[@text=concat('Order Pickup Information | Chili', \"'\", 's')]/*[@class='UIATextField'])[2]";
   public static final String iosDeliveryInstructionTextBoxXpath="//*[@placeholder='Example: Place on front doorstep.' and @class='UIATextField']";
   public static final String iosDeliveryTypeXpath="//*[@text='*DELIVERY TIME' and @class='UIAStaticText']";
   public static final String iosVehicleMakeXpath="//*[@text='*VEHICLE MAKE' and @class='UIAStaticText']";
   public static final String iosVehicleModelXpath="//*[@text='*VEHICLE MODEL' and @class='UIAStaticText']";
   public static final String iosVehicleColorXpath="//*[@text='*VEHICLE COLOR' and @class='UIAStaticText']";
   public static final String iosOrderConfirmationPageHeaderXpath="//*[@text=concat('THANK YOU FOR ORDERING FROM CHILI', \"'\", 'S') and @class='UIAStaticText']";
   public static final String iosCustomizeItemButtonXpath="//*[@text='CUSTOMIZE ITEM']";
   public static final String iosCustomizeItemDropDownXpath="//*[contains(@text,'selectbox.name')]";
   public static final String iosExtraCheckBoxXpath="//*[@text='EXTRA' and @class='UIAStaticText']";
   public static final String iosDisplayedCustomizeItemXpath="//*[@class='UIAView' and @width>0 and ./*[@text='•']]";
   public static final String iosQuantityXpath="//*[@text='Select Quantity']";
   public static final String iosDeliveryTimeXpath="//*[@text='*DELIVERY TIME' and @class='UIAView' and (./preceding-sibling::* | ./following-sibling::*)[@class='UIAView']]";
   public static final String iosGuestFirstNameXpath="//*[@id='First Name']";
   public static final String iosGuestLastNameXpath="//*[@text='*LAST NAME' and @class='UIAStaticText']";
   public static final String iosGuestContactNumberXpath="//*[@text='*CONTACT PHONE' and @class='UIAStaticText']";
   public static final String iosGuestEmailAddressXpath="//*[@text='*E-MAIL ADDRESS' and @class='UIAStaticText']";
   public static final String iosAddToCartXpath="//*[@text='ADD TO CART' and @class='UIAStaticText']";
   public static final String iosYourorderHeaderXpath="//*[@text='YOUR ORDER']";
   public static final String iosReorderButtonXpath="//*[@id='REORDER']";
   public static final String iosOrderAgainXpath="//*[@id='ORDER AGAIN']";
   public static final String iosAddMyVisit="//*[@id='ADD MY VISIT']";
   public static final String iosRestaurantZipCodeXpath="//*[@placeholder='Zip Code']";
   public static final String iosDateOfVisitXpath="//*[@placeholder='Date of Visit']";
   public static final String iosCheckNumberXpath="//*[@placeholder='Receipt Check Number']";
   public static final String iosSubTotalXpath="//*[@id='Subtotal']";
   public static final String iosSubmitVisitButton="//*[@id='SUBMIT MY VISIT']";
   public static final String iosAddMyVisitHeaderXpath="//*[@text='ADD MY VISIT' and @class='UIAStaticText']";
   public static final String iosVisitSubmitConfirmationMessage="//*[@text='Submitted successfully' and @class='UIAStaticText']" ;
   public static final String OKbuttoninAddMyVisitXpath="//*[@text='OK']";
   public static final String addRewardRadioButtonXpath="(((//*[@text=concat('Cart | Chili', \"'\", 's')]/*[@class='UIAView'])[14]/*[@class='UIAView'])[3]/*[@class='UIAView' and @width>0])[1]";
   public static final String closeAddRewardButtonXpath="((//*[@text=concat('Cart | Chili', \"'\", 's')]/*[@class='UIAView'])[13]/*[@class='UIAView' and @width>0])[2]";
   public static final String appCardNumber="//android.widget.EditText[@id='card-number']";
   public static final String appCVVNumber="//*[@id='cvv']";
   public static final String appExpirationMonthXpath="//*[@id='month-selector']";
   public static final String androidListViewXpath="//*[@class='android.widget.ListView']";
   public static final String appExpirationYearXpath="//*[@id='year-selector']";
   public static final String appNameOnCardXpath="//*[@id='nameOnCard']";
   public static final String appNameOnCardqa2Xpath="//*[@id='name-on-card']";
   public static final String appZipCodeXpath="//*[@id='zipcode']";
   public static final String appZipCodeqa2Xpath="//*[@id='zip-code']";
   public static final String appGiveTipXpath="//*[@name='tip']";
   public static final By appOrderButton=MobileBy.xpath("//*[@text='ORDER']");
   public static final String appChangeLocationInsXpath="//*[@id='location-bar-change-location']";
   public static final By iosChangeLocation = MobileBy.xpath("//*[@text='Change Location' and @class='UIAStaticText']");
   public static final String iosAppPayWithCreditCardButton = "//*[@text='PAY WITH CREDIT CARD']";
   public static final String iosAppGiftCardTextBox = "//*[@text='CARD NUMBER']";
   public static final String androidAppGiftCardTextBox = "//*[@text='CARD NUMBER']";
   public static final String iosAppApplyGiftCard = "//*[@text='APPLY']";
   public static final String androidAppApplyGiftCard = "//*[@text='APPLY']";
   public static final By browserGiftCardTextBox = By.xpath("//*[@id='gift-card-number']");
   public static final By browserApplyGiftCardButton = By.xpath("//*[text()='Apply']");
   
   
   
   
   public static final String backToMenuButtonXpath="//*[@id='cart-add-food-mobile']";
   
   public static final String deliveryOrderEstimatedArrivalXpath = "//div[@class='heading-secondary status' and @text='  ']";
   public static final String deliveryOrderStatusXpath = "//*[@id='order-delivery-status']";
   
 //DOORDASH AUTOMATION		
   /**************************************************************************************************************/
   public static final By hamburgerMenuButtonXpath=By.xpath("//button[@aria-label='Open Menu']");
   //public static final By hamburgerMenuButtonXpath=By.xpath("//*[@id=\"root\"]/div/div[1]/div[1]/div/div[1]/div[1]/button");
   public static final By siginSignUpButtonXpath=By.xpath("//span[text()='Sign Up or Sign In']");
   public static final By emailTextBoxXpath=By.xpath("//*[@type='email']");
   public static final By passwordTextBoxXpath=By.xpath("//*[@type='password']");
   public static final By signinButtonXpath=By.xpath("//*[@id='login-submit-button']/div/span/div");
   public static final By signinPageHeaderXpath=By.xpath("//*[@id='root']/div/div[1]/div/div[2]/div/div/span");
   public static final By doorDashHeaderXpath=By.xpath("//*[@id='root']/div/div[1]/div[1]/div/div[2]");
   public static final By accountNameXpath=By.xpath("//*[@data-anchor-id='HamburgerMenuProfilePageLink']/div/div[2]/span[2]");
   public static final By signOutButtonXpath=By.xpath("//*[@id='root']/div/div[3]/div/div[2]/div/div/div[2]/div/a[10]/div/div[2]/span");
   public static final By doorDashHomePageLogo=By.xpath("//*[@id='root']/div/div[1]/div[1]/div/div");
   public static final By locationChangeLinkXpath=By.xpath("//*[@id='root']/div/div[1]/div[1]/div/div[1]/div[2]/div/div/div/button/div/div/div/span");		
   public static final By addressTextBoxXpath=By.xpath("//input[@placeholder='Address']");
   public static final By addressLayoutXpath=By.xpath("//*[@id='layout-address-picker']/div/div[1]/div/div");
   public static final By saveButtonXpath=By.xpath("//*[@id='layout-address-picker']/div/div[1]/div/div/div[6]/button[2]/div/div/div/span");
   public static final By displayedAddressXpath=By.xpath("//*[@id='root']/div/div[1]/div[2]/div/div[1]/div[9]/div/div[2]/span[3]");


   //BY SUMAN
   /************************************************************************************************************************/
   public static final By chilisLogo_dd=By.xpath("//header/div[1]/div[1]/div[1]/picture[1]/img[1]");
   public static final By slidingMenuClose_dd=By.xpath(" //button[@aria-label='Close']");  
   public static final By verifyAddressXpath_dd=By.xpath("//body/div[@id='root']/div[1]/div[1]/div[2]/div[1]/div[1]/div[9]/div[1]/div[2]/span[3]");
   public static final By locationSelect_dd=By.xpath("(//div[@kind='BUTTON/FLAT_SECONDARY'])[3]");
   public static final By verifyInputBox_dd=By.xpath("//label[contains(text(),'Search for a new address')]");
   public static final By enterAddress_dd=By.xpath("//body[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[2]/input[1]");
   public static final By clickSave_dd=By.xpath("//span[contains(text(),'Save')]");
   public static final By verifyAddress_dd=By.xpath("//span[contains(text(),'101 Seal Dr')]");
   public static final By addToOrderbutton_dd=By.xpath("//span[contains(text(),'Add to cart ')]");  
   public static final By verifyOrderQuantity_dd=By.xpath("//div[contains(text(),' ×')]");
   public static final By checkout_dd=By.xpath("//div[contains(text(),'Checkout')]");
   public static final By verifyMenuItem_dd=By.xpath("//body/div[@id='root']/div[1]/div[1]/div[2]/div[1]/div[1]/div[7]/div[2]/div[2]/div/button/div/div/div/span/div/div/div/div/span");
   public static final By deliveryButton_dd=By.xpath("//button[@role='radio' and @data-anchor-id='DeliveryMethodToggle'] ");
   public static final By pickupButton_dd=By.xpath("//button[@role='radio' and @data-anchor-id='PickupMethodToggle']");
   public static final By asapButton_dd=By.xpath("//span[contains(text(),'ASAP')]");
   public static final By orderConfirmationAsap_dd=By.xpath("//h1[contains(text(),'Confirming your order')]"); 
   public static final By orderConfirmationScheduled_dd=By.xpath("//h1[contains(text(),'Scheduled')]");
   public static final String rewardOption = "(((//*[@text='main']/*[@class='UIAView'])[13]/*[@class='UIAView'])[1]/*[@class='UIAView' and @width>0])[1]";
   public static final String rewardItemIOSApp = "//*[@text='Chips and Salsa' and @class='UIAStaticText']";
   public static final String rewardItemName = "//*[@text='Chips & Salsa' and @class='UIAStaticText' and ./parent::*[@text='Chips & Salsa']]";
   public static final String discountLabelIOSApp = "//*[@id='Discounts']";
   public static final String discountAmountIOSApp = "(//*[@text='main']/*[@class='UIAView' and ./*[@class='UIAStaticText']])[6]";
   public static final String rewardTitleIOSApp = "//*[@id='Free Chips & Salsa OR Non-Alcoholic Beverage']";
   //*[@role='radiogroup' and @aria-label='Schedule for later']/button[1]


   public static final By selectScheduleForLater_DD=By.xpath("//div[@kind='BUTTON/PRIMARY']//span[contains(text(),'Schedule for later')]");
   public static final By verifyPickupOrderPlaced_DD=By.xpath("//h1[contains(text(),'Scheduled')]");

   //Created by Ayushman
   /********************************************UBER EATS ELEMENTS******************************************************************/

   public static final By validateHomePage=By.xpath("//hh1[contains(text(),'')]");	
   //public static final By signInButton=By.xpath("//header/div[1]/div[1]/div[1]/div[1]/a[2]");	
   public static final By validateSignInPage=By.xpath("//h4[contains(text(),\"Welcome back\")]");	
   public static final By appetizers=By.xpath("//button[contains(text(),'Appetizers')]");
   public static final By menuCategoryList=By.xpath("//nav[@role='navigation']/div/button");
   public static final By itemIncrease=By.xpath("//body/div[@id='root']/div[@id='wrapper']/div[4]/div[1]/div[1]/div[2]/div[4]/div[1]/div[1]/div[2]/div[1]/div[1]/button[2]");
   public static final By addToOrderUE=By.xpath("//body/div[@id='root']/div[@id='wrapper']/div[4]/div[1]/div[1]/div[2]/div[4]/div[1]/div[1]/div[2]/button[1]");
   //public static final By cart=By.xpath("//*[@id=\"wrapper\"]/header/div[2]/div/div[6]/div/button");
   public static final By itemDecrease=By.xpath("//body/div[@id='root']/div[@id='wrapper']/div[4]/div[1]/div[1]/div[2]/div[4]/div[1]/div[1]/div[2]/div[1]/div[1]/button[1]");
   public static final By verifyCart=By.xpath("//div[contains(text(),'Group order')]");
   //public static final By checkoutButton=By.xpath("//*[@id=\"wrapper\"]/header/div/div/div[5]/div[2]/div/div[3]/div[7]/a");
   //public static final By placeOrderButtonUE=By.xpath("//button[contains(text(),'Place order')]");
   public static final By scheduleOption=By.xpath("//span[contains(text(),'Schedule')]");
   public static final By scheduleButton=By.xpath("//button[contains(text(),'Schedule')]");
   public static final By selectScheduleTime=By.xpath("//body/div[@id='root']/div[@id='wrapper']/div[4]/div[1]/div[1]/div[2]/div[3]/div[4]/div[1]/select[1]");
   public static final By orderConfirmLT=By.xpath("//div[contains(text(),'Your order is scheduled')]");
   //public static final By orderConfirmASAP=By.xpath("//div[contains(text(),'Preparing your order...')]");
   public static final By orderConfirmASAP=By.xpath("//div[contains(text(),'Preparing your order...')]");
   public static final By priorityOption=By.xpath("//input[@id='PREMIUM_DELIVERY-option']");
   public static final By priorityOptionClick=By.xpath("//span[contains(text(),'Priority')]");
   public static final By verifyOrdersPage=By.xpath("//a[contains(text(),'Get Help')]");
   public static final By showMoreButton=By.xpath("//button[contains(text(),'Show more')]");
   public static final By listOfOrders=By.xpath("//*[@id=\"main-content\"]/div/div");

   //Suman- begin(UE)
   /*********************************************************************************************************************/
   public static final By signInButton=By.xpath("//header/div[1]/div[1]/div[1]/div[1]/a[2]");	 
   public static final By userInputBox=By.xpath("//input[@id='useridInput']");
   public static final By nextButton=By.xpath("//span[contains(text(),'Next')]");
   public static final By passInputBox=By.xpath("//input[@id='password']");
   public static final By deliverAdressInputbox=By.xpath("//input[@id='location-typeahead-home-input']");
   public static final By findFoodButton=By.xpath("//button[contains(text(),'Find Food')]");
   public static final By verifyAddressXpath=By.xpath("//*[@id='main-content']/div[4]/div/div[1]/p/text()"); 
   public static final By chilisstore6_title=By.xpath("//h1[contains(text(),\"Chili's Test Store 6\")]");
   public static final By addToOrder_button=By.xpath("//div[contains(text(),' to order')]");
   public static final By verifyOrderItem=By.xpath("//header/div[1]/div[1]/div[5]/div[2]/div[1]/div[3]/ul[1]/li[1]/a[1]/div[1]/div[1]/div[1]");
   public static final By cart=By.xpath("//div[contains(text(),'Cart')]");
   public static final By placeOrderButtonUE=By.xpath("//button[contains(text(),'Place order')]");
   public static final By selectChangeQuantity=By.xpath("//select"); //button[@aria-label='checkout']/div/text()[3]
   public static final By verifyCartQuantity=By.xpath("//button[@aria-label='checkout']/div");
   public static final By checkoutButton=By.xpath("//a[@rel='nofollow' and contains(text(),'Go to checkout')]");
   //public static final By scheduleOption=By.xpath("//span[contains(text(),'Schedule')]");
   public static final By deliveryButton_Xpath=By.xpath("//button[contains(text(),'Deliver now')]");
   public static final By viewOrders=By.xpath("//a[contains(text(),'View Orders')]");
   public static final By upcomingOrders=By.xpath("//div[contains(text(),'Upcoming Orders')]");
   public static final By verifyTimeZone=By.xpath("//div[contains(text(),'Upcoming Orders')]/following::div[1]/div/div[3]/div/div[2]/div");
   public static final By pastOrderItemName=By.xpath("//div[contains(text(),'Past Orders')]/following-sibling::div[2]/div/div/ul/li/div/div/ul/li/div/div[3]/div[1]/div");
   public static final By pastOrderItemPrice=By.xpath("//div[contains(text(),'Past Orders')]/following-sibling::div[2]/div/div/div[2]/div");  
   public static final By saveButtonUE=By.xpath("//div[contains(text(),'Save')]");
   public static final By addItemUE=By.xpath("//div[contains(text(),'Add items')]");
   public static final By otherButtonUE=By.xpath("//a[contains(text(),'Other')]");
   public static final By enterTipUE=By.xpath("//input[@type='text']"); 
   public static final By saveButton=By.xpath("//button[contains(text(),'Save')]");
   public static final By itemName=By.xpath("//h3[contains(text(),'Your items')]/following::li[1]/a/div/div/div[1]");
   public static final By subTotal=By.xpath("//div[contains(text(),'Subtotal')]/following::div[1]/span");
   public static final By tip=By.xpath("//h2[contains(text(),'Add a tip')]/following::div[3]");
   public static final By Total=By.xpath("//div[contains(text(),'Total')]/parent::div");

   /*****************************************************srividhya***********************************************************************/
   public static final By menuCategory=By.xpath("//main[@id='main-content']//following::h4");
   public static final By menuClosebutton=By.xpath( "//button[@aria-label='Close']");
   public static final By menuDescriptionDisplay=By.xpath("//div[@id='wrapper']/div[4]/div/div/div[2]/div[4]/div/div/div[1]/div/div");
   public static final By menuPriceDisplay=By.xpath("//button/div[3]");
   public static final By summaryOrder = By.xpath("//div[contains(text(),'Upcoming Orders')]/following::li[1]/div/div[3]/div[1]"); 
   //public static final By clickMain=By.xpath("//div[@id='wrapper']/div[2]/header/div/div/button");
   //public static final By clickOrders = By.xpath("//div[@id='wrapper']/div[1]/div/div/div[1]/a[1]/div[3]");
   //public static final By viewOrder = By.xpath("//main[@id='main-content']/div/div[2]/div/div[3]/div/div[2]/a");
   
 //DOORDASH AUTOMATION		
   /**************************************************************************************************************/
   public static final By hamburgerMenuButtonXpath_DD=By.xpath("//button[@aria-label='Open Menu']");
   public static final By siginSignUpButtonXpath_DD=By.xpath("//span[text()='Sign Up or Sign In']");
   public static final By emailTextBoxXpath_DD=By.xpath("//*[@type='email']");
   public static final By passwordTextBoxXpath_DD=By.xpath("//*[@type='password']");
   public static final By signinButtonXpath_DD=By.xpath("//*[@id='login-submit-button']/div/span/div");
   public static final By signinPageHeaderXpath_DD=By.xpath("//*[@id='root']/div/div[1]/div/div[2]/div/div/span");
   public static final By doorDashHeaderXpath_DD=By.xpath("//*[@id='root']/div/div[1]/div[1]/div/div[2]");
   //public static final By accountNameXpath_DD=By.xpath("//*[@id='root']/div/div[3]/div/div[2]/div/div/div[2]/div/a[5]/div/div[2]/span[2]");
   public static final By accountNameXpath_DD=By.xpath("//*[@data-anchor-id='HamburgerMenuProfilePageLink']//span[2]");
   public static final By signOutButtonXpath_DD=By.xpath("//*[@id='root']/div/div[3]/div/div[2]/div/div/div[2]/div/a[10]/div/div[2]/span");
   public static final By doorDashHomePageLogo_DD=By.xpath("//*[@id='root']/div/div[1]/div[1]/div/div");
   public static final By locationChangeLinkXpath_DD=By.xpath("//*[@id='root']/div/div[1]/div[1]/div/div[1]/div[2]/div/div/div/button/div/div/div/span");		
   public static final By addressTextBoxXpath_DD=By.xpath("//input[@placeholder='Address']");
   public static final By addressLayoutXpath_DD=By.xpath("//*[@id='layout-address-picker']/div/div[1]/div/div");
   public static final By saveButtonXpath_DD=By.xpath("//*[@id='layout-address-picker']/div/div[1]/div/div/div[6]/button[2]/div/div/div/span");
   public static final By displayedAddressXpath_DD=By.xpath("//*[@id='root']/div/div[1]/div[2]/div/div[1]/div[9]/div/div[2]/span[3]");


   //Created by Ayushman-DD
   /**************************************************************************************************************/
   public static final By closeSlidingMenu_DD=By.xpath("//button[@aria-label='Close' and @kind='BUTTON/PLAIN']");
   public static final By locationSelect_DD=By.xpath("//button[@aria-controls='layout-address-picker']//span");
   public static final By verifyInputBox_DD=By.xpath("//label[contains(text(),'Search for a new address')]");
   public static final By enterAddress_DD=By.xpath("//input[@placeholder='Address']");
   //public static final By enterAddress_DD=By.xpath("//body[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[2]/input[1]");
   //public static final By verifyAddress_Xpath_DD=By.xpath("//body/div[@id='root']/div[1]/div[1]/div[2]/div[1]/div[1]/div[9]/div[1]/div[2]/span[3]");
   public static final By verifyAddress_Xpath_DD=By.xpath("//span[contains(text(),'delivered from')]/parent::div/span[3]");
   public static final By clickSave_DD=By.xpath("//span[contains(text(),'Save')]");
   public static final By verifyAddress_DD=By.xpath("//span[contains(text(),'101 Seal Dr')]");
   public static final By addToOrderbutton_DD=By.xpath("//span[contains(text(),'Add to cart ')]"); 
   //span[contains(text(),'The Boss Burger*')]/../span/span[contains(text(),'Black Beans')]    verify added subitems
   //public static final By verifyOrderQuantity=By.xpath("(//div[@KIND='BUTTON/FLAT_SECONDARY'])[24]/div/div/span/span/div");
   public static final By checkout_DD=By.xpath("//div[contains(text(),'Checkout')]/parent::*");
   public static final By amountDue_DD=By.xpath("//span[contains(text(),'Amount Due')]");
   public static final By placeOrder_DD=By.xpath("//span[contains(text(),'Place Order')]");
   public static final By tipThree_DD=By.xpath("//button[@data-anchor-id='TipPickerOption']//div[contains(text(),'$3.00')]");
   public static final By tipFour_DD=By.xpath("//button[@data-anchor-id='TipPickerOption']//div[contains(text(),'$4.00')]");
   public static final By tipOther_DD=By.xpath("//div[contains(text(),'Other')]");
   public static final By otherTipInput_DD=By.xpath("//input[@data-anchor-id='OtherTipInput']");
   public static final By verifyZeroTip_DD=By.xpath("//div[@data-testid='Dasher Tip']//span[contains(text(),'$0.00')]");
   public static final By verifyThreeTip_DD=By.xpath("//div[@data-testid='Dasher Tip']//span[contains(text(),'$3.00')]");
   public static final By verifyFourTip_DD=By.xpath("//div[@data-testid='Dasher Tip']//span[contains(text(),'$4.00')]");
   public static final By menuCategoriesList_DD=By.xpath("//div//button//span/span[@color='TextSecondary']");
   public static final By increaseItem_DD=By.xpath("//button[@data-anchor-id='IncrementQuantity']");
   public static final By decreaseItem_DD=By.xpath("//button[@data-anchor-id='DecrementQuantity']");
   public static final By saveButton_DD=By.xpath("//span[contains(text(),'Save')]");
   public static final By verifyEmptyCart_DD=By.xpath("//span[contains(text(),'Your cart is empty')]");
   public static final By pickupOption_DD=By.xpath("//span[contains(text(),'Pickup')]");
   public static final By verifyPickupOption_DD=By.xpath("//span[contains(text(),'Pickup Time')]");
   public static final By scheduleLater_DD=By.xpath("//span[contains(text(),'Schedule for later')]");
   public static final By verifyPickupScheduleLater_DD=By.xpath("//span[contains(text(),'Select a Pickup Date')]");
   public static final By verifyDeliveryScheduleLater_DD=By.xpath("//span[contains(text(),'Select a Delivery Date')]");
//   public static final By selectTomorrow_DD=By.xpath("//span[contains(text(),'TMR')]/parent::div");
   public static final By placePickupOrder_DD=By.xpath("//span[contains(text(),'Place Pickup Order')]");
   public static final By verifyScheduleOrderPlaced_DD=By.xpath("//h1[contains(text(),'Scheduled')]");
   public static final By crossButton_DD=By.xpath("//button[@aria-label='Close  modal']");
   public static final By asapButton_DD=By.xpath("//span[contains(text(),'ASAP')]");
   public static final By orderConfirmationAsap_DD=By.xpath("//h1[contains(text(),'Confirming your order')]"); 
   public static final By orderConfirmationScheduled_DD=By.xpath("//h1[contains(text(),'Scheduled')]");
   public static final By deliveryButton_DD=By.xpath("//button[@role='radio' and @data-anchor-id='DeliveryMethodToggle'] ");
   public static final By verifyOrderQuantity_DD=By.xpath("//div[contains(text(),' ×')]");
   public static final By verifyMenuItem_DD=By.xpath("//body/div[@id='root']/div[1]/div[1]/div[2]/div[1]/div[1]/div[7]/div[2]/div[2]/div/button/div/div/div/span/div/div/div/div/span");
   public static final By pickupButton_DD=By.xpath("//button[@role='radio' and @data-anchor-id='PickupMethodToggle']");
   public static final By addMoreItems_DD=By.xpath("//span[contains(text(),'Add more items')]");
   public static final By validateCountOne_DD=By.xpath("//div[contains(text(),'1 ×')]");
   public static final By increaseCount_DD=By.xpath("//button[@aria-label='add additional to cart']");
   public static final By validateCountTwo_DD=By.xpath("//div[contains(text(),'2 ×')]");
   public static final By decreaseCount_DD=By.xpath("//button[@aria-label='remove from cart']");
   public static final By menuCategory_DD=By.xpath("//div[@data-anchor-id='MenuItem']");
   public static final By totalCost_DD=By.xpath("//span[@data-anchor-id='AmountDue']");
   public static final By itemNames_DD=By.xpath("//div[@data-anchor-id='OrderItemContainer']//span[@color='TextPrimary']");
   public static final By itemQtys_DD=By.xpath("//div[@data-anchor-id='OrderItemContainer']//button//span/span/div");
   public static final By tipOther=By.xpath("//div[contains(text(),'Other')]");
   public static final By otherTipInput=By.xpath("//input[@data-anchor-id='OtherTipInput']");
   public static final By IJWDisplayXpath=By.xpath("//h1[contains(text(),\"It's Just Wings\")]");
   public static final By IJWOpenMenuXpath=By.xpath("//button[@aria-label='Open Menu']");
   public static final By IJWsiginSignUpButtonXpath=By.xpath("//span[contains(text(),'Sign Up or Sign In')]");
   public static final By IJWsigninPageHeaderXpath=By.xpath("//*[@id='root']/div/div[1]/div/div[2]/div/div/span");
   public static final By IJWemailTextBoxXpath=By.xpath("//input[@data-anchor-id='IdentityLoginPageEmailField']");
   public static final By IJWpasswordTextBoxXpath=By.xpath("//input[@data-anchor-id='IdentityLoginPagePasswordField']");
   public static final By IJWsigninButtonXpath=By.xpath("//button[@id=\"login-submit-button\"]/div/span/div");
   public static final By IJWdoorDashHeaderXpath=By.xpath("//a[@data-accessibility-id='header-homepage-link']");
   public static final By IJWaccountNameXpath=By.xpath("//span[(text()='Account')]//following::span[1]");
   public static final By IJWsignOutButtonXpath=By.xpath("//a[@data-anchor-id='UserLogOutButton']");
   public static final By IJWMenuDesc=By.xpath("//div[@data-anchor-id='MenuItem']//following::span/div/div/div/div[1]/span");//use for loop 37
   //public static final By IJWlocClickXpath=By.xpath("(//div[@id='root']//following::div/span)[5]");
   public static final By IJWlocClickXpath=By.xpath("//button[@aria-controls='layout-address-picker']");
   public static final By IJWEnterAddressTextboxXpath=By.xpath("//input[@placeholder='Address']");
   public static final By IJWaddressSaveButtonXpath=By.xpath("//button[@data-anchor-id='AddressEditSave']");
   public static final By IJWmenuItemsXpath=By.xpath("//div[@data-anchor-id='MenuItem']");
   public static final By IJWAfterSignOutXpath=By.xpath("//span[contains(text(),'Restaurants and more, delivered to your door')]");
   public static final By IJWRequiredOptionsXpath=By.xpath("//span[contains(text(),'Required')]");
   public static final By IJWmenuCategory=By.xpath("//div[@data-anchor-id='MenuItem']");
   public static final By IJWOpenMenucat=By.xpath("//button[@aria-label='Show menu categories']");
   public static final By IJWMenucat=By.xpath("//div[@id='Popover-9']//following::div[2]/div[1]/span/span");
   public static final By IJWMainMenuXpath=By.xpath("//div[@id='root']/div/div[1]/div[2]/div[1]/div[1]/div[5]/div[2]/div");
   public static final By IJWclickMainMenuxpath=By.xpath("(//div[@id='root']//following::button)[6]");
   public static final By IJWcloseMenuItemXpath=By.xpath("//div[@id='root']/div/div[4]/div/div[2]/div/div/div[1]/button");
   public static final By selectTomorrow_DD=By.xpath("//span[contains(text(),'TMR')]/parent::div");
   public static final By IJWValidateAddress_bottom=By.xpath("//span[contains(text(),'delivered from')]//following-sibling::span[2]");
   //public static final By IJWValidateClosebuttonXpath=By.xpath("//button[@aria-label='Close']");
   public static final By IJWValidateClosebuttonXpath = By.xpath("//div[@id='root']//following::button[@aria-label='Close']");
   public static final By IJWHomeButton=By.xpath("//div[@id='root']/div/div[3]/div/div[2]/div/div/div[2]/div/a[1]/div/div[2]/span");
   public static final By IJWFullMenu=By.xpath("//span[text()='Full Menu']");
   public static final By IJWMenuItems=By.xpath("//div[@data-anchor-id='MenuItem']//following::span[2]");//use for loop to click on menu item
   public static final By validateStoreavailability=By.xpath("(//h1[contains(text(),\"It's Just Wings\")]//following::span)[12]");
   public static final By IJWSelectDashertip=By.xpath("//button[@data-anchor-id='TipPickerOption']/span/div"); //use for loop 4 elements
   public static final By IJWEnterOtherTip=By.xpath("//input[@data-anchor-id='OtherTipInput']");
   public static final By IJWAddmoreItem=By.xpath("//span[text()='Add more items']");
   public static final By IJWIncreaseQty=By.xpath("//button[@data-anchor-id='IncrementQuantity']");
   public static final By IJWrequired1Xpath=By.xpath("//input[@kind='RADIO']");
   public static final By IJWPickupXpath=By.xpath("//span[contains(text(),'Pickup')]");
   public static final By IJWScheduleforLaterXpath=By.xpath("//span[contains(text(),'Schedule for later')]");
   public static final By IJWTodayxpath=By.xpath("//span[contains(text(),'TODAY')]");
   public static final By IJWscheduleTimeXpath=By.xpath("//span[contains(text(),'11:05 AM-11:15 AM')]");
   public static final By IJWOrderTotalXpath=By.xpath("//span[@data-anchor-id='OrderCartTotal']");
   public static final By IJWAmountDueXpath=By.xpath("//span[@data-anchor-id='AmountDue']");
   public static final By IJWplacePickupOderXpath=By.xpath("//span[contains(text(),'Place Pickup Order')]");
   public static final By IJWRemoveItemXpath=By.xpath("//button[@data-anchor-id='RemoveOrderCartItemButton']");
   public static final By IJWFlavor8or11Xpath=By.xpath("//div[@aria-labelledby='optionList_Choose your wing flavor']//following-sibling::label/div[1]/span");//11 for combo , 12 for wings
   public static final By IJWFlavor1Xpath=By.xpath("//div[@aria-labelledby='optionList_Choose your 1st wing flavor']//following-sibling::label/div[1]/span"); // for loop 12
   public static final By IJWFlavor2Xpath=By.xpath("//div[@aria-labelledby='optionList_Choose a 2nd wing flavor']//following-sibling::label/div[1]/span"); //12
   public static final By IJWFlavor3Xpath=By.xpath("//div[@aria-labelledby='optionList_Choose a 3rd wing flavor']//following-sibling::label/div[1]/span"); //12
   public static final By IJWFlavor4Xpath=By.xpath("//div[@aria-labelledby='optionList_Choose a 4th wing flavor']//following-sibling::label/div[1]/span"); //12
   public static final By IJWBeverageXpath=By.xpath("//div[@aria-labelledby='optionList_Beverage']//following-sibling::label/div[1]/span");//6
   public static final By IJWDrinksReqXpath=By.xpath("//div[@aria-labelledby='optionList_Choice']//following-sibling::label/div[1]/span");//6
   public static final By IJWPieFlavorsXpath=By.xpath("//div[@aria-labelledby='optionList_Pick up to 4 flavors']//following-sibling::div[1]/div/div/span"); //5
   public static final By IJWCheckoutButton=By.xpath("//a[@data-anchor-id='CheckoutButton']");
   public static final By IJWdisplayPlaceOrderxpath=By.xpath("(//button[@data-anchor-id='PlaceOrderButton'])[2]");
   public static final By IJWOrderNameBeforeXpath=By.xpath("//div[@data-anchor-id='OrderItemContainer']//following::span/div/span[1]");
   public static final By IJWOrderQtyBeforeXpath=By.xpath("//div[@data-anchor-id='OrderItemContainer']//descendant::div[3]/span");
   public static final By IJWOrderMenuBeforeXpath=By.xpath("//div[@data-anchor-id='OrderItemContainer']//descendant::div[1]/div[2]//div/span[1]");//use for loop - contains Qty then menu name for all the items
   public static final By IJWOptionalDessertXpath=By.xpath("//div[@aria-labelledby='optionList_Dessert']//following-sibling::label/div[1]/span"); //1
   public static final By IJWOptionalDrinksXpath=By.xpath("//div[@aria-labelledby='optionList_Extra Drinks and Fries']//following-sibling::label/div[1]/span");//7
   public static final By IJWOptionalSauceXpath=By.xpath("//div[@aria-labelledby='optionList_Add Extra Sauce/Ranch']//following-sibling::label/div[1]/span"); //10
   public static final By IJWOptionalsXpath=By.xpath("//span[text()='Dessert']//following::label/div[1]/span");
   public static final By IJWBackToMenuXpath=By.xpath("//span[text()='Back to Menu']");
   public static final By IJWOrderQtyBefore=By.xpath("//div[@data-anchor-id='OrderItemContainer']//descendant::div[2]/span[1]");
   public static final By IJWSubTotalAfter=By.xpath("//span[text()='Subtotal']//following::div[2]/span");
   public static final By IJWTotalAfter=By.xpath("//span[text()='Total']//following::div[2]/span");
   //public static final By IJWViewOrdersXpath=By.xpath("//div[@id='root']/div/div[3]/div/div[2]/div/div/div[2]/div/a[4]/div/div[2]/span");
   public static final By IJWViewOrdersXpath=By.xpath("//span[text()='Orders']");
   public static final By IJWValidateViewOrderXpath=By.xpath("//div[@id='root']/div/div[1]/div[2]/div/div/span[1]");
   public static final By IJWOrdernameXpath=By.xpath("//div[@id='root']/div/div[1]/div[2]/div/div/div[1]/a/div/div[1]/span[2]/div/div/div");
   public static final By IJWOrderPriceXpath=By.xpath("//div[@id='root']/div/div[1]/div[2]/div/div/div[1]/a/div/div[1]/span[2]/div/div/text()[3]");
   public static final By IJWOrderTypeXpath=By.xpath("//div[@id='root']/div/div[1]/div[2]/div/div/div[1]/a/div/div[1]/span[2]/div/div/text()[1]");
   public static final By IJWASAPAfterOrderTime=By.xpath("//div[text()='Order Details']//following::div[7]/span[2]"); //use for loop it was 7 for ASAP
   public static final By IJWASAPAfterOrderMenu=By.xpath("//div[text()='Order Details']//following::div[14]/span");
   public static final By IJWASAPAfterOrderchoices=By.xpath("//div[text()='Order Details']//following::div[15]/span");//use for loop
   public static final By IJWASAPAfterOrdertotal=By.xpath("//span[text()='Total']//following::div[2]/span"); 
   public static final By IJWASAPAfterOrdeSubtotal=By.xpath("//span[text()='Subtotal']//following::div[2]/span");
   public static final By IJWASAPAfterOrderQty=By.xpath("//div[text()='Order Details']//following::div[11]/div/div[1]/span");
   public static final By IJWDisplayAfterOrderDetails=By.xpath("//div[text()='Order Details']//following::span");// upto 24 of 27 check for null value
   public static final By IJWAfterOrderDetails=By.xpath("//div[text()='Order Details']");
   public static final By IJWAfterAddressTime=By.xpath("//span[text()=\"It's Just Wings\"]//following::div[1]/div/span[2]");
   public static final By IJWAfterOrdersList=By.xpath("//span[text()=\"It's Just Wings\"]//following::div[4]/div");
   public static final By IJWAfterOrderPriceList=By.xpath("//span[text()=\"It's Just Wings\"]//following::div[21]/div/div/div/span"); //use for loop 
   public static final By IJWAfterOrderPriceDetails=By.xpath("//span[text()='Subtotal']|//span[text()='Subtotal']//following::div/span");//use for loop
   public static final By IJWPickupTax=By.xpath("(//div[text()='Estimated']//following::span)[3]");
   public static final By IJWPickupAfterOrderAddr=By.xpath("//div[text()='Order Details']//following::div[4]/span");
   public static final By IJWPickupAfterOrderMenu=By.xpath("//div[text()='Order Details']//following::div[11]/span");
   public static final By IJWPickupAfterOrderchoices=By.xpath("//div[text()='Order Details']//following::div[12]/span");//use for loop
   public static final By IJWPickupAfterOrdertotal=By.xpath("//span[text()='Subtotal']//following::div[@size='8']/span"); //use for loop 4
   public static final By IJWPickupAfterOrderQty=By.xpath("//div[text()='Order Details']//following::div[11]/div/div[1]/span");
   public static final By IJWSubtotal=By.xpath("//span[text()='Subtotal']//following::span[1]");
   public static final By IJWDoordashCredits=By.xpath("//span[text()='DoorDash Credits']//following::span[1]");
   public static final By IJWScheduletime=By.xpath("//div[@aria-labelledby='checkout-time-panel-label']/div[2]"); //have to test
   public static final By IJWorderaddrDetails=By.xpath("//div[@aria-labelledby='checkout-time-panel-label']//following::div/span[1][@display='block']/span"); //list of 4 addr,phno , qty have to check 
   public static final By IJWDeliveyorPickup=By.xpath("//div[@aria-label='Delivery or pickup selector']/button[@aria-pressed='true']/div/div/div/span");
   public static final By IJWASAPorLater=By.xpath("//div[@aria-labelledby='checkout-time-panel-label']/div/button[@aria-checked='true']/div/div/div/span");
   public static final By IJWDeliveryFee=By.xpath("//div[text()='Delivery']//following::span[3]");
   public static final By IJWDashertip=By.xpath(" //div[text()='Dasher']//following::span[2]");
   public static final By IJWDeliveryTax = By.xpath("//div[text()='Fees & Estimated']//following::div[9]/span");
   public static final By IJWDeliveryASAPTime=By.xpath("//div[@aria-labelledby='checkout-time-panel-label']/div[1]");
   public static final By IJWOrderIP1=By.xpath("//span[text()='In Progress']//following::a[@data-anchor-id='OrderHistoryOrderItem'][1]");
   public static final By IJWOrderInProgress=By.xpath("//span[text()='In Progress']//following::div[1]/a[@data-anchor-id='OrderHistoryOrderItem']");
   public static final By IJWPreviousOrders=By.xpath("//span[text()='Previous Orders']//following::a[@data-anchor-id='OrderHistoryOrderItem']");
   //public static final By IJWIJWDisplay=By.xpath("//span[text()=\"It's Just Wings\"]");
   public static final By IJWReorderitems=By.xpath("//button[@aria-label='Open Reorder Item']/div[1]/span[1]");//use for loop to get the items.
   public static final By IJWReorderDates=By.xpath("//div[@data-anchor-id='MenuItemModalBody']//following::div[@role='button']/div/span");//use for loop
   public static final By IJWscheduleforLater=By.xpath("//div[@aria-label='Toggle ASAP']/button[2]");
   public static final By IJWDeliveryDates=By.xpath("//div[@aria-label='Day Picker']//descendant::div[4]/span[1]");
   //public static final By IJWDeliveryDates=By.xpath("//div[@aria-label='Day Picker']//descendant::button");
   public static final By IJWIJWDisplaysvg=By.xpath("(//*[name()='svg'])[6]");
   public static final By IJWClickon1x=By.xpath("//button[@data-anchor-id='OrderCartItem']//child::button[@shape='Pill']");//use for loop to get to the item 
   public static final By IJWPlusClick=By.xpath("//button[@aria-label='add additional to cart']");
   public static final By IJWDec1x=By.xpath("//button[@aria-label='remove from cart']");
   public static final By IJWcartitems=By.xpath("//button[@data-anchor-id='OrderCartItem']//following::span[1]/div[1]/span[1]"); //loop thro the cart items.
   public static final By IJWAfterOrderPickupTime=By.xpath("//span[text()=\"It's Just Wings\"]//following::span[3]");
   public static final By IJWAfterPickupOrdersList=By.xpath("//span[text()=\"It's Just Wings\"]//following::div[7]/div");//use for loop to get the items
   public static final By IJWAfterPickupPriceDetails=By.xpath("//span[text()=\"It's Just Wings\"]//following::div[25]/div/div/span");//use for loop
   public static final By checkout=By.xpath("//div[contains(text(),'Checkout')]");
   public static final By verifyOrderQuantity=By.xpath("(//div[@KIND='BUTTON/FLAT_SECONDARY'])[24]/div/div/span/span/div");
   public static final By verifyScheduleLater_DD=By.xpath("//span[contains(text(),'Select a Pickup Date')]");
   public static final By verifyZeroTip=By.xpath("//div[@data-testid='Dasher Tip']//span[contains(text(),'$0.00')]");
   //It's just Wings for Doordash by Srividhya End


    
   
   
   
}














