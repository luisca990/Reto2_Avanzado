package com.example.proyectate.Presentation.Dash.Home.Implementations;

import android.content.Context;

import com.example.proyectate.DataAccess.DatabaseSQLite.Daos.ProjectDao;
import com.example.proyectate.DataAccess.SharedPreferences.SessionManager;
import com.example.proyectate.Models.Project;
import com.example.proyectate.Presentation.Dash.Home.Interfaces.IHomePresenter;
import com.example.proyectate.Presentation.Dash.Home.Interfaces.IHomeView;

public class HomePresenter implements IHomePresenter {
    private final IHomeView view;
    private final ProjectDao dao;
    private final Context context;

    public HomePresenter(IHomeView view, ProjectDao dao, Context context) {
        this.view = view;
        this.dao = dao;
        this.context = context;
        this.dao.openDb();
    }

    @Override
    public void getAllProductsSuccess() {
        SessionManager sessionManager = new SessionManager(context);
        view.showGetAllProductsSuccess(Project.getListProject(dao, sessionManager.getUseId()));
    }

}
