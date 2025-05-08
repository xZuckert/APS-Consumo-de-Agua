package org.example.apsconsumodeagua.database;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import org.example.apsconsumodeagua.dtos.usuario.UsuarioGraficoDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuarioGraficoDAO {
    public static final Map<String, ObservableList<XYChart.Data<String,Number>>> valores = new HashMap<>();
    public static Map<String,double[]> dados = new HashMap<>();
    public static Map<String, ObservableList<XYChart.Data<String, Number>>> dadosGraficoDB(String cpf) {
        try {
            Connection conexao = DatabaseConnection.getConexao();
            String sql = "SELECT grafico.ano, grafico.janeiro, grafico.fevereiro, grafico.março, grafico.abril, grafico.maio, grafico.junho,\n" +
                         "grafico.julho, grafico.agosto, grafico.setembro, grafico.outubro, grafico.novembro, grafico.dezembro\n" +
                         "FROM grafico\n" +
                         "JOIN usuario ON grafico.cpfUsuario = usuario.cpf\n" +
                         "WHERE usuario.cpf = ? and usuario.cpf = grafico.cpfUsuario;";
            PreparedStatement pstm = conexao.prepareStatement(sql);
            pstm.setString(1, cpf);

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                String ano = rs.getString("ano");
                double[] consumos = new double[] {
                        rs.getDouble("janeiro"), rs.getDouble("fevereiro"), rs.getDouble("março"),
                        rs.getDouble("abril"),rs.getDouble("maio"), rs.getDouble("junho"),
                        rs.getDouble("julho"), rs.getDouble("agosto"),rs.getDouble("setembro"),
                        rs.getDouble("outubro"), rs.getDouble("novembro"), rs.getDouble("dezembro")
                };
                ObservableList<XYChart.Data<String, Number>> dados = UsuarioGraficoDTO.gerarDados(consumos);
                UsuarioGraficoDAO.valores.put(ano,dados);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return valores;
    }
    /*Connectio conexao = DatabaseConnection.getConexao();
    String sql = "insert into graphic (cpfUsuarioAno, ano, Janeiro, fevereiro, março, abril, maio, junho,
    julho, agosto, setembro, outubro, novembro, dezembro) values (
    ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?
    );"
    PreparedStatement pstmGrafico = conexao.prepareStatement(sql);
    pstmGrafico.setString(1, UsuarioRequestDTO.getCpf());
    pstmGrafico.setString(2, );
    pstmGrafico.setString(3, );
    pstmGrafico.setString(4, );
    pstmGrafico.setString(5, );
    pstmGrafico.setString(6. );
    pstmGrafico.setString(7, );
    pstmGrafico.setString(8, );
    pstmGrafico.setString(9, );
    pstmGrafico.setString(10, );
    pstmGrafico.setString(11, );
    pstmGrafico.setString(12, );
    pstmGrafico.setString(13, );
    pstmGrafico.setString(14, );
    */
    /*insert into graphic (idDado, cpfUsuario, idano) values (
    ?, ?
    );*/
}
