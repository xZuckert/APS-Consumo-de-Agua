package org.example.apsconsumodeagua.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.example.apsconsumodeagua.utils.Toast;
import org.example.apsconsumodeagua.utils.Validadores;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
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
    private Button btnRegistrar;
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
    private TextField txtConsumo;
    @FXML
    private DatePicker dtpDataConta;


    @FXML
    private void onBTNAdicionarContaClick (ActionEvent event) {
        apnAdicionarConta.setVisible(true);
    }
    @FXML
    private void onBTNRegistrar(ActionEvent event) {
        String consumoMes = txtConsumo.getText();
        LocalDate dataConsumo = dtpDataConta.getValue();
        try {
            int consumo = Validadores.pegarConsumo(consumoMes);
            System.out.println(Validadores.pegarMes(dataConsumo));
            System.out.println(consumo);
        } catch (NumberFormatException e) {
            Toast.mostrarToast(paneInterface, "Inválido", Toast.tipoToast.ERRO);
        }
        apnAdicionarConta.setVisible(false);
        //Toast.mostrarToast(paneInterface,"Adicionado com sucesso", Toast.tipoToast.SUCESSO);
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

        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.getData().add(new XYChart.Data<>("Jan", 200));
        series2.getData().add(new XYChart.Data<>("Dez", 200));

        for (XYChart.Data<String, Number> data : series.getData()) {
            Tooltip tooltip = new Tooltip(
                    "X: " + data.getXValue() + "\nY: " + data.getYValue()
            );
            Tooltip.install(data.getNode(), tooltip);
        }

        lchConsumoAgua.getData().add(series);
        lchConsumoAgua.getData().add(series2);
    }

}