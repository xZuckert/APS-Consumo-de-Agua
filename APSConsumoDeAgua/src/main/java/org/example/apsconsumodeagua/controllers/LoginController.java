package org.example.apsconsumodeagua.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.apsconsumodeagua.Application;
import org.example.apsconsumodeagua.database.DatabaseConnection;
import org.example.apsconsumodeagua.database.UsuarioLoginDAO;
import org.example.apsconsumodeagua.dtos.usuario.UsuarioRequestDTO;
import org.example.apsconsumodeagua.models.usuario.UsuarioModel;
import org.example.apsconsumodeagua.services.UsuarioService;
import org.example.apsconsumodeagua.utils.Toast;
import org.example.apsconsumodeagua.utils.Validadores;
import org.example.apsconsumodeagua.utils.enums.ToastEnum;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public AnchorPane paneInterface;
    private final UsuarioService usuarioService = UsuarioService.getInstance();

    @FXML
    public VBox vboxLogin, vboxRegistrar;

    @FXML
    public PasswordField senhaField, confSenhaField, senhaLoginField;

    @FXML
    private TextField nomeField, sobrenomeField, cpfField, emailField, cepField, bairroField, ruaField, numeroField, estadoField, cidadeField, pessoasField, cpfLoginField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void onBtnCadastrarseClick(ActionEvent actionEvent) {
        vboxLogin.setVisible(false);
        vboxRegistrar.setVisible(true);
    }
    public void onBtnLoginRegistrarClick(ActionEvent actionEvent) {
        vboxRegistrar.setVisible(false);
        vboxLogin.setVisible(true);
    }

    public void login(ActionEvent event) throws IOException {
        if (Validadores.osCamposEstaoPreenchidos(paneInterface, cpfLoginField, senhaLoginField)) {
            try {
                //verifica se o cpf e senha existem no Banco de Dados
                UsuarioRequestDTO objUsuarioRequestDTO = new UsuarioRequestDTO();
                objUsuarioRequestDTO.setCpf(cpfLoginField.getText());
                objUsuarioRequestDTO.setPassword(senhaLoginField.getText());
                UsuarioLoginDAO objUsuarioLoginDAO = new UsuarioLoginDAO();
                ResultSet rsUsuarioLoginDAO = objUsuarioLoginDAO.autenticacaoUsuario(objUsuarioRequestDTO);
                if (rsUsuarioLoginDAO.next()) {
                    Connection conexao = DatabaseConnection.getConexao();
                    List<UsuarioRequestDTO> dadosUsuario = UsuarioLoginDAO.getDadosTeste(objUsuarioRequestDTO);
                    UsuarioRequestDTO usuario = dadosUsuario.get(0);

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

                    //Executar login
                    usuarioService.setUsuarioLogado(new UsuarioModel(nome, sobrenome, email, cpf, cep, bairro, rua, numero, cidade, estado, senha, pessoas));
                    FXMLLoader novaTela = new FXMLLoader(Application.class.getResource("/org/example/apsconsumodeagua/views/Aplicacao.fxml"));
                    Scene novaCena = new Scene(novaTela.load());
                    Stage palco = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    palco.setScene(novaCena);
                    palco.show();
                } else {
                    Toast.mostrarToast(paneInterface, "CPF ou Senha incorretos", ToastEnum.ERRO);
                }
            } catch (SQLException erro) {
                Toast.mostrarToast(paneInterface, "CPF ou Senha incorretos", ToastEnum.ERRO);
            }
        }
    }

    public void registrar(ActionEvent event) throws IOException {
        if (Validadores.osCamposEstaoPreenchidos(paneInterface, nomeField, sobrenomeField, emailField, cpfField, cepField, bairroField, ruaField, numeroField, estadoField, pessoasField)) {
            if (!Validadores.osCamposSaoIguais(paneInterface, senhaField, confSenhaField)) return;
            if (!Validadores.osCamposEstaoPreenchidosComInteiros(paneInterface, pessoasField)) return;

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
            int pessoas = Integer.parseInt(pessoasField.getText());

            usuarioService.setUsuarioLogado(new UsuarioModel(nome, sobrenome, email, cpf, cep, bairro,rua, numero, cidade, estado, senha, pessoas));
            FXMLLoader novaTela = new FXMLLoader(Application.class.getResource("/org/example/apsconsumodeagua/views/Aplicacao.fxml"));
            Scene novaCena = new Scene(novaTela.load());
            Stage palco = (Stage) ((Node) event.getSource()).getScene().getWindow();
            palco.setScene(novaCena);
            palco.show();
        }
    }
}
