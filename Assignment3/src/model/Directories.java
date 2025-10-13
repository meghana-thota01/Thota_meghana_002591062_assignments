
package model;

import java.util.*;

public class Directories {
    private final Map<String, User> usersByUsername = new HashMap<>();
    private final List<Branch> branches = new ArrayList<>();
    private final List<Author> authors = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();
    private final List<BranchManager> managers = new ArrayList<>();

    public void addUser(User u) { usersByUsername.put(u.getUsername(), u); }
    public User findUser(String username, String password) {
        User u = usersByUsername.get(username);
        if (u != null && u.getPassword().equals(password)) return u;
        return null;
    }

    public List<Branch> getBranches() { return branches; }
    public List<Author> getAuthors() { return authors; }
    public List<Customer> getCustomers() { return customers; }
    public List<BranchManager> getManagers() { return managers; }

    public void deleteBranch(Branch b) { branches.remove(b); }
}
