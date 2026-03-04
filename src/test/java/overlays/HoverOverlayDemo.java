package overlays;

import java.util.Arrays;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Frame;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

/**
 * =======================================================================================================================
 * Overlays
 * =======================================================================================================================
 * Official Doc - https://playwright.dev/java/docs/dialogs
 * 
 */

public class HoverOverlayDemo {

	static Page page;
	
	public static void main(String[] args) {

		String url = "https://automationexercise.com/";

		Playwright playwright = Playwright.create();
		Browser browser = playwright.chromium()
				.launch(new LaunchOptions()
				.setSlowMo(1000)
				.setHeadless(false)
				.setArgs(Arrays.asList("--disable-features=IsolateOrigins,site-per-process")));
		page = browser.newPage();
		page.navigate(url);

		/*
		 * demo - actions
		 */

		page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Women")).click();
		page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Dress")).click();
		
		addProductToCart("Sleeveless Dress");
		addProductToCart("Stylish Dress");
		

	}
	
	public static void addProductToCart(String productName) {
		
		Locator productCard = page.locator(".single-products")
								  .filter(new Locator.FilterOptions()
								  .setHasText(productName));
		
		//hover to make overlay display
		productCard.hover();
		
		//click add to cart inside the product card 
		productCard.locator(".product-overlay .add-to-cart").click(); //chained css selector - resolves to a single element for action
		//productCard.locator(".add-to-cart:visible").first().click();
		//productCard.locator(".add-to-cart").filter(new Locator.FilterOptions().setHas(page.locator(":visible"))).last().click();
		//productCard.getByText("Add to cart").last().click();
		//productCard.locator("a:has-text('Add to cart')").first().click();
		//productCard.locator("text=Add to cart").first().click();
		
		//click continue button
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue Shopping")).click();
		//page.getByText("Continue Shopping", new Page.GetByTextOptions().setExact(true)).click();
	}
	
	

}
