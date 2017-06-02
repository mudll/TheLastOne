package br.com.cmsystems.trabalho031;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Claudio on 31/05/2017.
 */

public class ProdutoAdapter extends ArrayAdapter<Produto> {

    private int resourceId;

    public ProdutoAdapter(Context context, int resource, List<Produto>
            produtos) {
        super(context, resource, produtos);
        this.resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Produto mpProduto = getItem(position);

        if (convertView == null) {
            LayoutInflater mInflater = LayoutInflater.from(getContext());
            convertView = mInflater.inflate(this.resourceId, parent, false);
        }

        TextView txtNome = (TextView)
                convertView.findViewById(R.id.txtNome);
        TextView txtDescricao = (TextView)
                convertView.findViewById(R.id.txtDescricao);
        TextView txtValor = (TextView)
                convertView.findViewById(R.id.txtValorUnitario);

        txtNome.setText(mpProduto.getNome());
        txtDescricao.setText(mpProduto.getDescricao());
        txtValor.setText(String.valueOf(mpProduto.getValor()));
        return convertView;

    }
}


