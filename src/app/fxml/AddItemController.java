/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app.fxml;

import app.Main;
import app.model.Item;
import app.model.ItemCategoryManager;
import app.model.ItemInStore;
import app.model.Store;
import app.model.StoreManager;
import app.utility.AppUtility;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class AddItemController implements Initializable {

    @FXML
    private Button btnGoBack;
    @FXML
    private Label lblMessage;
    @FXML
    private ImageView imvItemPicture;
    @FXML
    private TextField txfItemPictureUrl;
    @FXML
    private TextField txfItemName;
    @FXML
    private TextField txfItemSize;
    @FXML
    private ChoiceBox<String> cbxItemUnit;
    @FXML
    private ChoiceBox<String> cbxItemCategory;
    @FXML
    private TextArea txfItemDescription;
    @FXML
    private TextField txfItemStock;
    @FXML
    private TextField txfItemSellPrice;
    @FXML
    private TextField txfItemBuyPrice;
    @FXML
    private Button btnClearAll;
    @FXML
    private Button btnSaveItem;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String[] unitSet = {"pcs", "kg", "g", "ml", "l"};
        cbxItemUnit.getItems().setAll(unitSet);
        
        ObservableList<String> itemCategoryNameSet = ItemCategoryManager.getAllNames();
        cbxItemCategory.setItems(itemCategoryNameSet);

        btnClearAll.setOnAction(evt -> {
            cbxItemUnit.setValue("");
            cbxItemCategory.setValue("");
            txfItemPictureUrl.setText("");
            txfItemName.setText("");
            txfItemSize.setText("");
            txfItemDescription.setText("");
            txfItemStock.setText("");
            txfItemSellPrice.setText("");
            txfItemBuyPrice.setText("");
        });
        btnSaveItem.setOnAction(evt -> {
            String pictureUrl = txfItemPictureUrl.getText();
            String name = txfItemName.getText();
            int size = Integer.parseInt(txfItemSize.getText());
            String unit = cbxItemUnit.getValue();
            int idItemCategory = ItemCategoryManager.getByName(cbxItemCategory.getValue()).getId();
            
            String description = txfItemDescription.getText();
            int stock = Integer.parseInt(txfItemStock.getText());
            BigDecimal sellPrice = BigDecimal.valueOf(Long.parseLong(txfItemSellPrice.getText()));
            BigDecimal buyPrice = BigDecimal.valueOf(Long.parseLong(txfItemBuyPrice.getText()));
            
            Item item = new Item();
            
            item.setPictureUrl(pictureUrl);
            item.setName(name);
            item.setSize(size);
            item.setUnit(unit);
            item.setIdItemCategory(idItemCategory);
            item.setDescription(description);
            item.setSellPrice(sellPrice);
            item.setBuyPrice(buyPrice);
            item.save();
            
            ArrayList<Store> storeSet = StoreManager.getAll();
            for(int i=0; i<storeSet.size(); i++) {
                    // Create an ItemInStore object for all store
                    Store store = storeSet.get(i);
                    ItemInStore itemInStore = new ItemInStore(item.getId(), store.getId());
                    // Set the stock only if the store is same as current store 
                    if(itemInStore.getIdStore() == AppUtility.getCurrentStore().getId()) {
                        itemInStore.setStock(stock);
                    }
                    itemInStore.save();
            }
            
            AppUtility.setCurrentSelectedItem(item);
            AppUtility.setCurrentUrl("/item");
            AppUtility.mapCurrentUrl();
        });
    }    

    @FXML
    private void handleGoBack(ActionEvent event) {
        AppUtility.setCurrentUrl("/item");
        AppUtility.mapCurrentUrl();
    }

    @FXML
    private void handleClearAll(ActionEvent event) {
    }
    
}
