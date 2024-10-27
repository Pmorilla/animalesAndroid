package com.example.alberguedeanimales.capaDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class AlbergueDBHelper extends SQLiteOpenHelper
{
    //Nombre y versión por defecto de la base de datos
    public static String DATABASE_NAME = "AlbergueDB";
    public static int DATABASE_VERSION = 1;

    //Constructor por defecto
    public AlbergueDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public AlbergueDBHelper(Context context, String nombre, CursorFactory factory, int version)
    {
        super(context, nombre, null, version);

        //Guardamos los datos
        DATABASE_NAME = nombre;
        DATABASE_VERSION = version;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //Creación de la base de datos. Ejecutamos el script de la tabla Usuarios y la tabla Animales
        db.execSQL(UsuarioDataSource.CREATE_USUARIOS_SCRIPT);
        db.execSQL(AnimalDataSource.CREATE_ANIMALES_SCRIPT);

        db.execSQL(AnimalDataSource.INSERT_ANIMALES_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //Al actualizar, volvemos a crear la base de datos.
        //Si existe, borramos la versión anterior de la tabla, y la volvemos a crear
        db.execSQL("DROP TABLE IF EXISTS " + UsuarioDataSource.USUARIOS_TABLE_NAME);
        db.execSQL(UsuarioDataSource.CREATE_USUARIOS_SCRIPT);

        db.execSQL("DROP TABLE IF EXISTS " + AnimalDataSource.ANIMALES_TABLE_NAME);
        db.execSQL(AnimalDataSource.CREATE_ANIMALES_SCRIPT);

        DATABASE_VERSION = newVersion; //Guardamos la versión actual.
    }
}
