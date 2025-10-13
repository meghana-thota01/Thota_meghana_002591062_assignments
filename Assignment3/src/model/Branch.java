
package model;

public class Branch {
    private static int nextId = 1;
    private final int id;
    private String name;
    private final Library library;

    public Branch(String name) {
        this.id = nextId++;
        this.name = name;
        this.library = new Library();
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Library getLibrary() { return library; }

    @Override
    public String toString() { return name + " (Branch#" + id + ")"; }
}
