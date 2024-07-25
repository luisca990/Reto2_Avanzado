package com.example.proyectate.Presentation.AccessAcount.Register.Implementations;

import static com.example.proyectate.Utils.Util.isNetworkAvailable;

import android.content.Context;

import com.example.proyectate.Models.User;
import com.example.proyectate.Presentation.AccessAcount.Register.Interfaces.IRegisterUserBL;
import com.example.proyectate.Presentation.AccessAcount.Register.Interfaces.IRegisterUserListener;
import com.example.proyectate.Presentation.AccessAcount.Register.Interfaces.IRegisterUserPresenter;
import com.example.proyectate.Presentation.AccessAcount.Register.Interfaces.IRegisterUserView;
import com.example.proyectate.R;
import com.example.proyectate.Utils.DialogueGenerico;

public class RegisterUserPresenter implements IRegisterUserPresenter {

    private final Context context;
    private final IRegisterUserView registerView;
    private final IRegisterUserBL bL;

    public RegisterUserPresenter(Context context, IRegisterUserView view) {
        this.context = context;
        this.registerView = view;
        this.bL = new RegisterUserBL(context, new Listener());
    }

    @Override
    public void registerUser(User user) {
        if (!user.validatePassEqualConfirPass()) {
            registerView.showDialogFragment(
                    R.string.password,
                    R.string.details_password,
                    DialogueGenerico.TypeDialogue.ADVERTENCIA
            );
            return;
        }
        if (!isNetworkAvailable(context)) {
            registerView.showDialogFragment(
                    R.string.internet,
                    R.string.not_connection_internet,
                    DialogueGenerico.TypeDialogue.ADVERTENCIA
            );
            return;
        }
        if (!user.validateFieldsUser()) {
            registerView.showDialogFragment(
                    R.string.fields_empty,
                    R.string.details_fields_empty,
                    DialogueGenerico.TypeDialogue.ADVERTENCIA
            );
            return;
        }
        bL.registerUser(user);
    }

    private class Listener implements IRegisterUserListener {
        @Override
        public void responseRegisterUser(User user) {
            registerView.responseRegisterUser(user);
        }
    }
}
