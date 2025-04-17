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

public class LoginController implements Initializable {
    @FXML
    private AnchorPane paneInterface;
    @FXML
    private  TextField nomeField,sobrenomeField,cpfField,emailField,cepField,enderecoField,estadoField,cidadeField,pessoasField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }

    public void registrar(ActionEvent event) throws IOException {
        FXMLLoader novaTela = new FXMLLoader(Application.class.getResource("aplicacao.fxml"));
        Scene novaCena = new Scene(novaTela.load());
        AplicacaoController aplicacaoController = novaTela.getController();
        aplicacaoController.setNomeField(nomeField.getText());
        aplicacaoController.setSobrenomeField(sobrenomeField.getText());
        aplicacaoController.setcpfField(cpfField.getText());
        aplicacaoController.setEmailField(emailField.getText());
        aplicacaoController.setCepField(cepField.getText());
        aplicacaoController.setEnderecoField(enderecoField.getText());
        aplicacaoController.setEstadoField(estadoField.getText());
        aplicacaoController.setCidadeField(cidadeField.getText());
        aplicacaoController.setPessoasField(pessoasField.getText());

        // Pega a janela atual a partir do botão que disparou o evento
        Stage palco = (Stage) ((Node) event.getSource()).getScene().getWindow();

        palco.setScene(novaCena);
        palco.show();
    }
}
