package com.etespuniverse.mobile;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Cliente implements Serializable {

    private int id = -1;
    private String cpf = "";
    private String nome = "";
    private String sobrenome = "";
    private Date dataNascimento = null;
    private String email = "";
    private String senha = "";
    private Bitmap foto = null;
    //private String foto = "";
    private int acessos = 0;
    private SimpleDateFormat dateFormatShow = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat dateFormatDB = new SimpleDateFormat("yyyy-MM-dd");

    public Cliente() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public String getDataNascimentoShow() {
        return dateFormatShow.format(dataNascimento.getTime());
    }

    public String getDataNascimentoDB() {
        return dateFormatDB.format(dataNascimento.getTime());
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setDataNascimentoDB(String dataNascimento) {
        String[] data = dataNascimento.split("-");
        Calendar cal = Calendar.getInstance();
        int year = Integer.parseInt(data[0]);
        int month = Integer.parseInt(data[1]);
        month--;
        int day = Integer.parseInt(data[2]);
        cal.set(year, month, day);
        this.dataNascimento = cal.getTime();
    }

    public void setDataNascimentoShow(String dataNascimento) {
        Log.d("TAG", "dataNascimento: " + dataNascimento);
        String[] data = dataNascimento.split("/");
        Calendar cal = Calendar.getInstance();
        int year = Integer.parseInt(data[2]);
        int month = Integer.parseInt(data[1]);
        month--;
        int day = Integer.parseInt(data[0]);
        Log.d("TAG", "year: " + year);
        Log.d("TAG", "month: " + month);
        Log.d("TAG", "day: " + day);
        cal.set(year, month, day);
        this.dataNascimento = cal.getTime();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public int getAcessos() {
        return acessos;
    }

    public void setAcessos(int acessos) {
        this.acessos = acessos;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", dataNascimento='" + dataNascimento + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", foto='" + foto + '\'' +
                ", acessos='" + acessos + '\'' +
                '}';
    }

}
