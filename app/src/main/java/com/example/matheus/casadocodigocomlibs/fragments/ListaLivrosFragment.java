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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by matheus on 29/06/16.
 */
public class ListaLivrosFragment extends Fragment {

    @BindView(R.id.lista_livros)
    RecyclerView listaLivros;
    private List<Livro> livros = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.lista_livros_fragment, container, false);
        ButterKnife.bind(this, view);

        return view;
    }


    @Subscribe
    public void recebeLista(ListaEvent event) {

        this.livros = event.livros;

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

        listaLivros.setAdapter(new ListaLivrosAdapter(livros, (LivroDelegate) getActivity()));
        listaLivros.setLayoutManager(new LinearLayoutManager(getContext()));
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
