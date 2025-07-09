package tthoughtfocus.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import tthoughtfocus.TestComponents.BaseTest;
import tthoughtfocus.pageobjects.CartPage;
import tthoughtfocus.pageobjects.CheckoutPage;
import tthoughtfocus.pageobjects.ConfirmationPage;
import tthoughtfocus.pageobjects.OrderPage;
import tthoughtfocus.pageobjects.ProductCatalogue;

public class SubmitOrderTest extends BaseTest
{
	String productName = "ZARA COAT 3";

	@Test(dataProvider="getData", groups= {"Purchase"})
	public void submitOrder(HashMap<String,String> input) throws InterruptedException, IOException
	{	
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		List<WebElement> products = productCatalogue.getProductList();

		productCatalogue.addProductToCart(input.get("productName"));
		CartPage cartPage = productCatalogue.goToCartPage();

		Boolean match = cartPage.VerifyProductDisplay(input.get("productName"));
		Assert.assertTrue(match);

		CheckoutPage checkoutPage = cartPage.goToCheckOut();
		checkoutPage.selectCountry("india");

		ConfirmationPage confirmationPage = checkoutPage.submitOrder();	

		String confirmationMessage = confirmationPage.verifyConfirmationMessage();
		Assert.assertTrue(confirmationMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	}


	@Test(dependsOnMethods= {"submitOrder"}) 
	public void OrderHistoryTest() throws InterruptedException, IOException 
	{ 
		ProductCatalogue productCatalogue = landingPage.loginApplication("veena.alse88@gmail.com","Test@123"); 
		OrderPage ordersPage = productCatalogue.goToOrdersPage();
		Assert.assertTrue(ordersPage.VerifyOrderDisplay(productName)); 
	}

	@DataProvider
	public Object[][] getData() throws IOException
	{
		List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir")+"//src//test//java//tthoughtfocus//data//PurchaseOrder.json");

		return new Object[][] {{data.get(0)}};
		//,{data.get(1)}
	}
}
