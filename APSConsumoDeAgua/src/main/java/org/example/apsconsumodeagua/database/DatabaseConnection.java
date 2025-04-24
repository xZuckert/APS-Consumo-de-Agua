package org.example.apsconsumodeagua.database;

import java.sql.*;

public class DatabaseConnection {

    public static Connection conectarDB(Connection conexao) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/aps_consumo_de_agua?user=root&password="); // Cria a conexão
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
