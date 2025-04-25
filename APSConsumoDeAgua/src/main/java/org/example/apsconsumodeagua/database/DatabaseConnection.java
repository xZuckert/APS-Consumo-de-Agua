package org.example.apsconsumodeagua.database;

import java.sql.*;

public class DatabaseConnection {

    public static Connection conectarDB(Connection conexao) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexao = DriverManager.getConnection("jdbc:mysql://metro.proxy.rlwy.net:52382/railway?user=root&password=bWnNpXxdRFOOmaZPYTjSGgOuhDgSlACm"); // Cria a conexão
            return conexao;
        } catch (Exception e) {
            System.out.println("Erro ao conectar ao banco de dados " + e);
            return null;
        }
    }

    public static Connection getConexao() {
        Connection conexao = null;
        conexao = DatabaseConnection.conectarDB(conexao);
        return conexao;
    }
}
