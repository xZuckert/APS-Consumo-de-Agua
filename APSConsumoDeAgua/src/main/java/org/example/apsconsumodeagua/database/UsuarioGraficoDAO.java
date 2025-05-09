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
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

//Classe responsável pelo acesso e persistência dos dados do grafico do usuário integrada com o banco de dados----------
public class UsuarioGraficoDAO {
    private final static AppModel appModel = AppModel.getInstance();
    private final static GraficoManager manager = appModel.getGraficoManager();
    public static Map<String, double[]> dados = new HashMap<>();
    //Pega os dados do grafico do usuario no banco de dados e insere no aplicativo local--------------------------------
    public static void getDadosUsuarioGraficoDB(String cpf) {
        String sql = "SELECT grafico.ano, grafico.janeiro, grafico.fevereiro, grafico.março, grafico.abril, grafico.maio, grafico.junho,\n" +
                     "grafico.julho, grafico.agosto, grafico.setembro, grafico.outubro, grafico.novembro, grafico.dezembro\n" +
                     "FROM grafico\n" +
                     "JOIN usuario ON grafico.cpfUsuario = usuario.cpf\n" +
                     "WHERE usuario.cpf = ?";
        try(Connection conexao = DatabaseConnection.getConexao();
            PreparedStatement pstm = conexao.prepareStatement(sql)) {

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
    //Recebe os dados do banco de dados e transforma no tipo de dado usado nos graficos---------------------------------
    private static ObservableList<XYChart.Data<String, Number>> gerarDados(double... consumos) {
        ObservableList<XYChart.Data<String, Number>> dados = FXCollections.observableArrayList();
        String[] MESES = AppConstantes.MESES;
        for (int i = 0; i < MESES.length && i < consumos.length; i++) {
            dados.add(new XYChart.Data<>(MESES[i], consumos[i]));
        }
        return dados;
    }
    //Puxa os valores de cada mes do ano--------------------------------------------------------------------------------
    public static double[] getDados(String ano) {
        double[] dados = new double[12];
        ObservableList<XYChart.Data<String, Number>> valores = manager.valores.get(ano);
        if (valores == null) return dados;
        for (XYChart.Data<String, Number> dado : valores) {
            if (dado.getYValue() != null) {
                Integer index = AppConstantes.MES_INDEX.get(dado.getXValue());
                dados[index] = dado.getYValue().intValue();
            }
        }
        return dados;
    }
    //Criar um grafico no banco de dados com os valores que foram puxados-----------------------------------------------
    public static void criarGraficoDB(String cpf, String ano){
        String sql = "INSERT INTO grafico " +
                     "(cpfUsuario,ano,janeiro,fevereiro,março,abril,maio,junho,julho,agosto,setembro,outubro,novembro,dezembro)" +
                     "SELECT ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? " +
                     "WHERE NOT EXISTS (" +
                     "    SELECT 1 FROM grafico WHERE cpfUsuario = ? AND ano = ?" +
                     ")";
        try (Connection conexao = DatabaseConnection.getConexao();
             PreparedStatement pstm = conexao.prepareStatement(sql)){
            pstm.setString(1, cpf);
            pstm.setString(2, ano);
            setMesesStatement(pstm,getDados(ano),3);
            pstm.setString(15, cpf);
            pstm.setString(16, ano);
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    //Atualiza os valores dos meses no banco de dados pelos inseridos pelo usuario local--------------------------------
    public static void atualizarGraficoDB(String cpf, String ano) {
        String sql = "UPDATE grafico " +
                     "SET janeiro = ?, fevereiro = ?, março = ?, abril = ?, maio = ?, junho = ?, julho = ?, " +
                     "agosto = ?, setembro = ?, outubro = ?, novembro = ?, dezembro = ?" +
                     "where cpfUsuario = ? AND ano = ?";
        try (Connection conexao = DatabaseConnection.getConexao();
             PreparedStatement pstm = conexao.prepareStatement(sql)){
            setMesesStatement(pstm,getDados(ano),1);
            pstm.setString(13, cpf);
            pstm.setString(14, ano);
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    private static void setMesesStatement(PreparedStatement pstm, double[] meses, int offset) throws SQLException {
        for (int i = 0; i < 12; i++) {
            pstm.setDouble(i + offset, meses[i]);
        }
    }
    //------------------------------------------------------------------------------------------------------------------
}
