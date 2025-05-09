package org.example.apsconsumodeagua.managers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.layout.AnchorPane;
import org.example.apsconsumodeagua.controllers.TabGraficosController;
import org.example.apsconsumodeagua.database.UsuarioGraficoDAO;
import org.example.apsconsumodeagua.factory.GraficoFactory;
import org.example.apsconsumodeagua.models.base.GraficoModel;
import org.example.apsconsumodeagua.models.usuario.UsuarioModel;
import org.example.apsconsumodeagua.services.UsuarioService;
import org.example.apsconsumodeagua.utils.constantes.AppConstantes;
import org.example.apsconsumodeagua.utils.enums.TipoGrafico;

import java.util.*;

//(Classe para manipular os dados dos graficos)--------------------------------------------------------------------------
public class GraficoManager {
    public final Map<String, ObservableList<XYChart.Data<String,Number>>> valores = new HashMap<>();
    private final Map<String, GraficoModel> graficos = new HashMap<>();
    private final UsuarioModel usuario;
    private boolean graficosCarregados;

    GraficoFactory factory;
    TabGraficosController tabGraficosController;
    AnchorPane rootPane;


    public GraficoManager(GraficoFactory factory, TabGraficosController tabGraficosController, AnchorPane rootPane) {
        usuario = UsuarioService.getInstance().getUsuarioLogado();
        this.factory = factory;
        this.tabGraficosController = tabGraficosController;
        this.rootPane = rootPane;
        graficosCarregados = true;
    }

    //(função chamada para gerar novos graficos e vincular com os dados)------------------------------------------------
    public void gerarGrafico(String ano, TipoGrafico tipoGrafico) {
        if (graficosCarregados && !valores.containsKey(ano)) {
            valores.put(ano, gerarValoresIniciais(tipoGrafico));
        }
        graficos.put(ano, factory.criarGrafico(ano,valores.get(ano),tipoGrafico));
        graficos.get(ano).getData().add(gerarDadosConsumoIdeal());
        UsuarioGraficoDAO.criarGraficoDB(usuario.getCpf(),ano);
    }

    public void trocarTipoGrafico(TipoGrafico tipoGrafico){
        int quantiaGraficos = graficos.size();
        String[] anos = graficos.keySet().toArray(new String[quantiaGraficos]);
        graficos.clear();
        for (int i = 0; i < quantiaGraficos; i++) {
            GraficoModel grafico = factory.criarGrafico(anos[i],valores.get(anos[i]),tipoGrafico);
            grafico.getData().add(gerarDadosConsumoIdeal());
            graficos.put(anos[i],grafico);
        }
    }

    //(Função chamadas para atualizar dados)----------------------------------------------------------------------------
    public void atualizarDados(String ano,String mes, int consumo) {
        for (XYChart.Data<String, Number> dado : valores.get(ano)) {
            if(dado.getXValue().equals(mes)) {
                dado.setYValue(consumo);
            }
        }
        graficos.get(ano).atualizarYAxis();
        UsuarioGraficoDAO.atualizarGraficoDB(usuario.getCpf(), ano);
    }

    //(Funções chamadas para gerar dados iniciais)----------------------------------------------------------------------
    public ObservableList<XYChart.Data<String,Number>> gerarValoresIniciais(TipoGrafico tipoGrafico) {
        ObservableList<XYChart.Data<String,Number>> dados = FXCollections.observableArrayList();
        switch (tipoGrafico) {
            case AREA -> gerarDadosArea(dados);
            case LINHA -> gerarDadosLinha(dados);
            case BARRA -> gerarDadosBarra(dados);
        }
        return dados;
    }
    private void gerarDadosArea(ObservableList<XYChart.Data<String,Number>> dados) {
        for(String mes : AppConstantes.MESES){
            dados.add(new XYChart.Data<>(mes,0));
        }
    }
    private void gerarDadosBarra(ObservableList<XYChart.Data<String,Number>> dados) {
        for(String mes : AppConstantes.MESES){
            dados.add(new XYChart.Data<>(mes,0));
        }
    }
    private void gerarDadosLinha(ObservableList<XYChart.Data<String,Number>> dados){
        for(String mes : AppConstantes.MESES){
            dados.add(new XYChart.Data<>(mes,0));
        }
    }
    private XYChart.Series<String,Number> gerarDadosConsumoIdeal() {
        XYChart.Series<String,Number> series = new XYChart.Series<>();
        List<XYChart.Data<String, Number>> dados = new ArrayList<>();
        if (Objects.requireNonNull(UsuarioService.getInstance().getTipoGrafico()) == TipoGrafico.BARRA) {
            dados.add(new XYChart.Data<>("Consumo Ideal", usuario.getConsumoIdeal()));
            for (String mes : AppConstantes.MESES) {
                dados.add(new XYChart.Data<>(mes, 0));
            }
        } else {
            for (String mes : AppConstantes.MESES) {
                dados.add(new XYChart.Data<>(mes, usuario.getConsumoIdeal()));
            }
        }
        series.getData().addAll(dados);
        series.setName("Consumo Ideal");
        return series;
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
        GraficoModel graficoClone = factory.criarGrafico(ano,dadosClonados,grafico.getTipoGrafico());
        graficoClone.getData().add(gerarDadosConsumoIdeal());
        return graficoClone;
    }

    //(Funções chamadas para pegar dados e graficos)--------------------------------------------------------------------
    public Set<String> getAnos() {
        return graficos.keySet();
    }
    public double getMedia(String ano){
        int[] valoresGrifico = getDados(ano);
        int quantiaDeValoresInseridos = 12;
        int somaDeValoresInseridos = 0;
        for (int valor : valoresGrifico) {
            if (valor == 0) quantiaDeValoresInseridos--;
            somaDeValoresInseridos += valor;
        }
        if(somaDeValoresInseridos == 0)return 0;
        return (double) somaDeValoresInseridos / quantiaDeValoresInseridos;
    }
    public int[] getDados(String ano){
        int[] dados = new int[12];
        for(XYChart.Data<String, Number> dado : valores.get(ano)) {
            if (dado.getYValue() != null) {
                Integer index = AppConstantes.MES_INDEX.get(dado.getXValue());
                dados[index] = dado.getYValue().intValue();
            }
        }
        return dados;
    }
    public GraficoModel getGrafico(String ano) {
        return graficos.get(ano);
    }
    public void setGraficosCarregados(boolean carregar) {
        this.graficosCarregados = carregar;
    }
}

