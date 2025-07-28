
package app.model;

import java.util.ArrayList;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.model.ItemInCheckoutCart;
import app.utility.DatabaseUtility;

public class ItemInCheckoutCartManager {
    public static ArrayList<ItemInCheckoutCart> getAllByIdCheckoutCart(int idCheckoutCart) {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM item_in_checkout_cart WHERE id_checkout_cart = " + idCheckoutCart;
        ArrayList<ItemInCheckoutCart> itemInCheckoutCartSet = new ArrayList<ItemInCheckoutCart>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                ItemInCheckoutCart itemInCheckoutCart = new ItemInCheckoutCart(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_item"), 
                        resultSet.getInt("id_checkout_cart"), 
                        resultSet.getInt("quantity"),
                        resultSet.getTimestamp("datetime_added")

                );
                itemInCheckoutCartSet.add(itemInCheckoutCart);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemInCheckoutCartManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itemInCheckoutCartSet;
    }
    public static ArrayList<ItemInCheckoutCart> getAll() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM item_in_checkout_cart";
        ArrayList<ItemInCheckoutCart> itemInCheckoutCartSet = new ArrayList<ItemInCheckoutCart>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                ItemInCheckoutCart itemInCheckoutCart = new ItemInCheckoutCart(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_item"), 
                        resultSet.getInt("id_checkout_cart"), 
                        resultSet.getInt("quantity"),
                        resultSet.getTimestamp("datetime_added")

                );
                itemInCheckoutCartSet.add(itemInCheckoutCart);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemInCheckoutCartManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itemInCheckoutCartSet;
    }
    public static ItemInCheckoutCart getById(int idItemInCheckoutCart) {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM item_in_checkout_cart WHERE id = " + idItemInCheckoutCart;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                ItemInCheckoutCart itemInCheckoutCart = new ItemInCheckoutCart(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_item"), 
                        resultSet.getInt("id_checkout_cart"), 
                        resultSet.getInt("quantity"),
                        resultSet.getTimestamp("datetime_added")
        
                );
                return itemInCheckoutCart;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemInCheckoutCartManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public static ItemInCheckoutCart getByIdItemAndIdCheckoutCart(int idItem, int idCheckoutCart) {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM item_in_checkout_cart WHERE id_item = ? AND id_checkout_cart = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idItem);
            preparedStatement.setInt(2, idCheckoutCart);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ItemInCheckoutCart itemInCheckoutCart = new ItemInCheckoutCart(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_item"), 
                        resultSet.getInt("id_checkout_cart"), 
                        resultSet.getInt("quantity"),
                        resultSet.getTimestamp("datetime_added")
        
                );
                return itemInCheckoutCart;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemInCheckoutCartManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
