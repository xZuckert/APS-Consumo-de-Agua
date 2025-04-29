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
    private CategoryAxis xAxis;
    private  NumberAxis yAxis;
    private final String ano;

    //(Construtor da classe)--------------------------------------------------------------------------------------------
    protected GraficoModel(String ano, ObservableList<XYChart.Data<String, Number>> dados, TipoGrafico tipoGrafico) {
        this.ano = ano;
        this.tipoGrafico = tipoGrafico;
        this.series = new XYChart.Series<>();
        this.series.getData().setAll(dados);
        this.series.setName("Consumo");
        this.xAxis = new CategoryAxis();
        this.yAxis = new NumberAxis();
        configuarAxis();
        atualizarYAxis();
    }

    //(Função para congigurar os eixos do grafico)----------------------------------------------------------------------
    private void configuarAxis(){
        yAxis().setAutoRanging(false);
        yAxis().setUpperBound(50);
        yAxis().setTickUnit(10);
        xAxis().setLabel("Mês");
        yAxis().setLabel("Consumo (m³)");
    }

    public void atualizarYAxis(){
        double valorMaximoY;
        double maxValor = this.getSeries().getData().stream()
                .mapToDouble(data -> data != null ? data.getYValue().doubleValue() : 0)
                .max()
                .orElse(50);
        valorMaximoY = Math.ceil(maxValor / 10) * 10 + 10;
        yAxis().setUpperBound(Math.max(valorMaximoY, 50));
    }

    //(Funções para pegar os dados da classe)---------------------------------------------------------------------------
    public abstract Chart getChart();
    public abstract ObservableList<XYChart.Series<String,Number>> getData();

    public XYChart.Series<String, Number> getSeries() {
        return this.series;
    }
    public TipoGrafico getTipoGrafico() {
        return this.tipoGrafico;
    }
    public String getAno() {
        return this.ano;
    }
    public CategoryAxis xAxis() {
        return this.xAxis;
    }
    public NumberAxis yAxis() {
        return this.yAxis;
    }
}
