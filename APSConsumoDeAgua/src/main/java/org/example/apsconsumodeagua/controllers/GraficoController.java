package org.example.apsconsumodeagua.controllers;

import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.example.apsconsumodeagua.core.AppModel;
import org.example.apsconsumodeagua.models.grafico.GraficoBarraModel;
import org.example.apsconsumodeagua.models.grafico.GraficoModel;
import org.example.apsconsumodeagua.services.GraficoService;
import org.example.apsconsumodeagua.utils.Toast;
import org.example.apsconsumodeagua.utils.Validadores;
import org.example.apsconsumodeagua.utils.enums.TipoGrafico;
import org.example.apsconsumodeagua.utils.enums.ToastEnum;

import java.util.Set;

public class GraficoController {
    private final AppModel appModel;
    private final GraficoService service;

    public GraficoController(GraficoService service, AppModel appModel) {
        this.appModel = appModel;
        this.service = service;
    }

    public void adicionarGraficoNaTab(String ano, TabPane tabPane, AnchorPane paneInterface) {
        if (!Validadores.tabExiste(ano, tabPane)) {
            VBox vbox = new VBox();

            Region topSpacer = new Region();
            Region bottomSpacer = new Region();

            VBox.setVgrow(topSpacer, javafx.scene.layout.Priority.ALWAYS);
            VBox.setVgrow(bottomSpacer, javafx.scene.layout.Priority.ALWAYS);
            vbox.getChildren().addAll(topSpacer, service.getGrafico(ano).getChart() , bottomSpacer);

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
    public void criarOuAtualizarGrafico(String ano, String mes, int consumo, TabPane tabPane, AnchorPane paneInterface, ComboBox<String> boxGraficos) {
        if (service.getGrafico(ano) != null) {
            service.atualizarDados(ano, mes, consumo);
        } else {
            service.gerarGrafico(ano, TipoGrafico.BARRA);
            service.atualizarDados(ano, mes, consumo);
            adicionarGraficoNaTab(ano, tabPane, paneInterface);
        }
        GraficoModel graficoGerado = service.getGrafico(ano);
        if(graficoGerado instanceof GraficoBarraModel) {((GraficoBarraModel) graficoGerado).atualizarYAxis();}
        atualizarBoxGraficos(boxGraficos);
        selecionarGrafico(ano,boxGraficos);
    }
    public void selecionarGrafico(String ano, ComboBox<String> boxGraficos) {
        boxGraficos.selectionModelProperty().get().select(ano);
        selecionarGrafico(ano);
    }
    public void selecionarGrafico(String ano) {
        if (!service.valores.containsKey(ano)) {
            service.gerarGrafico(ano, TipoGrafico.LINHA);
        }
        GraficoModel graficoClone = service.clonarGrafico(ano);
        if(graficoClone instanceof GraficoBarraModel) {((GraficoBarraModel) graficoClone).atualizarYAxis();}
        appModel.getTabHomeController().exibirGraficoClone(graficoClone.getChart());
    }
    public void atualizarBoxGraficos(ComboBox<String> boxGraficos) {
        Set<String> anos = getAnos();
        SingleSelectionModel<String> selectionModel = boxGraficos.getSelectionModel();
        boxGraficos.getItems().setAll(anos);
        if (!anos.isEmpty() && selectionModel.getSelectedItem() == null) {
            selectionModel.selectLast();
        }
    }

    public Set<String> getAnos() {
        return service.getAnos();
    }

    public GraficoModel getGrafico(String ano) {
        return service.getGrafico(ano);
    }

}

