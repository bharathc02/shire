package com.shire.selenium;

import static org.openqa.selenium.devtools.v104.network.Network.emulateNetworkConditions;

import java.util.Optional;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v104.emulation.Emulation;
import org.openqa.selenium.devtools.v104.network.Network;
import org.openqa.selenium.devtools.v104.network.model.ConnectionType;
import org.openqa.selenium.remote.Augmenter;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class DevToolsTest extends DriverBase {

  @Test
  public void testLatency(ITestContext context) {
    ChromeOptions options = new ChromeOptions();
    WebDriver driver = new ChromeDriver(options);
    setDriver(context, driver);
    driver.navigate().to("https://the-internet.herokuapp.com/");
    driver = new Augmenter().augment(driver);
    DevTools devTools = ((HasDevTools) driver).getDevTools();
    devTools.createSession();

    devTools.send(Network.enable(Optional.of(1000000), Optional.empty(), Optional.empty()));
    devTools.send(
        emulateNetworkConditions(false, 100, 200000, 100000,
            Optional.of(ConnectionType.CELLULAR4G)));

    long startTime = System.currentTimeMillis();
    driver.navigate().to("https://www.swtestacademy.com");

    long endTime = System.currentTimeMillis();

    System.out.println("Load time is " + (endTime - startTime));
  }

  @Test
  public void testGeoLocation(ITestContext context) {
    ChromeOptions options = new ChromeOptions();
    WebDriver driver = new ChromeDriver(options);
    setDriver(context, driver);
    driver.navigate().to("https://the-internet.herokuapp.com/");
    driver = new Augmenter().augment(driver);
    DevTools devTools = ((HasDevTools) driver).getDevTools();
    devTools.createSession();
    devTools.send(Emulation.setGeolocationOverride(Optional.of(52.5043),
        Optional.of(13.4501),
        Optional.of(1)));
    driver.get("https://my-location.org/");
    System.out.println();
  }
}
