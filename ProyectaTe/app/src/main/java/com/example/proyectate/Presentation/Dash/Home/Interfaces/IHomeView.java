package com.example.proyectate.Presentation.Dash.Home.Interfaces;

import com.example.proyectate.Models.Project;
import com.example.proyectate.Utils.DialogueGenerico;

import java.util.List;

public interface IHomeView {
    void showGetAllProductsSuccess(List<Project> products);
    void showDialogFragment(int title, String detail, DialogueGenerico.TypeDialogue type);
}
