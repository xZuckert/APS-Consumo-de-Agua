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
import javafx.util.Duration;
import org.example.apsconsumodeagua.services.GraficoService;
import org.example.apsconsumodeagua.utils.Toast;

import java.net.URL;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.*;

public class Controller implements Initializable {
    public static GraficoService graficoService;
    private static final String[] MESES = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
    @FXML
    private LineChart<String, Number> chartTemplate;
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

//( Metodos chamados ao inicializar o fxml )----------------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        graficoService = new GraficoService();
        inicializarBoxGraficos();
        inicializarBoxAnos();
        inicializarListeners();
    }
    private void inicializarBoxGraficos(){
        String anoAtual = String.valueOf(Year.now().getValue());
        if (graficoService.getGrafico(anoAtual) == null) {
            String mesAtual = LocalDate.now().getMonth().getDisplayName(TextStyle.SHORT, Locale.getDefault());
            graficoService.gerarGraficoETab(anoAtual, mesAtual, 0,graficoService,tabPaneGraficos,paneInterface);
            atualizarBoxGraficos();
        }
        boxGraficos.getSelectionModel().select(anoAtual);
        graficoService.selecionarGrafico(anoAtual,chartTemplate,graficoService);
    }
    private void inicializarBoxAnos(){
        for (int i = Year.now().getValue(); i >= Year.now().getValue() - 20; i--) {
            boxAnos.getItems().add(String.valueOf(i));
        }
    }
    private void inicializarListeners(){
        boxAnos.valueProperty().addListener((obs, valorAntigo, valorNovo) -> atualizarBoxMeses(valorNovo));
        boxGraficos.valueProperty().addListener((obs, valorAntigo, valorNovo) -> graficoService.selecionarGrafico(valorNovo,chartTemplate,graficoService));
    }
//----------------------------------------------------------------------------------------------------------------------

//( Métodos chamados pelo usuário ao acionar algum evento )-------------------------------------------------------------
    @FXML
    public void changeTab(ActionEvent event) {
        ToggleButton botaoClicado = (ToggleButton) event.getSource();
        tabToggle(botaoClicado);
    }
    @FXML
    public void openAddConsumo() {
        mostrarDeslizando(addConsumo);
    }
    @FXML
    public void registrarConsumo() {
        try {
            int consumo = Integer.parseInt(consumoField.getText());
            String ano = boxAnos.getValue();
            String mes = boxMeses.getValue();

            if (ano == null || mes == null) {
                Toast.mostrarToast(paneInterface, "Selecione ano e mês!", Toast.tipoToast.ERRO, 100, 320);
                return;
            }

            if (graficoService.getGrafico(ano) != null) {
                graficoService.atualizarValorMes(ano, mes, consumo);
            } else {
                graficoService.gerarGraficoETab(ano, mes, consumo,graficoService,tabPaneGraficos,paneInterface);
                atualizarBoxGraficos();
            }
            boxGraficos.selectionModelProperty().get().select(ano);
            chartTemplate.getData().clear();
            chartTemplate.setTitle(ano);
            chartTemplate.getData().add(graficoService.clonarSeries(graficoService.getSerie(ano)));
            addConsumo.setVisible(false);
        } catch (NumberFormatException e) {
            Toast.mostrarToast(paneInterface, "Consumo inválido!", Toast.tipoToast.ERRO, 100, 320);
        }
    }
//----------------------------------------------------------------------------------------------------------------------

//( Métodos de atualização dos ComboBox )-------------------------------------------------------------------------------
    private void atualizarBoxGraficos() {
        Set<String> anos = graficoService.getKeys();

        boxGraficos.getItems().setAll(anos);
        if (!anos.isEmpty() && boxGraficos.getSelectionModel().getSelectedItem() == null) {
            boxGraficos.getSelectionModel().selectLast();
        }
    }
    private void atualizarBoxMeses(String ano) {
        int limiteMeses = 12;
        if (ano.equals(String.valueOf(Year.now().getValue()))) {
            limiteMeses = LocalDate.now().getMonth().getValue();
        }
        List<String> meses = new ArrayList<>(Arrays.asList(MESES).subList(0, limiteMeses));
        boxMeses.getItems().setAll(meses);
        boxMeses.setDisable(false);
    }
//----------------------------------------------------------------------------------------------------------------------

//( Métodos de manipulação de UI )--------------------------------------------------------------------------------------
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
//----------------------------------------------------------------------------------------------------------------------

//( Métodos para alterar o conteúdo dos Fields )------------------------------------------------------------------------
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
//----------------------------------------------------------------------------------------------------------------------
}
