package onlineBooking;

import java.util.concurrent.TimeUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import onlineBooking.SearchPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/*
* This is the Test Cases class to test the booking on www.expedia.com
* using "Bundle and Save" with "Flight + Hotel" feature.
* User has to provide Origin, Destination, Departing date, Returning date.
* This test is to verify the functionality of Search results 
* being showed based on the given of user inputs.
* 
* @author  Shammi Afroze
*/
public class ExpediaBookingTest {
	private WebDriver driver;
	private String url = "https://www.expedia.com/";
	SearchPage searchPage;

	/*
	* This is the Before method to initiate Firefox driver
	* and navigate to the given url
	*/
	@BeforeMethod
	public void beforeClass() {
		WebDriverManager.firefoxdriver().setup();
		driver = new FirefoxDriver();
		
		//WebDriverManager.chromedriver().setup();
		//driver = new ChromeDriver();
		
		//Creating search page object to get page locators and methods
		searchPage = new SearchPage(driver);

		// Maximizing the browser's window
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(url);
	}

	/*
	* This is the Test method to run the test case
	* and verify the search results being displayed
	*/
	@Test
	public void test() {
		searchPage = new SearchPage(driver);
		searchPage.setOriginCity("LAX");
		searchPage.setDestinationCity("DFW");
		searchPage.selectTravelDate(searchPage.departureDateField,searchPage.departCalMonth, "31");
		searchPage.selectTravelDate(searchPage.returnDateField,searchPage.returnCalMonth, "25");
		searchPage.clickSearchButton();
		searchPage.verifySearchResultTitle();		
	}

	/*
	* This is the After method to quit the driver
	*/
	@AfterMethod
	public void afterClass() {
		driver.quit();
	}
}