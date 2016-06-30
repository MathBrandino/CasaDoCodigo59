package com.example.matheus.casadocodigocomlibs.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.matheus.casadocodigocomlibs.R;
import com.example.matheus.casadocodigocomlibs.delegate.LivroDelegate;
import com.example.matheus.casadocodigocomlibs.model.Livro;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by matheus on 29/06/16.
 */
public class ListaLivrosAdapter extends RecyclerView.Adapter {

    private List<Livro> livros;
    private LivroDelegate delegate;

    public ListaLivrosAdapter(List<Livro> livros, LivroDelegate delegate) {
        this.livros = livros;
        this.delegate = delegate;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType % 2 == 0)
            view = LayoutInflater.from(delegate.getContext()).inflate(R.layout.item_livro_par, parent, false);
        else
            view = LayoutInflater.from(delegate.getContext()).inflate(R.layout.item_livro_impar, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        ViewHolder holder = (ViewHolder) viewHolder;

        Livro livro = livros.get(position);

        holder.nomeLivro.setText(livro.getNome());

        Picasso.with(delegate.getContext()).load(livro.getImagemUrl()).placeholder(R.drawable.livro).fit().into(holder.fotoLivro);

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
            delegate.lidaComClick(livros.get(getAdapterPosition()));
        }
    }
}
