
package app.model;

import java.util.ArrayList;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.model.ItemInStore;
import app.utility.DatabaseUtility;

public class ItemInStoreManager {
    public static ArrayList<ItemInStore> getAll() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM item_in_store";
        ArrayList<ItemInStore> itemInStoreSet = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                ItemInStore itemInStore = new ItemInStore(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_item"), 
                        resultSet.getInt("id_store"), 
                        resultSet.getInt("stock"),
                        resultSet.getBoolean("is_in_season"),
                        resultSet.getBigDecimal("nominal_discount"),
                        resultSet.getTimestamp("datetime_last_updated")

                );
                itemInStoreSet.add(itemInStore);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemInStoreManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itemInStoreSet;
    }
    public static ItemInStore getById(int idItemInStore) {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM item_in_store WHERE id = " + idItemInStore;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                ItemInStore itemInStore = new ItemInStore(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_item"), 
                        resultSet.getInt("id_store"), 
                        resultSet.getInt("stock"),
                        resultSet.getBoolean("is_in_season"),
                        resultSet.getBigDecimal("nominal_discount"),
                        resultSet.getTimestamp("datetime_last_updated")
                );
                return itemInStore;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemInStoreManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public static ItemInStore getByIdItemAndIdStore(int idItem, int idStore) {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM item_in_store WHERE id_item = ? AND id_store = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idItem);
            preparedStatement.setInt(2, idStore);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ItemInStore itemInStore = new ItemInStore(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_item"), 
                        resultSet.getInt("id_store"), 
                        resultSet.getInt("stock"),
                        resultSet.getBoolean("is_in_season"),
                        resultSet.getBigDecimal("nominal_discount"),
                        resultSet.getTimestamp("datetime_last_updated")
                );
                return itemInStore;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemInStoreManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public static ArrayList<ItemInStore> getByFilter(String filter) {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM item_in_store WHERE " + filter;
        ArrayList<ItemInStore> itemInStoreSet = new ArrayList<ItemInStore>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                ItemInStore itemInStore = new ItemInStore(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_item"), 
                        resultSet.getInt("id_store"), 
                        resultSet.getInt("stock"),
                        resultSet.getBoolean("is_in_season"),
                        resultSet.getBigDecimal("nominal_discount"),
                        resultSet.getTimestamp("datetime_last_updated")     
                );
                itemInStoreSet.add(itemInStore);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemInStoreManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itemInStoreSet;
    }
}
