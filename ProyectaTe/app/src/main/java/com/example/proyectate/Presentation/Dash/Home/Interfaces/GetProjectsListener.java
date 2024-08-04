package com.example.proyectate.Presentation.Dash.Home.Interfaces;

import com.example.proyectate.Models.Project;

import java.util.List;

public interface GetProjectsListener {
    void onListProjects(List<Project> projects);
    void onError(Exception e);
}
