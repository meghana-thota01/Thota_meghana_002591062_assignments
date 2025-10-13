
package UI;

import edu.neu.lms.model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {
    private final JTextField txtUser = new JTextField(15);
    private final JPasswordField txtPass = new JPasswordField(15);

    public LoginFrame() {
        setTitle("Library System - Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(4,4,4,4);
        gc.gridx = 0; gc.gridy = 0; gc.anchor = GridBagConstraints.LINE_END;
        form.add(new JLabel("Username:"), gc);
        gc.gridy++; form.add(new JLabel("Password:"), gc);

        gc.gridx = 1; gc.gridy = 0; gc.anchor = GridBagConstraints.LINE_START;
        form.add(txtUser, gc);
        gc.gridy++; form.add(txtPass, gc);

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(this::doLogin);

        JPanel root = new JPanel(new BorderLayout());
        root.add(form, BorderLayout.CENTER);
        root.add(btnLogin, BorderLayout.SOUTH);
        setContentPane(root);
    }

    private void doLogin(ActionEvent e) {
        String u = txtUser.getText().trim();
        String p = new String(txtPass.getPassword());

        User user = EcoSystem.getInstance().getDirectories().findUser(u, p);
        if (user == null) {
            JOptionPane.showMessageDialog(this, "Invalid credentials");
            return;
        }
        dispose();
        switch (user.getRole()) {
            case SYSTEM_ADMIN -> new SystemAdminFrame((SystemAdmin) user).setVisible(true);
            case BRANCH_MANAGER -> new ManagerFrame((BranchManager) user).setVisible(true);
            case CUSTOMER -> new CustomerFrame((Customer) user).setVisible(true);
        }
    }
}
