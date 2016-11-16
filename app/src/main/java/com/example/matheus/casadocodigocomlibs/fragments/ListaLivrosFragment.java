package com.example.matheus.casadocodigocomlibs.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.matheus.casadocodigocomlibs.R;
import com.example.matheus.casadocodigocomlibs.adapter.ListaLivrosAdapter;
import com.example.matheus.casadocodigocomlibs.adapter.ListaLivrosUmItemAdapter;
import com.example.matheus.casadocodigocomlibs.application.CasaDoCodigoApplication;
import com.example.matheus.casadocodigocomlibs.endlesslist.EndlessList;
import com.example.matheus.casadocodigocomlibs.event.ListaEvent;
import com.example.matheus.casadocodigocomlibs.infra.Infra;
import com.example.matheus.casadocodigocomlibs.model.Livro;
import com.example.matheus.casadocodigocomlibs.module.CasaDoCodigoComponent;
import com.example.matheus.casadocodigocomlibs.server.WebClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by matheus on 29/06/16.
 */
public class ListaLivrosFragment extends Fragment implements Serializable {

    @BindView(R.id.lista_livros)
    RecyclerView listaLivros;

    @BindView(R.id.include)
    View include;

    private ArrayList<Livro> livros = new ArrayList<>();

    private WebClient webClient;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    @Inject
    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CasaDoCodigoApplication app = (CasaDoCodigoApplication) getActivity().getApplication();
        CasaDoCodigoComponent component = app.getComponent();
        component.inject(this);

        criaEConfiguraRemoteConfig();

        if (savedInstanceState != null) {
            Toast.makeText(getContext(), "Reaproveitei", Toast.LENGTH_LONG).show();
            livros = (ArrayList<Livro>) savedInstanceState.getSerializable("livros");
        } else {
            Toast.makeText(getContext(), "Request novo", Toast.LENGTH_LONG).show();

            webClient.retornaLivroDoServidor(0, 10);
        }


    }

    private void criaEConfiguraRemoteConfig() {
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);

        mFirebaseRemoteConfig.fetch(15).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    mFirebaseRemoteConfig.activateFetched();
                }

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lista_livros_fragment, container, false);
        ButterKnife.bind(this, view);


        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        listaLivros.setLayoutManager(manager);

        listaLivros.addOnScrollListener(new EndlessList() {
            @Override
            public void carregaMaisItens() {
                Snackbar.make(listaLivros, "Carregando mais itens", Snackbar.LENGTH_SHORT).show();
                webClient.retornaLivroDoServidor(livros.size(), 10);
            }
        });

        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("livros", livros);
    }

    @Subscribe
    public void recebeLista(ListaEvent event) {

        this.livros.addAll(event.livros);
        listaLivros.getAdapter().notifyDataSetChanged();

        removeInclude();
    }


    @Override
    public void onResume() {
        super.onResume();
        removeInclude();

        carregaLista();
        Infra.removeBotaoVoltar((AppCompatActivity) getActivity());

    }

    private void removeInclude() {
        if (livros.size() > 0) {
            include.setVisibility(View.GONE);
        }
    }

    private void carregaLista() {

        if (mFirebaseRemoteConfig.getBoolean("list_type_single_item")) {
            listaLivros.setAdapter(new ListaLivrosUmItemAdapter(livros));
        } else {
            listaLivros.setAdapter(new ListaLivrosAdapter(livros));

        }

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }


    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}
