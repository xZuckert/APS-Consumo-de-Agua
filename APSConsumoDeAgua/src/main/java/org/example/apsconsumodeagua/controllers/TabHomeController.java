package org.example.apsconsumodeagua.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.apsconsumodeagua.models.AppModel;
import org.example.apsconsumodeagua.utils.UIUtils;

public class TabHomeController {
    private final AppModel appModel = AppModel.getInstance();
    @FXML
    public AnchorPane contentTabHome,adicionarConsumo;
    @FXML
    public ComboBox<String> boxGraficos,boxMeses,boxAnos;
    @FXML
    public TextField consumoField;
    @FXML
    public LineChart<String,Number> chartTemplate;

    @FXML
    public void registrarConsumo() {
        int consumo = Integer.parseInt(consumoField.getText());
        String ano = boxAnos.getValue();
        String mes = boxMeses.getValue();
        appModel.getGraficoController().criarOuAtualizarGrafico(ano,mes,consumo,chartTemplate,appModel.getTabGraficosController().tabPaneGraficos,appModel.getRootPane(),boxGraficos);
        adicionarConsumo.setVisible(false);
    }
    @FXML
    public void abrirAdicionarConsumo() {
        UIUtils.mostrarDeslizando(adicionarConsumo,600, UIUtils.direcao.DE_BAIXO_PRA_CIMA);
    }
}
