package com.fornari.eduardo.avaliacoes;

import android.app.TimePickerDialog;
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
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.fornari.eduardo.avaliacoes.bo.NotificacaoBO;
import com.fornari.eduardo.avaliacoes.model.Notificacao;

import java.util.Calendar;
import java.util.Date;

public class ConfiguracoesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Switch switchAtivarNotificacoes;
    private Switch switchSom;
    private Switch switchVibracao;
    private TextView textViewHorarioNotificacoes;
    private LinearLayout lynearLayoutNoficacao;

    Notificacao notificacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        switchAtivarNotificacoes = (Switch) findViewById(R.id.switchAtivarNotificacoes);
        switchSom = (Switch) findViewById(R.id.switchSom);
        switchVibracao = (Switch) findViewById(R.id.switchVibracao);
        textViewHorarioNotificacoes = (TextView) findViewById(R.id.textViewHorarioNotificacoes);
        lynearLayoutNoficacao = (LinearLayout) findViewById(R.id.lynearLayoutNoficacao);

        switchAtivarNotificacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchAtivarNotificacoes.isChecked()) {
                    lynearLayoutNoficacao.setVisibility(View.VISIBLE);
                } else {
                    lynearLayoutNoficacao.setVisibility(View.GONE);
                }
            }
        });

        textViewHorarioNotificacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exibeTime();
            }
        });

        carregaNotificacao();

    }

    private void carregaNotificacao() {
        buscaNotificacao();

        if(notificacao.isNotificar()){
            switchAtivarNotificacoes.setChecked(true);
            lynearLayoutNoficacao.setVisibility(View.VISIBLE);
        }

        if(notificacao.isSom()){
            switchSom.setChecked(true);
        }

        if(notificacao.isVibracao()){
            switchVibracao.setChecked(true);
        }

        atualizaHora(notificacao.getHorario());
    }

    private void atualizaHora(Date notificacaoHorario) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(notificacaoHorario);

        int horas = calendar.get(Calendar.HOUR_OF_DAY);
        int minutos = calendar.get(Calendar.MINUTE);

        StringBuilder horario = new StringBuilder();
        horario.append(horas<=9?"0"+horas:horas);
        horario.append(":");
        horario.append(minutos<=9?"0"+minutos:minutos);

        textViewHorarioNotificacoes.setText(horario.toString());
    }

    private void buscaNotificacao() {
        NotificacaoBO notificacaoBO = new NotificacaoBO(this);
        notificacao = notificacaoBO.buscaNotificacao();
    }

    public void exibeTime() {
        final Calendar dateTime = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                dateTime.set(Calendar.MINUTE, minute);
            }
        };
        new TimePickerDialog(this, new SelecionaTimeListener(), dateTime.get(Calendar.HOUR_OF_DAY), dateTime.get(Calendar.MINUTE), true).show();
    }

    private class SelecionaTimeListener implements TimePickerDialog.OnTimeSetListener {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i2) {
            String hora = ((i <= 9) ? "0" + i : i) + ":" + ((i2 <= 9) ? "0" + i2 : i2);
            textViewHorarioNotificacoes.setText(hora);
        }
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.configuracoes, menu);
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
            intent = new Intent(ConfiguracoesActivity.this, AvaliacoesActivity.class);
            finish();
            startActivityForResult(intent, 0);
        } else if (id == R.id.nav_disciplinas) {
            intent = new Intent(ConfiguracoesActivity.this, DisciplinasActivity.class);
            finish();
            startActivityForResult(intent, 0);
        } else if (id == R.id.nav_tipos_avaliacao) {
            intent = new Intent(ConfiguracoesActivity.this, TiposAvaliacaoActivity.class);
            finish();
            startActivityForResult(intent, 0);
        } else if (id == R.id.nav_configuracoes) {
            intent = new Intent(ConfiguracoesActivity.this, ConfiguracoesActivity.class);
            finish();
            startActivityForResult(intent, 0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}