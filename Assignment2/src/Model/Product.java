/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author meghana
 */
public class Product {
    // Attributes
    private String productId;
    private String productName;
    private String category; // e.g., Coffee, Tea, Pastry
    private double price;
    private int quantity; // Number/Stock available
    private int preparationTime; // in minutes

    /**
     * Constructor for the Product object.
     * @param productId The unique ID for the product.
     * @param productName The name of the product.
     * @param category The product category.
     * @param price The price of one unit.
     * @param quantity The current stock quantity.
     * @param preparationTime The time in minutes required to prepare the product.
     */
    public Product(String productId, String productName, String category, double price, int quantity, int preparationTime) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.preparationTime = preparationTime;
    }

    // Getters
    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    // Setters
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    /**
     * Returns the product name and ID for display in a JComboBox or list.
     * @return Formatted product string.
     */
    @Override
    public String toString() {
        return productName + " (#" + productId + ")";
    }
}
