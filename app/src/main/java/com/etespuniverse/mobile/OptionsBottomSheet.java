package com.etespuniverse.mobile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class OptionsBottomSheet extends BottomSheetDialogFragment {

    private LinearLayout optSair, optAjuda, optCupons, btnPerfil, optFazerLogin, optLogado;
    private TextView txtNome;
    private ImageView imgPerfil;

    private String apiUrl = SharedData.getApiUrl();
    private Cliente cliente = SharedData.getCliente();
    private boolean logado = true;

    public static OptionsBottomSheet newInstance() {

        //Bundle args = new Bundle();
        //args.putSerializable("cliente", cliente);
        OptionsBottomSheet fragment = new OptionsBottomSheet();
        //fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.options_bottom_sheet, container, false);

        optSair = view.findViewById(R.id.optSair);
        optAjuda = view.findViewById(R.id.optAjuda);
        optCupons = view.findViewById(R.id.optCupons);
        txtNome = view.findViewById(R.id.txtNome);
        btnPerfil = view.findViewById(R.id.btnPerfil);
        imgPerfil = view.findViewById(R.id.imgPerfil);
        optFazerLogin = view.findViewById(R.id.optFazerLogin);
        optLogado = view.findViewById(R.id.optLogado);

        SharedData sharedData = new SharedData(getContext());
        logado = sharedData.isLogado();
        Log.d("TAG", "Logado: " + logado);

        if (logado && cliente.getId() > 0) {
            optLogado.setVisibility(View.VISIBLE);
            optFazerLogin.setVisibility(View.GONE);
            Log.d("TAG", "Clinte got (BSheetPage): " + cliente.toString());
            txtNome.setText(cliente.getNome());
            imgPerfil.setImageBitmap(cliente.getFoto());
            btnPerfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent it = new Intent(getContext(), Perfil.class);
                    startActivity(it);
                    dismiss();
                }
            });
        } else {
            optLogado.setVisibility(View.GONE);
            optFazerLogin.setVisibility(View.VISIBLE);
            btnPerfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent it = new Intent(getContext(), Login.class);
                    startActivity(it);
                    dismiss();
                }
            });
        }
        optSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedData sharedData = new SharedData(getContext());
                sharedData.setLogado(false);
                SharedData.setCliente(new Cliente());
                DBHandlerLogin dbHandlerLogin = new DBHandlerLogin(getContext());
                dbHandlerLogin.deleteCliente(cliente);
                Intent it = new Intent(getContext(), MainActivity.class);
                //it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //it.putExtra()
                dismiss();
                //startActivity(it);
            }
        });

        //cliente = (Cliente) getArguments().getSerializable("cliente");

        optAjuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Ajuda", Toast.LENGTH_SHORT).show();
                Intent it = new Intent(getContext(), Ajuda.class);
                //it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(it);
            }
        });

        optCupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getContext(), Cupons.class);
                //it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(it);
            }
        });

        return view;
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
                Log.d("TAG", "bottomSheet: got image");
            } else {
                Log.d("TAG", "bottomSheet: no image");
            }
        }

    }

}
