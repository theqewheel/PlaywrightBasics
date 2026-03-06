package popUps;

import java.util.Arrays;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

/**
 * =======================================================================================================================
 * 														GOOGLE DYNAMIC ADDS ON BROWSER
 * =======================================================================================================================
 * Official Doc - https://playwright.dev/java/docs/api/class-route
 * Official Doc - https://playwright.dev/java/docs/api/class-browsercontext#browser-context-route
 * Using BrowserContext.route()
 * Using Page.route();
 * 		- to abort
 * 		- to fallback
 * 		- to fetch
 * 		- to fulfill
 * 		- to request
 * 		- to resume
 * 
 * Google Vignette Ads :
 * 					   - change the URL as well as they display as an overlay blocking your automation (e.g automationexercise.com/#google_vignette)
 * 					   - Google Vignette ads are full-screen overlay ads served by Google AdSense / Google Ads. 
 * 					   - They appear between page navigations and temporarily cover the website.
 * 					   - They appear only if the website owner enables them in their Google AdSense settings.
 * 				       - usually seen on demo sites, practice sites, content site
 * 					   - they earn revenue through adds mostly aggressive on mobile 
 * 					   - on enterprise apps (business) these are not enabled
 */

public class GoogleAdPopUpDemo {

	static Page page;
	
	public static void main(String[] args) {

		String url = "https://automationexercise.com/";

		Playwright playwright = Playwright.create();
		Browser browser = playwright.chromium()
				.launch(new LaunchOptions()
				.setSlowMo(1000)
				.setHeadless(false)
				.setArgs(Arrays.asList("--disable-features=IsolateOrigins,site-per-process")));
		BrowserContext context = browser.newContext();
		
		context.route("**/*", route -> {
		    String addUrls = route.request().url();

		    if (addUrls.contains("doubleclick") ||
		    	addUrls.contains("googlesyndication") ||
		    	addUrls.contains("googleads") ||
		    	addUrls.contains("adservice")) {
		        route.abort();
		    } else {
		        route.resume();
		    }
		});
		
		page = context.newPage();
		page.navigate(url);

		/*
		 * demo - actions
		 */
	    
		page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Women")).click();
		page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Dress")).click();
	}

}
