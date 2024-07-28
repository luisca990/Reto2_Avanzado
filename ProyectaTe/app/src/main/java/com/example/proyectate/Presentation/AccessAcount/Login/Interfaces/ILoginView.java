package com.example.proyectate.Presentation.AccessAcount.Login.Interfaces;

import com.example.proyectate.Models.User;
import com.example.proyectate.Utils.DialogueGenerico;

public interface ILoginView {
    void responseLogin(User user);
    void credentialsIncorrect();
    void showDialogFragment(int title, String detail, DialogueGenerico.TypeDialogue type);
}
