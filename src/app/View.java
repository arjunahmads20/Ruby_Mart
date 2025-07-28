package app;

import javafx.scene.Scene;

import app.utility.AppUtility;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class View {
    public static void welcomeView() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("fxml/Welcome.fxml"));
        AnchorPane root;
        try {
            root = loader.load();
            Scene scene = new Scene(root, 960, 640);
            AppUtility.setCurrentScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void selectStoreView() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("fxml/SelectStore.fxml"));
        AnchorPane root;
        try {
            root = loader.load();
            Scene scene = new Scene(root, 960, 640);
            AppUtility.setCurrentScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void selectRoleView() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("fxml/SelectRole.fxml"));
        AnchorPane root;
        try {
            root = loader.load();
            Scene scene = new Scene(root, 960, 640);
            AppUtility.setCurrentScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void loginView(String role) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("fxml/Login.fxml"));
        AnchorPane root;
        try {
            root = loader.load();
            Scene scene = new Scene(root, 960, 640);
            AppUtility.setCurrentScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void itemView() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("fxml/Item.fxml"));
        AnchorPane root;
        try {
            root = loader.load();
            Scene scene = new Scene(root, 960, 640);
            AppUtility.setCurrentScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void addItemView() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("fxml/AddItem.fxml"));
        AnchorPane root;
        try {
            root = loader.load();
            Scene scene = new Scene(root, 960, 640);
            AppUtility.setCurrentScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void itemStatisticView(int idItem) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("fxml/ItemStatistic.fxml"));
        AnchorPane root;
        try {
            root = loader.load();
            Scene scene = new Scene(root, 960, 640);
            AppUtility.setCurrentScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void orderView() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("fxml/Order.fxml"));
        AnchorPane root;
        try {
            root = loader.load();
            Scene scene = new Scene(root, 960, 640);
            AppUtility.setCurrentScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void orderStatisticView() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("fxml/OrderStatistic.fxml"));
        AnchorPane root;
        try {
            root = loader.load();
            Scene scene = new Scene(root, 960, 640);
            AppUtility.setCurrentScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void checkoutOrderView(int idCheckoutCart) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("fxml/CheckoutOrder.fxml"));
        AnchorPane root;
        try {
            root = loader.load();
            Scene scene = new Scene(root, 960, 640);
            AppUtility.setCurrentScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void orderPaymentDetailView(int idOrder) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("fxml/OrderPaymentDetail.fxml"));
        AnchorPane root;
        try {
            root = loader.load();
            Scene scene = new Scene(root, 960, 640);
            AppUtility.setCurrentScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void orderPaymentCashView(int idOrder) {}
    public static void orderPaymentQrisView(int idOrder) {}
    public static void orderPaymentHandpalmView(int idOrder) {}

}
