/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app.fxml;

import app.model.Item;
import app.model.ItemCategoryManager;
import app.model.ItemInOrder;
import app.model.ItemInStore;
import app.model.ItemInStoreManager;
import app.model.ItemManager;
import app.utility.AppUtility;
import java.math.BigDecimal;
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
public class HbxItemInOrderRowController implements Initializable {

    @FXML
    private Label lblIdItemInOrder;
    @FXML
    private Label lblItemInOrderName;
    @FXML
    private Label lblItemInOrderSize;
    @FXML
    private Label lblItemInOrderUnit;
    @FXML
    private Label lblItemInOrderCategory;
    @FXML
    private Label lblItemInOrderPrice;
    @FXML
    private Label lblItemInOrderDiscount;
    @FXML
    private Label lblItemInOrderQuantity;
    @FXML
    private Label lblItemInOrderTotalCost;
    
    public void setInfo(ItemInOrder itemInOrder) {
        Item item = ItemManager.getById(itemInOrder.getIdItem());
        ItemInStore itemInStore = ItemInStoreManager.getByIdItemAndIdStore(item.getId(), AppUtility.getCurrentStore().getId());
        lblIdItemInOrder.setText(String.valueOf(itemInOrder.getId()));
        lblItemInOrderName.setText(item.getName());
        lblItemInOrderUnit.setText(item.getUnit());
        lblItemInOrderSize.setText(String.valueOf(item.getSize()));
        lblItemInOrderCategory.setText(ItemCategoryManager.getById(item.getIdItemCategory()).getName());
        lblItemInOrderPrice.setText(String.valueOf(item.getSellPrice()));
        lblItemInOrderDiscount.setText(String.valueOf(itemInStore.getNominalDiscount()));
        lblItemInOrderQuantity.setText(String.valueOf(itemInOrder.getQuantity()));
        String totalCost = String.valueOf(item.getSellPrice().subtract(itemInStore.getNominalDiscount()).multiply(BigDecimal.valueOf(itemInOrder.getQuantity())));
        lblItemInOrderTotalCost.setText(totalCost);
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
