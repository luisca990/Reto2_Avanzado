package com.example.proyectate.DataAccess.Firebase.interfaces;

public interface onListenerCallback {
    void onSuccessChecked(boolean exists);
    void onError(Exception e);
}
