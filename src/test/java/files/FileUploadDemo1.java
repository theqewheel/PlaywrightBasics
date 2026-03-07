package files;


import java.nio.file.Path;
import java.nio.file.Paths;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

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
 * - if input tag provided type=file
 */

public class FileUploadDemo1 {

public static void main(String[] args) {
		
		String url = "https://the-internet.herokuapp.com/upload";
		String filename1 = "testFile1.txt";
		String filename2 = "testFile2.properties";
		
		Playwright playwright = Playwright.create();
		Browser browser = playwright.chromium().launch(new LaunchOptions().setSlowMo(1000).setHeadless(false));
		Page page = browser.newPage();
		page.navigate(url);
		
		/*
		 * demo - actions
		 */
		
		//fetching file from current directory - Way 1
		
		String filepath1 = ("src/main/resources/upload/"+filename1).toString();
		System.out.println(filepath1);
		page.locator("#file-upload").setInputFiles(Paths.get(filepath1));
		
		
		
		//fetching file from current directory - Way 2
		Path filePath2 = Paths.get(
				System.getProperty("user.dir"),
				"src",
				"main",
				"resources",
				"upload",
				filename2
				);
		
		System.out.println(filePath2.toAbsolutePath().toString());
		page.locator("#file-upload").setInputFiles(filePath2);
		
		//fetching a file unavailable - throws exception handled
		Path filePath3 = Paths.get(
				System.getProperty("user.dir"),
				"src",
				"main",
				"resources",
				"upload",
				"sample.txt"
				);
		
		System.out.println(filePath3.toAbsolutePath().toString());
		try {
			page.locator("#file-upload").setInputFiles(filePath3);
		}catch(Exception e) {
			System.out.println("Exception in file upload: " + e.getMessage());
		}
		
		//remove selected file
		page.locator("#file-upload").setInputFiles(new Path[0]);
		

	}

}
