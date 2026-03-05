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

public class DynamicWebTableDemo1 {
	
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
		
		String searchBrowser = "Firefox";
		
		Locator rows = page.locator("#taskTable tbody tr");
		int rowCount = rows.count();
		System.out.println("Web Table Row Count : " + rowCount);
		
		Locator headers = page.locator("#taskTable thead tr th");
		int colCount = headers.count();
		System.out.println("Web Table Column Count : " + colCount);
		
		int cellCount = page.locator("#taskTable tbody tr td").count();
		System.out.println("Web Table Cell Count : " + cellCount);
		
		/*
		 * print the specific cell content
		 */
		
		String cellText = page.locator("#taskTable tbody tr:nth-child(2) > td:nth-child(1)").textContent();
		System.out.println("The cell content at cell [2][1]: " + cellText);
		
		/*
		 * print the complete table content using scope function
		 */
	
		rows.locator(":scope").allInnerTexts().forEach(cell -> System.out.println(cell));
		
		/*
		 * get index of a particular column header
		 */
		
		String colHeader = "CPU (%)";
		int headerColIndex = -1;
		
		for(int i=0;i<colCount; i++) {
			if(headers.nth(i).innerText().equals(colHeader)) {
				headerColIndex = i;
				System.out.println("Header Column - " + colHeader + " is present at index[" + headerColIndex + "]");
				break;
			}
		}	
		
		/*
		 * check for a specific content in the web table and print the column value for chosen header
		 */
		
		Locator targetRow = rows.filter(new Locator.FilterOptions().setHasText(searchBrowser));
		
		if (targetRow.count() > 0) {
			String resultText = targetRow.locator("td")
										 .nth(headerColIndex)
										 .innerText();
			System.out.println("The value for [" + searchBrowser + "-" + colHeader + "] is " + resultText);
		}
		else {
			System.out.println("The value for [" + searchBrowser + "-" + colHeader + "] is not found !!!" );
		}
		
	}

}
