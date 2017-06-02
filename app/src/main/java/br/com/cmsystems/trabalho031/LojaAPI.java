package br.com.cmsystems.trabalho031;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Claudio on 31/05/2017.
 */

public interface LojaAPI {

    public final static String BASE_URL =
            "https://exemplo031.herokuapp.com/webapi/";

    @GET("Produto")Call<List<Produto>> getProdutos();

    @POST("Produto")Call<Boolean>inserirProduto(@Body Produto p);



}
