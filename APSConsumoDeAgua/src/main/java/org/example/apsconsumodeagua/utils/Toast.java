package org.example.apsconsumodeagua.utils;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class Toast {
    public enum tipoToast {ALERT, SUCESSO, ERRO}
    public static void mostrarToast(AnchorPane root, String message, tipoToast tipo) {
        String tipoal = switch (tipo) {
            case ALERT -> "rgba(255,0,0,0.7);";
            case SUCESSO -> "rgba(0,255,0,0.7);";
            case ERRO -> "rgba(255,0,0.7);";
        };

        Label toast = new Label(message);
        toast.setFont(new Font("Arial", 16));
        toast.setTextFill(Color.WHITE);
        toast.setStyle("-fx-background-color: " + tipoal + "; -fx-padding: 10px;");

        AnchorPane.setBottomAnchor(toast, 10.0);
        AnchorPane.setRightAnchor(toast, 10.0);

        root.getChildren().add(toast);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> root.getChildren().remove(toast))
        );
        timeline.setCycleCount(1);
        timeline.play();  // Inicia a animação
    }
    public static void mostrarToast(AnchorPane root, String message, tipoToast tipo,int posX, int posY) {
        String tipoal = switch (tipo) {
            case ALERT -> "rgba(255,0,0,0.7);";
            case SUCESSO -> "rgba(0,255,0,0.7);";
            case ERRO -> "rgba(255,0,0.7);";
        };

        Label toast = new Label(message);
        toast.setFont(new Font("Arial", 16));
        toast.setTextFill(Color.WHITE);
        toast.setStyle("-fx-background-color: " + tipoal + "; -fx-padding: 10px;");

        AnchorPane.setLeftAnchor(toast, (double) posX);
        AnchorPane.setBottomAnchor(toast, (double) posY);

        root.getChildren().add(toast);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> root.getChildren().remove(toast))
        );
        timeline.setCycleCount(1);
        timeline.play();  // Inicia a animação
    }
}


