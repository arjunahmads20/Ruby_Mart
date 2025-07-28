
package app.model;

import java.util.ArrayList;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.model.User;
import app.utility.DatabaseUtility;

public class UserManager {
    public static ArrayList<User> getAll() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM user";
        ArrayList<User> userSet = new ArrayList<User>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("fullname"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("email"),
                        resultSet.getString("gender"),
                        resultSet.getDate("date_of_birth"),
                        resultSet.getString("password"),
                        resultSet.getString("role"),
                        resultSet.getTimestamp("datetime_joined"),
                        resultSet.getTimestamp("datetime_last_updated")
                                        
                );
                userSet.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userSet;
    }
    public static User getById(int idUser) {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM user WHERE id = " + idUser;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("fullname"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("email"),
                        resultSet.getString("gender"),
                        resultSet.getDate("date_of_birth"),
                        resultSet.getString("password"),
                        resultSet.getString("role"),
                        resultSet.getTimestamp("datetime_joined"),
                        resultSet.getTimestamp("datetime_last_updated")
                                        
                );
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public static User getByUsernameAndPassword(String username, String password) {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
        User user;
        PreparedStatement preparedStatement;
        System.out.println(username);
        System.out.println(password);
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("fullname"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("email"),
                        resultSet.getString("gender"),
                        resultSet.getDate("date_of_birth"),
                        resultSet.getString("password"),
                        resultSet.getString("role"),
                        resultSet.getTimestamp("datetime_joined"),
                        resultSet.getTimestamp("datetime_last_updated")
                                        
                );
                return user;
            }
        } catch (SQLException ex) {
            System.out.println("Something went wrong" );

            Logger.getLogger(ItemInOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
