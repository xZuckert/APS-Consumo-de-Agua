package org.example.apsconsumodeagua.models.grafico;

import javafx.collections.ObservableList;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.Chart;
import javafx.scene.chart.XYChart;
import org.example.apsconsumodeagua.models.base.GraficoModel;
import org.example.apsconsumodeagua.utils.enums.TipoGrafico;

//Classe modelo do grafico de area--------------------------------------------------------------------------------------
public class GraficoAreaModel extends GraficoModel {
    private final AreaChart<String, Number> areaChart;
    //Construtor da classe----------------------------------------------------------------------------------------------
    public GraficoAreaModel(String ano, ObservableList<XYChart.Data<String, Number>> dados, TipoGrafico tipoGrafico) {
        super(ano, dados, tipoGrafico);
        areaChart = new AreaChart<>(xAxis(), yAxis());
        areaChart.setTitle(getAno());
        areaChart.getData().add(getSeries());
    }
    //Função para pegar o grafico---------------------------------------------------------------------------------------
    @Override
    public Chart getChart() {
        return areaChart;
    }
    //Função para pegar os dados do grafico-----------------------------------------------------------------------------
    @Override
    public ObservableList<XYChart.Series<String, Number>> getData() {
        return areaChart.getData();
    }
    //------------------------------------------------------------------------------------------------------------------
}