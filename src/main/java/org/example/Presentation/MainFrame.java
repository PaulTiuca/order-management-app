package org.example.Presentation;

import javax.swing.*;
import java.awt.*;

/**
 * The Main Menu Class of the UI
 */
public class MainFrame extends JFrame {
    protected JPanel contentPanel;
    private CardLayout cardLayout;
    private ClientPanel clientPanel;
    private ProductPanel productPanel;
    private OrderPanel orderPanel;
    private BillPanel billPanel;

    public MainFrame(Controller controller) {
        this.setTitle("Order Manager App");
        this.setSize(700, 700);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        JPanel mainMenuPanel = createMainMenu();
        clientPanel = new ClientPanel(this,controller);
        productPanel = new ProductPanel(this,controller);
        orderPanel = new OrderPanel(this,controller);
        billPanel = new BillPanel(this,controller);

        contentPanel.add(mainMenuPanel, "MainMenu");
        contentPanel.add(clientPanel, "clientPanel");
        contentPanel.add(productPanel, "productPanel");
        contentPanel.add(orderPanel,"orderPanel");
        contentPanel.add(billPanel,"billPanel");

        this.setContentPane(contentPanel);
        this.setVisible(true);
    }

    protected void switchToMainPanel() {
        cardLayout.show(contentPanel, "MainMenu");
    }

    private JPanel createMainMenu(){
        JPanel mainMenuPanel = new JPanel();
        mainMenuPanel.setLayout(new BoxLayout(mainMenuPanel, BoxLayout.Y_AXIS));

        JButton clientButton = new JButton("Client Panel");
        AppUtility.configureButton(clientButton);
        clientButton.addActionListener(e -> {
            clientPanel.updateClientDetailsPanel();
            cardLayout.show(contentPanel, "clientPanel");
        });

        mainMenuPanel.add(Box.createVerticalGlue());
        mainMenuPanel.add(clientButton);

        JButton productButton = new JButton("Product Panel");
        AppUtility.configureButton(productButton);
        productButton.addActionListener(e -> {
            productPanel.updateProductDetailsPanel();
            cardLayout.show(contentPanel, "productPanel");
        });

        mainMenuPanel.add(Box.createVerticalGlue());
        mainMenuPanel.add(productButton);

        JButton orderButton = new JButton("Add Order");
        AppUtility.configureButton(orderButton);
        orderButton.addActionListener(e -> {
            orderPanel.updateOrderPanel();
            cardLayout.show(contentPanel, "orderPanel");
        });

        mainMenuPanel.add(Box.createVerticalGlue());
        mainMenuPanel.add(orderButton);

        JButton billButton = new JButton("View Bills");
        AppUtility.configureButton(billButton);
        billButton.addActionListener(e -> {
            billPanel.updateBillDetailsPanel();
            cardLayout.show(contentPanel, "billPanel");
        });

        mainMenuPanel.add(Box.createVerticalGlue());
        mainMenuPanel.add(billButton);

        mainMenuPanel.add(Box.createVerticalGlue());

        return mainMenuPanel;
    }
}
