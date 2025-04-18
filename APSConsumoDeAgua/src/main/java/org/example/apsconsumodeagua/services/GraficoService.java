package org.example.apsconsumodeagua.services;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.example.apsconsumodeagua.models.GraficoModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GraficoService {
    private final Map<String, GraficoModel> graficos = new HashMap<>();
    private final Map<String, XYChart.Series<String, Number>> series = new HashMap<>();

//( Métodos chamados para adicionar novos graficos )--------------------------------------------------------------------
    public void gerarGrafico(String ano, String mes, int consumo) {
        series.put(ano, gerarSeries(mes, consumo));
        graficos.put(ano, new GraficoModel(ano, series.get(ano)));
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
//----------------------------------------------------------------------------------------------------------------------

//( Métodos chamados para manipular dados )-----------------------------------------------------------------------------
    public XYChart.Series<String, Number> clonarSeries(XYChart.Series<String, Number> original) {
        XYChart.Series<String, Number> copia = new XYChart.Series<>();
        for (XYChart.Data<String, Number> data : original.getData()) {
            copia.getData().add(new XYChart.Data<>(data.getXValue(), data.getYValue()));
        }
        copia.setName(original.getName());
        return copia;
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
                GraficoModel graficoModelAno = graficos.get(ano);

                // REMOVE a série antiga e adiciona a atualizada
                LineChart<String, Number> chart = graficoModelAno.getLineChart();
                chart.getData().clear(); // Limpa as séries existentes
                chart.getData().add(serie); // Adiciona a série atualizada
            }
        }
    }
//----------------------------------------------------------------------------------------------------------------------

//( Métodos chamados para manipular graficos )--------------------------------------------------------------------------
    public void selecionarGrafico(String ano, LineChart<String, Number> chart,GraficoService graficoService) {
        chart.getData().clear();
        chart.setTitle(ano);
        XYChart.Series<String, Number> serie = graficoService.getSerie(ano);
        if (serie != null) {
            chart.getData().add(graficoService.clonarSeries(serie));
        }

        chart.getYAxis().setAutoRanging(false);
        chart.getXAxis().setLabel("Mês");
        ((NumberAxis) chart.getYAxis()).setUpperBound(50);
        ((NumberAxis) chart.getYAxis()).setTickUnit(10);

    }
//----------------------------------------------------------------------------------------------------------------------

//( Métodos chamados para pegar dados e graficos )----------------------------------------------------------------------
    public Set<String> getKeys() {
        return graficos.keySet();
    }
    public XYChart.Series<String, Number> getSerie(String ano) {
        return series.get(ano);
    }
    public GraficoModel getGrafico(String ano) {
        return graficos.get(ano);
    }
//----------------------------------------------------------------------------------------------------------------------
}

