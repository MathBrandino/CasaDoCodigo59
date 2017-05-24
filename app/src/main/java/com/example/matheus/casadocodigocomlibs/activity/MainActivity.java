package com.example.matheus.casadocodigocomlibs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.matheus.casadocodigocomlibs.R;
import com.example.matheus.casadocodigocomlibs.application.CasaDoCodigoApplication;
import com.example.matheus.casadocodigocomlibs.event.AutorEvent;
import com.example.matheus.casadocodigocomlibs.event.LivroEvent;
import com.example.matheus.casadocodigocomlibs.event.NotificacaoEvent;
import com.example.matheus.casadocodigocomlibs.event.SignOutEvent;
import com.example.matheus.casadocodigocomlibs.fragments.AutorDetalheFragment;
import com.example.matheus.casadocodigocomlibs.fragments.DetalheLivroFragment;
import com.example.matheus.casadocodigocomlibs.fragments.ListaLivrosFragment;
import com.example.matheus.casadocodigocomlibs.model.Autor;
import com.example.matheus.casadocodigocomlibs.model.Livro;
import com.example.matheus.casadocodigocomlibs.module.CasaDoCodigoComponent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    private String TAG = "CasaDoCodigoAppMain";
    private FirebaseAuth.AuthStateListener listener;

    @BindView(R.id.main_frame)
    FrameLayout frameLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {

                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    firebaseAuth.removeAuthStateListener(this);
                    EventBus.getDefault().post(new SignOutEvent());

                }
            }
        };

        FirebaseAuth.getInstance().addAuthStateListener(listener);

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
            return true;
        }

        if (android.R.id.home == item.getItemId()) {
            onBackPressed();
        }

        if (item.getItemId() == R.id.cartao) {
            startActivity(new Intent(this, CartaoActivity.class));
        }

        return true;
    }

    @Subscribe
    public void signOut(SignOutEvent event) {
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }


    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseAuth.getInstance().removeAuthStateListener(listener);
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void recebeNotificacao(NotificacaoEvent event){

        Snackbar.make(frameLayout, "Recebeu uma notificação do servidor", Snackbar.LENGTH_SHORT).show();

    }
}
