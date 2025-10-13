
package model;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private static int nextBuilding = 1;
    private final int buildingNo;
    private BranchManager manager;
    private final List<Book> books = new ArrayList<>();
    private final List<RentalRecord> rentalRecords = new ArrayList<>();

    public Library() {
        this.buildingNo = nextBuilding++;
    }

    public int getBuildingNo() { return buildingNo; }
    public BranchManager getManager() { return manager; }
    public void setManager(BranchManager manager) { this.manager = manager; }
    public List<Book> getBooks() { return books; }
    public List<RentalRecord> getRentalRecords() { return rentalRecords; }
}
