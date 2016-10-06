package com.example.matheus.casadocodigocomlibs.model;

/**
 * Created by matheus on 27/07/15.
 */
public class Item {

    private Livro livro;
    private TipoDeCompra tipoDeCompra;

    public Item(Livro livro, TipoDeCompra tipoDeCompra) {
        this.livro = livro;
        this.tipoDeCompra = tipoDeCompra;
    }

    public Livro getLivro() {
        return livro;
    }

    public TipoDeCompra getTipoDeCompra() {
        return tipoDeCompra;
    }

}
