package org.example.apsconsumodeagua.models;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class Grafico {
    private String ano;
    private XYChart.Series<String, Number> series;
    private LineChart<String, Number> lineChart;

    public Grafico(String ano, XYChart.Series<String, Number> series) {
        this.ano = ano;
        this.series = series;

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();


        lineChart = new LineChart<>(xAxis, yAxis);

        yAxis.setAutoRanging(false);
        yAxis.setUpperBound(50); // Valor máximo no eixo Y
        yAxis.setTickUnit(10);    // Intervalos de marcação no eixo Y
        xAxis.setLabel("Mês");

        lineChart.setTitle(ano);
        lineChart.getData().add(series);
    }

    public LineChart<String, Number> getLineChart() {
        return lineChart;
    }

    public XYChart.Series<String, Number> getSeries() {
        return series;
    }

    public void setSeries(XYChart.Series<String, Number> series) {
        this.series = series;
    }

    public void limparDados() {
        for (XYChart.Data<String, Number> data : series.getData()) {
            data.setYValue(null);
        }
    }

    public String getAno() {
        return ano;
    }
}
