package app.model;

import java.sql.*;
import app.utility.DatabaseUtility;
import app.model.Item;
import app.model.ItemManager;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ItemInOrder {
    private int id = 0;
    private int idItem;
    private int idOrder;
    private int quantity;
    private Timestamp datetimeAdded;
    
    // For backup purpose
    private int idItemCategory;
    private String name;
    private int size;
    private String unit;
    private BigDecimal buyPrice;
    private BigDecimal sellPrice;
    private String description;
    private String timeConsumption;

    public ItemInOrder() {
    }
    public ItemInOrder(ItemInCheckoutCart itemInCheckoutCart) {
        this.id = 0;
        Item item = ItemManager.getById(itemInCheckoutCart.getIdItem());
        this.idItem = itemInCheckoutCart.getIdItem();
        this.quantity = itemInCheckoutCart.getQuantity();
        this.idItemCategory = item.getIdItemCategory();
        this.name = item.getName();
        this.size = item.getSize();
        this.unit = item.getUnit();
        this.buyPrice = item.getBuyPrice();
        this.sellPrice = item.getSellPrice();
        this.description = item.getDescription();
        this.timeConsumption = item.getTimeConsumption();
    }
    public ItemInOrder(int id, int idItem, int idOrder, int quantity, Timestamp datetimeAdded, int idItemCategory, String name, int size, String unit, BigDecimal buyPrice, BigDecimal sellPrice, String description, String timeConsumption) {
        this.id = id;
        this.idItem = idItem;
        this.idOrder = idOrder;
        this.quantity = quantity;
        this.datetimeAdded = datetimeAdded;
        this.idItemCategory = idItemCategory;
        this.name = name;
        this.size = size;
        this.unit = unit;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.description = description;
        this.timeConsumption = timeConsumption;
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

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Timestamp getDatetimeAdded() {
        return datetimeAdded;
    }

    public void setDatetimeAdded(Timestamp datetimeAdded) {
        this.datetimeAdded = datetimeAdded;
    }

    public int getIdItemCategory() {
        return idItemCategory;
    }

    public void setIdItemCategory(int idItemCategory) {
        this.idItemCategory = idItemCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimeConsumption() {
        return timeConsumption;
    }

    public void setTimeConsumption(String timeConsumption) {
        this.timeConsumption = timeConsumption;
    }
    
    
    public BigDecimal getTotalCost() {
        return this.sellPrice.multiply(BigDecimal.valueOf(this.quantity));
    }
    
    
    public void add() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = """
            INSERT INTO item_in_order (
                id_item, id_order, quantity, id_item_category, name, size, unit, buy_price, sell_price, description, time_consumption
            ) VALUES (
                ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?
            )
        """;
        
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);

            // Set the values
            preparedStatement.setInt(1, this.idItem);
            preparedStatement.setInt(2, this.idOrder);
            preparedStatement.setInt(3, this.quantity);

            preparedStatement.setInt(4, this.idItemCategory);
            preparedStatement.setString(5, this.name);
            preparedStatement.setInt(6, this.size);
            preparedStatement.setString(7, this.unit);
            preparedStatement.setBigDecimal(8, this.buyPrice);
            preparedStatement.setBigDecimal(9, this.sellPrice);
            preparedStatement.setString(10, this.description);
            preparedStatement.setString(11, this.timeConsumption);

            int result = preparedStatement.executeUpdate(); 
            if (result == 1) {
                sql = "SELECT MAX(id) as id FROM item_in_order ";

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
            Logger.getLogger(ItemInOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    public void save() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = """
            UPDATE item_in_order SET 
                id_item = ?,
                id_order = ?,
                quantity = ?,
                id_item_category = ?,
                name = ?,
                size = ?,
                unit = ?,
                buy_price = ?,
                sell_price = ?,
                description = ?,
                time_consumption = ?
            
                WHERE id = ?
        """ ;
        
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);

            // Set the values
            preparedStatement.setInt(1, this.idItem);
            preparedStatement.setInt(2, this.idOrder);
            preparedStatement.setInt(3, this.quantity);
//            preparedStatement.setTimestamp(4, this.datetimeAdded);

            preparedStatement.setInt(4, this.idItemCategory);
            preparedStatement.setString(5, this.name);
            preparedStatement.setInt(6, this.size);
            preparedStatement.setString(7, this.unit);
            preparedStatement.setBigDecimal(8, this.buyPrice);
            preparedStatement.setBigDecimal(9, this.sellPrice);
            preparedStatement.setString(10, this.description);
            preparedStatement.setString(11, this.timeConsumption);
            preparedStatement.setInt(12, this.id);

            int result = preparedStatement.executeUpdate();
            if(result == 0) {
                this.add();
            } 
        } catch (SQLException ex) {
            Logger.getLogger(ItemInOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void delete() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "DELETE FROM item_in_order " +   
                "WHERE id = " + this.id;
        
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ItemInOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

