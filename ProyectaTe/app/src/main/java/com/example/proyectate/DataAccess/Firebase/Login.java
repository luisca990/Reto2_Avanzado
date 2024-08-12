package com.example.proyectate.DataAccess.Firebase;

import android.content.Context;
import android.util.Log;

import com.example.proyectate.DataAccess.Firebase.interfaces.OnLoginSuccessListener;
import com.example.proyectate.Models.User;
import com.example.proyectate.R;
import com.example.proyectate.Utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login {
    private final OnLoginSuccessListener messageSuccess;
    private final Runnable failure;
    private final Context context;

    public Login(Context context, OnLoginSuccessListener messageSuccess, Runnable failure) {
        this.messageSuccess = messageSuccess;
        this.failure = failure;
        this.context = context;
    }

    public void loginWithUser(User user) {
        FirebaseAuth instanceFirebase = FirebaseAuth.getInstance();
        instanceFirebase.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = instanceFirebase.getCurrentUser();
                        if (firebaseUser != null) {
                            user.setId(firebaseUser.getUid());
                        }
                        messageSuccess.onLoginSuccess(user);
                        return;
                    }
                    Log.e(Constants.Tag.LOGIN, context.getString(R.string.firebase_login), task.getException());
                    failure.run();
                });
    }

    public void registerUser(User user) {
        FirebaseAuth instanceFirebase = FirebaseAuth.getInstance();
        instanceFirebase.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        messageSuccess.onLoginSuccess(user);
                        return;
                    }
                    Log.e(Constants.Tag.REGISTER, context.getString(R.string.firebase_register), task.getException());
                    failure.run();
                });
    }
}
