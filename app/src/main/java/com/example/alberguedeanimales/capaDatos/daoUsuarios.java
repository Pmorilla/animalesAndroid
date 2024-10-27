package com.example.alberguedeanimales.capaDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.alberguedeanimales.capaEntidades.dtoUsuarios;
import com.example.alberguedeanimales.capaVistas.AnimalAdapter;
import com.example.alberguedeanimales.capaVistas.principalAnimal;

import java.util.ArrayList;
import java.util.List;

public class daoUsuarios
{

    private SQLiteDatabase db;

    public daoUsuarios(Context conexto)
    {
        // Instanciamos un objeto de SQLOpenHelper de la DB Votantes
        AlbergueDBHelper albergueH = new AlbergueDBHelper(conexto,"AlbergueDB",null,1);

        // Abrimos la base de datos
        this.db = albergueH.getWritableDatabase();

    }



    public boolean Altas(dtoUsuarios miUSU) throws Exception {
        try
        {
            //Creamos un objeto del tipo ContentValues, para añadir los campos.
            ContentValues registro = guardaValoresContenido(miUSU);


            //Lo metemos a la tabla
            long result = db.insert(UsuarioDataSource.USUARIOS_TABLE_NAME, null, registro);

            //Al finalizar, mostramos un mensaje y borramos los campos, si todo ha ido bien
            if (result != -1)
            {
                return true;
            }
            else
            {
                return false;
            }

        }
        catch(Exception ex)
        {
            throw new Exception(ex.getMessage());
        }

    }

    public boolean Modificaciones(dtoUsuarios miUsuario) throws Exception
    {
        String id;
        try {
            id = miUsuario.getEmail(); // Guardamos el ID del usuario

            // Creamos un objeto del tipo ContentValues para añadir los campos.
            ContentValues registro = guardaValoresContenido(miUsuario);

            // Cláusula where
            String selection = "email = ?";
            String[] selectionArgs = { id };

            // Actualizamos el usuario
            int numAfectados = db.update("Usuarios", registro, selection, selectionArgs);

            // Al finalizar, mostramos un mensaje y borramos los campos, si todo ha ido bien
            if (numAfectados > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {

            // Si hubiera algún error al actualizar
            throw new Exception(ex.getMessage());
        }
    }

    // Método para obtener todos los correos electrónicos de la base de datos
    public List<String> obtenerTodosLosCorreos()
    {
        List<String> correos = new ArrayList<>();

        // Definir la consulta SQL
        String query = "SELECT " + UsuarioDataSource.UsuariosColumnas.EMAIL + " FROM " + UsuarioDataSource.USUARIOS_TABLE_NAME;

        // Ejecutar la consulta y obtener un cursor
        Cursor cursor = db.rawQuery(query, null);

        // Iterar sobre el cursor para obtener los correos electrónicos
        if (cursor.moveToFirst())
        {
            do
            {
                String email = cursor.getString(0);
                correos.add(email);
            } while (cursor.moveToNext());
        }

        cursor.close();

        // Devolver la lista de correos electrónicos
        return correos;
    }

    // Método para obtener una lista de las contraseñas de todos los usuarios
    public boolean validarContrasena(String email, String contraseña)
    {
        // Definir la consulta SQL para obtener la contraseña asociada al correo electrónico dado
        String query = "SELECT " + UsuarioDataSource.UsuariosColumnas.CONTRASENA + " FROM " +
                UsuarioDataSource.USUARIOS_TABLE_NAME + " WHERE " + UsuarioDataSource.UsuariosColumnas.EMAIL + " = ?";

        // Ejecutar la consulta y obtener un cursor
        Cursor cursor = db.rawQuery(query, new String[]{email});

        // Verificar si se encontró un registro con el correo electrónico dado
        if (cursor.moveToFirst())
        {
            // Obtener la contraseña almacenada en la base de datos
            String contraseñaAlmacenada = cursor.getString(0);

            // Comparar la contraseña almacenada con la contraseña proporcionada
            if (contraseña.equals(contraseñaAlmacenada))
            {
                // La contraseña coincide
                return true;
            }
        }

        // Cerrar el cursor y devolver false si no se encontró coincidencia o si ocurrió un error
        cursor.close();

        //La contraseña no coincide
        return false;
    }

    public dtoUsuarios consultarUsuario(String email) {
        dtoUsuarios usuario = null;

        // Definir la consulta SQL
        String query = "SELECT * FROM " + UsuarioDataSource.USUARIOS_TABLE_NAME + " WHERE " +
                UsuarioDataSource.UsuariosColumnas.EMAIL + " = ?";

        // Ejecutar la consulta y obtener un cursor
        Cursor cursor = db.rawQuery(query, new String[]{email});

        // Verificar si el cursor contiene algún resultado
        if (cursor.moveToFirst()) {
            // Crear un objeto dtoUsuarios con los datos del cursor
            usuario = new dtoUsuarios(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            );
        }

        // Cerrar el cursor y la conexión a la base de datos
        cursor.close();

        // Devolver el objeto dtoUsuarios
        return usuario;
    }


    private ContentValues guardaValoresContenido(dtoUsuarios usuario)
    {
        // Creamos un objeto del tipo ContentValues, para añadir los campos.
        ContentValues nuevoRegistro = new ContentValues();

        nuevoRegistro.put(UsuarioDataSource.UsuariosColumnas.EMAIL, usuario.getEmail());
        nuevoRegistro.put(UsuarioDataSource.UsuariosColumnas.CONTRASENA, usuario.getContrasena());
        nuevoRegistro.put(UsuarioDataSource.UsuariosColumnas.NOMBRE, usuario.getNombre());
        nuevoRegistro.put(UsuarioDataSource.UsuariosColumnas.DIRECCION, usuario.getDireccion());
        nuevoRegistro.put(UsuarioDataSource.UsuariosColumnas.ID_ANIMAL, usuario.getIdAnimal());

        return nuevoRegistro;

    }
}
