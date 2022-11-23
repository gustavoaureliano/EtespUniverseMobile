package com.etespuniverse.mobile;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Ingresso implements Serializable {

    private int idIngresso = -1;
    private int idCliente = -1;
    private int idStatusIngresso = -1;
    private int idTipoIngresso = -1;
    private String dataEmissao = "";
    private boolean meia = false;
    private Date dataInicio = new Date();
    private Date dataValidade = new Date();
    private int idEvento = -1;
    private double preco = 0;
    private Bitmap foto = null;
    private String descricao = "";
    private SimpleDateFormat dateFormatShow = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat dateFormatDB = new SimpleDateFormat("yyyy-MM-dd");

    public Ingresso() {
        //
    }

    public int getIdIngresso() {
        return idIngresso;
    }

    public void setIdIngresso(int idIngresso) {
        this.idIngresso = idIngresso;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdStatusIngresso() {
        return idStatusIngresso;
    }

    public void setIdStatusIngresso(int idStatusIngresso) {
        this.idStatusIngresso = idStatusIngresso;
    }

    public int getIdTipoIngresso() {
        return idTipoIngresso;
    }

    public void setIdTipoIngresso(int idTipoIngresso) {
        this.idTipoIngresso = idTipoIngresso;
    }

    public String getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(String dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public boolean isMeia() {
        return meia;
    }

    public void setMeia(boolean meia) {
        this.meia = meia;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public String getDataInicioShow() {
        return dateFormatShow.format(dataInicio.getTime());
    }

    public String getDataInicioDB() {
        return dateFormatDB.format(dataInicio.getTime());
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public void setDataInicioDB(String dataInicio) {
        String[] data = dataInicio.split("-");
        Calendar cal = Calendar.getInstance();
        int year = Integer.parseInt(data[0]);
        int month = Integer.parseInt(data[1]);
        month--;
        int day = Integer.parseInt(data[2]);
        cal.set(year, month, day);
        this.dataInicio = cal.getTime();
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public String getDataValidadeShow() {
        return dateFormatShow.format(dataValidade);
    }

    public String getDataValidadeDB() {
        return dateFormatDB.format(dataValidade);
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }

    public void setDataValidadeDB(String dataValidade) {
        String[] data = dataValidade.split("-");
        Calendar cal = Calendar.getInstance();
        int year = Integer.parseInt(data[0]);
        int month = Integer.parseInt(data[1]);
        int day = Integer.parseInt(data[2]);
        cal.set(year, month, day);
        this.dataValidade = cal.getTime();
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public double getPreco() {
        if (meia) {

        }
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Ingresso{" +
                "idIngresso=" + idIngresso +
                ", idCliente=" + idCliente +
                ", idStatusIngresso=" + idStatusIngresso +
                ", idTipoIngresso=" + idTipoIngresso +
                ", dataEmissao='" + dataEmissao + '\'' +
                ", meia=" + meia +
                ", dataInicio='" + dataInicio + '\'' +
                ", dataValidade='" + dataValidade + '\'' +
                '}';
    }
}
