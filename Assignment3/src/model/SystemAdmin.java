
package model;
public class SystemAdmin extends User {
    private static int nextId = 1;
    private final int id;
    public SystemAdmin(String username, String password) {
        super(username, password, Role.SYSTEM_ADMIN);
        this.id = nextId++;
    }
    public int getId() { return id; }
}
