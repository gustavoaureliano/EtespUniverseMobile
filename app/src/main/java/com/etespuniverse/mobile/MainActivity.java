package com.etespuniverse.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private CarouselView carouselView;
    private int[] sampleImages = {R.drawable.image_1, R.drawable.image_2, R.drawable.image_3, R.drawable.image_4, R.drawable.image_5};
    private Cliente cliente = new Cliente();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carouselView = findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(sampleImages[position]);
            }
        });

        LoginTask task = new LoginTask();
        task.execute();

    }

    private class LoginTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            //load = ProgressDialog.show(Login.this,
            //"Por favor Aguarde ...", "Recuperando Informações do Servidor...");
            //progressIndicator.show();
            Log.d("TAG", "Logando...");
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            //Cliente cliente = new Cliente();

            DBHandlerLogin dbHandlerLogin = new DBHandlerLogin(MainActivity.this);
            cliente = dbHandlerLogin.getCliente();
            Utils util = new Utils();
            int id = cliente.getId();
            boolean login = false;
            if (id > 0) {
                //int id = util.getIdCliente(apiUrl, email);
                //cliente.setId(id);
                //cliente = util.getCliente(apiUrl, id);
                //Bitmap foto = util.getImage(apiUrl, cliente);
                //cliente.setFoto(foto);
                Log.d("TAG", "Cliente (DB): " + cliente.toString());
                try {
                    Cliente clienteAPI = util.getCliente(id);
                    Log.d("TAG", "Cliente (API): " + clienteAPI.toString());
                    if (clienteAPI.getId() > 0) {
                        cliente = clienteAPI;
                        if (clienteAPI.getFoto() != null) {
                            Log.d("TAG", "Foto (Login): " + clienteAPI.getFoto().toString());
                        }
                    } else  {
                        Log.d("TAG", "No image (Login)");
                    }
                } catch (Exception e) {
                    Log.d("TAG", "login error: " + e.getMessage());
                }
                //Bitmap image = util.getImage(apiUrl, cliente);
                login = true;
                Log.d("TAG", "Clinte id (LogPage): " + cliente.getId());
            }
            return login;
        }

        @Override
        protected void onPostExecute(Boolean login) {
            super.onPostExecute(login);

            if (login) {
                Log.d("TAG", "Clinte got (LoginPage): " + cliente.toString());
                SharedData sharedData = new SharedData(MainActivity.this);
                sharedData.setLogado(true);
                SharedData.setCliente(cliente);
                DBHandlerLogin dbHandlerLogin = new DBHandlerLogin(MainActivity.this);
                dbHandlerLogin.setCliente(cliente);

                //SharedPreferences sharedPreferences = getSharedPreferences(
                //        getString(R.string.preference_file_key), MODE_PRIVATE);
                //SharedPreferences.Editor editor = sharedPreferences.edit();
                //editor.putInt("idCliente", cliente.getId());
                //editor.apply();

                Toast.makeText(MainActivity.this, "Login efetuado com sucesso", Toast.LENGTH_SHORT).show();
                Intent it = new Intent(MainActivity.this, FragmentsPage.class);
                startActivity(it);
                finish();
            } else {
                if (cliente.getId() > 0) {
                    SharedData sharedData = new SharedData(MainActivity.this);
                    sharedData.setLogado(true);
                    SharedData.setCliente(cliente);
                    Intent it = new Intent(MainActivity.this, FragmentsPage.class);
                    startActivity(it);
                    finish();
                } else {
                    SharedData sharedData = new SharedData(MainActivity.this);
                    sharedData.setLogado(false);
                    SharedData.setCliente(new Cliente());
                    DBHandlerLogin dbHandlerLogin = new DBHandlerLogin(MainActivity.this);
                    dbHandlerLogin.clearClientes();
                    Intent it = new Intent(MainActivity.this, FragmentsPage.class);
                    startActivity(it);
                    finish();
                }
            }
        }
    }

}