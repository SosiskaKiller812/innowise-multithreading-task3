package by.vladislav.restaurant.state.impl;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.vladislav.restaurant.entity.Customer;
import by.vladislav.restaurant.entity.Restaurant;
import by.vladislav.restaurant.state.CustomerState;

public class EatingState implements CustomerState{
  private static final Logger logger = LoggerFactory.getLogger(EatingState.class);

  @Override
  public void handle(Customer customer) {
    logger.info("{} makes an order.", customer.getCustomerName());

    Restaurant.getInstance().getKitchen().getDish(customer.getCustomerName());

    logger.info("{} eating.", customer.getCustomerName());
    try {
      TimeUnit.MILLISECONDS.sleep(1000 + (long) (Math.random() * 1000)); // eating
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    Restaurant.getInstance().getTables().release();
    logger.info("{} eats and leaves.", customer.getCustomerName());

    customer.setState(new LeftState());
  }
}
