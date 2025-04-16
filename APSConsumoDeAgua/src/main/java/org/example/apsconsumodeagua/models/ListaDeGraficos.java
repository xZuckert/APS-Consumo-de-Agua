package org.example.apsconsumodeagua.models;

import java.util.HashMap;
import java.util.Map;

public class ListaDeGraficos {
    private Map<String, Grafico> graficos = new HashMap<>();
    public void adicionarDado(String ano, String mes, int valor) {
        Grafico g;
        if(!graficos.containsKey(ano)){
            g = new Grafico(ano);
            graficos.put(ano, g);
        }else{
            g = getGrafico(ano);
        }
        g.atualizarValorMes(mes, valor);
    }
    public Grafico getGrafico(String ano) {
        return graficos.get(ano);
    }

    public boolean existeAno(String ano) {
        return graficos.containsKey(ano);
    }

    public void listarAnos() {
        for (String ano : graficos.keySet()) {
            System.out.println("Ano: " + ano);
        }
    }

    public int getQuantidade() {
        return graficos.size();
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Anos disponíveis:\n");
        for (String ano : graficos.keySet()) {
            sb.append("- ").append(ano).append("\n");
        }
        return sb.toString();
    }
}

