package com.example.proyectate.Presentation.Dash.ManageProduct.DetailAdmin.Implementations;

import com.example.proyectate.DataAccess.DatabaseSQLite.Daos.ProductDao;
import com.example.proyectate.Models.Product;
import com.example.proyectate.Presentation.Dash.ManageProduct.DetailAdmin.Interfaces.IDetailPresenter;
import com.example.proyectate.Presentation.Dash.ManageProduct.DetailAdmin.Interfaces.IDetailView;

public class DetailPresenter implements IDetailPresenter {
    private final IDetailView view;
    private final ProductDao dao;

    public DetailPresenter(IDetailView view, ProductDao dao) {
        this.view = view;
        this.dao = dao;
        dao.openDb();
    }

    @Override
    public void deleteProduct(Product product) {
        view.showDeleteProduct(product.deleteProduct(dao), product.getNombre());
    }
}
