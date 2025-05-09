package org.example.apsconsumodeagua.database;

import java.sql.Connection;
import java.sql.DriverManager;

//Classe usada para conexão com o banco de dados------------------------------------------------------------------------
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://metro.proxy.rlwy.net:52382/railway";
    private static final String USER = "root";
    private static final String PASSWORD = "bWnNpXxdRFOOmaZPYTjSGgOuhDgSlACm";
    //Função para estabelecer a conexão com o banco de dados------------------------------------------------------------
    public static Connection conectarDB() {
        try {
            // Conecta e retorna a conexão
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            return null;
        }
    }
    //Função simplificada para obter a conexão--------------------------------------------------------------------------
    public static Connection getConexao() {
        return conectarDB();
    }
}