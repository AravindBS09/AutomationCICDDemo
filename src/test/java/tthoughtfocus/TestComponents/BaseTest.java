package tthoughtfocus.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import tthoughtfocus.pageobjects.LandingPage;

public class BaseTest 
{
	public WebDriver driver;
	public LandingPage landingPage;
	
	/*
	 * final static SimpleDateFormat dateFormat = new
	 * SimpleDateFormat("yyyy-MM-dd-HH_mm_ss"); static Timestamp timestamp = new
	 * Timestamp(System.currentTimeMillis());
	 * 
	 * static String reportFolder = dateFormat.format(timestamp);
	 */
	
	public WebDriver initializeDriver() throws IOException
	{

		// properties class
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//main//java//tthoughtfocus//resources//GlobleData.properties");
		prop.load(fis);
		
		String browserName = System.getProperty("browser")!=null ? System.getProperty("browser") : prop.getProperty("browser");
		
		//String browserName = prop.getProperty("browser");

		if(browserName.contains("chrome"))
		{
			//Chrome
			ChromeOptions options = new ChromeOptions();
			WebDriverManager.chromedriver().clearDriverCache().setup();
			WebDriverManager.chromedriver().clearResolutionCache().setup();
			WebDriverManager.chromedriver().setup();
			
			if(browserName.contains("headless"))
			{
				options.addArguments("headless");
			}
			driver = new ChromeDriver(options);
			//driver.manage().window().setSize(new Dimension(1440,900)); //full screen
		}
		else if(browserName.equalsIgnoreCase("firefox"))
		{
			// Firefox
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			//driver.manage().window().setSize(new Dimension(1440,900)); //full screen
		}
		else if(browserName.equalsIgnoreCase("Edge"))
		{
			// Edge
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			//driver.manage().window().setSize(new Dimension(1440,900)); //full screen
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();

		return driver;
	}

	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException
	{
		//read json to string
		String jsonContent = FileUtils.readFileToString(new File(filePath),StandardCharsets.UTF_8);

		//String to HashMap (Jackson Datbind)
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String,String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>(){
		});
		return data;
	}

	// Take Screenshot
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException
	{
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
	}

	//Extent Reports

	@BeforeMethod(alwaysRun=true)
	public LandingPage launchApplication() throws IOException
	{
		driver = initializeDriver();

		landingPage = new LandingPage(driver);
		landingPage.navigateTo();

		return landingPage;
	}

	@AfterMethod(alwaysRun=true)
	public void tearDown()
	{
		driver.close();
	}
}
