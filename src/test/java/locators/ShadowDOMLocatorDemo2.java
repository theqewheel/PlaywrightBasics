package locators;


import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

/**
 * =======================================================================================================================
 *                                                        LOCATORS
 * =======================================================================================================================
 * Official Doc - https://playwright.dev/java/docs/locators#locating-elements
 * Official Doc - https://playwright.dev/java/docs/locators#filtering-locators
 * Official Doc - https://playwright.dev/java/docs/locators#locate-in-shadow-dom
 * Official Doc - https://playwright.dev/java/docs/locators#locator-operators
 * Official Doc - https://developer.mozilla.org/en-US/docs/Web/API/Web_components/Using_shadow_DOM
 * 
 * Shadow DOM elements can be accessed only after accessing the shadow root
 * Auto-piercing no need to switch to shadow dom
 * Always use CSS , XPaths do not work for Shadow root
 * Shadow Root should be "open" to perform any interaction {#shadow-root (open)}
 * Concepts :
 * 		- Shadow Host : the regular dom node to which shadow dom is attached to
 * 		- Shadow Root : The root node of the shadow tree
 * 		- Shadow Boundary : the pace where shadow dom ends and regular dom begins
 * 		- Shadow Tree : the dom tree inside the shadow dom
 */

public class ShadowDOMLocatorDemo2 {

public static void main(String[] args) {
		
		String url = "https://selectorshub.com/xpath-practice-page/";
		
		Playwright playwright = Playwright.create();
		Browser browser = playwright.chromium().launch(new LaunchOptions().setSlowMo(1000).setHeadless(false));
		Page page = browser.newPage();
		page.navigate(url);
		
		/*
		 * demo - actions
		 */
		
		//fetch the shadow host (provided shadow root is set to 'open')
		Locator shadowRoot = page.locator("#userName");
		shadowRoot.locator("#kils").fill("Autobot1"); //or
		shadowRoot.getByPlaceholder("enter name").fill("Autobot2"); //or
		
		//single line statement for accessing the shadow dom element
		page.locator("#userName #kils").fill("Autobot3");
		

	}

}
