/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app.fxml;


import app.exception.ItemIsOutOfStockException;

import app.model.Cashier;
import app.model.CashierManager;
import app.model.CheckoutCart;
import app.model.Customer;
import app.model.CustomerManager;
import app.model.Item;
import app.model.ItemInCheckoutCart;
import app.model.ItemInCheckoutCartManager;
import app.model.ItemInOrder;
import app.model.ItemInStore;
import app.model.ItemInStoreManager;
import app.model.ItemManager;
import app.model.Order;
import app.model.OrderPayment;
import app.model.OrderPaymentManager;
import app.model.User;
import app.model.VoucherOrder;
import app.model.VoucherOrderManager;
import app.utility.AppUtility;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class CheckoutOrderController implements Initializable {

    @FXML
    private Button btnGoBack;
    @FXML
    private Label lblMessage;
    @FXML
    private Label lblIdCheckoutCart;
    @FXML
    private Label lblIdCashier;
    @FXML
    private Label lblCashierUsername;
    @FXML
    private TextField txfIdCustomer;
    @FXML
    private Button btnCheckIdCustomer;  
    @FXML
    private TextField txfVoucherCode;
    @FXML
    private Button btnCheckVoucherCode;  
    private int idVoucherOrder = 0; 
    @FXML
    private ChoiceBox<String> cbxPaymentMethod;
    @FXML
    private Label lblTotalItemCost;
    private BigDecimal totalItemCost = BigDecimal.ZERO; 
    @FXML
    private Label lblNominalDiscount;    
    private BigDecimal nominalDiscount = BigDecimal.ZERO; 
    @FXML
    private Label lblAdminCost;
    private BigDecimal adminCost = BigDecimal.ZERO; 
    @FXML
    private Label lblTotalPayCost;  
    private BigDecimal totalPayCost = BigDecimal.ZERO; 
    @FXML
    private Button btnCreateOrder;

    int idCustomer = 0;
    
    User user = AppUtility.getCurrentUser();
    // The default value for idCashier is the id of the first cashier in the current store
    Cashier cashier = CashierManager.getByIdStore(AppUtility.getCurrentStore().getId());
    int idCashier = cashier.getId();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        String[] paymentMethodSet = {"Cash", "Qris", "Handpalm"};
        cbxPaymentMethod.getItems().setAll(paymentMethodSet);
        cbxPaymentMethod.setValue("cash");

        if (user.getRole().equals("cashier")) {
            idCashier = CashierManager.getByIdUser(user.getId()).getId();
            lblIdCashier.setText(String.valueOf(cashier.getId()));
            lblCashierUsername.setText(AppUtility.getCurrentUser().getUsername());
        }
        lblIdCheckoutCart.setText(String.valueOf(AppUtility.getCurrentCheckoutCart().getId()));
       
        totalItemCost = AppUtility.getCurrentCheckoutCart().getItemCost();
        lblTotalItemCost.setText(String.valueOf(totalItemCost));
        
        totalPayCost = totalItemCost.subtract(nominalDiscount).add(adminCost);
        lblTotalPayCost.setText(String.valueOf(totalPayCost));
        
        btnCheckIdCustomer.setOnAction(evt-> {
            Customer customer = CustomerManager.getById(Integer.parseInt(txfIdCustomer.getText()));
            if (customer != null) {
                idCustomer = customer.getId();
//                txfIdCustomer.setStyle("-fx-backgorund-color: green;");
            } else {
                lblMessage.setText("Invalid customer id");
            }
        });
        btnCheckVoucherCode.setOnAction(evt-> {
            VoucherOrder voucherOrder = VoucherOrderManager.getByCode(txfVoucherCode.getText());
            if (voucherOrder != null) {
                idVoucherOrder = voucherOrder.getId();                

                nominalDiscount = voucherOrder.getNominalDiscount();
                lblNominalDiscount.setText(String.valueOf(nominalDiscount));

                totalPayCost = totalItemCost.subtract(nominalDiscount).add(adminCost);
                lblTotalPayCost.setText(String.valueOf(totalPayCost));
        
            }
        });
        btnCreateOrder.setOnAction(evt -> {
            Order order = new Order();
            
            String paymentMethod = cbxPaymentMethod.getValue();
            if(paymentMethod.equals("cash")) {
                order.setStatus("finished");
                order.setDatetimeFinished(Timestamp.valueOf(LocalDateTime.now()));
            } else {
                // Untuk sekarang yang tersedia hanyalah metode pembayaran cash
                cbxPaymentMethod.setValue("cash");
                order.setStatus("finished");
                order.setDatetimeFinished(Timestamp.valueOf(LocalDateTime.now()));
            }
            
            order.setIdCustomer(idCustomer);
            order.setIdCashier(idCashier);
            order.setIdStore(AppUtility.getCurrentStore().getId());
            order.setIdVoucherOrder(idVoucherOrder);
            order.setPaymentMethod(paymentMethod);
            
            
            BigDecimal itemCost = BigDecimal.valueOf(0);
            BigDecimal itemBuyCost = BigDecimal.valueOf(0);
            
            order.setItemCost(itemCost);
            order.setItemBuyCost(itemBuyCost);
            order.setNominalDiscount(nominalDiscount);
            order.save();
            
            ArrayList<ItemInCheckoutCart> itemInCheckoutCartSet = ItemInCheckoutCartManager.getAllByIdCheckoutCart(AppUtility.getCurrentCheckoutCart().getId());
            for (int i=0; i<itemInCheckoutCartSet.size(); i++) {
                ItemInCheckoutCart itemInCheckoutCart = itemInCheckoutCartSet.get(i);
                
                Item item = ItemManager.getById(itemInCheckoutCart.getIdItem());
                ItemInStore itemInStore = ItemInStoreManager.getByIdItemAndIdStore(item.getId(), AppUtility.getCurrentStore().getId());
                
                if(itemInCheckoutCart.getQuantity() > itemInStore.getStock()) {
                    String message = "Quantity for item '" + item.getName() + "' is out of stock (current stock: " + itemInStore.getStock() + ")";
                    lblMessage.setText(message);
                    order.delete();
                    throw new ItemIsOutOfStockException(message);
                }
                
                itemBuyCost = itemBuyCost.add(item.getBuyPrice().multiply(BigDecimal.valueOf(itemInCheckoutCart.getQuantity())));
                itemCost = itemCost.add(item.getSellPrice().subtract(itemInStore.getNominalDiscount()).multiply(BigDecimal.valueOf(itemInCheckoutCart.getQuantity())));
                
                
                ItemInOrder itemInOrder = new ItemInOrder(itemInCheckoutCart);
                itemInOrder.setIdOrder(order.getId());
                itemInOrder.save();
                
            }
            for (int i=0; i<itemInCheckoutCartSet.size(); i++) {
                ItemInCheckoutCart itemInCheckoutCart = itemInCheckoutCartSet.get(i);
                
                Item item = ItemManager.getById(itemInCheckoutCart.getIdItem());
                ItemInStore itemInStore = ItemInStoreManager.getByIdItemAndIdStore(item.getId(), AppUtility.getCurrentStore().getId());
                // Update the stock and delete the itemInCheckoutCart if and only if the creation of order in the previous step is completed successfully
                itemInStore.setStock(itemInStore.getStock() - itemInCheckoutCart.getQuantity());
                itemInStore.save();
            }
            // Delete all the items in the checkout cart
            AppUtility.getCurrentCheckoutCart().deleteAllItemInCheckoutCart();
            
            order.setItemCost(itemCost);
            totalItemCost = itemCost;
           
            order.setItemBuyCost(itemBuyCost);
            
            order.save();
            
            System.out.println("Successfully create the order");
            
            OrderPayment orderPayment = new OrderPayment();
            orderPayment.setIdOrder(order.getId());
            orderPayment.setAccountNumber("NA");
            orderPayment.setOrderCost(order.getOrderCost());
            orderPayment.setAdminCost(BigDecimal.ZERO); // For now the default is 0
            orderPayment.save();

            System.out.println("Successfully create the order payment");
            
            // Change this to redirect to appropriate payment view
            AppUtility.setCurrentSelectedOrder(order);
            AppUtility.setCurrentUrl("/item");
            AppUtility.mapCurrentUrl();
        
        });
        
        
    }    

    @FXML
    private void handleGoBack(ActionEvent event) {
        AppUtility.setCurrentUrl("/item");
        AppUtility.mapCurrentUrl();
    }
    
}
