package org.example.DataAccess;

import org.example.Connection.ConnectionHandler;
import org.example.DataModels.Bill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DAO Class for Bill Objects (Records)
 * <p>
 * !!! DOESN'T EXTEND AbstractDAO !!!
 * </p>
 */
public class BillDAO {

    private static final Logger LOGGER = Logger.getLogger(BillDAO.class.getName());

    public List<Bill> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Bill> bills = new ArrayList<>();

        String query = "SELECT * FROM Log";

        try {
            connection = ConnectionHandler.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int orderId = resultSet.getInt("orderId");
                String clientName = resultSet.getString("clientName");
                String productName = resultSet.getString("productName");
                int quantity = resultSet.getInt("quantity");
                Timestamp date = resultSet.getTimestamp("date");

                Bill bill = new Bill(orderId, clientName, productName, quantity, date.toLocalDateTime());

                bills.add(bill);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "BillDAO:findAll " + e.getMessage());
        } finally {
            ConnectionHandler.close(resultSet);
            ConnectionHandler.close(statement);
            ConnectionHandler.close(connection);
        }

        return bills;
    }

    public void logBill(Bill bill) {
        String query = "INSERT INTO Log (orderId, clientName, productName, quantity, `date`) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionHandler.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, bill.orderId());
            stmt.setString(2, bill.clientName());
            stmt.setString(3, bill.productName());
            stmt.setInt(4, bill.quantity());
            stmt.setTimestamp(5, java.sql.Timestamp.valueOf(bill.date()));

            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "BillDAO:logBill " + e.getMessage());
        }
    }
}
