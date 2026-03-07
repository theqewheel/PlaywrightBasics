package files;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.FileChooser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

/**
 * =======================================================================================================================
 * FILE UPLOAD - FILE CHOOSER
 * =======================================================================================================================
 * Official Doc - https://playwright.dev/java/docs/api/class-filechooser
 * 
 * - Single File Upload - Multiple File Upload - Directory Selection - Remove
 * Selected Files - Upload Buffer from Memory - if no input tag provided
 */

public class FileUploadDemo2 {

	public static void main(String[] args) {

		String url = "https://the-internet.herokuapp.com/upload";

		Playwright playwright = Playwright.create();
		Browser browser = playwright.chromium().launch(new LaunchOptions().setSlowMo(1000).setHeadless(false));
		Page page = browser.newPage();
		page.navigate(url);

		/*
		 * demo - actions
		 */

		// when no input tag defined for file upload then use file chooser handler
		FileChooser fileChooser = page.waitForFileChooser(() -> page.locator("#drag-drop-upload").click());

		// fileChooser.setFiles(filePath1); //for single file upload

		// uploading multiple files
		if (fileChooser.isMultiple()) {

			Path[] filesToUpload = { Paths.get("src/main/resources/upload/" + "testFile1.txt"),
					Paths.get("src/main/resources/upload/" + "testFile2.properties"),
					Paths.get("src/main/resources/upload/" + "testFile3.txt") };

			System.out.println("Multi File upload supported - testing. . . ");

			fileChooser.setFiles(filesToUpload);

			// OR

			/*
			 * Path filePath1 = Paths.get(System.getProperty("user.dir"), "src", "main",
			 * "resources", "upload", "testFile1.txt");
			 * 
			 * Path filePath2 = Paths.get(System.getProperty("user.dir"), "src", "main",
			 * "resources", "upload", "testFile2.properties");
			 * 
			 * Path filePath3 = Paths.get(System.getProperty("user.dir"), "src", "main",
			 * "resources", "upload", "testFile3.txt");
			 * 
			 * fileChooser.setFiles(new Path[] {filePath1,filePath2, filePath3});
			 */

		}

	}

}
