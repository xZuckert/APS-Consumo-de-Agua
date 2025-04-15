package org.example.apsconsumodeagua.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private AnchorPane paneInterface;
    @FXML
    private TabPane tbpTelaInicial;
    //Menu
    @FXML
    private Button btnAdicionarConta;
    @FXML
    private Button btnAdicionar;
    @FXML
    private Label lblRecomendacao;
    @FXML
    private LineChart<String, Number> lchConsumoAgua;
    @FXML
    private AnchorPane apnAdicionarConta;
    @FXML
    private AnchorPane apnSucesso;
    @FXML
    private Button btnSucesso;


    @FXML
    private void onBTNAdicionarContaClick (ActionEvent event) {
        apnAdicionarConta.setVisible(true);
    }
    @FXML
    private void onBTNAdicionar (ActionEvent event) {
        apnSucesso.setVisible(true);
    }
    @FXML
    private void onBTNSucessoClick (ActionEvent event) {
        apnSucesso.setVisible(false);
        apnAdicionarConta.setVisible(false);
    }



    @Override

    public void initialize(URL location , ResourceBundle resources) {
        tbpTelaInicial.getSelectionModel().select(1);


        // Configura os eixos
        CategoryAxis xAxis = (CategoryAxis) lchConsumoAgua.getXAxis();
        NumberAxis yAxis = (NumberAxis) lchConsumoAgua.getYAxis();
        yAxis.setAutoRanging(false);
        yAxis.setUpperBound(300);//Aqui fica o valor maximo da coluna Y
        yAxis.setTickUnit(50); //Aqui fica o valor da coluna Y
        xAxis.setLabel("Mês");

        // Criando a série de dados
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Consumo");

        series.getData().add(new XYChart.Data<>("Jan", 121));
        series.getData().add(new XYChart.Data<>("Fev", 135));
        series.getData().add(new XYChart.Data<>("Mar", 215));
        series.getData().add(new XYChart.Data<>("Abr", 144));
        series.getData().add(new XYChart.Data<>("Mai", 115));
        series.getData().add(new XYChart.Data<>("Jun", 169));
        series.getData().add(new XYChart.Data<>("Jul", 128));
        series.getData().add(new XYChart.Data<>("Ago", 115));
        series.getData().add(new XYChart.Data<>("Set", 106));
        series.getData().add(new XYChart.Data<>("Out", 105));
        series.getData().add(new XYChart.Data<>("Nov", 168));
        series.getData().add(new XYChart.Data<>("Dez", 125));

        lchConsumoAgua.getData().add(series);
    }

}