package com.example.matheus.casadocodigocomlibs.server;

import android.util.Log;

import com.example.matheus.casadocodigocomlibs.event.ListaEvent;
import com.example.matheus.casadocodigocomlibs.event.RespostaEvent;
import com.example.matheus.casadocodigocomlibs.model.Livro;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by matheus on 29/06/16.
 */
public class WebClient {

    private ListaLivrosService listaLivrosService;
    private ItemService itemService;

    @Inject
    WebClient(ListaLivrosService listaLivrosService, ItemService itemService) {
        this.listaLivrosService = listaLivrosService;
        this.itemService = itemService;
    }

    public void retornaLivroDoServidor(int indice, int qtd) {

        Call<List<Livro>> call = listaLivrosService.listaLivros(indice, qtd);

        call.enqueue(new Callback<List<Livro>>() {
            @Override
            public void onResponse(Call<List<Livro>> call, Response<List<Livro>> response) {

                List<Livro> livros = response.body();

                EventBus.getDefault().post(new ListaEvent(livros));
            }

            @Override
            public void onFailure(Call<List<Livro>> call, Throwable t) {
                Log.d("Livros", "Deu ruim");

                EventBus.getDefault().post(t);
            }
        });
    }

    public void enviaItensParaServidor(String json) {
        Call<String> call = itemService.enviaItensComprados(json);

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
