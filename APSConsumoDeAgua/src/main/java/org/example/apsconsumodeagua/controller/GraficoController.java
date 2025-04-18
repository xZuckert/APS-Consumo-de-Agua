package org.example.apsconsumodeagua.controller;

import javafx.scene.chart.LineChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.example.apsconsumodeagua.model.GraficoModel;
import org.example.apsconsumodeagua.service.GraficoService;
import org.example.apsconsumodeagua.utils.uiutils.Toast;
import org.example.apsconsumodeagua.utils.Validadores;
import org.example.apsconsumodeagua.utils.enums.ToastEnum;

import java.util.Set;

public class GraficoController {
    private final GraficoService service;

    public GraficoController() {
        this.service = new GraficoService();
    }

    public void criarOuAtualizarGrafico(String ano, String mes, int consumo, LineChart<String, Number> chart, TabPane tabPane, AnchorPane paneInterface, ComboBox<String> boxGraficos) {
        if (service.getGrafico(ano) != null) {
            service.atualizarValorMes(ano, mes, consumo);
        } else {
            service.gerarGrafico(ano, mes, consumo);
            adicionarGraficoNaTab(ano, tabPane, paneInterface);
        }
        atualizarBoxGraficos(boxGraficos);
        selecionarGrafico(ano,chart,boxGraficos);
    }

    private void adicionarGraficoNaTab(String ano, TabPane tabPane, AnchorPane paneInterface) {
        if (!Validadores.tabExiste(ano, tabPane)) {
            GraficoModel graficoModel = service.getGrafico(ano);
            LineChart<String, Number> graficoTab = graficoModel.getLineChart();

            VBox vbox = new VBox();

            Region topSpacer = new Region();
            Region bottomSpacer = new Region();

            VBox.setVgrow(topSpacer, javafx.scene.layout.Priority.ALWAYS);
            VBox.setVgrow(bottomSpacer, javafx.scene.layout.Priority.ALWAYS);
            vbox.getChildren().addAll(topSpacer, graficoTab, bottomSpacer);

            AnchorPane graficoContainer = new AnchorPane(vbox);
            AnchorPane.setTopAnchor(vbox, 10.0);
            AnchorPane.setBottomAnchor(vbox, 10.0);
            AnchorPane.setLeftAnchor(vbox, 10.0);
            AnchorPane.setRightAnchor(vbox, 10.0);

            Tab tab = new Tab(ano);
            tab.setContent(graficoContainer);
            tabPane.getTabs().add(tab);
            Toast.mostrarToast(paneInterface, "Grafico adicionado!", ToastEnum.SUCESSO, 100, 320);
        }
    }
    private void atualizarBoxGraficos(ComboBox<String> boxGraficos) {
        Set<String> anos = getAnos();
        SingleSelectionModel<String> selectionModel = boxGraficos.getSelectionModel();
        boxGraficos.getItems().setAll(anos);
        if (!anos.isEmpty() && selectionModel.getSelectedItem() == null) {
            selectionModel.selectLast();
        }
    }

    public void selecionarGrafico(String ano, LineChart<String, Number> chartTemplate, ComboBox<String> boxGraficos) {
        boxGraficos.selectionModelProperty().get().select(ano);
        service.selecionarGrafico(ano, chartTemplate, service);
    }

    public Set<String> getAnos() {
        return service.getKeys();
    }

    public GraficoModel getGrafico(String ano) {
        return service.getGrafico(ano);
    }

}

