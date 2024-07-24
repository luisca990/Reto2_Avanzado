package com.example.proyectate.Presentation.AccessAcount.Login.Implementations;


import android.content.Context;

import androidx.annotation.NonNull;

import com.example.proyectate.DataAccess.DatabaseSQLite.Daos.UsuarioDao;
import com.example.proyectate.DataAccess.Repositories.IRepository;
import com.example.proyectate.DataAccess.Repositories.RepoLogin;
import com.example.proyectate.DataAccess.Services;
import com.example.proyectate.DataAccess.SharedPreferences.SessionManager;
import com.example.proyectate.Models.MessageResponse;
import com.example.proyectate.Models.Pedido;
import com.example.proyectate.Models.User;
import com.example.proyectate.Presentation.AccessAcount.Login.Interfaces.ILoginBL;
import com.example.proyectate.Presentation.AccessAcount.Login.Interfaces.ILoginListener;

public class LoginBL implements ILoginBL {

    private final Context context;
    private final ILoginListener listener;
    private final SessionManager sessionManager;
    private final UsuarioDao dao;


    public LoginBL(Context context, ILoginListener loginListener) {
        this.context = context;
        this.listener = loginListener;
        this.sessionManager = new SessionManager(context);
        this.dao = new UsuarioDao(context);
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
                Pedido pedido = dao.getPedidoIdByUsuarioId(userID);
                if (pedido != null) sessionManager.setPedido(pedido.getId(), pedido.getDate(), pedido.getMontoTotal());
                sessionManager.createLoginSession(user.getEmail(), (int) userID);
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
