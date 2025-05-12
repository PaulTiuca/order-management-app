package org.example;

import org.example.Presentation.Controller;
import org.example.Presentation.MainFrame;

import javax.swing.*;

/**
 * Starts App
 */
public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        SwingUtilities.invokeLater(() -> new MainFrame(controller));
    }
}