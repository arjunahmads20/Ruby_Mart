package app.model;

import java.sql.*;
import app.utility.DatabaseUtility;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VoucherOrder {
    private int id;
    private String name;
    private String code;
    private BigDecimal nominalDiscount;
    private BigDecimal minimumItemCost;
    private Timestamp datetimeAdded;
    private Timestamp datetimeExpired;

    public VoucherOrder(int id, String name, String code, BigDecimal nominalDiscount, BigDecimal minimumItemCost, Timestamp datetimeAdded, Timestamp datetimeExpired) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.nominalDiscount = nominalDiscount;
        this.minimumItemCost = minimumItemCost;
        this.datetimeAdded = datetimeAdded;
        this.datetimeExpired = datetimeExpired;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getNominalDiscount() {
        return nominalDiscount;
    }

    public void setNominalDiscount(BigDecimal nominalDiscount) {
        this.nominalDiscount = nominalDiscount;
    }

    public BigDecimal getMinimumItemCost() {
        return minimumItemCost;
    }

    public void setMinimumItemCost(BigDecimal minimumItemCost) {
        this.minimumItemCost = minimumItemCost;
    }

    public Timestamp getDatetimeAdded() {
        return datetimeAdded;
    }

    public void setDatetimeAdded(Timestamp datetimeAdded) {
        this.datetimeAdded = datetimeAdded;
    }

    public Timestamp getDatetimeExpired() {
        return datetimeExpired;
    }

    public void setDatetimeExpired(Timestamp datetimeExpired) {
        this.datetimeExpired = datetimeExpired;
    }
    

    public void add() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = """
            INSERT INTO voucher_order (
                name, code, nominal_discount, minimum_item_cost, datetime_added, datetime_expired
            ) VALUES (
                ?, ?, ?
            )
        """;
        
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);

            // Set the values
            preparedStatement.setString(1, this.name);
            preparedStatement.setString(2, this.code);
            preparedStatement.setBigDecimal(3, this.nominalDiscount);
            preparedStatement.setBigDecimal(4, this.minimumItemCost);
            preparedStatement.setTimestamp(5, this.datetimeAdded);
            preparedStatement.setTimestamp(6, this.datetimeExpired);
            
            int result = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(VoucherOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void save() {
        Connection connection = DatabaseUtility.getConnection();
        
        String sql = """
            UPDATE voucher_order SET
                 
                name = ?,
                code = ?,
                nominal_discount = ?,
                minimum_item_cost = ?,
                datetime_added = ?,
                datetime_expired = ?
            
            WHERE id = ?
        """;
        
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            // Set the values
            preparedStatement.setString(1, this.name);
            preparedStatement.setString(2, this.code);
            preparedStatement.setBigDecimal(3, this.nominalDiscount);
            preparedStatement.setBigDecimal(4, this.minimumItemCost);
            preparedStatement.setTimestamp(5, this.datetimeAdded);
            preparedStatement.setTimestamp(6, this.datetimeExpired);

            preparedStatement.setInt(7, this.id);

            int result = preparedStatement.executeUpdate();
            if(result == 0) {
                this.add();
            }
        } catch (SQLException ex) {
            Logger.getLogger(VoucherOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void delete() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "DELETE FROM voucher_order " +   
                "WHERE id = " + this.id;
        
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(VoucherOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

