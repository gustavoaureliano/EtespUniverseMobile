package com.etespuniverse.mobile;

import android.graphics.Bitmap;

import java.io.Serializable;

public class ModelAtracao implements Serializable {

    private int id = -1;
    private int status = -1;
    private String nome = "";
    private String descricao = "";
    private String dataInauguracao = "";
    private String localização = "";
    private Bitmap foto = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getDataInauguracao() {
        return dataInauguracao;
    }

    public void setDataInauguracao(String dataInauguracao) {
        this.dataInauguracao = dataInauguracao;
    }

    public String getLocalização() {
        return localização;
    }

    public void setLocalização(String localização) {
        this.localização = localização;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }
}
