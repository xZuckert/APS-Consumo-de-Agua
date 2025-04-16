package org.example.apsconsumodeagua.utils;

import java.time.LocalDate;

public class Validadores {
    public static int pegarMes(LocalDate date) {
        return date.getMonthValue();
    }

    public static int pegarConsumo(String consumo) {
        return Integer.parseInt(consumo);
    }

}

