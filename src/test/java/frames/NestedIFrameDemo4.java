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

public class NestedIFrameDemo4 {

public static void main(String[] args) {
		
		String url = "https://demo.automationtesting.in/Alerts.html";
		
		Playwright playwright = Playwright.create();
		Browser browser = playwright.chromium().launch(new LaunchOptions().setSlowMo(1000).setHeadless(false));
		Page page = browser.newPage();
		page.navigate(url);
		
		/*
		 * demo - actions
		 */
		
		//page.getByText("SwitchTo").click();
		page.getByText("SwitchTo").hover();
		page.getByText("Frames").click();
		page.getByText(Pattern.compile(".*Iframe with in an Iframe.*")).click();
		
		/*
		 * get the parent frame
		 */
		
		Frame parentFrame = page.frameByUrl(Pattern.compile("MultipleFrames.*"));
		
		/*
		 * get the child frame with index or use first/last
		 */
			
		Frame childFrame = parentFrame.childFrames().getFirst();
		//Frame childFrame = parentFrame.childFrames().get(0);
		
		/*
		 * Access the element in child frame
		 */
		
		childFrame.locator("//input[@type='text']").fill("Test 1");
		
		/*
		 * Single line for accessing the child frame elements
		 */
		
		page.frameByUrl(Pattern.compile("MultipleFrames.*"))
			.childFrames().get(0)
			.getByRole(AriaRole.TEXTBOX)
			.fill("Test 2");
		
	}

}
