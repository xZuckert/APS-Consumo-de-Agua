package org.example.apsconsumodeagua.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import org.example.apsconsumodeagua.services.GraficoService;
import org.example.apsconsumodeagua.utils.Constantes;
import org.example.apsconsumodeagua.utils.Toast;
import org.example.apsconsumodeagua.utils.UIUtils;

import java.net.URL;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.*;

public class AplicacaoController implements Initializable {
    private GraficoController graficoController;
    private TabController tabController;

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
        this.graficoController = new GraficoController(new GraficoService());
        this.tabController = new TabController(tabUsuario,tabHome,tabGraficos,contentTabUsuario,contentTabHome,contentTabGraficos);
        inicializarBoxGraficos();
        inicializarBoxAnos();
        inicializarListeners();
    }
    private void inicializarBoxGraficos(){
        String anoAtual = String.valueOf(Year.now().getValue());
        if (graficoController.getGrafico(anoAtual) == null) {
            String mesAtual = LocalDate.now().getMonth().getDisplayName(TextStyle.SHORT, Locale.getDefault());
            graficoController.criarOuAtualizarGrafico(anoAtual,mesAtual,0,tabPaneGraficos,paneInterface);
            atualizarBoxGraficos();
        }
        boxGraficos.getSelectionModel().select(anoAtual);
        graficoController.selecionarGrafico(anoAtual,chartTemplate);
    }
    private void inicializarBoxAnos(){
        for (int i = Year.now().getValue(); i >= Year.now().getValue() - 20; i--) {
            boxAnos.getItems().add(String.valueOf(i));
        }
    }
    private void inicializarListeners(){
        boxAnos.valueProperty().addListener((obs, valorAntigo, valorNovo) -> atualizarBoxMeses(valorNovo));
        boxGraficos.valueProperty().addListener((obs, valorAntigo, valorNovo) -> graficoController.selecionarGrafico(valorNovo,chartTemplate));
    }
    //----------------------------------------------------------------------------------------------------------------------

    //( Métodos chamados pelo usuário ao acionar algum evento )-------------------------------------------------------------
    @FXML
    public void changeTab(ActionEvent event) {
        ToggleButton botaoClicado = (ToggleButton) event.getSource();
        tabController.alternarAba(botaoClicado);
    }
    @FXML
    public void openAddConsumo() {
        UIUtils.mostrarDeslizandoDeBaixoParaCima(addConsumo,400);
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
            graficoController.criarOuAtualizarGrafico(ano,mes,consumo,tabPaneGraficos,paneInterface);
            atualizarBoxGraficos();

            boxGraficos.selectionModelProperty().get().select(ano);
            chartTemplate.getData().clear();
            chartTemplate.setTitle(ano);
            chartTemplate.getData().add(graficoController.getSerieClonada(ano));
            addConsumo.setVisible(false);
        } catch (NumberFormatException e) {
            Toast.mostrarToast(paneInterface, "Consumo inválido!", Toast.tipoToast.ERRO, 100, 320);
        }
    }
    //----------------------------------------------------------------------------------------------------------------------

    //( Métodos de atualização dos ComboBox )-------------------------------------------------------------------------------
    private void atualizarBoxGraficos() {
        Set<String> anos = graficoController.getAnos();

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
        List<String> meses = new ArrayList<>(Arrays.asList(Constantes.MESES).subList(0, limiteMeses));
        boxMeses.getItems().setAll(meses);
        boxMeses.setDisable(false);
    }
    //----------------------------------------------------------------------------------------------------------------------

    //( Métodos para alterar o conteúdo dos Fields )------------------------------------------------------------------------
    public void setNomeField(String nome) {
        this.nomeField.setText(nome);
    }
    public void setSobrenomeField(String sobrenome) {
        this.sobrenomeField.setText(sobrenome);
    }
    public void setCpfField(String cpf) {
        this.cpfField.setText(cpf);
    }
    public void setEmailField(String email) {
        this.emailField.setText(email);
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