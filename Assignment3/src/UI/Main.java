
package UI;

import javax.swing.SwingUtilities;
import edu.neu.lms.config.ConfigureTheBusiness;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ConfigureTheBusiness.init();
            new LoginFrame().setVisible(true);
        });
    }
}
