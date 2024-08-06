package com.example.proyectate.Presentation.AccessAcount.Login.Implementations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import com.example.proyectate.Base.BaseFragment;
import com.example.proyectate.Models.User;
import com.example.proyectate.Presentation.AccessAcount.Login.Interfaces.ILoginPresenter;
import com.example.proyectate.Presentation.AccessAcount.Login.Interfaces.ILoginView;
import com.example.proyectate.R;
import com.example.proyectate.Utils.DialogueGenerico;
import com.example.proyectate.Utils.Util;
import com.example.proyectate.databinding.FragmentLoginBinding;

public class LoginFragment extends BaseFragment {
    private ILoginPresenter presenter;
    private final actionListenerViewPresenter actionPresenter = new actionListenerViewPresenter();
    private User user;
    private FragmentLoginBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(getLayoutInflater());
        setCustomView(binding.getRoot());

        return getCustomView();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter = new LoginPresenter(requireContext(), actionPresenter);
        Util.validateEmailFormat(binding.etEmailLogin, binding.textFieldEmail, getString(R.string.error_correo_formato));
        binding.btnStartSection.setOnClickListener(v->{
            user = new User();
            user.setEmail(binding.etEmailLogin.getText().toString());
            user.setPassword(binding.etPassLogin.getText().toString());
            presenter.startSection(user);
        });
        binding.tvRegisterUser.setOnClickListener(v-> Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_registerFragment));
    }

    private class actionListenerViewPresenter implements ILoginView {

        @Override
        public void responseLogin(@NonNull User user) {
            Toast.makeText(getContext(), getString(R.string.login_user), Toast.LENGTH_SHORT).show();
            Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment);
        }

        @Override
        public void credentialsIncorrect() {
            dialogueFragment(R.string.credentials_incorrect,
                    getString(R.string.details_credentials_incorrect),
                    DialogueGenerico.TypeDialogue.ERROR);
        }

        @Override
        public void showDialogFragment(int title, String detail, DialogueGenerico.TypeDialogue type) {
            dialogueFragment(title, detail, type);
        }
    }
}