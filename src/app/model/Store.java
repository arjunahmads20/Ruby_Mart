package app.model;

import java.sql.*;
import app.utility.DatabaseUtility;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Store {
    private int id;
    private String address;
    private String name;
    private Timestamp datetimeAdded;


    public Store(int id, String address, String name, Timestamp datetimeAdded) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.datetimeAdded = datetimeAdded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Timestamp getDatetimeAdded() {
        return datetimeAdded;
    }

    public void setDatetimeAdded(Timestamp datetimeAdded) {
        this.datetimeAdded = datetimeAdded;
    }

    
    public void add() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = """
            INSERT INTO store (
                address, name, datetime_added
            ) VALUES (
                ?, ?, ?
            )
        """;
        
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);

            // Set the values
            preparedStatement.setString(1, this.address);
            preparedStatement.setString(2, this.name);
            preparedStatement.setTimestamp(3, this.datetimeAdded);
            int result = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void save() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = """
            UPDATE store SET
                 
                address = ?,
                name = ?,
                datetime_added = ?
            
            WHERE id = ?
        """;
        
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            // Set the values
            preparedStatement.setString(1, this.address);
            preparedStatement.setString(2, this.name);
            preparedStatement.setTimestamp(3, this.datetimeAdded);

            preparedStatement.setInt(4, this.id);

            int result = preparedStatement.executeUpdate();
            if(result == 0) {
                this.add();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void delete() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "DELETE FROM store " +   
                "WHERE id = " + this.id;
        
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

