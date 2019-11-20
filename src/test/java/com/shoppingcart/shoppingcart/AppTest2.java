package com.shoppingcart.shoppingcart;

import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;


import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;

//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.Assert;

import org.testng.annotations.AfterSuite;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AppTest2 {

	private RemoteWebDriver driver;
	private String testScore = "unset";
	private String username = "dermot.canniffe%40smartbear.com"; // Your username
	private String authkey = "u1b26bf49cce3f32"; // Your authkey
	private static final Logger LOGGER = Logger.getLogger(AppTest.class);

	@BeforeSuite
	public void setup() throws MalformedURLException {

		DesiredCapabilities caps = new DesiredCapabilities();

		caps.setCapability("name", "Shopping Cart Test Beta");
		caps.setCapability("build", "1.0");
		caps.setCapability("browserName", "Safari");
		caps.setCapability("deviceName", "iPhone X Simulator");
		caps.setCapability("platformVersion", "11.0");
		caps.setCapability("platformName", "iOS");
		caps.setCapability("deviceOrientation", "portrait");
		caps.setCapability("record_video", "true");

		driver = new RemoteWebDriver(
				new URL("http://" + username + ":" + authkey + "@hub.crossbrowsertesting.com:80/wd/hub"), caps);

	}

	@BeforeTest
	public void Login() {
		try {
			String hostname = "shopping-cart-sa.herokuapp.com";
			driver.get("https://" + hostname + "/login.php");
			driver.findElement(By.xpath("html/body/div[2]/div/div[1]/form/div[1]/input")).sendKeys("dermot@localhost");
			driver.findElement(By.xpath("html/body/div[2]/div/div[1]/form/div[2]/input")).sendKeys("password");
			driver.findElement(By.xpath("html/body/div[2]/div/div[1]/form/button")).click();
			Assert.assertEquals(driver.getTitle(), "Awesome Shopping Store - Products");
			testScore = "pass";
		} catch (AssertionError ae) {
			testScore = "fail";
		}
	}

	@Test
	public void Shopping() {
		try {
			driver.findElement(By.xpath("//div[6]//div[1]//div[1]//a[1]//img[1]")).click();
			String getProduct = driver.findElement(By.xpath("//h4[@class='list-group-item-heading']")).getText();
			Assert.assertEquals(getProduct, "iPhone");
			driver.findElement(By.xpath("//a[contains(text(),'Add to cart')]")).click();
			driver.findElement(By.xpath("//input[@name='Submit']")).click();
			driver.findElement(By.xpath("//body//button[2]")).click();
			String getMessage = driver.findElement(By.xpath("/html[1]/body[1]/div[2]/p[1]")).getText();
			Assert.assertEquals(getMessage, "Your order has submitted successfully.");
			testScore = "pass";
		} catch (AssertionError ae) {
			testScore = "fail";
		}
	}

	public String setScore(String seleniumTestId, String score, String username, String authkey)
			throws UnirestException {
		// Mark a Selenium test as Pass/Fail
		HttpResponse<String> response = Unirest.put("http://crossbrowsertesting.com/api/v3/selenium/{seleniumTestId}")
				.basicAuth(username, authkey).routeParam("seleniumTestId", seleniumTestId).field("action", "set_score")
				.field("score", score).asString();
		// .asJson();
		return response.getBody();
	}

	@AfterSuite
	public void tearDown() {
		LOGGER.info(setScore(driver.getSessionId().toString(), testScore, username, authkey));
		driver.quit();
	}

}