package com.example.matheus.casadocodigocomlibs.server;

import com.example.matheus.casadocodigocomlibs.model.Livro;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by matheus on 29/06/16.
 */
public interface ListaLivrosService {

    @GET("listarLivros?indicePrimeiroLivro=0&qtdLivros=20")
    Call<List<Livro>> listaLivros();
}
