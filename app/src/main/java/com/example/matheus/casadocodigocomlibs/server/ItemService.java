package com.example.matheus.casadocodigocomlibs.server;

import com.example.matheus.casadocodigocomlibs.model.Livro;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by matheus on 29/06/16.
 */
public interface ItemService {

    @POST("registrarCompra")
    Call<String> enviaItensComprados(@Body String json);
}
