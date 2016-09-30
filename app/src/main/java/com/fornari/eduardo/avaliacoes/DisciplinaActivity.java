package com.fornari.eduardo.avaliacoes;

import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.fornari.eduardo.avaliacoes.dao.AvaliacaoDAO;
import com.fornari.eduardo.avaliacoes.dao.DisciplinaDAO;
import com.fornari.eduardo.avaliacoes.database.DataBase;
import com.fornari.eduardo.avaliacoes.model.Avaliacao;

import java.util.Comparator;
import java.util.List;

public class DisciplinaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private int disciplinaId = -1;

    private ListView listViewAvaliacoesDisciplina;
    private ArrayAdapter<Avaliacao> arrayAdapterAvaliacoesDisciplina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplina);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("DISCIPLINA_ID")) {
            disciplinaId = (int) bundle.getSerializable("DISCIPLINA_ID");
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(DisciplinaActivity.this, AvaliacaoActivity.class);
                it.putExtra("DISCIPLINA_ID", disciplinaId);
                startActivityForResult(it, 0);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listViewAvaliacoesDisciplina = (ListView) findViewById(R.id.listViewAvaliacoesDisciplina);
        preencheArrayAdapterAvaliacoesDisciplinas(carregaAvaliacoesDisciplina());
        sortArrayAdapterAvaliacoesDisciplina(arrayAdapterAvaliacoesDisciplina);
        listViewAvaliacoesDisciplina.setAdapter(arrayAdapterAvaliacoesDisciplina);

        listViewAvaliacoesDisciplina.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Avaliacao avaliacao = arrayAdapterAvaliacoesDisciplina.getItem(position);
                Intent intent = new Intent(DisciplinaActivity.this, AvaliacaoActivity.class);
                intent.putExtra("AVALIACAO_ID", avaliacao.getId());
                intent.putExtra("DISCIPLINA_ID", disciplinaId);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.disciplina, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_deletar_disciplina) {
            final Dialog dialog = new Dialog(DisciplinaActivity.this);
            dialog.setContentView(R.layout.deletar_disciplina);

            dialog.setTitle("DELETAR AVALIAÇÃO");

            ImageButton cancelar = (ImageButton) dialog.findViewById(R.id.imageButton_deletarDisciplina);
            cancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.cancel();
                }
            });

            ImageButton adicionar = (ImageButton) dialog.findViewById(R.id.imageButton_deletarDisciplina);
            adicionar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DataBase dataBase = new DataBase(DisciplinaActivity.this);
                    SQLiteDatabase connection = dataBase.getReadableDatabase();

                    AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO(DisciplinaActivity.this);
                    avaliacaoDAO.deletarAvaliacoesDisciplina(disciplinaId);

                    DisciplinaDAO disciplinaDAO = new DisciplinaDAO(DisciplinaActivity.this);
                    disciplinaDAO.deletarDisciplinaId(disciplinaId);

                    Intent intent = new Intent(DisciplinaActivity.this, DisciplinasActivity.class);
                    startActivityForResult(intent, 0);
                }
            });
            dialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Intent intent;

        if (id == R.id.nav_avaliacoes) {
            intent = new Intent(DisciplinaActivity.this, AvaliacoesActivity.class);
            startActivityForResult(intent, 0);
        } else if (id == R.id.nav_disciplinas) {
            intent = new Intent(DisciplinaActivity.this, DisciplinasActivity.class);
            startActivityForResult(intent, 0);
        } else if (id == R.id.nav_tipos_avaliacao) {
            intent = new Intent(DisciplinaActivity.this, TiposDeAvaliacaoActivity.class);
            startActivityForResult(intent, 0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void preencheArrayAdapterAvaliacoesDisciplinas(List<Avaliacao> avaliacoes) {
        int layoutAdapter = android.R.layout.simple_list_item_1;
        arrayAdapterAvaliacoesDisciplina = new ArrayAdapter<Avaliacao>(DisciplinaActivity.this, layoutAdapter);
        for (int i = 0; i < avaliacoes.size(); i++) {
            arrayAdapterAvaliacoesDisciplina.add(avaliacoes.get(i));
        }
    }

    public List<Avaliacao> carregaAvaliacoesDisciplina() {
        AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO(this);
        List<Avaliacao> avaliacoes = avaliacaoDAO.buscaAvaliacoesDisciplina(disciplinaId);
        return avaliacoes;
    }

    private void sortArrayAdapterAvaliacoesDisciplina(ArrayAdapter<Avaliacao> arrayAdapterAvaliacoesDisciplina) {
        Comparator<Avaliacao> porNome = new Comparator<Avaliacao>() {
            @Override
            public int compare(Avaliacao avaliacao1, Avaliacao avaliacao2) {
                return avaliacao1.getData().compareTo(avaliacao2.getData());
            }
        };
        arrayAdapterAvaliacoesDisciplina.sort(porNome);
    }
}
