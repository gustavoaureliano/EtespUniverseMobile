package com.etespuniverse.mobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;

public class SharedData {

    private Context context = null;
    private static Cliente cliente = new Cliente();
    private static ArrayList<Ingresso> ingressos = new ArrayList<>();
    private static ArrayList<ModelAtracao> atracoes = new ArrayList<>();
    private static ArrayList<Cupom> cupons = new ArrayList<>();
    private static ArrayList<ItemCarrinho> carrinho = new ArrayList<>();
    private static boolean logado = false;
    //private static String apiUrl = "http://10.0.2.2/web-tcc/api/"; //emulador
    //private static final String apiUrl = "http://192.168.0.9/tcc/api/"; //meu pc
    //private static final String apiUrl = "http://192.168.0.9/testcomposer/api/"; //meu pc 2
    private static String apiUrl = "http://192.168.0.7/web-tcc/api/"; //meu pc 3
    //private static String apiUrl = "http://10.67.74.58:8080/web-tcc/api/"; //emulador
    //public static final String apiUrl = "http://192.168.62.145/web-tcc/api/"; //meu pc 4
    //private static final String apiUrl = "http://testtccheroku3.herokuapp.com/api/"; //internet

    public SharedData() {
        this.context = context;
    }

    public SharedData(Context context) {
        this.context = context;
    }

    public static Cliente getCliente() {
        //cliente.setId(1);
        return cliente;
    }

    public static void setCliente(Cliente cliente) {
        Log.d("TAG", "Set cliente (SharedData)");
        Log.d("TAG", "Cliente: " + cliente.toString());
        SharedData.cliente = cliente;
    }

    public static void salvarCliente() {
        SharedData.cliente = new Utils().getCliente(cliente.getId());
    }

    public static ArrayList<Ingresso> getIngressos() {
        return ingressos;
    }

    public static void setIngressos(ArrayList<Ingresso> ingressos) {
        SharedData.ingressos = ingressos;
    }

    public static ArrayList<ModelAtracao> getAtracoes() {
        return atracoes;
    }

    public static void setAtracoes(ArrayList<ModelAtracao> atracoes) {
        SharedData.atracoes = atracoes;
    }

    public static ArrayList<Cupom> getCupons() {
        return cupons;
    }

    public static void setCupons(ArrayList<Cupom> cupons) {
        SharedData.cupons = cupons;
    }

    public static ArrayList<ItemCarrinho> getCarrinho() {
        return carrinho;
    }

    public static void setCarrinho(ArrayList<ItemCarrinho> carrinho) {
        SharedData.carrinho = carrinho;
    }

    public boolean isLogado() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("login", false);
    }

    public void setLogado(boolean logado) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("login", logado);
        editor.apply();
    }

    public static String getApiUrl() {
        return apiUrl;
    }

    public static void setApiUrl(String apiUrl) {
        SharedData.apiUrl = apiUrl;
    }

}
