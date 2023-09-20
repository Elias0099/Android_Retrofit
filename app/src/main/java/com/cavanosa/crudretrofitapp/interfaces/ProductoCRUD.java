package com.cavanosa.crudretrofitapp.interfaces;

import com.cavanosa.crudretrofitapp.model.Producto;
import com.cavanosa.crudretrofitapp.responses.ProductoResponse;
import com.cavanosa.crudretrofitapp.responses.ProductoResponseImpl;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
public interface ProductoCRUD {

    @GET("productos")
    Call<List<Producto>> getAllProductos();

    @GET("productos/{id}")
    Call<Producto> getProductoById(@Path("id") long id);

    @POST("productos")
    Call<Producto> crearProducto(@Body Producto producto);

    @PUT("productos/{id}")
    Call<Producto> actualizarProducto(@Path("id") long id, @Body Producto producto);

    @DELETE("productos/{id}")
    Call<Void> eliminarProducto(@Path("id") long id);

    @GET("productos/all2/{nombre}")
    Call<ProductoResponseImpl> consultarProductoPorNombre(@Path("nombre") String nombre);



}
