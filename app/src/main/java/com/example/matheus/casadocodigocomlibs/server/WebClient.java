package com.example.matheus.casadocodigocomlibs.server;

import android.util.Log;

import com.example.matheus.casadocodigocomlibs.converter.ItemServiceConverterFactory;
import com.example.matheus.casadocodigocomlibs.converter.LivroServiceConverterFactory;
import com.example.matheus.casadocodigocomlibs.event.ListaEvent;
import com.example.matheus.casadocodigocomlibs.event.RespostaEvent;
import com.example.matheus.casadocodigocomlibs.model.Livro;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by matheus on 29/06/16.
 */
public class WebClient {


    public static final String SERVER = "http://cdcmob.herokuapp.com/";

    public void retornaLivroDoServidor() {

        Retrofit client = new Retrofit.Builder()
                .baseUrl(SERVER)
                .addConverterFactory(new LivroServiceConverterFactory())
                .build();

        ListaLivrosService service = client.create(ListaLivrosService.class);

        Call<List<Livro>> call = service.listaLivros();

        call.enqueue(new Callback<List<Livro>>() {
            @Override
            public void onResponse(Call<List<Livro>> call, Response<List<Livro>> response) {

                List<Livro> livros = response.body();

                EventBus.getDefault().post(new ListaEvent(livros));
            }

            @Override
            public void onFailure(Call<List<Livro>> call, Throwable t) {
                Log.d("Livros", "Deu ruim");

            }
        });


    }

    public void enviaItensParaServidor(String json) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER)
                .addConverterFactory(new ItemServiceConverterFactory())
                .build();


        ListaLivrosService service = retrofit.create(ListaLivrosService.class);


        Call<String> call = service.enviaItensComprados(json);


        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                EventBus.getDefault().post(new RespostaEvent(response.body()));

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });


    }
}
