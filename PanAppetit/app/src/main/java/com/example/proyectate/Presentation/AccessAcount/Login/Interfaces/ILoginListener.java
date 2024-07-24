package com.example.proyectate.Presentation.AccessAcount.Login.Interfaces;

import com.example.proyectate.Models.User;

public interface ILoginListener {
    void responseLogin(User user);
    void credentialsIncorrect();
}
