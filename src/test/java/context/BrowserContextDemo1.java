package context;


import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

/**
 * =======================================================================================================================
 * BROWSER CONTEXT
 * =======================================================================================================================
 * Official Doc - https://playwright.dev/docs/api/class-browsercontext
 * 
 * This provides a way to operate with multiple independent browser sessions without sharing cookies or cache
 * Dispose context when no longer needed
 * Each context opens the browser in a incognito mode
 * New pages are created inside the context
 * You can run the test scripts with multi users - admin and guest and interacting with each other
 * Multiple Logins can be avoided
 */

public class BrowserContextDemo1 {

public static void main(String[] args) {
		
		String url1 = "https://demo.automationtesting.in/Index.html";
		String url2 = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
		
		Playwright playwright = Playwright.create();
		Browser browser = playwright.chromium().launch(new LaunchOptions().setSlowMo(1000).setHeadless(false));
		
		/*
		 * demo - actions
		 */
		
		//create browser contexts
		BrowserContext bx1 = browser.newContext();
		BrowserContext bx2 = browser.newContext();
		
		//create pages on the context
		Page bxpage1 = bx1.newPage();
		bxpage1.navigate(url1);
		
		Page bxpage2 = bx2.newPage();
		bxpage2.navigate(url2);
		
		System.out.println("Page Opened on Browser Context1: " + bxpage1.title());
		System.out.println("Page Opened on Browser Context2: " + bxpage2.title());
		
		//bxpage1.close();
		//bx1.close();
		
		//bxpage2.close();
		//bx2.close();
		
	}

}
