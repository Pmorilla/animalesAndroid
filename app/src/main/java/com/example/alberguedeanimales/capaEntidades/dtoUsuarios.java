package com.example.alberguedeanimales.capaEntidades;

public class dtoUsuarios
{
    // Atributos
    private String email;
    private String contrasena;
    private String nombre;
    private String direccion;
    private String idAnimal; // Se asume que este es el ID del animal como una foreign key

    // Constructor
    public dtoUsuarios(String email, String contrasena, String nombre, String direccion, String idAnimal) {
        this.email = email;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.direccion = direccion;
        this.idAnimal = idAnimal;
    }

    public dtoUsuarios()
    {}

    // Métodos getters y setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(String idAnimal) {
        this.idAnimal = idAnimal;
    }
}
