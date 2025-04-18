package org.example.apsconsumodeagua.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.apsconsumodeagua.Application;
import org.example.apsconsumodeagua.model.usuario.UsuarioModel;
import org.example.apsconsumodeagua.service.UsuarioService;
import org.example.apsconsumodeagua.utils.Validadores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public AnchorPane paneInterface;
    private final UsuarioService usuarioService = UsuarioService.getInstance();
    @FXML
    public PasswordField senhaField,confSenhaField;

    @FXML
    private  TextField nomeField,sobrenomeField,cpfField,emailField,cepField,enderecoField,estadoField,cidadeField,pessoasField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void registrar(ActionEvent event) throws IOException {
        if (Validadores.osCamposEstaoPreenchidos(paneInterface, nomeField, sobrenomeField, emailField, cpfField, cepField, enderecoField, estadoField, pessoasField)) {
            if (!Validadores.osCamposSaoIguais(paneInterface, senhaField, confSenhaField)) {
                return;
            }

            // Agora tudo está validado corretamente
            String nome = nomeField.getText();
            String sobrenome = sobrenomeField.getText();
            String email = emailField.getText();
            String cpf = cpfField.getText();
            String cep = cepField.getText();
            String endereco = enderecoField.getText();
            String cidade = cidadeField.getText();
            String estado = estadoField.getText();
            String senha = senhaField.getText();
            int pessoas = Integer.parseInt(pessoasField.getText());

            usuarioService.setUsuarioLogado(new UsuarioModel(nome, sobrenome, email, cpf, cep, endereco, cidade, estado, senha, pessoas));

            FXMLLoader novaTela = new FXMLLoader(Application.class.getResource("/org/example/apsconsumodeagua/views/aplicacao.fxml"));
            Scene novaCena = new Scene(novaTela.load());
            Stage palco = (Stage) ((Node) event.getSource()).getScene().getWindow();
            palco.setScene(novaCena);
            palco.show();
        }
    }
}
