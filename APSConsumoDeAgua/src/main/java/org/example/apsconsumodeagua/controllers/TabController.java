package org.example.apsconsumodeagua.controllers;

import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;

public class TabController {
    private final ToggleButton tabUsuario, tabHome, tabGraficos;
    private final AnchorPane contentTabUsuario, contentTabHome, contentTabGraficos;

    public TabController(ToggleButton tabUsuario, ToggleButton tabHome, ToggleButton tabGraficos,
                         AnchorPane contentTabUsuario, AnchorPane contentTabHome, AnchorPane contentTabGraficos) {
        this.tabUsuario = tabUsuario;
        this.tabHome = tabHome;
        this.tabGraficos = tabGraficos;
        this.contentTabUsuario = contentTabUsuario;
        this.contentTabHome = contentTabHome;
        this.contentTabGraficos = contentTabGraficos;
    }

    public void alternarAba(ToggleButton toggleButton) {
        String id = toggleButton.getId();

        tabUsuario.setSelected("tabUsuario".equals(id));
        contentTabUsuario.setVisible("tabUsuario".equals(id));

        tabHome.setSelected("tabHome".equals(id));
        contentTabHome.setVisible("tabHome".equals(id));

        tabGraficos.setSelected("tabGraficos".equals(id));
        contentTabGraficos.setVisible("tabGraficos".equals(id));
    }
}
