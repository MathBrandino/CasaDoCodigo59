package com.example.matheus.casadocodigocomlibs.server;

import com.example.matheus.casadocodigocomlibs.model.Livro;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by matheus on 29/06/16.
 */
public interface ListaLivrosService {

    @GET("listarLivros")
    Call<List<Livro>> listaLivros(@Query("indicePrimeiroLivro") int indice, @Query("qtdLivros") int qtd);

}
