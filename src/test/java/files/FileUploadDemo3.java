package files;


import java.nio.charset.StandardCharsets;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.FilePayload;

/**
 * =======================================================================================================================
 *                                                          FILE UPLOAD
 * =======================================================================================================================
 * Official Doc - https://playwright.dev/java/docs/input#upload-files
 * 
 * - Single File Upload
 * - Multiple File Upload
 * - Directory Selection
 * - Remove Selected Files
 * - Upload Buffer from Memory
 * - if no input tag provided
 */

public class FileUploadDemo3 {

public static void main(String[] args) {
		
		String url = "https://the-internet.herokuapp.com/upload";
		
		Playwright playwright = Playwright.create();
		Browser browser = playwright.chromium().launch(new LaunchOptions().setSlowMo(1000).setHeadless(false));
		Page page = browser.newPage();
		page.navigate(url);
		
		/*
		 * demo - actions
		 */

		//upload buffer from memory
		page.locator("#file-upload").setInputFiles(new FilePayload(
				"file.txt", "text/plain", 
				"This is a sample test file from buffer memory!."
				.getBytes(StandardCharsets.UTF_8)));
	}

}
