/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app.fxml;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import app.model.ItemCategoryManager;
import app.model.Item;
/**
 * FXML Controller class
 *
 * @author ACER
 */
public class HbxItemRowController implements Initializable {
    
    @FXML
    private Label lblItemIdItem;
    @FXML
    private Label lblItemName;
    @FXML
    private Label lblItemUnit;
    @FXML
    private Label lblItemSize;
    @FXML
    private Label lblItemCategory;
    
    public void setInfo(Item item) {
        lblItemIdItem.setText(String.valueOf(item.getId()));
        lblItemName.setText(item.getName());
        lblItemUnit.setText(item.getUnit());
        lblItemSize.setText(String.valueOf(item.getSize()));
        lblItemCategory.setText(ItemCategoryManager.getById(item.getIdItemCategory()).getName());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    
}
