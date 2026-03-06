package popUps;

import java.util.List;
import java.util.regex.Pattern;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

/**
 * =======================================================================================================================
 * POP-UP WINDOWS
 * =======================================================================================================================
 * Official Doc - https://playwright.dev/java/docs/pages#pages
 * 
 * The Page events on the browser can be used to get new pages that are created
 * on the browser context - Get Page after a specific action - if the action is
 * unknown for triggering a new page The Popups - when a new blank tab opens up
 * to a new window - when a new window opens up seprately - when multiple new
 * windows open up seperately
 */

public class MultipleTabPopUpDemo2 {

	static Page page;

	public static void main(String[] args) {

		String url = "https://demo.automationtesting.in/Windows.html";

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

		/*
		 * Opening a new tab from the parent window by clicking on an element set
		 * predicate ensures that all the pages expected are loaded using a count of
		 * pages required to load
		 * 
		 * This is essential when the pages - might take time to load (delayed loads)
		 */

		Page tabs = page.waitForPopup(new Page.WaitForPopupOptions().setPredicate(p -> p.context().pages().size() == 3),
				() -> {
					page.getByText(Pattern.compile("Open Seperate Multiple Windows")).click();
					page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Click")).click();
				});

		tabs.waitForLoadState();

		List<Page> pages = tabs.context().pages();
		System.out.println("Total Pages opened: " + pages.size());

		pages.forEach(tab -> {
			//tab.waitForLoadState();
			System.out.println("Opened Page: " + tab.title());
		});
		
		Page index = null, selenium = null;
		
		for(int i=0; i<pages.size(); i++) {
			switch(pages.get(i).title().toLowerCase()){
				case "index":
					index = pages.get(i);
					break;
				case "selenium":
					selenium = pages.get(i);
					break;
				default:
					System.out.println("New Page title do not have switch: " + pages.get(i).title());
					
			}
		}
		
		index.bringToFront();
		
		index.getByPlaceholder("Email id for Sign Up").fill("autobot@gmail.com");
		index.locator("img[id='enterimg']").click();
		
		index.waitForLoadState();
		
		System.out.println("Navigated to Page: " + index.title());
		
		selenium.bringToFront();
		
		
	}
}