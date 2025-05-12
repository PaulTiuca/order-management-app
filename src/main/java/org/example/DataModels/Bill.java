package org.example.DataModels;

import java.time.LocalDateTime;

/**
 * Created and inserted into the Log Table upon placing an order
 * @param orderId
 * @param clientName
 * @param productName
 * @param quantity
 * @param date
 */
public record Bill(int orderId, String clientName, String productName, int quantity, LocalDateTime date) {
    public Bill(int orderId, String clientName, String productName, int quantity, LocalDateTime date) {
        this.orderId = orderId;
        this.clientName = clientName;
        this.productName = productName;
        this.quantity = quantity;
        this.date = date;
    }

    @Override
    public int orderId() {
        return orderId;
    }

    @Override
    public String clientName() {
        return clientName;
    }

    @Override
    public String productName() {
        return productName;
    }

    @Override
    public int quantity() {
        return quantity;
    }

    @Override
    public LocalDateTime date() {
        return date;
    }
}
