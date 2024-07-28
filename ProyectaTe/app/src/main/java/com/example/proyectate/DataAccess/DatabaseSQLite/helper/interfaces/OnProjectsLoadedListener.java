package com.example.proyectate.DataAccess.DatabaseSQLite.helper.interfaces;

import com.example.proyectate.Models.Project;

import java.util.List;

public interface OnProjectsLoadedListener {
    void onProjectsLoaded(List<Project> projects);
    void onError(Exception e);
}
