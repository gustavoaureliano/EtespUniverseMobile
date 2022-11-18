package com.etespuniverse.mobile;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    private static final String ARG_PARAM_CLIENTE = "cliente";

    private Button btnComprar;
    private MaterialToolbar topAppBar;
    private RecyclerView rvIngressosComprar;
    private CircularProgressIndicator progressIndicator;

    private CarouselView carouselView;
    private int[] sampleImages = {R.drawable.carrossel_01, R.drawable.carrossel_02, R.drawable.carrossel_03};

    private Cliente cliente = FragmentsPage.cliente;
    private String apiUrl = SharedData.getApiUrl();

    public Home() {
        // Required empty public constructor
    }

    public static Home newInstance() {
        Home fragment = new Home();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //btnComprar = view.findViewById(R.id.btnComprar);
        //btnComprar.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        Intent it = new Intent(getContext(), ComprarIngresso.class);
        //        //it.putExtra(ARG_PARAM_CLIENTE, cliente);
        //        startActivity(it);
        //    }
        //});

        rvIngressosComprar = view.findViewById(R.id.rvIngressos);
        progressIndicator = view.findViewById(R.id.progressIndicator);
        carouselView = view.findViewById(R.id.carouselView);

        topAppBar = view.findViewById(R.id.topAppBar);
        topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.optCarrinho:
                        Intent it = new Intent(getContext(), Carrinho.class);
                        //it.putExtra(ARG_PARAM_CLIENTE, cliente);
                        startActivity(it);
                        return true;
                }
                return false;
            }
        });

        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(sampleImages[position]);
            }
        });

        //Ingresso ingresso = new Ingresso();
        //Ingresso ingresso2 = new Ingresso();
        //ingresso.setIdTipoIngresso(1);
        //ingresso.setDataInicioDB("2022-10-02");
        //ingresso.setDataValidadeDB("2022-10-02");
        //ingresso.setMeia(false);
        //ingresso.setPreco(56.91);
        //ingresso.setDescricao("Ingresso comum");
        //ingresso2.setIdTipoIngresso(1);
        //ingresso2.setDataInicioDB("2022-10-02");
        //ingresso2.setDataValidadeDB("2022-10-02");
        //ingresso2.setMeia(true);
        //ingresso2.setPreco(199.99);
        //ingresso2.setDescricao("Ingresso VIP");
        //ArrayList<Ingresso> ingressos = new ArrayList<Ingresso>();
        //ingressos.add(ingresso);
        //ingressos.add(ingresso2);
//
        //IngressosComprarAdapter adapter = new IngressosComprarAdapter(getContext(), ingressos);
        //rvIngressosComprar.setAdapter(adapter);
        //rvIngressosComprar.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        IngressosTask task = new IngressosTask();
        task.execute();

        return view;
    }

    private class IngressosTask extends AsyncTask<Void, Void, ArrayList<Ingresso>> {

        @Override
        protected void onPreExecute() {
            //load = ProgressDialog.show(Carrinho.this,"", "");
            progressIndicator.show();
        }

        @Override
        protected ArrayList<Ingresso> doInBackground(Void... voids) {
            ArrayList<Ingresso> ingressos = new ArrayList<Ingresso>();
            Utils util = new Utils();
            ingressos = util.getTiposIngresso(SharedData.getApiUrl(), cliente);
            return ingressos;
        }

        @Override
        protected void onPostExecute(ArrayList<Ingresso> ingressos) {
            super.onPostExecute(ingressos);
            //load.dismiss();
            IngressosComprarAdapter adapter = new IngressosComprarAdapter(getContext(), ingressos);
            OnBtnComprarClick clickListener = new OnBtnComprarClick() {
                @Override
                public void onClick(Ingresso ingresso, int index) {
                    Intent it = new Intent(getContext(), ComprarIngresso.class);
                    it.putExtra("ingresso", ingresso);
                    startActivity(it);
                }
            };
            adapter.setOnClickListener(clickListener);
            rvIngressosComprar.setAdapter(adapter);
            rvIngressosComprar.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            progressIndicator.hide();
        }

    }

}