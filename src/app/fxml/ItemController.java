/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app.fxml;

import app.Main;
import app.model.CheckoutCart;
import app.model.CheckoutCartManager;

import app.utility.AppUtility;

import app.model.Store;
import app.model.StoreManager;
import app.model.Item;
import app.model.ItemCategoryManager;
import app.model.ItemInCheckoutCart;
import app.model.ItemInCheckoutCartManager;
import app.model.ItemInStore;
import app.model.ItemInStoreManager;
import app.model.ItemManager;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class ItemController implements Initializable {

    @FXML
    private Label lblStoreName;
    @FXML
    private Label lblStoreAddress;
    @FXML
    private Label lblUserUsername;
    @FXML
    private Label lblUserRole;
    @FXML
    private Label lblMessage;
    @FXML
    private TextField txfSearchItemBar;
    @FXML
    private Button btnSearchItem;
    @FXML
    private VBox vbxItemList;
    @FXML
    private Button btnAddItem;
    @FXML
    private Button btnEditSelectedItem;
    @FXML
    private Button btnSaveSelectedItem;
    @FXML
    private Button btnDeleteSelectedItem;
    @FXML
    private Label lblSelectedItemIdItem;
    @FXML
    private TextField txfSelectedItemName;
    @FXML
    private TextField txfSelectedItemSize;
    @FXML
    private ChoiceBox<String> cbxSelectedItemUnit;
    @FXML
    private ChoiceBox<String> cbxSelectedItemCategory;
    @FXML
    private TextField txfSelectedItemStock;
    @FXML
    private TextField txfSelectedItemSellPrice;
    @FXML
    private Button btnDecreaseSelectedItemInCheckoutCartQuantity;
    @FXML
    private Label lblSelectedItemInCheckoutCartQuantity;
    @FXML
    private Button btnIncreaseSelectedItemInCheckoutCartQuantity;
    @FXML
    private TextArea txfSelectedItemDescription;
    @FXML
    private Label lblSelectedItemDatetimeAdded;
    @FXML
    private Button btnSelectedItemStatistic;
    @FXML
    private AnchorPane acpCheckoutCart;
    @FXML
    private Button btnToggleCheckoutCart;
    @FXML
    private ChoiceBox<String> cbxCheckoutCart;
    @FXML
    private VBox vbxItemInCheckoutCartList;
    @FXML
    private Label lblCheckoutCartTotalItemCost;
    @FXML
    private Button btnClearAllItemInCheckoutCart;
    @FXML
    private Button btnCreateOrder;
    @FXML
    private Button btnOrderList;
   
    public void setSelectedItem(Item item) {
        AppUtility.setCurrentSelectedItem(item);
        lblSelectedItemIdItem.setText(String.valueOf(item.getId()));
        txfSelectedItemName.setText(item.getName());
        txfSelectedItemSize.setText(String.valueOf(item.getSize()));
        cbxSelectedItemUnit.setValue(String.valueOf(item.getUnit()));
        
        cbxSelectedItemCategory.setValue(ItemCategoryManager.getById(item.getIdItemCategory()).getName());
        txfSelectedItemStock.setText(String.valueOf(ItemInStoreManager.getByIdItemAndIdStore(item.getId(), AppUtility.getCurrentStore().getId()).getStock()));
        txfSelectedItemSellPrice.setText(String.valueOf(item.getSellPrice()));
        ItemInCheckoutCart itemInCheckoutCart = ItemInCheckoutCartManager.getByIdItemAndIdCheckoutCart(item.getId(), AppUtility.getCurrentCheckoutCart().getId());
        if (itemInCheckoutCart == null) {
            lblSelectedItemInCheckoutCartQuantity.setText(String.valueOf(0));
        } else {
            lblSelectedItemInCheckoutCartQuantity.setText(String.valueOf(itemInCheckoutCart.getQuantity()));
        }
        txfSelectedItemDescription.setText(item.getDescription());
        lblSelectedItemDatetimeAdded.setText(String.valueOf(item.getDatetimeAdded()));
    }
    
    public void setSelectedItem(ItemInCheckoutCart itemInCheckoutCart) {
        Item item = ItemManager.getById(itemInCheckoutCart.getIdItem());
        AppUtility.setCurrentSelectedItem(item);
        lblSelectedItemIdItem.setText(String.valueOf(item.getId()));
        txfSelectedItemName.setText(item.getName());
        txfSelectedItemSize.setText(String.valueOf(item.getSize()));
        cbxSelectedItemUnit.setValue(item.getUnit());

        cbxSelectedItemCategory.setValue(ItemCategoryManager.getById(item.getIdItemCategory()).getName());
        txfSelectedItemStock.setText(String.valueOf(ItemInStoreManager.getByIdItemAndIdStore(item.getId(), AppUtility.getCurrentStore().getId()).getStock()));
        txfSelectedItemSellPrice.setText(String.valueOf(item.getSellPrice()));
        lblSelectedItemInCheckoutCartQuantity.setText(String.valueOf(itemInCheckoutCart.getQuantity()));
        txfSelectedItemDescription.setText(item.getDescription());
        lblSelectedItemDatetimeAdded.setText(String.valueOf(item.getDatetimeAdded()));
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        // Initialize the store info
        lblStoreName.setText(AppUtility.getCurrentStore().getName());
        lblStoreAddress.setText(AppUtility.getCurrentStore().getAddress());
        
        // Initialize the user info
        lblUserUsername.setText(AppUtility.getCurrentUser().getUsername());
        lblUserRole.setText(AppUtility.getCurrentUser().getRole());
        
        // Initialize the checkboxes
        String[] itemUnitSet = {"pcs", "g", "kg", "ml", "l"};
        cbxSelectedItemUnit.getItems().addAll(itemUnitSet);
        
        ObservableList<String> itemCategoryNameSet = ItemCategoryManager.getAllNames();
        cbxSelectedItemCategory.setItems(itemCategoryNameSet);
        
        // Load all items
        ArrayList<Item> itemSet = ItemManager.getAll();
        try {
            for(int i=0; i<itemSet.size(); i++) {
                
                    Item item = itemSet.get(i);
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Main.class.getResource("fxml/HbxItemRow.fxml"));
                    Node node = loader.load();

                    HbxItemRowController controller = loader.getController();
                    controller.setInfo(item);
                    // add row to the vbox
                    vbxItemList.getChildren().add(node);
                    node.setOnMousePressed(evt -> {
                        setSelectedItem(item);
                    });
            }
        } catch (IOException ex) {
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Item catalog features
        btnSearchItem.setOnAction(evt -> {
            String keyword  = txfSearchItemBar.getText();
            ArrayList<Item> itemSearchSet = ItemManager.getByFilter("name LIKE '%" + keyword + "%'");
            
            Node headerNode = vbxItemList.getChildren().getFirst();
            vbxItemList.getChildren().clear();
            vbxItemList.getChildren().add(headerNode);
            
            try {
                for(int i=0; i<itemSearchSet.size(); i++) {

                        Item item = itemSearchSet.get(i);
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(Main.class.getResource("fxml/HbxItemRow.fxml"));
                        Node node = loader.load();

                        HbxItemRowController controller = loader.getController();
                        controller.setInfo(item);
                        // add row to the vbox
                        vbxItemList.getChildren().add(node);
                        node.setOnMousePressed(evtSearch -> {
                            setSelectedItem(item);
                        });
                }
            } catch (IOException ex) {
                Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
        
        btnAddItem.setOnAction(evt -> {
            AppUtility.setCurrentUrl("/item/add-item");
            AppUtility.mapCurrentUrl();
        });
        
        // Initialize the selected item
        setSelectedItem(AppUtility.getCurrentSelectedItem());
        // Selected item property
        

        txfSelectedItemName.setDisable(true);
        txfSelectedItemSize.setDisable(true);
        cbxSelectedItemUnit.setDisable(true);
        cbxSelectedItemCategory.setDisable(true);
        txfSelectedItemDescription.setDisable(true);
        txfSelectedItemStock.setDisable(true);
        txfSelectedItemSellPrice.setDisable(true);
        if (AppUtility.getCurrentRole().equals("cashier")) {
            btnEditSelectedItem.setVisible(false);
        } // The edit selected item functionality is only appear when the user role is admin
        else if (AppUtility.getCurrentRole().equals("admin")) {
            
            btnEditSelectedItem.setOnAction(evt -> {
                if (btnEditSelectedItem.getText().equals("Edit")) {
                    btnEditSelectedItem.setText("Cancel");
                    btnSaveSelectedItem.setVisible(true);
                    btnDeleteSelectedItem.setVisible(true);
                    
                    txfSelectedItemName.setDisable(false);
                    txfSelectedItemSize.setDisable(false);
                    cbxSelectedItemUnit.setDisable(false);
                    cbxSelectedItemCategory.setDisable(false);
                    txfSelectedItemDescription.setDisable(false);
                    txfSelectedItemStock.setDisable(false);
                    txfSelectedItemSellPrice.setDisable(false);
                } else if (btnEditSelectedItem.getText().equals("Cancel")) {
                    setSelectedItem(AppUtility.getCurrentSelectedItem());
                    btnSaveSelectedItem.setVisible(false);
                    btnDeleteSelectedItem.setVisible(false);
                    
                    txfSelectedItemName.setDisable(true);
                    txfSelectedItemSize.setDisable(true);
                    cbxSelectedItemUnit.setDisable(true);
                    cbxSelectedItemCategory.setDisable(true);
                    txfSelectedItemDescription.setDisable(true);
                    txfSelectedItemStock.setDisable(true);
                    txfSelectedItemSellPrice.setDisable(true);
                    AppUtility.mapCurrentUrl();
                }
                
            });
            btnSaveSelectedItem.setOnAction(evt -> {
                String name = txfSelectedItemName.getText();
                String category = cbxSelectedItemCategory.getValue();
                String size = txfSelectedItemSize.getText();
                String unit = cbxSelectedItemUnit.getValue();
                String description = txfSelectedItemDescription.getText();
                String stock = txfSelectedItemStock.getText();
                String sellPrice = txfSelectedItemSellPrice.getText();
                
                Item item = AppUtility.getCurrentSelectedItem();
                item.setName(name);
                item.setIdItemCategory(ItemCategoryManager.getByName(category).getId());
                item.setSize(Integer.parseInt(size));
                item.setUnit(unit);
                item.setDescription(description);
                item.setSellPrice(BigDecimal.valueOf(Long.parseLong(sellPrice)));
                item.save();

                ItemInStore itemInStore = ItemInStoreManager.getByIdItemAndIdStore(item.getId(), AppUtility.getCurrentStore().getId());
                itemInStore.setStock(Integer.parseInt(stock));
                itemInStore.save();
                
                AppUtility.mapCurrentUrl();
            });
            btnDeleteSelectedItem.setOnAction(evt -> {
                
                AppUtility.getCurrentSelectedItem().delete();
                // Reset the current selected item in order to prevent error caused by null reference to current selected item
                AppUtility.setCurrentSelectedItem(ItemManager.getById(1));
                AppUtility.mapCurrentUrl();
            });
        }
        
        
        ItemInCheckoutCart _selectedItemInCheckoutCart = ItemInCheckoutCartManager.getByIdItemAndIdCheckoutCart(AppUtility.getCurrentSelectedItem().getId(), AppUtility.getCurrentCheckoutCart().getId());
        ItemInStore _selectedItemInStore = ItemInStoreManager.getByIdItemAndIdStore(AppUtility.getCurrentSelectedItem().getId(), AppUtility.getCurrentStore().getId());
        if (_selectedItemInCheckoutCart == null) {
        } else {
            lblSelectedItemInCheckoutCartQuantity.setText(String.valueOf(_selectedItemInCheckoutCart.getQuantity()));
        }
        btnDecreaseSelectedItemInCheckoutCartQuantity.setOnAction(evt -> {
            final ItemInCheckoutCart selectedItemInCheckoutCart = ItemInCheckoutCartManager.getByIdItemAndIdCheckoutCart(AppUtility.getCurrentSelectedItem().getId(), AppUtility.getCurrentCheckoutCart().getId());
            final ItemInStore selectedItemInStore = ItemInStoreManager.getByIdItemAndIdStore(AppUtility.getCurrentSelectedItem().getId(), AppUtility.getCurrentStore().getId());
            if (selectedItemInCheckoutCart == null) {
            } else {
                selectedItemInCheckoutCart.decreaseQuantity(1);
                if (selectedItemInCheckoutCart.getQuantity() < 1) {
                    selectedItemInCheckoutCart.delete();
                } else {
                    selectedItemInCheckoutCart.save();
                }
            }
            AppUtility.mapCurrentUrl();
        });
        btnIncreaseSelectedItemInCheckoutCartQuantity.setOnAction(evt -> {
            final ItemInCheckoutCart selectedItemInCheckoutCart = ItemInCheckoutCartManager.getByIdItemAndIdCheckoutCart(AppUtility.getCurrentSelectedItem().getId(), AppUtility.getCurrentCheckoutCart().getId());
            final ItemInStore selectedItemInStore = ItemInStoreManager.getByIdItemAndIdStore(AppUtility.getCurrentSelectedItem().getId(), AppUtility.getCurrentStore().getId());
            if (selectedItemInCheckoutCart == null) {
                ItemInCheckoutCart newSelectedItemInCheckoutCart = new ItemInCheckoutCart(AppUtility.getCurrentSelectedItem(), AppUtility.getCurrentCheckoutCart());
                newSelectedItemInCheckoutCart.save();
            } else {
                if (selectedItemInCheckoutCart.getQuantity() > selectedItemInStore.getStock() - 1) {
                    lblMessage.setText("Quantity for item '" + AppUtility.getCurrentSelectedItem().getName() + "' is out of stock (current stock: " + selectedItemInStore.getStock() + ")");
                    return;
                }
                selectedItemInCheckoutCart.increaseQuantity(1);
                selectedItemInCheckoutCart.save();
            }
            AppUtility.mapCurrentUrl();
        });
        
        
        btnSelectedItemStatistic.setOnAction(evt -> {
            AppUtility.setCurrentUrl("/item/" + AppUtility.getCurrentSelectedItem().getId() + "/statistic");
            AppUtility.mapCurrentUrl();
        });
        btnOrderList.setOnAction(evt -> {
            AppUtility.setCurrentUrl("/order");
            AppUtility.mapCurrentUrl();
        });
        
        
        // Initialize the checkout cart state
        if (AppUtility.getCurrentCheckoutCartState().equals("show")) {
            btnToggleCheckoutCart.setText("Hide");
            acpCheckoutCart.setTranslateY(0);
        } else {
            btnToggleCheckoutCart.setText("Show");
            acpCheckoutCart.setTranslateY(308);
        }
        
        btnToggleCheckoutCart.setOnAction(evt -> {
            if (AppUtility.getCurrentCheckoutCartState().equals("hide")) {
                AppUtility.setCurrentCheckoutCartState("show");
                btnToggleCheckoutCart.setText("Hide");
                acpCheckoutCart.setTranslateY(0);
            } else {
                AppUtility.setCurrentCheckoutCartState("hide");
                btnToggleCheckoutCart.setText("Show");
                acpCheckoutCart.setTranslateY(308);
            }
        });
        
        cbxCheckoutCart.setValue(AppUtility.getCurrentCheckoutCart().getName());
        ObservableList<String> checkoutCartNameSet = CheckoutCartManager.getAllNames(AppUtility.getCurrentStore().getId());
        cbxCheckoutCart.setItems(checkoutCartNameSet);
        
        cbxCheckoutCart.setOnAction(evt -> {
            CheckoutCart selectedCheckoutCart = CheckoutCartManager.getByNameAndIdStore(cbxCheckoutCart.getValue(), AppUtility.getCurrentStore().getId());
            AppUtility.setCurrentCheckoutCart(selectedCheckoutCart);
            AppUtility.mapCurrentUrl();
        });
        
        // Load all items in checkout cart
        ArrayList<ItemInCheckoutCart> itemInCheckoutCartSet = ItemInCheckoutCartManager.getAllByIdCheckoutCart(AppUtility.getCurrentCheckoutCart().getId());
        try {
            for(int i=0; i<itemInCheckoutCartSet.size(); i++) {
                
                    ItemInCheckoutCart itemInCheckoutCart = itemInCheckoutCartSet.get(i);
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Main.class.getResource("fxml/HbxItemInCheckoutCartRow.fxml"));
                    Node node = loader.load();

                    HbxItemInCheckoutCartRowController controller = loader.getController();
                    controller.setInfo(itemInCheckoutCart);
                    // add row to the vbox
                    vbxItemInCheckoutCartList.getChildren().add(node);
                    node.setOnMousePressed(evt -> {
                        setSelectedItem(itemInCheckoutCart);
                    });

            }
        } catch (IOException ex) {
            Logger.getLogger(SelectStoreController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Set the total item cost
        lblCheckoutCartTotalItemCost.setText(String.valueOf(AppUtility.getCurrentCheckoutCart().getItemCost()));
        btnClearAllItemInCheckoutCart.setOnAction(evt -> {
                AppUtility.getCurrentCheckoutCart().deleteAllItemInCheckoutCart();
                AppUtility.mapCurrentUrl();
        });
        btnCreateOrder.setOnAction(evt -> {
            AppUtility.setCurrentUrl("/checkout/" + AppUtility.getCurrentCheckoutCart().getId());
            AppUtility.mapCurrentUrl();
        });

    }    

}
