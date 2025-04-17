package org.example.apsconsumodeagua.utils;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class Validadores {
    public static boolean tabExiste(String ano, TabPane tabPane) {
        for (Tab tab : tabPane.getTabs()) {
            if (tab.getText().equals(ano)) {
                return true;
            }
        }
        return false;
    }
}

