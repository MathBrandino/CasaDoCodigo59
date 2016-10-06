package com.example.matheus.casadocodigocomlibs.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by matheus on 27/07/15.
 */
public class Carrinho implements Serializable {

    private List<Item> items;

    public Carrinho() {
        items = new ArrayList<>();
    }

    public void adicionar(Item item) {
        items.add(item);
    }

    public void remove(Item item) {
        items.remove(item);
    }

    public void limpaLista(List<Item> items) {
        items.removeAll(items);
    }

    public List<Item> pegaListaItens() {

        return Collections.unmodifiableList(items);
    }
}

