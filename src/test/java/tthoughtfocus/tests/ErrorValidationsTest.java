package tthoughtfocus.tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import tthoughtfocus.TestComponents.BaseTest;
import tthoughtfocus.TestComponents.Retry;
import tthoughtfocus.pageobjects.CartPage;
import tthoughtfocus.pageobjects.ProductCatalogue;

public class ErrorValidationsTest extends BaseTest
{
	@Test(groups= {"ErrorHandling"}, retryAnalyzer=Retry.class)
	public void LoginErrorValidation() throws InterruptedException, IOException
	{
		landingPage.loginApplication("aruvaidya@gmail.com", "Test123");
		Assert.assertEquals("Incorrect email or password.",landingPage.getErrorMessage());
	}


	@Test 
	public void ProductErrorValidation() throws InterruptedException,IOException 
	{ 
		String productName = "ADIDAS ORIGINAL";

		ProductCatalogue productCatalogue =	landingPage.loginApplication("aruvaidya@gmail.com", "Test@123");
		List<WebElement> products = productCatalogue.getProductList();

		productCatalogue.addProductToCart(productName); 
		
		CartPage cartPage =productCatalogue.goToCartPage();

		Boolean match = cartPage.VerifyProductDisplay("ADIDAS ORIGINAL 11");
		Assert.assertFalse(match); 
	}
}
