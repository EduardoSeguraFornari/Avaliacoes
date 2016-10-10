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

import com.fornari.eduardo.avaliacoes.dao.DisciplinaDAO;
import com.fornari.eduardo.avaliacoes.database.DataBase;
import com.fornari.eduardo.avaliacoes.model.Disciplina;

import java.util.Comparator;
import java.util.List;

public class DisciplinasActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listViewDisciplinas;
    private ArrayAdapter<Disciplina> arrayAdapterDisciplinas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplinas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listViewDisciplinas = (ListView) findViewById(R.id.listViewDisciplinas);
        preencheAdapterDisciplinas(carregaDisciplinas());
        sortArrayAdapterDisciplinas(arrayAdapterDisciplinas);
        listViewDisciplinas.setAdapter(arrayAdapterDisciplinas);

        listViewDisciplinas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Disciplina disciplina = arrayAdapterDisciplinas.getItem(position);
                Intent intent = new Intent(DisciplinasActivity.this, DisciplinaActivity.class);
                intent.putExtra("DISCIPLINA_ID", disciplina.getId());
                startActivityForResult(intent, 1);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostraDialogAdicionarDisciplina();
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

    private void mostraDialogAdicionarDisciplina() {
        final Dialog dialog = new Dialog(DisciplinasActivity.this);
        dialog.setContentView(R.layout.nome_disciplina);

        dialog.setTitle("ADICIONAR DISCIPLINA");

        TextView textViewNomeDisciplinaDialog = (TextView) dialog.findViewById(R.id.textViewNomeDisciplinaDialog);
        textViewNomeDisciplinaDialog.setText("Informe o nome da disciplina");

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
                EditText editTextNomeDisciplina = (EditText) dialog.findViewById(R.id.editTextNomeDiciplinaDialog);
                String nomeDiciplina = editTextNomeDisciplina.getText().toString();
                if (nomeDiciplina.trim().isEmpty()) {
                    Toast.makeText(DisciplinasActivity.this, "O nome da disciplina não pode ficar em branco!", Toast.LENGTH_LONG).show();
                } else {
                    DataBase dataBase = new DataBase(DisciplinasActivity.this);
                    SQLiteDatabase connection = dataBase.getReadableDatabase();
                    DisciplinaDAO disciplinaDAO = new DisciplinaDAO(DisciplinasActivity.this);
                    if (disciplinaDAO.buscaDisciplinaPorNome(nomeDiciplina) == null) {
                        Disciplina disciplina = new Disciplina(nomeDiciplina);
                        int novaDisciplinaId = disciplinaDAO.inserir(disciplina);
                        disciplina.setId(novaDisciplinaId);
                        arrayAdapterDisciplinas.add(disciplina);

                        sortArrayAdapterDisciplinas(arrayAdapterDisciplinas);

                        listViewDisciplinas.setAdapter(arrayAdapterDisciplinas);
                        dialog.cancel();
                    } else {
                        Toast.makeText(DisciplinasActivity.this, "Está disciplina ja existe!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        dialog.show();
    }

    private void sortArrayAdapterDisciplinas(ArrayAdapter<Disciplina> arrayAdapterDisciplinas) {
        Comparator<Disciplina> porNome = new Comparator<Disciplina>() {
            @Override
            public int compare(Disciplina o1, Disciplina o2) {
                return o1.getNome().compareTo(o2.getNome());
            }
        };
        arrayAdapterDisciplinas.sort(porNome);
    }

    public List<Disciplina> carregaDisciplinas() {
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO(this);
        List<Disciplina> disciplinas = disciplinaDAO.buscaDisciplinas(this);
        return disciplinas;
    }

    public void preencheAdapterDisciplinas(List<Disciplina> disciplinas) {
        int layoutAdapter = android.R.layout.simple_list_item_1;
        arrayAdapterDisciplinas = new ArrayAdapter<Disciplina>(DisciplinasActivity.this, layoutAdapter);
        for (int i = 0; i < disciplinas.size(); i++) {
            arrayAdapterDisciplinas.add(disciplinas.get(i));
        }
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.disciplinas, menu);
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
            intent = new Intent(DisciplinasActivity.this, AvaliacoesActivity.class);
            finish();
            startActivityForResult(intent, 0);
        } else if (id == R.id.nav_tipos_avaliacao) {
            intent = new Intent(DisciplinasActivity.this, TiposAvaliacaoActivity.class);
            finish();
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
                preencheAdapterDisciplinas(carregaDisciplinas());
                sortArrayAdapterDisciplinas(arrayAdapterDisciplinas);
                listViewDisciplinas.setAdapter(arrayAdapterDisciplinas);
            }
            if (resultCode == RESULT_CANCELED) {
                //Do nothing?
            }
        }
    }
}
