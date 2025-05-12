package org.example.BussinessLogic;

/**
 * Thrown when trying to insert orders with a product quantity exceeding product stock
 */
public class UnderStockException extends RuntimeException {
  public UnderStockException(String message) {
    super(message);
  }
}
