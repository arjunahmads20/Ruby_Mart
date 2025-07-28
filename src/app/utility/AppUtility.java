package app.utility;

import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.stage.Stage;
import javafx.scene.Scene;


import app.View;

import app.model.User;
import app.model.Store;
import app.model.Item;
import app.model.Order;
import app.model.CheckoutCart;


public class AppUtility {
	private static Stage currentStage;
	private static String currentUrl;
	private static Store currentStore;
	private static String currentRole;
	private static User currentUser;
        private static Item currentSelectedItem;
        private static Order currentSelectedOrder;
	private static CheckoutCart currentCheckoutCart;
	private static String currentCheckoutCartState = "hide";


	public static void setCurrentStage(Stage stage) {
		currentStage = stage;
	}
	public static Stage getCurrentStage() {
		return currentStage;
	}
	public static void setCurrentScene(Scene scene) {
		currentStage.setScene(scene);
	}
	public static void setCurrentUrl(String url) {
		currentUrl = url;
	}
	public static String getCurrentUrl() {
		return currentUrl;
	}
	public static void setCurrentStore(Store store) {
		currentStore = store;
	}
	public static Store getCurrentStore() {
		return currentStore;
	}
    public static void setCurrentRole(String role) {
		currentRole = role;
	}
	public static String getCurrentRole() {
		return currentRole;
	}
	public static void setCurrentUser(User user) {
		currentUser = user;
	}
	public static User getCurrentUser() {
		return currentUser;
	}
        public static void setCurrentSelectedItem(Item item) {
		currentSelectedItem = item;
	}
        public static Item getCurrentSelectedItem() {
		return currentSelectedItem;
	}
        public static void setCurrentSelectedOrder(Order order) {
		currentSelectedOrder = order;
	}
        public static Order getCurrentSelectedOrder() {
		return currentSelectedOrder;
	}
	public static void setCurrentCheckoutCart(CheckoutCart checkoutCart) {
		currentCheckoutCart = checkoutCart;
	}
        public static CheckoutCart getCurrentCheckoutCart() {
		return currentCheckoutCart;
	}
	public static void setCurrentCheckoutCartState(String checkoutCartState) {
		currentCheckoutCartState = checkoutCartState;
	}
	public static String getCurrentCheckoutCartState() {
		return currentCheckoutCartState;
	}
	public static void mapCurrentUrl() {
		switch(currentUrl) {
			case "/":
				View.welcomeView();
				break;
			case "/select-store":
				View.selectStoreView();
				break;
			case "/select-role":
				View.selectRoleView();
				break;
			case "/item":
				View.itemView();
				break;
			case "/item/add-item":
				View.addItemView();
				break;
			case "/order":
				View.orderView();
				break;
			case "/order/statistic":
				View.orderStatisticView();
				break;
			default: // = : ; +
                                if (currentUrl.matches("^/login/(.*?)$")) {
					Pattern pattern = Pattern.compile("^/login/(.*?)$");
					Matcher matcher = pattern.matcher(currentUrl);
					if (matcher.find()) {
						String role = matcher.group(1);
						View.loginView(role);
					}
				}
                                else if (currentUrl.matches("^/item/\\d+/statistic$")) {
					Pattern pattern = Pattern.compile("^/item/(\\d+)/statistic$");
					Matcher matcher = pattern.matcher(currentUrl);
					if (matcher.find()) {
						int idItem = Integer.parseInt(matcher.group(1));
						View.itemStatisticView(idItem);
					}
				}
				else if (currentUrl.matches("^/checkout/\\d+$")) {
					Pattern pattern = Pattern.compile("^/checkout/(\\d+)$");
					Matcher matcher = pattern.matcher(currentUrl);
					if (matcher.find()) {
						int idCheckoutCart = Integer.parseInt(matcher.group(1));
						View.checkoutOrderView(idCheckoutCart);
					}
				}
				else if (currentUrl.matches("^/order/\\d+/payment-detail$")) {
					Pattern pattern = Pattern.compile("^/order/(\\d+)/payment-detail$");
					Matcher matcher = pattern.matcher(currentUrl);
					if (matcher.find()) {
						int idOrder = Integer.parseInt(matcher.group(1));
						View.orderPaymentDetailView(idOrder);
					}
				}
				else if (currentUrl.matches("^/order/\\d+/payment/cash$")) {
					Pattern pattern = Pattern.compile("^/order/(\\d+)/payment/cash$");
					Matcher matcher = pattern.matcher(currentUrl);
					if (matcher.find()) {
						int idOrder = Integer.parseInt(matcher.group(1));
						View.orderPaymentCashView(idOrder);
					}
				}
				else if (currentUrl.matches("^/order/\\d+/payment/qris$")) {
					Pattern pattern = Pattern.compile("^/order/(\\d+)/payment/qris$");
					Matcher matcher = pattern.matcher(currentUrl);
					if (matcher.find()) {
						int idOrder = Integer.parseInt(matcher.group(1));
						View.orderPaymentQrisView(idOrder);
					}
				}
				else if (currentUrl.matches("^/order/\\d+/payment/handpalm$")) {
					Pattern pattern = Pattern.compile("^/order/(\\d+)/payment/handpalm$");
					Matcher matcher = pattern.matcher(currentUrl);
					if (matcher.find()) {
						int idOrder = Integer.parseInt(matcher.group(1));
						View.orderPaymentHandpalmView(idOrder);
					}
				}
				break;	
		}

	}
}