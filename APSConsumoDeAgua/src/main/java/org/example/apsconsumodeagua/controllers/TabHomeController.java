package org.example.apsconsumodeagua.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.Chart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.example.apsconsumodeagua.core.AppModel;
import org.example.apsconsumodeagua.utils.constantes.AppConstantes;
import org.example.apsconsumodeagua.utils.UIUtils;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabHomeController {
    private final AppModel appModel = AppModel.getInstance();
    @FXML
    public VBox vBox;
    @FXML
    public AnchorPane contentTabHome,adicionarConsumo;
    @FXML
    public ComboBox<String> boxGraficos,boxMeses,boxAnos;
    @FXML
    public TextField consumoField;

    @FXML
    public void registrarConsumo() {
        int consumo = Integer.parseInt(consumoField.getText());
        String ano = boxAnos.getValue();
        String mes = boxMeses.getValue();
        appModel.getGraficoController().criarOuAtualizarGrafico(ano,mes,consumo,appModel.getTabGraficosController().tabPaneGraficos,appModel.getRootPane(),boxGraficos);
        adicionarConsumo.setVisible(false);
    }
    @FXML
    public void abrirAdicionarConsumo() {
        UIUtils.mostrarDeslizando(adicionarConsumo,600, UIUtils.direcao.DE_BAIXO_PRA_CIMA);
    }

    public void atualizarBoxMeses(String ano) {
        int limiteMeses = 12;
        if (ano.equals(String.valueOf(Year.now().getValue()))) {
            limiteMeses = LocalDate.now().getMonth().getValue();
        }
        List<String> meses = new ArrayList<>(Arrays.asList(AppConstantes.MESES).subList(0, limiteMeses));
        boxMeses.getItems().setAll(meses);
        boxMeses.setDisable(false);
    }
    public void exibirGraficoClone(Chart chart) {
        if (vBox.getChildren().size() < 4) {
            vBox.getChildren().add(2, chart);
        } else {
            vBox.getChildren().set(2, chart);
        }
    }
}
