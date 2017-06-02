package br.com.cmsystems.thelastone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListarProdutoActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener, View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView lstProdutos;
    private FloatingActionButton fab = null;
    private LocalBroadcastManager mLocalManager = null;
    private DataChangedReceiver mDataChangedReceiver = null;
    private String filter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_produto);

        lstProdutos = (ListView) findViewById(R.id.lstProdutos);
        listarProdutos();

        fab = (FloatingActionButton) findViewById(R.id.fabAddProduto);
        fab.setOnClickListener(this);

        mLocalManager = LocalBroadcastManager.getInstance(this);
        mDataChangedReceiver = new DataChangedReceiver();
        filter = getResources().getString(R.string.dados_atualizados);

        lstProdutos.setOnItemClickListener(this);
        lstProdutos.setOnItemLongClickListener(this);

    }

    public void listarProdutos() {

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(LojaAPI.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        LojaAPI api = retrofit.create(LojaAPI.class);

        api.getProdutos().enqueue(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                List<Produto> produtos = (List<Produto>) response.body();
                ProdutoAdapter mAdapter = new ProdutoAdapter(ListarProdutoActivity.this, R.layout.item_list_produto, produtos);
                lstProdutos.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<List<Produto>> call, Throwable t) {
                Log.i(ListarProdutoActivity.class.getCanonicalName(), "deu errado");
                Log.i(ListarProdutoActivity.class.getCanonicalName(), t.toString());
            }
        });

    }

    @Override
    public void onClick(View v) {
        Intent mIntent = new Intent(ListarProdutoActivity.this, GerenciarProdutoActivity.class);
        startActivity(mIntent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Produto p = (Produto) parent.getItemAtPosition(position);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LojaAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        LojaAPI api = retrofit.create(LojaAPI.class);

        api.deletarProduto(p.getId()).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean>
                    response) {
                mLocalManager.sendBroadcast(new Intent(filter));
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.i(ListarProdutoActivity.class.getCanonicalName(),
                        t.toString());
            }
        });
        return true;
    }


    private class DataChangedReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ListarProdutoActivity.this.listarProdutos();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        mLocalManager.registerReceiver(
                new DataChangedReceiver(),
                new IntentFilter(filter));
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocalManager.unregisterReceiver(
                mDataChangedReceiver);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int
            position, long id) {
        Produto mProduto = (Produto)
                parent.getItemAtPosition(position);
        Intent mIntent = new Intent(
                this,
                GerenciarProdutoActivity.class);
        mIntent.putExtra("produto", mProduto);
        startActivity(mIntent);
    }
}
