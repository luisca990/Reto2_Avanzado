package com.example.proyectate.DataAccess;

public interface IServiceParameters {
    enum Methods {
        GET,
        POST,
        PUT,
        DELETE
    }

    String getURL();

    Methods getMethods();
}
