package org.example.apsconsumodeagua.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.apsconsumodeagua.Application;
import org.example.apsconsumodeagua.database.UsuarioLoginDAO;
import org.example.apsconsumodeagua.dtos.usuario.UsuarioRequestDTO;
import org.example.apsconsumodeagua.models.usuario.UsuarioModel;
import org.example.apsconsumodeagua.services.UsuarioService;
import org.example.apsconsumodeagua.utils.Toast;
import org.example.apsconsumodeagua.utils.Validadores;
import org.example.apsconsumodeagua.utils.constantes.CaminhoFxml;
import org.example.apsconsumodeagua.utils.encryptor.PasswordHasher;
import org.example.apsconsumodeagua.utils.enums.ToastEnum;

import java.io.IOException;
import java.util.List;

//Classe responsavel por efetuar o Registro e Login do usuário ao iniciar o aplicativo----------------------------------
public class LoginController {
    public AnchorPane paneInterface;
    private final UsuarioService usuarioService = UsuarioService.getInstance();
    @FXML
    public VBox vboxLogin, vboxRegistrar;
    @FXML
    public PasswordField senhaField, confSenhaField, senhaLoginField;
    private final PasswordHasher hasher = new PasswordHasher();
    @FXML
    private TextField nomeField, sobrenomeField, cpfField, emailField, cepField, bairroField, ruaField, numeroField, estadoField, cidadeField, pessoasField, cpfLoginField;
    //Alterna as janelas entre Login e Registro-------------------------------------------------------------------------
    public void onBtnCadastrarseClick() {
        vboxLogin.setVisible(false);
        vboxRegistrar.setVisible(true);
    }
    public void onBtnLoginRegistrarClick() {
        vboxRegistrar.setVisible(false);
        vboxLogin.setVisible(true);
    }
    //Faz o login do usuário--------------------------------------------------------------------------------------------
    public void login(ActionEvent event) throws IOException {
        //verifica se o cpf e senha existem no Banco de Dados-----------------------------------------------------------
        if (Validadores.osCamposEstaoPreenchidos(paneInterface, cpfLoginField, senhaLoginField)) {
            UsuarioRequestDTO objUsuarioRequestDTO = new UsuarioRequestDTO();
            objUsuarioRequestDTO.setCpf(cpfLoginField.getText());
            objUsuarioRequestDTO.setPassword(senhaLoginField.getText());
            UsuarioLoginDAO objUsuarioLoginDAO = new UsuarioLoginDAO();
            //puxa os dados do usuário no Banco de Dados----------------------------------------------------------------
            if (objUsuarioLoginDAO.autenticacaoUsuario(objUsuarioRequestDTO)) {
                List<UsuarioRequestDTO> dadosUsuario = UsuarioLoginDAO.getDados(objUsuarioRequestDTO.getCpf());
                UsuarioRequestDTO usuario = dadosUsuario.getFirst();
                String nome = usuario.getNome();
                String sobrenome = usuario.getSobrenome();
                String email = usuario.getEmail();
                String cpf = usuario.getCpf();
                String cep = usuario.getCep();
                String rua = usuario.getRua();
                String numero = usuario.getNumero();
                String senha = usuario.getPassword();
                String bairro = usuario.getBairro();
                String cidade = usuario.getCidade();
                String estado = usuario.getEstado();
                int pessoas = usuario.getPessoasNaCasa();
                //Executa o login---------------------------------------------------------------------------------------
                usuarioService.setUsuarioLogado(new UsuarioModel(nome, sobrenome, email, cpf, cep, bairro, rua, numero, cidade, estado, senha, pessoas));
                carregarTelaAplicacao(event);
            } else {
                Toast.mostrarToast(paneInterface, "CPF ou Senha incorretos", ToastEnum.ERRO);
            }
        }
    }
    //Faz o registro do usuário-----------------------------------------------------------------------------------------
    public void registrar(ActionEvent event){
        //Verifica se todos os campos estão estão preenchidos da maneira correta----------------------------------------
        if (Validadores.osCamposEstaoPreenchidos(paneInterface, nomeField, sobrenomeField, emailField, cpfField, cepField, bairroField, ruaField, numeroField, estadoField, pessoasField)) {
            if (!Validadores.osCamposSaoIguais(paneInterface, senhaField, confSenhaField)) return;
            if (!Validadores.osCamposEstaoPreenchidosComInteiros(paneInterface, pessoasField)) return;
            //Pega o registro e adiciona ao banco de dados--------------------------------------------------------------
            try {
                String nome = nomeField.getText();
                String sobrenome = sobrenomeField.getText();
                String email = emailField.getText();
                String cpf = cpfField.getText();
                String cep = cepField.getText();
                String bairro = bairroField.getText();
                String rua = ruaField.getText();
                String numero = numeroField.getText();
                String cidade = cidadeField.getText();
                String estado = estadoField.getText();
                String senha = senhaField.getText();
                String senhaGerada = hasher.hashPassword(senha);
                int pessoas = Integer.parseInt(pessoasField.getText());
                UsuarioLoginDAO.registrarDados(nome, sobrenome, email, cpf, cep, bairro, rua, numero, cidade, estado, senhaGerada, pessoas);
                //Efetua o login----------------------------------------------------------------------------------------
                usuarioService.setUsuarioLogado(new UsuarioModel(nome, sobrenome, email, cpf, cep, bairro, rua, numero, cidade, estado, senha, pessoas));
                carregarTelaAplicacao(event);
            } catch (Exception e) {
                Toast.mostrarToast(paneInterface, "Erro no cadastro", ToastEnum.ERRO);
            }
        }
    }
    private void carregarTelaAplicacao(ActionEvent event) throws IOException {
        FXMLLoader novaTela = new FXMLLoader(Application.class.getResource(CaminhoFxml.APLICACAO));
        Scene novaCena = new Scene(novaTela.load());
        Stage palco = (Stage) ((Node) event.getSource()).getScene().getWindow();
        palco.setScene(novaCena);
        palco.show();
    }
    //------------------------------------------------------------------------------------------------------------------
}