package com.example.matheus.casadocodigocomlibs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.matheus.casadocodigocomlibs.R;
import com.example.matheus.casadocodigocomlibs.adapter.AutoresAdapter;
import com.example.matheus.casadocodigocomlibs.infra.Infra;
import com.example.matheus.casadocodigocomlibs.model.Autor;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by matheus on 05/07/16.
 */

public class AutorDetalheFragment extends Fragment {

    @BindView(R.id.lista_autores)
    RecyclerView listaAutores;


    ArrayList<Autor> autores;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.autor_detalhe_fragment, container, false);
        ButterKnife.bind(this, view);


        autores = (ArrayList<Autor>) getArguments().getSerializable("autores");


        listaAutores.setLayoutManager(new LinearLayoutManager(getContext()));
        listaAutores.setAdapter(new AutoresAdapter(autores));

        return view;


    }

    @Override
    public void onResume() {
        super.onResume();
        Infra.colocaBotaoVoltar((AppCompatActivity) getActivity());
    }
}
