package com.example.matheus.casadocodigocomlibs.converter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by matheus on 30/06/16.
 */
public class ItemServiceResponseConverter implements Converter<ResponseBody, String> {
    @Override
    public String convert(ResponseBody value) throws IOException {

        String string = value.string();

        return string;
    }
}
