package org.example.apsconsumodeagua.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class DadosGrafico {
    private final ObservableList<XYChart.Data<String,Number>> dados = FXCollections.observableArrayList();
    public DadosGrafico() {
        dados.add(new XYChart.Data<>("Jan",0));
        dados.add(new XYChart.Data<>("Fev",0));
        dados.add(new XYChart.Data<>("Mar",0));
        dados.add(new XYChart.Data<>("Abr",0));
        dados.add(new XYChart.Data<>("Mai",0));
        dados.add(new XYChart.Data<>("Jun",0));
        dados.add(new XYChart.Data<>("Jul",0));
        dados.add(new XYChart.Data<>("Ago",0));
        dados.add(new XYChart.Data<>("Set",0));
        dados.add(new XYChart.Data<>("Out",0));
        dados.add(new XYChart.Data<>("Nov",0));
        dados.add(new XYChart.Data<>("Dez",0));
    }
    public ObservableList<XYChart.Data<String, Number>> getDados() {
        return this.dados;
    }
}
