package com.shire.selenium.page.core;

import java.util.Comparator;

public class DefaultPageComparators {

  public static Comparator<IPage> natural() {
    return (o1, o2) -> 0;
  }

  public static Comparator<IPage> pageName() {
    return Comparator.comparing(IPage::pageName);
  }

  public static Comparator<IPage> className() {
    return Comparator.comparing(o -> o.getClass().getName());
  }

}
