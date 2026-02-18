package by.vladislav.restaurant.entity;

import java.util.Map;
import java.util.concurrent.Semaphore;

import by.vladislav.restaurant.util.ConfigReader;

public class Restaurant {
  private static final Restaurant INSTANCE = new Restaurant();
  private final Semaphore tables;
  private final Kitchen kitchen;

  private Restaurant() {
    Map<String, Integer> config = ConfigReader.readConfig();
    int tablesCount = config.getOrDefault("tables", 2);
    int dishesCount = config.getOrDefault("dishes", 10);

    this.tables = new Semaphore(tablesCount, true);
    this.kitchen = new Kitchen(dishesCount);
  }

  public static Restaurant getInstance() {
    return INSTANCE;
  }

  public Semaphore getTables() {
    return tables;
  }

  public Kitchen getKitchen() {
    return kitchen;
  }
}
