package com.example.alberguedeanimales.capaDatos;

import com.example.alberguedeanimales.R;

public class AnimalDataSource
{
    // Metainformación de la base de datos
    public static final String ANIMALES_TABLE_NAME = "Animales";
    public static final String STRING_TYPE = "text";
    public static final String INT_TYPE = "integer";
    public static final String BOOLEAN_TYPE = "boolean";

    // Campos de la tabla Animales
    public static class AnimalesColumnas {
        public static final String ID_ANIMAL = "id_animal";
        public static final String NOMBRE = "nombre";
        public static final String ESPECIE = "especie";
        public static final String RAZA = "raza";
        public static final String ADOPTADO = "adoptado";
        public static final String IMAGEN = "imagen"; // Nuevo campo para el ID de la imagen
    }

    // Script de creación de la tabla Animales
    public static final String CREATE_ANIMALES_SCRIPT =
            "create table " + ANIMALES_TABLE_NAME + "(" +
                    AnimalesColumnas.ID_ANIMAL + " " + STRING_TYPE + " primary key," +
                    AnimalesColumnas.NOMBRE + " " + STRING_TYPE + " not null," +
                    AnimalesColumnas.ESPECIE + " " + STRING_TYPE + " not null," +
                    AnimalesColumnas.RAZA + " " + STRING_TYPE + " not null," +
                    AnimalesColumnas.ADOPTADO + " " + BOOLEAN_TYPE + " not null," +
                    AnimalesColumnas.IMAGEN + " " + INT_TYPE + " not null)";

    public static final String INSERT_ANIMALES_SCRIPT =
            "insert into " + ANIMALES_TABLE_NAME + "(" +
                    AnimalesColumnas.ID_ANIMAL + ", " +
                    AnimalesColumnas.NOMBRE + ", " +
                    AnimalesColumnas.ESPECIE + ", " +
                    AnimalesColumnas.RAZA + ", " +
                    AnimalesColumnas.ADOPTADO + ", " +
                    AnimalesColumnas.IMAGEN + ") values " +
                    "('1', 'Luna', 'Gato', 'Siames', 'false', " + R.drawable.gato_siames + "), " +
                    "('2', 'Copito', 'Gato', 'Persa', 'false', " + R.drawable.gato_persa + "), " +
                    "('3', 'DonGato', 'Gato', 'Comun Europeo', 'false', " + R.drawable.gato_europeo + "), " +
                    "('4', 'Toby', 'Perro', 'Pastor Alemán', 'false', " + R.drawable.perro_pastor_aleman + "), " +
                    "('5', 'Bachicha', 'Perro', 'Dachshund (Salchicha)', 'false', " + R.drawable.perro_salchicha_dachshund + ")";
}

