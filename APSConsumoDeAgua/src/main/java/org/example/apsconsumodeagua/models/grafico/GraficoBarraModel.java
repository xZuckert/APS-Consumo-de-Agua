package org.example.apsconsumodeagua.models.grafico;

import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import org.example.apsconsumodeagua.models.base.GraficoModel;
import org.example.apsconsumodeagua.utils.enums.TipoGrafico;

//(Classe modelo do grafico de barras)----------------------------------------------------------------------------------
public class GraficoBarraModel extends GraficoModel {
    private final BarChart<String, Number> barChart;

    //(Construtor da classe)--------------------------------------------------------------------------------------------
    public GraficoBarraModel(String ano, ObservableList<XYChart.Data<String,Number>> dados, TipoGrafico tipoGrafico) {
        super(ano, dados,tipoGrafico);
        barChart = new BarChart<>(xAxis(), yAxis());
        barChart.setTitle(getAno());
        barChart.getData().add(getSeries());
        barChart.setLegendVisible(false);
        barChart.getStyleClass().add("grafico");
    }

    //(Função para atualizar o tamanho maximo do eixo Y)----------------------------------------------------------------

    @Override
    //(Função para pegar o grafico)-------------------------------------------------------------------------------------
    public BarChart<String,Number> getChart() {
        return barChart;
    }
}
