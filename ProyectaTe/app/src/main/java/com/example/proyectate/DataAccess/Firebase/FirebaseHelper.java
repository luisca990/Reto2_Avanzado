package com.example.proyectate.DataAccess.Firebase;

import com.example.proyectate.DataAccess.Firebase.interfaces.OnProjectsLoadedListener;
import com.example.proyectate.DataAccess.Firebase.interfaces.onListenerCallback;
import com.example.proyectate.Models.Project;
import com.example.proyectate.Utils.Constants;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FirebaseHelper {
    private final FirebaseFirestore db;

    public FirebaseHelper() {
        db = FirebaseFirestore.getInstance();
    }

    // Método para sincronizar una lista de proyectos con Firestore
    public void syncProjects(List<Project> projects, onListenerCallback callback) {
        AtomicInteger counter = new AtomicInteger(0);
        for (Project project : projects) {
            checkProjectExists(project, true, new onListenerCallback() {
                @Override
                public void onSuccessChecked(boolean exists) {
                    if (!exists) {
                        addOrUpdateProject(project);
                    }
                    if (counter.incrementAndGet() == projects.size()) {
                        callback.onSuccessChecked(true);
                    }
                }

                @Override
                public void onError(Exception e) {
                    callback.onError(e);
                }
            });
        }
    }

    // Método para verificar si un proyecto existe
    public void checkProjectExists(Project project, Boolean isAdd, onListenerCallback callback) {
        db.collection(Constants.TABLE_PROJECTS)
                .whereEqualTo("title", project.getTitle())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        boolean exists = false;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document.exists()) {
                                exists = true;
                                deleteProjectById(project, isAdd);
                                break; // Si encuentras al menos uno, puedes detener la búsqueda
                            }
                        }
                        callback.onSuccessChecked(exists);
                    } else {
                        callback.onError(task.getException());
                    }
                });
    }

    // Método para agregar o actualizar un proyecto
    private void addOrUpdateProject(Project project) {
        db.collection(Constants.TABLE_PROJECTS)
                .document(String.valueOf(project.getId()))
                .set(project, SetOptions.merge()) // Usar merge para actualizar campos específicos
                .addOnSuccessListener(aVoid -> {
                    // Proyecto subido o actualizado correctamente
                })
                .addOnFailureListener(e -> {
                    // Error al subir o actualizar el proyecto
                });
    }

    // Método para agregar o actualizar un proyecto
    private void deleteProjectById(Project project, Boolean isAdd) {
        db.collection(Constants.TABLE_PROJECTS)
                .document(String.valueOf(project.getId()))
                .delete()
                .addOnSuccessListener(aVoid -> {
                    // Proyecto eliminado con éxito
                    if (isAdd) addOrUpdateProject(project);
                })
                .addOnFailureListener(e -> {
                    // Error al eliminar el proyecto
                });
    }

    public void getProjectsForUser(String userId, final OnProjectsLoadedListener listener) {
        db.collection(Constants.TABLE_PROJECTS)
                .whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Project> projects = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Project project = document.toObject(Project.class);
                            projects.add(project);
                        }
                        listener.onProjectsLoaded(projects);
                    } else {
                        listener.onError(task.getException());
                    }
                });
    }
}
