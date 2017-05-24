package com.example.matheus.casadocodigocomlibs.event;

import com.example.matheus.casadocodigocomlibs.model.Cartao;

/**
 * Created by matheusbrandino on 5/24/17.
 */

public class CartaoParaEdicaoEvent {
    public Cartao cartao;

    public CartaoParaEdicaoEvent(Cartao cartao) {
        this.cartao = cartao;
    }
}

