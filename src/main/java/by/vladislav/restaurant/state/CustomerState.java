package by.vladislav.restaurant.state;

import by.vladislav.restaurant.entity.Customer;

public interface CustomerState {
  void handle(Customer customer);
}
