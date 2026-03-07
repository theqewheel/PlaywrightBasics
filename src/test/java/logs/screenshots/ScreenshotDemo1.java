package logs.screenshots;

import java.nio.file.Paths;
import java.util.Base64;

import org.testng.util.TimeUtils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

/**
 * =======================================================================================================================
 * SCREENSHOTS
 * =======================================================================================================================
 * Official Doc - https://playwright.dev/java/docs/screenshots
 * 
 * - Screenshots that can be saved as byte[] arrays
 * - Screenshots as full page screenshots
 * - Screenshot of a given Element locator
 * - convert byte[] array to desired formats like base64
 * - Set path for screenshot
 */

public class ScreenshotDemo1 {

	public static void main(String[] args) {

		String url = "https://www.naukri.com/";

		Playwright playwright = Playwright.create();
		Browser browser = playwright.chromium().launch(new LaunchOptions().setSlowMo(1000).setHeadless(false));
		Page page = browser.newPage();
		page.navigate(url);

		/*
		 * demo - actions
		 */

		/*
		 * In third party reporting tools like allure, extent reports etc the
		 * screenshots has to be passed as byte[] or base64 or .png Hence the playwright
		 * implementation fetches screenshot as byte[] array.
		 */

		// capture screenshot as byte array
		byte[] arr1 = page.screenshot();

		// capture and save screenshot to directory
		page.screenshot(
				new Page.ScreenshotOptions().setPath(Paths.get("src/test/resources/screenshots/screenshot1.png")));

		// capture and save FULL screenshot to directory
		byte[] arr3 = page.screenshot(new Page.ScreenshotOptions().setFullPage(true)
				.setPath(Paths.get("src/test/resources/screenshots/screenshot2.png")));

		// for converting the byte array screenshot to base64 for external reporting
		// methods

		System.out.println(Base64.getEncoder().encodeToString(arr3));

		// capture screenshot of a particular web element
		page.locator("a[title='Jobseeker Login']").screenshot(new Locator.ScreenshotOptions()
				.setPath(Paths.get("src/test/resources/screenshots/ElementScreenshot1.png")));
		
		page.close();

		browser.close();

	}

}
