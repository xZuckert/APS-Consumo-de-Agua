package org.example.apsconsumodeagua.controllers;

import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import org.example.apsconsumodeagua.utils.Constantes;
import org.example.apsconsumodeagua.utils.UIUtils;

public class TabController {
    private final ToggleButton tabUsuario, tabHome, tabGraficos;
    private final AnchorPane contentTabUsuario, contentTabHome, contentTabGraficos;
    String antigoId;

    public TabController(ToggleButton tabUsuario, ToggleButton tabHome, ToggleButton tabGraficos,
                         AnchorPane contentTabUsuario, AnchorPane contentTabHome, AnchorPane contentTabGraficos) {
        antigoId = "tabHome";
        this.tabUsuario = tabUsuario;
        this.tabHome = tabHome;
        this.tabGraficos = tabGraficos;
        this.contentTabUsuario = contentTabUsuario;
        this.contentTabHome = contentTabHome;
        this.contentTabGraficos = contentTabGraficos;
    }

    public void alternarAba(ToggleButton toggleButton) {
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
        contentTabUsuario.setVisible("tabUsuario".equals(id));
        contentTabHome.setVisible("tabHome".equals(id));
        contentTabGraficos.setVisible("tabGraficos".equals(id));
    }
    private void animarAlteracao(){
        if(antigoId.equals("tabHome")){
            if(tabUsuario.isSelected()){
                UIUtils.mostrarDeslizando(contentTabUsuario, Constantes.VELOCIDADE_ANIMACOES, UIUtils.direcao.DA_ESQUERDA_PRA_DIREITA);
            }
            if(tabGraficos.isSelected()){
                UIUtils.mostrarDeslizando(contentTabGraficos,Constantes.VELOCIDADE_ANIMACOES, UIUtils.direcao.DA_DIREITA_PRA_ESQUERDA);
            }
        }
        if(antigoId.equals("tabUsuario")){
            if(tabHome.isSelected()){
                UIUtils.mostrarDeslizando(contentTabHome,Constantes.VELOCIDADE_ANIMACOES, UIUtils.direcao.DE_BAIXO_PRA_CIMA);
            }
            if(tabGraficos.isSelected()){
                UIUtils.mostrarDeslizando(contentTabGraficos,Constantes.VELOCIDADE_ANIMACOES, UIUtils.direcao.DA_DIREITA_PRA_ESQUERDA);
            }
        }
        if(antigoId.equals("tabGraficos")){
            if(tabHome.isSelected()){
                UIUtils.mostrarDeslizando(contentTabHome,Constantes.VELOCIDADE_ANIMACOES, UIUtils.direcao.DE_BAIXO_PRA_CIMA);
            }
            if(tabUsuario.isSelected()){
                UIUtils.mostrarDeslizando(contentTabUsuario,Constantes.VELOCIDADE_ANIMACOES, UIUtils.direcao.DA_ESQUERDA_PRA_DIREITA);
            }
        }
    }
}
