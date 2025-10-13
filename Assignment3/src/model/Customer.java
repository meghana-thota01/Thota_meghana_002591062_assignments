
package model;

public class Customer extends User {
    private static int nextId = 5000;
    private final int customerId;

    public Customer(String username, String password) {
        super(username, password, Role.CUSTOMER);
        this.customerId = nextId++;
    }

    public int getCustomerId() { return customerId; }
    @Override
    public String toString() { return username + " (Cust#" + customerId + ")"; }
}
