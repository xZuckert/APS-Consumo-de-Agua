package org.example.apsconsumodeagua;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

//Classe principal que inicializa toda a aplicação----------------------------------------------------------------------
public class Application extends javafx.application.Application {
    //Define como a aplicação se inicializara---------------------------------------------------------------------------
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/org/example/apsconsumodeagua/views/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("APLICAÇÃO");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    //Executa a aplicação-----------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        launch();
    }
    //------------------------------------------------------------------------------------------------------------------
}