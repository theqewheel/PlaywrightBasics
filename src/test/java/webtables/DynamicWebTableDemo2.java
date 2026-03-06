package webtables;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

/**
 * =======================================================================================================================
 * WEBTABLES
 * =======================================================================================================================
 * A web table contains th (headers) tr (rows) and td (columns) There are Static
 * & Dynamic web tables Static web tables have pre-defined or constant th,tr,td
 * Dynamic web tables can have dynamic td,tr Paginated web tables span across
 * multiple page content due to excess data rows Web tables are handled like any
 * other locators and can use filters and loops to iterate Fetch all the row
 * elements to use :scope function to print all contents of the web table
 * References - https://www.youtube.com/watch?v=-oGI5AxOWoU
 */

public class DynamicWebTableDemo2 {

	static Page page;

	public static void main(String[] args) {

		String url = "https://www.dezlearn.com/webtable-example/";

		Playwright playwright = Playwright.create();
		Browser browser = playwright.chromium().launch(new LaunchOptions().setSlowMo(1000).setHeadless(false)
				.setArgs(Arrays.asList("--disable-features=IsolateOrigins,site-per-process")));
		page = browser.newPage();
		page.navigate(url);

		/*
		 * demo - actions
		 */

		String searchName = "M";
		boolean standard = true;
		boolean premium = true;
		String type = "Sedan"; // can have values Sedan, Suv, Sports, Hatchback
		String comments = "Test Comments";

		boolean isFound = false;
		
		// handle dynamic pop-ups
		page.onDialog(dialog -> {
			System.out.println(dialog.message());
			dialog.dismiss();
		});
		

		// header index map fetched

		Map<String, Integer> headers = getHeaderIndexMap();

		Locator rows = page.locator("table[class='tg'] tr");
		Locator targetRow = rows.filter(new Locator.FilterOptions().setHasText(Pattern.compile(searchName)));
		int totTargetRows = targetRow.count();

		if (targetRow.count() > 0) {
			
			System.out.println("Found matching rows for search name - " + searchName + ": " + totTargetRows);
			
			for (int i = 0; i < totTargetRows; i++) {

				// print the email
				
				String email = targetRow.nth(i).locator("td").nth(headers.get("Email")).innerText();
				System.out.println("The Email of the Target Row is: " + email);

				// fill the Standard checkbox

				boolean isStandard = targetRow.nth(i).locator("td").nth(headers.get("Standard")).getByRole(AriaRole.CHECKBOX).isChecked();

				if ((standard && !isStandard) || (!standard && isStandard)) {

					targetRow.nth(i).locator("td").nth(headers.get("Standard")).getByRole(AriaRole.CHECKBOX).check();
					System.out.println("The Standard opt of the Target Row is: checked");
				}

				// fill the Premium checkbox
				
				boolean isPremium = targetRow.nth(i).locator("td").nth(headers.get("Premium")).getByRole(AriaRole.CHECKBOX).isChecked();

				if ((premium && !isPremium) || (!premium && isPremium)) {

					targetRow.nth(i).locator("td").nth(headers.get("Premium")).getByRole(AriaRole.CHECKBOX).check();
					System.out.println("The Premium opt of the Target Row is: checked");
				}

				// select the Type dropdown
				
				if(type!="" && type!=null) {
					
					targetRow.nth(i).locator("td").nth(headers.get("Type")).getByRole(AriaRole.COMBOBOX).selectOption(type);
					System.out.println("The Type of the Target Row is selected as: " + type);
				}
				else {
					
					//choose a default value at index 1
					targetRow.nth(i).locator("td").nth(headers.get("Type")).locator("select").selectOption(new SelectOption().setIndex(1));	
					System.out.println("The Type of the Target Row is selected as: default");
				}
				
				// enter the comments
				targetRow.nth(i).locator("td").nth(headers.get("Comments")).getByRole(AriaRole.TEXTBOX).fill(comments);
				System.out.println("The Comment of the Target Row is entered");
			}
		}
		
	}

	// re-usable method for fetching the headers and its index as a Map

	public static Map<String, Integer> getHeaderIndexMap() {

		Map<String, Integer> headerMap = new HashMap<>();

		Locator headers = page.locator("table[class='tg'] tr th");

		for (int i = 0; i < headers.count(); i++) {

			String headerName = headers.nth(i).innerText().trim();

			headerMap.put(headerName, i);
		}

		return headerMap;
	}

}
