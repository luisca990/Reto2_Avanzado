package com.example.proyectate.Presentation;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import com.example.proyectate.Presentation.AccessAcount.SplashFragment;
import com.example.proyectate.Presentation.AccessAcount.Login.Implementations.LoginFragment;
import com.example.proyectate.Presentation.Dash.Home.Implementations.HomeFragment;
import com.example.proyectate.R;
import com.example.proyectate.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        EdgeToEdge.enable(this);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public void onBackPressed() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        Fragment currentFragment = navHostFragment.getChildFragmentManager().getPrimaryNavigationFragment();

        if (currentFragment instanceof SplashFragment || currentFragment instanceof LoginFragment|| currentFragment instanceof HomeFragment) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}