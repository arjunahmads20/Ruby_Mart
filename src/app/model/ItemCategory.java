package app.model;

import java.sql.*;
import app.utility.DatabaseUtility;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ItemCategory {
    private int id;
    private String name;
    private String iconUrl;
    private Timestamp datetimeAdded;
    private Timestamp datetimeLastUpdated;

    public ItemCategory(int id, String name, String iconUrl, Timestamp datetimeAdded, Timestamp datetimeLastUpdated) {
        this.id = id;
        this.name = name;
        this.iconUrl = iconUrl;
        this.datetimeAdded = datetimeAdded;
        this.datetimeLastUpdated = datetimeLastUpdated;
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

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
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
    
    

    public int getItemCount() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT COUNT(*) as item_count FROM item WHERE item.id_item_category = " + this.id;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getInt("item_count");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

}

