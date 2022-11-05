package com.etespuniverse.mobile;

import java.util.ArrayList;

public class Pedido {

    private Cliente cliente = new Cliente();
    private int idCupom = -1;
    private ArrayList<Ingresso> ingressos = new ArrayList<Ingresso>();

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getIdCupom() {
        return idCupom;
    }

    public void setIdCupom(int idCupom) {
        this.idCupom = idCupom;
    }

    public ArrayList<Ingresso> getIngressos() {
        return ingressos;
    }

    public void setIngressos(ArrayList<Ingresso> ingressos) {
        this.ingressos = ingressos;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "cliente=" + cliente +
                ", idCupom=" + idCupom +
                ", ingressos=" + ingressos +
                '}';
    }
}
