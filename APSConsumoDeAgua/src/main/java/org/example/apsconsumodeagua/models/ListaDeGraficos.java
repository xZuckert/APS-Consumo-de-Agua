package org.example.apsconsumodeagua.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ListaDeGraficos {
    private Map<String, Grafico> graficos = new HashMap<>();
    private Set<String> keys = graficos.keySet();
    public void gerarGrafico(String ano){
        graficos.put(ano, new Grafico(ano));
    }
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Anos disponíveis:\n");
        for (String ano : graficos.keySet()) {
            sb.append("- ").append(ano).append("\n");
        }
        return sb.toString();
    }

    public Set<String> getKeys() {
        return keys;
    }
}

