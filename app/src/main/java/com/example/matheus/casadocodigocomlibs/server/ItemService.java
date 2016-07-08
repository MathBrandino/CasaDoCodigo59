package com.example.matheus.casadocodigocomlibs.server;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by matheus on 29/06/16.
 */
public interface ItemService {

    @POST("registrarCompra")
    Call<String> enviaItensComprados(@Body String json);
}
