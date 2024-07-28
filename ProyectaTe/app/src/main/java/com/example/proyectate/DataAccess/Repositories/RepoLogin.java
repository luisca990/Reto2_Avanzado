package com.example.proyectate.DataAccess.Repositories;

import android.content.Context;
import com.example.proyectate.DataAccess.Firebase.Login;
import com.example.proyectate.DataAccess.Services;
import com.example.proyectate.Models.MessageResponse;
import com.example.proyectate.Models.User;

public class RepoLogin {
    public <T extends IRepository> void LogIn(Context context, User user, T responder, Services servicio) {
        new Login(context,
                user1 -> responder.onSuccessResponse(user1, servicio),
                () -> responder.onFailedResponse(new MessageResponse(), servicio)
        ).loginWithUser(user);
    }

    public <T extends IRepository> void RegisterUser(Context context, User user, T responder, Services servicio) {
        new Login(context,
                user1 -> responder.onSuccessResponse(user1, servicio),
                () -> responder.onFailedResponse(new MessageResponse(), servicio)
        ).registerUser(user);
    }
}
