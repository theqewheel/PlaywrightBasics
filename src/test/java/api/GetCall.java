package api;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;

public class GetCall {

	Playwright playwright;
	APIRequest request;
	APIRequestContext apiRequestContext;
	APIResponse apiResponse;
	
	@BeforeMethod
	public void setUp(){
		
		playwright = Playwright.create();
		request = playwright.request();
		apiRequestContext = request.newContext();
	}
	
	@AfterMethod
	public void tearDown() {
		playwright.close();
	}
	
	@Test
	public void GetUsers() {
		
		apiResponse = apiRequestContext.get("https://gorest.co.in/public/v2/users");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~ TEST : Get Users ~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("Response Status Code: " + apiResponse.status());
		System.out.println("Response Status Code: " + apiResponse.statusText());
		System.out.println("Response Text       : " + apiResponse.text());
		System.out.println("Response Body       : " + apiResponse.body()); // gives byte[] need to parse
		System.out.println("Response URL        : " + apiResponse.url());
		System.out.println("Response Headers    : " + apiResponse.headers());
		
		//json response body formatter
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonResponse = objectMapper.readTree(apiResponse.body());
			String jsonPrettyResponse = jsonResponse.toPrettyString();
			System.out.println("Response (formatted): ");
			System.out.println(jsonPrettyResponse);
		} catch (IOException e) {
			System.out.println("Error on parsing json: ");
			e.printStackTrace();
		}
		
		//validations
		assertEquals(apiResponse.status(), 200, "Response Status code is not as expected");
		assertEquals(apiResponse.statusText(), "OK", "Response Status text is not as expected");
		assertTrue(apiResponse.headers().get("content-type").contains("json"),"Response Content type is mismatched.");
		assertTrue(apiResponse.ok(), "Response status code is not 2xx");
		
		
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~ XXX ~~~~~~~~~~~~~~~~~~~~~~~");
	}
	
	@Test
	public void GetSpecificUser() {
		
		apiResponse = apiRequestContext.get("https://gorest.co.in/public/v2/users",
				RequestOptions.create()
				.setQueryParam("id", "8406602")
				.setQueryParam("status", "inactive"));
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~ TEST : Get Specific Users ~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("Response Status Code: " + apiResponse.status());
		System.out.println("Response Status Code: " + apiResponse.statusText());
		System.out.println("Response Text       : " + apiResponse.text());
		System.out.println("Response Body       : " + apiResponse.body()); // gives byte[] need to parse
		System.out.println("Response URL        : " + apiResponse.url());
		System.out.println("Response Headers    : " + apiResponse.headers());
		
		//json response body formatter
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonResponse = objectMapper.readTree(apiResponse.body());
			String jsonPrettyResponse = jsonResponse.toPrettyString();
			System.out.println("Response (formatted): ");
			System.out.println(jsonPrettyResponse);
		} catch (IOException e) {
			System.out.println("Error on parsing json: ");
			e.printStackTrace();
		}
		
		//validations
		assertEquals(apiResponse.status(), 200, "Response Status code is not as expected");
		assertEquals(apiResponse.statusText(), "OK", "Response Status text is not as expected");
		assertTrue(apiResponse.headers().get("content-type").contains("json"),"Response Content type is mismatched.");
		assertTrue(apiResponse.ok(), "Response status code is not 2xx");
		
	}
	
}
