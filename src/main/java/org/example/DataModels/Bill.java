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
}
