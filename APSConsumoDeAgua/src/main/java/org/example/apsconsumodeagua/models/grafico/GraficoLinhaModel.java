package org.example.apsconsumodeagua.models.grafico;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.example.apsconsumodeagua.models.DadosGrafico;

public class GraficoLinhaModel {
    private final LineChart<String, Number> lineChart;
    private final XYChart.Series<String, Number> series;
    public GraficoLinhaModel(String ano, DadosGrafico dadosGrafico) {
        this.series = new XYChart.Series<>();
        series.getData().setAll(dadosGrafico.getDados());
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
        return this.lineChart;
    }
    public XYChart.Series<String, Number> getSeries() {return this.lineChart.getData().getFirst();}
}
