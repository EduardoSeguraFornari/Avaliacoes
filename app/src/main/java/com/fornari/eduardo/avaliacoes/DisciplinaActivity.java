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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fornari.eduardo.avaliacoes.dao.AvaliacaoDAO;
import com.fornari.eduardo.avaliacoes.dao.DisciplinaDAO;
import com.fornari.eduardo.avaliacoes.database.DataBase;
import com.fornari.eduardo.avaliacoes.model.Avaliacao;
import com.fornari.eduardo.avaliacoes.model.Disciplina;

import java.util.Comparator;
import java.util.List;

public class DisciplinaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView textViewNomeDisciplina;
    private ListView listViewAvaliacoesDisciplina;
    private ArrayAdapter<Avaliacao> arrayAdapterAvaliacoesDisciplina;
    private Disciplina disciplina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplina);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("DISCIPLINA_ID")) {
            int disciplinaId = (int) bundle.getSerializable("DISCIPLINA_ID");
            DisciplinaDAO disciplinaDAO = new DisciplinaDAO(this);
            disciplina = disciplinaDAO.buscaDisciplinaId(disciplinaId);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(DisciplinaActivity.this, AvaliacaoActivity.class);
                it.putExtra("DISCIPLINA_ID", disciplina.getId());
                startActivityForResult(it, 1);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        textViewNomeDisciplina = (TextView) findViewById(R.id.textViewNomeDisciplina);
        textViewNomeDisciplina.setText(disciplina.getNome());

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
                intent.putExtra("DISCIPLINA_ID", disciplina.getId());
                startActivityForResult(intent, 1);
            }
        });
    }

    private void mostraDialogRenomearDisciplina() {
        final Dialog dialog = new Dialog(DisciplinaActivity.this);
        dialog.setContentView(R.layout.nome_disciplina);

        dialog.setTitle("ADICIONAR DISCIPLINA");

        TextView textViewNomeDisciplinaDialog = (TextView) dialog.findViewById(R.id.textViewNomeDisciplinaDialog);
        textViewNomeDisciplinaDialog.setText("Informe o novo nome da disciplina");

        ImageButton imageButtonCancelNomeDisciplinaDialog = (ImageButton) dialog.findViewById(R.id.imageButtonCancelNomeDisciplinaDialog);
        imageButtonCancelNomeDisciplinaDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        ImageButton imageButtonDoneNomeDisciplinaDialog = (ImageButton) dialog.findViewById(R.id.imageButtonDoneNomeDisciplinaDialog);
        imageButtonDoneNomeDisciplinaDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editTextNomeDiciplinaDialog = (EditText) dialog.findViewById(R.id.editTextNomeDiciplinaDialog);
                String nomeDiciplina = editTextNomeDiciplinaDialog.getText().toString();
                if (nomeDiciplina.trim().isEmpty()) {
                    Toast.makeText(DisciplinaActivity.this, "O nome da disciplina não pode ficar em branco!", Toast.LENGTH_LONG).show();
                } else if (nomeDiciplina.equals(disciplina.getNome())) {
                    dialog.cancel();
                } else {
                    DataBase dataBase = new DataBase(DisciplinaActivity.this);
                    SQLiteDatabase connection = dataBase.getReadableDatabase();
                    DisciplinaDAO disciplinaDAO = new DisciplinaDAO(DisciplinaActivity.this);
                    if (disciplinaDAO.buscaDisciplinaPorNome(nomeDiciplina) == null) {
                        Disciplina disciplinaAUX = new Disciplina(disciplina.getId(), nomeDiciplina);
                        disciplinaDAO.atualizaDisciplina(disciplinaAUX);

                        disciplina.setNome(nomeDiciplina);
                        textViewNomeDisciplina.setText(nomeDiciplina);

                        dialog.cancel();
                    } else {
                        Toast.makeText(DisciplinaActivity.this, "Está disciplina ja existe!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        dialog.show();
    }

    private void mostraDialogDeletarDisciplina() {
        final Dialog dialog = new Dialog(DisciplinaActivity.this);
        dialog.setContentView(R.layout.deletar);

        dialog.setTitle("DELETAR DISCIPLINA");

        TextView textViewDeletarDialog = (TextView) dialog.findViewById(R.id.textViewDeletarDialog);
        textViewDeletarDialog.setText("Deletar esta disciplina?");

        ImageButton imageButtonCancelDeletarDialog = (ImageButton) dialog.findViewById(R.id.imageButtonCancelDeletarDialog);
        imageButtonCancelDeletarDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        ImageButton imageButtonDoneDeletarDialog = (ImageButton) dialog.findViewById(R.id.imageButtonDoneDeletarDialog);
        imageButtonDoneDeletarDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO(DisciplinaActivity.this);
                avaliacaoDAO.deletarAvaliacoesDisciplina(disciplina.getId());

                DisciplinaDAO disciplinaDAO = new DisciplinaDAO(DisciplinaActivity.this);
                disciplinaDAO.deletarDisciplinaId(disciplina.getId());

                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        dialog.show();
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
        List<Avaliacao> avaliacoes = avaliacaoDAO.buscaAvaliacoesDisciplina(disciplina.getId());
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
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
            mostraDialogDeletarDisciplina();
            return true;
        } else if (id == R.id.action_renomear_disciplina) {
            mostraDialogRenomearDisciplina();
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
            intent = new Intent(DisciplinaActivity.this, TiposAvaliacaoActivity.class);
            startActivityForResult(intent, 0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {
                preencheArrayAdapterAvaliacoesDisciplinas(carregaAvaliacoesDisciplina());
                sortArrayAdapterAvaliacoesDisciplina(arrayAdapterAvaliacoesDisciplina);
                listViewAvaliacoesDisciplina.setAdapter(arrayAdapterAvaliacoesDisciplina);
            }
            if (resultCode == RESULT_CANCELED) {
                //Do nothing?
            }
        }
    }
}
