package com.etespuniverse.mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AtracoesAdapter extends RecyclerView.Adapter<AtracoesAdapter.AtracoesViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<ModelAtracao> atracoes;
    private ItemClickListener mClickListener;

    public AtracoesAdapter(Context context, ArrayList<ModelAtracao> atracoes) {
        this.inflater = LayoutInflater.from(context);
        this.atracoes = atracoes;
    }

    @NonNull
    @Override
    public AtracoesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_atracao, parent, false);
        AtracoesViewHolder viewHolder = new AtracoesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AtracoesViewHolder holder, int position) {
        //holder.image.setImageResource(R.drawable.roda);
        //holder.lblNome.setText(holder.lblStatus.getText());
        holder.lblNome.setText(atracoes.get(position).getNome());
        int status = atracoes.get(position).getStatus();
        if(status == 1) {
            holder.lblStatus.setText("Aberta");
        } else {
            holder.lblStatus.setText("Fechada");
        }
    }

    @Override
    public int getItemCount() {
        return atracoes.size();
    }

    public class AtracoesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView image;
        private TextView lblNome, lblStatus;

        public AtracoesViewHolder(@NonNull View itemView) {
            super(itemView);
            //image = itemView.findViewById(R.id.cardImage);
            lblNome = itemView.findViewById(R.id.lblNome);
            lblStatus = itemView.findViewById(R.id.lblStatus);
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
