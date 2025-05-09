package org.example.apsconsumodeagua.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import org.example.apsconsumodeagua.core.AppModel;

import java.net.URL;
import java.util.*;

//Classe que controla a view Aplicacao----------------------------------------------------------------------------------
public class AplicacaoController implements Initializable {
    private final AppModel appModel = AppModel.getInstance();
    @FXML
    public ToggleButton tabUsuario, tabHome, tabGraficos;
    @FXML
    private AnchorPane paneInterface;
    //Função chamada ao inicializar o fxml------------------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appModel.setRootPane(paneInterface);
        appModel.carregarAplicacao(tabUsuario, tabHome, tabGraficos);
    }
    //Função para trocar de tela----------------------------------------------------------------------------------------
    @FXML
    public void trocarTab(ActionEvent event) {
        ToggleButton botaoClicado = (ToggleButton) event.getSource();
        appModel.getTabManager().alternarAba(botaoClicado);
    }
    //------------------------------------------------------------------------------------------------------------------
}