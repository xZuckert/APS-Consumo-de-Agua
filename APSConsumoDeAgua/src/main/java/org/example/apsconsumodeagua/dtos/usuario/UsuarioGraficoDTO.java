package org.example.apsconsumodeagua.dtos.usuario;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import java.util.function.DoubleToIntFunction;

public class UsuarioGraficoDTO {
    private String ano;
    private static final String[] MESES = {"janeiro", "fevereiro", "marco", "abril", "maio", "junho", "julho", "agosto",
            "setembro", "outubro", "novembro", "dezembro"};

    public static ObservableList<XYChart.Data<String, Number>> gerarDados(double... consumos) {
        ObservableList<XYChart.Data<String, Number>> dados = FXCollections.observableArrayList();
        for (int i = 0; i < MESES.length && i < consumos.length; i++) {
            dados.add(new XYChart.Data<>(MESES[i], consumos[i]));
        }
        return dados;
    }
}