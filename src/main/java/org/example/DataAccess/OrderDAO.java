package org.example.DataAccess;

import org.example.Connection.ConnectionHandler;

import java.sql.*;
import java.util.List;

/**
 * DAO Class for Order Objects capable of only inserting into DB
 * <p>
 * !!! DOESN'T EXTEND AbstractDAO !!!
 * </p>
 */
public class OrderDAO{
    /**
     * @return Id of the created order for bill creation
     */
    public int insertOrder(List<String> values) {
        String query = "INSERT INTO `Order` VALUES (0,?,?,?)";
        int generatedId = -1;
        try {
            Connection connection = ConnectionHandler.getConnection();
            Statement statement = connection.prepareStatement(query);

            PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, Integer.parseInt(values.get(0).trim()));
            stmt.setInt(2, Integer.parseInt(values.get(1).trim()));
            stmt.setInt(3, Integer.parseInt(values.get(2).trim()));

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return generatedId;
    }
}
