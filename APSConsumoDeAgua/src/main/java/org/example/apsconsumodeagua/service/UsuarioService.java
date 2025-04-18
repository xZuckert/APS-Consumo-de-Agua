package org.example.apsconsumodeagua.service;

import org.example.apsconsumodeagua.model.usuario.UsuarioModel;

public class UsuarioService {
    private static UsuarioService usuarioService;
    private UsuarioModel usuarioLogado;
    private UsuarioService() {}
    public static UsuarioService getInstance() {
        if (usuarioService == null) {
            usuarioService = new UsuarioService();
        }
        return usuarioService;
    }

    public UsuarioModel getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(UsuarioModel usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }
}
