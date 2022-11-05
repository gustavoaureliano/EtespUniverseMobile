package com.etespuniverse.mobile;

public class ItemCarrinho extends Ingresso {

    private int idItem;
    private int qtde = 0;

    public ItemCarrinho() {
        //
    }

    public int getQtde() {
        return qtde;
    }

    public void setQtde(int qtde) {
        this.qtde = qtde;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }
}
