package com.example.matheus.casadocodigocomlibs.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.matheus.casadocodigocomlibs.R;
import com.example.matheus.casadocodigocomlibs.adapter.CartaoAdapter;
import com.example.matheus.casadocodigocomlibs.application.CasaDoCodigoApplication;
import com.example.matheus.casadocodigocomlibs.event.CartaoParaDeletarEvent;
import com.example.matheus.casadocodigocomlibs.event.NovoCartaoEvent;
import com.example.matheus.casadocodigocomlibs.model.Cartao;
import com.example.matheus.casadocodigocomlibs.model.CartaoDao;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by matheusbrandino on 5/24/17.
 */

public class ListaCartoesFragment extends Fragment {


    @Inject
    CartaoDao dao;

    @BindView(R.id.lista_cartoes)
    RecyclerView lista;
    List<Cartao> cartoes;

    CartaoAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((CasaDoCodigoApplication) getActivity().getApplication()).getComponent().inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        colocaNomeNaActionBar();

        cartoes = dao.loadAll();
        adapter = new CartaoAdapter(cartoes);
        lista.setLayoutManager(new LinearLayoutManager(getContext()));

        lista.setAdapter(adapter);

    }

    private void colocaNomeNaActionBar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("Cartões Cadastrados");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lista_cartoes_fragment, container, false);
        ButterKnife.bind(this, view);

        return view;

    }


    @Subscribe
    public void recebeCartaoParaDeletar(final CartaoParaDeletarEvent event) {

        new AlertDialog.Builder(getContext()).setMessage("Deseja mesmo deletar o cartão ?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cartoes.remove(event.cartao);
                        dao.delete(event.cartao);
                        adapter.notifyItemRemoved(event.position);
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.btn_add_cartao)
    public void addNovoCartao() {

        EventBus.getDefault().post(new NovoCartaoEvent());

    }

}
