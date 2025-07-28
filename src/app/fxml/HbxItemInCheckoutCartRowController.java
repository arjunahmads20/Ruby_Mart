/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app.fxml;

import app.model.Item;
import app.model.ItemCategoryManager;
import app.model.ItemInCheckoutCart;
import app.model.ItemInStore;
import app.model.ItemInStoreManager;
import app.model.ItemManager;
import app.utility.AppUtility;
import java.math.BigDecimal;
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
public class HbxItemInCheckoutCartRowController implements Initializable {
    @FXML
    private Label lblIdItemInCheckoutCart;
    @FXML
    private Label lblItemInCheckoutCartName;
    @FXML
    private Label lblItemInCheckoutCartSize;
    @FXML
    private Label lblItemInCheckoutCartUnit;
    @FXML
    private Label lblItemInCheckoutCartCategory;
    @FXML
    private Label lblItemInCheckoutCartPrice;
    @FXML
    private Label lblItemInCheckoutCartDiscount;
    @FXML
    private Button btnDecreaseItemInCheckoutCartQuantity;
    @FXML
    private Label lblItemInCheckoutCartQuantity;
    @FXML
    private Button btnIncreaseItemInCheckoutCartQuantity;
    @FXML
    private Label lblItemInCheckoutCartTotalCost;
    @FXML

    public void setInfo(ItemInCheckoutCart itemInCheckoutCart) {
        Item item = ItemManager.getById(itemInCheckoutCart.getIdItem());
        ItemInStore itemInStore = ItemInStoreManager.getByIdItemAndIdStore(item.getId(), AppUtility.getCurrentStore().getId());
        lblIdItemInCheckoutCart.setText(String.valueOf(itemInCheckoutCart.getId()));
        lblItemInCheckoutCartName.setText(item.getName());
        lblItemInCheckoutCartUnit.setText(item.getUnit());
        lblItemInCheckoutCartSize.setText(String.valueOf(item.getSize()));
        lblItemInCheckoutCartCategory.setText(ItemCategoryManager.getById(item.getIdItemCategory()).getName());
        lblItemInCheckoutCartPrice.setText(String.valueOf(item.getSellPrice()));
        lblItemInCheckoutCartDiscount.setText(String.valueOf(itemInStore.getNominalDiscount()));
        lblItemInCheckoutCartQuantity.setText(String.valueOf(itemInCheckoutCart.getQuantity()));
        String totalCost = String.valueOf(item.getSellPrice().subtract(itemInStore.getNominalDiscount()).multiply(BigDecimal.valueOf(itemInCheckoutCart.getQuantity())));
        lblItemInCheckoutCartTotalCost.setText(totalCost);
      
        
        btnDecreaseItemInCheckoutCartQuantity.setOnAction(evt -> {
            itemInCheckoutCart.decreaseQuantity(1);
            
            if (itemInCheckoutCart.getQuantity() < 1) {
                itemInCheckoutCart.delete();
            } else {
                itemInCheckoutCart.save();
            }
            AppUtility.mapCurrentUrl();
        });
        btnIncreaseItemInCheckoutCartQuantity.setOnAction(evt -> {
            if (itemInCheckoutCart.getQuantity() > itemInStore.getStock() - 1) {
                // lblMessage.setText("Quantity for item '" + AppUtility.getCurrentSelectedItem().getName() + "' is out of stock (current stock: " + selectedItemInStore.getStock() + ")");
                return;
            }
            itemInCheckoutCart.increaseQuantity(1);
            itemInCheckoutCart.save();
            AppUtility.mapCurrentUrl();
        });
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
