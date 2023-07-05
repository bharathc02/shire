package com.shire.selenium;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;

public class DriverBase {

  private String webDriverKey = "WebDriver";

  @AfterMethod
  public void teardown(ITestContext context) {
    WebDriver driver = getDriver(context);
    driver.quit();
  }

  protected void setDriver(ITestContext context, WebDriver driver) {
    context.setAttribute(webDriverKey, driver);
  }

  protected WebDriver getDriver(ITestContext context) {
    Object attribute = context.getAttribute(webDriverKey);
    if (attribute == null) {
      throw new RuntimeException("WebDriver key not present");
    }
    if (!(attribute instanceof WebDriver)) {
      throw new RuntimeException("WebDriver key has different object");
    }
    return (WebDriver) attribute;
  }

  protected WebDriver createLocalChromeDriver(ChromeOptions capabilities) {
    return new ChromeDriver(capabilities);
  }

  protected WebDriver createRemoteWebDriver(Capabilities capabilities) {
    try {
      return new RemoteWebDriver(new URL("http://localhost:4449/wd/hub"), capabilities);
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

}
