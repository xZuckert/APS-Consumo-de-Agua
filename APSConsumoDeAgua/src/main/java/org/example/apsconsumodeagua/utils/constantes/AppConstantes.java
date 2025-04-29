package org.example.apsconsumodeagua.utils.constantes;

import java.util.HashMap;
import java.util.Map;

public class AppConstantes {
    private AppConstantes(){}
    public static final Integer VELOCIDADE_ANIMACOES = 300;
    public static final Integer ANOS_ANTERIORES = 20;
    public static final Double CONSUMO_IDEAL_POR_PESSOA = 0.11;
    public static final String[] MESES = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
    public static final Map<String, Integer> MES_INDEX = new HashMap<>() {{
        put("Jan", 0);
        put("Fev", 1);
        put("Mar", 2);
        put("Abr", 3);
        put("Mai", 4);
        put("Jun", 5);
        put("Jul", 6);
        put("Ago", 7);
        put("Set", 8);
        put("Out", 9);
        put("Nov", 10);
        put("Dez", 11);
    }};
}
