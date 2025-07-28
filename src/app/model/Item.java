package app.model;

import java.sql.*;
import app.utility.DatabaseUtility;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;


public class Item {
    private int id = 0;
    private int idItemCategory;
    private String name;
    private int size;
    private String unit;
    private BigDecimal buyPrice;
    private BigDecimal sellPrice;
    private String description;
    private String pictureUrl;
    private String timeConsumption;
    private Timestamp datetimeAdded;
    private Timestamp datetimeLastUpdated;
    
    public Map<String, BigDecimal> getTotalProfit(String interval) {
        BigDecimal itemProfit = this.getSellPrice().subtract(this.getBuyPrice());
        
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
        
        Map<String, Integer> data = new HashMap<>();
        try (Statement statement = connection.createStatement()) {
            if (interval_split[2].equals("d")) {
                sql = """
                      SELECT DATE(`order`.datetime_finished) as `date`, SUM(item_in_order.quantity) as quantity_sold
                      FROM `order`, item_in_order
                      WHERE 
                        `order`.status = "finished" AND 
                        `order`.id = item_in_order.id_order AND
                        item_in_order.id_item = """ + this.id + " " + 
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
                        item_in_order.id_item = """ + this.id + " " + 
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
            Logger.getLogger(Item.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
    public Item() {
    }
    public Item(int id, int idItemCategory, String name, int size, String unit, BigDecimal buyPrice, BigDecimal sellPrice, String description, String pictureUrl, String timeConsumption, Timestamp datetimeAdded, Timestamp datetimeLastUpdated) {
        this.id = id;
        this.idItemCategory = idItemCategory;
        this.name = name;
        this.size = size;
        this.unit = unit;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.description = description;
        this.pictureUrl = pictureUrl;
        this.timeConsumption = timeConsumption;
        this.datetimeAdded = datetimeAdded;
        this.datetimeLastUpdated = datetimeLastUpdated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getTimeConsumption() {
        return timeConsumption;
    }

    public void setTimeConsumption(String timeConsumption) {
        this.timeConsumption = timeConsumption;
    }

    public Timestamp getDatetimeAdded() {
        return datetimeAdded;
    }

    public void setDatetimeAdded(Timestamp datetimeAdded) {
        this.datetimeAdded = datetimeAdded;
    }

    public Timestamp getDatetimeLastUpdated() {
        return datetimeLastUpdated;
    }

    public void setDatetimeLastUpdated(Timestamp datetimeLastUpdated) {
        this.datetimeLastUpdated = datetimeLastUpdated;
    }

    
    public void add() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = """
            INSERT INTO item (
                id_item_category, name, size, unit, buy_price, sell_price, description, time_consumption
            ) VALUES (
                ?, ?, ?, ?, ?, ?, ?, ?
            )
        """;
        
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);

            // Set the values
            preparedStatement.setInt(1, this.idItemCategory);
            preparedStatement.setString(2, this.name);
            preparedStatement.setInt(3, this.size);
            preparedStatement.setString(4, this.unit);
            preparedStatement.setBigDecimal(5, this.buyPrice);
            preparedStatement.setBigDecimal(6, this.sellPrice);
            preparedStatement.setString(7, this.description);
            preparedStatement.setString(8, this.timeConsumption);

            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                sql = "SELECT MAX(id) as id FROM item ";

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
            Logger.getLogger(Item.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void save() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = """
            UPDATE item SET 
            
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
            preparedStatement.setInt(1, this.idItemCategory);
            preparedStatement.setString(2, this.name);
            preparedStatement.setInt(3, this.size);
            preparedStatement.setString(4, this.unit);
            preparedStatement.setBigDecimal(5, this.buyPrice);
            preparedStatement.setBigDecimal(6, this.sellPrice);
            preparedStatement.setString(7, this.description);
            preparedStatement.setString(8, this.timeConsumption);

            preparedStatement.setInt(9, this.id);

            int result = preparedStatement.executeUpdate();
            if(result == 0) {
                this.add();
            }
        } catch (SQLException ex) {
            this.add();
            Logger.getLogger(Item.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void delete() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "DELETE FROM item " +   
                "WHERE id = " + this.id;
        
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Item.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

