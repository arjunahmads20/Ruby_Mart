
package app.model;

import java.util.ArrayList;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.model.ItemInOrder;
import app.utility.DatabaseUtility;

public class ItemInOrderManager {
    public static ArrayList<ItemInOrder> getAllByIdOrder(int idOrder) {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM item_in_order WHERE id_order = " + idOrder;;
        ArrayList<ItemInOrder> itemInOrderSet = new ArrayList<ItemInOrder>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
                ItemInOrder itemInOrder = new ItemInOrder(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_item"), 
                        resultSet.getInt("id_order"), 
                        resultSet.getInt("quantity"),
                        resultSet.getTimestamp("datetime_added"),
                        
                        resultSet.getInt("id_item_category"), 
                        resultSet.getString("name"), 
                        resultSet.getInt("size"), 
                        resultSet.getString("unit"),
                        resultSet.getBigDecimal("buy_price"),
                        resultSet.getBigDecimal("sell_price"),
                        resultSet.getString("description"),
                        resultSet.getString("time_consumption")

                );
                itemInOrderSet.add(itemInOrder);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemInOrderManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itemInOrderSet;
    }
    public static ArrayList<ItemInOrder> getAll() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM item_in_order";
        ArrayList<ItemInOrder> itemInOrderSet = new ArrayList<ItemInOrder>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                ItemInOrder itemInOrder = new ItemInOrder(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_item"), 
                        resultSet.getInt("id_order"), 
                        resultSet.getInt("quantity"),
                        resultSet.getTimestamp("datetime_added"),
                        
                        resultSet.getInt("id_item_category"), 
                        resultSet.getString("name"), 
                        resultSet.getInt("size"), 
                        resultSet.getString("unit"),
                        resultSet.getBigDecimal("buy_price"),
                        resultSet.getBigDecimal("sell_price"),
                        resultSet.getString("description"),
                        resultSet.getString("time_consumption")

                );
                itemInOrderSet.add(itemInOrder);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemInOrderManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itemInOrderSet;
    }
    public static ItemInOrder getById(int idItemInOrder) {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM item_in_order WHERE id = " + idItemInOrder;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                ItemInOrder itemInOrder = new ItemInOrder(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_item"), 
                        resultSet.getInt("id_order"), 
                        resultSet.getInt("quantity"),
                        resultSet.getTimestamp("datetime_added"),
                        
                        resultSet.getInt("id_item_category"), 
                        resultSet.getString("name"), 
                        resultSet.getInt("size"), 
                        resultSet.getString("unit"),
                        resultSet.getBigDecimal("buy_price"),
                        resultSet.getBigDecimal("sell_price"),
                        resultSet.getString("description"),
                        resultSet.getString("time_consumption")
                );
                return itemInOrder;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemInOrderManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
