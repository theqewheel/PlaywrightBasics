package dialogs;

import java.util.regex.Pattern;

import org.testng.Assert;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.Dialog;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

/**
 * =======================================================================================================================
 * ALERTS
 * =======================================================================================================================
 * Official Docs - https://playwright.dev/java/docs/dialogs
 * 
 * Web Dialogs can be of three types - alert, confirm, prompt
 * 
 * Default - all alerts are dismissed by Playwright. Need not handle. If there
 * is no listener for Page.onDialog(handler), all dialogs are automatically
 * dismissed.
 * 
 * If handled then as below - accept alert (clicks OK button) dismiss alert
 * (clicks Cancel button) prompt and accept/dismiss (enters input and clicks
 * Ok/Cancel button)
 * 
 * Page.onDialog listener should handle the dialog, else the subsequent actions
 * will fail.
 * 
 * You should have only single handler on a method else all handlers will try to
 * act on the same alert.
 * 
 * Sometimes the alert pop up is shown on the browser and sometimes not.
 * Thats expected behaviour as Playwright runs fast and process it like 
 */

public class AlertOkCancelDemo {

	public static void main(String[] args) {

		String url = "https://demo.automationtesting.in/Alerts.html";

		Playwright playwright = Playwright.create();
		Browser browser = playwright.chromium().launch(new LaunchOptions().setSlowMo(1000).setHeadless(false));
		Page page = browser.newPage();
		page.navigate(url);

		/*
		 * demo - testing alert with okay auto accepts alerts
		 */

		page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Alert with OK & Cancel").setExact(true))
				.click();

		// if want to control alert manually call the dialog handler prior to the action
		// that generates the dialog.

		page.onDialog(dialog -> {
			System.out.println("Dialog Message: " + dialog.message());
			Assert.assertTrue(dialog.message().contains("Press a Button"), "Expected Dialog Message: Press a Button.");
			dialog.accept();
			// dialog.dismiss();
		});

		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(Pattern.compile("confirm"))).click();

		// This will work if placed after the action which invokes the alert because
		// playwright auto dismisses the alerts appearing

		/*
		 * page.onDialog(dialog -> { System.out.println("Dialog Message: " +
		 * dialog.message());
		 * Assert.assertTrue(dialog.message().contains("I am an alert"),
		 * "Expected Dialog Message: I am an alert."); //dialog.accept();
		 * dialog.dismiss(); });
		 */

		PlaywrightAssertions.assertThat(page.getByText("You pressed")).hasText("You pressed Ok");
		//PlaywrightAssertions.assertThat(page.getByText(Pattern.compile("You pressed",Pattern.CASE_INSENSITIVE))).containsText("Cancel");

	}

}
