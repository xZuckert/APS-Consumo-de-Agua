package org.example.apsconsumodeagua.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.apsconsumodeagua.Application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private  TextField nomeField,sobrenomeField,cpfField,emailField,cepField,enderecoField,estadoField,cidadeField,pessoasField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }

    public void registrar(ActionEvent event) throws IOException {
        FXMLLoader novaTela = new FXMLLoader(Application.class.getResource("/org/example/apsconsumodeagua/views/aplicacao.fxml"));
        Scene novaCena = new Scene(novaTela.load());
        AplicacaoController controller = novaTela.getController();
//        controller.setNomeField(nomeField.getText());
//        controller.setSobrenomeField(sobrenomeField.getText());
//        controller.setCpfField(cpfField.getText());
//        controller.setEmailField(emailField.getText());
//        controller.setCepField(cepField.getText());
//        controller.setEnderecoField(enderecoField.getText());
//        controller.setEstadoField(estadoField.getText());
//        controller.setCidadeField(cidadeField.getText());
//        controller.setPessoasField(pessoasField.getText());

        // Pega a janela atual a partir do botão que disparou o evento
        Stage palco = (Stage) ((Node) event.getSource()).getScene().getWindow();

        palco.setScene(novaCena);
        palco.show();
    }
}
