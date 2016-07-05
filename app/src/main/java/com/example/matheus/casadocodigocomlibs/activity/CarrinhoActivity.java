package com.example.matheus.casadocodigocomlibs.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matheus.casadocodigocomlibs.R;
import com.example.matheus.casadocodigocomlibs.adapter.ItensAdapter;
import com.example.matheus.casadocodigocomlibs.application.CasaDoCodigoApplication;
import com.example.matheus.casadocodigocomlibs.converter.LivroConverter;
import com.example.matheus.casadocodigocomlibs.event.RespostaEvent;
import com.example.matheus.casadocodigocomlibs.model.Carrinho;
import com.example.matheus.casadocodigocomlibs.model.Item;
import com.example.matheus.casadocodigocomlibs.module.CasaDoCodigoComponent;
import com.example.matheus.casadocodigocomlibs.server.WebClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by matheus on 30/06/16.
 */
public class CarrinhoActivity extends AppCompatActivity {


    @BindView(R.id.lista_itens_carrinho)
    RecyclerView listaItens;

    @BindView(R.id.valor_carrinho)
    TextView valorTotal;

    private Carrinho carrinho;
    private WebClient webClient;

    @Inject
    void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    @Inject
    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CasaDoCodigoApplication application = (CasaDoCodigoApplication) getApplication();
        CasaDoCodigoComponent component = application.getComponent();
        component.inject(this);
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
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    public void carregaLista() {
        listaItens.setAdapter(new ItensAdapter(carrinho.pegaListaItens(), this));
        listaItens.setLayoutManager(new LinearLayoutManager(this));

        carregaValorTotal();
    }

    private void carregaValorTotal() {
        double total = 0;

        for (Item item : carrinho.pegaListaItens()) {
            total += devolveValorDo(item);
        }
        valorTotal.setText("R$ " + total);
    }

    private double devolveValorDo(Item item) {
        switch (item.getTipoDeCompra()) {
            case FISICO:
                return item.getLivro().getValorFisico();

            case VIRTUAL:
                return item.getLivro().getValorVirtual();

            default:
                return item.getLivro().getValorDoisJuntos();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return true;
    }

    @OnClick(R.id.fab_carrinho)
    public void clickFAB() {
        final TextInputLayout layout = new TextInputLayout(this);
        final EditText editText = new EditText(this);
        editText.setHint("Email");
        layout.addView(editText);

        new AlertDialog.Builder(this).setView(layout).setPositiveButton("Pronto", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String json = new LivroConverter().toJson(carrinho.pegaListaItens(), editText.getText().toString());
                webClient.enviaItensParaServidor(json);
            }
        }).show();
    }

    @Subscribe
    public void recebeRespostaDoServer(RespostaEvent event) {
        String body = event.body;

        Toast.makeText(this, body, Toast.LENGTH_LONG)
                .show();

        carrinho.limpaLista(carrinho.pegaListaItens());
        carregaLista();
    }

}
