
package UI;

import edu.neu.lms.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerFrame extends JFrame {
    private final Customer customer;
    private final Directories d = EcoSystem.getInstance().getDirectories();
    private final DefaultTableModel catalogModel = new DefaultTableModel(new Object[]{"Branch","Serial","Name","Author","Available","Action"},0) {
        @Override
        public boolean isCellEditable(int row, int column) { return column == 5; }
    };
    private final JTable tblCatalog = new JTable(catalogModel);

    public CustomerFrame(Customer customer) {
        this.customer = customer;
        setTitle("Customer Workspace - " + customer);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Browse & Rent", browsePanel());
        tabs.add("Return", returnPanel());
        tabs.add("Rental History", historyPanel());
        setContentPane(tabs);
        refreshCatalog();
    }

    private JPanel browsePanel() {
        JPanel p = new JPanel(new BorderLayout());
        tblCatalog.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        tblCatalog.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox(), (branchId, serial) -> {
            Branch b = d.getBranches().stream().filter(br -> br.getId()==branchId).findFirst().orElse(null);
            if (b == null) return;
            Book book = b.getLibrary().getBooks().stream().filter(k -> k.getSerialNumber()==serial).findFirst().orElse(null);
            if (book == null || book.isRented()) return;
            book.setRented(true);
            RentalRecord rr = new RentalRecord(book, customer, b.getLibrary());
            b.getLibrary().getRentalRecords().add(rr);
            refreshCatalog();
        }));
        p.add(new JScrollPane(tblCatalog), BorderLayout.CENTER);
        return p;
    }

    private JPanel returnPanel() {
        JPanel p = new JPanel(new BorderLayout());
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Serial","Name","Branch","Action"},0) {
            @Override public boolean isCellEditable(int r, int c){ return c==3; }
        };
        JTable tbl = new JTable(model);
        tbl.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
        tbl.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox(), (branchId, serial) -> {
            Branch b = d.getBranches().stream().filter(br -> br.getId()==branchId).findFirst().orElse(null);
            if (b == null) return;
            Book book = b.getLibrary().getBooks().stream().filter(k -> k.getSerialNumber()==serial).findFirst().orElse(null);
            if (book == null || !book.isRented()) return;
            book.setRented(false);
            // mark latest rental for this book & customer as returned
            List<RentalRecord> list = b.getLibrary().getRentalRecords().stream()
                .filter(r -> r.getBook()==book && r.getCustomer()==customer && r.getStatus()==RentalStatus.RENTED)
                .collect(Collectors.toList());
            if (!list.isEmpty()) list.get(list.size()-1).markReturned();
            refreshCatalog();
            reloadReturns(model);
        }));
        p.add(new JScrollPane(tbl), BorderLayout.CENTER);
        p.addHierarchyListener(ev -> reloadReturns(model));
        return p;
    }

    private JPanel historyPanel() {
        JPanel p = new JPanel(new BorderLayout());
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Book","Branch","Rented At","Returned At","Status"},0);
        JTable tbl = new JTable(model);
        p.add(new JScrollPane(tbl), BorderLayout.CENTER);
        p.addHierarchyListener(ev -> {
            model.setRowCount(0);
            for (Branch br: d.getBranches()) {
                for (RentalRecord r: br.getLibrary().getRentalRecords()) {
                    if (r.getCustomer()==customer) {
                        model.addRow(new Object[]{r.getBook().getName(), br.getName(), r.getRentedAt(), r.getReturnedAt(), r.getStatus()});
                    }
                }
            }
        });
        return p;
    }

    private void refreshCatalog() {
        catalogModel.setRowCount(0);
        for (Branch br: d.getBranches()) {
            for (Book b: br.getLibrary().getBooks()) {
                boolean available = !b.isRented();
                catalogModel.addRow(new Object[]{br.getName()+" (#"+br.getId()+")", b.getSerialNumber(), b.getName(), b.getAuthor().getName(), available, available? "Rent" : "Unavailable"});
            }
        }
    }

    private void reloadReturns(DefaultTableModel model) {
        model.setRowCount(0);
        for (Branch br: d.getBranches()) {
            for (Book b: br.getLibrary().getBooks()) {
                if (b.isRented()) {
                    // check if rented by this customer
                    boolean mine = br.getLibrary().getRentalRecords().stream()
                            .anyMatch(r -> r.getBook()==b && r.getCustomer()==customer && r.getStatus()==RentalStatus.RENTED);
                    if (mine) {
                        model.addRow(new Object[]{b.getSerialNumber(), b.getName(), br.getName()+" (#"+br.getId()+")", "Return"});
                    }
                }
            }
        }
    }
}
