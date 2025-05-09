package org.example.apsconsumodeagua.database;

import javafx.fxml.FXML;
import org.example.apsconsumodeagua.dtos.usuario.UsuarioRequestDTO;
import org.example.apsconsumodeagua.utils.encryptor.PasswordVerifier;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//Classe responsável pelo acesso e persistência dos dados de login do usuário integrada com o banco de dados------------
public class UsuarioLoginDAO {
    @FXML
    private final PasswordVerifier verifier = new PasswordVerifier();
    //Função de autenticação do logindo do usuario com banco de dados---------------------------------------------------
    public boolean autenticacaoUsuario(UsuarioRequestDTO objUsuarioRequestDTO) {
        try(Connection conexao = DatabaseConnection.conectarDB()) {
            assert conexao != null;
            try (PreparedStatement pstm = conexao.prepareStatement("SELECT senha FROM usuario WHERE cpf = ?")) {
                pstm.setString(1, objUsuarioRequestDTO.getCpf());
                try (ResultSet rs = pstm.executeQuery()) {
                    if (rs.next()) {
                        return verifier.verifyPassword(objUsuarioRequestDTO.getPassword(), rs.getString("senha"));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
        return false;
    }
    //Função para pegar os dados do usuário no banco de dados-----------------------------------------------------------
    public static List<UsuarioRequestDTO> getDados(String cpf) {
        List<UsuarioRequestDTO> todosDados = new ArrayList<>();
        String sql = "SELECT usuario.nome, usuario.sobrenome, usuario.email, usuario.cpf, usuario.senha, " +
                     "cep.bairro, cep.rua, endereco.numero, cep.cidade, cep.estado, cep.cep, usuario.nummoradores " +
                     "FROM usuario " +
                     "JOIN cep ON usuario.cepfk = cep.cep " +
                     "JOIN endereco ON cep.cep = endereco.cep " +
                     "WHERE usuario.cpf = ?";
        try(Connection conexao = DatabaseConnection.getConexao();
            PreparedStatement pstm = conexao.prepareStatement(sql)){

            pstm.setString(1, cpf);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                UsuarioRequestDTO usuarioRequestLoginDTO = new UsuarioRequestDTO();
                usuarioRequestLoginDTO.setNome(rs.getString("nome"));
                usuarioRequestLoginDTO.setSobrenome(rs.getString("sobrenome"));
                usuarioRequestLoginDTO.setEmail(rs.getString("email"));
                usuarioRequestLoginDTO.setCpf(rs.getString("cpf"));
                usuarioRequestLoginDTO.setCep(rs.getString("cep"));
                usuarioRequestLoginDTO.setPassword(rs.getString("senha"));
                usuarioRequestLoginDTO.setBairro(rs.getString("bairro"));
                usuarioRequestLoginDTO.setRua(rs.getString("rua"));
                usuarioRequestLoginDTO.setNumero(rs.getString("numero"));
                usuarioRequestLoginDTO.setCidade(rs.getString("cidade"));
                usuarioRequestLoginDTO.setEstado(rs.getString("estado"));
                usuarioRequestLoginDTO.setPessoasNaCasa(Integer.parseInt(rs.getString("nummoradores")));
                todosDados.add(usuarioRequestLoginDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return todosDados;
    }
    //Registro dos dados do usuário no banco de dados-------------------------------------------------------------------
    public static void registrarDados(String nome, String sobrenome, String email, String cpf, String cep, String bairro,
        String rua, String numero, String cidade, String estado, String senha, int pessoas) {
        String sqlCep = "insert into cep values ( ?, ?, ?, ?, ?);";

        try(Connection conexao = DatabaseConnection.getConexao();
            PreparedStatement pstmCep = conexao.prepareStatement(sqlCep)) {
            pstmCep.setString(1, cep);
            pstmCep.setString(2, rua);
            pstmCep.setString(3, bairro);
            pstmCep.setString(4, cidade);
            pstmCep.setString(5, estado);
            pstmCep.executeUpdate();
            String sqlEndereco = "insert into endereco (cep, numero) values (?, ?);";
            try(PreparedStatement pstmEndereco = conexao.prepareStatement(sqlEndereco)){
                pstmEndereco.setString(1, cep);
                pstmEndereco.setString(2, numero);
                pstmEndereco.executeUpdate();
                String sqlUsuario = "insert into usuario (nome, sobrenome, email, cpf, senha, cepfk, nummoradores) values \n" +
                            "(?, ?, ?, ?, ?, ?, ?);";
                try (PreparedStatement pstmUsuario = conexao.prepareStatement(sqlUsuario)){
                    pstmUsuario.setString(1, nome);
                    pstmUsuario.setString(2, sobrenome);
                    pstmUsuario.setString(3, email);
                    pstmUsuario.setString(4, cpf);
                    pstmUsuario.setString(5, senha);
                    pstmUsuario.setString(6, cep);
                    pstmUsuario.setString(7, String.valueOf(pessoas));
                    pstmUsuario.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    //Atualização do numero de moradores no banco de dados--------------------------------------------------------------
    public static void atualizarMoradores(int pessoas, String cpf) {
        String sql = "UPDATE usuario " +
                     "SET nummoradores = ? " +
                     "where cpf = ?";
        try(Connection conexao = DatabaseConnection.getConexao();
            PreparedStatement pstm = conexao.prepareStatement(sql)){

            pstm.setInt(1, pessoas);
            pstm.setString(2, cpf);
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    //------------------------------------------------------------------------------------------------------------------
}
