package br.com.cmsystems.trabalho031;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListarProdutoActivity extends AppCompatActivity {

    private ListView lstProdutos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_produto);

        lstProdutos = (ListView)findViewById(R.id.lstProdutos);

        listarProdutos();

    }
    public void listarProdutos(){

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(LojaAPI.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        LojaAPI api = retrofit.create(LojaAPI.class);

        api.getProdutos().enqueue(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                List<Produto> produtos = (List<Produto>)response.body();
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

}
