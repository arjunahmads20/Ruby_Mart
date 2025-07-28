
package app.model;

import java.util.ArrayList;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.model.Admin;
import app.utility.DatabaseUtility;

public class AdminManager {
    public static ArrayList<Admin> getAll() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM admin";
        ArrayList<Admin> adminSet = new ArrayList<Admin>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Admin admin = new Admin(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_user")              
                );
                adminSet.add(admin);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return adminSet;
    }
    public static Admin getById(int idAdmin) {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM admin WHERE id = " + idAdmin;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                Admin admin = new Admin(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_user")
                );
                return admin;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public static ArrayList<Admin> getByFilter(String filter) {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM admin WHERE " + filter;
        ArrayList<Admin> adminSet = new ArrayList<Admin>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Admin admin = new Admin(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_user")             
                );
                adminSet.add(admin);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return adminSet;
    }
}
