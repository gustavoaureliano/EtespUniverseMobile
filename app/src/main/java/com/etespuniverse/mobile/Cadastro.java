package com.etespuniverse.mobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class Cadastro extends AppCompatActivity {

    private TextInputEditText txtNome, txtCPF, txtDataNasc, txtEmail, txtSenha;
    private Button btnCadastrar;
    private TextView btnLogin;
    private ProgressDialog load;
    private String apiUrl = SharedData.getApiUrl();
    private Cliente cliente = SharedData.getCliente();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        txtNome = findViewById(R.id.txtNome);
        txtCPF = findViewById(R.id.txtCPF);
        txtDataNasc = findViewById(R.id.txtDataNasc);
        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);
        btnLogin = findViewById(R.id.btnLogin);

        btnCadastrar = findViewById(R.id.btnCadastro);

        txtDataNasc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = "";
                String sobrenome = "";
                String nomeCompleto = txtNome.getText().toString();
                String[] nomeSplit = nomeCompleto.split(" ");
                nome = nomeSplit[0];
                sobrenome = nomeCompleto.substring(nome.length()+1);
                String cpf = txtCPF.getText().toString();
                String dataNascimento = txtDataNasc.getText().toString();
                String email = txtEmail.getText().toString();
                String senha = txtSenha.getText().toString();
                CadastroTask task = new CadastroTask();
                task.execute(nome, sobrenome, cpf, dataNascimento, email, senha);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Cadastro.this, Login.class);
                startActivity(it);
                finish();
            }
        });

    }

    private class CadastroTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //load = ProgressDialog.show(Cadastro.this,
              //      "Por favor Aguarde ...", "Recuperando Informações do Servidor...");
        }

        @Override
        protected Integer doInBackground(String... strings) {
            String[] tests = strings.clone();
            Utils util = new Utils();
            String nome = tests[0];
            String sobrenome = tests[1];
            String cpf = tests[2];
            String dataNascimento = tests[3];
            String email = tests[4];
            String senha = tests[5];
            cliente.setNome(nome);
            cliente.setSobrenome(sobrenome);
            cliente.setCpf(cpf);
            cliente.setDataNascimentoDB(dataNascimento);
            cliente.setEmail(email);
            cliente.setSenha(senha);
            int idCliente = util.cadastro(cliente);

            if(idCliente > 0) {
                //int id = 2;
                //cliente.setId(idCliente);
                cliente = util.getCliente(idCliente);
            }
            return idCliente;
        }

        @Override
        protected void onPostExecute(Integer idCliente) {
            super.onPostExecute(idCliente);
            if (idCliente > 0) {
                Toast.makeText(Cadastro.this, "Cadastro efetuado com sucesso", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "Clinte got (CadastroPage): " + cliente.toString());
                SharedData sharedData = new SharedData(Cadastro.this);
                sharedData.setLogado(true);
                SharedData.setCliente(cliente);
                DBHandlerLogin dbHandlerLogin = new DBHandlerLogin(Cadastro.this);
                dbHandlerLogin.setCliente(cliente);
                finish();
            } else {
                Snackbar.make(btnCadastrar, "Falha ao cadastrar", Snackbar.LENGTH_SHORT);
            }
            //load.dismiss();
        }

    }

}