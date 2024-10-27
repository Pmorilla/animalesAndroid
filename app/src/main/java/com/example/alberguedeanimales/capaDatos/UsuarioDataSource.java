package com.example.alberguedeanimales.capaDatos;

public class UsuarioDataSource
{
    // Metainformación de la base de datos
    public static final String USUARIOS_TABLE_NAME = "Usuarios";
    public static final String STRING_TYPE = "text";
    public static final String BOOLEAN_TYPE = "boolean";

    // Campos de la tabla Usuarios
    public static class UsuariosColumnas {
        public static final String EMAIL = "email";
        public static final String CONTRASENA = "contrasena";
        public static final String NOMBRE = "nombre";
        public static final String DIRECCION = "direccion";
        public static final String ID_ANIMAL = "id_animal";
    }

    // Script de creación de la tabla Usuarios
    public static final String CREATE_USUARIOS_SCRIPT =
            "create table " + USUARIOS_TABLE_NAME + "(" +
                    UsuariosColumnas.EMAIL + " " + STRING_TYPE + " primary key," +
                    UsuariosColumnas.CONTRASENA + " " + STRING_TYPE + " not null," +
                    UsuariosColumnas.NOMBRE + " " + STRING_TYPE + " not null," +
                    UsuariosColumnas.DIRECCION + " " + STRING_TYPE + " not null," +
                    UsuariosColumnas.ID_ANIMAL + " " + STRING_TYPE + "," +
                    "foreign key(" + UsuariosColumnas.ID_ANIMAL + ") references " + AnimalDataSource.ANIMALES_TABLE_NAME + "(" + AnimalDataSource.AnimalesColumnas.ID_ANIMAL + "))";
}
