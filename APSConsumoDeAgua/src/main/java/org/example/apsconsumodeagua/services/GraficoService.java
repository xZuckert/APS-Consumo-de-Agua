package org.example.apsconsumodeagua.services;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.example.apsconsumodeagua.models.DadosGrafico;
import org.example.apsconsumodeagua.models.grafico.GraficoLinhaModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GraficoService {
    private final Map<String, GraficoLinhaModel> graficos = new HashMap<>();
    private final Map<String, DadosGrafico> dados = new HashMap<>();


    //(Métodos chamados para gerar novos graficos)--------------------------------------------------------------------
    public void gerarGrafico(String ano) {
        dados.put(ano, new DadosGrafico());
        graficos.put(ano, new GraficoLinhaModel(ano, dados.get(ano)));
    }

    //(Métodos chamados para manipular dados)-----------------------------------------------------------------------------
    public void atualizarDados(String ano,String mes, int consumo) {
        for (XYChart.Data<String, Number> dado : dados.get(ano).getDados()) {
            if(dado.getXValue().equals(mes)) {
                dado.setYValue(consumo);
            }
        }
    }
    //(Métodos chamados para manipular graficos)--------------------------------------------------------------------------
    public void selecionarGrafico(String ano, LineChart<String, Number> chart) {
        if (!dados.containsKey(ano)) {
            gerarGrafico(ano);
        }
        chart.getData().clear();
        chart.setTitle(ano);
        chart.getData().add(clonarSerie(ano));
        chart.getYAxis().setAutoRanging(false);
        chart.getXAxis().setLabel("Mês");
        ((NumberAxis) chart.getYAxis()).setUpperBound(50);
        ((NumberAxis) chart.getYAxis()).setTickUnit(10);
    }

    private XYChart.Series<String, Number> clonarSerie(String ano) {
        XYChart.Series<String, Number> serieOriginal = graficos.get(ano).getSeries();
        XYChart.Series<String, Number> novaSerie = new XYChart.Series<>();

        for (XYChart.Data<String, Number> data : serieOriginal.getData()) {
            novaSerie.getData().add(new XYChart.Data<>(data.getXValue(), data.getYValue()));
        }

        return novaSerie;
    }


//----------------------------------------------------------------------------------------------------------------------

//( Métodos chamados para pegar dados e graficos )----------------------------------------------------------------------
    public Set<String> getKeys() {
        return graficos.keySet();
    }

    public GraficoLinhaModel getGrafico(String ano) {
        return graficos.get(ano);
    }
//----------------------------------------------------------------------------------------------------------------------
}

