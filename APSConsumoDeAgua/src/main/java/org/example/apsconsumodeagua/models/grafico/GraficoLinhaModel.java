package org.example.apsconsumodeagua.models.grafico;

import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.example.apsconsumodeagua.models.base.GraficoModel;
import org.example.apsconsumodeagua.utils.enums.TipoGrafico;

//(Classe modelo do grafico de linhas)----------------------------------------------------------------------------------
public class GraficoLinhaModel extends GraficoModel {
    private final LineChart<String, Number> lineChart;
    //(Construtor da classe)--------------------------------------------------------------------------------------------
    public GraficoLinhaModel(String ano, ObservableList<XYChart.Data<String,Number>> dados, TipoGrafico tipoGrafico, CategoryAxis xAxis, NumberAxis yAxis) {
        super(ano, dados,tipoGrafico);
        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setHorizontalGridLinesVisible(false);
        lineChart.setVerticalGridLinesVisible(false);
        lineChart.setLegendVisible(false);

        xAxis.setTickLabelsVisible(true);
        xAxis.setTickMarkVisible(false);

        yAxis.setTickLabelsVisible(true);
        yAxis.setTickMarkVisible(false);

        lineChart.setCreateSymbols(false);
        lineChart.getData().add(getSeries());
        lineChart.getStyleClass().add("grafico");
    }

    //(Função para pegar o grafico)-------------------------------------------------------------------------------------
    @Override
    public LineChart<String, Number> getChart() {
        return this.lineChart;
    }
}
