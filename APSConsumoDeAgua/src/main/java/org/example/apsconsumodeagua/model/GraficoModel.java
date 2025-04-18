package org.example.apsconsumodeagua.model;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class GraficoModel {
    private final LineChart<String, Number> lineChart;

    public GraficoModel(String ano, XYChart.Series<String, Number> series) {

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();


        lineChart = new LineChart<>(xAxis, yAxis);

        yAxis.setAutoRanging(false);
        yAxis.setUpperBound(50); // Valor máximo no eixo Y
        yAxis.setTickUnit(10);    // Intervalos de marcação no eixo Y
        xAxis.setLabel("Mês");

        lineChart.setTitle(ano);
        lineChart.getData().add(series);
        lineChart.getStyleClass().add("grafico");
    }

    public LineChart<String, Number> getLineChart() {
        return lineChart;
    }
}
