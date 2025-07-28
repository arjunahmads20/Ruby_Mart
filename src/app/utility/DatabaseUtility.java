package app.utility;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
        
public class DatabaseUtility {
    private static Connection connection;
    
    public static Connection getConnection() {
        try {
            if(connection == null) {
                String dbName = "ruby_mart";
                String url = "jdbc:mysql://localhost:3306/" + dbName;
                String user = "root";
                String password = "";
                connection = DriverManager.getConnection(url, user, password);
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseUtility.class.getName()).log(Level.SEVERE, null, e);
        }
        return connection;
    }
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch(SQLException e) {
                Logger.getLogger(DatabaseUtility.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
}
