package org.example.apsconsumodeagua.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.apsconsumodeagua.models.usuario.UsuarioModel;
import org.example.apsconsumodeagua.services.UsuarioService;

import java.net.URL;
import java.util.ResourceBundle;

public class TabUsuarioController implements Initializable {
    UsuarioModel usuario = UsuarioService.getInstance().getUsuarioLogado();
    @FXML
    public AnchorPane contentTabUsuario;
    @FXML
    public TextField nomeField,sobrenomeField,cpfField,emailField,cepField,enderecoField,estadoField,cidadeField,pessoasField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nomeField.setText(usuario.getNome());
        sobrenomeField.setText(usuario.getSobrenome());
        cpfField.setText(usuario.getCpf());
        emailField.setText(usuario.getEmail());
        cepField.setText(usuario.getCep());
        enderecoField.setText(usuario.getEndereco());
        estadoField.setText(usuario.getEstado());
        cidadeField.setText(usuario.getCidade());
        pessoasField.setText(String.valueOf(usuario.getPessoasNaCasa()));
    }
}
