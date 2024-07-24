package com.example.proyectate.Presentation.Dash.ManageProduct.DetailClient.Interfaces;

import com.example.proyectate.Utils.DialogueGenerico;

public interface IDetailClientView {
    void showActionPedido(int id);
    void showDialogAdvertence(int title, String message, DialogueGenerico.TypeDialogue type);
}
