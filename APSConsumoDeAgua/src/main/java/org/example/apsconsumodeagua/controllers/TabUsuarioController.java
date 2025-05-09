package org.example.apsconsumodeagua.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.apsconsumodeagua.core.AppModel;
import org.example.apsconsumodeagua.database.UsuarioLoginDAO;
import org.example.apsconsumodeagua.models.usuario.UsuarioModel;
import org.example.apsconsumodeagua.services.UsuarioService;
import org.example.apsconsumodeagua.utils.Validadores;
import org.example.apsconsumodeagua.utils.constantes.AppConstantes;

import java.net.URL;
import java.util.ResourceBundle;

//Classe que controla a view Usuario------------------------------------------------------------------------------------
public class TabUsuarioController implements Initializable {
    AppModel appModel = AppModel.getInstance();
    //Variavel que recebe o usuario logado------------------------------------------------------------------------------
    final UsuarioModel usuario = UsuarioService.getInstance().getUsuarioLogado();
    @FXML
    public AnchorPane contentTabUsuario;
    @FXML
    public TextField nomeField,sobrenomeField,cpfField,emailField,cepField, bairroField, ruaField, numeroField,estadoField,cidadeField,pessoasField;
    //Função chamada ao iniciar o fxml de usuario-----------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nomeField.setText(usuario.getNome());
        sobrenomeField.setText(usuario.getSobrenome());
        cpfField.setText(usuario.getCpf());
        emailField.setText(usuario.getEmail());
        cepField.setText(usuario.getCep());
        bairroField.setText(usuario.getBairro());
        ruaField.setText(usuario.getRua());
        numeroField.setText(usuario.getNumero());
        estadoField.setText(usuario.getEstado());
        cidadeField.setText(usuario.getCidade());
        pessoasField.setText(String.valueOf(usuario.getPessoasNaCasa()));
    }
    //Atualiza o numero de moradores do usuario-------------------------------------------------------------------------
    @FXML
    public void atualizarQuantidadeMoradores(){
        if(Validadores.osCamposEstaoPreenchidosComInteiros(appModel.getRootPane(),pessoasField)){
            UsuarioModel usuario = UsuarioService.getInstance().getUsuarioLogado();
            usuario.setPessoasNaCasa(Integer.parseInt(pessoasField.getText()));
            int pessoas = usuario.getPessoasNaCasa();
            String cpf = usuario.getCpf();
            UsuarioLoginDAO.atualizarMoradores(pessoas, cpf);
            atualizarConsumoIdeal();
        }
    }
    //Atualiza o calculo de consumo ideal com base no numero de moradores-----------------------------------------------
    public void atualizarConsumoIdeal(){
        usuario.setConsumoIdeal(AppConstantes.CONSUMO_IDEAL_POR_PESSOA * 30 * usuario.getPessoasNaCasa());
    }
    //------------------------------------------------------------------------------------------------------------------
}