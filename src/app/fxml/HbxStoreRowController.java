/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app.fxml;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import app.model.Store;
import java.util.Date;
/**
 * FXML Controller class
 *
 * @author ACER
 */
public class HbxStoreRowController implements Initializable {

    @FXML
    private Label lblIdStore;
    @FXML
    private Label lblStoreName;
    @FXML
    private Label lblStoreAddress;
    @FXML
    private Label lblStoreDateAdded;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void setInfo(Store store) {
        lblIdStore.setText(String.valueOf(store.getId()));
        lblStoreName.setText(store.getName());
        lblStoreAddress.setText(store.getAddress());
        lblStoreDateAdded.setText(String.valueOf(new Date(store.getDatetimeAdded().getTime())));
    }

    @FXML
    private void handleSelectStore(MouseEvent event) {
        
    }
    
}
