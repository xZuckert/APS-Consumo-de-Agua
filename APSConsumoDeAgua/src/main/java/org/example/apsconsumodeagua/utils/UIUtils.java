package org.example.apsconsumodeagua.utils;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class UIUtils {
    public static void mostrarDeslizandoDeBaixoParaCima(Pane pane, double duracao) {
        pane.setTranslateY(pane.getHeight());     // começa deslocado pra baixo
        pane.setOpacity(0);
        pane.setVisible(true);

        TranslateTransition slideIn = new TranslateTransition(Duration.millis(duracao), pane);
        slideIn.setFromY(pane.getHeight());
        slideIn.setToY(0);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(duracao), pane);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        ParallelTransition animation = new ParallelTransition(slideIn, fadeIn);
        animation.play();
    }
}
