package org.example.apsconsumodeagua.models;

import org.example.apsconsumodeagua.controllers.GraficoController;

public class AppModel {
    private static AppModel instance;
    private final GraficoController graficoController;
    private AppModel() {
        graficoController = new GraficoController();
    }
    public static AppModel getInstance() {
        if (instance == null) {
            instance = new AppModel();
        }
        return instance;
    }
    public GraficoController getGraficoController() {
        return graficoController;
    }
}
