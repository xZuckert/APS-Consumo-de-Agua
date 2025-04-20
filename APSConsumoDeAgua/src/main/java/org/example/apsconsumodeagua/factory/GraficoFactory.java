package org.example.apsconsumodeagua.factory;


import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import org.example.apsconsumodeagua.models.grafico.GraficoBarraModel;
import org.example.apsconsumodeagua.models.grafico.GraficoLinhaModel;
import org.example.apsconsumodeagua.models.grafico.GraficoModel;
import org.example.apsconsumodeagua.utils.enums.TipoGrafico;

//(Classe para griar graficos)------------------------------------------------------------------------------------------
public class GraficoFactory {
    public GraficoModel criarGrafico(String ano, ObservableList<XYChart.Data<String,Number>> dados, TipoGrafico tipoGrafico) {
        GraficoModel graficoModel = null;
        if(tipoGrafico == null) {
            return graficoModel;
        }
        switch (tipoGrafico) {
            case LINHA -> graficoModel = new GraficoLinhaModel(ano, dados);
            case BARRA -> graficoModel = new GraficoBarraModel(ano, dados);
        }
        return graficoModel;
    }
}
