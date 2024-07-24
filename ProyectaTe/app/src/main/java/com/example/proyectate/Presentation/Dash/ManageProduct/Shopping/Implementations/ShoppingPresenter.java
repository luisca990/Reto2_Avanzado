package com.example.proyectate.Presentation.Dash.ManageProduct.Shopping.Implementations;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.proyectate.DataAccess.DatabaseSQLite.Daos.VentaDao;
import com.example.proyectate.Models.Venta;
import com.example.proyectate.Presentation.Dash.ManageProduct.Shopping.Interfaces.IShoppingPresenter;
import com.example.proyectate.Presentation.Dash.ManageProduct.Shopping.Interfaces.IShoppingView;
import com.example.proyectate.R;
import com.example.proyectate.Utils.DialogueGenerico;

public class ShoppingPresenter implements IShoppingPresenter {
    private final IShoppingView view;
    private final Context context;
    private final VentaDao dao;

    public ShoppingPresenter(IShoppingView view, Context context, VentaDao dao) {
        this.view = view;
        this.context = context;
        this.dao = dao;
        this.dao.openDb();
    }

    @Override
    public void insertVenta(@NonNull Venta venta, int idPedido) {
        if (!venta.validateFieldsVenta()) {
            view.showDialogAdvertence(R.string.fiels_vacio, context.getString(R.string.mess_fiels_vacio), DialogueGenerico.TypeDialogue.ADVERTENCIA);
            return;
        }
        view.showActionVenta(venta.insertVenta(dao, venta, idPedido));
    }

    @Override
    public void deleteDetallePedido(int idProduct, int idPedido) {
        view.showActionDelete(Venta.deleteDetallePedido(dao, idProduct, idPedido));
    }
}
