package com.etespuniverse.mobile;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {

    private Button btnLogin;
    private TextInputEditText txtEmail, txtSenha;
    private TextView btnCadastro;
    //private MaterialSwitch switchLogin;

    EditText txtApiURL;
    private ProgressDialog load;
    private Cliente cliente = SharedData.getCliente();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);
        //switchLogin = findViewById(R.id.switchLogin);
        btnCadastro = findViewById(R.id.btnCadastro);

        //SharedPreferences sharedPreferences = getSharedPreferences(
        //        getString(R.string.preference_file_key), MODE_PRIVATE);
        //boolean checked = sharedPreferences.getBoolean("login", false);
        //switchLogin.setChecked(checked);

        /*
        switchLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                SharedPreferences sharedPreferences = getSharedPreferences(
                        getString(R.string.preference_file_key), MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("login", checked);
                editor.apply();
                Toast.makeText(Login.this, "Checked: " + checked, Toast.LENGTH_SHORT).show();
            }
        });
        */
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = cliente.getId();
                String email = txtEmail.getText().toString();
                String senha = txtSenha.getText().toString();
                cliente.setEmail(email);
                cliente.setSenha(senha);
                LoginTask login = new LoginTask();
                login.execute();
        }
        });

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Login.this, Cadastro.class);
                startActivity(it);
                finish();
            }
        });

    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
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
            String email = cliente.getEmail();
            String senha = cliente.getSenha();
            Log.d("TAG", "Clinte got (LoginPage): " + cliente.toString());
            Utils util = new Utils();
            int id = util.checkLogin(email, senha);
            boolean login = false;
            if(id > 0) {
                //int id = util.getIdCliente(apiUrl, email);
                //cliente.setId(id);
                //cliente = util.getCliente(apiUrl, id);
                //Bitmap foto = util.getImage(apiUrl, cliente);
                //cliente.setFoto(foto);
                cliente = util.getCliente(id);
                if (cliente.getFoto() != null) {
                    Log.d("TAG", "Foto (Login): " + cliente.getFoto().toString());
                } else  {
                    Log.d("TAG", "No image (Login)");
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
                SharedData sharedData = new SharedData(Login.this);
                sharedData.setLogado(true);
                SharedData.setCliente(cliente);
                DBHandlerLogin dbHandlerLogin = new DBHandlerLogin(Login.this);
                dbHandlerLogin.setCliente(cliente);
                Toast.makeText(Login.this, "Login efetuado com sucesso", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(Login.this, "Dados incorretos", Toast.LENGTH_SHORT).show();
            }
        }
    }

}