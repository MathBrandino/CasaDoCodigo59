package com.example.matheus.casadocodigocomlibs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.matheus.casadocodigocomlibs.R;
import com.example.matheus.casadocodigocomlibs.application.CasaDoCodigoApplication;
import com.example.matheus.casadocodigocomlibs.model.Cartao;
import com.example.matheus.casadocodigocomlibs.model.CartaoDao;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by matheusbrandino on 5/24/17.
 */

public class FormularioCartaoFragment extends Fragment {

    private Cartao cartao = new Cartao();

    @BindView(R.id.formulario_nome)
    EditText nome;

    @BindView(R.id.formulario_vencimento)
    EditText vencimento;

    @BindView(R.id.formulario_numero)
    EditText numero;

    @BindView(R.id.formulario_cvv)
    EditText cvv;

    @Inject
    CartaoDao dao;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preparaActionBar();

        ((CasaDoCodigoApplication) getActivity().getApplication()).getComponent().inject(this);

    }

    private void preparaActionBar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("Cadastro Cartão");
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.formulario_cartao_fragment, container, false);
        ButterKnife.bind(this, view);


        Bundle arguments = getArguments();
        if (arguments != null) {
            cartao = (Cartao) arguments.getSerializable("cartao");
            populaCamposCom(cartao);
        }

        return view;
    }

    private void populaCamposCom(Cartao cartao) {
        nome.setText(cartao.getNomeCompleto());
        numero.setText(cartao.getNumero().toString());
        vencimento.setText(cartao.getValidade());
        cvv.setText(cartao.getCvv().toString());

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.formulario, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.salvar:

                cartao = geraCartaoDoFormulario();

                if (cartao.getId() == null) {
                    dao.save(cartao);
                } else {
                    dao.update(cartao);

                }


                getActivity().onBackPressed();

        }

        return super.

                onOptionsItemSelected(item);

    }

    private Cartao geraCartaoDoFormulario() {

        cartao.setCvv(Integer.parseInt(cvv.getText().toString()));
        cartao.setNomeCompleto(nome.getText().toString().trim());
        cartao.setNumero(Long.parseLong(numero.getText().toString()));
        cartao.setValidade(vencimento.getText().toString());

        return cartao;
    }
}
