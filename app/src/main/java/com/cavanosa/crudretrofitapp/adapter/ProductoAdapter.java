package com.cavanosa.crudretrofitapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cavanosa.crudretrofitapp.R;
import com.cavanosa.crudretrofitapp.model.Producto;

import java.util.List;

public class ProductoAdapter extends ArrayAdapter<Producto> {

    public ProductoAdapter(Context context, List<Producto> productos) {
        super(context, 0, productos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Producto producto = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_listar, parent, false);
        }

        TextView idTextView = convertView.findViewById(R.id.editTextId);
        TextView nombreTextView = convertView.findViewById(R.id.editTextNombre);
        TextView descripcionTextView = convertView.findViewById(R.id.editTextDescripcion);
        TextView precioTextView = convertView.findViewById(R.id.editTextPrecio);
        TextView estadoTextView = convertView.findViewById(R.id.editTextEstado);
        TextView idCategoriaTextView = convertView.findViewById(R.id.editTextIdCategoria);

        // Configura los TextView con los datos del producto
        idTextView.setText("ID: " + producto.getId());
        nombreTextView.setText("Nombre: " + producto.getNombre());
        descripcionTextView.setText("Descripción: " + producto.getDescripcion());
        precioTextView.setText("Precio: " + Double.valueOf(producto.getPrecio()));
        estadoTextView.setText("Estado: " + producto.getEstado());
        idCategoriaTextView.setText("ID de Categoría: " + producto.getCategoria().getId_categoria());

        return convertView;
    }
}