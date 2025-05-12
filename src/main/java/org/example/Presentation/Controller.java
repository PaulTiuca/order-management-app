package org.example.Presentation;

import org.example.BussinessLogic.*;
import org.example.DataModels.Bill;
import org.example.DataModels.Client;
import org.example.DataModels.Product;

import java.util.List;

/**
 * Controller Class to link Presentation to the BusinessLogic
 * <p>
 * Only provides allowed operations on the Data Models
 * </p>
 */
public class Controller{
    private ClientBL clientBL;
    private ProductBL productBL;
    private OrderBL orderBL;
    private BillBL billBL;

    public Controller() {
        this.clientBL = new ClientBL();
        this.productBL = new ProductBL();
        this.orderBL = new OrderBL();
        this.billBL = new BillBL();
    }

    public Client findClientById(int id){
        return clientBL.findClientById(id);
    }

    public List<Client> viewAllClients() {
        return clientBL.findAllClients();
    }

    public void insertClient(List<String> values){
        clientBL.insertClient(values);
    }

    public void updateClient(List<String> values){
        clientBL.updateClient(values);
    }

    public void deleteClientWithId(int id){
        clientBL.deleteClientWithId(id);
    }

    public Product findProductById(int id){
        return productBL.findProductById(id);
    }

    public List<Product> viewAllProducts() {
        return productBL.findAllProducts();
    }

    public void insertProduct(List<String> values){
        productBL.insertProduct(values);
    }

    public void updateProduct(List<String> values){
        productBL.updateProduct(values);
    }

    public void deleteProductWithId(int id){
        productBL.deleteProductWithId(id);
    }

    public void addOrder(List<String> values) {
        orderBL.insertOrder(values);
    }

    public List<Bill> viewAllBills(){
        return billBL.findAllBills();
    }
}
