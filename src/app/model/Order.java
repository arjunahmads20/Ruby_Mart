package app.model;

import java.sql.*;
import app.utility.DatabaseUtility;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Order {
    private int id = 0;
    private int idCustomer = 0;
    private int idCashier;
    private int idStore;
    private int idVoucherOrder = 0;
    private BigDecimal itemBuyCost;
    private BigDecimal itemCost;
    private BigDecimal nominalDiscount = BigDecimal.valueOf(0);
    private String paymentMethod;
    private String status = "wait-for-payment";
    private Timestamp datetimeCreated;
    private Timestamp datetimeFinished;
    
    public BigDecimal getOrderCost() {
        return this.itemCost.subtract(this.nominalDiscount);
    }
    public BigDecimal getTotalPayCost() {
        BigDecimal totalPayCost = OrderPaymentManager.getByIdOrder(this.id).getTotalPayCost();
        return totalPayCost;
    }
    public int getTotalItemClassQuantity() {
        ArrayList<ItemInOrder> itemInOrderSet = ItemInOrderManager.getAllByIdOrder(this.id);
        int totalItemClassQuantity = itemInOrderSet.size();
        return totalItemClassQuantity;
    }
    public int getTotalItemQuantity() {
        int totalItemQuantity = 0;
        ArrayList<ItemInOrder> itemInOrderSet = ItemInOrderManager.getAllByIdOrder(this.id);
        for(int i=0; i<itemInOrderSet.size(); i++) {
                ItemInOrder itemInOrder = itemInOrderSet.get(i);
                totalItemQuantity += itemInOrder.getQuantity();
        }
        return totalItemQuantity;
    }
    
    
    
    public Order() {
    }
    public Order(int id, int idCustomer, int idCashier, int idStore, int idVoucherOrder, BigDecimal itemBuyCost, BigDecimal itemCost, BigDecimal nominalDiscount, String paymentMethod, String status, Timestamp datetimeCreated, Timestamp datetimeFinished) {
        this.id = id;
        this.idCustomer = idCustomer;
        this.idCashier = idCashier;
        this.idStore = idStore;
        this.idVoucherOrder = idVoucherOrder;
        this.itemBuyCost = itemBuyCost;
        this.itemCost = itemCost;
        this.nominalDiscount = nominalDiscount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.datetimeCreated = datetimeCreated;
        this.datetimeFinished = datetimeFinished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public int getIdCashier() {
        return idCashier;
    }

    public void setIdCashier(int idCashier) {
        this.idCashier = idCashier;
    }

    public int getIdStore() {
        return idStore;
    }

    public void setIdStore(int idStore) {
        this.idStore = idStore;
    }

    public int getIdVoucherOrder() {
        return idVoucherOrder;
    }

    public void setIdVoucherOrder(int idVoucherOrder) {
        this.idVoucherOrder = idVoucherOrder;
    }

    public BigDecimal getItemBuyCost() {
        return itemBuyCost;
    }

    public void setItemBuyCost(BigDecimal itemBuyCost) {
        this.itemBuyCost = itemBuyCost;
    }

    public BigDecimal getItemCost() {
        return itemCost;
    }

    public void setItemCost(BigDecimal itemCost) {
        this.itemCost = itemCost;
    }

    public BigDecimal getNominalDiscount() {
        return nominalDiscount;
    }

    public void setNominalDiscount(BigDecimal nominalDiscount) {
        this.nominalDiscount = nominalDiscount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
            INSERT INTO `order` (
                id_customer, id_cashier, id_store, id_voucher_order, item_buy_cost, item_cost, nominal_discount, payment_method, `status`
            ) VALUES (
                ?, ?, ?, ?, ?, ?, ?, ?, ?
            )
        """;
        
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);

            // Set the values
            preparedStatement.setInt(1, this.idCustomer);
            preparedStatement.setInt(2, this.idCashier);
            preparedStatement.setInt(3, this.idStore);
            preparedStatement.setInt(4, this.idVoucherOrder);
            preparedStatement.setBigDecimal(5, this.itemBuyCost);
            preparedStatement.setBigDecimal(6, this.itemCost);
            preparedStatement.setBigDecimal(7, this.nominalDiscount);
            preparedStatement.setString(8, this.paymentMethod);
            preparedStatement.setString(9, this.status);
//            preparedStatement.setTimestamp(10, this.datetimeCreated);
//            preparedStatement.setTimestamp(10, this.datetimeFinished);

            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                sql = "SELECT MAX(id) as id FROM `order` ";

                Statement statement = connection.createStatement();
                int id = 0;
                try {
                    ResultSet resultSet = statement.executeQuery(sql);
                    if (resultSet.next()) {
                        id = resultSet.getInt("id");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Order.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.id = id;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Order.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void save() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = """
            UPDATE `order` SET
                 
                id_customer = ?,
                id_cashier = ?,
                id_store = ?,
                id_voucher_order = ?,
                item_buy_cost = ?,
                item_cost = ?,
                nominal_discount = ?,
                payment_method = ?,
                `status` = ?,
                datetime_finished = ?
            
            WHERE id = ?
        """;
        
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            // Set the values
            preparedStatement.setInt(1, this.idCustomer);
            preparedStatement.setInt(2, this.idCashier);
            preparedStatement.setInt(3, this.idStore);
            preparedStatement.setInt(4, this.idVoucherOrder);
            preparedStatement.setBigDecimal(5, this.itemBuyCost);
            preparedStatement.setBigDecimal(6, this.itemCost);
            preparedStatement.setBigDecimal(7, this.nominalDiscount);
            preparedStatement.setString(8, this.paymentMethod);
            preparedStatement.setString(9, this.status);
            preparedStatement.setTimestamp(10, this.datetimeFinished);
            
            preparedStatement.setInt(11, this.id);

            int result = preparedStatement.executeUpdate();
            if(result == 0) {
                this.add();
            } 
        } catch (SQLException ex) {
            Logger.getLogger(Order.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void delete() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "DELETE FROM `order` " +   
                "WHERE id = " + this.id;
        
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Order.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

