package com.example.matheus.casadocodigocomlibs.module;

import com.example.matheus.casadocodigocomlibs.activity.CarrinhoActivity;
import com.example.matheus.casadocodigocomlibs.fragments.DetalheLivroFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = CasaDoCodigoModule.class)
public interface CasaDoCodigoComponent {
    void inject(DetalheLivroFragment fragment);
    void inject(CarrinhoActivity activity);
}
