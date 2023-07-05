package com.shire.selenium.page.core;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public interface IPage {

  String pageName();

  List<IPage> previousPages();

  default IPage previousPage() {
    Function<List<IPage>, IPage> findFirst = (e ->
        e.stream()
            .min(comparator())
            .orElseThrow(
                () -> new RuntimeException("Previous page is not defined for " + pageName())));
    List<IPage> pages = previousPages();
    return findFirst.apply(pages);
  }

  List<IPage> nextPages();

  default IPage nextPage() {
    Function<List<IPage>, IPage> findFirst = (e ->
        e.stream()
            .min(comparator())
            .orElseThrow(
                () -> new RuntimeException("Next page is not defined for " + pageName())));
    List<IPage> pages = nextPages();
    return findFirst.apply(pages);
  }

  boolean hasLoaded();

  void process();

  default Comparator<IPage> comparator() {
    return DefaultPageComparators.natural();
  }

  default List<IPage> preparePageChain() {
    LinkedList<IPage> pageList = new LinkedList<>();
    IPage current = this.previousPage();
    while (current != null && current != DefaultPages.NONE) {
      pageList.add(current);
      current = current.previousPage();
    }
    Collections.reverse(pageList);
    return pageList;
  }

  default void processPageChain() {
    List<IPage> pageList = preparePageChain();
    for (IPage page : pageList) {
      if (page.hasLoaded()) {
        page.process();
      } else {
        throw new RuntimeException(String.format("Page (%s) has not loaded", page.pageName()));
      }
    }
  }
}
