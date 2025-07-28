
package app.model;

import java.util.ArrayList;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.model.Cashier;
import app.utility.DatabaseUtility;

public class CashierManager {
    public static ArrayList<Cashier> getAll() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM cashier";
        ArrayList<Cashier> cashierSet = new ArrayList<Cashier>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Cashier cashier = new Cashier(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_user"), 
                        resultSet.getInt("id_store")

                );
                cashierSet.add(cashier);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CashierManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cashierSet;
    }
    
    public static Cashier getByIdStore(int idStore) {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM cashier WHERE id_store = " + idStore;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                Cashier cashier = new Cashier(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_user"),
                        resultSet.getInt("id_store")
                );
                return cashier;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CashierManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public static Cashier getById(int idCashier) {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM cashier WHERE id = " + idCashier;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                Cashier cashier = new Cashier(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_user"),
                        resultSet.getInt("id_store")
                );
                return cashier;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CashierManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public static Cashier getByIdUser(int idUser) {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM cashier WHERE id_user = " + idUser;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                Cashier cashier = new Cashier(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_user"),
                        resultSet.getInt("id_store")
                );
                return cashier;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CashierManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public static ArrayList<Cashier> getByFilter(String filter) {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM cashier WHERE " + filter;
        ArrayList<Cashier> cashierSet = new ArrayList<Cashier>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Cashier cashier = new Cashier(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_user"),
                        resultSet.getInt("id_store")
                );
                cashierSet.add(cashier);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CashierManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cashierSet;
    }
}
