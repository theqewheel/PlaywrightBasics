package webtables;

import java.util.Arrays;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

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
 * References - https://www.youtube.com/watch?v=-oGI5AxOWoU
 */

public class WebTablePaginationDemo {
	
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
		
		// page1 text to search - Wireless Earbuds
		// page2 text to search - Action Camera
		// page3 text to search - Desktop Computer
		// page4 text to search - VR Headset
		
		boolean isFound = false;
		String searchText = "VR Headsetw";
		
		//fetch page count for the web table
		Locator nextPage = page.locator("#pagination > li > a");	
		int pageCount = nextPage.count();
		System.out.println("Total Pages for web table is : " + pageCount);
		
		
		/*
		 * search for a text content in the web table by switching across table pages
		 */
		
		for(int i=0; i<pageCount; i++) { //i starts from zero because the Locator index starts from zero
			
			Locator rows = page.locator("#productTable > tbody > tr");
			
			Locator targetRow = rows.locator(":scope", new Locator.LocatorOptions().setHasText(searchText));
			
			if(targetRow.count() > 0) {
				targetRow.getByRole(AriaRole.CHECKBOX).click();
				isFound = true;
				break;
			}
			else if(i < pageCount - 1 ){  //we need to click next page only until the last page
				nextPage.nth(i+1).click();
				page.waitForLoadState();
			}
			
		}
		
		//you can assert here if value not found
		
		if(!isFound) { 
			System.out.println("The search value - '" + searchText + "' was not found on web table!!!");
		}	
		
	}

}
