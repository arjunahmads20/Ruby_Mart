/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app.fxml;

import app.utility.AppUtility;
import app.utility.APIUtility;
import app.model.Item;
import app.model.ItemCategoryManager;
import app.model.ItemInStore;
import app.model.ItemInStoreManager;
import app.model.StoreManager;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Pair;

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class ItemStatisticController implements Initializable {

    @FXML
    private Button btnGoBack;
    @FXML
    private Label lblMessage;
    @FXML
    private Label lblItemIdItem;
    @FXML
    private Label lblItemName;
    @FXML
    private LineChart<String, Integer> lctItemStatisticQuantitySoldChart;
    @FXML
    private LineChart<String, Integer> lctItemStatisticTotalProfitChart;
    @FXML
    private Label lblItemQuantitySold;
    @FXML
    private Label lblItemTotalProfit;
    @FXML
    private ChoiceBox<String> cbxStore;
    @FXML
    private ChoiceBox<String> cbxTimeInterval;
    @FXML
    private Label lblItemRanking;
    @FXML
    private Button btnDownloadAsXlsxFile;
    @FXML
    private Button btnGetAiRecommendation;
    @FXML
    private TextArea txfAiRecommendation;
    
    public void updateData() {
        Item item = AppUtility.getCurrentSelectedItem();
        ItemInStore itemInStore = ItemInStoreManager.getByIdItemAndIdStore(item.getId(), AppUtility.getCurrentStore().getId());
        // Set the attributes of the item
        lblItemIdItem.setText(String.valueOf(item.getId()));
        lblItemName.setText(item.getName());
        
        // Set the charts
        
        // Quantity sold chart
        XYChart.Series quantitySoldSeries = new XYChart.Series();
        quantitySoldSeries.setName("Quantity Sold");
        
        Map<String, Integer> quantitySoldData = new HashMap<>();
        if (cbxStore.getValue().equals("This Store")) {
            quantitySoldData = itemInStore.getQuantitySold(cbxTimeInterval.getValue());
        } else {
            quantitySoldData = item.getQuantitySold(cbxTimeInterval.getValue());
        }
        int quantitySold = 0;
        Integer[] quantitySoldValueSet = quantitySoldData.values().toArray(new Integer[quantitySoldData.size()]);
        ArrayList<String> quantitySoldKeySet = new ArrayList<> (quantitySoldData.keySet());
        for (int i=0; i<quantitySoldData.size(); i++) {
            // Add the datum to the chart
            quantitySoldSeries.getData().add(new XYChart.Data(quantitySoldKeySet.get(i), quantitySoldValueSet[i]));
            quantitySold += quantitySoldValueSet[i];
        }
        lctItemStatisticQuantitySoldChart.getData().clear();
        lctItemStatisticQuantitySoldChart.getData().add(quantitySoldSeries);
        lblItemQuantitySold.setText(String.valueOf(quantitySold));
        
        // Total Profit Chart
        XYChart.Series totalProfitSeries = new XYChart.Series();
        totalProfitSeries.setName("Total Profit");
        Map<String, BigDecimal> totalProfitData = new HashMap<>();
        if (cbxStore.getValue().equals("This Store")) {
            totalProfitData = itemInStore.getTotalProfit(cbxTimeInterval.getValue());
        } else {
            totalProfitData = item.getTotalProfit(cbxTimeInterval.getValue());
        }
        BigDecimal totalProfit = BigDecimal.valueOf(0);
        BigDecimal[] totalProfitValueSet = totalProfitData.values().toArray(new BigDecimal[totalProfitData.size()]);
        List<String> totalProfitKeySet = new ArrayList<> (totalProfitData.keySet());
        for (int i=0; i<totalProfitData.size(); i++) {
            // Add the datum to the chart
            totalProfitSeries.getData().add(new XYChart.Data(totalProfitKeySet.get(i), totalProfitValueSet[i]));
            totalProfit = totalProfit.add(totalProfitValueSet[i]);
        }
        lctItemStatisticTotalProfitChart.getData().clear();
        lctItemStatisticTotalProfitChart.getData().add(totalProfitSeries);
        lblItemTotalProfit.setText(String.valueOf(totalProfit));
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Initialize the checkboxes
        String[] storeTypeSet = {"All Stores", "This Store"};
        cbxStore.getItems().addAll(storeTypeSet);
        cbxStore.setValue("This Store");
        
        String[] timeIntervalSet = {"last_7_d", "last_28_d", "last_4_m", "last_12_m", "last_36_m"};
        cbxTimeInterval.getItems().addAll(timeIntervalSet);
        cbxTimeInterval.setValue("last_7_d");
        
        updateData();
        
        // Add event listener
        cbxStore.setOnAction(evt -> {
            updateData();
        });
        cbxTimeInterval.setOnAction(evt -> {
            updateData();
        });
        btnGetAiRecommendation.setOnAction(evt -> {
            Item item = AppUtility.getCurrentSelectedItem();
            ItemInStore itemInStore = ItemInStoreManager.getByIdItemAndIdStore(item.getId(), AppUtility.getCurrentStore().getId());
            // Quantity sold data
            Map<String, Integer> quantitySoldData = new HashMap<>();
            if (cbxStore.getValue().equals("This Store")) {
                quantitySoldData = itemInStore.getQuantitySold(cbxTimeInterval.getValue());
            } else {
                quantitySoldData = item.getQuantitySold(cbxTimeInterval.getValue());
            }
            // Total profit data
            Map<String, BigDecimal> totalProfitData = new HashMap<>();
            if (cbxStore.getValue().equals("This Store")) {
                totalProfitData = itemInStore.getTotalProfit(cbxTimeInterval.getValue());
            } else {
                totalProfitData = item.getTotalProfit(cbxTimeInterval.getValue());
            }
            
            String statisticDataPrompt = " Berikan saran singkat dari data time-series quantity sold dan total profit (Rp) dari item '" + AppUtility.getCurrentSelectedItem().getName() + "' berikut: " + quantitySoldData + ", " + totalProfitData;
            String aiRecommendation = APIUtility.getAiRecommendation(statisticDataPrompt);
            txfAiRecommendation.setText(aiRecommendation);
        });
        btnDownloadAsXlsxFile.setOnAction(evt -> {
            
        });
    }    

    @FXML
    private void handleGoBack(ActionEvent event) {
        AppUtility.setCurrentUrl("/item");
        AppUtility.mapCurrentUrl();
    }
    
}
