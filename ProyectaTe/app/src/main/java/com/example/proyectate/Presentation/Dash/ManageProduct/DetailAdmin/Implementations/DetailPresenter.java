package com.example.proyectate.Presentation.Dash.ManageProduct.DetailAdmin.Implementations;

import com.example.proyectate.DataAccess.DatabaseSQLite.Daos.ProjectDao;
import com.example.proyectate.Models.Project;
import com.example.proyectate.Presentation.Dash.ManageProduct.DetailAdmin.Interfaces.IDetailPresenter;
import com.example.proyectate.Presentation.Dash.ManageProduct.DetailAdmin.Interfaces.IDetailView;

public class DetailPresenter implements IDetailPresenter {
    private final IDetailView view;
    private final ProjectDao dao;

    public DetailPresenter(IDetailView view, ProjectDao dao) {
        this.view = view;
        this.dao = dao;
        dao.openDb();
    }

    @Override
    public void deleteProduct(Project product) {
        view.showDeleteProduct(product.deleteProject(dao), product.getTitle());
    }
}
