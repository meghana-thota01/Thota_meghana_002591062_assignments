/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Model.Customer;
import Model.CustomerDirectory;
import Model.Order;
import Model.OrderDirectory;
import Model.Product;
import Model.ProductDirectory;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
/**
 *
 * @author meghana
 */
public class ManageOrdersJPanel extends javax.swing.JPanel {

    private OrderDirectory orderDirectory;
    private ProductDirectory productDirectory;
    private CustomerDirectory customerDirectory;
    // Date format for the Order Date field
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * Creates new form ManageOrdersJPanel
     */
    public ManageOrdersJPanel(OrderDirectory od, ProductDirectory pd, CustomerDirectory cd) {
        this.orderDirectory = od;
        this.productDirectory = pd;
        this.customerDirectory = cd;
        initComponents();
        populateComboBoxes();
        populateTable();
        // Set the Order Date field to the current time initially
        fieldOrderDate.setText(LocalDateTime.now().format(DATE_FORMAT));
    }

    private void populateComboBoxes() {
        // 1. Product Opted (Display Product Name and ID)
        DefaultComboBoxModel<Product> productModel = new DefaultComboBoxModel<>();
        for (Product p : productDirectory.getProductList()) {
            productModel.addElement(p); // Custom toString() in Product handles display
        }
        jComboBoxProductOpted.setModel(productModel);

        // 2. Customer (Display Customer Name and ID)
        DefaultComboBoxModel<Customer> customerModel = new DefaultComboBoxModel<>();
        for (Customer c : customerDirectory.getCustomerList()) {
            customerModel.addElement(c); // Custom toString() in Customer handles display
        }
        jComboBoxCustomer.setModel(customerModel);

        // 3. Order Type, Payment Method, and Status (Static lists from Model/Order.java)
        jComboBoxOrderType.setModel(new DefaultComboBoxModel<>(Order.ORDER_TYPES));
        jComboBoxPaymentMethod.setModel(new DefaultComboBoxModel<>(Order.PAYMENT_METHODS));
        jComboBoxOrderStatus.setModel(new DefaultComboBoxModel<>(Order.ORDER_STATUSES));
    }

    private void populateTable() {
        DefaultTableModel model = (DefaultTableModel) jTableOrders.getModel();
        model.setRowCount(0); // Clear existing rows

        for (Order order : orderDirectory.getOrderList()) {
            Object[] row = new Object[4]; // Title 1, 2, 3, 4 (Mapping to Order properties)
            // Column mapping: Order ID, Customer Name, Product Name, Status
            row[0] = order.getOrderId();
            row[1] = order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName();
            row[2] = order.getProduct().getProductName();
            row[3] = order.getOrderStatus();
            model.addRow(row);
        }
    }

    private void clearFields() {
        fieldOrderID.setText("");
        fieldOrderDate.setText(LocalDateTime.now().format(DATE_FORMAT)); // Reset date to current time

        if (jComboBoxOrderType.getItemCount() > 0) jComboBoxOrderType.setSelectedIndex(0);
        if (jComboBoxPaymentMethod.getItemCount() > 0) jComboBoxPaymentMethod.setSelectedIndex(0);
        if (jComboBoxOrderStatus.getItemCount() > 0) jComboBoxOrderStatus.setSelectedIndex(0);
        if (jComboBoxProductOpted.getItemCount() > 0) jComboBoxProductOpted.setSelectedIndex(0);
        if (jComboBoxCustomer.getItemCount() > 0) jComboBoxCustomer.setSelectedIndex(0);

        fieldOrderID.setEnabled(true);
        jTableOrders.clearSelection();
    }

    private Order validateAndCreateOrder() {
        // --- Get inputs ---
        String id = fieldOrderID.getText().trim();
        String dateStr = fieldOrderDate.getText().trim();
        String orderType = (String) jComboBoxOrderType.getSelectedItem();
        String paymentMethod = (String) jComboBoxPaymentMethod.getSelectedItem();
        String orderStatus = (String) jComboBoxOrderStatus.getSelectedItem();
        Product selectedProduct = (Product) jComboBoxProductOpted.getSelectedItem();
        Customer selectedCustomer = (Customer) jComboBoxCustomer.getSelectedItem();

        // --- 1. Mandatory Checks ---
        if (id.isEmpty() || dateStr.isEmpty() || orderType == null || paymentMethod == null || orderStatus == null || selectedProduct == null || selectedCustomer == null) {
            JOptionPane.showMessageDialog(this, "All fields must be selected/filled.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // --- 2. Date Validation ---
        LocalDateTime orderDate;
        try {
            orderDate = LocalDateTime.parse(dateStr, DATE_FORMAT); // LocalDate Time (primitive datatype)
        } catch (java.time.format.DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Order Date/Time format is invalid. Use YYYY-MM-DD HH:MM:SS format.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // NOTE: Order object structure currently doesn't include quantity or paid status.
        // Assuming quantity is 1 and paid is true for simplicity, as per basic requirement.
        int quantity = 1;
        boolean paid = true;

        return new Order(id, orderDate, orderType, paymentMethod, orderStatus, selectedProduct, quantity, paid, selectedCustomer);
    }

    private void loadSelectedOrder() {
        int selectedRow = jTableOrders.getSelectedRow();
        if (selectedRow >= 0) {
            DefaultTableModel model = (DefaultTableModel) jTableOrders.getModel();
            String orderId = model.getValueAt(selectedRow, 0).toString();

            Order order = orderDirectory.findOrderById(orderId);

            if (order != null) {
                fieldOrderID.setText(order.getOrderId());
                fieldOrderDate.setText(order.getOrderDate().format(DATE_FORMAT));

                jComboBoxOrderType.setSelectedItem(order.getOrderType());
                jComboBoxPaymentMethod.setSelectedItem(order.getPaymentMethod());
                jComboBoxOrderStatus.setSelectedItem(order.getOrderStatus());

                // Select Customer and Product by object reference (requires custom toString in model classes)
                jComboBoxCustomer.setSelectedItem(order.getCustomer());
                jComboBoxProductOpted.setSelectedItem(order.getProduct());

                // Disable ID editing when an order is selected
                fieldOrderID.setEnabled(false);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableOrders = new javax.swing.JTable();
        lblManageOrders = new javax.swing.JLabel();
        lblOrderID = new javax.swing.JLabel();
        lblOrderDate = new javax.swing.JLabel();
        lblOrderType = new javax.swing.JLabel();
        lblOrderStatus = new javax.swing.JLabel();
        lblProductOpted = new javax.swing.JLabel();
        lblCustomer = new javax.swing.JLabel();
        lblPaymentMethod = new javax.swing.JLabel();
        fieldOrderID = new javax.swing.JTextField();
        fieldOrderDate = new javax.swing.JTextField();
        jComboBoxOrderType = new javax.swing.JComboBox<>();
        jComboBoxPaymentMethod = new javax.swing.JComboBox<>();
        jComboBoxOrderStatus = new javax.swing.JComboBox<>();
        jComboBoxProductOpted = new javax.swing.JComboBox<>();
        jComboBoxCustomer = new javax.swing.JComboBox<>();
        btnAddOrders = new javax.swing.JButton();
        btnUpdateOrders = new javax.swing.JButton();
        btnDeleteOrders = new javax.swing.JButton();
        btnClearOrder = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jTableOrders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTableOrders);

        lblManageOrders.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        lblManageOrders.setText("Manage Orders");

        lblOrderID.setText("Order ID");

        lblOrderDate.setText("Order Date");

        lblOrderType.setText("Order Type");

        lblOrderStatus.setText("Order Status");

        lblProductOpted.setText("Product Opted");

        lblCustomer.setText("Customer");

        lblPaymentMethod.setText("Payment Method");

        jComboBoxOrderType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dine-in", "Takeout", "Pickup"}));

        jComboBoxPaymentMethod.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cash", "Card", "Mobile"}));

        jComboBoxOrderStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pending", "Preparing", "Ready", "Completed" }));

        jComboBoxProductOpted.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBoxCustomer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnAddOrders.setText("Add");
        btnAddOrders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddOrdersActionPerformed(evt);
            }
        });

        btnUpdateOrders.setText("Update");
        btnUpdateOrders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateOrdersActionPerformed(evt);
            }
        });

        btnDeleteOrders.setText("Delete");
        btnDeleteOrders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteOrdersActionPerformed(evt);
            }
        });

        btnClearOrder.setText("Clear");
        btnClearOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearOrderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(206, 206, 206)
                                .addComponent(lblManageOrders))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lblPaymentMethod)
                                            .addComponent(lblProductOpted)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(lblOrderType)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(lblOrderID)
                                                    .addComponent(lblOrderDate)))
                                            .addComponent(lblOrderStatus)
                                            .addComponent(lblCustomer))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(fieldOrderID)
                                            .addComponent(fieldOrderDate)
                                            .addComponent(jComboBoxOrderType, 0, 104, Short.MAX_VALUE)
                                            .addComponent(jComboBoxPaymentMethod, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jComboBoxOrderStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jComboBoxProductOpted, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jComboBoxCustomer, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(59, 59, 59))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnAddOrders)
                                        .addGap(39, 39, 39)
                                        .addComponent(btnUpdateOrders)
                                        .addGap(47, 47, 47)
                                        .addComponent(btnDeleteOrders)
                                        .addGap(49, 49, 49)))
                                .addComponent(btnClearOrder)))
                        .addGap(0, 55, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblManageOrders)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOrderID)
                    .addComponent(fieldOrderID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOrderDate)
                    .addComponent(fieldOrderDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOrderType)
                    .addComponent(jComboBoxOrderType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPaymentMethod)
                    .addComponent(jComboBoxPaymentMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOrderStatus)
                    .addComponent(jComboBoxOrderStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProductOpted)
                    .addComponent(jComboBoxProductOpted, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCustomer)
                    .addComponent(jComboBoxCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddOrders)
                    .addComponent(btnUpdateOrders)
                    .addComponent(btnDeleteOrders)
                    .addComponent(btnClearOrder))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnClearOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearOrderActionPerformed
        // TODO add your handling code here:
        clearFields();
    }//GEN-LAST:event_btnClearOrderActionPerformed

    private void btnAddOrdersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddOrdersActionPerformed
        // TODO add your handling code here:
        Order newOrderData = validateAndCreateOrder();
        if (newOrderData != null) {
            // Check for unique ID before adding
            if (!orderDirectory.isOrderIdUnique(newOrderData.getOrderId())) {
                JOptionPane.showMessageDialog(this, "Order ID already exists. Please use a unique ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            orderDirectory.addOrder(newOrderData);
            JOptionPane.showMessageDialog(this, "Order added successfully!");
            clearFields();
            populateTable();
        }
    }//GEN-LAST:event_btnAddOrdersActionPerformed

    private void btnUpdateOrdersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateOrdersActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTableOrders.getSelectedRow();

        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select an order from the table to update.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) jTableOrders.getModel();
        String originalId = model.getValueAt(selectedRow, 0).toString();
        Order orderToUpdate = orderDirectory.findOrderById(originalId);

        if (orderToUpdate != null) {
            Order updatedOrderData = validateAndCreateOrder();

            if (updatedOrderData != null) {
                // Update properties. Order ID remains fixed.
                orderToUpdate.setOrderDate(updatedOrderData.getOrderDate());
                orderToUpdate.setOrderType(updatedOrderData.getOrderType());
                orderToUpdate.setPaymentMethod(updatedOrderData.getPaymentMethod());
                orderToUpdate.setOrderStatus(updatedOrderData.getOrderStatus());
                orderToUpdate.setProduct(updatedOrderData.getProduct());
                orderToUpdate.setCustomer(updatedOrderData.getCustomer());

                JOptionPane.showMessageDialog(this, "Order updated successfully!");
                clearFields();
                populateTable();
            }
        }
    }//GEN-LAST:event_btnUpdateOrdersActionPerformed

    private void btnDeleteOrdersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteOrdersActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTableOrders.getSelectedRow();

        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select an order to delete.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) jTableOrders.getModel();
        String idToDelete = model.getValueAt(selectedRow, 0).toString();

        Order orderToDelete = orderDirectory.findOrderById(idToDelete);

        if (orderToDelete != null) {
             int confirm = JOptionPane.showConfirmDialog(this,
                     "Are you sure you want to delete order ID: " + orderToDelete.getOrderId() + "?",
                     "Confirm Deletion", JOptionPane.YES_NO_OPTION);

             if (confirm == JOptionPane.YES_OPTION) {
                 orderDirectory.deleteOrder(orderToDelete);
                 JOptionPane.showMessageDialog(this, "Order deleted successfully!");
                 clearFields();
                 populateTable();
             }
        }
    }//GEN-LAST:event_btnDeleteOrdersActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddOrders;
    private javax.swing.JButton btnClearOrder;
    private javax.swing.JButton btnDeleteOrders;
    private javax.swing.JButton btnUpdateOrders;
    private javax.swing.JTextField fieldOrderDate;
    private javax.swing.JTextField fieldOrderID;
    private javax.swing.JComboBox<String> jComboBoxCustomer;
    private javax.swing.JComboBox<String> jComboBoxOrderStatus;
    private javax.swing.JComboBox<String> jComboBoxOrderType;
    private javax.swing.JComboBox<String> jComboBoxPaymentMethod;
    private javax.swing.JComboBox<String> jComboBoxProductOpted;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableOrders;
    private javax.swing.JLabel lblCustomer;
    private javax.swing.JLabel lblManageOrders;
    private javax.swing.JLabel lblOrderDate;
    private javax.swing.JLabel lblOrderID;
    private javax.swing.JLabel lblOrderStatus;
    private javax.swing.JLabel lblOrderType;
    private javax.swing.JLabel lblPaymentMethod;
    private javax.swing.JLabel lblProductOpted;
    // End of variables declaration//GEN-END:variables
}
