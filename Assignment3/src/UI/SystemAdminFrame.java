
package UI;

import edu.neu.lms.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SystemAdminFrame extends JFrame {
    private final SystemAdmin admin;
    private final Directories d = EcoSystem.getInstance().getDirectories();

    private final DefaultTableModel branchModel = new DefaultTableModel(new Object[]{"ID","Name","Manager","Building"}, 0);
    private final JTable tblBranches = new JTable(branchModel);

    public SystemAdminFrame(SystemAdmin admin) {
        this.admin = admin;
        setTitle("System Admin Workspace");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Branches", branchPanel());
        tabs.add("Users", usersPanel());
        setContentPane(tabs);
        refreshBranches();
    }

    private JPanel branchPanel() {
        JPanel p = new JPanel(new BorderLayout());
        JPanel top = new JPanel();
        JTextField txtName = new JTextField(12);
        JButton btnAdd = new JButton("Create Branch");
        JButton btnDelete = new JButton("Delete Selected");

        btnAdd.addActionListener(ev -> {
            String name = txtName.getText().trim();
            if (!name.isEmpty()) {
                d.getBranches().add(new Branch(name));
                refreshBranches();
                txtName.setText("");
            }
        });

        btnDelete.addActionListener(ev -> {
            int row = tblBranches.getSelectedRow();
            if (row >= 0) {
                int id = (int) branchModel.getValueAt(row, 0);
                Branch toDelete = d.getBranches().stream().filter(b -> b.getId() == id).findFirst().orElse(null);
                if (toDelete != null) {
                    d.deleteBranch(toDelete);
                    refreshBranches();
                }
            }
        });

        top.add(new JLabel("Name:"));
        top.add(txtName);
        top.add(btnAdd);
        top.add(btnDelete);

        p.add(top, BorderLayout.NORTH);
        p.add(new JScrollPane(tblBranches), BorderLayout.CENTER);

        // Manager assignment panel
        JPanel bottom = new JPanel();
        JComboBox<Branch> cbBranch = new JComboBox<>();
        JComboBox<BranchManager> cbManager = new JComboBox<>();
        JButton btnAssign = new JButton("Assign Manager");
        btnAssign.addActionListener(e -> {
            Branch b = (Branch) cbBranch.getSelectedItem();
            BranchManager m = (BranchManager) cbManager.getSelectedItem();
            if (b != null && m != null) {
                b.getLibrary().setManager(m);
                refreshBranches();
            }
        });
        bottom.add(new JLabel("Branch:"));
        bottom.add(cbBranch);
        bottom.add(new JLabel("Manager:"));
        bottom.add(cbManager);
        bottom.add(btnAssign);

        p.add(bottom, BorderLayout.SOUTH);

        // populate combos on show
        p.addHierarchyListener(ev -> {
            cbBranch.removeAllItems();
            for (Branch b: d.getBranches()) cbBranch.addItem(b);
            cbManager.removeAllItems();
            for (BranchManager m: d.getManagers()) cbManager.addItem(m);
        });

        return p;
    }

    private JPanel usersPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(4,4,4,4);
        JTextField txtU = new JTextField(10);
        JTextField txtP = new JTextField(10);
        JComboBox<String> cbType = new JComboBox<>(new String[]{"Customer","Branch Manager"});
        JTextField txtExp = new JTextField(5);
        JButton btnAdd = new JButton("Create");

        btnAdd.addActionListener((ActionEvent e) -> {
            String u = txtU.getText().trim();
            String pass = txtP.getText().trim();
            if (cbType.getSelectedIndex() == 0) {
                Customer c = new Customer(u, pass);
                d.getCustomers().add(c); d.addUser(c);
                JOptionPane.showMessageDialog(this, "Customer created: " + c);
            } else {
                int exp = 0;
                try { exp = Integer.parseInt(txtExp.getText().trim()); } catch (Exception ignored){}
                BranchManager m = new BranchManager(u, pass, exp);
                d.getManagers().add(m); d.addUser(m);
                JOptionPane.showMessageDialog(this, "Manager created: " + m);
            }
            txtU.setText(""); txtP.setText(""); txtExp.setText("");
        });

        gc.gridx=0;gc.gridy=0; p.add(new JLabel("Username"), gc);
        gc.gridx=1; p.add(txtU, gc);
        gc.gridx=0;gc.gridy++; p.add(new JLabel("Password"), gc);
        gc.gridx=1; p.add(txtP, gc);
        gc.gridx=0;gc.gridy++; p.add(new JLabel("Type"), gc);
        gc.gridx=1; p.add(cbType, gc);
        gc.gridx=0;gc.gridy++; p.add(new JLabel("Experience (Mgr only)"), gc);
        gc.gridx=1; p.add(txtExp, gc);
        gc.gridx=1;gc.gridy++; p.add(btnAdd, gc);
        return p;
    }

    private void refreshBranches() {
        branchModel.setRowCount(0);
        for (Branch b: d.getBranches()) {
            String mgr = b.getLibrary().getManager()==null? "-" : b.getLibrary().getManager().toString();
            branchModel.addRow(new Object[]{b.getId(), b.getName(), mgr, b.getLibrary().getBuildingNo()});
        }
    }
}
