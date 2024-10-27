package com.example.alberguedeanimales.capaEntidades;

public class dtoAnimales {
    // Atributos
    private String id;
    private String nombre;
    private String especie;
    private String raza;
    private boolean adoptado;
    private int idImagen; // Nuevo campo para el ID de la imagen

    // Constructor
    public dtoAnimales(String id, String nombre, String especie, String raza, boolean adoptado, int idImagen) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.adoptado = adoptado;
        this.idImagen = idImagen;
    }

    public dtoAnimales()
    {

    }

    // Métodos getters y setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public boolean isAdoptado() {
        return adoptado;
    }

    public void setAdoptado(boolean adoptado) {
        this.adoptado = adoptado;
    }

    public int getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(int idImagen) {
        this.idImagen = idImagen;
    }
}


