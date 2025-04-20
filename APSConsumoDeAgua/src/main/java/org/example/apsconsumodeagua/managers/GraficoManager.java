package org.example.apsconsumodeagua.managers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import org.example.apsconsumodeagua.factory.GraficoFactory;
import org.example.apsconsumodeagua.models.grafico.GraficoBarraModel;
import org.example.apsconsumodeagua.models.base.GraficoModel;
import org.example.apsconsumodeagua.models.grafico.GraficoLinhaModel;
import org.example.apsconsumodeagua.utils.constantes.AppConstantes;
import org.example.apsconsumodeagua.utils.enums.TipoGrafico;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//(Classe para manipular os dados dos graficos)--------------------------------------------------------------------------
public class GraficoManager {
    private final Map<String, GraficoModel> graficos = new HashMap<>();
    public final Map<String, ObservableList<XYChart.Data<String,Number>>> valores = new HashMap<>();

    GraficoFactory factory;

    public GraficoManager(GraficoFactory factory) {
        this.factory = factory;
    }

    //(função chamada para gerar novos graficos e vincular com os dados)------------------------------------------------
    public void gerarGrafico(String ano, TipoGrafico tipoGrafico) {
        valores.put(ano, gerarValores(tipoGrafico));
        graficos.put(ano, factory.criarGrafico(ano,valores.get(ano),tipoGrafico));
    }
    public GraficoModel gerarGrafico(TipoGrafico tipoGrafico) {
        return factory.criarGrafico(null,gerarValores(tipoGrafico),tipoGrafico);
    }

    //(Função chamadas para atualizar dados)----------------------------------------------------------------------------
    public void atualizarDados(String ano,String mes, int consumo) {
        for (XYChart.Data<String, Number> dado : valores.get(ano)) {
            if(dado.getXValue().equals(mes)) {
                dado.setYValue(consumo);
            }
        }
        graficos.get(ano).atualizarYAxis();
    }

    //(Funções chamadas para gerar dados iniciais)----------------------------------------------------------------------
    public ObservableList<XYChart.Data<String,Number>> gerarValores(TipoGrafico tipoGrafico) {
        ObservableList<XYChart.Data<String,Number>> dados = FXCollections.observableArrayList();
        switch (tipoGrafico) {
            case LINHA -> gerarDadosLinha(dados);
            case BARRA -> gerarDadosBarra(dados);
        }
        return dados;
    }
    private void gerarDadosBarra(ObservableList<XYChart.Data<String,Number>> dados) {
        for(String mes : AppConstantes.MESES){
            dados.add(new XYChart.Data<>(mes,0));
        }
    }
    private void gerarDadosLinha(ObservableList<XYChart.Data<String,Number>> dados){
        for(String mes : AppConstantes.MESES){
            dados.add(new XYChart.Data<>(mes,20));
        }
    }

    //(Função para atualizar os dados do grafico template)--------------------------------------------------------------
    public ObservableList<XYChart.Data<String, Number>> clonarSerie(String ano) {
        XYChart.Series<String, Number> serieOriginal = graficos.get(ano).getSeries();
        ObservableList<XYChart.Data<String, Number>> novaData = FXCollections.observableArrayList();

        for (XYChart.Data<String, Number> dado : serieOriginal.getData()) {
            novaData.add(new XYChart.Data<>(dado.getXValue(), dado.getYValue()));
        }

        return novaData;
    }
    public GraficoModel clonarGrafico(String ano) {
        GraficoModel grafico = graficos.get(ano);
        if (grafico == null) return null;

        ObservableList<XYChart.Data<String, Number>> dadosClonados = clonarSerie(ano);
        return factory.criarGrafico(ano,dadosClonados,grafico.getTipoGrafico());
    }

    //(Funções chamadas para pegar dados e graficos)--------------------------------------------------------------------
    public Set<String> getAnos() {
        return graficos.keySet();
    }
    public GraficoModel getGrafico(String ano) {
        return graficos.get(ano);
    }
}

