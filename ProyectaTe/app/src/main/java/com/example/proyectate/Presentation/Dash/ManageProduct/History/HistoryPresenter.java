package com.example.proyectate.Presentation.Dash.ManageProduct.History;

import com.example.proyectate.DataAccess.DatabaseSQLite.Daos.VentaDao;
import com.example.proyectate.Models.Venta;
import com.example.proyectate.Presentation.Dash.ManageProduct.History.Interfaces.IHistoryPresenter;
import com.example.proyectate.Presentation.Dash.ManageProduct.History.Interfaces.IHistoryView;

public class HistoryPresenter implements IHistoryPresenter {
    private final IHistoryView view;
    private final VentaDao dao;

    public HistoryPresenter(IHistoryView view, VentaDao dao) {
        this.view = view;
       this.dao = dao;
        dao.openDb();
    }

    @Override
    public void getAllVentas(int idUser) {
        view.getAllVentas(Venta.getAllVentas(dao, idUser));
    }
}
