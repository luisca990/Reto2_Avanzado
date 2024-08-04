package com.example.proyectate.Presentation.Dash.ManageProduct.AddUpdate.Implementations;

import android.content.Context;

import com.example.proyectate.DataAccess.DatabaseSQLite.Daos.ProjectDao;
import com.example.proyectate.Models.Project;
import com.example.proyectate.Presentation.Dash.ManageProduct.AddUpdate.Interfaces.IAddUpdatePresenter;
import com.example.proyectate.Presentation.Dash.ManageProduct.AddUpdate.Interfaces.IAddUpdateView;
import com.example.proyectate.R;
import com.example.proyectate.Utils.DialogueGenerico;

public class AddUpdatePresenter implements IAddUpdatePresenter {
    private final IAddUpdateView view;
    private final Context context;
    private final ProjectDao dao;

    public AddUpdatePresenter(IAddUpdateView view, Context context, ProjectDao dao) {
        this.view = view;
        this.context = context;
        this.dao = dao;
        dao.openDb();
    }

    @Override
    public void insertProject(Project product) {
        if (!product.validateFieldsProject()) {
            view.showDialogAdvertence(R.string.fiels_vacio, context.getString(R.string.mess_fiels_vacio), DialogueGenerico.TypeDialogue.ADVERTENCIA);
            return;
        }
        view.showInsertProduct(product.insertProject(dao), product.getTitle());
    }

    @Override
    public void updateProject(Project product) {
        if (!product.validateFieldsProject()) {
            view.showDialogAdvertence(R.string.fiels_vacio, context.getString(R.string.mess_fiels_vacio), DialogueGenerico.TypeDialogue.ADVERTENCIA);
            return;
        }
        view.showUpdateProduct(product.updateProject(dao), product.getTitle());
    }
}
