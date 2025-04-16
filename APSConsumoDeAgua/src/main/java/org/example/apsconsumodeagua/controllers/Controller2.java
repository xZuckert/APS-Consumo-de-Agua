package org.example.apsconsumodeagua.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.example.apsconsumodeagua.models.Grafico;
import org.example.apsconsumodeagua.models.ListaDeGraficos;
import org.example.apsconsumodeagua.utils.Toast;
import org.example.apsconsumodeagua.utils.Validadores;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Controller2 implements Initializable {
    public static ListaDeGraficos listaDeGraficos = new ListaDeGraficos();
    @FXML
    private VBox homeVBox;
    @FXML
    private TabPane tabPaneGraficos;
    @FXML
    private TextField nomeField, sobrenomeField, cepField, enderecoField,estadoField, cidadeField, pessoasField, consumoField;
    @FXML
    private DatePicker dateField;
    @FXML
    private AnchorPane paneInterface, contentTabUsuario, contentTabHome, contentTabGraficos, addConsumo;
    @FXML
    private ToggleButton tabUsuario, tabHome, tabGraficos;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
        int consumo = Integer.parseInt(consumoField.getText());
        LocalDate dataConsumo = dateField.getValue();
        String ano = Validadores.pegarAno(dataConsumo);
        String mes = Validadores.pegarMes(dataConsumo);
        listaDeGraficos.adicionarDado(ano,mes,consumo);
        if (!tabExiste(ano)) {
            Grafico grafico = listaDeGraficos.getGrafico(ano);
            LineChart<String, Number> chart = grafico.getLineChart();

            chart.getStyleClass().add("grafico");

            VBox vbox = new VBox();

            Region topSpacer = new Region();
            Region bottomSpacer = new Region();

            VBox.setVgrow(topSpacer, javafx.scene.layout.Priority.ALWAYS);
            VBox.setVgrow(bottomSpacer, javafx.scene.layout.Priority.ALWAYS);
            vbox.getChildren().addAll(topSpacer, chart, bottomSpacer);

            AnchorPane graficoContainer = new AnchorPane(vbox);
            AnchorPane.setTopAnchor(vbox, 10.0);
            AnchorPane.setBottomAnchor(vbox, 10.0);
            AnchorPane.setLeftAnchor(vbox, 10.0);
            AnchorPane.setRightAnchor(vbox, 10.0);

            Tab tab = new Tab(ano);
            tab.setContent(graficoContainer);
            tabPaneGraficos.getTabs().add(tab);
            Toast.mostrarToast(paneInterface,"Grafico adicionado!", Toast.tipoToast.SUCESSO,100,320);
        }else{
            Toast.mostrarToast(paneInterface,"Grafico atualizado!", Toast.tipoToast.SUCESSO);
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

    public void mostrarDeslizando(Pane pane) {
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

    private boolean tabExiste(String ano) {
        for (Tab tab : tabPaneGraficos.getTabs()) {
            if (tab.getText().equals(ano)) {
                return true;
            }
        }
        return false;
    }

    public void setNomeField(String nome) {
        this.nomeField.setText(nome);
    }
    public void setSobrenomeField(String sobrenome) {
        this.sobrenomeField.setText(sobrenome);
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
