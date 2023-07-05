package com.shire.selenium;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class WebTest extends DriverBase {

  //  @Test
  public void testNewWindow(ITestContext context) {
    ChromeOptions options = new ChromeOptions();
    WebDriver driver = new ChromeDriver(options);
    setDriver(context, driver);
    driver.navigate().to("https://the-internet.herokuapp.com/");
    String firstHandle = driver.getWindowHandle();
    driver.switchTo().newWindow(WindowType.WINDOW);
    driver.navigate().to("https://www.google.com/");
    driver.close();
    driver.switchTo().window(firstHandle);
  }

  @Test
  public void testElementScreenshot(ITestContext context) throws IOException {
    ChromeOptions options = new ChromeOptions();
    WebDriver driver = createRemoteWebDriver(options);
    setDriver(context, driver);
    WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(30));
    driver.navigate().to("https://the-internet.herokuapp.com/");
    WebElement inputElement = webDriverWait
        .until(ExpectedConditions.elementToBeClickable(By.xpath("//li/a[@href='/inputs']")));
    File file = inputElement.getScreenshotAs(OutputType.FILE);
    FileUtils.copyFile(file, new File("inputElement.png"));
    inputElement.click();
  }
}
