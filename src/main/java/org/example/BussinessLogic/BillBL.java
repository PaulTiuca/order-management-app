package org.example.BussinessLogic;

import org.example.DataAccess.BillDAO;
import org.example.DataModels.Bill;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Class created to be used on Bill Objects (Records)
 * <p>
 * Provides all the functionalities necessary for the CRUD operations
 * </p>
 */
public class BillBL {
    private BillDAO billDAO;

    public BillBL(){
        billDAO = new BillDAO();
    }

    public List<Bill> findAllBills(){
        List<Bill> bills = billDAO.findAll();
        if(bills == null || bills.isEmpty()) {
            throw new NoSuchElementException("No bills could be found");
        }
        return bills;
    }
}
