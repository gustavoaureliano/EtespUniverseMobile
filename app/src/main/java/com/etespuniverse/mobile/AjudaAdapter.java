package com.etespuniverse.mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AjudaAdapter extends RecyclerView.Adapter<AjudaAdapter.AjudaViewHolder>{

    private LayoutInflater inflater;
    private ArrayList<Pergunta> perguntas;
    private ItemClickListener mClickListener;
    private ImageView icon;

    public AjudaAdapter(Context context, ArrayList<Pergunta> perguntas) {
        this.inflater = LayoutInflater.from(context);
        this.perguntas = perguntas;

    }

    @NonNull
    @Override
    public AjudaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_ajuda, parent, false);
        AjudaViewHolder viewHolder = new AjudaViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AjudaViewHolder holder, int position) {
        Pergunta pergunta = perguntas.get(position);
        holder.lblTitulo.setText(pergunta.getTitulo());
        holder.lblResp.setText(pergunta.getResposta());
        holder.expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(holder.itemView, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return perguntas.size();
    }

    public class AjudaViewHolder extends RecyclerView.ViewHolder{
        private TextView lblTitulo, lblResp;
        private LinearLayout item, content, expand;

        public AjudaViewHolder(@NonNull View itemView) {
            super(itemView);
            lblTitulo = itemView.findViewById(R.id.lblTitulo);
            lblResp = itemView.findViewById(R.id.lblResp);
            item = itemView.findViewById(R.id.item);
            content = itemView.findViewById(R.id.content);
            expand = itemView.findViewById(R.id.expand);
            icon = itemView.findViewById(R.id.icon);
        }

    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
