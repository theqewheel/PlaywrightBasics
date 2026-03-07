package popUps;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;

/**
 * =======================================================================================================================
 * POP-UP WINDOWS
 * =======================================================================================================================
 * Official Doc - https://playwright.dev/java/docs/pages#pages
 * 
 * The Page events on the browser can be used to get new pages that are created
 * on the browser context - Get Page after a specific action - if the action is
 * unknown for triggering a new page The Popups - when a new blank tab opens up
 * to a new window - when a new window opens up separately - when multiple new
 * windows open up separately
 */

public class NewWindowPageDemo1 {

	static Page page;

	public static void main(String[] args) {

		String url = "https://freelance-learn-automation.vercel.app/login";

		Playwright playwright = Playwright.create();
		Browser browser = playwright.chromium().launch(new LaunchOptions().setSlowMo(1000).setHeadless(false));


		/*
		 * demo - actions
		 */

		BrowserContext context = browser.newContext();
		
		page = context.newPage();
		
		page.navigate(url);
		
		page.waitForLoadState();

		System.out.println("Parent Page Title is: " + page.title());

		// Opening a new window from the parent window by clicking on an element
		Page newPage = context.waitForPage(() -> {
			page.locator("a[href*=\"facebook\"]").first().click();
		});
		
		newPage.waitForLoadState();
		
		System.out.println("New Child Page Title is: " + newPage.title());
		
		newPage.getByText("Email").fill("Autobot1@gmail.com");
		newPage.getByText("Password", new Page.GetByTextOptions().setExact(true)).fill("autobot1@123");
		
		page.bringToFront();
		
		page.getByPlaceholder("Enter Email").fill("Autobot2@gmail.com");
		page.getByPlaceholder("Enter Password").fill("autobot2@123");
	}

}