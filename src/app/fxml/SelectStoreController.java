/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app.fxml;

import app.Main;
import app.model.CheckoutCartManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import app.model.Store;
import app.model.StoreManager;
import app.utility.AppUtility;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class SelectStoreController implements Initializable {

    @FXML
    private Button btnGoBack;
    @FXML
    private Label lblMessage;
    @FXML
    private VBox vbxStoreList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        lblMessage.setText("Made by Kelompok 10");
        
        ArrayList<Store> storeSet = StoreManager.getAll();
        try {
            for(int i=0; i<storeSet.size(); i++) {
                
                    Store store = storeSet.get(i);
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Main.class.getResource("fxml/HbxStoreRow.fxml"));
                    Node node = loader.load();

                    HbxStoreRowController controller = loader.getController();
                    controller.setInfo(store);
                    // add row to the vbox
                    vbxStoreList.getChildren().add(node);
                    
//                    node.setOnMouseEntered(evt -> {
//                        node.setStyle("-fx-background-color: #165ddb");
//                    });
//                    node.setOnMouseExited(evt -> {
//                        node.setStyle("-fx-background-color: #ffffff");
//                    });
                    node.setOnMousePressed(evt -> {
                        AppUtility.setCurrentStore(store);
                        AppUtility.setCurrentCheckoutCart(CheckoutCartManager.getByIdStore(store.getId()));
                        
                        AppUtility.setCurrentUrl("/select-role");
                        AppUtility.mapCurrentUrl();
                    });

            }
        } catch (IOException ex) {
            Logger.getLogger(SelectStoreController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }    

    @FXML
    private void handleGoBack(ActionEvent event) {
    }
    
}
