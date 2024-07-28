package com.example.proyectate.Presentation.AccessAcount.Login.Interfaces;

import com.example.proyectate.Models.MessageResponse;
import com.example.proyectate.Models.User;

public interface ILoginListener {
    void responseLogin(User user);
    void errorLoginDB(MessageResponse mess);
    void credentialsIncorrect();
}
