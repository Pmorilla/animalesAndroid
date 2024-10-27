package com.example.alberguedeanimales.capaDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.alberguedeanimales.capaEntidades.dtoAnimales;
import com.example.alberguedeanimales.capaVistas.AnimalAdapter;

import java.util.ArrayList;
import java.util.List;

public class daoAnimales
{
    private SQLiteDatabase db;

    public daoAnimales(Context contexto)
    {
        // Instanciamos un objeto de SQLOpenHelper de la DB Albergue
        AlbergueDBHelper albergueH = new AlbergueDBHelper(contexto,"AlbergueDB",null,1);

        // Abrimos la base de datos
        this.db = albergueH.getWritableDatabase();

    }



    public dtoAnimales consultarAnimal(String idAnimal) {
        dtoAnimales animal = null;

        // Definir la consulta SQL
        String query = "SELECT * FROM " + AnimalDataSource.ANIMALES_TABLE_NAME + " WHERE " +
                AnimalDataSource.AnimalesColumnas.ID_ANIMAL + " = ?";

        // Ejecutar la consulta y obtener un cursor
        Cursor cursor = db.rawQuery(query, new String[]{idAnimal});

        // Verificar si el cursor contiene algún resultado
        if (cursor.moveToFirst()) {
            // Crear un objeto dtoAnimales con los datos del cursor
            animal = new dtoAnimales(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4) > 0,
                    cursor.getInt(5)
            );
        }

        // Cerrar el cursor
        cursor.close();

        // Devolver el objeto dtoAnimales
        return animal;
    }

    // Método para eliminar un animal de la base de datos
    public boolean eliminarAnimal(String idAnimal) {
        try {
            // Define la cláusula WHERE
            String whereClause = AnimalDataSource.AnimalesColumnas.ID_ANIMAL + " = ?";
            // Define los argumentos para la cláusula WHERE
            String[] whereArgs = {idAnimal};

            // Elimina el animal de la tabla
            int eliminados = db.delete(AnimalDataSource.ANIMALES_TABLE_NAME, whereClause, whereArgs);

            // Verifica si el animal fue eliminado exitosamente
            return eliminados > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Hubo un error al intentar eliminar el animal
        }
    }

    // Método para actualizar la información de un animal en la base de datos
    // Método para actualizar el estado de adopción de un animal en la base de datos
    public boolean actualizarAdopcion(String idAnimal, boolean adoptado) {
        try {
            // Crea un objeto ContentValues para almacenar el valor de adopción actualizado
            ContentValues values = new ContentValues();
            values.put(AnimalDataSource.AnimalesColumnas.ADOPTADO, adoptado ? 1 : 0);

            // Define la cláusula WHERE
            String whereClause = AnimalDataSource.AnimalesColumnas.ID_ANIMAL + " = ?";
            // Define los argumentos para la cláusula WHERE
            String[] whereArgs = {idAnimal};

            // Actualiza el estado de adopción del animal en la tabla
            int actualizados = db.update(AnimalDataSource.ANIMALES_TABLE_NAME, values, whereClause, whereArgs);

            // Verifica si el animal fue actualizado exitosamente
            return actualizados > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Hubo un error al intentar actualizar el estado de adopción del animal
        }
    }

    public List<dtoAnimales> obtenerTodosLosAnimalesNoAdoptados() {
        List<dtoAnimales> listaAnimales = new ArrayList<>();

        // Definir la consulta SQL que excluye los animales adoptados
        String query = "SELECT * FROM " + AnimalDataSource.ANIMALES_TABLE_NAME;

        // Ejecutar la consulta y obtener un cursor
        Cursor cursor = db.rawQuery(query, null);

        // Verificar si el cursor contiene resultados
        if (cursor.moveToFirst()) {
            do {
                // Crear un objeto dtoAnimales con los datos del cursor
                dtoAnimales animal = new dtoAnimales(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4) > 0,
                        cursor.getInt(5)
                );

                // Añadir el objeto a la lista
                listaAnimales.add(animal);
            } while (cursor.moveToNext());
        }

        // Cerrar el cursor
        cursor.close();

        // Devolver la lista de animales
        return listaAnimales;
    }


}
