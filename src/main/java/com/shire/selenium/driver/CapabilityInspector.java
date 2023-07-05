package com.shire.selenium.driver;

import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import java.util.Optional;
import java.util.function.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;

public class CapabilityInspector {

  private final Capabilities capabilities;
  private final Predicate<Capabilities> BROWSER = e -> notEmpty(e, CapabilityType.BROWSER_NAME);
  private final Predicate<Capabilities> ANDROID_PLATFORM = e -> equalsAny(e,
      MobileCapabilityType.PLATFORM_NAME, "Android");
  private final Predicate<Capabilities> ANDROID_AUTOMATION_NAME = e -> equalsAny(e,
      MobileCapabilityType.AUTOMATION_NAME, "UiAutomator", "UiAutomator2", "espresso");
  private final Predicate<Capabilities> ANDROID = ANDROID_PLATFORM.or(ANDROID_AUTOMATION_NAME);
  private final Predicate<Capabilities> IOS_PLATFORM = e -> equalsAny(e,
      MobileCapabilityType.PLATFORM_NAME, "IOS");
  private final Predicate<Capabilities> IOS_AUTOMATION_NAME = e -> equalsAny(e,
      MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
  private final Predicate<Capabilities> IOS = IOS_PLATFORM.or(IOS_AUTOMATION_NAME);
  private final Predicate<Capabilities> MOBILE = ANDROID.or(IOS);
  private final Predicate<Capabilities> NATIVE = e -> equalsAny(e,
      AndroidMobileCapabilityType.APP_PACKAGE, AndroidMobileCapabilityType.APP_ACTIVITY,
      IOSMobileCapabilityType.BUNDLE_ID, MobileCapabilityType.APP);

  public CapabilityInspector(WebDriver driver) {
    this.capabilities = getCapabilities(driver);
  }

  public CapabilityInspector(Capabilities capabilities) {
    this.capabilities = capabilities;
  }

  private boolean notEmpty(Capabilities capabilities, String capabilityName) {
    Object cap = capabilities.getCapability(capabilityName);
    if (cap == null) {
      return false;
    }
    String actual = cap.toString();
    return StringUtils.isNotEmpty(actual);
  }

  private boolean equalsAny(Capabilities capabilities, String capabilityName,
      String... searchStrings) {
    Object cap = capabilities.getCapability(capabilityName);
    if (cap == null) {
      return false;
    }
    String actual = cap.toString();
    return StringUtils.equalsAnyIgnoreCase(actual, searchStrings);
  }

  public boolean isAndroid() {
    return Optional.ofNullable(capabilities)
        .filter(ANDROID)
        .isPresent();
  }

  public boolean isIOS() {
    return Optional.ofNullable(capabilities)
        .filter(IOS)
        .isPresent();
  }

  public boolean isMobile() {
    return isIOS() || isAndroid();
  }

  public boolean isNative() {
    return Optional.ofNullable(capabilities)
        .filter(BROWSER.negate())
        .filter(NATIVE)
        .isPresent();
  }

  public boolean isWeb() {
    return Optional.ofNullable(capabilities)
        .filter(BROWSER)
        .isPresent();
  }

  public boolean isMobileWeb() {
    return Optional.ofNullable(capabilities)
        .filter(BROWSER)
        .filter(MOBILE)
        .isPresent();
  }

  public Platform getPlatform() {
    return Optional.ofNullable(capabilities)
        .map(Capabilities::getPlatformName)
        .orElseThrow(() -> new UnsupportedOperationException("Cannot find platformName"));
  }

  public Capabilities getCapabilities(WebDriver driver) {
    Capabilities capabilities;
    if (driver instanceof HasCapabilities) {
      capabilities = ((HasCapabilities) driver).getCapabilities();
    } else {
      throw new UnsupportedOperationException("Cannot extract capabilities for driver");
    }
    return capabilities;
  }
}

