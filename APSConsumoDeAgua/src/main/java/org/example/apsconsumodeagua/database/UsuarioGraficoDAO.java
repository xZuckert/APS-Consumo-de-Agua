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
    public static Map<String, double[]> dados = new HashMap<>();

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
                double[] consumos = new double[]{
                        rs.getDouble("janeiro"), rs.getDouble("fevereiro"), rs.getDouble("março"),
                        rs.getDouble("abril"), rs.getDouble("maio"), rs.getDouble("junho"),
                        rs.getDouble("julho"), rs.getDouble("agosto"), rs.getDouble("setembro"),
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

    public static double[] getDados(String ano) {
        double[] dados = new double[12];
        for (XYChart.Data<String, Number> dado : manager.valores.get(ano)) {
            if (dado.getYValue() != null) {
                Integer index = AppConstantes.MES_INDEX.get(dado.getXValue());
                dados[index] = dado.getYValue().intValue();
            }
        }
        return dados;
    }

    public static void atualizarGraficoDB(String cpf, String ano) {
        double[] meses = getDados(ano);
        double jan = meses[0];
        double fev = meses[1];
        double mar = meses[2];
        double abr = meses[3];
        double mai = meses[4];
        double jun = meses[5];
        double jul = meses[6];
        double ago = meses[7];
        double set = meses[8];
        double out = meses[9];
        double nov = meses[10];
        double dez = meses[11];
        try {
            Connection conexao = DatabaseConnection.getConexao();
            String sql = "UPDATE grafico " +
                         "SET janeiro = ?, fevereiro = ?, março = ?, abril = ?, maio = ?, junho = ?, julho = ?, " +
                         "agosto = ?, setembro = ?, outubro = ?, novembro = ?, dezembro = ?" +
                         "where cpfUsuario = ? AND ano = ?";

            PreparedStatement pstmGrafico = conexao.prepareStatement(sql);
            pstmGrafico.setDouble(1, jan);
            pstmGrafico.setDouble(2, fev);
            pstmGrafico.setDouble(3, mar);
            pstmGrafico.setDouble(4, abr);
            pstmGrafico.setDouble(5, mai);
            pstmGrafico.setDouble(6, jun);
            pstmGrafico.setDouble(7, jul);
            pstmGrafico.setDouble(8, ago);
            pstmGrafico.setDouble(9, set);
            pstmGrafico.setDouble(10, out);
            pstmGrafico.setDouble(11, nov);
            pstmGrafico.setDouble(12, dez);
            pstmGrafico.setString(13, cpf);
            pstmGrafico.setString(14, ano);
            pstmGrafico.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
