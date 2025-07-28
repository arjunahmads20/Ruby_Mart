
package app.model;

import java.util.ArrayList;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.model.ItemCategory;
import app.utility.DatabaseUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ItemCategoryManager {
    public static ArrayList<ItemCategory> getAll() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM item_category";
        ArrayList<ItemCategory> itemCategorySet = new ArrayList<ItemCategory>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                ItemCategory itemCategory = new ItemCategory(
                        resultSet.getInt("id"),
                        resultSet.getString("name"), 
                        resultSet.getString("icon_url"), 
                        resultSet.getTimestamp("datetime_added"),
                        resultSet.getTimestamp("datetime_last_updated")
                );
                itemCategorySet.add(itemCategory);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemCategoryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itemCategorySet;
    }
    public static ObservableList<String> getAllNames() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT name FROM item_category";
        ArrayList<String> itemCategoryNameSet = new ArrayList<String>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                itemCategoryNameSet.add(resultSet.getString("name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemCategoryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return FXCollections.observableArrayList(itemCategoryNameSet);
    }
    public static ItemCategory getById(int idItemCategory) {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM item_category WHERE id = " + idItemCategory;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                ItemCategory itemCategory = new ItemCategory(
                        resultSet.getInt("id"),
                        resultSet.getString("name"), 
                        resultSet.getString("icon_url"), 
                        resultSet.getTimestamp("datetime_added"),
                        resultSet.getTimestamp("datetime_last_updated")
                );
                return itemCategory;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemCategoryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public static ItemCategory getByName(String itemCategoryName) {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM item_category WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, itemCategoryName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ItemCategory itemCategory = new ItemCategory(
                        resultSet.getInt("id"),
                        resultSet.getString("name"), 
                        resultSet.getString("icon_url"), 
                        resultSet.getTimestamp("datetime_added"),
                        resultSet.getTimestamp("datetime_last_updated")
                );
                return itemCategory;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemCategoryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public static ArrayList<ItemCategory> getByFilter(String filter) {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM item_category WHERE " + filter;
        ArrayList<ItemCategory> itemCategorySet = new ArrayList<ItemCategory>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                ItemCategory itemCategory = new ItemCategory(
                        resultSet.getInt("id"),
                        resultSet.getString("name"), 
                        resultSet.getString("icon_url"), 
                        resultSet.getTimestamp("datetime_added"),
                        resultSet.getTimestamp("datetime_last_updated")     
                );
                itemCategorySet.add(itemCategory);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemCategoryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itemCategorySet;
    }
}
