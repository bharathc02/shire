package com.shire.selenium;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.openqa.selenium.devtools.events.CdpEventTypes.domMutation;
import static org.openqa.selenium.remote.http.Contents.utf8String;

import com.google.common.net.MediaType;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.devtools.events.DomMutationEvent;
import org.openqa.selenium.devtools.v104.performance.Performance;
import org.openqa.selenium.devtools.v104.performance.model.Metric;
import org.openqa.selenium.logging.EventType;
import org.openqa.selenium.logging.HasLogEvents;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Routable;
import org.openqa.selenium.remote.http.Route;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class BiDiAPITest extends DriverBase {

  //  @Test
  public void testMutationObservation(ITestContext context) throws InterruptedException {
    ChromeOptions options = new ChromeOptions();
    WebDriver driver = createLocalChromeDriver(options);
    setDriver(context, driver);

    AtomicReference<DomMutationEvent> seen = new AtomicReference<>();
    CountDownLatch latch = new CountDownLatch(1);
    EventType<Void> domMutation = domMutation(mutation -> {
      String id = mutation.getElement().getAttribute("id");
      if ("spchl".equals(id)) {
        seen.set(mutation);
        latch.countDown();
      }
    });
    ((HasLogEvents) driver).onLogEvent(domMutation);

    driver.get("https://www.google.com");
    WebElement span = driver.findElement(By.cssSelector("span"));

    ((JavascriptExecutor) driver)
        .executeScript("arguments[0].setAttribute('cheese', 'gouda');", span);
    Assertions.assertThat(latch.await(10, SECONDS)).isTrue();
    Assertions.assertThat(seen.get().getAttributeName()).isEqualTo("cheese");
    Assertions.assertThat(seen.get().getCurrentValue()).isEqualTo("gouda");
    driver.quit();
  }

//  @Test
  public void testNetworkInterception(ITestContext context) {
    ChromeOptions options = new ChromeOptions();
    WebDriver driver = createLocalChromeDriver(options);
    setDriver(context, driver);
    Routable routable = Route.matching(req -> req.getUri().contains("home.jsp"))
        .to(() -> req -> new HttpResponse()
            .setStatus(200)
            .addHeader("Content-Type", MediaType.HTML_UTF_8.toString())
            .setContent(utf8String("Creamy, delicious cheese!")));

    NetworkInterceptor interceptor = new NetworkInterceptor(
        driver, routable);
    driver.get("https://example-sausages-site.com");
    String source = driver.getPageSource();
    Assertions.assertThat(source).contains("delicious cheese!");
  }

  @Test
  public void testPerformanceMetrics(ITestContext context) {
    ChromeOptions options = new ChromeOptions();
    WebDriver driver = createLocalChromeDriver(options);
    setDriver(context, driver);
    driver = new Augmenter().augment(driver);
    DevTools devTools = ((HasDevTools) driver).getDevTools();
    devTools.createSession();
    devTools.send(Performance.enable(Optional.empty()));
    List<Metric> metricList = devTools.send(Performance.getMetrics());

    driver.get("https://google.com");
    driver.quit();

    for (Metric m : metricList) {
      System.out.println(m.getName() + " = " + m.getValue());
    }
  }
}
