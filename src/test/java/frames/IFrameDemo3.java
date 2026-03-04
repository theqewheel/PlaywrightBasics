package frames;

import java.util.regex.Pattern;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Frame;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

/**
 * =======================================================================================================================
 *                                                          IFRAMES
 * =======================================================================================================================
 * Official Doc - https://playwright.dev/docs/frames
 * FrameLocator can be used to interact with elements inside the frame
 * 									. frame accessed with id attribute using #id value
 * 									. frame accessed with name/title etc attributes
 * 									. frame accessed with index (not recommended)
 * Frame API can be used to interact with elements inside the frame when you have -
 * 									. name attribute for the frame
 * 									. URL
 */

public class IFrameDemo3 {

public static void main(String[] args) {
		
		String url = "https://redbus.in";
		
		Playwright playwright = Playwright.create();
		Browser browser = playwright.chromium().launch(new LaunchOptions().setSlowMo(1000).setHeadless(false));
		Page page = browser.newPage();
		page.navigate(url);
		
		/*
		 * demo - actions
		 */
		
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Account")).click();
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Log in")).click();
		//page.getByLabel("Mobile number").fill("9047865203"); //playwright auto links label to input
		//page.locator("input[type='tel']").fill("9047865205"); //css
		page.locator("label:has-text('Mobile number') + input").fill("9047865207"); //css scoped with label
		
		/*
		 * click google sign-in inside frame
		 */
		
		Page childPage = page.waitForPopup(() ->{
			page.frameLocator("iframe[title='Sign in with Google Button']")
			.getByText("Sign in with Google").nth(0)
			.click();
		});
		
		childPage.waitForLoadState();
		
		System.out.println("The new page opened is: " + childPage.title());
		
		childPage.close();
		
		/*
		 * Google re-captcha click inside frame 
		 * this will definitely throw an error, saying cannot change state of the check box as this re-captcha cannot be automated
		 */
		
		  page.frameLocator("iframe[title='reCAPTCHA']") 
		  	  .locator("#recaptcha-anchor")
		  	  .check();
	}

}
