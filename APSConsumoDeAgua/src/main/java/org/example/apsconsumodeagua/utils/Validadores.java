package org.example.apsconsumodeagua.utils;

import java.time.LocalDate;

public class Validadores {
    public static String pegarMes(LocalDate date) {
        int mes = date.getMonthValue();
        return switch (mes) {
            case 1 -> "Jan";
            case 2 -> "Fev";
            case 3 -> "Mar";
            case 4 -> "Abr";
            case 5 -> "Mai";
            case 6 -> "Jun";
            case 7 -> "Jul";
            case 8 -> "Ago";
            case 9 -> "Set";
            case 10 -> "Out";
            case 11 -> "Nov";
            case 12 -> "Dez";
            default -> "";
        };
    }
    public static String pegarAno(LocalDate date) {
        return String.valueOf(date.getYear());
    }

    public static int pegarConsumo(String consumo) {
        return Integer.parseInt(consumo);
    }

}

