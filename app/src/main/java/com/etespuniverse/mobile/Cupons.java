package com.etespuniverse.mobile;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class Cupons extends AppCompatActivity {

    private MaterialToolbar topAppBar;
    private TextView lblCupons;
    private Cliente cliente = SharedData.getCliente();
    private String apiUrl = SharedData.getApiUrl();
    private RecyclerView rvCupons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cupons);

        //Intent it = getIntent();
        //cliente = it.getParcelableExtra("cliente");

        rvCupons =findViewById(R.id.rvCupons);
        topAppBar = findViewById(R.id.topAppBar);

        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        CuponsTask task = new CuponsTask();
        task.execute();

    }

    private class CuponsTask extends AsyncTask<Void, Void, ArrayList<Cupom>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(Perfil.this, "Got here", Toast.LENGTH_SHORT).show();
            //Toast.makeText(Perfil.this, cliente.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        protected ArrayList<Cupom> doInBackground(Void... voids) {
            Utils util = new Utils();
            ArrayList<Cupom> cupons = util.getCuponsCliente(apiUrl, cliente);
            Log.d("TAG", "count cupons: " + cupons.size());
            return cupons;
        }

        @Override
        protected void onPostExecute(ArrayList<Cupom> cupons) {
            super.onPostExecute(cupons);
            //Log.d("TAG", "atualizar: " + atualizar);
            //Toast.makeText(Perfil.this, "Not here", Toast.LENGTH_SHORT).show();
            //String txtCupons = "";
            //for (Cupom cupom: cupons) {
            //    txtCupons +=
            //        "ID: " + cupom.getIdCupom() + "; " +
            //        "Nome: " + cupom.getNome() + "; " +
            //        "DataValidade: " + cupom.getDataValidade() + "\n";
            //}
            //lblCupons.setText(txtCupons);
            CuponsAdapter adapter = new CuponsAdapter(Cupons.this, cupons);
            rvCupons.setAdapter(adapter);
            rvCupons.setLayoutManager(new LinearLayoutManager(Cupons.this, LinearLayoutManager.VERTICAL, false));

        }

    }

}