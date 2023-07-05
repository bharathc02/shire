package com.shire.selenium.driver;

import org.openqa.selenium.WebDriver;

public final class WebDriverPool extends AbstractPool<WebDriver> {

  private static final WebDriverPool DRIVER_POOL = new WebDriverPool();
  private static final String DEFAULT_WEB_DRIVER_KEY = WebDriverPool.class.getName();

  public static WebDriverPool get() {
    return DRIVER_POOL;
  }

  public WebDriver fetch() {
    return fetch(DEFAULT_WEB_DRIVER_KEY);
  }

  public void store(WebDriver value) {
    store(DEFAULT_WEB_DRIVER_KEY, value);
  }

  protected void gracefullyQuit(WebDriver driver) {
    try {
      driver.quit();
    } catch (Exception ignored) {

    }
  }
}
