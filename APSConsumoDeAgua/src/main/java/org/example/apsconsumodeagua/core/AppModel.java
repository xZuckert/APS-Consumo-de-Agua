package org.example.apsconsumodeagua.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import org.example.apsconsumodeagua.controllers.GraficoController;
import org.example.apsconsumodeagua.controllers.TabGraficosController;
import org.example.apsconsumodeagua.controllers.TabHomeController;
import org.example.apsconsumodeagua.controllers.TabUsuarioController;
import org.example.apsconsumodeagua.factory.GraficoFactory;
import org.example.apsconsumodeagua.managers.TabManager;
import org.example.apsconsumodeagua.services.GraficoService;
import org.example.apsconsumodeagua.utils.constantes.CaminhoFxml;
import org.example.apsconsumodeagua.utils.constantes.AppConstantes;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AppModel {
    private static AppModel instance;
    private final GraficoFactory graficoFactory;
    private final GraficoService graficoService;
    private final GraficoController graficoController;
    private TabManager tabManager;

    private final Map<String, Parent> roots = new HashMap<>();

    private TabUsuarioController tabUsuarioController;
    private TabHomeController tabHomeController;
    private TabGraficosController tabGraficosController;

    private AnchorPane rootPane;

    private AppModel() {
        graficoFactory = new GraficoFactory();
        graficoService = new GraficoService(graficoFactory);
        graficoController = new GraficoController(graficoService,this);
    }

    public void carregarAplicacao(ToggleButton ... tabs) {
        tabManager = new TabManager(tabs[0], tabs[1], tabs[2]);
        tabUsuarioController = carregarFXMLComController(CaminhoFxml.TAB_USUARIO);
        tabHomeController = carregarFXMLComController(CaminhoFxml.TAB_HOME);
        tabGraficosController = carregarFXMLComController(CaminhoFxml.TAB_GRAFICOS);
        inicializarGraficoAtual();
        inicializarBoxAnos();
        inicializarListeners();
        tabManager.inicializarComTabInicial(CaminhoFxml.TAB_HOME);
    }
    public <T> T carregarFXMLComController(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            addTela(fxmlPath, root);

            return loader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void inicializarGraficoAtual(){
        String anoAtual = String.valueOf(Year.now().getValue());
        if (getGraficoController().getGrafico(anoAtual) == null) {
            String mesAtual = LocalDate.now().getMonth().getDisplayName(TextStyle.SHORT, Locale.getDefault());
            getGraficoController().criarOuAtualizarGrafico(anoAtual,mesAtual,0,getTabGraficosController().tabPaneGraficos,getRootPane(),getTabHomeController().boxGraficos);
        }
    }
    private void inicializarBoxAnos(){
        for (int i = Year.now().getValue(); i >= Year.now().getValue() - AppConstantes.ANOS_ANTERIORES; i--) {
            getTabHomeController().boxAnos.getItems().add(String.valueOf(i));
        }
    }
    private void inicializarListeners(){
        getTabHomeController().boxAnos.valueProperty().addListener((obs, valorAntigo, valorNovo) -> getTabHomeController().atualizarBoxMeses(valorNovo));
        getTabHomeController().boxGraficos.valueProperty().addListener((obs, valorAntigo, valorNovo) -> getGraficoController().selecionarGrafico(valorNovo,getTabHomeController().boxGraficos));
    }

    public void trocarTela(String fxmlPath) {
        getRootPane().getChildren().set(0,getTela(fxmlPath));
    }
    public GraficoController getGraficoController() {
        return this.graficoController;
    }

    public TabUsuarioController getTabUsuarioController() {
        return this.tabUsuarioController;
    }

    public TabHomeController getTabHomeController() {
        return this.tabHomeController;
    }

    public TabGraficosController getTabGraficosController() {
        return this.tabGraficosController;
    }

    public void addTela(String key, Parent root) {
        roots.put(key, root);
    }

    public Parent getTela(String key) {
        return roots.get(key);
    }

    public AnchorPane getRootPane() {
        return rootPane;
    }
    public TabManager getTabManager() {
        return tabManager;
    }

    public void setRootPane(AnchorPane rootPane) {
        this.rootPane = rootPane;
    }

    public static synchronized AppModel getInstance() {
        if (instance == null) {
            instance = new AppModel();
        }
        return instance;
    }
}
