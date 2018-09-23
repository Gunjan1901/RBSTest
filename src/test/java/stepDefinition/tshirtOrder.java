package stepDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
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
	WebElement signIn = locateElement("xpath", "//i[@class='icon-lock left']");
	click(signIn);
		
	    
	}

	@And("^the user is logged in to the site$")
	public void the_user_is_logged_in_to_the_site() throws Throwable {
		
		System.out.println("User logged in to site successfully");
		verifyTitle("My account - My Store");
	    
	}

	@When("^the user selects a tshirt$")
	public void the_user_selects_a_tshirt() throws Throwable {
		WebElement tshirt = locateElement("xpath", "(//a[@title='T-shirts'])[2]");
		click(tshirt);
		
		WebElement product = locateElement("xpath", "(//a[@title='Faded Short Sleeve T-shirts'])[2]");
		ScrollByVisibleElement(product);
		
		mouseHover(product, product);
		
		WebElement tshirtN = locateElement("xpath", "//h1[text()='Faded Short Sleeve T-shirts']");
		String tshirtName = getText(tshirtN);
		
		WebElement qty = locateElement("xpath", "//i[@class='icon-plus']");
		click(qty);
		
		WebElement ddSize = locateElement("group_1");
		selectDropDownUsingText(ddSize, "L");
		WebElement addToCart = locateElement("add_to_cart");
		click(addToCart);
		System.out.println("Product successfully added to cart");
		
	    
	}

	@And("^orders the same after making payment$")
	public void orders_the_same_after_making_payment() throws Throwable {
	   
		/********Clicking on Proceed to Checkout after Adding to Cart***********/
		
		WebElement ptc = locateElement("xpath", "//a[@class='btn btn-default button button-medium']");
	   clickWithNoSnap(ptc);
	   
	   /*********Clicking on Proceed to Checkout in Summary tab*************/
	   
	   WebElement chkoutSummary = locateElement("xpath", "//span[text()='Proceed to checkout']");
	   click(chkoutSummary);
	   
	   /**********Clicking on Proceed to Checkout in Address tab***********/
	   
	   WebElement chkoutAddress = locateElement("xpath", "//span[text()='Proceed to checkout']");
	   click(chkoutAddress);
	  
	   /*********Clicking on Terms of Service Checkbox************/
	   
	   WebElement chkbox = locateElement("xpath", "//input[@type='checkbox']");
	   ScrollByVisibleElement(chkbox);
	   clickWithNoSnap(chkbox);
	   
	   /********Clicking on Checkout in Shipping tab*************/
	   
	   WebElement chkoutShipping = locateElement("xpath", "//button[@class='button btn btn-default standard-checkout button-medium']");
	   click(chkoutShipping);
	   
	   /**********Selecting payment mode and confirming the order*************/
	   
	   WebElement paymentMode = locateElement("xpath", "//a[@title='Pay by bank wire']");
	   click(paymentMode);
	   WebElement confirmOrder = locateElement("xpath", "//span[text()='I confirm my order']");
	   click(confirmOrder);
	   
	}

	@Then("^the current order is present in order history$")
	public void the_current_order_is_present_in_order_history() throws Throwable {
	 
		/********Going to order page after placing order and taking the text present in order box**********/
		
		WebElement orderBox = locateElement("xpath", "//div[@class='box']");
		String OrderText = getText(orderBox);
			
		/********Going back to Order history to validate the order****************/
		
		WebElement orderHist = locateElement("xpath", "//a[@title='Back to orders']");
		click(orderHist);
		Thread.sleep(20000);
		
		/********************All the order id's are in 1st Column of Order History table, 
		  			taking all the order reference to validate************************************/
		 
		WebElement orderTable = driver.findElementById("order-list");
		
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

@And("^the browser is closed$")
public void the_browser_is_closed()
{
	closeBrowser();
}
}
