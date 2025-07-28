/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app.fxml;

import app.model.Order;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class HbxOrderRowController implements Initializable {

    @FXML
    private Label lblOrderIdOrder;
    @FXML
    private Label lblOrderDatetimeCreated;
    @FXML
    private Label lblOrderDatetimeFinished;
    @FXML
    private Label lblOrderStatus;
    @FXML
    private Label lblOrderTotalPayCost;

    public void setInfo(Order order) {
        lblOrderIdOrder.setText(String.valueOf(order.getId()));
        lblOrderDatetimeCreated.setText(String.valueOf(order.getDatetimeCreated()));
        lblOrderDatetimeFinished.setText(String.valueOf(order.getDatetimeFinished()));
        lblOrderStatus.setText(order.getStatus());
        lblOrderTotalPayCost.setText(String.valueOf(order.getTotalPayCost()));
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
