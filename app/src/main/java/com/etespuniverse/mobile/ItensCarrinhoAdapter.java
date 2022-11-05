package com.etespuniverse.mobile;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class ItensCarrinhoAdapter extends RecyclerView.Adapter<ItensCarrinhoAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<ItemCarrinho> itens;
    private OnCartItemClickInterface onCartItemClickInterface;

    public ItensCarrinhoAdapter(Context context, ArrayList<ItemCarrinho> itens) {
        this.inflater = LayoutInflater.from(context);
        this.itens = itens;
        Log.d("TAG", "cont: " + itens.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_item_carrinho, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemCarrinho item = itens.get(position);
        holder.lblIngresso.setText("ingresso");
        holder.lblPreco.setText("preço");
        holder.lblQtde.setText("qtde");
        Log.d("TAG", "qtde: " + item.getQtde());
        String qtde = String.valueOf(item.getQtde());
        holder.lblQtde.setText(qtde);
        //double preco = 89.74;
        holder.lblIngresso.setText((item.isMeia()?"Meia":"Inteira"));
        //holder.lblQtde.setText(item.getQtde());
        //holder.lblPreco.setText("R$ " + preco);
        double money = item.getPreco();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
        DecimalFormat formatter = new DecimalFormat("###,###,##0.00", symbols);
        formatter.setRoundingMode(RoundingMode.DOWN);
        String moneyString = "R$ " + formatter.format(money);
        Log.d("TAG", "Preço: " + moneyString);
        holder.lblPreco.setText(moneyString);
        Log.d("TAG", "qtde: " + itens.get(position).getQtde());

        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCartItemClickInterface.clickPlus(item.getIdItem(), holder.getAdapterPosition());
            }
        });

        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCartItemClickInterface.clickMinus(item.getIdItem(), holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    public void setOnClickInterface(OnCartItemClickInterface onCartItemClickInterface) {
        this.onCartItemClickInterface = onCartItemClickInterface;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private TextView lblQtde, lblPreco, lblIngresso;
        private Button btnMinus, btnPlus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblQtde = itemView.findViewById(R.id.lblQtde);
            lblPreco = itemView.findViewById(R.id.lblPreco);
            lblIngresso = itemView.findViewById(R.id.lblIngresso);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnPlus = itemView.findViewById(R.id.btnPlus);
        }
    }

}
