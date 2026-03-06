package popUps;

import java.util.Arrays;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

/**
 * =======================================================================================================================
 * POP-UP WINDOWS
 * =======================================================================================================================
 * Official Doc - https://playwright.dev/java/docs/pages#pages
 * 
 * The Page events on the browser can be used to get new pages that are created on the browser context
 *  	- Get Page after a specific action 
 *  	- if the action is unknown for triggering a new page
 *  The Popups 
 *  	- when a new blank tab opens up to a new window
 *  	- when a new window opens up seprately
 *  	- when multiple new windows open up seperately
*/

public class NewBlankTabDemo {

	static Page page;

	public static void main(String[] args) {

		String url = "https://playwright.dev/java/docs/pages#pages";

		Playwright playwright = Playwright.create();
		Browser browser = playwright.chromium().launch(new LaunchOptions().setSlowMo(1000).setHeadless(false));
		BrowserContext context = browser.newContext();
		
		page = context.newPage();
		page.navigate(url);

		/*
		 * demo - actions
		 */
		
		page.waitForLoadState();
		
		System.out.println("Parent Page Title is: " + page.title());
		
		// Opening a blank new tab from the parent window
		
		Page newBlankPage = page.waitForPopup(() -> {
			page.click("a[target='_blank']"); // open up new blank tab
		});
		
		newBlankPage.waitForLoadState();
		
		newBlankPage.navigate("https://mail.google.com");
		
		newBlankPage.waitForLoadState();
		
		System.out.println("New Blank Page Navigated to Title is: " + newBlankPage.title());
	}

}