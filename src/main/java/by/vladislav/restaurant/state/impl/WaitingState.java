package by.vladislav.restaurant.state.impl;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.vladislav.restaurant.entity.Customer;
import by.vladislav.restaurant.entity.Restaurant;
import by.vladislav.restaurant.state.CustomerState;

public class WaitingState implements CustomerState {
  private static final Logger logger = LoggerFactory.getLogger(WaitingState.class);

  @Override
  public void handle(Customer customer) {
    logger.info("{} enter restaurant.", customer.getCustomerName());
    try {
      boolean acquired = Restaurant.getInstance().getTables().tryAcquire(5, TimeUnit.SECONDS);
      if (acquired) {
        logger.info("{} sat down at the table.", customer.getCustomerName());
        customer.setState(new EatingState());
      } else {
        logger.warn("{} left hungry.", customer.getCustomerName());
        customer.setState(new LeftState());
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
