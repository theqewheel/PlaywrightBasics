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

public class IFrameDemo1 {

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
		page.getByText(Pattern.compile(".*Single Iframe.*")).click();
		
		/*
		 * get the frame using name attribute - if exists
		 * <iframe src="SingleFrame.html" id="singleframe" name="SingleFrame" style="float: left;height: 300px;width: 600px">
		 * <p>Your browser does not support iframes.</p></iframe>
		 */
		
		Frame frame = page.frame("SingleFrame");
		frame.getByRole(AriaRole.TEXTBOX).fill("Test 1");
		frame.getByRole(AriaRole.TEXTBOX).clear(); //this is optional only for demo - .fill will clear values and enter new value
		
		/*
		 * The combined single statement for accessing the frame locator
		 */
		
		page.frame("SingleFrame").getByRole(AriaRole.TEXTBOX).fill("Test 2");
		
		/*
		 * get the frame using the frame URL as the parameter, src is a mandatory attribute of any frame with the URL
		 */
		
		page.frameByUrl(Pattern.compile("SingleFrame.*")).getByRole(AriaRole.TEXTBOX).fill("Test 3");

		/*
		 * get the frame using the frame id or class attribute #id or .class as a parameter 
		 */
		
		page.frameLocator("#singleframe").getByRole(AriaRole.TEXTBOX).fill("Test 4");
			
	}

}
