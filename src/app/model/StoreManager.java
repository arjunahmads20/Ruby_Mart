
package app.model;

import java.util.ArrayList;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.model.Store;
import app.utility.DatabaseUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StoreManager {
    public static ArrayList<Store> getAll() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM store";
        ArrayList<Store> storeSet = new ArrayList<Store>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Store store = new Store(
                        resultSet.getInt("id"),
                        resultSet.getString("address"),
                        resultSet.getString("name"),
                        resultSet.getTimestamp("datetime_added")                  
                );
                storeSet.add(store);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StoreManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return storeSet;
    }
    public static ObservableList<String> getAllNames() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT name FROM store";
        ArrayList<String> storeNameSet = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                storeNameSet.add(resultSet.getString("name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StoreManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return FXCollections.observableArrayList(storeNameSet);
    }
    public static Store getByName(String name) {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM store WHERE name = '" + name + "'";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                Store store = new Store(
                        resultSet.getInt("id"),
                        resultSet.getString("address"),
                        resultSet.getString("name"),
                        resultSet.getTimestamp("datetime_added")
                );
                return store;
            }
        } catch (SQLException ex) {
            Logger.getLogger(StoreManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public static Store getById(int idStore) {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM store WHERE id = " + idStore;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                Store store = new Store(
                        resultSet.getInt("id"),
                        resultSet.getString("address"),
                        resultSet.getString("name"),
                        resultSet.getTimestamp("datetime_added") 
                );
                return store;
            }
        } catch (SQLException ex) {
            Logger.getLogger(StoreManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public static ArrayList<Store> getByFilter(String filter) {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM store WHERE " + filter;
        ArrayList<Store> storeSet = new ArrayList<Store>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Store store = new Store(
                        resultSet.getInt("id"),
                        resultSet.getString("address"),
                        resultSet.getString("name"),
                        resultSet.getTimestamp("datetime_added")          
                );
                storeSet.add(store);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StoreManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return storeSet;
    }
}
