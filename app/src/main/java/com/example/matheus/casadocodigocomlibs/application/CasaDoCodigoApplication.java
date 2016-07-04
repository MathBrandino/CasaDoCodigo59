package com.example.matheus.casadocodigocomlibs.application;

import android.app.Application;

import com.example.matheus.casadocodigocomlibs.model.Carrinho;
import com.example.matheus.casadocodigocomlibs.model.Item;
import com.example.matheus.casadocodigocomlibs.module.CasaDoCodigoComponent;
import com.example.matheus.casadocodigocomlibs.module.CasaDoCodigoModule;
import com.example.matheus.casadocodigocomlibs.module.DaggerCasaDoCodigoComponent;

import java.util.List;

/**
 * Created by matheus on 30/06/16.
 */
public class CasaDoCodigoApplication extends Application {

    private Carrinho carrinho;

    private CasaDoCodigoComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        carrinho = new Carrinho();

        component = DaggerCasaDoCodigoComponent
                        .builder()
                        .casaDoCodigoModule(new CasaDoCodigoModule())
                        .build();
    }

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

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public CasaDoCodigoComponent getComponent() {
        return component;
    }
}
