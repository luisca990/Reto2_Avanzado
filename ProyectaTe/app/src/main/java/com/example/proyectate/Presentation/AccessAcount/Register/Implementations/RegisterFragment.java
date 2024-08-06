package com.example.proyectate.Presentation.AccessAcount.Register.Implementations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import com.example.proyectate.Base.BaseFragment;
import com.example.proyectate.Models.MessageResponse;
import com.example.proyectate.Models.User;
import com.example.proyectate.Presentation.AccessAcount.Register.Interfaces.IRegisterUserPresenter;
import com.example.proyectate.Presentation.AccessAcount.Register.Interfaces.IRegisterUserView;
import com.example.proyectate.R;
import com.example.proyectate.Utils.DialogueGenerico;
import com.example.proyectate.Utils.Util;
import com.example.proyectate.databinding.FragmentRegisterBinding;


public class RegisterFragment extends BaseFragment {

    private IRegisterUserPresenter presenter;
    private final IRegisterUserView actionPresenter = new ActionViewPresenter();
    private FragmentRegisterBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(getLayoutInflater());
        setCustomView(binding.getRoot());

        presenter = new RegisterUserPresenter(getContext(), actionPresenter);

        return getCustomView();
    }
    @Override
    public void onResume() {
        super.onResume();
        binding.btnCrearRegister.setOnClickListener(v -> {
            User user = new User();
            user.setEmail(binding.etEmailRegister.getText().toString());
            user.setPassword(binding.etPassRegister.getText().toString());
            user.setConfPassword(binding.etConfPassRegister.getText().toString());
            presenter.registerUser(user);
        });
        Util.validateEmailFormat(binding.etEmailRegister, binding.textFieldEmailRegister, getString(R.string.error_correo_formato));
        Util.validateSizePassCharacters(binding.etPassRegister, binding.textFieldPassRegister);
    }

    private class ActionViewPresenter implements IRegisterUserView {

        @Override
        public void responseRegisterUser(User user) {
            Toast.makeText(getContext(), getString(R.string.user_create), Toast.LENGTH_LONG).show();
            Navigation.findNavController(requireView()).navigateUp();
        }

        @Override
        public void messageError(MessageResponse message) {
            if (message.getMessage() != null) {
                dialogueFragment(R.string.Error, message.getMessage(), DialogueGenerico.TypeDialogue.ERROR);
            }
        }

        @Override
        public void showDialogFragment(int title, int detail, DialogueGenerico.TypeDialogue type) {
            dialogueFragment(title, getString(detail), type);
        }
    }
}