package com.example.matheus.casadocodigocomlibs.endlesslist;

/**
 * Created by matheus on 04/07/16.
 */

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndlessList extends RecyclerView.OnScrollListener {

    int primeiroItemVisivel, quantidadeItensVisiveis, quantidadeTotalItens;
    private int totalAnterior = 0;
    private boolean carregando = true;
    private int itensVisiveisProntos = 1;
    private LinearLayoutManager layoutManager;


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        quantidadeItensVisiveis = recyclerView.getChildCount();
        quantidadeTotalItens = layoutManager.getItemCount();
        primeiroItemVisivel = layoutManager.findFirstVisibleItemPosition();

        if (carregando) {
            if (quantidadeTotalItens > totalAnterior) {
                carregando = false;
                totalAnterior = quantidadeTotalItens;
            }
        }
        if (!carregando && (quantidadeTotalItens - quantidadeItensVisiveis)
                <= (primeiroItemVisivel + itensVisiveisProntos)) {

            carregaMaisItens();

            carregando = true;
        }
    }

    public abstract void carregaMaisItens();
}