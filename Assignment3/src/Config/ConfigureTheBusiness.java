
package Config;

import model.BranchManager;
import model.EcoSystem;
import model.Branch;
import model.Author;
import model.Customer;
import model.SystemAdmin;
import model.Book;
import model.Directories;
import java.time.LocalDate;

public class ConfigureTheBusiness {

    public static void init() {
        EcoSystem eco = EcoSystem.getInstance();
        Directories d = eco.getDirectories();

        // Users
        SystemAdmin admin = new SystemAdmin("admin", "admin");
        d.addUser(admin);

        // Branches & Managers
        Branch b1 = new Branch("Downtown");
        Branch b2 = new Branch("Uptown");
        d.getBranches().add(b1);
        d.getBranches().add(b2);

        BranchManager m1 = new BranchManager("manager1", "pass1", 5);
        BranchManager m2 = new BranchManager("manager2", "pass2", 3);
        d.getManagers().add(m1);
        d.getManagers().add(m2);
        d.addUser(m1);
        d.addUser(m2);

        b1.getLibrary().setManager(m1);
        b2.getLibrary().setManager(m2);

        // Customers
        for (int i = 1; i <= 5; i++) {
            Customer c = new Customer("cust"+i, "cust"+i);
            d.getCustomers().add(c);
            d.addUser(c);
        }

        // Authors
        Author a1 = new Author("Jane Austen");
        Author a2 = new Author("Mark Twain");
        Author a3 = new Author("George Orwell");
        d.getAuthors().add(a1); d.getAuthors().add(a2); d.getAuthors().add(a3);

        // Books
        b1.getLibrary().getBooks().add(new Book("Pride and Prejudice", LocalDate.now(), 432, "English", a1));
        b1.getLibrary().getBooks().add(new Book("Emma", LocalDate.now(), 400, "English", a1));
        b2.getLibrary().getBooks().add(new Book("Adventures of Huckleberry Finn", LocalDate.now(), 366, "English", a2));
        b2.getLibrary().getBooks().add(new Book("1984", LocalDate.now(), 328, "English", a3));
        b2.getLibrary().getBooks().add(new Book("Animal Farm", LocalDate.now(), 112, "English", a3));
    }
}
