package com.shire.selenium.driver;
import com.google.common.base.Preconditions;
import io.appium.java_client.remote.MobileCapabilityType;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Level;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.net.PortProber;
import org.openqa.selenium.remote.Browser;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariOptions;

public class CapabilityBuilder {
  private Capabilities capabilities;

  public CapabilityBuilder() {
    this(new DesiredCapabilities());
  }

  public CapabilityBuilder(Map<String, Object> capabilitiesMap) {
    this(new DesiredCapabilities(capabilitiesMap));
  }

  public CapabilityBuilder(Capabilities capabilities) {
    this.capabilities = capabilities;
  }

  private ChromeOptions getChromeOptions(boolean headless) {
    ChromeOptions chrome = new ChromeOptions();
    Map<String, String> chromePreferences = new HashMap<>();
    chromePreferences.put("profile.password_manager_enabled", "false");
    chrome.setCapability("chrome.switches", Arrays.asList("--no-default-browser-check"));
    chrome.setCapability("chrome.prefs", chromePreferences);
    chrome.setHeadless(headless);
    return chrome;
  }

  private DesiredCapabilities getChromeAndroidOptions() {
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability("browserName", "Chrome");
    capabilities.setCapability("platformName", "Android");
    capabilities.setCapability("deviceName", "Android");
    return capabilities;
  }

}
