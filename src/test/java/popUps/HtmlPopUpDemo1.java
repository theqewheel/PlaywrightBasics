package popUps;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;

/**
 * =======================================================================================================================
 * POP-UPS or OVERLAYS
 * =======================================================================================================================
 * Official Doc -
 * https://playwright.dev/java/docs/api/class-page#page-add-locator-handler
 * 
 * Overlays and Pop-Ups may not show the same way or same time It blocks any
 * further action on the web page Playwright provides addLocatorHandler()
 * function to handle these Playwright checks for the overlay locator every time
 * before an actionability check is executed Handler only executed when you try
 * to perform an action if not the overlay appeared will remain Can be executed
 * throughout your program You can create multiple handlers but only one will
 * run at a time Alert - Avoid Mouse Actions between steps and instead use
 * locators or can cause confusion on action performed by handler
 * 
 * When to use - . When you see an overlay random and dynamically appearing .
 * When multiple overlays appear in random . When overlays occur multiple times
 * in random When not recommended to use - . When the overlay or pop-up shown is
 * predictable then explicitly wait and dismiss Other variation of usage - these
 * are optional . setNoWaitAfter - for handling overlay and not wait after for
 * checking if it reappears . setTimes - can specify how much times it should
 * run
 * 
 */

public class HtmlPopUpDemo1 {

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
		page.addLocatorHandler(page.getByText("Learn It Easy"), l -> {
			page.locator("button[class='close']:visible").click();
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

				boolean isStandard = targetRow.nth(i).locator("td").nth(headers.get("Standard"))
						.getByRole(AriaRole.CHECKBOX).isChecked();

				if ((standard && !isStandard) || (!standard && isStandard)) {

					targetRow.nth(i).locator("td").nth(headers.get("Standard")).getByRole(AriaRole.CHECKBOX).check();
					System.out.println("The Standard opt of the Target Row is: checked");
				}

				// fill the Premium checkbox

				boolean isPremium = targetRow.nth(i).locator("td").nth(headers.get("Premium"))
						.getByRole(AriaRole.CHECKBOX).isChecked();

				if ((premium && !isPremium) || (!premium && isPremium)) {

					targetRow.nth(i).locator("td").nth(headers.get("Premium")).getByRole(AriaRole.CHECKBOX).check();
					System.out.println("The Premium opt of the Target Row is: checked");
				}

				// select the Type dropdown

				if (type != "" && type != null) {

					targetRow.nth(i).locator("td").nth(headers.get("Type")).getByRole(AriaRole.COMBOBOX)
							.selectOption(type);
					System.out.println("The Type of the Target Row is selected as: " + type);
				} else {

					// choose a default value at index 1
					targetRow.nth(i).locator("td").nth(headers.get("Type")).locator("select")
							.selectOption(new SelectOption().setIndex(1));
					System.out.println("The Type of the Target Row is selected as: default");
				}

				// enter the comments
				targetRow.nth(i).locator("td").nth(headers.get("Comments")).getByRole(AriaRole.TEXTBOX).fill(comments);
				System.out.println("The Comment of the Target Row is entered");

			}

			// click update
			page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Update")).click();
			String statusMessage = page.locator("#updateDiv").innerText().trim();
			System.out.println("On Click of Update >> " + statusMessage);

		}

		// waiting for the html pop-up frame to appear
		page.waitForTimeout(3000);

		Locator mailerFrame = page.locator("iframe[title='MailerLite Form']");

		if (mailerFrame.count() > 0) {

			FrameLocator frame = page.frameLocator("iframe[title='MailerLite Form']");
			
			System.out.println("The MailerLite Form is seen.");

			frame.getByPlaceholder("Name").fill("John Doe");
			frame.getByPlaceholder("Email").fill("john@test.com");
			System.out.println("The MailerLite Form is filled.");
			
			frame.getByRole(AriaRole.BUTTON, new FrameLocator.GetByRoleOptions().setName("Join Now")).click();
			
			Locator logInSuccessHeader = frame.getByText(Pattern.compile("The journey"));
			
			logInSuccessHeader.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
			
			assertThat(logInSuccessHeader).containsText("The journey of a thousandS miles begins with one step.");
		}
		else
			System.out.println("The MailerLite Form is NOT seen.");
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