package com.etespuniverse.mobile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class Perfil extends AppCompatActivity {

    private MaterialToolbar topAppBar;
    private Cliente cliente = SharedData.getCliente();
    private TextInputEditText txtNome, txtEmail, txtSenha, txtCPF, txtDataNascimento;
    private MaterialButton btnAtualizar;
    private String apiUrl = SharedData.getApiUrl();
    private ImageView imgPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        topAppBar = findViewById(R.id.topAppBar);
        txtNome = findViewById(R.id.txtNome);
        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);
        txtCPF = findViewById(R.id.txtCPF);
        txtDataNascimento = findViewById(R.id.txtDataNasc);
        btnAtualizar = findViewById(R.id.btnAtualizar);
        imgPerfil = findViewById(R.id.imgPerfil);

        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        txtNome.setText(cliente.getNome() + " " + cliente.getSobrenome());
        txtEmail.setText(cliente.getEmail());
        txtSenha.setText(cliente.getSenha());
        txtCPF.setText(cliente.getCpf());
        txtDataNascimento.setText(cliente.getDataNascimentoShow());
        imgPerfil.setImageBitmap(cliente.getFoto());
        Bitmap foto = cliente.getFoto();
        if (foto != null) {
            Log.d("TAG", "Foto (Perfil): " + cliente.getFoto().toString());
            imgPerfil.setImageBitmap(cliente.getFoto());
        } else  {
            Log.d("TAG", "No image (Perfil)");
        }

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            try {
                                Intent data = result.getData();
                                Uri dataUri = data.getData();
                                InputStream stream = getContentResolver().openInputStream(dataUri);
                                Bitmap image = BitmapFactory.decodeStream(stream);
                                //Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), dataUri);
                                imgPerfil.setImageBitmap(image);
                                //foto = image;
                                cliente.setFoto(image);
                                //SendImageTask sendTask = new SendImageTask();
                                //sendTask.execute(image);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            //Toast.makeText(Perfil.this, "Result OK", Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(Perfil.this, "Result Not OK", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        imgPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent it = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                Intent it = new Intent(Intent.ACTION_PICK);
                it.setType("image/*");
                activityResultLauncher.launch(it);
            }
        });


        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = "";
                String sobrenome = "";
                String nomeCompleto = txtNome.getText().toString();
                String[] nomeSplit = nomeCompleto.split(" ");
                nome = nomeSplit[0];
                sobrenome = nomeCompleto.substring(nome.length()+1);
                String cpf = txtCPF.getText().toString();
                String dataNascimento = txtDataNascimento.getText().toString();
                String email = txtEmail.getText().toString();
                String senha = txtSenha.getText().toString();
                cliente.setNome(nome);
                cliente.setSobrenome(sobrenome);
                cliente.setCpf(cpf);
                cliente.setDataNascimentoShow(dataNascimento);
                cliente.setEmail(email);
                cliente.setSenha(senha);

                AtualizarTask task = new AtualizarTask();
                task.execute();

            }
        });

        //ImageTask imgTask = new ImageTask();
        //imgTask.execute();



    }
    private class AtualizarTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(Perfil.this, "Got here", Toast.LENGTH_SHORT).show();
            //Toast.makeText(Perfil.this, cliente.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            Utils util = new Utils();
            Boolean atualizar = util.atualizarCliente(cliente);
            Boolean atualizarFoto = util.atualizarFotoCliente(cliente);
            Log.d("TAG", "atualizar: " + atualizar);
            if (atualizar && atualizarFoto) {
                SharedData.salvarCliente();
                DBHandlerLogin dbHandlerLogin = new DBHandlerLogin(Perfil.this);
                dbHandlerLogin.setCliente(cliente);
            }
            return atualizar && atualizarFoto;
        }

        @Override
        protected void onPostExecute(Boolean atualizar) {
            super.onPostExecute(atualizar);
            Log.d("TAG", "atualizar: " + atualizar);
            if (atualizar) {
                Snackbar.make(btnAtualizar, "Dados atualizados.", Snackbar.LENGTH_SHORT)
                        .show();
            } else {
                Snackbar.make(btnAtualizar, "Falha ao atualizar os dados.", Snackbar.LENGTH_SHORT)
                        .show();
            }
        }

    }

    private class ImageTask extends AsyncTask<Void, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(Perfil.this, "Got here", Toast.LENGTH_SHORT).show();
            //Toast.makeText(Perfil.this, cliente.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            Utils util = new Utils();
            Bitmap image = util.getImageCliente(cliente);
            Log.d("TAG", "image: " + image);
            return image;
        }

        @Override
        protected void onPostExecute(Bitmap image) {
            super.onPostExecute(image);
            Log.d("TAG", "image: " + image);
            //Toast.makeText(Perfil.this, "Not here", Toast.LENGTH_SHORT).show();
            if (image != null) {
                imgPerfil.setImageBitmap(image);
                Toast.makeText(Perfil.this, "Got image", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Perfil.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        }

    }

}