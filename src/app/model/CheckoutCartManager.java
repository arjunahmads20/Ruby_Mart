
package app.model;

import java.util.ArrayList;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.model.CheckoutCart;
import app.utility.DatabaseUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CheckoutCartManager {
    public static ArrayList<CheckoutCart> getAll() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM checkout_cart";
        ArrayList<CheckoutCart> checkoutCartSet = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                CheckoutCart checkoutCart = new CheckoutCart(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_store"), 
                        resultSet.getString("name"),
                        resultSet.getTimestamp("datetime_added")

                );
                checkoutCartSet.add(checkoutCart);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CheckoutCartManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return checkoutCartSet;
    }
    public static ObservableList<String> getAllNames(int idStore) {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT name FROM checkout_cart WHERE id_store = " + idStore;
        ArrayList<String> checkoutCartNameSet = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                checkoutCartNameSet.add(resultSet.getString("name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CheckoutCartManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return FXCollections.observableArrayList(checkoutCartNameSet);
    }
    public static CheckoutCart getByNameAndIdStore(String name, int idStore) {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM checkout_cart WHERE name = '" + name + "' AND id_store = " + idStore;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                CheckoutCart checkoutCart = new CheckoutCart(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_store"), 
                        resultSet.getString("name"),
                        resultSet.getTimestamp("datetime_added")
                );
                return checkoutCart;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CheckoutCartManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public static CheckoutCart getByIdStore(int idStore) {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM checkout_cart WHERE id_store = " + idStore;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                CheckoutCart checkoutCart = new CheckoutCart(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_store"), 
                        resultSet.getString("name"),
                        resultSet.getTimestamp("datetime_added")
                );
                return checkoutCart;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CheckoutCartManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public static CheckoutCart getById(int idCheckoutCart) {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM checkout_cart WHERE id = " + idCheckoutCart;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                CheckoutCart checkoutCart = new CheckoutCart(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_store"), 
                        resultSet.getString("name"),
                        resultSet.getTimestamp("datetime_added")
                );
                return checkoutCart;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CheckoutCartManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
