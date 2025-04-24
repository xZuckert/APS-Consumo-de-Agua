package org.example.apsconsumodeagua.database;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import org.example.apsconsumodeagua.dtos.usuario.UsuarioRequestDTO;
import org.example.apsconsumodeagua.utils.Toast;
import org.example.apsconsumodeagua.utils.enums.ToastEnum;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioLoginDAO {
    @FXML
    private AnchorPane paneInterface;
    Connection conexao;

    public ResultSet autenticacaoUsuario(UsuarioRequestDTO objUsuarioRequestDTO) {
        conexao = DatabaseConnection.conectarDB(conexao);
        try {
            //verifica se existe os cpf e a senha na tabela de usuario
            String sql = "select * from usuario where cpf = ? and senha = ?";
            PreparedStatement pstm = conexao.prepareStatement(sql);
            pstm.setString(1, objUsuarioRequestDTO.getCpf());
            pstm.setString(2, objUsuarioRequestDTO.getPassword());
            ResultSet rs = pstm.executeQuery();
            return rs;
        } catch (SQLException erro) {
            Toast.mostrarToast(paneInterface, "CPF ou Senha incorretos", ToastEnum.ERRO);
            return null;
        }
    }

    public static List<UsuarioRequestDTO> getDadosTeste(UsuarioRequestDTO objUsuarioRequestDTO) {
        List<UsuarioRequestDTO> todosDados = new ArrayList<>();
        try {
            Connection conexao = DatabaseConnection.getConexao();
            String sql = "select usuario.nome, usuario.sobrenome, usuario.email, usuario.cpf, usuario.senha,\n" +
                    "cep.bairro, cep.cidade, cep.estado, cep.cep, usuario.nummoradores\n" +
                    "from usuario\n" +
                    "join cep on usuario.cepfk = cep.cep\n" +
                    "join endereco on cep.cep = endereco.cep where usuario.cpf = ? and usuario.senha = ?;";
            PreparedStatement pstm = conexao.prepareStatement(sql);
            pstm.setString(1, objUsuarioRequestDTO.getCpf());
            pstm.setString(2, objUsuarioRequestDTO.getPassword());

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                UsuarioRequestDTO usuarioRequestLoginDTO = new UsuarioRequestDTO();
                usuarioRequestLoginDTO.setNome(rs.getString("nome"));
                usuarioRequestLoginDTO.setSobrenome(rs.getString("sobrenome"));
                usuarioRequestLoginDTO.setEmail(rs.getString("email"));
                usuarioRequestLoginDTO.setCpf(rs.getString("cpf"));
                usuarioRequestLoginDTO.setCep(rs.getString("cep"));
                usuarioRequestLoginDTO.setPassword(rs.getString("senha"));
                usuarioRequestLoginDTO.setEndereco(rs.getString("bairro"));
                usuarioRequestLoginDTO.setCidade(rs.getString("cidade"));
                usuarioRequestLoginDTO.setEstado(rs.getString("estado"));
                usuarioRequestLoginDTO.setPessoasNaCasa(Integer.parseInt(rs.getString("nummoradores")));
                todosDados.add(usuarioRequestLoginDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return todosDados;
    }
}
