package com.cavanosa.crudretrofitapp.model;

public class Categoria {

    private Long id_categoria;

    private String nombre;

    private String descripcion;

    private String estado;

    public Categoria() {
    }

    public Categoria(Long id_categoria) {
        this.id_categoria = id_categoria;
    }

    public Categoria(String nombre, String descripcion, String estado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public Long getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(Long id_categoria) {
        this.id_categoria = id_categoria;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id_categoria=" + id_categoria +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}