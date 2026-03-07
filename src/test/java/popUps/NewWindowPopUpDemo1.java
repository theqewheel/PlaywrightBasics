package popUps;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.microsoft.playwright.Browser;
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
 * to a new window - when a new window opens up seprately - when multiple new
 * windows open up seperately
 */

public class NewWindowPopUpDemo1 {

	static Page page;

	public static void main(String[] args) {

		String url = "https://demo.automationtesting.in/Windows.html";

		Playwright playwright = Playwright.create();
		Browser browser = playwright.chromium().launch(new LaunchOptions().setSlowMo(1000).setHeadless(false)
				.setArgs(Arrays.asList("--disable-features=IsolateOrigins,site-per-process")));
		page = browser.newPage();
		page.navigate(url);

		/*
		 * demo - actions
		 */

		page.waitForLoadState();

		System.out.println("Parent Page Title is: " + page.title());

		// Opening a new window from the parent window by clicking on an element

		Page childWindow = page.waitForPopup(() -> {
			page.getByText(Pattern.compile("Open New Seperate Windows")).click();
			page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Click")).click();
		});

		childWindow.waitForLoadState();

		System.out.println("Child Window Page Title is: " + childWindow.title());
		
		page.bringToFront();

	}

}