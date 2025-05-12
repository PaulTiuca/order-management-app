package org.example.Presentation;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Component;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;

/**
 * Provides utility for configuring JButton settings and generating a table from a List of Objects using Reflection
 */
public class AppUtility {
    public static void configureButton(JButton button){
        button.setPreferredSize(new Dimension(150,40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFocusable(false);
    }

    public static <T> JTable abstractCreateTable(List<T> objects) {
        if(objects == null || objects.isEmpty()) {
            return new JTable();
        }

        Class<?> tableClass = objects.get(0).getClass();
        Field[] fields = tableClass.getDeclaredFields();

        String[] columnNames = Arrays.stream(fields).map(Field::getName).toArray(String[]::new);

        Object[][] rowData = new Object[objects.size()][fields.length];

        for (int i = 0; i < objects.size(); i++) {
            T obj = objects.get(i);
            for (int j = 0; j < fields.length; j++) {
                fields[j].setAccessible(true);
                try {
                    rowData[i][j] = fields[j].get(obj);
                } catch (IllegalAccessException e) {
                    rowData[i][j] = "ERROR";
                }
            }
        }

        return new JTable(rowData, columnNames);
    }
}