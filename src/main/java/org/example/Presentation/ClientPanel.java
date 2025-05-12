package org.example.Presentation;

import org.example.DataModels.Client;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Panel for viewing, adding, updating and deleting Clients
 */
public class ClientPanel extends JPanel {
    private Controller controller;
    private JComboBox<String> clientOpComboBox;
    private JPanel clientDetailsPanel;
    private JTextField clientIdField;
    private JTextField clientNameField;
    private JTextField clientAddressField;
    private JTextField clientEmailField;
    private JButton confirmButton;
    private JPanel buttonPanel;

    public ClientPanel(MainFrame parentFrame, Controller controller){
        this.controller = controller;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel clientOpPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        String[] taskTypes = { "View All", "Insert", "Update", "Delete" };
        clientOpComboBox = new JComboBox<>(taskTypes);
        clientOpComboBox.setSelectedIndex(0);

        clientOpPanel.add(new JLabel("Select Operation:"));
        clientOpPanel.add(clientOpComboBox);

        this.add(clientOpPanel);

        clientDetailsPanel = new JPanel();
        clientDetailsPanel.setLayout(new BoxLayout(clientDetailsPanel, BoxLayout.Y_AXIS));
        this.add(clientDetailsPanel);

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

        clientOpComboBox.addActionListener(e -> updateClientDetailsPanel());
        updateClientDetailsPanel();
    }

    void updateClientDetailsPanel() {
        clientDetailsPanel.removeAll();
        String selectedClientOp = (String) clientOpComboBox.getSelectedItem();
        switch(selectedClientOp) {
            case "View All":
                createViewAllClientsPanel();
                confirmButton.setVisible(false);
                break;
            case "Insert":
                createInsertClientPanel();
                confirmButton.setVisible(true);
                break;
            case "Update":
                createUpdateClientPanel();
                confirmButton.setVisible(true);
                break;
            case "Delete":
                createDeleteClientPanel();
                confirmButton.setVisible(true);
                break;
        }
        clientDetailsPanel.add(buttonPanel);
        clientDetailsPanel.revalidate();
        clientDetailsPanel.repaint();
    }

    private void createViewAllClientsPanel() {
        List<Client> clients;
        JTable clientsTable = new JTable();
        try {
            clients = controller.viewAllClients();
            clientsTable = AppUtility.abstractCreateTable(clients);
        } catch (NoSuchElementException e) {
            System.err.println(e.getMessage());

        }
        JScrollPane scrollPane = new JScrollPane(clientsTable);
        scrollPane.setPreferredSize(new Dimension(650, 400));

        clientDetailsPanel.add(scrollPane);
    }

    private void createInsertClientPanel() {
        JLabel titleLabel = new JLabel("Insert a new Client");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel clientNameLabel = new JLabel("Client Name:");
        clientNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        clientNameField = new JTextField();
        clientNameField.setMaximumSize(new Dimension(305,20));

        JLabel clientAddressLabel = new JLabel("Client Address:");
        clientAddressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        clientAddressField = new JTextField();
        clientAddressField.setMaximumSize(new Dimension(305,20));

        JLabel clientEmailLabel = new JLabel("Client Email:");
        clientEmailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        clientEmailField = new JTextField();
        clientEmailField.setMaximumSize(new Dimension(305,20));

        clientDetailsPanel.add(titleLabel);
        clientDetailsPanel.add(clientNameLabel);
        clientDetailsPanel.add(clientNameField);
        clientDetailsPanel.add(clientAddressLabel);
        clientDetailsPanel.add(clientAddressField);
        clientDetailsPanel.add(clientEmailLabel);
        clientDetailsPanel.add(clientEmailField);
    }

    private void createUpdateClientPanel() {
        JLabel titleLabel = new JLabel("Update an existing Client");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel clientIdLabel = new JLabel("Updated Client Id:");
        clientIdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        clientIdField = new JTextField();
        clientIdField.setMaximumSize(new Dimension(100,20));

        JLabel clientNameLabel = new JLabel("New Client Name:");
        clientNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        clientNameField = new JTextField();
        clientNameField.setMaximumSize(new Dimension(305,20));

        JLabel clientAddressLabel = new JLabel("New Client Address:");
        clientAddressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        clientAddressField = new JTextField();
        clientAddressField.setMaximumSize(new Dimension(305,20));

        JLabel clientEmailLabel = new JLabel("New Client Email:");
        clientEmailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        clientEmailField = new JTextField();
        clientEmailField.setMaximumSize(new Dimension(305,20));

        clientDetailsPanel.add(titleLabel);
        clientDetailsPanel.add(clientIdLabel);
        clientDetailsPanel.add(clientIdField);
        clientDetailsPanel.add(clientNameLabel);
        clientDetailsPanel.add(clientNameField);
        clientDetailsPanel.add(clientAddressLabel);
        clientDetailsPanel.add(clientAddressField);
        clientDetailsPanel.add(clientEmailLabel);
        clientDetailsPanel.add(clientEmailField);
    }

    private void createDeleteClientPanel() {
        JLabel titleLabel = new JLabel("Delete an existing Client");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel clientIdLabel = new JLabel("Client Id:");
        clientIdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        clientIdField = new JTextField();
        clientIdField.setMaximumSize(new Dimension(100,20));

        clientDetailsPanel.add(titleLabel);
        clientDetailsPanel.add(clientIdLabel);
        clientDetailsPanel.add(clientIdField);
    }

    private void confirmOp() {
        String selectedClientOp = (String) clientOpComboBox.getSelectedItem();
        switch(selectedClientOp) {
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
        String clientName = clientNameField.getText();
        String clientAddress = clientAddressField.getText();
        String clientEmail = clientEmailField.getText();
        if(validateInsertClientOp(clientName,clientAddress,clientEmail)) {
            values.add(clientName);
            values.add(clientAddress);
            values.add(clientEmail);

            clientNameField.setText("");
            clientAddressField.setText("");
            clientEmailField.setText("");

            controller.insertClient(values);
            JOptionPane.showMessageDialog(this, "Client has been inserted successfully.", "Operation Successful", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private boolean validateInsertClientOp(String clientName, String clientAddress, String clientEmail) {
        if (clientName == null || clientName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Client name cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (clientAddress == null || clientAddress.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Client address cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (clientEmail == null || clientEmail.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Client email cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!clientEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            JOptionPane.showMessageDialog(this, "Invalid email format. Expected something@domain.com", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void updateOp(){
        List<String> values = new ArrayList<>();
        String clientId = clientIdField.getText();
        String clientName = clientNameField.getText();
        String clientAddress = clientAddressField.getText();
        String clientEmail = clientEmailField.getText();
        if(validateUpdateClientOp(clientId,clientName,clientAddress,clientEmail)) {
            try {
                Client updatedClient = controller.findClientById(Integer.parseInt(clientId.trim()));
                values.add(clientId);

                if(clientName == null || clientName.trim().isEmpty())
                    values.add(updatedClient.getName());
                else
                    values.add(clientName);
                if(clientAddress == null || clientAddress.trim().isEmpty())
                    values.add(updatedClient.getAddress());
                else
                    values.add(clientAddress);
                if(clientEmail == null || clientEmail.trim().isEmpty())
                    values.add(updatedClient.getEmail());
                else
                    values.add(clientEmail);

                clientIdField.setText("");
                clientNameField.setText("");
                clientAddressField.setText("");
                clientEmailField.setText("");

                controller.updateClient(values);
                JOptionPane.showMessageDialog(this, "Client has been updated successfully.", "Operation Successful", JOptionPane.INFORMATION_MESSAGE);
            } catch (NoSuchElementException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean validateUpdateClientOp(String clientId, String clientName, String clientAddress, String clientEmail) {
        if(clientId == null || clientId.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Client Id cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if( (clientName == null || clientName.trim().isEmpty()) && (clientAddress == null || clientAddress.trim().isEmpty()) && (clientEmail == null || clientEmail.trim().isEmpty())) {
            JOptionPane.showMessageDialog(this, "Client must have at least one field modified.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Integer.parseInt(clientId);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Client Id needs to be a number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;

    }

    private void deleteOp(){
        String clientId = clientIdField.getText();
        if(validateDeleteClientOp(clientId)) {
            clientIdField.setText("");
            controller.deleteClientWithId(Integer.parseInt(clientId.trim()));
            JOptionPane.showMessageDialog(this, "Client has been deleted successfully.", "Operation Successful", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private boolean validateDeleteClientOp(String clientId) {
        if(clientId == null || clientId.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Client Id cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Integer.parseInt(clientId);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Client Id needs to be a number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
