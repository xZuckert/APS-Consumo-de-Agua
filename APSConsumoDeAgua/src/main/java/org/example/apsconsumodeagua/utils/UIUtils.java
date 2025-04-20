package org.example.apsconsumodeagua.utils;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.*;
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

    public static VBox criarNodeCentralizadoVerticalmente(Node node) {
        VBox vbox = new VBox();

        Region espacoTopo = new Region();
        Region espacoBaixo = new Region();

        VBox.setVgrow(espacoTopo, javafx.scene.layout.Priority.ALWAYS);
        VBox.setVgrow(espacoBaixo, javafx.scene.layout.Priority.ALWAYS);
        vbox.getChildren().addAll(espacoTopo, node, espacoBaixo);

        AnchorPane.setTopAnchor(vbox, 10.0);
        AnchorPane.setBottomAnchor(vbox, 10.0);
        AnchorPane.setLeftAnchor(vbox, 10.0);
        AnchorPane.setRightAnchor(vbox, 10.0);
        return vbox;
    }

    private HBox criarNodeCentralizadoHorizontalmente(Node node) {
        HBox hBox = new HBox();

        Region espacoEsquerdo = new Region();
        Region espacoDireito = new Region();

        HBox.setHgrow(espacoEsquerdo, javafx.scene.layout.Priority.ALWAYS);
        HBox.setHgrow(espacoDireito, javafx.scene.layout.Priority.ALWAYS);
        hBox.getChildren().addAll(espacoEsquerdo, node, espacoDireito);

        AnchorPane.setTopAnchor(hBox, 10.0);
        AnchorPane.setBottomAnchor(hBox, 10.0);
        AnchorPane.setLeftAnchor(hBox, 10.0);
        AnchorPane.setRightAnchor(hBox, 10.0);
        return hBox;
    }

    public static StackPane addNodeNoStackPane(Node ... nodes) {
        StackPane stackPaneCriado = new StackPane();
        for (Node node : nodes) {
            stackPaneCriado.getChildren().add(node);
            StackPane.setAlignment(node, javafx.geometry.Pos.CENTER);
            StackPane.setMargin(node, Insets.EMPTY);
        }
        return stackPaneCriado;
    }
}
