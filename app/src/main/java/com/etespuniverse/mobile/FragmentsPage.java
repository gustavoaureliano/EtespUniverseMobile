package com.etespuniverse.mobile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class FragmentsPage extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    public static Cliente cliente = SharedData.getCliente();
    final String nome = "Ciclano";

    private String apiUrl = SharedData.getApiUrl();
    private ProgressDialog load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments_page);

        //getSupportFragmentManager();

        //ClienteTask task = new ClienteTask();
        //task.execute(cliente.getId());
        //Log.d("TAG", "Clinte got (FragPage): " + cliente.toString());
//
        bottomNavigation = findViewById(R.id.bottom_navigation);
//
        //bottomNavigation.getMenu().getItem(1).setChecked(true);
//
        openFragment(Home.newInstance());
//
        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_atacoes:
                        openFragment(new Atracoes());
                        return true;
                    case R.id.page_ingressos:
                        openFragment(Ingressos.newInstance());
                        return true;
                    case R.id.page_home:
                        openFragment(Home.newInstance());
                        return true;
                    case R.id.page_options:
                        OptionsBottomSheet optionsBottomSheet = OptionsBottomSheet.newInstance();
                        optionsBottomSheet.show(getSupportFragmentManager(), "tag");
                        return true;
                }
                return false;
            }
        });

    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        //transaction.addToBackStack(null);
        transaction.commit();
    }

    private class ClienteTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected void onPreExecute() {
            //load = ProgressDialog.show(FragmentsPage.this,
                    //"Por favor Aguarde ...", "Recuperando Informações do Servidor...");
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            //Cliente cliente = new Cliente();
            Integer[] ids = integers.clone();
            Utils util = new Utils();
            int id = ids[0];
            if(id > 0) {
                cliente = util.getCliente(id);
                Log.d("TAG", "Clinte got (FragPaged): " + cliente.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            //load.dismiss();
        }
    }

}