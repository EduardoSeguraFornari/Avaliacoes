package com.fornari.eduardo.avaliacoes.bo;

import android.content.Context;

import com.fornari.eduardo.avaliacoes.DisciplinasActivity;
import com.fornari.eduardo.avaliacoes.dao.AvaliacaoDAO;
import com.fornari.eduardo.avaliacoes.dao.DisciplinaDAO;
import com.fornari.eduardo.avaliacoes.model.Avaliacao;
import com.fornari.eduardo.avaliacoes.model.Disciplina;

import java.util.List;

/**
 * Created by dufor on 10/10/2016.
 */
public class DisciplinaBO {
    private Context context;

    public DisciplinaBO(Context context) {
        this.context = context;
    }

    public Disciplina buscaDisciplinaId(int id) {
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO(context);
        return disciplinaDAO.buscaDisciplinaId(id);
    }

    public Disciplina buscaDisciplinaPorNome(String nomeDiciplina) {
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO(context);
        return disciplinaDAO.buscaDisciplinaPorNome(nomeDiciplina);
    }

    public void atualizaDisciplina(int id, Disciplina disciplina) {
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO(context);
        disciplinaDAO.atualizaDisciplina(id, disciplina);
    }

    public void deletarDisciplinaId(int id) {
        AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO(context);
        avaliacaoDAO.deletarAvaliacoesDisciplina(id);

        DisciplinaDAO disciplinaDAO = new DisciplinaDAO(context);
        disciplinaDAO.deletarDisciplinaId(id);
    }

    public int inserir(Disciplina disciplina) {
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO(context);
        return  disciplinaDAO.inserir(disciplina);
    }

    public List<Disciplina> buscaDisciplinas() {
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO(context);
        return disciplinaDAO.buscaDisciplinas();
    }
}
