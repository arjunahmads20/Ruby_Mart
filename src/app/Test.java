/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import app.utility.DatabaseUtility;
import static javafx.application.Application.launch;

import app.model.Item;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;

public class Test {
    public static void main(String[] args) {
        Item i;
        
        
                /*
                    Testing Query:
        
                      SELECT DATE(`order`.datetime_finished) as `date`, SUM(`order`.item_buy_cost) as sum_item_buy_cost, SUM(`order`.item_cost) as sum_item_cost
                      FROM `order`
                      WHERE `order`.status = "finished" AND `order`.id_store = 1
                      
                      GROUP BY DATE(`order`.datetime_finished)
                      ORDER BY DATE(`order`.datetime_finished) DESC
                      LIMIT 7;
        
        
                      
                      SELECT DATE(`order`.datetime_finished) as `date`, SUM(item_in_order.quantity) as quantity_sold
                      FROM `order`, item_in_order
                      WHERE 
                        `order`.status = "finished" AND 
                        `order`.id = item_in_order.id_order AND
                        item_in_order.id_item = 1 
                      GROUP BY DATE(`order`.datetime_finished)
                      ORDER BY DATE(`order`.datetime_finished) DESC
                      LIMIT 7;
                      
                */
    }
}
