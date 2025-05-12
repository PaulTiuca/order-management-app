package org.example.Presentation;

import org.example.DataModels.Product;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Panel for viewing, adding, updating and deleting Products
 */
public class ProductPanel extends JPanel {
    private Controller controller;
    private JComboBox<String> productOpComboBox;
    private JPanel productDetailsPanel;
    private JTextField productIdField;
    private JTextField productNameField;
    private JTextField productStockField;
    private JButton confirmButton;
    private JPanel buttonPanel;

    public ProductPanel(MainFrame parentFrame, Controller controller){
        this.controller = controller;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel productOpPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        String[] taskTypes = { "View All", "Insert", "Update", "Delete" };
        productOpComboBox = new JComboBox<>(taskTypes);
        productOpComboBox.setSelectedIndex(0);

        productOpPanel.add(new JLabel("Select Operation:"));
        productOpPanel.add(productOpComboBox);

        this.add(productOpPanel);

        productDetailsPanel = new JPanel();
        productDetailsPanel.setLayout(new BoxLayout(productDetailsPanel, BoxLayout.Y_AXIS));
        this.add(productDetailsPanel);

        confirmButton = new JButton("Confirm");
        AppUtility.configureButton(confirmButton);
        confirmButton.addActionListener(e -> {
            confirmOp();
        });

        JButton backButton = new JButton("Back to Main Menu");
        AppUtility.configureButton(backButton);
        backButton.addActionListener(e -> parentFrame.switchToMainPanel());

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        buttonPanel.add(confirmButton);
        buttonPanel.add(backButton);

        productOpComboBox.addActionListener(e -> updateProductDetailsPanel());
        updateProductDetailsPanel();
    }

    void updateProductDetailsPanel() {
        productDetailsPanel.removeAll();
        String selectedProductOp = (String) productOpComboBox.getSelectedItem();
        switch(selectedProductOp) {
            case "View All":
                createViewAllProductsPanel();
                confirmButton.setVisible(false);
                break;
            case "Insert":
                createInsertProductPanel();
                confirmButton.setVisible(true);
                break;
            case "Update":
                createUpdateProductPanel();
                confirmButton.setVisible(true);
                break;
            case "Delete":
                createDeleteProductPanel();
                confirmButton.setVisible(true);
                break;
        }
        productDetailsPanel.add(buttonPanel);
        productDetailsPanel.revalidate();
        productDetailsPanel.repaint();
    }

    private void createViewAllProductsPanel() {
        List<Product> products;
        JTable productsTable = new JTable();
        try {
            products = controller.viewAllProducts();
            productsTable = AppUtility.abstractCreateTable(products);
        } catch (NoSuchElementException e) {
            System.err.println(e.getMessage());
        }
        JScrollPane scrollPane = new JScrollPane(productsTable);
        scrollPane.setPreferredSize(new Dimension(650, 400));

        productDetailsPanel.add(scrollPane);
    }

    private void createInsertProductPanel() {
        JLabel titleLabel = new JLabel("Insert a new Product");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel productNameLabel = new JLabel("Product Name:");
        productNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        productNameField = new JTextField();
        productNameField.setMaximumSize(new Dimension(305,20));

        JLabel productStockLabel = new JLabel("Product Stock:");
        productStockLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        productStockField = new JTextField();
        productStockField.setMaximumSize(new Dimension(100,20));


        productDetailsPanel.add(titleLabel);
        productDetailsPanel.add(productNameLabel);
        productDetailsPanel.add(productNameField);
        productDetailsPanel.add(productStockLabel);
        productDetailsPanel.add(productStockField);
    }

    private void createUpdateProductPanel() {
        JLabel titleLabel = new JLabel("Update an existing Product");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel productIdLabel = new JLabel("Updated Product Id:");
        productIdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        productIdField = new JTextField();
        productIdField.setMaximumSize(new Dimension(100,20));

        JLabel productNameLabel = new JLabel("New Product Name:");
        productNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        productNameField = new JTextField();
        productNameField.setMaximumSize(new Dimension(305,20));

        JLabel productStockLabel = new JLabel("New Product Stock:");
        productStockLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        productStockField = new JTextField();
        productStockField.setMaximumSize(new Dimension(100,20));

        productDetailsPanel.add(titleLabel);
        productDetailsPanel.add(productIdLabel);
        productDetailsPanel.add(productIdField);
        productDetailsPanel.add(productNameLabel);
        productDetailsPanel.add(productNameField);
        productDetailsPanel.add(productStockLabel);
        productDetailsPanel.add(productStockField);
    }

    private void createDeleteProductPanel() {
        JLabel titleLabel = new JLabel("Delete an existing Product");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel productIdLabel = new JLabel("Product Id:");
        productIdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        productIdField = new JTextField();
        productIdField.setMaximumSize(new Dimension(100,20));

        productDetailsPanel.add(titleLabel);
        productDetailsPanel.add(productIdLabel);
        productDetailsPanel.add(productIdField);
    }

    private void confirmOp() {
        String selectedProductOp = (String) productOpComboBox.getSelectedItem();
        switch(selectedProductOp) {
            case "Insert":
                insertOp();
                break;
            case "Update":
                updateOp();
                break;
            case "Delete":
                deleteOp();
                break;
        }
    }

    private void insertOp(){
        List<String> values = new ArrayList<>();
        String productName = productNameField.getText();
        String productStock = productStockField.getText();
        if(validateInsertProductOp(productName,productStock)) {
            values.add(productName);
            values.add(productStock);

            productNameField.setText("");
            productStockField.setText("");

            controller.insertProduct(values);
            JOptionPane.showMessageDialog(this, "Product has been inserted successfully.", "Operation Successful", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private boolean validateInsertProductOp(String productName, String productStock) {
        if (productName == null || productName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Product name cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (productStock == null || productStock.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Product stock cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Integer.parseInt(productStock);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Product stock needs to be a number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void updateOp(){
        List<String> values = new ArrayList<>();
        String productId = productIdField.getText();
        String productName = productNameField.getText();
        String productStock = productStockField.getText();
        if(validateUpdateProductOp(productId,productName, productStock)) {
            try {
                Product updatedProduct = controller.findProductById(Integer.parseInt(productId.trim()));
                values.add(productId);

                if(productName == null || productName.trim().isEmpty())
                    values.add(updatedProduct.getName());
                else
                    values.add(productName);
                if(productStock == null || productStock.trim().isEmpty())
                    values.add(String.valueOf(updatedProduct.getStock()));
                else
                    values.add(productStock);

                productIdField.setText("");
                productNameField.setText("");
                productStockField.setText("");

                controller.updateProduct(values);
                JOptionPane.showMessageDialog(this, "Product has been updated successfully.", "Operation Successful", JOptionPane.INFORMATION_MESSAGE);
            } catch (NoSuchElementException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean validateUpdateProductOp(String productId, String productName, String productStock) {
        if(productId == null || productId.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Product Id cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if( (productName == null || productName.trim().isEmpty()) && (productStock == null || productStock.trim().isEmpty()) ) {
            JOptionPane.showMessageDialog(this, "Product must have at least one field modified.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Integer.parseInt(productId);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Product Id needs to be a number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;

    }

    private void deleteOp(){
        String productId = productIdField.getText();
        if(validateDeleteProductOp(productId)) {
            productIdField.setText("");
            controller.deleteProductWithId(Integer.parseInt(productId.trim()));
            JOptionPane.showMessageDialog(this, "Product has been deleted successfully.", "Operation Successful", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private boolean validateDeleteProductOp(String productId) {
        if(productId == null || productId.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Product Id cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Integer.parseInt(productId);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Product Id needs to be a number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}

