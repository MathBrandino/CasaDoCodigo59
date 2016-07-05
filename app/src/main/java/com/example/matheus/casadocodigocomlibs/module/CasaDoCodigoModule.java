package com.example.matheus.casadocodigocomlibs.module;

import com.example.matheus.casadocodigocomlibs.application.CasaDoCodigoApplication;
import com.example.matheus.casadocodigocomlibs.converter.ItemServiceConverterFactory;
import com.example.matheus.casadocodigocomlibs.converter.LivroServiceConverterFactory;
import com.example.matheus.casadocodigocomlibs.fragments.ListaLivrosFragment;
import com.example.matheus.casadocodigocomlibs.model.Carrinho;
import com.example.matheus.casadocodigocomlibs.server.ItemService;
import com.example.matheus.casadocodigocomlibs.server.ListaLivrosService;
import com.example.matheus.casadocodigocomlibs.server.WebClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class CasaDoCodigoModule {
    public static final String SERVER = "http://cdcmob.herokuapp.com/";

    @Provides
    @Singleton
    public Carrinho getCarrinho() {
        return new Carrinho();
    }

    @Provides
    @Singleton
    public ListaLivrosService getListaLivrosService() {
        Retrofit client = new Retrofit.Builder()
                .baseUrl(SERVER)
                .addConverterFactory(new LivroServiceConverterFactory())
                .build();

        ListaLivrosService service = client.create(ListaLivrosService.class);
        return service;
    }

    @Provides
    @Singleton
    public ItemService getItemService() {
        Retrofit client = new Retrofit.Builder()
                .baseUrl(SERVER)
                .addConverterFactory(new ItemServiceConverterFactory())
                .build();

        ItemService service = client.create(ItemService.class);
        return service;
    }
}
