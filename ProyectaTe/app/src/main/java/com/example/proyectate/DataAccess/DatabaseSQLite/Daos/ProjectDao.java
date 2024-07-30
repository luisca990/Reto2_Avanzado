package com.example.proyectate.DataAccess.DatabaseSQLite.Daos;

import static com.example.proyectate.Utils.Constants.TABLE_PROJECTS;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.proyectate.DataAccess.DatabaseSQLite.helper.DatabaseHelper;
import com.example.proyectate.Models.Project;
import java.util.ArrayList;
import java.util.List;

public class ProjectDao {
    private SQLiteDatabase db; // Objeto para interactuar con la base de datos
    private final DatabaseHelper dbHelper; // Instancia de DatabaseHelper para crear y actualizar la base de datos

    // Constructor que recibe el contexto de la aplicación y crea una instancia de DatabaseHelper
    public ProjectDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Método para abrir la conexión con la base de datos en modo escritura
    public void openDb() {
        db = dbHelper.getWritableDatabase();
    }

    // Método para cerrar la conexión con la base de datos
    public void closeDb() {
        dbHelper.close();
    }

    public long insertProject(Project project){
        ContentValues values = new ContentValues(); // Objeto para almacenar los valores a insertar
        values.put("title", project.getTitle()); // Inserción del nombre del producto
        values.put("description", project.getDescription()); // Inserción del descripcion del producto
        values.put("date_init", project.getDateInit()); // Inserción del date inicial del producto
        values.put("date_end", project.getDateEnd()); // Inserción del date final del producto
        values.put("user_id", project.getUserId());  // Inserción del id del usuario
        values.put("imagen", project.getImage()); // Inserción del imagen del producto
        return db.insert(TABLE_PROJECTS, null, values);
    }
    public long updateProject(Project project){
        ContentValues values = new ContentValues(); // Objeto para almacenar los valores a actualizar
        values.put("title", project.getTitle()); // Actualización del nombre del producto
        values.put("description", project.getDescription()); // Actualización de la descripcion del producto
        values.put("date_init", project.getDateInit()); // Actualización del date inicial del producto
        values.put("date_end", project.getDateEnd()); // Actualización de la date final del producto
        values.put("user_id", project.getUserId());  // Actualización del id del usuario
        values.put("imagen", project.getImage()); // Actualización de la imagen del producto

        // Definición de la condición para la actualización (en este caso, el ID del producto)
        String selection = "id = ?";
        String[] selectionArgs = { String.valueOf(project.getId()) };

        // Realización de la actualización y obtención del número de filas afectadas
        int count = db.update(TABLE_PROJECTS, values, selection, selectionArgs);

        // Si la actualización fue exitosa, devolver el ID del producto
        if (count > 0) {
            return project.getId();
        } else {
            return -1; // Indica que la actualización no se realizó correctamente
        }
    }
    public boolean deleteProject(int projectId) {
        // Verificación del ID del producto
        if (projectId <= 0) {
            Log.e("Database", "ID del proyecto no válido.");
            return false;
        }

        // Definición de la condición para la eliminación (en este caso, el ID del producto)
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(projectId)};

        // Realización de la eliminación y obtención del número de filas afectadas
        int count = -1;
        try {
            count = db.delete(TABLE_PROJECTS, selection, selectionArgs);
        } catch (Exception e) {
            Log.e("Database", "Error al eliminar el proyecto: " + e.getMessage());
            return false; // Indica que la eliminación no se realizó correctamente
        }

        // Si la eliminación fue exitosa, devolver true
        if (count > 0) {
            Log.d("Database", "Proyecto eliminado con ID: " + projectId);
            return true;
        } else {
            Log.e("Database", "No se eliminó ninguna fila para el proyecto con ID: " + projectId);
            return false; // Indica que la eliminación no se realizó correctamente
        }
    }
    // Método para obtener todos los productos de la tabla 'productos'
    public List<Project> getListProjects(String userId) {
        List<Project> projects = new ArrayList<>();
        // Consulta SQL para seleccionar proyectos específicos por userId
        String query = "SELECT * FROM " + TABLE_PROJECTS + " WHERE user_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)}); // Ejecución de la consulta SQL con parámetro de selección

        // Iteración sobre los resultados del cursor para obtener los datos de cada proyecto
        if (cursor.moveToFirst()) {
            do {
                Project project = new Project(
                        cursor.getString(1), // Nombre del proyecto
                        cursor.getString(2), // Descripción del proyecto
                        cursor.getString(4), // Fecha de inicio (o cualquier otro campo que tengas)
                        cursor.getString(5)); // Fecha de fin (o cualquier otro campo que tengas)
                project.setId(cursor.getInt(0)); // ID del proyecto
                project.setUserId(cursor.getString(3)); // userId del proyecto
                project.setImage(cursor.getString(6)); // Imagen del proyecto
                projects.add(project);
            } while (cursor.moveToNext());
        }
        cursor.close(); // Cierre del cursor
        return projects;
    }
}
