package app.model;

import java.sql.*;
import app.utility.DatabaseUtility;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderPayment {
    private int id = 0;
    private int idOrder;
    private String accountNumber;
    private BigDecimal orderCost; // Optional
    private BigDecimal adminCost;
    private Timestamp datetimeCreated;
    private Timestamp datetimeFinished;
    
    public BigDecimal geOrderCost() {
        return OrderManager.getById(this.idOrder).getOrderCost();
    }
    public BigDecimal getTotalPayCost() {
        return this.orderCost.add(this.adminCost);
    }
    
    public OrderPayment() {
    }
    public OrderPayment(int id, int idOrder, String accountNumber, BigDecimal orderCost, BigDecimal adminCost, Timestamp datetimeCreated, Timestamp datetimeFinished) {
        this.id = id;
        this.idOrder = idOrder;
        this.accountNumber = accountNumber;
        this.orderCost = orderCost;
        this.adminCost = adminCost;
        this.datetimeCreated = datetimeCreated;
        this.datetimeFinished = datetimeFinished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(BigDecimal orderCost) {
        this.orderCost = orderCost;
    }

    public BigDecimal getAdminCost() {
        return adminCost;
    }

    public void setAdminCost(BigDecimal adminCost) {
        this.adminCost = adminCost;
    }

    public Timestamp getDatetimeCreated() {
        return datetimeCreated;
    }

    public void setDatetimeCreated(Timestamp datetimeCreated) {
        this.datetimeCreated = datetimeCreated;
    }

    public Timestamp getDatetimeFinished() {
        return datetimeFinished;
    }

    public void setDatetimeFinished(Timestamp datetimeFinished) {
        this.datetimeFinished = datetimeFinished;
    }
    
    
    
    public void add() {
        Connection connection = DatabaseUtility.getConnection();
        
        String sql = """
            INSERT INTO order_payment (
                id_order, account_number, order_cost, admin_cost
            ) VALUES (
                ?, ?, ?, ?
            )
        """;
        
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);

            // Set the values
            preparedStatement.setInt(1, this.idOrder);
            preparedStatement.setString(2, this.accountNumber);
            preparedStatement.setBigDecimal(3, this.orderCost);
            preparedStatement.setBigDecimal(4, this.adminCost);
            int result = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderPayment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void save() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = """
            UPDATE order_payment SET
                 
                id_order = ?,
                account_number = ?,
                order_cost = ?,
                admin_cost = ?
            
            WHERE id = ?
        """;
        
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            // Set the values
            preparedStatement.setInt(1, this.idOrder);
            preparedStatement.setString(2, this.accountNumber);
            preparedStatement.setBigDecimal(3, this.orderCost);
            preparedStatement.setBigDecimal(4, this.adminCost);
//            preparedStatement.setTimestamp(6, this.datetimeFinished);

            preparedStatement.setInt(5, this.id);

            int result = preparedStatement.executeUpdate();
            if(result == 0) {
                this.add();
            } 
        } catch (SQLException ex) {
            Logger.getLogger(OrderPayment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void delete() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "DELETE FROM order_payment " +   
                "WHERE id = " + this.id;
        
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderPayment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

