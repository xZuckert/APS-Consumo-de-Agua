package org.example.apsconsumodeagua.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import org.example.apsconsumodeagua.managers.TabManager;
import org.example.apsconsumodeagua.models.AppModel;
import java.net.URL;
import java.util.*;

public class AplicacaoController implements Initializable {
    private final AppModel appModel = AppModel.getInstance();
    private TabManager tabManager;

    @FXML
    private AnchorPane paneInterface;
    @FXML
    private ToggleButton tabUsuario, tabHome, tabGraficos;

    //( Metodos chamados ao inicializar o fxml )----------------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.tabManager = new TabManager(tabUsuario,tabHome,tabGraficos);
        appModel.setRootPane(paneInterface);
        appModel.carregarAplicacao(tabUsuario,tabHome,tabGraficos);
    }

    //----------------------------------------------------------------------------------------------------------------------

    //( Métodos chamados pelo usuário ao acionar algum evento )-------------------------------------------------------------
    @FXML
    public void trocarTab(ActionEvent event) {
        ToggleButton botaoClicado = (ToggleButton) event.getSource();
        tabManager.alternarAba(botaoClicado);
    }
    //----------------------------------------------------------------------------------------------------------------------
}