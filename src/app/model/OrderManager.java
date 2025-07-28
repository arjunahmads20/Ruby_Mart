
package app.model;

import java.util.ArrayList;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;

import app.model.Store;
import app.model.Order;
import app.utility.DatabaseUtility;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class OrderManager {
    
    public static Map<String, BigDecimal> getTotalProfit(String interval, int idStore) {
        Connection connection = DatabaseUtility.getConnection();
        String sql;
        String[] interval_split = interval.split("_");
        
        Map<String, BigDecimal> data = new HashMap<>();
        try (Statement statement = connection.createStatement()) {
            if (interval_split[2].equals("d")) {
                sql = """
                      SELECT DATE(`order`.datetime_finished) as `date`, SUM(`order`.item_buy_cost) as sum_item_buy_cost, SUM(`order`.item_cost) as sum_item_cost
                      FROM `order`
                      WHERE `order`.status = "finished" AND `order`.id_store = """ + idStore + " " + 
                      """
                      GROUP BY DATE(`order`.datetime_finished)
                      ORDER BY DATE(`order`.datetime_finished) DESC
                      LIMIT
                      """ + interval_split[1];
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    data.put(resultSet.getString("date"), resultSet.getBigDecimal("sum_item_cost").subtract(resultSet.getBigDecimal("sum_item_buy_cost")));
                }
            } else if (interval_split[2].equals("m")) {
                sql = """
                      SELECT YEAR(`order`.datetime_finished) as year, MONTH(`order`.datetime_finished) as month, SUM(`order`.item_buy_cost) as sum_item_buy_cost, SUM(`order`.item_cost) as sum_item_cost
                      FROM `order`
                      WHERE `order`.status = "finished" AND `order`.id_store = """ + idStore + " " + 
                      """
                      GROUP BY YEAR(`order`.datetime_finished), MONTH(`order`.datetime_finished)
                      ORDER BY YEAR(`order`.datetime_finished) DESC, MONTH(`order`.datetime_finished) DESC
                      LIMIT
                      """ + interval_split[1];
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    data.put(resultSet.getString("month") + "-" + resultSet.getString("year"), resultSet.getBigDecimal("sum_item_cost").subtract(resultSet.getBigDecimal("sum_item_buy_cost")));
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(OrderManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    public static Map<String, Integer> getQuantityFinished(String interval, int idStore) {
        Connection connection = DatabaseUtility.getConnection();
        String sql;
        String[] interval_split = interval.split("_");
        
        Map<String, Integer> data = new HashMap<>();
        try (Statement statement = connection.createStatement()) {
            if (interval_split[2].equals("d")) {
                sql = """
                      SELECT DATE(`order`.datetime_finished) as `date`, COUNT(*) as quantity_finished
                      FROM `order`
                      WHERE `order`.status = "finished" AND `order`.id_store = """ + idStore + """
                       GROUP BY DATE(`order`.datetime_finished)
                      ORDER BY DATE(`order`.datetime_finished) DESC
                      LIMIT
                       """ + interval_split[1];
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    data.put(resultSet.getString("date"), resultSet.getInt("quantity_finished"));
                }
            } else if (interval_split[2].equals("m")) {
                sql = """
                      SELECT YEAR(`order`.datetime_finished) as year, MONTH(`order`.datetime_finished) as month, COUNT(*) as quantity_finished
                      FROM `order`
                      WHERE `order`.status = "finished" AND `order`.id_store = """ + idStore + """
                       GROUP BY YEAR(`order`.datetime_finished), MONTH(`order`.datetime_finished)
                      ORDER BY YEAR(`order`.datetime_finished) DESC, MONTH(`order`.datetime_finished) DESC
                      LIMIT
                       """ + interval_split[1];
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    data.put(resultSet.getString("month") + "-" + resultSet.getString("year"), resultSet.getInt("quantity_finished"));
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(OrderManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
    public static ArrayList<Order> getAll() {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM `order`";
        ArrayList<Order> orderSet = new ArrayList<Order>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Order order = new Order(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_customer"), 
                        resultSet.getInt("id_cashier"), 
                        resultSet.getInt("id_store"),
                        resultSet.getInt("id_voucher_order"), 
                        resultSet.getBigDecimal("item_buy_cost"), 
                        resultSet.getBigDecimal("item_cost"), 
                        resultSet.getBigDecimal("nominal_discount"), 
                        resultSet.getString("payment_method"), 
                        resultSet.getString("status"), 
                        resultSet.getTimestamp("datetime_created"), 
                        resultSet.getTimestamp("datetime_finished")

                );
                orderSet.add(order);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orderSet;
    }
    public static Order getById(int idOrder) {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM `order` WHERE id = " + idOrder;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                Order order = new Order(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_customer"), 
                        resultSet.getInt("id_cashier"), 
                        resultSet.getInt("id_store"),
                        resultSet.getInt("id_voucher_order"), 
                        resultSet.getBigDecimal("item_buy_cost"), 
                        resultSet.getBigDecimal("item_cost"), 
                        resultSet.getBigDecimal("nominal_discount"), 
                        resultSet.getString("payment_method"), 
                        resultSet.getString("status"), 
                        resultSet.getTimestamp("datetime_created"), 
                        resultSet.getTimestamp("datetime_finished")
                );
                return order;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public static ArrayList<Order> getByFilter(String filter) {
        Connection connection = DatabaseUtility.getConnection();
        String sql = "SELECT * FROM `order` WHERE " + filter;
        ArrayList<Order> orderSet = new ArrayList<Order>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Order order = new Order(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_customer"), 
                        resultSet.getInt("id_cashier"), 
                        resultSet.getInt("id_store"),
                        resultSet.getInt("id_voucher_order"), 
                        resultSet.getBigDecimal("item_buy_cost"), 
                        resultSet.getBigDecimal("item_cost"), 
                        resultSet.getBigDecimal("nominal_discount"), 
                        resultSet.getString("payment_method"), 
                        resultSet.getString("status"), 
                        resultSet.getTimestamp("datetime_created"), 
                        resultSet.getTimestamp("datetime_finished")           
                );
                orderSet.add(order);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orderSet;
    }
}
