package org.example.apsconsumodeagua.models;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ListaDeGraficos {
    private Map<String, Grafico> graficos = new HashMap<>();
    private Map<String, XYChart.Series<String, Number>> series = new HashMap<>();

    public void gerarGrafico(String ano, String mes, int consumo) {
        series.put(ano, gerarSeries(mes, consumo));
        graficos.put(ano, new Grafico(ano, series.get(ano)));
    }

    private XYChart.Series<String, Number> gerarSeries(String mes, int consumo) {
        XYChart.Series<String, Number> series;
        series = new XYChart.Series<>();

        series.setName("Consumo");

        series.getData().add(new XYChart.Data<>("Jan", null));
        series.getData().add(new XYChart.Data<>("Fev", null));
        series.getData().add(new XYChart.Data<>("Mar", null));
        series.getData().add(new XYChart.Data<>("Abr", null));
        series.getData().add(new XYChart.Data<>("Mai", null));
        series.getData().add(new XYChart.Data<>("Jun", null));
        series.getData().add(new XYChart.Data<>("Jul", null));
        series.getData().add(new XYChart.Data<>("Ago", null));
        series.getData().add(new XYChart.Data<>("Set", null));
        series.getData().add(new XYChart.Data<>("Out", null));
        series.getData().add(new XYChart.Data<>("Nov", null));
        series.getData().add(new XYChart.Data<>("Dez", null));

        if (mes != null) {
            for (XYChart.Data<String, Number> data : series.getData()) {
                if (data.getXValue().equals(mes)) {
                    data.setYValue(consumo);
                    break;
                }
            }
        }
        return series;
    }

    public void atualizarValorMes(String ano, String mes, int novoValor) {
        if (series.containsKey(ano)) {
            XYChart.Series<String, Number> serie = series.get(ano);

            for (XYChart.Data<String, Number> data : serie.getData()) {
                if (data.getXValue().equals(mes)) {
                    data.setYValue(novoValor);
                    break;
                }
            }

            if (graficos.containsKey(ano)) {
                Grafico graficoAno = graficos.get(ano);

                // REMOVE a série antiga e adiciona a atualizada
                LineChart<String, Number> chart = graficoAno.getLineChart();
                chart.getData().clear(); // Limpa as séries existentes
                chart.getData().add(serie); // Adiciona a série atualizada
            }
        }
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Anos disponíveis:\n");
        for (String ano : graficos.keySet()) {
            sb.append("- ").append(ano).append("\n");
        }
        return sb.toString();
    }

    public Set<String> getKeys() {
        return graficos.keySet();
    }

    public XYChart.Series<String, Number> getSerie(String ano) {
        return series.get(ano);
    }

    public Grafico getGrafico(String ano) {
        return graficos.get(ano);
    }
}

