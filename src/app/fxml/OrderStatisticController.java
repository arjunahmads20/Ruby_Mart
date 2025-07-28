/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app.fxml;

import app.model.Order;
import app.model.OrderManager;
import app.utility.APIUtility;
import app.utility.AppUtility;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
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

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class OrderStatisticController implements Initializable {

    @FXML
    private Button btnGoBack;
    @FXML
    private Label lblMessage;
    @FXML
    private LineChart<String, Integer> lctOrderStatisticQuantityFinishedChart;
    @FXML
    private Label lblOrderQuantityFinished;
    @FXML
    private LineChart<String, Integer> lctOrderStatisticTotalProfitChart;
    @FXML
    private Label lblOrderTotalProfit;
    @FXML
    private ChoiceBox<String> cbxStore;
    @FXML
    private ChoiceBox<String> cbxTimeInterval;
    @FXML
    private Button btnDownloadAsXlsxFile;
    @FXML
    private Button btnGetAiRecommendation;
    @FXML
    private TextArea txfAiRecommendation;
    
    
    public void updateData() {
        // Set the chart
        
        // Quantity Finished Chart
        XYChart.Series quantityFinishedSeries = new XYChart.Series();
        quantityFinishedSeries.setName("Quantity Finished");
        Map<String, Integer> quantityFinishedData = new HashMap<>();
        if (cbxStore.getValue().equals("This Store")) {
            quantityFinishedData = OrderManager.getQuantityFinished(cbxTimeInterval.getValue(), AppUtility.getCurrentStore().getId());
        } else {
            quantityFinishedData = OrderManager.getQuantityFinished(cbxTimeInterval.getValue(), AppUtility.getCurrentStore().getId());
        }
        int quantityFinished = 0;
        Integer[] quantityFinishedValueSet = quantityFinishedData.values().toArray(new Integer[quantityFinishedData.size()]);
        List<String> quantityFinishedKeySet = new ArrayList<> (quantityFinishedData.keySet());
        for (int i=0; i<quantityFinishedData.size(); i++) {
            // Add the datum to the chart
            quantityFinishedSeries.getData().add(new XYChart.Data(quantityFinishedKeySet.get(i), quantityFinishedValueSet[i]));
            quantityFinished += quantityFinishedValueSet[i];
        }
        lctOrderStatisticQuantityFinishedChart.getData().clear();
        lctOrderStatisticQuantityFinishedChart.getData().add(quantityFinishedSeries);
        lblOrderQuantityFinished.setText(String.valueOf(quantityFinished));
        
        // Total Profit Chart
        XYChart.Series totalProfitSeries = new XYChart.Series();
        totalProfitSeries.setName("Total Profit");
        Map<String, BigDecimal> totalProfitData = new HashMap<>();
        if (cbxStore.getValue().equals("This Store")) {
            totalProfitData = OrderManager.getTotalProfit(cbxTimeInterval.getValue(), AppUtility.getCurrentStore().getId());
        } else {
            totalProfitData = OrderManager.getTotalProfit(cbxTimeInterval.getValue(), AppUtility.getCurrentStore().getId());
        }
        BigDecimal totalProfit = BigDecimal.valueOf(0);
        BigDecimal[] totalProfitValueSet = totalProfitData.values().toArray(new BigDecimal[totalProfitData.size()]);
        List<String> totalProfitKeySet = new ArrayList<> (totalProfitData.keySet());
        for (int i=0; i<totalProfitData.size(); i++) {
            // Add the datum to the chart
            totalProfitSeries.getData().add(new XYChart.Data(totalProfitKeySet.get(i), totalProfitValueSet[i]));
            totalProfit = totalProfit.add(totalProfitValueSet[i]);
        }
        lctOrderStatisticTotalProfitChart.getData().clear();
        lctOrderStatisticTotalProfitChart.getData().add(totalProfitSeries);
        lblOrderTotalProfit.setText(String.valueOf(totalProfit));
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
            // Quantity finished data
            Map<String, Integer> quantityFinishedData = new HashMap<>();
            if (cbxStore.getValue().equals("This Store")) {
                quantityFinishedData = OrderManager.getQuantityFinished(cbxTimeInterval.getValue(), AppUtility.getCurrentStore().getId());
            } else {
                quantityFinishedData = OrderManager.getQuantityFinished(cbxTimeInterval.getValue(), AppUtility.getCurrentStore().getId());
            }
            // Total profit data
            Map<String, BigDecimal> totalProfitData = new HashMap<>();
            if (cbxStore.getValue().equals("This Store")) {
                totalProfitData = OrderManager.getTotalProfit(cbxTimeInterval.getValue(), AppUtility.getCurrentStore().getId());
            } else {
                totalProfitData = OrderManager.getTotalProfit(cbxTimeInterval.getValue(), AppUtility.getCurrentStore().getId());
            }
            
            String statisticDataPrompt = " Berikan saran singkat dari data time-series quantity finished dan total profit (Rp) dari order-order(pesanan-pesanan) berikut: " + quantityFinishedData + ", " + totalProfitData;
            String aiRecommendation = APIUtility.getAiRecommendation(statisticDataPrompt);
            txfAiRecommendation.setText(aiRecommendation);
        });
        btnDownloadAsXlsxFile.setOnAction(evt -> {
            // Todo
        });
    }    

    @FXML
    private void handleGoBack(ActionEvent event) {
        AppUtility.setCurrentUrl("/order");
        AppUtility.mapCurrentUrl();
    }
    
}
