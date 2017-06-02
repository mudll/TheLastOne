package br.com.cmsystems.trabalho031;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Claudio on 31/05/2017.
 */

public class GerenciarProdutoActivity extends AppCompatActivity {

    private TextView txtNome = null;
    private TextView txtDescricao = null;
    private TextView txtValorUnitario = null;
    private Produto mProduto;
    private LocalBroadcastManager mLocalManager = null;
    private String filter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_produto);
        txtNome = (TextView) findViewById(R.id.txtNome);
        txtDescricao = (TextView) findViewById(R.id.txtDescricao);
        txtValorUnitario = (TextView) findViewById(R.id.txtValorUnitario);
        Bundle dados = getIntent().getExtras();
        if (dados != null) {
            mProduto = (Produto) dados.getSerializable("produto");
            txtNome.setText(mProduto.getNome());
            txtDescricao.setText(mProduto.getDescricao());
            txtValorUnitario.setText(String.valueOf(mProduto.getValor()));
        }
        filter = getResources().getString(R.string.dados_atualizados);
        mLocalManager = LocalBroadcastManager.getInstance(this);
    }

    public void onBtnInserirClick(View v) {
        String nome = txtNome.getText().toString();
        String descricao = txtDescricao.getText().toString();
        String valorUnitarioStr = txtValorUnitario.getText().toString();
        float valorUnitario = Float.valueOf(valorUnitarioStr);
        if (mProduto == null) {
            mProduto = new Produto();
        }
        mProduto.setNome(nome);
        mProduto.setDescricao(descricao);
        mProduto.setValor(valorUnitario);
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LojaAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        LojaAPI api = retrofit.create(LojaAPI.class);
        if (mProduto.getId() == 0) {
            api.inserirProduto(mProduto).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    mLocalManager.sendBroadcast(new Intent(filter));
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Log.i(GerenciarProdutoActivity.class.getCanonicalName(), t.toString());
                }
            });
        }
        finish();
    }
}
