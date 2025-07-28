/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app.fxml;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import app.utility.AppUtility;



/**
 * FXML Controller class
 *
 * @author ACER
 */
public class SelectRoleController implements Initializable {

    @FXML
    private Button btnGoBack;
    @FXML
    private Label lblMessage;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnAdminRole;
    @FXML
    private Button btnCashierRole;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleGoBack(ActionEvent event) {
        AppUtility.setCurrentUrl("/select-store");
        AppUtility.mapCurrentUrl();
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        AppUtility.setCurrentUrl("/login/" + AppUtility.getCurrentRole());
        AppUtility.mapCurrentUrl();
    }

    @FXML
    private void handleSelectAdminRole(ActionEvent event) {
        AppUtility.setCurrentRole("admin");
    }

    @FXML
    private void handleSelectCashierRole(ActionEvent event) {
        AppUtility.setCurrentRole("cashier");
    }
    
}
