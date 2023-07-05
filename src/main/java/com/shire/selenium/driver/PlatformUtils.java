package com.shire.selenium.driver;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;

public class PlatformUtils {

  private final WebDriver driver;

  public PlatformUtils(WebDriver driver) {
    this.driver = driver;
  }

  public boolean isAndroid() {
    return isPlatform(Platform.ANDROID);
  }

  public boolean isIOS(WebDriver driver) {
    return isPlatform(Platform.IOS);
  }

  public boolean isPlatform(Platform platform) {
    Platform actualPlatform = new CapabilityInspector(driver).getPlatform();
    return actualPlatform.is(platform);
  }
}
