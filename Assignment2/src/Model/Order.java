/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
/**
 *
 * @author meghana
 */
public class Order {
    // Static lists for JComboBoxes, defined here for easy access
    public static final String[] ORDER_TYPES = {"Dine-in", "Takeout", "Pickup"};
    public static final String[] PAYMENT_METHODS = {"Cash", "Card", "Mobile"};
    public static final String[] ORDER_STATUSES = {"Pending", "Preparing", "Ready", "Completed"};

    private String orderId;
    private LocalDateTime orderDate; // Use LocalDateTime for date/time (Primitive type)
    private String orderType;
    private String paymentMethod;
    private String orderStatus;
    private Product product;
    private int quantity; // int (Primitive type)
    private boolean isPaid; // boolean (Primitive type)
    private Customer customer;

    // Constructor
    public Order(String orderId, LocalDateTime orderDate, String orderType, String paymentMethod, String orderStatus, Product product, int quantity, boolean isPaid, Customer customer) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderType = orderType;
        this.paymentMethod = paymentMethod;
        this.orderStatus = orderStatus;
        this.product = product;
        this.quantity = quantity;
        this.isPaid = isPaid;
        this.customer = customer;
    }

    // --- Getters and Setters ---

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    // FIX: Ensure this method returns LocalDateTime as expected by the UI
    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Custom toString to display order details nicely in the combo box.
     */
    @Override
    public String toString() {
        // Example display: Order 101 (Nitish Ahuja)
        // Check if customer is null before accessing its properties (safety)
        String customerName = (customer != null) ? customer.getFirstName() + " " + customer.getLastName() : "Unknown Customer";
        return orderId + " (" + customerName + ")";
    }
}
