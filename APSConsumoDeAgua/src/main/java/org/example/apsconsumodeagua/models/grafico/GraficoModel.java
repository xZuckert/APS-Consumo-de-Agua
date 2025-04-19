package org.example.apsconsumodeagua.models.grafico;

import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public abstract class GraficoModel {
    private final XYChart.Series<String, Number> series;
    private final CategoryAxis xAxis;
    private final NumberAxis yAxis;
    private final String ano;

    public GraficoModel(String ano, ObservableList<XYChart.Data<String, Number>> dados) {
        this.ano = ano;
        this.series = new XYChart.Series<>();
        this.series.getData().setAll(dados);

        this.xAxis = new CategoryAxis();
        this.yAxis = new NumberAxis();
        configuarAxis();
    }

    void configuarAxis(){
        yAxis().setAutoRanging(false);
        yAxis().setUpperBound(50);
        yAxis().setTickUnit(10);
        xAxis().setLabel("Mês");
        yAxis().setLabel("Consumo (m³)");
    }

    public abstract Chart getChart();
    public XYChart.Series<String, Number> getSeries() {
        return series;
    }
    public String getAno() {
        return ano;
    }
    protected CategoryAxis xAxis() {
        return xAxis;
    }
    protected NumberAxis yAxis() {
        return yAxis;
    }
}
