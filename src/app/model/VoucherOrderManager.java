
package app.model;

import java.util.ArrayList;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.model.VoucherOrder;
import app.utility.DatabaseUtility;

public class VoucherOrderManager {
    public static ArrayList<VoucherOrder> getAll() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM voucher_order";
        ArrayList<VoucherOrder> voucherOrderSet = new ArrayList<VoucherOrder>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                VoucherOrder voucherOrder = new VoucherOrder(
                        resultSet.getInt("id"),
                        resultSet.getString("name"), 
                        resultSet.getString("code"), 
                        resultSet.getBigDecimal("nominal_discount"),
                        resultSet.getBigDecimal("minimum_item_cost"),
                        resultSet.getTimestamp("datetime_added"),
                        resultSet.getTimestamp("datetime_expired")

                );
                voucherOrderSet.add(voucherOrder);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VoucherOrderManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return voucherOrderSet;
    }
    public static VoucherOrder getByCode(String code) {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM voucher_order WHERE code = '" + code + "'";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                VoucherOrder VoucherOrderManager = new VoucherOrder(
                        resultSet.getInt("id"),
                        resultSet.getString("name"), 
                        resultSet.getString("code"), 
                        resultSet.getBigDecimal("nominal_discount"),
                        resultSet.getBigDecimal("minimum_item_cost"),
                        resultSet.getTimestamp("datetime_added"),
                        resultSet.getTimestamp("datetime_expired")
                );
                return VoucherOrderManager;
            }
        } catch (SQLException ex) {
            Logger.getLogger(VoucherOrderManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public static VoucherOrder getById(int idVoucherOrder) {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM voucher_order WHERE id = " + idVoucherOrder;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                VoucherOrder VoucherOrderManager = new VoucherOrder(
                        resultSet.getInt("id"),
                        resultSet.getString("name"), 
                        resultSet.getString("code"), 
                        resultSet.getBigDecimal("nominal_discount"),
                        resultSet.getBigDecimal("minimum_item_cost"),
                        resultSet.getTimestamp("datetime_added"),
                        resultSet.getTimestamp("datetime_expired")
                );
                return VoucherOrderManager;
            }
        } catch (SQLException ex) {
            Logger.getLogger(VoucherOrderManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
