package com.example.matheus.casadocodigocomlibs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.matheus.casadocodigocomlibs.R;
import com.example.matheus.casadocodigocomlibs.application.CasaDoCodigoApplication;
import com.example.matheus.casadocodigocomlibs.event.AutorEvent;
import com.example.matheus.casadocodigocomlibs.event.LivroEvent;
import com.example.matheus.casadocodigocomlibs.fragments.AutorDetalheFragment;
import com.example.matheus.casadocodigocomlibs.fragments.DetalheLivroFragment;
import com.example.matheus.casadocodigocomlibs.fragments.ListaLivrosFragment;
import com.example.matheus.casadocodigocomlibs.model.Autor;
import com.example.matheus.casadocodigocomlibs.model.Livro;
import com.example.matheus.casadocodigocomlibs.module.CasaDoCodigoComponent;
import com.google.firebase.auth.FirebaseAuth;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            trocaFragment(new ListaLivrosFragment());

        }
    }

    private void trocaFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.main_frame, fragment);
        transaction.commit();
    }


    @Subscribe
    public void lidaComClick(AutorEvent event) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


        AutorDetalheFragment autoresDetalhes = criaDetalheCom(event.autores);
        transaction.replace(R.id.main_frame, autoresDetalhes);
        transaction.addToBackStack(null);

        transaction.commit();

    }

    private AutorDetalheFragment criaDetalheCom(ArrayList<Autor> autores) {

        AutorDetalheFragment detalheFragment = new AutorDetalheFragment();

        Bundle arguments = new Bundle();
        arguments.putSerializable("autores", autores);

        detalheFragment.setArguments(arguments);

        return detalheFragment;
    }

    @Subscribe
    public void lidaComClick(LivroEvent event) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        DetalheLivroFragment detalheLivroFragment = criaDetalheCom(event.livro);
        CasaDoCodigoApplication app = (CasaDoCodigoApplication) getApplication();
        CasaDoCodigoComponent component = app.getComponent();
        component.inject(detalheLivroFragment);

        transaction.replace(R.id.main_frame, detalheLivroFragment);
        transaction.addToBackStack(null);

        transaction.commit();

    }

    @NonNull
    private DetalheLivroFragment criaDetalheCom(Livro livro) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("livro", livro);
        DetalheLivroFragment detalheLivroFragment = new DetalheLivroFragment();
        detalheLivroFragment.setArguments(bundle);

        return detalheLivroFragment;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == R.id.vai_para_carrinho) {
            Intent vaiParaCarrinho = new Intent(this, CarrinhoActivity.class);
            startActivity(vaiParaCarrinho);
        }

        if (item.getItemId() == R.id.desloga) {
            FirebaseAuth.getInstance().signOut();

            finish();
        }


        if (android.R.id.home == item.getItemId()) {
            onBackPressed();
        }

        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        EventBus.getDefault().unregister(this);
    }


}
