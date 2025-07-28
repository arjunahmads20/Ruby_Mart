
package app.model;

import java.util.ArrayList;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.model.Item;
import app.utility.DatabaseUtility;

public class ItemManager {
    
    public static ArrayList<Item> getAll() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM item";
        ArrayList<Item> itemSet = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Item item = new Item(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_item_category"), 
                        resultSet.getString("name"), 
                        resultSet.getInt("size"), 
                        resultSet.getString("unit"),
                        resultSet.getBigDecimal("buy_price"),
                        resultSet.getBigDecimal("sell_price"),
                        resultSet.getString("description"),
                        resultSet.getString("picture_url"),
                        resultSet.getString("time_consumption"),
                        resultSet.getTimestamp("datetime_added"),
                        resultSet.getTimestamp("datetime_last_updated")

                );
                itemSet.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itemSet;
    }
    public static Item getById(int idItem) {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM item WHERE id = " + idItem;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                Item item = new Item(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_item_category"), 
                        resultSet.getString("name"), 
                        resultSet.getInt("size"), 
                        resultSet.getString("unit"),
                        resultSet.getBigDecimal("buy_price"),
                        resultSet.getBigDecimal("sell_price"),
                        resultSet.getString("description"),
                        resultSet.getString("picture_url"),
                        resultSet.getString("time_consumption"),
                        resultSet.getTimestamp("datetime_added"),
                        resultSet.getTimestamp("datetime_last_updated")
                );
                return item;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public static ArrayList<Item> getByFilter(String filter) {
        Connection connection = DatabaseUtility.getConnection();
        String sql;
        if (filter.equals("")) {
            sql = "SELECT * FROM item";         
        } else {
            sql = "SELECT * FROM item WHERE " + filter;
        }
        ArrayList<Item> itemSet = new ArrayList<Item>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Item item = new Item(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_item_category"), 
                        resultSet.getString("name"), 
                        resultSet.getInt("size"), 
                        resultSet.getString("unit"),
                        resultSet.getBigDecimal("buy_price"),
                        resultSet.getBigDecimal("sell_price"),
                        resultSet.getString("description"),
                        resultSet.getString("picture_url"),
                        resultSet.getString("time_consumption"),
                        resultSet.getTimestamp("datetime_added"),
                        resultSet.getTimestamp("datetime_last_updated")   
                );
                itemSet.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itemSet;
    }
}
