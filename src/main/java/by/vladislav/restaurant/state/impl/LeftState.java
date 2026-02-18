package by.vladislav.restaurant.state.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.vladislav.restaurant.entity.Customer;
import by.vladislav.restaurant.state.CustomerState;

public class LeftState implements CustomerState{
  private static final Logger logger = LoggerFactory.getLogger(LeftState.class);

    @Override
    public void handle(Customer customer) {
        logger.info("{} left.", customer.getCustomerName());
    }
}
