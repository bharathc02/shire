package com.shire.selenium.page.impl;

import com.shire.selenium.page.core.DefaultPageComparators;
import com.shire.selenium.page.core.DefaultPages;
import com.shire.selenium.page.core.IPage;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public enum Workflow1 implements IPage {

  LOGIN() {
    @Override
    public List<IPage> previousPages() {
      return Collections.singletonList(DefaultPages.NONE);
    }

    @Override
    public List<IPage> nextPages() {
      return Collections.singletonList(DASHBOARD);
    }

    @Override
    public boolean hasLoaded() {
      return false;
    }

    @Override
    public void process() {

    }
  },
  DASHBOARD() {
    @Override
    public List<IPage> previousPages() {
      return Collections.singletonList(LOGIN);
    }

    @Override
    public List<IPage> nextPages() {
      return Collections.singletonList(DefaultPages.NONE);
    }

    @Override
    public boolean hasLoaded() {
      return false;
    }

    @Override
    public void process() {

    }
  },
  DEPOSIT() {
    @Override
    public List<IPage> previousPages() {
      return Arrays.asList(LOGIN, DASHBOARD);
    }

    @Override
    public Comparator<IPage> comparator() {
      return DefaultPageComparators.pageName();
    }

    @Override
    public List<IPage> nextPages() {
      return Collections.singletonList(DefaultPages.NONE);
    }

    @Override
    public boolean hasLoaded() {
      return false;
    }

    @Override
    public void process() {

    }
  },
  DEPOSIT_DETAILS() {
    @Override
    public List<IPage> previousPages() {
      return Collections.singletonList(DEPOSIT);
    }

    @Override
    public List<IPage> nextPages() {
      return Collections.singletonList(DefaultPages.NONE);
    }

    @Override
    public boolean hasLoaded() {
      return false;
    }

    @Override
    public void process() {

    }
  };

  public static void setWebDriverSupplier(Supplier<WebDriver> webDriverSupplier) {

  }

  public WebDriver getDriver() {
    Supplier<WebDriver> webDriverSupplier = ChromeDriver::new;
    return webDriverSupplier.get();
  }

  @Override
  public String pageName() {
    return name();
  }
}
