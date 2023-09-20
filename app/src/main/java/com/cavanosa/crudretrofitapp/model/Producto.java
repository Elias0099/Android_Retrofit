package com.cavanosa.crudretrofitapp.model;

public class Producto {

    private Long id;

    private String nombre;

    private String descripcion;

    private double precio;

    private String estado;

    private Categoria categoria;

    public Producto() {
    }

    public Producto(String nombre, double precio, String estado, Categoria categoria, String descripcion) {
        this.nombre = nombre;
        this.precio = precio;
        this.estado = estado;
        this.categoria = categoria;
        this.descripcion = descripcion;
    }

    public Producto(Long id, String nombre, String descripcion, double precio, String estado, Categoria categoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.estado = estado;
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", estado='" + estado + '\'' +
                ", categoria=" + categoria +
                '}';
    }
}