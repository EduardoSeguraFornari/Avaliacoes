package com.fornari.eduardo.avaliacoes;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.fornari.eduardo.avaliacoes.bo.TipoAvaliacaoBO;
import com.fornari.eduardo.avaliacoes.model.TipoAvaliacao;

public class TipoAvaliacaoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText editTextNome;
    private Switch switchNotificar;
    private LinearLayout linearLayoutAntecedenciaNotificacao;
    private Spinner spinnerAntecedenciaNotificacao;
    private ArrayAdapter<Integer> arrayAdapterAntecedenciaNotificacao;
    private EditText editTextDescricao;

    private TipoAvaliacao tipoAvaliacao;

    Menu myMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_avaliacao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        editTextNome = (EditText) findViewById(R.id.editTextNome);

        switchNotificar = (Switch) findViewById(R.id.switchNotificar);

        linearLayoutAntecedenciaNotificacao = (LinearLayout) findViewById(R.id.linearLayoutAntecedenciaNotificacao);
        linearLayoutAntecedenciaNotificacao.setVisibility(View.GONE);

        spinnerAntecedenciaNotificacao = (Spinner) findViewById(R.id.spinnerAntecedenciaNotificacao);
        preencheArrayAdapterAntecedenciaNotificacao();
        spinnerAntecedenciaNotificacao.setAdapter(arrayAdapterAntecedenciaNotificacao);

        editTextDescricao = (EditText) findViewById(R.id.editTextDescricao);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("TIPO_AVALIACAO_ID")) {
            int tipo_avaliacao_id = (int) bundle.getSerializable("TIPO_AVALIACAO_ID");

            TipoAvaliacaoBO tipoAvaliacaoBO = new TipoAvaliacaoBO(this);

            tipoAvaliacao = tipoAvaliacaoBO.buscaTipoAvaliacaoId(tipo_avaliacao_id);

            setTipoAvaliacao();
        }

        editTextNome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validaOpcaoSalvar();
            }
        });

        switchNotificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchNotificar.isChecked() == true) {
                    linearLayoutAntecedenciaNotificacao.setVisibility(View.VISIBLE);
                } else {
                    linearLayoutAntecedenciaNotificacao.setVisibility(View.GONE);
                }
                validaOpcaoSalvar();
            }
        });

        spinnerAntecedenciaNotificacao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                     @Override
                                                                     public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                                         validaOpcaoSalvar();
                                                                     }

                                                                     @Override
                                                                     public void onNothingSelected(AdapterView<?> parent) {
                                                                     }
                                                                 }
        );

        editTextDescricao.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validaOpcaoSalvar();
            }
        });

    }

    private void setTipoAvaliacao() {
        editTextNome.setText(tipoAvaliacao.getNome());
        switchNotificar.setChecked(tipoAvaliacao.isNotificar());
        if (tipoAvaliacao.isNotificar()) {
            linearLayoutAntecedenciaNotificacao.setVisibility(View.VISIBLE);
        } else {
            linearLayoutAntecedenciaNotificacao.setVisibility(View.GONE);
        }
        spinnerAntecedenciaNotificacao.setSelection(tipoAvaliacao.getAntecedenciaNotificacao() - 1);
        editTextDescricao.setText(tipoAvaliacao.getDescricao());
        editTextNome.setText(tipoAvaliacao.getNome());
        switchNotificar.setChecked(tipoAvaliacao.isNotificar());
        if (tipoAvaliacao.isNotificar()) {
            linearLayoutAntecedenciaNotificacao.setVisibility(View.VISIBLE);
        } else {
            linearLayoutAntecedenciaNotificacao.setVisibility(View.GONE);
        }
        spinnerAntecedenciaNotificacao.setSelection(tipoAvaliacao.getAntecedenciaNotificacao() - 1);
        editTextDescricao.setText(tipoAvaliacao.getDescricao());
    }

    public void preencheArrayAdapterAntecedenciaNotificacao() {
        int layoutAdapter = android.R.layout.simple_list_item_1;
        arrayAdapterAntecedenciaNotificacao = new ArrayAdapter<Integer>(TipoAvaliacaoActivity.this, layoutAdapter);
        for (int i = 1; i <= 50; i++) {
            arrayAdapterAntecedenciaNotificacao.add(i);
        }
    }

    public void validaOpcaoSalvar() {
        String nomeTipoAvaliacao = editTextNome.getText().toString();
        if (nomeTipoAvaliacao.equals("")) {
            myMenu.findItem(R.id.action_salvar_tipo_avaliacao).setVisible(false);
        } else {
            if (tipoAvaliacao != null) {
                String dercricaoTipoAvaliacao = editTextDescricao.getText().toString();
                boolean notificarTipoAvaliacao = switchNotificar.isChecked();
                int antecedenciaNotificacao = arrayAdapterAntecedenciaNotificacao.getItem(spinnerAntecedenciaNotificacao.getSelectedItemPosition());
                if (!nomeTipoAvaliacao.equals(tipoAvaliacao.getNome()) || notificarTipoAvaliacao != tipoAvaliacao.isNotificar() || (notificarTipoAvaliacao && tipoAvaliacao.getAntecedenciaNotificacao() != antecedenciaNotificacao) || !dercricaoTipoAvaliacao.equals(tipoAvaliacao.getDescricao())) {
                    myMenu.findItem(R.id.action_salvar_tipo_avaliacao).setVisible(true);
                } else {
                    myMenu.findItem(R.id.action_salvar_tipo_avaliacao).setVisible(false);
                }
            } else {
                myMenu.findItem(R.id.action_salvar_tipo_avaliacao).setVisible(true);
            }
        }
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
        getMenuInflater().inflate(R.menu.tipo_avaliacao, menu);
        if (tipoAvaliacao != null) {
            menu.findItem(R.id.action_deletar_tipo_avaliacao).setVisible(true);
        }
        myMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_salvar_tipo_avaliacao) {
            String nome = editTextNome.getText().toString();
            TipoAvaliacaoBO tipoAvaliacaoBO = new TipoAvaliacaoBO(this);
            if (tipoAvaliacao != null && !nome.equals(tipoAvaliacao.getNome()) && tipoAvaliacaoBO.buscaTipoAvaliacaoPorNome(nome) != null) {
                Toast.makeText(TipoAvaliacaoActivity.this, "Já existe um tipo de avaliação com este nome!", Toast.LENGTH_LONG).show();
            } else {
                boolean notificarTipoAvaliacao = switchNotificar.isChecked();
                int antecedenciaNotificacaoTipoAvaliacao = (int) spinnerAntecedenciaNotificacao.getSelectedItem();
                String descricaoTipoAvaliacao = editTextDescricao.getText().toString();

                TipoAvaliacao tipoAvaliacaoAUX = new TipoAvaliacao(nome, notificarTipoAvaliacao, antecedenciaNotificacaoTipoAvaliacao, descricaoTipoAvaliacao);

                if (tipoAvaliacao == null) {
                    tipoAvaliacaoBO.inserir(tipoAvaliacaoAUX);
                } else {
                    tipoAvaliacaoBO.atualizaTipoAvaliacao(tipoAvaliacao.getId(), tipoAvaliacaoAUX);
                }
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }

            return true;
        }
        if (id == R.id.action_deletar_tipo_avaliacao) {
            final Dialog dialog = new Dialog(TipoAvaliacaoActivity.this);
            dialog.setContentView(R.layout.deletar);

            dialog.setTitle("DELETAR TIPO AVALIAÇÃO");

            TextView textViewDeletar = (TextView) dialog.findViewById(R.id.textViewDeletarDialog);
            textViewDeletar.setText("Deletar este tipo de avaliação?");

            ImageButton cancelar = (ImageButton) dialog.findViewById(R.id.imageButtonCancelDeletarDialog);
            cancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.cancel();
                }
            });

            ImageButton adicionar = (ImageButton) dialog.findViewById(R.id.imageButtonDoneDeletarDialog);
            adicionar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    TipoAvaliacaoBO tipoAvaliacaoBO = new TipoAvaliacaoBO(TipoAvaliacaoActivity.this);
                    tipoAvaliacaoBO.deletarTipoAvaliacaoId(tipoAvaliacao.getId());

                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
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
            intent = new Intent(TipoAvaliacaoActivity.this, AvaliacoesActivity.class);
            finish();
            startActivityForResult(intent, 0);
        } else if (id == R.id.nav_disciplinas) {
            intent = new Intent(TipoAvaliacaoActivity.this, DisciplinasActivity.class);
            finish();
            startActivityForResult(intent, 0);
        } else if (id == R.id.nav_tipos_avaliacao) {
            intent = new Intent(TipoAvaliacaoActivity.this, TiposAvaliacaoActivity.class);
            finish();
            startActivityForResult(intent, 0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
