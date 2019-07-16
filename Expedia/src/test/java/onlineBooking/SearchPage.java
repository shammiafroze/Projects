package onlineBooking;

import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/*
* This is the search page class for expedia.com
* This class consists of all related locators using Page Factory 
* and methods for this page functionalities.
* 
* @author  Shammi Afroze
*/
public class SearchPage {
	WebDriver driver;
		
	@FindBy(id = "primary-header-flight")
	public WebElement fligthsTab;
	
	@FindBy(id = "package-origin-hp-package")
	public WebElement originCity;
	
	@FindBy(id = "package-destination-hp-package")
	public WebElement destinationCity;
	
	@FindBy(id = "package-departing-hp-package")
	public WebElement departureDateField;
	
	@FindBy(id = "package-returning-hp-package")
	public WebElement returnDateField;
	
	@FindBy(id = "search-button-hp-package")
	public WebElement searchButton;
	
	@FindBy(className = "section-header-main")
	public WebElement searchResultTitle;
	
	String departCalMonth ="//div[@class='datepicker-cal-month'][position()=1]//button[@class='datepicker-cal-date']";
	
	String returnCalMonth ="//div[@class='datepicker-cal-month'][position()=2]//button[@class='datepicker-cal-date']";
	
	/*
	 * Search page class constructor 
	 * and Page Factory initializer
	 */
	public SearchPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	/*
	 * Method to click on flight tab
	 */
	public void clickFlightsTab() {
		fligthsTab.click();
	}
	
	/*
	 * Method to send origin city name
	 * @param origin, String city name
	 */
	public void setOriginCity(String origin) {
		originCity.sendKeys(origin);
	}
	
	/*
	 * Method to send destination city name
	 * @param destination, String city name
	 */
	public void setDestinationCity(String destination) {
		destinationCity.sendKeys(destination);
	}
	
	/*
	 * Method to click on search button
	 */
	public void clickSearchButton() {
		searchButton.click();
	}
	
	/*
	* This method selects specific date from Origin or Destination calendar
	* @param field, This is WebElement field to select Origin or Destination calendar
	* @param calMonth, This is the String of calendar month of Origin or Destination
	* @param selectDate, This is the String of calendar date of selected month
	*/
	public void selectTravelDate(WebElement field, String calMonth, String selectDate){
		
		field.click();
				
		List<WebElement> allValidDates = driver.findElements(By.xpath(calMonth));
				
		Boolean validDate = false;
		System.out.print("Available Dates: ");
		
		//Iterating through all valid dates to find the given date
		for (WebElement date : allValidDates) {
			
			String dateWithMonth = date.getText();
			//Removing extra words & characters to get the exact date
			String dateText = dateWithMonth.substring(dateWithMonth.length()-2, dateWithMonth.length());
			dateText = dateText.replace("\n", "");
			
			System.out.print(dateText+" ");
			
			//Comparing given date with available dates
			if (dateText.equalsIgnoreCase(selectDate)) {
				System.out.println("Selected Date: "+dateText);
				date.click();
				validDate = true;
				break;
			}
			else{
				validDate = false;
			}
		}
		
		if(!validDate){
			System.out.println("Given date is not available");		
		}
		
	}
	
	/*
	 * This method is to verify if the search results page is displayed
	 */
	public void verifySearchResultTitle() {
		
		//Switching to search results window to verify page title
		Set<String> windowSet = driver.getWindowHandles(); //Return a set of window handle
		String[] windowArray = windowSet.toArray(new String[windowSet.size()]); //convert set of string to string array
		int i = 0;
		for (String childWindow : windowArray) {
			if(i==1) {
				driver.switchTo().window(childWindow);
			}
		}
		
		//Using Explicit Wait for search results title WebElement to be visible on the page
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(searchResultTitle));
		
		//Verifying the search results page title is displayed
		Assert.assertTrue(searchResultTitle.isDisplayed());
		System.out.println("Search result page title verified");
		
	}	
}
