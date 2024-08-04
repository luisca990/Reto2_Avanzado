package com.example.proyectate.Presentation.Dash.Home.Interfaces;

import com.example.proyectate.Models.Project;
import java.util.List;

public interface IHomePresenter {
    void getAllProjectsSuccess();
    void getProjectsFirebase();
    void synchronizeData(List<Project> list);
}
