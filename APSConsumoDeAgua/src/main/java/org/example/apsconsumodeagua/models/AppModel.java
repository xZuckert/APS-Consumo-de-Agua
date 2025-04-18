package org.example.apsconsumodeagua.models;

import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import org.example.apsconsumodeagua.controllers.GraficoController;
import org.example.apsconsumodeagua.controllers.TabGraficosController;
import org.example.apsconsumodeagua.controllers.TabHomeController;
import org.example.apsconsumodeagua.controllers.TabUsuarioController;

import java.util.HashMap;
import java.util.Map;

public class AppModel {
    private static AppModel instance;
    private final Map<String, Parent> roots = new HashMap<>();
    private final GraficoController graficoController;
    private TabUsuarioController tabUsuarioController;
    private TabHomeController tabHomeController;
    private TabGraficosController tabGraficosController;

    private AnchorPane rootPane;
    private ComboBox<String> boxAnos, boxMeses, boxGraficos;

    private AppModel() {
        graficoController = new GraficoController();
    }

    public GraficoController getGraficoController() {
        return graficoController;
    }

    public ComboBox<String> getBoxAnos() {
        return boxAnos;
    }

    public void setBoxAnos(ComboBox<String> boxAnos) {
        this.boxAnos = boxAnos;
    }

    public ComboBox<String> getBoxMeses() {
        return boxMeses;
    }

    public void setBoxMeses(ComboBox<String> boxMeses) {
        this.boxMeses = boxMeses;
    }

    public ComboBox<String> getBoxGraficos() {
        return boxGraficos;
    }

    public void setBoxGraficos(ComboBox<String> boxGraficos) {
        this.boxGraficos = boxGraficos;
    }

    public TabUsuarioController getTabUsuarioController() {
        return tabUsuarioController;
    }

    public void setTabUsuarioController(TabUsuarioController tabUsuarioController) {
        this.tabUsuarioController = tabUsuarioController;
    }

    public TabHomeController getTabHomeController() {
        return tabHomeController;
    }

    public void setTabHomeController(TabHomeController tabHomeController) {
        this.tabHomeController = tabHomeController;
    }

    public TabGraficosController getTabGraficosController() {
        return tabGraficosController;
    }

    public void setTabGraficosController(TabGraficosController tabGraficosController) {
        this.tabGraficosController = tabGraficosController;
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
