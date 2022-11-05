package com.etespuniverse.mobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class Atracao extends AppCompatActivity {

    private MaterialToolbar topAppBar;
    private Window window;
    private ProgressDialog load;
    private String apiUrl = SharedData.getApiUrl();
    private TextView lblNome, lblDesc, lblLocation, lblInauguracao, lblStatus;
    private ModelAtracao atracao = new ModelAtracao();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atracao);
        //window = getWindow();
        //window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //window.setStatusBarColor(ContextCompat.getColor(Atracao.this, R.color.transparent));

        Intent it = getIntent();
        atracao = (ModelAtracao) it.getSerializableExtra("atracao");

        topAppBar = findViewById(R.id.topAppBar);
        lblNome = findViewById(R.id.lblNome);
        lblDesc = findViewById(R.id.lblDesc);
        lblLocation = findViewById(R.id.lblLocation);
        lblInauguracao = findViewById(R.id.lblDataInauguracao);
        lblStatus = findViewById(R.id.lblStatus);

        //Toast.makeText(Atracao.this, atracao.getNome(), Toast.LENGTH_SHORT).show();

        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        lblNome.setText(atracao.getNome());
        lblDesc.setText(atracao.getDescricao());
        lblLocation.setText(atracao.getLocalização());
        lblInauguracao.setText(atracao.getDataInauguracao());
        int status = atracao.getStatus();
        if (status == 1) {
            lblStatus.setText("Aberta");
        } else if (status == 0) {
            lblStatus.setText("Fechada");
        } else {
            lblStatus.setText("Desconhecido");
        }

        //AtracoesTask task = new AtracoesTask();
        //task.execute();

    }

    private class AtracoesTask extends AsyncTask<String, Void, ArrayList<ModelAtracao>> {

        @Override
        protected void onPreExecute() {
            load = ProgressDialog.show(Atracao.this,
                    "Por favor Aguarde ...", "Recuperando Informações do Servidor...");
        }

        @Override
        protected ArrayList<ModelAtracao> doInBackground(String... strings) {
            ArrayList<ModelAtracao> atracoes = new ArrayList<ModelAtracao>();
            Utils util = new Utils();
            atracoes = util.getAtracoes(apiUrl);
            lblNome.setText(atracoes.get(4).getNome());
            return atracoes;
        }

        @Override
        protected void onPostExecute(ArrayList<ModelAtracao> atracao) {
            super.onPostExecute(atracao);
            int num = atracao.size();
            //Toast.makeText(Atracao.this, ""+num, Toast.LENGTH_SHORT).show();
            load.dismiss();
        }

    }

}