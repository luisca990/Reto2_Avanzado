package com.example.proyectate.Presentation.AccessAcount;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.Navigation;
import com.example.proyectate.Base.BaseFragment;
import com.example.proyectate.DataAccess.SharedPreferences.SessionManager;
import com.example.proyectate.R;
import com.example.proyectate.Utils.Constants;

import java.util.Objects;

public class SplashFragment extends BaseFragment {
    private SessionManager sessionManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setCustomView(inflater.inflate(R.layout.fragment_splash, container, false));
        new Handler().postDelayed(() -> {
            if (sessionManager.isLoggedIn()) {
                Bundle bundle = new Bundle();
                if ((Objects.equals(sessionManager.getUserEmail(), Constants.Tag.ADMIN))) {
                    bundle.putString(Constants.Tag.USER, "admin");
                } else {
                    bundle.putString(Constants.Tag.USER, "client");
                }
                Navigation.findNavController(requireView()).navigate(R.id.action_splashFragment_to_homeFragment, bundle);
            } else {
                Navigation.findNavController(requireView()).navigate(R.id.action_splashFragment_to_loginFragment);
            }
        }, 4000);
        sessionManager = new SessionManager(requireContext());
        return getCustomView();
    }
}