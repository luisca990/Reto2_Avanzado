package com.example.proyectate.Presentation.Dash.ManageProduct.History.Interfaces;

import com.example.proyectate.Models.Venta;
import com.example.proyectate.Utils.DialogueGenerico;

import java.util.List;

public interface IHistoryView {
    void getAllVentas(List<Venta> list);
    void showDialogAdvertence(int title, String message, DialogueGenerico.TypeDialogue type);
}
