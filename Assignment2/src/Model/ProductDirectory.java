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
public class ProductDirectory {
    private List<Product> productList;

    public ProductDirectory() {
        this.productList = new ArrayList<>();
    }

    public List<Product> getProductList() {
        return productList;
    }

    // CRUD Operations

    /**
     * Adds a new product to the catalog.
     * @param product The Product object to add.
     * @return The added Product object.
     */
    public Product addProduct(Product product) {
        productList.add(product);
        return product;
    }

    /**
     * Finds a product by its unique ID.
     * @param id The product ID to search for.
     * @return The Product object if found, otherwise null.
     */
    public Product findProductById(String id) {
        for (Product product : productList) {
            if (product.getProductId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    /**
     * Deletes a product from the catalog.
     * @param product The Product object to remove.
     */
    public void deleteProduct(Product product) {
        productList.remove(product);
    }

    /**
     * Checks if a product ID already exists.
     * @param id The product ID to check.
     * @return true if the ID exists, false otherwise.
     */
    public boolean isProductIdUnique(String id) {
        return findProductById(id) == null;
    }
}
