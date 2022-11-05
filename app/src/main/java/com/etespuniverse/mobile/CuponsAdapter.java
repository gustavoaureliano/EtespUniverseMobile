package com.etespuniverse.mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CuponsAdapter extends RecyclerView.Adapter<CuponsAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<Cupom> cupons;

    public CuponsAdapter(Context context, ArrayList<Cupom> cupons) {
        this.inflater = LayoutInflater.from(context);
        this.cupons = cupons;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_cupom, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cupom cupom = cupons.get(position);
        holder.lblNome.setText(cupom.getNome());
        String txtDesconto = cupom.getDesconto()+"%";
        holder.lblDesconto.setText(txtDesconto);
    }

    @Override
    public int getItemCount() {
        return cupons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView lblNome, lblDesconto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblNome = itemView.findViewById(R.id.lblNome);
            lblDesconto = itemView.findViewById(R.id.lblDesconto);
        }

        @Override
        public void onClick(View view) {

        }
    }

}
