package com.example.matheus.casadocodigocomlibs.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.matheus.casadocodigocomlibs.R;
import com.example.matheus.casadocodigocomlibs.model.Autor;
import com.example.matheus.casadocodigocomlibs.model.Livro;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by matheus on 29/06/16.
 */
public class DetalheLivroFragment extends Fragment {

    public static final String LIVRO = "livro";
    @BindView(R.id.foto_livro_detalhes)
    ImageView imagem;

    @BindView(R.id.nome_livro_detalhes)
    TextView nome;

    @BindView(R.id.autores_livro_detalhes)
    TextView autores;

    @BindView(R.id.desc_livro_detalhe)
    TextView descricao;

    @BindView(R.id.num_pag_livro_detalhe)
    TextView numPag;

    @BindView(R.id.livro_isbn_detalhe)
    TextView isbn;

    @BindView(R.id.data_pub_livro_detalhe)
    TextView dataPublicacao;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.detalhe_livro_fragment, container, false);
        ButterKnife.bind(this, view);


        Bundle arguments = getArguments();
        Livro livro = (Livro) arguments.getSerializable(LIVRO);

        populaCamposCom(livro);


        return view;
    }

    private void populaCamposCom(Livro livro) {
        nome.setText(livro.getNome());
        descricao.setText(livro.getDescricao());

        String nomeAutores = pegaNomeAutoresDo(livro);

        autores.setText(nomeAutores);

        numPag.setText(livro.getNumPaginas() + " paginas");

        isbn.setText(livro.getISBN());

        dataPublicacao.setText(livro.getDataPublicacao());

        Picasso.with(getContext()).load(livro.getImagemUrl()).placeholder(R.drawable.livro).fit().into(imagem);
    }

    @NonNull
    private String pegaNomeAutoresDo(Livro livro) {
        String nomeAutores = "";
        for (Autor autor : livro.getAutores()) {
            nomeAutores += autor.getNomeAutor() + "\n";
        }
        return nomeAutores;
    }
}
