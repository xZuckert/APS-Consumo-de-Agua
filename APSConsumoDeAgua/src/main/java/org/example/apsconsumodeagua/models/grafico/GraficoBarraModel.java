package org.example.apsconsumodeagua.models.grafico;

import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import org.example.apsconsumodeagua.utils.enums.TipoGrafico;

//(Classe modelo do grafico de barras)----------------------------------------------------------------------------------
public class GraficoBarraModel extends GraficoModel{
    private final BarChart<String, Number> barChart;

    //(Construtor da classe)--------------------------------------------------------------------------------------------
    public GraficoBarraModel(String ano, ObservableList<XYChart.Data<String,Number>> dados, TipoGrafico tipoGrafico) {
        super(ano, dados,tipoGrafico);
        barChart = new BarChart<>(xAxis(), yAxis());
        barChart.setTitle(getAno());
        barChart.getData().add(getSeries());
        barChart.getStyleClass().add("grafico");
        atualizarYAxis();
    }

    //(Função para atualizar o tamanho maximo do eixo Y)----------------------------------------------------------------
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
    //(Função para pegar o grafico)-------------------------------------------------------------------------------------
    public BarChart<String,Number> getChart() {
        return barChart;
    }
}
