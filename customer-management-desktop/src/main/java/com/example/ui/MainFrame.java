package com.example.ui;

import com.example.model.Customer;
import com.example.service.CustomerService;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    
    private JTextField nameField = new JTextField();
    private JTextField emailField = new JTextField();
    private JTextField phoneField = new JTextField();

    private JButton addBtn = new JButton("Add");
    private JButton updateBtn = new JButton("Update");
    private JButton deleteBtn = new JButton("Delete");
    private JButton refreshBtn = new JButton("Refresh");

    private JLabel loadingLabel = new JLabel("Loading...");

    private CustomerService service = new CustomerService();

    private Integer selectedId = null;

    private JTextField searchField = new JTextField();

    private TableRowSorter<DefaultTableModel> sorter;

    // Color Palette Definition
    private final Color COLOR_PRIMARY = new Color(41, 128, 185);    // Professional Slate Blue
    private final Color COLOR_SUCCESS = new Color(39, 174, 96);    // Emerald Green
    private final Color COLOR_WARNING = new Color(243, 156, 18);    // Soft Amber
    private final Color COLOR_DANGER = new Color(192, 41, 43);     // Deep Crimson
    private final Color COLOR_BG_LIGHT = new Color(248, 249, 250); // Clean Off-White
    private final Color COLOR_TEXT_DARK = new Color(44, 62, 80);   // Dark Charcoal

    public MainFrame() {
        // Apply System Look and Feel before initializing components
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Fallback gracefully if system style is unavailable
        }

        setTitle("Customer Management System");
        setSize(1000, 600); // Slightly adjusted dimensions for breathing room
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
        loadCustomers();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(COLOR_BG_LIGHT);

        // =========================
        // TOP PANEL (SEARCH & HEADER)
        // =========================
        JPanel topPanel = new JPanel(new BorderLayout(15, 0));
        topPanel.setBackground(COLOR_BG_LIGHT);
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("Customers");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(COLOR_TEXT_DARK);
        topPanel.add(titleLabel, BorderLayout.WEST);

        JPanel searchContainer = new JPanel(new BorderLayout(8, 0));
        searchContainer.setBackground(COLOR_BG_LIGHT);
        JLabel searchLabel = new JLabel("Search: ");
        searchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchLabel.setForeground(COLOR_TEXT_DARK);
        
        searchField.setPreferredSize(new Dimension(250, 30));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        searchContainer.add(searchLabel, BorderLayout.WEST);
        searchContainer.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchContainer, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // =========================
        // CENTER PANEL (TABLE)
        // =========================
        model = new DefaultTableModel(
                new String[]{"ID", "Name", "Email", "Phone", "Created At"},
                0
        );

        table = new JTable(model);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        table.setDefaultEditor(Object.class, null);
        
        // Table Styling Rules
        table.setRowHeight(32);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setGridColor(new Color(230, 233, 237));
        table.setSelectionBackground(new Color(52, 152, 219));
        table.setSelectionForeground(Color.WHITE);
        table.setShowVerticalLines(false); // Clean modern look without vertical dividers

        // Header Styling Rules
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(Color.WHITE);
        header.setForeground(COLOR_TEXT_DARK);
        header.setPreferredSize(new Dimension(header.getWidth(), 38));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(220, 224, 230)));
        
        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setBackground(COLOR_BG_LIGHT);
        centerWrapper.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 10));
        centerWrapper.add(scrollPane, BorderLayout.CENTER);
        
        add(centerWrapper, BorderLayout.CENTER);

        // =========================
        // EAST PANEL (FORM CONTROL)
        // =========================
        JPanel sidePanel = new JPanel(new BorderLayout());
        sidePanel.setBackground(Color.WHITE);
        sidePanel.setPreferredSize(new Dimension(300, getHeight()));
        sidePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(220, 224, 230)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Form Fields Wrapper
        JPanel formFieldsPanel = new JPanel(new GridBagLayout());
        formFieldsPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 0, 6, 0);
        gbc.weightx = 1.0;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        Dimension fieldSize = new Dimension(260, 32);

        // Name Block
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(labelFont);
        nameLabel.setForeground(COLOR_TEXT_DARK);
        formFieldsPanel.add(nameLabel, gbc);
        
        gbc.gridy = 1;
        nameField.setFont(fieldFont);
        nameField.setPreferredSize(fieldSize);
        formFieldsPanel.add(nameField, gbc);

        // Email Block
        gbc.gridy = 2;
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(labelFont);
        emailLabel.setForeground(COLOR_TEXT_DARK);
        formFieldsPanel.add(emailLabel, gbc);
        
        gbc.gridy = 3;
        emailField.setFont(fieldFont);
        emailField.setPreferredSize(fieldSize);
        formFieldsPanel.add(emailField, gbc);

        // Phone Block
        gbc.gridy = 4;
        JLabel phoneLabel = new JLabel("Phone");
        phoneLabel.setFont(labelFont);
        phoneLabel.setForeground(COLOR_TEXT_DARK);
        formFieldsPanel.add(phoneLabel, gbc);
        
        gbc.gridy = 5;
        phoneField.setFont(fieldFont);
        phoneField.setPreferredSize(fieldSize);
        formFieldsPanel.add(phoneField, gbc);

        sidePanel.add(formFieldsPanel, BorderLayout.NORTH);

        // Action Buttons Wrapper
        JPanel actionsPanel = new JPanel(new GridLayout(5, 1, 0, 10));
        actionsPanel.setBackground(Color.WHITE);

        styleButton(addBtn, COLOR_SUCCESS);
        styleButton(updateBtn, COLOR_WARNING);
        styleButton(deleteBtn, COLOR_DANGER);
        styleButton(refreshBtn, COLOR_PRIMARY);

        actionsPanel.add(addBtn);
        actionsPanel.add(updateBtn);
        actionsPanel.add(deleteBtn);
        actionsPanel.add(refreshBtn);

        // Loading Indicator Setup
        loadingLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        loadingLabel.setForeground(COLOR_PRIMARY);
        loadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loadingLabel.setVisible(false);
        actionsPanel.add(loadingLabel);

        sidePanel.add(actionsPanel, BorderLayout.SOUTH);
        add(sidePanel, BorderLayout.EAST);

        // =========================
        // EVENTS CONFIGURATION
        // =========================
        table.getSelectionModel().addListSelectionListener(e -> fillFormFromTable());
        addBtn.addActionListener(e -> addCustomer());
        updateBtn.addActionListener(e -> updateCustomer());
        deleteBtn.addActionListener(e -> deleteCustomer());
        refreshBtn.addActionListener(e -> loadCustomers());

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { filterTable(); }
            @Override
            public void removeUpdate(DocumentEvent e) { filterTable(); }
            @Override
            public void changedUpdate(DocumentEvent e) { filterTable(); }
        });
    }

    private void styleButton(JButton button, Color primaryColor) {
        button.setBackground(primaryColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(button.getPreferredSize().width, 36));
        button.setBorder(BorderFactory.createEmptyBorder());
        
        // Ensure standard native button highlights don't override custom backgrounds
        button.setContentAreaFilled(true);
        button.setOpaque(true);
    }

    // =========================
    // LOAD CUSTOMERS
    // =========================
    private void loadCustomers() {
        startLoading();
        try {
            List<Customer> list = service.getAllCustomers();
            model.setRowCount(0);
            sorter.setRowFilter(null);

            for (Customer c : list) {
                model.addRow(new Object[]{
                        c.getId(),
                        c.getName(),
                        c.getEmail(),
                        c.getPhone(),
                        c.getCreatedAt()
                });
            }
            stopLoading();
        } catch (Exception e) {
            stopLoading();
            showError("Failed to load customers");
        }
    }

    // =========================
    // FILL FORM FROM TABLE
    // =========================
    private void fillFormFromTable() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            selectedId = (Integer) model.getValueAt(row, 0);
            nameField.setText(model.getValueAt(row, 1).toString());
            emailField.setText(model.getValueAt(row, 2).toString());
            phoneField.setText(model.getValueAt(row, 3).toString());
        }
    }

    // =========================
    // VALIDATION
    // =========================
    private boolean validateForm() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();

        if (name.isEmpty()) {
            showError("Name is required");
            return false;
        }
        if (name.length() < 3) {
            showError("Name must be at least 3 characters");
            return false;
        }
        if (email.isEmpty()) {
            showError("Email is required");
            return false;
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            showError("Invalid email format");
            return false;
        }
        if (phone.isEmpty()) {
            showError("Phone is required");
            return false;
        }
        if (!phone.matches("\\d+")) {
            showError("Phone must contain numbers only");
            return false;
        }
        if (phone.length() < 8) {
            showError("Phone number is too short");
            return false;
        }
        return true;
    }

    // =========================
    // ADD CUSTOMER
    // =========================
    private void addCustomer() {
        if (!validateForm()) return;
        startLoading();
        try {
            Customer customer = new Customer();
            customer.setName(nameField.getText());
            customer.setEmail(emailField.getText());
            customer.setPhone(phoneField.getText());

            service.addCustomer(customer);
            loadCustomers();
            clearForm();
            stopLoading();

            JOptionPane.showMessageDialog(this, "Customer added successfully");
        } catch (Exception e) {
            stopLoading();
            showError(e.getMessage());
        }
    }

    // =========================
    // UPDATE CUSTOMER
    // =========================
    private void updateCustomer() {
        if (selectedId == null) {
            showError("Please select a customer");
            return;
        }
        if (!validateForm()) return;
        startLoading();
        try {
            Customer customer = new Customer();
            customer.setName(nameField.getText());
            customer.setEmail(emailField.getText());
            customer.setPhone(phoneField.getText());

            service.updateCustomer(selectedId, customer);
            loadCustomers();
            clearForm();
            stopLoading();

            JOptionPane.showMessageDialog(this, "Customer updated successfully");
        } catch (Exception e) {
            stopLoading();
            showError(e.getMessage());
        }
    }

    // =========================
    // DELETE CUSTOMER
    // =========================
    private void deleteCustomer() {
        if (selectedId == null) {
            showError("Please select a customer");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this customer?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        startLoading();
        try {
            service.deleteCustomer(selectedId);
            loadCustomers();
            clearForm();
            stopLoading();

            JOptionPane.showMessageDialog(this, "Customer deleted successfully");
        } catch (Exception e) {
            stopLoading();
            showError(e.getMessage());
        }
    }

    // =========================
    // CLEAR FORM
    // =========================
    private void clearForm() {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        selectedId = null;
        table.clearSelection();
    }

    // =========================
    // LOADING STATE
    // =========================
    private void startLoading() {
        loadingLabel.setVisible(true);
        addBtn.setEnabled(false);
        updateBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
        refreshBtn.setEnabled(false);
    }

    private void stopLoading() {
        loadingLabel.setVisible(false);
        addBtn.setEnabled(true);
        updateBtn.setEnabled(true);
        deleteBtn.setEnabled(true);
        refreshBtn.setEnabled(true);
    }

    // =========================
    // ERROR MESSAGE
    // =========================
    private void showError(String msg) {
        JOptionPane.showMessageDialog(
                this,
                msg,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void filterTable() {
        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(
                    RowFilter.regexFilter("(?i)" + searchText)
            );
        }
    }
}