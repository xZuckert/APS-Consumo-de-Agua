package org.example.apsconsumodeagua.services;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.example.apsconsumodeagua.models.Grafico;
import org.example.apsconsumodeagua.utils.Toast;
import org.example.apsconsumodeagua.utils.Validadores;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GraficoService {
    private final Map<String, Grafico> graficos = new HashMap<>();
    private final Map<String, XYChart.Series<String, Number>> series = new HashMap<>();

//( Métodos chamados para adicionar novos graficos )--------------------------------------------------------------------
    public void gerarGrafico(String ano, String mes, int consumo) {
        series.put(ano, gerarSeries(mes, consumo));
        graficos.put(ano, new Grafico(ano, series.get(ano)));
    }
    public void gerarGraficoETab(String ano, String mes, int consumo, GraficoService service, TabPane tabPane, AnchorPane pane) {
        service.gerarGrafico(ano, mes, consumo);
        service.getGrafico(ano).getLineChart().getStyleClass().add("grafico");
        if (!Validadores.tabExiste(ano, tabPane)) {
            Grafico grafico = service.getGrafico(ano);
            LineChart<String, Number> graficoTab = grafico.getLineChart();

            VBox vbox = new VBox();

            Region topSpacer = new Region();
            Region bottomSpacer = new Region();

            VBox.setVgrow(topSpacer, javafx.scene.layout.Priority.ALWAYS);
            VBox.setVgrow(bottomSpacer, javafx.scene.layout.Priority.ALWAYS);
            vbox.getChildren().addAll(topSpacer, graficoTab, bottomSpacer);

            AnchorPane graficoContainer = new AnchorPane(vbox);
            AnchorPane.setTopAnchor(vbox, 10.0);
            AnchorPane.setBottomAnchor(vbox, 10.0);
            AnchorPane.setLeftAnchor(vbox, 10.0);
            AnchorPane.setRightAnchor(vbox, 10.0);

            Tab tab = new Tab(ano);
            tab.setContent(graficoContainer);
            tabPane.getTabs().add(tab);
            Toast.mostrarToast(pane, "Grafico adicionado!", Toast.tipoToast.SUCESSO, 100, 320);
        }
    }
    private XYChart.Series<String, Number> gerarSeries(String mes, int consumo) {
        XYChart.Series<String, Number> series;
        series = new XYChart.Series<>();

        series.setName("Consumo");

        series.getData().add(new XYChart.Data<>("Jan", null));
        series.getData().add(new XYChart.Data<>("Fev", null));
        series.getData().add(new XYChart.Data<>("Mar", null));
        series.getData().add(new XYChart.Data<>("Abr", null));
        series.getData().add(new XYChart.Data<>("Mai", null));
        series.getData().add(new XYChart.Data<>("Jun", null));
        series.getData().add(new XYChart.Data<>("Jul", null));
        series.getData().add(new XYChart.Data<>("Ago", null));
        series.getData().add(new XYChart.Data<>("Set", null));
        series.getData().add(new XYChart.Data<>("Out", null));
        series.getData().add(new XYChart.Data<>("Nov", null));
        series.getData().add(new XYChart.Data<>("Dez", null));

        if (mes != null) {
            for (XYChart.Data<String, Number> data : series.getData()) {
                if (data.getXValue().equals(mes)) {
                    data.setYValue(consumo);
                    break;
                }
            }
        }
        return series;
    }
//----------------------------------------------------------------------------------------------------------------------

//( Métodos chamados para manipular dados )-----------------------------------------------------------------------------
    public XYChart.Series<String, Number> clonarSeries(XYChart.Series<String, Number> original) {
        XYChart.Series<String, Number> copia = new XYChart.Series<>();
        for (XYChart.Data<String, Number> data : original.getData()) {
            copia.getData().add(new XYChart.Data<>(data.getXValue(), data.getYValue()));
        }
        copia.setName(original.getName());
        return copia;
    }
    public void atualizarValorMes(String ano, String mes, int novoValor) {
        if (series.containsKey(ano)) {
            XYChart.Series<String, Number> serie = series.get(ano);

            for (XYChart.Data<String, Number> data : serie.getData()) {
                if (data.getXValue().equals(mes)) {
                    data.setYValue(novoValor);
                    break;
                }
            }

            if (graficos.containsKey(ano)) {
                Grafico graficoAno = graficos.get(ano);

                // REMOVE a série antiga e adiciona a atualizada
                LineChart<String, Number> chart = graficoAno.getLineChart();
                chart.getData().clear(); // Limpa as séries existentes
                chart.getData().add(serie); // Adiciona a série atualizada
            }
        }
    }
//----------------------------------------------------------------------------------------------------------------------

//( Métodos chamados para manipular graficos )--------------------------------------------------------------------------
    public void selecionarGrafico(String ano, LineChart<String, Number> chart,GraficoService graficoService) {
        chart.getData().clear();
        XYChart.Series<String, Number> serie = graficoService.getSerie(ano);
        if (serie != null) {
            chart.getData().add(graficoService.clonarSeries(serie));
        }
        chart.setTitle(ano);

        chart.getYAxis().setAutoRanging(false);
        chart.getXAxis().setLabel("Mês");
        ((NumberAxis) chart.getYAxis()).setUpperBound(50);
        ((NumberAxis) chart.getYAxis()).setTickUnit(10);

    }
//----------------------------------------------------------------------------------------------------------------------

//( Métodos chamados para pegar dados e graficos )----------------------------------------------------------------------
    public Set<String> getKeys() {
        return graficos.keySet();
    }
    public XYChart.Series<String, Number> getSerie(String ano) {
        return series.get(ano);
    }
    public Grafico getGrafico(String ano) {
        return graficos.get(ano);
    }
//----------------------------------------------------------------------------------------------------------------------
}

