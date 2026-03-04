

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

/**
 * =======================================================================================================================
 *                                                          SAMPLE TOPIC
 * =======================================================================================================================
 * Official Doc - https://playwright.dev/java/docs/dialogs
 * 
 */

public class SampleTest {

public static void main(String[] args) {
		
		String url = "https://demo.automationtesting.in/Alerts.html";
		
		Playwright playwright = Playwright.create();
		Browser browser = playwright.chromium().launch(new LaunchOptions().setSlowMo(1000).setHeadless(false));
		Page page = browser.newPage();
		page.navigate(url);
		
		/*
		 * demo - actions
		 */

	}

}
