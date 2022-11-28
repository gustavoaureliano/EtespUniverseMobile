package com.etespuniverse.mobile;

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class Ajuda extends AppCompatActivity {

    private MaterialToolbar topAppBar;
    private RecyclerView rvPerguntas;
    private AjudaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajuda);

        topAppBar = findViewById(R.id.topAppBar);
        rvPerguntas = findViewById(R.id.rvPerguntas);

        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ArrayList<Pergunta> perguntas = new ArrayList<>();

        Pergunta pergunta1 = new Pergunta();
        pergunta1.setTitulo("Tem Comida no Parque?");
        pergunta1.setResposta("Sim, temos vários parceiros que montam suas barracas pelo parque. São diversas opções de comidas e bebidas, nos mais variados preços. Existem cupons para conseguir descontos nos restaurantes.");
        Pergunta pergunta2 = new Pergunta();
        pergunta2.setTitulo("O Que Levar Para o Parque?");
        pergunta2.setResposta("É essencial levar documento de identificação, ir com roupas confortáveis, QRcode do ingresso e guarda chuva. Evite levar muitas coisas para não atraplhar sua diversão");
        Pergunta pergunta3 = new Pergunta();
        pergunta3.setTitulo("Quais São os Requisitos para Poder ir no Parque?");
        pergunta3.setResposta("É livre para todos, porém em algumas atrações é necessário certos requesitos como: altura, peso e idade. Confira na parte de atrações quais que precisam disso. Se divirta!");
        perguntas.add(pergunta1);
        perguntas.add(pergunta2);
        perguntas.add(pergunta3);

        Toast.makeText(this, "Length: " + perguntas.size(), Toast.LENGTH_SHORT).show();
        rvPerguntas.setLayoutManager(new LinearLayoutManager(Ajuda.this, LinearLayoutManager.VERTICAL, false));
        adapter = new AjudaAdapter(Ajuda.this, perguntas);
        adapter.setClickListener(new AjudaAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(Ajuda.this, "Hello", Toast.LENGTH_SHORT).show();
                LinearLayout content = view.findViewById(R.id.content);
                ImageView icon = view.findViewById(R.id.icon);
                if (content.getVisibility() == View.VISIBLE) {
                    TransitionManager.beginDelayedTransition((ViewGroup) view, new AutoTransition());
                    content.setVisibility(View.GONE);
                    icon.setImageResource(R.drawable.ic_round_expand_more_24);
                } else {
                    TransitionManager.beginDelayedTransition((ViewGroup) view, new AutoTransition());
                    content.setVisibility(View.VISIBLE);
                    icon.setImageResource(R.drawable.ic_round_expand_less_24);
                }
            }
        });
        rvPerguntas.setAdapter(adapter);

    }
}