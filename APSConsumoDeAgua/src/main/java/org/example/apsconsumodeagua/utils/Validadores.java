package org.example.apsconsumodeagua.utils;

import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.apsconsumodeagua.utils.enums.ToastEnum;

public class Validadores {
    public static boolean tabExiste(String ano, TabPane tabPane) {
        for (Tab tab : tabPane.getTabs()) {
            if (tab.getText().equals(ano)) {
                return true;
            }
        }
        return false;
    }
    public static boolean osCamposEstaoPreenchidos(AnchorPane anchorPane, TextField ... fields) {
        for (TextField textField : fields) {
            if (textField.getText() == null || textField.getText().trim().isEmpty()) {
                Toast.mostrarToast(anchorPane,"Preencha todos os campos!", ToastEnum.ERRO,ToastEnum.TOPO_ESQUERDA);
                return false;
            }
        }
        return true;
    }
    public static boolean osCamposSaoIguais(AnchorPane anchorPane, TextField ... fields) {
        if (fields.length < 2) {
            return true;
        }
        String primeiroValor = fields[0].getText().trim();
        for (TextField textField : fields) {
            if (!textField.getText().trim().equals(primeiroValor)) {
                if(textField instanceof PasswordField){
                    Toast.mostrarToast(anchorPane, "As senhas não são iguais!", ToastEnum.ERRO,ToastEnum.TOPO_ESQUERDA);
                    return false;
                }else {
                    Toast.mostrarToast(anchorPane, "Os campos não são iguais!", ToastEnum.ERRO,ToastEnum.TOPO_ESQUERDA);
                    return false;
                }
            }
        }
        return true;
    }
}

