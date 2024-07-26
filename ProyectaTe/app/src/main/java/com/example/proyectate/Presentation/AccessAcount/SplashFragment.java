package com.example.proyectate.Presentation.AccessAcount;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import com.example.proyectate.Base.BaseFragment;
import com.example.proyectate.DataAccess.SharedPreferences.SessionManager;
import com.example.proyectate.R;
import com.example.proyectate.databinding.FragmentSplashBinding;

public class SplashFragment extends BaseFragment {
    private SessionManager sessionManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentSplashBinding binding = FragmentSplashBinding.inflate(getLayoutInflater());
        setCustomView(binding.getRoot());
        return getCustomView();
    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(() -> {
            if (sessionManager.isLoggedIn()) {
                Navigation.findNavController(requireView()).navigate(R.id.action_splashFragment_to_homeFragment);
            } else {
                Navigation.findNavController(requireView()).navigate(R.id.action_splashFragment_to_loginFragment);
            }
        }, 4000);
        sessionManager = new SessionManager(requireContext());
    }
}