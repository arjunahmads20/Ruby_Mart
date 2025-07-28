
package app.model;

import java.util.ArrayList;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.model.OrderPayment;
import app.utility.DatabaseUtility;

public class OrderPaymentManager {
    public static ArrayList<OrderPayment> getAll() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM order_payment";
        ArrayList<OrderPayment> orderPaymentSet = new ArrayList<OrderPayment>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                OrderPayment orderPayment = new OrderPayment(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_order"), 
                        resultSet.getString("account_number"), 
                        resultSet.getBigDecimal("order_cost"),
                        resultSet.getBigDecimal("admin_cost"),
                        resultSet.getTimestamp("datetime_created"),
                        resultSet.getTimestamp("datetime_finished")

                );
                orderPaymentSet.add(orderPayment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderPaymentManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orderPaymentSet;
    }
    public static OrderPayment getByIdOrder(int idOrder) {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM order_payment WHERE id_order = " + idOrder;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                OrderPayment orderPayment = new OrderPayment(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_order"), 
                        resultSet.getString("account_number"), 
                        resultSet.getBigDecimal("order_cost"),
                        resultSet.getBigDecimal("admin_cost"),
                        resultSet.getTimestamp("datetime_created"),
                        resultSet.getTimestamp("datetime_finished")
                );
                return orderPayment;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderPaymentManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public static OrderPayment getById(int idOrderPayment) {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM order_payment WHERE id = " + idOrderPayment;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                OrderPayment orderPayment = new OrderPayment(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_order"), 
                        resultSet.getString("account_number"), 
                        resultSet.getBigDecimal("order_cost"),
                        resultSet.getBigDecimal("admin_cost"),
                        resultSet.getTimestamp("datetime_created"),
                        resultSet.getTimestamp("datetime_finished")
                );
                return orderPayment;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderPaymentManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
