package com.example.proyectate.Utils;

public class Constants {
    public static class Tag {
        public static final String LOGIN = "Login";
        public static final String PROJECT = "project";
        public static final String REGISTER = "Register";
    }

    //  Nombres Tablas
    public static final String TABLE_PROJECTS = "projects";
    public static final String TABLE_USERS = "users";

    // Query Tablas
    public static final String CREATE_TABLE_PROJECTS = "CREATE TABLE " + TABLE_PROJECTS + "("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "title TEXT NOT NULL,"
            + "description TEXT,"
            + "user_id TEXT NOT NULL,"
            + "date_init TEXT NOT NULL,"
            + "date_end TEXT NOT NULL,"
            + "imagen TEXT,"
            + "FOREIGN KEY (user_id) REFERENCES users(id)"
            + ")";
    public static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + "id TEXT PRIMARY KEY,"
            + "email TEXT NOT NULL UNIQUE"
            + ")";
}
