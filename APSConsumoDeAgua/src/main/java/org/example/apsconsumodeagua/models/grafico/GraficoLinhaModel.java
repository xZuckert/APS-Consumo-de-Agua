package org.example.apsconsumodeagua.models.grafico;

import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public class GraficoLinhaModel extends GraficoModel{
    private final LineChart<String, Number> lineChart;
    public GraficoLinhaModel(String ano, ObservableList<XYChart.Data<String,Number>> dados) {
        super(ano, dados);
        lineChart = new LineChart<>(xAxis(), yAxis());
        lineChart.setTitle(getAno());
        lineChart.getData().add(getSeries());
        lineChart.getStyleClass().add("grafico");
    }

    public LineChart<String, Number> getChart() {
        return this.lineChart;
    }
}
