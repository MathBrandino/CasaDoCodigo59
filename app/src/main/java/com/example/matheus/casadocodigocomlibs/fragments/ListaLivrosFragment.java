package com.example.matheus.casadocodigocomlibs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.matheus.casadocodigocomlibs.R;
import com.example.matheus.casadocodigocomlibs.adapter.ListaLivrosAdapter;
import com.example.matheus.casadocodigocomlibs.endlesslist.EndlessList;
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
    private LinearLayoutManager manager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            Toast.makeText(getContext(), "Reaproveitei", Toast.LENGTH_LONG).show();
            livros = (ArrayList<Livro>) savedInstanceState.getSerializable("livros");
        } else {
            Toast.makeText(getContext(), "Request novo", Toast.LENGTH_LONG).show();

            new WebClient().retornaLivroDoServidor(0, 10);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.lista_livros_fragment, container, false);
        ButterKnife.bind(this, view);


        manager = new LinearLayoutManager(getContext());

        listaLivros.addOnScrollListener(new EndlessList(manager) {
            @Override
            public void carregaMaisItens() {
                Snackbar.make(listaLivros, "Carregando mais itens", Snackbar.LENGTH_SHORT).show();
                new WebClient().retornaLivroDoServidor(livros.size(), 10);
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

        listaLivros.setLayoutManager(manager);
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
