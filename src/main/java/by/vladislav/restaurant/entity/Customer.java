package by.vladislav.restaurant.entity;

import java.util.concurrent.Callable;

import by.vladislav.restaurant.state.CustomerState;
import by.vladislav.restaurant.state.impl.LeftState;
import by.vladislav.restaurant.state.impl.WaitingState;

public class Customer implements Callable<String>{
  private final String name;
    private CustomerState state;

    public Customer(int id) {
        this.name = "Visitor #" + id;
        this.state = new WaitingState();
    }

    public void setState(CustomerState state) {
        this.state = state;
    }
    
    public String getCustomerName() {
        return name;
    }

    @Override
    public String call() {
        while (!(state instanceof LeftState)) {
            state.handle(this);
        }

        state.handle(this);
        
        return name + " Maitenance completed.";
    }
}
