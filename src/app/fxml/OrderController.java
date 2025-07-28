/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app.fxml;

import app.Main;
import app.model.ItemInCheckoutCart;
import app.model.ItemInCheckoutCartManager;
import app.model.ItemInOrder;
import app.model.ItemInOrderManager;
import app.model.Order;
import app.model.OrderManager;
import app.utility.AppUtility;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class OrderController implements Initializable {

    @FXML
    private Label lblStoreName;
    @FXML
    private Label lblStoreAddress;
    @FXML
    private Label lblUserUsername;
    @FXML
    private Label lblUserRole;
    @FXML
    private TextField txfSearchOrderBar;
    @FXML
    private Button btnSearchOrder;
    @FXML
    private VBox vbxOrderList;
    @FXML
    private Button btnAddOrder;
    @FXML
    private Button btnEditSelectedOrder;
    @FXML
    private Button btnSaveSelectedOrder;
    @FXML
    private Button btnDeleteSelectedOrder;
    @FXML
    private Label lblSelectedOrderIdOrder;
    @FXML
    private Label lblSelectedOrderDatetimeCreated;
    @FXML
    private Label lblSelectedOrderDatetimeFinished;
    @FXML
    private Label lblSelectedOrderStatus;
    @FXML
    private Label lblSelectedOrderTotalItemClassQuantity;
    @FXML
    private Label lblSelectedOrderTotalItemQuantity;
    @FXML
    private Label lblSelectedOrderTotalItemCost;
    @FXML
    private Label lblSelectedOrderPaymentMethod;
    @FXML
    private Label lblSelectedOrderNominalDiscount;
    @FXML
    private Label lblSelectedOrderTotalPayCost;
    @FXML
    private Button btnSelectedOrderPaymentDetail;
    @FXML
    private Button btnOrderStatistic;
    @FXML
    private Button btnItemList;
    @FXML
    private AnchorPane acpOrderCart;
    @FXML
    private Button btnToggleOrderCart;
    @FXML
    private Label lblIdOrder;
    @FXML
    private VBox vbxItemInOrderList;
    @FXML
    private Label lblSelectedOrderTotalItemCostInCart;

    
    
    public void setSelectedOrder(Order order) {
        AppUtility.setCurrentSelectedOrder(order);
        
        
        lblSelectedOrderIdOrder.setText(String.valueOf(order.getId()));
        lblSelectedOrderDatetimeCreated.setText(String.valueOf(order.getDatetimeCreated()));
        lblSelectedOrderDatetimeFinished.setText(String.valueOf(order.getDatetimeFinished()));
        lblSelectedOrderStatus.setText(String.valueOf(order.getStatus()));
       
        lblSelectedOrderTotalItemClassQuantity.setText(String.valueOf(order.getTotalItemClassQuantity()));
        lblSelectedOrderTotalItemQuantity.setText(String.valueOf(order.getTotalItemQuantity()));
        lblSelectedOrderTotalItemCost.setText(String.valueOf(order.getItemCost()));
        lblSelectedOrderPaymentMethod.setText(order.getPaymentMethod());
        lblSelectedOrderNominalDiscount.setText(String.valueOf(order.getNominalDiscount()));
        lblSelectedOrderTotalPayCost.setText(String.valueOf(order.getTotalPayCost()));;
        
        
        Node headerNode = vbxItemInOrderList.getChildren().getFirst();
        vbxItemInOrderList.getChildren().clear();
        vbxItemInOrderList.getChildren().add(headerNode);
        
        // Load all items in the order
        ArrayList<ItemInOrder> itemInOrderSet = ItemInOrderManager.getAllByIdOrder(AppUtility.getCurrentSelectedOrder().getId());
        try {
            for(int i=0; i<itemInOrderSet.size(); i++) {
                
                    ItemInOrder itemInOrder = itemInOrderSet.get(i);
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Main.class.getResource("fxml/HbxItemInOrderRow.fxml"));
                    Node node = loader.load();

                    HbxItemInOrderRowController controller = loader.getController();
                    controller.setInfo(itemInOrder);
                    // add row to the vbox
                    vbxItemInOrderList.getChildren().add(node);

            }
        } catch (IOException ex) {
            Logger.getLogger(SelectStoreController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Set the total item cost
        lblSelectedOrderTotalItemCostInCart.setText(String.valueOf(AppUtility.getCurrentSelectedOrder().getItemCost()));

    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // Load all orders
        ArrayList<Order> orderSet = OrderManager.getAll();
        try {
            for(int i=0; i<orderSet.size(); i++) {
                
                    Order order = orderSet.get(i);
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Main.class.getResource("fxml/HbxOrderRow.fxml"));
                    Node node = loader.load();

                    HbxOrderRowController controller = loader.getController();
                    controller.setInfo(order);
                    // add row to the vbox
                    vbxOrderList.getChildren().add(node);
                    node.setOnMousePressed(evt -> {
                        
                        setSelectedOrder(order);
                    });
            }
        } catch (IOException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Order catalog feature
        btnSearchOrder.setOnAction(evt -> {
            String keyword = txfSearchOrderBar.getText();
            int idOrder = Integer.parseInt(keyword);
            ArrayList<Order> orderSearchSet = OrderManager.getByFilter("id = " + idOrder);
            
            Node headerNode = vbxOrderList.getChildren().getFirst();
            vbxOrderList.getChildren().clear();
            vbxOrderList.getChildren().add(headerNode);
            
            try {
                for(int i=0; i<orderSearchSet.size(); i++) {

                        Order order = orderSearchSet.get(i);
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(Main.class.getResource("fxml/HbxOrderRow.fxml"));
                        Node node = loader.load();

                        HbxOrderRowController controller = loader.getController();
                        controller.setInfo(order);
                        // add row to the vbox
                        vbxOrderList.getChildren().add(node);
                        node.setOnMousePressed(evtSearch -> {
                            setSelectedOrder(order);
                        });
                }
            } catch (IOException ex) {
                Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
        
        btnAddOrder.setOnAction(evt -> {
            AppUtility.setCurrentUrl("/item");
            AppUtility.mapCurrentUrl();
        });
        
        
        if (AppUtility.getCurrentRole().equals("cashier")) {
            btnEditSelectedOrder.setVisible(false);
        } // The edit selected order functionality is only appear when the user role is admin
        else if (AppUtility.getCurrentRole().equals("admin")) {
            
            btnEditSelectedOrder.setOnAction(evt -> {
                if (btnEditSelectedOrder.getText().equals("Edit")) {
                    btnEditSelectedOrder.setText("Cancel");
                    btnSaveSelectedOrder.setVisible(true);
                    btnDeleteSelectedOrder.setVisible(true);
                    
                } else if (btnEditSelectedOrder.getText().equals("Cancel")) {
                    setSelectedOrder(AppUtility.getCurrentSelectedOrder());
                    btnSaveSelectedOrder.setVisible(false);
                    btnDeleteSelectedOrder.setVisible(false);
                    
                    AppUtility.mapCurrentUrl();
                }
                
            });
            btnSaveSelectedOrder.setOnAction(evt -> {
                
                AppUtility.mapCurrentUrl();
            });
            btnDeleteSelectedOrder.setOnAction(evt -> {
                
                AppUtility.getCurrentSelectedOrder().delete();
                // Reset the current selected item in order to prevent error caused by null reference to current selected item
                AppUtility.setCurrentSelectedOrder(OrderManager.getById(1));
                AppUtility.mapCurrentUrl();
            });
        }
        
        btnSelectedOrderPaymentDetail.setOnAction(evt -> {
            AppUtility.setCurrentUrl("/order/" + AppUtility.getCurrentSelectedOrder().getId() + "/payment-detail");
            AppUtility.mapCurrentUrl();
        });
        
        btnItemList.setOnAction(evt -> {
            AppUtility.setCurrentUrl("/item");
            AppUtility.mapCurrentUrl();
        });
        btnOrderStatistic.setOnAction(evt -> {
            AppUtility.setCurrentUrl("/order/statistic");
            AppUtility.mapCurrentUrl();
        });
        
        btnToggleOrderCart.setOnAction(evt -> {
            if (AppUtility.getCurrentCheckoutCartState().equals("hide")) {
                AppUtility.setCurrentCheckoutCartState("show");
                btnToggleOrderCart.setText("Hide");
                acpOrderCart.setTranslateY(0);
            } else {
                AppUtility.setCurrentCheckoutCartState("hide");
                btnToggleOrderCart.setText("Show");
                acpOrderCart.setTranslateY(308);
            }
        });
        
        // Prevent error caused by null reference to AppUtility.getCurrentSelectedOrder() 
        if(AppUtility.getCurrentSelectedOrder() == null) {
            return;
        }
        
        // Load all items in the order
        ArrayList<ItemInOrder> itemInOrderSet = ItemInOrderManager.getAllByIdOrder(AppUtility.getCurrentSelectedOrder().getId());
        try {
            for(int i=0; i<itemInOrderSet.size(); i++) {
                
                    ItemInOrder itemInOrder = itemInOrderSet.get(i);
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Main.class.getResource("fxml/HbxItemInOrderRow.fxml"));
                    Node node = loader.load();

                    HbxItemInOrderRowController controller = loader.getController();
                    controller.setInfo(itemInOrder);
                    // add row to the vbox
                    vbxItemInOrderList.getChildren().add(node);

            }
        } catch (IOException ex) {
            Logger.getLogger(SelectStoreController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Set the total item cost
        lblSelectedOrderTotalItemCostInCart.setText(String.valueOf(AppUtility.getCurrentSelectedOrder().getItemCost()));

    }    
    
}
