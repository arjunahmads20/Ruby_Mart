/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app.fxml;

import app.model.User;
import app.model.UserManager;
import app.model.Cashier;
import app.model.CashierManager;
import app.model.ItemManager;
import app.utility.AppUtility;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class LoginController implements Initializable {

    @FXML
    private Button btnGoBack;
    @FXML
    private Label lblMessage;
    @FXML
    private Label lblLoginRole;
    @FXML
    private TextField txfUsername;
    @FXML
    private PasswordField pwfPassword;
    @FXML
    private Button btnLogin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lblLoginRole.setText("Login as " + AppUtility.getCurrentRole());
    }    

    @FXML
    private void handleGoBack(ActionEvent event) {
        AppUtility.setCurrentUrl("/select-role");
        AppUtility.mapCurrentUrl();
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = txfUsername.getText();
        String password = pwfPassword.getText();
        User user;
        user = UserManager.getByUsernameAndPassword(username, password);
        if (user == null) {
            lblMessage.setText("Invalid username or password (null user)");
            return;
        }
        if (user.getRole().equals(AppUtility.getCurrentRole())) {
            if (user.getRole().equals("cashier")) {
                Cashier cashier = CashierManager.getByIdUser(user.getId());
                if (cashier.getIdStore() != AppUtility.getCurrentStore().getId()) {
                    lblMessage.setText("Invalid store");
                    return;
                }
            }
            AppUtility.setCurrentUser(user);
            AppUtility.setCurrentSelectedItem(ItemManager.getById(1));
            AppUtility.setCurrentUrl("/item");
            AppUtility.mapCurrentUrl();
        }
        else {
            lblMessage.setText("Invalid username or password");
        }
    }
    
    
}
