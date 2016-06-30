package com.example.matheus.casadocodigocomlibs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.matheus.casadocodigocomlibs.R;
import com.example.matheus.casadocodigocomlibs.adapter.ListaLivrosAdapter;
import com.example.matheus.casadocodigocomlibs.delegate.LivroDelegate;
import com.example.matheus.casadocodigocomlibs.event.ListaEvent;
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


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.lista_livros_fragment, container, false);
        ButterKnife.bind(this, view);

        if (savedInstanceState != null) {
            livros = (ArrayList<Livro>) savedInstanceState.getSerializable("livros");
        } else {
            new WebClient().retornaLivroDoServidor();

        }


        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("livros", livros);
    }

    @Subscribe
    public void recebeLista(ListaEvent event) {

        this.livros = (ArrayList<Livro>) event.livros;

        carregaLista();
    }


    @Override
    public void onResume() {
        super.onResume();

        carregaLista();

    }

    private void carregaLista() {

        // se não setar a listagem fica bugada, ainda não corrigiram isto
        listaLivros.setAdapter(null);

        listaLivros.setLayoutManager(new LinearLayoutManager(getContext()));
        listaLivros.setAdapter(new ListaLivrosAdapter(livros, (LivroDelegate) getActivity()));
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }


    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

    }
}
