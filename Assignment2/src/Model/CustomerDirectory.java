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
public class CustomerDirectory {
    private List<Customer> customerList;

    public CustomerDirectory() {
        this.customerList = new ArrayList<>();
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    // CRUD Operations

    /**
     * Adds a new customer to the directory.
     * @param customer The Customer object to add.
     * @return The added Customer object.
     */
    public Customer addCustomer(Customer customer) {
        customerList.add(customer);
        return customer;
    }

    /**
     * Finds a customer by their unique ID.
     * @param id The customer ID to search for.
     * @return The Customer object if found, otherwise null.
     */
    public Customer findCustomerById(String id) {
        for (Customer customer : customerList) {
            if (customer.getCustomerId().equals(id)) {
                return customer;
            }
        }
        return null;
    }

    /**
     * Finds all customers whose first or last name matches the search query.
     * @param name The name (partial or full) to search for.
     * @return A list of matching Customer objects.
     */
    public List<Customer> findCustomerByName(String name) {
        List<Customer> foundCustomers = new ArrayList<>();
        String searchName = name.toLowerCase();

        for (Customer customer : customerList) {
            if (customer.getFirstName().toLowerCase().contains(searchName) ||
                customer.getLastName().toLowerCase().contains(searchName)) {
                foundCustomers.add(customer);
            }
        }
        return foundCustomers;
    }

    /**
     * Deletes a customer from the directory.
     * @param customer The Customer object to remove.
     */
    public void deleteCustomer(Customer customer) {
        customerList.remove(customer);
    }

    /**
     * Checks if a customer ID already exists.
     * @param id The customer ID to check.
     * @return true if the ID exists, false otherwise.
     */
    public boolean isCustomerIdUnique(String id) {
        return findCustomerById(id) == null;
    }
}
