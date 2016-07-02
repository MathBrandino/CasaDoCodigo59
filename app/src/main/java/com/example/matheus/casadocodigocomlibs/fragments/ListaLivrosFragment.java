package com.example.matheus.casadocodigocomlibs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.matheus.casadocodigocomlibs.R;
import com.example.matheus.casadocodigocomlibs.adapter.ListaLivrosAdapter;
import com.example.matheus.casadocodigocomlibs.event.ListaEvent;
import com.example.matheus.casadocodigocomlibs.infra.Infra;
import com.example.matheus.casadocodigocomlibs.model.Livro;
import com.example.matheus.casadocodigocomlibs.server.WebClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by matheus on 29/06/16.
 */
public class ListaLivrosFragment extends Fragment implements Serializable {




    @BindView(R.id.lista_livros)
    RecyclerView listaLivros;

    private ArrayList<Livro> livros = new ArrayList<>();
    private boolean carregando = false;
    private int quantidadeTotalAntiga;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.lista_livros_fragment, container, false);
        ButterKnife.bind(this, view);

        if (savedInstanceState != null) {
            livros = (ArrayList<Livro>) savedInstanceState.getSerializable("livros");
        } else {
            new WebClient().retornaLivroDoServidor(0, 10);

        }


        listaLivros.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();

                int quantidadeItensExibidos = manager.getChildCount();
                int totalItens = manager.getItemCount();
                int ultimaPosicaoVisivel = manager.findLastVisibleItemPosition();

                Log.d("Ultima Posicao", String.valueOf(ultimaPosicaoVisivel));





                // aqui faz um novo request para o servidor
                if (!carregando && quantidadeItensExibidos + ultimaPosicaoVisivel == totalItens){

                    new WebClient().retornaLivroDoServidor(livros.size(), 10);
                    carregando = true;
                }

                if (carregando && quantidadeTotalAntiga < totalItens) {
                    quantidadeTotalAntiga = totalItens;
                    carregando = false;
                }



            }
        });

        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("livros", livros);
    }

    @Subscribe
    public void recebeLista(ListaEvent event) {

        this.livros.addAll(event.livros);

        Log.d("Quantidade", String.valueOf(livros.size()));
        listaLivros.getAdapter().notifyDataSetChanged();
    }


    @Override
    public void onResume() {
        super.onResume();

        carregaLista();
        Infra.removeBotaoVoltar((AppCompatActivity) getActivity());
    }

    private void carregaLista() {

        // se não setar a listagem fica bugada, ainda não corrigiram isto
        //  listaLivros.setAdapter(null);

        listaLivros.setLayoutManager(new LinearLayoutManager(getContext()));
        listaLivros.setAdapter(new ListaLivrosAdapter(livros));
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }


    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);

    }
}
