package com.etespuniverse.mobile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class Ingressos extends Fragment {

    private static final String ARG_PARAM_CLIENTE = "cliente";

    private MaterialToolbar topAppBar;
    private RecyclerView rvIngressos;
    private IngressosAdapter adapter;
    private ArrayList<Ingresso> ingressos = new ArrayList<Ingresso>();

    private Cliente cliente = SharedData.getCliente();
    private String apiUrl = SharedData.getApiUrl();

    public Ingressos() {
        // Required empty public constructor
    }

    public static Ingressos newInstance() {
        Ingressos fragment = new Ingressos();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //cliente = (Cliente) getArguments().getSerializable(ARG_PARAM_CLIENTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingressos, container, false);

        rvIngressos = view.findViewById(R.id.rvIngressos);

        topAppBar = view.findViewById(R.id.topAppBar);
        topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.optCarrinho:
                        Toast.makeText(getContext(), cliente.getNome(), Toast.LENGTH_SHORT).show();
                        Intent it = new Intent(getContext(), Carrinho.class);
                        //it.putExtra(ARG_PARAM_CLIENTE, cliente);
                        startActivity(it);
                        return true;
                }
                return false;
            }
        });

        IngressosTask task = new IngressosTask();
        task.execute();

        return view;
    }

    private class IngressosTask extends AsyncTask<Void, Void, ArrayList<Ingresso>> {

        @Override
        protected void onPreExecute() {
            //load = ProgressDialog.show(Carrinho.this,"", "");
        }

        @Override
        protected ArrayList<Ingresso> doInBackground(Void... voids) {
            ArrayList<Ingresso> ingressos = new ArrayList<Ingresso>();
            Utils util = new Utils();
            ingressos = util.getIngressos(apiUrl, cliente);
            return ingressos;
        }

        @Override
        protected void onPostExecute(ArrayList<Ingresso> ingressos) {
            super.onPostExecute(ingressos);
            //load.dismiss();
            rvIngressos.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            adapter = new IngressosAdapter(getContext(), ingressos);
            adapter.setOnClickListener(new OnClickIngressoListener() {
                @Override
                public void onClick(Ingresso ingresso, int index, ImageView imgIngresso) {
                    Toast.makeText(getContext(), "Mostrar Ingresso", Toast.LENGTH_SHORT).show();
                    Bitmap bitmap;
                    QRGEncoder qrgEncoder;
                    WindowManager manager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;

                    int dimen = width < height ? width : height;
                    Log.d("TAG", "X: " + width);
                    Log.d("TAG", "Y: " + height);
                    Log.d("TAG", "dimen: " + dimen);
                    dimen = dimen * 3 / 4;
                    Log.d("TAG", "dimen (3/4): " + dimen);
                    qrgEncoder = new QRGEncoder(String.valueOf(ingresso.getIdIngresso()), null, QRGContents.Type.TEXT, dimen);
                    try {
                        bitmap = qrgEncoder.getBitmap();
                        //imgIngresso.setImageBitmap(bitmap);
                        QrCodeDialog dialog = new QrCodeDialog(ingresso, bitmap);
                        dialog.show(getParentFragmentManager(), dialog.getTag());
                    } catch (Exception e) {
                        Log.d("TAG", "Error: " + e.getMessage());
                        //Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            rvIngressos.setAdapter(adapter);

        }

    }

}