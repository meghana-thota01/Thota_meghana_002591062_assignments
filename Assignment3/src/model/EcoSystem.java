
package model;

public class EcoSystem {
    private static EcoSystem instance;
    private final Directories directories = new Directories();

    private EcoSystem(){}

    public static EcoSystem getInstance() {
        if (instance == null) instance = new EcoSystem();
        return instance;
    }

    public Directories getDirectories() { return directories; }
}
