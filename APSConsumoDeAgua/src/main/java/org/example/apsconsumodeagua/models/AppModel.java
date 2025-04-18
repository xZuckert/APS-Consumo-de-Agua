package org.example.apsconsumodeagua.models;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import org.example.apsconsumodeagua.controllers.GraficoController;
import org.example.apsconsumodeagua.controllers.TabGraficosController;
import org.example.apsconsumodeagua.controllers.TabHomeController;
import org.example.apsconsumodeagua.controllers.TabUsuarioController;
import org.example.apsconsumodeagua.utils.CaminhoFxml;
import org.example.apsconsumodeagua.utils.Constantes;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AppModel {
    private static AppModel instance;
    private final Map<String, Parent> roots = new HashMap<>();
    private final GraficoController graficoController;
    private TabUsuarioController tabUsuarioController;
    private TabHomeController tabHomeController;
    private TabGraficosController tabGraficosController;

    private AnchorPane rootPane;

    private AppModel() {
        graficoController = new GraficoController();
    }
    public void carregarAplicacao() {
        tabUsuarioController = (TabUsuarioController) carregarTela(CaminhoFxml.TAB_USUARIO);
        tabHomeController = (TabHomeController) carregarTela(CaminhoFxml.TAB_HOME);
        tabGraficosController = (TabGraficosController) carregarTela(CaminhoFxml.TAB_GRAFICOS);
        inicializarGraficoAtual();
        inicializarBoxAnos();
        inicializarListeners();
        getRootPane().getChildren().addFirst(getPane(CaminhoFxml.TAB_HOME));
    }
    public Object carregarTela(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Object controller = loader.getController();

            addPane(fxmlPath, root);

            return controller;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void inicializarGraficoAtual(){
        String anoAtual = String.valueOf(Year.now().getValue());
        if (getGraficoController().getGrafico(anoAtual) == null) {
            String mesAtual = LocalDate.now().getMonth().getDisplayName(TextStyle.SHORT, Locale.getDefault());
            getGraficoController().criarOuAtualizarGrafico(anoAtual,mesAtual,0,getTabHomeController().chartTemplate,getTabGraficosController().tabPaneGraficos,getRootPane(),getTabHomeController().boxGraficos);
        }
    }
    private void inicializarBoxAnos(){
        for (int i = Year.now().getValue(); i >= Year.now().getValue() - Constantes.ANOS_ANTERIORES; i--) {
            getTabHomeController().boxAnos.getItems().add(String.valueOf(i));
        }
    }
    private void inicializarListeners(){
        getTabHomeController().boxAnos.valueProperty().addListener((obs, valorAntigo, valorNovo) -> getTabHomeController().atualizarBoxMeses(valorNovo));
        getTabHomeController().boxGraficos.valueProperty().addListener((obs, valorAntigo, valorNovo) -> getGraficoController().selecionarGrafico(valorNovo,getTabHomeController().chartTemplate,getTabHomeController().boxGraficos));
    }

    public GraficoController getGraficoController() {
        return graficoController;
    }

    public TabUsuarioController getTabUsuarioController() {
        return tabUsuarioController;
    }

    public TabHomeController getTabHomeController() {
        return tabHomeController;
    }

    public TabGraficosController getTabGraficosController() {
        return tabGraficosController;
    }

    public void addPane(String key, Parent root) {
        roots.put(key, root);
    }

    public Parent getPane(String key) {
        return roots.get(key);
    }

    public AnchorPane getRootPane() {
        return rootPane;
    }

    public void setRootPane(AnchorPane rootPane) {
        this.rootPane = rootPane;
    }

    public static AppModel getInstance() {
        if (instance == null) {
            instance = new AppModel();
        }
        return instance;
    }
}
