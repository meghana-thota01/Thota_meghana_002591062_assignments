
package model;

public class Author {
    private static int nextId = 1;
    private final int id;
    private String name;

    public Author(String name) {
        this.id = nextId++;
        this.name = name;
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    @Override
    public String toString() { return name; }
}
