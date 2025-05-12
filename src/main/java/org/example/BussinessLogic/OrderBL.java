package org.example.BussinessLogic;

import org.example.DataAccess.BillDAO;
import org.example.DataAccess.ClientDAO;
import org.example.DataAccess.OrderDAO;
import org.example.DataAccess.ProductDAO;
import org.example.DataModels.Bill;
import org.example.DataModels.Client;
import org.example.DataModels.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Class created to be used on Order Objects
 * <p>
 * Provides all the functionalities necessary for the CRUD operations on the DB from the Order Panel
 * </p>
 */
public class OrderBL {
    private OrderDAO orderDAO;
    private ClientDAO clientDAO;
    private ProductDAO productDAO;
    private BillDAO billDAO;

    public OrderBL() {
        orderDAO = new OrderDAO();
        clientDAO = new ClientDAO();
        productDAO = new ProductDAO();
        billDAO = new BillDAO();
    }

    public void insertOrder(List<String> values){
        String clientId = values.get(0);
        String productId = values.get(1);
        String productQuantity = values.get(2);

        Client client = clientDAO.findById(Integer.parseInt(clientId.trim()));
        Product product = productDAO.findById(Integer.parseInt(productId.trim()));

        if(Integer.parseInt(productQuantity) > product.getStock()){
            throw new UnderStockException("Demand exceeds Stock");
        }

        int orderId = orderDAO.insertOrder(values);

        Bill bill = new Bill(orderId,client.getName(),product.getName(),Integer.parseInt(productQuantity.trim()),LocalDateTime.now());
        billDAO.logBill(bill);

        int newStock = product.getStock() - Integer.parseInt(productQuantity);
        List<String> productValues = new ArrayList<>();
        productValues.add(String.valueOf(product.getId()));
        productValues.add(product.getName());
        productValues.add(String.valueOf(newStock));

        productDAO.update(productValues);
    }
}
