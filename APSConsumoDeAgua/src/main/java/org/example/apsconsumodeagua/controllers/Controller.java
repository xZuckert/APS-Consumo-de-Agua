package org.example.apsconsumodeagua.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.example.apsconsumodeagua.models.Grafico;
import org.example.apsconsumodeagua.models.ListaDeGraficos;
import org.example.apsconsumodeagua.utils.Toast;

import java.net.URL;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.*;

public class Controller implements Initializable {
    public static ListaDeGraficos listaDeGraficos = new ListaDeGraficos();

    @FXML
    private LineChart<String, Number> chartTemplate;
    @FXML
    private VBox homeVBox;
    @FXML
    private ComboBox<String> boxMeses, boxAnos, boxGraficos;
    @FXML
    private TabPane tabPaneGraficos;
    @FXML
    private TextField nomeField, sobrenomeField, cpfField, emailField, cepField, enderecoField, estadoField, cidadeField, pessoasField, consumoField;
    @FXML
    private AnchorPane paneInterface, contentTabUsuario, contentTabHome, contentTabGraficos, addConsumo;
    @FXML
    private ToggleButton tabUsuario, tabHome, tabGraficos;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String anoAtual = String.valueOf(Year.now().getValue());

        if (listaDeGraficos.getGrafico(anoAtual) == null) {
            String mesAtual = LocalDate.now().getMonth().getDisplayName(TextStyle.SHORT, Locale.getDefault());
            gerarGraficoETab(anoAtual, mesAtual, 0);
        }

        atualizarBoxGraficos();

        boxGraficos.getSelectionModel().select(anoAtual);

        selecionarGrafico(anoAtual);
        for (int i = Year.now().getValue(); i >= Year.now().getValue() - 20; i--) {
            boxAnos.getItems().add(String.valueOf(i));
        }
        //atualiza o boxMeses, verifica se é o ano atual e permite selecionar somente até o mes atual
        boxAnos.valueProperty().addListener((obs, valorAntigo, valorNovo) -> {
            atualizarBoxMeses(valorNovo);
        });
        boxGraficos.valueProperty().addListener((obs, valorAntigo, valorNovo) -> {
            selecionarGrafico(valorNovo);
        });
    }

    @FXML
    public void changeTab(ActionEvent event) {
        ToggleButton botaoClicado = (ToggleButton) event.getSource();
        tabToggle(botaoClicado);
    }

    @FXML
    public void openAddConsumo(ActionEvent event) {
        mostrarDeslizando(addConsumo);
    }

    @FXML
    public void registrarConsumo(ActionEvent event) {
        try {
            int consumo = Integer.parseInt(consumoField.getText());
            String ano = boxAnos.getValue();
            String mes = boxMeses.getValue();

            if (ano == null || mes == null) {
                Toast.mostrarToast(paneInterface, "Selecione ano e mês!", Toast.tipoToast.ERRO, 100, 320);
                return;
            }

            if (listaDeGraficos.getGrafico(ano) != null) {
                listaDeGraficos.atualizarValorMes(ano, mes, consumo);
            } else {
                gerarGraficoETab(ano, mes, consumo);
            }
            boxGraficos.selectionModelProperty().get().select(ano);
            chartTemplate.getData().clear();
            chartTemplate.setTitle(ano);
            chartTemplate.getData().add(clonarSeries(listaDeGraficos.getSerie(ano)));
            addConsumo.setVisible(false);
        } catch (NumberFormatException e) {
            Toast.mostrarToast(paneInterface, "Consumo inválido!", Toast.tipoToast.ERRO, 100, 320);
        }
    }

    private void selecionarGrafico(String ano) {
        chartTemplate.getData().clear();
        XYChart.Series<String, Number> serie = listaDeGraficos.getSerie(ano);
        if (serie != null) {
            chartTemplate.getData().add(clonarSeries(serie));
        }
        chartTemplate.setTitle(ano);

        chartTemplate.getYAxis().setAutoRanging(false);
        chartTemplate.getXAxis().setLabel("Mês");
        ((NumberAxis) chartTemplate.getYAxis()).setUpperBound(50);
        ((NumberAxis) chartTemplate.getYAxis()).setTickUnit(10);

    }

    private void atualizarBoxGraficos() {
        Set<String> anos = listaDeGraficos.getKeys();

        boxGraficos.getItems().setAll(anos);
        if (!anos.isEmpty() && boxGraficos.getSelectionModel().getSelectedItem() == null) {
            boxGraficos.getSelectionModel().selectLast();
        }
    }

    private void tabToggle(ToggleButton toggleButton) {
        switch (toggleButton.getId()) {
            case "tabUsuario":
                tabUsuario.setSelected(true);
                contentTabUsuario.setVisible(true);

                tabHome.setSelected(false);
                contentTabHome.setVisible(false);

                tabGraficos.setSelected(false);
                contentTabGraficos.setVisible(false);
                break;
            case "tabHome":
                tabUsuario.setSelected(false);
                contentTabUsuario.setVisible(false);

                tabHome.setSelected(true);
                contentTabHome.setVisible(true);

                tabGraficos.setSelected(false);
                contentTabGraficos.setVisible(false);
                break;
            case "tabGraficos":
                tabUsuario.setSelected(false);
                contentTabUsuario.setVisible(false);

                tabHome.setSelected(false);
                contentTabHome.setVisible(false);

                tabGraficos.setSelected(true);
                contentTabGraficos.setVisible(true);
                break;
        }
    }

    private void mostrarDeslizando(Pane pane) {
        pane.setTranslateY(pane.getHeight());     // começa deslocado pra baixo
        pane.setOpacity(0);
        pane.setVisible(true);

        TranslateTransition slideIn = new TranslateTransition(Duration.millis(400), pane);
        slideIn.setFromY(pane.getHeight());
        slideIn.setToY(0);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(400), pane);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        ParallelTransition animation = new ParallelTransition(slideIn, fadeIn);
        animation.play();
    }

    private void atualizarBoxMeses(String ano) {
        List<String> meses = new ArrayList<>();
        String[] nomeMeses = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
        int limiteMeses = 12;
        if (ano.equals(String.valueOf(Year.now().getValue()))) {
            limiteMeses = LocalDate.now().getMonth().getValue();
        }
        for (int i = 0; i < limiteMeses; i++) {
            meses.add(nomeMeses[i]);
        }
        boxMeses.getItems().setAll(meses);
        boxMeses.setDisable(false);
    }

    private void gerarGraficoETab(String ano, String mes, int consumo) {
        listaDeGraficos.gerarGrafico(ano, mes, consumo);
        atualizarBoxGraficos();
        listaDeGraficos.getGrafico(ano).getLineChart().getStyleClass().add("grafico");
        if (!tabExiste(ano)) {
            Grafico grafico = listaDeGraficos.getGrafico(ano);
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
            tabPaneGraficos.getTabs().add(tab);
            Toast.mostrarToast(paneInterface, "Grafico adicionado!", Toast.tipoToast.SUCESSO, 100, 320);
        }
    }


    private boolean tabExiste(String ano) {
        for (Tab tab : tabPaneGraficos.getTabs()) {
            if (tab.getText().equals(ano)) {
                return true;
            }
        }
        return false;
    }

    private XYChart.Series<String, Number> clonarSeries(XYChart.Series<String, Number> original) {
        XYChart.Series<String, Number> copia = new XYChart.Series<>();
        for (XYChart.Data<String, Number> data : original.getData()) {
            copia.getData().add(new XYChart.Data<>(data.getXValue(), data.getYValue()));
        }
        return copia;
    }


    public void setNomeField(String nome) {
        this.nomeField.setText(nome);
    }

    public void setSobrenomeField(String sobrenome) {
        this.sobrenomeField.setText(sobrenome);
    }

    public void setcpfField(String cpf) {
        this.cpfField.setText(cpf);
    }
    public void setEmailField(String cpf) {
        this.emailField.setText(cpf);
    }

    public void setCepField(String cep) {
        this.cepField.setText(cep);
    }

    public void setEnderecoField(String endereco) {
        this.enderecoField.setText(endereco);
    }

    public void setEstadoField(String estado) {
        this.estadoField.setText(estado);
    }

    public void setCidadeField(String cidade) {
        this.cidadeField.setText(cidade);
    }

    public void setPessoasField(String pessoas) {
        this.pessoasField.setText(pessoas);
    }
}
