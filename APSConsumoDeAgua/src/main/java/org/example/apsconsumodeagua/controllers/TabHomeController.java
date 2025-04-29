package org.example.apsconsumodeagua.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.apsconsumodeagua.core.AppModel;
import org.example.apsconsumodeagua.managers.GraficoManager;
import org.example.apsconsumodeagua.models.base.GraficoModel;
import org.example.apsconsumodeagua.utils.constantes.AppConstantes;
import org.example.apsconsumodeagua.utils.UIUtils;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

//(Classe que controla a view Home)-------------------------------------------------------------------------------------
public class TabHomeController {
    private final AppModel appModel = AppModel.getInstance();
    GraficoManager manager = appModel.getGraficoManager();

    @FXML
    public AnchorPane contentTabHome,adicionarConsumo,paneGraficoTemplate;
    @FXML
    public ComboBox<String> boxGraficos,boxMeses,boxAnos;
    @FXML
    public TextField consumoField;

    //(Função que coleta os dados da tela de registro de consumo)-------------------------------------------------------
    @FXML
    public void registrarConsumo() {
        int consumo = Integer.parseInt(consumoField.getText());
        String ano = boxAnos.getValue();
        String mes = boxMeses.getValue();
        registrarValores(ano,mes,consumo);
    }

    //(Função que mostra a tela para o usuario registrar um consumo)----------------------------------------------------
    @FXML
    public void abrirPaneAdicionarConsumo() {
        if(!adicionarConsumo.isVisible()){
            UIUtils.mostrarDeslizando(adicionarConsumo,300, UIUtils.direcao.DE_BAIXO_PRA_CIMA);
        }
    }

    //(Função chamada para criar ou atualizar um grafico)---------------------------------------------------------------
    private void registrarValores(String ano,String mes,int consumo) {
        if (manager.getGrafico(ano) != null) {
            manager.atualizarDados(ano, mes, consumo);
        } else {
            manager.gerarGrafico(ano, appModel.getTipoGrafico());
            manager.atualizarDados(ano, mes, consumo);
            appModel.getTabGraficosController().adicionarGraficoNaTab(ano, appModel.getRootPane());
        }
        atualizarBoxGraficos();
        selecionarGrafico(ano);
        adicionarConsumo.setVisible(false);
    }

    public void esconderPaneAdicionarConsumo(){
        if(adicionarConsumo.isVisible()){
            adicionarConsumo.setVisible(false);
        }
    }

    //(Função chamada para selecionar o grafico na Home)----------------------------------------------------------------
    public void selecionarGrafico(String ano) {
        GraficoModel grafico = manager.getGrafico(ano);
        if (grafico != null) {
            paneGraficoTemplate.getChildren().clear();
            GraficoModel graficoGerado = manager.clonarGrafico(ano);
            paneGraficoTemplate.getChildren().add(UIUtils.criarNodeCentralizadoVerticalmente(graficoGerado.getChart()));
            boxGraficos.selectionModelProperty().get().select(ano);
        }
    }

    //(Funções chamadas para atualizar os valores nos ComboBox)---------------------------------------------------------
    public void atualizarBoxGraficos() {
        Set<String> anos = appModel.getGraficoManager().getAnos();
        SingleSelectionModel<String> selectionModel = boxGraficos.getSelectionModel();
        boxGraficos.getItems().setAll(anos);
        if (!anos.isEmpty() && selectionModel.getSelectedItem() == null) {
            selectionModel.selectLast();
        }
    }
    public void atualizarBoxMeses(String ano) {
        int limiteMeses = 12;
        if (ano.equals(String.valueOf(Year.now().getValue()))) {
            limiteMeses = LocalDate.now().getMonth().getValue();
        }
        List<String> meses = new ArrayList<>(Arrays.asList(AppConstantes.MESES).subList(0, limiteMeses));
        boxMeses.getItems().setAll(meses);
        boxMeses.setDisable(false);
    }
}
