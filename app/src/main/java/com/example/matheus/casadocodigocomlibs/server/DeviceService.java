package com.example.matheus.casadocodigocomlibs.server;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by matheus on 30/11/16.
 */

public interface DeviceService {

    @GET("/device/register/{email}/{id}")
    Call<String> mandaTokenParaServer(@Path("email") String email, @Path("id") String id);
}
