package org.example.apsconsumodeagua.managers;

import javafx.scene.control.ToggleButton;
import org.example.apsconsumodeagua.core.AppModel;
import org.example.apsconsumodeagua.utils.constantes.CaminhoFxml;
import org.example.apsconsumodeagua.utils.constantes.AppConstantes;
import org.example.apsconsumodeagua.utils.UIUtils;
import org.example.apsconsumodeagua.utils.constantes.TabId;

public class TabManager {
    private final AppModel appModel = AppModel.getInstance();
    private final ToggleButton tabUsuario, tabHome, tabGraficos;
    private String antigoId;

    public TabManager(ToggleButton tabUsuario, ToggleButton tabHome, ToggleButton tabGraficos) {
        antigoId = TabId.TAB_HOME;
        this.tabUsuario = tabUsuario;
        this.tabHome = tabHome;
        this.tabGraficos = tabGraficos;
    }

    public void inicializarComTabInicial(String id) {
        appModel.getRootPane().getChildren().addFirst(appModel.getTela(id));
        String tabId = getTabIdPeloCaminhoFxml(id);
        alterarBotaoSelecionado(tabId);
    }

    public void alternarAba(ToggleButton toggleButton) {
        String id = toggleButton.getId();
        alterarBotaoSelecionado(id);
        alterarConteudoExibido(id);
        animarAlteracao();
        antigoId = id;
    }

    private void alterarBotaoSelecionado(String id) {
        tabUsuario.setSelected(TabId.TAB_USUARIO.equals(id));
        tabHome.setSelected(TabId.TAB_HOME.equals(id));
        tabGraficos.setSelected(TabId.TAB_GRAFICOS.equals(id));
    }

    private void alterarConteudoExibido(String id) {
        switch (id) {
            case TabId.TAB_USUARIO:
                tabUsuario.setSelected(true);
                tabGraficos.setSelected(false);
                tabHome.setSelected(false);
                appModel.trocarTela(CaminhoFxml.TAB_USUARIO);
                break;
            case TabId.TAB_HOME:
                tabUsuario.setSelected(false);
                tabHome.setSelected(true);
                tabGraficos.setSelected(false);
                appModel.trocarTela(CaminhoFxml.TAB_HOME);
                break;
            case TabId.TAB_GRAFICOS:
                tabUsuario.setSelected(false);
                tabHome.setSelected(false);
                tabGraficos.setSelected(true);
                appModel.trocarTela(CaminhoFxml.TAB_GRAFICOS);
                break;
        }
    }

    private void animarAlteracao() {
        if (antigoId.equals(TabId.TAB_HOME)) {
            if (tabUsuario.isSelected()) {
                UIUtils.mostrarDeslizando(appModel.getTabUsuarioController().contentTabUsuario, AppConstantes.VELOCIDADE_ANIMACOES, UIUtils.direcao.DA_ESQUERDA_PRA_DIREITA);
            }
            if (tabGraficos.isSelected()) {
                UIUtils.mostrarDeslizando(appModel.getTabGraficosController().contentTabGraficos, AppConstantes.VELOCIDADE_ANIMACOES, UIUtils.direcao.DA_DIREITA_PRA_ESQUERDA);
            }
        }
        if (antigoId.equals(TabId.TAB_USUARIO)) {
            if (tabHome.isSelected()) {
                UIUtils.mostrarDeslizando(appModel.getTabHomeController().contentTabHome, AppConstantes.VELOCIDADE_ANIMACOES, UIUtils.direcao.DE_BAIXO_PRA_CIMA);
            }
            if (tabGraficos.isSelected()) {
                UIUtils.mostrarDeslizando(appModel.getTabGraficosController().contentTabGraficos, AppConstantes.VELOCIDADE_ANIMACOES, UIUtils.direcao.DA_DIREITA_PRA_ESQUERDA);
            }
        }
        if (antigoId.equals(TabId.TAB_GRAFICOS)) {
            if (tabHome.isSelected()) {
                UIUtils.mostrarDeslizando(appModel.getTabHomeController().contentTabHome, AppConstantes.VELOCIDADE_ANIMACOES, UIUtils.direcao.DE_BAIXO_PRA_CIMA);
            }
            if (tabUsuario.isSelected()) {
                UIUtils.mostrarDeslizando(appModel.getTabUsuarioController().contentTabUsuario, AppConstantes.VELOCIDADE_ANIMACOES, UIUtils.direcao.DA_ESQUERDA_PRA_DIREITA);
            }
        }
    }

    private String getTabIdPeloCaminhoFxml(String caminhoFxml) {
        return switch (caminhoFxml) {
            case CaminhoFxml.TAB_USUARIO -> TabId.TAB_USUARIO;
            case CaminhoFxml.TAB_HOME -> TabId.TAB_HOME;
            case CaminhoFxml.TAB_GRAFICOS -> TabId.TAB_GRAFICOS;
            default -> null;
        };
    }
}
