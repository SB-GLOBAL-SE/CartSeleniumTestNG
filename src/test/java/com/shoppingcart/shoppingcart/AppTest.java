package com.shoppingcart.shoppingcart;

import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


import static org.junit.Assert.*;

public class AppTest {

  /*public WebDriver driver;
   * 
   */
	
private RemoteWebDriver driver;
@BeforeSuite

public void setup() throws MalformedURLException {

String username = "dermot.canniffe%40smartbear.com"; // Your username
String authkey = "u1b26bf49cce3f32";  // Your authkey
String testScore = "unset";

DesiredCapabilities caps = new DesiredCapabilities();

caps.setCapability("name", "Shopping Cart Test");
caps.setCapability("build", "1.0");
caps.setCapability("browserName", "Safari");
caps.setCapability("deviceName", "iPad 6th Generation Simulator");
caps.setCapability("platformVersion", "12.0");
caps.setCapability("platformName", "iOS");
caps.setCapability("deviceOrientation", "landscape");


driver = new RemoteWebDriver(new URL("http://" + username + ":" + authkey +"@hub.crossbrowsertesting.com:80/wd/hub"), caps);

}
	

@Test
public void Login() {

    // String hostname = "localhost";
	//driver.get("https://" + hostname + "/shoppingcart/");
    String hostname = "shopping-cart-sa.herokuapp.com";
    driver.get("https://" + hostname + "/login.php");
    driver.findElement(By.xpath("html/body/div[2]/div/div[1]/form/div[1]/input")).sendKeys("dermot@localhost");
    driver.findElement(By.xpath("html/body/div[2]/div/div[1]/form/div[2]/input")).sendKeys("password");
    driver.findElement(By.xpath("html/body/div[2]/div/div[1]/form/button")).click();
    Assert.assertEquals(driver.getTitle(), "Awesome Shopping Store - Products");
  }

  @Test
  public void Shopping() {
    // driver.findElement(By.xpath("html/body/div[2]/div/div[6]/div/div/div/div[2]/a")).click();
    driver.findElement(By.xpath("//div[6]//div[1]//div[1]//a[1]//img[1]")).click();
    String getProduct = driver.findElement(By.xpath("//h4[@class='list-group-item-heading']")).getText();
    Assert.assertEquals(getProduct, "iPhone");
    driver.findElement(By.xpath("//a[contains(text(),'Add to cart')]")).click();
    driver.findElement(By.xpath("//input[@name='Submit']")).click();
    driver.findElement(By.xpath("//body//button[2]")).click();
    String getMessage = driver.findElement(By.xpath("/html[1]/body[1]/div[2]/p[1]")).getText();
    Assert.assertEquals(getMessage, "Your order has submitted successfully.");

  }

//  @BeforeClass
//  public void beforeClass() {
//
//    System.setProperty("webdriver.gecko.driver", "C:\\Selenium\\geckodriver.exe");
//    driver = new FirefoxDriver();
//
//  }
//
//  @AfterClass
//  public void afterClass() {
//    // driver.quit();
//  }

  @AfterSuite
  public void tearDown() {
      driver.quit();
  }
  
}