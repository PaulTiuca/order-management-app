package org.example.BussinessLogic;

import org.example.DataAccess.ProductDAO;
import org.example.DataModels.Product;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Class created to be used on Product Objects
 * <p>
 * Provides all the functionalities necessary for the CRUD operations on the DB from the Product Panel
 * </p>
 */
public class ProductBL {
    private ProductDAO productDAO;

    public ProductBL(){
        productDAO = new ProductDAO();
    }

    public List<Product> findAllProducts() {
        List<Product> products = productDAO.findAll();
        if(products == null || products.isEmpty()) {
            throw new NoSuchElementException("No products could be found");
        }
        return products;
    }

    public Product findProductById(int id) {
        Product product = productDAO.findById(id);
        if(product == null) {
            throw new NoSuchElementException("Client with id " + id + " could not be found");
        }
        return product;
    }

    public void insertProduct(List<String> values){
        productDAO.insert(values);
    }

    public void updateProduct(List<String> values){
        productDAO.update(values);
    }

    public void deleteProductWithId(int id){
        productDAO.deleteWithId(id);
    }
}
