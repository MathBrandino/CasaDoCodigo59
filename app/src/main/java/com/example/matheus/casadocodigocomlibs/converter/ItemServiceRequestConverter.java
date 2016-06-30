package com.example.matheus.casadocodigocomlibs.converter;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * Created by matheus on 30/06/16.
 */
public class ItemServiceRequestConverter implements Converter<String, RequestBody> {

    @Override
    public RequestBody convert(String json) throws IOException {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
    }
}
