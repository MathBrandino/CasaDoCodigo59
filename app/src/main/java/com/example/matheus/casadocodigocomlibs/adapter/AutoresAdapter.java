package com.example.matheus.casadocodigocomlibs.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.matheus.casadocodigocomlibs.R;
import com.example.matheus.casadocodigocomlibs.model.Autor;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by matheus on 05/07/16.
 */
public class AutoresAdapter extends RecyclerView.Adapter {
    private final ArrayList<Autor> autores;

    public AutoresAdapter(ArrayList<Autor> autores) {

        this.autores = autores;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.autor_item, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        ViewHolder holder = (ViewHolder) viewHolder;

        Autor autor = autores.get(position);

        holder.nome.setText(autor.getNomeAutor());

        holder.descricao.setText(autor.getDetalhesAutor());

        Picasso.with(holder.foto.getContext()).load(autor.getImagemAutorUrl()).fit().into(holder.foto);

    }

    @Override
    public int getItemCount() {
        return autores.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.foto_autor_item)
        ImageView foto;

        @BindView(R.id.nome_autor_item)
        TextView nome;

        @BindView(R.id.desc_autor_item)
        TextView descricao;

        public ViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }


}
