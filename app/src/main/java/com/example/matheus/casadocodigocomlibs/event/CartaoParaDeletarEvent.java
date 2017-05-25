package com.example.matheus.casadocodigocomlibs.event;

import com.example.matheus.casadocodigocomlibs.model.Cartao;

/**
 * Created by matheusbrandino on 5/25/17.
 */

public class CartaoParaDeletarEvent {
    public Cartao cartao;
    public int position;

    public CartaoParaDeletarEvent(Cartao cartao, int position) {
        this.cartao = cartao;
        this.position = position;
    }
}
