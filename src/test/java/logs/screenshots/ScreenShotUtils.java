package logs.screenshots;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ScreenShotUtils {

	public static byte[] captureScreenshot(Page page) {

		SimpleDateFormat customFormat = new SimpleDateFormat("dd_MM_yy_HH_mm_ss");

		Date date = new Date(); // gives current date

		String newDate = customFormat.format(date);

		byte[] arr = page.screenshot(new Page.ScreenshotOptions()
				.setPath(Paths.get("src/test/resources/screenshots/shortScreenshot_" + newDate + ".png")));

		return arr;

	}

	public static byte[] captureFullScreenshot(Page page) {

		SimpleDateFormat customFormat = new SimpleDateFormat("dd_MM_yy_HH_mm_ss");

		Date date = new Date(); // gives current date

		String newDate = customFormat.format(date);

		byte[] arr = page.screenshot(new Page.ScreenshotOptions().setFullPage(true)
				.setPath(Paths.get("src/test/resources/screenshots/fullScreenshot_" + newDate + ".png")));

		return arr;

	}
	
	public static byte[] captureLocatorScreenshot(Locator locator) {

		SimpleDateFormat customFormat = new SimpleDateFormat("dd_MM_yy_HH_mm_ss");

		Date date = new Date(); // gives current date

		String newDate = customFormat.format(date);
		
		byte[] arr = locator.screenshot(new Locator.ScreenshotOptions()
				.setPath(Paths.get("src/test/resources/screenshots/locatorScreenshot_" + newDate + ".png")));

		return arr;

	}
}
