package app.model;

import java.math.BigDecimal;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.utility.DatabaseUtility;
import app.model.ItemInCheckoutCart;
import app.utility.AppUtility;
import java.util.ArrayList;

public class CheckoutCart {
    private int id;
    private int idStore;
    private String name;
    private Timestamp datetimeAdded;

    public CheckoutCart(int id, int idStore, String name, Timestamp datetimeAdded) {
        this.id = id;
        this.idStore = idStore;
        this.name = name;
        this.datetimeAdded = datetimeAdded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdStore() {
        return idStore;
    }

    public void setIdStore(int idStore) {
        this.idStore = idStore;
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

    public BigDecimal getItemCost() {
        BigDecimal itemCost = BigDecimal.valueOf(0);
        // BigDecimal itemBuyCost = BigDecimal.valueOf(0);
        
        ArrayList<ItemInCheckoutCart> itemInCheckoutCartSet = ItemInCheckoutCartManager.getAllByIdCheckoutCart(AppUtility.getCurrentCheckoutCart().getId());
//        System.out.println("itemInCheckoutCartSet size is " + itemInCheckoutCartSet.size());
        for (int i=0; i<itemInCheckoutCartSet.size(); i++) {
            ItemInCheckoutCart itemInCheckoutCart = itemInCheckoutCartSet.get(i);
            
            Item item = ItemManager.getById(itemInCheckoutCart.getIdItem());
            ItemInStore itemInStore = ItemInStoreManager.getByIdItemAndIdStore(item.getId(), AppUtility.getCurrentStore().getId());
            
            // itemBuyCost.add(item.getBuyPrice().multiply(BigDecimal.valueOf(itemInCheckoutCart.getQuantity())));
            itemCost = itemCost.add(item.getSellPrice().subtract(itemInStore.getNominalDiscount()).multiply(BigDecimal.valueOf(itemInCheckoutCart.getQuantity())));

        }
        return itemCost;
    }
    public void deleteAllItemInCheckoutCart() {
       ArrayList<ItemInCheckoutCart> itemInCheckoutCartSet = ItemInCheckoutCartManager.getAllByIdCheckoutCart(AppUtility.getCurrentCheckoutCart().getId());

        for(int i=0; i<itemInCheckoutCartSet.size(); i++) {
                ItemInCheckoutCart itemInCheckoutCart = itemInCheckoutCartSet.get(i);
                itemInCheckoutCart.delete();
        }
    }
    
    public void add() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = """
            INSERT INTO checkout_cart (
                id_store, name
            ) VALUES (
                ?, ?
            )
        """;
        
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);

            // Set the values
            preparedStatement.setInt(1, this.idStore);
            preparedStatement.setString(2, this.name);

            int result = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CheckoutCart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void save() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = """
            UPDATE checkout_cart SET
                 
                id_store = ?,
                name = ?
            
            WHERE id = ?
        """;
        
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            // Set the values
            preparedStatement.setInt(1, this.idStore);
            preparedStatement.setString(2, this.name);
            preparedStatement.setInt(3, this.id);

            int result = preparedStatement.executeUpdate();
            if(result == 0) {
                this.add();
            } 
        } catch (SQLException ex) {
            Logger.getLogger(CheckoutCart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void delete() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "DELETE FROM checkout_cart " +   
                "WHERE id = " + this.id;
        
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CheckoutCart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

