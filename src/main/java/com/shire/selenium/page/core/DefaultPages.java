package com.shire.selenium.page.core;

import java.util.List;

public enum DefaultPages implements IPage {

  NONE() {
    @Override
    public List<IPage> previousPages() {
      return null;
    }

    @Override
    public List<IPage> nextPages() {
      return null;
    }

    @Override
    public boolean hasLoaded() {
      return true;
    }

    @Override
    public void process() {

    }
  };

  @Override
  public String pageName() {
    return name();
  }
}
