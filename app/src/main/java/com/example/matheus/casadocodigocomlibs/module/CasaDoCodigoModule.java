package com.example.matheus.casadocodigocomlibs.module;

import com.example.matheus.casadocodigocomlibs.application.CasaDoCodigoApplication;
import com.example.matheus.casadocodigocomlibs.model.Carrinho;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CasaDoCodigoModule {

    @Provides
    @Singleton
    public Carrinho getCarrinho() {
        return new Carrinho();
    }

}
