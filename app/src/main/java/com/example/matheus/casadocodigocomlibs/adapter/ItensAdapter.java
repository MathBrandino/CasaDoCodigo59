package com.example.matheus.casadocodigocomlibs.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.matheus.casadocodigocomlibs.R;
import com.example.matheus.casadocodigocomlibs.model.Item;
import com.example.matheus.casadocodigocomlibs.model.TipoDeCompra;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by matheus on 30/06/16.
 */
public class ItensAdapter extends RecyclerView.Adapter {


    private List<Item> items;
    private Context context;

    public ItensAdapter(List<Item> items, Context context) {

        this.items = items;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.livro_comprado, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        ViewHolder holder = (ViewHolder) viewHolder;

        Item item = items.get(position);

        String valorComprado = retornaValorCompradoDo(item);

        holder.nomeItem.setText(item.getLivro().getNome());
        holder.valorComprado.setText(valorComprado);

        Picasso.with(context).load(item.getLivro().getImagemUrl()).placeholder(R.drawable.livro).fit().into(holder.fotoLivro);
    }

    private String retornaValorCompradoDo(Item item) {

        TipoDeCompra tipoDeCompra = item.getTipoDeCompra();

        switch (tipoDeCompra) {
            case FISICO:
                return "R$ " + item.getLivro().getValorFisico();

            case VIRTUAL:
                return "R$ " + item.getLivro().getValorVirtual();

            default:
                return "R$ " + item.getLivro().getValorDoisJuntos();

        }

    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.imagem_item_comprado)
        ImageView fotoLivro;

        @BindView(R.id.nome_item_comprado)
        TextView nomeItem;

        @BindView(R.id.valor_pago_item_comprado)
        TextView valorComprado;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
