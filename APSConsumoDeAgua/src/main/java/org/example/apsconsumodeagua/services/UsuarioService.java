package org.example.apsconsumodeagua.services;

import org.example.apsconsumodeagua.models.usuario.UsuarioModel;
import org.example.apsconsumodeagua.utils.enums.TipoGrafico;

//Classe para tornar o usuario acessivel globalmente na aplicação-------------------------------------------------------
public class UsuarioService {
    private static UsuarioService usuarioService;
    private UsuarioModel usuarioLogado;
    private TipoGrafico tipoGrafico;
    private UsuarioService() {}
    public static UsuarioService getInstance() {
        if (usuarioService == null) {
            usuarioService = new UsuarioService();
        }
        return usuarioService;
    }
    //Funções que retornam se o usuario esta logado e o tipo do grafico-------------------------------------------------
    public UsuarioModel getUsuarioLogado() {
        return usuarioLogado;
    }
    public TipoGrafico getTipoGrafico() {
        return this.tipoGrafico;
    }
    public void setUsuarioLogado(UsuarioModel usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }
    public void setTipoGrafico(TipoGrafico tipoGrafico) {
        this.tipoGrafico = tipoGrafico;
    }
    //------------------------------------------------------------------------------------------------------------------
}