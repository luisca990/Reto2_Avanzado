package com.example.proyectate.Presentation.Dash.ManageProduct.DetailClient.Implementations;

import android.content.Context;

import com.example.proyectate.DataAccess.DatabaseSQLite.Daos.PedidoDao;
import com.example.proyectate.Models.Pedido;
import com.example.proyectate.Presentation.Dash.ManageProduct.DetailClient.Interfaces.IDetailClientPresenter;
import com.example.proyectate.Presentation.Dash.ManageProduct.DetailClient.Interfaces.IDetailClientView;
import com.example.proyectate.R;
import com.example.proyectate.Utils.DialogueGenerico;

public class DetailClientPresenter implements IDetailClientPresenter {
    private final IDetailClientView view;
    private final Context context;
    private final PedidoDao dao;

    public DetailClientPresenter(IDetailClientView view, Context context, PedidoDao dao) {
        this.view = view;
        this.context = context;
        this.dao = dao;
        dao.openDb();
    }

    @Override
    public void insertVenta(Pedido pedido) {
        if (!pedido.validateFieldsPedidos()) {
            view.showDialogAdvertence(R.string.fiels_vacio, context.getString(R.string.mess_fiels_vacio), DialogueGenerico.TypeDialogue.ADVERTENCIA);
            return;
        }
        view.showActionPedido(pedido.insertPedido(dao, pedido));
    }

    @Override
    public void updateVenta(Pedido pedido) {
        if (!pedido.validateFieldsPedidos()) {
            view.showDialogAdvertence(R.string.fiels_vacio, context.getString(R.string.mess_fiels_vacio), DialogueGenerico.TypeDialogue.ADVERTENCIA);
        return;
    }
        view.showActionPedido(pedido.updatePedido(dao, pedido));
    }
}
