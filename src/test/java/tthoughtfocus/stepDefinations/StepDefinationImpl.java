package tthoughtfocus.stepDefinations;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import tthoughtfocus.TestComponents.BaseTest;
import tthoughtfocus.pageobjects.CartPage;
import tthoughtfocus.pageobjects.CheckoutPage;
import tthoughtfocus.pageobjects.ConfirmationPage;
import tthoughtfocus.pageobjects.LandingPage;
import tthoughtfocus.pageobjects.ProductCatalogue;

public class StepDefinationImpl extends BaseTest 
{
	public LandingPage landingPage;
	public ProductCatalogue productCatalogue;
	public CheckoutPage checkoutPage;
	public ConfirmationPage confirmationPage;
	
	@Given("I landed on Ecommerce Page")
	public void I_landed_on_Ecommerce_Page() throws IOException
	{
		//code
		landingPage = launchApplication();
	}
	
	// ^ $ - this is used for telling it is RegX based expression, (.+) - Regular expression for any variable
	
	@Given("^Logged in with username (.+) and password (.+)$")
	public void logged_in_with_username_and_password(String username, String password)
	{
		productCatalogue = landingPage.loginApplication(username, password);
	}
	
	@When("^I add product (.+) to Cart$")
	public void i_add_product_to_cart(String productName) throws InterruptedException
	{
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
	}
	
	@When("^Checkout (.+) and submit the order$")
	public void checkout_and_submit_the_order(String productName) throws InterruptedException
	{
		CartPage cartPage = productCatalogue.goToCartPage();

		Boolean match = cartPage.VerifyProductDisplay(productName);
		Assert.assertTrue(match);

		checkoutPage = cartPage.goToCheckOut();
		checkoutPage.selectCountry("india");

		confirmationPage = checkoutPage.submitOrder();
	}
	
	@Then("{string} message is displayed on ConfirmationPage")
	public void message_is_displayed_on_confirmationPage(String string)
	{
		String confirmationMessage = confirmationPage.verifyConfirmationMessage();
		Assert.assertTrue(confirmationMessage.equalsIgnoreCase(string));
		driver.close();
	}
	
	@Then("^\"([^\"]*)\" message is displayed")
	public void message_is_displayed(String stringArg1)
	{
		Assert.assertEquals(stringArg1,landingPage.getErrorMessage());
		driver.close();
	}
}
