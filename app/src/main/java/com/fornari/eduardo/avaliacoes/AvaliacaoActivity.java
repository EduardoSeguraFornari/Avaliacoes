package com.fornari.eduardo.avaliacoes;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fornari.eduardo.avaliacoes.dao.AvaliacaoDAO;
import com.fornari.eduardo.avaliacoes.dao.TipoAvaliacaoDAO;
import com.fornari.eduardo.avaliacoes.model.Avaliacao;
import com.fornari.eduardo.avaliacoes.model.TipoAvaliacao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class AvaliacaoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText editTextObservacao;
    private TextView textViewDataAvaliacao;
    private Spinner spinnerTiposAvaliacao;
    private ArrayAdapter<TipoAvaliacao> arrayAdapterTiposAvaliacao;

    private int disciplinaId;
    private Avaliacao avaliacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinnerTiposAvaliacao = (Spinner) findViewById(R.id.spinnerTiposAvaliacao);
        editTextObservacao = (EditText) findViewById(R.id.editTextObservacao);
        textViewDataAvaliacao = (TextView) findViewById(R.id.textViewDataAvaliacao);

        //Busca e mostra na tela os tipos de avaliações cadastrados no banco de dados em ordem alfabetica
        preencheAdapterTiposAvaliacao(carregaTiposAvaliacao());
        arrayAdapterTiposAvaliacao.add(new TipoAvaliacao("Selecionar"));
        sortArrayAdapterTiposAvaliacao(arrayAdapterTiposAvaliacao);
        spinnerTiposAvaliacao.setAdapter(arrayAdapterTiposAvaliacao);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("DISCIPLINA_ID")) {
                disciplinaId = (int) bundle.getSerializable("DISCIPLINA_ID");
            }

            if (bundle.containsKey("AVALIACAO_ID")) {
                int avaliacaoId = (int) bundle.getSerializable("AVALIACAO_ID");
                AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO(this);
                avaliacao = avaliacaoDAO.buscaAvaliacaoID(avaliacaoId);
                setAvaliacao(avaliacao);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ExibeDataListener exibeDataListener = new ExibeDataListener();
        textViewDataAvaliacao.setOnClickListener(exibeDataListener);
        textViewDataAvaliacao.setOnFocusChangeListener(exibeDataListener);
    }

    private void setAvaliacao(Avaliacao avaliacao) {
        for (int i = 0; i < arrayAdapterTiposAvaliacao.getCount(); i++) {
            TipoAvaliacao tipoAvaliacao = arrayAdapterTiposAvaliacao.getItem(i);
            if (tipoAvaliacao.getId() == avaliacao.getTipoAvaliacao().getId()) {
                spinnerTiposAvaliacao.setSelection(i);
                break;
            }
        }

        Date dataAvaliacao = avaliacao.getData();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        String format = dateFormat.format(dataAvaliacao);
        textViewDataAvaliacao.setText(format);

        editTextObservacao.setText(avaliacao.getObservacao());
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
        getMenuInflater().inflate(R.menu.avaliacao, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_salvar_avaliacao) {
            String tipoSelecionado = ((TipoAvaliacao) spinnerTiposAvaliacao.getSelectedItem()).getNome();
            if (tipoSelecionado.equalsIgnoreCase("Selecionar")) {
                Toast.makeText(AvaliacaoActivity.this, "Selecione um tipo de avaliação!", Toast.LENGTH_LONG).show();
            } else if (textViewDataAvaliacao.getText().toString().equalsIgnoreCase("__ /__ /__")) {
                Toast.makeText(AvaliacaoActivity.this, "Selecione uma data!", Toast.LENGTH_LONG).show();
            } else {
                Date data = new Date();
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
                try {
                    data = df.parse(textViewDataAvaliacao.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int tipoAvaliacaoId = arrayAdapterTiposAvaliacao.getItem(spinnerTiposAvaliacao.getSelectedItemPosition()).getId();

                String observacao = editTextObservacao.getText().toString();

                AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO(this);

                if (avaliacao != null) {
                    Avaliacao avaliacaoAUX = new Avaliacao(avaliacao.getId(), tipoAvaliacaoId, data, observacao, disciplinaId);
                    avaliacaoDAO.atualizaAvaliacao(avaliacaoAUX);
                } else {
                    Avaliacao avaliacaoAUX = new Avaliacao(tipoAvaliacaoId, data, observacao, disciplinaId);
                    avaliacaoDAO.inserir(avaliacaoAUX);
                }

                Intent intent = new Intent(AvaliacaoActivity.this, DisciplinaActivity.class);
                intent.putExtra("DISCIPLINA_ID", disciplinaId);
                finish();
                startActivityForResult(intent, 0);
            }
            return true;
        } else if (id == R.id.action_deletar_avaliacao) {
            final Dialog dialog = new Dialog(AvaliacaoActivity.this);
            dialog.setContentView(R.layout.deletar);

            dialog.setTitle("DELETAR AVALIAÇÃO");

            TextView textViewDeletar = (TextView) dialog.findViewById(R.id.textViewDeletar);
            textViewDeletar.setText("Deletar esta avaliação?");

            ImageButton cancelar = (ImageButton) dialog.findViewById(R.id.imageButton_cancelar_deletar);
            cancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.cancel();
                }
            });

            ImageButton adicionar = (ImageButton) dialog.findViewById(R.id.imageButton_confirmar_deletar);
            adicionar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO(AvaliacaoActivity.this);
                    avaliacaoDAO.deletarAvaliacaoId(avaliacao.getId());

                    Intent intent = new Intent(AvaliacaoActivity.this, DisciplinaActivity.class);
                    intent.putExtra("DISCIPLINA_ID", disciplinaId);
                    finish();
                    startActivityForResult(intent, 0);
                }
            });
            dialog.show();
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
            intent = new Intent(AvaliacaoActivity.this, AvaliacoesActivity.class);
            startActivityForResult(intent, 0);
        } else if (id == R.id.nav_disciplinas) {
            intent = new Intent(AvaliacaoActivity.this, DisciplinasActivity.class);
            startActivityForResult(intent, 0);
        } else if (id == R.id.nav_tipos_avaliacao) {
            intent = new Intent(AvaliacaoActivity.this, TiposAvaliacaoActivity.class);
            startActivityForResult(intent, 0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class SelecionaDataListener implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            Date date = calendar.getTime();
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
            String format = dateFormat.format(date);
            if (date.compareTo(new Date()) >= 0) {
                textViewDataAvaliacao.setText(format);
            } else textViewDataAvaliacao.setText("__ /__ /__");
        }
    }

    public class ExibeDataListener implements View.OnClickListener, View.OnFocusChangeListener {
        @Override
        public void onClick(View v) {
            exibeData();
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) exibeData();
        }
    }

    private void exibeData() {
        int dia, mes, ano;
        Calendar calendar = Calendar.getInstance();
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        mes = calendar.get(Calendar.MONTH);
        ano = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new SelecionaDataListener(), ano, mes, dia);
        datePickerDialog.show();
    }

    private void sortArrayAdapterTiposAvaliacao(ArrayAdapter<TipoAvaliacao> arrayAdapterTiposAvaliacao) {
        Comparator<TipoAvaliacao> porNome = new Comparator<TipoAvaliacao>() {
            @Override
            public int compare(TipoAvaliacao avaliacao1, TipoAvaliacao avaliacao2) {
                if (avaliacao1.getNome().equalsIgnoreCase("Selecionar")) return -1;
                if (avaliacao2.getNome().equalsIgnoreCase("Selecionar")) return 1;
                return avaliacao1.getNome().compareTo(avaliacao2.getNome());
            }
        };
        arrayAdapterTiposAvaliacao.sort(porNome);
    }

    public void preencheAdapterTiposAvaliacao(List<TipoAvaliacao> tiposAvaliacao) {
        int layoutAdapter = android.R.layout.simple_list_item_1;
        arrayAdapterTiposAvaliacao = new ArrayAdapter<TipoAvaliacao>(AvaliacaoActivity.this, layoutAdapter);
        for (TipoAvaliacao tipoAvaliacao : tiposAvaliacao) {
            arrayAdapterTiposAvaliacao.add(tipoAvaliacao);
        }
    }

    public List<TipoAvaliacao> carregaTiposAvaliacao() {
        TipoAvaliacaoDAO disciplinaDAO = new TipoAvaliacaoDAO(this);
        List<TipoAvaliacao> tiposAvaliacao = disciplinaDAO.buscaTiposAvaliacao();
        return tiposAvaliacao;
    }
}