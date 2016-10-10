package com.fornari.eduardo.avaliacoes.bo;

import android.content.Context;

import com.fornari.eduardo.avaliacoes.AvaliacaoActivity;
import com.fornari.eduardo.avaliacoes.dao.AvaliacaoDAO;
import com.fornari.eduardo.avaliacoes.model.Avaliacao;

import java.util.List;

/**
 * Created by dufor on 10/10/2016.
 */
public class AvaliacaoBO {
    private Context context;

    public AvaliacaoBO(Context context) {
        this.context = context;
    }

    public void atualizaAvaliacao(int id, Avaliacao avaliacao) {
        AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO(context);
        avaliacaoDAO.atualizaAvaliacao(id,avaliacao);
    }

    public void inserir(Avaliacao avaliacao) {
        AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO(context);
        avaliacaoDAO.inserir(avaliacao);
    }

    public void deletarAvaliacaoId(int id) {
        AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO(context);
        avaliacaoDAO.deletarAvaliacaoId(id);
    }

    public Avaliacao buscaAvaliacaoID(int id) {
        AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO(context);
        return avaliacaoDAO.buscaAvaliacaoID(id);
    }

    public List<Avaliacao> buscaAvaliacoes() {
        AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO(context);
        return avaliacaoDAO.buscaAvaliacoes();
    }

    public List<Avaliacao> buscaAvaliacoesDisciplina(int disciplinaId) {
        AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO(context);
        return avaliacaoDAO.buscaAvaliacoesDisciplina(disciplinaId);
    }
}
