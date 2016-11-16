package com.example.matheus.casadocodigocomlibs.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.matheus.casadocodigocomlibs.R;
import com.example.matheus.casadocodigocomlibs.event.LivroEvent;
import com.example.matheus.casadocodigocomlibs.model.Livro;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by matheus on 29/06/16.
 */
public class ListaLivrosUmItemAdapter extends RecyclerView.Adapter {

    private List<Livro> livros;

    public ListaLivrosUmItemAdapter(List<Livro> livros) {
        this.livros = livros;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_livro_par, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        ViewHolder holder = (ViewHolder) viewHolder;

        Livro livro = livros.get(position);

        holder.nomeLivro.setText(livro.getNome());

        Picasso.with(holder.fotoLivro.getContext()).load(livro.getImagemUrl()).placeholder(R.drawable.livro).fit().into(holder.fotoLivro);

    }

    @Override
    public int getItemCount() {
        return livros.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imagem_livro_item)
        ImageView fotoLivro;

        @BindView(R.id.nome_livro_item)
        TextView nomeLivro;


        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }


        @OnClick(R.id.item)
        public void clickItem() {
            EventBus.getDefault().post(new LivroEvent(livros.get(getAdapterPosition())));

        }
    }
}
