package com.cavanosa.crudretrofitapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cavanosa.crudretrofitapp.R;
import com.cavanosa.crudretrofitapp.adapter.ProductoAdapter;
import com.cavanosa.crudretrofitapp.interfaces.ProductoCRUD;
import com.cavanosa.crudretrofitapp.model.Producto;
import com.cavanosa.crudretrofitapp.utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListarActivity extends AppCompatActivity {

    private LinearLayout linearLayoutProductos;
    private ProductoCRUD productoCRUDInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        linearLayoutProductos = findViewById(R.id.linearLayoutProductos);

        // Configura Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        productoCRUDInterface = retrofit.create(ProductoCRUD.class);

        // Obtén y muestra la lista de productos ordenada por ID
        obtenerListaDeProductosOrdenadaPorId();
    }

    private void obtenerListaDeProductosOrdenadaPorId() {
        Call<List<Producto>> call = productoCRUDInterface.getAllProductos();
        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // La respuesta exitosa contiene la lista de productos
                    List<Producto> productos = response.body();

                    // Ordena la lista de productos por ID
                    Collections.sort(productos, (p1, p2) -> Long.compare(p1.getId(), p2.getId()));

                    // Limpia el contenido actual del LinearLayout
                    linearLayoutProductos.removeAllViews();

                    // Recorre la lista de productos y agrega vistas al LinearLayout
                    for (Producto producto : productos) {
                        // Crea una vista para mostrar los atributos del producto
                        TextView textViewId = new TextView(ListarActivity.this);
                        TextView textViewNombre = new TextView(ListarActivity.this);
                        TextView textViewDescripcion = new TextView(ListarActivity.this);
                        TextView textViewPrecio = new TextView(ListarActivity.this);
                        TextView textViewEstado = new TextView(ListarActivity.this);
                        TextView textViewIdCategoria = new TextView(ListarActivity.this);

                        // Configura el contenido de las vistas
                        textViewId.setText("Id: " + producto.getId());
                        textViewNombre.setText("Nombre: " + producto.getNombre());
                        textViewDescripcion.setText("Descripción: " + producto.getDescripcion());
                        textViewPrecio.setText("Precio: " + producto.getPrecio());
                        textViewEstado.setText("Estado: " + producto.getEstado());

                        // Verifica si la categoría es nula antes de acceder a sus métodos
                        if (producto.getCategoria() != null) {
                            textViewIdCategoria.setText("Id de Categoría: " + producto.getCategoria().getId_categoria());
                        } else {
                            textViewIdCategoria.setText("Categoría no disponible");
                        }

                        // Agrega las vistas al LinearLayout
                        linearLayoutProductos.addView(textViewId);
                        linearLayoutProductos.addView(textViewNombre);
                        linearLayoutProductos.addView(textViewDescripcion);
                        linearLayoutProductos.addView(textViewPrecio);
                        linearLayoutProductos.addView(textViewEstado);
                        linearLayoutProductos.addView(textViewIdCategoria);

                        // Agrega un espacio (View en blanco) entre cada conjunto de datos del producto
                        View espacioView = new View(ListarActivity.this);
                        espacioView.setLayoutParams(new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                getResources().getDimensionPixelSize(R.dimen.espacio_entre_productos) // Define esta dimensión en tu archivo de recursos
                        ));
                        linearLayoutProductos.addView(espacioView);
                    }
                } else {
                    // Maneja errores si la respuesta no fue exitosa
                    Toast.makeText(ListarActivity.this, "Error al obtener la lista de productos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                // Maneja errores de solicitud
                Toast.makeText(ListarActivity.this, "Error en la solicitud", Toast.LENGTH_SHORT).show();
            }
        });
    }
}