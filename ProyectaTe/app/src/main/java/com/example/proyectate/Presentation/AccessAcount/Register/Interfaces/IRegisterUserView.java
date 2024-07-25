package com.example.proyectate.Presentation.AccessAcount.Register.Interfaces;

import com.example.proyectate.Models.MessageResponse;
import com.example.proyectate.Models.User;
import com.example.proyectate.Utils.DialogueGenerico;

public interface IRegisterUserView {
    void responseRegisterUser(User user);
    void messageError(MessageResponse message);
    void showDialogFragment(int title, int detail, DialogueGenerico.TypeDialogue type);
}
