package org.example.apsconsumodeagua.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://metro.proxy.rlwy.net:52382/railway";
    private static final String USER = "root";
    private static final String PASSWORD = "bWnNpXxdRFOOmaZPYTjSGgOuhDgSlACm";

    public static Connection conectarDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD); // Conecta e retorna a conexão
        } catch (Exception e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            return null;
        }
    }

    public static Connection getConexao() {
        return conectarDB(); // Método simplificado para obter a conexão
    }
}
