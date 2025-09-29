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
public class OrderDirectory {
    private List<Order> orderList;

    public OrderDirectory() {
        this.orderList = new ArrayList<>();
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    // CRUD Operations

    /**
     * Adds a new order to the directory.
     * IMPORTANT: This method also ensures the order is added to the customer's order history.
     * @param order The Order object to add.
     * @return The added Order object.
     */
    public Order addOrder(Order order) {
        orderList.add(order);
        // Link the order to the customer's history
        order.getCustomer().addOrderToHistory(order);
        return order;
    }

    /**
     * Finds an order by its unique ID.
     * @param id The order ID to search for.
     * @return The Order object if found, otherwise null.
     */
    public Order findOrderById(String id) {
        for (Order order : orderList) {
            if (order.getOrderId().equals(id)) {
                return order;
            }
        }
        return null;
    }

    /**
     * Deletes an order from the directory.
     * Note: Deleting an order from the directory does NOT automatically remove it
     * from the Customer's order history list, which would need to be handled
     * separately if full data consistency is required. For this assignment,
     * we focus on the main directory list.
     * @param order The Order object to remove.
     */
    public void deleteOrder(Order order) {
        // Optional: Remove from customer history for full consistency
        // order.getCustomer().getOrderHistory().remove(order);
        orderList.remove(order);
    }

    /**
     * Checks if an order ID already exists.
     * @param id The order ID to check.
     * @return true if the ID exists, false otherwise.
     */
    public boolean isOrderIdUnique(String id) {
        return findOrderById(id) == null;
    }
}
