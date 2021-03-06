package com.fornari.eduardo.avaliacoes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fornari.eduardo.avaliacoes.bo.AvaliacaoBO;
import com.fornari.eduardo.avaliacoes.model.Avaliacao;

import java.util.Comparator;
import java.util.List;

public class AvaliacoesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listViewAvaliacoes;
    private ArrayAdapter<Avaliacao> arrayAdapterAvaliacoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacoes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listViewAvaliacoes = (ListView) findViewById(R.id.listViewAvaliacoes);
        carregaAvaliacoes();

    }

    private void carregaAvaliacoes() {
        preencheArrayAdapterAvaliacoes(buscaAvaliacoes());
        sortArrayAdapterAvaliacoes(arrayAdapterAvaliacoes);
        listViewAvaliacoes.setAdapter(arrayAdapterAvaliacoes);
    }

    public void preencheArrayAdapterAvaliacoes(List<Avaliacao> avaliacoes) {
        int layoutAdapter = android.R.layout.simple_list_item_1;
        arrayAdapterAvaliacoes = new ArrayAdapter<Avaliacao>(AvaliacoesActivity.this, layoutAdapter);
        for (int i = 0; i < avaliacoes.size(); i++) {
            arrayAdapterAvaliacoes.add(avaliacoes.get(i));
        }
    }

    public List<Avaliacao> buscaAvaliacoes() {
        AvaliacaoBO avaliacaoBO = new AvaliacaoBO(this);
        List<Avaliacao> avaliacoes = avaliacaoBO.buscaAvaliacoes();
        return avaliacoes;
    }

    private void sortArrayAdapterAvaliacoes(ArrayAdapter<Avaliacao> arrayAdapterAvaliacoesDisciplina) {
        Comparator<Avaliacao> porNome = new Comparator<Avaliacao>() {
            @Override
            public int compare(Avaliacao avaliacao1, Avaliacao avaliacao2) {
                return avaliacao1.getData().compareTo(avaliacao2.getData());
            }
        };
        arrayAdapterAvaliacoesDisciplina.sort(porNome);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.avaliacoes, menu);
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

        if (id == R.id.nav_disciplinas) {
            intent = new Intent(AvaliacoesActivity.this, DisciplinasActivity.class);
            finish();
            startActivityForResult(intent, 0);
        } else if (id == R.id.nav_tipos_avaliacao) {
            intent = new Intent(AvaliacoesActivity.this, TiposAvaliacaoActivity.class);
            finish();
            startActivityForResult(intent, 0);
        } else if (id == R.id.nav_configuracoes) {
            intent = new Intent(AvaliacoesActivity.this, ConfiguracoesActivity.class);
            finish();
            startActivityForResult(intent, 0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}