package com.example.matheus.casadocodigocomlibs.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.matheus.casadocodigocomlibs.R;
import com.example.matheus.casadocodigocomlibs.event.AutorEvent;
import com.example.matheus.casadocodigocomlibs.infra.Infra;
import com.example.matheus.casadocodigocomlibs.model.Autor;
import com.example.matheus.casadocodigocomlibs.model.Carrinho;
import com.example.matheus.casadocodigocomlibs.model.Item;
import com.example.matheus.casadocodigocomlibs.model.Livro;
import com.example.matheus.casadocodigocomlibs.model.TipoDeCompra;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    private Livro livro;

    private Carrinho carrinho;

    @Inject
    void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.detalhe_livro_fragment, container, false);
        ButterKnife.bind(this, view);

        Bundle arguments = getArguments();
        livro = (Livro) arguments.getSerializable(LIVRO);

        populaCamposCom(livro);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Infra.colocaBotaoVoltar((AppCompatActivity) getActivity());
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


    @OnClick(R.id.autores_livro_detalhes)
    public void clickAutores() {

        EventBus.getDefault().post(new AutorEvent((ArrayList<Autor>) livro.getAutores()));
    }


    @OnClick(R.id.btn_comprar_detalhe)
    public void clickComprar() {


        final RadioGroup opcoes = retornaOpcoes();

        new AlertDialog.Builder(getContext())
                .setTitle(livro.getNome())
                .setView(opcoes)
                .setPositiveButton("Adicionar ao carrinho", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final Item item = new Item(livro, retornaTipoDeCompra(opcoes));

                        carrinho.adicionar(item);

                        Snackbar.make(getView(), "Adicionado ao carrinho", Snackbar.LENGTH_SHORT)
                                .setAction("Cancelar", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        carrinho.remove(item);
                                    }
                                })
                                .show();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private TipoDeCompra retornaTipoDeCompra(RadioGroup opcoes) {

        switch (opcoes.getCheckedRadioButtonId()) {
            case R.id.juntos:
                return TipoDeCompra.JUNTOS;

            case R.id.virtual:
                return TipoDeCompra.VIRTUAL;

            default:
                return TipoDeCompra.FISICO;
        }

    }

    private RadioGroup retornaOpcoes() {

        RadioGroup view = (RadioGroup) LayoutInflater.from(getContext()).inflate(R.layout.opcoes_pagamento, null);

        RadioButton fisico = ButterKnife.findById(view, R.id.fisico);
        RadioButton virtual = ButterKnife.findById(view, R.id.virtual);
        RadioButton juntos = ButterKnife.findById(view, R.id.juntos);


        fisico.setText("Fisico - " + "R$ " + livro.getValorFisico());

        virtual.setText("Virtual- " + "R$ " + livro.getValorVirtual());

        juntos.setText("Juntos - " + "R$ " + livro.getValorDoisJuntos());

        juntos.toggle();


        return view;
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
