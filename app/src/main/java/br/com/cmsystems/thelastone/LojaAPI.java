package br.com.cmsystems.thelastone;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Claudio on 31/05/2017.
 */

public interface LojaAPI {

    public final static String BASE_URL =
            "https://exemplo031.herokuapp.com/webapi/";

    @GET("Produto")Call<List<Produto>> getProdutos();

    @POST("Produto")Call<Boolean>inserirProduto(@Body Produto p);

    @PUT("Produto")Call<Boolean> atualizarProduto(@Body Produto p);

    @DELETE("Produto/{id}")Call<Boolean> deletarProduto(@Path("id") int id);
}
