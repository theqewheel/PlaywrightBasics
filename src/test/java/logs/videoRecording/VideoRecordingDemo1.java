package logs.videoRecording;

import java.nio.file.Paths;
import java.util.List;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

/**
 * =======================================================================================================================
 * SAMPLE TOPIC
 * =======================================================================================================================
 * Official Doc - https://playwright.dev/java/docs/videos#record-video
 * 
 * This needs a browser context
 * You need to close the context to save the recorded file properly
 */

public class VideoRecordingDemo1 {

public static void main(String[] args) {
		
		String url = "https://freelance-learn-automation.vercel.app/login";
		
		Playwright playwright = Playwright.create();
		Browser browser = playwright.chromium().launch(new LaunchOptions().setSlowMo(1000).setHeadless(false));
		
		
		/*
		 * demo - actions
		 */

		//start recording for the context 
		BrowserContext context = browser.newContext(new Browser.NewContextOptions()
				.setRecordVideoDir(Paths.get("src/test/resources/videos/"))
				.setRecordVideoSize(640, 480));
		
		Page page = context.newPage();
		
		page.navigate(url);
		
		page.waitForLoadState();

		System.out.println("Parent Page Title is: " + page.title());

		// Opening multiple new window from the parent window by clicking on an element
		Locator allLinks = page.locator("//div[@class='social']//a");
		
		for(int i=0; i<allLinks.count(); i++) {
			allLinks.nth(i).click();
		}
	
		List<Page> allPages = context.pages();
		
		for(Page p:allPages) {
			if(p.title().contains("Facebook")) {
				System.out.println("New Child Page Title is: " + p.title());
				p.getByText("Email").fill("Autobot1@gmail.com");
				p.getByText("Password", new Page.GetByTextOptions().setExact(true)).fill("autobot1@123");
				p.close();
				break;
			}
		}

		page.bringToFront();
		
		page.getByPlaceholder("Enter Email").fill("Autobot2@gmail.com");
		page.getByPlaceholder("Enter Password").fill("autobot2@123");
		
		context.close();
		browser.close();
				
	}

}
