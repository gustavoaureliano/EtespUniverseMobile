package com.etespuniverse.mobile;

import android.graphics.Bitmap;

public class Cupom {

    private int idCupom = -1;
    private int idTipoCupom = -1;
    private String nome = "";
    private String descricao = "";
    private String dataValidade = "";
    private String dataInicio = "";
    private Bitmap foto = null;
    private double desconto = 0;
    private int idCliente = -1;

    public int getIdCupom() {
        return idCupom;
    }

    public void setIdCupom(int idCupom) {
        this.idCupom = idCupom;
    }

    public int getIdTipoCupom() {
        return idTipoCupom;
    }

    public void setIdTipoCupom(int idTipoCupom) {
        this.idTipoCupom = idTipoCupom;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(String dataValidade) {
        this.dataValidade = dataValidade;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public String toString() {
        if (idCupom > 0) {
            return nome + " - desconto de " + desconto + "%";
        } else {
            return "Não selecionado";
        }
    }
}
