package org.example.apsconsumodeagua.models;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class Grafico {
    public String ano;
    private XYChart.Series<String, Number> series = new XYChart.Series<>();
    private LineChart<String, Number> lineChart;

    public Grafico(String ano) {
        this.ano = ano;

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        lineChart = new LineChart<>(xAxis, yAxis);

        yAxis.setAutoRanging(false);
        yAxis.setUpperBound(300); // Valor máximo no eixo Y
        yAxis.setTickUnit(50);    // Intervalos de marcação no eixo Y
        xAxis.setLabel("Mês");

        series.setName("Consumo");

        series.getData().add(new XYChart.Data<>("Jan", 0));
        series.getData().add(new XYChart.Data<>("Fev", 0));
        series.getData().add(new XYChart.Data<>("Mar", 0));
        series.getData().add(new XYChart.Data<>("Abr", 0));
        series.getData().add(new XYChart.Data<>("Mai", 0));
        series.getData().add(new XYChart.Data<>("Jun", 0));
        series.getData().add(new XYChart.Data<>("Jul", 0));
        series.getData().add(new XYChart.Data<>("Ago", 0));
        series.getData().add(new XYChart.Data<>("Set", 0));
        series.getData().add(new XYChart.Data<>("Out", 0));
        series.getData().add(new XYChart.Data<>("Nov", 0));
        series.getData().add(new XYChart.Data<>("Dez", 0));

        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.getData().add(new XYChart.Data<>("Jan", 200));
        series2.getData().add(new XYChart.Data<>("Dez", 200));

        lineChart.getData().add(series);
        lineChart.getData().add(series2);
    }

    public LineChart<String, Number> getLineChart() {
        return lineChart;
    }

    public void atualizarValorMes(String mes, int novoValor) {
        for (XYChart.Data<String, Number> data : series.getData()) {
            if (data.getXValue().equals(mes)) {
                data.setYValue(novoValor);
                break;
            }
        }
    }
}
