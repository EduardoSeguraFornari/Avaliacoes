package com.fornari.eduardo.avaliacoes.bo;

import android.content.Context;

import com.fornari.eduardo.avaliacoes.dao.NotificacaoDAO;
import com.fornari.eduardo.avaliacoes.model.Notificacao;

/**
 * Created by dufor on 11/10/2016.
 */
public class NotificacaoBO {
    private Context context;

    public NotificacaoBO(Context context) {
        this.context = context;
    }

    public Notificacao buscaNotificacao() {
        NotificacaoDAO notificacaoDAO = new NotificacaoDAO(context);
        Notificacao notificacao = notificacaoDAO.buscaNotificacao();

        if (notificacao == null) {
            notificacaoDAO.inserirNotificacaoDefault();
            notificacao = notificacaoDAO.buscaNotificacao();
        }

        return notificacao;
    }

    public void atualizaNotificacao(Notificacao notificacao) {
        NotificacaoDAO notificacaoDAO = new NotificacaoDAO(context);
        notificacaoDAO.atualizaNotificacao(notificacao);
    }
}