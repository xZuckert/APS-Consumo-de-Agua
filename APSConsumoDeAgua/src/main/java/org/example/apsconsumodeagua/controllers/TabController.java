package org.example.apsconsumodeagua.controllers;

import javafx.scene.control.ToggleButton;
import org.example.apsconsumodeagua.models.AppModel;
import org.example.apsconsumodeagua.utils.CaminhoFxml;
import org.example.apsconsumodeagua.utils.Constantes;
import org.example.apsconsumodeagua.utils.UIUtils;

public class TabController {
    private final AppModel appModel = AppModel.getInstance();
    private final ToggleButton tabUsuario, tabHome, tabGraficos;
    String antigoId;

    public TabController(ToggleButton tabUsuario, ToggleButton tabHome, ToggleButton tabGraficos) {
        antigoId = "tabHome";
        this.tabUsuario = tabUsuario;
        this.tabHome = tabHome;
        this.tabGraficos = tabGraficos;
    }

    protected void alternarAba(ToggleButton toggleButton) {
        String id = toggleButton.getId();
        alterarBotaoSelecionado(id);
        alterarConteudoExibido(id);
        animarAlteracao();
        antigoId = id;
    }

    private void alterarBotaoSelecionado(String id){
        tabUsuario.setSelected("tabUsuario".equals(id));
        tabHome.setSelected("tabHome".equals(id));
        tabGraficos.setSelected("tabGraficos".equals(id));
    }
    private void alterarConteudoExibido(String id){
        switch (id) {
            case "tabUsuario":
                tabUsuario.setSelected(true);
                tabGraficos.setSelected(false);
                tabHome.setSelected(false);
                appModel.getRootPane().getChildren().set(0,appModel.getPane(CaminhoFxml.TAB_USUARIO));
                break;
            case "tabHome":
                tabUsuario.setSelected(false);
                tabHome.setSelected(true);
                tabGraficos.setSelected(false);
                appModel.getRootPane().getChildren().set(0,appModel.getPane(CaminhoFxml.TAB_HOME));
                break;
            case "tabGraficos":
                tabUsuario.setSelected(false);
                tabHome.setSelected(false);
                tabGraficos.setSelected(true);
                appModel.getRootPane().getChildren().set(0,appModel.getPane(CaminhoFxml.TAB_GRAFICOS));
                break;
        }
    }
    private void animarAlteracao(){
        if(antigoId.equals("tabHome")){
            if(tabUsuario.isSelected()){
                UIUtils.mostrarDeslizando(appModel.getTabUsuarioController().contentTabUsuario, Constantes.VELOCIDADE_ANIMACOES, UIUtils.direcao.DA_ESQUERDA_PRA_DIREITA);
            }
            if(tabGraficos.isSelected()){
                UIUtils.mostrarDeslizando(appModel.getTabGraficosController().contentTabGraficos,Constantes.VELOCIDADE_ANIMACOES, UIUtils.direcao.DA_DIREITA_PRA_ESQUERDA);
            }
        }
        if(antigoId.equals("tabUsuario")){
            if(tabHome.isSelected()){
                UIUtils.mostrarDeslizando(appModel.getTabHomeController().contentTabHome,Constantes.VELOCIDADE_ANIMACOES, UIUtils.direcao.DE_BAIXO_PRA_CIMA);
            }
            if(tabGraficos.isSelected()){
                UIUtils.mostrarDeslizando(appModel.getTabGraficosController().contentTabGraficos, Constantes.VELOCIDADE_ANIMACOES, UIUtils.direcao.DA_DIREITA_PRA_ESQUERDA);
            }
        }
        if(antigoId.equals("tabGraficos")){
            if(tabHome.isSelected()){
                UIUtils.mostrarDeslizando(appModel.getTabHomeController().contentTabHome, Constantes.VELOCIDADE_ANIMACOES, UIUtils.direcao.DE_BAIXO_PRA_CIMA);
            }
            if(tabUsuario.isSelected()){
                UIUtils.mostrarDeslizando(appModel.getTabUsuarioController().contentTabUsuario, Constantes.VELOCIDADE_ANIMACOES, UIUtils.direcao.DA_ESQUERDA_PRA_DIREITA);
            }
        }
    }
}
