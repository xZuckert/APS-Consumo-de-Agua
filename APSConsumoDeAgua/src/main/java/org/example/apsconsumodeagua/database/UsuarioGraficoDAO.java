package org.example.apsconsumodeagua.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import org.example.apsconsumodeagua.core.AppModel;
import org.example.apsconsumodeagua.managers.GraficoManager;
import org.example.apsconsumodeagua.utils.constantes.AppConstantes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class UsuarioGraficoDAO {
    private final static AppModel appModel = AppModel.getInstance();
    private final static GraficoManager manager = appModel.getGraficoManager();
    public static final Map<String, ObservableList<XYChart.Data<String,Number>>> valores = new HashMap<>();
    public static Map<String,double[]> dados = new HashMap<>();
    public static void getDadosUsuarioGraficoDB(String cpf) {
        try {
            Connection conexao = DatabaseConnection.getConexao();
            String sql = "SELECT grafico.ano, grafico.janeiro, grafico.fevereiro, grafico.março, grafico.abril, grafico.maio, grafico.junho,\n" +
                         "grafico.julho, grafico.agosto, grafico.setembro, grafico.outubro, grafico.novembro, grafico.dezembro\n" +
                         "FROM grafico\n" +
                         "JOIN usuario ON grafico.cpfUsuario = usuario.cpf\n" +
                         "WHERE usuario.cpf = ?";
            PreparedStatement pstm = conexao.prepareStatement(sql);
            pstm.setString(1, cpf);

            ResultSet rs = pstm.executeQuery();

            manager.setGraficosCarregados(false);
            while (rs.next()) {
                String ano = rs.getString("ano");
                double[] consumos = new double[] {
                        rs.getDouble("janeiro"), rs.getDouble("fevereiro"), rs.getDouble("março"),
                        rs.getDouble("abril"),rs.getDouble("maio"), rs.getDouble("junho"),
                        rs.getDouble("julho"), rs.getDouble("agosto"),rs.getDouble("setembro"),
                        rs.getDouble("outubro"), rs.getDouble("novembro"), rs.getDouble("dezembro")
                };
                ObservableList<XYChart.Data<String, Number>> dados = gerarDados(consumos);
                manager.valores.put(ano, dados);
            }
            manager.setGraficosCarregados(true);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    private static ObservableList<XYChart.Data<String, Number>> gerarDados(double... consumos) {
        ObservableList<XYChart.Data<String, Number>> dados = FXCollections.observableArrayList();
        String[] MESES = AppConstantes.MESES;
        for (int i = 0; i < MESES.length && i < consumos.length; i++) {
            dados.add(new XYChart.Data<>(MESES[i], consumos[i]));
        }
        return dados;
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
