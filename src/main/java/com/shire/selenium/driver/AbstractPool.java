package com.shire.selenium.driver;

import java.util.HashMap;
import java.util.Map;

abstract class AbstractPool<T> {

  protected ThreadLocal<Map<String, T>> tlMap = new ThreadLocal<>();

  protected AbstractPool() {
    Thread thread = new Thread() {
      @Override
      public void run() {
        super.run();
        AbstractPool.this.dismissAll();
      }
    };
    Runtime.getRuntime().addShutdownHook(thread);
  }

  protected abstract void gracefullyQuit(T value);

  public synchronized void store(final String key, T value) {
    if (isEmpty()) {
      tlMap.set(new HashMap<>());
    }
    Map<String, T> valueMap = tlMap.get();
    if (valueMap.containsKey(key)) {
      throw new RuntimeException(
          String.format("Key (%s) is already present in Pool", key));
    }
    valueMap.put(key, value);
  }

  public synchronized T fetch(final String key) {
    Map<String, T> valueMap = validateKeyPresent(key);
    return valueMap.get(key);
  }

  public synchronized void dismiss(String key) {
    Map<String, T> valueMap = validateKeyPresent(key);
    valueMap.entrySet().removeIf(entry -> {
      if (entry.getKey().equals(key)) {
        gracefullyQuit(entry.getValue());
        return true;
      }
      return false;
    });
  }

  public synchronized void dismissAll() {
    if (isEmpty()) {
      return;
    }
    Map<String, T> valueMap = tlMap.get();
    valueMap.entrySet().removeIf(entry -> {
      gracefullyQuit(entry.getValue());
      return true;
    });
  }

  private synchronized Map<String, T> validateKeyPresent(String key) {
    if (key == null || key.isEmpty()) {
      throw new RuntimeException("Empty Key");
    }
    if (isEmpty()) {
      throw new RuntimeException("Empty Pool");
    }
    Map<String, T> valueMap = tlMap.get();
    if (!valueMap.containsKey(key)) {
      throw new RuntimeException(
          String.format("Key (%s) is not present in Pool", key));
    }
    return valueMap;
  }

  private synchronized boolean isEmpty() {
    Map<String, T> valueMap = tlMap.get();
    return valueMap == null || valueMap.isEmpty();
  }
}
