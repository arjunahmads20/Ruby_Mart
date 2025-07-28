package app.model;

import java.sql.*;
import app.utility.DatabaseUtility;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;

public class ItemInStore {
    private int id;
    private int idItem;
    private int idStore;
    private int stock = 0;
    private boolean isInSeason = false;
    private BigDecimal nominalDiscount = BigDecimal.valueOf(0);
    private Timestamp datetimeLastUpdated;
    
    
    
    public Map<String, BigDecimal> getTotalProfit(String interval) {
        Item item = ItemManager.getById(this.idItem);
        BigDecimal itemProfit = item.getSellPrice().subtract(item.getBuyPrice());
        
        Map<String, Integer> quantitySoldData = this.getQuantitySold(interval);
        Map<String, BigDecimal> totalProfitData = new HashMap<String, BigDecimal>();
        
        for(var entry: quantitySoldData.entrySet()) {
            totalProfitData.put(entry.getKey(), itemProfit.multiply(BigDecimal.valueOf(entry.getValue())));
        }
        return totalProfitData;
    }
    
    public Map<String, Integer> getQuantitySold(String interval) {
        Connection connection = DatabaseUtility.getConnection();
        String sql;
        String[] interval_split = interval.split("_");
        
        Map<String, Integer> data = new HashMap<String, Integer>();
        try (Statement statement = connection.createStatement()) {
            if (interval_split[2].equals("d")) {
                sql = """
                      SELECT DATE(`order`.datetime_finished) as `date`, SUM(item_in_order.quantity) as quantity_sold
                      FROM `order`, item_in_order
                      WHERE 
                        `order`.status = "finished" AND 
                        `order`.id = item_in_order.id_order AND
                        item_in_order.id_item = """ + this.idItem + " AND `order`.id_store = " + this.idStore + " " + 
                                                
                      """
                      GROUP BY DATE(`order`.datetime_finished)
                      ORDER BY DATE(`order`.datetime_finished) DESC
                      LIMIT
                      """ + interval_split[1];
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    data.put(resultSet.getString("date"), resultSet.getInt("quantity_sold"));
                }
            } else if (interval_split[2].equals("m")) {
                sql = """
                      SELECT YEAR(`order`.datetime_finished) as year, MONTH(`order`.datetime_finished) as month, SUM(item_in_order.quantity) as quantity_sold
                      FROM `order`, item_in_order
                      WHERE 
                        `order`.status = "finished" AND 
                        `order`.id = item_in_order.id_order AND
                        item_in_order.id_item = """ + this.idItem + " AND `order`.id_store = " + this.idStore + " " + 
                        
                      """
                      GROUP BY YEAR(`order`.datetime_finished), MONTH(`order`.datetime_finished)
                      ORDER BY YEAR(`order`.datetime_finished) DESC, MONTH(`order`.datetime_finished) DESC
                      LIMIT
                      """ + interval_split[1];
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    data.put(resultSet.getString("month") + "-" + resultSet.getString("year"), resultSet.getInt("quantity_sold"));
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ItemInStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    public ItemInStore(int idItem, int idStore) {
        this.idItem = idItem;
        this.idStore = idStore;
    }
    public ItemInStore(int id, int idItem, int idStore, int stock, boolean isInSeason, BigDecimal nominalDiscount, Timestamp datetimeLastUpdated) {
        this.id = id;
        this.idItem = idItem;
        this.idStore = idStore;
        this.stock = stock;
        this.isInSeason = isInSeason;
        this.nominalDiscount = nominalDiscount;
        this.datetimeLastUpdated = datetimeLastUpdated;
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

    public int getIdStore() {
        return idStore;
    }

    public void setIdStore(int idStore) {
        this.idStore = idStore;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isIsInSeason() {
        return isInSeason;
    }

    public void setIsInSeason(boolean isInSeason) {
        this.isInSeason = isInSeason;
    }

    public BigDecimal getNominalDiscount() {
        return nominalDiscount;
    }

    public void setNominalDiscount(BigDecimal nominalDiscount) {
        this.nominalDiscount = nominalDiscount;
    }

    public Timestamp getDatetimeLastUpdated() {
        return datetimeLastUpdated;
    }

    public void setDatetimeLastUpdated(Timestamp datetimeLastUpdated) {
        this.datetimeLastUpdated = datetimeLastUpdated;
    }
    
    

    public int getQuantitySold() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT SUM(item_in_order.quantity) AS quantity_sold FROM item_in_order, order WHERE item_in_order.id_order = order.id AND item_in_order.id_item = " + this.idItem + " AND order.id_store = " + this.idStore;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getInt("quantity_sold");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemInStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public void add() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = """
            INSERT INTO item_in_store (
                id_item, id_store, stock, is_in_season, nominal_discount
            ) VALUES (
                ?, ?, ?, ?, ?
            )

        """;
        
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);

            // Set the values
            preparedStatement.setInt(1, this.idItem);
            preparedStatement.setInt(2, this.idStore);
            preparedStatement.setInt(3, this.stock);
            preparedStatement.setBoolean(4, this.isInSeason);
            preparedStatement.setBigDecimal(5, this.nominalDiscount);

            int result = preparedStatement.executeUpdate(); 
        } catch (SQLException ex) {
            Logger.getLogger(ItemInStore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void save() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = """
            UPDATE item_in_store SET 
                id_item = ?,
                id_store = ?,
                stock = ?,
                is_in_season = ?,
                nominal_discount = ?
            
                WHERE id = ?
        """ ;
        
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);

            // Set the values
            preparedStatement.setInt(1, this.idItem);
            preparedStatement.setInt(2, this.idStore);
            preparedStatement.setInt(3, this.stock);
            preparedStatement.setBoolean(4, this.isInSeason);
            preparedStatement.setBigDecimal(5, this.nominalDiscount);

            preparedStatement.setInt(6, this.id);

            int result = preparedStatement.executeUpdate();
            if(result == 0) {
                this.add();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemInStore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void delete() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "DELETE FROM item_in_store " +   
                "WHERE id = " + this.id;
        
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ItemInStore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

