package org.example.Presentation;

import org.example.BussinessLogic.UnderStockException;
import org.example.DataModels.Client;
import org.example.DataModels.Product;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Panel for placing Orders
 */
public class OrderPanel extends JPanel{
    private Controller controller;
    private JComboBox<String> clientComboBox;
    private JComboBox<String> productComboBox;
    private JTextField quantityField;
    private JPanel buttonPanel;
    private JLabel clientLabel;
    private JLabel productLabel;

    public OrderPanel(MainFrame parentFrame, Controller controller){
        this.controller = controller;

        clientLabel = new JLabel("Select Client ID:");
        productLabel = new JLabel("Select Product ID:");
        clientComboBox = new JComboBox<>();
        productComboBox = new JComboBox<>();

        JButton backButton = new JButton("Back to Main Menu");
        AppUtility.configureButton(backButton);
        backButton.addActionListener(e -> parentFrame.switchToMainPanel());

        JButton confirmButton = new JButton("Add Order");
        AppUtility.configureButton(confirmButton);
        confirmButton.addActionListener(e -> confirmOp());

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(confirmButton);
        buttonPanel.add(backButton);

        updateOrderPanel();
    }

    void updateOrderPanel(){
        this.removeAll();
        List<String> clientIds = new ArrayList<>();
        List<String> productIds = new ArrayList<>();
        try {
            List<Client> clients = controller.viewAllClients();
            clientIds = clients.stream()
                    .map(client -> String.valueOf(client.getId()))
                    .toList();
        } catch (NoSuchElementException e) {
            System.err.println(e.getMessage());
        }

        try {
            List<Product> products = controller.viewAllProducts();
            productIds = products.stream()
                    .map(product -> String.valueOf(product.getId()))
                    .toList();
        } catch (NoSuchElementException e) {
            System.err.println(e.getMessage());
        }

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(clientLabel, gbc);

        clientComboBox = new JComboBox<>(clientIds.toArray(new String[0]));
        gbc.gridx = 1;
        add(clientComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(productLabel, gbc);

        productComboBox = new JComboBox<>(productIds.toArray(new String[0]));
        gbc.gridx = 1;
        add(productComboBox, gbc);

        JLabel quantityLabel = new JLabel("Enter Quantity:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(quantityLabel, gbc);

        quantityField = new JTextField();
        gbc.gridx = 1;
        add(quantityField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);
    }

    private void confirmOp(){
        String selectedClientId = (String) clientComboBox.getSelectedItem();
        String selectedProductId = (String) productComboBox.getSelectedItem();
        String productQuantity = quantityField.getText();

        if(validateAddOrderOp(productQuantity)){
            List<String> values = new ArrayList<>();
            values.add(selectedClientId);
            values.add(selectedProductId);
            values.add(productQuantity);
            try {
                controller.addOrder(values);
                JOptionPane.showMessageDialog(this, "Order has been placed successfully", "Add Order Error", JOptionPane.INFORMATION_MESSAGE);
                quantityField.setText("");
            } catch (UnderStockException | NoSuchElementException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Add Order Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean validateAddOrderOp(String productQuantity){
        if(productQuantity == null || productQuantity.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Product Quantity cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Integer.parseInt(productQuantity);
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this, "Product Quantity needs to be a integer", "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
        return true;
    }
}
