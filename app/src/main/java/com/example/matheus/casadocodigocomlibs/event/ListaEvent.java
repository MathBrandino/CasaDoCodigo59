package com.example.matheus.casadocodigocomlibs.event;

import com.example.matheus.casadocodigocomlibs.model.Livro;

import java.util.List;

/**
 * Created by matheus on 29/06/16.
 */
public class ListaEvent {

    public final List<Livro> livros;


    public ListaEvent(List<Livro> livros) {
        this.livros = livros;
    }
}
