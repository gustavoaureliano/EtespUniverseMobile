package com.etespuniverse.mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class IngressosComprarAdapter extends RecyclerView.Adapter<IngressosComprarAdapter.ViewHolder>{

    private LayoutInflater inflater;
    private ArrayList<Ingresso> ingressos;
    private OnBtnComprarClick clickListener;

    public IngressosComprarAdapter(Context context, ArrayList<Ingresso> ingressos) {
        this.inflater = LayoutInflater.from(context);
        this.ingressos = ingressos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_ingresso_comprar, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingresso ingresso = ingressos.get(position);
        holder.txtDesc.setText(ingresso.getDescricao());
        double money = ingresso.getPreco();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
        DecimalFormat formatter = new DecimalFormat("###,###,##0.00", symbols);
        formatter.setRoundingMode(RoundingMode.DOWN);
        String moneyString = "R$ " + formatter.format(money);
        holder.txtPreco.setText(moneyString);
        holder.imgIngresso.setImageBitmap(ingresso.getFoto());
        holder.btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onClick(ingresso, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingressos.size();
    }

    public void setOnClickListener(OnBtnComprarClick clickListener) {
        this.clickListener = clickListener;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDesc, txtPreco;
        ImageView imgIngresso;
        Button btnComprar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDesc = itemView.findViewById(R.id.txtDesc);
            txtPreco = itemView.findViewById(R.id.txtPreco);
            imgIngresso = itemView.findViewById(R.id.imgIngresso);
            btnComprar = itemView.findViewById(R.id.btnComprar);
        }
    }

}
