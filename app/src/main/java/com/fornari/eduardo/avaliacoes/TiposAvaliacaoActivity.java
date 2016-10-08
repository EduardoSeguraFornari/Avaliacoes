package com.fornari.eduardo.avaliacoes;

import android.content.Intent;
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
import android.widget.ListView;

import com.fornari.eduardo.avaliacoes.dao.TipoAvaliacaoDAO;
import com.fornari.eduardo.avaliacoes.model.TipoAvaliacao;

import java.util.Comparator;
import java.util.List;

public class TiposAvaliacaoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listViewTiposAvaliacao;
    private ArrayAdapter<TipoAvaliacao> arrayAdapterTiposAvaliacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipos_avaliacao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listViewTiposAvaliacao = (ListView) findViewById(R.id.listViewTiposAvaliacao);
        preencheAdapterTiposAvaliacao(carregaTiposAvaliacao());
        sortArrayAdapterTiposAvaliacao(arrayAdapterTiposAvaliacao);
        listViewTiposAvaliacao.setAdapter(arrayAdapterTiposAvaliacao);

        listViewTiposAvaliacao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                TipoAvaliacao tipoAvaliacao = arrayAdapterTiposAvaliacao.getItem(position);
                Intent intent = new Intent(TiposAvaliacaoActivity.this, TipoAvaliacaoActivity.class);
                intent.putExtra("TIPO_AVALIACAO_ID", tipoAvaliacao.getId());
                startActivityForResult(intent, 1);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TiposAvaliacaoActivity.this, TipoAvaliacaoActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.tipos_avaliacao, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;
        if (id == R.id.nav_avaliacoes) {
            intent = new Intent(TiposAvaliacaoActivity.this, AvaliacoesActivity.class);
            finish();
            startActivityForResult(intent, 0);
        } else if (id == R.id.nav_disciplinas) {
            intent = new Intent(TiposAvaliacaoActivity.this, DisciplinasActivity.class);
            finish();
            startActivityForResult(intent, 0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void sortArrayAdapterTiposAvaliacao(ArrayAdapter<TipoAvaliacao> arrayAdapterDisciplinas) {
        Comparator<TipoAvaliacao> porNome = new Comparator<TipoAvaliacao>() {
            @Override
            public int compare(TipoAvaliacao o1, TipoAvaliacao o2) {
                return o1.getNome().compareTo(o2.getNome());
            }
        };
        arrayAdapterDisciplinas.sort(porNome);
    }

    public void preencheAdapterTiposAvaliacao(List<TipoAvaliacao> tiposAvaliacao) {
        int layoutAdapter = android.R.layout.simple_list_item_1;
        ArrayAdapter<TipoAvaliacao> adapter = new ArrayAdapter<TipoAvaliacao>(TiposAvaliacaoActivity.this, layoutAdapter);
        for (int i = 0; i < tiposAvaliacao.size(); i++) {
            adapter.add(tiposAvaliacao.get(i));
        }
        arrayAdapterTiposAvaliacao = adapter;
    }

    public List<TipoAvaliacao> carregaTiposAvaliacao() {
        TipoAvaliacaoDAO disciplinaDAO = new TipoAvaliacaoDAO(this);
        List<TipoAvaliacao> tiposAvaliacao = disciplinaDAO.buscaTiposAvaliacao();
        return tiposAvaliacao;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if(resultCode == RESULT_OK){
                preencheAdapterTiposAvaliacao(carregaTiposAvaliacao());
                sortArrayAdapterTiposAvaliacao(arrayAdapterTiposAvaliacao);
                listViewTiposAvaliacao.setAdapter(arrayAdapterTiposAvaliacao);
            }
            if (resultCode == RESULT_CANCELED) {
                //Do nothing?
            }
        }
    }
}