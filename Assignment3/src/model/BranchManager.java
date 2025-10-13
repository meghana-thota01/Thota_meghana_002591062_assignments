
package model;

public class BranchManager extends User {
    private static int nextId = 1000;
    private final int employeeId;
    private int experienceYears;

    public BranchManager(String username, String password, int experienceYears) {
        super(username, password, Role.BRANCH_MANAGER);
        this.employeeId = nextId++;
        this.experienceYears = experienceYears;
    }

    public int getEmployeeId() { return employeeId; }
    public int getExperienceYears() { return experienceYears; }
    public void setExperienceYears(int years) { this.experienceYears = years; }

    @Override
    public String toString() { return username + " (Emp#" + employeeId + ")"; }
}
