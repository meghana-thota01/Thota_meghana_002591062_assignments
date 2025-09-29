/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author meghana
 */
public class Customer {
    // Attributes
    private String customerId;
    private String firstName;
    private String lastName;
    private String contact;

    // Relationship: A Customer can have multiple Orders (Order History)
    private List<Order> orderHistory;

    /**
     * Constructor for the Customer object.
     * @param customerId The unique ID for the customer.
     * @param firstName The first name of the customer.
     * @param lastName The last name of the customer.
     * @param contact The contact number of the customer.
     */
    public Customer(String customerId, String firstName, String lastName, String contact) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contact = contact;
        this.orderHistory = new ArrayList<>();
    }

    // Getters
    public String getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getContact() {
        return contact;
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    // Setters
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void addOrderToHistory(Order order) {
        this.orderHistory.add(order);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (#" + customerId + ")";
    }
}
