package com.example.matheus.casadocodigocomlibs.delegate;

import android.content.Context;

import com.example.matheus.casadocodigocomlibs.model.Livro;

/**
 * Created by matheus on 29/06/16.
 */
public interface LivroDelegate {

    Context getContext();

    void lidaComClick(Livro livro);
}
