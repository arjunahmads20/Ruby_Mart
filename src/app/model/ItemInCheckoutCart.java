package app.model;

import java.sql.*;
import app.utility.DatabaseUtility;
import app.model.Item;
import app.model.ItemManager;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ItemInCheckoutCart {
    private int id = 0;
    private int idItem;
    private int idCheckoutCart;
    private int quantity;
    private Timestamp datetimeAdded;

    public ItemInCheckoutCart() {
    }
    public ItemInCheckoutCart(Item item, CheckoutCart checkoutCart) {
        this.idItem = item.getId();
        this.idCheckoutCart = checkoutCart.getId();
        this.quantity = 1;
    }
    public ItemInCheckoutCart(int id, int idItem, int idCheckoutCart, int quantity, Timestamp datetimeAdded) {
        this.id = id;
        this.idItem = idItem;
        this.idCheckoutCart = idCheckoutCart;
        this.quantity = quantity;
        this.datetimeAdded = datetimeAdded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public int getIdCheckoutCart() {
        return idCheckoutCart;
    }

    public void setIdCheckoutCart(int idCheckoutCart) {
        this.idCheckoutCart = idCheckoutCart;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public void increaseQuantity(int n) {
        this.quantity += n;
    }
    
    public void decreaseQuantity(int n) {
        this.quantity -= n;
    }


    public Timestamp getDatetimeAdded() {
        return datetimeAdded;
    }

    public void setDatetimeAdded(Timestamp datetimeAdded) {
        this.datetimeAdded = datetimeAdded;
    }
    

    public BigDecimal getTotalCost() {
        Item item; 
        item = ItemManager.getById(this.idItem);
        if (item != null) {
            return item.getSellPrice().multiply(BigDecimal.valueOf( this.quantity));
        } else {
            return BigDecimal.valueOf(0);
        }
    }

    public void add() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = """
            INSERT INTO item_in_checkout_cart (
                id_item, id_checkout_cart, quantity
            ) VALUES (
                ?, ?, ?
            )

        """;
        
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);

            // Set the values
            preparedStatement.setInt(1, this.idItem);
            preparedStatement.setInt(2, this.idCheckoutCart);
            preparedStatement.setInt(3, this.quantity);

            int result = preparedStatement.executeUpdate(); 
            if (result == 1) {
                sql = "SELECT MAX(id) as id FROM item_in_checkout_cart ";

                Statement statement = connection.createStatement();
                try {
                    ResultSet resultSet = statement.executeQuery(sql);
                    if (resultSet.next()) {
                        this.id = resultSet.getInt("id");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Order.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemInCheckoutCart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void save() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = """
            UPDATE item_in_checkout_cart SET 
                id_item = ?,
                id_checkout_cart = ?,
                quantity = ?
            
                WHERE id = ?
        """ ;
        
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);

            // Set the values
            preparedStatement.setInt(1, this.idItem);
            preparedStatement.setInt(2, this.idCheckoutCart);
            preparedStatement.setInt(3, this.quantity);

            preparedStatement.setInt(4, this.id);

            int result = preparedStatement.executeUpdate();
            if(result == 0) {
                this.add();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemInCheckoutCart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void delete() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "DELETE FROM item_in_checkout_cart " +   
                "WHERE id = " + this.id;
        
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ItemInCheckoutCart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

