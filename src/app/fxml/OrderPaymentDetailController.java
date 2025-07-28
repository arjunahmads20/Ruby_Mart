/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app.fxml;

import app.model.Order;
import app.model.OrderPayment;
import app.model.OrderPaymentManager;
import app.utility.AppUtility;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class OrderPaymentDetailController implements Initializable {

    @FXML
    private Button btnGoBack;
    @FXML
    private Label lblMessage;
    @FXML
    private Label lblIdPayment;
    @FXML
    private Label lblIdOrder;
    @FXML
    private Label lblOrderCost;
    @FXML
    private Label lblAdminCost;
    @FXML
    private Label lblTotalPayCost;
    @FXML
    private Label lblPaymentMethod;
    @FXML
    private Label lblDatetimeCreated;
    @FXML
    private Label lblDatetimeFinished;
    @FXML
    private Button btnPrintOrderEvidence;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Order order = AppUtility.getCurrentSelectedOrder();
        OrderPayment orderPayment = OrderPaymentManager.getByIdOrder(order.getId());
        lblIdOrder.setText(String.valueOf(orderPayment.getId()));
        lblIdOrder.setText(String.valueOf(order.getId()));
        lblOrderCost.setText(String.valueOf(order.getOrderCost()));
        lblAdminCost.setText(String.valueOf(orderPayment.getAdminCost()));
        
        lblPaymentMethod.setText(String.valueOf(order.getPaymentMethod()));
        lblTotalPayCost.setText(String.valueOf(orderPayment.getTotalPayCost()));
        lblDatetimeCreated.setText(String.valueOf(orderPayment.getDatetimeCreated()));
        lblDatetimeFinished.setText(String.valueOf(orderPayment.getDatetimeFinished()));

        lblPaymentMethod.setText(String.valueOf(order.getPaymentMethod()));

    }    

    @FXML
    private void handleGoBack(ActionEvent event) {
        AppUtility.setCurrentUrl("/order");
        AppUtility.mapCurrentUrl();
    }
    
}
