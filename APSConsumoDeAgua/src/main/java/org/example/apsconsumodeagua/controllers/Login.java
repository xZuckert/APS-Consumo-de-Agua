package org.example.apsconsumodeagua.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.apsconsumodeagua.Application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {
    @FXML
    private AnchorPane paneInterface;
    @FXML
    private  TextField nomeField,sobrenomeField,cepField,enderecoField,estadoField,cidadeField,pessoasField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }

    public void entrar(ActionEvent event) throws IOException {
        FXMLLoader novaTela = new FXMLLoader(Application.class.getResource("viewmodel2.fxml"));
        Scene novaCena = new Scene(novaTela.load());
        Controller2 controller = novaTela.getController();
        controller.setNomeField(nomeField.getText());
        controller.setSobrenomeField(sobrenomeField.getText());
        controller.setCepField(cepField.getText());
        controller.setEnderecoField(enderecoField.getText());
        controller.setEstadoField(estadoField.getText());
        controller.setCidadeField(cidadeField.getText());
        controller.setPessoasField(pessoasField.getText());

        // Pega a janela atual a partir do botão que disparou o evento
        Stage palco = (Stage) ((Node) event.getSource()).getScene().getWindow();

        palco.setScene(novaCena);
        palco.show();
    }
}
