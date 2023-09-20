package com.cavanosa.crudretrofitapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cavanosa.crudretrofitapp.R;
import com.cavanosa.crudretrofitapp.interfaces.ProductoCRUD;
import com.cavanosa.crudretrofitapp.model.Categoria;
import com.cavanosa.crudretrofitapp.model.Producto;
import com.cavanosa.crudretrofitapp.responses.ProductoResponseImpl;
import com.cavanosa.crudretrofitapp.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNombre, editTextPrecio, editTextEstado, editTextIdCategoria, editTextDescripcion, idEditText;
    private Button btnGuardar, btnConsultar, btnActualizar, btnEliminar, btnListar;
    private ProductoCRUD productoCRUDInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextPrecio = findViewById(R.id.editTextPrecio);
        editTextEstado = findViewById(R.id.editTextEstado);
        editTextIdCategoria = findViewById(R.id.editTextIdCategoria);
        editTextDescripcion = findViewById(R.id.editTextDescripcion);
        idEditText = findViewById(R.id.editTextID);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnConsultar = findViewById(R.id.btnConsultar);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnListar = findViewById(R.id.btnListar);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        productoCRUDInterface = retrofit.create(ProductoCRUD.class);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarProducto();
            }
        });

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultarProductoPorNombre();
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarProducto();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarProducto();
            }
        });

        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para iniciar ListarActivity
                Intent intent = new Intent(MainActivity.this, ListarActivity.class);
                // Iniciar la actividad ListarActivity
                startActivity(intent);
            }
        });
    }

    private void guardarProducto() {
        String nombre = editTextNombre.getText().toString();
        String descripcion = editTextDescripcion.getText().toString();
        String precioStr = editTextPrecio.getText().toString();
        String estado = editTextEstado.getText().toString();

        if (nombre.isEmpty() || precioStr.isEmpty() || estado.isEmpty() || descripcion.isEmpty()) {
            // Algunos campos obligatorios están vacíos, muestra un mensaje de error
            Toast.makeText(MainActivity.this, "Completa todos los campos obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        double precio = Double.parseDouble(precioStr);

        String idCategoriaStr = editTextIdCategoria.getText().toString();

        // Verificar si se proporcionó un ID de categoría
        long idCategoria = 0; // Valor predeterminado
        if (!idCategoriaStr.isEmpty()) {
            idCategoria = Long.parseLong(idCategoriaStr);
        }

        // Crear un objeto Categoria con el ID proporcionado
        Categoria categoria = new Categoria();
        categoria.setId_categoria(idCategoria);

        // Crear un nuevo Producto y asignarle la Categoria
        Producto nuevoProducto = new Producto(nombre, precio, estado, categoria, descripcion);

        Call<Producto> call = productoCRUDInterface.crearProducto(nuevoProducto);
        call.enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(@NonNull Call<Producto> call, @NonNull Response<Producto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Producto creado", response.body().toString());
                    Toast.makeText(MainActivity.this, "Producto creado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("Error al crear producto", response.message());
                    Toast.makeText(MainActivity.this, "Error al crear producto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Producto> call, @NonNull Throwable t) {
                Log.e("Error en la solicitud", t.getMessage());
                Toast.makeText(MainActivity.this, "Error en la solicitud", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void consultarProductoPorNombre() {
        String nombreProducto = editTextNombre.getText().toString();

        Call<ProductoResponseImpl> call = productoCRUDInterface.consultarProductoPorNombre(nombreProducto);
        call.enqueue(new Callback<ProductoResponseImpl>() {
            @Override
            public void onResponse(@NonNull Call<ProductoResponseImpl> call, @NonNull Response<ProductoResponseImpl> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProductoResponseImpl producto = response.body();
                    idEditText.setText(String.valueOf(producto.getId()));
                    editTextNombre.setText(producto.getNombre());
                    editTextDescripcion.setText(producto.getDescripcion());
                    editTextPrecio.setText(String.valueOf(producto.getPrecio()));
                    editTextEstado.setText(producto.getEstado());
                    editTextIdCategoria.setText(String.valueOf(producto.getCategoria().getId_categoria()));
                } else {
                    Log.e("Error al consultar", response.message());
                    Toast.makeText(MainActivity.this, "Error al consultar producto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductoResponseImpl> call, @NonNull Throwable t) {
                Log.e("Error en la solicitud", t.getMessage());
                Toast.makeText(MainActivity.this, "Error en la solicitud", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void actualizarProducto() {
        String idStr = idEditText.getText().toString();
        if (idStr.isEmpty()) {
            // El campo de ID está vacío, muestra un mensaje de error
            Toast.makeText(MainActivity.this, "Ingresa un ID válido para actualizar el producto", Toast.LENGTH_SHORT).show();
            return;
        }

        long id = Long.parseLong(idStr);
        String nombre = editTextNombre.getText().toString();
        String descripcion = editTextDescripcion.getText().toString();
        double precio = Double.parseDouble(editTextPrecio.getText().toString());
        String estado = editTextEstado.getText().toString();
        String idCategoriaStr = editTextIdCategoria.getText().toString();

        if (idCategoriaStr.isEmpty()) {
            // El campo de ID de categoría está vacío, muestra un mensaje de error
            Toast.makeText(MainActivity.this, "Ingresa un ID de categoría válido", Toast.LENGTH_SHORT).show();
            return;
        }

        long idCategoria = Long.parseLong(idCategoriaStr);

        // Crear un objeto Categoria con el ID proporcionado
        Categoria categoria = new Categoria();
        categoria.setId_categoria(idCategoria);

        // Crear un nuevo Producto y asignarle la Categoria
        Producto productoActualizado = new Producto(id, nombre, descripcion, precio, estado, categoria);

        Call<Producto> call = productoCRUDInterface.actualizarProducto(id, productoActualizado);
        call.enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(@NonNull Call<Producto> call, @NonNull Response<Producto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Producto actualizado", response.body().toString());
                    Toast.makeText(MainActivity.this, "Producto actualizado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("Error al actualizar", response.message());
                    Toast.makeText(MainActivity.this, "Error al actualizar producto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Producto> call, @NonNull Throwable t) {
                Log.e("Error en la solicitud", t.getMessage());
                Toast.makeText(MainActivity.this, "Error en la solicitud", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void eliminarProducto() {
        String idStr = idEditText.getText().toString();

        if (!idStr.isEmpty()) {
            long id = Long.parseLong(idStr);

            Call<Void> call = productoCRUDInterface.eliminarProducto(id);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.d("Producto eliminado", "Producto eliminado correctamente");
                        Toast.makeText(MainActivity.this, "Producto eliminado correctamente", Toast.LENGTH_SHORT).show();
                        // Limpiar campos después de eliminar
                        editTextNombre.setText("");
                        editTextPrecio.setText("");
                        editTextEstado.setText("");
                        editTextIdCategoria.setText("");
                        editTextDescripcion.setText("");
                        idEditText.setText("");
                    } else {
                        Log.e("Error al eliminar", response.message());
                        Toast.makeText(MainActivity.this, "Error al eliminar producto", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    Log.e("Error en la solicitud", t.getMessage());
                    Toast.makeText(MainActivity.this, "Error en la solicitud", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Manejo de caso cuando el campo de ID está vacío
            Toast.makeText(MainActivity.this, "Ingresa un ID válido para eliminar el producto", Toast.LENGTH_SHORT).show();
        }
    }
}