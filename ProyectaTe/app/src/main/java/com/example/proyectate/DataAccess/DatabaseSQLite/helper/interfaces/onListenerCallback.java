package com.example.proyectate.DataAccess.DatabaseSQLite.helper.interfaces;

public interface onListenerCallback {
    void onSuccessChecked(boolean exists);
    void onError(Exception e);
}
