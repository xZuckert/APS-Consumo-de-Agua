package org.example.apsconsumodeagua.models.base;

import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.example.apsconsumodeagua.utils.enums.TipoGrafico;

//(Classe base para os graficos)----------------------------------------------------------------------------------------
public abstract class GraficoModel {
    private final XYChart.Series<String, Number> series;
    private final TipoGrafico tipoGrafico;
    private final CategoryAxis xAxis;
    private final NumberAxis yAxis;
    private final String ano;

    //(Construtor da classe)--------------------------------------------------------------------------------------------
    public GraficoModel(String ano, ObservableList<XYChart.Data<String, Number>> dados, TipoGrafico tipoGrafico) {
        this.ano = ano;
        this.tipoGrafico = tipoGrafico;
        this.series = new XYChart.Series<>();
        this.series.getData().setAll(dados);
        this.series.setName("Consumo");
        this.xAxis = new CategoryAxis();
        this.yAxis = new NumberAxis();
        configuarAxis();
    }

    //(Função para congigurar os eixos do grafico)----------------------------------------------------------------------
    void configuarAxis(){
        yAxis().setAutoRanging(false);
        yAxis().setUpperBound(50);
        yAxis().setTickUnit(10);
        xAxis().setLabel("Mês");
        yAxis().setLabel("Consumo (m³)");
    }

    //(Funções para pegar os dados da classe)---------------------------------------------------------------------------
    public abstract Chart getChart();

    public XYChart.Series<String, Number> getSeries() {
        return this.series;
    }
    public TipoGrafico getTipoGrafico() {
        return this.tipoGrafico;
    }
    public String getAno() {
        return this.ano;
    }
    protected CategoryAxis xAxis() {
        return this.xAxis;
    }
    protected NumberAxis yAxis() {
        return this.yAxis;
    }
}
