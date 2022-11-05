package com.etespuniverse.mobile;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class Atracoes extends Fragment {

    //private CardView cardAtracao;
    private ImageView card1Image;
    private ProgressDialog load;
    private String apiUrl = SharedData.getApiUrl();
    private RecyclerView rvAtracoes;
    private AtracoesAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    MaterialToolbar topAppBar;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Atracoes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Maps.
     */
    // TODO: Rename and change types and number of parameters
    public static Maps newInstance(String param1, String param2) {
        Maps fragment = new Maps();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atracoes);

        btnAtracao = findViewById(R.id.btnAtracao);
        atracao = findViewById(R.id.imgAtracao);

        btnAtracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Atracoes.this, Atracao.class);
                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation(Atracoes.this, atracao, "atracao");
                startActivity(it, options.toBundle());
            }
        });

    }
    */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_atracoes, container, false);

        //cardAtracao = view.findViewById(R.id.card1);
        card1Image = view.findViewById(R.id.card1Image);
        rvAtracoes = view.findViewById(R.id.rvAtracoes);
        /*
        cardAtracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getContext(), Atracao.class);
                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation(getActivity(), card1Image, "atracao");
                startActivity(it, options.toBundle());
                //startActivity(it);
            }
        });
        */
        topAppBar = view.findViewById(R.id.topAppBar);
        topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.optCarrinho:
                        Intent it = new Intent(getContext(), Carrinho.class);
                        startActivity(it);
                        return true;
                }
                return false;
            }
        });

        AtracoesTask task = new AtracoesTask();
        task.execute();

        return view;
    }

    private class AtracoesTask extends AsyncTask<String, Void, ArrayList<ModelAtracao>> {

        @Override
        protected void onPreExecute() {
            //load = ProgressDialog.show(getContext(), "Por favor Aguarde ...", "Recuperando Informações do Servidor...");
        }

        @Override
        protected ArrayList<ModelAtracao> doInBackground(String... strings) {
            ArrayList<ModelAtracao> atracoes = new ArrayList<ModelAtracao>();
            Utils util = new Utils();
            atracoes = util.getAtracoes(apiUrl);
            return atracoes;
        }

        @Override
        protected void onPostExecute(ArrayList<ModelAtracao> atracoes) {
            super.onPostExecute(atracoes);
            //int num = atracoes.size();
            //Toast.makeText(getContext(), ""+num, Toast.LENGTH_SHORT).show();
            rvAtracoes.setLayoutManager(new GridLayoutManager(getContext(), 2));
            adapter = new AtracoesAdapter(getContext(), atracoes);
            adapter.setClickListener(new AtracoesAdapter.ItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    ImageView cardImage = view.findViewById(R.id.cardImage);
                    Intent it = new Intent(getContext(), Atracao.class);
                    it.putExtra("atracao", atracoes.get(position));
                    ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation(getActivity(), cardImage, "cardImage");
                    startActivity(it, options.toBundle());
                    //startActivity(it);
                }
            });
            rvAtracoes.setAdapter(adapter);
            //load.dismiss();
        }

    }

}