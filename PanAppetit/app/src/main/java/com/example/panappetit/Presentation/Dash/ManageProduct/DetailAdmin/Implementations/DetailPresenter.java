package com.example.panappetit.Presentation.Dash.ManageProduct.DetailAdmin.Implementations;

import android.content.Context;
import com.example.panappetit.DataAccess.DatabaseSQLite.Daos.ProductDao;
import com.example.panappetit.Models.Product;
import com.example.panappetit.Presentation.Dash.ManageProduct.DetailAdmin.Interfaces.IDetailPresenter;
import com.example.panappetit.Presentation.Dash.ManageProduct.DetailAdmin.Interfaces.IDetailView;

public class DetailPresenter implements IDetailPresenter {
    private final IDetailView view;
    private final Context context;
    private final ProductDao dao;

    public DetailPresenter(IDetailView view, Context context) {
        this.view = view;
        this.context = context;
        dao = new ProductDao(context);
    }

    @Override
    public void deleteProduct(Product product) {
        dao.openDb();
        view.showDeleteProduct(product.deleteProduct(dao), product.getNombre());
        dao.closeDb();
    }
}
