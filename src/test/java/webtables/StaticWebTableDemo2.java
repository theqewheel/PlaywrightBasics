package webtables;

import java.util.Arrays;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

/**
 * =======================================================================================================================
 *                                                          WEBTABLES
 * =======================================================================================================================
 * A web table contains th (headers) tr (rows) and td (columns)
 * There are Static & Dynamic web tables
 * Static web tables have pre-defined or constant th,tr,td
 * Dynamic web tables can have dynamic td,tr
 * Paginated web tables span across multiple page content due to excess data rows
 * Web tables are handled like any other locators and can use filters and loops to iterate
 * Fetch all the row elements to use :scope function to print all contents of the web table
 * References - https://www.youtube.com/watch?v=X9HPgX6Fs4A
 */

public class StaticWebTableDemo2 {
	
static Page page;
	
	public static void main(String[] args) {

		String url = "https://testautomationpractice.blogspot.com/";

		Playwright playwright = Playwright.create();
		Browser browser = playwright.chromium()
				.launch(new LaunchOptions()
				.setSlowMo(1000)
				.setHeadless(false)
				.setArgs(Arrays.asList("--disable-features=IsolateOrigins,site-per-process")));
		page = browser.newPage();
		page.navigate(url);
		
		/*
		 * demo - actions
		 */
		
		Locator rows = page.locator("table[name='BookTable'] > tbody > tr");
		
		int rowCount = rows.count();
		System.out.println("Web Table Row Count : " + rowCount);
		
		/*
		 * print the complete table content using for loop
		 */
	
		for(int i=0;i<rowCount;i++) {
			
			Locator columns = rows.nth(i).locator("td");
			int columnCount = columns.count();
			
			for(int j=0;j<columnCount;j++) {
				System.out.print(columns.nth(j).innerText() + " | ");
			}
			
			System.out.println();
			System.out.println("-------------------------------------------------");
			
		}
	}

}
