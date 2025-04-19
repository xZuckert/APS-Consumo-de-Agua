package org.example.apsconsumodeagua.models.grafico;

import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;


public class GraficoBarraModel extends GraficoModel{
    private final BarChart<String, Number> barChart;
    public GraficoBarraModel(String ano, ObservableList<XYChart.Data<String,Number>> dados) {
        super(ano, dados);
        barChart = new BarChart<>(xAxis(), yAxis());
        barChart.setTitle(getAno());
        barChart.getData().add(getSeries());
        barChart.getStyleClass().add("grafico");
    }

    public void atualizarYAxis(){
        double valorMaximoY;
        double maxValor = this.getSeries().getData().stream()
                .mapToDouble(data -> data.getYValue().doubleValue())
                .max()
                .orElse(50);
        valorMaximoY = Math.ceil(maxValor / 10) * 10 + 10;
        yAxis().setUpperBound(Math.max(valorMaximoY, 50));
    }

    @Override
    public BarChart<String,Number> getChart() {
        return barChart;
    }
}
