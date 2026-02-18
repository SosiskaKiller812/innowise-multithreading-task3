package by.vladislav.restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.*;

import by.vladislav.restaurant.entity.Customer;
import by.vladislav.restaurant.util.ConfigReader;

public class App {

  private static final Logger logger = LoggerFactory.getLogger(App.class);

  public static void main(String[] args) {
    logger.info("Restaurant is open...");

    Map<String, Integer> config = ConfigReader.readConfig();
    int customersCount = config.getOrDefault("customers", 5);

    ExecutorService executorService = Executors.newFixedThreadPool(customersCount);
    List<Future<String>> results = new ArrayList<>();

    for (int i = 1; i <= customersCount; i++) {
      Customer customer = new Customer(i);
      results.add(executorService.submit(customer));
    }

    executorService.shutdown();

    for (Future<String> result : results) {
      try {
        logger.info(result.get());
      } catch (Exception e) {
        logger.error("Ошибка выполнения потока", e);
      }
    }

    logger.info("Restaurant closed.");
  }
}
