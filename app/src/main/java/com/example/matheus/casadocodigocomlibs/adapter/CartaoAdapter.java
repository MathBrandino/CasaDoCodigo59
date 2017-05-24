package com.example.matheus.casadocodigocomlibs.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.matheus.casadocodigocomlibs.R;
import com.example.matheus.casadocodigocomlibs.event.CartaoParaEdicaoEvent;
import com.example.matheus.casadocodigocomlibs.model.Cartao;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by matheusbrandino on 5/24/17.
 */

public class CartaoAdapter extends RecyclerView.Adapter {
    private List<Cartao> cartoes;

    public CartaoAdapter(List<Cartao> cartoes) {
        this.cartoes = cartoes;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cartao, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Cartao cartao = cartoes.get(position);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.nome.setText(cartao.getNomeCompleto());
        holder.numero.setText(cartao.getNumero().toString());
        holder.validade.setText(cartao.getValidade());
        holder.cvv.setText(cartao.getCvv().toString());
    }

    @Override
    public int getItemCount() {
        return cartoes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.titular_cartao_item)
        TextView nome;

        @BindView(R.id.numero_cartao_item)
        TextView numero;

        @BindView(R.id.validade_cartao_item)
        TextView validade;

        @BindView(R.id.cvv_cartao_item)
        TextView cvv;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);


        }


        @OnClick(R.id.cartao_item)
        public void seleciona() {

            int position = getAdapterPosition();
            Cartao cartao = cartoes.get(position);

            EventBus.getDefault().post(new CartaoParaEdicaoEvent(cartao));


        }
    }

}
