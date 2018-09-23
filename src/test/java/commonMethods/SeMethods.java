package commonMethods;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
//import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
//import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeMethods implements WdMethods{
	protected static RemoteWebDriver driver;	
	int i = 1;
	public Properties prop;
	
	public SeMethods() {
		prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("./src/test/resources/Xpath/xpath.properties")));
			} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void startApp(String browser, String url) {
		ChromeOptions op = new ChromeOptions();
		op.addArguments("disable-infobars");
		try {
			if (browser.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
				driver = new ChromeDriver(op);			
			} else {
				System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
				driver = new FirefoxDriver();
			}
			driver.manage().window().maximize();
			driver.get(url);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			System.out.println("The Browser is Launched");
		} catch (WebDriverException e) {
			//e.printStackTrace();
			System.err.println("The browser is Invalid or not available");
		}

	}

	public WebElement locateElement(String locator, String locValue) {
		try {
			switch (locator) {
			case "id":
				return	driver.findElementById(locValue);		
			case "name":
				return	driver.findElementByName(locValue);			
			case "class":
				return	driver.findElementByClassName(locValue);			
			case "link":
				return	driver.findElementByLinkText(locValue);			
			case "tag":
				return	driver.findElementByTagName(locValue);			
			case "xpath":
				return	driver.findElementByXPath(locValue);
			default:
				break;

			}
			System.out.println("The appropriate element is located using " + locator + " and " + locValue);
		} catch (NoSuchElementException e) {
			//e.printStackTrace();
			System.err.println("Locator is not found");
			//throw new RuntimeException();
		}

		finally {
			takeSnap();

			//return null;
		}
		return null;
	}

	public WebElement locateElement(String locValue) {
		return locateElement("id", locValue);

	}

	public void type(WebElement ele, String data) {
		try {
			ele.clear();
			ele.sendKeys(data);
			System.out.println("The data "+data+ " is Entered for the webelement "+ele);

		}catch (InvalidElementStateException e) {
			System.err.println("The data: "+data+" could not be entered in the field :"+ele);
		} catch (WebDriverException e) {
			System.err.println("Unknown exception occured while entering "+data+" in the field :"+ele);
		}
		finally
		{
			takeSnap();
		}
	}

	public void click(WebElement ele) {
		String text = "";
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(ele));			
			text = ele.getText();
			ele.click();
			System.out.println("The element "+text+" is clicked");
		} catch (InvalidElementStateException e) {
			System.err.println("The element: "+text+" could not be clicked");
		} catch (WebDriverException e) {
			System.err.println("Unknown exception occured while clicking in the field");
		} 
		finally
		{
			takeSnap();
		}
	}

	public void clickWithNoSnap(WebElement ele) {
		String text ="";
		try {
			text = ele.getText();
			ele.click();
			System.out.println("The Element "+text+" Is Clicked");
		} catch (InvalidElementStateException e) {
			System.err.println("The element: "+text+" could not be clicked");
		} catch (WebDriverException e) {
			System.err.println("Unknown exception occured while clicking in the field");
		} 
	}

	public String getText(WebElement ele) {
		String text="";
		try {
			text = ele.getText();
			System.out.println("The text "+text+" is retrieved for the WebElement " + ele);
		}
		catch(NullPointerException n)
		{
			System.err.println("The element pointing to null, since it's not located");
		}

		catch (NoSuchElementException e) {
			System.err.println("The element is not found");
		}
		finally
		{
			takeSnap();
		}
		return text;

	}
	public String getAttribute(WebElement ele, String attribute) {
		String text="";
		try {
			text = ele.getAttribute(attribute);
			System.out.println("The attribute text "+text+" is retrieved for the WebElement " + ele);
		}
		catch(NullPointerException n)
		{
			System.err.println("The element pointing to null, since it's not located");
		}

		catch (NoSuchElementException e) {
			System.err.println("The element is not found");
		}
		finally
		{
			takeSnap();
		}
		return text;
	}

	public void selectDropDownUsingText(WebElement ele, String value) {
		try {
			Select dd = new Select(ele);
			dd.selectByVisibleText(value);
			System.out.println("The " +ele+ "dropdown is selected with value "+value);
		}
		catch(NullPointerException n)
		{
			System.err.println("The element pointing to null, since it's not located");
		}

		catch (WebDriverException e) {
			System.err.println("The element for dropdown is not found");
		}

		finally
		{
			takeSnap();
		}

	}

	public void selectDropDownUsingIndex(WebElement ele, int index) {
		try {

			Select dd1 = new Select(ele);
			dd1.selectByIndex(index);;
			System.out.println("The " +ele+ "dropdown is selected with value "+index);
		}
		catch(NullPointerException n)
		{
			System.err.println("The element pointing to null, since it's not located");
		}

		catch (WebDriverException e) {
			System.err.println("The element for dropdown is not found");
		}
		finally
		{
			takeSnap();
		}

	}

	public boolean verifyTitle(String expectedTitle) {
		boolean breturn = false;
		String title = driver.getTitle();
		try {
			if(title.equalsIgnoreCase(expectedTitle))
			{
				System.out.println("The title of the page matches with the value:"+expectedTitle);
				takeSnap();
				breturn=true;
			}
			else
			{
				System.out.println("The title of the page"+title+"didnot matches with the value:"+expectedTitle);
				takeSnap();
			}
		}
		catch (WebDriverException e) {
			System.out.println("Unknown exception occured while verifying the title");
		}

		return breturn;
	}

	public void verifyExactText(WebElement ele, String expectedText) {
		String text = ele.getText();
		try {
			if(text.equals(expectedText))
				System.out.println("The text:"+text+"exactly matches with the value:"+expectedText);
			else
				System.err.println("The text:"+text+"didnot matches with the actual text:"+expectedText);
		}
		catch (WebDriverException e) {
			System.out.println("Unknown exception occured while verifying the text");
		}
		finally {
			takeSnap();
		}}

	public void verifyPartialText(WebElement ele, String expectedText) {
		String text = ele.getText();
		try {
			if(expectedText.contains(text))
				System.out.println("The expected text " + expectedText + " contains actual text " + text);
			else
				System.err.println("The expected text " + expectedText + " does not contains actual text " + text);
		}
		catch (WebDriverException e) {
			System.out.println("Unknown exception occured while verifying the text");
		}
		finally {
			takeSnap();

		}
	}

	public void verifyExactAttribute(WebElement ele, String attribute, String value) {
		String attrValue = ele.getAttribute(attribute);
		System.out.println(attrValue);
		try {
			if(attrValue.equalsIgnoreCase(value))
			{
				System.out.println("The given value of attribute " +value+ " is matching with the attribute value");
				takeSnap();
			}
			else
			{
				System.out.println("The given value of attribute " +value+ " is not matching with the attribute value");
				takeSnap();
			}
		}
		catch (WebDriverException e) {
			System.out.println("Unknown exception occured while verifying the Attribute text");
		}

	}

	public void verifyPartialAttribute(WebElement ele, String attribute, String value) {
		String attrValue = ele.getAttribute(attribute);
		System.out.println(attrValue);
		try {
			if(attrValue.contains(value))
			{
				System.out.println("The attribute value " + attrValue+ "contains the value of attribute given by user");

				takeSnap();
			}
			else
			{
				System.out.println("The attribute value " + attrValue+ " doesnot contains the value of attribute given by user");
				takeSnap();
			}
		}
		catch (WebDriverException e) {
			System.out.println("Unknown exception occured while verifying the Attribute text");
		}
	}
	public void verifySelected(WebElement ele) {
		try {
			if(ele.isSelected())
			{
				System.out.println("The WebElement " + ele+ " is selected");
				takeSnap();
			}
			else
			{
				System.out.println("The WebElement " + ele+ " is not selected");
				takeSnap();
			}
		}
		catch(WebDriverException e)
		{
			System.err.println("Exception message while selecting element: "+e.getMessage());
		}
	}

	public void verifyDisplayed(WebElement ele) {
		try {
			if(ele.isDisplayed())
			{
				System.out.println("The WebElement " + ele+ " is displayed in DOM");
				takeSnap();
			}
			else
			{
				System.out.println("The WebElement " + ele+ " is not displayed in DOM");
				takeSnap();
			}
		}
		catch(WebDriverException e)
		{
			System.err.println("Exception message while verifying element display: "+e.getMessage());
		}
	}

	public void switchToWindow(int index) {
		try {
			Set<String> windowHandles = driver.getWindowHandles();
			List<String> allWindowHandles = new ArrayList<String>();
			allWindowHandles.addAll(windowHandles);
			//driver.switchTo takes argument as String, Hence we have to convert index to string.
			driver.switchTo().window(allWindowHandles.get(index));
			System.out.println("The control is switched from one window to another using the index "+index);
			takeSnap();
		}

		catch (NoSuchWindowException e) {
			System.err.println("The driver could not move to the given window by index "+index);
		} catch (WebDriverException e) {
			System.err.println("WebDriverException : "+e.getMessage());
		}
	}


	public void switchToFrame(WebElement ele) {
		try {
			driver.switchTo().frame(ele);
			System.out.println("The control is switched to the the frame with respect to WebElement "+ele);
			takeSnap();
		}
		catch (NoSuchFrameException e) {
			System.err.println("Frame exception : "+e.getMessage());
		} catch (WebDriverException e) {
			System.err.println("WebDriverException : "+e.getMessage());
		} 
	}

	public void acceptAlert() {
		String text = "";		
		try {
			Alert alert = driver.switchTo().alert();
			text = alert.getText();
			alert.accept();
			System.out.println("The alert "+text+" is accepted.");
		} catch (NoAlertPresentException e) {
			System.err.println("There is no alert present.");
		} catch (WebDriverException e) {
			System.err.println("WebDriverException : "+e.getMessage());
		}  
	}

	public void dismissAlert() {
		String text = "";		
		try {
			Alert alert = driver.switchTo().alert();
			text = alert.getText();
			alert.dismiss();
			System.out.println("The alert "+text+" is dismissed.");
		} catch (NoAlertPresentException e) {
			System.err.println("There is no alert present.");
		} catch (WebDriverException e) {
			System.err.println("WebDriverException : "+e.getMessage());
		}  
	}


	public String getAlertText() {
		String alertText="";
		try {
			alertText = driver.switchTo().alert().getText();
			System.out.println("The alert text is : " + alertText);
		}
		catch(NoAlertPresentException e)
		{
			System.err.println("There is no alert which needs to be handled");
		}
		catch (WebDriverException e) {
			System.err.println("WebDriverException : "+e.getMessage());
		}  

		return alertText;
	}

	public void mouseHover(WebElement source, WebElement target)
	{
		try
		{
			Actions builder = new Actions(driver);
			builder.moveToElement(source).perform();
			builder.pause(3000);
			builder.click(target).perform();
			System.out.println("Mousehover is performed on the weblement");

		}
		catch (NoSuchElementException e) {
			System.err.println("The element is not found");	
		}
		catch (WebDriverException e) {
			System.err.println("WebDriverException : "+e.getMessage());
		}

	}

	public void ScrollByVisibleElement(WebElement ele) {
		try {
			System.setProperty("webdriver.chrome.driver", "G://chromedriver.exe");
			JavascriptExecutor js = (JavascriptExecutor) driver;

			//This will scroll the page till the element is found		
			js.executeScript("arguments[0].scrollIntoView();", ele);
		}
		catch(NoSuchElementException e)
		{
			System.err.println("The element is not found");
		}
		catch (WebDriverException e) {
			System.err.println("WebDriverException : "+e.getMessage());
		}

		finally
		{
			takeSnap();
		}
	}

	public long takeSnap() {
		long number = (long) Math.floor(Math.random() * 900000000L) + 10000000L; 
		try {
			FileUtils.copyFile(driver.getScreenshotAs(OutputType.FILE) , new File("./reports/images/"+number+".jpg"));
		} catch (WebDriverException e) {
			System.out.println("The browser has been closed.");
		} catch (IOException e) {
			System.out.println("The snapshot could not be taken");
		}
		return number;
	}

	public void closeBrowser() {
		driver.close();
		System.out.println("The current browser session is closed");
	}

	public void closeAllBrowsers() {
		driver.quit();
		System.out.println("All the browsers in current session is closed.");

	}

}

