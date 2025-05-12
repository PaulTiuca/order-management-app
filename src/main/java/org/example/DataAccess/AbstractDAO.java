package org.example.DataAccess;

import org.example.Connection.ConnectionHandler;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generic DAO Class meant to be extended for other DAO Classes
 * <p>
 * Provides all CRUD Operations using Reflection
 * </p>
 */
public class AbstractDAO<T>{
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private String createSelectQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM `");
        sb.append(type.getSimpleName());
        sb.append("` WHERE ");
        Field[] fields = type.getDeclaredFields();
        sb.append(fields[0].getName());
        sb.append(" = ?");
        return sb.toString();
    }

    private String createInsertQuery(){
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO `");
        sb.append(type.getSimpleName());
        sb.append("` VALUES (0");
        for(int i = 0; i < type.getDeclaredFields().length - 1; i++) {
            sb.append(",?");
        }
        sb.append(")");
        return sb.toString();
    }

    private String createUpdateQuery(){
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE `");
        sb.append(type.getSimpleName());
        sb.append("` SET ");
        Field[] fields = type.getDeclaredFields();
        for(int i = 1; i<fields.length; i++) {
            sb.append(fields[i].getName()).append(" = ?");
            if(i < fields.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(" WHERE ");
        sb.append(fields[0].getName());
        sb.append(" = ?");
        return sb.toString();
    }

    private String createDeleteQuery(){
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM `");
        sb.append(type.getSimpleName());
        sb.append("` WHERE ");
        Field[] fields = type.getDeclaredFields();
        sb.append(fields[0].getName());
        sb.append(" = ?");
        return sb.toString();
    }

    public List<T> findAll(){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM `");
        query.append(type.getSimpleName());
        query.append("`");

        try {
            connection = ConnectionHandler.getConnection();
            statement = connection.prepareStatement(query.toString());
            resultSet = statement.executeQuery();

            List<T> objects = createObjects(resultSet);
            if(objects.isEmpty()) {
                return null;
            }
            return objects;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionHandler.close(resultSet);
            ConnectionHandler.close(statement);
            ConnectionHandler.close(connection);
        }
        return null;
    }

    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery();
        try {
            connection = ConnectionHandler.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            List<T> objects = createObjects(resultSet);
            if(objects.isEmpty()) {
                return null;
            }
            return objects.get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionHandler.close(resultSet);
            ConnectionHandler.close(statement);
            ConnectionHandler.close(connection);
        }
        return null;
    }

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException |
                 InvocationTargetException | SQLException | IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insert(List<String> values){
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createInsertQuery();
        try {
            connection = ConnectionHandler.getConnection();
            statement = connection.prepareStatement(query);

            Field[] fields = type.getDeclaredFields();
            for(int i = 1; i < fields.length; i++) {
                fields[i].setAccessible(true);
                Class<?> fieldType = fields[i].getType();
                String rawValue = values.get(i-1);

                if(fieldType == int.class) {
                    statement.setInt(i,Integer.parseInt(rawValue));
                }
                else {
                    statement.setString(i,rawValue);
                }

            }
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionHandler.close(statement);
            ConnectionHandler.close(connection);
        }
    }

    public void update(List<String> values){
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createUpdateQuery();
        try {
            connection = ConnectionHandler.getConnection();
            statement = connection.prepareStatement(query);

            Field[] fields = type.getDeclaredFields();
            statement.setInt(fields.length,Integer.parseInt(values.getFirst()));
            for(int i = 1; i < fields.length; i++) {
                fields[i].setAccessible(true);
                Class<?> fieldType = fields[i].getType();
                String rawValue = values.get(i);

                if(fieldType == int.class) {
                    statement.setInt(i,Integer.parseInt(rawValue));
                }
                else {
                    statement.setString(i,rawValue);
                }
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } finally {
            ConnectionHandler.close(statement);
            ConnectionHandler.close(connection);
        }
    }

    public void deleteWithId(int id){
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDeleteQuery();
        try {
            connection = ConnectionHandler.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
        } finally {
            ConnectionHandler.close(statement);
            ConnectionHandler.close(connection);
        }
    }

}
