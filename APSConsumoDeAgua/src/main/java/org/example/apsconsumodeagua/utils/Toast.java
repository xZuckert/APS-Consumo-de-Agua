package org.example.apsconsumodeagua.utils;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.example.apsconsumodeagua.utils.enums.ToastEnum;

//Classe para criar, editar e mostrar os Toasts na tela-----------------------------------------------------------------
public class Toast {
    //Funções para mostrar os Toasts------------------------------------------------------------------------------------
    public static void mostrarToast(AnchorPane root, String message, ToastEnum tipo) {
        String tipoal = selecionarCor(tipo);
        Label toast = new Label(message);
        toast.setFont(new Font("Arial", 16));
        toast.setTextFill(Color.WHITE);
        toast.setStyle("-fx-background-color: " + tipoal + "; -fx-padding: 10px; -fx-background-radius: 5px;");
        AnchorPane.setBottomAnchor(toast, 10.0);
        AnchorPane.setRightAnchor(toast, 10.0);
        root.getChildren().add(toast);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> root.getChildren().remove(toast))
        );
        timeline.setCycleCount(1);
        timeline.play();
    }
    public static void mostrarToast(AnchorPane root, String message, ToastEnum tipo, int posX, int posY) {
        String tipoal = selecionarCor(tipo);
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
        //Inicia a animação---------------------------------------------------------------------------------------------
        timeline.play();
    }
    public static void mostrarToast(AnchorPane root, String message, ToastEnum tipo, ToastEnum posicao) {
        String tipoal = selecionarCor(tipo);
        Label toast = new Label(message);
        toast.setFont(new Font("Arial", 16));
        toast.setTextFill(Color.WHITE);
        toast.setStyle("-fx-background-color: " + tipoal + "; -fx-padding: 10px; -fx-background-radius: 5px;");
        root.getChildren().add(toast);
        //Força o layout------------------------------------------------------------------------------------------------
        toast.applyCss();
        toast.layout();
        double width = toast.getWidth();
        double height = toast.getHeight();
        double paneWidth = root.getWidth();
        double paneHeight = root.getHeight();
        //evita bugs de múltiplas âncoras-------------------------------------------------------------------------------
        AnchorPane.clearConstraints(toast);
        //Posição do Toast na tela--------------------------------------------------------------------------------------
        switch (posicao) {
            case CENTRO -> {
                AnchorPane.setLeftAnchor(toast, (paneWidth - width) / 2);
                AnchorPane.setTopAnchor(toast, (paneHeight - height) / 2);
            }
            case CENTRO_TOPO -> {
                AnchorPane.setLeftAnchor(toast, (paneWidth - width) / 2);
                AnchorPane.setTopAnchor(toast, 10.0);
            }
            case CENTRO_BAIXO -> {
                AnchorPane.setLeftAnchor(toast, (paneWidth - width) / 2);
                AnchorPane.setBottomAnchor(toast, 10.0);
            }
            case CENTRO_DIREITA -> {
                AnchorPane.setRightAnchor(toast, 10.0);
                AnchorPane.setTopAnchor(toast, (paneHeight - height) / 2);
            }
            case CENTRO_ESQUERDA -> {
                AnchorPane.setLeftAnchor(toast, 10.0);
                AnchorPane.setTopAnchor(toast, (paneHeight - height) / 2);
            }
            case TOPO_ESQUERDA -> {
                AnchorPane.setTopAnchor(toast, 10.0);
                AnchorPane.setLeftAnchor(toast, 10.0);
            }
            case TOPO_DIREITA -> {
                AnchorPane.setTopAnchor(toast, 10.0);
                AnchorPane.setRightAnchor(toast, 10.0);
            }
            case BAIXO_ESQUERDA -> {
                AnchorPane.setBottomAnchor(toast, 10.0);
                AnchorPane.setLeftAnchor(toast, 10.0);
            }
            case BAIXO_DIREITA -> {
                AnchorPane.setBottomAnchor(toast, 10.0);
                AnchorPane.setRightAnchor(toast, 10.0);
            }
        }
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> root.getChildren().remove(toast))
        );
        timeline.setCycleCount(1);
        timeline.play();
    }
    //Função para selecionar a cor do Toast-----------------------------------------------------------------------------
    private static String selecionarCor(ToastEnum tipo) {
        return switch (tipo) {
            case ALERT -> "rgba(255,255,0,0.7);";
            case SUCESSO -> "rgba(0,255,0,0.7);";
            case ERRO -> "rgba(255,0,0,0.7);";
            default -> "rgba(0,0,0,0.7);";
        };
    }
    //------------------------------------------------------------------------------------------------------------------
}