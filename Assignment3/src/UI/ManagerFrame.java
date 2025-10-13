
package UI;

import edu.neu.lms.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;

public class ManagerFrame extends JFrame {
    private final BranchManager manager;
    private final Directories d = EcoSystem.getInstance().getDirectories();

    private final DefaultTableModel booksModel = new DefaultTableModel(new Object[]{"Serial","Name","Author","Pages","Lang","Registered","Rented"},0);
   private final JTable tblBooks = new JTable(booksModel);
    private Library myLib;

    public ManagerFrame(BranchManager manager) {
        this.manager = manager;
        setTitle("Manager Workspace - " + manager);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        myLib = d.getBranches().stream().map(Branch::getLibrary).filter(l -> l.getManager()==manager).findFirst().orElse(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Books", booksPanel());
        tabs.add("Authors", authorsPanel());
        tabs.add("Rental Records", rentalPanel());
        setContentPane(tabs);
        refreshBooks();
    }

    private JPanel booksPanel() {
        JPanel p = new JPanel(new BorderLayout());
        JPanel form = new JPanel();
        JTextField txtName = new JTextField(12);
        JTextField txtPages = new JTextField(5);
        JTextField txtLang = new JTextField(8);
        JComboBox<Author> cbAuthor = new JComboBox<>();
        JButton btnAdd = new JButton("Add");

        form.add(new JLabel("Name:"));
        form.add(txtName);
        form.add(new JLabel("Pages:"));
        form.add(txtPages);
        form.add(new JLabel("Lang:"));
        form.add(txtLang);
        form.add(new JLabel("Author:"));
        form.add(cbAuthor);
        form.add(btnAdd);

        btnAdd.addActionListener(ev -> {
            if (myLib == null) return;
            String nm = txtName.getText().trim();
            int pages = 0;
            try { pages = Integer.parseInt(txtPages.getText().trim()); } catch(Exception ignored){}
            String lang = txtLang.getText().trim();
            Author a = (Author) cbAuthor.getSelectedItem();
            if (nm.isEmpty() || a==null) return;
            myLib.getBooks().add(new Book(nm, LocalDate.now(), pages, lang, a));
            refreshBooks();
            txtName.setText(""); txtPages.setText(""); txtLang.setText("");
        });

        p.add(form, BorderLayout.NORTH);
        p.add(new JScrollPane(tblBooks), BorderLayout.CENTER);

        p.addHierarchyListener(ev -> {
            cbAuthor.removeAllItems();
            for (Author a: d.getAuthors()) cbAuthor.addItem(a);
        });

        return p;
    }

    private JPanel authorsPanel() {
        JPanel p = new JPanel(new BorderLayout());
        DefaultListModel<Author> model = new DefaultListModel<>();
        JList<Author> list = new JList<>(model);
        JTextField txtName = new JTextField(15);
        JButton btnAdd = new JButton("Add Author");
        btnAdd.addActionListener(ev -> {
            String n = txtName.getText().trim();
            if (!n.isEmpty()) {
                d.getAuthors().add(new Author(n));
                txtName.setText("");
                reloadAuthors(model);
            }
        });
        JPanel top = new JPanel();
        top.add(new JLabel("Name:")); top.add(txtName); top.add(btnAdd);
        p.add(top, BorderLayout.NORTH);
        p.add(new JScrollPane(list), BorderLayout.CENTER);
        p.addHierarchyListener(ev -> reloadAuthors(model));
        return p;
    }

    private void reloadAuthors(DefaultListModel<Author> model) {
        model.clear();
        for (Author a: d.getAuthors()) model.addElement(a);
    }

    private JPanel rentalPanel() {
        JPanel p = new JPanel(new BorderLayout());
        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID","Book","Customer","Rented At","Returned At","Status"},0);
        JTable tbl = new JTable(model);
        p.add(new JScrollPane(tbl), BorderLayout.CENTER);
        p.addHierarchyListener(ev -> {
            model.setRowCount(0);
            if (myLib != null) {
                for (RentalRecord r: myLib.getRentalRecords()) {
                    model.addRow(new Object[]{r.getId(), r.getBook().getName(), r.getCustomer().toString(), r.getRentedAt(), r.getReturnedAt(), r.getStatus()});
                }
            }
        });
        return p;
    }

    private void refreshBooks() {
        booksModel.setRowCount(0);
        if (myLib == null) return;
        for (Book b: myLib.getBooks()) {
            booksModel.addRow(new Object[]{b.getSerialNumber(), b.getName(), b.getAuthor().getName(), b.getPages(), b.getLanguage(), b.getRegisteredDate(), b.isRented()});
        }
    }
}
