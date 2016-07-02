package com.example.matheus.casadocodigocomlibs.event;

import com.example.matheus.casadocodigocomlibs.model.Livro;

/**
 * Created by matheus on 01/07/16.
 */
public class LivroEvent {

    public final Livro livro;

    public LivroEvent(Livro livro) {
        this.livro = livro;
    }
}
