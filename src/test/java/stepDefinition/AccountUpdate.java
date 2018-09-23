package stepDefinition;

import org.openqa.selenium.WebElement;

import commonMethods.SeMethods;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AccountUpdate extends SeMethods {
	static String Name;

	@And("^the user clicks on My Personal Information tab in Homepage$")
	public void the_user_clicks_on_My_Personal_Information_tab_in_Homepage() throws Throwable {

		WebElement personalInfo = locateElement("xpath", "//span[text()='My personal information']");
		click(personalInfo);
	}

	@When("^the user updates the first name \"(.*?)\"$")
	public void the_user_updates_the_first_name(String firstName) throws Throwable {

		/*********** Locating first Name and updating the name*********************/

		WebElement fName = locateElement("firstname");
		type(fName, firstName);
		Name = firstName;
		WebElement passwd = locateElement("old_passwd");
		type(passwd, "Password123");

	}

	@And("^clicks on save button$")
	public void clicks_on_save_button() throws Throwable {

		WebElement save = locateElement("xpath", "//span[text()='Save']");
		click(save);


	}

	@Then("^the first name is updated$")
	public void the_first_name_is_updated() throws Throwable {

		/************Navigating back to my account************/
		WebElement accntBack = locateElement("xpath", "//i[@class='icon-chevron-left']");
		click(accntBack);
		
		/********Clicking on personal Information tab and taking first name to validate the update****************/
		
		WebElement personalInfo = locateElement("xpath", "//span[text()='My personal information']");
		click(personalInfo);
		
		WebElement fName1=locateElement("firstname");
		String updatedName = getAttribute(fName1, "value");
		
		if(updatedName.equals(Name))
			System.out.println("Name is updated properly");
		else
			System.err.println("Name is not updated");

	}

}
