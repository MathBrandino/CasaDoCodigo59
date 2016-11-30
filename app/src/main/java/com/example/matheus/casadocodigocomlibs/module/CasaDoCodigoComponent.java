package com.example.matheus.casadocodigocomlibs.module;

import com.example.matheus.casadocodigocomlibs.activity.CarrinhoActivity;
import com.example.matheus.casadocodigocomlibs.activity.LoginActivity;
import com.example.matheus.casadocodigocomlibs.activity.NovoUserActivity;
import com.example.matheus.casadocodigocomlibs.fragments.DetalheLivroFragment;
import com.example.matheus.casadocodigocomlibs.fragments.ListaLivrosFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = CasaDoCodigoModule.class)
public interface CasaDoCodigoComponent {
    void inject(ListaLivrosFragment fragment);

    void inject(DetalheLivroFragment fragment);

    void inject(CarrinhoActivity activity);

    void inject(NovoUserActivity activity);
}
