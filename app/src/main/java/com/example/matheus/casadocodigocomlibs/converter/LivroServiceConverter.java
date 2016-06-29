package com.example.matheus.casadocodigocomlibs.converter;

import com.example.matheus.casadocodigocomlibs.model.Livro;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by matheus on 29/06/16.
 */
public class LivroServiceConverter implements Converter<ResponseBody, List<Livro>> {

    @Override
    public List<Livro> convert(ResponseBody value) throws IOException {

        String json = value.string();
        LivroConverter livroConverter = new LivroConverter();

        List<Livro> livros = livroConverter.fromJson(json);

        return livros;
    }
}
