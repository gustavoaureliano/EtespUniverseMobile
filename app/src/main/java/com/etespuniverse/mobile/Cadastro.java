package com.etespuniverse.mobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class Cadastro extends AppCompatActivity {

    private TextInputEditText txtNome, txtCPF, txtDataNasc, txtEmail, txtSenha;
    private Button btnCadastrar;
    private ProgressDialog load;
    private String apiUrl = SharedData.getApiUrl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        txtNome = findViewById(R.id.txtNome);
        txtCPF = findViewById(R.id.txtCPF);
        txtDataNasc = findViewById(R.id.txtDataNasc);
        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);

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
                /*
                Cliente cliente = new Cliente();
                cliente.setNome(txtNome.getText().toString());
                cliente.setEmail(txtEmail.getText().toString());
                cliente.setDataNascimento(txtDataNasc.getText().toString());
                Intent it = new Intent(Cadastro.this, FragmentsPage.class);
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                it.putExtra("cliente", cliente);
                startActivity(it);*/

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
                //Toast.makeText(Cadastro.this, ""+nomeCompleto+"\n"+nome+"\n"+sobrenome, Toast.LENGTH_SHORT).show();
                CadastroTask task = new CadastroTask();
                task.execute(nome, sobrenome, cpf, dataNascimento, email, senha);
            }
        });

    }

    private class CadastroTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            load = ProgressDialog.show(Cadastro.this,
                    "Por favor Aguarde ...", "Recuperando Informações do Servidor...");
        }

        @Override
        protected Integer doInBackground(String... strings) {
            Cliente cliente = new Cliente();
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
            int idCliente = util.cadastro(apiUrl, cliente);

            if(idCliente > 0) {
                //int id = 2;
                //cliente.setId(idCliente);

                
                //cliente = util.getCliente(apiUrl, idCliente);
            }
            return idCliente;
        }

        @Override
        protected void onPostExecute(Integer idCliente) {
            super.onPostExecute(idCliente);
            if (idCliente > 0) {
                Toast.makeText(Cadastro.this, "Cadastro efetuado com sucesso", Toast.LENGTH_SHORT).show();
                Intent it = new Intent(Cadastro.this, FragmentsPage.class);
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                it.putExtra("idCliente", idCliente);
                startActivity(it);
            } else {
                Toast.makeText(Cadastro.this, "Falha ao cadastrar", Toast.LENGTH_SHORT).show();
            }
            load.dismiss();
        }

    }

}