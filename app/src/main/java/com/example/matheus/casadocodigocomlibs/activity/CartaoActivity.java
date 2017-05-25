package com.example.matheus.casadocodigocomlibs.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.matheus.casadocodigocomlibs.R;
import com.example.matheus.casadocodigocomlibs.event.CartaoParaEdicaoEvent;
import com.example.matheus.casadocodigocomlibs.event.NovoCartaoEvent;
import com.example.matheus.casadocodigocomlibs.fragments.FormularioCartaoFragment;
import com.example.matheus.casadocodigocomlibs.fragments.ListaCartoesFragment;
import com.example.matheus.casadocodigocomlibs.model.Cartao;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

/**
 * Created by matheusbrandino on 5/24/17.
 */

public class CartaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartao);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        colocaListagemDeCartoes();
    }

    private void colocaListagemDeCartoes() {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.cartao_frame, new ListaCartoesFragment());

        transaction.commit();

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void alteraTelaPara(NovoCartaoEvent event) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.cartao_frame, new FormularioCartaoFragment());

        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Subscribe
    public void vaiParaAlteracao(CartaoParaEdicaoEvent event) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        FormularioCartaoFragment formulario = getFormularioCartaoFragment(event.cartao);

        transaction.replace(R.id.cartao_frame, formulario);

        transaction.addToBackStack(null);
        transaction.commit();


    }


    @NonNull
    private FormularioCartaoFragment getFormularioCartaoFragment(Cartao cartao) {
        FormularioCartaoFragment formulario = new FormularioCartaoFragment();

        Bundle arguments = new Bundle();

        arguments.putSerializable("cartao", cartao);

        formulario.setArguments(arguments);
        return formulario;
    }
}
