package com.fornari.eduardo.avaliacoes.bo;

import android.content.Context;

import com.fornari.eduardo.avaliacoes.dao.AvaliacaoDAO;
import com.fornari.eduardo.avaliacoes.dao.TipoAvaliacaoDAO;
import com.fornari.eduardo.avaliacoes.model.TipoAvaliacao;

import java.util.List;

/**
 * Created by dufor on 10/10/2016.
 */
public class TipoAvaliacaoBO {
    private Context context;

    public TipoAvaliacaoBO(Context context) {
        this.context = context;
    }

    public List<TipoAvaliacao> buscaTiposAvaliacao() {
        TipoAvaliacaoDAO tipoAvaliacaoDAO = new TipoAvaliacaoDAO(context);
        return tipoAvaliacaoDAO.buscaTiposAvaliacao();
    }

    public TipoAvaliacao buscaTipoAvaliacaoId(int id) {
        TipoAvaliacaoDAO tipoAvaliacaoDAO = new TipoAvaliacaoDAO(context);
        return tipoAvaliacaoDAO.buscaTipoAvaliacaoId(id);
    }

    public TipoAvaliacao buscaTipoAvaliacaoPorNome(String nome) {
        TipoAvaliacaoDAO tipoAvaliacaoDAO = new TipoAvaliacaoDAO(context);
        return tipoAvaliacaoDAO.buscaTipoAvaliacaoPorNome(nome);
    }

    public void inserir(TipoAvaliacao tipoAvaliacao) {
        TipoAvaliacaoDAO tipoAvaliacaoDAO = new TipoAvaliacaoDAO(context);
        tipoAvaliacaoDAO.inserir(tipoAvaliacao);
    }

    public void atualizaTipoAvaliacao(int id, TipoAvaliacao tipoAvaliacao) {
        TipoAvaliacaoDAO tipoAvaliacaoDAO = new TipoAvaliacaoDAO(context);
        tipoAvaliacaoDAO.atualizaTipoAvaliacao(id, tipoAvaliacao);
    }

    public void deletarTipoAvaliacaoId(int id) {
        AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO(context);
        avaliacaoDAO.deletarAvaliacoesTipoAvaliacao(id);

        TipoAvaliacaoDAO tipoAvaliacaoDAO = new TipoAvaliacaoDAO(context);
        tipoAvaliacaoDAO.deletarTipoAvaliacaoId(id);
    }
}
