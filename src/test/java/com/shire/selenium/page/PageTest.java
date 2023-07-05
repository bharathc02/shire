package com.shire.selenium.page;

import com.shire.selenium.page.core.IPage;
import com.shire.selenium.page.impl.Workflow1;
import java.util.List;
import org.testng.annotations.Test;

public class PageTest {

  @Test
  public void testPages() {
//    WebDriverPool.get().store("", null);
//    WebDriverPool.get().fetch("");
//    WebDriverPool.getDriverPool().
//    Workflow1.setWebDriverSupplier(ChromeDriver::new);
    IPage current = Workflow1.DEPOSIT_DETAILS;
    List<IPage> pages = current.preparePageChain();
    pages.forEach(System.out::println);
    System.out.println(current);
  }
}
