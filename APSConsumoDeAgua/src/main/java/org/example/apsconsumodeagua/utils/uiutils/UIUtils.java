package org.example.apsconsumodeagua.utils.uiutils;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class UIUtils {
    public enum direcao {DE_BAIXO_PRA_CIMA, DE_CIMA_PRA_BAIXO, DA_DIREITA_PRA_ESQUERDA, DA_ESQUERDA_PRA_DIREITA}

    public static void mostrarDeslizando(Pane pane, double duracao, direcao direcao) {
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(duracao), pane);
        switch (direcao) {
            case DE_BAIXO_PRA_CIMA:
                pane.setTranslateY(pane.getHeight());
                slideIn.setFromY(pane.getHeight());
                slideIn.setToY(0);
                break;
            case DE_CIMA_PRA_BAIXO:
                pane.setTranslateY(-pane.getHeight());
                slideIn.setFromY(-pane.getHeight());
                slideIn.setToY(0);
                break;
            case DA_DIREITA_PRA_ESQUERDA:
                pane.setTranslateX(pane.getWidth());
                slideIn.setFromX(pane.getWidth());
                slideIn.setToX(0);
                break;
            case DA_ESQUERDA_PRA_DIREITA:
                pane.setTranslateX(-pane.getWidth());
                slideIn.setFromX(-pane.getWidth());
                slideIn.setToX(0);
                break;
        }

        pane.setVisible(true);

        ParallelTransition animation = new ParallelTransition(slideIn);
        animation.play();
    }
}
