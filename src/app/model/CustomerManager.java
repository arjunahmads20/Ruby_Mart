
package app.model;

import java.util.ArrayList;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.model.Customer;
import app.utility.DatabaseUtility;

public class CustomerManager {
    public static ArrayList<Customer> getAll() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM customer";
        ArrayList<Customer> customerSet = new ArrayList<Customer>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Customer customer = new Customer(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_user"),
                        resultSet.getString("status"),
                        resultSet.getString("account_number"),
                        resultSet.getInt("account_pin"),
                        resultSet.getBigDecimal("account_balance")                
                );
                customerSet.add(customer);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return customerSet;
    }
    public static Customer getById(int idCustomer) {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM customer WHERE id = " + idCustomer;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                Customer customer = new Customer(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_user"),
                        resultSet.getString("status"),
                        resultSet.getString("account_number"),
                        resultSet.getInt("account_pin"),
                        resultSet.getBigDecimal("account_balance")
                );
                return customer;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public static ArrayList<Customer> getByFilter(String filter) {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM customer WHERE " + filter;
        ArrayList<Customer> customerSet = new ArrayList<Customer>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Customer customer = new Customer(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_user"),
                        resultSet.getString("status"),
                        resultSet.getString("account_number"),
                        resultSet.getInt("account_pin"),
                        resultSet.getBigDecimal("account_balance")               
                );
                customerSet.add(customer);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return customerSet;
    }
}
