package org.example.apsconsumodeagua.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import org.example.apsconsumodeagua.core.AppModel;
import org.example.apsconsumodeagua.managers.GraficoManager;
import org.example.apsconsumodeagua.models.base.GraficoModel;
import org.example.apsconsumodeagua.utils.Toast;
import org.example.apsconsumodeagua.utils.UIUtils;
import org.example.apsconsumodeagua.utils.Validadores;
import org.example.apsconsumodeagua.utils.enums.ToastEnum;

//(Classe que controla a view Graficos)---------------------------------------------------------------------------------
public class TabGraficosController {
    AppModel appModel = AppModel.getInstance();
    GraficoManager manager = appModel.getGraficoManager();
    @FXML
    public AnchorPane contentTabGraficos;
    @FXML
    public TabPane tabPaneGraficos;

    //(Função chamada para adicionar novos tabs)------------------------------------------------------------------------
    private void criarTab(String titulo, Node conteudo){
        Tab tab = new Tab(titulo);
        tab.setContent(conteudo);
        tabPaneGraficos.getTabs().add(tab);
    }

    //(Função chamada para adicionar novos graficos nos tabs)-----------------------------------------------------------
    public void adicionarGraficoNaTab(String ano,AnchorPane paneInterface) {
        GraficoModel grafico = manager.getGrafico(ano);
        if (!Validadores.tabExiste(ano, tabPaneGraficos)) {
            criarTab(ano, UIUtils.criarNodeCentralizadoVerticalmente(grafico.getChart()));
            Toast.mostrarToast(paneInterface, "Grafico adicionado!", ToastEnum.SUCESSO, 100, 320);
        }
    }
}
