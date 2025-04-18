package org.example.apsconsumodeagua.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import org.example.apsconsumodeagua.models.AppModel;
import org.example.apsconsumodeagua.utils.Constantes;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.*;

public class AplicacaoController implements Initializable {
    private final AppModel appModel = AppModel.getInstance();
    private TabController tabController;

    @FXML
    private AnchorPane paneInterface;
    @FXML
    private ToggleButton tabUsuario, tabHome, tabGraficos;

    //( Metodos chamados ao inicializar o fxml )----------------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.tabController = new TabController(tabUsuario,tabHome,tabGraficos);
        appModel.setRootPane(paneInterface);
        appModel.setTabUsuarioController((TabUsuarioController) carregarTela(Constantes.TAB_USUARIO));
        appModel.setTabHomeController((TabHomeController) carregarTela(Constantes.TAB_HOME));
        appModel.setTabGraficosController((TabGraficosController) carregarTela(Constantes.TAB_GRAFICOS));
        inicializarGraficoAtual();
        inicializarBoxAnos();
        inicializarListeners();
        paneInterface.getChildren().addFirst(appModel.getPane(Constantes.TAB_HOME));
    }
    private void inicializarGraficoAtual(){
        String anoAtual = String.valueOf(Year.now().getValue());
        if (appModel.getGraficoController().getGrafico(anoAtual) == null) {
            String mesAtual = LocalDate.now().getMonth().getDisplayName(TextStyle.SHORT, Locale.getDefault());
            appModel.getGraficoController().criarOuAtualizarGrafico(anoAtual,mesAtual,0,appModel.getTabHomeController().chartTemplate,appModel.getTabGraficosController().tabPaneGraficos,paneInterface,appModel.getTabHomeController().boxGraficos);
        }
    }
    private void inicializarBoxAnos(){
        for (int i = Year.now().getValue(); i >= Year.now().getValue() - Constantes.ANOS_ANTERIORES; i--) {
            appModel.getTabHomeController().boxAnos.getItems().add(String.valueOf(i));
        }
    }
    private void inicializarListeners(){
        appModel.getTabHomeController().boxAnos.valueProperty().addListener((obs, valorAntigo, valorNovo) -> atualizarBoxMeses(valorNovo));
        appModel.getTabHomeController().boxGraficos.valueProperty().addListener((obs, valorAntigo, valorNovo) -> appModel.getGraficoController().selecionarGrafico(valorNovo,appModel.getTabHomeController().chartTemplate,appModel.getTabHomeController().boxGraficos));
    }
    //----------------------------------------------------------------------------------------------------------------------

    //( Métodos chamados pelo usuário ao acionar algum evento )-------------------------------------------------------------
    @FXML
    public void trocarTab(ActionEvent event) {
        ToggleButton botaoClicado = (ToggleButton) event.getSource();
        tabController.alternarAba(botaoClicado);
    }
    //----------------------------------------------------------------------------------------------------------------------

    //( Métodos de atualização dos ComboBox )-------------------------------------------------------------------------------
    private void atualizarBoxMeses(String ano) {
        int limiteMeses = 12;
        if (ano.equals(String.valueOf(Year.now().getValue()))) {
            limiteMeses = LocalDate.now().getMonth().getValue();
        }
        List<String> meses = new ArrayList<>(Arrays.asList(Constantes.MESES).subList(0, limiteMeses));
        appModel.getTabHomeController().boxMeses.getItems().setAll(meses);
        appModel.getTabHomeController().boxMeses.setDisable(false);
    }
    //----------------------------------------------------------------------------------------------------------------------

    private Object carregarTela(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load(); // Isso carrega o conteúdo
            Object controller = loader.getController();

            // Se quiser guardar o root em algum lugar para usar depois:
            appModel.addPane(fxmlPath, root);

            return controller;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //( Métodos para alterar o conteúdo dos Fields )------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------
}