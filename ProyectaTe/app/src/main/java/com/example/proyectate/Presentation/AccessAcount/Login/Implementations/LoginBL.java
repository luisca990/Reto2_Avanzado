package com.example.proyectate.Presentation.AccessAcount.Login.Implementations;


import android.content.Context;

import androidx.annotation.NonNull;

import com.example.proyectate.DataAccess.DatabaseSQLite.Daos.UserDao;
import com.example.proyectate.DataAccess.Repositories.IRepository;
import com.example.proyectate.DataAccess.Repositories.RepoLogin;
import com.example.proyectate.DataAccess.Services;
import com.example.proyectate.DataAccess.SharedPreferences.SessionManager;
import com.example.proyectate.Models.MessageResponse;
import com.example.proyectate.Models.User;
import com.example.proyectate.Presentation.AccessAcount.Login.Interfaces.ILoginBL;
import com.example.proyectate.Presentation.AccessAcount.Login.Interfaces.ILoginListener;

public class LoginBL implements ILoginBL {

    private final Context context;
    private final ILoginListener listener;
    private final SessionManager sessionManager;
    private final UserDao dao;


    public LoginBL(Context context, ILoginListener loginListener) {
        this.context = context;
        this.listener = loginListener;
        this.sessionManager = new SessionManager(context);
        this.dao = new UserDao(context);
        dao.openDb();
    }

    @Override
    public void startSection(@NonNull User user) {
        new RepoLogin().LogIn(context, user, new ListenerRepositories(), Services.LOGIN);
    }

    private class ListenerRepositories implements IRepository {

        @Override
        public void onSuccessResponse(Object objectResponse, Services services) {
            if (services == Services.LOGIN) {
                User user = (User) objectResponse;
                listener.responseLogin(user);
                long userID = dao.insertUser(user);
                if (userID == -1) {
                    listener.errorLoginDB(new MessageResponse(0, "Hubo un error al ingresar en la base de datos"));
                    return;
                }
                sessionManager.createLoginSession(user.getEmail(), user.getId());
                dao.closeDb();
            }
        }

        @Override
        public void onFailedResponse(MessageResponse response, Services services) {
            if (services == Services.LOGIN) {
                listener.credentialsIncorrect();
            }
        }
    }
}
