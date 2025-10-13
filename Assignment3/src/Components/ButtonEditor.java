
package Components;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.function.BiConsumer;

public class ButtonEditor extends DefaultCellEditor implements TableCellEditor {
    protected JButton button;
    private String label;
    private boolean clicked;
    private int branchId;
    private int serial;
    private final BiConsumer<Integer, Integer> onClick;

    public ButtonEditor(JCheckBox checkBox, BiConsumer<Integer, Integer> onClick) {
        super(checkBox);
        this.onClick = onClick;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener((ActionEvent e) -> fireEditingStopped());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        // parse branch id from first column when present; fallback to 0
        try {
            Object branchCol = table.getValueAt(row, 0);
            String s = String.valueOf(branchCol);
            int idx = s.lastIndexOf("#");
            int idx2 = s.lastIndexOf(")");
            if (idx >= 0 && idx2 > idx) branchId = Integer.parseInt(s.substring(idx+1, idx2));
        } catch (Exception ex) { branchId = 0; }
        try {
            serial = Integer.parseInt(String.valueOf(table.getValueAt(row, 1)));
        } catch (Exception ex) { serial = 0; }
        clicked = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (clicked && "Rent".equals(label) || "Return".equals(label)) {
            onClick.accept(branchId, serial);
        }
        clicked = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        clicked = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
