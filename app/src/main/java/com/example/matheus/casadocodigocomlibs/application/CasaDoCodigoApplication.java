package com.example.matheus.casadocodigocomlibs.application;

import android.app.Application;

import com.example.matheus.casadocodigocomlibs.model.Carrinho;
import com.example.matheus.casadocodigocomlibs.model.Item;

import java.util.List;

/**
 * Created by matheus on 30/06/16.
 */
public class CasaDoCodigoApplication extends Application {


    private Carrinho carrinho = new Carrinho();


    public void adicionaNaLista(Item item) {
        carrinho.adicionar(item);
    }

    public void removeDaLista(Item item) {
        carrinho.remove(item);
    }

    public List<Item> itensComprados() {
        return carrinho.pegaListaItens();
    }

    public void limpaLista() {
        carrinho.limpaLista(itensComprados());
    }
}
