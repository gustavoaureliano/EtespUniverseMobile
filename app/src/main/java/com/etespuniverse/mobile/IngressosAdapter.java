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

import java.util.ArrayList;

public class IngressosAdapter extends RecyclerView.Adapter<IngressosAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<Ingresso> ingressos;
    private OnClickIngressoListener clickListener;

    public IngressosAdapter(Context context, ArrayList<Ingresso> ingressos) {
        this.inflater = LayoutInflater.from(context);
        this.ingressos = ingressos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_ingresso, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingresso ingresso = ingressos.get(position);
        boolean meia = ingresso.isMeia();
        if(!meia) {
            holder.lblInteiro.setText("Inteiro");
        } else {
            holder.lblInteiro.setText("Meia");
        }
        holder.lblData.setText(ingresso.getDataInicioShow());
        holder.imgIngresso.setImageBitmap(ingresso.getFoto());
        holder.btnMostrarIngresso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onClick(ingresso, holder.getAdapterPosition(), holder.imgIngresso);
            }
        });

    }

    @Override
    public int getItemCount() {
        return ingressos.size();
    }

    public void setOnClickListener(OnClickIngressoListener clickListener) {
        this.clickListener = clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView lblInteiro, lblData;
        private Button btnMostrarIngresso;
        private ImageView imgIngresso;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblInteiro = itemView.findViewById(R.id.lblInteiro);
            lblData = itemView.findViewById(R.id.lblData);
            btnMostrarIngresso = itemView.findViewById(R.id.btnMostrarIngresso);
            imgIngresso = itemView.findViewById(R.id.imgIngresso);
        }

        @Override
        public void onClick(View view) {

        }
    }

}
