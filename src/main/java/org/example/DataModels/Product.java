package org.example.DataModels;

/**
 * Represents the Product Object in the Project
 * <p>
 * Has all the CRUD Operations for the DB in the ProductBL Class
 * </p>
 */
public class Product {
    private int id;
    private String name;
    private int stock;

    public Product(){};

    public Product(int id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.stock = quantity;
    }

    public Product(String name, int quantity) {
        this.name = name;
        this.stock = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + stock +
                '}';
    }
}
