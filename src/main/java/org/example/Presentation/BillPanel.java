package org.example.Presentation;

import org.example.DataModels.Bill;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Panel for all viewing Order Bills
 */
public class BillPanel extends JPanel {
    private Controller controller;
    private JPanel billDetailsPanel;
    private JPanel buttonPanel;
    private JButton backButton;

    public BillPanel(MainFrame parentFrame, Controller controller) {
        this.controller = controller;

        this.setLayout(new BorderLayout());

        billDetailsPanel = new JPanel();
        billDetailsPanel.setLayout(new BoxLayout(billDetailsPanel, BoxLayout.Y_AXIS));

        this.add(billDetailsPanel, BorderLayout.CENTER);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        backButton = new JButton("Back to Main Menu");
        AppUtility.configureButton(backButton);
        backButton.addActionListener(e -> parentFrame.switchToMainPanel());

        buttonPanel.add(backButton);

        updateBillDetailsPanel();
    }

    void updateBillDetailsPanel() {
        billDetailsPanel.removeAll();
        List<Bill> bills;
        JTable billsTable = new JTable();
        try {
            bills = controller.viewAllBills();
            billsTable = AppUtility.abstractCreateTable(bills);
        } catch (NoSuchElementException e) {
            System.err.println("No bills could be found");
        }
        JScrollPane scrollPane = new JScrollPane(billsTable);
        scrollPane.setPreferredSize(new Dimension(650, 400));
        billDetailsPanel.add(scrollPane);
        billDetailsPanel.add(buttonPanel);
    }
}
