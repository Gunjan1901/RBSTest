package stepDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import commonMethods.SeMethods;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class tshirtOrder extends SeMethods {

	
	@Given("^user opens browser \"(.*?)\" and enters \"(.*?)\"$")
	public void user_opens_browser_and_enters(String browser,String url) throws Throwable {
	
		startApp(browser, url);
		
	}

	@And("^enters username \"(.*?)\" and password \"(.*?)\"$")
	public void enters_username_and_password(String uname, String passwd) throws Throwable {
		
		WebElement login = locateElement("class", "login");
		click(login);
	WebElement id = locateElement("email");
	WebElement password = locateElement("passwd");
	type(id, uname);
	type(password, passwd);
	WebElement signIn = locateElement("xpath", prop.getProperty("signInButton"));
	click(signIn);
		
	    
	}

	@And("^the user is logged in to the site$")
	public void the_user_is_logged_in_to_the_site() throws Throwable {
		
		System.out.println("User logged in to site successfully");
		verifyTitle("My account - My Store");
	    
	}

	@When("^the user selects a tshirt$")
	public void the_user_selects_a_tshirt() throws Throwable {
		
		/*********Clicking on thsirt link in home page****************/
		
		WebElement tshirt = locateElement("xpath", prop.getProperty("tshirtLink"));
		click(tshirt);
		
		/**************Scrolling down the web page Hovering on the tshirt image and clicking on the tshirt**************/
		
		WebElement product = locateElement("xpath", prop.getProperty("tshirtImage"));
		ScrollByVisibleElement(product);
		mouseHover(product, product);
		
		/******Getting the tshirt name**************/
		
		WebElement tshirtN = locateElement("xpath", prop.getProperty("tshirtName"));
		String tshirtName = getText(tshirtN);
		
		/***********Increasing the quantity of tshirt and selecting the size************/
		
		WebElement qty = locateElement("xpath", prop.getProperty("QuantityIcon"));
		click(qty);
		
		WebElement ddSize = locateElement(prop.getProperty("sizeDropdown"));
		selectDropDownUsingText(ddSize, "L");
		
		/*********Adding the product to cart**************/
		
		WebElement addToCart = locateElement(prop.getProperty("cartAdd"));
		click(addToCart);
		System.out.println("Product successfully added to cart");
		
	    
	}

	@And("^orders the same after making payment$")
	public void orders_the_same_after_making_payment() throws Throwable {
	   
		/********Clicking on Proceed to Checkout after Adding to Cart***********/
		
		WebElement ptc = locateElement("xpath", prop.getProperty("proceedToChkout"));
	   clickWithNoSnap(ptc);
	   
	   /*********Clicking on Proceed to Checkout in Summary tab*************/
	   
	   WebElement chkoutSummary = locateElement("xpath", prop.getProperty("proceedToChkout1"));
	   click(chkoutSummary);
	   
	   /**********Clicking on Proceed to Checkout in Address tab***********/
	   
	   WebElement chkoutAddress = locateElement("xpath", prop.getProperty("proceedToChkout1"));
	   click(chkoutAddress);
	  
	   /*********Clicking on Terms of Service Checkbox************/
	   
	   WebElement chkbox = locateElement("xpath", prop.getProperty("checkbox"));
	   ScrollByVisibleElement(chkbox);
	   clickWithNoSnap(chkbox);
	   
	   /********Clicking on Checkout in Shipping tab*************/
	   
	   WebElement chkoutShipping = locateElement("xpath", prop.getProperty("shippingChkout"));
	   click(chkoutShipping);
	   
	   /**********Selecting payment mode and confirming the order*************/
	   
	   WebElement paymentMode = locateElement("xpath", prop.getProperty("paymode"));
	   click(paymentMode);
	   WebElement confirmOrder = locateElement("xpath", prop.getProperty("orderConfirm"));
	   click(confirmOrder);
	   
	}

	@Then("^the current order is present in order history$")
	public void the_current_order_is_present_in_order_history() throws Throwable {
	 
		/********Going to order page after placing order and taking the text present in order box**********/
		
		WebElement orderBox = locateElement("xpath", prop.getProperty("orderBox"));
		String OrderText = getText(orderBox);
			
		/********Going back to Order history to validate the order****************/
		
		WebElement orderHist = locateElement("xpath", prop.getProperty("backToOrders"));
		click(orderHist);
		Thread.sleep(20000);
		
		/********************All the order id's are in 1st Column of Order History table, 
		  			taking all the order reference to validate************************************/
		try { 
		WebElement orderTable = driver.findElementById(prop.getProperty("orderTable"));
		
		List<WebElement> allRows = orderTable.findElements(By.tagName("tr"));
		
			ArrayList<String> columnText = new ArrayList<>();	
		
			for (WebElement ele : allRows) 
		{
			List<WebElement> allColumns = ele.findElements(By.tagName("td"));
			System.out.println("All Columns: "+allColumns);
			columnText.add(allColumns.get(0).getText());
						
		}
			System.out.println("Column text: "+columnText);
		
		for (String firstColumnText : columnText) {
			
			if(OrderText.contains(firstColumnText))
				System.out.println("Order placed successfully and the order id is present in Order History");
			else
				System.err.println("Order id is not present in Order History");
			
		}
		}
		catch(WebDriverException e)
		{
			System.err.println("Unknown exception occured while loading the order History page :" +e.getMessage());
		}
		
		
		
		
	}

@And("^the browser is closed$")
public void the_browser_is_closed()
{
	closeBrowser();
}
}
