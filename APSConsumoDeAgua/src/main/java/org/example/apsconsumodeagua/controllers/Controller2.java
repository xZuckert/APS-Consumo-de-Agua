package org.example.apsconsumodeagua.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller2 implements Initializable {
    @FXML
    private TextField nomeField, sobrenomeField, cepField, enderecoField,estadoField, cidadeField, pessoasField;
    @FXML
    private AnchorPane paneInterface, contentTabUsuario, contentTabHome, contentTabGraficos;
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
