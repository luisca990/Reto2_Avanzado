package com.example.proyectate.Presentation.Dash.ManageProduct.AddUpdate.Interfaces;

import com.example.proyectate.Utils.DialogueGenerico;

public interface IAddUpdateView {
    void showInsertProduct(int id, String name);
    void showUpdateProduct(int id, String name);
    void showDialogAdvertence(int title, String message, DialogueGenerico.TypeDialogue type);
}
