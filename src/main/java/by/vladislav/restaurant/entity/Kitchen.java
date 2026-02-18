package by.vladislav.restaurant.entity;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Kitchen {
  private static final Logger logger = LoggerFactory.getLogger(Kitchen.class);
  private int dishesCount;
  private final Lock lock = new ReentrantLock();
  private final Condition dishesAvailable = lock.newCondition();

  public Kitchen(int initialDishes) {
    this.dishesCount = initialDishes;
  }

  public void getDish(String customerName) {
    lock.lock();
    try {
      while (dishesCount <= 0) {
        logger.warn("Don't have food...", customerName);
        cookMoreDishes();
        dishesAvailable.await(1, TimeUnit.SECONDS);
      }
      dishesCount--;
      logger.info("food given to {}. remained dishes: {}", customerName, dishesCount);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    } finally {
      lock.unlock();
    }
  }

  private void cookMoreDishes() {
    try {
      TimeUnit.MILLISECONDS.sleep(500);
      dishesCount += 5;
      logger.info("New food are ready!: {}", dishesCount);
      dishesAvailable.signalAll();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
