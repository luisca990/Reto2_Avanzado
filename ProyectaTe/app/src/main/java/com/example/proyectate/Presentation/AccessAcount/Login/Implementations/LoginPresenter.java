package com.example.proyectate.Presentation.AccessAcount.Login.Implementations;

import static com.example.proyectate.Utils.Util.isNetworkAvailable;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.proyectate.Models.MessageResponse;
import com.example.proyectate.Models.User;
import com.example.proyectate.Presentation.AccessAcount.Login.Interfaces.ILoginBL;
import com.example.proyectate.Presentation.AccessAcount.Login.Interfaces.ILoginListener;
import com.example.proyectate.Presentation.AccessAcount.Login.Interfaces.ILoginPresenter;
import com.example.proyectate.Presentation.AccessAcount.Login.Interfaces.ILoginView;
import com.example.proyectate.R;
import com.example.proyectate.Utils.DialogueGenerico;

public class LoginPresenter implements ILoginPresenter {

    private final Context context;
    private final ILoginView loginView;
    private final ILoginBL bL;

    public LoginPresenter(@NonNull Context context, @NonNull ILoginView view) {
        this.context = context;
        this.loginView = view;
        this.bL = new LoginBL(context, new Listener());
    }
    @Override
    public void startSection(@NonNull User user) {
        if (!isNetworkAvailable(context)) {
            return;
        }
        if (!user.validateFieldsUser()) {
            loginView.showDialogFragment(R.string.fields_empty, context.getString(R.string.details_fields_empty), DialogueGenerico.TypeDialogue.ADVERTENCIA);
            return;
        }
        bL.startSection(user);
    }

    private class Listener implements ILoginListener {
        @Override
        public void credentialsIncorrect() {
            loginView.credentialsIncorrect();
        }

        @Override
        public void responseLogin(@NonNull User user) {
            loginView.responseLogin(user);
        }

        @Override
        public void errorLoginDB(MessageResponse mess) {
            loginView.showDialogFragment(R.string.error_user_db, mess.getMessage(), DialogueGenerico.TypeDialogue.ERROR);
        }
    }
}
